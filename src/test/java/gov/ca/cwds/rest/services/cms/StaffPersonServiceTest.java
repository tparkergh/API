package gov.ca.cwds.rest.services.cms;

import static io.dropwizard.testing.FixtureHelpers.fixture;
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

import com.fasterxml.jackson.databind.ObjectMapper;

import gov.ca.cwds.data.cms.StaffPersonDao;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.cms.PostedStaffPerson;
import gov.ca.cwds.rest.api.domain.cms.StaffPerson;
import gov.ca.cwds.rest.services.ServiceException;
import io.dropwizard.jackson.Jackson;

/**
 * @author CWDS API Team
 *
 */
public class StaffPersonServiceTest {
  private static final ObjectMapper MAPPER = Jackson.newObjectMapper();
  private StaffPersonService staffPersonService;
  private StaffPersonDao staffPersonDao;

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @SuppressWarnings("javadoc")
  @Before
  public void setup() throws Exception {
    staffPersonDao = mock(StaffPersonDao.class);

    staffPersonService = new StaffPersonService(staffPersonDao);

  }

  // find test
  @SuppressWarnings("javadoc")
  @Test
  public void findThrowsAssertionError() {
    thrown.expect(AssertionError.class);
    try {
      staffPersonService.find(1);
    } catch (AssertionError e) {
      assertEquals("Expected AssertionError", e.getMessage());
    }
  }

  @SuppressWarnings("javadoc")
  @Test
  public void findReturnsCorrectStaffPersonWhenFound() throws Exception {
    String id = "ABC";
    StaffPerson expected = MAPPER.readValue(
        fixture("fixtures/domain/legacy/StaffPerson/valid/valid.json"), StaffPerson.class);

    gov.ca.cwds.data.persistence.cms.StaffPerson staffPerson =
        new gov.ca.cwds.data.persistence.cms.StaffPerson(id, expected, "2017-01-07");

    when(staffPersonDao.find(id)).thenReturn(staffPerson);

    StaffPerson found = staffPersonService.find(id);

    assertThat(found, is(expected));
  }

  @SuppressWarnings("javadoc")
  @Test
  public void findReturnsNullWhenNotFound() throws Exception {
    Response found = staffPersonService.find("0XA");
    assertThat(found, is(nullValue()));
  }

  @SuppressWarnings("javadoc")
  @Test
  public void deleteThrowsAssersionError() throws Exception {
    thrown.expect(AssertionError.class);
    try {
      staffPersonService.delete(1);
    } catch (AssertionError e) {
      assertEquals("Expected AssertionError", e.getMessage());
    }
  }

  @SuppressWarnings("javadoc")
  @Test
  public void deleteReturnsNullWhenNotFound() throws Exception {
    Response found = staffPersonService.delete("ABC");
    assertThat(found, is(nullValue()));
  }

  @SuppressWarnings("javadoc")
  @Test
  public void deleteDelegatesToCrudsService() {
    staffPersonService.delete("ABC");
    verify(staffPersonDao, times(1)).delete("ABC");
  }

  // update test
  @SuppressWarnings("javadoc")
  @Test
  public void updateThrowsAssertionError() throws Exception {
    thrown.expect(AssertionError.class);
    try {
      staffPersonService.update("xxx", null);
      Assert.fail("Expected AssertionError");
    } catch (AssertionError e) {
      assertEquals("Expected AssertionError", e.getMessage());
    }
  }

  @SuppressWarnings("javadoc")
  @Test
  public void updateReturnsStaffPersonResponseOnSuccess() throws Exception {
    String id = "ABC";
    StaffPerson expected = MAPPER.readValue(
        fixture("fixtures/domain/legacy/StaffPerson/valid/valid.json"), StaffPerson.class);

    gov.ca.cwds.data.persistence.cms.StaffPerson staffPerson =
        new gov.ca.cwds.data.persistence.cms.StaffPerson(id, expected, "2017-01-07");

    when(staffPersonDao.find(id)).thenReturn(staffPerson);
    when(staffPersonDao.update(any())).thenReturn(staffPerson);
    Object retval = staffPersonService.update(id, expected);
    assertThat(retval.getClass(), is(StaffPerson.class));

  }

  @SuppressWarnings("javadoc")
  @Test
  public void updateReturnsCorrectStaffPersonOnSuccess() throws Exception {
    String id = "ABC";
    StaffPerson staffPersonRequest = MAPPER.readValue(
        fixture("fixtures/domain/legacy/StaffPerson/valid/valid.json"), StaffPerson.class);

    gov.ca.cwds.data.persistence.cms.StaffPerson staffPerson =
        new gov.ca.cwds.data.persistence.cms.StaffPerson(id, staffPersonRequest, "2017-01-07");

    when(staffPersonDao.find(id)).thenReturn(staffPerson);
    when(staffPersonDao.update(any())).thenReturn(staffPerson);

    StaffPerson expected = new StaffPerson(staffPerson);
    StaffPerson updated = staffPersonService.update(id, expected);

    assertThat(updated, is(expected));

  }

  @SuppressWarnings("javadoc")
  @Test
  public void updateThrowsExceptionWhenStaffPersonNotFound() throws Exception {
    try {
      String id = "ABC";
      StaffPerson staffPersonRequest = MAPPER.readValue(
          fixture("fixtures/domain/legacy/StaffPerson/valid/valid.json"), StaffPerson.class);

      when(staffPersonDao.update(any())).thenThrow(EntityNotFoundException.class);

      staffPersonService.update("ZZZ", staffPersonRequest);
      Assert.fail("Expected EntityNotFoundException was not thrown");
    } catch (Exception ex) {
      assertEquals(ex.getClass(), ServiceException.class);
    }
  }

  // create test
  @SuppressWarnings("javadoc")
  @Test
  public void createThrowsAssertionError() throws Exception {
    thrown.expect(AssertionError.class);
    try {
      staffPersonService.create(null);
      Assert.fail("Expected AssertionError");
    } catch (AssertionError e) {
      assertEquals("Expected AssertionError", e.getMessage());
    }
  }

  @SuppressWarnings("javadoc")
  @Test
  public void createReturnsPostedStaffPersonClass() throws Exception {
    String id = "ABC";
    StaffPerson staffPersonDomain = MAPPER.readValue(
        fixture("fixtures/domain/legacy/StaffPerson/valid/valid.json"), StaffPerson.class);
    gov.ca.cwds.data.persistence.cms.StaffPerson toCreate =
        new gov.ca.cwds.data.persistence.cms.StaffPerson(id, staffPersonDomain, "2017-01-07");

    StaffPerson request = new StaffPerson(toCreate);

    when(staffPersonDao.create(any(gov.ca.cwds.data.persistence.cms.StaffPerson.class)))
        .thenReturn(toCreate);

    Response response = staffPersonService.create(request);

    assertThat(response.getClass(), is(PostedStaffPerson.class));
  }

  @SuppressWarnings("javadoc")
  @Test
  public void createReturnsNonNull() throws Exception {
    String id = "ABC";
    StaffPerson staffPersonDomain = MAPPER.readValue(
        fixture("fixtures/domain/legacy/StaffPerson/valid/valid.json"), StaffPerson.class);
    gov.ca.cwds.data.persistence.cms.StaffPerson toCreate =
        new gov.ca.cwds.data.persistence.cms.StaffPerson(id, staffPersonDomain, "2017-01-07");

    StaffPerson request = new StaffPerson(toCreate);

    when(staffPersonDao.create(any(gov.ca.cwds.data.persistence.cms.StaffPerson.class)))
        .thenReturn(toCreate);

    PostedStaffPerson postedStaffPerson = staffPersonService.create(request);

    assertThat(postedStaffPerson, is(notNullValue()));
  }

  @SuppressWarnings("javadoc")
  @Test
  public void createReturnsCorrectPostedPerson() throws Exception {
    String id = "ABC";
    StaffPerson staffPersonDomain = MAPPER.readValue(
        fixture("fixtures/domain/legacy/StaffPerson/valid/valid.json"), StaffPerson.class);
    gov.ca.cwds.data.persistence.cms.StaffPerson toCreate =
        new gov.ca.cwds.data.persistence.cms.StaffPerson(id, staffPersonDomain, "2017-01-07");

    StaffPerson request = new StaffPerson(toCreate);

    when(staffPersonDao.create(any(gov.ca.cwds.data.persistence.cms.StaffPerson.class)))
        .thenReturn(toCreate);

    PostedStaffPerson expected = new PostedStaffPerson(toCreate);

    PostedStaffPerson returned = staffPersonService.create(request);

    assertThat(returned, is(expected));
  }

  @SuppressWarnings("javadoc")
  @Test
  public void failsWhenPostedStaffPersonIdBlank() throws Exception {
    try {
      StaffPerson staffPersonDomain = MAPPER.readValue(
          fixture("fixtures/domain/legacy/StaffPerson/valid/valid.json"), StaffPerson.class);
      gov.ca.cwds.data.persistence.cms.StaffPerson toCreate =
          new gov.ca.cwds.data.persistence.cms.StaffPerson("   ", staffPersonDomain, "2017-01-07");

      when(staffPersonDao.create(any(gov.ca.cwds.data.persistence.cms.StaffPerson.class)))
          .thenReturn(toCreate);

      PostedStaffPerson expected = new PostedStaffPerson(toCreate);

      Assert.fail("Expected AssertionError not thrown");
    } catch (ServiceException e) {
      assertEquals("StaffPerson ID cannot be empty", e.getMessage());
    }
  }

  @SuppressWarnings("javadoc")
  @Test
  public void failsWhenPostedStaffPersonIdNull() throws Exception {
    try {
      StaffPerson staffPersonDomain = MAPPER.readValue(
          fixture("fixtures/domain/legacy/StaffPerson/valid/valid.json"), StaffPerson.class);
      gov.ca.cwds.data.persistence.cms.StaffPerson toCreate =
          new gov.ca.cwds.data.persistence.cms.StaffPerson(null, staffPersonDomain, "2017-01-07");

      when(staffPersonDao.create(any(gov.ca.cwds.data.persistence.cms.StaffPerson.class)))
          .thenReturn(toCreate);

      PostedStaffPerson expected = new PostedStaffPerson(toCreate);

      Assert.fail("Expected AssertionError not thrown");
    } catch (ServiceException e) {
      assertEquals("StaffPerson ID cannot be empty", e.getMessage());
    }
  }

  @SuppressWarnings("javadoc")
  @Test
  public void failsWhenPostedStaffPersonIdEmmpty() throws Exception {
    try {
      StaffPerson staffPersonDomain = MAPPER.readValue(
          fixture("fixtures/domain/legacy/StaffPerson/valid/valid.json"), StaffPerson.class);
      gov.ca.cwds.data.persistence.cms.StaffPerson toCreate =
          new gov.ca.cwds.data.persistence.cms.StaffPerson("", staffPersonDomain, "2017-01-07");

      when(staffPersonDao.create(any(gov.ca.cwds.data.persistence.cms.StaffPerson.class)))
          .thenReturn(toCreate);

      PostedStaffPerson expected = new PostedStaffPerson(toCreate);

      Assert.fail("Expected AssertionError not thrown");
    } catch (ServiceException e) {
      assertEquals("StaffPerson ID cannot be empty", e.getMessage());
    }
  }
}
