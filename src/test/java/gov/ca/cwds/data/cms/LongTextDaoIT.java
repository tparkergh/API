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
import gov.ca.cwds.data.persistence.cms.LongText;

/**
 * 
 * @author CWDS API Team
 */
public class LongTextDaoIT implements DaoTestTemplate {

  private static SessionFactory sessionFactory;
  private static LongTextDao longTextDao;
  private Session session;

  /*
   * id matches src/main/resources/db.cms/ci-seeds.sql
   */
  private String id = "AaoDyiJq27";

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
    longTextDao = new LongTextDao(sessionFactory);
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
    LongText found = longTextDao.find(id);
    assertThat(found.getId(), is(equalTo(id)));
  }

  @Override
  @Test
  public void testFindEntityNotFoundException() throws Exception {
    LongText found = longTextDao.find("9999999ZZZ");
    assertThat(found, is(nullValue()));
  }

  /**
   * Create JUnit test
   */
  @Override
  @Test
  public void testCreate() throws Exception {

    gov.ca.cwds.rest.api.domain.cms.LongText vdlt = validDomainLongText();
    LongText longText =
        new LongText("ABC1234567", vdlt.getCountySpecificCode(), vdlt.getTextDescription());

    LongText create = longTextDao.create(longText);
    assertThat(longText, is(create));
  }

  @Override
  @Test
  public void testCreateExistingEntityException() throws Exception {

    thrown.expect(EntityExistsException.class);

    gov.ca.cwds.rest.api.domain.cms.LongText vdlt = validDomainLongText();

    LongText longText = new LongText(id, vdlt.getCountySpecificCode(), vdlt.getTextDescription());
    longTextDao.create(longText);
  }

  /**
   * Delete JUnit test
   */
  @Override
  @Test
  public void testDelete() throws Exception {
    LongText deleted = longTextDao.delete(id);
    assertThat(deleted.getId(), is(id));
  }

  @Override
  @Test
  public void testDeleteEntityNotFoundException() throws Exception {
    LongText deleted = longTextDao.delete("9999999ZZZ");
    assertThat(deleted, is(nullValue()));
  }

  @Override
  @Test
  public void testUpdate() throws Exception {

    gov.ca.cwds.rest.api.domain.cms.LongText vdlt = validDomainLongText();

    LongText longText = new LongText(id, vdlt.getCountySpecificCode(), vdlt.getTextDescription());
    LongText updated = longTextDao.update(longText);

    assertThat(longText, is(updated));

  }

  @Override
  @Test
  public void testUpdateEntityNotFoundException() throws Exception {

    thrown.expect(EntityNotFoundException.class);

    gov.ca.cwds.rest.api.domain.cms.LongText vdlt = validDomainLongText();

    LongText longText =
        new LongText("ABC1234567", vdlt.getCountySpecificCode(), vdlt.getTextDescription());

    longTextDao.update(longText);
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

  private gov.ca.cwds.rest.api.domain.cms.LongText validDomainLongText()
      throws JsonParseException, JsonMappingException, IOException {

    gov.ca.cwds.rest.api.domain.cms.LongText validDomainLongText =
        MAPPER.readValue(fixture("fixtures/domain/legacy/LongText/valid/valid.json"),
            gov.ca.cwds.rest.api.domain.cms.LongText.class);
    return validDomainLongText;
  }

}
