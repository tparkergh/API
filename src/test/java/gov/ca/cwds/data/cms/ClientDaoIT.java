package gov.ca.cwds.data.cms;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

import java.io.IOException;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.hamcrest.junit.ExpectedException;
import org.hibernate.Query;
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
import com.fasterxml.jackson.databind.ObjectMapper;

import gov.ca.cwds.data.persistence.cms.Client;
import io.dropwizard.jackson.Jackson;

/**
 * 
 * @author CWDS API Team
 */
@SuppressWarnings("javadoc")
public class ClientDaoIT {
  static final ObjectMapper MAPPER = Jackson.newObjectMapper();

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  private static ClientDao clientDao;
  private static SessionFactory sessionFactory;
  private Session session;

  @BeforeClass
  public static void beforeClass() {
    sessionFactory = new Configuration().configure().buildSessionFactory();
    clientDao = new ClientDao(sessionFactory);
  }

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

  @Test
  public void shouldReturnANonNullValueWhenFindingAllClients() throws Exception {
    Query query = session.getNamedQuery("gov.ca.cwds.data.persistence.cms.Client.findAll");
    assertThat(query, is(notNullValue()));
  }

  // Test on mainframe DB2 with schema CWSNS2/4.
  // @Test
  // public void shouldRunSuccessfullWhenCallingStoredProc() {
  // clientDao.callStoredProc();
  // System.out.println("Survived calling stored proc!");
  // }

  @Test
  public void shouldFindTheClientWhenSearchingById() throws IOException {
    String id = "VCITznlBPu";
    Client pers = createClientWithId(id);
    clientDao.create(pers);

    Client found = clientDao.find(id);
    assertThat(found.getId(), is(id));
    assertThat(found.getLastUpdatedTime(), is(pers.getLastUpdatedTime()));
  }

  @Test
  public void shouldReturnNullWhenSearchingByIdThatDoesNotExist() throws Exception {
    String id = "9999999ZZZ";
    Client found = clientDao.find(id);
    assertThat(found, is(nullValue()));
  }

  @Test
  public void shouldSaveANewClientGivenAValidClient() throws Exception {
    Client pers = createClient();
    Client created = clientDao.create(pers);
    assertThat(created, is(pers));
  }

  @Test
  public void shouldThrowExceptionWhenSavingClientGivenAClientIdThatAlreadyExists()
      throws Exception {
    String id = "AaiU7IW0Rt";
    Client pers = createClientWithId(id);
    clientDao.create(pers);

    thrown.expect(EntityExistsException.class);
    Client clientWithExistingId = createClientWithId(id);

    clientDao.create(clientWithExistingId);
  }

  @Test
  public void shouldDeleteAClientWithAnId() throws IOException {
    String id = "AaiU7IW0Rt";
    Client pers = createClientWithId(id);
    clientDao.create(pers);

    Client deleted = clientDao.delete(id);
    assertThat(deleted.getId(), is(id));
  }

  @Test
  public void shouldReturnNullWhenDeletingAClientByANonExistentId() throws Exception {
    String id = "9999999ZZZ";
    Client deleted = clientDao.delete(id);
    assertThat(deleted, is(nullValue()));
  }

  @Test
  public void shouldReturnTheUpdatedClientGivenAnExistingClient() throws Exception {
    String id = "AaiU7IW0Rt";
    Client pers = createClientWithId(id);
    clientDao.create(pers);

    Client updated = clientDao.update(pers);
    assertThat(updated, is(pers));
  }

  @Test
  public void shoudlThrowExceptionWhenIdNotFound() throws Exception {
    thrown.expect(EntityNotFoundException.class);
    Client vc = validClient();
    Client pers = createClientWithId("AasRx3r0HA");
    clientDao.update(pers);
  }

  private Client validClient() throws JsonParseException, JsonMappingException, IOException {
    Client validClient =
        MAPPER.readValue(fixture("fixtures/persistent/Client/valid/valid.json"), Client.class);
    return validClient;
  }

  private Client createClient() throws IOException {
    return createClientWithId(null);
  }

  private Client createClientWithId(String id) throws IOException {
    Client vc = validClient();
    String clientId = id == null ? vc.getId() : id;
    return new Client(vc.getAdjudicatedDelinquentIndicator(), vc.getAdoptionStatusCode(),
        vc.getAlienRegistrationNumber(), vc.getBirthCity(), vc.getBirthCountryCodeType(),
        vc.getBirthDate(), vc.getBirthFacilityName(), vc.getBirthStateCodeType(),
        vc.getBirthplaceVerifiedIndicator(), vc.getChildClientIndicatorVar(),
        vc.getClientIndexNumber(), vc.getCommentDescription(), vc.getCommonFirstName(),
        vc.getCommonLastName(), vc.getCommonMiddleName(), vc.getConfidentialityActionDate(),
        vc.getConfidentialityInEffectIndicator(), vc.getCreationDate(),
        vc.getCurrCaChildrenServIndicator(), vc.getCurrentlyOtherDescription(),
        vc.getCurrentlyRegionalCenterIndicator(), vc.getDeathDate(),
        vc.getDeathDateVerifiedIndicator(), vc.getDeathPlace(), vc.getDeathReasonText(),
        vc.getDriverLicenseNumber(), vc.getDriverLicenseStateCodeType(), vc.getEmailAddress(),
        vc.getEstimatedDobCode(), vc.getEthUnableToDetReasonCode(),
        vc.getFatherParentalRightTermDate(), vc.getGenderCode(), vc.getHealthSummaryText(),
        vc.getHispUnableToDetReasonCode(), vc.getHispanicOriginCode(), clientId,
        vc.getImmigrationCountryCodeType(), vc.getImmigrationStatusType(),
        vc.getIncapacitatedParentCode(), vc.getIndividualHealthCarePlanIndicator(),
        vc.getLimitationOnScpHealthIndicator(), vc.getLiterateCode(),
        vc.getMaritalCohabitatnHstryIndicatorVar(), vc.getMaritalStatusType(),
        vc.getMilitaryStatusCode(), vc.getMotherParentalRightTermDate(),
        vc.getNamePrefixDescription(), vc.getNameType(), vc.getOutstandingWarrantIndicator(),
        vc.getPrevCaChildrenServIndicator(), vc.getPrevOtherDescription(),
        vc.getPrevRegionalCenterIndicator(), vc.getPrimaryEthnicityType(),
        vc.getPrimaryLanguageType(), vc.getReligionType(), vc.getSecondaryLanguageType(),
        vc.getSensitiveHlthInfoOnFileIndicator(), vc.getSensitivityIndicator(),
        vc.getSoc158PlacementCode(), vc.getSoc158SealedClientIndicator(),
        vc.getSocialSecurityNumChangedCode(), vc.getSocialSecurityNumber(),
        vc.getSuffixTitleDescription(), vc.getTribalAncestryClientIndicatorVar(),
        vc.getTribalMembrshpVerifctnIndicatorVar(), vc.getUnemployedParentCode(),
        vc.getZippyCreatedIndicator(), null);
  }

}
