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

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.squarespace.jersey2.guice.JerseyGuiceUtils;

import gov.ca.cwds.rest.core.Api;
import gov.ca.cwds.rest.resources.ScreeningToReferralResource;
import gov.ca.cwds.rest.resources.cms.JerseyGuiceRule;
import io.dropwizard.jackson.Jackson;
import io.dropwizard.testing.junit.ResourceTestRule;

@SuppressWarnings("javadoc")
public class ScreeningToReferralTest {

  private static final String ROOT_RESOURCE = "/" + Api.RESOURCE_REFERRALS + "/";;
  private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

  private static final ScreeningToReferralResource mockedScreeningToReferralResource =
      mock(ScreeningToReferralResource.class);

  private String agencyType = "Law enforcement";
  private String agencyName = "Sacramento County Sheriff Deparment";
  private String method = "electronic report";
  private String informDate = "2017-03-15";
  private Set<Participant> participants = new HashSet<Participant>();
  private Set<CrossReport> crossReports = new HashSet<CrossReport>();
  private Set<Allegation> allegations = new HashSet<Allegation>();
  private long id = 2;

  @After
  public void ensureServiceLocatorPopulated() {
    JerseyGuiceUtils.reset();
  }

  @ClassRule
  public static JerseyGuiceRule rule = new JerseyGuiceRule();

  @ClassRule
  public static final ResourceTestRule resources =
      ResourceTestRule.builder().addResource(mockedScreeningToReferralResource).build();

  @Before
  public void setup() {
    ScreeningToReferral validScreeningToReferral = validScreeningToReferral();
    when(mockedScreeningToReferralResource.create(eq(validScreeningToReferral)))
        .thenReturn(Response.status(Response.Status.NO_CONTENT).entity(null).build());
    MAPPER.configure(SerializationFeature.INDENT_OUTPUT, true);
  }

  /*
   * Serialization and deserialization
   */
  @Test
  public void serializesToJSON() throws Exception {

    Address address = validAddress();
    Participant participant = validParticipant();
    participants.add(participant);
    CrossReport crossReport = new CrossReport(agencyType, agencyName, method, informDate);
    crossReports.add(crossReport);
    Allegation allegation = validAllegation();
    allegations.add(allegation);


    String expected = MAPPER.writeValueAsString(
        new ScreeningToReferral(id, "2016-08-03T01:00:00.000Z", "sacramento", "2016-08-02",
            "Foster Home", "Phone", "The Rocky Horror Show", "Narrative 123 test", "123ABC",
            "immediate", "2016-08-03T01:00:00.000Z", "Michael Bastow", "addtional information",
            "Response time", "Detail", address, participants, crossReports, allegations));

    String serialized = MAPPER.writeValueAsString(MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validScreeningToReferral.json"),
        ScreeningToReferral.class));
    // System.out.println(serialized);
    assertThat(serialized, is(expected));
  }

  @Test
  public void deserializesFromJSON() throws Exception {
    MAPPER.configure(SerializationFeature.INDENT_OUTPUT, true);
    Address address = validAddress();
    Participant participant = validParticipant();
    participants.add(participant);
    CrossReport crossReport = new CrossReport(agencyType, agencyName, method, informDate);
    crossReports.add(crossReport);
    Allegation allegation = validAllegation();
    allegations.add(allegation);

    ScreeningToReferral expected =
        new ScreeningToReferral(id, "2016-08-03T01:00:00.000Z", "sacramento", "2016-08-02",
            "Foster Home", "Phone", "The Rocky Horror Show", "Narrative 123 test", "123ABC",
            "immediate", "2016-08-03T01:00:00.000Z", "Michael Bastow", "addtional information",
            "Response time", "Detail", address, participants, crossReports, allegations);


    ScreeningToReferral serialized = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validScreeningToReferral.json"),
        ScreeningToReferral.class);

    assertThat(serialized, is(expected));

  }

  @Test
  public void testWithValidSuccess() throws Exception {
    ScreeningToReferral toCreate = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validDomainScreeningToReferral.json"),
        ScreeningToReferral.class);
    // String str = MAPPER.writeValueAsString(MAPPER.readValue(
    // fixture("fixtures/domain/ScreeningToReferral/valid/validDomainScreeningToReferral.json"),
    // ScreeningToReferral.class));
    // System.out.println(str);

    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));

  }

  @Test
  public void testWithNullParticipantsFail() throws Exception {

    ScreeningToReferral toCreate = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/invalid/nullParticipants.json"),
        ScreeningToReferral.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
  }

  @Test
  public void testWithEmptyParticipantsFail() throws Exception {
    ScreeningToReferral toCreate = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/invalid/emptyParticipants.json"),
        ScreeningToReferral.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));

  }

  @Test
  public void testWithNullAllegationsFail() throws Exception {
    ScreeningToReferral toCreate = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/invalid/nullAllegations.json"),
        ScreeningToReferral.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));

  }

  @Test
  public void testWithEmptyAllegationsFail() throws Exception {
    ScreeningToReferral toCreate = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/invalid/emptyAllegations.json"),
        ScreeningToReferral.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));

  }

  @Test
  public void testWithEmptyCrossReportSuccess() throws Exception {
    ScreeningToReferral toCreate =
        MAPPER.readValue(fixture("fixtures/domain/ScreeningToReferral/valid/emptyCrossReport.json"),
            ScreeningToReferral.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));

  }

  @Test
  public void testWithNullCrossReportSuccess() throws Exception {
    ScreeningToReferral toCreate =
        MAPPER.readValue(fixture("fixtures/domain/ScreeningToReferral/valid/nullCrossReport.json"),
            ScreeningToReferral.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));

  }

  @Test
  public void testWithMultipleCrossReportsSuccess() throws Exception {
    ScreeningToReferral toCreate = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validMultipleCrossReports.json"),
        ScreeningToReferral.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));

  }

  @Test
  public void testWithAgencyTypeMissingFail() throws Exception {
    ScreeningToReferral toCreate = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/invalid/missingAgencyType.json"),
        ScreeningToReferral.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
  }

  private ScreeningToReferral validScreeningToReferral() {
    ScreeningToReferral str = null;
    try {
      str = MAPPER.readValue(
          fixture("fixtures/domain/ScreeningToReferral/valid/validDomainScreeningToReferral.json"),
          ScreeningToReferral.class);
    } catch (JsonParseException e) {
      e.printStackTrace();
      return null;
    } catch (JsonMappingException e) {
      e.printStackTrace();
      return null;
    } catch (IOException e) {
      e.printStackTrace();
    }
    return str;
  }

  private Address validAddress() {

    try {
      Address validAddress =
          MAPPER.readValue(fixture("fixtures/domain/address/valid/valid.json"), Address.class);

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

  private Allegation validAllegation() {

    try {
      Allegation validAllegation = MAPPER
          .readValue(fixture("fixtures/domain/Allegation/valid/valid.json"), Allegation.class);
      return validAllegation;

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
