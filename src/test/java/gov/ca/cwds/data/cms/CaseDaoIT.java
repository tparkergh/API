package gov.ca.cwds.data.cms;

import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.hamcrest.junit.ExpectedException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;

import gov.ca.cwds.data.AutocloseSessionFactory;
import gov.ca.cwds.data.junit.template.DaoTestTemplate;
import gov.ca.cwds.data.persistence.cms.CmsCase;
import gov.ca.cwds.data.persistence.cms.CmsKeyIdGenerator;

/**
 * @author CWDS API Team
 */
public class CaseDaoIT implements DaoTestTemplate {

  private static SessionFactory sessionFactory;
  private static CaseDao dao;
  private Session session;

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  /**
   * 
   */
  @BeforeClass
  public static void beforeClass() {
    sessionFactory = AutocloseSessionFactory.getSessionFactory();
    dao = new CaseDao(sessionFactory);
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
    final String id = "0iiVVuE088";
    CmsCase found = dao.find(id);
    assertThat(found.getId(), is(id));
  }

  @Override
  @Test
  public void testFindEntityNotFoundException() throws Exception {
    final String id = "ZZZZZZ999";
    CmsCase found = dao.find(id);
    assertThat(found, is(nullValue()));
  }

  @Override
  @Test
  public void testCreate() throws Exception {
    CmsCase bean = new CmsCase();
    bean.setId(CmsKeyIdGenerator.generate("0x5"));
    CmsCase created = dao.create(bean);
    assertThat(created, is(bean));
  }

  @Override
  // @Test
  public void testCreateExistingEntityException() throws Exception {
    thrown.expect(EntityExistsException.class);
    CmsCase bean = new CmsCase();
    dao.create(bean);
  }

  @Override
  @Test
  public void testDelete() throws Exception {
    String id = "0iiVVuE088";
    CmsCase deleted = dao.delete(id);
    assertThat(deleted.getId(), is(id));
  }

  @Override
  @Test
  public void testDeleteEntityNotFoundException() throws Exception {
    String id = "ZZZZZZZ999";
    CmsCase deleted = dao.delete(id);
    assertThat(deleted, is(nullValue()));
  }

  @Override
  @Test
  public void testUpdate() throws Exception {
    CmsCase bean = new CmsCase();
    bean.setId("0iiVVuE088");
    CmsCase updated = dao.update(bean);
    assertThat(updated, is(bean));
  }

  @Override
  // @Test
  public void testUpdateEntityNotFoundException() throws Exception {
    thrown.expect(EntityNotFoundException.class);
    CmsCase bean = new CmsCase();
    dao.update(bean);
  }

  @Override
  public void testFindAllNamedQueryExist() throws Exception {

  }

  @Override
  public void testFindAllReturnsCorrectList() throws Exception {

  }

}
