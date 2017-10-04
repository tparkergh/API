package gov.ca.cwds.data.persistence.cms;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import java.io.IOException;
import java.util.Date;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import gov.ca.cwds.fixture.AssignmentEntityBuilder;
import gov.ca.cwds.fixture.AssignmentResourceBuilder;
import gov.ca.cwds.rest.api.domain.DomainChef;

@SuppressWarnings("javadoc")
public class AssignmentTest {

  private String id = "SlCAr46088";
  private String lastUpdatedId = "0X5";
  private Date lastUpdatedTime = new Date();

  private static final ObjectMapper MAPPER = SystemCodeTestHarness.MAPPER;

  @Test
  public void testEmptyConstructor() throws Exception {
    assertThat(Assignment.class.newInstance(), is(notNullValue()));
  }

  @Test
  public void testPersistentConstructor() throws Exception {

    Assignment validPersistent = new AssignmentEntityBuilder().build();

    Assignment persistent = new Assignment(id, validPersistent.getCountySpecificCode(),
        validPersistent.getEndDate(), validPersistent.getEndTime(),
        validPersistent.getEstablishedForCode(), validPersistent.getEstablishedForId(),
        validPersistent.getFkCaseLoad(), validPersistent.getFkOutOfStateContactParty(),
        validPersistent.getResponsibilityDescription(),
        validPersistent.getSecondaryAssignmentRoleType(), validPersistent.getStartDate(),
        validPersistent.getStartTime(), validPersistent.getTypeOfAssignmentCode(),
        validPersistent.getWeightingNumber());

    assertThat(persistent.getId(), is(equalTo(id)));
    assertThat(persistent.getCountySpecificCode(),
        is(equalTo(validPersistent.getCountySpecificCode())));
    assertThat(persistent.getEndDate(), is(equalTo(validPersistent.getEndDate())));
    assertThat(persistent.getEndTime(), is(equalTo(validPersistent.getEndTime())));
    assertThat(persistent.getEstablishedForCode(),
        is(equalTo(validPersistent.getEstablishedForCode())));
    assertThat(persistent.getEstablishedForId(),
        is(equalTo(validPersistent.getEstablishedForId())));
    assertThat(persistent.getFkCaseLoad(), is(equalTo(validPersistent.getFkCaseLoad())));
    assertThat(persistent.getFkOutOfStateContactParty(),
        is(equalTo(validPersistent.getFkOutOfStateContactParty())));
    assertThat(persistent.getResponsibilityDescription(),
        is(equalTo(validPersistent.getResponsibilityDescription())));
    assertThat(persistent.getSecondaryAssignmentRoleType(),
        is(equalTo(validPersistent.getSecondaryAssignmentRoleType())));
    assertThat(persistent.getStartDate(), is(equalTo(validPersistent.getStartDate())));
    assertThat(persistent.getTypeOfAssignmentCode(),
        is(equalTo(validPersistent.getTypeOfAssignmentCode())));
    assertThat(persistent.getWeightingNumber(), is(equalTo(validPersistent.getWeightingNumber())));
  }

  @Test
  public void testConstructorUsingDomain() throws Exception {

    gov.ca.cwds.rest.api.domain.cms.Assignment validDomain =
        new AssignmentResourceBuilder().buildAssignment();

    Assignment persistent = new Assignment(id, validDomain, lastUpdatedId);

    assertThat(persistent.getId(), is(equalTo(id)));
    assertThat(persistent.getCountySpecificCode(),
        is(equalTo(validDomain.getCountySpecificCode())));
    assertThat(persistent.getEndDate(),
        is(equalTo(DomainChef.uncookDateString(validDomain.getEndDate()))));
    assertThat(persistent.getEndTime(),
        is(equalTo(DomainChef.uncookTimeString(validDomain.getEndTime()))));
    assertThat(persistent.getEstablishedForCode(),
        is(equalTo(validDomain.getEstablishedForCode())));
    assertThat(persistent.getEstablishedForId(), is(equalTo(validDomain.getEstablishedForId())));
    assertThat(persistent.getFkCaseLoad(), is(equalTo(validDomain.getCaseLoadId())));
    assertThat(persistent.getFkOutOfStateContactParty(),
        is(equalTo(validDomain.getOutOfStateContactId())));
    assertThat(persistent.getResponsibilityDescription(),
        is(equalTo(validDomain.getResponsibilityDescription())));
    assertThat(persistent.getSecondaryAssignmentRoleType(),
        is(equalTo(validDomain.getSecondaryAssignmentRoleType())));
    assertThat(persistent.getStartDate(),
        is(equalTo(DomainChef.uncookDateString(validDomain.getStartDate()))));
    assertThat(persistent.getStartTime(),
        is(equalTo(DomainChef.uncookTimeString(validDomain.getStartTime()))));
    assertThat(persistent.getTypeOfAssignmentCode(),
        is(equalTo(validDomain.getTypeOfAssignmentCode())));
    assertThat(persistent.getWeightingNumber(), is(equalTo(validDomain.getWeightingNumber())));
  }

  @Test
  public void testSerializeAndDeserialize() throws Exception {
    Assignment va = validAssignment();

    Assignment pa = new Assignment(va.getId(), va.getCountySpecificCode(), va.getEndDate(),
        va.getEndTime(), va.getEstablishedForCode(), va.getEstablishedForId(), va.getFkCaseLoad(),
        va.getFkOutOfStateContactParty(), va.getResponsibilityDescription(),
        va.getSecondaryAssignmentRoleType(), va.getStartDate(), va.getStartTime(),
        va.getTypeOfAssignmentCode(), va.getWeightingNumber());

    final String expected = MAPPER.writeValueAsString((MAPPER.readValue(
        fixture("fixtures/persistent/cms/Assignment/valid/valid.json"), Assignment.class)));

    assertThat(MAPPER.writeValueAsString(pa)).isEqualTo(expected);
  }

  private Assignment validAssignment()
      throws JsonParseException, JsonMappingException, IOException {

    Assignment validAssignment = MAPPER.readValue(
        fixture("fixtures/persistent/cms/Assignment/valid/valid.json"), Assignment.class);
    return validAssignment;
  }
}
