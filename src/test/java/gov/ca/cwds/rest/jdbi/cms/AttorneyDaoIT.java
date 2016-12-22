package gov.ca.cwds.rest.jdbi.cms;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

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

import gov.ca.cwds.rest.api.persistence.cms.Attorney;

/**
 * @author Tabpcenc1
 *
 */
public class AttorneyDaoIT {
  private static final DateFormat TIMESTAMP_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
  private DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
  private String endDateString = "1998-08-01";


  /**
   * 
   */
  @Rule
  public ExpectedException thrown = ExpectedException.none();

  private static AttorneyDao attorneyDao;
  private static SessionFactory sessionFactory;
  private Session session;

  @SuppressWarnings("javadoc")
  @BeforeClass
  public static void beforeClass() {
    sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
    attorneyDao = new AttorneyDao(sessionFactory);
  }

  @SuppressWarnings("javadoc")
  @AfterClass
  public static void afterClass() {
    sessionFactory.close();
  }

  @SuppressWarnings("javadoc")
  @Before
  public void setup() {
    session = sessionFactory.getCurrentSession();
    session.beginTransaction();
  }

  @SuppressWarnings("javadoc")
  @After
  public void tearddown() {
    session.getTransaction().rollback();
  }

  @SuppressWarnings("javadoc")
  @Test
  public void testFindAllNamedQueryExists() throws Exception {
    Query query = session.getNamedQuery("gov.ca.cwds.rest.api.persistence.cms.Attorney.findAll");
    assertThat(query, is(notNullValue()));
  }

  /**
   * find using query test
   */
  @Test
  public void testFindAllReturnsCorrectList() {
    Query query = session.getNamedQuery("gov.ca.cwds.rest.api.persistence.cms.Attorney.findAll");
    assertThat(query.list().size(), is(2));
  }

  /**
   * @throws Exception
   */
  @Test
  public void testfindAllUpdatedAfterNamedQueryExists() throws Exception {
    Query query =
        session.getNamedQuery("gov.ca.cwds.rest.api.persistence.cms.Attorney.findAllUpdatedAfter");
    assertThat(query, is(notNullValue()));
  }

  /**
   * @throws Exception
   */
  @Test
  public void testfindAllUpdatedAfterReturnsCorrectList() throws Exception {
    Query query =
        session.getNamedQuery("gov.ca.cwds.rest.api.persistence.cms.Attorney.findAllUpdatedAfter")
            .setDate("after", TIMESTAMP_FORMAT.parse("2000-11-02 00:00:00"));
    assertThat(query.list().size(), is(1));
  }

  @SuppressWarnings("javadoc")
  @Test
  public void testFind() {
    String id = "AcjOOPa0BU";
    Attorney found = attorneyDao.find(id);
    assertThat(found.getId(), is(id));
  }

  /**
   * test the create methods
   * 
   * @throws Exception
   */
  @Test
  public void testCreate() throws Exception {
    Attorney attorney = new Attorney("N", " ", " ", " ", null, null, BigDecimal.ZERO, "Lance",
        (short) 0, "A6IeoSu0Um", (short) 1274, "Johnson", 0, BigDecimal.ZERO, " ", " ", "Attorney",
        0, BigDecimal.ZERO, (short) 0, " ", " ", " ", 0, (short) 0);
    Attorney created = attorneyDao.create(attorney);
    assertThat(created, is(attorney));
  }

  @SuppressWarnings("javadoc")
  @Test
  public void testCreateExistingEntityException() throws Exception {
    thrown.expect(EntityExistsException.class);
    Attorney attorney = new Attorney("N", " ", " ", " ", null, null, BigDecimal.ZERO, "Lance",
        (short) 0, "AcjOOPa0BU", (short) 1274, "Johnson", 0, BigDecimal.ZERO, " ", " ", "Attorney",
        0, BigDecimal.ZERO, (short) 0, " ", " ", " ", 0, (short) 0);
    attorneyDao.create(attorney);
  }

  /**
   * test the delete method
   */
  @Test
  public void testDelete() {
    String id = "AcjOOPa0BU";
    Attorney deleted = attorneyDao.delete(id);
    assertThat(deleted.getId(), is(id));
  }

  /**
   * test the update method
   * 
   * @throws Exception
   */
  @Test
  public void testUpdate() throws Exception {
    Date endDate = df.parse(endDateString);
    Attorney attorney = new Attorney("N", " ", " ", " ", null, endDate, BigDecimal.ZERO, "Lance",
        (short) 0, "AcjOOPa0BU", (short) 1274, "Johnson", 0, BigDecimal.ZERO, " ", " ", "Attorney",
        0, BigDecimal.ZERO, (short) 0, " ", " ", " ", 0, (short) 0);
    Attorney updated = attorneyDao.update(attorney);
    assertThat(updated, is(attorney));
  }

  @SuppressWarnings("javadoc")
  @Test
  public void testUpdateEntityNotFoundException() throws Exception {
    thrown.expect(EntityNotFoundException.class);
    Date endDate = df.parse(endDateString);
    Attorney attorney = new Attorney("N", " ", " ", " ", null, endDate, BigDecimal.ZERO, "Lance",
        (short) 0, "AcjOOPa0By", (short) 1274, "Johnson", 0, BigDecimal.ZERO, " ", " ", "Attorney",
        0, BigDecimal.ZERO, (short) 0, " ", " ", " ", 0, (short) 0);
    attorneyDao.update(attorney);
  }
}
