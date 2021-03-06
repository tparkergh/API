package gov.ca.cwds.rest.services.cms;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import gov.ca.cwds.data.cms.ChildClientDao;
import gov.ca.cwds.fixture.ChildClientResourceBuilder;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.cms.ChildClient;
import gov.ca.cwds.rest.services.ServiceException;
import gov.ca.cwds.rest.services.referentialintegrity.RIChildClient;

/**
 * @author CWDS API Team
 *
 */
public class ChildClientServiceTest {
  private ChildClientService childClientService;
  private ChildClientDao childClientDao;
  private StaffPersonIdRetriever staffPersonIdRetriever;
  private RIChildClient ri;

  @SuppressWarnings("javadoc")
  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @SuppressWarnings("javadoc")
  @Before
  public void setup() throws Exception {
    childClientDao = mock(ChildClientDao.class);
    staffPersonIdRetriever = mock(StaffPersonIdRetriever.class);
    ri = mock(RIChildClient.class);
    childClientService = new ChildClientService(childClientDao, staffPersonIdRetriever, ri);
  }


  // find test
  @SuppressWarnings("javadoc")
  @Test
  public void findReturnsChildClientReportWhenFound() throws Exception {
    ChildClient expected = new ChildClientResourceBuilder().buildChildClient();

    gov.ca.cwds.data.persistence.cms.ChildClient childCleint =
        new gov.ca.cwds.data.persistence.cms.ChildClient(expected.getVictimClientId(), expected,
            "0X5");
    when(childClientDao.find(eq(expected.getVictimClientId()))).thenReturn(childCleint);
    ChildClient found = childClientService.find("ABC1234567");
    assertThat(found, is(expected));
  }

  @SuppressWarnings("javadoc")
  @Test
  public void findReturnsNullWhenNotFound() throws Exception {
    Response found = childClientService.find("0X51234567");
    assertThat(found, is(nullValue()));
  }

  // delete test
  @SuppressWarnings("javadoc")
  @Test
  public void deleteDelegatesToCrudsService() {
    childClientService.delete("ABC2345678");
    verify(childClientDao, times(1)).delete("ABC2345678");
  }

  @SuppressWarnings("javadoc")
  @Test
  public void deleteReturnsNullWhenNotFount() throws Exception {
    Response found = childClientService.delete("ABC1234567");
    assertThat(found, is(nullValue()));
  }

  @SuppressWarnings("javadoc")
  @Test
  public void childClientServiceDeleteReturnsNotNull() throws Exception {
    String id = "ABC1234567";
    ChildClient expected = new ChildClientResourceBuilder().buildChildClient();
    gov.ca.cwds.data.persistence.cms.ChildClient childClient =
        new gov.ca.cwds.data.persistence.cms.ChildClient(id, expected, "0XA");

    when(childClientDao.delete(id)).thenReturn(childClient);
    ChildClient found = childClientService.delete(id);
    assertThat(found, is(expected));
  }

  // update test
  @SuppressWarnings("javadoc")
  @Test
  public void updateReturnsChildClientResponseOnSuccess() throws Exception {
    ChildClient expected = new ChildClientResourceBuilder().buildChildClient();

    gov.ca.cwds.data.persistence.cms.ChildClient childClient =
        new gov.ca.cwds.data.persistence.cms.ChildClient(expected.getVictimClientId(), expected,
            "ABC");

    when(childClientDao.find("ABC1234567")).thenReturn(childClient);
    when(childClientDao.update(any())).thenReturn(childClient);
    Object retval = childClientService.update("ABC1234567", expected);
    assertThat(retval.getClass(), is(ChildClient.class));
  }

  @SuppressWarnings("javadoc")
  @Test
  public void updateReturnsCorrectChildClientOnSuccess() throws Exception {
    ChildClient childClientRequest = new ChildClientResourceBuilder().buildChildClient();

    gov.ca.cwds.data.persistence.cms.ChildClient childClient =
        new gov.ca.cwds.data.persistence.cms.ChildClient(childClientRequest.getVictimClientId(),
            childClientRequest, "ABC");

    when(childClientDao.find("ABC1234567")).thenReturn(childClient);
    when(childClientDao.update(any())).thenReturn(childClient);

    ChildClient expected = new ChildClient(childClient);
    ChildClient updated = childClientService.update("ABC1234567", expected);
    assertThat(updated, is(expected));
  }

  @SuppressWarnings("javadoc")
  @Test
  public void updateThrowsExceptionWhenChildClientNotFound() throws Exception {

    try {
      ChildClient childClientRequest = new ChildClientResourceBuilder().buildChildClient();

      when(childClientDao.update(any())).thenThrow(EntityNotFoundException.class);

      childClientService.update("ZZZ1234567", childClientRequest);
    } catch (Exception e) {
      assertEquals(e.getClass(), ServiceException.class);
    }
  }

  // create test
  @SuppressWarnings("javadoc")
  @Test
  public void createReturnsPostedChildClientClass() throws Exception {
    ChildClient childClientDomain = new ChildClientResourceBuilder().buildChildClient();

    gov.ca.cwds.data.persistence.cms.ChildClient toCreate =
        new gov.ca.cwds.data.persistence.cms.ChildClient(childClientDomain.getVictimClientId(),
            childClientDomain, "ABC");

    ChildClient request = new ChildClient(toCreate);
    when(childClientDao.create(any(gov.ca.cwds.data.persistence.cms.ChildClient.class)))
        .thenReturn(toCreate);

    Response response = childClientService.create(request);
    assertThat(response.getClass(), is(ChildClient.class));
  }

  @SuppressWarnings("javadoc")
  @Test(expected = ServiceException.class)
  public void childClientServiceCreateThrowsServiceExceptionWhenVictimIdNull() throws Exception {
    ChildClient childClientRequest =
        new ChildClientResourceBuilder().setVictimClientId(null).buildChildClient();

    childClientService.create(childClientRequest);
  }


  @SuppressWarnings("javadoc")
  @Test
  public void childClientServiceCreateThrowsEntityExistsException() throws Exception {
    try {
      ChildClient childClientRequest = new ChildClientResourceBuilder().buildChildClient();

      when(childClientDao.create(any())).thenThrow(EntityExistsException.class);
      childClientService.create(childClientRequest);
    } catch (Exception e) {
      assertEquals(e.getClass(), ServiceException.class);
    }
  }

  @SuppressWarnings("javadoc")
  @Test
  public void createReturnsNonNull() throws Exception {
    ChildClient childClientDomain = new ChildClientResourceBuilder().buildChildClient();

    gov.ca.cwds.data.persistence.cms.ChildClient toCreate =
        new gov.ca.cwds.data.persistence.cms.ChildClient(childClientDomain.getVictimClientId(),
            childClientDomain, "ABC");

    ChildClient request = new ChildClient(toCreate);
    when(childClientDao.create(any(gov.ca.cwds.data.persistence.cms.ChildClient.class)))
        .thenReturn(toCreate);

    ChildClient postedChildClient = childClientService.create(request);
    assertThat(postedChildClient, is(notNullValue()));
  }

  @SuppressWarnings("javadoc")
  @Test
  public void createReturnsCorrectPostedChildClient() throws Exception {
    ChildClient childClientDomain = new ChildClientResourceBuilder().buildChildClient();

    gov.ca.cwds.data.persistence.cms.ChildClient toCreate =
        new gov.ca.cwds.data.persistence.cms.ChildClient(childClientDomain.getVictimClientId(),
            childClientDomain, "ABC");

    ChildClient request = new ChildClient(toCreate);

    when(childClientDao.create(any(gov.ca.cwds.data.persistence.cms.ChildClient.class)))
        .thenReturn(toCreate);

    ChildClient expected = new ChildClient(toCreate);
    ChildClient returned = childClientService.create(request);
    assertThat(returned, is(expected));
  }

}
