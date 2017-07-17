package gov.ca.cwds.rest.api.domain.cms;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gov.ca.cwds.rest.core.Api;
import gov.ca.cwds.rest.resources.cms.ClientRelationshipResource;
import gov.ca.cwds.rest.resources.cms.JerseyGuiceRule;
import io.dropwizard.testing.junit.ResourceTestRule;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import com.squarespace.jersey2.guice.JerseyGuiceUtils;

public class ClientRelationshipTest {
  private static final String ROOT_RESOURCE = "/" + Api.RESOURCE_RELATIONSHIPS + "/";

  private static final ClientRelationshipResource mockedResource =
      mock(ClientRelationshipResource.class);

  @SuppressWarnings("javadoc")
  @After
  public void ensureServiceLocatorPopulated() {
    JerseyGuiceUtils.reset();
  }

  @SuppressWarnings("javadoc")
  @ClassRule
  public static JerseyGuiceRule rule = new JerseyGuiceRule();

  @ClassRule
  public static final ResourceTestRule resources = ResourceTestRule.builder()
      .addResource(mockedResource).build();
  private ClientRelationship validClientRelationship = validClientRelationship();

  private final static DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
  private String id = "CLNTRELN";
  private Short clientRelationshipType = 172;
  private String absentParentCode = "N";
  private String endDate = "2017-01-07";
  private String startDate = "2017-01-07";
  private String secondaryClientId = "SECCLIENT";
  private String primaryClientId = "PRICLIENT";
  private String sameHomeCode = "Y";

  @Before
  public void setup() {
    when(mockedResource.create(eq(validClientRelationship))).thenReturn(
        Response.status(Response.Status.NO_CONTENT).entity(null).build());
  }

  @Test
  public void persistentObjectConstructorTest() throws Exception {

    ClientRelationship domain =
        new ClientRelationship(absentParentCode, clientRelationshipType, endDate,
            secondaryClientId, primaryClientId, sameHomeCode, startDate);

    gov.ca.cwds.data.persistence.cms.ClientRelationship pt =
        new gov.ca.cwds.data.persistence.cms.ClientRelationship(id, domain, "lastUpdatedId");

    assertThat(domain.getAbsentParentCode(), is(equalTo(pt.getAbsentParentCode())));
    assertThat(domain.getClientRelationshipType(), is(equalTo(pt.getClientRelationshipType())));
    assertThat(domain.getSecondaryClientId(), is(equalTo(pt.getSecondaryClientId())));
    assertThat(domain.getPrimaryClientId(), is(equalTo(pt.getPrimaryClientId())));
    assertThat(domain.getEndDate(), is(equalTo(df.format(pt.getEndDate()))));
    assertThat(domain.getSameHomeCode(), is(equalTo(pt.getSameHomeCode())));
    assertThat(domain.getStartDate(), is(equalTo(df.format(pt.getStartDate()))));
  }

  @Test
  public void jsonCreatorConstructorTest() throws Exception {

    ClientRelationship domain =
        new ClientRelationship(absentParentCode, clientRelationshipType, endDate,
            secondaryClientId, primaryClientId, sameHomeCode, startDate);
    assertThat(domain.getAbsentParentCode(), is(equalTo(absentParentCode)));
    assertThat(domain.getClientRelationshipType(), is(equalTo(clientRelationshipType)));
    assertThat(domain.getSecondaryClientId(), is(equalTo(secondaryClientId)));
    assertThat(domain.getPrimaryClientId(), is(equalTo(primaryClientId)));
    assertThat(domain.getEndDate(), is(equalTo(endDate)));
    assertThat(domain.getSameHomeCode(), is(equalTo(sameHomeCode)));
    assertThat(domain.getStartDate(), is(equalTo(startDate)));
  }

  @Test
  public void equalsHashCodeWork() {
    EqualsVerifier.forClass(ClientRelationship.class).suppress(Warning.NONFINAL_FIELDS).verify();
  }

  /*
   * Successful Tests
   */
  @Test
  public void successfulWithValid() throws Exception {
    ClientRelationship toCreate =
        new ClientRelationship(absentParentCode, clientRelationshipType, endDate,
            secondaryClientId, primaryClientId, sameHomeCode, startDate);
    assertThat(
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON)).getStatus(),
        is(equalTo(204)));
  }

  @Test
  public void successfulWithOptionalsNotIncluded() throws Exception {
    ClientRelationship toCreate =
        new ClientRelationship(absentParentCode, clientRelationshipType, null, secondaryClientId,
            primaryClientId, sameHomeCode, null);
    assertThat(
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON)).getStatus(),
        is(equalTo(204)));
  }

  /*
   * absentParentCode Tests
   */
  @Test
  public void failsWhenAbsentParentCodeNull() throws Exception {
    ClientRelationship toCreate =
        new ClientRelationship(null, clientRelationshipType, endDate, secondaryClientId,
            primaryClientId, sameHomeCode, startDate);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("absentParentCode may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenAbsentParentCodeEmpty() throws Exception {
    ClientRelationship toCreate =
        new ClientRelationship("", clientRelationshipType, endDate, secondaryClientId,
            primaryClientId, sameHomeCode, startDate);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("absentParentCode may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenAbsentParentCodeTooLong() throws Exception {
    ClientRelationship toCreate =
        new ClientRelationship("AB", clientRelationshipType, endDate, secondaryClientId,
            primaryClientId, sameHomeCode, startDate);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("absentParentCode size must be 1"),
        is(greaterThanOrEqualTo(0)));
  }

  /*
   * clientRelationshipType Tests
   */
  @Test
  public void failsWhenClientRelationshipTypeNull() throws Exception {
    ClientRelationship toCreate =
        new ClientRelationship(absentParentCode, null, endDate, secondaryClientId, primaryClientId,
            sameHomeCode, startDate);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("clientRelationshipType may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenClientRelationshipTypeEmpty() throws Exception {
    ClientRelationship toCreate =
        new ClientRelationship(absentParentCode, null, endDate, secondaryClientId, primaryClientId,
            sameHomeCode, startDate);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("clientRelationshipType may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  /*
   * endDate Tests
   */
  @Test
  public void successWhenEndDateEmpty() throws Exception {
    ClientRelationship toCreate =
        new ClientRelationship(absentParentCode, clientRelationshipType, "", secondaryClientId,
            primaryClientId, sameHomeCode, startDate);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void successWhenEndDateNull() throws Exception {
    ClientRelationship toCreate =
        new ClientRelationship(absentParentCode, clientRelationshipType, null, secondaryClientId,
            primaryClientId, sameHomeCode, startDate);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void failsWhenEndDateWrongFormat() throws Exception {
    ClientRelationship toCreate =
        new ClientRelationship(absentParentCode, clientRelationshipType, "09-09-2012",
            secondaryClientId, primaryClientId, sameHomeCode, startDate);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("endDate must be in the format of yyyy-MM-dd"),
        is(greaterThanOrEqualTo(0)));
  }

  /*
   * secondaryClientId Tests
   */
  @Test
  public void failsWhenSecondaryClientIdNull() throws Exception {
    ClientRelationship toCreate =
        new ClientRelationship(absentParentCode, clientRelationshipType, endDate, null,
            primaryClientId, sameHomeCode, startDate);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("secondaryClientId may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenSecondaryClientIdEmpty() throws Exception {
    ClientRelationship toCreate =
        new ClientRelationship(absentParentCode, clientRelationshipType, endDate, "",
            primaryClientId, sameHomeCode, startDate);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("secondaryClientId may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenSecondaryClientIdTooLong() throws Exception {
    ClientRelationship toCreate =
        new ClientRelationship(absentParentCode, clientRelationshipType, endDate, "123456789012",
            primaryClientId, sameHomeCode, startDate);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class)
            .indexOf("secondaryClientId size must be between 1 and 10"),
        is(greaterThanOrEqualTo(0)));
  }

  /*
   * primaryClientId Tests
   */
  @Test
  public void failsWhenPrimaryClientIdNull() throws Exception {
    ClientRelationship toCreate =
        new ClientRelationship(absentParentCode, clientRelationshipType, endDate,
            secondaryClientId, null, sameHomeCode, startDate);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("primaryClientId may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenPrimaryClientIdEmpty() throws Exception {
    ClientRelationship toCreate =
        new ClientRelationship(absentParentCode, clientRelationshipType, endDate,
            secondaryClientId, "", sameHomeCode, startDate);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("primaryClientId may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenPrimaryClientIdTooLong() throws Exception {
    ClientRelationship toCreate =
        new ClientRelationship(absentParentCode, clientRelationshipType, endDate,
            secondaryClientId, "123456789012", sameHomeCode, startDate);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("primaryClientId size must be between 1 and 10"),
        is(greaterThanOrEqualTo(0)));
  }

  /*
   * sameHomeCode Tests
   */
  @Test
  public void failsWhenSameHomeCodeMissing() throws Exception {
    ClientRelationship toCreate =
        new ClientRelationship(absentParentCode, clientRelationshipType, endDate,
            secondaryClientId, primaryClientId, "", startDate);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("sameHomeCode may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenSameHomeCodeNull() throws Exception {
    ClientRelationship toCreate =
        new ClientRelationship(absentParentCode, clientRelationshipType, endDate,
            secondaryClientId, primaryClientId, null, startDate);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("sameHomeCode may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenSameHomeCodeEmpty() throws Exception {
    ClientRelationship toCreate =
        new ClientRelationship(absentParentCode, clientRelationshipType, endDate,
            secondaryClientId, primaryClientId, "", startDate);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("sameHomeCode may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenSameHomeCodeTooLong() throws Exception {
    ClientRelationship toCreate =
        new ClientRelationship(absentParentCode, clientRelationshipType, endDate,
            secondaryClientId, primaryClientId, "AB", startDate);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("sameHomeCode size must be 1"),
        is(greaterThanOrEqualTo(0)));
  }

  /*
   * startDate Tests
   */
  @Test
  public void successWhenStartDateEmpty() throws Exception {
    ClientRelationship toCreate =
        new ClientRelationship(absentParentCode, clientRelationshipType, endDate,
            secondaryClientId, primaryClientId, sameHomeCode, "");
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void successWhenStartDateNull() throws Exception {
    ClientRelationship toCreate =
        new ClientRelationship(absentParentCode, clientRelationshipType, endDate,
            secondaryClientId, primaryClientId, sameHomeCode, null);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void failsWhenStartDateWrongFormat() throws Exception {
    ClientRelationship toCreate =
        new ClientRelationship(absentParentCode, clientRelationshipType, endDate,
            secondaryClientId, primaryClientId, sameHomeCode, "12-12-2000");
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("startDate must be in the format of yyyy-MM-dd"),
        is(greaterThanOrEqualTo(0)));
  }

  /*
   * Utils
   */
  private ClientRelationship validClientRelationship() {
    return new ClientRelationship(absentParentCode, clientRelationshipType, endDate,
        secondaryClientId, primaryClientId, sameHomeCode, startDate);
  }

}
