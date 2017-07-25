package gov.ca.cwds.rest.api.domain.cms;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gov.ca.cwds.rest.core.Api;
import gov.ca.cwds.rest.resources.cms.ClientCollateralResource;
import gov.ca.cwds.rest.resources.cms.JerseyGuiceRule;
import io.dropwizard.testing.junit.ResourceTestRule;

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

public class ClientCollateralTest {
  private static final String ROOT_RESOURCE = "/" + Api.RESOURCE_CLIENT_COLLATERALS + "/";

  private static final ClientCollateralResource mockedResource =
      mock(ClientCollateralResource.class);


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
  private ClientCollateral validClientCollateral = validClientCollateral();


  private String thirdId = "CLNTCOLL";
  private Short collateralClientReporterRelationshipType = 511;
  private String activeIndicator = "Y";
  private String commentDescription = "comment";
  private String clientId = "CLIENTID";
  private String collateralIndividualId = "COLLCLIENT";

  @Before
  public void setup() {
    when(mockedResource.create(eq(validClientCollateral))).thenReturn(
        Response.status(Response.Status.NO_CONTENT).entity(null).build());
  }

  @Test
  public void persistentObjectConstructorTest() throws Exception {

    ClientCollateral domain =
        new ClientCollateral(activeIndicator, collateralClientReporterRelationshipType,
            commentDescription, clientId, collateralIndividualId);

    gov.ca.cwds.data.persistence.cms.ClientCollateral pt =
        new gov.ca.cwds.data.persistence.cms.ClientCollateral(thirdId, domain, "lastUpdatedId");

    assertThat(domain.getActiveIndicator(), is(equalTo(pt.getActiveIndicator())));
    assertThat(domain.getCollateralClientReporterRelationshipType(),
        is(equalTo(pt.getCollateralClientReporterRelationshipType())));
    assertThat(domain.getCommentDescription(), is(equalTo(pt.getCommentDescription())));
    assertThat(domain.getClientId(), is(equalTo(pt.getClientId())));
    assertThat(domain.getCollateralIndividualId(), is(equalTo(pt.getCollateralIndividualId())));
  }

  @Test
  public void jsonCreatorConstructorTest() throws Exception {

    ClientCollateral domain =
        new ClientCollateral(activeIndicator, collateralClientReporterRelationshipType,
            commentDescription, clientId, collateralIndividualId);
    assertThat(domain.getActiveIndicator(), is(equalTo(activeIndicator)));
    assertThat(domain.getCollateralClientReporterRelationshipType(),
        is(equalTo(collateralClientReporterRelationshipType)));
    assertThat(domain.getCommentDescription(), is(commentDescription));
    assertThat(domain.getClientId(), is(equalTo(clientId)));
    assertThat(domain.getCollateralIndividualId(), is(equalTo(collateralIndividualId)));

  }

  @Test
  public void equalsHashCodeWork() {
    EqualsVerifier.forClass(ClientCollateral.class).suppress(Warning.NONFINAL_FIELDS)
        .withRedefinedSubclass(PostedClientCollateral.class).verify();
  }

  /*
   * Successful Tests
   */
  @Test
  public void successfulWithValid() throws Exception {
    ClientCollateral toCreate =
        new ClientCollateral(activeIndicator, collateralClientReporterRelationshipType,
            commentDescription, clientId, collateralIndividualId);
    assertThat(
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON)).getStatus(),
        is(equalTo(204)));
  }

  @Test
  public void successfulWithOptionalsNotIncluded() throws Exception {
    ClientCollateral toCreate =
        new ClientCollateral(activeIndicator, collateralClientReporterRelationshipType, null,
            clientId, collateralIndividualId);
    assertThat(
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON)).getStatus(),
        is(equalTo(204)));
  }

  /*
   * activeIndicator Tests
   */
  @Test
  public void failsWhenActiveIndicatorNull() throws Exception {
    ClientCollateral toCreate =
        new ClientCollateral(null, collateralClientReporterRelationshipType, commentDescription,
            clientId, collateralIndividualId);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("activeIndicator may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenActiveIndicatorEmpty() throws Exception {
    ClientCollateral toCreate =
        new ClientCollateral("", collateralClientReporterRelationshipType, commentDescription,
            clientId, collateralIndividualId);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("activeIndicator may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenActiveIndicatorTooLong() throws Exception {
    ClientCollateral toCreate =
        new ClientCollateral("AB", collateralClientReporterRelationshipType, commentDescription,
            clientId, collateralIndividualId);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("activeIndicator size must be 1"),
        is(greaterThanOrEqualTo(0)));
  }

  /*
   * collateralClientReporterRelationshipType Tests
   */
  @Test
  public void failsWhenCollateralClientReporterRelationshipTypeNull() throws Exception {
    ClientCollateral toCreate =
        new ClientCollateral(activeIndicator, null, commentDescription, clientId,
            collateralIndividualId);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf(
            "collateralClientReporterRelationshipType may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenCollateralClientReporterRelationshipTypeEmpty() throws Exception {
    ClientCollateral toCreate =
        new ClientCollateral(activeIndicator, null, commentDescription, clientId,
            collateralIndividualId);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf(
            "collateralClientReporterRelationshipType may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  /*
   * commentDescription Tests
   */
  @Test
  public void successWhenCommentDescriptionNull() throws Exception {
    ClientCollateral toCreate =
        new ClientCollateral(activeIndicator, collateralClientReporterRelationshipType, null,
            clientId, collateralIndividualId);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));

  }

  @Test
  public void successWhenCommentDescriptionEmpty() throws Exception {
    ClientCollateral toCreate =
        new ClientCollateral(activeIndicator, collateralClientReporterRelationshipType, "",
            clientId, collateralIndividualId);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void failsWhenCommentDescriptionTooLong() throws Exception {
    ClientCollateral toCreate =
        new ClientCollateral(activeIndicator, collateralClientReporterRelationshipType,
            "123456789012345678901234567890123456789012345678901234567890", clientId,
            collateralIndividualId);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf(
            "commentDescription size must be between 0 and 50"), is(greaterThanOrEqualTo(0)));
  }

  /*
   * clientId Tests
   */
  @Test
  public void failsWhenClientIdNull() throws Exception {
    ClientCollateral toCreate =
        new ClientCollateral(activeIndicator, collateralClientReporterRelationshipType,
            commentDescription, null, collateralIndividualId);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("clientId may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenClientIdEmpty() throws Exception {
    ClientCollateral toCreate =
        new ClientCollateral(activeIndicator, collateralClientReporterRelationshipType,
            commentDescription, "", collateralIndividualId);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("clientId may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenClientIdTooLong() throws Exception {
    ClientCollateral toCreate =
        new ClientCollateral(activeIndicator, collateralClientReporterRelationshipType,
            commentDescription, "123456789012", collateralIndividualId);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("clientId size must be between 1 and 10"),
        is(greaterThanOrEqualTo(0)));
  }

  /*
   * collateralIndividualId Tests
   */
  @Test
  public void failsWhenCollateralIndividualIdNull() throws Exception {
    ClientCollateral toCreate =
        new ClientCollateral(activeIndicator, collateralClientReporterRelationshipType,
            commentDescription, clientId, null);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("collateralIndividualId may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenCollateralIndividualIdEmpty() throws Exception {
    ClientCollateral toCreate =
        new ClientCollateral(activeIndicator, collateralClientReporterRelationshipType,
            commentDescription, clientId, "");
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("collateralIndividualId may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenCollateralIndividualIdTooLong() throws Exception {
    ClientCollateral toCreate =
        new ClientCollateral(activeIndicator, collateralClientReporterRelationshipType,
            commentDescription, clientId, "123456789012");
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf(
            "collateralIndividualId size must be between 1 and 10"), is(greaterThanOrEqualTo(0)));
  }

  /*
   * Utils
   */
  private ClientCollateral validClientCollateral() {
    return new ClientCollateral(activeIndicator, collateralClientReporterRelationshipType,
        commentDescription, clientId, collateralIndividualId);
  }


}
