package gov.ca.cwds.rest.services.investigation;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import gov.ca.cwds.data.cms.AttorneyDao;
import gov.ca.cwds.data.cms.ClientDao;
import gov.ca.cwds.data.cms.CollateralIndividualDao;
import gov.ca.cwds.data.cms.LongTextDao;
import gov.ca.cwds.data.cms.ReporterDao;
import gov.ca.cwds.data.cms.ServiceProviderDao;
import gov.ca.cwds.data.cms.StaffPersonDao;
import gov.ca.cwds.data.cms.SubstituteCareProviderDao;
import gov.ca.cwds.data.cms.TestSystemCodeCache;
import gov.ca.cwds.data.dao.contact.DeliveredServiceDao;
import gov.ca.cwds.data.dao.contact.IndividualDeliveredServiceDao;
import gov.ca.cwds.data.dao.contact.ReferralClientDeliveredServiceDao;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.investigation.contact.ContactRequest;
import gov.ca.cwds.rest.filters.TestingRequestExecutionContext;

import org.apache.commons.lang3.NotImplementedException;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ContactServiceTest {

  private static final String DEFAULT_KEY = "abc1234567";

  DeliveredServiceDao deliveredServiceDao;
  StaffPersonDao staffPersonDao;
  LongTextDao longTextDao;
  IndividualDeliveredServiceDao individualDeliveredServiceDao;
  ClientDao clientDao;
  AttorneyDao attorneyDao;
  CollateralIndividualDao collateralIndividualDao;
  ServiceProviderDao serviceProviderDao;
  SubstituteCareProviderDao substituteCareProviderDao;
  ReporterDao reporterDao;
  ReferralClientDeliveredServiceDao referralClientDeliveredServiceDao;
  DeliveredToIndividualService deliveredToIndividualService;
  ContactService target;

  @BeforeClass
  public static void setupSuite() {
    new TestSystemCodeCache();
    new TestingRequestExecutionContext("abc1234567");
  }

  @Before
  public void setup() throws Exception {
    attorneyDao = mock(AttorneyDao.class);
    clientDao = mock(ClientDao.class);
    collateralIndividualDao = mock(CollateralIndividualDao.class);
    deliveredServiceDao = mock(DeliveredServiceDao.class);
    individualDeliveredServiceDao = mock(IndividualDeliveredServiceDao.class);
    longTextDao = mock(LongTextDao.class);
    reporterDao = mock(ReporterDao.class);
    serviceProviderDao = mock(ServiceProviderDao.class);
    staffPersonDao = mock(StaffPersonDao.class);
    substituteCareProviderDao = mock(SubstituteCareProviderDao.class);
    referralClientDeliveredServiceDao = mock(ReferralClientDeliveredServiceDao.class);
    deliveredToIndividualService =
        new DeliveredToIndividualService(clientDao, attorneyDao, collateralIndividualDao,
            serviceProviderDao, substituteCareProviderDao, reporterDao);
    target =
        new ContactService(deliveredServiceDao, staffPersonDao, longTextDao,
            individualDeliveredServiceDao, referralClientDeliveredServiceDao,
            deliveredToIndividualService);
  }

  @Test
  public void type() throws Exception {
    assertThat(ContactService.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    assertThat(target, notNullValue());
  }

  @Test
  public void find_Args__String() throws Exception {
    final String primaryKey = DEFAULT_KEY;
    Response actual = target.find(primaryKey);
    assertThat(actual, notNullValue());
  }

  @Test(expected = NotImplementedException.class)
  public void delete_Args__String() throws Exception {
    String primaryKey = null;
    target.delete(primaryKey);
  }

  @Test
  public void create_Args__ContactRequestList() throws Exception {
    final ContactRequest request = mock(ContactRequest.class);
    Response actual = target.create(request);
    assertThat(actual, notNullValue());
  }

  @Test
  public void update_Args__String__ContactRequestList() throws Exception {
    String primaryKey = null;
    final ContactRequest request = mock(ContactRequest.class);
    Response actual = target.update(primaryKey, request);
    assertThat(actual, notNullValue());
  }

}
