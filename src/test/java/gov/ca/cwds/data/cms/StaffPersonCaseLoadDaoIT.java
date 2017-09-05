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

import gov.ca.cwds.data.persistence.cms.StaffPersonCaseLoad;
import gov.ca.cwds.fixture.StaffPersonCaseLoadEntityBuilder;

/**
 * @author CWDS API Team
 *
 */
@SuppressWarnings("javadoc")
public class StaffPersonCaseLoadDaoIT {

  private static SessionFactory sessionFactory;
  private static StaffPersonCaseLoadDao staffPersonCaseLoadDao;
  private Session session;

  /*
   * thirdId matches src/main/resources/db.cms/ci-seeds.sql
   */
  private String thirdId = "MLazRFR00E";

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
    staffPersonCaseLoadDao = new StaffPersonCaseLoadDao(sessionFactory);
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

  @Test
  public void testFind() throws Exception {
    StaffPersonCaseLoad found = staffPersonCaseLoadDao.find(thirdId);
    assertThat(found.getThirdId(), is(equalTo(thirdId)));
  }

  @Test
  public void testFindEntityNotFoundException() throws Exception {
    StaffPersonCaseLoad found = staffPersonCaseLoadDao.find("9999999ZZZ");
    assertThat(found, is(nullValue()));
  }

  @Test
  public void testCreate() throws Exception {
    StaffPersonCaseLoad staffPersonCaseLoad =
        new StaffPersonCaseLoadEntityBuilder().setThirdId("098u6tR43G").build();
    StaffPersonCaseLoad created = staffPersonCaseLoadDao.create(staffPersonCaseLoad);
    assertThat(created, is(staffPersonCaseLoad));
  }

  @Test(expected = EntityExistsException.class)
  public void testCreateExistingEntityException() throws Exception {
    StaffPersonCaseLoad staffPersonCaseLoad = new StaffPersonCaseLoadEntityBuilder().build();
    staffPersonCaseLoadDao.create(staffPersonCaseLoad);
  }

  @Test
  public void testDelete() throws Exception {
    StaffPersonCaseLoad deleted = staffPersonCaseLoadDao.delete(thirdId);
    assertThat(deleted.getThirdId(), is(thirdId));
  }

  @Test
  public void testDeleteEntityNotFoundException() throws Exception {
    String thirdId = "ABC1234568";
    StaffPersonCaseLoad deleted = staffPersonCaseLoadDao.delete(thirdId);
    assertThat(deleted, is(nullValue()));
  }

  @Test
  public void testUpdate() throws Exception {
    StaffPersonCaseLoad staffPersonCaseLoad =
        new StaffPersonCaseLoadEntityBuilder().setCountyCode("00").build();
    StaffPersonCaseLoad updated = staffPersonCaseLoadDao.update(staffPersonCaseLoad);
    assertThat(updated, is(staffPersonCaseLoad));
  }

  @Test(expected = EntityNotFoundException.class)
  public void testUpdateEntityNotFoundException() throws Exception {
    StaffPersonCaseLoad staffPersonCaseLoad =
        new StaffPersonCaseLoadEntityBuilder().setThirdId("098u6tR43GL").build();
    staffPersonCaseLoadDao.update(staffPersonCaseLoad);
  }

}
