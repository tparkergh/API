package gov.ca.cwds.data.persistence.cms;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PersistenceException;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import gov.ca.cwds.rest.api.ApiException;
import gov.ca.cwds.rest.api.domain.DomainChef;

/**
 * {@link CmsPersistentObject} Class representing a Child Client.
 * 
 * @author CWDS API Team
 */
@Entity
@Table(name = "CHLD_CLT")
@JsonPropertyOrder(alphabetic = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ChildClient extends CmsPersistentObject {

  /**
   * Serialization version
   */
  private static final long serialVersionUID = 1L;

  @Id
  @Column(name = "FKCLIENT_T", length = CMS_ID_LEN)
  private String victimClientId;

  @Column(name = "ADOPTBL_CD")
  private String adoptableCode;

  @Type(type = "short")
  @Column(name = "ADPT_AGE")
  private Short adoptedAge;

  @Column(name = "FC_ELIGT_B")
  private String afdcFcEligibilityIndicatorVar;

  @Column(name = "EDONFL_IND")
  private String allEducationInfoOnFileIndicator;

  @Column(name = "HLONFL_IND")
  private String allHealthInfoOnFileIndicator;

  @Column(name = "ACQ_ED_DSC")
  private String attemptToAcquireEducInfoDesc;

  @Column(name = "ACQ_HTHDSC")
  private String attemptToAcquireHlthInfoDesc;

  @Column(name = "AWOL_AB_CD")
  private String awolAbductedCode;

  @Column(name = "BHIST_IND")
  private String birthHistoryIndicatorVar;

  @Column(name = "INDIAN_IND")
  private String childIndianAncestryIndicator;

  @Column(name = "CLG_IND")
  private String collegeIndicator;

  @Column(name = "CURNT_CSID")
  private String currentCaseId;

  @Type(type = "short")
  @Column(name = "DTH_CIRC")
  private Short deathCircumstancesType;

  @Column(name = "DISABLD_CD")
  private String disabilityDiagnosedCode;

  @Column(name = "HEPDOC_OLD")
  private String drmsHePassportDocOld;

  @Column(name = "DRMSHEPDOC")
  private String drmsHealthEducPassportDoc;

  @Column(name = "VOL_PLCDOC")
  private String drmsVoluntaryPlcmntAgrmntDoc;

  @Column(name = "FC2_APLT_B")
  private String fc2EligApplicationIndicatorVar;

  @Column(name = "FSTAMP_DT")
  private Date foodStampsApplicationDate;

  @Column(name = "FSTAMP_IND")
  private String foodStampsApplicationIndicator;

  @Column(name = "ICWA_ELGCD")
  private String icwaEligibilityCode;

  @Column(name = "ICADSR_IND")
  private String intercountryAdoptDisruptedIndicator;

  @Column(name = "ICADSL_IND")
  private String intercountryAdoptDissolvedIndicator;

  @Column(name = "MEDELIGT_B")
  private String medEligibilityApplicationIndicatorVar;

  @Column(name = "MNRMOM_IND")
  private String minorNmdParentIndicator;

  @Column(name = "PRTLIM_IND")
  private String parentalRightsLimitedIndicator;

  @Column(name = "PRG_TRMT_B")
  private String parentalRightsTermintnIndicatorVar;

  @Column(name = "PTRN_INT_B")
  private String paternityIndividualIndicatorVar;

  @Column(name = "PSVOC_IND")
  private String postsecVocIndicator;

  @Column(name = "PREADPT_CD")
  private String previouslyAdoptedCode;

  @Column(name = "SFSURB_IND")
  private String safelySurrendedBabiesIndicatorVar;

  @Column(name = "SAW1APLT_B")
  private String saw1EligApplicationIndicatorVar;

  @Type(type = "integer")
  @Column(name = "SAWS_CS_NO")
  private Integer sawsCaseSerialNumber;

  @Type(type = "date")
  @Column(name = "SIJ_INT_DT")
  private Date sijsScheduledInterviewDate;

  @Type(type = "date")
  @Column(name = "SSI_SCR_DT")
  private Date siiNextScreeningDueDate;

  @Column(name = "SSI_SSPIND")
  private String ssiSspApplicationIndicator;

  @Column(name = "TRBA_NOT_B")
  private String tribalAncestryNotifctnIndicatorVar;

  @Type(type = "date")
  @Column(name = "TCADPT_DT")
  private Date tribalCustomaryAdoptionDate;

  @Column(name = "TCADPT_IND")
  private String tribalCustomaryAdoptionIndicator;

  /**
   * referential integrity check.
   * <p>
   * Doesn't actually load the data. Just checks the existence of the parent client record.
   * </p>
   */
  @OneToOne(optional = false)
  @JoinColumn(name = "FKCLIENT_T", nullable = false, updatable = false, insertable = false)
  private Client client;


  /**
   * Default constructor
   * 
   * Required for Hibernate
   */
  public ChildClient() {
    super();
  }

  /**
   * @param victimClientId - unique Id
   * @param adoptableCode - adoptableCode
   * @param adoptedAge - adoptedAge
   * @param afdcFcEligibilityIndicatorVar - afdcFcEligibilityIndicatorVar
   * @param allEducationInfoOnFileIndicator - allEducationInfoOnFileIndicator
   * @param allHealthInfoOnFileIndicator - allHealthInfoOnFileIndicator
   * @param attemptToAcquireEducInfoDesc - attemptToAcquireEducInfoDesc
   * @param attemptToAcquireHlthInfoDesc - attemptToAcquireHlthInfoDesc
   * @param awolAbductedCode - awolAbductedCode
   * @param birthHistoryIndicatorVar - birthHistoryIndicatorVar
   * @param childIndianAncestryIndicator - childIndianAncestryIndicator
   * @param collegeIndicator - collegeIndicator
   * @param currentCaseId - currentCaseId
   * @param deathCircumstancesType - deathCircumstancesType
   * @param disabilityDiagnosedCode - disabilityDiagnosedCode
   * @param drmsHePassportDocOld - drmsHePassportDocOld
   * @param drmsHealthEducPassportDoc - drmsHealthEducPassportDoc
   * @param drmsVoluntaryPlcmntAgrmntDoc - drmsVoluntaryPlcmntAgrmntDoc
   * @param fc2EligApplicationIndicatorVar - fc2EligApplicationIndicatorVar
   * @param foodStampsApplicationDate - foodStampsApplicationDate
   * @param foodStampsApplicationIndicator - foodStampsApplicationIndicator
   * @param icwaEligibilityCode - icwaEligibilityCode
   * @param intercountryAdoptDisruptedIndicator - intercountryAdoptDisruptedIndicator
   * @param intercountryAdoptDissolvedIndicator - intercountryAdoptDissolvedIndicator
   * @param medEligibilityApplicationIndicatorVar - medEligibilityApplicationIndicatorVar
   * @param minorNmdParentIndicator - minorNmdParentIndicator
   * @param parentalRightsLimitedIndicator - parentalRightsLimitedIndicator
   * @param parentalRightsTermintnIndicatorVar - parentalRightsTermintnIndicatorVar
   * @param paternityIndividualIndicatorVar - paternityIndividualIndicatorVar
   * @param postsecVocIndicator - postsecVocIndicator
   * @param previouslyAdoptedCode - previouslyAdoptedCode
   * @param safelySurrendedBabiesIndicatorVar - safelySurrendedBabiesIndicatorVar
   * @param saw1EligApplicationIndicatorVar - saw1EligApplicationIndicatorVar
   * @param sawsCaseSerialNumber - sawsCaseSerialNumber
   * @param sijsScheduledInterviewDate - sijsScheduledInterviewDate
   * @param siiNextScreeningDueDate - siiNextScreeningDueDate
   * @param ssiSspApplicationIndicator - ssiSspApplicationIndicator
   * @param tribalAncestryNotifctnIndicatorVar - tribalAncestryNotifctnIndicatorVar
   * @param tribalCustomaryAdoptionDate - tribalCustomaryAdoptionDate
   * @param tribalCustomaryAdoptionIndicator - tribalCustomaryAdoptionIndicator
   * 
   */
  public ChildClient(String victimClientId, String adoptableCode, Short adoptedAge,
      String afdcFcEligibilityIndicatorVar, String allEducationInfoOnFileIndicator,
      String allHealthInfoOnFileIndicator, String attemptToAcquireEducInfoDesc,
      String attemptToAcquireHlthInfoDesc, String awolAbductedCode, String birthHistoryIndicatorVar,
      String childIndianAncestryIndicator, String collegeIndicator, String currentCaseId,
      Short deathCircumstancesType, String disabilityDiagnosedCode, String drmsHePassportDocOld,
      String drmsHealthEducPassportDoc, String drmsVoluntaryPlcmntAgrmntDoc,
      String fc2EligApplicationIndicatorVar, Date foodStampsApplicationDate,
      String foodStampsApplicationIndicator, String icwaEligibilityCode,
      String intercountryAdoptDisruptedIndicator, String intercountryAdoptDissolvedIndicator,
      String medEligibilityApplicationIndicatorVar, String minorNmdParentIndicator,
      String parentalRightsLimitedIndicator, String parentalRightsTermintnIndicatorVar,
      String paternityIndividualIndicatorVar, String postsecVocIndicator,
      String previouslyAdoptedCode, String safelySurrendedBabiesIndicatorVar,
      String saw1EligApplicationIndicatorVar, Integer sawsCaseSerialNumber,
      Date sijsScheduledInterviewDate, Date siiNextScreeningDueDate,
      String ssiSspApplicationIndicator, String tribalAncestryNotifctnIndicatorVar,
      Date tribalCustomaryAdoptionDate, String tribalCustomaryAdoptionIndicator) {
    super();
    this.victimClientId = victimClientId;
    this.adoptableCode = adoptableCode;
    this.adoptedAge = adoptedAge;
    this.afdcFcEligibilityIndicatorVar = afdcFcEligibilityIndicatorVar;
    this.allEducationInfoOnFileIndicator = allEducationInfoOnFileIndicator;
    this.allHealthInfoOnFileIndicator = allHealthInfoOnFileIndicator;
    this.attemptToAcquireEducInfoDesc = attemptToAcquireEducInfoDesc;
    this.attemptToAcquireHlthInfoDesc = attemptToAcquireHlthInfoDesc;
    this.awolAbductedCode = awolAbductedCode;
    this.birthHistoryIndicatorVar = birthHistoryIndicatorVar;
    this.childIndianAncestryIndicator = childIndianAncestryIndicator;
    this.collegeIndicator = collegeIndicator;
    this.currentCaseId = currentCaseId;
    this.deathCircumstancesType = deathCircumstancesType;
    this.disabilityDiagnosedCode = disabilityDiagnosedCode;
    this.drmsHePassportDocOld = drmsHePassportDocOld;
    this.drmsHealthEducPassportDoc = drmsHealthEducPassportDoc;
    this.drmsVoluntaryPlcmntAgrmntDoc = drmsVoluntaryPlcmntAgrmntDoc;
    this.fc2EligApplicationIndicatorVar = fc2EligApplicationIndicatorVar;
    this.foodStampsApplicationDate = foodStampsApplicationDate;
    this.foodStampsApplicationIndicator = foodStampsApplicationIndicator;
    this.icwaEligibilityCode = icwaEligibilityCode;
    this.intercountryAdoptDisruptedIndicator = intercountryAdoptDisruptedIndicator;
    this.intercountryAdoptDissolvedIndicator = intercountryAdoptDissolvedIndicator;
    this.medEligibilityApplicationIndicatorVar = medEligibilityApplicationIndicatorVar;
    this.minorNmdParentIndicator = minorNmdParentIndicator;
    this.parentalRightsLimitedIndicator = parentalRightsLimitedIndicator;
    this.parentalRightsTermintnIndicatorVar = parentalRightsTermintnIndicatorVar;
    this.paternityIndividualIndicatorVar = paternityIndividualIndicatorVar;
    this.postsecVocIndicator = postsecVocIndicator;
    this.previouslyAdoptedCode = previouslyAdoptedCode;
    this.safelySurrendedBabiesIndicatorVar = safelySurrendedBabiesIndicatorVar;
    this.saw1EligApplicationIndicatorVar = saw1EligApplicationIndicatorVar;
    this.sawsCaseSerialNumber = sawsCaseSerialNumber;
    this.sijsScheduledInterviewDate = sijsScheduledInterviewDate;
    this.siiNextScreeningDueDate = siiNextScreeningDueDate;
    this.ssiSspApplicationIndicator = ssiSspApplicationIndicator;
    this.tribalAncestryNotifctnIndicatorVar = tribalAncestryNotifctnIndicatorVar;
    this.tribalCustomaryAdoptionDate = tribalCustomaryAdoptionDate;
    this.tribalCustomaryAdoptionIndicator = tribalCustomaryAdoptionIndicator;
  }

  /**
   * @param victimClientId The victimId is unique key to ChildClient
   * @param childClient The domain object to construct this object from
   * @param lastUpdatedId the id of the last person to update this object
   */
  public ChildClient(String victimClientId, gov.ca.cwds.rest.api.domain.cms.ChildClient childClient,
      String lastUpdatedId) {
    super(lastUpdatedId);

    try {
      this.victimClientId = victimClientId;
      this.adoptableCode = childClient.getAdoptableCode();
      this.adoptedAge = childClient.getAdoptedAge();
      this.afdcFcEligibilityIndicatorVar =
          DomainChef.cookBoolean(childClient.getAfdcFcEligibilityIndicatorVar());
      this.allEducationInfoOnFileIndicator =
          DomainChef.cookBoolean(childClient.getAllEducationInfoOnFileIndicator());
      this.allHealthInfoOnFileIndicator =
          DomainChef.cookBoolean(childClient.getAllHealthInfoOnFileIndicator());
      this.attemptToAcquireEducInfoDesc = childClient.getAttemptToAcquireEducInfoDesc();
      this.attemptToAcquireHlthInfoDesc = childClient.getAttemptToAcquireHlthInfoDesc();
      this.awolAbductedCode = childClient.getAwolAbductedCode();
      this.birthHistoryIndicatorVar =
          DomainChef.cookBoolean(childClient.getBirthHistoryIndicatorVar());
      this.childIndianAncestryIndicator =
          DomainChef.cookBoolean(childClient.getChildIndianAncestryIndicator());
      this.collegeIndicator = DomainChef.cookBoolean(childClient.getCollegeIndicator());
      this.currentCaseId = childClient.getCurrentCaseId();
      this.deathCircumstancesType = childClient.getDeathCircumstancesType();
      this.disabilityDiagnosedCode = childClient.getDisabilityDiagnosedCode();
      this.drmsHePassportDocOld = childClient.getDrmsHePassportDocOld();
      this.drmsHealthEducPassportDoc = childClient.getDrmsHealthEducPassportDoc();
      this.drmsVoluntaryPlcmntAgrmntDoc = childClient.getDrmsVoluntaryPlcmntAgrmntDoc();
      this.fc2EligApplicationIndicatorVar =
          DomainChef.cookBoolean(childClient.getFc2EligApplicationIndicatorVar());
      this.foodStampsApplicationDate =
          DomainChef.uncookDateString(childClient.getFoodStampsApplicationDate());
      this.foodStampsApplicationIndicator =
          DomainChef.cookBoolean(childClient.getFoodStampsApplicationIndicator());
      this.icwaEligibilityCode = childClient.getIcwaEligibilityCode();
      this.intercountryAdoptDisruptedIndicator =
          DomainChef.cookBoolean(childClient.getIntercountryAdoptDisruptedIndicator());
      this.intercountryAdoptDissolvedIndicator =
          DomainChef.cookBoolean(childClient.getIntercountryAdoptDissolvedIndicator());
      this.medEligibilityApplicationIndicatorVar =
          DomainChef.cookBoolean(childClient.getMedEligibilityApplicationIndicatorVar());
      this.minorNmdParentIndicator =
          DomainChef.cookBoolean(childClient.getMinorNmdParentIndicator());
      this.parentalRightsLimitedIndicator =
          DomainChef.cookBoolean(childClient.getParentalRightsLimitedIndicator());
      this.parentalRightsTermintnIndicatorVar =
          DomainChef.cookBoolean(childClient.getParentalRightsTermintnIndicatorVar());
      this.paternityIndividualIndicatorVar =
          DomainChef.cookBoolean(childClient.getPaternityIndividualIndicatorVar());
      this.postsecVocIndicator = DomainChef.cookBoolean(childClient.getPostsecVocIndicator());
      this.previouslyAdoptedCode = childClient.getPreviouslyAdoptedCode();
      this.safelySurrendedBabiesIndicatorVar =
          DomainChef.cookBoolean(childClient.getSafelySurrendedBabiesIndicatorVar());
      this.saw1EligApplicationIndicatorVar =
          DomainChef.cookBoolean(childClient.getSaw1EligApplicationIndicatorVar());
      this.sawsCaseSerialNumber = childClient.getSawsCaseSerialNumber();
      this.sijsScheduledInterviewDate =
          DomainChef.uncookDateString(childClient.getSijsScheduledInterviewDate());
      this.siiNextScreeningDueDate =
          DomainChef.uncookDateString(childClient.getSiiNextScreeningDueDate());
      this.ssiSspApplicationIndicator =
          DomainChef.cookBoolean(childClient.getSsiSspApplicationIndicator());
      this.tribalAncestryNotifctnIndicatorVar =
          DomainChef.cookBoolean(childClient.getTribalAncestryNotifctnIndicatorVar());
      this.tribalCustomaryAdoptionDate =
          DomainChef.uncookDateString(childClient.getTribalCustomaryAdoptionDate());
      this.tribalCustomaryAdoptionIndicator =
          DomainChef.cookBoolean(childClient.getTribalCustomaryAdoptionIndicator());
    } catch (ApiException e) {
      throw new PersistenceException(e);
    }
  }

  /*
   * (non-Javadoc)
   */
  @Override
  public String getPrimaryKey() {
    return getVictimClientId();
  }

  /**
   * @return the victimClientId
   */
  public String getVictimClientId() {
    return victimClientId;
  }

  /**
   * @return the adoptableCode
   */
  public String getAdoptableCode() {
    return adoptableCode;
  }

  /**
   * @return the adoptedAge
   */
  public Short getAdoptedAge() {
    return adoptedAge;
  }

  /**
   * @return the afdcFcEligibilityIndicatorVar
   */
  public String getAfdcFcEligibilityIndicatorVar() {
    return afdcFcEligibilityIndicatorVar;
  }

  /**
   * @return the allEducationInfoOnFileIndicator
   */
  public String getAllEducationInfoOnFileIndicator() {
    return allEducationInfoOnFileIndicator;
  }

  /**
   * @return the allHealthInfoOnFileIndicator
   */
  public String getAllHealthInfoOnFileIndicator() {
    return allHealthInfoOnFileIndicator;
  }

  /**
   * @return the attemptToAcquireEducInfoDesc
   */
  public String getAttemptToAcquireEducInfoDesc() {
    return attemptToAcquireEducInfoDesc;
  }

  /**
   * @return the attemptToAcquireHlthInfoDesc
   */
  public String getAttemptToAcquireHlthInfoDesc() {
    return attemptToAcquireHlthInfoDesc;
  }

  /**
   * @return the awolAbductedCode
   */
  public String getAwolAbductedCode() {
    return awolAbductedCode;
  }

  /**
   * @return the birthHistoryIndicatorVar
   */
  public String getBirthHistoryIndicatorVar() {
    return birthHistoryIndicatorVar;
  }

  /**
   * @return the childIndianAncestryIndicator
   */
  public String getChildIndianAncestryIndicator() {
    return childIndianAncestryIndicator;
  }

  /**
   * @return the collegeIndicator
   */
  public String getCollegeIndicator() {
    return collegeIndicator;
  }

  /**
   * @return the currentCaseId
   */
  public String getCurrentCaseId() {
    return currentCaseId;
  }

  /**
   * @return the deathCircumstancesType
   */
  public Short getDeathCircumstancesType() {
    return deathCircumstancesType;
  }

  /**
   * @return the disabilityDiagnosedCode
   */
  public String getDisabilityDiagnosedCode() {
    return disabilityDiagnosedCode;
  }

  /**
   * @return the drmsHePassportDocOld
   */
  public String getDrmsHePassportDocOld() {
    return drmsHePassportDocOld;
  }

  /**
   * @return the drmsHealthEducPassportDoc
   */
  public String getDrmsHealthEducPassportDoc() {
    return drmsHealthEducPassportDoc;
  }

  /**
   * @return the drmsVoluntaryPlcmntAgrmntDoc
   */
  public String getDrmsVoluntaryPlcmntAgrmntDoc() {
    return drmsVoluntaryPlcmntAgrmntDoc;
  }

  /**
   * @return the fc2EligApplicationIndicatorVar
   */
  public String getFc2EligApplicationIndicatorVar() {
    return fc2EligApplicationIndicatorVar;
  }

  /**
   * @return the foodStampsApplicationDate
   */
  public Date getFoodStampsApplicationDate() {
    return foodStampsApplicationDate;
  }

  /**
   * @return the foodStampsApplicationIndicator
   */
  public String getFoodStampsApplicationIndicator() {
    return foodStampsApplicationIndicator;
  }

  /**
   * @return the icwaEligibilityCode
   */
  public String getIcwaEligibilityCode() {
    return icwaEligibilityCode;
  }

  /**
   * @return intercountryAdoptDisruptedIndicator
   */
  public String getIntercountryAdoptDisruptedIndicator() {
    return intercountryAdoptDisruptedIndicator;
  }

  /**
   * @return the intercountryAdoptDissolvedIndicator
   */
  public String getIntercountryAdoptDissolvedIndicator() {
    return intercountryAdoptDissolvedIndicator;
  }

  /**
   * @return the medEligibilityApplicationIndicatorVar
   */
  public String getMedEligibilityApplicationIndicatorVar() {
    return medEligibilityApplicationIndicatorVar;
  }

  /**
   * @return the minorNmdParentIndicator
   */
  public String getMinorNmdParentIndicator() {
    return minorNmdParentIndicator;
  }

  /**
   * @return the parentalRightsLimitedIndicator
   */
  public String getParentalRightsLimitedIndicator() {
    return parentalRightsLimitedIndicator;
  }

  /**
   * @return the parentalRightsTermintnIndicatorVar
   */
  public String getParentalRightsTermintnIndicatorVar() {
    return parentalRightsTermintnIndicatorVar;
  }

  /**
   * @return the paternityIndividualIndicatorVar
   */
  public String getPaternityIndividualIndicatorVar() {
    return paternityIndividualIndicatorVar;
  }

  /**
   * @return the postsecVocIndicator
   */
  public String getPostsecVocIndicator() {
    return postsecVocIndicator;
  }

  /**
   * @return the previouslyAdoptedCode
   */
  public String getPreviouslyAdoptedCode() {
    return previouslyAdoptedCode;
  }

  /**
   * @return the safelySurrendedBabiesIndicatorVar
   */
  public String getSafelySurrendedBabiesIndicatorVar() {
    return safelySurrendedBabiesIndicatorVar;
  }

  /**
   * @return the saw1EligApplicationIndicatorVar
   */
  public String getSaw1EligApplicationIndicatorVar() {
    return saw1EligApplicationIndicatorVar;
  }

  /**
   * @return the sawsCaseSerialNumber
   */
  public Integer getSawsCaseSerialNumber() {
    return sawsCaseSerialNumber;
  }

  /**
   * @return the sijsScheduledInterviewDate
   */
  public Date getSijsScheduledInterviewDate() {
    return sijsScheduledInterviewDate;
  }

  /**
   * @return the siiNextScreeningDueDate
   */
  public Date getSiiNextScreeningDueDate() {
    return siiNextScreeningDueDate;
  }

  /**
   * @return the ssiSspApplicationIndicator
   */
  public String getSsiSspApplicationIndicator() {
    return ssiSspApplicationIndicator;
  }

  /**
   * @return the tribalAncestryNotifctnIndicatorVar
   */
  public String getTribalAncestryNotifctnIndicatorVar() {
    return tribalAncestryNotifctnIndicatorVar;
  }

  /**
   * @return the tribalCustomaryAdoptionDate
   */
  public Date getTribalCustomaryAdoptionDate() {
    return tribalCustomaryAdoptionDate;
  }

  /**
   * @return the tribalCustomaryAdoptionIndicator
   */
  public String getTribalCustomaryAdoptionIndicator() {
    return tribalCustomaryAdoptionIndicator;
  }

}
