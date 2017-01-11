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

import gov.ca.cwds.data.cms.AllegationDao;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.cms.Allegation;
import gov.ca.cwds.rest.api.domain.cms.PostedAllegation;
import gov.ca.cwds.rest.services.ServiceException;
import gov.ca.cwds.rest.services.junit.template.ServiceTestTemplate;
import io.dropwizard.jackson.Jackson;

/**
 * @author CWDS API Team
 *
 */
public class AllegationServiceTest implements ServiceTestTemplate {
  private static final ObjectMapper MAPPER = Jackson.newObjectMapper();
  private AllegationService allegationService;
  private AllegationDao allegationDao;

  @SuppressWarnings("javadoc")
  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Override
  @Before
  public void setup() throws Exception {
    allegationDao = mock(AllegationDao.class);
    allegationService = new AllegationService(allegationDao);
  }

  // find test
  @Override
  @Test
  public void testEntityFindThrowsAssertionError() {
    // expect string type for primary key test
    thrown.expect(AssertionError.class);
    try {
      allegationService.find(1);
    } catch (AssertionError e) {
      assertEquals("Expeceted AssertionError", e.getMessage());
    }
  }

  @Override
  @Test
  public void testEntityFindReturnsCorrectEntity() throws Exception {
    String id = "Aaeae9r0F4";
    Allegation expected = MAPPER
        .readValue(fixture("fixtures/domain/legacy/Allegation/valid/valid.json"), Allegation.class);
    gov.ca.cwds.data.persistence.cms.Allegation allegation =
        new gov.ca.cwds.data.persistence.cms.Allegation(id, expected, "0XA");

    when(allegationDao.find(id)).thenReturn(allegation);
    Allegation found = allegationService.find(id);
    assertThat(found, is(expected));
  }

  @Override
  @Test
  public void testEntityFindReturnsNullWhenNotFound() throws Exception {
    Response found = allegationService.find("ABC1234567");
    assertThat(found, is(nullValue()));
  }

  @Override
  @Test
  // delete test
  public void testEntityDeleteThrowsAssertionError() throws Exception {
    // expect string type for primary key test
    thrown.expect(AssertionError.class);
    try {
      allegationService.delete(123);
    } catch (AssertionError e) {
      assertEquals("Expected AssertionError", e.getMessage());
    }
  }

  @Override
  @Test
  public void testEntityDeleteDelegatesToCrudsService() {
    allegationService.delete("ABC2345678");
    verify(allegationDao, times(1)).delete("ABC2345678");
  }

  @Override
  @Test
  public void testEntityDeleteReturnsNullWhenNotFound() throws Exception {
    Response found = allegationService.delete("ABC1234567");
    assertThat(found, is(nullValue()));
  }

  // update test
  @Override
  @Test
  public void testEntityUpdateThrowsAssertionError() throws Exception {
    // expected string type for primary key test
    thrown.expect(AssertionError.class);
    try {
      allegationService.update("ABC1234567", null);
    } catch (AssertionError e) {
      assertEquals("Expected AssertionError", e.getMessage());
    }
  }

  @Override
  public void testEntityUpdateReturnsPersistent() throws Exception {
    // TODO Auto-generated method stub

  }

  @Override
  @Test
  public void testEntityUpdateReturnsCorrectEntity() throws Exception {
    String id = "Aaeae9r0F4";
    Allegation expected = MAPPER
        .readValue(fixture("fixtures/domain/legacy/Allegation/valid/valid.json"), Allegation.class);

    gov.ca.cwds.data.persistence.cms.Allegation allegation =
        new gov.ca.cwds.data.persistence.cms.Allegation(id, expected, "ABC");

    when(allegationDao.find("ABC1234567")).thenReturn(allegation);
    when(allegationDao.update(any())).thenReturn(allegation);

    Object retval = allegationService.update("ABC1234567", expected);
    assertThat(retval.getClass(), is(Allegation.class));
  }

  @SuppressWarnings("javadoc")
  @Test
  public void testEntityUpdateThrowsExceptionWhenNotFound() throws Exception {
    try {
      Allegation allegationRequest = MAPPER.readValue(
          fixture("fixtures/domain/legacy/Allegation/valid/valid.json"), Allegation.class);

      when(allegationDao.update(any())).thenThrow(EntityNotFoundException.class);

      allegationService.update("ZZZZZZZZZZ", allegationRequest);
    } catch (Exception e) {
      assertEquals(e.getClass(), ServiceException.class);
    }
  }

  // create test
  @Override
  @Test
  public void testEntityCreateReturnsPostedClass() throws Exception {
    String id = "Aaeae9r0F4";
    Allegation allegationDomain = MAPPER
        .readValue(fixture("fixtures/domain/legacy/Allegation/valid/valid.json"), Allegation.class);
    gov.ca.cwds.data.persistence.cms.Allegation toCreate =
        new gov.ca.cwds.data.persistence.cms.Allegation(id, allegationDomain, "ABC");

    Allegation request = new Allegation(toCreate);
    when(allegationDao.create(any(gov.ca.cwds.data.persistence.cms.Allegation.class)))
        .thenReturn(toCreate);

    Response response = allegationService.create(request);
    assertThat(response.getClass(), is(PostedAllegation.class));
  }

  @SuppressWarnings("javadoc")
  @Test
  public void testEntityCreateReturnsNonNull() throws Exception {
    String id = "Aaeae9r0F4";
    Allegation allegationDomain = MAPPER
        .readValue(fixture("fixtures/domain/legacy/Allegation/valid/valid.json"), Allegation.class);
    gov.ca.cwds.data.persistence.cms.Allegation toCreate =
        new gov.ca.cwds.data.persistence.cms.Allegation(id, allegationDomain, "ABC");

    Allegation request = new Allegation(toCreate);
    when(allegationDao.create(any(gov.ca.cwds.data.persistence.cms.Allegation.class)))
        .thenReturn(toCreate);

    PostedAllegation postedAllegation = allegationService.create(request);
    assertThat(postedAllegation, is(notNullValue()));
  }

  @Override
  @Test
  public void testEntityCreateReturnsCorrectEntity() throws Exception {
    String id = "Aaeae9r0F4";
    Allegation allegationDomain = MAPPER
        .readValue(fixture("fixtures/domain/legacy/Allegation/valid/valid.json"), Allegation.class);
    gov.ca.cwds.data.persistence.cms.Allegation toCreate =
        new gov.ca.cwds.data.persistence.cms.Allegation(id, allegationDomain, "ABC");

    Allegation request = new Allegation(toCreate);
    when(allegationDao.create(any(gov.ca.cwds.data.persistence.cms.Allegation.class)))
        .thenReturn(toCreate);

    PostedAllegation expected = new PostedAllegation(toCreate);
    PostedAllegation returned = allegationService.create(request);
    assertThat(returned, is(expected));
  }

  @Override
  @Test
  public void testEntityCreateNullIDError() throws Exception {
    try {
      Allegation allegationDomain = MAPPER.readValue(
          fixture("fixtures/domain/legacy/Allegation/valid/valid.json"), Allegation.class);
      gov.ca.cwds.data.persistence.cms.Allegation toCreate =
          new gov.ca.cwds.data.persistence.cms.Allegation(null, allegationDomain, "ABC");

      when(allegationDao.create(any(gov.ca.cwds.data.persistence.cms.Allegation.class)))
          .thenReturn(toCreate);

      PostedAllegation expected = new PostedAllegation(toCreate);
    } catch (ServiceException e) {
      assertEquals("Allegation ID cannot be blank", e.getMessage());
    }

  }

  @Override
  @Test
  public void testEntityCreateBlankIDError() throws Exception {
    try {
      Allegation allegationDomain = MAPPER.readValue(
          fixture("fixtures/domain/legacy/Allegation/valid/valid.json"), Allegation.class);
      gov.ca.cwds.data.persistence.cms.Allegation toCreate =
          new gov.ca.cwds.data.persistence.cms.Allegation("    ", allegationDomain, "ABC");

      when(allegationDao.create(any(gov.ca.cwds.data.persistence.cms.Allegation.class)))
          .thenReturn(toCreate);

      PostedAllegation expected = new PostedAllegation(toCreate);
    } catch (ServiceException e) {
      assertEquals("Allegation ID cannot be blank", e.getMessage());
    }

  }

  @Override
  public void testEntityCreateThrowsAssertionError() throws Exception {
    // TODO Auto-generated method stub

  }

  @Override
  public void testEntityCreateEmptyError() throws Exception {
    // TODO Auto-generated method stub

  }

  @Override
  public void testEntityFindThrowsNotImplementedException() throws Exception {
    // TODO Auto-generated method stub

  }

  @Override
  public void testDeleteThrowsNotImplementedException() throws Exception {
    // TODO Auto-generated method stub

  }

  @Override
  public void testEntityUpdateThrowsNotImplementedException() throws Exception {
    // TODO Auto-generated method stub

  }

  @Override
  public void testEntityCreateThrowsNotImplementedException() throws Exception {
    // TODO Auto-generated method stub

  }
}
