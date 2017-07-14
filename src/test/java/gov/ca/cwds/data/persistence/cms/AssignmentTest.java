package gov.ca.cwds.data.persistence.cms;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import java.io.IOException;

import org.junit.Ignore;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

@SuppressWarnings("javadoc")
public class AssignmentTest {

  private static final ObjectMapper MAPPER = SystemCodeTestHarness.MAPPER;

  @Test
  public void testEmptyConstructor() throws Exception {
    assertThat(Assignment.class.newInstance(), is(notNullValue()));
  }

  @Test
  public void testConstructorUsingDomain() throws Exception {

    Assignment va = validAssignment();

    Assignment pa = new Assignment(va.getId(), va.getCountySpecificCode(), va.getEndDate(),
        va.getEndTime(), va.getEstablishedForCode(), va.getEstablishedForId(), va.getFkCaseLoad(),
        va.getFkOutOfStateContactParty(), va.getResponsibilityDescription(),
        va.getSecondaryAssignmentRoleType(), va.getStartDate(), va.getStartTime(),
        va.getTypeOfAssignmentCode(), va.getWeightingNumber());

    // assertThat(pa.getId(), is(equalTo(id)));
    assertThat(pa.getCountySpecificCode(), is(equalTo(va.getCountySpecificCode())));
    assertThat(pa.getEndDate(), is(equalTo(va.getEndDate())));
    assertThat(pa.getEndTime(), is(equalTo(va.getEndTime())));
    assertThat(pa.getEstablishedForCode(), is(equalTo(va.getEstablishedForCode())));
    assertThat(pa.getEstablishedForId(), is(equalTo(va.getEstablishedForId())));
    assertThat(pa.getFkCaseLoad(), is(equalTo(va.getFkCaseLoad())));
    assertThat(pa.getFkOutOfStateContactParty(), is(equalTo(va.getFkOutOfStateContactParty())));
    assertThat(pa.getResponsibilityDescription(), is(equalTo(va.getResponsibilityDescription())));
    assertThat(pa.getSecondaryAssignmentRoleType(),
        is(equalTo(va.getSecondaryAssignmentRoleType())));
    assertThat(pa.getStartDate(), is(equalTo(va.getStartDate())));
    assertThat(pa.getTypeOfAssignmentCode(), is(equalTo(va.getTypeOfAssignmentCode())));
    assertThat(pa.getWeightingNumber(), is(equalTo(va.getWeightingNumber())));
  }

  @Test
  @Ignore
  public void testEqualsHashCodeWorks() {
    EqualsVerifier.forClass(Assignment.class).suppress(Warning.NONFINAL_FIELDS).verify();
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
