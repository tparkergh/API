package gov.ca.cwds.rest.api.domain.cms;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.DomainChef;
import gov.ca.cwds.rest.api.domain.DomainObject;
import io.dropwizard.validation.OneOf;
import io.swagger.annotations.ApiModelProperty;

/**
 * {@link DomainObject} representing a ChildClient
 * 
 * @author CWDS API Team
 */
public class ChildClient extends DomainObject implements Request, Response {

  /**
   * Serialization version
   */
  private static final long serialVersionUID = 1L;

  @NotNull
  @Size(min = 10, max = 10)
  @ApiModelProperty(required = true, readOnly = true, value = "CLIENT ID of victim",
      example = "ABC1234567")
  private String victimClientId;

  @NotEmpty
  @Size(min = 1, max = 2)
  @OneOf(value = {"N", "Y", "NA"}, ignoreCase = true, ignoreWhitespace = true)
  @ApiModelProperty(required = true, readOnly = false, value = "", example = "N")
  private String adoptableCode;

  @NotNull
  @ApiModelProperty(required = false, readOnly = false, example = "1234")
  private Short adoptedAge;

  @NotNull
  @ApiModelProperty(required = true, readOnly = false)
  private Boolean afdcFcEligibilityIndicatorVar;

  @NotNull
  @ApiModelProperty(required = true, readOnly = false)
  private Boolean allEducationInfoOnFileIndicator;

  @NotNull
  @ApiModelProperty(required = true, readOnly = false)
  private Boolean allHealthInfoOnFileIndicator;

  @NotNull
  @Size(min = 1, max = 254)
  @ApiModelProperty(required = false, readOnly = false, value = "", example = "Eucation")
  private String attemptToAcquireEducInfoDesc;

  @NotNull
  @Size(min = 1, max = 254)
  @ApiModelProperty(required = false, readOnly = false, value = "", example = "Health")
  private String attemptToAcquireHlthInfoDesc;

  @NotNull
  @Size(min = 1, max = 1, message = "size must be 1")
  @ApiModelProperty(required = true, readOnly = false, value = "", example = "N")
  private String awolAbductedCode;

  @NotNull
  @ApiModelProperty(required = true, readOnly = false)
  private Boolean birthHistoryIndicatorVar;

  @NotNull
  @ApiModelProperty(required = false, readOnly = false)
  private Boolean childIndianAncestryIndicator;

  @ApiModelProperty(required = false, readOnly = false)
  private Boolean collegeIndicator;

  @Size(max = 10)
  @ApiModelProperty(required = false, readOnly = false, value = "", example = "ABC1234567")
  private String currentCaseId;

  @NotNull
  @ApiModelProperty(required = false, readOnly = false, example = "1234")
  private Short deathCircumstancesType;

  @Size(min = 1, max = 1)
  @OneOf(value = {"N", "Y", "D"}, ignoreCase = true, ignoreWhitespace = true)
  @ApiModelProperty(required = false, readOnly = false, value = "", example = "N")
  private String disabilityDiagnosedCode;

  @Size(max = 10)
  @ApiModelProperty(required = false, readOnly = false, value = "", example = "Old")
  private String drmsHePassportDocOld;

  @Size(max = 10)
  @ApiModelProperty(required = false, readOnly = false, value = "", example = "Document")
  private String drmsHealthEducPassportDoc;

  @Size(max = 10)
  @ApiModelProperty(required = false, readOnly = false, value = "", example = "Agreement")
  private String drmsVoluntaryPlcmntAgrmntDoc;

  @NotNull
  @ApiModelProperty(required = true, readOnly = false)
  private Boolean fc2EligApplicationIndicatorVar;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_FORMAT)
  @JsonProperty(value = "foodStampsApplicationDate")
  @gov.ca.cwds.rest.validation.Date(format = DATE_FORMAT, required = false)
  @ApiModelProperty(required = false, readOnly = false, value = "yyyy-MM-dd",
      example = "2000-01-01")
  private String foodStampsApplicationDate;

  @NotNull
  @ApiModelProperty(required = true, readOnly = false)
  private Boolean foodStampsApplicationIndicator;

  @NotEmpty
  @Size(min = 1, max = 1, message = "size must be 1")
  @OneOf(value = {"Y", "N", "U", "P"}, ignoreCase = true, ignoreWhitespace = true)
  @ApiModelProperty(required = true, readOnly = false, value = "", example = "N")
  private String icwaEligibilityCode;

  @NotNull
  @ApiModelProperty(required = true, readOnly = false)
  private Boolean intercountryAdoptDisruptedIndicator;

  @NotNull
  @ApiModelProperty(required = true, readOnly = false)
  private Boolean intercountryAdoptDissolvedIndicator;

  @NotNull
  @ApiModelProperty(required = true, readOnly = false)
  private Boolean medEligibilityApplicationIndicatorVar;

  @NotNull
  @ApiModelProperty(required = true, readOnly = false)
  private Boolean minorNmdParentIndicator;

  @NotNull
  @ApiModelProperty(required = true, readOnly = false)
  private Boolean parentalRightsLimitedIndicator;

  @NotNull
  @ApiModelProperty(required = true, readOnly = false)
  private Boolean parentalRightsTermintnIndicatorVar;

  @NotNull
  @ApiModelProperty(required = true, readOnly = false)
  private Boolean paternityIndividualIndicatorVar;

  @ApiModelProperty(required = false, readOnly = false)
  private Boolean postsecVocIndicator;

  @NotEmpty
  @Size(min = 1, max = 1)
  @ApiModelProperty(required = true, readOnly = false, value = "", example = "N")
  private String previouslyAdoptedCode;

  @NotNull
  @ApiModelProperty(required = true, readOnly = false)
  private Boolean safelySurrendedBabiesIndicatorVar;

  @NotNull
  @ApiModelProperty(required = true, readOnly = false)
  private Boolean saw1EligApplicationIndicatorVar;

  @NotNull
  @ApiModelProperty(required = false, readOnly = false, value = "", example = "0")
  private Integer sawsCaseSerialNumber;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_FORMAT)
  @JsonProperty(value = "sijsScheduledInterviewDate")
  @gov.ca.cwds.rest.validation.Date(format = DATE_FORMAT, required = false)
  @ApiModelProperty(required = false, readOnly = false, value = "yyyy-MM-dd",
      example = "2000-01-01")
  private String sijsScheduledInterviewDate;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_FORMAT)
  @JsonProperty(value = "siiNextScreeningDueDate")
  @gov.ca.cwds.rest.validation.Date(format = DATE_FORMAT, required = false)
  @ApiModelProperty(required = false, readOnly = false, value = "yyyy-MM-dd",
      example = "2000-01-01")
  private String siiNextScreeningDueDate;

  @NotNull
  @ApiModelProperty(required = true, readOnly = false)
  private Boolean ssiSspApplicationIndicator;

  @NotNull
  @ApiModelProperty(required = true, readOnly = false)
  private Boolean tribalAncestryNotifctnIndicatorVar;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_FORMAT)
  @JsonProperty(value = "tribalCustomaryAdoptionDate")
  @gov.ca.cwds.rest.validation.Date(format = DATE_FORMAT, required = false)
  @ApiModelProperty(required = false, readOnly = false, value = "yyyy-MM-dd",
      example = "2000-01-01")
  private String tribalCustomaryAdoptionDate;

  @NotNull
  @ApiModelProperty(required = true, readOnly = false)
  private Boolean tribalCustomaryAdoptionIndicator;

  /**
   * Construct from all fields.
   * 
   * @param victimClientId "victim client id" makes record unique
   * @param adoptableCode adoptable code
   * @param adoptedAge adoptable age
   * @param afdcFcEligibilityIndicatorVar afdc Fc eligibility indicator var
   * @param allEducationInfoOnFileIndicator all education info on file indicator
   * @param allHealthInfoOnFileIndicator all health info on file indicator
   * @param attemptToAcquireEducInfoDesc attempt to acquire education info desc
   * @param attemptToAcquireHlthInfoDesc attempt to acquire health info desc
   * @param awolAbductedCode awol abducted code
   * @param birthHistoryIndicatorVar birth history indicator var
   * @param childIndianAncestryIndicator child indian ancestry indicator
   * @param collegeIndicator college indicator
   * @param currentCaseId current case Id
   * @param deathCircumstancesType death circumstances type
   * @param disabilityDiagnosedCode disability diagnosed code
   * @param drmsHePassportDocOld drms he passport doc old
   * @param drmsHealthEducPassportDoc drms health edu passport doc
   * @param drmsVoluntaryPlcmntAgrmntDoc drms coluntary plcmt agreement doc
   * @param fc2EligApplicationIndicatorVar fc2 elig application indicator
   * @param foodStampsApplicationDate food stamps application date
   * @param foodStampsApplicationIndicator food stamps application indicator
   * @param icwaEligibilityCode icwa eligibity code
   * @param intercountryAdoptDisruptedIndicator inter country adopt disrupted indiactor
   * @param intercountryAdoptDissolvedIndicator inter country adopt dissolved indiactor
   * @param medEligibilityApplicationIndicatorVar med eligibilty application indicator
   * @param minorNmdParentIndicator minor nmd parent Indiactor
   * @param parentalRightsLimitedIndicator parental rights limited indicator
   * @param parentalRightsTermintnIndicatorVar parental rights termination indicator
   * @param paternityIndividualIndicatorVar paternity individual indicator var
   * @param postsecVocIndicator post sec voc indicator
   * @param previouslyAdoptedCode previously adopted code
   * @param safelySurrendedBabiesIndicatorVar safely surrended babies indicator var
   * @param saw1EligApplicationIndicatorVar saw1 elig application indcator
   * @param sawsCaseSerialNumber saws case serial number
   * @param sijsScheduledInterviewDate sijs schedules interview date
   * @param siiNextScreeningDueDate sii next screening date
   * @param ssiSspApplicationIndicator ssi ssp application indicator
   * @param tribalAncestryNotifctnIndicatorVar tribal ancestry notication indicator car
   * @param tribalCustomaryAdoptionDate tribal customary adoption date
   * @param tribalCustomaryAdoptionIndicator tribal customary adoption indicator
   */
  @JsonCreator
  public ChildClient(@JsonProperty("victimClientId") String victimClientId,
      @JsonProperty("adoptableCode") String adoptableCode,
      @JsonProperty("adoptedAge") Short adoptedAge,
      @JsonProperty("afdcFcEligibilityIndicatorVar") Boolean afdcFcEligibilityIndicatorVar,
      @JsonProperty("allEducationInfoOnFileIndicator") Boolean allEducationInfoOnFileIndicator,
      @JsonProperty("allHealthInfoOnFileIndicator") Boolean allHealthInfoOnFileIndicator,
      @JsonProperty("attemptToAcquireEducInfoDesc") String attemptToAcquireEducInfoDesc,
      @JsonProperty("attemptToAcquireHlthInfoDesc") String attemptToAcquireHlthInfoDesc,
      @JsonProperty("awolAbductedCode") String awolAbductedCode,
      @JsonProperty("birthHistoryIndicatorVar") Boolean birthHistoryIndicatorVar,
      @JsonProperty("childIndianAncestryIndicator") Boolean childIndianAncestryIndicator,
      @JsonProperty("collegeIndicator") Boolean collegeIndicator,
      @JsonProperty("currentCaseId") String currentCaseId,
      @JsonProperty("deathCircumstancesType") Short deathCircumstancesType,
      @JsonProperty("disabilityDiagnosedCode") String disabilityDiagnosedCode,
      @JsonProperty("drmsHePassportDocOld") String drmsHePassportDocOld,
      @JsonProperty("drmsHealthEducPassportDoc") String drmsHealthEducPassportDoc,
      @JsonProperty("drmsVoluntaryPlcmntAgrmntDoc") String drmsVoluntaryPlcmntAgrmntDoc,
      @JsonProperty("fc2EligApplicationIndicatorVar") Boolean fc2EligApplicationIndicatorVar,
      @JsonProperty("foodStampsApplicationDate") String foodStampsApplicationDate,
      @JsonProperty("foodStampsApplicationIndicator") Boolean foodStampsApplicationIndicator,
      @JsonProperty("icwaEligibilityCode") String icwaEligibilityCode,
      @JsonProperty("intercountryAdoptDisruptedIndicator") Boolean intercountryAdoptDisruptedIndicator,
      @JsonProperty("intercountryAdoptDissolvedIndicator") Boolean intercountryAdoptDissolvedIndicator,
      @JsonProperty("medEligibilityApplicationIndicatorVar") Boolean medEligibilityApplicationIndicatorVar,
      @JsonProperty("minorNmdParentIndicator") Boolean minorNmdParentIndicator,
      @JsonProperty("parentalRightsLimitedIndicator") Boolean parentalRightsLimitedIndicator,
      @JsonProperty("parentalRightsTermintnIndicatorVar") Boolean parentalRightsTermintnIndicatorVar,
      @JsonProperty("paternityIndividualIndicatorVar") Boolean paternityIndividualIndicatorVar,
      @JsonProperty("postsecVocIndicator") Boolean postsecVocIndicator,
      @JsonProperty("previouslyAdoptedCode") String previouslyAdoptedCode,
      @JsonProperty("safelySurrendedBabiesIndicatorVar") Boolean safelySurrendedBabiesIndicatorVar,
      @JsonProperty("saw1EligApplicationIndicatorVar") Boolean saw1EligApplicationIndicatorVar,
      @JsonProperty("sawsCaseSerialNumber") Integer sawsCaseSerialNumber,
      @JsonProperty("sijsScheduledInterviewDate") String sijsScheduledInterviewDate,
      @JsonProperty("siiNextScreeningDueDate") String siiNextScreeningDueDate,
      @JsonProperty("ssiSspApplicationIndicator") Boolean ssiSspApplicationIndicator,
      @JsonProperty("tribalAncestryNotifctnIndicatorVar") Boolean tribalAncestryNotifctnIndicatorVar,
      @JsonProperty("tribalCustomaryAdoptionDate") String tribalCustomaryAdoptionDate,
      @JsonProperty("tribalCustomaryAdoptionIndicator") Boolean tribalCustomaryAdoptionIndicator) {
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
   * Construct from sibling persistence class.
   * 
   * @param persistedChildClient whether the child client is persisted
   */
  public ChildClient(gov.ca.cwds.data.persistence.cms.ChildClient persistedChildClient) {
    this.victimClientId = persistedChildClient.getVictimClientId();
    this.adoptableCode = persistedChildClient.getAdoptableCode();
    this.adoptedAge = persistedChildClient.getAdoptedAge();
    this.afdcFcEligibilityIndicatorVar =
        DomainChef.uncookBooleanString(persistedChildClient.getAfdcFcEligibilityIndicatorVar());
    this.allEducationInfoOnFileIndicator =
        DomainChef.uncookBooleanString(persistedChildClient.getAllEducationInfoOnFileIndicator());
    this.allHealthInfoOnFileIndicator =
        DomainChef.uncookBooleanString(persistedChildClient.getAllHealthInfoOnFileIndicator());
    this.attemptToAcquireEducInfoDesc = persistedChildClient.getAttemptToAcquireEducInfoDesc();
    this.attemptToAcquireHlthInfoDesc = persistedChildClient.getAttemptToAcquireHlthInfoDesc();
    this.awolAbductedCode = persistedChildClient.getAwolAbductedCode();
    this.birthHistoryIndicatorVar =
        DomainChef.uncookBooleanString(persistedChildClient.getBirthHistoryIndicatorVar());
    this.childIndianAncestryIndicator =
        DomainChef.uncookBooleanString(persistedChildClient.getChildIndianAncestryIndicator());
    this.collegeIndicator =
        DomainChef.uncookBooleanString(persistedChildClient.getCollegeIndicator());
    this.currentCaseId = persistedChildClient.getCurrentCaseId();
    this.deathCircumstancesType = persistedChildClient.getDeathCircumstancesType();
    this.disabilityDiagnosedCode = persistedChildClient.getDisabilityDiagnosedCode();
    this.drmsHePassportDocOld = persistedChildClient.getDrmsHePassportDocOld();
    this.drmsHealthEducPassportDoc = persistedChildClient.getDrmsHealthEducPassportDoc();
    this.drmsVoluntaryPlcmntAgrmntDoc = persistedChildClient.getDrmsVoluntaryPlcmntAgrmntDoc();
    this.fc2EligApplicationIndicatorVar =
        DomainChef.uncookBooleanString(persistedChildClient.getFc2EligApplicationIndicatorVar());
    this.foodStampsApplicationDate =
        DomainChef.cookDate(persistedChildClient.getFoodStampsApplicationDate());
    this.foodStampsApplicationIndicator =
        DomainChef.uncookBooleanString(persistedChildClient.getFoodStampsApplicationIndicator());
    this.icwaEligibilityCode = persistedChildClient.getIcwaEligibilityCode();
    this.intercountryAdoptDisruptedIndicator = DomainChef
        .uncookBooleanString(persistedChildClient.getIntercountryAdoptDisruptedIndicator());
    this.intercountryAdoptDissolvedIndicator = DomainChef
        .uncookBooleanString(persistedChildClient.getIntercountryAdoptDissolvedIndicator());
    this.medEligibilityApplicationIndicatorVar = DomainChef
        .uncookBooleanString(persistedChildClient.getMedEligibilityApplicationIndicatorVar());
    this.minorNmdParentIndicator =
        DomainChef.uncookBooleanString(persistedChildClient.getMinorNmdParentIndicator());
    this.parentalRightsLimitedIndicator =
        DomainChef.uncookBooleanString(persistedChildClient.getParentalRightsLimitedIndicator());
    this.parentalRightsTermintnIndicatorVar = DomainChef
        .uncookBooleanString(persistedChildClient.getParentalRightsTermintnIndicatorVar());
    this.paternityIndividualIndicatorVar =
        DomainChef.uncookBooleanString(persistedChildClient.getPaternityIndividualIndicatorVar());
    this.postsecVocIndicator =
        DomainChef.uncookBooleanString(persistedChildClient.getPostsecVocIndicator());
    this.previouslyAdoptedCode = persistedChildClient.getPreviouslyAdoptedCode();
    this.safelySurrendedBabiesIndicatorVar =
        DomainChef.uncookBooleanString(persistedChildClient.getSafelySurrendedBabiesIndicatorVar());
    this.saw1EligApplicationIndicatorVar =
        DomainChef.uncookBooleanString(persistedChildClient.getSaw1EligApplicationIndicatorVar());
    this.sawsCaseSerialNumber = persistedChildClient.getSawsCaseSerialNumber();
    this.sijsScheduledInterviewDate =
        DomainChef.cookDate(persistedChildClient.getSijsScheduledInterviewDate());
    this.siiNextScreeningDueDate =
        DomainChef.cookDate(persistedChildClient.getSiiNextScreeningDueDate());
    this.ssiSspApplicationIndicator =
        DomainChef.uncookBooleanString(persistedChildClient.getSsiSspApplicationIndicator());
    this.tribalAncestryNotifctnIndicatorVar = DomainChef
        .uncookBooleanString(persistedChildClient.getTribalAncestryNotifctnIndicatorVar());
    this.tribalCustomaryAdoptionDate =
        DomainChef.cookDate(persistedChildClient.getTribalCustomaryAdoptionDate());
    this.tribalCustomaryAdoptionIndicator =
        DomainChef.uncookBooleanString(persistedChildClient.getTribalCustomaryAdoptionIndicator());
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
  public Boolean getAfdcFcEligibilityIndicatorVar() {
    return afdcFcEligibilityIndicatorVar;
  }

  /**
   * @return the return allEducationInfoOnFileIndicator;
   * 
   */
  public Boolean getAllEducationInfoOnFileIndicator() {
    return allEducationInfoOnFileIndicator;
  }

  /**
   * @return the allHealthInfoOnFileIndicator
   */
  public Boolean getAllHealthInfoOnFileIndicator() {
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
  public Boolean getBirthHistoryIndicatorVar() {
    return birthHistoryIndicatorVar;
  }

  /**
   * @return the childIndianAncestryIndicator
   */
  public Boolean getChildIndianAncestryIndicator() {
    return childIndianAncestryIndicator;
  }

  /**
   * @return the collegeIndicator
   */
  public Boolean getCollegeIndicator() {
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
  public Boolean getFc2EligApplicationIndicatorVar() {
    return fc2EligApplicationIndicatorVar;
  }

  /**
   * @return the fc2EligApplicationIndicatorVar
   */
  public String getFoodStampsApplicationDate() {
    return foodStampsApplicationDate;
  }

  /**
   * @return the foodStampsApplicationIndicator
   */
  public Boolean getFoodStampsApplicationIndicator() {
    return foodStampsApplicationIndicator;
  }

  /**
   * @return the icwaEligibilityCode
   */
  public String getIcwaEligibilityCode() {
    return icwaEligibilityCode;
  }

  /**
   * @return the intercountryAdoptDisruptedIndicator
   */
  public Boolean getIntercountryAdoptDisruptedIndicator() {
    return intercountryAdoptDisruptedIndicator;
  }

  /**
   * @return the intercountryAdoptDissolvedIndicator
   */
  public Boolean getIntercountryAdoptDissolvedIndicator() {
    return intercountryAdoptDissolvedIndicator;
  }

  /**
   * @return the medEligibilityApplicationIndicatorVar
   */
  public Boolean getMedEligibilityApplicationIndicatorVar() {
    return medEligibilityApplicationIndicatorVar;
  }

  /**
   * @return the minorNmdParentIndicator
   */
  public Boolean getMinorNmdParentIndicator() {
    return minorNmdParentIndicator;
  }

  /**
   * @return the parentalRightsLimitedIndicator
   */
  public Boolean getParentalRightsLimitedIndicator() {
    return parentalRightsLimitedIndicator;
  }

  /**
   * @return the parentalRightsTermintnIndicatorVar
   */
  public Boolean getParentalRightsTermintnIndicatorVar() {
    return parentalRightsTermintnIndicatorVar;
  }

  /**
   * @return the paternityIndividualIndicatorVar
   */
  public Boolean getPaternityIndividualIndicatorVar() {
    return paternityIndividualIndicatorVar;
  }

  /**
   * @return the postsecVocIndicator
   */
  public Boolean getPostsecVocIndicator() {
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
  public Boolean getSafelySurrendedBabiesIndicatorVar() {
    return safelySurrendedBabiesIndicatorVar;
  }

  /**
   * @return the saw1EligApplicationIndicatorVar
   */
  public Boolean getSaw1EligApplicationIndicatorVar() {
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
  public String getSijsScheduledInterviewDate() {
    return sijsScheduledInterviewDate;
  }

  /**
   * @return the siiNextScreeningDueDate
   */
  public String getSiiNextScreeningDueDate() {
    return siiNextScreeningDueDate;
  }

  /**
   * @return the ssiSspApplicationIndicator
   */
  public Boolean getSsiSspApplicationIndicator() {
    return ssiSspApplicationIndicator;
  }

  /**
   * @return the tribalAncestryNotifctnIndicatorVar
   */
  public Boolean getTribalAncestryNotifctnIndicatorVar() {
    return tribalAncestryNotifctnIndicatorVar;
  }

  /**
   * @return the tribalCustomaryAdoptionDate
   */
  public String getTribalCustomaryAdoptionDate() {
    return tribalCustomaryAdoptionDate;
  }

  /**
   * @return the tribalCustomaryAdoptionIndicator
   */
  public Boolean getTribalCustomaryAdoptionIndicator() {
    return tribalCustomaryAdoptionIndicator;
  }

  /**
   * {@inheritDoc}
   *
   * @see java.lang.Object#hashCode()
   */
  @Override
  public final int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this, false);
  }

  /**
   * {@inheritDoc}
   *
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public final boolean equals(Object obj) {
    return EqualsBuilder.reflectionEquals(this, obj, false);
  }

}
