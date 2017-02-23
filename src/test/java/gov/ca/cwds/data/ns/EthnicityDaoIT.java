package gov.ca.cwds.data.ns;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import gov.ca.cwds.data.junit.template.DaoTestTemplate;
import gov.ca.cwds.data.persistence.ns.Ethnicity;

/**
 * @author CWDS API Team
 *
 */
public class EthnicityDaoIT implements DaoTestTemplate {

  private static final DateFormat TIMESTAMP_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

  /**
   * 
   */
  @Rule
  public ExpectedException thrown = ExpectedException.none();

  private static EthnicityDao ethnicityDao;
  private static SessionFactory sessionFactory;
  private Session session;

  @SuppressWarnings("javadoc")
  @BeforeClass
  public static void beforeClass() {
    sessionFactory = new Configuration().configure("ns-hibernate.cfg.xml").buildSessionFactory();
    ethnicityDao = new EthnicityDao(sessionFactory);
  }

  @SuppressWarnings("javadoc")
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

  @Override
  @Test
  public void testFindAllNamedQueryExist() throws Exception {
    Query query = session.getNamedQuery("gov.ca.cwds.rest.api.persistence.ns.Ethnicity.findAll");
    assertThat(query, is(notNullValue()));
  }

  @Override
  @Test
  public void testFindAllReturnsCorrectList() {
    Query query = session.getNamedQuery("gov.ca.cwds.rest.api.persistence.ns.Ethnicity.findAll");
    assertThat(query.list().size(), is(2));
  }

  @SuppressWarnings("javadoc")
  @Test
  public void testfindAllUpdatedAfterNamedQueryExists() throws Exception {
    Query query =
        session.getNamedQuery("gov.ca.cwds.rest.api.persistence.ns.Ethnicity.findAllUpdatedAfter");
    assertThat(query, is(notNullValue()));
  }

  @SuppressWarnings("javadoc")
  @Test
  public void testfindAllUpdatedAfterReturnsCorrectList() throws Exception {
    Query query =
        session.getNamedQuery("gov.ca.cwds.rest.api.persistence.ns.Ethnicity.findAllUpdatedAfter")
            .setDate("after", TIMESTAMP_FORMAT.parse("2017-02-22 13:52:27.801"));
    assertThat(query.list().size(), is(2));
  }

  @Override
  @Test
  public void testFind() {
    long id = 1;
    Ethnicity found = ethnicityDao.find(id);
    assertThat(found.getId(), is(id));
  }

  @Override
  @Test
  public void testFindEntityNotFoundException() throws Exception {
    long id = 99;
    Ethnicity found = ethnicityDao.find(id);
    assertThat(found, is(nullValue()));

  }

  @Override
  @Test
  public void testCreate() {
    Ethnicity ethnicity = new Ethnicity(0L, "Unknown", "North American");
    Ethnicity created = ethnicityDao.create(ethnicity);
    assertThat(created, is(ethnicity));
  }

  @Override
  @Test
  public void testCreateExistingEntityException() {
    thrown.expect(EntityExistsException.class);
    Ethnicity ethnicity = new Ethnicity(1L, "Unknown", "North American");
    ethnicityDao.create(ethnicity);
  }

  @Override
  @Test
  public void testDelete() throws Exception {
    long id = 1;
    Ethnicity deleted = ethnicityDao.delete(id);
    assertThat(deleted.getId(), is(id));

  }

  @Override
  @Test
  public void testDeleteEntityNotFoundException() {
    Ethnicity updated = ethnicityDao.delete((long) 99);
    assertThat(updated, is(nullValue()));

  }

  @Override
  @Test
  public void testUpdate() {
    Ethnicity ethnicity = new Ethnicity(1L, "Unknown", "South American");
    Ethnicity updated = ethnicityDao.update(ethnicity);
    assertThat(updated, is(ethnicity));

  }

  @Override
  @Test
  public void testUpdateEntityNotFoundException() {
    thrown.expect(EntityNotFoundException.class);
    Ethnicity ethnicity = new Ethnicity(11L, "Unknown", "South American");
    ethnicityDao.update(ethnicity);
  }

}
