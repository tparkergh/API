package gov.ca.cwds.data.cms;

import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

import java.util.Date;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.hamcrest.junit.ExpectedException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import gov.ca.cwds.data.junit.template.DaoTestTemplate;
import gov.ca.cwds.data.persistence.cms.CountyTrigger;

public class CountyTriggerDaoIT implements DaoTestTemplate {

  private SessionFactory sessionFactory;
  private CountyTriggerDao countyTriggerDao;

  /*
   * pktableId matches src/main/resources/db.cms/ci-seeds.sql
   */
  private String countyOwnershipT = "DjG7V870X5";

  /**
   * 
   */
  @Rule
  public ExpectedException thrown = ExpectedException.none();

  /**
   * 
   */
  @Override
  @Before
  public void setup() {
    sessionFactory = new Configuration().configure().buildSessionFactory();
    sessionFactory.getCurrentSession().beginTransaction();
    countyTriggerDao = new CountyTriggerDao(sessionFactory);
  }

  /**
   * 
   */
  @Override
  @After
  public void teardown() {
    sessionFactory.close();
  }

  @Override
  @Test
  public void testFind() throws Exception {
    CountyTrigger found = countyTriggerDao.find(countyOwnershipT);
    assertThat(found.getCountyOwnershipT(), is(equalTo(countyOwnershipT)));
  }

  @Override
  @Test
  public void testFindEntityNotFoundException() throws Exception {
    CountyTrigger found = countyTriggerDao.find("9999999KKK");
    assertThat(found, is(nullValue()));
  }

  @Override
  @Test
  public void testCreate() throws Exception {
    CountyTrigger countyTrigger =
        new CountyTrigger("ABC1234567", "62", "C", (Date) null, "REFR_CLT");
    CountyTrigger created = countyTriggerDao.create(countyTrigger);
    assertThat(created, is(countyTrigger));
  }

  @Override
  @Test
  public void testCreateExistingEntityException() throws Exception {
    thrown.expect(EntityExistsException.class);
    CountyTrigger countyTrigger =
        new CountyTrigger("Hzfdiu90X5", "62", "C", (Date) null, "REFR_CLT");
    countyTriggerDao.create(countyTrigger);
  }

  @Override
  @Test
  public void testDelete() throws Exception {
    CountyTrigger deleted = countyTriggerDao.delete(countyOwnershipT);
    assertThat(deleted.getCountyOwnershipT(), is(countyOwnershipT));
  }

  @Override
  @Test
  public void testDeleteEntityNotFoundException() throws Exception {
    String countyOwnershipT = "ABC1234568";
    CountyTrigger deleted = countyTriggerDao.delete(countyOwnershipT);
    assertThat(deleted, is(nullValue()));
  }

  @Override
  @Test
  public void testUpdate() throws Exception {
    CountyTrigger countyTrigger =
        new CountyTrigger(countyOwnershipT, "61", "C", (Date) null, "REFR_CLT");
    CountyTrigger updated = countyTriggerDao.update(countyTrigger);
    assertThat(updated, is(countyTrigger));
  }

  @Override
  @Test
  public void testUpdateEntityNotFoundException() throws Exception {
    thrown.expect(EntityNotFoundException.class);
    CountyTrigger countyTrigger =
        new CountyTrigger("ABC1234569", "61", "C", (Date) null, "REFR_CLT");
    countyTriggerDao.update(countyTrigger);
  }


  @Override
  public void testFindAllNamedQueryExist() throws Exception {

  }

  @Override
  public void testFindAllReturnsCorrectList() throws Exception {

  }

}
