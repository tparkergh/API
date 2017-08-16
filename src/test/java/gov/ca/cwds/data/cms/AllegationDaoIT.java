package gov.ca.cwds.data.cms;

import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

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

import gov.ca.cwds.data.persistence.cms.Allegation;
import gov.ca.cwds.fixture.CmsAllegationResourceBuilder;
import gov.ca.cwds.rest.api.domain.DomainChef;

/**
 * 
 * @author CWDS API Team
 */
@SuppressWarnings("javadoc")
public class AllegationDaoIT {

  private static SessionFactory sessionFactory;
  private static AllegationDao allegationDao;
  private Session session;

  /**
   * id matches src/main/resources/db.cms/ci-seeds.sql
   */
  private String id = "Aaeae9r0F4";

  /**
   * 
   */
  @Rule
  public ExpectedException thrown = ExpectedException.none();

  /**
   * 
   */
  @BeforeClass
  public static void beforeClass() {
    sessionFactory = new Configuration().configure().buildSessionFactory();
    allegationDao = new AllegationDao(sessionFactory);
  }

  /**
   * 
   */
  @AfterClass
  public static void afterClass() {
    sessionFactory.close();
  }

  @Before
  public void setup() {
    session = sessionFactory.getCurrentSession();
    session.beginTransaction();
  }

  @After
  public void teardown() {
    session.getTransaction().rollback();
  }

  /**
   * Find JUnit test
   * 
   * @throws Exception test general
   */
  @Test
  public void testFind() throws Exception {
    Allegation found = allegationDao.find(id);
    assertThat(found.getId(), is(equalTo(id)));
  }

  @Test
  public void testFindEntityNotFoundException() throws Exception {
    Allegation found = allegationDao.find("9999999ZZZ");
    assertThat(found, is(nullValue()));
  }

  /**
   * Create JUnit test
   * 
   * @throws Exception test general
   */
  @Test
  public void testCreate() throws Exception {

    gov.ca.cwds.rest.api.domain.cms.Allegation vda =
        new CmsAllegationResourceBuilder().buildCmsAllegation();

    Allegation allegation =
        new Allegation("1234567ABC", DomainChef.uncookDateString(vda.getAbuseEndDate()),
            DomainChef.uncookDateString(vda.getAbuseStartDate()), vda.getAbuseFrequency(),
            vda.getAbuseFrequencyPeriodCode(), vda.getAbuseLocationDescription(),
            vda.getAllegationDispositionType(), vda.getAllegationType(),
            vda.getDispositionDescription(), DomainChef.uncookDateString(vda.getDispositionDate()),
            DomainChef.cookBoolean(vda.getInjuryHarmDetailIndicator()),
            vda.getNonProtectingParentCode(),
            DomainChef.cookBoolean(vda.getStaffPersonAddedIndicator()), vda.getVictimClientId(),
            vda.getPerpetratorClientId(), vda.getReferralId(), vda.getCountySpecificCode(),
            DomainChef.cookBoolean(vda.getZippyCreatedIndicator()), vda.getPlacementFacilityType(),
            null, null);

    Allegation create = allegationDao.create(allegation);
    assertThat(allegation, is(create));
  }

  @Test(expected = EntityExistsException.class)
  public void testCreateExistingEntityException() throws Exception {

    gov.ca.cwds.rest.api.domain.cms.Allegation vda =
        new CmsAllegationResourceBuilder().buildCmsAllegation();

    Allegation allegation = new Allegation(id, DomainChef.uncookDateString(vda.getAbuseEndDate()),
        DomainChef.uncookDateString(vda.getAbuseStartDate()), vda.getAbuseFrequency(),
        vda.getAbuseFrequencyPeriodCode(), vda.getAbuseLocationDescription(),
        vda.getAllegationDispositionType(), vda.getAllegationType(),
        vda.getDispositionDescription(), DomainChef.uncookDateString(vda.getDispositionDate()),
        DomainChef.cookBoolean(vda.getInjuryHarmDetailIndicator()),
        vda.getNonProtectingParentCode(),
        DomainChef.cookBoolean(vda.getStaffPersonAddedIndicator()), vda.getVictimClientId(),
        vda.getPerpetratorClientId(), vda.getReferralId(), vda.getCountySpecificCode(),
        DomainChef.cookBoolean(vda.getZippyCreatedIndicator()), vda.getPlacementFacilityType(),
        null, null);

    allegationDao.create(allegation);
  }

  /**
   * Delete JUnit test
   * 
   * @throws Exception test general
   */
  @Test
  public void testDelete() throws Exception {
    Allegation deleted = allegationDao.delete(id);
    assertThat(deleted.getId(), is(id));
  }

  @Test
  public void testDeleteEntityNotFoundException() throws Exception {
    Allegation deleted = allegationDao.delete("9999999ZZZ");
    assertThat(deleted, is(nullValue()));
  }

  /**
   * Update JUnit test
   * 
   * @throws Exception test general
   */
  @Test
  public void testUpdate() throws Exception {

    gov.ca.cwds.rest.api.domain.cms.Allegation vda =
        new CmsAllegationResourceBuilder().buildCmsAllegation();

    Allegation allegation = new Allegation(id, DomainChef.uncookDateString(vda.getAbuseEndDate()),
        DomainChef.uncookDateString(vda.getAbuseStartDate()), vda.getAbuseFrequency(),
        vda.getAbuseFrequencyPeriodCode(), vda.getAbuseLocationDescription(),
        vda.getAllegationDispositionType(), vda.getAllegationType(),
        vda.getDispositionDescription(), DomainChef.uncookDateString(vda.getDispositionDate()),
        DomainChef.cookBoolean(vda.getInjuryHarmDetailIndicator()),
        vda.getNonProtectingParentCode(),
        DomainChef.cookBoolean(vda.getStaffPersonAddedIndicator()), vda.getVictimClientId(),
        vda.getPerpetratorClientId(), vda.getReferralId(), vda.getCountySpecificCode(),
        DomainChef.cookBoolean(vda.getZippyCreatedIndicator()), vda.getPlacementFacilityType(),
        null, null);

    Allegation updated = allegationDao.update(allegation);
    assertThat(allegation, is(updated));
  }

  @Test(expected = EntityNotFoundException.class)
  public void testUpdateEntityNotFoundException() throws Exception {

    gov.ca.cwds.rest.api.domain.cms.Allegation vda =
        new CmsAllegationResourceBuilder().buildCmsAllegation();

    Allegation allegation =
        new Allegation("1234567ABC", DomainChef.uncookDateString(vda.getAbuseEndDate()),
            DomainChef.uncookDateString(vda.getAbuseStartDate()), vda.getAbuseFrequency(),
            vda.getAbuseFrequencyPeriodCode(), vda.getAbuseLocationDescription(),
            vda.getAllegationDispositionType(), vda.getAllegationType(),
            vda.getDispositionDescription(), DomainChef.uncookDateString(vda.getDispositionDate()),
            DomainChef.cookBoolean(vda.getInjuryHarmDetailIndicator()),
            vda.getNonProtectingParentCode(),
            DomainChef.cookBoolean(vda.getStaffPersonAddedIndicator()), vda.getVictimClientId(),
            vda.getPerpetratorClientId(), vda.getReferralId(), vda.getCountySpecificCode(),
            DomainChef.cookBoolean(vda.getZippyCreatedIndicator()), vda.getPlacementFacilityType(),
            null, null);

    allegationDao.update(allegation);
  }

}
