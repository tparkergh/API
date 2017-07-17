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

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.fasterxml.jackson.databind.ObjectMapper;

import gov.ca.cwds.data.cms.LongTextDao;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.cms.LongText;
import gov.ca.cwds.rest.api.domain.cms.PostedLongText;
import gov.ca.cwds.rest.services.ServiceException;
import io.dropwizard.jackson.Jackson;

/**
 * @author CWDS API Team
 *
 */
@SuppressWarnings("javadoc")
public class LongTextServiceTest {
  private static final ObjectMapper MAPPER = Jackson.newObjectMapper();
  private LongTextService longTextService;
  private LongTextDao longTextDao;
  private StaffPersonIdRetriever staffPersonIdRetriever;

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Before
  public void setup() throws Exception {
    longTextDao = mock(LongTextDao.class);
    staffPersonIdRetriever = mock(StaffPersonIdRetriever.class);
    longTextService = new LongTextService(longTextDao, staffPersonIdRetriever);
  }

  // find test
  @Test
  public void longTextServiceFindThrowsAssertionError() {
    // expect string type for primary key test
    thrown.expect(AssertionError.class);
    try {
      longTextService.find(1);
    } catch (AssertionError e) {
      assertEquals("Expeceted AssertionError", e.getMessage());
    }
  }

  @Test
  public void longTextServiceFindReturnsCorrectEntity() throws Exception {
    String id = "AaoDyiJq27";
    LongText expected = MAPPER
        .readValue(fixture("fixtures/domain/legacy/LongText/valid/valid.json"), LongText.class);
    gov.ca.cwds.data.persistence.cms.LongText longText =
        new gov.ca.cwds.data.persistence.cms.LongText(id, expected, "q27");

    when(longTextDao.find(id)).thenReturn(longText);
    LongText found = longTextService.find(id);
    assertThat(found, is(expected));
  }

  @Test
  public void longTextServiceFindReturnsNullWhenNotFound() throws Exception {
    Response found = longTextService.find("ABC1234567");
    assertThat(found, is(nullValue()));
  }

  @Test
  // delete test
  public void longTextServiceDeleteThrowsAssertionError() throws Exception {
    // expect string type for primary key test
    thrown.expect(AssertionError.class);
    try {
      longTextService.delete(123);
    } catch (AssertionError e) {
      assertEquals("Expected AssertionError", e.getMessage());
    }
  }

  @Test
  public void longTextServiceDeleteDelegatesToCrudsService() {
    longTextService.delete("ABC2345678");
    verify(longTextDao, times(1)).delete("ABC2345678");
  }

  @Test
  public void longTextServiceDeleteReturnsNullWhenNotFound() throws Exception {
    Response found = longTextService.delete("ABC1234567");
    assertThat(found, is(nullValue()));
  }

  // update test
  @Test
  public void longTextServiceUpdateThrowsAssertionError() throws Exception {
    // expected string type for primary key test
    thrown.expect(AssertionError.class);
    try {
      longTextService.update("ABC1234567", null);
    } catch (AssertionError e) {
      assertEquals("Expected AssertionError", e.getMessage());
    }
  }

  @Test
  public void longTextServiceUpdateReturnsCorrectEntity() throws Exception {
    String id = "AaoDyiJq27";
    LongText expected = MAPPER
        .readValue(fixture("fixtures/domain/legacy/LongText/valid/valid.json"), LongText.class);

    gov.ca.cwds.data.persistence.cms.LongText longText =
        new gov.ca.cwds.data.persistence.cms.LongText(id, expected, "q27");

    when(longTextDao.find("ABC1234567")).thenReturn(longText);
    when(longTextDao.update(any())).thenReturn(longText);

    Object retval = longTextService.update("ABC1234567", expected);
    assertThat(retval.getClass(), is(LongText.class));
  }

  @Test
  public void longTextServiceUpdateThrowsExceptionWhenNotFound() throws Exception {
    try {
      LongText longTextRquest = MAPPER
          .readValue(fixture("fixtures/domain/legacy/LongText/valid/valid.json"), LongText.class);

      when(longTextDao.update(any())).thenThrow(EntityNotFoundException.class);

      longTextService.update("ZZZZZZZZZZ", longTextRquest);
    } catch (Exception e) {
      assertEquals(e.getClass(), ServiceException.class);
    }
  }

  // create test
  @Test
  public void longTextServiceCreateReturnsPostedClass() throws Exception {
    String id = "AaoDyiJq27";
    LongText longTextDomain = MAPPER
        .readValue(fixture("fixtures/domain/legacy/LongText/valid/valid.json"), LongText.class);
    gov.ca.cwds.data.persistence.cms.LongText toCreate =
        new gov.ca.cwds.data.persistence.cms.LongText(id, longTextDomain, "q27");

    LongText request = new LongText(toCreate);
    when(longTextDao.create(any(gov.ca.cwds.data.persistence.cms.LongText.class)))
        .thenReturn(toCreate);

    Response response = longTextService.create(request);
    assertThat(response.getClass(), is(PostedLongText.class));
  }

  @Test
  public void longTextServiceCreateReturnsNonNull() throws Exception {
    String id = "AaoDyiJq27";
    LongText longTextDomain = MAPPER
        .readValue(fixture("fixtures/domain/legacy/LongText/valid/valid.json"), LongText.class);
    gov.ca.cwds.data.persistence.cms.LongText toCreate =
        new gov.ca.cwds.data.persistence.cms.LongText(id, longTextDomain, "q27");

    LongText request = new LongText(toCreate);
    when(longTextDao.create(any(gov.ca.cwds.data.persistence.cms.LongText.class)))
        .thenReturn(toCreate);

    PostedLongText postedLongText = longTextService.create(request);
    assertThat(postedLongText, is(notNullValue()));
  }

  @Test
  public void longTextServiceCreateReturnsCorrectEntity() throws Exception {
    String id = "AaoDyiJq27";
    LongText longTextDomain = MAPPER
        .readValue(fixture("fixtures/domain/legacy/LongText/valid/valid.json"), LongText.class);
    gov.ca.cwds.data.persistence.cms.LongText toCreate =
        new gov.ca.cwds.data.persistence.cms.LongText(id, longTextDomain, "q27");

    LongText request = new LongText(toCreate);
    when(longTextDao.create(any(gov.ca.cwds.data.persistence.cms.LongText.class)))
        .thenReturn(toCreate);

    PostedLongText expected = new PostedLongText(toCreate);
    PostedLongText returned = longTextService.create(request);
    assertThat(returned, is(expected));
  }

  @Test
  public void longTextServiceCreateThrowsEntityExistsException() throws Exception {
    try {
      LongText longTextRquest = MAPPER
          .readValue(fixture("fixtures/domain/legacy/LongText/valid/valid.json"), LongText.class);

      when(longTextDao.create(any())).thenThrow(EntityExistsException.class);

      longTextService.create(longTextRquest);
    } catch (Exception e) {
      assertEquals(e.getClass(), ServiceException.class);
    }
  }

  @Test
  public void longTextServiceCreateNullIDError() throws Exception {
    try {
      LongText longTextDomain = MAPPER
          .readValue(fixture("fixtures/domain/legacy/LongText/valid/valid.json"), LongText.class);
      gov.ca.cwds.data.persistence.cms.LongText toCreate =
          new gov.ca.cwds.data.persistence.cms.LongText(null, longTextDomain, "q27");

      when(longTextDao.create(any(gov.ca.cwds.data.persistence.cms.LongText.class)))
          .thenReturn(toCreate);

      PostedLongText expected = new PostedLongText(toCreate);
    } catch (ServiceException e) {
      assertEquals("LongText ID cannot be blank", e.getMessage());
    }

  }

  @Test
  public void longTextServiceCreateBlankIDError() throws Exception {
    try {
      LongText longTextDomain = MAPPER
          .readValue(fixture("fixtures/domain/legacy/LongText/valid/valid.json"), LongText.class);
      gov.ca.cwds.data.persistence.cms.LongText toCreate =
          new gov.ca.cwds.data.persistence.cms.LongText(" ", longTextDomain, "q27");

      when(longTextDao.create(any(gov.ca.cwds.data.persistence.cms.LongText.class)))
          .thenReturn(toCreate);

      PostedLongText expected = new PostedLongText(toCreate);
    } catch (ServiceException e) {
      assertEquals("LongText ID cannot be blank", e.getMessage());
    }

  }

  /*
   * Test for checking the new Allegation Id generated and lenght is 10
   */
  @Test
  public void longTextServicecreateReturnsGeneratedId() throws Exception {
    LongText longTextDomain = MAPPER
        .readValue(fixture("fixtures/domain/legacy/LongText/valid/valid.json"), LongText.class);
    when(longTextDao.create(any(gov.ca.cwds.data.persistence.cms.LongText.class)))
        .thenAnswer(new Answer<gov.ca.cwds.data.persistence.cms.LongText>() {

          @Override
          public gov.ca.cwds.data.persistence.cms.LongText answer(InvocationOnMock invocation)
              throws Throwable {
            gov.ca.cwds.data.persistence.cms.LongText report =
                (gov.ca.cwds.data.persistence.cms.LongText) invocation.getArguments()[0];
            return report;
          }
        });

    PostedLongText returned = longTextService.create(longTextDomain);
    assertEquals(returned.getId().length(), 10);
    PostedLongText newReturned = longTextService.create(longTextDomain);
    Assert.assertNotEquals(returned.getId(), newReturned.getId());
  }

}
