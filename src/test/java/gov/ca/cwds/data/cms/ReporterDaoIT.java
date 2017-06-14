package gov.ca.cwds.data.cms;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

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
import gov.ca.cwds.data.persistence.cms.Reporter;

/**
 * @author CWDS API Team
 *
 */
public class ReporterDaoIT implements DaoTestTemplate {
  private static final DateFormat TIMESTAMP_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

  private static SessionFactory sessionFactory;
  private static ReporterDao reporterDao;
  private Session session;

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  /**
   * 
   */
  @BeforeClass
  public static void beforeClass() {
    sessionFactory = new Configuration().configure().buildSessionFactory();
    reporterDao = new ReporterDao(sessionFactory);
  }

  /**
   * 
   */
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
  public void testFind() throws Exception {
    String id = "AbiQCgu0Hj";
    Reporter found = reporterDao.find(id);
    assertThat(found.getReferralId(), is(id));
  }

  @Override
  @Test
  public void testFindEntityNotFoundException() throws Exception {
    String id = "ZZZZZZZ999";
    Reporter found = reporterDao.find(id);
    assertThat(found, is(nullValue()));
  }

  @Override
  @Test
  public void testCreate() throws Exception {
    Reporter reporter = new Reporter("AbiQCgu0AA", "  ", "City", (short) 591, (short) 0, "N", null,
        " ", null, "N", "Fred", "Reporter", "N", 0, BigDecimal.valueOf(0), " ", " ",
        BigDecimal.valueOf(0L), 0, (short) 1828, "Street", "12345", " ", new Integer(95845),
        "AbiQCgu0Hk", (short) 0, "51");
    Reporter created = reporterDao.create(reporter);
    assertThat(created, is(reporter));
  }

  @Override
  @Test
  public void testCreateExistingEntityException() throws Exception {
    thrown.expect(EntityExistsException.class);
    Reporter reporter = new Reporter("AbiQCgu0Hj", "  ", "City", (short) 591, (short) 0, "N", null,
        " ", null, "N", "Fred", "Reporter", "N", 0, BigDecimal.valueOf(0), " ", " ",
        BigDecimal.valueOf(0L), 0, (short) 1828, "Street", "12345", " ", new Integer(95845),
        "AbiQCgu0Hk", (short) 0, "51");
    reporterDao.create(reporter);
  }

  @Override
  @Test
  public void testDelete() throws Exception {
    String id = "AbiQCgu0Hj";
    Reporter deleted = reporterDao.delete(id);
    assertThat(deleted.getReferralId(), is(id));
  }

  @Override
  @Test
  public void testDeleteEntityNotFoundException() throws Exception {
    String id = "ZZZZZZZ999";
    Reporter deleted = reporterDao.delete(id);
    assertThat(deleted, is(nullValue()));
  }

  @Override
  @Test
  public void testUpdate() throws Exception {
    Reporter reporter = new Reporter("AbiQCgu0Hj", "  ", "City", (short) 591, (short) 0, "N", null,
        " ", null, "N", "Fred", "Reporter", "N", 0, BigDecimal.valueOf(0), " ", " ",
        BigDecimal.valueOf(0L), 0, (short) 1828, "Street", "12345", " ", new Integer(95845),
        "AbiQCgu0Hk", (short) 0, "51");
    Reporter updated = reporterDao.update(reporter);
    assertThat(reporter, is(updated));
  }

  @Override
  @Test
  public void testUpdateEntityNotFoundException() throws Exception {
    thrown.expect(EntityNotFoundException.class);
    Reporter reporter = new Reporter("ZZZZZZ", "  ", "City", (short) 591, (short) 0, "N", null, " ",
        null, "N", "Fred", "Reporter", "N", 0, BigDecimal.valueOf(0), " ", " ",
        BigDecimal.valueOf(0L), 0, (short) 1828, "Street", "12345", " ", new Integer(95845),
        "AbiQCgu0Hk", (short) 0, "51");
    reporterDao.update(reporter);
  }

  @Override
  @Test
  public void testFindAllNamedQueryExist() throws Exception {
    Query query = session.getNamedQuery("gov.ca.cwds.data.persistence.cms.Reporter.findAll");
    assertThat(query, is(notNullValue()));
  }

  @Override
  @Test
  public void testFindAllReturnsCorrectList() throws Exception {
    Query query = session.getNamedQuery("gov.ca.cwds.data.persistence.cms.Reporter.findAll");
    @SuppressWarnings("unchecked")
    final List<Reporter> list = query.list();
    System.out.println("size of query list is: " + list.size());
    for (Reporter c : list) {
      System.out.println("id " + c.getPrimaryKey() + " " + c.getLastName());
    }
    assertThat(query.list().size(), is(0));
  }

  // TODO: #138438305: move to jobs project.
  // @SuppressWarnings("javadoc")
  // @Test
  // public void testFindAllUpdatedAfterNamedQueryExists() throws Exception {
  // Query query =
  // session.getNamedQuery("gov.ca.cwds.data.persistence.cms.Reporter.findAllUpdatedAfter");
  // assertThat(query, is(notNullValue()));
  // }

  // TODO: #138438305: move to jobs project.
  // @SuppressWarnings("javadoc")
  // @Test
  // public void testFindAllUpdatedAfterReturnsCorrectList() throws Exception {
  // Query query =
  // session.getNamedQuery("gov.ca.cwds.data.persistence.cms.Reporter.findAllUpdatedAfter")
  // .setDate("after", TIMESTAMP_FORMAT.parse("1997-01-02 00:00:00"));
  //
  // @SuppressWarnings("unchecked")
  // final List<Reporter> list = query.list();
  // System.out.println("size of query list is: " + list.size());
  // for (Reporter c : list) {
  // System.out.println("id " + c.getPrimaryKey() + " " + c.getLastName());
  // }
  // assertThat(query.list().size(), is(1));
  // }

}
