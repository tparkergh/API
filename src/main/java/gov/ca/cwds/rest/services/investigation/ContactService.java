package gov.ca.cwds.rest.services.investigation;

import gov.ca.cwds.data.Dao;
import gov.ca.cwds.data.cms.ChildClientDao;
import gov.ca.cwds.data.cms.ReferralClientDao;
import gov.ca.cwds.data.cms.ReferralDao;
import gov.ca.cwds.data.cms.StaffPersonDao;
import gov.ca.cwds.data.dao.contact.ContactPartyDeliveredServiceDao;
import gov.ca.cwds.data.dao.contact.DeliveredServiceDao;
import gov.ca.cwds.data.dao.contact.ReferralClientDeliveredServiceDao;
import gov.ca.cwds.data.persistence.cms.ChildClient;
import gov.ca.cwds.data.persistence.cms.CmsKeyIdGenerator;
import gov.ca.cwds.data.persistence.cms.Referral;
import gov.ca.cwds.data.persistence.cms.ReferralClient;
import gov.ca.cwds.data.persistence.cms.StaffPerson;
import gov.ca.cwds.data.persistence.contact.ContactPartyDeliveredServiceEntity;
import gov.ca.cwds.data.persistence.contact.DeliveredServiceEntity;
import gov.ca.cwds.data.persistence.contact.ReferralClientDeliveredServiceEntity;
import gov.ca.cwds.rest.api.ApiException;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.contact.DeliveredServiceDomain;
import gov.ca.cwds.rest.api.domain.DomainChef;
import gov.ca.cwds.rest.api.domain.LastUpdatedBy;
import gov.ca.cwds.rest.api.domain.PostedIndividualDeliveredService;
// import gov.ca.cwds.data.persistence.cms.LongText;
import gov.ca.cwds.rest.api.domain.cms.LongText;
import gov.ca.cwds.rest.api.domain.cms.PostedLongText;
import gov.ca.cwds.rest.api.domain.investigation.contact.Contact;
import gov.ca.cwds.rest.api.domain.investigation.contact.ContactList;
import gov.ca.cwds.rest.api.domain.investigation.contact.ContactReferralRequest;
import gov.ca.cwds.rest.api.domain.investigation.contact.ContactRequest;
import gov.ca.cwds.rest.filters.RequestExecutionContext;
import gov.ca.cwds.rest.services.ServiceException;
import gov.ca.cwds.rest.services.TypedCrudsService;
import gov.ca.cwds.rest.services.cms.LongTextService;
import io.dropwizard.jackson.Jackson;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.persistence.EntityExistsException;

import org.apache.commons.lang3.NotImplementedException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;

/**
 * Business layer object to work on {@link DeliveredServiceEntity}
 * 
 * @author CWDS API Team
 */
public class ContactService implements TypedCrudsService<String, ContactReferralRequest, Response> {

  private static final Logger LOGGER = LoggerFactory.getLogger(ContactService.class);

  private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

  private String lastUpdatedId = RequestExecutionContext.instance().getStaffId();
  private Date lastUpdatedTime = RequestExecutionContext.instance().getRequestStartTime();
  private Contact validContact = validContact();
  private Set<Integer> services = new HashSet<>();

  private DeliveredServiceDao deliveredServiceDao;
  private StaffPersonDao staffPersonDao;
  private LongTextService longTextService;
  private ReferralClientDeliveredServiceDao referralClientDeliveredServiceDao;
  private ContactPartyDeliveredServiceDao contactPartyDeliveredServiceDao;
  private ReferralDao referralDao;
  private ReferralClientDao referralClientDao;
  private ChildClientDao childClientDao;
  private DeliveredToIndividualService deliveredToIndividualService;

  private String countySpecificCode;


  /**
   * @param deliveredServiceDao the {@link Dao} handling
   *        {@link gov.ca.cwds.data.persistence.contact.DeliveredServiceEntity} objects
   * @param staffPersonDao the {@link Dao} handling
   *        {@link gov.ca.cwds.data.persistence.cms.StaffPerson} objects.
   * @param longTextService the {@link Dao} handling
   *        {@link gov.ca.cwds.data.persistence.cms.LongText} objects
   * @param referralClientDeliveredServiceDao the {@link Dao} handling
   *        {@link gov.ca.cwds.data.persistence.contact.ReferralClientDeliveredServiceEntity}
   *        objects
   * @param contactPartyDeliveredServiceDao the {@link Dao} handling
   *        {@link gov.ca.cwds.data.persistence.contact.ContactPartyDeliveredServiceEntity} objects
   * @param referralDao the {@link Dao} handling {@link gov.ca.cwds.data.persistence.cms.Referral}
   *        objects
   * @param referralClientDao the {@link Dao} handling
   *        {@link gov.ca.cwds.data.persistence.cms.ReferralClient} objects
   * @param childClientDao the {@link Dao} handling
   *        {@link gov.ca.cwds.data.persistence.cms.ChildClient} objects
   * @param deliveredToIndividualService the deliveredToIndividualService
   */
  @Inject
  public ContactService(DeliveredServiceDao deliveredServiceDao, StaffPersonDao staffPersonDao,
      LongTextService longTextService,
      ReferralClientDeliveredServiceDao referralClientDeliveredServiceDao,
      ContactPartyDeliveredServiceDao contactPartyDeliveredServiceDao, ReferralDao referralDao,
      ReferralClientDao referralClientDao, ChildClientDao childClientDao,
      DeliveredToIndividualService deliveredToIndividualService) {
    super();
    this.deliveredServiceDao = deliveredServiceDao;
    this.staffPersonDao = staffPersonDao;
    this.longTextService = longTextService;
    this.referralClientDeliveredServiceDao = referralClientDeliveredServiceDao;
    this.contactPartyDeliveredServiceDao = contactPartyDeliveredServiceDao;
    this.referralDao = referralDao;
    this.referralClientDao = referralClientDao;
    this.childClientDao = childClientDao;
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
            deliveredToIndividualService
                .getPeopleInIndividualDeliveredService(deliveredServiceEntity);

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
      detail = longTextService.find(detailText);
    }

    return (Optional.of(detail)).map(LongText::getTextDescription).orElse("");
  }

  @Override
  public Contact delete(String primaryKey) {
    throw new NotImplementedException("delete not implemented");
  }

  /**
   * Creates Long Text Entity and returns the identifier
   * 
   * @param countySpecificCode the county of the referral
   * @param textDescription the text
   * @return identifier of the posted LongText entity
   * @throws ServiceException
   */
  private String createLongText(String textDescription) throws ServiceException {

    gov.ca.cwds.rest.api.domain.cms.LongText longText =
        new gov.ca.cwds.rest.api.domain.cms.LongText(countySpecificCode, textDescription);
    PostedLongText postedLongText = longTextService.create(longText);

    return postedLongText.getId();

  }

  /**
   * Extract Date object from formatted String
   * 
   * @param timestamp the string to extract from
   * @return Date
   */
  public static Date extractDateTime(String timestamp) {
    String trimTimestamp = StringUtils.trim(timestamp);
    if (StringUtils.isNotEmpty(trimTimestamp)) {
      try {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        return df.parse(trimTimestamp);
      } catch (Exception e) {
        throw new ApiException(e);
      }
    }
    return null;
  }

  /**
   * Create a Contact ... this includes the following,
   * 
   * <pre>
   * creates two records in LongText creates a record in DeliveredService
   * creates a record in ReferralClientDeliveredService for each Child Client associated with the Referral
   * create a record in IndividualDeliveredService for each Person in persons 
   * create a record in ContactPartyDeliveredService
   * </pre>
   * 
   */
  @Override
  public Response create(ContactReferralRequest request) {
    Referral referral = validateReferral(request);
    ContactRequest contactRequest = request.getContactRequest();

    String referralId = referral.getId();
    countySpecificCode = referral.getCountySpecificCode();

    Date endedAt = extractDateTime(contactRequest.getEndedAt());
    Date startedAt = extractDateTime(contactRequest.getStartedAt());
    Integer serviceContactType = contactRequest.getPurpose();
    Set<PostedIndividualDeliveredService> people = contactRequest.getPeople();

    String deliveredServiceId =
        createDeliveredService(contactRequest, DomainChef.cookDate(endedAt),
            DomainChef.cookTime(endedAt), serviceContactType, DomainChef.cookDate(startedAt),
            DomainChef.cookTime(startedAt));

    addOnBehalfOfClientsToReferralClientDeliveredService(deliveredServiceId, referralId);

    deliveredToIndividualService.addPeopleToIndividualDeliveredService(people, deliveredServiceId,
        countySpecificCode, endedAt, serviceContactType.shortValue(), startedAt, lastUpdatedId,
        lastUpdatedTime);

    contactPartyDeliveredServiceDao.create(new ContactPartyDeliveredServiceEntity(CmsKeyIdGenerator
        .generate(lastUpdatedId), serviceContactType.shortValue(), countySpecificCode,
        deliveredServiceId, lastUpdatedId, lastUpdatedTime));

    return this.find(referralId + ":" + deliveredServiceId);
  }

  /**
   * Validates that there exists a Referral for the given identifier and returns the Referral
   * 
   * @param request the ContactReferralRequest
   * @return the Referral
   */
  private Referral validateReferral(ContactReferralRequest request) {
    String referralId = request.getReferralId();
    Referral referral = referralDao.find(referralId);
    if (referral == null) {
      throw new ServiceException("ReferralId is not Valid");
    }
    return referral;
  }

  /**
   * Adds a record to ReferralClientDeliveredService for each child client associated with the
   * Referral
   * 
   * @param deliveredServiceId the identifier for the DeliveredService
   * @param referralId the identifier for the Referral
   * @param countySpecificCode the county of the referral
   */
  private void addOnBehalfOfClientsToReferralClientDeliveredService(String deliveredServiceId,
      String referralId) {
    ReferralClient[] referralClients = referralClientDao.findByReferralId(referralId);
    boolean atLeastOneOnBehalfOfExists = false;
    for (ReferralClient referralClient : referralClients) {
      String id = referralClient.getClientId();
      ChildClient childClient = childClientDao.find(id);
      if (childClient != null) {
        atLeastOneOnBehalfOfExists = true;
        ReferralClientDeliveredServiceEntity rcdse =
            new ReferralClientDeliveredServiceEntity(deliveredServiceId, referralId, id,
                countySpecificCode, lastUpdatedId, lastUpdatedTime);
        referralClientDeliveredServiceDao.create(rcdse);
      }
    }
    if (!atLeastOneOnBehalfOfExists) {
      throw new ServiceException(
          "An  On Behalf Of Client for the referral could not be found. At least one On Behalf Of Client should exist");
    }
  }

  /**
   * Creates a record in DeliveredService and returns the unique identifier
   * 
   * @param contactRequest the request
   * @param endDate the end date
   * @param endTime the end time
   * @param serviceContactType the service contact type
   * @param startDate the start date
   * @param startTime the start time
   * @return the identifier of the DeliveredService created
   */
  private String createDeliveredService(ContactRequest contactRequest, String endDate,
      String endTime, Integer serviceContactType, String startDate, String startTime) {

    String note = contactRequest.getNote();
    String noteContinuation = "";
    if (note.length() > 4000) {
      noteContinuation = note.substring(4000);
      note = note.substring(0, 3999);
    }
    String longTextId = createLongText(note);
    String longTextContinuationId = createLongText(noteContinuation);

    DeliveredServiceDomain deliveredServiceDomain =
        DeliveredServiceDomain.createWithDefaults(contactRequest.getCommunicationMethod(),
            contactRequest.getLocation(), countySpecificCode, longTextId, longTextContinuationId,
            endDate, endTime, serviceContactType, startDate, startTime, contactRequest.getStatus());

    try {
      gov.ca.cwds.data.persistence.contact.DeliveredServiceEntity persistedDeliveredService =
          new gov.ca.cwds.data.persistence.contact.DeliveredServiceEntity(
              CmsKeyIdGenerator.generate(lastUpdatedId), deliveredServiceDomain, lastUpdatedId,
              lastUpdatedTime);
      persistedDeliveredService = deliveredServiceDao.create(persistedDeliveredService);
      return new gov.ca.cwds.rest.api.contact.DeliveredServiceDomain(persistedDeliveredService)
          .getId();
    } catch (EntityExistsException e) {
      LOGGER.info("deliveredServiceEntity already exists : {}", deliveredServiceDomain);
      throw new ServiceException(e);
    }

  }

  @Override
  public Response update(String primaryKey, ContactReferralRequest request) {
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
