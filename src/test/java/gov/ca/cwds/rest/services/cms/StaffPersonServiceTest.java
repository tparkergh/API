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

public class StaffPersonServiceTest {
  private static final ObjectMapper MAPPER = Jackson.newObjectMapper();
  private StaffPersonService staffPersonService;
  private StaffPersonDao staffPersonDao;

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Before
  public void setup() throws Exception {
    staffPersonDao = mock(StaffPersonDao.class);

    staffPersonService = new StaffPersonService(staffPersonDao);

  }

  // find test
  @Test
  public void findThrowsAssertionError() {
    thrown.expect(AssertionError.class);
    try {
      staffPersonService.find(1);
    } catch (AssertionError e) {
      assertEquals("Expected AssertionError", e.getMessage());
    }
  }

  @Test
  public void findReturnsCorrectStaffPersonWhenFound() throws Exception {
    String id = "ABC";
    StaffPerson expected = MAPPER.readValue(
        fixture("fixtures/domain/legacy/StaffPerson/valid/valid.json"), StaffPerson.class);

    gov.ca.cwds.data.persistence.cms.StaffPerson staffPerson =
        new gov.ca.cwds.data.persistence.cms.StaffPerson(id, expected, "000");

    when(staffPersonDao.find(id)).thenReturn(staffPerson);

    StaffPerson found = staffPersonService.find(id);

    assertThat(found, is(expected));
  }

  @Test
  public void findReturnsNullWhenNotFound() throws Exception {
    Response found = staffPersonService.find("0XA");
    assertThat(found, is(nullValue()));
  }

  @Test
  public void deleteThrowsAssersionError() throws Exception {
    thrown.expect(AssertionError.class);
    try {
      staffPersonService.delete(1);
    } catch (AssertionError e) {
      assertEquals("Expected AssertionError", e.getMessage());
    }
  }

  @Test
  public void deleteReturnsNullWhenNotFount() throws Exception {
    Response found = staffPersonService.delete("ABC");
    assertThat(found, is(nullValue()));
  }

  @Test
  public void deleteDelegatesToCrudsService() {
    staffPersonService.delete("ABC");
    verify(staffPersonDao, times(1)).delete("ABC");
  }

  // update test
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

  @Test
  public void updateReturnsStaffPersonResponseOnSuccess() throws Exception {
    String id = "ABC";
    StaffPerson expected = MAPPER.readValue(
        fixture("fixtures/domain/legacy/StaffPerson/valid/valid.json"), StaffPerson.class);

    gov.ca.cwds.data.persistence.cms.StaffPerson staffPerson =
        new gov.ca.cwds.data.persistence.cms.StaffPerson(id, expected, "000");

    when(staffPersonDao.find(id)).thenReturn(staffPerson);
    when(staffPersonDao.update(any())).thenReturn(staffPerson);
    Object retval = staffPersonService.update(id, expected);
    assertThat(retval.getClass(), is(StaffPerson.class));

  }

  @Test
  public void updateReturnsCorrectStaffPersonOnSuccess() throws Exception {
    String id = "ABC";
    StaffPerson staffPersonRequest = MAPPER.readValue(
        fixture("fixtures/domain/legacy/StaffPerson/valid/valid.json"), StaffPerson.class);

    gov.ca.cwds.data.persistence.cms.StaffPerson staffPerson =
        new gov.ca.cwds.data.persistence.cms.StaffPerson(id, staffPersonRequest, "000");

    when(staffPersonDao.find(id)).thenReturn(staffPerson);
    when(staffPersonDao.update(any())).thenReturn(staffPerson);

    StaffPerson expected = new StaffPerson(staffPerson);
    StaffPerson updated = staffPersonService.update(id, expected);

    assertThat(updated, is(expected));

  }

  @Test
  public void updateThrowsExceptionWhenStaffPersonNotFound() throws Exception {
    // TODO: test does not throw exception from allegationService.update method
    //
    // remove comments before running unit test
    //
    // thrown.expect(ServiceException.class);
    // thrown.expectCause(Is.isA(EntityNotFoundException.class));
    // thrown.expectMessage(contains("Expected test to throw"));
    String id = "ABC";
    StaffPerson staffPersonRequest = MAPPER.readValue(
        fixture("fixtures/domain/legacy/StaffPerson/valid/valid.json"), StaffPerson.class);

    gov.ca.cwds.data.persistence.cms.StaffPerson staffPerson =
        new gov.ca.cwds.data.persistence.cms.StaffPerson(id, staffPersonRequest, "000");

    when(staffPersonDao.find(id)).thenReturn(staffPerson);
    when(staffPersonDao.update(any())).thenReturn(staffPerson);

    staffPersonService.update("ZZZ", staffPersonRequest);
  }

  // create test
  @Test
  public void createReturnsPostedStaffPersonClass() throws Exception {
    String id = "ABC";
    StaffPerson staffPersonDomain = MAPPER.readValue(
        fixture("fixtures/domain/legacy/StaffPerson/valid/valid.json"), StaffPerson.class);
    gov.ca.cwds.data.persistence.cms.StaffPerson toCreate =
        new gov.ca.cwds.data.persistence.cms.StaffPerson(id, staffPersonDomain, "last_update");

    StaffPerson request = new StaffPerson(toCreate);

    when(staffPersonDao.create(any(gov.ca.cwds.data.persistence.cms.StaffPerson.class)))
        .thenReturn(toCreate);

    Response response = staffPersonService.create(request);

    assertThat(response.getClass(), is(PostedStaffPerson.class));
  }

  @Test
  public void createReturnsNonNull() throws Exception {
    String id = "ABC";
    StaffPerson staffPersonDomain = MAPPER.readValue(
        fixture("fixtures/domain/legacy/StaffPerson/valid/valid.json"), StaffPerson.class);
    gov.ca.cwds.data.persistence.cms.StaffPerson toCreate =
        new gov.ca.cwds.data.persistence.cms.StaffPerson(id, staffPersonDomain, "last_update");

    StaffPerson request = new StaffPerson(toCreate);

    when(staffPersonDao.create(any(gov.ca.cwds.data.persistence.cms.StaffPerson.class)))
        .thenReturn(toCreate);

    PostedStaffPerson postedStaffPerson = staffPersonService.create(request);

    assertThat(postedStaffPerson, is(notNullValue()));
  }

  @Test
  public void createReturnsCorrectPostedPerson() throws Exception {
    String id = "ABC";
    StaffPerson staffPersonDomain = MAPPER.readValue(
        fixture("fixtures/domain/legacy/StaffPerson/valid/valid.json"), StaffPerson.class);
    gov.ca.cwds.data.persistence.cms.StaffPerson toCreate =
        new gov.ca.cwds.data.persistence.cms.StaffPerson(id, staffPersonDomain, "last_update");

    StaffPerson request = new StaffPerson(toCreate);

    when(staffPersonDao.create(any(gov.ca.cwds.data.persistence.cms.StaffPerson.class)))
        .thenReturn(toCreate);

    PostedStaffPerson expected = new PostedStaffPerson(toCreate);

    PostedStaffPerson returned = staffPersonService.create(request);

    assertThat(returned, is(expected));
  }

  @Test
  public void failsWhenPostedStaffPersonIdBlank() throws Exception {
    try {
      StaffPerson staffPersonDomain = MAPPER.readValue(
          fixture("fixtures/domain/legacy/StaffPerson/valid/valid.json"), StaffPerson.class);
      gov.ca.cwds.data.persistence.cms.StaffPerson toCreate =
          new gov.ca.cwds.data.persistence.cms.StaffPerson("   ", staffPersonDomain,
              "last_update");

      when(staffPersonDao.create(any(gov.ca.cwds.data.persistence.cms.StaffPerson.class)))
          .thenReturn(toCreate);

      PostedStaffPerson expected = new PostedStaffPerson(toCreate);

      Assert.fail("Expected AssertionError not thrown");
    } catch (ServiceException e) {
      assertEquals("StaffPerson ID cannot be empty", e.getMessage());
    }
  }

  @Test
  public void failsWhenPostedStaffPersonIdNull() throws Exception {
    try {
      StaffPerson staffPersonDomain = MAPPER.readValue(
          fixture("fixtures/domain/legacy/StaffPerson/valid/valid.json"), StaffPerson.class);
      gov.ca.cwds.data.persistence.cms.StaffPerson toCreate =
          new gov.ca.cwds.data.persistence.cms.StaffPerson(null, staffPersonDomain,
              "last_update");

      when(staffPersonDao.create(any(gov.ca.cwds.data.persistence.cms.StaffPerson.class)))
          .thenReturn(toCreate);

      PostedStaffPerson expected = new PostedStaffPerson(toCreate);

      Assert.fail("Expected AssertionError not thrown");
    } catch (ServiceException e) {
      assertEquals("StaffPerson ID cannot be empty", e.getMessage());
    }
  }

  @Test
  public void failsWhenPostedStaffPersonIdEmmpty() throws Exception {
    try {
      StaffPerson staffPersonDomain = MAPPER.readValue(
          fixture("fixtures/domain/legacy/StaffPerson/valid/valid.json"), StaffPerson.class);
      gov.ca.cwds.data.persistence.cms.StaffPerson toCreate =
          new gov.ca.cwds.data.persistence.cms.StaffPerson("", staffPersonDomain,
              "last_update");

      when(staffPersonDao.create(any(gov.ca.cwds.data.persistence.cms.StaffPerson.class)))
          .thenReturn(toCreate);

      PostedStaffPerson expected = new PostedStaffPerson(toCreate);

      Assert.fail("Expected AssertionError not thrown");
    } catch (ServiceException e) {
      assertEquals("StaffPerson ID cannot be empty", e.getMessage());
    }
  }
}
