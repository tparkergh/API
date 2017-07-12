package gov.ca.cwds.rest.services.cms;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gov.ca.cwds.data.cms.ClientCollateralDao;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.cms.ClientCollateral;
import gov.ca.cwds.rest.api.domain.cms.PostedClientCollateral;
import gov.ca.cwds.rest.services.ServiceException;

import org.apache.commons.lang3.NotImplementedException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * @author CWDS API Team
 *
 */
public class ClientCollateralServiceTest {
  private ClientCollateralService clientCollateralService;
  private ClientCollateralDao clientCollateralDao;
  private StaffPersonIdRetriever staffPersonIdRetriever;

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @SuppressWarnings("javadoc")
  @Before
  public void setup() throws Exception {
    clientCollateralDao = mock(ClientCollateralDao.class);
    staffPersonIdRetriever = mock(StaffPersonIdRetriever.class);

    clientCollateralService =
        new ClientCollateralService(clientCollateralDao, staffPersonIdRetriever);

  }

  // find test
  @SuppressWarnings("javadoc")
  @Test
  public void findThrowsAssertionError() {
    thrown.expect(AssertionError.class);
    try {
      clientCollateralService.find(new Long(1));
    } catch (AssertionError e) {
      assertEquals("Expected AssertionError", e.getMessage());
    }
  }

  @SuppressWarnings("javadoc")
  @Test
  public void findReturnsCorrectClientCollateralWhenFound() throws Exception {
    String id = "ABC";
    PostedClientCollateral expected = validClientCollateralDomainObject();
    gov.ca.cwds.data.persistence.cms.ClientCollateral clientCollateral =
        new gov.ca.cwds.data.persistence.cms.ClientCollateral(id, expected, "0X5");

    when(clientCollateralDao.find(id)).thenReturn(clientCollateral);

    ClientCollateral found = clientCollateralService.find(id);

    assertThat(found, is(expected));
  }

  @SuppressWarnings("javadoc")
  @Test
  public void findReturnsNullWhenNotFound() throws Exception {
    Response found = clientCollateralService.find("0XA");
    assertThat(found, is(nullValue()));
  }

  // Delete Tests
  @SuppressWarnings("javadoc")
  @Test
  public void deleteThrowsNotImplementedException() throws Exception {
    thrown.expect(NotImplementedException.class);
    clientCollateralService.delete("string");
  }

  @SuppressWarnings("javadoc")
  @Test
  public void deleteThrowsAssertionError() throws Exception {
    thrown.expect(AssertionError.class);
    try {
      clientCollateralService.delete(new Long(1));
    } catch (AssertionError e) {
      assertEquals("Expected AssertionError", e.getMessage());
    }
  }

  // create test
  @SuppressWarnings("javadoc")
  @Test
  public void createThrowsAssertionError() throws Exception {
    thrown.expect(AssertionError.class);
    try {
      clientCollateralService.create(null);
      Assert.fail("Expected AssertionError");
    } catch (AssertionError e) {
      assertEquals("Expected AssertionError", e.getMessage());
    }
  }

  @SuppressWarnings("javadoc")
  @Test
  public void createReturnsPostedClientCollateralClass() throws Exception {
    String id = "ABC";
    ClientCollateral clientCollateralDomain = validClientCollateralDomainObject();
    gov.ca.cwds.data.persistence.cms.ClientCollateral toCreate =
        new gov.ca.cwds.data.persistence.cms.ClientCollateral(id, clientCollateralDomain,
            "2017-01-07");

    ClientCollateral request = new ClientCollateral(toCreate);

    when(clientCollateralDao.create(any(gov.ca.cwds.data.persistence.cms.ClientCollateral.class)))
        .thenReturn(toCreate);

    Response response = clientCollateralService.create(request);

    assertThat(response.getClass(), is(PostedClientCollateral.class));
  }

  @SuppressWarnings("javadoc")
  @Test
  public void createReturnsNonNull() throws Exception {
    String id = "ABC";
    ClientCollateral clientCollateralDomain = validClientCollateralDomainObject();
    gov.ca.cwds.data.persistence.cms.ClientCollateral toCreate =
        new gov.ca.cwds.data.persistence.cms.ClientCollateral(id, clientCollateralDomain,
            "2017-01-07");

    ClientCollateral request = new ClientCollateral(toCreate);

    when(clientCollateralDao.create(any(gov.ca.cwds.data.persistence.cms.ClientCollateral.class)))
        .thenReturn(toCreate);

    PostedClientCollateral postedClientCollateral = clientCollateralService.create(request);

    assertThat(postedClientCollateral, is(notNullValue()));
  }

  @SuppressWarnings("javadoc")
  @Test
  public void createReturnsCorrectPostedPerson() throws Exception {
    String id = "ABC";
    ClientCollateral clientCollateralDomain = validClientCollateralDomainObject();
    gov.ca.cwds.data.persistence.cms.ClientCollateral toCreate =
        new gov.ca.cwds.data.persistence.cms.ClientCollateral(id, clientCollateralDomain,
            "2017-01-07");

    ClientCollateral request = new ClientCollateral(toCreate);

    when(clientCollateralDao.create(any(gov.ca.cwds.data.persistence.cms.ClientCollateral.class)))
        .thenReturn(toCreate);

    PostedClientCollateral expected = new PostedClientCollateral(toCreate);

    PostedClientCollateral returned = clientCollateralService.create(request);

    assertThat(returned, is(expected));
  }

  @SuppressWarnings("javadoc")
  @Test
  public void failsWhenPostedClientCollateralIdBlank() throws Exception {
    try {
      ClientCollateral clientCollateralDomain = validClientCollateralDomainObject();
      gov.ca.cwds.data.persistence.cms.ClientCollateral toCreate =
          new gov.ca.cwds.data.persistence.cms.ClientCollateral("   ", clientCollateralDomain,
              "2017-01-07");

      when(clientCollateralDao.create(any(gov.ca.cwds.data.persistence.cms.ClientCollateral.class)))
          .thenReturn(toCreate);

      new PostedClientCollateral(toCreate);

      Assert.fail("Expected AssertionError not thrown");
    } catch (ServiceException e) {
      assertEquals("ClientCollateral ID cannot be empty", e.getMessage());
    }
  }

  @SuppressWarnings("javadoc")
  @Test
  public void failsWhenPostedClientCollateralIdNull() throws Exception {
    try {
      ClientCollateral clientCollateralDomain = validClientCollateralDomainObject();
      gov.ca.cwds.data.persistence.cms.ClientCollateral toCreate =
          new gov.ca.cwds.data.persistence.cms.ClientCollateral(null, clientCollateralDomain,
              "2017-01-07");

      when(clientCollateralDao.create(any(gov.ca.cwds.data.persistence.cms.ClientCollateral.class)))
          .thenReturn(toCreate);

      new PostedClientCollateral(toCreate);

      Assert.fail("Expected AssertionError not thrown");
    } catch (ServiceException e) {
      assertEquals("ClientCollateral ID cannot be empty", e.getMessage());
    }
  }

  @SuppressWarnings("javadoc")
  @Test
  public void failsWhenPostedClientCollateralIdEmpty() throws Exception {
    try {
      ClientCollateral clientCollateralDomain = validClientCollateralDomainObject();
      gov.ca.cwds.data.persistence.cms.ClientCollateral toCreate =
          new gov.ca.cwds.data.persistence.cms.ClientCollateral("", clientCollateralDomain,
              "2017-01-07");

      when(clientCollateralDao.create(any(gov.ca.cwds.data.persistence.cms.ClientCollateral.class)))
          .thenReturn(toCreate);

      new PostedClientCollateral(toCreate);

      Assert.fail("Expected AssertionError not thrown");
    } catch (ServiceException e) {
      assertEquals("ClientCollateral ID cannot be empty", e.getMessage());
    }
  }

  public PostedClientCollateral validClientCollateralDomainObject() {
    ClientCollateral clientCollateral =
        new ClientCollateral("Y", (short) 512, "comment", "CLIENTID", "COLLID");
    return new PostedClientCollateral(clientCollateral, "ABC");
  }
}
