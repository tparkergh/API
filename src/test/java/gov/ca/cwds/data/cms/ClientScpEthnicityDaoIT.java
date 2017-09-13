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

import gov.ca.cwds.data.persistence.cms.ClientScpEthnicity;
import gov.ca.cwds.fixture.ClientScpEthnicityEntityBuilder;

/**
 * @author CWDS API Team
 *
 */
@SuppressWarnings("javadoc")
public class ClientScpEthnicityDaoIT {

  private static SessionFactory sessionFactory;
  private static ClientScpEthnicityDao clientScpEthnicityDao;
  private Session session;

  /*
   * id matches src/main/resources/db.cms/ci-seeds.sql
   */
  private String id = "AcirYvt0Ki";

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
    clientScpEthnicityDao = new ClientScpEthnicityDao(sessionFactory);
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


  @Test
  public void testFind() throws Exception {
    ClientScpEthnicity found = clientScpEthnicityDao.find(id);
    assertThat(found.getId(), is(equalTo(id)));
  }

  @Test
  public void testFindEntityNotFoundException() throws Exception {
    ClientScpEthnicity found = clientScpEthnicityDao.find("9999999ZZZ");
    assertThat(found, is(nullValue()));
  }

  @Test
  public void testCreate() throws Exception {
    ClientScpEthnicity clientScpEthnicity = new ClientScpEthnicityEntityBuilder().build();
    ClientScpEthnicity created = clientScpEthnicityDao.create(clientScpEthnicity);
    assertThat(created, is(clientScpEthnicity));
  }

  @Test(expected = EntityExistsException.class)
  public void testCreateExistingEntityException() throws Exception {
    ClientScpEthnicity clientScpEthnicity = new ClientScpEthnicityEntityBuilder().setId(id).build();
    clientScpEthnicityDao.create(clientScpEthnicity);
  }

  @Test
  public void testDelete() throws Exception {
    ClientScpEthnicity deleted = clientScpEthnicityDao.delete(id);
    assertThat(deleted.getId(), is(id));
  }

  @Test
  public void testDeleteEntityNotFoundException() throws Exception {
    String id = "ABC1234568";
    ClientScpEthnicity deleted = clientScpEthnicityDao.delete(id);
    assertThat(deleted, is(nullValue()));
  }

  @Test
  public void testUpdate() throws Exception {
    ClientScpEthnicity clientScpEthnicity =
        new ClientScpEthnicityEntityBuilder().setId(id).setEthnicity((short) 835).build();
    ClientScpEthnicity updated = clientScpEthnicityDao.update(clientScpEthnicity);
    assertThat(updated, is(clientScpEthnicity));
  }

  @Test(expected = EntityNotFoundException.class)
  public void testUpdateEntityNotFoundException() throws Exception {
    ClientScpEthnicity clientScpEthnicity =
        new ClientScpEthnicityEntityBuilder().setId("ppjUt8F41uG").build();
    clientScpEthnicityDao.update(clientScpEthnicity);
  }

}
