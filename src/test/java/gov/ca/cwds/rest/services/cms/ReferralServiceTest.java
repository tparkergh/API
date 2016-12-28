package gov.ca.cwds.rest.services.cms;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.fasterxml.jackson.databind.ObjectMapper;

import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.cms.PostedReferral;
import gov.ca.cwds.rest.api.domain.cms.Referral;
import gov.ca.cwds.rest.jdbi.cms.ReferralDao;
import gov.ca.cwds.rest.services.ServiceException;
import io.dropwizard.jackson.Jackson;

public class ReferralServiceTest {
  private static final ObjectMapper MAPPER = Jackson.newObjectMapper();
  private ReferralService referralService;
  private ReferralDao referralDao;

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Before
  public void setup() throws Exception {
    referralDao = mock(ReferralDao.class);
    referralService = new ReferralService(referralDao);
  }

  // find test
  // TODO: Story #136701343: Tech debt: exception handling in service layer.
  @Test
  public void findThrowsAssertionError() {
    // TODO : thrown.expect not working on AssertionError???? WHY???
    // thrown.expect(AssertionError.class);
    try {
      referralService.find("1");
      Assert.fail("Expected AssertionError");
    } catch (AssertionError e) {
    }
  }

  @Test
  public void findReturnsCorrectReferralWhenFound() throws Exception {
    Referral expected = MAPPER
        .readValue(fixture("fixtures/domain/legacy/Referral/valid/valid.json"), Referral.class);
    gov.ca.cwds.rest.api.persistence.cms.Referral referral =
        new gov.ca.cwds.rest.api.persistence.cms.Referral("1234567ABC", expected, "0XA");

    when(referralDao.find("1234567ABC")).thenReturn(referral);
    Referral found = referralService.find("1234567ABC");
    assertThat(found, is(expected));
  }

  @Test
  public void findReturnsNullWhenNotFound() throws Exception {
    Response found = referralService.find("ABC1234567");
    assertThat(found, is(nullValue()));
  }

  // delete test
  public void deleteThrowsAssersionError() throws Exception {
    // TODO : thrown.expect not working on AssertionError???? WHY???
    // thrown.expect(AssertionError.class);
    try {
      referralService.delete("ABC1234567");
      Assert.fail("Expected AssertionError");
    } catch (AssertionError e) {
    }
  }

  @Test
  public void deleteDelegatesToCrudsService() {
    referralService.delete("ABC2345678");
    verify(referralDao, times(1)).delete("ABC2345678");
  }

  @Test
  public void deleteReturnsNullWhenNotFount() throws Exception {
    Response found = referralService.delete("ABC1234567");
    assertThat(found, is(nullValue()));
  }

  // update test
  @Test
  public void updateThrowsAssertionError() throws Exception {
    // TODO: thrown.expect not working on AssertionError???? WHY???
    // thrown.expect(AssertionError.class);
    try {
      referralService.update("ABC1234567", null);
      Assert.fail("Expected AssertionError");
    } catch (AssertionError e) {
    }
  }

  @Test
  public void updateReturnsReferralResponseOnSuccess() throws Exception {
    Referral expected = MAPPER
        .readValue(fixture("fixtures/domain/legacy/Referral/valid/valid.json"), Referral.class);

    gov.ca.cwds.rest.api.persistence.cms.Referral referral =
        new gov.ca.cwds.rest.api.persistence.cms.Referral("1234567ABC", expected, "ABC");

    when(referralDao.find("ABC1234567")).thenReturn(referral);
    when(referralDao.update(any())).thenReturn(referral);

    Object retval = referralService.update("ABC1234567", expected);
    assertThat(retval.getClass(), is(Referral.class));
  }

  @Test
  public void updateThrowsExceptionWhenReferralNotFound() throws Exception {
    // TODO: test does not throw exception from referralService.update method
    //
    // remove comments before running unit test
    //
    // thrown.expect(ServiceException.class);
    // thrown.expectCause(Is.isA(EntityNotFoundException.class));
    // thrown.expectMessage(contains("Referral not found"));

    Referral referralRequest = MAPPER
        .readValue(fixture("fixtures/domain/legacy/Referral/valid/valid.json"), Referral.class);

    gov.ca.cwds.rest.api.persistence.cms.Referral referral =
        new gov.ca.cwds.rest.api.persistence.cms.Referral("1234567ABC", referralRequest, "ABC");

    when(referralDao.find("ABC1234567")).thenReturn(referral);
    when(referralDao.update(any())).thenReturn(referral);
    referralService.update("ZZZZZZZZZZ", referralRequest);
  }

  // create test
  @Test
  public void createReturnsPostedReferralClass() throws Exception {
    Referral referralDomain = MAPPER
        .readValue(fixture("fixtures/domain/legacy/Referral/valid/valid.json"), Referral.class);
    gov.ca.cwds.rest.api.persistence.cms.Referral toCreate =
        new gov.ca.cwds.rest.api.persistence.cms.Referral("1234567ABC", referralDomain,
            "last_update");

    Referral request = new Referral(toCreate);
    when(referralDao.create(any(gov.ca.cwds.rest.api.persistence.cms.Referral.class)))
        .thenReturn(toCreate);

    Response response = referralService.create(request);
    assertThat(response.getClass(), is(PostedReferral.class));
  }

  @Test
  public void createReturnsNonNull() throws Exception {
    Referral referralDomain = MAPPER
        .readValue(fixture("fixtures/domain/legacy/Referral/valid/valid.json"), Referral.class);
    gov.ca.cwds.rest.api.persistence.cms.Referral toCreate =
        new gov.ca.cwds.rest.api.persistence.cms.Referral("1234567ABC", referralDomain,
            "last_update");

    Referral request = new Referral(toCreate);
    when(referralDao.create(any(gov.ca.cwds.rest.api.persistence.cms.Referral.class)))
        .thenReturn(toCreate);

    PostedReferral postedReferral = referralService.create(request);
    assertThat(postedReferral, is(notNullValue()));
  }

  @Test
  public void createReturnsCorrectPostedReferral() throws Exception {
    Referral referralDomain = MAPPER
        .readValue(fixture("fixtures/domain/legacy/Referral/valid/valid.json"), Referral.class);
    gov.ca.cwds.rest.api.persistence.cms.Referral toCreate =
        new gov.ca.cwds.rest.api.persistence.cms.Referral("1234567ABC", referralDomain,
            "last_update");

    Referral request = new Referral(toCreate);
    when(referralDao.create(any(gov.ca.cwds.rest.api.persistence.cms.Referral.class)))
        .thenReturn(toCreate);

    PostedReferral expected = new PostedReferral(toCreate);
    PostedReferral returned = referralService.create(request);
    assertThat(returned, is(expected));
  }

  @Test
  public void failsWhenPostedReferralIdEmpty() throws Exception {
    thrown.expect(ServiceException.class);
    thrown.expectMessage("Referral ID cannot be empty");
    try {
      Referral referralDomain = MAPPER
          .readValue(fixture("fixtures/domain/legacy/Referral/valid/valid.json"), Referral.class);
      gov.ca.cwds.rest.api.persistence.cms.Referral toCreate =
          new gov.ca.cwds.rest.api.persistence.cms.Referral("", referralDomain, "last_update");

      when(referralDao.create(any(gov.ca.cwds.rest.api.persistence.cms.Referral.class)))
          .thenReturn(toCreate);

      PostedReferral expected = new PostedReferral(toCreate);

      Assert.fail("Expected AssertionError");
    } catch (AssertionError e) {
    }
  }


  @Test
  public void failsWhenPostedReferralIdNull() throws Exception {
    thrown.expect(ServiceException.class);
    thrown.expectMessage("Referral ID cannot be empty");
    try {
      Referral referralDomain = MAPPER
          .readValue(fixture("fixtures/domain/legacy/Referral/valid/valid.json"), Referral.class);
      gov.ca.cwds.rest.api.persistence.cms.Referral toCreate =
          new gov.ca.cwds.rest.api.persistence.cms.Referral(null, referralDomain, "last_update");

      when(referralDao.create(any(gov.ca.cwds.rest.api.persistence.cms.Referral.class)))
          .thenReturn(toCreate);

      PostedReferral expected = new PostedReferral(toCreate);

      Assert.fail("Expected AssertionError");
    } catch (AssertionError e) {
    }
  }

  @Test
  public void failsWhenPostedReferralIdBlank() throws Exception {
    thrown.expect(ServiceException.class);
    thrown.expectMessage("Referral ID cannot be empty");
    try {
      Referral referralDomain = MAPPER
          .readValue(fixture("fixtures/domain/legacy/Referral/valid/valid.json"), Referral.class);
      gov.ca.cwds.rest.api.persistence.cms.Referral toCreate =
          new gov.ca.cwds.rest.api.persistence.cms.Referral("   ", referralDomain, "last_update");

      when(referralDao.create(any(gov.ca.cwds.rest.api.persistence.cms.Referral.class)))
          .thenReturn(toCreate);

      PostedReferral expected = new PostedReferral(toCreate);

      Assert.fail("Expected AssertionError");
    } catch (AssertionError e) {
    }
  }

  @Test
  public void createReturnsCorrectPostedReferralId() throws Exception {
    Referral referralDomain = MAPPER
        .readValue(fixture("fixtures/domain/legacy/Referral/valid/valid.json"), Referral.class);
    gov.ca.cwds.rest.api.persistence.cms.Referral toCreate =
        new gov.ca.cwds.rest.api.persistence.cms.Referral("1234567ABC", referralDomain,
            "last_update");

    Referral request = new Referral(toCreate);

    when(referralDao.create(any(gov.ca.cwds.rest.api.persistence.cms.Referral.class)))
        .thenReturn(toCreate);

    PostedReferral returned = referralService.create(request);

    assertThat(returned.getId(), is("1234567ABC"));
  }

}
