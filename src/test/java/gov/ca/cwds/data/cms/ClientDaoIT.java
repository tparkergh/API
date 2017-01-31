package gov.ca.cwds.data.cms;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

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

import gov.ca.cwds.data.junit.template.DaoTestTemplate;
import gov.ca.cwds.data.persistence.cms.Client;

/**
 * @author CWDS API Team
 *
 */
public class ClientDaoIT implements DaoTestTemplate {
  private static final DateFormat TIMESTAMP_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

  @SuppressWarnings("javadoc")
  @Rule
  public ExpectedException thrown = ExpectedException.none();

  private static ClientDao clientDao;
  private static SessionFactory sessionFactory;
  private Session session;

  @SuppressWarnings("javadoc")
  @BeforeClass
  public static void beforeClass() {
    sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
    clientDao = new ClientDao(sessionFactory);
  }

  @SuppressWarnings("javadoc")
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
  public void teardown() throws Exception {
    session.getTransaction().rollback();
  }

  @Override
  @Test
  public void testFindAllNamedQueryExist() throws Exception {
    Query query = session.getNamedQuery("gov.ca.cwds.data.persistence.cms.Client.findAll");
    assertThat(query, is(notNullValue()));
  }

  @SuppressWarnings("unchecked")
  @Override
  @Test
  public void testFindAllReturnsCorrectList() {
    Query query = session.getNamedQuery("gov.ca.cwds.data.persistence.cms.Client.findAll");
    final List<Client> list = query.list();
    System.out.println("size of query list is: " + list.size());
    for (Client c : list) {
      System.out.println("id " + c.getId() + " " + c.getSensitivityIndicator() + " "
          + c.getSoc158SealedClientIndicator() + " " + c.getLastUpdatedTime());
    }
    assertThat(query.list().size(), is(2));
  }

  @SuppressWarnings("javadoc")
  @Test
  public void testFindAllUpdatedAfterNamedQueryExists() throws Exception {
    Query query =
        session.getNamedQuery("gov.ca.cwds.data.persistence.cms.Client.findAllUpdatedAfter");
    assertThat(query, is(notNullValue()));
  }

  @SuppressWarnings("javadoc")
  @Test
  public void testFindAllUpdatedAfterReturnsCorrectList() throws Exception {
    Query query =
        session.getNamedQuery("gov.ca.cwds.data.persistence.cms.Client.findAllUpdatedAfter")
            .setDate("after", TIMESTAMP_FORMAT.parse("2004-01-02 00:00:00"));

    @SuppressWarnings("unchecked")
    final List<Client> list = query.list();
    System.out.println("size of query list is: " + list.size());
    for (Client c : list) {
      System.out.println("id " + c.getId() + " " + c.getSensitivityIndicator() + " "
          + c.getSoc158SealedClientIndicator() + " " + c.getLastUpdatedTime());
    }

    assertThat(query.list().size(), is(1));
  }

  @Override
  @Test
  public void testFind() {
    String id = "AaiU7IW0Rt";
    Client found = clientDao.find(id);
    assertThat(found.getId(), is(id));
  }

  @Override
  @Test
  public void testFindEntityNotFoundException() throws Exception {
    String id = "9999999ZZZ";
    Client found = clientDao.find(id);
    assertThat(found, is(nullValue()));
  }

  @Override
  @Test
  public void testCreate() throws Exception {

    Client vc = validClient();
    Client pers = new Client(vc.getAdjudicatedDelinquentIndicator(), vc.getAdoptionStatusCode(),
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
        vc.getHispUnableToDetReasonCode(), vc.getHispanicOriginCode(), vc.getId(),
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
        vc.getZippyCreatedIndicator());
    Client created = clientDao.create(pers);
    assertThat(created, is(pers));
  }

  @Override
  @Test
  public void testCreateExistingEntityException() throws Exception {
    thrown.expect(EntityExistsException.class);
    Client vc = validClient();
    Client pers = new Client(vc.getAdjudicatedDelinquentIndicator(), vc.getAdoptionStatusCode(),
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
        vc.getHispUnableToDetReasonCode(), vc.getHispanicOriginCode(), "AaiU7IW0Rt",
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
        vc.getZippyCreatedIndicator());

    clientDao.create(pers);
  }

  @Override
  @Test
  public void testDelete() {
    String id = "AaiU7IW0Rt";
    Client deleted = clientDao.delete(id);
    assertThat(deleted.getId(), is(id));
  }

  @Override
  public void testDeleteEntityNotFoundException() throws Exception {
    String id = "9999999ZZZ";
    Client deleted = clientDao.delete(id);
    assertThat(deleted, is(nullValue()));
  }

  @Override
  @Test
  public void testUpdate() throws Exception {
    Client vc = validClient();
    Client pers = new Client(vc.getAdjudicatedDelinquentIndicator(), vc.getAdoptionStatusCode(),
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
        vc.getHispUnableToDetReasonCode(), vc.getHispanicOriginCode(), "AaiU7IW0Rt",
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
        vc.getZippyCreatedIndicator());
    Client updated = clientDao.update(pers);
    assertThat(updated, is(pers));
  }

  @Override
  @Test
  public void testUpdateEntityNotFoundException() throws Exception {
    thrown.expect(EntityNotFoundException.class);
    Client vc = validClient();
    Client pers = new Client(vc.getAdjudicatedDelinquentIndicator(), vc.getAdoptionStatusCode(),
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
        vc.getHispUnableToDetReasonCode(), vc.getHispanicOriginCode(), "AasRx3r0Ha",
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
        vc.getZippyCreatedIndicator());

    clientDao.update(pers);

  }

  private Client validClient() throws JsonParseException, JsonMappingException, IOException {
    Client validClient =
        MAPPER.readValue(fixture("fixtures/persistence/Client/valid/valid.json"), Client.class);
    return validClient;
  }

}
