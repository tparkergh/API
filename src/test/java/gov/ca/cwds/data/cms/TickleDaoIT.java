package gov.ca.cwds.data.cms;

import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

import java.util.Date;

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
import gov.ca.cwds.data.persistence.cms.Tickle;

/**
 * @author CWS-NS2
 *
 */
public class TickleDaoIT implements DaoTestTemplate {

  private static SessionFactory sessionFactory;
  private static TickleDao tickleDao;
  private Session session;

  /**
   * id matches src/main/resources/db.cms/ci-seeds.sql
   */
  private String id = "AabekZX00F";

  /**
   * 
   */
  @Rule
  public ExpectedException thrown = ExpectedException.none();

  /**
   * 
   */
  @BeforeClass
  public static void beforeClass() {
    sessionFactory = new Configuration().configure().buildSessionFactory();
    tickleDao = new TickleDao(sessionFactory);
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
    Tickle found = tickleDao.find(id);
    assertThat(found.getId(), is(equalTo(id)));

  }

  @Override
  @Test
  public void testFindEntityNotFoundException() throws Exception {
    Tickle found = tickleDao.find("9999999ZZZ");
    assertThat(found, is(nullValue()));

  }

  @Override
  @Test
  public void testCreate() throws Exception {

    Tickle tickle = new Tickle("ABC1234567", "ABD1234589", "C", "K5Fk8Yd00F", null, (Date) null,
        "note text", Short.MAX_VALUE);
    Tickle create = tickleDao.create(tickle);
    assertThat(tickle, is(create));
  }

  @Override
  @Test(expected = EntityExistsException.class)
  public void testCreateExistingEntityException() throws Exception {

    Tickle tickle = new Tickle(id, "ABD1234589", "C", "K5Fk8Yd00F", null, (Date) null, "note text",
        Short.MAX_VALUE);
    tickleDao.create(tickle);
  }

  @Override
  @Test
  public void testDelete() throws Exception {
    Tickle deleted = tickleDao.delete(id);
    assertThat(deleted.getId(), is(id));

  }

  @Override
  @Test
  public void testDeleteEntityNotFoundException() throws Exception {
    String id = "ABC1234568";
    Tickle deleted = tickleDao.delete(id);
    assertThat(deleted, is(nullValue()));

  }

  @Override
  @Test
  public void testUpdate() throws Exception {

    Tickle tickle = new Tickle(id, "ABD1234589", "C", "K5Fk8Yd00F", null, (Date) null, "note text1",
        Short.MAX_VALUE);
    Tickle updated = tickleDao.update(tickle);
    assertThat(updated, is(tickle));
  }

  @Override
  @Test(expected = EntityNotFoundException.class)
  public void testUpdateEntityNotFoundException() throws Exception {

    Tickle tickle = new Tickle("R2VC6AK0X5", "ABD1234589", "C", "K5Fk8Yd00F", null, (Date) null,
        "note text1", Short.MAX_VALUE);
    tickleDao.update(tickle);
  }

  @Override
  public void testFindAllNamedQueryExist() throws Exception {

  }

  @Override
  public void testFindAllReturnsCorrectList() throws Exception {

  }

}
