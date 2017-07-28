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

import gov.ca.cwds.data.persistence.cms.ClientRelationship;
import gov.ca.cwds.rest.api.domain.DomainChef;
import io.dropwizard.jackson.Jackson;

/**
 * 
 * @author CWDS API Team
 */
@SuppressWarnings("javadoc")
public class ClientRelationshipDaoIT {

  static final ObjectMapper MAPPER = Jackson.newObjectMapper();

  private static SessionFactory sessionFactory;
  private static ClientRelationshipDao clientRelationshipDao;
  private Session session;

  /**
   * id matches src/main/resources/db.cms/ci-seeds.sql
   */
  private String id = "Aaqj06L00h";

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
    clientRelationshipDao = new ClientRelationshipDao(sessionFactory);
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
    ClientRelationship found = clientRelationshipDao.find(id);
    assertThat(found.getId(), is(equalTo(id)));
  }

  @Test
  public void testFindEntityNotFoundException() throws Exception {
    ClientRelationship found = clientRelationshipDao.find("9999999ZZZ");
    assertThat(found, is(nullValue()));
  }

  /**
   * Create JUnit test
   * 
   * @throws Exception test general
   */
  @Test
  public void testCreate() throws Exception {
    gov.ca.cwds.rest.api.domain.cms.ClientRelationship validDomainClientRelationship =
        new gov.ca.cwds.rest.api.domain.cms.ClientRelationship("N", (short) 172, "2017-01-07",
            "SECCLIENT", "PRICLIENT", "Y", "2017-01-07");

    ClientRelationship clientRelationship =
        new ClientRelationship(validDomainClientRelationship.getAbsentParentCode(),
            validDomainClientRelationship.getClientRelationshipType(),
            DomainChef.uncookDateString(validDomainClientRelationship.getEndDate()),
            validDomainClientRelationship.getSecondaryClientId(),
            validDomainClientRelationship.getPrimaryClientId(), "ABC1234567",
            validDomainClientRelationship.getSameHomeCode(),
            DomainChef.uncookDateString(validDomainClientRelationship.getStartDate()));

    ClientRelationship create = clientRelationshipDao.create(clientRelationship);
    assertThat(clientRelationship, is(create));
  }

  @Test(expected = EntityExistsException.class)
  public void testCreateExistingEntityException() throws Exception {

    gov.ca.cwds.rest.api.domain.cms.ClientRelationship validDomainClientRelationship =
        new gov.ca.cwds.rest.api.domain.cms.ClientRelationship("N", (short) 172, "2017-01-07",
            "SECCLIENT", "PRICLIENT", "Y", "2017-01-07");

    ClientRelationship clientRelationship =
        new ClientRelationship(validDomainClientRelationship.getAbsentParentCode(),
            validDomainClientRelationship.getClientRelationshipType(),
            DomainChef.uncookDateString(validDomainClientRelationship.getEndDate()),
            validDomainClientRelationship.getSecondaryClientId(),
            validDomainClientRelationship.getPrimaryClientId(), id,
            validDomainClientRelationship.getSameHomeCode(),
            DomainChef.uncookDateString(validDomainClientRelationship.getStartDate()));

    clientRelationshipDao.create(clientRelationship);
  }

  /**
   * Delete JUnit test
   * 
   * @throws Exception test general
   */
  @Test
  public void testDelete() throws Exception {
    ClientRelationship deleted = clientRelationshipDao.delete(id);
    assertThat(deleted.getId(), is(id));
  }

  @Test
  public void testDeleteEntityNotFoundException() throws Exception {
    ClientRelationship deleted = clientRelationshipDao.delete("9999999ZZZ");
    assertThat(deleted, is(nullValue()));
  }

  /**
   * Update JUnit test
   * 
   * @throws Exception test general
   */
  @Test
  public void testUpdate() throws Exception {

    gov.ca.cwds.rest.api.domain.cms.ClientRelationship validDomainClientRelationship =
        new gov.ca.cwds.rest.api.domain.cms.ClientRelationship("N", (short) 172, "2017-01-07",
            "SECCLIENT", "PRICLIENT", "Y", "2017-01-07");

    ClientRelationship clientRelationship =
        new ClientRelationship(validDomainClientRelationship.getAbsentParentCode(),
            validDomainClientRelationship.getClientRelationshipType(),
            DomainChef.uncookDateString(validDomainClientRelationship.getEndDate()),
            validDomainClientRelationship.getSecondaryClientId(),
            validDomainClientRelationship.getPrimaryClientId(), id,
            validDomainClientRelationship.getSameHomeCode(),
            DomainChef.uncookDateString(validDomainClientRelationship.getStartDate()));

    ClientRelationship updated = clientRelationshipDao.update(clientRelationship);
    assertThat(clientRelationship, is(updated));
  }

  @Test(expected = EntityNotFoundException.class)
  public void testUpdateEntityNotFoundException() throws Exception {

    gov.ca.cwds.rest.api.domain.cms.ClientRelationship validDomainClientRelationship =
        new gov.ca.cwds.rest.api.domain.cms.ClientRelationship("N", (short) 172, "2017-01-07",
            "SECCLIENT", "PRICLIENT", "Y", "2017-01-07");

    ClientRelationship clientRelationship =
        new ClientRelationship(validDomainClientRelationship.getAbsentParentCode(),
            validDomainClientRelationship.getClientRelationshipType(),
            DomainChef.uncookDateString(validDomainClientRelationship.getEndDate()),
            validDomainClientRelationship.getSecondaryClientId(),
            validDomainClientRelationship.getPrimaryClientId(), "ABC9999123",
            validDomainClientRelationship.getSameHomeCode(),
            DomainChef.uncookDateString(validDomainClientRelationship.getStartDate()));


    clientRelationshipDao.update(clientRelationship);
  }

}
