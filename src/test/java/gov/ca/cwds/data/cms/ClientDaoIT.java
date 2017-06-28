package gov.ca.cwds.data.cms;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

import com.fasterxml.jackson.databind.ObjectMapper;
import gov.ca.cwds.data.persistence.cms.CmsKeyIdGenerator;
import io.dropwizard.jackson.Jackson;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.hamcrest.junit.ExpectedException;
import org.hibernate.FlushMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.annotations.Table;
import org.hibernate.cfg.Configuration;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import gov.ca.cwds.data.junit.template.DaoTestTemplate;
import gov.ca.cwds.data.persistence.cms.Client;

/**
 * 
 * @author CWDS API Team
 */
public class ClientDaoIT {
  static final ObjectMapper MAPPER = Jackson.newObjectMapper();

  private static final DateFormat TIMESTAMP_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

  @SuppressWarnings("javadoc")
  @Rule
  public ExpectedException thrown = ExpectedException.none();

  private static ClientDao clientDao;
  private static SessionFactory sessionFactory;
  private Session session;
  Transaction transaction;

  @SuppressWarnings("javadoc")
  @BeforeClass
  public static void beforeClass() {
    sessionFactory = new Configuration().configure().buildSessionFactory();
    clientDao = new ClientDao(sessionFactory);
  }

  @SuppressWarnings("javadoc")
  @AfterClass
  public static void afterClass() {
    sessionFactory.close();
  }

  @Before
  public void setup() {
    session = sessionFactory.getCurrentSession();
    session.setFlushMode(FlushMode.COMMIT);
    transaction = session.beginTransaction();

    sessionFactory.getCurrentSession().createQuery("delete from gov.ca.cwds.data.persistence.cms.Client").executeUpdate();

  }

  @After
  public void teardown() throws Exception {
    if (session.getTransaction().getStatus().canRollback()) {
      session.getTransaction().rollback();
    }
  }

  @Test
  public void shouldReturnANonNullValueWhenFindingAllClients() throws Exception {
    Query query = session.getNamedQuery("gov.ca.cwds.data.persistence.cms.Client.findAll");
    assertThat(query, is(notNullValue()));
  }

  //TODO:Deprecated, no longer using findall. Research and remove test and code
  @SuppressWarnings("unchecked")
  @Ignore
  @Test
  public void shouldReturntheSavedClientsWhenPerformingAFindAll() throws IOException {
    List<String> ids = Arrays.asList("AaiU7IW0Rt","baiT7IX0Rt","SaiUDIWmRt","QaWU7EW0Rt");
    for (String id : ids) {
      Client entity = createClientWithId(id);

      gov.ca.cwds.rest.api.domain.cms.Client domainClient = new gov.ca.cwds.rest.api.domain.cms.Client(entity, true);
      Client entityClient = new Client(id, domainClient, "0X5");
      Client saved = clientDao.create(entityClient);
      session.flush();
    };

    Query query = session.getNamedQuery("gov.ca.cwds.data.persistence.cms.Client.findAll");
    final List<Client> list = query.list();
    System.out.println("size of query list is: " + list.size());
    for (Client c : list) {
      System.out.println("id " + c.getId() + " " + c.getSensitivityIndicator() + " "
          + c.getSoc158SealedClientIndicator() + " " + c.getLastUpdatedTime());
    }
    assertThat(query.list().size(), is(4));
  }

  /**
   * Test on mainframe DB2 with schema CWSNS2/4.
   */
  // @Test
  public void shouldRunSuccessfullWhenCallingStoredProc() {
    clientDao.callStoredProc();
    System.out.println("Survived calling stored proc!");
  }

  @Test
  public void shouldFindTheClientWhenSearchingById() throws IOException {
    String id = "AaiU7IW0Rt";
    Client pers = createClientWithId(id);
    clientDao.create(pers);

    Client found = clientDao.find(id);
    assertThat(found.getId(), is(id));
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
  public void shouldThrowExceptionWhenSavingClientGivenAClientIdThatAlreadyExists() throws Exception {
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
    Client pers = createClientWithId("AasRx3r0Ha");
    clientDao.update(pers);
  }

  private Client validClient() throws JsonParseException, JsonMappingException, IOException {
    Client validClient =
        MAPPER.readValue(fixture("fixtures/persistence/Client/valid/valid.json"), Client.class);
    return validClient;
  }

  private Client createClient() throws IOException {
   return createClientWithId(null)  ;
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
