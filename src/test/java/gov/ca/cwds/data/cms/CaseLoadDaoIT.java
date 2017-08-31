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

import gov.ca.cwds.data.persistence.cms.CaseLoad;
import gov.ca.cwds.fixture.CaseLoadEntityBuilder;

/**
 * @author CWDS API Team
 *
 */
@SuppressWarnings("javadoc")
public class CaseLoadDaoIT {

  private static SessionFactory sessionFactory;
  private static CaseLoadDao caseLoadDao;
  private Session session;

  /*
   * id matches src/main/resources/db.cms/ci-seeds.sql
   */
  private String id = "AanwMid0QM";

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
    caseLoadDao = new CaseLoadDao(sessionFactory);
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
    CaseLoad found = caseLoadDao.find(id);
    assertThat(found.getId(), is(equalTo(id)));
  }

  @Test
  public void testFindEntityNotFoundException() throws Exception {
    CaseLoad found = caseLoadDao.find("9999999ZZZ");
    assertThat(found, is(nullValue()));
  }

  @Test
  public void testCreate() throws Exception {
    CaseLoad caseLoad = new CaseLoadEntityBuilder().build();
    CaseLoad created = caseLoadDao.create(caseLoad);
    assertThat(created, is(caseLoad));
  }

  @Test(expected = EntityExistsException.class)
  public void testCreateExistingEntityException() throws Exception {
    CaseLoad caseLoad = new CaseLoadEntityBuilder().setId(id).build();
    caseLoadDao.create(caseLoad);
  }

  @Test
  public void testDelete() throws Exception {
    CaseLoad deleted = caseLoadDao.delete(id);
    assertThat(deleted.getId(), is(id));
  }

  @Test
  public void testDeleteEntityNotFoundException() throws Exception {
    String id = "ABC1234568";
    CaseLoad deleted = caseLoadDao.delete(id);
    assertThat(deleted, is(nullValue()));
  }

  @Test
  public void testUpdate() throws Exception {
    CaseLoad caseLoad = new CaseLoadEntityBuilder().setId(id).setCountySpecificCode("00").build();
    CaseLoad updated = caseLoadDao.update(caseLoad);
    assertThat(updated, is(caseLoad));
  }

  @Test(expected = EntityNotFoundException.class)
  public void testUpdateEntityNotFoundException() throws Exception {
    CaseLoad caseLoad = new CaseLoadEntityBuilder().setId("ppjUt8F41uG").build();
    caseLoadDao.update(caseLoad);
  }

}
