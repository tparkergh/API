package gov.ca.cwds.data.cms;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

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

import gov.ca.cwds.data.junit.template.DaoTestTemplate;
import gov.ca.cwds.data.persistence.cms.ServiceProvider;

/**
 * @author CWDS API Team
 *
 */
public class ServiceProviderDaoIT implements DaoTestTemplate {
  private static final DateFormat TIMESTAMP_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


  @SuppressWarnings("javadoc")
  @Rule
  public ExpectedException thrown = ExpectedException.none();

  private static ServiceProviderDao serviceProviderDao;
  private static SessionFactory sessionFactory;
  private Session session;

  @SuppressWarnings("javadoc")
  @BeforeClass
  public static void beforeClass() {
    sessionFactory = new Configuration().configure().buildSessionFactory();
    serviceProviderDao = new ServiceProviderDao(sessionFactory);
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
    Query query = session.getNamedQuery("gov.ca.cwds.data.persistence.cms.ServiceProvider.findAll");
    assertThat(query, is(notNullValue()));
  }

  @Override
  @Test
  public void testFindAllReturnsCorrectList() throws Exception {
    Query query = session.getNamedQuery("gov.ca.cwds.data.persistence.cms.ServiceProvider.findAll");
    assertThat(query.list().size(), is(3));
  }

  @SuppressWarnings("javadoc")
  @Test
  public void testfindAllUpdatedAfterNamedQueryExists() throws Exception {
    Query query = session
        .getNamedQuery("gov.ca.cwds.data.persistence.cms.ServiceProvider.findAllUpdatedAfter");
    assertThat(query, is(notNullValue()));
  }

  @SuppressWarnings("javadoc")
  @Test
  public void testfindAllUpdatedAfterReturnsCorrectList() throws Exception {
    Query query = session
        .getNamedQuery("gov.ca.cwds.data.persistence.cms.ServiceProvider.findAllUpdatedAfter")
        .setDate("after", TIMESTAMP_FORMAT.parse("2000-11-02 00:00:00"));
    assertThat(query.list().size(), is(3));
  }

  @Override
  @Test
  public void testFind() throws Exception {
    String id = "Ao9dm8T0Ki";
    ServiceProvider found = serviceProviderDao.find(id);
    assertThat(found.getId(), is(id));
  }

  @Override
  public void testFindEntityNotFoundException() throws Exception {
    String id = "ZZZZZZZ999";
    ServiceProvider found = serviceProviderDao.find(id);
    assertThat(found, is(nullValue()));
  }

  @Override
  @Test
  public void testCreate() throws Exception {
    ServiceProvider serviceProvider = new ServiceProvider("Test Agency", "N", " ", null,
        BigDecimal.ZERO, "Horacio", "Ao9dm8T0Kj", "G.", "Dr.", 0, BigDecimal.ZERO, "Kiddie Care",
        (short) 0, (short) 0, " ", " ", " ", 0, (short) 0);
    ServiceProvider created = serviceProviderDao.create(serviceProvider);
    assertThat(created, is(serviceProvider));
  }

  @Override
  @Test
  public void testCreateExistingEntityException() throws Exception {
    thrown.expect(EntityExistsException.class);
    ServiceProvider serviceProvider = new ServiceProvider("Test Agency", "N", " ", null,
        BigDecimal.ZERO, "Horacio", "Ao9dm8T0Ki", "G.", "Dr.", 0, BigDecimal.ZERO, "Kiddie Care",
        (short) 0, (short) 0, " ", " ", " ", 0, (short) 0);
    serviceProviderDao.create(serviceProvider);
  }

  @Override
  @Test
  public void testDelete() throws Exception {
    String id = "Ao9dm8T0Ki";
    ServiceProvider deleted = serviceProviderDao.delete(id);
    assertThat(deleted.getId(), is(id));
  }


  @Override
  public void testDeleteEntityNotFoundException() throws Exception {
    String id = "ZZZZZZZ999";
    ServiceProvider deleted = serviceProviderDao.delete(id);
    assertThat(deleted, is(nullValue()));

  }

  @Override
  @Test
  public void testUpdate() throws Exception {

    ServiceProvider serviceProvider = new ServiceProvider("Test Agency", "N", " ", null,
        BigDecimal.ZERO, "Horacio", "Ao9dm8T0Ki", "Guest", "Dr.", 0, BigDecimal.ZERO, "Kiddie Care",
        (short) 0, (short) 0, " ", " ", " ", 0, (short) 0);
    ServiceProvider updated = serviceProviderDao.update(serviceProvider);
    assertThat(updated, is(serviceProvider));
  }

  @Override
  @Test
  public void testUpdateEntityNotFoundException() throws Exception {
    thrown.expect(EntityNotFoundException.class);

    ServiceProvider serviceProvider = new ServiceProvider("Test Agency", "N", " ", null,
        BigDecimal.ZERO, "Horacio", "Ao9dm8T0Kk", "G.", "Dr.", 0, BigDecimal.ZERO, "Kiddie Care",
        (short) 0, (short) 0, " ", " ", " ", 0, (short) 0);
    serviceProviderDao.update(serviceProvider);
  }

}
