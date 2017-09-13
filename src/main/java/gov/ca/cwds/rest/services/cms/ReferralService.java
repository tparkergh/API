package gov.ca.cwds.rest.services.cms;

import gov.ca.cwds.rest.api.domain.DomainChef;
import java.util.Date;
import java.util.Set;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.validation.Validator;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.Dao;
import gov.ca.cwds.data.cms.ReferralDao;
import gov.ca.cwds.data.cms.StaffPersonDao;
import gov.ca.cwds.data.persistence.cms.CmsKeyIdGenerator;
import gov.ca.cwds.data.persistence.cms.Referral;
import gov.ca.cwds.data.persistence.cms.StaffPerson;
import gov.ca.cwds.data.rules.TriggerTablesDao;
import gov.ca.cwds.rest.api.domain.ScreeningToReferral;
import gov.ca.cwds.rest.api.domain.cms.LongText;
import gov.ca.cwds.rest.api.domain.cms.PostedLongText;
import gov.ca.cwds.rest.api.domain.cms.PostedReferral;
import gov.ca.cwds.rest.api.domain.cms.SystemCode;
import gov.ca.cwds.rest.api.domain.cms.SystemCodeCache;
import gov.ca.cwds.rest.business.rules.LACountyTrigger;
import gov.ca.cwds.rest.business.rules.NonLACountyTriggers;
import gov.ca.cwds.rest.filters.RequestExecutionContext;
import gov.ca.cwds.rest.messages.MessageBuilder;
import gov.ca.cwds.rest.services.LegacyDefaultValues;
import gov.ca.cwds.rest.services.ServiceException;
import gov.ca.cwds.rest.services.TypedCrudsService;
import gov.ca.cwds.rest.services.referentialintegrity.RIReferral;
import gov.ca.cwds.rest.validation.ParticipantValidator;

/**
 * Business layer object to work on {@link Referral}
 * 
 * @author CWDS API Team
 */
public class ReferralService implements
    TypedCrudsService<String, gov.ca.cwds.rest.api.domain.cms.Referral, gov.ca.cwds.rest.api.domain.cms.Referral> {

  private static final Logger LOGGER = LoggerFactory.getLogger(ReferralService.class);

  private ReferralDao referralDao;
  private NonLACountyTriggers nonLaTriggers;
  private LACountyTrigger laCountyTrigger;
  private TriggerTablesDao triggerTablesDao;
  private StaffPersonDao staffpersonDao;
  private StaffPersonIdRetriever staffPersonIdRetriever;

  private Validator validator;
  private AssignmentService assignmentService;
  private DrmsDocumentService drmsDocumentService;
  private AddressService addressService;
  private LongTextService longTextService;
  private RIReferral riReferral;

  /**
   * Constructor
   * 
   * @param referralDao The {@link Dao} handling {@link gov.ca.cwds.data.persistence.cms.Referral}
   *        objects.
   * @param nonLaTriggers The {@link Dao} handling
   *        {@link gov.ca.cwds.rest.business.rules.NonLACountyTriggers} objects
   * @param laCountyTrigger The {@link Dao} handling
   *        {@link gov.ca.cwds.rest.business.rules.LACountyTrigger} objects
   * @param triggerTablesDao The {@link Dao} handling
   *        {@link gov.ca.cwds.data.rules.TriggerTablesDao} objects
   * @param staffpersonDao The {@link Dao} handling
   *        {@link gov.ca.cwds.data.persistence.cms.StaffPerson} objects
   * @param staffPersonIdRetriever the staffPersonIdRetriever
   * @param assignmentService the Assignment Service
   * @param validator the validator used for entity validation
   * @param drmsDocumentService the service for generating DRMS Documents
   * @param addressService the service for creating addresses
   * @param longTextService the longText Service
   * @param riReferral the ri
   */
  @Inject
  public ReferralService(final ReferralDao referralDao, NonLACountyTriggers nonLaTriggers,
      LACountyTrigger laCountyTrigger, TriggerTablesDao triggerTablesDao,
      StaffPersonDao staffpersonDao, StaffPersonIdRetriever staffPersonIdRetriever,
      AssignmentService assignmentService, Validator validator,
      DrmsDocumentService drmsDocumentService, AddressService addressService,
      LongTextService longTextService, RIReferral riReferral) {
    this.referralDao = referralDao;
    this.nonLaTriggers = nonLaTriggers;
    this.laCountyTrigger = laCountyTrigger;
    this.triggerTablesDao = triggerTablesDao;
    this.staffpersonDao = staffpersonDao;
    this.staffPersonIdRetriever = staffPersonIdRetriever;
    this.assignmentService = assignmentService;
    this.validator = validator;
    this.drmsDocumentService = drmsDocumentService;
    this.addressService = addressService;
    this.longTextService = longTextService;
    this.riReferral = riReferral;
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#find(java.io.Serializable)
   */
  @Override
  public gov.ca.cwds.rest.api.domain.cms.Referral find(String primaryKey) {

    gov.ca.cwds.data.persistence.cms.Referral persistedReferral = referralDao.find(primaryKey);
    if (persistedReferral != null) {
      return new gov.ca.cwds.rest.api.domain.cms.Referral(persistedReferral);
    }
    return null;
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#delete(java.io.Serializable)
   */
  @Override
  public gov.ca.cwds.rest.api.domain.cms.Referral delete(String primaryKey) {
    gov.ca.cwds.data.persistence.cms.Referral persistedReferral = referralDao.delete(primaryKey);
    if (persistedReferral != null) {
      return new gov.ca.cwds.rest.api.domain.cms.Referral(persistedReferral);
    }
    return null;
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#create(gov.ca.cwds.rest.api.Request)
   */
  @Override
  public PostedReferral create(gov.ca.cwds.rest.api.domain.cms.Referral request) {

    gov.ca.cwds.rest.api.domain.cms.Referral referral = request;
    return create(referral, null);
  }

  /**
   * This createWithSingleTimestamp is used for the referrals to maintian the same timestamp for the
   * whole transaction
   * 
   * @param request - request
   * @param timestamp - timestamp
   * @return the single timestamp
   */
  public PostedReferral createWithSingleTimestamp(gov.ca.cwds.rest.api.domain.cms.Referral request,
      Date timestamp) {

    gov.ca.cwds.rest.api.domain.cms.Referral referral = request;
    return create(referral, timestamp);
  }

  /**
   * This private method is created to handle to single referral and referrals with single timestamp
   * 
   */
  private PostedReferral create(gov.ca.cwds.rest.api.domain.cms.Referral referral, Date timestamp) {
    try {
      String lastUpdatedId = staffPersonIdRetriever.getStaffPersonId();
      Referral managed;
      if (timestamp == null) {
        managed = new Referral(CmsKeyIdGenerator.generate(lastUpdatedId), referral, lastUpdatedId);
      } else {
        managed = new Referral(CmsKeyIdGenerator.generate(lastUpdatedId), referral, lastUpdatedId,
            timestamp);
      }
      managed = referralDao.create(managed);
      if (managed.getId() == null) {
        throw new ServiceException("Referral ID cannot be null");
      }
      // checking the staffPerson county code
      StaffPerson staffperson = staffpersonDao.find(managed.getLastUpdatedId());
      if (staffperson != null
          && (triggerTablesDao.getLaCountySpecificCode().equals(staffperson.getCountyCode()))) {
        laCountyTrigger.createCountyTrigger(managed);
      }
      return new PostedReferral(managed);
    } catch (EntityExistsException e) {
      LOGGER.info("Referral already exists : {}", referral);
      throw new ServiceException(e);
    }
  }

  /**
   * @param screeningToReferral - screeningToReferral
   * @param dateStarted - dateStarted
   * @param timeStarted - timeStarted
   * @param timestamp - timestamp
   * @param messageBuilder - messageBuilder
   * @return the cmsReferral
   */
  public String createCmsReferralFromScreening(ScreeningToReferral screeningToReferral,
      String dateStarted, String timeStarted, Date timestamp, MessageBuilder messageBuilder) {

    String referralId = null;

    if (screeningToReferral.getReferralId() == null
        || screeningToReferral.getReferralId().isEmpty()) {
      // the legacy id is not set - create the referral
      // create a CMS Referral
      gov.ca.cwds.rest.api.domain.cms.Referral referral = null;
      try {
        referral = createReferralWithDefaults(screeningToReferral, dateStarted, timeStarted,
            timestamp, messageBuilder);
      } catch (ServiceException e) {
        String message = e.getMessage();
        messageBuilder.addMessageAndLog(message, e, LOGGER);
      } catch (NullPointerException e) {
        String message = e.getMessage();
        messageBuilder.addMessageAndLog(message, e, LOGGER);
      } catch (Exception e) {
        String message = e.getMessage();
        messageBuilder.addMessageAndLog(message, e, LOGGER);
        throw e;
      }

      messageBuilder.addDomainValidationError(validator.validate(referral));

      PostedReferral postedReferral = this.createWithSingleTimestamp(referral, timestamp);
      referralId = postedReferral.getId();

      // when creating a referral - create the default assignment to 0XA staff person
      assignmentService.createDefaultAssignmentForNewReferral(referralId, timestamp,
          messageBuilder);
      // TODO: R - 01054 Prmary Assignment Adding

    } else {
      // Referral ID passed - validate that Referral exist in CWS/CMS - no update for now
      referralId = screeningToReferral.getReferralId();
      gov.ca.cwds.rest.api.domain.cms.Referral foundReferral = this.find(referralId);
      if (foundReferral == null) {
        String message = "Legacy Id does not correspond to an existing CMS/CWS Referral";
        ServiceException se = new ServiceException(message);
        messageBuilder.addMessageAndLog(message, se, LOGGER);
      }
    }
    return referralId;
  }

  /**
   * @param screeningToReferral - screeningToReferral
   * @param dateStarted - dateStarted
   * @param timeStarted - timeStarted
   * @param timestamp - timestamp
   * @param messageBuilder - the messageBuilder object responsible for handling errors
   * @return the referral
   * @throws ServiceException - ServiceException
   */
  public gov.ca.cwds.rest.api.domain.cms.Referral createReferralWithDefaults(
      ScreeningToReferral screeningToReferral, String dateStarted, String timeStarted,
      Date timestamp, MessageBuilder messageBuilder) throws ServiceException {
    String longTextId = generateReportNarrative(screeningToReferral, messageBuilder);
    String responseRationalLongTextId =
        generateResponseRationalText(screeningToReferral, messageBuilder);

    /*
     * create a three dummy records using generateDrmsDocumentId method
     */
    String drmsAllegationDescriptionDoc =
        drmsDocumentService.generateDrmsDocumentId(messageBuilder);
    String drmsErReferralDoc = drmsDocumentService.generateDrmsDocumentId(messageBuilder);
    String drmsInvestigationDoc = drmsDocumentService.generateDrmsDocumentId(messageBuilder);

    /*
     * create the referralAddress and assign the value to the
     * allegesAbuseOccurredAtAddressId(FKADDRS_T)
     */
    createReferralAddress(screeningToReferral, timestamp, messageBuilder);
    String allegesAbuseOccurredAtAddressId = screeningToReferral.getAddress().getLegacyId();

    String limitedAccessDate = DomainChef.cookDate(screeningToReferral.getLimitedAccessDate());

    int govEnt =
        convertLogicalIdToSystemCodeFor(screeningToReferral.getIncidentCounty(), "GVR_ENTC");
    Short agencyCode = convertLimitedAccessAgencyToNumericCode(screeningToReferral);

    return gov.ca.cwds.rest.api.domain.cms.Referral.createWithDefaults(
        ParticipantValidator.anonymousReporter(screeningToReferral),
        screeningToReferral.getCommunicationMethod(), drmsAllegationDescriptionDoc,
        drmsErReferralDoc, drmsInvestigationDoc, screeningToReferral.isFiledWithLawEnforcement(),
        screeningToReferral.isFamilyAwareness(), govEnt, screeningToReferral.getName(), dateStarted,
        timeStarted, screeningToReferral.getResponseTime(), allegesAbuseOccurredAtAddressId,
        firstResponseDeterminedByStaffPersonId(), longTextId,
        screeningToReferral.getIncidentCounty(), (short) screeningToReferral.getApprovalStatus(),
        LegacyDefaultValues.DEFAULT_STAFF_PERSON_ID, responseRationalLongTextId,
        screeningToReferral.getResponsibleAgency(), screeningToReferral.getLimitedAccessCode(),
        screeningToReferral.getLimitedAccessDescription(), limitedAccessDate ,agencyCode);
  }

  private Short convertLimitedAccessAgencyToNumericCode(ScreeningToReferral screeningToReferral) {
    String agencyValue = screeningToReferral.getLimitedAccessAgency();
    return StringUtils.isNumeric(agencyValue) ? Short.parseShort(agencyValue) : 0;
  }

  private int convertLogicalIdToSystemCodeFor(String logicalCode, String governmentEntityCode) {
    int foundCode = 0;
    Set<SystemCode> systemCodes =
        SystemCodeCache.global().getSystemCodesForMeta(governmentEntityCode);
    for (SystemCode systemCode : systemCodes) {
      if (systemCode.getLogicalId().equals(logicalCode)) {
        foundCode = systemCode.getSystemId();
      }
    }
    return foundCode;
  }


  private void createReferralAddress(ScreeningToReferral screeningToReferral, Date timestamp,
      MessageBuilder messageBuilder) {
    try {
      gov.ca.cwds.rest.api.domain.Address referralAddress =
          addressService.createAddressFromScreening(screeningToReferral, timestamp, messageBuilder);
      screeningToReferral.setAddress(referralAddress);
    } catch (ServiceException e1) {
      String message = e1.getMessage();
      messageBuilder.addMessageAndLog(message, e1, LOGGER);
    }
  }

  /**
   * <blockquote>
   *
   * <pre>
   * BUSINESS RULE: "R - 04537" - FKSTFPERS0 set when first referral determined
   *
   * IF    referralResponseTypeCode is set to default
   * THEN  firstResponseDeterminedByStaffPersonId is set to the staffpersonId
   *
   * </pre>
   *
   * </blockquote>
   */
  private static String firstResponseDeterminedByStaffPersonId() {
    return RequestExecutionContext.instance().getStaffId();

  }

  private String generateReportNarrative(ScreeningToReferral screeningToReferral,
      MessageBuilder messageBuilder) {
    String longTextId = null;
    if (screeningToReferral.getReportNarrative() == null
        || screeningToReferral.getReportNarrative().isEmpty()) {
      longTextId = null;
    } else {
      try {
        longTextId = createLongText(screeningToReferral.getIncidentCounty(),
            screeningToReferral.getReportNarrative(), messageBuilder);
      } catch (ServiceException e) {
        String message = e.getMessage();
        messageBuilder.addMessageAndLog(message, e, LOGGER);
      }
    }
    return longTextId;
  }

  private String generateResponseRationalText(ScreeningToReferral screeningToReferral,
      MessageBuilder messageBuilder) {
    String longTextId = null;
    if (screeningToReferral.getAdditionalInformation() == null
        || screeningToReferral.getAdditionalInformation().isEmpty()) {
      longTextId = null;
    } else {
      try {
        longTextId = createLongText(screeningToReferral.getIncidentCounty(),
            screeningToReferral.getAdditionalInformation(), messageBuilder);
      } catch (ServiceException e) {
        String message = e.getMessage();
        messageBuilder.addMessageAndLog(message, e, LOGGER);
      }
    }
    return longTextId;
  }

  private String createLongText(String countySpecificCode, String textDescription,
      MessageBuilder messageBuilder) throws ServiceException {

    LongText longText = new LongText(countySpecificCode, textDescription);
    PostedLongText postedLongText = longTextService.create(longText);

    messageBuilder.addDomainValidationError(validator.validate(longText));

    return postedLongText.getId();

  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#update(java.io.Serializable,
   *      gov.ca.cwds.rest.api.Request)
   */
  @Override
  public gov.ca.cwds.rest.api.domain.cms.Referral update(String primaryKey,
      gov.ca.cwds.rest.api.domain.cms.Referral request) {
    gov.ca.cwds.rest.api.domain.cms.Referral referral = request;

    try {
      String lastUpdatedId = staffPersonIdRetriever.getStaffPersonId();
      Referral managed = new Referral(primaryKey, referral, lastUpdatedId);
      managed = referralDao.update(managed);
      // checking the staffPerson county code
      StaffPerson staffperson = staffpersonDao.find(managed.getLastUpdatedId());
      if (staffperson != null
          && (triggerTablesDao.getLaCountySpecificCode().equals(staffperson.getCountyCode()))) {
        laCountyTrigger.createCountyTrigger(managed);
      }
      return new gov.ca.cwds.rest.api.domain.cms.Referral(managed);
    } catch (EntityNotFoundException e) {
      final String msg = "Referral not found : " + referral;
      LOGGER.error("Referral not found : {}", referral);
      throw new ServiceException(msg, e);
    }
  }

}
