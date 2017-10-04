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
import gov.ca.cwds.data.persistence.cms.CountyOwnership;

/**
 * @author CWDS API Team
 *
 */
public class CountyOwnershipDaoIT implements DaoTestTemplate {

  private static SessionFactory sessionFactory;
  private static CountyOwnershipDao countyOwnershipDao;
  private Session session;

  /*
   * pktableId matches src/main/resources/db.cms/ci-seeds.sql
   */
  private String id = "AaNli340MV";

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
    countyOwnershipDao = new CountyOwnershipDao(sessionFactory);
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
    String entityId = "AaNli340MV";
    CountyOwnership found = countyOwnershipDao.find(entityId);
    assertThat(found.getEntityId(), is(equalTo(entityId)));
  }

  @Override
  @Test
  public void testFindEntityNotFoundException() throws Exception {
    CountyOwnership found = countyOwnershipDao.find("9999999ZZZ");
    assertThat(found, is(nullValue()));
  }

  @Override
  @Test
  public void testCreate() throws Exception {
    CountyOwnership countyOwnership = new CountyOwnership("ABC1234567", "C", "N", "N", "N", "N",
        "N", "N", "N", "N", "N", "N", "N", "N", "N", "N", "N", "N", "N", "N", "N", "N", "N", "N",
        "N", "N", "N", "N", "N", "N", "N", "N", "N", "N", "N", "N", "N", "N", "N", "N", "N", "N",
        "N", "N", "N", "N", "N", "N", "N", "N", "N", "N", "N", "N", "N", "N", "N", "N", "N", "N",
        "N", "N", "N", "N", "N", "N", "N", null);
    CountyOwnership created = countyOwnershipDao.create(countyOwnership);
    assertThat(created, is(countyOwnership));
  }

  @Override
  @Test(expected = EntityExistsException.class)
  public void testCreateExistingEntityException() throws Exception {
    CountyOwnership countyOwnership = new CountyOwnership("AaQshqm0Mb", "C", "N", "N", "N", "N",
        "N", "N", "N", "N", "N", "N", "N", "N", "N", "N", "N", "N", "N", "N", "N", "N", "N", "N",
        "N", "N", "N", "N", "N", "N", "N", "N", "N", "N", "N", "N", "N", "N", "N", "N", "N", "N",
        "N", "N", "N", "N", "N", "N", "N", "N", "N", "N", "N", "N", "N", "N", "N", "N", "N", "N",
        "N", "N", "N", "N", "N", "N", "N", null);
    countyOwnershipDao.create(countyOwnership);
  }

  @Override
  @Test
  public void testDelete() throws Exception {
    CountyOwnership deleted = countyOwnershipDao.delete(id);
    assertThat(deleted.getEntityId(), is(id));
  }

  @Override
  @Test
  public void testDeleteEntityNotFoundException() throws Exception {
    String id = "ABC1234568";
    CountyOwnership deleted = countyOwnershipDao.delete(id);
    assertThat(deleted, is(nullValue()));

  }

  @Override
  // @Test
  public void testUpdate() throws Exception {
    CountyOwnership countyOwnership = new CountyOwnership("9J1kqk10X5", "C", "N", "N", "N", "N",
        "N", "N", "N", "N", "N", "N", "N", "N", "N", "N", "N", "N", "N", "N", "N", "N", "N", "N",
        "N", "N", "N", "N", "N", "N", "N", "N", "N", "N", "N", "N", "N", "N", "N", "N", "N", "N",
        "N", "N", "N", "N", "N", "N", "N", "N", "N", "N", "N", "N", "N", "N", "N", "N", "N", "N",
        "N", "N", "N", "N", "N", "N", "N", null);
    CountyOwnership updated = countyOwnershipDao.update(countyOwnership);
    assertThat(updated, is(countyOwnership));
  }

  @Override
  @Test(expected = EntityNotFoundException.class)
  public void testUpdateEntityNotFoundException() throws Exception {
    CountyOwnership countyOwnership = new CountyOwnership("AaQshqm0ML", "C", "Y", "N", "N", "N",
        "N", "N", "N", "N", "N", "N", "N", "N", "N", "N", "N", "N", "N", "N", "N", "N", "N", "N",
        "N", "N", "N", "N", "N", "N", "N", "N", "N", "N", "N", "N", "N", "N", "N", "N", "N", "N",
        "N", "N", "N", "N", "N", "N", "N", "N", "N", "N", "N", "N", "N", "N", "N", "N", "N", "N",
        "N", "N", "N", "N", "N", "N", "N", null);
    countyOwnershipDao.update(countyOwnership);
  }

  @Override
  public void testFindAllNamedQueryExist() throws Exception {

  }

  @Override
  public void testFindAllReturnsCorrectList() throws Exception {

  }


}
