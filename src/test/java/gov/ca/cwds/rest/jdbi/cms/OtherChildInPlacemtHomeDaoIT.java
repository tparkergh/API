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

import gov.ca.cwds.rest.api.persistence.cms.OtherChildInPlacemtHome;

public class OtherChildInPlacemtHomeDaoIT {
  private static final DateFormat TIMESTAMP_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
  private DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
  private String birthDateString = "2002-06-25";



  @Rule
  public ExpectedException thrown = ExpectedException.none();

  private static OtherChildInPlacemtHomeDao otherChildInPlacemtHomeDao;
  private static SessionFactory sessionFactory;
  private Session session;

  @BeforeClass
  public static void beforeClass() {
    sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
    otherChildInPlacemtHomeDao = new OtherChildInPlacemtHomeDao(sessionFactory);
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
    Query query = session
        .getNamedQuery("gov.ca.cwds.rest.api.persistence.cms.OtherChildInPlacemtHome.findAll");
    assertThat(query, is(notNullValue()));
  }

  @Test
  public void testFindAllReturnsCorrectList() {
    Query query = session
        .getNamedQuery("gov.ca.cwds.rest.api.persistence.cms.OtherChildInPlacemtHome.findAll");
    assertThat(query.list().size(), is(2));
  }

  @Test
  public void testfindAllUpdatedAfterNamedQueryExists() throws Exception {
    Query query = session.getNamedQuery(
        "gov.ca.cwds.rest.api.persistence.cms.OtherChildInPlacemtHome.findAllUpdatedAfter");
    assertThat(query, is(notNullValue()));
  }

  @Test
  public void testfindAllUpdatedAfterReturnsCorrectList() throws Exception {
    Query query = session
        .getNamedQuery(
            "gov.ca.cwds.rest.api.persistence.cms.OtherChildInPlacemtHome.findAllUpdatedAfter")
        .setDate("after", TIMESTAMP_FORMAT.parse("2001-01-01 00:00:00"));
    assertThat(query.list().size(), is(2));
  }

  @Test
  public void testFind() {
    String id = "PT9kQgI0Mq";
    OtherChildInPlacemtHome found = otherChildInPlacemtHomeDao.find(id);
    assertThat(found.getId(), is(id));
  }

  @Test
  public void testCreate() throws Exception {
    Date birthDate = df.parse(birthDateString);
    OtherChildInPlacemtHome otherChildInPlacemtHome = new OtherChildInPlacemtHome(BigDecimal.ZERO,
        birthDate, "0M401G20Mq", "F", "PT9kQgI0Mr", "Milly W.");
    OtherChildInPlacemtHome created = otherChildInPlacemtHomeDao.create(otherChildInPlacemtHome);
    assertThat(created, is(otherChildInPlacemtHome));
  }

  @Test
  public void testCreateExistingEntityException() throws Exception {
    thrown.expect(EntityExistsException.class);
    Date birthDate = df.parse(birthDateString);
    OtherChildInPlacemtHome otherChildInPlacemtHome = new OtherChildInPlacemtHome(BigDecimal.ZERO,
        birthDate, "0M401G20Mq", "F", "PT9kQgI0Mq", "Milly W.");
    otherChildInPlacemtHomeDao.create(otherChildInPlacemtHome);
  }

  @Test
  public void testDelete() {
    String id = "PT9kQgI0Mq";
    OtherChildInPlacemtHome deleted = otherChildInPlacemtHomeDao.delete(id);
    assertThat(deleted.getId(), is(id));
  }

  @Test
  public void testUpdate() throws Exception {

    Date birthDate = df.parse(birthDateString);
    OtherChildInPlacemtHome otherChildInPlacemtHome = new OtherChildInPlacemtHome(BigDecimal.ZERO,
        birthDate, "0M401G20Mq", "F", "PT9kQgI0Mq", "Milly G.");
    OtherChildInPlacemtHome updated = otherChildInPlacemtHomeDao.update(otherChildInPlacemtHome);
    assertThat(updated, is(otherChildInPlacemtHome));
  }

  @Test
  public void testUpdateEntityNotFoundException() throws Exception {
    thrown.expect(EntityNotFoundException.class);

    Date birthDate = df.parse(birthDateString);
    OtherChildInPlacemtHome otherChildInPlacemtHome = new OtherChildInPlacemtHome(BigDecimal.ZERO,
        birthDate, "0M401G20Mq", "F", "PT9kQgI0Ms", "Milly W.");
    otherChildInPlacemtHomeDao.update(otherChildInPlacemtHome);
  }
}
