package gov.ca.cwds.rest.jdbi.cms;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

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

import gov.ca.cwds.rest.api.persistence.cms.OtherAdultInPlacemtHome;

public class OtherAdultInPlacemtHomeDaoIT {
  private static final DateFormat TIMESTAMP_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
  private DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
  private String birthDateString = "1929-09-09";
  private String startDateString = "1990-06-01";
  private String endDateString = "1999-06-05";


  @Rule
  public ExpectedException thrown = ExpectedException.none();

  private static OtherAdultInPlacemtHomeDao otherAdultInPlacemtHomeDao;
  private static SessionFactory sessionFactory;
  private Session session;

  @BeforeClass
  public static void beforeClass() {
    sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
    otherAdultInPlacemtHomeDao = new OtherAdultInPlacemtHomeDao(sessionFactory);
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
        .getNamedQuery("gov.ca.cwds.rest.api.persistence.cms.OtherAdultInPlacemtHome.findAll");
    assertThat(query, is(notNullValue()));
  }

  @Test
  public void testFindAllReturnsCorrectList() {
    Query query = session
        .getNamedQuery("gov.ca.cwds.rest.api.persistence.cms.OtherAdultInPlacemtHome.findAll");
    assertThat(query.list().size(), is(2));
  }

  @Test
  public void testfindAllUpdatedAfterNamedQueryExists() throws Exception {
    Query query = session.getNamedQuery(
        "gov.ca.cwds.rest.api.persistence.cms.OtherAdultInPlacemtHome.findAllUpdatedAfter");
    assertThat(query, is(notNullValue()));
  }

  @Test
  public void testfindAllUpdatedAfterReturnsCorrectList() throws Exception {
    Query query = session
        .getNamedQuery(
            "gov.ca.cwds.rest.api.persistence.cms.OtherAdultInPlacemtHome.findAllUpdatedAfter")
        .setDate("after", TIMESTAMP_FORMAT.parse("2002-10-02 00:00:00"));
    assertThat(query.list().size(), is(2));
  }

  @Test
  public void testFind() {
    String id = "AhfOGkK0QO";
    OtherAdultInPlacemtHome found = otherAdultInPlacemtHomeDao.find(id);
    assertThat(found.getId(), is(id));
  }

  @Test
  public void testCreate() throws Exception {
    Date birthDate = df.parse(birthDateString);
    Date startDate = df.parse(startDateString);
    Date endDate = df.parse(endDateString);
    OtherAdultInPlacemtHome otherAdultInPlacemtHome =
        new OtherAdultInPlacemtHome(birthDate, "test description", endDate, "TUB5YO1q1x", "M",
            "AhfOGkK0Qp", null, "Granpa B.", "O", null, null, startDate);
    OtherAdultInPlacemtHome created = otherAdultInPlacemtHomeDao.create(otherAdultInPlacemtHome);
    assertThat(created, is(otherAdultInPlacemtHome));
  }

  @Test
  public void testCreateExistingEntityException() throws Exception {
    thrown.expect(EntityExistsException.class);
    Date birthDate = df.parse(birthDateString);
    Date startDate = df.parse(startDateString);
    Date endDate = df.parse(endDateString);
    OtherAdultInPlacemtHome otherAdultInPlacemtHome =
        new OtherAdultInPlacemtHome(birthDate, "test description", endDate, "TUB5YO1q1x", "M",
            "AhfOGkK0QO", null, "Granpa B.", "O", null, null, startDate);
    otherAdultInPlacemtHomeDao.create(otherAdultInPlacemtHome);
  }

  @Test
  public void testDelete() {
    String id = "AhfOGkK0QO";
    OtherAdultInPlacemtHome deleted = otherAdultInPlacemtHomeDao.delete(id);
    assertThat(deleted.getId(), is(id));
  }

  @Test
  public void testUpdate() throws Exception {

    Date birthDate = df.parse(birthDateString);
    Date startDate = df.parse(startDateString);
    Date endDate = df.parse(endDateString);
    OtherAdultInPlacemtHome otherAdultInPlacemtHome =
        new OtherAdultInPlacemtHome(birthDate, "test description2", endDate, "TUB5YO1q1x", "M",
            "AhfOGkK0QO", null, "Granpa B.", "O", null, null, startDate);
    OtherAdultInPlacemtHome updated = otherAdultInPlacemtHomeDao.update(otherAdultInPlacemtHome);
    assertThat(updated, is(otherAdultInPlacemtHome));
  }

  @Test
  public void testUpdateEntityNotFoundException() throws Exception {
    thrown.expect(EntityNotFoundException.class);

    Date birthDate = df.parse(birthDateString);
    Date startDate = df.parse(startDateString);
    Date endDate = df.parse(endDateString);
    OtherAdultInPlacemtHome otherAdultInPlacemtHome =
        new OtherAdultInPlacemtHome(birthDate, "test description", endDate, "TUB5YO1q1x", "M",
            "AhfOGkK0QOr", null, "Granpa B.", "O", null, null, startDate);
    otherAdultInPlacemtHomeDao.update(otherAdultInPlacemtHome);
  }
}
