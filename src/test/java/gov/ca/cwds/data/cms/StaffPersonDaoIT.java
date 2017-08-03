package gov.ca.cwds.data.cms;

import static org.hamcrest.Matchers.nullValue;
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

import gov.ca.cwds.data.junit.template.DaoTestTemplate;
import gov.ca.cwds.data.persistence.cms.StaffPerson;

/**
 * @author CWDS API Team
 *
 */
public class StaffPersonDaoIT implements DaoTestTemplate {
  @SuppressWarnings("javadoc")
  @Rule
  public ExpectedException thrown = ExpectedException.none();

  private static StaffPersonDao staffPersonDao;
  private static SessionFactory sessionFactory;
  private Session session;

  @SuppressWarnings("javadoc")
  @BeforeClass
  public static void beforeClass() {
    sessionFactory = new Configuration().configure().buildSessionFactory();
    staffPersonDao = new StaffPersonDao(sessionFactory);
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
  public void testFind() throws Exception {
    String id = "q1p";
    StaffPerson found = staffPersonDao.find(id);
    assertThat(found.getId(), is(id));
  }

  @Override
  @Test
  public void testFindEntityNotFoundException() throws Exception {
    String id = "ZZZ";
    StaffPerson found = staffPersonDao.find(id);
    assertThat(found, is(nullValue()));
  }

  @Override
  @Test
  public void testCreate() throws Exception {
    // TODO: id may already exist in database.
    StaffPerson staffPerson = new StaffPerson("ZZp", null, "External Interface",
        "external interface", "SCXCIN7", " ", "", BigDecimal.valueOf(9165672100L), 0, null, "    ",
        "N", "MIZN02k00E", "  ", "    ", "99", "N", "3XPCP92q38", null);
    StaffPerson created = staffPersonDao.create(staffPerson);
    assertThat(created, is(staffPerson));
  }

  @Override
  @Test
  public void testCreateExistingEntityException() throws Exception {
    thrown.expect(EntityExistsException.class);
    StaffPerson staffPerson = new StaffPerson("q1p", null, "External Interface",
        "external interface", "SCXCIN7", " ", "", BigDecimal.valueOf(9165672100L), 0, null, "    ",
        "N", "MIZN02k00E", "  ", "    ", "99", "N", "3XPCP92q38", null);
    staffPersonDao.create(staffPerson);
  }

  @Override
  @Test
  public void testDelete() throws Exception {
    String id = "q1p";
    StaffPerson deleted = staffPersonDao.delete(id);
    assertThat(deleted.getId(), is(id));
  }

  @Override
  @Test
  public void testDeleteEntityNotFoundException() throws Exception {
    // TODO: depends on an id not created by this unit test.
    String id = "ZZZ";
    StaffPerson deleted = staffPersonDao.delete(id);
    assertThat(deleted, is(nullValue()));
  }

  @Override
  @Test
  public void testUpdate() throws Exception {
    StaffPerson staffPerson = new StaffPerson("q1p", null, "External Interface",
        "external interface", "SCXCIN7", " ", "", BigDecimal.valueOf(9165672100L), 0, null, "    ",
        "N", "MIZN02k00E", "  ", "    ", "99", "N", "3XPCP92q38", null);
    StaffPerson updated = staffPersonDao.update(staffPerson);
    assertThat(updated, is(staffPerson));
  }

  @Override
  @Test
  public void testUpdateEntityNotFoundException() throws Exception {
    // TODO: depends on an id not created by this unit test.
    thrown.expect(EntityNotFoundException.class);
    StaffPerson staffPerson = new StaffPerson("zzp", null, "External Interface",
        "external interface", "SCXCIN7", " ", "", BigDecimal.valueOf(9165672100L), 0, null, "    ",
        "N", "MIZN02k00E", "  ", "    ", "99", "N", "3XPCP92q38", null);
    staffPersonDao.update(staffPerson);
  }

  @Override
  public void testFindAllNamedQueryExist() throws Exception {

  }

  @Override
  public void testFindAllReturnsCorrectList() throws Exception {

  }

}
