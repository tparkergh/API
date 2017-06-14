package gov.ca.cwds.data.cms;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

import java.io.IOException;

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

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import gov.ca.cwds.data.junit.template.DaoTestTemplate;
import gov.ca.cwds.data.persistence.cms.DrmsDocument;

/**
 * 
 * @author CWDS API Team
 */
public class DrmsDocumentDaoIT implements DaoTestTemplate {

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

  /**
   * Find JUnit test
   */
  @Override
  @Test
  public void testFind() throws Exception {
    DrmsDocument found = drmsDocumentDao.find(id);
    assertThat(found.getId(), is(equalTo(id)));
  }

  @Override
  @Test
  public void testFindEntityNotFoundException() throws Exception {
    DrmsDocument found = drmsDocumentDao.find("9999999ZZZ");
    assertThat(found, is(nullValue()));
  }

  @Override
  @Test
  public void testCreate() throws Exception {

    gov.ca.cwds.rest.api.domain.cms.DrmsDocument vdd = validDrmsDocument();

    DrmsDocument drmsDocument =
        new DrmsDocument("ABC1234567", vdd.getCreationTimeStamp(), vdd.getDrmsDocumentTemplateId(),
            vdd.getFingerprintStaffPerson(), vdd.getStaffPersonId(), vdd.getHandleName());

    DrmsDocument create = drmsDocumentDao.create(drmsDocument);
    assertThat(drmsDocument, is(create));
  }

  @Override
  @Test
  public void testCreateExistingEntityException() throws Exception {

    thrown.expect(EntityExistsException.class);
    gov.ca.cwds.rest.api.domain.cms.DrmsDocument vdd = validDrmsDocument();

    DrmsDocument drmsDocument =
        new DrmsDocument(id, vdd.getCreationTimeStamp(), vdd.getDrmsDocumentTemplateId(),
            vdd.getFingerprintStaffPerson(), vdd.getStaffPersonId(), vdd.getHandleName());

    drmsDocumentDao.create(drmsDocument);
  }

  /**
   * Delete JUnit test
   */
  @Override
  @Test
  public void testDelete() throws Exception {
    DrmsDocument deleted = drmsDocumentDao.delete(id);
    assertThat(deleted.getId(), is(id));
  }

  @Override
  @Test
  public void testDeleteEntityNotFoundException() throws Exception {
    DrmsDocument deleted = drmsDocumentDao.delete("9999999ZZZ");
    assertThat(deleted, is(nullValue()));
  }

  /**
   * Update JUnit test
   */
  @Override
  @Test
  public void testUpdate() throws Exception {

    gov.ca.cwds.rest.api.domain.cms.DrmsDocument vdd = validDrmsDocument();

    DrmsDocument drmsDocument =
        new DrmsDocument(id, vdd.getCreationTimeStamp(), vdd.getDrmsDocumentTemplateId(),
            vdd.getFingerprintStaffPerson(), vdd.getStaffPersonId(), vdd.getHandleName());

    DrmsDocument updated = drmsDocumentDao.update(drmsDocument);
    assertThat(drmsDocument, is(updated));
  }

  @Override
  @Test
  public void testUpdateEntityNotFoundException() throws Exception {

    thrown.expect(EntityNotFoundException.class);
    gov.ca.cwds.rest.api.domain.cms.DrmsDocument vdd = validDrmsDocument();

    DrmsDocument drmsDocument =
        new DrmsDocument("1234567ABC", vdd.getCreationTimeStamp(), vdd.getDrmsDocumentTemplateId(),
            vdd.getFingerprintStaffPerson(), vdd.getStaffPersonId(), vdd.getHandleName());

    drmsDocumentDao.update(drmsDocument);
  }

  /*
   * Named Query JUnit test
   */
  @Override
  public void testFindAllNamedQueryExist() throws Exception {

  }

  @Override
  public void testFindAllReturnsCorrectList() throws Exception {

  }

  private gov.ca.cwds.rest.api.domain.cms.DrmsDocument validDrmsDocument()
      throws JsonParseException, JsonMappingException, IOException {

    gov.ca.cwds.rest.api.domain.cms.DrmsDocument validDrmsDocument =
        MAPPER.readValue(fixture("fixtures/domain/legacy/DrmsDocument/valid/valid.json"),
            gov.ca.cwds.rest.api.domain.cms.DrmsDocument.class);
    return validDrmsDocument;
  }

}
