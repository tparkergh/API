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
import gov.ca.cwds.data.persistence.ns.Race;

/**
 * @author CWDS API Team
 *
 */
public class RaceDaoIT implements DaoTestTemplate {
  private static final DateFormat TIMESTAMP_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

  /**
   * 
   */
  @Rule
  public ExpectedException thrown = ExpectedException.none();

  private static RaceDao raceDao;
  private static SessionFactory sessionFactory;
  private Session session;

  @SuppressWarnings("javadoc")
  @BeforeClass
  public static void beforeClass() {
    sessionFactory = new Configuration().configure("ns-hibernate.cfg.xml").buildSessionFactory();
    raceDao = new RaceDao(sessionFactory);
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
    Query query = session.getNamedQuery("gov.ca.cwds.rest.api.persistence.ns.Race.findAll");
    assertThat(query, is(notNullValue()));
  }

  @Override
  @Test
  public void testFindAllReturnsCorrectList() {
    Query query = session.getNamedQuery("gov.ca.cwds.rest.api.persistence.ns.Race.findAll");
    assertThat(query.list().size(), is(2));
  }

  @SuppressWarnings("javadoc")
  @Test
  public void testfindAllUpdatedAfterNamedQueryExists() throws Exception {
    Query query =
        session.getNamedQuery("gov.ca.cwds.rest.api.persistence.ns.Race.findAllUpdatedAfter");
    assertThat(query, is(notNullValue()));
  }

  @SuppressWarnings("javadoc")
  @Test
  public void testfindAllUpdatedAfterReturnsCorrectList() throws Exception {
    Query query =
        session.getNamedQuery("gov.ca.cwds.rest.api.persistence.ns.Race.findAllUpdatedAfter")
            .setDate("after", TIMESTAMP_FORMAT.parse("2017-02-22 13:52:27.801"));
    assertThat(query.list().size(), is(2));
  }

  @Override
  @Test
  public void testFind() {
    long id = 1;
    Race found = raceDao.find(id);
    assertThat(found.getId(), is(id));
  }

  @Override
  @Test
  public void testFindEntityNotFoundException() throws Exception {
    long id = 99;
    Race found = raceDao.find(id);
    assertThat(found, is(nullValue()));

  }

  @Override
  @Test
  public void testCreate() {
    Race race = new Race(0L, "Brown", "Asian");
    Race created = raceDao.create(race);
    assertThat(created, is(race));
  }

  @Override
  @Test
  public void testCreateExistingEntityException() {
    thrown.expect(EntityExistsException.class);
    Race race = new Race(1L, "Brown", "Asian");
    raceDao.create(race);
  }

  @Override
  @Test
  public void testDelete() throws Exception {
    long id = 1;
    Race deleted = raceDao.delete(id);
    assertThat(deleted.getId(), is(id));

  }

  @Override
  @Test
  public void testDeleteEntityNotFoundException() {
    Race updated = raceDao.delete((long) 99);
    assertThat(updated, is(nullValue()));

  }

  @Override
  @Test
  public void testUpdate() {
    Race race = new Race(1L, "Brown", "American");
    Race updated = raceDao.update(race);
    assertThat(updated, is(race));

  }

  @Override
  @Test
  public void testUpdateEntityNotFoundException() {
    thrown.expect(EntityNotFoundException.class);
    Race race = new Race(11L, "Brown", "American");
    raceDao.update(race);
  }
}
