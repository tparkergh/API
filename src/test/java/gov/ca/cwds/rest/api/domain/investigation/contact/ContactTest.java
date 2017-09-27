package gov.ca.cwds.rest.api.domain.investigation.contact;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import gov.ca.cwds.data.persistence.contact.DeliveredServiceEntity;
import gov.ca.cwds.fixture.contacts.DeliveredServiceEntityBuilder;
import gov.ca.cwds.rest.api.domain.LastUpdatedBy;
import gov.ca.cwds.rest.api.domain.PostedIndividualDeliveredService;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

import org.junit.Test;

@SuppressWarnings("javadoc")
public class ContactTest {

  @Test
  public void defaultConstructorTest() {
    Contact contact = new Contact();
    assertNotNull(contact);
  }

  @Test
  public void createFromDeliveredServiceConstructorTest() throws Exception {
    DeliveredServiceEntity persistedDeliveredService =
        new DeliveredServiceEntityBuilder().setStartDate(new Date(1506543120))
            .setStartTime(new Date(1506543120)).setEndDate(new Date(1506543120))
            .setEndTime(new Date(1506543120)).buildDeliveredServiceEntity();
    Set<Integer> services = new HashSet<>();
    final Set<PostedIndividualDeliveredService> people = validPeople();
    LastUpdatedBy lastUpdatedByPerson =
        new LastUpdatedBy("0X5", "Joe", "M", "Friday", "Mr.", "Jr.");
    Contact domain =
        new Contact(persistedDeliveredService, lastUpdatedByPerson,
            "some text describing the contact of up to 8000 characters can be stored in CMS",
            people);
    assertThat(domain.getId(), is(equalTo(persistedDeliveredService.getId())));
    assertThat(domain.getLastUpdatedBy(), is(equalTo(lastUpdatedByPerson)));
    assertThat(domain.getStartedAt(), is(equalTo("1970-01-18T02:29:03.120Z")));
    assertThat(domain.getEndedAt(), is(equalTo("1970-01-18T02:29:03.120Z")));
    assertThat(domain.getPurpose(),
        is(equalTo(Integer.valueOf(persistedDeliveredService.getServiceContactType()))));
    assertThat(domain.getCommunicationMethod(),
        is(Integer.valueOf(persistedDeliveredService.getCommunicationMethodType())));
    assertThat(domain.getStatus(), is(equalTo(persistedDeliveredService.getStatusCode())));
    assertThat(domain.getServices(), is(equalTo(null)));
    assertThat(domain.getLocation(),
        is(equalTo(Integer.valueOf(persistedDeliveredService.getContactLocationType()))));
    assertThat(
        domain.getNote(),
        is(equalTo("some text describing the contact of up to 8000 characters can be stored in CMS")));
    assertThat(domain.getPeople(), is(equalTo(people)));
  }

  @Test
  public void jsonCreatorConstructorTest() throws Exception {
    Set<Integer> services = new HashSet<>();
    final Set<PostedIndividualDeliveredService> people = validPeople();
    LastUpdatedBy lastUpdatedByPerson =
        new LastUpdatedBy("0X5", "Joe", "M", "Friday", "Mr.", "Jr.");
    Contact domain =
        new Contact("1234567ABC", lastUpdatedByPerson, "2010-04-27T23:30:14.000Z", "", 433, 408,
            "C", services, 415,
            "some text describing the contact of up to 8000 characters can be stored in CMS",
            people);
    assertThat(domain.getId(), is(equalTo("1234567ABC")));
    assertThat(domain.getLastUpdatedBy(), is(equalTo(lastUpdatedByPerson)));
    assertThat(domain.getStartedAt(), is(equalTo("2010-04-27T23:30:14.000Z")));
    assertThat(domain.getEndedAt(), is(equalTo("")));
    assertThat(domain.getPurpose(), is(equalTo(433)));
    assertThat(domain.getCommunicationMethod(), is(equalTo(408)));
    assertThat(domain.getStatus(), is(equalTo("C")));
    assertThat(domain.getServices(), is(equalTo(services)));
    assertThat(domain.getLocation(), is(equalTo(415)));
    assertThat(
        domain.getNote(),
        is(equalTo("some text describing the contact of up to 8000 characters can be stored in CMS")));
    assertThat(domain.getPeople(), is(equalTo(people)));
  }


  @Test
  public void equalsHashCodeWork() {
    EqualsVerifier.forClass(Contact.class).suppress(Warning.NONFINAL_FIELDS).verify();
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
