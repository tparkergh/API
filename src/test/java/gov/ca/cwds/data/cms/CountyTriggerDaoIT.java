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

import gov.ca.cwds.data.persistence.cms.CountyTrigger;
import gov.ca.cwds.data.persistence.cms.CountyTriggerEmbeddable;

/**
 * @author CWDS API Team
 *
 */
@SuppressWarnings("javadoc")
public class CountyTriggerDaoIT {

  private static SessionFactory sessionFactory;
  private static CountyTriggerDao countyTriggerDao;
  private Session session;

  /*
   * pktableId matches src/main/resources/db.cms/ci-seeds.sql
   */
  private String countyOwnershipT = "DjG7V870X5";
  private String logicId = "62";
  private String countyOwnership0 = "C";
  private String integratorEntity = "REFR_CLT";

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
    countyTriggerDao = new CountyTriggerDao(sessionFactory);
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
    CountyTriggerEmbeddable countyTriggerEmbeddable =
        new CountyTriggerEmbeddable(logicId, countyOwnership0, countyOwnershipT, integratorEntity);

    CountyTrigger found = countyTriggerDao.find(countyTriggerEmbeddable);
    assertThat(found.getCountyTriggerEmbeddable().getCountyOwnershipT(),
        is(equalTo(countyOwnershipT)));
  }

  @Test
  public void testFindEntityNotFoundException() throws Exception {
    CountyTriggerEmbeddable countyTriggerEmbeddable =
        new CountyTriggerEmbeddable("99", countyOwnership0, countyOwnershipT, integratorEntity);
    CountyTrigger found = countyTriggerDao.find(countyTriggerEmbeddable);
    assertThat(found, is(nullValue()));
  }

  @Test
  public void testCreate() throws Exception {
    CountyTrigger countyTrigger =
        new CountyTrigger(logicId, "A", countyOwnershipT, integratorEntity, new Date());
    CountyTrigger created = countyTriggerDao.create(countyTrigger);
    assertThat(created, is(countyTrigger));
  }

  @Test(expected = EntityExistsException.class)
  public void testCreateExistingEntityException() throws Exception {
    CountyTrigger countyTrigger =
        new CountyTrigger(logicId, countyOwnership0, "LiFHJnZ0X5", integratorEntity, null);
    countyTriggerDao.create(countyTrigger);
  }

  @Test
  public void testDelete() throws Exception {
    CountyTriggerEmbeddable countyTriggerEmbeddable =
        new CountyTriggerEmbeddable(logicId, countyOwnership0, countyOwnershipT, integratorEntity);

    CountyTrigger deleted = countyTriggerDao.delete(countyTriggerEmbeddable);
    assertThat(deleted.getCountyTriggerEmbeddable().getCountyOwnershipT(), is(countyOwnershipT));
  }

  @Test
  public void testDeleteEntityNotFoundException() throws Exception {
    CountyTriggerEmbeddable countyTriggerEmbeddable =
        new CountyTriggerEmbeddable(logicId, countyOwnership0, "ABC1234568", integratorEntity);
    CountyTrigger deleted = countyTriggerDao.delete(countyTriggerEmbeddable);
    assertThat(deleted, is(nullValue()));
  }

  @Test
  public void testUpdate() throws Exception {
    CountyTrigger countyTrigger = new CountyTrigger(logicId, countyOwnership0, countyOwnershipT,
        integratorEntity, new Date());
    CountyTrigger updated = countyTriggerDao.update(countyTrigger);
    assertThat(updated, is(countyTrigger));
  }

  @Test(expected = EntityNotFoundException.class)
  public void testUpdateEntityNotFoundException() throws Exception {
    CountyTrigger countyTrigger =
        new CountyTrigger(logicId, countyOwnership0, "ABC1234569", integratorEntity, null);
    countyTriggerDao.update(countyTrigger);
  }

}
