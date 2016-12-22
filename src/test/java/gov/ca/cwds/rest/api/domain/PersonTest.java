package gov.ca.cwds.rest.api.domain;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;

import javax.ws.rs.core.Response;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import gov.ca.cwds.rest.resources.PersonResource;
import io.dropwizard.jackson.Jackson;
import io.dropwizard.testing.junit.ResourceTestRule;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

public class PersonTest {

  private String firstName = "firstname";
  private String lastName = "lastename";
  private String gender = "M";
  private String birthDate = "2001-09-01";
  private String ssn = "123456789";
  private Address address = new Address("123 Main", "Sacramento", "CA", 95757);

  private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

  private static final PersonResource mockedPersonResource = mock(PersonResource.class);

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
    EqualsVerifier.forClass(Person.class).suppress(Warning.NONFINAL_FIELDS).verify();
  }

  /*
   * Constructor Tests
   */
  @Test
  public void persistentObjectConstructorTest() throws Exception {

    Person domain = this.validPerson();

    gov.ca.cwds.rest.api.persistence.ns.Person persistent =
        new gov.ca.cwds.rest.api.persistence.ns.Person(domain, (long) 1234565);

    Person totest = new Person(persistent);
    assertThat(totest.getBirthDate(),
        is(equalTo(DomainObject.cookDate(persistent.getDateOfBirth()))));
    assertThat(totest.getFirstName(), is(equalTo(persistent.getFirstName())));
    assertThat(totest.getGender(), is(equalTo(persistent.getGender())));
    assertThat(totest.getLastName(), is(equalTo(persistent.getLastName())));

  }

  @Test
  public void jsonCreatorConstructorTest() throws Exception {

    Person domain = new Person(firstName, lastName, gender, birthDate, ssn, address);

    assertThat(domain.getAddress(), is(equalTo(address)));
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

