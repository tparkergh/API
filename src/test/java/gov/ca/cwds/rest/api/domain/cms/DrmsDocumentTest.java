package gov.ca.cwds.rest.api.domain.cms;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

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
import gov.ca.cwds.rest.resources.cms.DrmsDocumentResource;
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
public class DrmsDocumentTest {

  private static final String ROOT_RESOURCE = "/" + Api.RESOURCE_DRMS_DOCUMENT + "/";

  private static final DrmsDocumentResource mockedDrmsDocumentResource =
      mock(DrmsDocumentResource.class);

  @After
  public void ensureServiceLocatorPopulated() {
    JerseyGuiceUtils.reset();
  }

  @ClassRule
  public static JerseyGuiceRule rule = new JerseyGuiceRule();

  /**
   * 
   */
  @ClassRule
  public static final ResourceTestRule resources =
      ResourceTestRule.builder().addResource(mockedDrmsDocumentResource).build();

  private static final ObjectMapper MAPPER = Jackson.newObjectMapper();
  private final static DateFormat tf = new SimpleDateFormat("yyyy-MM-dd-HH.mm.ss.SSS");
  private String time = "2002-10-25T11:21:02.000196";

  private String id = "1234567ABC";
  private String drmsDocumentTemplateId = "DUMMY";
  private String fingerprintStaffPerson = "q1p";
  private String staffPersonId = "q1p";
  private String handleName = "DUMMY";
  private Date creationTimeStamp = null;

  @Before
  public void setup() throws Exception {
    DrmsDocument validDrmsDocument = validDrmsDocument();

    when(mockedDrmsDocumentResource.create(eq(validDrmsDocument)))
        .thenReturn(Response.status(Response.Status.NO_CONTENT).entity(null).build());

    when(mockedDrmsDocumentResource.create(eq(validDrmsDocument)))
        .thenReturn(Response.status(Response.Status.NO_CONTENT).entity(null).build());
  }

  /*
   * Constructor Tests
   */
  @Test
  public void persistentObjectConstructorTest() throws Exception {
    DrmsDocument domain = new DrmsDocument(creationTimeStamp, drmsDocumentTemplateId,
        fingerprintStaffPerson, staffPersonId, handleName);

    gov.ca.cwds.data.persistence.cms.DrmsDocument persistent =
        new gov.ca.cwds.data.persistence.cms.DrmsDocument(id, domain, "lastUpdatedId");

    DrmsDocument totest = new DrmsDocument(persistent);
    assertThat(totest.getCreationTimeStamp(), is(equalTo(persistent.getCreationTimeStamp())));
    assertThat(totest.getDrmsDocumentTemplateId(),
        is(equalTo(persistent.getDrmsDocumentTemplateId())));
    assertThat(totest.getFingerprintStaffPerson(),
        is(equalTo(persistent.getFingerprintStaffPerson())));
    assertThat(totest.getStaffPersonId(), is(equalTo(persistent.getStaffPersonId())));
    assertThat(totest.getHandleName(), is(equalTo(persistent.getHandleName())));
  }

  /**
   * @throws Exception test standard test standard
   */
  @Test
  public void jsonCreatorConstructorTest() throws Exception {
    DrmsDocument domain = new DrmsDocument((Date) null, drmsDocumentTemplateId,
        fingerprintStaffPerson, staffPersonId, handleName);

    assertThat(domain.getCreationTimeStamp(), is(equalTo(creationTimeStamp)));
    assertThat(domain.getDrmsDocumentTemplateId(), is(equalTo(drmsDocumentTemplateId)));
    assertThat(domain.getFingerprintStaffPerson(), is(equalTo(fingerprintStaffPerson)));
    assertThat(domain.getStaffPersonId(), is(equalTo(staffPersonId)));
    assertThat(domain.getHandleName(), is(equalTo(handleName)));

  }

  @Test
  public void equalsHashCodeWork() {
    EqualsVerifier.forClass(DrmsDocument.class).suppress(Warning.NONFINAL_FIELDS).verify();
  }

  @Test
  public void serializesToJSON() throws Exception {
    final String expected = MAPPER.writeValueAsString(MAPPER.readValue(
        fixture("fixtures/domain/legacy/DrmsDocument/valid/valid.json"), DrmsDocument.class));

    assertThat(MAPPER.writeValueAsString(validDrmsDocument()), is(equalTo(expected)));
  }

  @Test
  public void deserializesFromJSON() throws Exception {
    assertThat(MAPPER.readValue(fixture("fixtures/domain/legacy/DrmsDocument/valid/valid.json"),
        DrmsDocument.class), is(equalTo(validDrmsDocument())));
  }

  /*
   * Successful Tests
   */
  @Test
  public void successfulWithValid() throws Exception {
    DrmsDocument toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/DrmsDocument/valid/valid.json"), DrmsDocument.class);
    assertThat(
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON)).getStatus(),
        is(equalTo(204)));
  }

  @Test
  public void successfulWithOptionalsNotIncluded() throws Exception {
    DrmsDocument toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/DrmsDocument/valid/optionalsNotIncluded.json"),
        DrmsDocument.class);
    assertThat(
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON)).getStatus(),
        is(equalTo(204)));
  }

  /*
   * creationTimeStamp test
   */
  @Test
  public void failsWhenCreationTimeStampMissing() throws Exception {
    DrmsDocument toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/DrmsDocument/invalid/creationTimeStampMissing.json"),
        DrmsDocument.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("creationTimeStamp may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenCreationTimeStampNull() throws Exception {
    DrmsDocument toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/DrmsDocument/invalid/creationTimeStampNull.json"),
        DrmsDocument.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("creationTimeStamp may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenCreationTimeStampEmpty() throws Exception {
    DrmsDocument toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/DrmsDocument/invalid/creationTimeStampEmpty.json"),
        DrmsDocument.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("creationTimeStamp may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  /*
   * drmsDocumentTemplateId test
   */
  @Test
  public void failsWhenDrmsDocumentTemplateIdMissing() throws Exception {
    DrmsDocument toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/DrmsDocument/invalid/drmsDocumentTemplateIdMissing.json"),
        DrmsDocument.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("drmsDocumentTemplateId may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenDrmsDocumentTemplateIdNull() throws Exception {
    DrmsDocument toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/DrmsDocument/invalid/drmsDocumentTemplateIdNull.json"),
        DrmsDocument.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("drmsDocumentTemplateId may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenDrmsDocumentTemplateIdEmpty() throws Exception {
    DrmsDocument toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/DrmsDocument/invalid/drmsDocumentTemplateIdEmpty.json"),
        DrmsDocument.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf(
        "drmsDocumentTemplateId size must be between 1 and 10"), is(greaterThanOrEqualTo(0)));
  }

  /*
   * handleName test
   */
  @Test
  public void failsWhenHandleNameMissing() throws Exception {
    DrmsDocument toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/DrmsDocument/invalid/handleNameMissing.json"),
        DrmsDocument.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("handleName may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenHandleNameNull() throws Exception {
    DrmsDocument toCreate =
        MAPPER.readValue(fixture("fixtures/domain/legacy/DrmsDocument/invalid/handleNameNull.json"),
            DrmsDocument.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("handleName may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenHandleNameEmpty() throws Exception {
    DrmsDocument toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/DrmsDocument/invalid/handleNameEmpty.json"),
        DrmsDocument.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("handleName size must be between 1 and 30"),
        is(greaterThanOrEqualTo(0)));
  }


  private DrmsDocument validDrmsDocument() throws Exception {

    DrmsDocument validDrmsDocument = MAPPER.readValue(
        fixture("fixtures/domain/legacy/DrmsDocument/valid/valid.json"), DrmsDocument.class);
    return validDrmsDocument;
  }

}
