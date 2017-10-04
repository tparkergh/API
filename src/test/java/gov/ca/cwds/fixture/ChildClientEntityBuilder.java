package gov.ca.cwds.fixture;

import java.util.Date;

import gov.ca.cwds.data.persistence.cms.ChildClient;

/**
 * @author CWDS API Team
 *
 */
@SuppressWarnings("javadoc")
public class ChildClientEntityBuilder {

  String victimClientId = "ABC12345ll";
  String adoptableCode = "N";
  Short adoptedAge = 1234;
  String afdcFcEligibilityIndicatorVar = "N";
  String allEducationInfoOnFileIndicator = "N";
  String allHealthInfoOnFileIndicator = "N";
  String attemptToAcquireEducInfoDesc = "Eucation";
  String attemptToAcquireHlthInfoDesc = "Health";
  String awolAbductedCode = "N";
  String birthHistoryIndicatorVar = "N";
  String childIndianAncestryIndicator = "N";
  String collegeIndicator = "N";
  String currentCaseId = "ABC1234567";
  Short deathCircumstancesType = 1234;
  String disabilityDiagnosedCode = "N";
  String drmsHePassportDocOld = "Old";
  String drmsHealthEducPassportDoc = "Document";
  String drmsVoluntaryPlcmntAgrmntDoc = "Agreement";
  String fc2EligApplicationIndicatorVar = "N";
  Date foodStampsApplicationDate = new Date();
  String foodStampsApplicationIndicator = "N";
  String icwaEligibilityCode = "N";
  String intercountryAdoptDisruptedIndicator = "N";
  String intercountryAdoptDissolvedIndicator = "N";
  String medEligibilityApplicationIndicatorVar = "N";
  String minorNmdParentIndicator = "N";
  String parentalRightsLimitedIndicator = "N";
  String parentalRightsTermintnIndicatorVar = "N";
  String paternityIndividualIndicatorVar = "N";
  String postsecVocIndicator = "N";
  String previouslyAdoptedCode = "N";
  String safelySurrendedBabiesIndicatorVar = "N";
  String saw1EligApplicationIndicatorVar = "N";
  Integer sawsCaseSerialNumber = 0;
  Date sijsScheduledInterviewDate = new Date();
  Date siiNextScreeningDueDate = new Date();
  String ssiSspApplicationIndicator = "N";
  String tribalAncestryNotifctnIndicatorVar = "N";
  Date tribalCustomaryAdoptionDate = new Date();
  String tribalCustomaryAdoptionIndicator = "Y";

  public ChildClient build() {
    return new ChildClient(victimClientId, adoptableCode, adoptedAge, afdcFcEligibilityIndicatorVar,
        allEducationInfoOnFileIndicator, allHealthInfoOnFileIndicator, attemptToAcquireEducInfoDesc,
        attemptToAcquireHlthInfoDesc, awolAbductedCode, birthHistoryIndicatorVar,
        childIndianAncestryIndicator, collegeIndicator, currentCaseId, deathCircumstancesType,
        disabilityDiagnosedCode, drmsHePassportDocOld, drmsHealthEducPassportDoc,
        drmsVoluntaryPlcmntAgrmntDoc, fc2EligApplicationIndicatorVar, foodStampsApplicationDate,
        foodStampsApplicationIndicator, icwaEligibilityCode, intercountryAdoptDisruptedIndicator,
        intercountryAdoptDissolvedIndicator, medEligibilityApplicationIndicatorVar,
        minorNmdParentIndicator, parentalRightsLimitedIndicator, parentalRightsTermintnIndicatorVar,
        paternityIndividualIndicatorVar, postsecVocIndicator, previouslyAdoptedCode,
        safelySurrendedBabiesIndicatorVar, saw1EligApplicationIndicatorVar, sawsCaseSerialNumber,
        sijsScheduledInterviewDate, siiNextScreeningDueDate, ssiSspApplicationIndicator,
        tribalAncestryNotifctnIndicatorVar, tribalCustomaryAdoptionDate,
        tribalCustomaryAdoptionIndicator);
  }

  public String getVictimClientId() {
    return victimClientId;
  }

  public ChildClientEntityBuilder setVictimClientId(String victimClientId) {
    this.victimClientId = victimClientId;
    return this;
  }

  public String getAdoptableCode() {
    return adoptableCode;
  }

  public ChildClientEntityBuilder setAdoptableCode(String adoptableCode) {
    this.adoptableCode = adoptableCode;
    return this;
  }

  public Short getAdoptedAge() {
    return adoptedAge;
  }

  public ChildClientEntityBuilder setAdoptedAge(Short adoptedAge) {
    this.adoptedAge = adoptedAge;
    return this;
  }

  public String getAfdcFcEligibilityIndicatorVar() {
    return afdcFcEligibilityIndicatorVar;
  }

  public ChildClientEntityBuilder setAfdcFcEligibilityIndicatorVar(
      String afdcFcEligibilityIndicatorVar) {
    this.afdcFcEligibilityIndicatorVar = afdcFcEligibilityIndicatorVar;
    return this;
  }

  public String getAllEducationInfoOnFileIndicator() {
    return allEducationInfoOnFileIndicator;
  }

  public ChildClientEntityBuilder setAllEducationInfoOnFileIndicator(
      String allEducationInfoOnFileIndicator) {
    this.allEducationInfoOnFileIndicator = allEducationInfoOnFileIndicator;
    return this;
  }

  public String getAllHealthInfoOnFileIndicator() {
    return allHealthInfoOnFileIndicator;
  }

  public ChildClientEntityBuilder setAllHealthInfoOnFileIndicator(
      String allHealthInfoOnFileIndicator) {
    this.allHealthInfoOnFileIndicator = allHealthInfoOnFileIndicator;
    return this;
  }

  public String getAttemptToAcquireEducInfoDesc() {
    return attemptToAcquireEducInfoDesc;
  }

  public ChildClientEntityBuilder setAttemptToAcquireEducInfoDesc(
      String attemptToAcquireEducInfoDesc) {
    this.attemptToAcquireEducInfoDesc = attemptToAcquireEducInfoDesc;
    return this;
  }

  public String getAttemptToAcquireHlthInfoDesc() {
    return attemptToAcquireHlthInfoDesc;
  }

  public ChildClientEntityBuilder setAttemptToAcquireHlthInfoDesc(
      String attemptToAcquireHlthInfoDesc) {
    this.attemptToAcquireHlthInfoDesc = attemptToAcquireHlthInfoDesc;
    return this;
  }

  public String getAwolAbductedCode() {
    return awolAbductedCode;
  }

  public ChildClientEntityBuilder setAwolAbductedCode(String awolAbductedCode) {
    this.awolAbductedCode = awolAbductedCode;
    return this;
  }

  public String getBirthHistoryIndicatorVar() {
    return birthHistoryIndicatorVar;
  }

  public ChildClientEntityBuilder setBirthHistoryIndicatorVar(String birthHistoryIndicatorVar) {
    this.birthHistoryIndicatorVar = birthHistoryIndicatorVar;
    return this;
  }

  public String getChildIndianAncestryIndicator() {
    return childIndianAncestryIndicator;
  }

  public ChildClientEntityBuilder setChildIndianAncestryIndicator(
      String childIndianAncestryIndicator) {
    this.childIndianAncestryIndicator = childIndianAncestryIndicator;
    return this;
  }

  public String getCollegeIndicator() {
    return collegeIndicator;
  }

  public ChildClientEntityBuilder setCollegeIndicator(String collegeIndicator) {
    this.collegeIndicator = collegeIndicator;
    return this;
  }

  public String getCurrentCaseId() {
    return currentCaseId;
  }

  public ChildClientEntityBuilder setCurrentCaseId(String currentCaseId) {
    this.currentCaseId = currentCaseId;
    return this;
  }

  public Short getDeathCircumstancesType() {
    return deathCircumstancesType;
  }

  public ChildClientEntityBuilder setDeathCircumstancesType(Short deathCircumstancesType) {
    this.deathCircumstancesType = deathCircumstancesType;
    return this;
  }

  public String getDisabilityDiagnosedCode() {
    return disabilityDiagnosedCode;
  }

  public ChildClientEntityBuilder setDisabilityDiagnosedCode(String disabilityDiagnosedCode) {
    this.disabilityDiagnosedCode = disabilityDiagnosedCode;
    return this;
  }

  public String getDrmsHePassportDocOld() {
    return drmsHePassportDocOld;
  }

  public ChildClientEntityBuilder setDrmsHePassportDocOld(String drmsHePassportDocOld) {
    this.drmsHePassportDocOld = drmsHePassportDocOld;
    return this;
  }

  public String getDrmsHealthEducPassportDoc() {
    return drmsHealthEducPassportDoc;
  }

  public ChildClientEntityBuilder setDrmsHealthEducPassportDoc(String drmsHealthEducPassportDoc) {
    this.drmsHealthEducPassportDoc = drmsHealthEducPassportDoc;
    return this;
  }

  public String getDrmsVoluntaryPlcmntAgrmntDoc() {
    return drmsVoluntaryPlcmntAgrmntDoc;
  }

  public ChildClientEntityBuilder setDrmsVoluntaryPlcmntAgrmntDoc(
      String drmsVoluntaryPlcmntAgrmntDoc) {
    this.drmsVoluntaryPlcmntAgrmntDoc = drmsVoluntaryPlcmntAgrmntDoc;
    return this;
  }

  public String getFc2EligApplicationIndicatorVar() {
    return fc2EligApplicationIndicatorVar;
  }

  public ChildClientEntityBuilder setFc2EligApplicationIndicatorVar(
      String fc2EligApplicationIndicatorVar) {
    this.fc2EligApplicationIndicatorVar = fc2EligApplicationIndicatorVar;
    return this;
  }

  public Date getFoodStampsApplicationDate() {
    return foodStampsApplicationDate;
  }

  public ChildClientEntityBuilder setFoodStampsApplicationDate(Date foodStampsApplicationDate) {
    this.foodStampsApplicationDate = foodStampsApplicationDate;
    return this;
  }

  public String getFoodStampsApplicationIndicator() {
    return foodStampsApplicationIndicator;
  }

  public ChildClientEntityBuilder setFoodStampsApplicationIndicator(
      String foodStampsApplicationIndicator) {
    this.foodStampsApplicationIndicator = foodStampsApplicationIndicator;
    return this;
  }

  public String getIcwaEligibilityCode() {
    return icwaEligibilityCode;
  }

  public ChildClientEntityBuilder setIcwaEligibilityCode(String icwaEligibilityCode) {
    this.icwaEligibilityCode = icwaEligibilityCode;
    return this;
  }

  public String getIntercountryAdoptDisruptedIndicator() {
    return intercountryAdoptDisruptedIndicator;
  }

  public ChildClientEntityBuilder setIntercountryAdoptDisruptedIndicator(
      String intercountryAdoptDisruptedIndicator) {
    this.intercountryAdoptDisruptedIndicator = intercountryAdoptDisruptedIndicator;
    return this;
  }

  public String getIntercountryAdoptDissolvedIndicator() {
    return intercountryAdoptDissolvedIndicator;
  }

  public ChildClientEntityBuilder setIntercountryAdoptDissolvedIndicator(
      String intercountryAdoptDissolvedIndicator) {
    this.intercountryAdoptDissolvedIndicator = intercountryAdoptDissolvedIndicator;
    return this;
  }

  public String getMedEligibilityApplicationIndicatorVar() {
    return medEligibilityApplicationIndicatorVar;
  }

  public ChildClientEntityBuilder setMedEligibilityApplicationIndicatorVar(
      String medEligibilityApplicationIndicatorVar) {
    this.medEligibilityApplicationIndicatorVar = medEligibilityApplicationIndicatorVar;
    return this;
  }

  public String getMinorNmdParentIndicator() {
    return minorNmdParentIndicator;
  }

  public ChildClientEntityBuilder setMinorNmdParentIndicator(String minorNmdParentIndicator) {
    this.minorNmdParentIndicator = minorNmdParentIndicator;
    return this;
  }

  public String getParentalRightsLimitedIndicator() {
    return parentalRightsLimitedIndicator;
  }

  public ChildClientEntityBuilder setParentalRightsLimitedIndicator(
      String parentalRightsLimitedIndicator) {
    this.parentalRightsLimitedIndicator = parentalRightsLimitedIndicator;
    return this;
  }

  public String getParentalRightsTermintnIndicatorVar() {
    return parentalRightsTermintnIndicatorVar;
  }

  public ChildClientEntityBuilder setParentalRightsTermintnIndicatorVar(
      String parentalRightsTermintnIndicatorVar) {
    this.parentalRightsTermintnIndicatorVar = parentalRightsTermintnIndicatorVar;
    return this;
  }

  public String getPaternityIndividualIndicatorVar() {
    return paternityIndividualIndicatorVar;
  }

  public ChildClientEntityBuilder setPaternityIndividualIndicatorVar(
      String paternityIndividualIndicatorVar) {
    this.paternityIndividualIndicatorVar = paternityIndividualIndicatorVar;
    return this;
  }

  public String getPostsecVocIndicator() {
    return postsecVocIndicator;
  }

  public ChildClientEntityBuilder setPostsecVocIndicator(String postsecVocIndicator) {
    this.postsecVocIndicator = postsecVocIndicator;
    return this;
  }

  public String getPreviouslyAdoptedCode() {
    return previouslyAdoptedCode;
  }

  public ChildClientEntityBuilder setPreviouslyAdoptedCode(String previouslyAdoptedCode) {
    this.previouslyAdoptedCode = previouslyAdoptedCode;
    return this;
  }

  public String getSafelySurrendedBabiesIndicatorVar() {
    return safelySurrendedBabiesIndicatorVar;
  }

  public ChildClientEntityBuilder setSafelySurrendedBabiesIndicatorVar(
      String safelySurrendedBabiesIndicatorVar) {
    this.safelySurrendedBabiesIndicatorVar = safelySurrendedBabiesIndicatorVar;
    return this;
  }

  public String getSaw1EligApplicationIndicatorVar() {
    return saw1EligApplicationIndicatorVar;
  }

  public ChildClientEntityBuilder setSaw1EligApplicationIndicatorVar(
      String saw1EligApplicationIndicatorVar) {
    this.saw1EligApplicationIndicatorVar = saw1EligApplicationIndicatorVar;
    return this;
  }

  public Integer getSawsCaseSerialNumber() {
    return sawsCaseSerialNumber;
  }

  public ChildClientEntityBuilder setSawsCaseSerialNumber(Integer sawsCaseSerialNumber) {
    this.sawsCaseSerialNumber = sawsCaseSerialNumber;
    return this;
  }

  public Date getSijsScheduledInterviewDate() {
    return sijsScheduledInterviewDate;
  }

  public ChildClientEntityBuilder setSijsScheduledInterviewDate(Date sijsScheduledInterviewDate) {
    this.sijsScheduledInterviewDate = sijsScheduledInterviewDate;
    return this;
  }

  public Date getSiiNextScreeningDueDate() {
    return siiNextScreeningDueDate;
  }

  public ChildClientEntityBuilder setSiiNextScreeningDueDate(Date siiNextScreeningDueDate) {
    this.siiNextScreeningDueDate = siiNextScreeningDueDate;
    return this;
  }

  public String getSsiSspApplicationIndicator() {
    return ssiSspApplicationIndicator;
  }

  public ChildClientEntityBuilder setSsiSspApplicationIndicator(String ssiSspApplicationIndicator) {
    this.ssiSspApplicationIndicator = ssiSspApplicationIndicator;
    return this;
  }

  public String getTribalAncestryNotifctnIndicatorVar() {
    return tribalAncestryNotifctnIndicatorVar;
  }

  public ChildClientEntityBuilder setTribalAncestryNotifctnIndicatorVar(
      String tribalAncestryNotifctnIndicatorVar) {
    this.tribalAncestryNotifctnIndicatorVar = tribalAncestryNotifctnIndicatorVar;
    return this;
  }

  public Date getTribalCustomaryAdoptionDate() {
    return tribalCustomaryAdoptionDate;
  }

  public ChildClientEntityBuilder setTribalCustomaryAdoptionDate(Date tribalCustomaryAdoptionDate) {
    this.tribalCustomaryAdoptionDate = tribalCustomaryAdoptionDate;
    return this;
  }

  public String getTribalCustomaryAdoptionIndicator() {
    return tribalCustomaryAdoptionIndicator;
  }

  public ChildClientEntityBuilder setTribalCustomaryAdoptionIndicator(
      String tribalCustomaryAdoptionIndicator) {
    this.tribalCustomaryAdoptionIndicator = tribalCustomaryAdoptionIndicator;
    return this;
  }

}
