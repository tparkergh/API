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
import gov.ca.cwds.data.persistence.ns.Address;

/**
 * @author CWDS API Team
 *
 */
public class AddressDaoIT implements DaoTestTemplate {
  private static final DateFormat TIMESTAMP_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

  /**
   * 
   */
  @Rule
  public ExpectedException thrown = ExpectedException.none();

  private static AddressDao addressDao;
  private static SessionFactory sessionFactory;
  private Session session;

  @SuppressWarnings("javadoc")
  @BeforeClass
  public static void beforeClass() {
    sessionFactory = new Configuration().configure("ns-hibernate.cfg.xml").buildSessionFactory();
    addressDao = new AddressDao(sessionFactory);
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
    Query query = session.getNamedQuery("gov.ca.cwds.rest.api.persistence.ns.Address.findAll");
    assertThat(query, is(notNullValue()));
  }

  @Override
  @Test
  public void testFindAllReturnsCorrectList() {
    Query query = session.getNamedQuery("gov.ca.cwds.rest.api.persistence.ns.Address.findAll");
    assertThat(query.list().size(), is(2));
  }

  @SuppressWarnings("javadoc")
  @Test
  public void testfindAllUpdatedAfterNamedQueryExists() throws Exception {
    Query query =
        session.getNamedQuery("gov.ca.cwds.rest.api.persistence.ns.Address.findAllUpdatedAfter");
    assertThat(query, is(notNullValue()));
  }

  @SuppressWarnings("javadoc")
  @Test
  public void testfindAllUpdatedAfterReturnsCorrectList() throws Exception {
    Query query =
        session.getNamedQuery("gov.ca.cwds.rest.api.persistence.ns.Address.findAllUpdatedAfter")
            .setDate("after", TIMESTAMP_FORMAT.parse("2016-11-02 00:00:00"));
    assertThat(query.list().size(), is(2));
  }

  @Override
  @Test
  public void testFind() {
    long id = 1;
    Address found = addressDao.find(id);
    assertThat(found.getId(), is(id));
  }

  @Override
  @Test
  public void testFindEntityNotFoundException() throws Exception {
    long id = 99;
    Address found = addressDao.find(id);
    assertThat(found, is(nullValue()));
  }

  @Override
  @Test
  public void testCreate() {
    Address address = new Address(null, "123 Main Street", "SAC", "CA", "95757", "32");
    Address created = addressDao.create(address);
    assertThat(created, is(address));
  }

  @Override
  @Test
  public void testCreateExistingEntityException() {
    thrown.expect(EntityExistsException.class);
    Address address = new Address(1L, "123 Main Street", "SAC", "CA", "95757", "32");
    addressDao.create(address);
  }

  @Override
  @Test
  public void testDelete() {
    long id = 1;
    Address deleted = addressDao.delete(id);
    assertThat(deleted.getId(), is(id));
  }

  @Override
  @Test
  public void testDeleteEntityNotFoundException() throws Exception {
    Address updated = addressDao.delete((long) 99);
    assertThat(updated, is(nullValue()));
  }

  @Override
  @Test
  public void testUpdate() {
    Address address = new Address(1L, "123 Main Street", "SAC", "CA", "95757", "32");
    Address updated = addressDao.update(address);
    assertThat(updated, is(address));
  }

  @Override
  @Test
  public void testUpdateEntityNotFoundException() {
    thrown.expect(EntityNotFoundException.class);
    Address address = new Address(111L, "123 Main Street", "SAC", "CA", "95757", "32");
    addressDao.update(address);
  }

}
