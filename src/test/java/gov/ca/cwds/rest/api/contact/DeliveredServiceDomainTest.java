package gov.ca.cwds.rest.api.contact;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gov.ca.cwds.data.cms.TestSystemCodeCache;
import gov.ca.cwds.data.persistence.contact.DeliveredServiceEntity;
import gov.ca.cwds.fixture.contacts.DeliveredServiceResourceBuilder;
import gov.ca.cwds.rest.api.domain.DomainChef;
import gov.ca.cwds.rest.core.Api;
import gov.ca.cwds.rest.resources.cms.JerseyGuiceRule;
import gov.ca.cwds.rest.resources.contact.DeliveredServiceResource;
import io.dropwizard.testing.junit.ResourceTestRule;

import java.util.Date;

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

/***
 * 
 * @author CWDS API Team
 *
 */
@SuppressWarnings("javadoc")
public class DeliveredServiceDomainTest {

  private static final String ROOT_RESOURCE = "/" + Api.RESOURCE_DELIVERY_SERVICE + "/";
  private static final DeliveredServiceResource mockedDeliveredServiceResource =
      mock(DeliveredServiceResource.class);

  @After
  public void ensureServiceLocatorPopulated() {
    JerseyGuiceUtils.reset();
  }

  @ClassRule
  public static JerseyGuiceRule rule = new JerseyGuiceRule();

  @ClassRule
  public static final ResourceTestRule resources = ResourceTestRule.builder()
      .addResource(mockedDeliveredServiceResource).build();

  private DeliveredServiceDomain validDeliveredServiceDomain =
      new DeliveredServiceResourceBuilder().buildDeliveredServiceResource();

  private String id = "ABC1234567";
  private String lastUpdatedId = "0X5";
  private Date lastUpdatedTime = new Date();

  private Short cftLeadAgencyType = (short) 4212;
  private Boolean coreServiceIndicator = Boolean.FALSE;
  private Integer communicationMethodType = 409;
  private Integer contactLocationType = 415;
  private String contactVisitCode = "C";
  private String countySpecificCode = "99";
  private String detailText = "ABC1234567";
  private String detailTextContinuation = "ABC12345t7";
  private String endDate = "2000-01-01";
  private String endTime = "16:41:49";
  private String primaryDeliveredServiceId = "ABC1at0875";
  private String hardCopyDocumentOnFileCode = "N";
  private String otherParticipantsDesc = "description of the world";
  private String providedByCode = "S";
  private String providedById = "ABC097r534";
  private Integer serviceContactType = 408;
  private String startDate = "1992-01-01";
  private String startTime = "16:41:49";
  private String statusCode = "C";
  private String supervisionCode = "C";
  private Boolean wraparoundServiceIndicator = Boolean.FALSE;

  /*
   * Load system code cache
   */
  TestSystemCodeCache testSystemCodeCache = new TestSystemCodeCache();

  @Before
  public void setup() {
    when(mockedDeliveredServiceResource.create(eq(validDeliveredServiceDomain))).thenReturn(
        Response.status(Response.Status.NO_CONTENT).entity(null).build());

  }

  @Test
  public void testPersistentConstructor() throws Exception {

    DeliveredServiceDomain domain =
        new DeliveredServiceResourceBuilder().buildDeliveredServiceResource();

    DeliveredServiceEntity persistent =
        new DeliveredServiceEntity(id, domain, lastUpdatedId, lastUpdatedTime);

    assertThat(persistent.getId(), is(equalTo(id)));
    assertThat(persistent.getCftLeadAgencyType(), is(equalTo(cftLeadAgencyType)));
    assertThat(persistent.getCoreServiceIndicator(),
        is(equalTo(DomainChef.cookBoolean(coreServiceIndicator))));
    assertThat(persistent.getCommunicationMethodType(),
        is(equalTo(communicationMethodType.shortValue())));
    assertThat(persistent.getContactLocationType(), is(equalTo(contactLocationType.shortValue())));
    assertThat(persistent.getContactVisitCode(), is(equalTo(contactVisitCode)));
    assertThat(persistent.getCountySpecificCode(), is(equalTo(countySpecificCode)));
    assertThat(persistent.getDetailText(), is(equalTo(detailText)));
    assertThat(persistent.getHardCopyDocumentOnFileCode(), is(equalTo(hardCopyDocumentOnFileCode)));
    assertThat(persistent.getDetailTextContinuation(), is(equalTo(detailTextContinuation)));
    assertThat(persistent.getEndDate(), is(equalTo(DomainChef.uncookDateString(endDate))));
    assertThat(persistent.getEndTime(), is(equalTo(DomainChef.uncookTimeString(endTime))));
    assertThat(persistent.getPrimaryDeliveredServiceId(), is(equalTo(primaryDeliveredServiceId)));
    assertThat(persistent.getOtherParticipantsDesc(), is(equalTo(otherParticipantsDesc)));
    assertThat(persistent.getProvidedByCode(), is(equalTo(providedByCode)));
    assertThat(persistent.getProvidedById(), is(equalTo(providedById)));
    assertThat(persistent.getStartDate(), is(equalTo(DomainChef.uncookDateString(startDate))));
    assertThat(persistent.getStartTime(), is(equalTo(DomainChef.uncookTimeString(startTime))));
    assertThat(persistent.getStatusCode(), is(equalTo(statusCode)));
    assertThat(persistent.getSupervisionCode(), is(equalTo(supervisionCode)));
    assertThat(persistent.getServiceContactType(), is(equalTo(serviceContactType.shortValue())));
    assertThat(persistent.getWraparoundServiceIndicator(),
        is(equalTo(DomainChef.cookBoolean(wraparoundServiceIndicator))));

  }

  @Test
  public void testJSONCreatorConstructor() throws Exception {

    DeliveredServiceDomain validDeliveredServiceDomain =
        new DeliveredServiceResourceBuilder().buildDeliveredServiceResource();

    DeliveredServiceDomain domain =
        new DeliveredServiceDomain(id, cftLeadAgencyType, coreServiceIndicator,
            communicationMethodType, contactLocationType, contactVisitCode, countySpecificCode,
            detailText, detailTextContinuation, endDate, endTime, primaryDeliveredServiceId,
            hardCopyDocumentOnFileCode, otherParticipantsDesc, providedByCode, providedById,
            serviceContactType, startDate, startTime, statusCode, supervisionCode,
            wraparoundServiceIndicator);

    assertThat(domain.getId(), is(equalTo(validDeliveredServiceDomain.getId())));
    assertThat(domain.getCftLeadAgencyType(),
        is(equalTo(validDeliveredServiceDomain.getCftLeadAgencyType())));
    assertThat(domain.getCoreServiceIndicator(),
        is(equalTo(validDeliveredServiceDomain.getCoreServiceIndicator())));
    assertThat(domain.getCommunicationMethodType(),
        is(equalTo(validDeliveredServiceDomain.getCommunicationMethodType())));
    assertThat(domain.getContactLocationType(),
        is(equalTo(validDeliveredServiceDomain.getContactLocationType())));
    assertThat(domain.getContactVisitCode(),
        is(equalTo(validDeliveredServiceDomain.getContactVisitCode())));
    assertThat(domain.getCountySpecificCode(),
        is(equalTo(validDeliveredServiceDomain.getCountySpecificCode())));
    assertThat(domain.getDetailText(), is(equalTo(validDeliveredServiceDomain.getDetailText())));
    assertThat(domain.getHardCopyDocumentOnFileCode(),
        is(equalTo(validDeliveredServiceDomain.getHardCopyDocumentOnFileCode())));
    assertThat(domain.getDetailTextContinuation(),
        is(equalTo(validDeliveredServiceDomain.getDetailTextContinuation())));
    assertThat(domain.getEndDate(), is(equalTo(validDeliveredServiceDomain.getEndDate())));
    assertThat(domain.getEndTime(), is(equalTo(validDeliveredServiceDomain.getEndTime())));
    assertThat(domain.getPrimaryDeliveredServiceId(),
        is(equalTo(validDeliveredServiceDomain.getPrimaryDeliveredServiceId())));
    assertThat(domain.getOtherParticipantsDesc(),
        is(equalTo(validDeliveredServiceDomain.getOtherParticipantsDesc())));
    assertThat(domain.getProvidedByCode(),
        is(equalTo(validDeliveredServiceDomain.getProvidedByCode())));
    assertThat(domain.getProvidedById(), is(equalTo(validDeliveredServiceDomain.getProvidedById())));
    assertThat(domain.getStartDate(), is(equalTo(validDeliveredServiceDomain.getStartDate())));
    assertThat(domain.getStartTime(), is(equalTo(validDeliveredServiceDomain.getStartTime())));
    assertThat(domain.getStatusCode(), is(equalTo(validDeliveredServiceDomain.getStatusCode())));
    assertThat(domain.getSupervisionCode(),
        is(equalTo(validDeliveredServiceDomain.getSupervisionCode())));
    assertThat(domain.getServiceContactType(),
        is(equalTo(validDeliveredServiceDomain.getServiceContactType())));
    assertThat(domain.getWraparoundServiceIndicator(),
        is(equalTo(validDeliveredServiceDomain.getWraparoundServiceIndicator())));
  }

  @Test
  public void testEqualsHashCodeWorks() throws Exception {
    EqualsVerifier.forClass(DeliveredServiceDomain.class).suppress(Warning.NONFINAL_FIELDS)
        .verify();

  }

  @Test
  public void testSuccessWithValid() throws Exception {
    DeliveredServiceDomain toCreate =
        new DeliveredServiceResourceBuilder().buildDeliveredServiceResource();

    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));

  }

  /*
   * id test
   */
  @Test
  public void failWhenIdNull() throws Exception {
    DeliveredServiceDomain validDeliveredService =
        new DeliveredServiceResourceBuilder().setId(null).buildDeliveredServiceResource();

    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validDeliveredService, MediaType.APPLICATION_JSON));

    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("id may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  /*
   * communicationMethodType test
   */
  @Test
  public void failWhenCommunicationMethodTypeInvalid() throws Exception {
    DeliveredServiceDomain validDeliveredService =
        new DeliveredServiceResourceBuilder().setCommunicationMethodType(123)
            .buildDeliveredServiceResource();

    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validDeliveredService, MediaType.APPLICATION_JSON));

    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf(
            "communicationMethodType  must be a valid system code for category CMM_MTHC"),
        is(greaterThanOrEqualTo(0)));
  }

  /*
   * contactLocationType test
   */
  @Test
  public void failWhenContactLocationTypeInvalid() throws Exception {
    DeliveredServiceDomain validDeliveredService =
        new DeliveredServiceResourceBuilder().setContactLocationType(123)
            .buildDeliveredServiceResource();

    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validDeliveredService, MediaType.APPLICATION_JSON));

    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf(
            "contactLocationType  must be a valid system code for category CNT_LOC"),
        is(greaterThanOrEqualTo(0)));
  }

  /*
   * contactVisitCode test
   */
  @Test
  public void failWhenContactVisitCodeNull() throws Exception {
    DeliveredServiceDomain validDeliveredService =
        new DeliveredServiceResourceBuilder().setContactVisitCode(null)
            .buildDeliveredServiceResource();

    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validDeliveredService, MediaType.APPLICATION_JSON));

    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("contactVisitCode may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failWhenContactVisitCodeEmpty() throws Exception {
    DeliveredServiceDomain validDeliveredService =
        new DeliveredServiceResourceBuilder().setContactVisitCode("")
            .buildDeliveredServiceResource();

    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validDeliveredService, MediaType.APPLICATION_JSON));

    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("contactVisitCode may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void successWhenContactVisitCodeC() throws Exception {
    DeliveredServiceDomain validDeliveredService =
        new DeliveredServiceResourceBuilder().setContactVisitCode("C")
            .buildDeliveredServiceResource();

    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validDeliveredService, MediaType.APPLICATION_JSON));

    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void successWhenContactVisitCodeN() throws Exception {
    DeliveredServiceDomain validDeliveredService =
        new DeliveredServiceResourceBuilder().setContactVisitCode("N")
            .buildDeliveredServiceResource();

    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validDeliveredService, MediaType.APPLICATION_JSON));

    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void successWhenContactVisitCodeV() throws Exception {
    DeliveredServiceDomain validDeliveredService =
        new DeliveredServiceResourceBuilder().setContactVisitCode("V")
            .buildDeliveredServiceResource();

    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validDeliveredService, MediaType.APPLICATION_JSON));

    assertThat(response.getStatus(), is(equalTo(204)));
  }

  /*
   * countySpecificCode test
   */
  @Test
  public void failWhenCountySpecificCodeNull() throws Exception {
    DeliveredServiceDomain validDeliveredService =
        new DeliveredServiceResourceBuilder().setCountySpecificCode(null)
            .buildDeliveredServiceResource();

    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validDeliveredService, MediaType.APPLICATION_JSON));

    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("countySpecificCode may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failWhenCountySpecificCodeEmpty() throws Exception {
    DeliveredServiceDomain validDeliveredService =
        new DeliveredServiceResourceBuilder().setCountySpecificCode("")
            .buildDeliveredServiceResource();

    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validDeliveredService, MediaType.APPLICATION_JSON));

    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("countySpecificCode may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  /*
   * endDate test
   */
  @Test
  public void failWhenEndDateWrongFormat() throws Exception {
    DeliveredServiceDomain validDeliveredService =
        new DeliveredServiceResourceBuilder().setEndDate("2000/01/01")
            .buildDeliveredServiceResource();

    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validDeliveredService, MediaType.APPLICATION_JSON));

    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("endDate must be in the format of yyyy-MM-dd"),
        is(greaterThanOrEqualTo(0)));
  }

  /*
   * endTime test
   */
  @Test
  public void failWhenEndTimeWrongFormat() throws Exception {
    DeliveredServiceDomain validDeliveredService =
        new DeliveredServiceResourceBuilder().setEndTime("16/41/49")
            .buildDeliveredServiceResource();

    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validDeliveredService, MediaType.APPLICATION_JSON));

    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("endTime must be in the format of HH:mm:ss"),
        is(greaterThanOrEqualTo(0)));
  }

  /*
   * hardCopyDocumentOnFileCode test
   */
  @Test
  public void failWhenHardCopyDocumentOnFileCodeNull() throws Exception {
    DeliveredServiceDomain validDeliveredService =
        new DeliveredServiceResourceBuilder().setHardCopyDocumentOnFileCode(null)
            .buildDeliveredServiceResource();

    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validDeliveredService, MediaType.APPLICATION_JSON));

    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("hardCopyDocumentOnFileCode may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failWhenHardCopyDocumentOnFileCodeEmpty() throws Exception {
    DeliveredServiceDomain validDeliveredService =
        new DeliveredServiceResourceBuilder().setHardCopyDocumentOnFileCode("")
            .buildDeliveredServiceResource();

    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validDeliveredService, MediaType.APPLICATION_JSON));

    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("hardCopyDocumentOnFileCode may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void sucessWhenHardCopyDocumentOnFileCodeN() throws Exception {
    DeliveredServiceDomain validDeliveredService =
        new DeliveredServiceResourceBuilder().setHardCopyDocumentOnFileCode("N")
            .buildDeliveredServiceResource();

    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validDeliveredService, MediaType.APPLICATION_JSON));

    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void sucessWhenHardCopyDocumentOnFileCodeU() throws Exception {
    DeliveredServiceDomain validDeliveredService =
        new DeliveredServiceResourceBuilder().setHardCopyDocumentOnFileCode("U")
            .buildDeliveredServiceResource();

    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validDeliveredService, MediaType.APPLICATION_JSON));

    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void sucessWhenHardCopyDocumentOnFileCodeY() throws Exception {
    DeliveredServiceDomain validDeliveredService =
        new DeliveredServiceResourceBuilder().setHardCopyDocumentOnFileCode("Y")
            .buildDeliveredServiceResource();

    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validDeliveredService, MediaType.APPLICATION_JSON));

    assertThat(response.getStatus(), is(equalTo(204)));
  }

  /*
   * providedByCode test
   */
  @Test
  public void failWhenProvidedByCodeInValid() throws Exception {
    DeliveredServiceDomain validDeliveredService =
        new DeliveredServiceResourceBuilder().setProvidedByCode("Z")
            .buildDeliveredServiceResource();

    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validDeliveredService, MediaType.APPLICATION_JSON));

    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("providedByCode must be one of [S, O, V, X]"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void successWhenProvidedByCodeS() throws Exception {
    DeliveredServiceDomain validDeliveredService =
        new DeliveredServiceResourceBuilder().setProvidedByCode("S")
            .buildDeliveredServiceResource();

    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validDeliveredService, MediaType.APPLICATION_JSON));

    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void successWhenProvidedByCodeO() throws Exception {
    DeliveredServiceDomain validDeliveredService =
        new DeliveredServiceResourceBuilder().setProvidedByCode("O")
            .buildDeliveredServiceResource();

    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validDeliveredService, MediaType.APPLICATION_JSON));

    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void successWhenProvidedByCodeV() throws Exception {
    DeliveredServiceDomain validDeliveredService =
        new DeliveredServiceResourceBuilder().setProvidedByCode("V")
            .buildDeliveredServiceResource();

    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validDeliveredService, MediaType.APPLICATION_JSON));

    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void successWhenProvidedByCodeX() throws Exception {
    DeliveredServiceDomain validDeliveredService =
        new DeliveredServiceResourceBuilder().setProvidedByCode("X")
            .buildDeliveredServiceResource();

    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validDeliveredService, MediaType.APPLICATION_JSON));

    assertThat(response.getStatus(), is(equalTo(204)));
  }

  /*
   * startDate test
   */
  @Test
  public void failWhenStartDateWrongFormat() throws Exception {
    DeliveredServiceDomain validDeliveredService =
        new DeliveredServiceResourceBuilder().setStartDate("2000/01/01")
            .buildDeliveredServiceResource();

    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validDeliveredService, MediaType.APPLICATION_JSON));

    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("startDate must be in the format of yyyy-MM-dd"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failWhenStartDateNull() throws Exception {
    DeliveredServiceDomain validDeliveredService =
        new DeliveredServiceResourceBuilder().setStartDate(null).buildDeliveredServiceResource();

    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validDeliveredService, MediaType.APPLICATION_JSON));

    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).lastIndexOf("startDate may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failWhenStartDateEmpty() throws Exception {
    DeliveredServiceDomain validDeliveredService =
        new DeliveredServiceResourceBuilder().setStartDate("").buildDeliveredServiceResource();

    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validDeliveredService, MediaType.APPLICATION_JSON));

    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).lastIndexOf("startDate may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  /*
   * startTime test
   */
  @Test
  public void failWhenStartTimeWrongFormat() throws Exception {
    DeliveredServiceDomain validDeliveredService =
        new DeliveredServiceResourceBuilder().setStartTime("16/41/49")
            .buildDeliveredServiceResource();

    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validDeliveredService, MediaType.APPLICATION_JSON));

    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("startTime must be in the format of HH:mm:ss"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failWhenStartTimeNull() throws Exception {
    DeliveredServiceDomain validDeliveredService =
        new DeliveredServiceResourceBuilder().setStartTime(null).buildDeliveredServiceResource();

    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validDeliveredService, MediaType.APPLICATION_JSON));

    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).lastIndexOf("startTime may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failWhenStartTimeEmpty() throws Exception {
    DeliveredServiceDomain validDeliveredService =
        new DeliveredServiceResourceBuilder().setStartTime("").buildDeliveredServiceResource();

    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validDeliveredService, MediaType.APPLICATION_JSON));

    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).lastIndexOf("startTime may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  /*
   * statusCode test
   */
  @Test
  public void failWhenStatusCodeInValid() throws Exception {
    DeliveredServiceDomain validDeliveredService =
        new DeliveredServiceResourceBuilder().setStatusCode("Z").buildDeliveredServiceResource();

    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validDeliveredService, MediaType.APPLICATION_JSON));

    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("statusCode must be one of [C, A, S]"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failWhenStatusCodeEmpty() throws Exception {
    DeliveredServiceDomain validDeliveredService =
        new DeliveredServiceResourceBuilder().setStatusCode("").buildDeliveredServiceResource();

    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validDeliveredService, MediaType.APPLICATION_JSON));

    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("statusCode may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failWhenStatusCodeNull() throws Exception {
    DeliveredServiceDomain validDeliveredService =
        new DeliveredServiceResourceBuilder().setStatusCode(null).buildDeliveredServiceResource();

    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validDeliveredService, MediaType.APPLICATION_JSON));

    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("statusCode may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void successWhenStatusCodeC() throws Exception {
    DeliveredServiceDomain validDeliveredService =
        new DeliveredServiceResourceBuilder().setStatusCode("C").buildDeliveredServiceResource();

    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validDeliveredService, MediaType.APPLICATION_JSON));

    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void successWhenStatusCodeA() throws Exception {
    DeliveredServiceDomain validDeliveredService =
        new DeliveredServiceResourceBuilder().setStatusCode("A").buildDeliveredServiceResource();

    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validDeliveredService, MediaType.APPLICATION_JSON));

    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void successWhenStatusCodeS() throws Exception {
    DeliveredServiceDomain validDeliveredService =
        new DeliveredServiceResourceBuilder().setStatusCode("S").buildDeliveredServiceResource();

    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validDeliveredService, MediaType.APPLICATION_JSON));

    assertThat(response.getStatus(), is(equalTo(204)));
  }

  /*
   * supervisionCode test
   */
  @Test
  public void failWhenSupervisionCodeInValid() throws Exception {
    DeliveredServiceDomain validDeliveredService =
        new DeliveredServiceResourceBuilder().setSupervisionCode("Z")
            .buildDeliveredServiceResource();

    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validDeliveredService, MediaType.APPLICATION_JSON));

    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("supervisionCode must be one of [C, S, O, N]"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void successWhenSupervisionCodeC() throws Exception {
    DeliveredServiceDomain validDeliveredService =
        new DeliveredServiceResourceBuilder().setSupervisionCode("C")
            .buildDeliveredServiceResource();

    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validDeliveredService, MediaType.APPLICATION_JSON));

    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void successWhenSupervisionCodeS() throws Exception {
    DeliveredServiceDomain validDeliveredService =
        new DeliveredServiceResourceBuilder().setSupervisionCode("S")
            .buildDeliveredServiceResource();

    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validDeliveredService, MediaType.APPLICATION_JSON));

    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void successWhenSupervisionCodeO() throws Exception {
    DeliveredServiceDomain validDeliveredService =
        new DeliveredServiceResourceBuilder().setSupervisionCode("O")
            .buildDeliveredServiceResource();

    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validDeliveredService, MediaType.APPLICATION_JSON));

    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void successWhenSupervisionCodeN() throws Exception {
    DeliveredServiceDomain validDeliveredService =
        new DeliveredServiceResourceBuilder().setSupervisionCode("N")
            .buildDeliveredServiceResource();

    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validDeliveredService, MediaType.APPLICATION_JSON));

    assertThat(response.getStatus(), is(equalTo(204)));
  }

  /*
   * wraparoundServiceIndicator test
   */
  @Test
  public void failWhenWraparoundServiceIndicatorNull() throws Exception {
    DeliveredServiceDomain validDeliveredService =
        new DeliveredServiceResourceBuilder().setWraparoundServiceIndicator(null)
            .buildDeliveredServiceResource();

    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validDeliveredService, MediaType.APPLICATION_JSON));

    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("wraparoundServiceIndicator may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

}
