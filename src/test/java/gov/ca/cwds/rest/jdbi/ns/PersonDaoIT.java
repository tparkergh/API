package gov.ca.cwds.rest.jdbi.ns;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

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

import gov.ca.cwds.rest.api.persistence.ns.Address;
import gov.ca.cwds.rest.api.persistence.ns.Person;

/**
 * @author CWDS API Team
 *
 */
public class PersonDaoIT {

  private static final DateFormat TIMESTAMP_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

  /**
   * 
   */
  @Rule
  public ExpectedException thrown = ExpectedException.none();

  private static PersonDao personDao;
  private static SessionFactory sessionFactory;
  private Session session;

  @BeforeClass
  public static void beforeClass() {
    sessionFactory = new Configuration().configure("ns-hibernate.cfg.xml").buildSessionFactory();
    personDao = new PersonDao(sessionFactory);
  }

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
  public void tearddown() {
    session.getTransaction().rollback();
  }

  @Test
  public void testFindAllNamedQueryExists() throws Exception {
    Query query = session.getNamedQuery("gov.ca.cwds.rest.api.persistence.ns.Person.findAll");
    assertThat(query, is(notNullValue()));
  }

  @Test
  public void testFindAllReturnsCorrectList() {
    Query query = session.getNamedQuery("gov.ca.cwds.rest.api.persistence.ns.Person.findAll");
    assertThat(query.list().size(), is(2));
  }

  @Test
  public void testfindAllUpdatedAfterNamedQueryExists() throws Exception {
    Query query =
        session.getNamedQuery("gov.ca.cwds.rest.api.persistence.ns.Person.findAllUpdatedAfter");
    assertThat(query, is(notNullValue()));
  }

  @Test
  public void testfindAllUpdatedAfterReturnsCorrectList() throws Exception {
    Query query =
        session.getNamedQuery("gov.ca.cwds.rest.api.persistence.ns.Person.findAllUpdatedAfter")
            .setDate("after", TIMESTAMP_FORMAT.parse("2016-11-02 00:00:00"));
    assertThat(query.list().size(), is(1));
  }

  @Test
  public void testFind() {
    long id = 1;
    Person found = personDao.find(id);
    assertThat(found.getId(), is(id));
  }

  @Test
  public void testCreate() {
    Address address = new Address(null, "123 Main Street", "SAC", "CA", 95757);
    Person person = new Person(null, "John", "Doe", "Male", new Date(), "111-11-1111", address);
    Person created = personDao.create(person);
    assertThat(created, is(person));
  }

  @Test
  public void testCreateExistingEntityException() {
    thrown.expect(EntityExistsException.class);
    Address address = new Address(1L, "123 Main Street", "SAC", "CA", 95757);
    Person person = new Person(1L, "John", "Doe", "Male", new Date(), "111-11-1111", address);
    personDao.create(person);
  }

  @Test
  public void testDelete() {
    long id = 1;
    Person deleted = personDao.delete(id);
    assertThat(deleted.getId(), is(id));
  }

  @Test
  public void testUpdate() {
    Address address = new Address(1L, "123 Main Street", "SAC", "CA", 95757);
    Person person = new Person(1L, "John", "Doe", "Male", new Date(), "111-11-1111", address);
    Person updated = personDao.update(person);
    assertThat(updated, is(person));
  }

  @Test
  public void testUpdateEntityNotFoundException() {
    thrown.expect(EntityNotFoundException.class);
    Address address = new Address(111L, "123 Main Street", "SAC", "CA", 95757);
    Person person = new Person(1111L, "John", "Doe", "Male", new Date(), "111-11-1111", address);
    personDao.update(person);
  }

}
