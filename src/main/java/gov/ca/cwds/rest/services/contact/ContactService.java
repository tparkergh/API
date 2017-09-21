package gov.ca.cwds.rest.services.contact;

import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.apache.commons.lang3.NotImplementedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;

import gov.ca.cwds.data.BaseDaoImpl;
import gov.ca.cwds.data.Dao;
import gov.ca.cwds.data.cms.AttorneyDao;
import gov.ca.cwds.data.cms.ClientDao;
import gov.ca.cwds.data.cms.CollateralIndividualDao;
import gov.ca.cwds.data.cms.LongTextDao;
import gov.ca.cwds.data.cms.ReporterDao;
import gov.ca.cwds.data.cms.ServiceProviderDao;
import gov.ca.cwds.data.cms.StaffPersonDao;
import gov.ca.cwds.data.cms.SubstituteCareProviderDao;
import gov.ca.cwds.data.dao.contact.DeliveredServiceDao;
import gov.ca.cwds.data.dao.contact.IndividualDeliveredServiceDao;
import gov.ca.cwds.data.persistence.cms.LongText;
import gov.ca.cwds.data.persistence.cms.StaffPerson;
import gov.ca.cwds.data.persistence.contact.DeliveredServiceEntity;
import gov.ca.cwds.data.persistence.contact.IndividualDeliveredServiceEntity;
import gov.ca.cwds.data.std.ApiPersonAware;
import gov.ca.cwds.rest.api.ApiException;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.Contact;
import gov.ca.cwds.rest.api.domain.ContactList;
import gov.ca.cwds.rest.api.domain.ContactRequestList;
import gov.ca.cwds.rest.api.domain.LastUpdatedBy;
import gov.ca.cwds.rest.api.domain.PostedIndividualDeliveredService;
import gov.ca.cwds.rest.api.domain.investigation.contact.DeliveredToIndividualCode;
import gov.ca.cwds.rest.filters.RequestExecutionContext;
import gov.ca.cwds.rest.services.TypedCrudsService;
import io.dropwizard.jackson.Jackson;

/**
 * Business layer object to work on {@link DeliveredServiceEntity}
 * 
 * @author CWDS API Team
 */
public class ContactService implements TypedCrudsService<String, ContactRequestList, Response> {

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
  private ClientDao clientDao;
  private AttorneyDao attorneyDao;
  private CollateralIndividualDao collateralIndividualDao;
  private ServiceProviderDao serviceProviderDao;
  private SubstituteCareProviderDao substituteCareProviderDao;
  private ReporterDao reporterDao;

  /**
   * @param deliveredServiceDao {@link Dao} handling
   *        {@link gov.ca.cwds.data.persistence.contact.DeliveredServiceEntity} objects
   * @param staffPersonDao the staffPersonDao
   * @param longTextDao the longTextDao
   * @param individualDeliveredServiceDao the individualDeliveredServiceDao
   * @param clientDao the clientDao
   * @param attorneyDao the attorneyDao
   * @param collateralIndividualDao the collateralIndividualDao
   * @param serviceProviderDao the serviceProviderDao
   * @param substituteCareProviderDao the substituteCareProviderDao
   * @param reporterDao the reporterDao
   */
  @Inject
  public ContactService(DeliveredServiceDao deliveredServiceDao, StaffPersonDao staffPersonDao,
      LongTextDao longTextDao, IndividualDeliveredServiceDao individualDeliveredServiceDao,
      ClientDao clientDao, AttorneyDao attorneyDao, CollateralIndividualDao collateralIndividualDao,
      ServiceProviderDao serviceProviderDao, SubstituteCareProviderDao substituteCareProviderDao,
      ReporterDao reporterDao) {
    super();
    this.deliveredServiceDao = deliveredServiceDao;
    this.staffPersonDao = staffPersonDao;
    this.longTextDao = longTextDao;
    this.individualDeliveredServiceDao = individualDeliveredServiceDao;
    this.clientDao = clientDao;
    this.attorneyDao = attorneyDao;
    this.collateralIndividualDao = collateralIndividualDao;
    this.serviceProviderDao = serviceProviderDao;
    this.substituteCareProviderDao = substituteCareProviderDao;
    this.reporterDao = reporterDao;
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
      String[] ids = primaryKey.split(":");
      String referralId = ids[0];
      String contactId = ids[1];
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

  @Override
  public Contact delete(String primaryKey) {
    throw new NotImplementedException("delete not implemented");
  }

  @Override
  public Response create(ContactRequestList request) {
    Contact contact = validContact;
    Set<Contact> contacts = new HashSet<>();
    contacts.add(contact);
    return new ContactList(contacts);
  }

  @Override
  public Response update(String primaryKey, ContactRequestList request) {
    return validContact;
  }

  private LastUpdatedBy getTheLastUpdatedByStaffPerson(DeliveredServiceEntity ds) {
    StaffPerson lastUpdtatedId = staffPersonDao.find(ds.getLastUpdatedId());
    if (lastUpdtatedId != null) {
      return new LastUpdatedBy(lastUpdtatedId);
    }
    return new LastUpdatedBy();
  }

  private String combineDetailTextAndContinuation(DeliveredServiceEntity ds) {
    String detailText = ds.getDetailText();
    String detailTextContinuation = ds.getDetailTextContinuation();
    return getLongText(detailText) + getLongText(detailTextContinuation);
  }

  private String getLongText(String detailText) {
    LongText detail = new LongText();
    if (detailText != null) {
      detail = longTextDao.find(detailText);
    }
    return (Optional.of(detail)).map(LongText::getTextDescription).orElse("");
  }

  private Set<PostedIndividualDeliveredService> getPeopleInIndividualDeliveredService(
      DeliveredServiceEntity deliveredServiceEntity) {
    final Set<PostedIndividualDeliveredService> people = new HashSet<>();
    final IndividualDeliveredServiceEntity[] individualDeliveredServices =
        individualDeliveredServiceDao.findByDeliveredServiceId(deliveredServiceEntity.getId());
    for (IndividualDeliveredServiceEntity individualDeliveredService : individualDeliveredServices) {
      final DeliveredToIndividualCode deliveredToIndividualCode =
          DeliveredToIndividualCode.lookupByCode(individualDeliveredService
              .getIndividualDeliveredServiceEmbeddable().getDeliveredToIndividualCode());
      final String id = individualDeliveredService.getIndividualDeliveredServiceEmbeddable()
          .getDeliveredToIndividualId();
      people.add(findPerson(deliveredToIndividualCode, id));
    }
    return people;
  }

  /**
   * @param code type of contact
   * @param id key
   * @return contact person
   */
  protected PostedIndividualDeliveredService findPerson(final DeliveredToIndividualCode code,
      final String id) {
    PostedIndividualDeliveredService ret;
    switch (code) {
      case CLIENT:
        ret = findPerson(clientDao, code, id);
        break;
      case SERVICE_PROVIDER:
        ret = findPerson(serviceProviderDao, code, id);
        break;
      case COLLATERAL_INDIVIDUAL:
        ret = findPerson(collateralIndividualDao, code, id);
        break;
      case REPORTER:
        ret = findPerson(reporterDao, code, id);
        break;
      case ATTORNEY:
        ret = findPerson(attorneyDao, code, id);
        break;
      case SUBSTITUTE_CARE_PROVIDER:
        ret = findPerson(substituteCareProviderDao, code, id);
        break;
      default:
        throw new ApiException("UNKNOWN DELIVERED TO INDIVIDUAL CODE: " + code);
    }
    return ret;
  }

  /**
   * Generic, reusable approach to fetch contact persons.
   * 
   * @param dao person aware DAO
   * @param code referenced table
   * @param id referenced id
   * @return contact
   */
  protected PostedIndividualDeliveredService findPerson(
      final BaseDaoImpl<? extends ApiPersonAware> dao, final DeliveredToIndividualCode code,
      final String id) {
    final ApiPersonAware person = dao.find(id);
    return (person != null)
        ? new PostedIndividualDeliveredService(code.getValue(), id, person.getFirstName(),
            person.getMiddleName(), person.getLastName(), person.getNameSuffix(),
            person.getNameSuffix(), code.getDescription())
        : defaultPostedIndividualDeliveredService(code, id);
  }

  private PostedIndividualDeliveredService defaultPostedIndividualDeliveredService(
      DeliveredToIndividualCode deliveredToIndividualCode, String id) {
    return new PostedIndividualDeliveredService(deliveredToIndividualCode.getValue(), id, "", "",
        "", "", "", deliveredToIndividualCode.getDescription());
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
