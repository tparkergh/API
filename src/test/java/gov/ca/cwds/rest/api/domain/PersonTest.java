package gov.ca.cwds.rest.api.domain;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Response;

import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.squarespace.jersey2.guice.JerseyGuiceUtils;

import gov.ca.cwds.rest.resources.PersonResource;
import gov.ca.cwds.rest.resources.cms.JerseyGuiceRule;
import io.dropwizard.jackson.Jackson;
import io.dropwizard.testing.junit.ResourceTestRule;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

/**
 * @author CWDS API Team
 *
 */
@SuppressWarnings("javadoc")
public class PersonTest {

  private String firstName = "firstname";
  private String middleName = "middlename";
  private String lastName = "lastename";
  private String suffix = "jr";
  private String gender = "M";
  private String birthDate = "2001-09-01";
  private String ssn = "123456789";
  private Address address = new Address("", "", "123 Main", "Sacramento", "CA", 95757, 32);
  private Set<Address> addresses = new HashSet<>();
  private PhoneNumber phoneNumber = new PhoneNumber("408-641-0287", "cell");
  private Set<PhoneNumber> phoneNumbers = new HashSet<>();
  private Language language = new Language("English");
  private Set<Language> languages = new HashSet<>();
  private Race race = new Race("White", "European");
  private Set<Race> races = new HashSet<>();
  private Ethnicity ethnicity = new Ethnicity("Unknow", "South American");
  private Set<Ethnicity> ethnicities = new HashSet<>();

  private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

  private static final PersonResource mockedPersonResource = mock(PersonResource.class);

  @After
  public void ensureServiceLocatorPopulated() {
    JerseyGuiceUtils.reset();
  }

  @ClassRule
  public static JerseyGuiceRule rule = new JerseyGuiceRule();

  @ClassRule
  public static final ResourceTestRule resources =
      ResourceTestRule.builder().addResource(mockedPersonResource).build();

  @Before
  public void setup() {
    Person validPerson = this.validPerson();

    when(mockedPersonResource.create(eq(validPerson)))
        .thenReturn(Response.status(Response.Status.NO_CONTENT).entity(null).build());

  }

  /*
   * Serialization and de-serialization
   */
  @Test
  public void serializesToJSON() throws Exception {
    String expected = MAPPER.writeValueAsString(validPerson());

    String serialized = MAPPER.writeValueAsString(
        MAPPER.readValue(fixture("fixtures/domain/person/valid/valid.json"), Person.class));

    assertThat(serialized, is(expected));
  }

  @Test
  public void deserializesFromJSON() throws Exception {
    Person expected = this.validPerson();

    Person serialized =
        MAPPER.readValue(fixture("fixtures/domain/person/valid/valid.json"), Person.class);
    assertThat(serialized, is(expected));
  }

  @Test
  public void equalsHashCodeWork() {
    EqualsVerifier.forClass(Person.class).suppress(Warning.NONFINAL_FIELDS)
        .withIgnoredFields("messages").verify();
  }

  /*
   * Constructor Tests
   */
  @Test
  public void persistentObjectConstructorTest() throws Exception {

    Person domain = this.validPerson();

    gov.ca.cwds.data.persistence.ns.Person persistent =
        new gov.ca.cwds.data.persistence.ns.Person(domain, "12345", "12345");

    Person totest = new Person(persistent);
    assertThat(totest.getBirthDate(),
        is(equalTo(DomainChef.cookDate(persistent.getDateOfBirth()))));
    assertThat(totest.getFirstName(), is(equalTo(persistent.getFirstName())));
    assertThat(totest.getGender(), is(equalTo(persistent.getGender())));
    assertThat(totest.getLastName(), is(equalTo(persistent.getLastName())));

  }

  @Test
  public void jsonCreatorConstructorTest() throws Exception {

    addresses.add(address);
    phoneNumbers.add(phoneNumber);
    languages.add(language);
    races.add(race);
    ethnicities.add(ethnicity);
    Person domain = new Person(firstName, middleName, lastName, suffix, gender, birthDate, ssn,
        addresses, phoneNumbers, languages, races, ethnicities);

    assertThat(domain.getAddress(), is(equalTo(addresses)));
    assertThat(domain.getPhoneNumber(), is(equalTo(phoneNumbers)));
    assertThat(domain.getBirthDate(), is(equalTo(birthDate)));
    assertThat(domain.getFirstName(), is(equalTo(firstName)));
    assertThat(domain.getGender(), is(equalTo(gender)));
    assertThat(domain.getLastName(), is(equalTo(lastName)));
    assertThat(domain.getSsn(), is(equalTo(ssn)));

  }

  private Person validPerson() {

    try {
      Person validPerson =
          MAPPER.readValue(fixture("fixtures/domain/person/valid/valid.json"), Person.class);

      return validPerson;

    } catch (JsonParseException e) {
      e.printStackTrace();
      return null;
    } catch (JsonMappingException e) {
      e.printStackTrace();
      return null;
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }
}
