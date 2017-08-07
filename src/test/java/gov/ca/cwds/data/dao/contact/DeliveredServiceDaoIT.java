package gov.ca.cwds.data.dao.contact;

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

import gov.ca.cwds.data.persistence.contact.DeliveredServiceEntity;
import gov.ca.cwds.fixture.contacts.DeliveredServiceEntityBuilder;

/**
 * @author CWDS API Team
 *
 */
@SuppressWarnings("javadoc")
public class DeliveredServiceDaoIT {

  private static SessionFactory sessionFactory;
  private static DeliveredServiceDao deliveredServiceDao;
  private Session session;

  /*
   * id matches src/main/resources/db.cms/ci-seeds.sql
   */
  private String id = "Aabg4cV0AB";

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
    deliveredServiceDao = new DeliveredServiceDao(sessionFactory);
  }

  /**
   * 
   */
  @AfterClass
  public static void afterClass() {
    sessionFactory.close();
  }

  /**
   * 
   */
  @Before
  public void setup() {
    session = sessionFactory.getCurrentSession();
    session.beginTransaction();
  }

  /**
   * 
   */
  @After
  public void teardown() {
    session.getTransaction().rollback();
  }

  @Test
  public void testFind() throws Exception {
    DeliveredServiceEntity found = deliveredServiceDao.find(id);
    assertThat(found.getId(), is(equalTo(id)));
  }


  @Test
  public void testFindEntityNotFoundException() throws Exception {
    DeliveredServiceEntity found = deliveredServiceDao.find("Aabg4cV0AD");
    assertThat(found, is(nullValue()));
  }

  @Test
  public void testCreate() throws Exception {
    DeliveredServiceEntity deliveredServiceEntity =
        new DeliveredServiceEntityBuilder().buildDeliveredServiceEntity();

    DeliveredServiceEntity created = deliveredServiceDao.create(deliveredServiceEntity);
    assertThat(created, is(deliveredServiceEntity));
  }

  @Test
  public void testCreateExistingEntityException() throws Exception {
    thrown.expect(EntityExistsException.class);
    DeliveredServiceEntity deliveredServiceEntity =
        new DeliveredServiceEntityBuilder().setId(id).buildDeliveredServiceEntity();
    deliveredServiceDao.create(deliveredServiceEntity);
  }

  @Test
  public void testDelete() throws Exception {
    DeliveredServiceEntity deleted = deliveredServiceDao.delete("AajvGGx0Et");
    assertThat(deleted.getId(), is(equalTo("AajvGGx0Et")));
  }

  @Test
  public void testDeleteEntityNotFoundException() throws Exception {
    DeliveredServiceEntity deleted = deliveredServiceDao.delete("AajvGGx0Ey");
    assertThat(deleted, is(nullValue()));

  }

  @Test
  public void testUpdate() throws Exception {
    DeliveredServiceEntity deliveredServiceEntity = new DeliveredServiceEntityBuilder().setId(id)
        .setCommunicationMethodType((short) 409).buildDeliveredServiceEntity();
    DeliveredServiceEntity updated = deliveredServiceDao.update(deliveredServiceEntity);
    assertThat(updated, is(deliveredServiceEntity));
  }

  @Test
  public void testUpdateEntityNotFoundException() throws Exception {
    thrown.expect(EntityNotFoundException.class);

    DeliveredServiceEntity deliveredServiceEntity =
        new DeliveredServiceEntityBuilder().setId("VXGcagc66").buildDeliveredServiceEntity();
    deliveredServiceDao.update(deliveredServiceEntity);
  }

}
