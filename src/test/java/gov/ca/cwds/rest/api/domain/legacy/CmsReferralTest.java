package gov.ca.cwds.rest.api.domain.legacy;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import gov.ca.cwds.rest.api.domain.cms.Allegation;
import gov.ca.cwds.rest.api.domain.cms.CmsReferral;
import gov.ca.cwds.rest.api.domain.cms.CrossReport;
import gov.ca.cwds.rest.api.domain.cms.Referral;
import gov.ca.cwds.rest.api.domain.cms.ReferralClient;
import gov.ca.cwds.rest.api.domain.cms.Reporter;
import gov.ca.cwds.rest.api.persistence.cms.StaffPerson;
import gov.ca.cwds.rest.core.Api;
import gov.ca.cwds.rest.jdbi.CrudsDao;
import gov.ca.cwds.rest.jdbi.DataAccessEnvironment;
import gov.ca.cwds.rest.resources.cms.CmsReferralResource;
import io.dropwizard.jackson.Jackson;
import io.dropwizard.testing.junit.ResourceTestRule;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

public class CmsReferralTest {

  private static final String ROOT_RESOURCE = "/" + Api.RESOURCE_CMSREFERRAL + "/";;

  private static final CmsReferralResource mockedCmsReferralResource =
      mock(CmsReferralResource.class);

  @ClassRule
  public static final ResourceTestRule resources =
      ResourceTestRule.builder().addResource(mockedCmsReferralResource).build();

  private static final ObjectMapper MAPPER = Jackson.newObjectMapper();
  private CmsReferral validCmsReferral = validCmsReferral();

  @Before
  public void setup() {
    @SuppressWarnings("rawtypes")
    CrudsDao crudsDao = mock(CrudsDao.class);
    DataAccessEnvironment.register(gov.ca.cwds.rest.api.persistence.cms.StaffPerson.class,
        crudsDao);
    when(crudsDao.find(any())).thenReturn(mock(StaffPerson.class));

    when(mockedCmsReferralResource.create(eq(validCmsReferral)))
        .thenReturn(Response.status(Response.Status.NO_CONTENT).entity(null).build());
  }


  @Test
  public void equalsHashCodeWork() {
    EqualsVerifier.forClass(CmsReferral.class).suppress(Warning.NONFINAL_FIELDS).verify();
  }

  @Test
  public void jsonCreatorConstructorTest() throws Exception {

    Referral referral = MAPPER.readValue(
        fixture("fixtures/domain/cms/CmsReferral/valid/referralCmsReferral.json"), Referral.class);
    Reporter reporter = MAPPER.readValue(
        fixture("fixtures/domain/cms/CmsReferral/valid/reporterCmsReferral.json"), Reporter.class);
    ReferralClient referralClient = MAPPER.readValue(
        fixture("fixtures/domain/cms/CmsReferral/valid/referralClientCmsReferral.json"),
        ReferralClient.class);
    CrossReport crossReport = MAPPER.readValue(
        fixture("fixtures/domain/cms/CmsReferral/valid/crossReportCmsReferral.json"),
        CrossReport.class);
    Allegation allegation = MAPPER.readValue(
        fixture("fixtures/domain/cms/CmsReferral/valid/allegationCmsReferral.json"),
        Allegation.class);

    CmsReferral cmsReferral =
        new CmsReferral(referral, allegation, crossReport, referralClient, reporter);

    assertThat(cmsReferral.getReferral(), is(equalTo(referral)));
    assertThat(cmsReferral.getAllegation(), is(equalTo(allegation)));
    assertThat(cmsReferral.getCrossReport(), is(equalTo(crossReport)));
    assertThat(cmsReferral.getReferralClient(), is(equalTo(referralClient)));
    assertThat(cmsReferral.getReporter(), is(equalTo(reporter)));

  }

  @Test
  public void serializesToJSON() throws Exception {
    final String expected = MAPPER.writeValueAsString(MAPPER.readValue(
        fixture("fixtures/domain/cms/CmsReferral/valid/cmsReferral.json"), CmsReferral.class));

    assertThat(MAPPER.writeValueAsString(validCmsReferral()), is(equalTo(expected)));
  }

  @Test
  public void deserializesFromJSON() throws Exception {
    assertThat(MAPPER.readValue(fixture("fixtures/domain/cms/CmsReferral/valid/cmsReferral.json"),
        CmsReferral.class), is(equalTo(validCmsReferral())));
  }

  /*
   * Successful Tests
   */
  @Test
  public void successfulWithValid() throws Exception {
    CmsReferral toCreate = MAPPER.readValue(
        fixture("fixtures/domain/cms/CmsReferral/valid/cmsReferral.json"), CmsReferral.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void successfulWithOptionalsNotIncluded() throws Exception {
    CmsReferral toCreate = MAPPER.readValue(
        fixture("fixtures/domain/cms/CmsReferral/valid/cmsReferralWithOptionalsNotIncluded.json"),
        CmsReferral.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  // failure when Referral is invalid, missing, or null
  @Test
  public void failureWhenReferralNull() throws Exception {

    CmsReferral toCreate = MAPPER.readValue(
        fixture("fixtures/domain/cms/CmsReferral/invalid/cmsReferralNullReferral.json"),
        CmsReferral.class);

    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    // String message = response.readEntity(String.class);
    // System.out.print(message);

    assertThat(response.getStatus(), is(equalTo(422)));
  }

  @Test
  public void failureWhenReferralIsEmpty() throws Exception {

    CmsReferral toCreate = MAPPER.readValue(
        fixture("fixtures/domain/cms/CmsReferral/invalid/cmsReferralWhenReferralEmpty.json"),
        CmsReferral.class);

    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));

    assertThat(response.getStatus(), is(equalTo(422)));

  }

  @Test
  public void failureWhenReferralIsInvalid() throws Exception {

    CmsReferral toCreate = MAPPER.readValue(
        fixture("fixtures/domain/cms/CmsReferral/invalid/cmsReferralInvalidReferral.json"),
        CmsReferral.class);

    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    // String message = response.readEntity(String.class);
    // System.out.print(message);

    assertThat(response.getStatus(), is(equalTo(422)));

  }

  /*
   * failure when ReferralClient is null, missing, or invalid
   */
  @Test
  public void failureWhenReferralClientNull() throws Exception {

    CmsReferral toCreate = MAPPER.readValue(
        fixture("fixtures/domain/cms/CmsReferral/invalid/cmsReferralNullReferralClient.json"),
        CmsReferral.class);

    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    // String message = response.readEntity(String.class);
    // System.out.print(message);

    assertThat(response.getStatus(), is(equalTo(422)));

  }

  @Test
  public void failureWhenReferralClientIsEmpty() throws Exception {

    CmsReferral toCreate = MAPPER.readValue(
        fixture("fixtures/domain/cms/CmsReferral/invalid/cmsReferralWhenReferralClientEmpty.json"),
        CmsReferral.class);

    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));

    assertThat(response.getStatus(), is(equalTo(422)));

  }

  @Test
  public void failureWhenReferralClientIsInvalid() throws Exception {

    CmsReferral toCreate = MAPPER.readValue(
        fixture("fixtures/domain/cms/CmsReferral/invalid/cmsReferralInvalidReferralClient.json"),
        CmsReferral.class);

    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    // String message = response.readEntity(String.class);
    // System.out.print(message);

    assertThat(response.getStatus(), is(equalTo(422)));

  }

  /*
   * failure when Reporter is null, missing, or invalid
   */
  @Test
  public void failureWhenReporterNull() throws Exception {

    CmsReferral toCreate = MAPPER.readValue(
        fixture("fixtures/domain/cms/CmsReferral/invalid/cmsReferralNullReporter.json"),
        CmsReferral.class);

    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    // String message = response.readEntity(String.class);
    // System.out.print(message);

    assertThat(response.getStatus(), is(equalTo(422)));

  }

  @Test
  public void failureWhenReporterIsEmpty() throws Exception {

    CmsReferral toCreate = MAPPER.readValue(
        fixture("fixtures/domain/cms/CmsReferral/invalid/cmsReferralWhenReporterEmpty.json"),
        CmsReferral.class);

    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));

    assertThat(response.getStatus(), is(equalTo(422)));

  }

  @Test
  public void failureWhenReporterInvalid() throws Exception {

    CmsReferral toCreate = MAPPER.readValue(
        fixture("fixtures/domain/cms/CmsReferral/invalid/cmsReferralInvalidReporter.json"),
        CmsReferral.class);

    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));

    assertThat(response.getStatus(), is(equalTo(422)));

  }

  /*
   * failure when Reporter is null, missing, or invalid
   */
  @Test
  public void failureWhenAllegationNull() throws Exception {

    CmsReferral toCreate = MAPPER.readValue(
        fixture("fixtures/domain/cms/CmsReferral/invalid/cmsReferralNullAllegation.json"),
        CmsReferral.class);

    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    // String message = response.readEntity(String.class);
    // System.out.print(message);

    assertThat(response.getStatus(), is(equalTo(422)));

  }

  @Test
  public void failureWhenAllegationIsEmpty() throws Exception {

    CmsReferral toCreate = MAPPER.readValue(
        fixture("fixtures/domain/cms/CmsReferral/invalid/cmsReferralWhenAllegationEmpty.json"),
        CmsReferral.class);

    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));

    assertThat(response.getStatus(), is(equalTo(422)));

  }

  @Test
  public void failureWhenAllegationInvalid() throws Exception {

    CmsReferral toCreate = MAPPER.readValue(
        fixture("fixtures/domain/cms/CmsReferral/invalid/cmsReferralInvalidAllegation.json"),
        CmsReferral.class);

    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));

    assertThat(response.getStatus(), is(equalTo(422)));

  }

  /*
   * cross report test - cross report is not required for minimal referral data
   */

  @Test
  public void successWhenCrossReportNull() throws Exception {
    CmsReferral toCreate = MAPPER.readValue(
        fixture("fixtures/domain/cms/CmsReferral/valid/cmsReferralNullCrossReport.json"),
        CmsReferral.class);

    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    // String message = response.readEntity(String.class);
    // System.out.print(message);

    assertThat(response.getStatus(), is(equalTo(204)));
  }


  @Test
  public void failureWhenCrossReportIsEmpty() throws Exception {
    CmsReferral toCreate = MAPPER.readValue(
        fixture("fixtures/domain/cms/CmsReferral/invalid/cmsReferralEmptyCrossReport.json"),
        CmsReferral.class);

    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    // String message = response.readEntity(String.class);
    // System.out.print(message);

    assertThat(response.getStatus(), is(equalTo(422)));

  }

  @Test
  public void failureWhenCrossReportInvalid() throws Exception {
    CmsReferral toCreate = MAPPER.readValue(
        fixture("fixtures/domain/cms/CmsReferral/invalid/cmsReferralInvalidCrossReport.json"),
        CmsReferral.class);

    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));

    assertThat(response.getStatus(), is(equalTo(422)));

  }

  @Test
  public void SuccessWhenCmsReferralEqualsNullCmsReferral() throws Exception {
    CmsReferral validReferral = MAPPER.readValue(
        fixture("fixtures/domain/cms/CmsReferral/valid/cmsReferral.json"), CmsReferral.class);

    assertThat(validReferral.equals(null), is(equalTo(Boolean.FALSE)));

  }

  @Test
  public void SuccessWhenCmsReferralEqualsNullReferralClient() throws Exception {
    CmsReferral validReferral = MAPPER.readValue(
        fixture("fixtures/domain/cms/CmsReferral/valid/cmsReferral.json"), CmsReferral.class);

    CmsReferral invalidReferral = MAPPER.readValue(
        fixture("fixtures/domain/cms/CmsReferral/invalid/cmsReferralNullReferralClient.json"),
        CmsReferral.class);

    assertThat(validReferral.equals(invalidReferral), is(equalTo(Boolean.FALSE)));

  }

  @Test
  public void SucessWhenCmsReferralEqualsOtherObjectType() throws Exception {
    CmsReferral validCmsReferral = MAPPER.readValue(
        fixture("fixtures/domain/cms/CmsReferral/valid/cmsReferral.json"), CmsReferral.class);

    Referral validReferral = MAPPER
        .readValue(fixture("fixtures/domain/legacy/Referral/valid/valid.json"), Referral.class);

    assertThat(validCmsReferral.equals(validReferral), is(equalTo(Boolean.FALSE)));
  }

  @Test
  public void SucessWhenCmsReferralAndEmptyReferralClient() throws Exception {
    CmsReferral validCmsReferral = this.validCmsReferral();

    CmsReferral invalidCmsReferral = MAPPER.readValue(
        fixture("fixtures/domain/cms/CmsReferral/invalid/cmsReferralWhenReferralClientEmpty.json"),
        CmsReferral.class);

    assertThat(validCmsReferral.equals(invalidCmsReferral), is(equalTo(Boolean.FALSE)));

  }

  @Test
  public void SucessWhenCmsReferralAndEmptyAllegation() throws Exception {
    CmsReferral validCmsReferral = this.validCmsReferral();

    CmsReferral invalidCmsReferral = MAPPER.readValue(
        fixture("fixtures/domain/cms/CmsReferral/invalid/cmsReferralWhenAllegationEmpty.json"),
        CmsReferral.class);

    assertThat(validCmsReferral.equals(invalidCmsReferral), is(equalTo(Boolean.FALSE)));

  }

  @Test
  public void SucessWhenCmsReferralAndEmptyReporter() throws Exception {
    CmsReferral validCmsReferral = this.validCmsReferral();

    CmsReferral invalidCmsReferral = MAPPER.readValue(
        fixture("fixtures/domain/cms/CmsReferral/invalid/cmsReferralWhenReporterEmpty.json"),
        CmsReferral.class);

    assertThat(validCmsReferral.equals(invalidCmsReferral), is(equalTo(Boolean.FALSE)));

  }

  @Test
  public void SucessWhenCmsReferralAndEmptyReferral() throws Exception {
    CmsReferral validCmsReferral = this.validCmsReferral();

    CmsReferral invalidCmsReferral = MAPPER.readValue(
        fixture("fixtures/domain/cms/CmsReferral/invalid/cmsReferralWhenReferralEmpty.json"),
        CmsReferral.class);

    assertThat(validCmsReferral.equals(invalidCmsReferral), is(equalTo(Boolean.FALSE)));

  }

  @Test
  public void SucessWhenCmsReferralAndEmptyCrossReport() throws Exception {
    CmsReferral validCmsReferral = this.validCmsReferral();

    CmsReferral invalidCmsReferral = MAPPER.readValue(
        fixture("fixtures/domain/cms/CmsReferral/invalid/cmsReferralEmptyCrossReport.json"),
        CmsReferral.class);

    assertThat(validCmsReferral.equals(invalidCmsReferral), is(equalTo(Boolean.FALSE)));

  }

  @Test
  public void SucessWhenCmsReferralAndNullAllegation() throws Exception {
    CmsReferral validCmsReferral = this.validCmsReferral();

    CmsReferral invalidCmsReferral = MAPPER.readValue(
        fixture("fixtures/domain/cms/CmsReferral/invalid/cmsReferralNullAllegation.json"),
        CmsReferral.class);

    assertThat(invalidCmsReferral.equals(validCmsReferral), is(equalTo(Boolean.FALSE)));

  }
  // @Test
  // public void failureWhenReferralIdIsDifferentReferralClient() throws Exception {
  // CmsReferral toCreate = MAPPER.readValue(
  // fixture(
  // "fixtures/domain/cms/CmsReferral/invalid/cmsReferralIdIsDifferentReferralClient.json"),
  // CmsReferral.class);
  //
  // Response response =
  // resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
  // .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
  //
  // assertThat(response.getStatus(), is(equalTo(422)));
  //
  //
  // }

  /*
   * Utilities
   */
  private CmsReferral validCmsReferral() {

    try {
      Referral referral = MAPPER.readValue(
          fixture("fixtures/domain/cms/CmsReferral/valid/referralCmsReferral.json"),
          Referral.class);
      Reporter reporter = MAPPER.readValue(
          fixture("fixtures/domain/cms/CmsReferral/valid/reporterCmsReferral.json"),
          Reporter.class);
      ReferralClient referralClient = MAPPER.readValue(
          fixture("fixtures/domain/cms/CmsReferral/valid/referralClientCmsReferral.json"),
          ReferralClient.class);
      CrossReport crossReport = MAPPER.readValue(
          fixture("fixtures/domain/cms/CmsReferral/valid/crossReportCmsReferral.json"),
          CrossReport.class);
      Allegation allegation = MAPPER.readValue(
          fixture("fixtures/domain/cms/CmsReferral/valid/allegationCmsReferral.json"),
          Allegation.class);
      return new CmsReferral(referral, allegation, crossReport, referralClient, reporter);

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
