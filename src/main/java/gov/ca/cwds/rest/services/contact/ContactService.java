package gov.ca.cwds.rest.services.contact;

import gov.ca.cwds.data.Dao;
import gov.ca.cwds.data.dao.contact.DeliveredServiceDao;
import gov.ca.cwds.data.persistence.contact.DeliveredServiceEntity;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.Contact;
import gov.ca.cwds.rest.api.domain.ContactList;
import gov.ca.cwds.rest.api.domain.ContactRequestList;
import gov.ca.cwds.rest.api.domain.LastUpdatedBy;
import gov.ca.cwds.rest.api.domain.PostedIndividualDeliveredService;
import gov.ca.cwds.rest.filters.RequestExecutionContext;
import gov.ca.cwds.rest.services.TypedCrudsService;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

/**
 * Business layer object to work on {@link DeliveredServiceEntity}
 * 
 * @author CWDS API Team
 */
public class ContactService implements TypedCrudsService<String, ContactRequestList, Response> {

  private static final Logger LOGGER = LoggerFactory.getLogger(ContactService.class);

  private String lastUpdatedId = RequestExecutionContext.instance().getUserId();
  private Date lastUpdatedTime = RequestExecutionContext.instance().getRequestStartTime();
  private Contact validContact = validContact();
  private Set<Integer> services = new HashSet<>();

  private DeliveredServiceDao deliveredServiceDao;

  /**
   * @param deliveredServiceDao {@link Dao} handling
   *        {@link gov.ca.cwds.data.persistence.contact.DeliveredServiceEntity} objects
   */
  @Inject
  public ContactService(DeliveredServiceDao deliveredServiceDao) {
    super();
    this.deliveredServiceDao = deliveredServiceDao;
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
        "4567890ABC ", "Sam", "Bill", "Doe", "Mr.", "III", "Reporter"));
    return peopleInIndividualDeliveredService;
  }


  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#create(gov.ca.cwds.rest.api.Request)
   */

  @Override
  public Response find(String primaryKey) {
    if (primaryKey.equals("test")) {
      Contact contact = validContact;

      Set<Contact> contacts = new HashSet<>();
      contacts.add(contact);
      return new ContactList(contacts);

    } else {
      return validContact;

    }
  }

  @Override
  public Contact delete(String primaryKey) {
    return validContact;
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

}
