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

import gov.ca.cwds.data.persistence.cms.GovernmentOrganizationCrossReport;
import gov.ca.cwds.fixture.GovernmentOrganizationCrossReportEntityBuilder;

/**
 * @author CWS-NS2
 *
 */
@SuppressWarnings("javadoc")
public class GovernmentOrganizationCrossReportDaoIT {

  private static SessionFactory sessionFactory;
  private static GovernmentOrganizationCrossReportDao governmentOrganizationCrossReportDao;
  private Session session;

  /**
   * thirdId matches src/main/resources/db.cms/ci-seeds.sql
   */
  private String thirdId = "AbalBln0Ki";

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
    governmentOrganizationCrossReportDao = new GovernmentOrganizationCrossReportDao(sessionFactory);
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
    GovernmentOrganizationCrossReport found = governmentOrganizationCrossReportDao.find(thirdId);
    assertThat(found.getThirdId(), is(equalTo(thirdId)));
  }

  @Test
  public void testFindEntityNotFoundException() throws Exception {
    GovernmentOrganizationCrossReport found =
        governmentOrganizationCrossReportDao.find("9999999ZZZ");
    assertThat(found, is(nullValue()));
  }

  @Test
  public void testCreate() throws Exception {
    GovernmentOrganizationCrossReport governmentOrganizationCrossReport =
        new GovernmentOrganizationCrossReportEntityBuilder().setThirdId("ABC1234567").build();
    GovernmentOrganizationCrossReport created =
        governmentOrganizationCrossReportDao.create(governmentOrganizationCrossReport);
    assertThat(created, is(governmentOrganizationCrossReport));
  }

  @Test(expected = EntityExistsException.class)
  public void testCreateExistingEntityException() throws Exception {
    GovernmentOrganizationCrossReport governmentOrganizationCrossReport =
        new GovernmentOrganizationCrossReportEntityBuilder().build();
    governmentOrganizationCrossReportDao.create(governmentOrganizationCrossReport);
  }

  @Test
  public void testDelete() throws Exception {
    GovernmentOrganizationCrossReport deleted =
        governmentOrganizationCrossReportDao.delete(thirdId);
    assertThat(deleted.getThirdId(), is(thirdId));
  }

  @Test
  public void testDeleteEntityNotFoundException() throws Exception {
    String thirdId = "ABC1234568";
    GovernmentOrganizationCrossReport deleted =
        governmentOrganizationCrossReportDao.delete(thirdId);
    assertThat(deleted, is(nullValue()));
  }

  @Test
  public void testUpdate() throws Exception {
    GovernmentOrganizationCrossReport governmentOrganizationCrossReport =
        new GovernmentOrganizationCrossReportEntityBuilder().setCountySpecificCode("23").build();
    GovernmentOrganizationCrossReport updated =
        governmentOrganizationCrossReportDao.update(governmentOrganizationCrossReport);
    assertThat(updated, is(governmentOrganizationCrossReport));
  }

  @Test(expected = EntityNotFoundException.class)
  public void testUpdateEntityNotFoundException() throws Exception {
    GovernmentOrganizationCrossReport governmentOrganizationCrossReport =
        new GovernmentOrganizationCrossReportEntityBuilder().setThirdId("llkk3r9K0B").build();
    governmentOrganizationCrossReportDao.update(governmentOrganizationCrossReport);
  }

}
