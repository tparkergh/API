package gov.ca.cwds.rest.api.domain.cms;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

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
import gov.ca.cwds.rest.resources.cms.AssignmentResource;
import io.dropwizard.jackson.Jackson;
import io.dropwizard.testing.junit.ResourceTestRule;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

/**
 * @author CWDS API Team
 *
 */
public class AssignmentTest {

  private static final String ROOT_RESOURCE = "/" + Api.RESOURCE_ASSIGNMENT + "/";
  private static final AssignmentResource mockedAssignmentResource = mock(AssignmentResource.class);

  @SuppressWarnings("javadoc")
  @After
  public void ensureServiceLocatorPopulated() {
    JerseyGuiceUtils.reset();
  }

  @SuppressWarnings("javadoc")
  @ClassRule

  public static final ResourceTestRule resources =
      ResourceTestRule.builder().addResource(mockedAssignmentResource).build();

  private final static DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
  private final static DateFormat timeOnlyFormat = new SimpleDateFormat("HH:mm:ss");
  private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

  private String countySpecificCode = "20";
  private String endDate = "2018-06-01";
  private String endTime = "12:01:00";
  private String establishedForCode = "R";
  private String establishedForId = "12345678ABC";
  private String caseLoadId = "2345678ABC";
  private String outOfStatePartyContactId = "";
  private String id = "3456789ABC";
  private String responsiblityDescription = "Assignment responsibility description";
  private Short secondaryAssignmentRoleType = 0;
  private String startDate = "2017-06-20";
  private String startTime = "16:41:49";
  private String typeOfAssignmentCode = "P";
  private String weightingNumber = "0";
  private String staffId = "0X5";

  private Assignment validAssignment = validAssignment();

  /**
   * 
   */
  @Before
  public void setup() {
    when(mockedAssignmentResource.create(eq(validAssignment)))
        .thenReturn(Response.status(Response.Status.NO_CONTENT).entity(null).build());
  }

  @SuppressWarnings("javadoc")
  @Test
  public void equalsHashCodeWorks() {
    EqualsVerifier.forClass(Assignment.class).suppress(Warning.NONFINAL_FIELDS)
        .withIgnoredFields("messages").verify();

  }

  @SuppressWarnings("javadoc")
  @Test
  public void deserializesFromJson() throws Exception {
    assertThat(MAPPER.readValue(fixture("fixtures/domain/legacy/Assignment/valid/valid.json"),
        Assignment.class), is(equalTo(validAssignment())));

  }

  @Test
  public void testWithValidSuccess() throws Exception {
    Assignment toCreate = MAPPER
        .readValue(fixture("fixtures/domain/legacy/Assignment/valid/valid.json"), Assignment.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void testDomainConstructorSuccess() throws Exception {

    Assignment da = validAssignment();

    assertThat(da.getCountySpecificCode(), is(equalTo(countySpecificCode)));
    assertThat(da.getEndDate(), is(equalTo(endDate)));
    assertThat(da.getEndTime(), is(equalTo(endTime)));
    assertThat(da.getEstablishedForCode(), is(equalTo(establishedForCode)));
    assertThat(da.getEstablishedForId(), is(equalTo(establishedForId)));
    assertThat(da.getCaseLoadId(), is(equalTo(caseLoadId)));
    assertThat(da.getOutOfStateContactId(), is(equalTo(outOfStatePartyContactId)));
    assertThat(da.getResponsibilityDescription(), is(equalTo(responsiblityDescription)));
    assertThat(da.getSecondaryAssignmentRoleType(), is(equalTo(secondaryAssignmentRoleType)));
    assertThat(da.getStartDate(), is(equalTo(startDate)));
    assertThat(da.getStartTime(), is(equalTo(startTime)));
    assertThat(da.getTypeOfAssignmentCode(), is(equalTo(typeOfAssignmentCode)));
    assertThat(da.getWeightingNumber(), is(equalTo(weightingNumber)));

  }

  @Test
  public void testPersistentObjectConstructorSuccess() throws Exception {

    Assignment vda = validAssignment();

    gov.ca.cwds.data.persistence.cms.Assignment pa =
        new gov.ca.cwds.data.persistence.cms.Assignment(id, vda, staffId);

    Assignment pc = new Assignment(pa);

    assertThat(pc.getCountySpecificCode(), is(equalTo(pa.getCountySpecificCode())));
    assertThat(pc.getEndDate(), is(equalTo(df.format(pa.getEndDate()))));
    assertThat(pc.getEndTime(), is(equalTo(timeOnlyFormat.format(pa.getEndTime()))));
    assertThat(pc.getEstablishedForCode(), is(equalTo(pa.getEstablishedForCode())));
    assertThat(pc.getEstablishedForId(), is(equalTo(pa.getEstablishedForId())));
    assertThat(pc.getCaseLoadId(), is(equalTo(pa.getFkCaseLoad())));
    assertThat(pc.getOutOfStateContactId(), is(equalTo(pa.getFkOutOfStateContactParty())));
    assertThat(pc.getResponsibilityDescription(), is(equalTo(pa.getResponsibilityDescription())));
    assertThat(pc.getSecondaryAssignmentRoleType(),
        is(equalTo(pa.getSecondaryAssignmentRoleType())));
    assertThat(pc.getStartDate(), is(equalTo(df.format(pa.getStartDate()))));
    assertThat(pc.getStartTime(), is(equalTo(timeOnlyFormat.format(pa.getStartTime()))));
  }

  private Assignment validAssignment() {
    Assignment validAssignment = new Assignment(countySpecificCode, endDate, endTime,
        establishedForCode, establishedForId, caseLoadId, outOfStatePartyContactId,
        responsiblityDescription, secondaryAssignmentRoleType, startDate, startTime,
        typeOfAssignmentCode, weightingNumber);
    return validAssignment;
  }

}
