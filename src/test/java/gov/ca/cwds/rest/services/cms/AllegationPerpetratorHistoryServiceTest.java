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
import java.util.Date;

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

import gov.ca.cwds.data.cms.AllegationPerpetratorHistoryDao;
import gov.ca.cwds.fixture.AllegationPerpetratorHistoryResourceBuilder;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.cms.AllegationPerpetratorHistory;
import gov.ca.cwds.rest.api.domain.cms.PostedAllegationPerpetratorHistory;
import gov.ca.cwds.rest.services.ServiceException;
import gov.ca.cwds.rest.services.junit.template.ServiceTestTemplate;
import io.dropwizard.jackson.Jackson;

/**
 * @author CWDS API Team
 *
 */
public class AllegationPerpetratorHistoryServiceTest implements ServiceTestTemplate {
  private static final ObjectMapper MAPPER = Jackson.newObjectMapper();
  private AllegationPerpetratorHistoryService allegationPerpetratorHistoryService;
  private AllegationPerpetratorHistoryDao allegationPerpetratorHistoryDao;
  private StaffPersonIdRetriever staffPersonIdRetriever;
  private Date timestamp;

  @SuppressWarnings("javadoc")
  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Override
  @Before
  public void setup() throws Exception {
    allegationPerpetratorHistoryDao = mock(AllegationPerpetratorHistoryDao.class);
    staffPersonIdRetriever = mock(StaffPersonIdRetriever.class);
    allegationPerpetratorHistoryService = new AllegationPerpetratorHistoryService(
        allegationPerpetratorHistoryDao, staffPersonIdRetriever);
   timestamp = new Date();
  }

  // find test
  @Override
  @Test
  public void testFindThrowsAssertionError() {
    // expect string type for primary key test
    thrown.expect(AssertionError.class);
    try {
      allegationPerpetratorHistoryService.find(1);
    } catch (AssertionError e) {
      assertEquals("Expeceted AssertionError", e.getMessage());
    }
  }

  @Override
  @Test
  public void testFindReturnsCorrectEntity() throws Exception {
    String id = "AbjFyc80It";
    AllegationPerpetratorHistory expected =
        new AllegationPerpetratorHistoryResourceBuilder().createAllegationPerpetratorHistory();

    gov.ca.cwds.data.persistence.cms.AllegationPerpetratorHistory allegationPerpetratorHistory =
        new gov.ca.cwds.data.persistence.cms.AllegationPerpetratorHistory(id, expected, "0XA");

    when(allegationPerpetratorHistoryDao.find(id)).thenReturn(allegationPerpetratorHistory);
    AllegationPerpetratorHistory found = allegationPerpetratorHistoryService.find(id);
    assertThat(found, is(expected));
  }

  @Override
  @Test
  public void testFindReturnsNullWhenNotFound() throws Exception {
    Response found = allegationPerpetratorHistoryService.find("ABC1234567");
    assertThat(found, is(nullValue()));
  }

  @Override
  @Test
  // delete test
  public void testDeleteThrowsAssertionError() throws Exception {
    // expect string type for primary key test
    thrown.expect(AssertionError.class);
    try {
      allegationPerpetratorHistoryService.delete(123);
    } catch (AssertionError e) {
      assertEquals("Expected AssertionError", e.getMessage());
    }
  }

  @Override
  @Test
  public void testDeleteDelegatesToCrudsService() {
    allegationPerpetratorHistoryService.delete("ABC2345678");
    verify(allegationPerpetratorHistoryDao, times(1)).delete("ABC2345678");
  }

  @Override
  @Test
  public void testDeleteReturnsNullWhenNotFound() throws Exception {
    Response found = allegationPerpetratorHistoryService.delete("ABC1234567");
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
      allegationPerpetratorHistoryService.update("ABC1234567", null);
    } catch (AssertionError e) {
      assertEquals("Expected AssertionError", e.getMessage());
    }
  }

  @Override
  @Test
  public void testUpdateReturnsCorrectEntity() throws Exception {
    String id = "AbjFyc80It";
    AllegationPerpetratorHistory expected =
        new AllegationPerpetratorHistoryResourceBuilder().createAllegationPerpetratorHistory();

    gov.ca.cwds.data.persistence.cms.AllegationPerpetratorHistory allegationPerpetratorHistory =
        new gov.ca.cwds.data.persistence.cms.AllegationPerpetratorHistory(id, expected, "ABC");

    when(allegationPerpetratorHistoryDao.find("ABC1234567"))
        .thenReturn(allegationPerpetratorHistory);
    when(allegationPerpetratorHistoryDao.update(any())).thenReturn(allegationPerpetratorHistory);

    Object retval = allegationPerpetratorHistoryService.update("ABC1234567", expected);
    assertThat(retval.getClass(), is(AllegationPerpetratorHistory.class));
  }

  @SuppressWarnings("javadoc")
  @Test
  public void testUpdateThrowsExceptionWhenNotFound() throws Exception {
    try {
      AllegationPerpetratorHistory allegationPerpetratorHistoryRequest =
          new AllegationPerpetratorHistoryResourceBuilder().createAllegationPerpetratorHistory();

      when(allegationPerpetratorHistoryDao.update(any())).thenThrow(EntityNotFoundException.class);

      allegationPerpetratorHistoryService.update("ZZZZZZZZZZ", allegationPerpetratorHistoryRequest);
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
    String id = "AbjFyc80It";
    AllegationPerpetratorHistory allegationPerpetratorHistoryDomain =
        new AllegationPerpetratorHistoryResourceBuilder().createAllegationPerpetratorHistory();

    gov.ca.cwds.data.persistence.cms.AllegationPerpetratorHistory toCreate =
        new gov.ca.cwds.data.persistence.cms.AllegationPerpetratorHistory(id,
            allegationPerpetratorHistoryDomain, "ABC");

    AllegationPerpetratorHistory request = new AllegationPerpetratorHistory(toCreate);
    when(allegationPerpetratorHistoryDao
        .create(any(gov.ca.cwds.data.persistence.cms.AllegationPerpetratorHistory.class)))
            .thenReturn(toCreate);

    Response response = allegationPerpetratorHistoryService.create(request);
    assertThat(response.getClass(), is(PostedAllegationPerpetratorHistory.class));
  }

  @SuppressWarnings("javadoc")
  @Test
  public void allegationPerpetratorHistoryServiceCreateThrowsEntityExistsException()
      throws Exception {
    try {
      AllegationPerpetratorHistory allegationPerpetratorHistoryRequest = MAPPER.readValue(
          fixture("fixtures/domain/legacy/AllegationPerpetratorHistory/valid/valid.json"),
          AllegationPerpetratorHistory.class);

      when(allegationPerpetratorHistoryDao.create(any())).thenThrow(EntityExistsException.class);
      allegationPerpetratorHistoryService.create(allegationPerpetratorHistoryRequest);
    } catch (Exception e) {
      assertEquals(e.getClass(), ServiceException.class);
    }
  }

  @SuppressWarnings("javadoc")
  @Test
  public void testCreateReturnsNonNull() throws Exception {
    String id = "AbjFyc80It";
    AllegationPerpetratorHistory allegationPerpetratorHistoryDomain =
        new AllegationPerpetratorHistoryResourceBuilder().createAllegationPerpetratorHistory();

    gov.ca.cwds.data.persistence.cms.AllegationPerpetratorHistory toCreate =
        new gov.ca.cwds.data.persistence.cms.AllegationPerpetratorHistory(id,
            allegationPerpetratorHistoryDomain, "ABC");

    AllegationPerpetratorHistory request = new AllegationPerpetratorHistory(toCreate);
    when(allegationPerpetratorHistoryDao
        .create(any(gov.ca.cwds.data.persistence.cms.AllegationPerpetratorHistory.class)))
            .thenReturn(toCreate);

    PostedAllegationPerpetratorHistory postedAllegationPerpetratorHistory =
        allegationPerpetratorHistoryService.create(request);
    assertThat(postedAllegationPerpetratorHistory, is(notNullValue()));
  }

  @Override
  @Test
  public void testCreateReturnsCorrectEntity() throws Exception {
    String id = "AbjFyc80It";
    AllegationPerpetratorHistory allegationPerpetratorHistoryDomain =
        new AllegationPerpetratorHistoryResourceBuilder().createAllegationPerpetratorHistory();

    gov.ca.cwds.data.persistence.cms.AllegationPerpetratorHistory toCreate =
        new gov.ca.cwds.data.persistence.cms.AllegationPerpetratorHistory(id,
            allegationPerpetratorHistoryDomain, "ABC");

    AllegationPerpetratorHistory request = new AllegationPerpetratorHistory(toCreate);
    when(allegationPerpetratorHistoryDao
        .create(any(gov.ca.cwds.data.persistence.cms.AllegationPerpetratorHistory.class)))
            .thenReturn(toCreate);

    PostedAllegationPerpetratorHistory expected = new PostedAllegationPerpetratorHistory(toCreate);
    PostedAllegationPerpetratorHistory returned =
        allegationPerpetratorHistoryService.create(request);
    assertThat(returned, is(expected));
  }

  @SuppressWarnings("javadoc")
  @Test
  public void testCreateWithSingleTimestampReturnsCorrectEntity() throws Exception {
    String id = "AbjFyc80It";
    AllegationPerpetratorHistory allegationPerpetratorHistoryDomain =
        new AllegationPerpetratorHistoryResourceBuilder().createAllegationPerpetratorHistory();
    gov.ca.cwds.data.persistence.cms.AllegationPerpetratorHistory toCreate =
        new gov.ca.cwds.data.persistence.cms.AllegationPerpetratorHistory(id,
            allegationPerpetratorHistoryDomain, "ABC");

    AllegationPerpetratorHistory request = new AllegationPerpetratorHistory(toCreate);
    when(allegationPerpetratorHistoryDao
        .create(any(gov.ca.cwds.data.persistence.cms.AllegationPerpetratorHistory.class)))
            .thenReturn(toCreate);

    PostedAllegationPerpetratorHistory expected = new PostedAllegationPerpetratorHistory(toCreate);
    PostedAllegationPerpetratorHistory returned =
        allegationPerpetratorHistoryService.createWithSingleTimestamp(request, timestamp);
    assertThat(returned, is(expected));
  }

  @Override
  @Test
  public void testCreateNullIDError() throws Exception {
    try {
      AllegationPerpetratorHistory allegationPerpetratorHistoryDomain =
          new AllegationPerpetratorHistoryResourceBuilder().createAllegationPerpetratorHistory();
      gov.ca.cwds.data.persistence.cms.AllegationPerpetratorHistory toCreate =
          new gov.ca.cwds.data.persistence.cms.AllegationPerpetratorHistory(null,
              allegationPerpetratorHistoryDomain, "ABC");

      when(allegationPerpetratorHistoryDao
          .create(any(gov.ca.cwds.data.persistence.cms.AllegationPerpetratorHistory.class)))
              .thenReturn(toCreate);

      PostedAllegationPerpetratorHistory expected =
          new PostedAllegationPerpetratorHistory(toCreate);
    } catch (ServiceException e) {
      assertEquals("AllegationPerpetratorHistory ID cannot be blank", e.getMessage());
    }

  }

  @Override
  @Test
  public void testCreateBlankIDError() throws Exception {
    try {
      AllegationPerpetratorHistory allegationPerpetratorHistoryDomain =
          new AllegationPerpetratorHistoryResourceBuilder().createAllegationPerpetratorHistory();

      gov.ca.cwds.data.persistence.cms.AllegationPerpetratorHistory toCreate =
          new gov.ca.cwds.data.persistence.cms.AllegationPerpetratorHistory(" ",
              allegationPerpetratorHistoryDomain, "ABC");

      when(allegationPerpetratorHistoryDao
          .create(any(gov.ca.cwds.data.persistence.cms.AllegationPerpetratorHistory.class)))
              .thenReturn(toCreate);

      PostedAllegationPerpetratorHistory expected =
          new PostedAllegationPerpetratorHistory(toCreate);
    } catch (ServiceException e) {
      assertEquals("AllegationPerpetratorHistory ID cannot be blank", e.getMessage());
    }

  }

  /*
   * Test for checking the new Allegation Id generated and lenght is 10
   */
  @SuppressWarnings("javadoc")
  @Test
  public void testCreateReturnsGeneratedId() throws Exception {
    AllegationPerpetratorHistory allegationPerpetratorHistoryDomain =
        new AllegationPerpetratorHistoryResourceBuilder().createAllegationPerpetratorHistory();
    when(allegationPerpetratorHistoryDao
        .create(any(gov.ca.cwds.data.persistence.cms.AllegationPerpetratorHistory.class)))
            .thenAnswer(
                new Answer<gov.ca.cwds.data.persistence.cms.AllegationPerpetratorHistory>() {

                  @Override
                  public gov.ca.cwds.data.persistence.cms.AllegationPerpetratorHistory answer(
                      InvocationOnMock invocation) throws Throwable {
                    gov.ca.cwds.data.persistence.cms.AllegationPerpetratorHistory allegationPerpetratorHistory =
                        (gov.ca.cwds.data.persistence.cms.AllegationPerpetratorHistory) invocation
                            .getArguments()[0];
                    return allegationPerpetratorHistory;
                  }
                });

    PostedAllegationPerpetratorHistory returned =
        allegationPerpetratorHistoryService.create(allegationPerpetratorHistoryDomain);
    assertEquals(returned.getId().length(), 10);
    PostedAllegationPerpetratorHistory newReturned =
        allegationPerpetratorHistoryService.create(allegationPerpetratorHistoryDomain);
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
