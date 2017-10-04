package gov.ca.cwds.rest.api.domain.investigation.contact;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import gov.ca.cwds.rest.api.domain.PostedIndividualDeliveredService;

import java.util.HashSet;
import java.util.Set;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

import org.junit.Test;

@SuppressWarnings("javadoc")
public class ContactRequestTest {

  @Test
  public void defaultConstructorTest() {
    Contact contact = new Contact();
    assertNotNull(contact);
  }

  @Test
  public void jsonCreatorConstructorTest() throws Exception {
    Set<Integer> services = new HashSet<>();
    final Set<PostedIndividualDeliveredService> people = validPeople();
    ContactRequest domain =
        new ContactRequest("2010-04-27T23:30:14.000Z", "", "433", "408", "C", services, "415",
            "some text describing the contact of up to 8000 characters can be stored in CMS",
            people);
    assertThat(domain.getStartedAt(), is(equalTo("2010-04-27T23:30:14.000Z")));
    assertThat(domain.getEndedAt(), is(equalTo("")));
    assertThat(domain.getPurpose(), is(equalTo("433")));
    assertThat(domain.getCommunicationMethod(), is(equalTo("408")));
    assertThat(domain.getStatus(), is(equalTo("C")));
    assertThat(domain.getServices(), is(equalTo(services)));
    assertThat(domain.getLocation(), is(equalTo("415")));
    assertThat(
        domain.getNote(),
        is(equalTo("some text describing the contact of up to 8000 characters can be stored in CMS")));
    assertThat(domain.getPeople(), is(equalTo(people)));
  }


  @Test
  public void equalsHashCodeWork() {
    EqualsVerifier.forClass(ContactRequest.class).suppress(Warning.NONFINAL_FIELDS).verify();
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
