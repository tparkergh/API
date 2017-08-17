package gov.ca.cwds.data.ns;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

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
import gov.ca.cwds.data.persistence.ns.Person;
import gov.ca.cwds.data.persistence.ns.PersonAddress;

/**
 * 
 * @author CWDS API Team
 */
public class PersonDaoIT implements DaoTestTemplate {

  // TODO: #138169961: comment out test cases until DDL changes are ready.

  private static final DateFormat TIMESTAMP_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

  /**
   * 
   */
  @Rule
  public ExpectedException thrown = ExpectedException.none();

  private static PersonDao personDao;
  private static SessionFactory sessionFactory;
  private Session session;

  @SuppressWarnings("javadoc")
  @BeforeClass
  public static void beforeClass() {
    sessionFactory = new Configuration().configure("ns-hibernate.cfg.xml").buildSessionFactory();
    personDao = new PersonDao(sessionFactory);
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
    Query query = session.getNamedQuery("gov.ca.cwds.rest.api.persistence.ns.Person.findAll");
    assertThat(query, is(notNullValue()));
  }

  @Override
  @Test
  public void testFindAllReturnsCorrectList() {
    // Query query = session.getNamedQuery("gov.ca.cwds.rest.api.persistence.ns.Person.findAll");
    // assertThat(query.list().size(), is(2));
  }

  @SuppressWarnings("javadoc")
  @Test
  public void testfindAllUpdatedAfterNamedQueryExists() throws Exception {
    Query query =
        session.getNamedQuery("gov.ca.cwds.rest.api.persistence.ns.Person.findAllUpdatedAfter");
    assertThat(query, is(notNullValue()));
  }

  @SuppressWarnings("javadoc")
  @Test
  public void testfindAllUpdatedAfterReturnsCorrectList() throws Exception {
    // Query query =
    // session.getNamedQuery("gov.ca.cwds.rest.api.persistence.ns.Person.findAllUpdatedAfter")
    // .setDate("after", TIMESTAMP_FORMAT.parse("2016-11-02 00:00:00"));
    // assertThat(query.list().size(), is(1));
  }

  @Override
  @Test
  public void testFind() {
    // long id = 1;
    // Person found = personDao.find(id);
    // assertThat(found.getId(), is(id));
  }

  @Override
  @Test
  public void testFindEntityNotFoundException() throws Exception {
    // long id = 99;
    // Person found = personDao.find(id);
    // assertThat(found, is(nullValue()));
  }

  @Override
  @Test
  public void testCreate() {
    gov.ca.cwds.data.persistence.ns.Address toCreateAddress =
        new gov.ca.cwds.data.persistence.ns.Address(1L, "742 Evergreen Terrace", "Springfield",
            "WA", "98700", "32");
    Set<PersonAddress> personAddresses = new HashSet<>();

    PersonAddress personAddress = new PersonAddress();
    personAddress.setAddress(toCreateAddress);
    personAddresses.add(personAddress);
    Person person = new Person(null, "John", "J.", "Doe", "Male", new Date(), "111-11-1111",
        personAddresses, null, null, null, null);
    Person created = personDao.create(person);
    assertThat(created, is(person));
  }

  @Override
  @Test
  public void testCreateExistingEntityException() {
    // thrown.expect(EntityExistsException.class);
    // Address address = new Address(1L, "123 Main Street", "SAC", "CA", 95757, "Home");
    // Person person = new Person(1L, "John", "Doe", "Male", new Date(), "111-11-1111", address);
    // personDao.create(person);
  }

  @Override
  @Test
  public void testDelete() {
    // long id = 1;
    // Person deleted = personDao.delete(id);
    // assertThat(deleted.getId(), is(id));
  }

  @Override
  @Test
  public void testDeleteEntityNotFoundException() throws Exception {
    // Person updated = personDao.delete((long) 99);
    // assertThat(updated, is(nullValue()));
  }

  @Override
  @Test
  public void testUpdate() {
    // Address address = new Address(1L, "123 Main Street", "SAC", "CA", 95757, "Home");
    // Person person = new Person(1L, "John", "Doe", "Male", new Date(), "111-11-1111", address);
    // Person updated = personDao.update(person);
    // assertThat(updated, is(person));
  }

  @Override
  @Test
  public void testUpdateEntityNotFoundException() {
    // thrown.expect(EntityNotFoundException.class);
    // Address address = new Address(111L, "123 Main Street", "SAC", "CA", 95757, "Home");
    // Person person = new Person(1111L, "John", "Doe", "Male", new Date(), "111-11-1111", address);
    // personDao.update(person);
  }

}
