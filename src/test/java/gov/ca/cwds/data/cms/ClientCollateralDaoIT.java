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

import com.fasterxml.jackson.databind.ObjectMapper;

import gov.ca.cwds.data.persistence.cms.ClientCollateral;
import gov.ca.cwds.fixture.ClientCollateralResourceBuilder;
import io.dropwizard.jackson.Jackson;

/**
 * 
 * @author CWDS API Team
 */
@SuppressWarnings("javadoc")
public class ClientCollateralDaoIT {

  static final ObjectMapper MAPPER = Jackson.newObjectMapper();

  private static SessionFactory sessionFactory;
  private static ClientCollateralDao clientCollateralDao;
  private Session session;

  /**
   * id matches src/main/resources/db.cms/ci-seeds.sql
   */
  private String thirdId = "AaxovM900b";

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
    clientCollateralDao = new ClientCollateralDao(sessionFactory);
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
   * @throws Exception test general
   */
  @Test
  public void testFind() throws Exception {
    ClientCollateral found = clientCollateralDao.find(thirdId);
    assertThat(found.getThirdId(), is(equalTo(thirdId)));
  }

  @Test
  public void testFindEntityNotFoundException() throws Exception {
    ClientCollateral found = clientCollateralDao.find("9999999ZZZ");
    assertThat(found, is(nullValue()));
  }

  /**
   * Create JUnit test
   * 
   * @throws Exception test general
   */
  @Test
  public void testCreate() throws Exception {
    gov.ca.cwds.rest.api.domain.cms.ClientCollateral validDomainClientCollateral =
        new ClientCollateralResourceBuilder().buildClientCollateral();

    ClientCollateral clientCollateral =
        new ClientCollateral(validDomainClientCollateral.getActiveIndicator(),
            validDomainClientCollateral.getCollateralClientReporterRelationshipType(),
            validDomainClientCollateral.getCommentDescription(),
            validDomainClientCollateral.getClientId(),
            validDomainClientCollateral.getCollateralIndividualId(), "ABC1235600");

    ClientCollateral create = clientCollateralDao.create(clientCollateral);
    assertThat(clientCollateral, is(create));
  }

  @Test(expected = EntityExistsException.class)
  public void testCreateExistingEntityException() throws Exception {

    gov.ca.cwds.rest.api.domain.cms.ClientCollateral validDomainClientCollateral =
        new ClientCollateralResourceBuilder().buildClientCollateral();

    ClientCollateral clientCollateral =
        new ClientCollateral(validDomainClientCollateral.getActiveIndicator(),
            validDomainClientCollateral.getCollateralClientReporterRelationshipType(),
            validDomainClientCollateral.getCommentDescription(),
            validDomainClientCollateral.getClientId(),
            validDomainClientCollateral.getCollateralIndividualId(), thirdId);

    clientCollateralDao.create(clientCollateral);

  }

  /**
   * Delete JUnit test
   * 
   * @throws Exception test general
   */
  @Test
  public void testDelete() throws Exception {
    ClientCollateral deleted = clientCollateralDao.delete(thirdId);
    assertThat(deleted.getThirdId(), is(thirdId));
  }

  @Test
  public void testDeleteEntityNotFoundException() throws Exception {
    ClientCollateral deleted = clientCollateralDao.delete("9999999ZZZ");
    assertThat(deleted, is(nullValue()));
  }

  /**
   * Update JUnit test
   * 
   * @throws Exception test general
   */
  @Test
  public void testUpdate() throws Exception {
    gov.ca.cwds.rest.api.domain.cms.ClientCollateral validDomainClientCollateral =
        new ClientCollateralResourceBuilder().buildClientCollateral();

    ClientCollateral clientCollateral =
        new ClientCollateral(validDomainClientCollateral.getActiveIndicator(),
            validDomainClientCollateral.getCollateralClientReporterRelationshipType(),
            validDomainClientCollateral.getCommentDescription(),
            validDomainClientCollateral.getClientId(),
            validDomainClientCollateral.getCollateralIndividualId(), thirdId);

    ClientCollateral updated = clientCollateralDao.update(clientCollateral);
    assertThat(clientCollateral, is(updated));

  }

  @Test(expected = EntityNotFoundException.class)
  public void testUpdateEntityNotFoundException() throws Exception {
    gov.ca.cwds.rest.api.domain.cms.ClientCollateral validDomainClientCollateral =
        new ClientCollateralResourceBuilder().buildClientCollateral();

    ClientCollateral clientCollateral =
        new ClientCollateral(validDomainClientCollateral.getActiveIndicator(),
            validDomainClientCollateral.getCollateralClientReporterRelationshipType(),
            validDomainClientCollateral.getCommentDescription(),
            validDomainClientCollateral.getClientId(),
            validDomainClientCollateral.getCollateralIndividualId(), "ABC0981267");

    clientCollateralDao.update(clientCollateral);

  }

}
