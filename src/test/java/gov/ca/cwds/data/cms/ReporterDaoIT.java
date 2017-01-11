package gov.ca.cwds.data.cms;

import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.hamcrest.junit.ExpectedException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import gov.ca.cwds.data.junit.template.DaoTestTemplate;
import gov.ca.cwds.data.persistence.cms.Reporter;

public class ReporterDaoIT implements DaoTestTemplate {
  private SessionFactory sessionFactory;
  private ReporterDao reporterDao;

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Override
  @Before
  public void setup() {
    sessionFactory = new Configuration().configure().buildSessionFactory();
    sessionFactory.getCurrentSession().beginTransaction();
    reporterDao = new ReporterDao(sessionFactory);
  }

  @Override
  @After
  public void teardown() {
    sessionFactory.close();
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
  public void testFindAllNamedQueryExist() throws Exception {
    // TODO Auto-generated method stub

  }

  @Override
  public void testFindAllReturnsCorrectList() throws Exception {
    // TODO Auto-generated method stub

  }
}
