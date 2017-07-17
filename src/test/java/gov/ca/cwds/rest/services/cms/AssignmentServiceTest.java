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

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import gov.ca.cwds.data.cms.AssignmentDao;
import gov.ca.cwds.fixture.AssignmentResourceBuilder;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.cms.Assignment;
import gov.ca.cwds.rest.api.domain.cms.PostedAssignment;
import gov.ca.cwds.rest.services.ServiceException;

/**
 * @author CWDS API Team
 *
 */
@SuppressWarnings("javadoc")
public class AssignmentServiceTest {

  private AssignmentService assignmentService;
  private AssignmentDao assignmentDao;;
  private StaffPersonIdRetriever staffPersonIdRetriever;

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Before
  public void setup() throws Exception {
    assignmentDao = mock(AssignmentDao.class);
    staffPersonIdRetriever = mock(StaffPersonIdRetriever.class);
    assignmentService = new AssignmentService(assignmentDao, staffPersonIdRetriever);
  }

  // find test
  @Test(expected = AssertionError.class)
  public void assignmentServiceFindThrowsAssertionError() {
    try {
      assignmentService.find(1);
    } catch (AssertionError e) {
      assertEquals("Expeceted AssertionError", e.getMessage());
    }
  }


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
  @Test(expected = AssertionError.class)
  public void assignmentServiceDeleteThrowsAssertionError() throws Exception {
    try {
      assignmentService.delete(123);
    } catch (AssertionError e) {
      assertEquals("Expected AssertionError", e.getMessage());
    }
  }


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

  // update test
  @Test(expected = AssertionError.class)
  public void assignmentServiceUpdateThrowsAssertionError() throws Exception {
    try {
      assignmentService.update("ABC1234567", null);
    } catch (AssertionError e) {
      assertEquals("Expected AssertionError", e.getMessage());
    }
  }


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

}
