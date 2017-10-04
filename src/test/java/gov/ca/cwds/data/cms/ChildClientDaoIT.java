package gov.ca.cwds.data.cms;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.IsEqual.equalTo;

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

import gov.ca.cwds.data.persistence.cms.ChildClient;
import gov.ca.cwds.fixture.ChildClientEntityBuilder;

/**
 * 
 * author CWDS API Team
 */
@SuppressWarnings("javadoc")
public class ChildClientDaoIT {

  private static SessionFactory sessionFactory;
  private static ChildClientDao childClientDao;
  private Session session;

  /*
   * id matches src/main/resources/db.cms/ci-seeds.sql
   */
  private String victimId = "AazXkWY06s";

  @Rule

  public ExpectedException thrown = ExpectedException.none();

  /**
   * 
   */
  @BeforeClass
  public static void beforeClass() {
    sessionFactory = new Configuration().configure().buildSessionFactory();
    childClientDao = new ChildClientDao(sessionFactory);
  }

  /**
   * 
   */
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
  public void teardown() {
    session.getTransaction().rollback();
  }

  /**
   * Find JUnit test
   */
  @Test
  public void testFind() throws Exception {
    ChildClient found = childClientDao.find(victimId);
    assertThat(found.getVictimClientId(), is(equalTo(victimId)));
  }


  @Test
  public void testFindEntityNotFoundException() throws Exception {
    ChildClient found = childClientDao.find("9999999ZZZ");
    assertThat(found, is(nullValue()));
  }

  /**
   * Create JUnit test
   */
  @Test
  public void testCreate() throws Exception {
    ChildClient childClient = new ChildClientEntityBuilder().build();
    ChildClient create = childClientDao.create(childClient);
    assertThat(childClient, is(create));
  }


  @Test
  public void testCreateExistingEntityException() throws Exception {
    thrown.expect(EntityExistsException.class);
    ChildClient childClient = new ChildClientEntityBuilder().setVictimClientId(victimId).build();
    childClientDao.create(childClient);
  }

  /**
   * Delete JUnit test
   */
  @Test
  public void testDelete() throws Exception {
    ChildClient deleted = childClientDao.delete(victimId);
    assertThat(deleted.getVictimClientId(), is(victimId));
  }


  @Test
  public void testDeleteEntityNotFoundException() throws Exception {
    ChildClient deleted = childClientDao.delete("9999999ZZZ");
    assertThat(deleted, is(nullValue()));
  }

  /**
   * Update JUnit test
   */
  @Test
  public void testUpdate() throws Exception {
    ChildClient childClient = new ChildClientEntityBuilder().setVictimClientId(victimId)
        .setTribalAncestryNotifctnIndicatorVar("Y").build();
    ChildClient updated = childClientDao.update(childClient);
    assertThat(updated, is(childClient));
  }

  @Test(expected = EntityNotFoundException.class)
  public void testUpdateEntityNotFoundException() throws Exception {
    ChildClient ChildClient =
        new ChildClientEntityBuilder().setVictimClientId("AbNNjTK0P2").build();

    childClientDao.update(ChildClient);
  }

}
