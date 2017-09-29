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

import gov.ca.cwds.data.persistence.cms.LawEnforcementEntity;
import gov.ca.cwds.fixture.LawEnforcementEntityBuilder;

/**
 * @author CWDS API Team
 *
 */
@SuppressWarnings("javadoc")
public class LawEnforcementDaoIT {

  private static SessionFactory sessionFactory;
  private static LawEnforcementDao lawEnforcementDao;
  private Session session;

  /*
   * id matches src/main/resources/db.cms/ci-seeds.sql
   */
  private String id = "AdAOv470GR";

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
    lawEnforcementDao = new LawEnforcementDao(sessionFactory);
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
    LawEnforcementEntity found = lawEnforcementDao.find(id);
    assertThat(found.getId(), is(equalTo(id)));
  }

  @Test
  public void testFindEntityNotFoundException() throws Exception {
    LawEnforcementEntity found = lawEnforcementDao.find("9999999ZZZ");
    assertThat(found, is(nullValue()));
  }

  @Test
  public void testCreate() throws Exception {
    LawEnforcementEntity lawEnforcement = new LawEnforcementEntityBuilder().setId("ABc0987yt5").build();
    LawEnforcementEntity created = lawEnforcementDao.create(lawEnforcement);
    assertThat(created, is(lawEnforcement));
  }

  @Test(expected = EntityExistsException.class)
  public void testCreateExistingEntityException() throws Exception {
    LawEnforcementEntity lawEnforcement = new LawEnforcementEntityBuilder().setId(id).build();
    lawEnforcementDao.create(lawEnforcement);
  }

  @Test
  public void testDelete() throws Exception {
    LawEnforcementEntity deleted = lawEnforcementDao.delete(id);
    assertThat(deleted.getId(), is(id));
  }

  @Test
  public void testDeleteEntityNotFoundException() throws Exception {
    String id = "ABC1234568";
    LawEnforcementEntity deleted = lawEnforcementDao.delete(id);
    assertThat(deleted, is(nullValue()));
  }

  @Test
  public void testUpdate() throws Exception {
    LawEnforcementEntity lawEnforcement =
        new LawEnforcementEntityBuilder().setCityName("Fremont").build();
    LawEnforcementEntity updated = lawEnforcementDao.update(lawEnforcement);
    assertThat(updated, is(lawEnforcement));
  }

  @Test(expected = EntityNotFoundException.class)
  public void testUpdateEntityNotFoundException() throws Exception {
    LawEnforcementEntity lawEnforcement = new LawEnforcementEntityBuilder().setId("ABc098vztZ").build();
    lawEnforcementDao.update(lawEnforcement);
  }

}
