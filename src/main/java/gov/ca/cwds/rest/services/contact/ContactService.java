package gov.ca.cwds.rest.services.contact;

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
import gov.ca.cwds.data.persistence.cms.Attorney;
import gov.ca.cwds.data.persistence.cms.Client;
import gov.ca.cwds.data.persistence.cms.CollateralIndividual;
import gov.ca.cwds.data.persistence.cms.LongText;
import gov.ca.cwds.data.persistence.cms.Reporter;
import gov.ca.cwds.data.persistence.cms.ServiceProvider;
import gov.ca.cwds.data.persistence.cms.StaffPerson;
import gov.ca.cwds.data.persistence.cms.SubstituteCareProvider;
import gov.ca.cwds.data.persistence.contact.DeliveredServiceEntity;
import gov.ca.cwds.data.persistence.contact.IndividualDeliveredServiceEntity;
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
   * @param deliveredServiceDao the deliveredServiceDao
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
      ClientDao clientDao, AttorneyDao attorneyDao,
      CollateralIndividualDao collateralIndividualDao, ServiceProviderDao serviceProviderDao,
      SubstituteCareProviderDao substituteCareProviderDao, ReporterDao reporterDao) {
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

      Set<Contact> contacts = new HashSet<>();
      contacts.add(contact);
      return new ContactList(contacts);

    } else {
      String[] ids = primaryKey.split(":");
      String referralId = ids[0];
      String contactId = ids[1];
      DeliveredServiceEntity deliveredServiceEntity = deliveredServiceDao.find(contactId);
      if (deliveredServiceEntity == null) {
        return null;
      } else {
        LastUpdatedBy lastUpdatedBy = getTheLastUpdatedByStaffPerson(deliveredServiceEntity);

        String note = combineDetailTextAndContinuation(deliveredServiceEntity);

        Set<PostedIndividualDeliveredService> peopleInIndividualDeliveredService =
            getPeopleInIndividulDeliveredService(deliveredServiceEntity);

        return new Contact(deliveredServiceEntity, lastUpdatedBy, note,
            peopleInIndividualDeliveredService);

      }
    }
  }

  @Override
  public Contact delete(String primaryKey) {
    throw new NotImplementedException("delete not implement");
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
    return (Optional.of(detail)).map(LongText::getTextDescrption).orElse("");
  }

  private Set<PostedIndividualDeliveredService> getPeopleInIndividulDeliveredService(
      DeliveredServiceEntity deliveredServiceEntity) {
    Set<PostedIndividualDeliveredService> peopleInIndividualDeliveredService = new HashSet<>();

    IndividualDeliveredServiceEntity[] individualDeliveredServices =
        individualDeliveredServiceDao.findByDeliveredServiceId(deliveredServiceEntity.getId());
    for (IndividualDeliveredServiceEntity individualDeliveredService : individualDeliveredServices) {

      String code =
          individualDeliveredService.getIndividualDeliveredServiceEmbeddable()
              .getDeliveredToIndividualCode();
      DeliveredToIndividualCode deliveredToIndividualCode =
          DeliveredToIndividualCode.lookupByCode(code);
      PostedIndividualDeliveredService person = new PostedIndividualDeliveredService();
      String id =
          individualDeliveredService.getIndividualDeliveredServiceEmbeddable()
              .getDeliveredToIndividualId();
      switch (deliveredToIndividualCode) {
        case CLIENT:
          person = processClient(deliveredToIndividualCode, id);
          break;
        case SERVICE_PROVIDER:
          person = processServiceProvider(deliveredToIndividualCode, id);
          break;
        case COLLATERAL_INDIVIDUAL:
          person = processCollateralIndividual(deliveredToIndividualCode, id);
          break;
        case REPORTER:
          person = processReporter(deliveredToIndividualCode, id);
          break;
        case ATTORNEY:
          person = processAttorney(deliveredToIndividualCode, id);
          break;
        case SUBSTITUTE_CARE_PROVIDER:
          person = processSubstituteCareProvider(deliveredToIndividualCode, id);
          break;

      }
      peopleInIndividualDeliveredService.add(person);

    }
    return peopleInIndividualDeliveredService;
  }

  private PostedIndividualDeliveredService processSubstituteCareProvider(
      DeliveredToIndividualCode deliveredToIndividualCode, String id) {
    SubstituteCareProvider substituteCareProvider = substituteCareProviderDao.find(id);
    if (substituteCareProvider != null)
      return new PostedIndividualDeliveredService(deliveredToIndividualCode.getValue(), id,
          substituteCareProvider.getFirstName(), substituteCareProvider.getMiddleName(),
          substituteCareProvider.getLastName(), substituteCareProvider.getNameSuffix(),
          substituteCareProvider.getNamePrefixDescription(),
          deliveredToIndividualCode.getDescription());
    return defaultPostedIndividualDeliveredService(deliveredToIndividualCode, id);

  }

  private PostedIndividualDeliveredService processAttorney(
      DeliveredToIndividualCode deliveredToIndividualCode, String id) {
    Attorney attorney = attorneyDao.find(id);
    if (attorney != null)
      return new PostedIndividualDeliveredService(deliveredToIndividualCode.getValue(), id,
          attorney.getFirstName(), attorney.getMiddleName(), attorney.getLastName(),
          attorney.getNameSuffix(), attorney.getNamePrefixDescription(),
          deliveredToIndividualCode.getDescription());
    return defaultPostedIndividualDeliveredService(deliveredToIndividualCode, id);

  }

  private PostedIndividualDeliveredService processReporter(
      DeliveredToIndividualCode deliveredToIndividualCode, String id) {
    Reporter reporter = reporterDao.find(id);
    if (reporter != null)
      return new PostedIndividualDeliveredService(deliveredToIndividualCode.getValue(), id,
          reporter.getFirstName(), reporter.getMiddleName(), reporter.getLastName(),
          reporter.getNameSuffix(), reporter.getNamePrefixDescription(),
          deliveredToIndividualCode.getDescription());
    return defaultPostedIndividualDeliveredService(deliveredToIndividualCode, id);
  }

  private PostedIndividualDeliveredService processCollateralIndividual(
      DeliveredToIndividualCode deliveredToIndividualCode, String id) {
    CollateralIndividual collateralIndividual = collateralIndividualDao.find(id);
    if (collateralIndividual != null)
      return new PostedIndividualDeliveredService(deliveredToIndividualCode.getValue(), id,
          collateralIndividual.getFirstName(), collateralIndividual.getMiddleName(),
          collateralIndividual.getLastName(), collateralIndividual.getNameSuffix(),
          collateralIndividual.getNamePrefixDescription(),
          deliveredToIndividualCode.getDescription());
    return defaultPostedIndividualDeliveredService(deliveredToIndividualCode, id);
  }

  private PostedIndividualDeliveredService processServiceProvider(
      DeliveredToIndividualCode deliveredToIndividualCode, String id) {
    ServiceProvider serviceProvider = serviceProviderDao.find(id);
    if (serviceProvider != null)
      return new PostedIndividualDeliveredService(deliveredToIndividualCode.getValue(), id,
          serviceProvider.getFirstName(), serviceProvider.getMiddleName(),
          serviceProvider.getLastName(), serviceProvider.getNameSuffix(),
          serviceProvider.getNamePrefixDescription(), deliveredToIndividualCode.getDescription());
    return defaultPostedIndividualDeliveredService(deliveredToIndividualCode, id);
  }

  private PostedIndividualDeliveredService processClient(
      DeliveredToIndividualCode deliveredToIndividualCode, String id) {
    Client client = clientDao.find(id);
    if (client != null)
      return new PostedIndividualDeliveredService(deliveredToIndividualCode.getValue(), id,
          client.getFirstName(), client.getMiddleName(), client.getLastName(),
          client.getNameSuffix(), client.getNamePrefixDescription(),
          deliveredToIndividualCode.getDescription());
    return defaultPostedIndividualDeliveredService(deliveredToIndividualCode, id);
  }

  private PostedIndividualDeliveredService defaultPostedIndividualDeliveredService(
      DeliveredToIndividualCode deliveredToIndividualCode, String id) {
    return new PostedIndividualDeliveredService(deliveredToIndividualCode.getValue(), id, "", "",
        "", "", "", deliveredToIndividualCode.getDescription());
  }



  private Contact validContact() {
    Set<PostedIndividualDeliveredService> people = validPeople();
    LastUpdatedBy lastUpdatedByPerson =
        new LastUpdatedBy("0X5", "Joe", "M", "Friday", "Mr.", "Jr.");
    return new Contact("1234567ABC", lastUpdatedByPerson, "2010-04-27T23:30:14.000Z", "", 433, 408,
        "C", services, 415,
        "some text describing the contact of up to 8000 characters can be stored in CMS", people);
  }

  private Set<PostedIndividualDeliveredService> validPeople() {
    Set<PostedIndividualDeliveredService> peopleInIndividualDeliveredService = new HashSet<>();
    peopleInIndividualDeliveredService.add(new PostedIndividualDeliveredService("CLIENT_T",
        "3456789ABC", "John", "Bob", "Smith", "Mr.", "Jr.", ""));
    peopleInIndividualDeliveredService.add(new PostedIndividualDeliveredService("REPTR_T",
        "4567890ABC ", "Sam", "Bill", "Jones", "Mr.", "III", "Reporter"));
    return peopleInIndividualDeliveredService;
  }

}
