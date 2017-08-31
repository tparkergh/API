package gov.ca.cwds.data.persistence.cms;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import org.junit.Test;

import gov.ca.cwds.fixture.CaseLoadEntityBuilder;

/**
 * @author CWDS API Team
 *
 */
public class CaseLoadTest {

  private String id = "1234567ABC";

  /**
   * Constructor test
   * 
   * @throws Exception - exception
   */
  @Test
  public void testEmptyConstructor() throws Exception {
    assertThat(CaseLoad.class.newInstance(), is(notNullValue()));
  }


  /**
   * persistent Constructor test
   * 
   * @throws Exception - exception
   */
  @Test
  public void testPersistentConstructor() throws Exception {

    CaseLoad validCaseLoad = new CaseLoadEntityBuilder().build();

    CaseLoad persistent = new CaseLoad(id, validCaseLoad.getArchiveAssociationIndicator(),
        validCaseLoad.getCountySpecificCode(), validCaseLoad.getCaseLoadIndicatorVariable(),
        validCaseLoad.getFkAssignmentUnit(), validCaseLoad.getStartDate(),
        validCaseLoad.getOnHoldIndicator(), validCaseLoad.getIdentifierName(),
        validCaseLoad.getEndDate(), validCaseLoad.getCeilingNumber(),
        validCaseLoad.getAssignmentDeskCaseLoadIndicator());

    assertThat(persistent.getId(), is(equalTo(id)));
    assertThat(persistent.getArchiveAssociationIndicator(),
        is(equalTo(validCaseLoad.getArchiveAssociationIndicator())));
    assertThat(persistent.getCountySpecificCode(),
        is(equalTo(validCaseLoad.getCountySpecificCode())));
    assertThat(persistent.getCaseLoadIndicatorVariable(),
        is(equalTo(validCaseLoad.getCaseLoadIndicatorVariable())));
    assertThat(persistent.getFkAssignmentUnit(), is(equalTo(validCaseLoad.getFkAssignmentUnit())));
    assertThat(persistent.getStartDate(), is(equalTo(validCaseLoad.getStartDate())));
    assertThat(persistent.getOnHoldIndicator(), is(equalTo(validCaseLoad.getOnHoldIndicator())));
    assertThat(persistent.getIdentifierName(), is(equalTo(validCaseLoad.getIdentifierName())));
    assertThat(persistent.getEndDate(), is(equalTo(validCaseLoad.getEndDate())));
    assertThat(persistent.getCeilingNumber(), is(equalTo(validCaseLoad.getCeilingNumber())));
    assertThat(persistent.getAssignmentDeskCaseLoadIndicator(),
        is(equalTo(validCaseLoad.getAssignmentDeskCaseLoadIndicator())));
  }

}
