package gov.ca.cwds.rest.services.cms;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.validation.Validation;
import javax.validation.Validator;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import gov.ca.cwds.data.cms.ReferralDao;
import gov.ca.cwds.data.cms.StaffPersonDao;
import gov.ca.cwds.data.persistence.cms.StaffPerson;
import gov.ca.cwds.data.rules.TriggerTablesDao;
import gov.ca.cwds.fixture.ReferralResourceBuilder;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.cms.PostedReferral;
import gov.ca.cwds.rest.api.domain.cms.Referral;
import gov.ca.cwds.rest.business.rules.LACountyTrigger;
import gov.ca.cwds.rest.business.rules.NonLACountyTriggers;
import gov.ca.cwds.rest.services.ServiceException;
import gov.ca.cwds.rest.services.junit.template.ServiceTestTemplate;

/**
 * @author CWDS API Team
 */
public class ReferralServiceTest implements ServiceTestTemplate {

  private AssignmentService assignmentService;
  private DrmsDocumentService drmsDocumentService;
  private AddressService addressService;
  private LongTextService longTextService;
  private ReferralService referralService;
  private ReferralDao referralDao;
  private NonLACountyTriggers nonLACountyTriggers;
  private LACountyTrigger laCountyTrigger;
  private TriggerTablesDao triggerTablesDao;
  private StaffPersonDao staffpersonDao;
  private StaffPersonIdRetriever staffPersonIdRetriever;
  private Validator validator;

  private static Boolean isLaCountyTrigger = false;

  @SuppressWarnings("javadoc")
  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Override
  @Before
  public void setup() throws Exception {
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    referralDao = mock(ReferralDao.class);
    nonLACountyTriggers = mock(NonLACountyTriggers.class);
    laCountyTrigger = mock(LACountyTrigger.class);
    triggerTablesDao = mock(TriggerTablesDao.class);
    staffpersonDao = mock(StaffPersonDao.class);
    assignmentService = mock(AssignmentService.class);
    drmsDocumentService = mock(DrmsDocumentService.class);
    addressService = mock(AddressService.class);
    longTextService = mock(LongTextService.class);

    staffPersonIdRetriever = mock(StaffPersonIdRetriever.class);
    referralService = new ReferralService(referralDao, nonLACountyTriggers, laCountyTrigger,
        triggerTablesDao, staffpersonDao, staffPersonIdRetriever, assignmentService, validator,
        drmsDocumentService, addressService, longTextService);
  }

  // find test
  @Override
  @Test
  public void testFindThrowsAssertionError() {
    thrown.expect(AssertionError.class);
    try {
      referralService.find(1);
    } catch (AssertionError e) {
      assertEquals("Expected AssertionError", e.getMessage());
    }
  }

  @Override
  @Test
  public void testFindReturnsCorrectEntity() throws Exception {
    Referral expected = MAPPER
        .readValue(fixture("fixtures/domain/legacy/Referral/valid/valid.json"), Referral.class);

    // #145948067: the Referral domain bean doesn't store the primary key???
    final String key = "1234567ABC";

    gov.ca.cwds.data.persistence.cms.Referral referral =
        new gov.ca.cwds.data.persistence.cms.Referral(key, expected, "0XA");

    when(referralDao.find(key)).thenReturn(referral);
    Referral found = referralService.find(key);
    assertThat(found, is(expected));
  }

  @Override
  @Test
  public void testFindReturnsNullWhenNotFound() throws Exception {
    Response found = referralService.find("ABC1234567");
    assertThat(found, is(nullValue()));
  }

  // delete test
  @Override
  @Test
  public void testDeleteThrowsAssertionError() throws Exception {
    thrown.expect(AssertionError.class);
    try {
      referralService.delete(1234);
    } catch (AssertionError e) {
      assertEquals("Expected AssertionError", e.getMessage());
    }
  }

  @Override
  @Test
  public void testDeleteDelegatesToCrudsService() {
    referralService.delete("ABC2345678");
    verify(referralDao, times(1)).delete("ABC2345678");
  }

  @Override
  @Test
  public void testDeleteReturnsNullWhenNotFound() throws Exception {
    Response found = referralService.delete("ABC1234567");
    assertThat(found, is(nullValue()));
  }

  @Override
  @Test
  public void testDeleteReturnsClass() throws Exception {
    // delete success
    Referral referralDomain = new ReferralResourceBuilder().build();

    gov.ca.cwds.data.persistence.cms.Referral referral =
        new gov.ca.cwds.data.persistence.cms.Referral("1234567ABC", referralDomain, "ABC");

    when(referralDao.delete("ABC1234567")).thenReturn(referral);

    Referral returned = referralService.delete("ABC1234567");

    assertThat(returned.getClass(), is(Referral.class));

  }

  @Override
  public void testDeleteThrowsNotImplementedException() throws Exception {
    // delete is implemented

  }


  // update test
  @Override
  @Test
  public void testUpdateThrowsAssertionError() throws Exception {
    thrown.expect(AssertionError.class);
    try {
      referralService.update("ABC1234567", null);
    } catch (AssertionError e) {
      assertEquals("Expected AssertionError", e.getMessage());
    }
  }

  @SuppressWarnings("javadoc")
  @Test
  public void testUpdateThrowsAssertionErrorNullPrimaryKey() throws Exception {
    thrown.expect(AssertionError.class);
    try {
      Referral referralDomain = new ReferralResourceBuilder().build();
      gov.ca.cwds.data.persistence.cms.Referral toCreate =
          new gov.ca.cwds.data.persistence.cms.Referral("1234567ABC", referralDomain, "0XA");

      Referral request = new Referral(toCreate);
      referralService.update(null, request);
    } catch (AssertionError e) {
      assertEquals("Expected AssertionError", e.getMessage());
    }
  }

  @Override
  @Test
  public void testUpdateReturnsDomain() throws Exception {
    Referral expected = new ReferralResourceBuilder().build();

    gov.ca.cwds.data.persistence.cms.Referral referral =
        new gov.ca.cwds.data.persistence.cms.Referral("1234567ABC", expected, "ABC");

    when(referralDao.update(any(gov.ca.cwds.data.persistence.cms.Referral.class)))
        .thenReturn(referral);

    Object retval = referralService.update("1234567ABC", expected);
    assertThat(retval.getClass(), is(Referral.class));
  }

  @Override
  @Test
  public void testUpdateThrowsServiceException() throws Exception {

    try {
      Referral referralRequest = new ReferralResourceBuilder().build();

      when(referralDao.update(any())).thenThrow(EntityNotFoundException.class);

      referralService.update("ZZZZZZZ0X5", referralRequest);

      Assert.fail("Expected EntityNotFoundException was not thrown");

    } catch (Exception ex) {
      assertEquals(ex.getClass(), ServiceException.class);
    }
  }

  @Override
  public void testFindThrowsNotImplementedException() throws Exception {

  }

  @Override
  public void testUpdateReturnsCorrectEntity() throws Exception {

  }

  @Override
  public void testUpdateThrowsNotImplementedException() throws Exception {

  }

  // create test
  @Override
  @Test
  public void testCreateThrowsAssertionError() throws Exception {
    thrown.expect(AssertionError.class);
    try {
      referralService.create(null);
    } catch (AssertionError e) {
      assertEquals("Expected AssertionError", e.getMessage());
    }

  }

  @SuppressWarnings("javadoc")
  @Test
  public void referralServiceCreateThrowsEntityExistsException() throws Exception {
    try {
      Referral referralRequest = new ReferralResourceBuilder().build();

      when(referralDao.create(any())).thenThrow(EntityExistsException.class);

      referralService.create(referralRequest);
    } catch (Exception e) {
      assertEquals(e.getClass(), ServiceException.class);
    }
  }

  @Override
  @Test
  public void testCreateReturnsPostedClass() throws Exception {
    Referral referralDomain = new ReferralResourceBuilder().build();
    gov.ca.cwds.data.persistence.cms.Referral toCreate =
        new gov.ca.cwds.data.persistence.cms.Referral("1234567ABC", referralDomain, "0XA");

    Referral request = MAPPER.readValue(fixture("fixtures/domain/legacy/Referral/valid/valid.json"),
        Referral.class);

    when(referralDao.create(any(gov.ca.cwds.data.persistence.cms.Referral.class)))
        .thenReturn(toCreate);

    Response response = referralService.create(request);
    assertThat(response.getClass(), is(PostedReferral.class));
  }

  @Override
  @Test
  public void testCreateReturnsCorrectEntity() throws Exception {
    Referral referralDomain = new ReferralResourceBuilder().build();
    gov.ca.cwds.data.persistence.cms.Referral toCreate =
        new gov.ca.cwds.data.persistence.cms.Referral("1234567ABC", referralDomain, "0XA");

    Referral request = new Referral(toCreate);
    when(referralDao.create(any(gov.ca.cwds.data.persistence.cms.Referral.class)))
        .thenReturn(toCreate);

    PostedReferral expected = new PostedReferral(toCreate);
    PostedReferral returned = referralService.create(request);
    assertThat(returned, is(expected));
  }

  @Override
  @Test
  public void testCreateEmptyIDError() throws Exception {
    try {
      Referral referralDomain = new ReferralResourceBuilder().build();
      gov.ca.cwds.data.persistence.cms.Referral toCreate =
          new gov.ca.cwds.data.persistence.cms.Referral("", referralDomain, "0XA");

      Referral request = new Referral(toCreate);
      when(referralDao.create(any(gov.ca.cwds.data.persistence.cms.Referral.class)))
          .thenReturn(toCreate);

      @SuppressWarnings("unused")
      PostedReferral returned = referralService.create(request);

    } catch (ServiceException e) {
      assertEquals("Referral ID cannot be empty", e.getMessage());
    }
  }

  @Override
  @Test
  public void testCreateNullIDError() throws Exception {
    try {
      Referral referralDomain = new ReferralResourceBuilder().build();
      gov.ca.cwds.data.persistence.cms.Referral toCreate =
          new gov.ca.cwds.data.persistence.cms.Referral(null, referralDomain, "0XA");

      when(referralDao.create(any(gov.ca.cwds.data.persistence.cms.Referral.class)))
          .thenReturn(toCreate);

      @SuppressWarnings("unused")
      PostedReferral expected = new PostedReferral(toCreate);

    } catch (ServiceException e) {
      assertEquals("Referral ID cannot be empty", e.getMessage());
    }
  }

  @Override
  @Test
  public void testCreateBlankIDError() throws Exception {
    try {
      Referral referralDomain = new ReferralResourceBuilder().build();
      gov.ca.cwds.data.persistence.cms.Referral toCreate =
          new gov.ca.cwds.data.persistence.cms.Referral("   ", referralDomain, "0XA");

      when(referralDao.create(any(gov.ca.cwds.data.persistence.cms.Referral.class)))
          .thenReturn(toCreate);

      @SuppressWarnings("unused")
      PostedReferral expected = new PostedReferral(toCreate);

    } catch (ServiceException e) {
      assertEquals("Referral ID cannot be empty", e.getMessage());
    }
  }

  @SuppressWarnings("javadoc")
  @Test
  public void testCreateReturnsCorrectReferralId() throws Exception {
    Referral referralDomain = new ReferralResourceBuilder().build();
    gov.ca.cwds.data.persistence.cms.Referral toCreate =
        new gov.ca.cwds.data.persistence.cms.Referral("1234567ABC", referralDomain, "0XA");

    Referral request = new Referral(toCreate);

    when(referralDao.create(any(gov.ca.cwds.data.persistence.cms.Referral.class)))
        .thenReturn(toCreate);

    PostedReferral returned = referralService.create(request);

    assertThat(returned.getId(), is("1234567ABC"));
  }

  /*
   * Test for checking the new Referral Id generated and lenght is 10
   */
  @SuppressWarnings("javadoc")
  @Test
  public void createReturnsGeneratedId() throws Exception {
    Referral referralDomain = new ReferralResourceBuilder().build();
    when(referralDao.create(any(gov.ca.cwds.data.persistence.cms.Referral.class)))
        .thenAnswer(new Answer<gov.ca.cwds.data.persistence.cms.Referral>() {

          @Override
          public gov.ca.cwds.data.persistence.cms.Referral answer(InvocationOnMock invocation)
              throws Throwable {
            gov.ca.cwds.data.persistence.cms.Referral report =
                (gov.ca.cwds.data.persistence.cms.Referral) invocation.getArguments()[0];
            return report;
          }
        });

    PostedReferral returned = referralService.create(referralDomain);
    assertEquals(returned.getId().length(), 10);
    PostedReferral newReturned = referralService.create(referralDomain);
    Assert.assertNotEquals(returned.getId(), newReturned.getId());
  }

  @Override
  public void testCreateThrowsNotImplementedException() throws Exception {

  }

  @SuppressWarnings("javadoc")
  @Test
  public void testCreateLACountyTriggerForReferralCreate() throws Exception {
    Referral referralDomain = new ReferralResourceBuilder().build();
    gov.ca.cwds.data.persistence.cms.Referral toCreate =
        new gov.ca.cwds.data.persistence.cms.Referral("1234567ABC", referralDomain, "BTr");

    Referral request = new Referral(toCreate);

    when(triggerTablesDao.getLaCountySpecificCode()).thenReturn("19");

    StaffPerson staffPerson = new StaffPerson("BTr", null, "External Interface",
        "external interface", "SCXCIN7", " ", "", BigDecimal.valueOf(9165672100L), 0, null, "    ",
        "N", "MIZN02k00E", "  ", "    ", "19", "N", "3XPCP92q38", null);

    when(staffpersonDao.find(any(String.class))).thenReturn(staffPerson);
    when(referralDao.create(any(gov.ca.cwds.data.persistence.cms.Referral.class)))
        .thenReturn(toCreate);

    when(laCountyTrigger.createCountyTrigger(any(gov.ca.cwds.data.persistence.cms.Referral.class)))
        .thenAnswer(new Answer<Boolean>() {

          @Override
          public Boolean answer(InvocationOnMock invocation) throws Throwable {
            isLaCountyTrigger = true;
            return true;
          }
        });

    referralService.create(request);
    assertThat(isLaCountyTrigger, is(true));

  }

  @SuppressWarnings("javadoc")
  @Test
  public void testCreateLACountyTriggerForReferralUpdate() throws Exception {
    Referral referralDomain = new ReferralResourceBuilder().build();

    gov.ca.cwds.data.persistence.cms.Referral toUpdate =
        new gov.ca.cwds.data.persistence.cms.Referral("1234567ABC", referralDomain, "BTr");

    Referral request = new Referral(toUpdate);

    when(triggerTablesDao.getLaCountySpecificCode()).thenReturn("19");

    StaffPerson staffPerson = new StaffPerson("BTr", null, "External Interface",
        "external interface", "SCXCIN7", " ", "", BigDecimal.valueOf(9165672100L), 0, null, "    ",
        "N", "MIZN02k00E", "  ", "    ", "19", "N", "3XPCP92q38", null);

    when(staffpersonDao.find(any(String.class))).thenReturn(staffPerson);
    when(referralDao.update(any(gov.ca.cwds.data.persistence.cms.Referral.class)))
        .thenReturn(toUpdate);

    when(laCountyTrigger.createCountyTrigger(any(gov.ca.cwds.data.persistence.cms.Referral.class)))
        .thenAnswer(new Answer<Boolean>() {

          @Override
          public Boolean answer(InvocationOnMock invocation) throws Throwable {
            isLaCountyTrigger = true;
            return true;
          }
        });

    referralService.update("1234567ABC", request);
    assertThat(isLaCountyTrigger, is(true));

  }

}
