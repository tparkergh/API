package gov.ca.cwds.data.cms;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

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

import gov.ca.cwds.data.junit.template.DaoTestTemplate;
import gov.ca.cwds.data.persistence.cms.OtherChildInPlacemtHome;

/**
 * @author CWDS API Team
 *
 */
public class OtherChildInPlacemtHomeDaoIT implements DaoTestTemplate {
  private static final DateFormat TIMESTAMP_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
  private DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
  private String birthDateString = "2002-06-25";

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  private static OtherChildInPlacemtHomeDao otherChildInPlacemtHomeDao;
  private static SessionFactory sessionFactory;
  private Session session;

  @SuppressWarnings("javadoc")
  @BeforeClass
  public static void beforeClass() {
    sessionFactory = new Configuration().configure().buildSessionFactory();
    otherChildInPlacemtHomeDao = new OtherChildInPlacemtHomeDao(sessionFactory);
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
        session.getNamedQuery("gov.ca.cwds.data.persistence.cms.OtherChildInPlacemtHome.findAll");
    assertThat(query, is(notNullValue()));
  }

  @Override
  @Test
  public void testFindAllReturnsCorrectList() throws Exception {
    Query query =
        session.getNamedQuery("gov.ca.cwds.data.persistence.cms.OtherChildInPlacemtHome.findAll");
    assertThat(query.list().size(), is(2));
  }

  // @SuppressWarnings("javadoc")
  // @Test
  // public void testfindAllUpdatedAfterNamedQueryExists() throws Exception {
  // Query query = session.getNamedQuery(
  // "gov.ca.cwds.data.persistence.cms.OtherChildInPlacemtHome.findAllUpdatedAfter");
  // assertThat(query, is(notNullValue()));
  // }
  //
  // @SuppressWarnings("javadoc")
  // @Test
  // public void testfindAllUpdatedAfterReturnsCorrectList() throws Exception {
  // Query query = session
  // .getNamedQuery(
  // "gov.ca.cwds.data.persistence.cms.OtherChildInPlacemtHome.findAllUpdatedAfter")
  // .setDate("after", TIMESTAMP_FORMAT.parse("2001-01-01 00:00:00"));
  // assertThat(query.list().size(), is(2));
  // }

  @Override
  @Test
  public void testFind() throws Exception {
    String id = "PT9kQgI0Mq";
    OtherChildInPlacemtHome found = otherChildInPlacemtHomeDao.find(id);
    assertThat(found.getId(), is(id));
  }

  @Override
  @Test
  public void testFindEntityNotFoundException() throws Exception {
    String id = "ZZZZZZZ999";
    OtherChildInPlacemtHome found = otherChildInPlacemtHomeDao.find(id);
    assertThat(found, is(nullValue()));

  }

  @Override
  @Test
  public void testCreate() throws Exception {
    Date birthDate = df.parse(birthDateString);
    OtherChildInPlacemtHome otherChildInPlacemtHome = new OtherChildInPlacemtHome(BigDecimal.ZERO,
        birthDate, "0M401G20Mq", "F", "PT9kQgI0Mr", "Milly W.");
    OtherChildInPlacemtHome created = otherChildInPlacemtHomeDao.create(otherChildInPlacemtHome);
    assertThat(created, is(otherChildInPlacemtHome));
  }

  @Override
  @Test(expected = EntityExistsException.class)
  public void testCreateExistingEntityException() throws Exception {
    Date birthDate = df.parse(birthDateString);
    OtherChildInPlacemtHome otherChildInPlacemtHome = new OtherChildInPlacemtHome(BigDecimal.ZERO,
        birthDate, "0M401G20Mq", "F", "PT9kQgI0Mq", "Milly W.");
    otherChildInPlacemtHomeDao.create(otherChildInPlacemtHome);
  }

  @Override
  @Test
  public void testDelete() throws Exception {
    String id = "PT9kQgI0Mq";
    OtherChildInPlacemtHome deleted = otherChildInPlacemtHomeDao.delete(id);
    assertThat(deleted.getId(), is(id));
  }

  @Override
  @Test
  public void testDeleteEntityNotFoundException() throws Exception {
    String id = "ZZZZZZZ999";
    OtherChildInPlacemtHome deleted = otherChildInPlacemtHomeDao.delete(id);
    assertThat(deleted, is(nullValue()));

  }

  @Override
  @Test
  public void testUpdate() throws Exception {

    Date birthDate = df.parse(birthDateString);
    OtherChildInPlacemtHome otherChildInPlacemtHome = new OtherChildInPlacemtHome(BigDecimal.ZERO,
        birthDate, "0M401G20Mq", "F", "PT9kQgI0Mq", "Milly G.");
    OtherChildInPlacemtHome updated = otherChildInPlacemtHomeDao.update(otherChildInPlacemtHome);
    assertThat(updated, is(otherChildInPlacemtHome));
  }

  @Override
  @Test(expected = EntityNotFoundException.class)
  public void testUpdateEntityNotFoundException() throws Exception {

    Date birthDate = df.parse(birthDateString);
    OtherChildInPlacemtHome otherChildInPlacemtHome = new OtherChildInPlacemtHome(BigDecimal.ZERO,
        birthDate, "0M401G20Mq", "F", "PT9kQgI0Ms", "Milly W.");
    otherChildInPlacemtHomeDao.update(otherChildInPlacemtHome);
  }

}
