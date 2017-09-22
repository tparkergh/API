package gov.ca.cwds.rest.services.cms;

import java.util.Date;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.validation.Validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.Dao;
import gov.ca.cwds.data.cms.AssignmentDao;
import gov.ca.cwds.data.cms.StaffPersonDao;
import gov.ca.cwds.data.persistence.cms.Assignment;
import gov.ca.cwds.data.persistence.cms.CmsKeyIdGenerator;
import gov.ca.cwds.data.persistence.cms.StaffPerson;
import gov.ca.cwds.data.rules.TriggerTablesDao;
import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.domain.ScreeningToReferral;
import gov.ca.cwds.rest.api.domain.cms.PostedAssignment;
import gov.ca.cwds.rest.business.rules.ExternalInterfaceTables;
import gov.ca.cwds.rest.business.rules.NonLACountyTriggers;
import gov.ca.cwds.rest.messages.MessageBuilder;
import gov.ca.cwds.rest.services.ServiceException;
import gov.ca.cwds.rest.services.TypedCrudsService;
import gov.ca.cwds.rest.services.referentialintegrity.RIAssignment;

/**
 * Business layer object serves {@link Assignment}.
 * 
 * @author CWDS API Team
 */
public class AssignmentService implements
    TypedCrudsService<String, gov.ca.cwds.rest.api.domain.cms.Assignment, gov.ca.cwds.rest.api.domain.cms.Assignment> {

  private static final Logger LOGGER = LoggerFactory.getLogger(AssignmentService.class);

  private AssignmentDao assignmentDao;
  private NonLACountyTriggers nonLACountyTriggers;
  private StaffPersonDao staffpersonDao;
  private TriggerTablesDao triggerTablesDao;
  private StaffPersonIdRetriever staffPersonIdRetriever;
  private ExternalInterfaceTables externalInterfaceTables;
  private RIAssignment riAssignment;

  private Validator validator;

  /**
   * Constructor
   * 
   * @param assignmentDao The {@link Dao} handling
   *        {@link gov.ca.cwds.data.persistence.cms.Assignment} objects.
   * @param nonLACountyTriggers The {@link Dao} handling
   *        {@link gov.ca.cwds.rest.business.rules.NonLACountyTriggers} objects
   * @param staffpersonDao The {@link Dao} handling
   *        {@link gov.ca.cwds.data.persistence.cms.StaffPerson} objects.
   * @param triggerTablesDao - triggerTablesDao
   * @param staffPersonIdRetriever the staffPersonIdRetriever
   * @param validator the validator to use to validate validatable objects
   * @param externalInterfaceTables external interface table
   * @param riAssignment - riAssignment
   */
  @Inject
  public AssignmentService(AssignmentDao assignmentDao, NonLACountyTriggers nonLACountyTriggers,
      StaffPersonDao staffpersonDao, TriggerTablesDao triggerTablesDao,
      StaffPersonIdRetriever staffPersonIdRetriever, Validator validator,
      ExternalInterfaceTables externalInterfaceTables, RIAssignment riAssignment) {
    this.assignmentDao = assignmentDao;
    this.nonLACountyTriggers = nonLACountyTriggers;
    this.staffpersonDao = staffpersonDao;
    this.triggerTablesDao = triggerTablesDao;
    this.staffPersonIdRetriever = staffPersonIdRetriever;
    this.validator = validator;
    this.externalInterfaceTables = externalInterfaceTables;
    this.riAssignment = riAssignment;
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#find(java.io.Serializable)
   */
  @Override
  public gov.ca.cwds.rest.api.domain.cms.Assignment find(String primaryKey) {

    gov.ca.cwds.data.persistence.cms.Assignment persistedAssignment =
        assignmentDao.find(primaryKey);
    if (persistedAssignment != null) {
      return new gov.ca.cwds.rest.api.domain.cms.Assignment(persistedAssignment);
    }
    return null;
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#delete(java.io.Serializable)
   */
  @Override
  public gov.ca.cwds.rest.api.domain.cms.Assignment delete(String primaryKey) {
    gov.ca.cwds.data.persistence.cms.Assignment persistedAssignment =
        assignmentDao.delete(primaryKey);
    externalInterfaceTables.createExtInterForDelete(primaryKey, "ASGNM_T");
    if (persistedAssignment != null) {
      return new gov.ca.cwds.rest.api.domain.cms.Assignment(persistedAssignment);
    }
    return null;
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#create(gov.ca.cwds.rest.api.Request)
   */
  @Override
  public PostedAssignment create(gov.ca.cwds.rest.api.domain.cms.Assignment request) {
    gov.ca.cwds.rest.api.domain.cms.Assignment assignment = request;
    return create(assignment, null);
  }

  /**
   * This createWithSingleTimestamp is used for the referrals to maintian the same timestamp for the
   * whole transaction
   * 
   * @param request - request
   * @param timestamp - timestamp
   * @return the PostedAssignment
   */
  public PostedAssignment createWithSingleTimestamp(Request request, Date timestamp) {

    gov.ca.cwds.rest.api.domain.cms.Assignment assignment =
        (gov.ca.cwds.rest.api.domain.cms.Assignment) request;
    return create(assignment, timestamp);

  }

  /**
   * This private method is created to handle to single referral and referrals with single timestamp
   * 
   */
  private PostedAssignment create(gov.ca.cwds.rest.api.domain.cms.Assignment assignment,
      Date timestamp) {
    try {
      String lastUpdatedId = staffPersonIdRetriever.getStaffPersonId();
      Assignment managed;
      if (timestamp == null) {
        managed =
            new Assignment(CmsKeyIdGenerator.generate(lastUpdatedId), assignment, lastUpdatedId);
      } else {
        managed = new Assignment(CmsKeyIdGenerator.generate(lastUpdatedId), assignment,
            lastUpdatedId, timestamp);
      }
      managed = assignmentDao.create(managed);
      if (managed.getId() == null) {
        throw new ServiceException("Assignment ID cannot be null");
      }
      // checking the staffPerson county code
      StaffPerson staffperson = staffpersonDao.find(managed.getLastUpdatedId());
      if (staffperson != null
          && !(triggerTablesDao.getLaCountySpecificCode().equals(staffperson.getCountyCode()))) {
        nonLACountyTriggers.createAndUpdateReferralCoutyOwnership(managed);
      }
      externalInterfaceTables.createExtInterAssignment(managed, "N");
      return new PostedAssignment(managed);
    } catch (EntityExistsException e) {
      LOGGER.info("Assignment already exists : {}", assignment);
      throw new ServiceException(e);
    }
  }

  /**
   * @param screeningToReferral - screeningToReferral
   * @param referralId - referralId
   * @param timestamp - timestamp
   * @param messageBuilder - messageBuilder
   */
  // create a default assignment
  // R - 02473 Default Referral Assignment
  // R - 02160 Assignment - Caseload Access
  public void createDefaultAssignmentForNewReferral(ScreeningToReferral screeningToReferral,
      String referralId, Date timestamp, MessageBuilder messageBuilder) {
    // #146713651 - BARNEY: Referrals require a default assignment
    // Default Assignment - referrals will be assigned to the '0X5' staff person ID.
    //
    // create an initial Assignment to the Staff Persons Case Load when the referral is created
    //
    // initialy use 0X5 staff person id to match the CWS/CMS CWDST user on the TESTDOM workstation
    // eventually, the id of the staff person making the request will be used
    String staffId = staffPersonIdRetriever.getStaffPersonId();

    // To find the Case Load of a Staff Person (0X5):
    // 1) find the STAFF_PERSON_CASE_LOAD row with FKSTFPERST = '0X5'
    // 2) find the CASE_LOAD row with CASE_LOAD.CASE_LDT = STAFF_PERSON_CASE_LOAD/IDENTIFIER
    //
    // On TESTDOM (CWSNS1) workstation this will find the CASE_LOAD/IDENTIFIER of OkAImUW0Wz
    //
    final String caseLoadId = assignmentDao.findCaseId(screeningToReferral.getAssigneeId());
    if (caseLoadId == null) {
      String message = "CaseLoad is not found for the staffperson";
      ServiceException se = new ServiceException(message);
      messageBuilder.addMessageAndLog(message, se, LOGGER);
    }

    // the county code of the CASE_LOAD row for the STAFF_PERSON_CASE_LOAD row with FKSTFPERST =
    // '0X5' is "20"
    final String COUNTY_CODE = "20";

    gov.ca.cwds.rest.api.domain.cms.Assignment da =
        createDefaultAssignmentToCaseLoad(COUNTY_CODE, referralId, caseLoadId);
    messageBuilder.addDomainValidationError(validator.validate(da));

    if ("R".equals(da.getEstablishedForCode())
        && ("P".equals(da.getTypeOfAssignmentCode()) || ("S".equals(da.getTypeOfAssignmentCode())))
        || (da.getSecondaryAssignmentRoleType() == 143)) {
      externalInterfaceTables.createExternalInterfaceReferral(referralId, "C");
    }

    try {
      this.createWithSingleTimestamp(da, timestamp);
    } catch (ServiceException e) {
      String message = e.getMessage();
      messageBuilder.addMessageAndLog(message, e, LOGGER);
    }
  }

  /**
   * @param countyCode - county code
   * @param referralId - referral Id
   * @return - default Assignment
   */
  private gov.ca.cwds.rest.api.domain.cms.Assignment createDefaultAssignmentToCaseLoad(
      String countyCode, String referralId, String caseLoadId) {
    // #146713651 - BARNEY: Referrals require a default assignment
    // Default Assignment - referrals will be assigned to the '0X5' staff person ID.
    //
    // An assignment is the association between a Staff Person Case Load and the Referral
    //

    return gov.ca.cwds.rest.api.domain.cms.Assignment.createDefaultReferralAssignment(countyCode,
        referralId, caseLoadId);

  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#update(java.io.Serializable,
   *      gov.ca.cwds.rest.api.Request)
   */
  @Override
  public gov.ca.cwds.rest.api.domain.cms.Assignment update(String primaryKey,
      gov.ca.cwds.rest.api.domain.cms.Assignment request) {
    gov.ca.cwds.rest.api.domain.cms.Assignment assignment = request;

    try {
      String lastUpdatedId = staffPersonIdRetriever.getStaffPersonId();
      Assignment managed = new Assignment(primaryKey, assignment, lastUpdatedId);
      managed = assignmentDao.update(managed);
      externalInterfaceTables.createExtInterAssignment(managed, "C");
      return new gov.ca.cwds.rest.api.domain.cms.Assignment(managed);
    } catch (EntityNotFoundException e) {
      LOGGER.info("Assignment not found : {}", assignment);
      throw new ServiceException(e);
    }
  }

}
