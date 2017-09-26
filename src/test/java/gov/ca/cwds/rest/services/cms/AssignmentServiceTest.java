package gov.ca.cwds.rest.services.cms;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Date;

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

import gov.ca.cwds.data.cms.AssignmentDao;
import gov.ca.cwds.data.cms.CaseLoadDao;
import gov.ca.cwds.data.cms.CountyOwnershipDao;
import gov.ca.cwds.data.cms.ReferralClientDao;
import gov.ca.cwds.data.cms.ReferralDao;
import gov.ca.cwds.data.cms.StaffPersonDao;
import gov.ca.cwds.data.persistence.cms.CaseLoad;
import gov.ca.cwds.data.persistence.cms.StaffPerson;
import gov.ca.cwds.data.rules.TriggerTablesDao;
import gov.ca.cwds.fixture.AssignmentResourceBuilder;
import gov.ca.cwds.fixture.CaseLoadEntityBuilder;
import gov.ca.cwds.fixture.ReferralResourceBuilder;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.ScreeningToReferral;
import gov.ca.cwds.rest.api.domain.cms.Assignment;
import gov.ca.cwds.rest.api.domain.cms.PostedAssignment;
import gov.ca.cwds.rest.api.domain.cms.Referral;
import gov.ca.cwds.rest.business.rules.ExternalInterfaceTables;
import gov.ca.cwds.rest.business.rules.NonLACountyTriggers;
import gov.ca.cwds.rest.messages.MessageBuilder;
import gov.ca.cwds.rest.services.ServiceException;
import gov.ca.cwds.rest.services.referentialintegrity.RIAssignment;

/**
 * @author CWDS API Team
 *
 */
@SuppressWarnings("javadoc")
public class AssignmentServiceTest {

  private AssignmentService assignmentService;
  private AssignmentDao assignmentDao;
  private NonLACountyTriggers nonLACountyTriggers;
  private StaffPersonDao staffpersonDao;
  private TriggerTablesDao triggerTablesDao;
  private StaffPersonIdRetriever staffPersonIdRetriever;
  private Validator validator;
  private ExternalInterfaceTables externalInterfaceTables;
  private CountyOwnershipDao countyOwnershipDao;
  private ReferralDao referralDao;
  private ReferralClientDao referralClientDao;
  private RIAssignment riAssignment;
  private MessageBuilder messageBuilder;
  private ScreeningToReferral screeningToReferral;
  private CaseLoadDao caseLoadDao;

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Before
  public void setup() throws Exception {
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    assignmentDao = mock(AssignmentDao.class);
    staffPersonIdRetriever = mock(StaffPersonIdRetriever.class);
    nonLACountyTriggers = mock(NonLACountyTriggers.class);
    staffpersonDao = mock(StaffPersonDao.class);
    triggerTablesDao = mock(TriggerTablesDao.class);
    externalInterfaceTables = mock(ExternalInterfaceTables.class);
    countyOwnershipDao = mock(CountyOwnershipDao.class);
    referralDao = mock(ReferralDao.class);
    referralClientDao = mock(ReferralClientDao.class);
    riAssignment = mock(RIAssignment.class);
    messageBuilder = mock(MessageBuilder.class);
    screeningToReferral = mock(ScreeningToReferral.class);
    caseLoadDao = mock(CaseLoadDao.class);
    nonLACountyTriggers =
        new NonLACountyTriggers(countyOwnershipDao, referralDao, referralClientDao);
    assignmentService =
        new AssignmentService(assignmentDao, nonLACountyTriggers, staffpersonDao, triggerTablesDao,
            staffPersonIdRetriever, validator, externalInterfaceTables, riAssignment, caseLoadDao);

  }

  // find test
  @Test
  public void assignmentServiceFindReturnsCorrectEntity() throws Exception {
    String id = "SlCAr46088";
    Assignment expected = new AssignmentResourceBuilder().buildAssignment();

    gov.ca.cwds.data.persistence.cms.Assignment assignment =
        new gov.ca.cwds.data.persistence.cms.Assignment(id, expected, "0XA");

    when(assignmentDao.find(id)).thenReturn(assignment);
    Assignment found = assignmentService.find(id);
    assertThat(found, is(expected));
  }


  @Test
  public void assignmentServiceFindReturnsNullWhenNotFound() throws Exception {
    Response found = assignmentService.find("ABC1234567");
    assertThat(found, is(nullValue()));
  }

  // delete test
  @Test
  public void assignmentServiceDeleteDelegatesToCrudsService() {
    assignmentService.delete("ABC2345678");
    verify(assignmentDao, times(1)).delete("ABC2345678");
  }

  @Test
  public void assignmentServiceDeleteReturnsNullWhenNotFound() throws Exception {
    Response found = assignmentService.delete("ABC1234567");
    assertThat(found, is(nullValue()));
  }

  @Test
  public void assignmentServiceDeleteReturnsNotNull() throws Exception {
    String id = "AabekZX00F";
    Assignment expected = new AssignmentResourceBuilder().buildAssignment();
    gov.ca.cwds.data.persistence.cms.Assignment assignment =
        new gov.ca.cwds.data.persistence.cms.Assignment(id, expected, "0XA");

    when(assignmentDao.delete(id)).thenReturn(assignment);
    Assignment found = assignmentService.delete(id);
    assertThat(found, is(expected));
  }

  // update test
  @Test
  public void assignmentServiceUpdateReturnsCorrectEntity() throws Exception {
    String id = "SlCAr46088";
    Assignment expected =
        new AssignmentResourceBuilder().setCountySpecificCode("45").buildAssignment();

    gov.ca.cwds.data.persistence.cms.Assignment assignment =
        new gov.ca.cwds.data.persistence.cms.Assignment(id, expected, "ABC");

    when(assignmentDao.find("ABC1234567")).thenReturn(assignment);
    when(assignmentDao.update(any())).thenReturn(assignment);

    Object retval = assignmentService.update("ABC1234567", expected);
    assertThat(retval.getClass(), is(Assignment.class));
  }

  @Test
  public void assignmentServiceUpdateThrowsExceptionWhenNotFound() throws Exception {
    try {
      Assignment assignmentRequest =
          new AssignmentResourceBuilder().setCountySpecificCode("45").buildAssignment();

      when(assignmentDao.update(any())).thenThrow(EntityNotFoundException.class);

      assignmentService.update("ZZZZZZZZZZ", assignmentRequest);
    } catch (Exception e) {
      assertEquals(e.getClass(), ServiceException.class);
    }
  }

  // create test
  @Test
  public void assignmentServiceCreateReturnsPostedClass() throws Exception {
    String id = "5rVkB8c088";
    Assignment domainAssignment = new AssignmentResourceBuilder().buildAssignment();

    gov.ca.cwds.data.persistence.cms.Assignment toCreate =
        new gov.ca.cwds.data.persistence.cms.Assignment(id, domainAssignment, "ABC");

    PostedAssignment request = new PostedAssignment(toCreate);
    when(assignmentDao.create(any(gov.ca.cwds.data.persistence.cms.Assignment.class)))
        .thenReturn(toCreate);

    Response response = assignmentService.create(request);
    assertThat(response.getClass(), is(PostedAssignment.class));
  }

  @Test
  public void assignmentServiceCreateReturnsNonNull() throws Exception {
    String id = "5rVkB8c088";
    Assignment domainAssignment = new AssignmentResourceBuilder().buildAssignment();

    gov.ca.cwds.data.persistence.cms.Assignment toCreate =
        new gov.ca.cwds.data.persistence.cms.Assignment(id, domainAssignment, "ABC");

    PostedAssignment request = new PostedAssignment(toCreate);
    when(assignmentDao.create(any(gov.ca.cwds.data.persistence.cms.Assignment.class)))
        .thenReturn(toCreate);

    PostedAssignment postedAssignment = assignmentService.create(request);
    assertThat(postedAssignment, is(notNullValue()));
  }

  @Test
  public void assignmentServiceCreateReturnsCorrectEntity() throws Exception {
    String id = "5rVkB8c088";
    Assignment domainAssignment = new AssignmentResourceBuilder().buildAssignment();

    gov.ca.cwds.data.persistence.cms.Assignment toCreate =
        new gov.ca.cwds.data.persistence.cms.Assignment(id, domainAssignment, "ABC");

    PostedAssignment request = new PostedAssignment(toCreate);
    when(assignmentDao.create(any(gov.ca.cwds.data.persistence.cms.Assignment.class)))
        .thenReturn(toCreate);

    PostedAssignment expected = new PostedAssignment(toCreate);
    PostedAssignment returned = assignmentService.create(request);
    assertThat(returned, is(expected));
  }

  @Test
  public void assignmentServiceCreateThrowsEntityExistsException() throws Exception {
    try {
      Assignment assignmentRequest = new AssignmentResourceBuilder().buildAssignment();

      when(assignmentDao.create(any())).thenThrow(EntityExistsException.class);

      assignmentService.create(assignmentRequest);
    } catch (Exception e) {
      assertEquals(e.getClass(), ServiceException.class);
    }
  }

  @Test
  public void assignmentServiceCreateNullIDError() throws Exception {
    try {
      Assignment domainAssignment = new AssignmentResourceBuilder().buildAssignment();

      gov.ca.cwds.data.persistence.cms.Assignment toCreate =
          new gov.ca.cwds.data.persistence.cms.Assignment(null, domainAssignment, "ABC");

      when(assignmentDao.create(any(gov.ca.cwds.data.persistence.cms.Assignment.class)))
          .thenReturn(toCreate);

      PostedAssignment expected = new PostedAssignment(toCreate);
    } catch (ServiceException e) {
      assertEquals("Assignment ID cannot be blank", e.getMessage());
    }

  }

  @Test
  public void assignmentServiceCreateBlankIDError() throws Exception {
    try {
      Assignment domainAssignment = new AssignmentResourceBuilder().buildAssignment();
      gov.ca.cwds.data.persistence.cms.Assignment toCreate =
          new gov.ca.cwds.data.persistence.cms.Assignment("   ", domainAssignment, "ABC");

      when(assignmentDao.create(any(gov.ca.cwds.data.persistence.cms.Assignment.class)))
          .thenReturn(toCreate);

      PostedAssignment expected = new PostedAssignment(toCreate);
    } catch (ServiceException e) {
      assertEquals("Assignment ID cannot be blank", e.getMessage());
    }
  }

  /**
   * Test for checking the new Assignment Id generated and length is 10
   * 
   * @throws Exception - exception
   */
  @Test
  public void assignmentServiceCreateReturnsGeneratedId() throws Exception {
    Assignment domainAssignment = new AssignmentResourceBuilder().buildAssignment();

    when(assignmentDao.create(any(gov.ca.cwds.data.persistence.cms.Assignment.class)))
        .thenAnswer(new Answer<gov.ca.cwds.data.persistence.cms.Assignment>() {

          @Override
          public gov.ca.cwds.data.persistence.cms.Assignment answer(InvocationOnMock invocation)
              throws Throwable {
            gov.ca.cwds.data.persistence.cms.Assignment report =
                (gov.ca.cwds.data.persistence.cms.Assignment) invocation.getArguments()[0];
            return report;
          }
        });

    PostedAssignment returned = assignmentService.create(domainAssignment);
    assertEquals(returned.getId().length(), 10);
    PostedAssignment newReturned = assignmentService.create(domainAssignment);
    Assert.assertNotEquals(returned.getId(), newReturned.getId());
  }

  @Test
  public void testCreateNonLACountyTriggerForAssignment() throws Exception {
    Assignment assignmentDomain = new AssignmentResourceBuilder().buildAssignment();
    gov.ca.cwds.data.persistence.cms.Assignment toCreate =
        new gov.ca.cwds.data.persistence.cms.Assignment("ABC1234567", assignmentDomain, "q1p");

    Referral domainReferral = new ReferralResourceBuilder().setCountySpecificCode("00").build();
    gov.ca.cwds.data.persistence.cms.Referral referral =
        new gov.ca.cwds.data.persistence.cms.Referral("ABC1234568", domainReferral, "0X5");

    Assignment request = new Assignment(toCreate);

    when(triggerTablesDao.getLaCountySpecificCode()).thenReturn("20");

    StaffPerson staffPerson = new StaffPerson("BTr", null, "External Interface",
        "external interface", "SCXCIN7", " ", "", BigDecimal.valueOf(9165672100L), 0, null, "    ",
        "N", "MIZN02k00E", "  ", "    ", "19", "N", "3XPCP92q38", null);

    when(staffpersonDao.find(any(String.class))).thenReturn(staffPerson);
    when(referralDao.find(any(gov.ca.cwds.data.persistence.cms.Referral.class)))
        .thenReturn(referral);
    when(assignmentDao.create(any(gov.ca.cwds.data.persistence.cms.Assignment.class)))
        .thenReturn(toCreate);
    assignmentService.create(request);

    verify(countyOwnershipDao, times(1)).create(any());
  }

  @Test
  public void testNotCreateNonLACountyTriggerForAssignment() throws Exception {
    Assignment assignmentDomain = new AssignmentResourceBuilder().buildAssignment();
    gov.ca.cwds.data.persistence.cms.Assignment toCreate =
        new gov.ca.cwds.data.persistence.cms.Assignment("ABC1234567", assignmentDomain, "BTr");

    Referral domainReferral = new ReferralResourceBuilder().setCountySpecificCode("00").build();
    gov.ca.cwds.data.persistence.cms.Referral referral =
        new gov.ca.cwds.data.persistence.cms.Referral("ABC1234568", domainReferral, "0X5");

    Assignment request = new Assignment(toCreate);

    when(triggerTablesDao.getLaCountySpecificCode()).thenReturn("19");

    StaffPerson staffPerson = new StaffPerson("BTr", null, "External Interface",
        "external interface", "SCXCIN7", " ", "", BigDecimal.valueOf(9165672100L), 0, null, "    ",
        "N", "MIZN02k00E", "  ", "    ", "19", "N", "3XPCP92q38", null);

    when(staffpersonDao.find(any(String.class))).thenReturn(staffPerson);
    when(referralDao.find(any(gov.ca.cwds.data.persistence.cms.Referral.class)))
        .thenReturn(referral);
    when(assignmentDao.create(any(gov.ca.cwds.data.persistence.cms.Assignment.class)))
        .thenReturn(toCreate);
    assignmentService.create(request);

    verify(countyOwnershipDao, times(0)).create(any());
  }

  @Test
  public void testForCreateAssignment() throws Exception {

    Assignment assignmentDomain = new AssignmentResourceBuilder().buildAssignment();
    gov.ca.cwds.data.persistence.cms.Assignment toCreate =
        new gov.ca.cwds.data.persistence.cms.Assignment("ABC1234567", assignmentDomain, "q1p");

    when(assignmentDao.create(any(gov.ca.cwds.data.persistence.cms.Assignment.class)))
        .thenReturn(toCreate);
    CaseLoad caseload = new CaseLoadEntityBuilder().build();
    when(caseLoadDao.find(any())).thenReturn(caseload);

    assignmentService.createDefaultAssignmentForNewReferral(screeningToReferral, "ABC1234567",
        new Date(), messageBuilder);
    verify(assignmentDao, times(1)).create(any());
  }

}
