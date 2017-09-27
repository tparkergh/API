package gov.ca.cwds.rest.services.investigation;

import gov.ca.cwds.data.Dao;
import gov.ca.cwds.data.cms.LongTextDao;
import gov.ca.cwds.data.cms.StaffPersonDao;
import gov.ca.cwds.data.dao.contact.DeliveredServiceDao;
import gov.ca.cwds.data.dao.contact.IndividualDeliveredServiceDao;
import gov.ca.cwds.data.dao.contact.ReferralClientDeliveredServiceDao;
import gov.ca.cwds.data.persistence.cms.LongText;
import gov.ca.cwds.data.persistence.cms.StaffPerson;
import gov.ca.cwds.data.persistence.contact.DeliveredServiceEntity;
import gov.ca.cwds.data.persistence.contact.IndividualDeliveredServiceEntity;
import gov.ca.cwds.data.persistence.contact.ReferralClientDeliveredServiceEntity;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.LastUpdatedBy;
import gov.ca.cwds.rest.api.domain.PostedIndividualDeliveredService;
import gov.ca.cwds.rest.api.domain.investigation.contact.Contact;
import gov.ca.cwds.rest.api.domain.investigation.contact.ContactList;
import gov.ca.cwds.rest.api.domain.investigation.contact.ContactRequest;
import gov.ca.cwds.rest.filters.RequestExecutionContext;
import gov.ca.cwds.rest.services.ServiceException;
import gov.ca.cwds.rest.services.TypedCrudsService;
import io.dropwizard.jackson.Jackson;

import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.apache.commons.lang3.NotImplementedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;

/**
 * Business layer object to work on {@link DeliveredServiceEntity}
 * 
 * @author CWDS API Team
 */
public class ContactService implements TypedCrudsService<String, ContactRequest, Response> {

  private static final Logger LOGGER = LoggerFactory.getLogger(ContactService.class);

  private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

  private String lastUpdatedId = RequestExecutionContext.instance().getUserId();
  private Date lastUpdatedTime = RequestExecutionContext.instance().getRequestStartTime();
  private Contact validContact = validContact();
  private Set<Integer> services = new HashSet<>();

  private DeliveredServiceDao deliveredServiceDao;
  private StaffPersonDao staffPersonDao;
  private LongTextDao longTextDao;
  private IndividualDeliveredServiceDao individualDeliveredServiceDao;
  private ReferralClientDeliveredServiceDao referralClientDeliveredServiceDao;
  private DeliveredToIndividualService deliveredToIndividualService;

  /**
   * @param deliveredServiceDao the {@link Dao} handling
   *        {@link gov.ca.cwds.data.persistence.contact.DeliveredServiceEntity} objects
   * @param staffPersonDao the {@link Dao} handling
   *        {@link gov.ca.cwds.data.persistence.cms.StaffPerson} objects.
   * @param longTextDao the {@link Dao} handling {@link gov.ca.cwds.data.persistence.cms.LongText}
   *        objects
   * @param individualDeliveredServiceDao the {@link Dao} handling
   *        {@link gov.ca.cwds.data.persistence.contact.IndividualDeliveredServiceEntity} objects
   * @param referralClientDeliveredServiceDao the {@link Dao} handling
   *        {@link gov.ca.cwds.data.persistence.contact.ReferralClientDeliveredServiceEntity}
   *        objects
   * @param deliveredToIndividualService the deliveredToIndividualService
   */
  @Inject
  public ContactService(DeliveredServiceDao deliveredServiceDao, StaffPersonDao staffPersonDao,
      LongTextDao longTextDao, IndividualDeliveredServiceDao individualDeliveredServiceDao,
      ReferralClientDeliveredServiceDao referralClientDeliveredServiceDao,
      DeliveredToIndividualService deliveredToIndividualService) {
    super();
    this.deliveredServiceDao = deliveredServiceDao;
    this.staffPersonDao = staffPersonDao;
    this.longTextDao = longTextDao;
    this.individualDeliveredServiceDao = individualDeliveredServiceDao;
    this.referralClientDeliveredServiceDao = referralClientDeliveredServiceDao;
    this.deliveredToIndividualService = deliveredToIndividualService;
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#create(gov.ca.cwds.rest.api.Request)
   */
  @Override
  public Response find(String primaryKey) {
    if (!primaryKey.contains(":")) {
      Contact contact = validContact;
      final Set<Contact> contacts = new HashSet<>();
      contacts.add(contact);
      return new ContactList(contacts);
    } else {
      String contactId = retrieveContactId(primaryKey);
      final DeliveredServiceEntity deliveredServiceEntity = deliveredServiceDao.find(contactId);
      if (deliveredServiceEntity == null) {
        return null;
      } else {
        LastUpdatedBy lastUpdatedBy = getTheLastUpdatedByStaffPerson(deliveredServiceEntity);
        String note = combineDetailTextAndContinuation(deliveredServiceEntity);

        Set<PostedIndividualDeliveredService> peopleInIndividualDeliveredService =
            getPeopleInIndividualDeliveredService(deliveredServiceEntity);

        return new Contact(deliveredServiceEntity, lastUpdatedBy, note,
            peopleInIndividualDeliveredService);
      }
    }

  }

  /**
   * Extract Contact Id from the primary key and return it if it is valid for the Referral Id
   * 
   * @param primaryKey the combination of referralId and contactId
   * @return The contactId
   */
  private String retrieveContactId(String primaryKey) {
    String[] ids = primaryKey.split(":");
    String referralId = ids[0].trim();
    String contactId = ids[1].trim();
    if (!isContactIdValidForGivenReferralId(referralId, contactId)) {
      throw new ServiceException("ContactId Is Not Valid For the Given ReferralId");
    }
    return contactId;
  }

  /**
   * Check if Contact Id is valid for the Referral Id
   * 
   * @param referralId the Referral Id
   * @param contactId the Contact Id
   * @return true if Contact Id is valid for the Referral Id
   */
  private boolean isContactIdValidForGivenReferralId(String referralId, String contactId) {

    ReferralClientDeliveredServiceEntity[] referralClientDeliveredServiceEntities =
        referralClientDeliveredServiceDao.findByReferralId(referralId);
    if (referralClientDeliveredServiceEntities.length == 0) {
      throw new ServiceException("There are no Contacts For the Given ReferralId");
    }
    for (ReferralClientDeliveredServiceEntity referralClientDeliveredServiceEntity : referralClientDeliveredServiceEntities) {
      if (referralClientDeliveredServiceEntity.getReferralClientDeliveredServiceEmbeddable()
          .getDeliveredServiceId().equals(contactId)) {
        return true;
      }
    }
    return false;
  }

  /**
   * Find the Staff Person who last updated the deliveredServiceEntity persistence object
   * 
   * @param deliveredServiceEntity The persistence object
   * @return The Staff Person who last updated the persistence object
   */
  private LastUpdatedBy getTheLastUpdatedByStaffPerson(DeliveredServiceEntity deliveredServiceEntity) {
    StaffPerson lastUpdtatedId = staffPersonDao.find(deliveredServiceEntity.getLastUpdatedId());
    if (lastUpdtatedId != null) {
      return new LastUpdatedBy(lastUpdtatedId);
    }
    return new LastUpdatedBy();
  }

  /**
   * Combine DetailText And DetailTextContinuation from the deliveredServiceEntity persistence
   * object
   * 
   * @param deliveredServiceEntity The persistence object
   * @return the combined DetailText And DetailTextContinuation
   */
  private String combineDetailTextAndContinuation(DeliveredServiceEntity deliveredServiceEntity) {
    String detailText = deliveredServiceEntity.getDetailText();
    String detailTextContinuation = deliveredServiceEntity.getDetailTextContinuation();
    return getLongText(detailText) + getLongText(detailTextContinuation);
  }

  /**
   * Find from the Long Text table the text value of the Detail Text
   * 
   * @param detailText the Detail Text
   * @return text value of the Detail Text
   */
  private String getLongText(String detailText) {
    LongText detail = new LongText();
    if (detailText != null) {
      detail = longTextDao.find(detailText);
    }
    return (Optional.of(detail)).map(LongText::getTextDescription).orElse("");
  }

  /**
   * Find the IndividualDeliveredService persistence objects for the deliveredServiceEntity and
   * include the person information for each
   * 
   * @param deliveredServiceEntity The persistence object
   * @return People In IndividualDeliveredService
   */
  private Set<PostedIndividualDeliveredService> getPeopleInIndividualDeliveredService(
      DeliveredServiceEntity deliveredServiceEntity) {
    final Set<PostedIndividualDeliveredService> people = new HashSet<>();
    final IndividualDeliveredServiceEntity[] individualDeliveredServices =
        individualDeliveredServiceDao.findByDeliveredServiceId(deliveredServiceEntity.getId());
    for (IndividualDeliveredServiceEntity individualDeliveredService : individualDeliveredServices) {
      people.add(deliveredToIndividualService.findPerson(individualDeliveredService));
    }
    return people;
  }


  @Override
  public Contact delete(String primaryKey) {
    throw new NotImplementedException("delete not implemented");
  }

  @Override
  public Response create(ContactRequest request) {
    return validContact;
  }

  @Override
  public Response update(String primaryKey, ContactRequest request) {
    return validContact;
  }

  private Contact validContact() {
    final Set<PostedIndividualDeliveredService> people = validPeople();
    LastUpdatedBy lastUpdatedByPerson =
        new LastUpdatedBy("0X5", "Joe", "M", "Friday", "Mr.", "Jr.");
    return new Contact("1234567ABC", lastUpdatedByPerson, "2010-04-27T23:30:14.000Z", "", 433, 408,
        "C", services, 415,
        "some text describing the contact of up to 8000 characters can be stored in CMS", people);
  }

  private Set<PostedIndividualDeliveredService> validPeople() {
    final Set<PostedIndividualDeliveredService> ret = new HashSet<>();
    ret.add(new PostedIndividualDeliveredService("CLIENT_T", "3456789ABC", "John", "Bob", "Smith",
        "Mr.", "Jr.", ""));
    ret.add(new PostedIndividualDeliveredService("REPTR_T", "4567890ABC ", "Sam", "Bill", "Jones",
        "Mr.", "III", "Reporter"));
    return ret;
  }

}
