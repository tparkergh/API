package gov.ca.cwds.data.persistence.cms;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.junit.Test;

import gov.ca.cwds.data.persistence.junit.template.PersistentTestTemplate;
import gov.ca.cwds.fixture.ChildClientEntityBuilder;
import gov.ca.cwds.fixture.ChildClientResourceBuilder;
import gov.ca.cwds.rest.api.domain.DomainChef;

/**
 * @author CWDS API Team
 *
 */
public class ChildClientTest implements PersistentTestTemplate {
  private final static DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

  private String lastUpdatedId = "ABC";

  /*
   * Constructor test
   */
  @Override
  @Test
  public void testEmptyConstructor() throws Exception {
    assertThat(ChildClient.class.newInstance(), is(notNullValue()));
  }

  @Override
  @Test
  public void testConstructorUsingDomain() throws Exception {

    gov.ca.cwds.rest.api.domain.cms.ChildClient domain =
        new ChildClientResourceBuilder().buildChildClient();

    ChildClient persistent = new ChildClient(domain.getVictimClientId(), domain, lastUpdatedId);

    assertThat(persistent.getVictimClientId(), is(equalTo(domain.getVictimClientId())));
    assertThat(persistent.getAdoptableCode(), is(equalTo(domain.getAdoptableCode())));
    assertThat(persistent.getAdoptedAge(), is(equalTo(domain.getAdoptedAge())));
    assertThat(persistent.getAfdcFcEligibilityIndicatorVar(),
        is(equalTo(DomainChef.cookBoolean(domain.getAfdcFcEligibilityIndicatorVar()))));
    assertThat(persistent.getAllEducationInfoOnFileIndicator(),
        is(equalTo(DomainChef.cookBoolean(domain.getAllEducationInfoOnFileIndicator()))));
    assertThat(persistent.getAllHealthInfoOnFileIndicator(),
        is(equalTo(DomainChef.cookBoolean(domain.getAllHealthInfoOnFileIndicator()))));
    assertThat(persistent.getAttemptToAcquireEducInfoDesc(),
        is(equalTo(domain.getAttemptToAcquireEducInfoDesc())));
    assertThat(persistent.getAttemptToAcquireHlthInfoDesc(),
        is(equalTo(domain.getAttemptToAcquireHlthInfoDesc())));
    assertThat(persistent.getAwolAbductedCode(), is(equalTo(domain.getAwolAbductedCode())));
    assertThat(persistent.getBirthHistoryIndicatorVar(),
        is(equalTo(DomainChef.cookBoolean(domain.getBirthHistoryIndicatorVar()))));
    assertThat(persistent.getChildIndianAncestryIndicator(),
        is(equalTo(DomainChef.cookBoolean(domain.getChildIndianAncestryIndicator()))));
    assertThat(persistent.getCollegeIndicator(),
        is(equalTo(DomainChef.cookBoolean(domain.getCollegeIndicator()))));
    assertThat(persistent.getCurrentCaseId(), is(equalTo(domain.getCurrentCaseId())));
    assertThat(persistent.getDeathCircumstancesType(),
        is(equalTo(domain.getDeathCircumstancesType())));
    assertThat(persistent.getDisabilityDiagnosedCode(),
        is(equalTo(domain.getDisabilityDiagnosedCode())));
    assertThat(persistent.getDrmsHePassportDocOld(), is(equalTo(domain.getDrmsHePassportDocOld())));
    assertThat(persistent.getDrmsHealthEducPassportDoc(),
        is(equalTo(domain.getDrmsHealthEducPassportDoc())));
    assertThat(persistent.getDrmsVoluntaryPlcmntAgrmntDoc(),
        is(equalTo(domain.getDrmsVoluntaryPlcmntAgrmntDoc())));
    assertThat(persistent.getFc2EligApplicationIndicatorVar(),
        is(equalTo(DomainChef.cookBoolean(domain.getFc2EligApplicationIndicatorVar()))));
    assertThat(persistent.getFoodStampsApplicationDate(),
        is(equalTo(df.parse(domain.getFoodStampsApplicationDate()))));
    assertThat(persistent.getFoodStampsApplicationIndicator(),
        is(equalTo(DomainChef.cookBoolean(domain.getFoodStampsApplicationIndicator()))));
    assertThat(persistent.getIcwaEligibilityCode(), is(equalTo(domain.getIcwaEligibilityCode())));
    assertThat(persistent.getIntercountryAdoptDisruptedIndicator(),
        is(equalTo(DomainChef.cookBoolean(domain.getIntercountryAdoptDisruptedIndicator()))));
    assertThat(persistent.getIntercountryAdoptDissolvedIndicator(),
        is(equalTo(DomainChef.cookBoolean(domain.getIntercountryAdoptDissolvedIndicator()))));
    assertThat(persistent.getMedEligibilityApplicationIndicatorVar(),
        is(equalTo(DomainChef.cookBoolean(domain.getMedEligibilityApplicationIndicatorVar()))));
    assertThat(persistent.getMinorNmdParentIndicator(),
        is(equalTo(DomainChef.cookBoolean(domain.getMinorNmdParentIndicator()))));
    assertThat(persistent.getParentalRightsLimitedIndicator(),
        is(equalTo(DomainChef.cookBoolean(domain.getParentalRightsLimitedIndicator()))));
    assertThat(persistent.getParentalRightsTermintnIndicatorVar(),
        is(equalTo(DomainChef.cookBoolean(domain.getParentalRightsTermintnIndicatorVar()))));
    assertThat(persistent.getPaternityIndividualIndicatorVar(),
        is(equalTo(DomainChef.cookBoolean(domain.getPaternityIndividualIndicatorVar()))));
    assertThat(persistent.getPostsecVocIndicator(),
        is(equalTo(DomainChef.cookBoolean(domain.getPostsecVocIndicator()))));
    assertThat(persistent.getPreviouslyAdoptedCode(),
        is(equalTo(domain.getPreviouslyAdoptedCode())));
    assertThat(persistent.getSafelySurrendedBabiesIndicatorVar(),
        is(equalTo(DomainChef.cookBoolean(domain.getSafelySurrendedBabiesIndicatorVar()))));
    assertThat(persistent.getSaw1EligApplicationIndicatorVar(),
        is(equalTo(DomainChef.cookBoolean(domain.getSaw1EligApplicationIndicatorVar()))));
    assertThat(persistent.getSawsCaseSerialNumber(), is(equalTo(domain.getSawsCaseSerialNumber())));
    assertThat(persistent.getSijsScheduledInterviewDate(),
        is(equalTo(df.parse(domain.getSijsScheduledInterviewDate()))));
    assertThat(persistent.getSiiNextScreeningDueDate(),
        is(equalTo(df.parse(domain.getSiiNextScreeningDueDate()))));
    assertThat(persistent.getSsiSspApplicationIndicator(),
        is(equalTo(DomainChef.cookBoolean(domain.getSsiSspApplicationIndicator()))));
    assertThat(persistent.getTribalAncestryNotifctnIndicatorVar(),
        is(equalTo(DomainChef.cookBoolean(domain.getTribalAncestryNotifctnIndicatorVar()))));
    assertThat(persistent.getTribalCustomaryAdoptionDate(),
        is(equalTo(df.parse(domain.getTribalCustomaryAdoptionDate()))));
    assertThat(persistent.getTribalCustomaryAdoptionIndicator(),
        is(equalTo(DomainChef.cookBoolean(domain.getTribalCustomaryAdoptionIndicator()))));
  }

  @Override
  @Test
  public void testPersistentConstructor() throws Exception {

    ChildClient vchc = new ChildClientEntityBuilder().build();
    ChildClient persistent = new ChildClient(vchc.getVictimClientId(), vchc.getAdoptableCode(),
        vchc.getAdoptedAge(), vchc.getAfdcFcEligibilityIndicatorVar(),
        vchc.getAllEducationInfoOnFileIndicator(), vchc.getAllHealthInfoOnFileIndicator(),
        vchc.getAttemptToAcquireEducInfoDesc(), vchc.getAttemptToAcquireHlthInfoDesc(),
        vchc.getAwolAbductedCode(), vchc.getBirthHistoryIndicatorVar(),
        vchc.getChildIndianAncestryIndicator(), vchc.getCollegeIndicator(), vchc.getCurrentCaseId(),
        vchc.getDeathCircumstancesType(), vchc.getDisabilityDiagnosedCode(),
        vchc.getDrmsHePassportDocOld(), vchc.getDrmsHealthEducPassportDoc(),
        vchc.getDrmsVoluntaryPlcmntAgrmntDoc(), vchc.getFc2EligApplicationIndicatorVar(),
        vchc.getFoodStampsApplicationDate(), vchc.getFoodStampsApplicationIndicator(),
        vchc.getIcwaEligibilityCode(), vchc.getIntercountryAdoptDisruptedIndicator(),
        vchc.getIntercountryAdoptDissolvedIndicator(),
        vchc.getMedEligibilityApplicationIndicatorVar(), vchc.getMinorNmdParentIndicator(),
        vchc.getParentalRightsLimitedIndicator(), vchc.getParentalRightsTermintnIndicatorVar(),
        vchc.getPaternityIndividualIndicatorVar(), vchc.getPostsecVocIndicator(),
        vchc.getPreviouslyAdoptedCode(), vchc.getSafelySurrendedBabiesIndicatorVar(),
        vchc.getSaw1EligApplicationIndicatorVar(), vchc.getSawsCaseSerialNumber(),
        vchc.getSijsScheduledInterviewDate(), vchc.getSiiNextScreeningDueDate(),
        vchc.getSsiSspApplicationIndicator(), vchc.getTribalAncestryNotifctnIndicatorVar(),
        vchc.getTribalCustomaryAdoptionDate(), vchc.getTribalCustomaryAdoptionIndicator());

    assertThat(persistent.getVictimClientId(), is(equalTo(persistent.getVictimClientId())));
    assertThat(persistent.getAdoptableCode(), is(equalTo(persistent.getAdoptableCode())));
    assertThat(persistent.getAdoptedAge(), is(equalTo(persistent.getAdoptedAge())));
    assertThat(persistent.getAfdcFcEligibilityIndicatorVar(),
        is(equalTo(persistent.getAfdcFcEligibilityIndicatorVar())));
    assertThat(persistent.getAllEducationInfoOnFileIndicator(),
        is(equalTo(persistent.getAllEducationInfoOnFileIndicator())));
    assertThat(persistent.getAllHealthInfoOnFileIndicator(),
        is(equalTo(persistent.getAllHealthInfoOnFileIndicator())));
    assertThat(persistent.getAttemptToAcquireEducInfoDesc(),
        is(equalTo(persistent.getAttemptToAcquireEducInfoDesc())));
    assertThat(persistent.getAttemptToAcquireHlthInfoDesc(),
        is(equalTo(persistent.getAttemptToAcquireHlthInfoDesc())));
    assertThat(persistent.getAwolAbductedCode(), is(equalTo(persistent.getAwolAbductedCode())));
    assertThat(persistent.getBirthHistoryIndicatorVar(),
        is(equalTo(persistent.getBirthHistoryIndicatorVar())));
    assertThat(persistent.getChildIndianAncestryIndicator(),
        is(equalTo(persistent.getChildIndianAncestryIndicator())));
    assertThat(persistent.getCollegeIndicator(), is(equalTo(persistent.getCollegeIndicator())));
    assertThat(persistent.getCurrentCaseId(), is(equalTo(persistent.getCurrentCaseId())));
    assertThat(persistent.getDeathCircumstancesType(),
        is(equalTo(persistent.getDeathCircumstancesType())));
    assertThat(persistent.getDisabilityDiagnosedCode(),
        is(equalTo(persistent.getDisabilityDiagnosedCode())));
    assertThat(persistent.getDrmsHePassportDocOld(),
        is(equalTo(persistent.getDrmsHePassportDocOld())));
    assertThat(persistent.getDrmsHealthEducPassportDoc(),
        is(equalTo(persistent.getDrmsHealthEducPassportDoc())));
    assertThat(persistent.getDrmsVoluntaryPlcmntAgrmntDoc(),
        is(equalTo(persistent.getDrmsVoluntaryPlcmntAgrmntDoc())));
    assertThat(persistent.getFc2EligApplicationIndicatorVar(),
        is(equalTo(persistent.getFc2EligApplicationIndicatorVar())));
    assertThat(persistent.getFoodStampsApplicationDate(),
        is(equalTo(persistent.getFoodStampsApplicationDate())));
    assertThat(persistent.getFoodStampsApplicationIndicator(),
        is(equalTo(persistent.getFoodStampsApplicationIndicator())));
    assertThat(persistent.getIcwaEligibilityCode(),
        is(equalTo(persistent.getIcwaEligibilityCode())));
    assertThat(persistent.getIntercountryAdoptDisruptedIndicator(),
        is(equalTo(persistent.getIntercountryAdoptDisruptedIndicator())));
    assertThat(persistent.getIntercountryAdoptDissolvedIndicator(),
        is(equalTo(persistent.getIntercountryAdoptDissolvedIndicator())));
    assertThat(persistent.getMedEligibilityApplicationIndicatorVar(),
        is(equalTo(persistent.getMedEligibilityApplicationIndicatorVar())));
    assertThat(persistent.getMinorNmdParentIndicator(),
        is(equalTo(persistent.getMinorNmdParentIndicator())));
    assertThat(persistent.getParentalRightsLimitedIndicator(),
        is(equalTo(persistent.getParentalRightsLimitedIndicator())));
    assertThat(persistent.getParentalRightsTermintnIndicatorVar(),
        is(equalTo(persistent.getParentalRightsTermintnIndicatorVar())));
    assertThat(persistent.getPaternityIndividualIndicatorVar(),
        is(equalTo(persistent.getPaternityIndividualIndicatorVar())));
    assertThat(persistent.getPostsecVocIndicator(),
        is(equalTo(persistent.getPostsecVocIndicator())));
    assertThat(persistent.getPreviouslyAdoptedCode(),
        is(equalTo(persistent.getPreviouslyAdoptedCode())));
    assertThat(persistent.getSafelySurrendedBabiesIndicatorVar(),
        is(equalTo(persistent.getSafelySurrendedBabiesIndicatorVar())));
    assertThat(persistent.getSaw1EligApplicationIndicatorVar(),
        is(equalTo(persistent.getSaw1EligApplicationIndicatorVar())));
    assertThat(persistent.getSawsCaseSerialNumber(),
        is(equalTo(persistent.getSawsCaseSerialNumber())));
    assertThat(persistent.getSijsScheduledInterviewDate(),
        is(equalTo(persistent.getSijsScheduledInterviewDate())));
    assertThat(persistent.getSiiNextScreeningDueDate(),
        is(equalTo(persistent.getSiiNextScreeningDueDate())));
    assertThat(persistent.getSsiSspApplicationIndicator(),
        is(equalTo(persistent.getSsiSspApplicationIndicator())));
    assertThat(persistent.getTribalAncestryNotifctnIndicatorVar(),
        is(equalTo(persistent.getTribalAncestryNotifctnIndicatorVar())));
    assertThat(persistent.getTribalCustomaryAdoptionDate(),
        is(equalTo(persistent.getTribalCustomaryAdoptionDate())));
    assertThat(persistent.getTribalCustomaryAdoptionIndicator(),
        is(equalTo(persistent.getTribalCustomaryAdoptionIndicator())));
  }

}
