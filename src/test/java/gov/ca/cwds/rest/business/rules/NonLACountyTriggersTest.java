package gov.ca.cwds.rest.business.rules;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.fasterxml.jackson.databind.ObjectMapper;

import gov.ca.cwds.data.cms.CountyOwnershipDao;
import gov.ca.cwds.data.cms.ReferralClientDao;
import gov.ca.cwds.data.cms.ReferralDao;
import gov.ca.cwds.data.persistence.cms.Client;
import gov.ca.cwds.data.persistence.cms.ClientAddress;
import gov.ca.cwds.data.persistence.cms.CountyOwnership;
import gov.ca.cwds.data.persistence.cms.SystemCodeTestHarness;
import gov.ca.cwds.fixture.AssignmentResourceBuilder;
import gov.ca.cwds.fixture.ClientAddressResourceBuilder;
import gov.ca.cwds.fixture.ReferralClientResourceBuilder;
import gov.ca.cwds.fixture.ReferralResourceBuilder;
import gov.ca.cwds.rest.api.domain.cms.Assignment;
import gov.ca.cwds.rest.api.domain.cms.Referral;
import gov.ca.cwds.rest.api.domain.cms.ReferralClient;

/**
 * @author CWDS API Team
 *
 */
public class NonLACountyTriggersTest {

  private static final ObjectMapper MAPPER = SystemCodeTestHarness.MAPPER;

  private CountyOwnershipDao countyOwnershipDao;
  private ReferralDao referralDao;
  private ReferralClientDao referralClientDao;
  private NonLACountyTriggers nonLaCountyTriggers;

  private static CountyOwnership countyOwnership;

  @SuppressWarnings("javadoc")
  @Rule
  public ExpectedException thrown = ExpectedException.none();

  /**
   * @throws Exception general test error
   */
  @Before
  public void setup() throws Exception {
    countyOwnershipDao = mock(CountyOwnershipDao.class);
    referralDao = mock(ReferralDao.class);
    referralClientDao = mock(ReferralClientDao.class);
    nonLaCountyTriggers =
        new NonLACountyTriggers(countyOwnershipDao, referralDao, referralClientDao);
    countyOwnership = null;
  }

  /*
   * Test for checking the referral Client CountyOnwership created or updated through the PUT method
   */
  @SuppressWarnings("javadoc")
  @Test
  public void testChecksReferralClientCreatedCountyOwnership() throws Exception {
    ReferralClient referralClientDomain = MAPPER.readValue(
        fixture("fixtures/legacy/business/rules/nonLaCountyTrigger/referralClientValid.json"),
        ReferralClient.class);
    gov.ca.cwds.data.persistence.cms.ReferralClient toCreate =
        new gov.ca.cwds.data.persistence.cms.ReferralClient(referralClientDomain, "ABC");

    // ReferralClient request = new ReferralClient(toCreate);
    when(countyOwnershipDao.find(any(String.class))).thenReturn(null);
    when(countyOwnershipDao.create(any(CountyOwnership.class)))
        .thenAnswer(new Answer<CountyOwnership>() {

          @Override
          public CountyOwnership answer(InvocationOnMock invocation) throws Throwable {

            CountyOwnership report = (CountyOwnership) invocation.getArguments()[0];
            countyOwnership = report;
            return report;
          }
        });
    nonLaCountyTriggers.createAndUpdateReferralClientCoutyOwnership(toCreate);
    assertThat(countyOwnership, is(notNullValue()));
    assertThat(countyOwnership.getEntityCode(), is(equalTo("C")));
  }

  /*
   * Test for checking the referral Client CountyOnwership updated
   */
  @SuppressWarnings("javadoc")
  @Test
  public void testForReferralClientUpdatedCountyOwnership() throws Exception {
    ReferralClient referralClientDomain = MAPPER.readValue(
        fixture("fixtures/legacy/business/rules/nonLaCountyTrigger/referralClientValid.json"),
        ReferralClient.class);
    gov.ca.cwds.data.persistence.cms.ReferralClient toCreate =
        new gov.ca.cwds.data.persistence.cms.ReferralClient(referralClientDomain, "ABC");

    // ReferralClient request = new ReferralClient(toCreate);
    when(countyOwnershipDao.find(any(String.class))).thenReturn(new CountyOwnership());
    when(countyOwnershipDao.update(any(CountyOwnership.class)))
        .thenAnswer(new Answer<CountyOwnership>() {

          @Override
          public CountyOwnership answer(InvocationOnMock invocation) throws Throwable {

            CountyOwnership report = (CountyOwnership) invocation.getArguments()[0];
            countyOwnership = report;
            return report;
          }
        });
    nonLaCountyTriggers.createAndUpdateReferralClientCoutyOwnership(toCreate);
    assertThat(countyOwnership, is(notNullValue()));
    assertThat(countyOwnership.getCounty62Flag(), is(equalTo("Y")));
  }

  /*
   * Test for checking the Client CountyOnwership created
   */
  @SuppressWarnings("javadoc")
  @Test
  public void testForClientCreatedCountyOwnership() throws Exception {
    gov.ca.cwds.rest.api.domain.cms.Client ClientDomain = MAPPER.readValue(
        fixture("fixtures/legacy/business/rules/nonLaCountyTrigger/clientValid.json"),
        gov.ca.cwds.rest.api.domain.cms.Client.class);
    Client toCreate = new Client("ABC1234567", ClientDomain, "ABC");
    when(countyOwnershipDao.create(any(CountyOwnership.class)))
        .thenAnswer(new Answer<CountyOwnership>() {

          @Override
          public CountyOwnership answer(InvocationOnMock invocation) throws Throwable {

            CountyOwnership report = (CountyOwnership) invocation.getArguments()[0];
            countyOwnership = report;
            return report;
          }
        });
    nonLaCountyTriggers.createClientCountyTrigger(toCreate);
    assertThat(countyOwnership, is(notNullValue()));
    assertThat(countyOwnership.getEntityCode(), is(equalTo("C")));
  }

  /**
   * Test for ClientAddress CountyOwnership created
   * 
   * @throws Exception - exception
   */
  @Test
  public void testForClientAddressCreatedCountyOwnership() throws Exception {

    gov.ca.cwds.rest.api.domain.cms.ClientAddress clientAddressDomain =
        new ClientAddressResourceBuilder().buildClientAddress();
    ClientAddress toCreate = new ClientAddress("ABC1234567", clientAddressDomain, "ABC");

    ReferralClient referralClientDomain =
        new ReferralClientResourceBuilder().setCountySpecificCode("62").buildReferralClient();
    gov.ca.cwds.data.persistence.cms.ReferralClient referralClient =
        new gov.ca.cwds.data.persistence.cms.ReferralClient(referralClientDomain, "ABC");

    when(referralClientDao.find(any(String.class))).thenReturn(referralClient);
    when(countyOwnershipDao.create(any(CountyOwnership.class)))
        .thenAnswer(new Answer<CountyOwnership>() {

          @Override
          public CountyOwnership answer(InvocationOnMock invocation) throws Throwable {

            CountyOwnership report = (CountyOwnership) invocation.getArguments()[0];
            countyOwnership = report;
            return report;
          }
        });
    nonLaCountyTriggers.createAndUpdateClientAddressCoutyOwnership(toCreate);
    assertThat(countyOwnership, is(notNullValue()));
    assertThat(countyOwnership.getEntityCode(), is(equalTo("A")));
    assertThat(countyOwnership.getCounty62Flag(), is(equalTo("Y")));
  }

  /**
   * Test for updating the countyOwnership for the exisiting Address Id
   * 
   * @throws Exception - exception
   */
  @Test
  public void testForClientAddressUpdatedCountyOwnership() throws Exception {

    gov.ca.cwds.rest.api.domain.cms.ClientAddress clientAddressDomain =
        new ClientAddressResourceBuilder().buildClientAddress();
    ClientAddress toCreate = new ClientAddress("ABC1234567", clientAddressDomain, "ABC");

    ReferralClient referralClientDomain =
        new ReferralClientResourceBuilder().setCountySpecificCode("54").buildReferralClient();
    gov.ca.cwds.data.persistence.cms.ReferralClient referralClient =
        new gov.ca.cwds.data.persistence.cms.ReferralClient(referralClientDomain, "ABC");

    when(referralClientDao.find(any(String.class))).thenReturn(referralClient);

    when(countyOwnershipDao.find(any(String.class))).thenReturn(new CountyOwnership());
    when(countyOwnershipDao.update(any(CountyOwnership.class)))
        .thenAnswer(new Answer<CountyOwnership>() {

          @Override
          public CountyOwnership answer(InvocationOnMock invocation) throws Throwable {

            CountyOwnership report = (CountyOwnership) invocation.getArguments()[0];
            countyOwnership = report;
            return report;
          }
        });

    nonLaCountyTriggers.createAndUpdateClientAddressCoutyOwnership(toCreate);
    assertThat(countyOwnership, is(notNullValue()));
    assertThat(countyOwnership.getCounty54Flag(), is(equalTo("Y")));
  }


  /**
   * Test for Referral CountyOwnership created
   * 
   * @throws Exception - exception
   */
  @Test
  public void testForReferralCreatedCountyOwnership() throws Exception {

    Assignment assignmentDomain = new AssignmentResourceBuilder().buildAssignment();
    gov.ca.cwds.data.persistence.cms.Assignment toCreate =
        new gov.ca.cwds.data.persistence.cms.Assignment("ABC1234567", assignmentDomain, "ABC");

    Referral referralClientDomain =
        new ReferralResourceBuilder().setCountySpecificCode("62").build();
    gov.ca.cwds.data.persistence.cms.Referral referral =
        new gov.ca.cwds.data.persistence.cms.Referral("ABC1234567", referralClientDomain, "0X5");

    when(referralDao.find(any(String.class))).thenReturn(referral);
    when(countyOwnershipDao.create(any(CountyOwnership.class)))
        .thenAnswer(new Answer<CountyOwnership>() {

          @Override
          public CountyOwnership answer(InvocationOnMock invocation) throws Throwable {

            CountyOwnership report = (CountyOwnership) invocation.getArguments()[0];
            countyOwnership = report;
            return report;
          }
        });
    nonLaCountyTriggers.createAndUpdateReferralCoutyOwnership(toCreate);
    assertThat(countyOwnership, is(notNullValue()));
    assertThat(countyOwnership.getEntityCode(), is(equalTo("R")));
    assertThat(countyOwnership.getCounty62Flag(), is(equalTo("Y")));
  }

  /**
   * Test for updating the countyOwnership for the exisiting Referral Id
   * 
   * @throws Exception - exception
   */
  @Test
  public void testForReferralUpdatedCountyOwnership() throws Exception {

    Assignment assignmentDomain = new AssignmentResourceBuilder().buildAssignment();
    gov.ca.cwds.data.persistence.cms.Assignment toCreate =
        new gov.ca.cwds.data.persistence.cms.Assignment("ABC1234567", assignmentDomain, "ABC");

    Referral referralClientDomain =
        new ReferralResourceBuilder().setCountySpecificCode("54").build();
    gov.ca.cwds.data.persistence.cms.Referral referral =
        new gov.ca.cwds.data.persistence.cms.Referral("ABC1234567", referralClientDomain, "0X5");

    when(referralDao.find(any(String.class))).thenReturn(referral);
    when(countyOwnershipDao.find(any(String.class))).thenReturn(new CountyOwnership());
    when(countyOwnershipDao.update(any(CountyOwnership.class)))
        .thenAnswer(new Answer<CountyOwnership>() {

          @Override
          public CountyOwnership answer(InvocationOnMock invocation) throws Throwable {

            CountyOwnership report = (CountyOwnership) invocation.getArguments()[0];
            countyOwnership = report;
            return report;
          }
        });
    nonLaCountyTriggers.createAndUpdateReferralCoutyOwnership(toCreate);
    assertThat(countyOwnership, is(notNullValue()));
    assertThat(countyOwnership.getCounty54Flag(), is(equalTo("Y")));
  }

}
