package gov.ca.cwds.data.cms;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

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

import gov.ca.cwds.data.junit.template.DaoTestTemplate;
import gov.ca.cwds.data.persistence.cms.OtherAdultInPlacemtHome;

/**
 * @author CWDS API Team
 */
public class OtherAdultInPlacemtHomeDaoIT implements DaoTestTemplate {

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

  @SuppressWarnings("javadoc")
  @BeforeClass
  public static void beforeClass() {
    sessionFactory = new Configuration().configure().buildSessionFactory();
    otherAdultInPlacemtHomeDao = new OtherAdultInPlacemtHomeDao(sessionFactory);
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
  public void testFindAllNamedQueryExist() throws Exception {
    Query query =
        session.getNamedQuery("gov.ca.cwds.data.persistence.cms.OtherAdultInPlacemtHome.findAll");
    assertThat(query, is(notNullValue()));
  }

  @Override
  @Test
  public void testFindAllReturnsCorrectList() throws Exception {
    Query query =
        session.getNamedQuery("gov.ca.cwds.data.persistence.cms.OtherAdultInPlacemtHome.findAll");
    assertThat(query.list().size(), is(2));
  }

  // @SuppressWarnings("javadoc")
  // @Test
  // public void testfindAllUpdatedAfterNamedQueryExists() throws Exception {
  // Query query = session.getNamedQuery(
  // "gov.ca.cwds.data.persistence.cms.OtherAdultInPlacemtHome.findAllUpdatedAfter");
  // assertThat(query, is(notNullValue()));
  // }
  //
  // @SuppressWarnings("javadoc")
  // @Test
  // public void testfindAllUpdatedAfterReturnsCorrectList() throws Exception {
  // Query query = session
  // .getNamedQuery(
  // "gov.ca.cwds.data.persistence.cms.OtherAdultInPlacemtHome.findAllUpdatedAfter")
  // .setDate("after", TIMESTAMP_FORMAT.parse("2002-10-02 00:00:00"));
  // final List<OtherAdultInPlacemtHome> list = query.list();
  // System.out.println("size of query list is: " + list.size());
  // for (OtherAdultInPlacemtHome c : list) {
  // System.out.println("id " + c.getPrimaryKey() + " " + c.getLastName());
  // }
  // assertThat(query.list().size(), is(2));
  // }

  @Override
  @Test
  public void testFind() throws Exception {
    String id = "AhfOGkK0QO";
    OtherAdultInPlacemtHome found = otherAdultInPlacemtHomeDao.find(id);
    assertThat(found.getId(), is(id));
  }

  @Override
  @Test
  public void testFindEntityNotFoundException() throws Exception {
    String id = "ZZZZZZ999";
    OtherAdultInPlacemtHome found = otherAdultInPlacemtHomeDao.find(id);
    assertThat(found, is(nullValue()));

  }

  @Override
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

  @Override
  @Test(expected = EntityExistsException.class)
  public void testCreateExistingEntityException() throws Exception {
    Date birthDate = df.parse(birthDateString);
    Date startDate = df.parse(startDateString);
    Date endDate = df.parse(endDateString);
    OtherAdultInPlacemtHome otherAdultInPlacemtHome =
        new OtherAdultInPlacemtHome(birthDate, "test description", endDate, "TUB5YO1q1x", "M",
            "AhfOGkK0QO", null, "Granpa B.", "O", null, null, startDate);
    otherAdultInPlacemtHomeDao.create(otherAdultInPlacemtHome);
  }

  @Override
  @Test
  public void testDelete() throws Exception {
    String id = "AhfOGkK0QO";
    OtherAdultInPlacemtHome deleted = otherAdultInPlacemtHomeDao.delete(id);
    assertThat(deleted.getId(), is(id));
  }

  @Override
  @Test
  public void testDeleteEntityNotFoundException() throws Exception {
    String id = "ZZZZZZZ999";
    OtherAdultInPlacemtHome deleted = otherAdultInPlacemtHomeDao.delete(id);
    assertThat(deleted, is(nullValue()));
  }

  @Override
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

  @Override
  @Test(expected = EntityNotFoundException.class)
  public void testUpdateEntityNotFoundException() throws Exception {

    Date birthDate = df.parse(birthDateString);
    Date startDate = df.parse(startDateString);
    Date endDate = df.parse(endDateString);
    OtherAdultInPlacemtHome otherAdultInPlacemtHome =
        new OtherAdultInPlacemtHome(birthDate, "test description", endDate, "TUB5YO1q1x", "M",
            "AhfOGkK0QOr", null, "Granpa B.", "O", null, null, startDate);
    otherAdultInPlacemtHomeDao.update(otherAdultInPlacemtHome);
  }

}
