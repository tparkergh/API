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

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.fasterxml.jackson.databind.ObjectMapper;

import gov.ca.cwds.data.cms.TickleDao;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.cms.Tickle;
import gov.ca.cwds.rest.services.ServiceException;
import gov.ca.cwds.rest.services.junit.template.ServiceTestTemplate;
import io.dropwizard.jackson.Jackson;

/**
 * @author CWDS API Team
 *
 */
public class TickleServiceTest implements ServiceTestTemplate {
  private static final ObjectMapper MAPPER = Jackson.newObjectMapper();
  private TickleService tickleService;
  private TickleDao tickleDao;
  private StaffPersonIdRetriever staffPersonIdRetriever;

  @SuppressWarnings("javadoc")
  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Override
  @Before
  public void setup() throws Exception {
    tickleDao = mock(TickleDao.class);
    staffPersonIdRetriever = mock(StaffPersonIdRetriever.class);
    tickleService = new TickleService(tickleDao, staffPersonIdRetriever);
  }

  // find test
  @Override
  @Test
  public void testFindThrowsAssertionError() {
    // expect string type for primary key test
    thrown.expect(AssertionError.class);
    try {
      tickleService.find(1);
    } catch (AssertionError e) {
      assertEquals("Expeceted AssertionError", e.getMessage());
    }
  }

  @Override
  @Test
  public void testFindReturnsCorrectEntity() throws Exception {
    String id = "AabekZX00F";
    Tickle expected =
        MAPPER.readValue(fixture("fixtures/domain/legacy/Tickle/valid/valid.json"), Tickle.class);
    gov.ca.cwds.data.persistence.cms.Tickle tickle =
        new gov.ca.cwds.data.persistence.cms.Tickle(id, expected, "0XA");

    when(tickleDao.find(id)).thenReturn(tickle);
    Tickle found = tickleService.find(id);
    assertThat(found, is(expected));
  }

  @Override
  @Test
  public void testFindReturnsNullWhenNotFound() throws Exception {
    Response found = tickleService.find("ABC1234567");
    assertThat(found, is(nullValue()));
  }

  // delete test
  @Override
  @Test
  public void testDeleteThrowsAssertionError() throws Exception {
    thrown.expect(AssertionError.class);
    try {
      tickleService.delete(123);
    } catch (AssertionError e) {
      assertEquals("Expected AssertionError", e.getMessage());
    }
  }

  @Override
  @Test
  public void testDeleteDelegatesToCrudsService() {
    tickleService.delete("ABC2345678");
    verify(tickleDao, times(1)).delete("ABC2345678");
  }

  @Override
  @Test
  public void testDeleteReturnsNullWhenNotFound() throws Exception {
    Response found = tickleService.delete("ABC1234567");
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
  @Test
  public void testUpdateThrowsAssertionError() throws Exception {
    thrown.expect(AssertionError.class);
    try {
      tickleService.update("ABC1234567", null);
    } catch (AssertionError e) {
      assertEquals("Expected AssertionError", e.getMessage());
    }
  }

  @Override
  @Test
  public void testUpdateReturnsCorrectEntity() throws Exception {
    String id = "AabekZX00F";
    Tickle expected =
        MAPPER.readValue(fixture("fixtures/domain/legacy/Tickle/valid/valid.json"), Tickle.class);

    gov.ca.cwds.data.persistence.cms.Tickle tickle =
        new gov.ca.cwds.data.persistence.cms.Tickle(id, expected, "ABC");

    when(tickleDao.find("ABC1234567")).thenReturn(tickle);
    when(tickleDao.update(any())).thenReturn(tickle);

    Object retval = tickleService.update("ABC1234567", expected);
    assertThat(retval.getClass(), is(Tickle.class));
  }

  @SuppressWarnings("javadoc")
  @Test
  public void testUpdateThrowsExceptionWhenNotFound() throws Exception {
    try {
      Tickle tickleRequest =
          MAPPER.readValue(fixture("fixtures/domain/legacy/Tickle/valid/valid.json"), Tickle.class);

      when(tickleDao.update(any())).thenThrow(EntityNotFoundException.class);

      tickleService.update("ZZZZZZZZZZ", tickleRequest);
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
    String id = "AabekZX00F";
    Tickle tickleDomain =
        MAPPER.readValue(fixture("fixtures/domain/legacy/Tickle/valid/valid.json"), Tickle.class);
    gov.ca.cwds.data.persistence.cms.Tickle toCreate =
        new gov.ca.cwds.data.persistence.cms.Tickle(id, tickleDomain, "ABC");

    Tickle request = new Tickle(toCreate);
    when(tickleDao.create(any(gov.ca.cwds.data.persistence.cms.Tickle.class))).thenReturn(toCreate);

    Response response = tickleService.create(request);
    assertThat(response.getClass(), is(Tickle.class));
  }

  @SuppressWarnings("javadoc")
  @Test
  public void testCreateReturnsNonNull() throws Exception {
    String id = "AabekZX00F";
    Tickle tickleDomain =
        MAPPER.readValue(fixture("fixtures/domain/legacy/Tickle/valid/valid.json"), Tickle.class);
    gov.ca.cwds.data.persistence.cms.Tickle toCreate =
        new gov.ca.cwds.data.persistence.cms.Tickle(id, tickleDomain, "ABC");

    Tickle request = new Tickle(toCreate);
    when(tickleDao.create(any(gov.ca.cwds.data.persistence.cms.Tickle.class))).thenReturn(toCreate);

    Tickle postedtickle = tickleService.create(request);
    assertThat(postedtickle, is(notNullValue()));
  }

  @Override
  @Test
  public void testCreateReturnsCorrectEntity() throws Exception {
    String id = "AabekZX00F";
    Tickle allegationDomain =
        MAPPER.readValue(fixture("fixtures/domain/legacy/Tickle/valid/valid.json"), Tickle.class);
    gov.ca.cwds.data.persistence.cms.Tickle toCreate =
        new gov.ca.cwds.data.persistence.cms.Tickle(id, allegationDomain, "ABC");

    Tickle request = new Tickle(toCreate);
    when(tickleDao.create(any(gov.ca.cwds.data.persistence.cms.Tickle.class))).thenReturn(toCreate);

    Tickle expected = new Tickle(toCreate);
    Tickle returned = tickleService.create(request);
    assertThat(returned, is(expected));
  }

  @Override
  @Test
  public void testCreateNullIDError() throws Exception {
    try {
      Tickle tickleDomain =
          MAPPER.readValue(fixture("fixtures/domain/legacy/Tickle/valid/valid.json"), Tickle.class);
      gov.ca.cwds.data.persistence.cms.Tickle toCreate =
          new gov.ca.cwds.data.persistence.cms.Tickle(null, tickleDomain, "ABC");

      when(tickleDao.create(any(gov.ca.cwds.data.persistence.cms.Tickle.class)))
          .thenReturn(toCreate);

      Tickle expected = new Tickle(toCreate);
    } catch (ServiceException e) {
      assertEquals("Allegation ID cannot be blank", e.getMessage());
    }

  }

  @Override
  @Test
  public void testCreateBlankIDError() throws Exception {
    try {
      Tickle tickleDomain =
          MAPPER.readValue(fixture("fixtures/domain/legacy/Tickle/valid/valid.json"), Tickle.class);
      gov.ca.cwds.data.persistence.cms.Tickle toCreate =
          new gov.ca.cwds.data.persistence.cms.Tickle("    ", tickleDomain, "ABC");

      when(tickleDao.create(any(gov.ca.cwds.data.persistence.cms.Tickle.class)))
          .thenReturn(toCreate);

      Tickle expected = new Tickle(toCreate);
    } catch (ServiceException e) {
      assertEquals("Allegation ID cannot be blank", e.getMessage());
    }

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
