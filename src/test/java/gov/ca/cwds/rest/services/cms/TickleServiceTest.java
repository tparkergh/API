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

import java.util.Date;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.fasterxml.jackson.databind.ObjectMapper;

import gov.ca.cwds.data.cms.TickleDao;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.cms.Tickle;
import gov.ca.cwds.rest.filters.TestingRequestExecutionContext;
import gov.ca.cwds.rest.services.ServiceException;
import io.dropwizard.jackson.Jackson;

/**
 * @author CWDS API Team
 *
 */
@SuppressWarnings("javadoc")
public class TickleServiceTest {
  private static final ObjectMapper MAPPER = Jackson.newObjectMapper();
  private TickleService tickleService;
  private TickleDao tickleDao;

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Before
  public void setup() throws Exception {
    new TestingRequestExecutionContext("0X5");
    tickleDao = mock(TickleDao.class);
    tickleService = new TickleService(tickleDao);

  }

  // find test
  @Test
  public void tickleServiceFindReturnsCorrectEntity() throws Exception {
    String id = "AabekZX00F";
    Tickle expected =
        MAPPER.readValue(fixture("fixtures/domain/legacy/Tickle/valid/valid.json"), Tickle.class);
    gov.ca.cwds.data.persistence.cms.Tickle tickle =
        new gov.ca.cwds.data.persistence.cms.Tickle(id, expected, "0XA", new Date());

    when(tickleDao.find(id)).thenReturn(tickle);
    Tickle found = tickleService.find(id);
    assertThat(found, is(expected));
  }

  @Test
  public void tickleServiceFindReturnsNullWhenNotFound() throws Exception {
    Response found = tickleService.find("ABC1234567");
    assertThat(found, is(nullValue()));
  }

  // delete test
  @Test
  public void tickleServiceDeleteDelegatesToCrudsService() {
    tickleService.delete("ABC2345678");
    verify(tickleDao, times(1)).delete("ABC2345678");
  }

  @Test
  public void tickleServiceDeleteReturnsNullWhenNotFound() throws Exception {
    Response found = tickleService.delete("ABC1234567");
    assertThat(found, is(nullValue()));
  }

  @Test
  public void tickleServiceDeleteReturnsNotNull() throws Exception {
    String id = "AabekZX00F";
    Tickle expected =
        MAPPER.readValue(fixture("fixtures/domain/legacy/Tickle/valid/valid.json"), Tickle.class);
    gov.ca.cwds.data.persistence.cms.Tickle tickle =
        new gov.ca.cwds.data.persistence.cms.Tickle(id, expected, "0XA", new Date());

    when(tickleDao.delete(id)).thenReturn(tickle);
    Tickle found = tickleService.delete(id);
    assertThat(found, is(expected));
  }

  // update test
  @Test
  public void tickleServiceUpdateReturnsCorrectEntity() throws Exception {
    String id = "AabekZX00F";
    Tickle expected =
        MAPPER.readValue(fixture("fixtures/domain/legacy/Tickle/valid/valid.json"), Tickle.class);

    gov.ca.cwds.data.persistence.cms.Tickle tickle =
        new gov.ca.cwds.data.persistence.cms.Tickle(id, expected, "ABC", new Date());

    when(tickleDao.find("ABC1234567")).thenReturn(tickle);
    when(tickleDao.update(any())).thenReturn(tickle);

    Object retval = tickleService.update("ABC1234567", expected);
    assertThat(retval.getClass(), is(Tickle.class));
  }

  @Test
  public void tickleServiceUpdateThrowsExceptionWhenNotFound() throws Exception {
    try {
      Tickle tickleRequest =
          MAPPER.readValue(fixture("fixtures/domain/legacy/Tickle/valid/valid.json"), Tickle.class);

      when(tickleDao.update(any())).thenThrow(EntityNotFoundException.class);

      tickleService.update("ZZZZZZZZZZ", tickleRequest);
    } catch (Exception e) {
      assertEquals(e.getClass(), ServiceException.class);
    }
  }

  // create test
  @Test
  public void tickleServiceCreateReturnsPostedClass() throws Exception {
    String id = "AabekZX00F";
    Tickle tickleDomain =
        MAPPER.readValue(fixture("fixtures/domain/legacy/Tickle/valid/valid.json"), Tickle.class);
    gov.ca.cwds.data.persistence.cms.Tickle toCreate =
        new gov.ca.cwds.data.persistence.cms.Tickle(id, tickleDomain, "ABC", new Date());

    Tickle request = new Tickle(toCreate);
    when(tickleDao.create(any(gov.ca.cwds.data.persistence.cms.Tickle.class))).thenReturn(toCreate);

    Response response = tickleService.create(request);
    assertThat(response.getClass(), is(Tickle.class));
  }

  @Test
  public void tickleServiceCreateReturnsNonNull() throws Exception {
    String id = "AabekZX00F";
    Tickle tickleDomain =
        MAPPER.readValue(fixture("fixtures/domain/legacy/Tickle/valid/valid.json"), Tickle.class);
    gov.ca.cwds.data.persistence.cms.Tickle toCreate =
        new gov.ca.cwds.data.persistence.cms.Tickle(id, tickleDomain, "ABC", new Date());

    Tickle request = new Tickle(toCreate);
    when(tickleDao.create(any(gov.ca.cwds.data.persistence.cms.Tickle.class))).thenReturn(toCreate);

    Tickle postedtickle = tickleService.create(request);
    assertThat(postedtickle, is(notNullValue()));
  }

  @Test
  public void tickleServiceCreateReturnsCorrectEntity() throws Exception {
    String id = "AabekZX00F";
    Tickle tickleDomain =
        MAPPER.readValue(fixture("fixtures/domain/legacy/Tickle/valid/valid.json"), Tickle.class);
    gov.ca.cwds.data.persistence.cms.Tickle toCreate =
        new gov.ca.cwds.data.persistence.cms.Tickle(id, tickleDomain, "ABC", new Date());

    Tickle request = new Tickle(toCreate);
    when(tickleDao.create(any(gov.ca.cwds.data.persistence.cms.Tickle.class))).thenReturn(toCreate);

    Tickle expected = new Tickle(toCreate);
    Tickle returned = tickleService.create(request);
    assertThat(returned, is(expected));
  }

  @Test
  public void tickleServiceCreateThrowsEntityExistsException() throws Exception {
    try {
      Tickle tickleDomain =
          MAPPER.readValue(fixture("fixtures/domain/legacy/Tickle/valid/valid.json"), Tickle.class);

      when(tickleDao.create(any())).thenThrow(EntityExistsException.class);

      tickleService.create(tickleDomain);
    } catch (Exception e) {
      assertEquals(e.getClass(), ServiceException.class);
    }
  }

  @Test
  public void tickleServiceCreateNullIDError() throws Exception {
    try {
      Tickle tickleDomain =
          MAPPER.readValue(fixture("fixtures/domain/legacy/Tickle/valid/valid.json"), Tickle.class);
      gov.ca.cwds.data.persistence.cms.Tickle toCreate =
          new gov.ca.cwds.data.persistence.cms.Tickle(null, tickleDomain, "ABC", new Date());

      when(tickleDao.create(any(gov.ca.cwds.data.persistence.cms.Tickle.class)))
          .thenReturn(toCreate);

      Tickle expected = new Tickle(toCreate);
    } catch (ServiceException e) {
      assertEquals("Tickle ID cannot be blank", e.getMessage());
    }

  }

  @Test
  public void tickleServiceCreateBlankIDError() throws Exception {
    try {
      Tickle tickleDomain =
          MAPPER.readValue(fixture("fixtures/domain/legacy/Tickle/valid/valid.json"), Tickle.class);
      gov.ca.cwds.data.persistence.cms.Tickle toCreate =
          new gov.ca.cwds.data.persistence.cms.Tickle("    ", tickleDomain, "ABC", new Date());

      when(tickleDao.create(any(gov.ca.cwds.data.persistence.cms.Tickle.class)))
          .thenReturn(toCreate);

      Tickle expected = new Tickle(toCreate);
    } catch (ServiceException e) {
      assertEquals("Tickle ID cannot be blank", e.getMessage());
    }

  }

}
