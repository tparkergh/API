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
import gov.ca.cwds.data.persistence.cms.AllegationPerpetratorHistory;
import gov.ca.cwds.fixture.AllegationPerpetratorHistoryResourceBuilder;
import gov.ca.cwds.rest.api.domain.DomainChef;

/**
 * 
 * @author CWDS API Team
 */
public class AllegationPerpetratorHistoryDaoIT implements DaoTestTemplate {

  private static SessionFactory sessionFactory;
  private static AllegationPerpetratorHistoryDao allegationPerpetratorHistoryDao;
  private Session session;

  /*
   * id matches src/main/resources/db.cms/ci-seeds.sql
   */
  private String id = "AagoG8c0ND";

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
    allegationPerpetratorHistoryDao = new AllegationPerpetratorHistoryDao(sessionFactory);
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
    AllegationPerpetratorHistory found = allegationPerpetratorHistoryDao.find(id);
    assertThat(found.getId(), is(equalTo(id)));
  }

  @Override
  @Test
  public void testFindEntityNotFoundException() throws Exception {
    AllegationPerpetratorHistory found = allegationPerpetratorHistoryDao.find("9999999ZZZ");
    assertThat(found, is(nullValue()));
  }

  /**
   * Create JUnit test
   */
  @Override
  @Test
  public void testCreate() throws Exception {

    gov.ca.cwds.rest.api.domain.cms.AllegationPerpetratorHistory vdaph =
        new AllegationPerpetratorHistoryResourceBuilder().createAllegationPerpetratorHistory();

    AllegationPerpetratorHistory allegationPerpetratorHistory = new AllegationPerpetratorHistory(
        "1234567ABC", vdaph.getCountySpecificCode(), vdaph.getPerpetratorClientId(),
        vdaph.getAllegationId(), DomainChef.uncookDateString(vdaph.getPerpetratorUpdateDate()));

    AllegationPerpetratorHistory create =
        allegationPerpetratorHistoryDao.create(allegationPerpetratorHistory);
    assertThat(allegationPerpetratorHistory, is(create));
  }

  @Override
  @Test(expected = EntityExistsException.class)
  public void testCreateExistingEntityException() throws Exception {

    gov.ca.cwds.rest.api.domain.cms.AllegationPerpetratorHistory vdaph =
        new AllegationPerpetratorHistoryResourceBuilder().createAllegationPerpetratorHistory();

    AllegationPerpetratorHistory allegationPerpetratorHistory = new AllegationPerpetratorHistory(id,
        vdaph.getCountySpecificCode(), vdaph.getPerpetratorClientId(), vdaph.getAllegationId(),
        DomainChef.uncookDateString(vdaph.getPerpetratorUpdateDate()));

    allegationPerpetratorHistoryDao.create(allegationPerpetratorHistory);
  }

  /**
   * Delete JUnit test
   */
  @Override
  @Test
  public void testDelete() throws Exception {
    AllegationPerpetratorHistory deleted = allegationPerpetratorHistoryDao.delete(id);
    assertThat(deleted.getId(), is(id));
  }

  @Override
  @Test
  public void testDeleteEntityNotFoundException() throws Exception {
    AllegationPerpetratorHistory deleted = allegationPerpetratorHistoryDao.delete("9999999ZZZ");
    assertThat(deleted, is(nullValue()));
  }

  @Override
  @Test
  public void testUpdate() throws Exception {

    gov.ca.cwds.rest.api.domain.cms.AllegationPerpetratorHistory vdaph =
        new AllegationPerpetratorHistoryResourceBuilder().createAllegationPerpetratorHistory();

    AllegationPerpetratorHistory allegationPerpetratorHistory = new AllegationPerpetratorHistory(id,
        vdaph.getCountySpecificCode(), vdaph.getPerpetratorClientId(), vdaph.getAllegationId(),
        DomainChef.uncookDateString(vdaph.getPerpetratorUpdateDate()));

    AllegationPerpetratorHistory updated =
        allegationPerpetratorHistoryDao.update(allegationPerpetratorHistory);

    assertThat(allegationPerpetratorHistory, is(updated));

  }

  @Override
  @Test(expected = EntityNotFoundException.class)
  public void testUpdateEntityNotFoundException() throws Exception {

    gov.ca.cwds.rest.api.domain.cms.AllegationPerpetratorHistory vdaph =
        new AllegationPerpetratorHistoryResourceBuilder().createAllegationPerpetratorHistory();

    AllegationPerpetratorHistory allegationPerpetratorHistory = new AllegationPerpetratorHistory(
        "1234567ABC", vdaph.getCountySpecificCode(), vdaph.getPerpetratorClientId(),
        vdaph.getAllegationId(), DomainChef.uncookDateString(vdaph.getPerpetratorUpdateDate()));

    allegationPerpetratorHistoryDao.update(allegationPerpetratorHistory);
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

}
