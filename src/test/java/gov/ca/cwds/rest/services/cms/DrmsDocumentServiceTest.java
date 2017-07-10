package gov.ca.cwds.rest.services.cms;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.fasterxml.jackson.databind.ObjectMapper;

import gov.ca.cwds.data.cms.DrmsDocumentDao;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.cms.DrmsDocument;
import gov.ca.cwds.rest.api.domain.cms.PostedDrmsDocument;
import gov.ca.cwds.rest.services.ServiceException;
import io.dropwizard.jackson.Jackson;

/**
 * @author CWDS API Team
 *
 */
public class DrmsDocumentServiceTest {
  private static final ObjectMapper MAPPER = Jackson.newObjectMapper();
  private DrmsDocumentService drmsDocumentService;
  private DrmsDocumentDao drmsDocumentDao;
  private StaffPersonIdRetriever staffPersonIdRetriever;

  @SuppressWarnings("javadoc")
  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @SuppressWarnings("javadoc")
  @Before
  public void setup() throws Exception {
    drmsDocumentDao = mock(DrmsDocumentDao.class);
    staffPersonIdRetriever = mock(StaffPersonIdRetriever.class);
    drmsDocumentService = new DrmsDocumentService(drmsDocumentDao, staffPersonIdRetriever);
  }


  @SuppressWarnings("javadoc")
  @Test
  public void createReturnsPostedClass() throws Exception {
    String id = "KWV5gl90X5";
    DrmsDocument drmsDocumentDomain = MAPPER.readValue(
        fixture("fixtures/domain/legacy/DrmsDocument/valid/valid.json"), DrmsDocument.class);
    gov.ca.cwds.data.persistence.cms.DrmsDocument toCreate =
        new gov.ca.cwds.data.persistence.cms.DrmsDocument(id, drmsDocumentDomain, "ABC");

    DrmsDocument request = new DrmsDocument(toCreate);
    when(drmsDocumentDao.create(any(gov.ca.cwds.data.persistence.cms.DrmsDocument.class)))
        .thenReturn(toCreate);

    Response response = drmsDocumentService.create(request);
    assertThat(response.getClass(), is(PostedDrmsDocument.class));
  }

  @SuppressWarnings("javadoc")
  @Test
  public void testCreateReturnsNonNull() throws Exception {
    String id = "KWV5gl90X5";
    DrmsDocument drmsDocumentDomain = MAPPER.readValue(
        fixture("fixtures/domain/legacy/DrmsDocument/valid/valid.json"), DrmsDocument.class);
    gov.ca.cwds.data.persistence.cms.DrmsDocument toCreate =
        new gov.ca.cwds.data.persistence.cms.DrmsDocument(id, drmsDocumentDomain, "ABC");

    DrmsDocument request = new DrmsDocument(toCreate);
    when(drmsDocumentDao.create(any(gov.ca.cwds.data.persistence.cms.DrmsDocument.class)))
        .thenReturn(toCreate);

    PostedDrmsDocument postedDrmsDocument = drmsDocumentService.create(request);
    assertThat(postedDrmsDocument, is(notNullValue()));
  }

  @SuppressWarnings("javadoc")
  @Test
  public void testCreateReturnsCorrectEntity() throws Exception {
    String id = "KWV5gl90X5";
    DrmsDocument drmsDocumentDomain = MAPPER.readValue(
        fixture("fixtures/domain/legacy/DrmsDocument/valid/valid.json"), DrmsDocument.class);
    gov.ca.cwds.data.persistence.cms.DrmsDocument toCreate =
        new gov.ca.cwds.data.persistence.cms.DrmsDocument(id, drmsDocumentDomain, "ABC");

    DrmsDocument request = new DrmsDocument(toCreate);
    when(drmsDocumentDao.create(any(gov.ca.cwds.data.persistence.cms.DrmsDocument.class)))
        .thenReturn(toCreate);

    PostedDrmsDocument expected = new PostedDrmsDocument(toCreate);
    PostedDrmsDocument returned = drmsDocumentService.create(request);
    assertThat(returned, is(expected));
  }

  @SuppressWarnings("javadoc")
  @Test
  public void testCreateNullIDError() throws Exception {
    try {
      DrmsDocument drmsDocumentDomain = MAPPER.readValue(
          fixture("fixtures/domain/legacy/DrmsDocument/valid/valid.json"), DrmsDocument.class);
      gov.ca.cwds.data.persistence.cms.DrmsDocument toCreate =
          new gov.ca.cwds.data.persistence.cms.DrmsDocument(null, drmsDocumentDomain, "ABC");

      when(drmsDocumentDao.create(any(gov.ca.cwds.data.persistence.cms.DrmsDocument.class)))
          .thenReturn(toCreate);

      PostedDrmsDocument expected = new PostedDrmsDocument(toCreate);
    } catch (ServiceException e) {
      assertEquals("drmsDocument ID cannot be blank", e.getMessage());
    }
  }

  @SuppressWarnings("javadoc")
  @Test
  public void testCreateBlankIDError() throws Exception {
    try {
      DrmsDocument drmsDocumentDomain = MAPPER.readValue(
          fixture("fixtures/domain/legacy/DrmsDocument/valid/valid.json"), DrmsDocument.class);
      gov.ca.cwds.data.persistence.cms.DrmsDocument toCreate =
          new gov.ca.cwds.data.persistence.cms.DrmsDocument("  ", drmsDocumentDomain, "ABC");

      when(drmsDocumentDao.create(any(gov.ca.cwds.data.persistence.cms.DrmsDocument.class)))
          .thenReturn(toCreate);

      PostedDrmsDocument expected = new PostedDrmsDocument(toCreate);
    } catch (ServiceException e) {
      assertEquals("drmsDocument ID cannot be blank", e.getMessage());
    }
  }

  /*
   * Test for checking the new Allegation Id generated and lenght is 10
   */
  @SuppressWarnings("javadoc")
  @Test
  public void createReturnsGeneratedId() throws Exception {
    DrmsDocument drmsDocumentDomain = MAPPER.readValue(
        fixture("fixtures/domain/legacy/DrmsDocument/valid/valid.json"), DrmsDocument.class);
    when(drmsDocumentDao.create(any(gov.ca.cwds.data.persistence.cms.DrmsDocument.class)))
        .thenAnswer(new Answer<gov.ca.cwds.data.persistence.cms.DrmsDocument>() {

          @Override
          public gov.ca.cwds.data.persistence.cms.DrmsDocument answer(InvocationOnMock invocation)
              throws Throwable {
            gov.ca.cwds.data.persistence.cms.DrmsDocument report =
                (gov.ca.cwds.data.persistence.cms.DrmsDocument) invocation.getArguments()[0];
            return report;
          }
        });

    PostedDrmsDocument returned = drmsDocumentService.create(drmsDocumentDomain);
    assertEquals(returned.getId().length(), 10);
    PostedDrmsDocument newReturned = drmsDocumentService.create(drmsDocumentDomain);
    Assert.assertNotEquals(returned.getId(), newReturned.getId());
  }

}
