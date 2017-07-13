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
import gov.ca.cwds.rest.services.junit.template.ServiceTestTemplate;

/**
 * @author CWDS API Team
 *
 */
public class AssignmentServiceTest implements ServiceTestTemplate {

  private AssignmentService assignmentService;
  private AssignmentDao assignmentDao;;
  private StaffPersonIdRetriever staffPersonIdRetriever;

  @SuppressWarnings("javadoc")
  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Override
  @Before
  public void setup() throws Exception {
    assignmentDao = mock(AssignmentDao.class);
    staffPersonIdRetriever = mock(StaffPersonIdRetriever.class);
    assignmentService = new AssignmentService(assignmentDao, staffPersonIdRetriever);
  }

  // find test
  @Override
  @Test(expected = AssertionError.class)
  public void testFindThrowsAssertionError() {
    // thrown.expect(AssertionError.class);
    try {
      assignmentService.find(1);
    } catch (AssertionError e) {
      assertEquals("Expeceted AssertionError", e.getMessage());
    }
  }

  @Override
  @Test
  public void testFindReturnsCorrectEntity() throws Exception {
    String id = "SlCAr46088";
    Assignment expected = new AssignmentResourceBuilder().createAssignment();

    gov.ca.cwds.data.persistence.cms.Assignment assignment =
        new gov.ca.cwds.data.persistence.cms.Assignment(id, expected, "0XA");

    when(assignmentDao.find(id)).thenReturn(assignment);
    Assignment found = assignmentService.find(id);
    assertThat(found, is(expected));
  }

  @Override
  @Test
  public void testFindReturnsNullWhenNotFound() throws Exception {
    Response found = assignmentService.find("ABC1234567");
    assertThat(found, is(nullValue()));
  }

  // delete test
  @Override
  @Test(expected = AssertionError.class)
  public void testDeleteThrowsAssertionError() throws Exception {
    try {
      assignmentService.delete(123);
    } catch (AssertionError e) {
      assertEquals("Expected AssertionError", e.getMessage());
    }
  }

  @Override
  @Test
  public void testDeleteDelegatesToCrudsService() {
    assignmentService.delete("ABC2345678");
    verify(assignmentDao, times(1)).delete("ABC2345678");
  }

  @Override
  @Test
  public void testDeleteReturnsNullWhenNotFound() throws Exception {
    Response found = assignmentService.delete("ABC1234567");
    assertThat(found, is(nullValue()));
  }

  @Override
  public void testDeleteThrowsNotImplementedException() throws Exception {

  }

  @Override
  public void testDeleteReturnsClass() throws Exception {

  }

  // update test
  @Override
  @Test(expected = AssertionError.class)
  public void testUpdateThrowsAssertionError() throws Exception {
    try {
      assignmentService.update("ABC1234567", null);
    } catch (AssertionError e) {
      assertEquals("Expected AssertionError", e.getMessage());
    }
  }

  @Override
  @Test
  public void testUpdateReturnsCorrectEntity() throws Exception {
    String id = "SlCAr46088";
    Assignment expected =
        new AssignmentResourceBuilder().setCountySpecificCode("45").createAssignment();

    gov.ca.cwds.data.persistence.cms.Assignment assignment =
        new gov.ca.cwds.data.persistence.cms.Assignment(id, expected, "ABC");

    when(assignmentDao.find("ABC1234567")).thenReturn(assignment);
    when(assignmentDao.update(any())).thenReturn(assignment);

    Object retval = assignmentService.update("ABC1234567", expected);
    assertThat(retval.getClass(), is(Assignment.class));
  }

  @SuppressWarnings("javadoc")
  @Test
  public void testUpdateThrowsExceptionWhenNotFound() throws Exception {
    try {
      Assignment assignmentRequest =
          new AssignmentResourceBuilder().setCountySpecificCode("45").createAssignment();

      when(assignmentDao.update(any())).thenThrow(EntityNotFoundException.class);

      assignmentService.update("ZZZZZZZZZZ", assignmentRequest);
    } catch (Exception e) {
      assertEquals(e.getClass(), ServiceException.class);
    }
  }

  @Override
  public void testUpdateReturnsDomain() throws Exception {

  }

  @Override
  public void testUpdateThrowsServiceException() throws Exception {

  }

  @Override
  public void testUpdateThrowsNotImplementedException() throws Exception {

  }

  // create test
  @Override
  @Test
  public void testCreateReturnsPostedClass() throws Exception {
    String id = "5rVkB8c088";
    Assignment domainAssignment = new AssignmentResourceBuilder().createAssignment();

    gov.ca.cwds.data.persistence.cms.Assignment toCreate =
        new gov.ca.cwds.data.persistence.cms.Assignment(id, domainAssignment, "ABC");

    PostedAssignment request = new PostedAssignment(toCreate);
    when(assignmentDao.create(any(gov.ca.cwds.data.persistence.cms.Assignment.class)))
        .thenReturn(toCreate);

    Response response = assignmentService.create(request);
    assertThat(response.getClass(), is(PostedAssignment.class));
  }

  @SuppressWarnings("javadoc")
  @Test
  public void testCreateReturnsNonNull() throws Exception {
    String id = "5rVkB8c088";
    Assignment domainAssignment = new AssignmentResourceBuilder().createAssignment();

    gov.ca.cwds.data.persistence.cms.Assignment toCreate =
        new gov.ca.cwds.data.persistence.cms.Assignment(id, domainAssignment, "ABC");

    PostedAssignment request = new PostedAssignment(toCreate);
    when(assignmentDao.create(any(gov.ca.cwds.data.persistence.cms.Assignment.class)))
        .thenReturn(toCreate);

    PostedAssignment postedAssignment = assignmentService.create(request);
    assertThat(postedAssignment, is(notNullValue()));
  }

  @Override
  @Test
  public void testCreateReturnsCorrectEntity() throws Exception {
    String id = "5rVkB8c088";
    Assignment domainAssignment = new AssignmentResourceBuilder().createAssignment();

    gov.ca.cwds.data.persistence.cms.Assignment toCreate =
        new gov.ca.cwds.data.persistence.cms.Assignment(id, domainAssignment, "ABC");

    PostedAssignment request = new PostedAssignment(toCreate);
    when(assignmentDao.create(any(gov.ca.cwds.data.persistence.cms.Assignment.class)))
        .thenReturn(toCreate);

    PostedAssignment expected = new PostedAssignment(toCreate);
    PostedAssignment returned = assignmentService.create(request);
    assertThat(returned, is(expected));
  }

  @Override
  @Test
  public void testCreateNullIDError() throws Exception {
    try {
      Assignment domainAssignment = new AssignmentResourceBuilder().createAssignment();

      gov.ca.cwds.data.persistence.cms.Assignment toCreate =
          new gov.ca.cwds.data.persistence.cms.Assignment(null, domainAssignment, "ABC");

      when(assignmentDao.create(any(gov.ca.cwds.data.persistence.cms.Assignment.class)))
          .thenReturn(toCreate);

      PostedAssignment expected = new PostedAssignment(toCreate);
    } catch (ServiceException e) {
      assertEquals("Assignment ID cannot be blank", e.getMessage());
    }

  }

  @Override
  @Test
  public void testCreateBlankIDError() throws Exception {
    try {
      Assignment domainAssignment = new AssignmentResourceBuilder().createAssignment();
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
  public void createReturnsGeneratedId() throws Exception {
    Assignment domainAssignment = new AssignmentResourceBuilder().createAssignment();

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

  @Override
  public void testCreateThrowsAssertionError() throws Exception {

  }

  @Override
  public void testCreateEmptyIDError() throws Exception {

  }

  @Override
  public void testCreateThrowsNotImplementedException() throws Exception {

  }

  @Override
  public void testFindThrowsNotImplementedException() throws Exception {

  }

}
