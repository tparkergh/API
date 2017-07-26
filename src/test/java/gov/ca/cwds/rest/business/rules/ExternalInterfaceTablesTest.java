package gov.ca.cwds.rest.business.rules;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.fasterxml.jackson.databind.ObjectMapper;

import gov.ca.cwds.data.DaoException;
import gov.ca.cwds.data.cms.ExternalInterfaceDao;
import gov.ca.cwds.data.persistence.cms.ExternalInterface;
import gov.ca.cwds.data.persistence.cms.SystemCodeTestHarness;
import gov.ca.cwds.fixture.AssignmentResourceBuilder;
import gov.ca.cwds.fixture.ClientResourceBuilder;
import gov.ca.cwds.rest.api.domain.cms.Assignment;
import gov.ca.cwds.rest.api.domain.cms.Client;
import gov.ca.cwds.rest.filters.TestApiRequestCommonInfo;
import gov.ca.cwds.rest.services.ServiceException;

/**
 * @author CWDS API Team
 *
 */
public class ExternalInterfaceTablesTest {

  private static final ObjectMapper MAPPER = SystemCodeTestHarness.MAPPER;

  private ExternalInterfaceDao externalInterfaceDao;
  private ExternalInterfaceTables externalInterfaceTables;

  private TestApiRequestCommonInfo testApiRequestCommonInfo =
      new TestApiRequestCommonInfo("CWDSTEST");

  private static ExternalInterface externalInterface;

  @SuppressWarnings("javadoc")
  @Rule
  public ExpectedException thrown = ExpectedException.none();

  /**
   * @throws Exception
   */
  @Before
  public void setup() throws Exception {
    externalInterfaceDao = mock(ExternalInterfaceDao.class);
    externalInterfaceTables = new ExternalInterfaceTables(externalInterfaceDao);
  }

  /**
   * @throws Exception - Exception
   */
  @Test
  public void testForClientExternalInterfaceCreated() throws Exception {
    Client clientDomain = new ClientResourceBuilder().build();
    gov.ca.cwds.data.persistence.cms.Client client =
        new gov.ca.cwds.data.persistence.cms.Client("ABC1234567", clientDomain, "0X5");
    externalInterfaceTables.createExtInterClient(client, "N");
    verify(externalInterfaceDao, times(1)).create(any());

  }

  /**
   * @throws Exception - Exception
   */
  @Test(expected = DaoException.class)
  public void testForClientExternalInterfaceThrowsDaoException() throws Exception {
    when(externalInterfaceDao.create(any())).thenThrow(new ServiceException());
    Client clientDomain = new ClientResourceBuilder().build();
    gov.ca.cwds.data.persistence.cms.Client client =
        new gov.ca.cwds.data.persistence.cms.Client("ABC1234567", clientDomain, "0X5");
    externalInterfaceTables.createExtInterClient(client, "N");

  }

  /**
   * @throws Exception - Exception
   */
  @Test
  public void testForCreateClientExternalInterface() throws Exception {
    Client clientDomain = new ClientResourceBuilder().build();
    gov.ca.cwds.data.persistence.cms.Client client =
        new gov.ca.cwds.data.persistence.cms.Client("ABC1234567", clientDomain, "0X5");

    when(externalInterfaceDao.create(any(ExternalInterface.class)))
        .thenAnswer(new Answer<ExternalInterface>() {
          @Override
          public ExternalInterface answer(InvocationOnMock invocation) throws Throwable {

            ExternalInterface report = (ExternalInterface) invocation.getArguments()[0];
            externalInterface = report;
            return report;
          }
        });
    externalInterfaceTables.createExtInterClient(client, "N");
    assertThat(externalInterface.getSequenceNumber(), is(equalTo(1)));
    assertThat(externalInterface.getPrimaryKey1(), is(equalTo("ABC1234567")));
    assertThat(externalInterface.getTableName(), is(equalTo("CLIENT_T")));
    assertThat(externalInterface.getOperationType(), is(equalTo("N")));
    assertThat(externalInterface.getLogonUserId(), is(equalTo("CWDSTEST")));

  }

  /**
   * @throws Exception - Exception
   */
  @Test
  public void testForCreateMultipleClientExternalInterface() throws Exception {
    Client clientDomain = new ClientResourceBuilder().build();
    gov.ca.cwds.data.persistence.cms.Client client1 =
        new gov.ca.cwds.data.persistence.cms.Client("VICTIM1111", clientDomain, "0X5");
    gov.ca.cwds.data.persistence.cms.Client client2 =
        new gov.ca.cwds.data.persistence.cms.Client("VICTIM2222", clientDomain, "0X5");
    gov.ca.cwds.data.persistence.cms.Client client3 =
        new gov.ca.cwds.data.persistence.cms.Client("PERPET1111", clientDomain, "0X5");

    when(externalInterfaceDao.create(any(ExternalInterface.class)))
        .thenAnswer(new Answer<ExternalInterface>() {
          @Override
          public ExternalInterface answer(InvocationOnMock invocation) throws Throwable {

            ExternalInterface report = (ExternalInterface) invocation.getArguments()[0];
            externalInterface = report;
            return report;
          }
        });
    externalInterfaceTables.createExtInterClient(client1, "N");
    assertThat(externalInterface.getSequenceNumber(), is(equalTo(1)));
    assertThat(externalInterface.getPrimaryKey1(), is(equalTo("VICTIM1111")));
    assertThat(externalInterface.getTableName(), is(equalTo("CLIENT_T")));
    assertThat(externalInterface.getOperationType(), is(equalTo("N")));
    assertThat(externalInterface.getLogonUserId(), is(equalTo("CWDSTEST")));
    externalInterfaceTables.createExtInterClient(client2, "C");
    assertThat(externalInterface.getSequenceNumber(), is(equalTo(2)));
    assertThat(externalInterface.getPrimaryKey1(), is(equalTo("VICTIM2222")));
    assertThat(externalInterface.getTableName(), is(equalTo("CLIENT_T")));
    assertThat(externalInterface.getOperationType(), is(equalTo("C")));
    assertThat(externalInterface.getLogonUserId(), is(equalTo("CWDSTEST")));
    externalInterfaceTables.createExtInterClient(client3, "N");
    assertThat(externalInterface.getSequenceNumber(), is(equalTo(3)));
    assertThat(externalInterface.getPrimaryKey1(), is(equalTo("PERPET1111")));
    assertThat(externalInterface.getTableName(), is(equalTo("CLIENT_T")));
    assertThat(externalInterface.getOperationType(), is(equalTo("N")));
    assertThat(externalInterface.getLogonUserId(), is(equalTo("CWDSTEST")));


  }


  /**
   * @throws Exception - Exception
   */
  @Test
  public void testForCreateAssignmentExternalInterface() throws Exception {
    Assignment assignmentDomain = new AssignmentResourceBuilder().buildAssignment();
    gov.ca.cwds.data.persistence.cms.Assignment assignment =
        new gov.ca.cwds.data.persistence.cms.Assignment("ABC1234567", assignmentDomain, "0X5");

    when(externalInterfaceDao.create(any(ExternalInterface.class)))
        .thenAnswer(new Answer<ExternalInterface>() {
          @Override
          public ExternalInterface answer(InvocationOnMock invocation) throws Throwable {

            ExternalInterface report = (ExternalInterface) invocation.getArguments()[0];
            externalInterface = report;
            return report;
          }
        });
    externalInterfaceTables.createExtInterAssignment(assignment, "N");
    assertThat(externalInterface.getSequenceNumber(), is(equalTo(1)));
    assertThat(externalInterface.getPrimaryKey1(), is(equalTo("ABC1234567")));
    assertThat(externalInterface.getTableName(), is(equalTo("ASGNM_T")));
    assertThat(externalInterface.getOperationType(), is(equalTo("N")));
    assertThat(externalInterface.getLogonUserId(), is(equalTo("CWDSTEST")));

  }

  /**
   * @throws Exception - Exception
   */
  @Test
  public void testForDeleteAssignmentExternalInterface() throws Exception {
    when(externalInterfaceDao.create(any(ExternalInterface.class)))
        .thenAnswer(new Answer<ExternalInterface>() {
          @Override
          public ExternalInterface answer(InvocationOnMock invocation) throws Throwable {

            ExternalInterface report = (ExternalInterface) invocation.getArguments()[0];
            externalInterface = report;
            return report;
          }
        });

    externalInterfaceTables.createExtInterForDelete("ABC1234567", "ASGNM_T");
    assertThat(externalInterface.getSequenceNumber(), is(equalTo(1)));
    assertThat(externalInterface.getPrimaryKey1(), is(equalTo("ABC1234567")));
    assertThat(externalInterface.getTableName(), is(equalTo("ASGNM_T")));
    assertThat(externalInterface.getOperationType(), is(equalTo("D")));
    assertThat(externalInterface.getLogonUserId(), is(equalTo("CWDSTEST")));

  }

  /**
   * @throws Exception - Exception
   */
  @Test
  public void testForDeleteMultipleExternalInterface() throws Exception {
    when(externalInterfaceDao.create(any(ExternalInterface.class)))
        .thenAnswer(new Answer<ExternalInterface>() {
          @Override
          public ExternalInterface answer(InvocationOnMock invocation) throws Throwable {

            ExternalInterface report = (ExternalInterface) invocation.getArguments()[0];
            externalInterface = report;
            return report;
          }
        });

    externalInterfaceTables.createExtInterForDelete("ABC1234567", "ASGNM_T");
    assertThat(externalInterface.getSequenceNumber(), is(equalTo(1)));
    assertThat(externalInterface.getPrimaryKey1(), is(equalTo("ABC1234567")));
    assertThat(externalInterface.getTableName(), is(equalTo("ASGNM_T")));
    assertThat(externalInterface.getOperationType(), is(equalTo("D")));
    assertThat(externalInterface.getLogonUserId(), is(equalTo("CWDSTEST")));

    externalInterfaceTables.createExtInterForDelete("ABC2345678", "CLIENT_T");
    assertThat(externalInterface.getSequenceNumber(), is(equalTo(2)));
    assertThat(externalInterface.getPrimaryKey1(), is(equalTo("ABC2345678")));
    assertThat(externalInterface.getTableName(), is(equalTo("CLIENT_T")));
    assertThat(externalInterface.getOperationType(), is(equalTo("D")));

  }


}
