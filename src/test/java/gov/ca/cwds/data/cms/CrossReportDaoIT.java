package gov.ca.cwds.data.cms;

import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.hamcrest.junit.ExpectedException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
// import org.hibernate.mapping.PrimaryKey;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;

import gov.ca.cwds.data.junit.template.DaoTestTemplate;
import gov.ca.cwds.data.persistence.cms.CrossReport;

/**
 * @author CWDS API Team
 *
 */
public class CrossReportDaoIT implements DaoTestTemplate {
  private static SessionFactory sessionFactory;
  private static CrossReportDao crossreportDao;
  private Session session;

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  /**
   * 
   */
  @BeforeClass
  public static void beforeClass() {
    sessionFactory = new Configuration().configure().buildSessionFactory();
    crossreportDao = new CrossReportDao(sessionFactory);
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
    CrossReport found = crossreportDao.find("7wviAIk0AB");
    assertThat(found.getThirdId(), is(equalTo("7wviAIk0AB")));
  }

  @Override
  @Test
  public void testFindEntityNotFoundException() throws Exception {
    String id = "ZZZZZZZ999";
    CrossReport found = crossreportDao.find(id);
    assertThat(found, is(nullValue()));
  }

  @Override
  @Test
  public void testCreate() throws Exception {
    CrossReport crossreport = new CrossReport("925q4As0AC", "7wviAIk0AC", (short) 2094, "N", "N",
        null, "  ", 0, BigDecimal.ZERO, null, " ", " ", "925q4As0AC", "0AC", " ", " ", " ", "34",
        "N", "N", "N");
    CrossReport created = crossreportDao.create(crossreport);
    assertThat(created, is(crossreport));

  }

  @Override
  @Test(expected = EntityExistsException.class)
  public void testCreateExistingEntityException() throws Exception {
    CrossReport crossreport = new CrossReport("Aj20cK10WS", "CVDUfmj0WS", (short) 2094, "N", "N",
        null, "  ", 0, BigDecimal.ZERO, null, " ", " ", "925q4As0AC", "0AC", " ", " ", " ", "34",
        "N", "N", "N");
    crossreportDao.create(crossreport);
  }

  @Override
  @Test
  public void testDelete() throws Exception {
    String thirdId = "FsMh09h00J";
    CrossReport delete = crossreportDao.delete(thirdId);
    assertThat(delete.getThirdId(), is(thirdId));
  }

  @Override
  @Test
  public void testDeleteEntityNotFoundException() throws Exception {
    String id = "9999999ZZZ";
    CrossReport deleted = crossreportDao.delete(id);
    assertThat(deleted, is(nullValue()));
  }

  @Override
  @Test
  public void testUpdate() throws Exception {
    CrossReport crossreport = new CrossReport("Aj20cK10WS", "CVDUfmj0WS", (short) 2094, "N", "N",
        null, "  ", 0, BigDecimal.ZERO, null, " ", " ", "925q4As0AC", "0AC", " ", " ", " ", "34",
        "N", "N", "N");
    CrossReport updated = crossreportDao.update(crossreport);
    assertThat(updated, is(crossreport));
  }

  @Override
  @Test(expected = EntityNotFoundException.class)
  public void testUpdateEntityNotFoundException() throws Exception {
    CrossReport crossreport = new CrossReport("ZZ20cK10WS", "ZZDUfmj0WS", (short) 2094, "N", "N",
        null, "  ", 0, BigDecimal.ZERO, null, " ", " ", "925q4As0AC", "0AC", " ", " ", " ", "34",
        "N", "N", "N");
    crossreportDao.update(crossreport);
  }

  @Override
  public void testFindAllNamedQueryExist() throws Exception {

  }

  @Override
  public void testFindAllReturnsCorrectList() throws Exception {

  }

}
