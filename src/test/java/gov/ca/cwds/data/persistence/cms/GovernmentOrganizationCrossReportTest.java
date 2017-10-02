package gov.ca.cwds.data.persistence.cms;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import java.util.Date;

import org.junit.Test;

import gov.ca.cwds.fixture.GovernmentOrganizationCrossReportResourceBuilder;

/**
 * @author CWDS API Team
 *
 */
public class GovernmentOrganizationCrossReportTest {

  private String thirdId = "AbalBln0Ki";
  private String lastUpdatedId = "0X5";
  private Date lastUpdatedTime = new Date();

  /**
   * Constructor test
   * 
   * @throws Exception general error
   */
  @Test
  public void testEmptyConstructor() throws Exception {
    assertThat(GovernmentOrganizationCrossReport.class.newInstance(), is(notNullValue()));
  }

  /**
   * persistent constructor test
   * 
   * @throws Exception general error
   */
  @Test
  public void testPersistentConstructor() throws Exception {
    gov.ca.cwds.rest.api.domain.cms.GovernmentOrganizationCrossReport validGovernmentOrganizationCrossReport =
        new GovernmentOrganizationCrossReportResourceBuilder().build();

    gov.ca.cwds.data.persistence.cms.GovernmentOrganizationCrossReport persistent =
        new gov.ca.cwds.data.persistence.cms.GovernmentOrganizationCrossReport(
            validGovernmentOrganizationCrossReport.getThirdId(),
            validGovernmentOrganizationCrossReport.getCountySpecificCode(),
            validGovernmentOrganizationCrossReport.getCrossReportThirdId(),
            validGovernmentOrganizationCrossReport.getReferralId(),
            validGovernmentOrganizationCrossReport.getGovernmentOrganizationId(),
            validGovernmentOrganizationCrossReport.getOrganizationTypeInd());

    assertThat(persistent.getThirdId(), is(equalTo(thirdId)));
    assertThat(persistent.getCountySpecificCode(), is(equalTo(persistent.getCountySpecificCode())));
    assertThat(persistent.getCrossReportThirdId(), is(equalTo(persistent.getCrossReportThirdId())));
    assertThat(persistent.getReferralId(), is(equalTo(persistent.getReferralId())));
    assertThat(persistent.getGovernmentOrganizationId(),
        is(equalTo(persistent.getGovernmentOrganizationId())));
    assertThat(persistent.getOrganizationTypeInd(),
        is(equalTo(persistent.getOrganizationTypeInd())));
  }

  /**
   * domain constructor test
   * 
   * @throws Exception general error
   */
  @Test
  public void testConstructorUsingDomain() throws Exception {
    gov.ca.cwds.rest.api.domain.cms.GovernmentOrganizationCrossReport domainGovernmentOrganizationCrossReport =
        new GovernmentOrganizationCrossReportResourceBuilder().build();

    GovernmentOrganizationCrossReport persistent = new GovernmentOrganizationCrossReport(thirdId,
        domainGovernmentOrganizationCrossReport, lastUpdatedId, lastUpdatedTime);

    assertThat(persistent.getThirdId(), is(equalTo(thirdId)));
    assertThat(persistent.getCountySpecificCode(),
        is(equalTo(domainGovernmentOrganizationCrossReport.getCountySpecificCode())));
    assertThat(persistent.getCrossReportThirdId(),
        is(equalTo(domainGovernmentOrganizationCrossReport.getCrossReportThirdId())));
    assertThat(persistent.getReferralId(),
        is(equalTo(domainGovernmentOrganizationCrossReport.getReferralId())));
    assertThat(persistent.getGovernmentOrganizationId(),
        is(equalTo(domainGovernmentOrganizationCrossReport.getGovernmentOrganizationId())));
    assertThat(persistent.getOrganizationTypeInd(),
        is(equalTo(domainGovernmentOrganizationCrossReport.getOrganizationTypeInd())));
    assertThat(persistent.getLastUpdatedId(), is(equalTo(lastUpdatedId)));
  }

}
