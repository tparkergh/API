package gov.ca.cwds.data.cms;

import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

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
import gov.ca.cwds.data.persistence.cms.AddressUc;

/**
 * @author CWDS API Team
 *
 */
public class AddressUcDaoIT implements DaoTestTemplate {

  private static SessionFactory sessionFactory;
  private static AddressUcDao addressUcDao;
  private Session session;

  /*
   * pktableId matches src/main/resources/db.cms/ci-seeds.sql
   */
  private String id = "AbjqVmy04O";

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
    addressUcDao = new AddressUcDao(sessionFactory);
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
    String entityId = "AbjqVmy04O";
    AddressUc found = addressUcDao.find(entityId);
    assertThat(found.getPktableId(), is(equalTo(entityId)));
  }

  @Override
  @Test
  public void testFindEntityNotFoundException() throws Exception {
    AddressUc found = addressUcDao.find("9999999ZZZ");
    assertThat(found, is(nullValue()));
  }

  @Override
  @Test
  public void testCreate() throws Exception {
    AddressUc addressUc = new AddressUc("ABC1234567", "C", "Sacramento", "Main Street", "123");
    AddressUc created = addressUcDao.create(addressUc);
    assertThat(created, is(addressUc));
  }

  @Override
  @Test
  public void testCreateExistingEntityException() throws Exception {
    thrown.expect(EntityExistsException.class);
    AddressUc addressUc = new AddressUc("AbjqVmy04O", "C", "Sacramento", "Main Street", "123");
    addressUcDao.create(addressUc);
  }

  @Override
  @Test
  public void testDelete() throws Exception {
    AddressUc deleted = addressUcDao.delete(id);
    assertThat(deleted.getPktableId(), is(id));
  }

  @Override
  @Test
  public void testDeleteEntityNotFoundException() throws Exception {
    String id = "ABC1234568";
    AddressUc deleted = addressUcDao.delete(id);
    assertThat(deleted, is(nullValue()));

  }

  @Override
  // @Test
  public void testUpdate() throws Exception {
    AddressUc addressUc = new AddressUc("AbjqVmy04O", "C", "Sacramento", "Main Street", "123");
    AddressUc updated = addressUcDao.update(addressUc);
    assertThat(updated, is(addressUc));
  }

  @Override
  @Test
  public void testUpdateEntityNotFoundException() throws Exception {
    thrown.expect(EntityNotFoundException.class);
    AddressUc addressUc = new AddressUc("AbjqVmy04y", "C", "Sacramento", "Main Street", "123");
    addressUcDao.update(addressUc);
  }

  @Override
  public void testFindAllNamedQueryExist() throws Exception {

  }

  @Override
  public void testFindAllReturnsCorrectList() throws Exception {

  }


}
