package gov.ca.cwds.rest.api.domain;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;

import java.io.IOException;
import java.util.Set;

import org.junit.After;
import org.junit.ClassRule;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableSet;
import com.squarespace.jersey2.guice.JerseyGuiceUtils;

import gov.ca.cwds.rest.resources.ScreeningResource;
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
public class ScreeningTest {

  private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

  private static final ScreeningResource mockedScreeningResource = mock(ScreeningResource.class);

  @After
  public void ensureServiceLocatorPopulated() {
    JerseyGuiceUtils.reset();
  }

  @ClassRule
  public static JerseyGuiceRule rule = new JerseyGuiceRule();

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
    EqualsVerifier.forClass(Screening.class)
            .suppress(Warning.STRICT_INHERITANCE, Warning.NONFINAL_FIELDS)
            .withIgnoredFields("messages")
            .verify();
  }

  /*
   * Constructor Tests
   * 
   * test the domain constructor using the persistent object
   */
  @Test
  public void persistentObjectConstructorTest() throws Exception {

    Screening domain = this.validScreening();
    gov.ca.cwds.data.persistence.ns.Address address = this.validAddress();

    final Long id = (long) 1234567;
    final String lastUpdateId = "234567";
    final String createId = "234567";

    Participant dp = this.validParticipant();
    gov.ca.cwds.data.persistence.ns.Participant participant =
        new gov.ca.cwds.data.persistence.ns.Participant(dp, lastUpdateId, createId);
    ImmutableSet.Builder<gov.ca.cwds.data.persistence.ns.Participant> participantSetBuilder =
        ImmutableSet.builder();
    participantSetBuilder.add(participant);
    Set<gov.ca.cwds.data.persistence.ns.Participant> participants;
    participants = participantSetBuilder.build();

    // Set<gov.ca.cwds.data.persistence.ns.Participant> participants =
    // new HashSet<gov.ca.cwds.data.persistence.ns.Participant>();
    // participants.add(participant);

    gov.ca.cwds.data.persistence.ns.Screening persistent =
        new gov.ca.cwds.data.persistence.ns.Screening(id, domain, address, participants,
            lastUpdateId, createId);

    Screening totest = new Screening(persistent);
    assertThat(totest.getReference(), is(equalTo(persistent.getReference())));
    assertThat(totest.getEndedAt(), is(equalTo(DomainChef.cookDate(persistent.getEndedAt()))));
    assertThat(totest.getIncidentCounty(), is(equalTo(persistent.getIncidentCounty())));
    assertThat(totest.getIncidentDate(),
        is(equalTo(DomainChef.cookDate(persistent.getIncidentDate()))));
    assertThat(totest.getLocationType(), is(equalTo(persistent.getLocationType())));
    assertThat(totest.getCommunicationMethod(), is(equalTo(persistent.getCommunicationMethod())));
    assertThat(totest.getName(), is(equalTo(persistent.getName())));
    assertThat(totest.getScreeningDecision(), is(equalTo(persistent.getScreeningDecision())));
    assertThat(totest.getStartedAt(), is(equalTo(DomainChef.cookDate(persistent.getStartedAt()))));
    assertThat(totest.getNarrative(), is(equalTo(persistent.getNarrative())));

  }

  @Test
  public void jsonCreatorConstructorTest() throws Exception {

    Screening sc = this.validScreening();

    Screening domain = new Screening(sc.getReference(), sc.getEndedAt(), sc.getIncidentCounty(),
        sc.getIncidentDate(), sc.getLocationType(), sc.getCommunicationMethod(), sc.getName(),
        sc.getResponseTime(), sc.getScreeningDecision(), sc.getStartedAt(), sc.getNarrative());

    assertThat(domain.getReference(), is(equalTo(sc.getReference())));
    assertThat(domain.getEndedAt(), is(equalTo(sc.getEndedAt())));
    assertThat(domain.getIncidentCounty(), is(equalTo(sc.getIncidentCounty())));
    assertThat(domain.getIncidentDate(), is(equalTo(sc.getIncidentDate())));
    assertThat(domain.getLocationType(), is(equalTo(sc.getLocationType())));
    assertThat(domain.getCommunicationMethod(), is(equalTo(sc.getCommunicationMethod())));
    assertThat(domain.getName(), is(equalTo(sc.getName())));
    assertThat(domain.getResponseTime(), is(equalTo(sc.getResponseTime())));
    assertThat(domain.getScreeningDecision(), is(equalTo(sc.getScreeningDecision())));
    assertThat(domain.getStartedAt(), is(equalTo(sc.getStartedAt())));
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
  private gov.ca.cwds.data.persistence.ns.Address validAddress() {

    try {
      gov.ca.cwds.data.persistence.ns.Address validAddress =
          MAPPER.readValue(fixture("fixtures/persistence/ns/Address/validPersistentAddress.json"),
              gov.ca.cwds.data.persistence.ns.Address.class);

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

  private Participant validParticipant() {

    try {
      Participant validParticipant = MAPPER
          .readValue(fixture("fixtures/domain/participant/valid/valid.json"), Participant.class);

      return validParticipant;

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
