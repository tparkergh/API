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
import gov.ca.cwds.data.persistence.cms.ClientUc;

/**
 * 
 * @author CWDS API Team
 */
public class ClientUcDaoIT implements DaoTestTemplate {

  private static SessionFactory sessionFactory;
  private static ClientUcDao clientUcDao;
  private Session session;

  /*
   * pktableId matches src/main/resources/db.cms/ci-seeds.sql
   */
  private String id = "KFXgqgo0JG";

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
    clientUcDao = new ClientUcDao(sessionFactory);
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
    ClientUc found = clientUcDao.find(id);
    assertThat(found.getPktableId(), is(equalTo(id)));
  }

  @Override
  @Test
  public void testFindEntityNotFoundException() throws Exception {
    ClientUc found = clientUcDao.find("9999999ZZZ");
    assertThat(found, is(nullValue()));
  }

  /**
   * Create JUnit test
   */
  @Override
  @Test
  public void testCreate() throws Exception {

    gov.ca.cwds.rest.api.domain.cms.ClientUc vcluc = validDomainClientUc();
    ClientUc clientUc = new ClientUc("ABC1234567", vcluc.getSourceTableCode(),
        vcluc.getCommonFirstName(), vcluc.getCommonLastName(), vcluc.getCommonMiddleName());

    ClientUc create = clientUcDao.create(clientUc);
    assertThat(clientUc, is(create));
  }

  @Override
  @Test(expected = EntityExistsException.class)
  public void testCreateExistingEntityException() throws Exception {

    gov.ca.cwds.rest.api.domain.cms.ClientUc vcluc = validDomainClientUc();
    ClientUc clientUc = new ClientUc(id, vcluc.getSourceTableCode(), vcluc.getCommonFirstName(),
        vcluc.getCommonLastName(), vcluc.getCommonMiddleName());
    clientUcDao.create(clientUc);
  }

  /**
   * Delete JUnit test
   */
  @Override
  @Test
  public void testDelete() throws Exception {
    ClientUc deleted = clientUcDao.delete(id);
    assertThat(deleted.getPktableId(), is(id));
  }

  @Override
  @Test
  public void testDeleteEntityNotFoundException() throws Exception {
    ClientUc deleted = clientUcDao.delete("9999999ZZZ");
    assertThat(deleted, is(nullValue()));
  }

  @Override
  @Test
  public void testUpdate() throws Exception {

    gov.ca.cwds.rest.api.domain.cms.ClientUc vcluc = validDomainClientUc();
    ClientUc clientUc = new ClientUc(id, vcluc.getSourceTableCode(), vcluc.getCommonFirstName(),
        vcluc.getCommonLastName(), vcluc.getCommonMiddleName());
    ClientUc updated = clientUcDao.update(clientUc);
    assertThat(clientUc, is(updated));

  }

  @Override
  @Test(expected = EntityNotFoundException.class)
  public void testUpdateEntityNotFoundException() throws Exception {

    gov.ca.cwds.rest.api.domain.cms.ClientUc vcluc = validDomainClientUc();
    ClientUc clientUc = new ClientUc("ABC1234567", vcluc.getSourceTableCode(),
        vcluc.getCommonFirstName(), vcluc.getCommonLastName(), vcluc.getCommonMiddleName());
    clientUcDao.update(clientUc);
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

  private gov.ca.cwds.rest.api.domain.cms.ClientUc validDomainClientUc()
      throws JsonParseException, JsonMappingException, IOException {

    gov.ca.cwds.rest.api.domain.cms.ClientUc validDomainClientUc =
        MAPPER.readValue(fixture("fixtures/domain/legacy/ClientUc/valid/valid.json"),
            gov.ca.cwds.rest.api.domain.cms.ClientUc.class);
    return validDomainClientUc;
  }

}
