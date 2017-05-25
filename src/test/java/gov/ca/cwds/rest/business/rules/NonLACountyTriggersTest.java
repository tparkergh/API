package gov.ca.cwds.rest.business.rules;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.fasterxml.jackson.databind.ObjectMapper;

import gov.ca.cwds.data.cms.CountyOwnershipDao;
import gov.ca.cwds.data.persistence.cms.Client;
import gov.ca.cwds.data.persistence.cms.CountyOwnership;
import gov.ca.cwds.data.persistence.cms.SystemCodeTestHarness;
import gov.ca.cwds.rest.api.domain.cms.ReferralClient;

/**
 * @author CWDS API Team
 *
 */
public class NonLACountyTriggersTest {

  private static final ObjectMapper MAPPER = SystemCodeTestHarness.MAPPER;

  private CountyOwnershipDao countyOwnershipDao;
  private NonLACountyTriggers nonLaCountyTriggers;

  private static CountyOwnership countyOwnership;

  @SuppressWarnings("javadoc")
  @Rule
  public ExpectedException thrown = ExpectedException.none();

  /**
   * @throws Exception
   */
  @Before
  public void setup() throws Exception {
    countyOwnershipDao = mock(CountyOwnershipDao.class);
    nonLaCountyTriggers = new NonLACountyTriggers(countyOwnershipDao);
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

    ReferralClient request = new ReferralClient(toCreate);
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
    Assert.assertNotNull(countyOwnership);
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

    ReferralClient request = new ReferralClient(toCreate);
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
    Assert.assertNotNull(countyOwnership);
    assertThat(countyOwnership.getCounty62Flag(), is(equalTo("Y")));
  }

  /*
   * Test for checking the Client CountyOnwership created
   */
  @SuppressWarnings("javadoc")
  @Test
  public void testForClientCreatedCountyOwnershipTest() throws Exception {
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
    Assert.assertNotNull(countyOwnership);
    assertThat(countyOwnership.getEntityCode(), is(equalTo("C")));
  }


}
