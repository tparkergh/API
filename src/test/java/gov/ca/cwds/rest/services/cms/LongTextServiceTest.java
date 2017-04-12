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
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.fasterxml.jackson.databind.ObjectMapper;

import gov.ca.cwds.data.cms.LongTextDao;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.cms.LongText;
import gov.ca.cwds.rest.api.domain.cms.PostedLongText;
import gov.ca.cwds.rest.services.ServiceException;
import gov.ca.cwds.rest.services.junit.template.ServiceTestTemplate;
import io.dropwizard.jackson.Jackson;

/**
 * @author CWDS API Team
 *
 */
public class LongTextServiceTest implements ServiceTestTemplate {
  private static final ObjectMapper MAPPER = Jackson.newObjectMapper();
  private LongTextService longTextService;
  private LongTextDao longTextDao;

  @SuppressWarnings("javadoc")
  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Override
  @Before
  public void setup() throws Exception {
    longTextDao = mock(LongTextDao.class);
    longTextService = new LongTextService(longTextDao);
  }

  // find test
  @Override
  @Test
  public void testFindThrowsAssertionError() {
    // expect string type for primary key test
    thrown.expect(AssertionError.class);
    try {
      longTextService.find(1);
    } catch (AssertionError e) {
      assertEquals("Expeceted AssertionError", e.getMessage());
    }
  }

  @Override
  @Test
  public void testFindReturnsCorrectEntity() throws Exception {
    String id = "AaoDyiJq27";
    LongText expected = MAPPER
        .readValue(fixture("fixtures/domain/legacy/LongText/valid/valid.json"), LongText.class);
    gov.ca.cwds.data.persistence.cms.LongText longText =
        new gov.ca.cwds.data.persistence.cms.LongText(id, expected, "q27");

    when(longTextDao.find(id)).thenReturn(longText);
    LongText found = longTextService.find(id);
    assertThat(found, is(expected));
  }

  @Override
  @Test
  public void testFindReturnsNullWhenNotFound() throws Exception {
    Response found = longTextService.find("ABC1234567");
    assertThat(found, is(nullValue()));
  }

  @Override
  @Test
  // delete test
  public void testDeleteThrowsAssertionError() throws Exception {
    // expect string type for primary key test
    thrown.expect(AssertionError.class);
    try {
      longTextService.delete(123);
    } catch (AssertionError e) {
      assertEquals("Expected AssertionError", e.getMessage());
    }
  }

  @Override
  @Test
  public void testDeleteDelegatesToCrudsService() {
    longTextService.delete("ABC2345678");
    verify(longTextDao, times(1)).delete("ABC2345678");
  }

  @Override
  @Test
  public void testDeleteReturnsNullWhenNotFound() throws Exception {
    Response found = longTextService.delete("ABC1234567");
    assertThat(found, is(nullValue()));
  }

  @Override
  public void testDeleteThrowsNotImplementedException() throws Exception {
    // delete is implemented

  }

  @Override
  public void testDeleteReturnsClass() throws Exception {

  }

  // update test
  @Override
  @Test
  public void testUpdateThrowsAssertionError() throws Exception {
    // expected string type for primary key test
    thrown.expect(AssertionError.class);
    try {
      longTextService.update("ABC1234567", null);
    } catch (AssertionError e) {
      assertEquals("Expected AssertionError", e.getMessage());
    }
  }

  @Override
  @Test
  public void testUpdateReturnsCorrectEntity() throws Exception {
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

  @SuppressWarnings("javadoc")
  @Test
  public void testUpdateThrowsExceptionWhenNotFound() throws Exception {
    try {
      LongText longTextRquest = MAPPER
          .readValue(fixture("fixtures/domain/legacy/LongText/valid/valid.json"), LongText.class);

      when(longTextDao.update(any())).thenThrow(EntityNotFoundException.class);

      longTextService.update("ZZZZZZZZZZ", longTextRquest);
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

  @SuppressWarnings("javadoc")
  @Test
  public void testCreateReturnsNonNull() throws Exception {
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

  @Override
  @Test
  public void testCreateReturnsCorrectEntity() throws Exception {
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

  @Override
  @Test
  public void testCreateNullIDError() throws Exception {
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

  @Override
  @Test
  public void testCreateBlankIDError() throws Exception {
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
  @SuppressWarnings("javadoc")
  @Test
  public void createReturnsGeneratedId() throws Exception {
    LongText longTextDomain = MAPPER
        .readValue(fixture("fixtures/domain/legacy/LOngText/valid/valid.json"), LongText.class);
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
