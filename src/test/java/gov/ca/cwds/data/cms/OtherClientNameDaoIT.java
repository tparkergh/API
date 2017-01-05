package gov.ca.cwds.data.cms;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

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

import gov.ca.cwds.data.persistence.EmbeddableCompositeKey2;
import gov.ca.cwds.data.persistence.cms.OtherClientName;

public class OtherClientNameDaoIT {
  private static final DateFormat TIMESTAMP_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  private static OtherClientNameDao otherClientNameDao;
  private static SessionFactory sessionFactory;
  private Session session;

  @BeforeClass
  public static void beforeClass() {
    sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
    otherClientNameDao = new OtherClientNameDao(sessionFactory);
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
    Query query = session.getNamedQuery("gov.ca.cwds.data.persistence.cms.OtherClientName.findAll");
    assertThat(query, is(notNullValue()));
  }

  @Test
  public void testFindAllReturnsCorrectList() {
    Query query = session.getNamedQuery("gov.ca.cwds.data.persistence.cms.OtherClientName.findAll");
    assertThat(query.list().size(), greaterThanOrEqualTo(1));
  }

  @Test
  public void testfindAllUpdatedAfterNamedQueryExists() throws Exception {
    Query query = session
        .getNamedQuery("gov.ca.cwds.data.persistence.cms.OtherClientName.findAllUpdatedAfter");
    assertThat(query, is(notNullValue()));
  }

  @Test
  public void testfindAllUpdatedAfterReturnsCorrectList() throws Exception {
    Query query = session
        .getNamedQuery("gov.ca.cwds.data.persistence.cms.OtherClientName.findAllUpdatedAfter")
        .setDate("after", TIMESTAMP_FORMAT.parse("2000-01-01 00:00:00"));
    assertThat(query.list().size(), greaterThanOrEqualTo(1));
  }

  @Test
  public void testFind() {
    final String thirdId = "123";
    final String clientId = "1";
    OtherClientName found = otherClientNameDao.find(new EmbeddableCompositeKey2(clientId, thirdId));
    assertThat(found.getThirdId(), is(thirdId));
  }

  @Test
  public void testCreate() throws Exception {
    OtherClientName otherClientName =
        new OtherClientName("1", "Gregg", "Hill", "Brian", "1", (short) 1, "1", "125");
    otherClientNameDao.create(otherClientName);
  }

  @Test
  public void testCreateExistingEntityException() throws Exception {
    thrown.expect(EntityExistsException.class);
    OtherClientName otherClientName =
        new OtherClientName("1", "Gregg", "Hill", "Brian", "1", (short) 1, "1", "123");
    otherClientNameDao.create(otherClientName);
  }

  @Test
  public void testDelete() {
    final String thirdId = "123";
    final String clientId = "1";
    OtherClientName deleted =
        otherClientNameDao.delete(new EmbeddableCompositeKey2(clientId, thirdId));
    assertThat(deleted.getThirdId(), is(thirdId));
  }

  @Test
  public void testUpdate() throws Exception {
    OtherClientName otherClientName =
        new OtherClientName("1", "Gregory", "Hill", "Brian", "1", (short) 1, "1", "123");
    OtherClientName updated = otherClientNameDao.update(otherClientName);
    assertThat(updated, is(otherClientName));
  }

  @Test
  public void testUpdateEntityNotFoundException() throws Exception {
    thrown.expect(EntityNotFoundException.class);
    OtherClientName otherClientName =
        new OtherClientName("1", "Gregory", "Hill", "Brian", "1", (short) 1, "1", "ZZZ");
    otherClientNameDao.update(otherClientName);
  }
}
