package gov.ca.cwds.rest.services.contact;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Date;

import javax.persistence.EntityExistsException;

import org.apache.commons.lang3.NotImplementedException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import gov.ca.cwds.data.dao.contact.DeliveredServiceDao;
import gov.ca.cwds.fixture.contacts.DeliveredServiceResourceBuilder;
import gov.ca.cwds.rest.api.contact.DeliveredServiceDomain;
import gov.ca.cwds.rest.filters.TestingRequestExecutionContext;
import gov.ca.cwds.rest.services.ServiceException;

/***
 * 
 * @author CWDS API Team
 *
 */
@SuppressWarnings("javadoc")
public class DeliveredServiceTest {

  private DeliveredService deliveredService;
  private DeliveredServiceDao deliveredServiceDao;

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Before
  public void setup() throws Exception {
    new TestingRequestExecutionContext("0X5");

    deliveredServiceDao = mock(DeliveredServiceDao.class);
    deliveredService = new DeliveredService(deliveredServiceDao);
  }

  // find test
  @Test
  public void findThrowsNotImplementedException() throws Exception {
    thrown.expect(NotImplementedException.class);
    deliveredService.find("string");
  }

  // delete test
  @Test
  public void deleteThrowsNotImplementedException() throws Exception {
    thrown.expect(NotImplementedException.class);
    deliveredService.delete("string");
  }

  // update test
  @Test
  public void updateThrowsNotImplementedException() throws Exception {
    thrown.expect(NotImplementedException.class);
    deliveredService.update("string", null);
  }

  // create test
  @Test
  public void deliveredServiceCreateReturnsPostedClass() throws Exception {
    String id = "5cFeXJN0Dv";
    DeliveredServiceDomain deliveredServiceDomain =
        new DeliveredServiceResourceBuilder().buildDeliveredServiceResource();

    gov.ca.cwds.data.persistence.contact.DeliveredServiceEntity toCreate =
        new gov.ca.cwds.data.persistence.contact.DeliveredServiceEntity(id, deliveredServiceDomain,
            "ABC", new Date());

    DeliveredServiceDomain request = new DeliveredServiceDomain(toCreate);

    when(deliveredServiceDao
        .create(any(gov.ca.cwds.data.persistence.contact.DeliveredServiceEntity.class)))
            .thenReturn(toCreate);

    DeliveredServiceDomain response = deliveredService.create(request);
    assertThat(response.getClass(), is(DeliveredServiceDomain.class));
  }

  @Test
  public void deliveredServiceCreateReturnsNonNull() throws Exception {
    String id = "5cFeXJN0Dv";
    DeliveredServiceDomain deliveredServiceDomain =
        new DeliveredServiceResourceBuilder().buildDeliveredServiceResource();

    gov.ca.cwds.data.persistence.contact.DeliveredServiceEntity toCreate =
        new gov.ca.cwds.data.persistence.contact.DeliveredServiceEntity(id, deliveredServiceDomain,
            "ABC", new Date());

    DeliveredServiceDomain request = new DeliveredServiceDomain(toCreate);
    when(deliveredServiceDao
        .create(any(gov.ca.cwds.data.persistence.contact.DeliveredServiceEntity.class)))
            .thenReturn(toCreate);

    DeliveredServiceDomain postedDeliveredServiceDomain = deliveredService.create(request);
    assertThat(postedDeliveredServiceDomain, is(notNullValue()));
  }

  @Test
  public void deliveredServiceCreateReturnsCorrectEntity() throws Exception {
    String id = "5cFeXJN0Dv";
    DeliveredServiceDomain deliveredServiceDomain =
        new DeliveredServiceResourceBuilder().buildDeliveredServiceResource();

    gov.ca.cwds.data.persistence.contact.DeliveredServiceEntity toCreate =
        new gov.ca.cwds.data.persistence.contact.DeliveredServiceEntity(id, deliveredServiceDomain,
            "ABC", new Date());

    DeliveredServiceDomain request = new DeliveredServiceDomain(toCreate);
    when(deliveredServiceDao
        .create(any(gov.ca.cwds.data.persistence.contact.DeliveredServiceEntity.class)))
            .thenReturn(toCreate);

    DeliveredServiceDomain expected = new DeliveredServiceDomain(toCreate);
    DeliveredServiceDomain returned = deliveredService.create(request);
    assertThat(returned, is(expected));
  }

  @Test
  public void deliveredServiceCreateThrowsEntityExistsException() throws Exception {
    try {
      DeliveredServiceDomain deliveredServiceDomain =
          new DeliveredServiceResourceBuilder().buildDeliveredServiceResource();

      when(deliveredServiceDao.create(any())).thenThrow(EntityExistsException.class);

      deliveredService.create(deliveredServiceDomain);
    } catch (Exception e) {
      assertEquals(e.getClass(), ServiceException.class);
    }
  }

  @Test
  public void deliveredServiceCreateNullIDError() throws Exception {
    try {
      DeliveredServiceDomain deliveredServiceDomain =
          new DeliveredServiceResourceBuilder().buildDeliveredServiceResource();

      gov.ca.cwds.data.persistence.contact.DeliveredServiceEntity toCreate =
          new gov.ca.cwds.data.persistence.contact.DeliveredServiceEntity(null,
              deliveredServiceDomain, "ABC", new Date());

      when(deliveredServiceDao
          .create(any(gov.ca.cwds.data.persistence.contact.DeliveredServiceEntity.class)))
              .thenReturn(toCreate);

      DeliveredServiceDomain expected = new DeliveredServiceDomain(toCreate);
    } catch (ServiceException e) {
      assertEquals("DeliveredService ID cannot be blank", e.getMessage());
    }

  }

  @Test
  public void deliveredServiceCreateBlankIDError() throws Exception {
    try {
      DeliveredServiceDomain deliveredServiceDomain =
          new DeliveredServiceResourceBuilder().buildDeliveredServiceResource();

      gov.ca.cwds.data.persistence.contact.DeliveredServiceEntity toCreate =
          new gov.ca.cwds.data.persistence.contact.DeliveredServiceEntity(" ",
              deliveredServiceDomain, "ABC", new Date());

      when(deliveredServiceDao
          .create(any(gov.ca.cwds.data.persistence.contact.DeliveredServiceEntity.class)))
              .thenReturn(toCreate);

      DeliveredServiceDomain expected = new DeliveredServiceDomain(toCreate);
    } catch (ServiceException e) {
      assertEquals("DeliveredService ID cannot be blank", e.getMessage());
    }

  }

  /*
   * Test for checking the new id Generated for deliveredService
   */
  @Test
  public void createReturnsGeneratedId() throws Exception {
    DeliveredServiceDomain deliveredServiceDomain =
        new DeliveredServiceResourceBuilder().buildDeliveredServiceResource();
    when(deliveredServiceDao
        .create(any(gov.ca.cwds.data.persistence.contact.DeliveredServiceEntity.class)))
            .thenAnswer(new Answer<gov.ca.cwds.data.persistence.contact.DeliveredServiceEntity>() {

              @Override
              public gov.ca.cwds.data.persistence.contact.DeliveredServiceEntity answer(
                  InvocationOnMock invocation) throws Throwable {
                gov.ca.cwds.data.persistence.contact.DeliveredServiceEntity report =
                    (gov.ca.cwds.data.persistence.contact.DeliveredServiceEntity) invocation
                        .getArguments()[0];
                return report;
              }
            });

    DeliveredServiceDomain returned = deliveredService.create(deliveredServiceDomain);
    Assert.assertNotEquals(returned.getId(), deliveredServiceDomain.getId());
  }

}
