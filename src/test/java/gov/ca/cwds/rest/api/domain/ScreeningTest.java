package gov.ca.cwds.rest.api.domain;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.junit.ClassRule;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import gov.ca.cwds.rest.resources.ScreeningResource;
import io.dropwizard.jackson.Jackson;
import io.dropwizard.testing.junit.ResourceTestRule;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

public class ScreeningTest {

  private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

  private static final ScreeningResource mockedScreeningResource = mock(ScreeningResource.class);

  @ClassRule
  public static final ResourceTestRule resources =
      ResourceTestRule.builder().addResource(mockedScreeningResource).build();

  /*
   * Serialization and deserialization
   */
  @Test
  public void serializesToJSON() throws Exception {

    String expected = MAPPER.writeValueAsString(new Screening("screening reference", "2016-10-31",
        "Santa Clara", "2016-10-31", "school", "phone", "screening name", "24 hour",
        "accept_for_investigation", "2016-10-05", "test the narrative"));
    String serialized = MAPPER.writeValueAsString(
        MAPPER.readValue(fixture("fixtures/domain/screening/valid/valid.json"), Screening.class));

    assertThat(serialized, is(expected));
  }

  @Test
  public void deserializesFromJSON() throws Exception {
    Screening expected = new Screening("screening reference", "2016-10-31", "Santa Clara",
        "2016-10-31", "school", "phone", "screening name", "24 hour", "accept_for_investigation",
        "2016-10-05", "test the narrative");
    Screening serialized =
        MAPPER.readValue(fixture("fixtures/domain/screening/valid/valid.json"), Screening.class);
    assertThat(serialized, is(expected));
  }

  @Test
  public void equalsHashCodeWork() {
    EqualsVerifier.forClass(Person.class).suppress(Warning.NONFINAL_FIELDS).verify();
  }

  /*
   * Constructor Tests
   * 
   * test the domain constructor using the persistent object
   */
  @Test
  public void persistentObjectConstructorTest() throws Exception {

    Screening domain = this.validScreening();
    gov.ca.cwds.rest.api.persistence.ns.Address address = this.validAddress();

    gov.ca.cwds.rest.api.persistence.ns.Person person = this.validPerson();
    final Long id = (long) 1234567;
    final Long lastUpdateId = (long) 234567;

    Set<gov.ca.cwds.rest.api.persistence.ns.Person> persons =
        new HashSet<gov.ca.cwds.rest.api.persistence.ns.Person>();
    persons.add(person);

    gov.ca.cwds.rest.api.persistence.ns.Screening persistent =
        new gov.ca.cwds.rest.api.persistence.ns.Screening(id, domain, address, persons,
            lastUpdateId);

    Screening totest = new Screening(persistent);
    assertThat(totest.getReference(), is(equalTo(persistent.getReference())));
    assertThat(totest.getEnded_at(), is(equalTo(DomainObject.cookDate(persistent.getEndedAt()))));
    assertThat(totest.getIncident_county(), is(equalTo(persistent.getIncidentCounty())));
    assertThat(totest.getIncident_date(),
        is(equalTo(DomainObject.cookDate(persistent.getIncidentDate()))));
    assertThat(totest.getLocation_type(), is(equalTo(persistent.getLocationType())));
    assertThat(totest.getCommunication_method(), is(equalTo(persistent.getCommunicationMethod())));
    assertThat(totest.getName(), is(equalTo(persistent.getName())));
    assertThat(totest.getScreening_decision(), is(equalTo(persistent.getScreeningDecision())));
    assertThat(totest.getStarted_at(),
        is(equalTo(DomainObject.cookDate(persistent.getStartedAt()))));
    assertThat(totest.getNarrative(), is(equalTo(persistent.getNarrative())));

  }

  @Test
  public void jsonCreatorConstructorTest() throws Exception {

    Screening sc = this.validScreening();

    Screening domain = new Screening(sc.getReference(), sc.getEnded_at(), sc.getIncident_county(),
        sc.getIncident_date(), sc.getLocation_type(), sc.getCommunication_method(), sc.getName(),
        sc.getResponse_time(), sc.getScreening_decision(), sc.getStarted_at(), sc.getNarrative());

    assertThat(domain.getReference(), is(equalTo(sc.getReference())));
    assertThat(domain.getEnded_at(), is(equalTo(sc.getEnded_at())));
    assertThat(domain.getIncident_county(), is(equalTo(sc.getIncident_county())));
    assertThat(domain.getIncident_date(), is(equalTo(sc.getIncident_date())));
    assertThat(domain.getLocation_type(), is(equalTo(sc.getLocation_type())));
    assertThat(domain.getCommunication_method(), is(equalTo(sc.getCommunication_method())));
    assertThat(domain.getName(), is(equalTo(sc.getName())));
    assertThat(domain.getResponse_time(), is(equalTo(sc.getResponse_time())));
    assertThat(domain.getScreening_decision(), is(equalTo(sc.getScreening_decision())));
    assertThat(domain.getStarted_at(), is(equalTo(sc.getStarted_at())));
    assertThat(domain.getNarrative(), is(equalTo(sc.getNarrative())));
  }

  private Screening validScreening() {

    try {
      Screening validScreening =
          MAPPER.readValue(fixture("fixtures/domain/screening/valid/valid.json"), Screening.class);

      return validScreening;

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

  /**
   * 
   * persistence Address object
   * 
   * @Address
   */
  private gov.ca.cwds.rest.api.persistence.ns.Address validAddress() {

    try {
      gov.ca.cwds.rest.api.persistence.ns.Address validAddress =
          MAPPER.readValue(fixture("fixtures/persistence/ns/Address/validPersistentAddress.json"),
              gov.ca.cwds.rest.api.persistence.ns.Address.class);

      return validAddress;

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

  /**
   * persistence Person object
   * 
   * @Person
   */
  private gov.ca.cwds.rest.api.persistence.ns.Person validPerson() {

    try {
      gov.ca.cwds.rest.api.persistence.ns.Person validPerson =
          MAPPER.readValue(fixture("fixtures/persistence/ns/Person/validPersistentPerson.json"),
              gov.ca.cwds.rest.api.persistence.ns.Person.class);

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
