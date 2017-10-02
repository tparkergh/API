package gov.ca.cwds.rest.api.domain.cms;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import java.util.Date;

import org.junit.After;
import org.junit.ClassRule;
import org.junit.Test;

import com.squarespace.jersey2.guice.JerseyGuiceUtils;

import gov.ca.cwds.fixture.GovernmentOrganizationCrossReportResourceBuilder;
import gov.ca.cwds.rest.resources.cms.JerseyGuiceRule;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

/**
 * @author CWDS API Team
 *
 */
public class GovernmentOrganizationCrossReportTest {

  @SuppressWarnings("javadoc")
  @After
  public void ensureServiceLocatorPopulated() {
    JerseyGuiceUtils.reset();
  }

  @SuppressWarnings("javadoc")
  @ClassRule
  public static JerseyGuiceRule rule = new JerseyGuiceRule();

  private String thirdId = "AbalBln0Ki";
  private String countySpecificCode = "99";
  private String crossReportThirdId = "LikGcFD0Ki";
  private String referralId = "RI1Wuve0Ki";
  private String governmentOrganizationId = "CS90ZBR01c";
  private String organizationTypeInd = "D";

  /**
   * persistent Constructor Test
   * 
   * @throws Exception - Exception
   */
  @Test
  public void persistentObjectConstructorTest() throws Exception {

    GovernmentOrganizationCrossReport domain =
        new GovernmentOrganizationCrossReportResourceBuilder().build();

    gov.ca.cwds.data.persistence.cms.GovernmentOrganizationCrossReport persistent =
        new gov.ca.cwds.data.persistence.cms.GovernmentOrganizationCrossReport(thirdId, domain,
            "lastUpdatedId", new Date());

    assertThat(domain.getThirdId(), is(equalTo(persistent.getThirdId())));
    assertThat(domain.getCountySpecificCode(), is(equalTo(persistent.getCountySpecificCode())));
    assertThat(domain.getCrossReportThirdId(), is(equalTo(persistent.getCrossReportThirdId())));
    assertThat(domain.getGovernmentOrganizationId(),
        is(equalTo(persistent.getGovernmentOrganizationId())));
    assertThat(domain.getReferralId(), is(equalTo(persistent.getReferralId())));
    assertThat(domain.getOrganizationTypeInd(), is(equalTo(persistent.getOrganizationTypeInd())));
  }

  /**
   * @throws Exception test standard test standard
   */
  @Test
  public void jsonCreatorConstructorTest() throws Exception {

    GovernmentOrganizationCrossReport domain =
        new GovernmentOrganizationCrossReport(thirdId, countySpecificCode, crossReportThirdId,
            referralId, governmentOrganizationId, organizationTypeInd);

    assertThat(domain.getThirdId(), is(equalTo(thirdId)));
    assertThat(domain.getCountySpecificCode(), is(equalTo(countySpecificCode)));
    assertThat(domain.getCrossReportThirdId(), is(equalTo(crossReportThirdId)));
    assertThat(domain.getGovernmentOrganizationId(), is(equalTo(governmentOrganizationId)));
    assertThat(domain.getReferralId(), is(equalTo(referralId)));
    assertThat(domain.getOrganizationTypeInd(), is(equalTo(organizationTypeInd)));
  }

  /**
   * 
   */
  @Test
  public void equalsHashCodeWork() {
    EqualsVerifier.forClass(GovernmentOrganizationCrossReport.class)
        .suppress(Warning.NONFINAL_FIELDS).verify();
  }

}
