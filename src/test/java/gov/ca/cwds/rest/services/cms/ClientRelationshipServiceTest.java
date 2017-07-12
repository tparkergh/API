package gov.ca.cwds.rest.services.cms;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gov.ca.cwds.data.cms.ClientRelationshipDao;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.cms.ClientRelationship;
import gov.ca.cwds.rest.api.domain.cms.PostedClientRelationship;
import gov.ca.cwds.rest.services.ServiceException;

import org.apache.commons.lang3.NotImplementedException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * @author CWDS API Team
 *
 */
public class ClientRelationshipServiceTest {
  private ClientRelationshipService clientRelationshipService;
  private ClientRelationshipDao clientRelationshipDao;
  private StaffPersonIdRetriever staffPersonIdRetriever;

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @SuppressWarnings("javadoc")
  @Before
  public void setup() throws Exception {
    clientRelationshipDao = mock(ClientRelationshipDao.class);
    staffPersonIdRetriever = mock(StaffPersonIdRetriever.class);

    clientRelationshipService =
        new ClientRelationshipService(clientRelationshipDao, staffPersonIdRetriever);

  }

  // find test
  @SuppressWarnings("javadoc")
  @Test
  public void findThrowsAssertionError() {
    thrown.expect(AssertionError.class);
    try {
      clientRelationshipService.find(new Long(1));
    } catch (AssertionError e) {
      assertEquals("Expected AssertionError", e.getMessage());
    }
  }

  @SuppressWarnings("javadoc")
  @Test
  public void findReturnsCorrectClientRelationshipWhenFound() throws Exception {
    String id = "ABC";
    PostedClientRelationship expected = validClientRelationshipDomainObject();
    gov.ca.cwds.data.persistence.cms.ClientRelationship clientRelationship =
        new gov.ca.cwds.data.persistence.cms.ClientRelationship(id, expected, "0X5");

    when(clientRelationshipDao.find(id)).thenReturn(clientRelationship);

    ClientRelationship found = clientRelationshipService.find(id);

    assertThat(found, is(expected));
  }

  @SuppressWarnings("javadoc")
  @Test
  public void findReturnsNullWhenNotFound() throws Exception {
    Response found = clientRelationshipService.find("0XA");
    assertThat(found, is(nullValue()));
  }

  // Delete Tests
  @SuppressWarnings("javadoc")
  @Test
  public void deleteThrowsNotImplementedException() throws Exception {
    thrown.expect(NotImplementedException.class);
    clientRelationshipService.delete("string");
  }

  @SuppressWarnings("javadoc")
  @Test
  public void deleteThrowsAssertionError() throws Exception {
    thrown.expect(AssertionError.class);
    try {
      clientRelationshipService.delete(new Long(1));
    } catch (AssertionError e) {
      assertEquals("Expected AssertionError", e.getMessage());
    }
  }

  // create test
  @SuppressWarnings("javadoc")
  @Test
  public void createThrowsAssertionError() throws Exception {
    thrown.expect(AssertionError.class);
    try {
      clientRelationshipService.create(null);
      Assert.fail("Expected AssertionError");
    } catch (AssertionError e) {
      assertEquals("Expected AssertionError", e.getMessage());
    }
  }

  @SuppressWarnings("javadoc")
  @Test
  public void createReturnsPostedClientRelationshipClass() throws Exception {
    String id = "ABC";
    ClientRelationship clientRelationshipDomain = validClientRelationshipDomainObject();
    gov.ca.cwds.data.persistence.cms.ClientRelationship toCreate =
        new gov.ca.cwds.data.persistence.cms.ClientRelationship(id, clientRelationshipDomain,
            "2017-01-07");

    ClientRelationship request = new ClientRelationship(toCreate);

    when(
        clientRelationshipDao
            .create(any(gov.ca.cwds.data.persistence.cms.ClientRelationship.class))).thenReturn(
        toCreate);

    Response response = clientRelationshipService.create(request);

    assertThat(response.getClass(), is(PostedClientRelationship.class));
  }

  @SuppressWarnings("javadoc")
  @Test
  public void createReturnsNonNull() throws Exception {
    String id = "ABC";
    ClientRelationship clientRelationshipDomain = validClientRelationshipDomainObject();
    gov.ca.cwds.data.persistence.cms.ClientRelationship toCreate =
        new gov.ca.cwds.data.persistence.cms.ClientRelationship(id, clientRelationshipDomain,
            "2017-01-07");

    ClientRelationship request = new ClientRelationship(toCreate);

    when(
        clientRelationshipDao
            .create(any(gov.ca.cwds.data.persistence.cms.ClientRelationship.class))).thenReturn(
        toCreate);

    PostedClientRelationship postedClientRelationship = clientRelationshipService.create(request);

    assertThat(postedClientRelationship, is(notNullValue()));
  }

  @SuppressWarnings("javadoc")
  @Test
  public void createReturnsCorrectPostedPerson() throws Exception {
    String id = "ABC";
    ClientRelationship clientRelationshipDomain = validClientRelationshipDomainObject();
    gov.ca.cwds.data.persistence.cms.ClientRelationship toCreate =
        new gov.ca.cwds.data.persistence.cms.ClientRelationship(id, clientRelationshipDomain,
            "2017-01-07");

    ClientRelationship request = new ClientRelationship(toCreate);

    when(
        clientRelationshipDao
            .create(any(gov.ca.cwds.data.persistence.cms.ClientRelationship.class))).thenReturn(
        toCreate);

    PostedClientRelationship expected = new PostedClientRelationship(toCreate);

    PostedClientRelationship returned = clientRelationshipService.create(request);

    assertThat(returned, is(expected));
  }

  @SuppressWarnings("javadoc")
  @Test
  public void failsWhenPostedClientRelationshipIdBlank() throws Exception {
    try {
      ClientRelationship clientRelationshipDomain = validClientRelationshipDomainObject();
      gov.ca.cwds.data.persistence.cms.ClientRelationship toCreate =
          new gov.ca.cwds.data.persistence.cms.ClientRelationship("   ", clientRelationshipDomain,
              "2017-01-07");

      when(
          clientRelationshipDao
              .create(any(gov.ca.cwds.data.persistence.cms.ClientRelationship.class))).thenReturn(
          toCreate);

      PostedClientRelationship expected = new PostedClientRelationship(toCreate);

      Assert.fail("Expected AssertionError not thrown");
    } catch (ServiceException e) {
      assertEquals("ClientRelationship ID cannot be empty", e.getMessage());
    }
  }

  @SuppressWarnings("javadoc")
  @Test
  public void failsWhenPostedClientRelationshipIdNull() throws Exception {
    try {
      ClientRelationship clientRelationshipDomain = validClientRelationshipDomainObject();
      gov.ca.cwds.data.persistence.cms.ClientRelationship toCreate =
          new gov.ca.cwds.data.persistence.cms.ClientRelationship(null, clientRelationshipDomain,
              "2017-01-07");

      when(
          clientRelationshipDao
              .create(any(gov.ca.cwds.data.persistence.cms.ClientRelationship.class))).thenReturn(
          toCreate);

      PostedClientRelationship expected = new PostedClientRelationship(toCreate);

      Assert.fail("Expected AssertionError not thrown");
    } catch (ServiceException e) {
      assertEquals("ClientRelationship ID cannot be empty", e.getMessage());
    }
  }

  @SuppressWarnings("javadoc")
  @Test
  public void failsWhenPostedClientRelationshipIdEmpty() throws Exception {
    try {
      ClientRelationship clientRelationshipDomain = validClientRelationshipDomainObject();
      gov.ca.cwds.data.persistence.cms.ClientRelationship toCreate =
          new gov.ca.cwds.data.persistence.cms.ClientRelationship("", clientRelationshipDomain,
              "2017-01-07");

      when(
          clientRelationshipDao
              .create(any(gov.ca.cwds.data.persistence.cms.ClientRelationship.class))).thenReturn(
          toCreate);

      PostedClientRelationship expected = new PostedClientRelationship(toCreate);

      Assert.fail("Expected AssertionError not thrown");
    } catch (ServiceException e) {
      assertEquals("ClientRelationship ID cannot be empty", e.getMessage());
    }
  }

  public PostedClientRelationship validClientRelationshipDomainObject() {
    ClientRelationship clientRelationship =
        new ClientRelationship("N", (short) 172, "2017-01-07", "SECCLIENT", "PRICLIENT", "Y",
            "2017-01-07");
    return new PostedClientRelationship(clientRelationship, "ABC");
  }
}
