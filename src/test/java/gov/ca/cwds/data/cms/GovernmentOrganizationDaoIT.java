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

import gov.ca.cwds.data.persistence.cms.GovernmentOrganizationEntity;
import gov.ca.cwds.fixture.GovernmentOrganizationEntityBuilder;

/**
 * @author CWDS API Team
 *
 */
@SuppressWarnings("javadoc")
public class GovernmentOrganizationDaoIT {

  private static SessionFactory sessionFactory;
  private static GovernmentOrganizationDao governmentOrganizationDao;
  private Session session;

  /*
   * id matches src/main/resources/db.cms/ci-seeds.sql
   */
  private String id = "02JBdLO00E";

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
    governmentOrganizationDao = new GovernmentOrganizationDao(sessionFactory);
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
    GovernmentOrganizationEntity found = governmentOrganizationDao.find(id);
    assertThat(found.getId(), is(equalTo(id)));
  }

  @Test
  public void testFindEntityNotFoundException() throws Exception {
    GovernmentOrganizationEntity found = governmentOrganizationDao.find("9999999ZZZ");
    assertThat(found, is(nullValue()));
  }

  @Test
  public void testCreate() throws Exception {
    GovernmentOrganizationEntity governmentOrganization =
        new GovernmentOrganizationEntityBuilder().setId("123tU8oP0").build();
    GovernmentOrganizationEntity created = governmentOrganizationDao.create(governmentOrganization);
    assertThat(created, is(governmentOrganization));
  }

  @Test(expected = EntityExistsException.class)
  public void testCreateExistingEntityException() throws Exception {
    GovernmentOrganizationEntity governmentOrganization =
        new GovernmentOrganizationEntityBuilder().setId(id).build();
    governmentOrganizationDao.create(governmentOrganization);
  }

  @Test
  public void testDelete() throws Exception {
    GovernmentOrganizationEntity deleted = governmentOrganizationDao.delete(id);
    assertThat(deleted.getId(), is(id));
  }

  @Test
  public void testDeleteEntityNotFoundException() throws Exception {
    String id = "ABC1234568";
    GovernmentOrganizationEntity deleted = governmentOrganizationDao.delete(id);
    assertThat(deleted, is(nullValue()));
  }

  @Test
  public void testUpdate() throws Exception {
    GovernmentOrganizationEntity governmentOrganization =
        new GovernmentOrganizationEntityBuilder().setId(id).setCityName("Fremont").build();
    GovernmentOrganizationEntity updated = governmentOrganizationDao.update(governmentOrganization);
    assertThat(updated, is(governmentOrganization));
  }

  @Test(expected = EntityNotFoundException.class)
  public void testUpdateEntityNotFoundException() throws Exception {
    GovernmentOrganizationEntity governmentOrganization =
        new GovernmentOrganizationEntityBuilder().setId("09ojyF5f3E").build();
    governmentOrganizationDao.update(governmentOrganization);
  }

}
