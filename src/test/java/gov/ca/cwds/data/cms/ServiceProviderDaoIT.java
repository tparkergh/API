package gov.ca.cwds.data.cms;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.hamcrest.junit.ExpectedException;
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

import gov.ca.cwds.data.cms.ServiceProviderDao;
import gov.ca.cwds.data.persistence.cms.ServiceProvider;

public class ServiceProviderDaoIT {
  private static final DateFormat TIMESTAMP_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


  @Rule
  public ExpectedException thrown = ExpectedException.none();

  private static ServiceProviderDao serviceProviderDao;
  private static SessionFactory sessionFactory;
  private Session session;

  @BeforeClass
  public static void beforeClass() {
    sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
    serviceProviderDao = new ServiceProviderDao(sessionFactory);
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
    Query query =
        session.getNamedQuery("gov.ca.cwds.rest.api.persistence.cms.ServiceProvider.findAll");
    assertThat(query, is(notNullValue()));
  }

  @Test
  public void testFindAllReturnsCorrectList() {
    Query query =
        session.getNamedQuery("gov.ca.cwds.rest.api.persistence.cms.ServiceProvider.findAll");
    assertThat(query.list().size(), is(3));
  }

  @Test
  public void testfindAllUpdatedAfterNamedQueryExists() throws Exception {
    Query query = session
        .getNamedQuery("gov.ca.cwds.rest.api.persistence.cms.ServiceProvider.findAllUpdatedAfter");
    assertThat(query, is(notNullValue()));
  }

  @Test
  public void testfindAllUpdatedAfterReturnsCorrectList() throws Exception {
    Query query = session
        .getNamedQuery("gov.ca.cwds.rest.api.persistence.cms.ServiceProvider.findAllUpdatedAfter")
        .setDate("after", TIMESTAMP_FORMAT.parse("2000-11-02 00:00:00"));
    assertThat(query.list().size(), is(3));
  }

  @Test
  public void testFind() {
    String id = "Ao9dm8T0Ki";
    ServiceProvider found = serviceProviderDao.find(id);
    assertThat(found.getId(), is(id));
  }

  @Test
  public void testCreate() throws Exception {
    ServiceProvider serviceProvider = new ServiceProvider("Test Agency", "N", " ", null,
        BigDecimal.ZERO, "Horacio", "Ao9dm8T0Kj", "G.", "Dr.", 0, BigDecimal.ZERO, "Kiddie Care",
        (short) 0, (short) 0, " ", " ", " ", 0, (short) 0);
    ServiceProvider created = serviceProviderDao.create(serviceProvider);
    assertThat(created, is(serviceProvider));
  }

  @Test
  public void testCreateExistingEntityException() throws Exception {
    thrown.expect(EntityExistsException.class);
    ServiceProvider serviceProvider = new ServiceProvider("Test Agency", "N", " ", null,
        BigDecimal.ZERO, "Horacio", "Ao9dm8T0Ki", "G.", "Dr.", 0, BigDecimal.ZERO, "Kiddie Care",
        (short) 0, (short) 0, " ", " ", " ", 0, (short) 0);
    serviceProviderDao.create(serviceProvider);
  }

  @Test
  public void testDelete() {
    String id = "Ao9dm8T0Ki";
    ServiceProvider deleted = serviceProviderDao.delete(id);
    assertThat(deleted.getId(), is(id));
  }

  @Test
  public void testUpdate() throws Exception {

    ServiceProvider serviceProvider = new ServiceProvider("Test Agency", "N", " ", null,
        BigDecimal.ZERO, "Horacio", "Ao9dm8T0Ki", "Guest", "Dr.", 0, BigDecimal.ZERO, "Kiddie Care",
        (short) 0, (short) 0, " ", " ", " ", 0, (short) 0);
    ServiceProvider updated = serviceProviderDao.update(serviceProvider);
    assertThat(updated, is(serviceProvider));
  }

  @Test
  public void testUpdateEntityNotFoundException() throws Exception {
    thrown.expect(EntityNotFoundException.class);

    ServiceProvider serviceProvider = new ServiceProvider("Test Agency", "N", " ", null,
        BigDecimal.ZERO, "Horacio", "Ao9dm8T0Kk", "G.", "Dr.", 0, BigDecimal.ZERO, "Kiddie Care",
        (short) 0, (short) 0, " ", " ", " ", 0, (short) 0);
    serviceProviderDao.update(serviceProvider);
  }
}
