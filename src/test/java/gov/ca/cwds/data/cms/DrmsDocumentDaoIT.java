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

import gov.ca.cwds.data.persistence.cms.DrmsDocument;
import gov.ca.cwds.fixture.DrmsDocumentResourceBuilder;

/**
 * 
 * @author CWDS API Team
 */
@SuppressWarnings("javadoc")
public class DrmsDocumentDaoIT {

  private static SessionFactory sessionFactory;
  private static DrmsDocumentDao drmsDocumentDao;
  private Session session;

  /**
   * id matches src/main/resources/db.cms/ci-seeds.sql
   */
  private String id = "AajeIli0Dv";

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
    drmsDocumentDao = new DrmsDocumentDao(sessionFactory);
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
   * 
   * @throws Exception like omg whatever lol
   */
  @Test
  public void testFind() throws Exception {
    DrmsDocument found = drmsDocumentDao.find(id);
    assertThat(found.getId(), is(equalTo(id)));
  }

  @Test
  public void testFindEntityNotFoundException() throws Exception {
    DrmsDocument found = drmsDocumentDao.find("9999999ZZZ");
    assertThat(found, is(nullValue()));
  }

  /**
   * Create JUnit test
   * 
   * @throws Exception some incredibly professional yet epically vague comment
   */
  @Test
  public void testCreate() throws Exception {

    gov.ca.cwds.rest.api.domain.cms.DrmsDocument vdd = new DrmsDocumentResourceBuilder().build();
    DrmsDocument drmsDocument =
        new DrmsDocument("ABC1234567", vdd.getCreationTimeStamp(), vdd.getDrmsDocumentTemplateId(),
            vdd.getFingerprintStaffPerson(), vdd.getStaffPersonId(), vdd.getHandleName());

    DrmsDocument create = drmsDocumentDao.create(drmsDocument);
    assertThat(drmsDocument, is(create));
  }

  @Test(expected = EntityExistsException.class)
  public void testCreateExistingEntityException() throws Exception {

    gov.ca.cwds.rest.api.domain.cms.DrmsDocument vdd = new DrmsDocumentResourceBuilder().build();
    DrmsDocument drmsDocument =
        new DrmsDocument(id, vdd.getCreationTimeStamp(), vdd.getDrmsDocumentTemplateId(),
            vdd.getFingerprintStaffPerson(), vdd.getStaffPersonId(), vdd.getHandleName());

    drmsDocumentDao.create(drmsDocument);
  }

  /**
   * Delete JUnit test
   * 
   * @throws Exception whatever -- it's a unit test
   */
  @Test
  public void testDelete() throws Exception {
    DrmsDocument deleted = drmsDocumentDao.delete(id);
    assertThat(deleted.getId(), is(id));
  }

  @Test
  public void testDeleteEntityNotFoundException() throws Exception {
    DrmsDocument deleted = drmsDocumentDao.delete("9999999ZZZ");
    assertThat(deleted, is(nullValue()));
  }

  /**
   * Update JUnit test
   * 
   * @throws Exception it's like totaly, you know, a unit test
   */
  @Test
  public void testUpdate() throws Exception {

    gov.ca.cwds.rest.api.domain.cms.DrmsDocument vdd = new DrmsDocumentResourceBuilder().build();
    DrmsDocument drmsDocument =
        new DrmsDocument(id, vdd.getCreationTimeStamp(), vdd.getDrmsDocumentTemplateId(),
            vdd.getFingerprintStaffPerson(), vdd.getStaffPersonId(), vdd.getHandleName());

    DrmsDocument updated = drmsDocumentDao.update(drmsDocument);
    assertThat(drmsDocument, is(updated));
  }

  @Test(expected = EntityNotFoundException.class)
  public void testUpdateEntityNotFoundException() throws Exception {

    gov.ca.cwds.rest.api.domain.cms.DrmsDocument vdd = new DrmsDocumentResourceBuilder().build();
    DrmsDocument drmsDocument =
        new DrmsDocument("1234567ABC", vdd.getCreationTimeStamp(), vdd.getDrmsDocumentTemplateId(),
            vdd.getFingerprintStaffPerson(), vdd.getStaffPersonId(), vdd.getHandleName());

    drmsDocumentDao.update(drmsDocument);
  }

}
