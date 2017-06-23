package gov.ca.cwds.fixture;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Date;

import javax.validation.Validation;

import gov.ca.cwds.data.cms.ReferralDao;
import gov.ca.cwds.rest.api.domain.cms.Address;
import gov.ca.cwds.rest.api.domain.cms.Allegation;
import gov.ca.cwds.rest.api.domain.cms.ChildClient;
import gov.ca.cwds.rest.api.domain.cms.Client;
import gov.ca.cwds.rest.api.domain.cms.ClientAddress;
import gov.ca.cwds.rest.api.domain.cms.CrossReport;
import gov.ca.cwds.rest.api.domain.cms.DrmsDocument;
import gov.ca.cwds.rest.api.domain.cms.LongText;
import gov.ca.cwds.rest.api.domain.cms.PostedAddress;
import gov.ca.cwds.rest.api.domain.cms.PostedAllegation;
import gov.ca.cwds.rest.api.domain.cms.PostedClient;
import gov.ca.cwds.rest.api.domain.cms.PostedDrmsDocument;
import gov.ca.cwds.rest.api.domain.cms.PostedLongText;
import gov.ca.cwds.rest.api.domain.cms.PostedReferral;
import gov.ca.cwds.rest.api.domain.cms.PostedReporter;
import gov.ca.cwds.rest.api.domain.cms.Referral;
import gov.ca.cwds.rest.api.domain.cms.ReferralClient;
import gov.ca.cwds.rest.api.domain.cms.Reporter;
import gov.ca.cwds.rest.messages.MessageBuilder;
import gov.ca.cwds.rest.services.ScreeningToReferralService;
import gov.ca.cwds.rest.services.cms.AddressService;
import gov.ca.cwds.rest.services.cms.AllegationService;
import gov.ca.cwds.rest.services.cms.ChildClientService;
import gov.ca.cwds.rest.services.cms.ClientAddressService;
import gov.ca.cwds.rest.services.cms.ClientService;
import gov.ca.cwds.rest.services.cms.CrossReportService;
import gov.ca.cwds.rest.services.cms.DrmsDocumentService;
import gov.ca.cwds.rest.services.cms.LongTextService;
import gov.ca.cwds.rest.services.cms.ReferralClientService;
import gov.ca.cwds.rest.services.cms.ReferralService;
import gov.ca.cwds.rest.services.cms.ReporterService;
import gov.ca.cwds.rest.services.cms.StaffPersonIdRetriever;

/**
 * 
 * @author CWDS API Team
 */
public class MockedScreeningToReferralServiceBuilder {
  private ReferralService referralService;
  private ClientService clientService;
  private ReferralClientService referralClientService;
  private AllegationService allegationService;
  private CrossReportService crossReportService;
  private ReporterService reporterService;
  private AddressService addressService;
  private ClientAddressService clientAddressService;
  private ChildClientService childClientService;
  private LongTextService longTextService;
  private DrmsDocumentService drmsDocumentService;

  private ReferralDao referralDao;
  private StaffPersonIdRetriever staffPersonIdRetriever;
  private MessageBuilder messageBuilder;

  /**
   * @return the referralService
   */
  public ReferralService getReferralService() {
    if (referralService == null) {
      buildDefaultMockForReferralService();
    }
    return referralService;
  }

  private void buildDefaultMockForReferralService() {
    referralService = mock(ReferralService.class);
    PostedReferral postedReferral = mock(PostedReferral.class);
    when(postedReferral.getId()).thenReturn("3456765433");
    when(referralService.create(any(Referral.class))).thenReturn(postedReferral);
    when(referralService.createWithSingleTimestamp(any(Referral.class), any(Date.class)))
        .thenReturn(postedReferral);
  }

  /**
   * @return the clientService
   */
  public ClientService getClientService() {
    if (clientService == null) {
      buildDefaultMockForClientService();
    }
    return clientService;
  }

  private void buildDefaultMockForClientService() {
    clientService = mock(ClientService.class);
    PostedClient postedClient = mock(PostedClient.class);
    when(postedClient.getId()).thenReturn("2342345674");
    when(clientService.create(any(Client.class))).thenReturn(postedClient);
  }

  /**
   * @return teh referralClientService
   */
  public ReferralClientService getReferralClientService() {
    if (referralClientService == null) {
      buildDefaultMockForReferralClientService();
    }
    return referralClientService;
  }

  private void buildDefaultMockForReferralClientService() {
    referralClientService = mock(ReferralClientService.class);
    ReferralClient referralClient = mock(ReferralClient.class);
    when(referralClientService.create(any(ReferralClient.class))).thenReturn(referralClient);
  }

  /**
   * @return the allegationService
   */
  public AllegationService getAllegationService() {
    if (allegationService == null) {
      buildDefaultMockForAllegationService();
    }
    return allegationService;
  }

  private void buildDefaultMockForAllegationService() {
    allegationService = mock(AllegationService.class);
    PostedAllegation postedAllegation = mock(PostedAllegation.class);
    when(postedAllegation.getId()).thenReturn("3453454323");
    when(allegationService.create(any(Allegation.class))).thenReturn(postedAllegation);
    when(allegationService.createWithSingleTimestamp(any(Allegation.class), any(Date.class)))
        .thenReturn(postedAllegation);
  }

  /**
   * @return the crossReportService
   */
  public CrossReportService getCrossReportService() {
    if (crossReportService == null) {
      buildDefaultMockForCrossReportService();
    }
    return crossReportService;
  }

  private void buildDefaultMockForCrossReportService() {
    crossReportService = mock(CrossReportService.class);
    CrossReport postedCrossReport = mock(CrossReport.class);
    when(postedCrossReport.getThirdId()).thenReturn("4565678905");
    when(crossReportService.create(any(CrossReport.class))).thenReturn(postedCrossReport);
    when(crossReportService.createWithSingleTimestamp(any(CrossReport.class), any(Date.class)))
        .thenReturn(postedCrossReport);
  }

  /**
   * @return the reporterService
   */
  public ReporterService getReporterService() {
    if (reporterService == null) {
      buildDefaultMockForReporterService();
    }
    return reporterService;
  }

  private void buildDefaultMockForReporterService() {
    reporterService = mock(ReporterService.class);
    Reporter reporter = mock(Reporter.class);
    PostedReporter postedReporter = mock(PostedReporter.class);
    when(postedReporter.getReferralId()).thenReturn("5674567845");
    when(reporterService.create(any(Reporter.class))).thenReturn(postedReporter);
  }

  /**
   * @return the addressService
   */
  public AddressService getAddressService() {
    if (addressService == null) {
      buildDefaultMockForAddressService();
    }
    return addressService;
  }

  private void buildDefaultMockForAddressService() {
    addressService = mock(AddressService.class);
    PostedAddress postedAddress = mock(PostedAddress.class);
    when(postedAddress.getExistingAddressId()).thenReturn("6783345677");
    when(addressService.create(any(Address.class))).thenReturn(postedAddress);
    when(addressService.createWithSingleTimestamp(any(Address.class), any(Date.class)))
        .thenReturn(postedAddress);
  }

  /**
   * @return the clientAddressService
   */
  public ClientAddressService getClientAddressService() {
    if (clientAddressService == null) {
      buildDefaultMockForClientAddressService();
    }
    return clientAddressService;
  }

  private void buildDefaultMockForClientAddressService() {
    clientAddressService = mock(ClientAddressService.class);
    ClientAddress clientAddress = mock(ClientAddress.class);
    when(clientAddressService.create(any(ClientAddress.class))).thenReturn(clientAddress);
  }

  /**
   * @return the childClientService
   */
  public ChildClientService getChildClientService() {
    if (childClientService == null) {
      buildDefaultMockForChildClientService();
    }
    return childClientService;
  }

  private void buildDefaultMockForChildClientService() {
    childClientService = mock(ChildClientService.class);
    ChildClient childClient = mock(ChildClient.class);
    when(childClientService.create(any(ChildClient.class))).thenReturn(childClient);
  }

  /**
   * @return the longTextService
   */
  public LongTextService getLongTextService() {
    if (longTextService == null) {
      buildDefaultMockForLongTextService();
    }
    return longTextService;
  }

  private void buildDefaultMockForLongTextService() {
    longTextService = mock(LongTextService.class);
    PostedLongText postedLongText = mock(PostedLongText.class);
    when(postedLongText.getId()).thenReturn("7894345643");
    when(longTextService.create(any(LongText.class))).thenReturn(postedLongText);
  }

  /**
   * @return the drmsDocumentService
   */
  public DrmsDocumentService getDrmsDocumentService() {
    if (drmsDocumentService == null) {
      buildDefaultMockForDmsDocumentService();
    }
    return drmsDocumentService;
  }

  private void buildDefaultMockForDmsDocumentService() {
    drmsDocumentService = mock(DrmsDocumentService.class);
    PostedDrmsDocument postedDrmsDocument = mock(PostedDrmsDocument.class);
    when(postedDrmsDocument.getId()).thenReturn("9876543BCA");
    when(drmsDocumentService.create(any(DrmsDocument.class))).thenReturn(postedDrmsDocument);

  }

  /**
   * @return the referralDao
   */
  public ReferralDao getReferralDao() {
    if (referralDao == null) {
      referralDao = mock(ReferralDao.class);
    }
    return referralDao;
  }

  /**
   * @return the staffPersonIdRetriever
   */
  public StaffPersonIdRetriever getStaffPersonIdRetriever() {
    if (staffPersonIdRetriever == null) {
      buildDefaultMockForStaffPersonIdRetriever();
    }
    return staffPersonIdRetriever;
  }

  private void buildDefaultMockForStaffPersonIdRetriever() {
    staffPersonIdRetriever = mock(StaffPersonIdRetriever.class);
    when(staffPersonIdRetriever.getStaffPersonId()).thenReturn("abc");
  }

  /**
   * @return the messageBuilder
   */
  public MessageBuilder getMessageBuilder() {
    if (messageBuilder == null) {
      messageBuilder = mock(MessageBuilder.class);
    }
    return messageBuilder;
  }

  /**
   * @param drmsDocumentService - drmsDocumentService
   * @return the drmsDocumentService
   */
  public MockedScreeningToReferralServiceBuilder addDrmsDocumentService(
      DrmsDocumentService drmsDocumentService) {
    this.drmsDocumentService = drmsDocumentService;
    return this;
  }

  /**
   * @param referralService - referralService
   * @return the referralService
   */
  public MockedScreeningToReferralServiceBuilder addReferralService(
      ReferralService referralService) {
    this.referralService = referralService;
    return this;
  }

  /**
   * @param clientService - clientService
   * @return the clientService
   */
  public MockedScreeningToReferralServiceBuilder addClientService(ClientService clientService) {
    this.clientService = clientService;
    return this;
  }

  /**
   * @param referralClientService - referralClientService
   * @return the referralClientService
   */
  public MockedScreeningToReferralServiceBuilder addReferralClientService(
      ReferralClientService referralClientService) {
    this.referralClientService = referralClientService;
    return this;
  }

  /**
   * @param allegationService - allegationService
   * @return the allegationService
   */
  public MockedScreeningToReferralServiceBuilder addAllegationService(
      AllegationService allegationService) {
    this.allegationService = allegationService;
    return this;
  }

  /**
   * @param crossReportService - crossReportService
   * @return the crossReportService
   */
  public MockedScreeningToReferralServiceBuilder addCrossReportService(
      CrossReportService crossReportService) {
    this.crossReportService = crossReportService;
    return this;
  }

  /**
   * @param reporterService - reporterService
   * @return the reporterService
   */
  public MockedScreeningToReferralServiceBuilder addReporterService(
      ReporterService reporterService) {
    this.reporterService = reporterService;
    return this;
  }

  /**
   * @param addressService - addressService
   * @return the addressService
   */
  public MockedScreeningToReferralServiceBuilder addAddressService(AddressService addressService) {
    this.addressService = addressService;
    return this;
  }

  /**
   * @param clientAddressService - clientAddressService
   * @return the clientAddressService
   */
  public MockedScreeningToReferralServiceBuilder addClientAddressService(
      ClientAddressService clientAddressService) {
    this.clientAddressService = clientAddressService;
    return this;
  }

  /**
   * @param childClientService - childClientService
   * @return the childClientService
   */
  public MockedScreeningToReferralServiceBuilder addChildClientService(
      ChildClientService childClientService) {
    this.childClientService = childClientService;
    return this;
  }

  /**
   * @param longTextService - longTextService
   * @return the longTextService
   */
  public MockedScreeningToReferralServiceBuilder addLongTextService(
      LongTextService longTextService) {
    this.longTextService = longTextService;
    return this;
  }

  /**
   * @param referralDao - referralDao
   * @return the referralDao
   */
  public MockedScreeningToReferralServiceBuilder addReferralDao(ReferralDao referralDao) {
    this.referralDao = referralDao;
    return this;
  }

  /**
   * @param staffPersonIdRetriever - staffPersonIdRetriever
   * @return the staffPersonIdRetriever
   */
  public MockedScreeningToReferralServiceBuilder addStaffPersonIdRetriever(
      StaffPersonIdRetriever staffPersonIdRetriever) {
    this.staffPersonIdRetriever = staffPersonIdRetriever;
    return this;
  }

  /**
   * @param messageBuilder - messageBuilder
   * @return the messageBuilder
   */
  public MockedScreeningToReferralServiceBuilder addMessageBuilder(MessageBuilder messageBuilder) {
    this.messageBuilder = messageBuilder;
    return this;
  }

  /**
   * @return the screeningToReferralService
   */
  public ScreeningToReferralService createScreeningToReferralService() {
    return new ScreeningToReferralService(getReferralService(), getClientService(),
        getAllegationService(), getCrossReportService(), getReferralClientService(),
        getReporterService(), getAddressService(), getClientAddressService(), getLongTextService(),
        getChildClientService(), Validation.buildDefaultValidatorFactory().getValidator(),
        getReferralDao(), getStaffPersonIdRetriever(), getMessageBuilder(),
        getDrmsDocumentService());
  }
}
