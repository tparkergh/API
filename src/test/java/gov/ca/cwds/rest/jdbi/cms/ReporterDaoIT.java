package gov.ca.cwds.rest.jdbi.cms;

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

import gov.ca.cwds.rest.api.persistence.cms.Reporter;

public class ReporterDaoIT {
  private SessionFactory sessionFactory;
  private ReporterDao reporterDao;

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Before
  public void setup() {
    sessionFactory = new Configuration().configure().buildSessionFactory();
    sessionFactory.getCurrentSession().beginTransaction();
    reporterDao = new ReporterDao(sessionFactory);
  }

  @After
  public void teardown() {
    sessionFactory.close();
  }

  @Test
  public void testFind() {
    String id = "AbiQCgu0Hj";
    Reporter found = reporterDao.find(id);
    assertThat(found.getReferralId(), is(id));
  }

  @Test
  public void testCreate() {
    Reporter reporter = new Reporter("AbiQCgu0AA", "  ", "City", (short) 591, (short) 0, "N", null,
        " ", null, "N", "Fred", "Reporter", "N", 0, BigDecimal.valueOf(0), " ", " ",
        BigDecimal.valueOf(0L), 0, (short) 1828, "Street", "12345", " ", new Integer(95845),
        "AbiQCgu0Hk", (short) 0, "51");
    Reporter created = reporterDao.create(reporter);
    assertThat(created, is(reporter));

  }

  @Test
  public void testCreateExistingEntityException() {
    thrown.expect(EntityExistsException.class);
    Reporter reporter = new Reporter("AbiQCgu0Hj", "  ", "City", (short) 591, (short) 0, "N", null,
        " ", null, "N", "Fred", "Reporter", "N", 0, BigDecimal.valueOf(0), " ", " ",
        BigDecimal.valueOf(0L), 0, (short) 1828, "Street", "12345", " ", new Integer(95845),
        "AbiQCgu0Hk", (short) 0, "51");
    reporterDao.create(reporter);
  }

  @Test
  public void testDelete() {
    String id = "AbiQCgu0Hj";
    Reporter deleted = reporterDao.delete(id);
    assertThat(deleted.getReferralId(), is(id));
  }

  @Test
  public void testUpdate() {
    Reporter reporter = new Reporter("AbiQCgu0Hj", "  ", "City", (short) 591, (short) 0, "N", null,
        " ", null, "N", "Fred", "Reporter", "N", 0, BigDecimal.valueOf(0), " ", " ",
        BigDecimal.valueOf(0L), 0, (short) 1828, "Street", "12345", " ", new Integer(95845),
        "AbiQCgu0Hk", (short) 0, "51");
    Reporter updated = reporterDao.update(reporter);
    assertThat(reporter, is(updated));
  }

  @Test
  public void testUpdateEntityNotFoundException() {
    thrown.expect(EntityNotFoundException.class);
    Reporter reporter = new Reporter("ZZZZZZ", "  ", "City", (short) 591, (short) 0, "N", null, " ",
        null, "N", "Fred", "Reporter", "N", 0, BigDecimal.valueOf(0), " ", " ",
        BigDecimal.valueOf(0L), 0, (short) 1828, "Street", "12345", " ", new Integer(95845),
        "AbiQCgu0Hk", (short) 0, "51");
    reporterDao.update(reporter);
  }

}
