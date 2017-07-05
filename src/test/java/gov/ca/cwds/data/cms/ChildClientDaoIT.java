package gov.ca.cwds.data.cms;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.IsEqual.equalTo;

import java.io.IOException;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.hamcrest.junit.ExpectedException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import gov.ca.cwds.data.junit.template.DaoTestTemplate;
import gov.ca.cwds.data.persistence.cms.ChildClient;

/**
 * 
 * @author CWDS API Team
 */
public class ChildClientDaoIT implements DaoTestTemplate {

  private static SessionFactory sessionFactory;
  private static ChildClientDao childClientDao;
  private Session session;

  /*
   * id matches src/main/resources/db.cms/ci-seeds.sql
   */
  private String victimId = "AazXkWY06s";

  @SuppressWarnings("javadoc")
  @Rule
  public ExpectedException thrown = ExpectedException.none();

  /**
   * 
   */
  @BeforeClass
  public static void beforeClass() {
    sessionFactory = new Configuration().configure().buildSessionFactory();
    childClientDao = new ChildClientDao(sessionFactory);
  }

  /**
   * 
   */
  @AfterClass
  public static void afterClass() {
    sessionFactory.close();
  }

  @Override
  @Before
  public void setup() {
    session = sessionFactory.getCurrentSession();
    session.beginTransaction();
  }

  @Override
  @After
  public void teardown() {
    session.getTransaction().rollback();
  }

  /**
   * Find JUnit test
   */
  @Override
  @Test
  public void testFind() throws Exception {
    ChildClient found = childClientDao.find(victimId);
    assertThat(found.getVictimClientId(), is(equalTo(victimId)));
  }

  @Override
  @Test
  public void testFindEntityNotFoundException() throws Exception {
    ChildClient found = childClientDao.find("9999999ZZZ");
    assertThat(found, is(nullValue()));
  }

  /**
   * Create JUnit test
   */
  @Override
  @Test
  public void testCreate() throws Exception {
    ChildClient vchc = validChildClient();

    ChildClient childClient = new ChildClient("1234567ABC", vchc.getAdoptableCode(),
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

    ChildClient create = childClientDao.create(childClient);
    assertThat(childClient, is(create));
  }

  @Override
  @Test
  public void testCreateExistingEntityException() throws Exception {
    thrown.expect(EntityExistsException.class);
    ChildClient vchc = validChildClient();
    ChildClient pers = new ChildClient("AazXkWY06s", vchc.getAdoptableCode(), vchc.getAdoptedAge(),
        vchc.getAfdcFcEligibilityIndicatorVar(), vchc.getAllEducationInfoOnFileIndicator(),
        vchc.getAllHealthInfoOnFileIndicator(), vchc.getAttemptToAcquireEducInfoDesc(),
        vchc.getAttemptToAcquireHlthInfoDesc(), vchc.getAwolAbductedCode(),
        vchc.getBirthHistoryIndicatorVar(), vchc.getChildIndianAncestryIndicator(),
        vchc.getCollegeIndicator(), vchc.getCurrentCaseId(), vchc.getDeathCircumstancesType(),
        vchc.getDisabilityDiagnosedCode(), vchc.getDrmsHePassportDocOld(),
        vchc.getDrmsHealthEducPassportDoc(), vchc.getDrmsVoluntaryPlcmntAgrmntDoc(),
        vchc.getFc2EligApplicationIndicatorVar(), vchc.getFoodStampsApplicationDate(),
        vchc.getFoodStampsApplicationIndicator(), vchc.getIcwaEligibilityCode(),
        vchc.getIntercountryAdoptDisruptedIndicator(),
        vchc.getIntercountryAdoptDissolvedIndicator(),
        vchc.getMedEligibilityApplicationIndicatorVar(), vchc.getMinorNmdParentIndicator(),
        vchc.getParentalRightsLimitedIndicator(), vchc.getParentalRightsTermintnIndicatorVar(),
        vchc.getPaternityIndividualIndicatorVar(), vchc.getPostsecVocIndicator(),
        vchc.getPreviouslyAdoptedCode(), vchc.getSafelySurrendedBabiesIndicatorVar(),
        vchc.getSaw1EligApplicationIndicatorVar(), vchc.getSawsCaseSerialNumber(),
        vchc.getSijsScheduledInterviewDate(), vchc.getSiiNextScreeningDueDate(),
        vchc.getSsiSspApplicationIndicator(), vchc.getTribalAncestryNotifctnIndicatorVar(),
        vchc.getTribalCustomaryAdoptionDate(), vchc.getTribalCustomaryAdoptionIndicator());

    childClientDao.create(pers);
  }

  /**
   * Delete JUnit test
   */
  @Override
  @Test
  public void testDelete() throws Exception {
    ChildClient deleted = childClientDao.delete(victimId);
    assertThat(deleted.getVictimClientId(), is(victimId));
  }

  @Override
  @Test
  public void testDeleteEntityNotFoundException() throws Exception {
    ChildClient deleted = childClientDao.delete("9999999ZZZ");
    assertThat(deleted, is(nullValue()));
  }

  @Override
  @Test
  public void testUpdate() throws Exception {
    ChildClient vchc = validChildClient();
    ChildClient pers = new ChildClient("AazXkWY06s", vchc.getAdoptableCode(), vchc.getAdoptedAge(),
        vchc.getAfdcFcEligibilityIndicatorVar(), vchc.getAllEducationInfoOnFileIndicator(),
        vchc.getAllHealthInfoOnFileIndicator(), vchc.getAttemptToAcquireEducInfoDesc(),
        vchc.getAttemptToAcquireHlthInfoDesc(), vchc.getAwolAbductedCode(),
        vchc.getBirthHistoryIndicatorVar(), vchc.getChildIndianAncestryIndicator(),
        vchc.getCollegeIndicator(), vchc.getCurrentCaseId(), vchc.getDeathCircumstancesType(),
        vchc.getDisabilityDiagnosedCode(), vchc.getDrmsHePassportDocOld(),
        vchc.getDrmsHealthEducPassportDoc(), vchc.getDrmsVoluntaryPlcmntAgrmntDoc(),
        vchc.getFc2EligApplicationIndicatorVar(), vchc.getFoodStampsApplicationDate(),
        vchc.getFoodStampsApplicationIndicator(), vchc.getIcwaEligibilityCode(),
        vchc.getIntercountryAdoptDisruptedIndicator(),
        vchc.getIntercountryAdoptDissolvedIndicator(),
        vchc.getMedEligibilityApplicationIndicatorVar(), vchc.getMinorNmdParentIndicator(),
        vchc.getParentalRightsLimitedIndicator(), vchc.getParentalRightsTermintnIndicatorVar(),
        vchc.getPaternityIndividualIndicatorVar(), vchc.getPostsecVocIndicator(),
        vchc.getPreviouslyAdoptedCode(), vchc.getSafelySurrendedBabiesIndicatorVar(),
        vchc.getSaw1EligApplicationIndicatorVar(), vchc.getSawsCaseSerialNumber(),
        vchc.getSijsScheduledInterviewDate(), vchc.getSiiNextScreeningDueDate(),
        vchc.getSsiSspApplicationIndicator(), vchc.getTribalAncestryNotifctnIndicatorVar(),
        vchc.getTribalCustomaryAdoptionDate(), vchc.getTribalCustomaryAdoptionIndicator());
    ChildClient updated = childClientDao.update(pers);
    assertThat(updated, is(pers));
  }

  @Override
  @Test
  public void testUpdateEntityNotFoundException() throws Exception {
    thrown.expect(EntityNotFoundException.class);
    ChildClient vchc = validChildClient();
    ChildClient pers = new ChildClient("AbNNjTK0P1", vchc.getAdoptableCode(), vchc.getAdoptedAge(),
        vchc.getAfdcFcEligibilityIndicatorVar(), vchc.getAllEducationInfoOnFileIndicator(),
        vchc.getAllHealthInfoOnFileIndicator(), vchc.getAttemptToAcquireEducInfoDesc(),
        vchc.getAttemptToAcquireHlthInfoDesc(), vchc.getAwolAbductedCode(),
        vchc.getBirthHistoryIndicatorVar(), vchc.getChildIndianAncestryIndicator(),
        vchc.getCollegeIndicator(), vchc.getCurrentCaseId(), vchc.getDeathCircumstancesType(),
        vchc.getDisabilityDiagnosedCode(), vchc.getDrmsHePassportDocOld(),
        vchc.getDrmsHealthEducPassportDoc(), vchc.getDrmsVoluntaryPlcmntAgrmntDoc(),
        vchc.getFc2EligApplicationIndicatorVar(), vchc.getFoodStampsApplicationDate(),
        vchc.getFoodStampsApplicationIndicator(), vchc.getIcwaEligibilityCode(),
        vchc.getIntercountryAdoptDisruptedIndicator(),
        vchc.getIntercountryAdoptDissolvedIndicator(),
        vchc.getMedEligibilityApplicationIndicatorVar(), vchc.getMinorNmdParentIndicator(),
        vchc.getParentalRightsLimitedIndicator(), vchc.getParentalRightsTermintnIndicatorVar(),
        vchc.getPaternityIndividualIndicatorVar(), vchc.getPostsecVocIndicator(),
        vchc.getPreviouslyAdoptedCode(), vchc.getSafelySurrendedBabiesIndicatorVar(),
        vchc.getSaw1EligApplicationIndicatorVar(), vchc.getSawsCaseSerialNumber(),
        vchc.getSijsScheduledInterviewDate(), vchc.getSiiNextScreeningDueDate(),
        vchc.getSsiSspApplicationIndicator(), vchc.getTribalAncestryNotifctnIndicatorVar(),
        vchc.getTribalCustomaryAdoptionDate(), vchc.getTribalCustomaryAdoptionIndicator());

    childClientDao.update(pers);
  }

  @Override
  public void testFindAllNamedQueryExist() throws Exception {

  }

  @Override
  public void testFindAllReturnsCorrectList() throws Exception {

  }

  private ChildClient validChildClient()
      throws JsonParseException, JsonMappingException, IOException {
    ChildClient validChildClient = MAPPER
        .readValue(fixture("fixtures/persistence/ChildClient/valid/valid.json"), ChildClient.class);
    return validChildClient;
  }

}
