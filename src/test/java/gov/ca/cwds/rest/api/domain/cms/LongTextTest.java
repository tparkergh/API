package gov.ca.cwds.rest.api.domain.cms;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.squarespace.jersey2.guice.JerseyGuiceUtils;

import gov.ca.cwds.rest.core.Api;
import gov.ca.cwds.rest.resources.cms.JerseyGuiceRule;
import gov.ca.cwds.rest.resources.cms.LongTextResource;
import io.dropwizard.jackson.Jackson;
import io.dropwizard.testing.junit.ResourceTestRule;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

/**
 * @author CWDS API Team
 *
 */
public class LongTextTest {

  private static final String ROOT_RESOURCE = "/" + Api.RESOURCE_LONG_TEXT + "/";
  private static final LongTextResource mockedLongTextResource = mock(LongTextResource.class);

  @SuppressWarnings("javadoc")
  @After
  public void ensureServiceLocatorPopulated() {
    JerseyGuiceUtils.reset();
  }

  @SuppressWarnings("javadoc")
  @ClassRule
  public static JerseyGuiceRule rule = new JerseyGuiceRule();

  /**
   * 
   */
  @ClassRule
  public static final ResourceTestRule resources =
      ResourceTestRule.builder().addResource(mockedLongTextResource).build();

  private static final ObjectMapper MAPPER = Jackson.newObjectMapper();
  private LongText validLongText = validLongText();

  private String id = "ABC1234567";
  private String countySpecificCode = "57";
  private String textDescription = "Child Education level is below average";

  /**
   * 
   */
  @Before
  public void setup() {
    when(mockedLongTextResource.create(eq(validLongText)))
        .thenReturn(Response.status(Response.Status.NO_CONTENT).entity(null).build());
  }

  /*
   * Constructor Tests
   */
  /**
   * @throws Exception test standard test standard
   */
  @Test
  public void persistentObjectConstructorTest() throws Exception {

    LongText domain = new LongText(countySpecificCode, textDescription);

    gov.ca.cwds.data.persistence.cms.LongText persistent =
        new gov.ca.cwds.data.persistence.cms.LongText(id, domain, "lastUpdatedId");

    LongText totest = new LongText(persistent);
    assertThat(totest.getCountySpecificCode(), is(equalTo(persistent.getCountySpecificCode())));
    assertThat(totest.getTextDescription(), is(equalTo(persistent.getTextDescription())));
  }

  /**
   * @throws Exception test standard test standard
   */
  @Test
  public void jsonCreatorConstructorTest() throws Exception {
    LongText domain = new LongText(countySpecificCode, textDescription);

    assertThat(domain.getCountySpecificCode(), is(equalTo(countySpecificCode)));
    assertThat(domain.getTextDescription(), is(equalTo(textDescription)));
  }

  /**
   * 
   */
  @Test
  public void equalsHashCodeWork() {
    EqualsVerifier.forClass(LongText.class).suppress(Warning.NONFINAL_FIELDS).verify();
  }

  /**
   * @throws Exception test standard
   * 
   */
  @Test
  public void serializesToJSON() throws Exception {
    final String expected = MAPPER.writeValueAsString(MAPPER
        .readValue(fixture("fixtures/domain/legacy/LongText/valid/valid.json"), LongText.class));

    assertThat(MAPPER.writeValueAsString(validLongText), is(equalTo(expected)));
  }

  /**
   * @throws Exception test standard
   * 
   */
  @Test
  public void deserializesFromJSON() throws Exception {
    assertThat(MAPPER.readValue(fixture("fixtures/domain/legacy/LongText/valid/valid.json"),
        LongText.class), is(equalTo(validLongText)));
  }

  /*
   * Successful Tests
   */
  /**
   * @throws Exception test standard
   * 
   */
  @Test
  public void successfulWithValid() throws Exception {
    LongText toCreate = MAPPER
        .readValue(fixture("fixtures/domain/legacy/LongText/valid/valid.json"), LongText.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  /*
   * countySpecificCode Tests
   */
  /**
   * @throws Exception test standard
   * 
   */
  @Test
  public void failsWhenCountySpecificCodeMissing() throws Exception {
    LongText toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/LongText/invalid/countySpecificCodeMissing.json"),
        LongText.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("countySpecificCode may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  /**
   * @throws Exception test standard
   * 
   */
  @Test
  public void failsWhenCountySpecificCodeNull() throws Exception {
    LongText toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/LongText/invalid/countySpecificCodeNull.json"),
        LongText.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("countySpecificCode may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  /**
   * @throws Exception test standard
   * 
   */
  @Test
  public void failsWhenCountySpecificCodeEmpty() throws Exception {
    LongText toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/LongText/invalid/countySpecificCodeEmpty.json"),
        LongText.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("countySpecificCode may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  /**
   * @throws Exception test standard
   * 
   */
  @Test
  public void failsWhenCountySpecificCodeTooLong() throws Exception {
    LongText toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/LongText/invalid/countySpecificCodeTooLong.json"),
        LongText.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class)
        .indexOf("countySpecificCode size must be between 1 and 2"), is(greaterThanOrEqualTo(0)));
  }

  /*
   * textDescription Tests
   */
  /**
   * @throws Exception test standard
   * 
   */
  @Test
  public void failsWhenTextDescriptionMissing() throws Exception {
    LongText toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/LongText/invalid/textDescriptionMissing.json"),
        LongText.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("textDescription may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  /**
   * @throws Exception test standard
   * 
   */
  @Test
  public void failsWhenTextDescriptionNull() throws Exception {
    LongText toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/LongText/invalid/textDescriptionNull.json"),
        LongText.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("textDescription may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  /**
   * @throws Exception test standard
   * 
   */
  @Test
  public void failsWhenTextDescriptionEmpty() throws Exception {
    LongText toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/LongText/invalid/textDescriptionEmpty.json"),
        LongText.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("textDescription may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  /*
   * Utilis
   */
  private LongText validLongText() {
    return new LongText("57", "Child Education level is below average");
  }

}
