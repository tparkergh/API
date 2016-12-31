package gov.ca.cwds.data.cms;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.hamcrest.junit.ExpectedException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;

import gov.ca.cwds.data.cms.StaffPersonDao;
import gov.ca.cwds.data.persistence.cms.StaffPerson;

public class StaffPersonDaoIT {
  @Rule
  public ExpectedException thrown = ExpectedException.none();

  private static StaffPersonDao staffPersonDao;
  private static SessionFactory sessionFactory;
  private Session session;

  @BeforeClass
  public static void beforeClass() {
    sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
    staffPersonDao = new StaffPersonDao(sessionFactory);
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
  public void testFind() {
    String id = "q1p";
    StaffPerson found = staffPersonDao.find(id);
    assertThat(found.getId(), is(id));
  }

  @Test
  public void testCreate() {
    StaffPerson staffPerson = new StaffPerson("q1k", null, "External Interface",
        "external interface", "SCXCIN7", " ", "", BigDecimal.valueOf(9165672100L), 0, null, "    ",
        "N", "MIZN02k00E", "  ", "    ", "99", "N", "3XPCP92q38", null);
    StaffPerson created = staffPersonDao.create(staffPerson);
    assertThat(created, is(staffPerson));
  }

  @Test
  public void testCreateExistingEntityException() {
    thrown.expect(EntityExistsException.class);
    StaffPerson staffPerson = new StaffPerson("q1p", null, "External Interface",
        "external interface", "SCXCIN7", " ", "", BigDecimal.valueOf(9165672100L), 0, null, "    ",
        "N", "MIZN02k00E", "  ", "    ", "99", "N", "3XPCP92q38", null);
    staffPersonDao.create(staffPerson);
  }

  @Test
  public void testDelete() {
    String id = "q1p";
    StaffPerson deleted = staffPersonDao.delete(id);
    assertThat(deleted.getId(), is(id));
  }

  @Test
  public void testUpdate() {
    StaffPerson staffPerson = new StaffPerson("q1p", null, "External Interface",
        "external interface", "SCXCIN7", " ", "", BigDecimal.valueOf(9165672100L), 0, null, "    ",
        "N", "MIZN02k00E", "  ", "    ", "99", "N", "3XPCP92q38", null);
    StaffPerson updated = staffPersonDao.update(staffPerson);
    assertThat(updated, is(staffPerson));
  }

  @Test
  public void testUpdateEntityNotFoundException() {
    thrown.expect(EntityNotFoundException.class);
    StaffPerson staffPerson = new StaffPerson("q1q", null, "External Interface",
        "external interface", "SCXCIN7", " ", "", BigDecimal.valueOf(9165672100L), 0, null, "    ",
        "N", "MIZN02k00E", "  ", "    ", "99", "N", "3XPCP92q38", null);
    staffPersonDao.update(staffPerson);
  }
}
