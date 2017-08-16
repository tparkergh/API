package gov.ca.cwds.fixture;

import gov.ca.cwds.rest.api.domain.cms.ChildClient;

/**
 * @author CWDS API Team
 *
 */
@SuppressWarnings("javadoc")
public class ChildClientResourceBuilder {

  String victimClientId = "ABC1234567";
  String adoptableCode = "N";
  Short adoptedAge = (short) 1234;
  Boolean afdcFcEligibilityIndicatorVar = false;
  Boolean allEducationInfoOnFileIndicator = false;
  Boolean allHealthInfoOnFileIndicator = false;
  String attemptToAcquireEducInfoDesc = "Eucation";
  String attemptToAcquireHlthInfoDesc = "Health";
  String awolAbductedCode = "N";
  Boolean birthHistoryIndicatorVar = false;
  Boolean childIndianAncestryIndicator = false;
  Boolean collegeIndicator = false;
  String currentCaseId = "ABC1234567";
  Short deathCircumstancesType = (short) 1234;
  String disabilityDiagnosedCode = "N";
  String drmsHePassportDocOld = "Old";
  String drmsHealthEducPassportDoc = "Document";
  String drmsVoluntaryPlcmntAgrmntDoc = "Agreement";
  Boolean fc2EligApplicationIndicatorVar = false;
  String foodStampsApplicationDate = "2000-01-01";
  Boolean foodStampsApplicationIndicator = false;
  String icwaEligibilityCode = "N";
  Boolean intercountryAdoptDisruptedIndicator = false;
  Boolean intercountryAdoptDissolvedIndicator = false;
  Boolean medEligibilityApplicationIndicatorVar = false;
  Boolean minorNmdParentIndicator = false;
  Boolean parentalRightsLimitedIndicator = false;
  Boolean parentalRightsTermintnIndicatorVar = false;
  Boolean paternityIndividualIndicatorVar = false;
  Boolean postsecVocIndicator = false;
  String previouslyAdoptedCode = "N";
  Boolean safelySurrendedBabiesIndicatorVar = false;
  Boolean saw1EligApplicationIndicatorVar = false;
  Integer sawsCaseSerialNumber = 0;
  String sijsScheduledInterviewDate = "2000-01-01";
  String siiNextScreeningDueDate = "2000-01-01";
  Boolean ssiSspApplicationIndicator = false;
  Boolean tribalAncestryNotifctnIndicatorVar = false;
  String tribalCustomaryAdoptionDate = "2000-01-01";
  Boolean tribalCustomaryAdoptionIndicator = false;

  public ChildClient buildChildClient() {
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

  public ChildClientResourceBuilder setVictimClientId(String victimClientId) {
    this.victimClientId = victimClientId;
    return this;
  }

  public String getAdoptableCode() {
    return adoptableCode;
  }

  public ChildClientResourceBuilder setAdoptableCode(String adoptableCode) {
    this.adoptableCode = adoptableCode;
    return this;
  }

  public Short getAdoptedAge() {
    return adoptedAge;
  }

  public ChildClientResourceBuilder setAdoptedAge(Short adoptedAge) {
    this.adoptedAge = adoptedAge;
    return this;
  }

  public Boolean getAfdcFcEligibilityIndicatorVar() {
    return afdcFcEligibilityIndicatorVar;
  }

  public ChildClientResourceBuilder setAfdcFcEligibilityIndicatorVar(
      Boolean afdcFcEligibilityIndicatorVar) {
    this.afdcFcEligibilityIndicatorVar = afdcFcEligibilityIndicatorVar;
    return this;
  }

  public Boolean getAllEducationInfoOnFileIndicator() {
    return allEducationInfoOnFileIndicator;
  }

  public ChildClientResourceBuilder setAllEducationInfoOnFileIndicator(
      Boolean allEducationInfoOnFileIndicator) {
    this.allEducationInfoOnFileIndicator = allEducationInfoOnFileIndicator;
    return this;
  }

  public Boolean getAllHealthInfoOnFileIndicator() {
    return allHealthInfoOnFileIndicator;
  }

  public ChildClientResourceBuilder setAllHealthInfoOnFileIndicator(
      Boolean allHealthInfoOnFileIndicator) {
    this.allHealthInfoOnFileIndicator = allHealthInfoOnFileIndicator;
    return this;
  }

  public String getAttemptToAcquireEducInfoDesc() {
    return attemptToAcquireEducInfoDesc;
  }

  public ChildClientResourceBuilder setAttemptToAcquireEducInfoDesc(
      String attemptToAcquireEducInfoDesc) {
    this.attemptToAcquireEducInfoDesc = attemptToAcquireEducInfoDesc;
    return this;
  }

  public String getAttemptToAcquireHlthInfoDesc() {
    return attemptToAcquireHlthInfoDesc;
  }

  public ChildClientResourceBuilder setAttemptToAcquireHlthInfoDesc(
      String attemptToAcquireHlthInfoDesc) {
    this.attemptToAcquireHlthInfoDesc = attemptToAcquireHlthInfoDesc;
    return this;
  }

  public String getAwolAbductedCode() {
    return awolAbductedCode;
  }

  public ChildClientResourceBuilder setAwolAbductedCode(String awolAbductedCode) {
    this.awolAbductedCode = awolAbductedCode;
    return this;
  }

  public Boolean getBirthHistoryIndicatorVar() {
    return birthHistoryIndicatorVar;
  }

  public ChildClientResourceBuilder setBirthHistoryIndicatorVar(Boolean birthHistoryIndicatorVar) {
    this.birthHistoryIndicatorVar = birthHistoryIndicatorVar;
    return this;
  }

  public Boolean getChildIndianAncestryIndicator() {
    return childIndianAncestryIndicator;
  }

  public ChildClientResourceBuilder setChildIndianAncestryIndicator(
      Boolean childIndianAncestryIndicator) {
    this.childIndianAncestryIndicator = childIndianAncestryIndicator;
    return this;
  }

  public Boolean getCollegeIndicator() {
    return collegeIndicator;
  }

  public ChildClientResourceBuilder setCollegeIndicator(Boolean collegeIndicator) {
    this.collegeIndicator = collegeIndicator;
    return this;
  }

  public String getCurrentCaseId() {
    return currentCaseId;
  }

  public ChildClientResourceBuilder setCurrentCaseId(String currentCaseId) {
    this.currentCaseId = currentCaseId;
    return this;
  }

  public Short getDeathCircumstancesType() {
    return deathCircumstancesType;
  }

  public ChildClientResourceBuilder setDeathCircumstancesType(Short deathCircumstancesType) {
    this.deathCircumstancesType = deathCircumstancesType;
    return this;
  }

  public String getDisabilityDiagnosedCode() {
    return disabilityDiagnosedCode;
  }

  public ChildClientResourceBuilder setDisabilityDiagnosedCode(String disabilityDiagnosedCode) {
    this.disabilityDiagnosedCode = disabilityDiagnosedCode;
    return this;
  }

  public String getDrmsHePassportDocOld() {
    return drmsHePassportDocOld;
  }

  public ChildClientResourceBuilder setDrmsHePassportDocOld(String drmsHePassportDocOld) {
    this.drmsHePassportDocOld = drmsHePassportDocOld;
    return this;
  }

  public String getDrmsHealthEducPassportDoc() {
    return drmsHealthEducPassportDoc;
  }

  public ChildClientResourceBuilder setDrmsHealthEducPassportDoc(String drmsHealthEducPassportDoc) {
    this.drmsHealthEducPassportDoc = drmsHealthEducPassportDoc;
    return this;
  }

  public String getDrmsVoluntaryPlcmntAgrmntDoc() {
    return drmsVoluntaryPlcmntAgrmntDoc;
  }

  public ChildClientResourceBuilder setDrmsVoluntaryPlcmntAgrmntDoc(
      String drmsVoluntaryPlcmntAgrmntDoc) {
    this.drmsVoluntaryPlcmntAgrmntDoc = drmsVoluntaryPlcmntAgrmntDoc;
    return this;
  }

  public Boolean getFc2EligApplicationIndicatorVar() {
    return fc2EligApplicationIndicatorVar;
  }

  public ChildClientResourceBuilder setFc2EligApplicationIndicatorVar(
      Boolean fc2EligApplicationIndicatorVar) {
    this.fc2EligApplicationIndicatorVar = fc2EligApplicationIndicatorVar;
    return this;
  }

  public String getFoodStampsApplicationDate() {
    return foodStampsApplicationDate;
  }

  public ChildClientResourceBuilder setFoodStampsApplicationDate(String foodStampsApplicationDate) {
    this.foodStampsApplicationDate = foodStampsApplicationDate;
    return this;
  }

  public Boolean getFoodStampsApplicationIndicator() {
    return foodStampsApplicationIndicator;
  }

  public ChildClientResourceBuilder setFoodStampsApplicationIndicator(
      Boolean foodStampsApplicationIndicator) {
    this.foodStampsApplicationIndicator = foodStampsApplicationIndicator;
    return this;
  }

  public String getIcwaEligibilityCode() {
    return icwaEligibilityCode;
  }

  public ChildClientResourceBuilder setIcwaEligibilityCode(String icwaEligibilityCode) {
    this.icwaEligibilityCode = icwaEligibilityCode;
    return this;
  }

  public Boolean getIntercountryAdoptDisruptedIndicator() {
    return intercountryAdoptDisruptedIndicator;
  }

  public ChildClientResourceBuilder setIntercountryAdoptDisruptedIndicator(
      Boolean intercountryAdoptDisruptedIndicator) {
    this.intercountryAdoptDisruptedIndicator = intercountryAdoptDisruptedIndicator;
    return this;
  }

  public Boolean getIntercountryAdoptDissolvedIndicator() {
    return intercountryAdoptDissolvedIndicator;
  }

  public ChildClientResourceBuilder setIntercountryAdoptDissolvedIndicator(
      Boolean intercountryAdoptDissolvedIndicator) {
    this.intercountryAdoptDissolvedIndicator = intercountryAdoptDissolvedIndicator;
    return this;
  }

  public Boolean getMedEligibilityApplicationIndicatorVar() {
    return medEligibilityApplicationIndicatorVar;
  }

  public ChildClientResourceBuilder setMedEligibilityApplicationIndicatorVar(
      Boolean medEligibilityApplicationIndicatorVar) {
    this.medEligibilityApplicationIndicatorVar = medEligibilityApplicationIndicatorVar;
    return this;
  }

  public Boolean getMinorNmdParentIndicator() {
    return minorNmdParentIndicator;
  }

  public ChildClientResourceBuilder setMinorNmdParentIndicator(Boolean minorNmdParentIndicator) {
    this.minorNmdParentIndicator = minorNmdParentIndicator;
    return this;
  }

  public Boolean getParentalRightsLimitedIndicator() {
    return parentalRightsLimitedIndicator;
  }

  public ChildClientResourceBuilder setParentalRightsLimitedIndicator(
      Boolean parentalRightsLimitedIndicator) {
    this.parentalRightsLimitedIndicator = parentalRightsLimitedIndicator;
    return this;
  }

  public Boolean getParentalRightsTermintnIndicatorVar() {
    return parentalRightsTermintnIndicatorVar;
  }

  public ChildClientResourceBuilder setParentalRightsTermintnIndicatorVar(
      Boolean parentalRightsTermintnIndicatorVar) {
    this.parentalRightsTermintnIndicatorVar = parentalRightsTermintnIndicatorVar;
    return this;
  }

  public Boolean getPaternityIndividualIndicatorVar() {
    return paternityIndividualIndicatorVar;
  }

  public ChildClientResourceBuilder setPaternityIndividualIndicatorVar(
      Boolean paternityIndividualIndicatorVar) {
    this.paternityIndividualIndicatorVar = paternityIndividualIndicatorVar;
    return this;
  }

  public Boolean getPostsecVocIndicator() {
    return postsecVocIndicator;
  }

  public ChildClientResourceBuilder setPostsecVocIndicator(Boolean postsecVocIndicator) {
    this.postsecVocIndicator = postsecVocIndicator;
    return this;
  }

  public String getPreviouslyAdoptedCode() {
    return previouslyAdoptedCode;
  }

  public ChildClientResourceBuilder setPreviouslyAdoptedCode(String previouslyAdoptedCode) {
    this.previouslyAdoptedCode = previouslyAdoptedCode;
    return this;
  }

  public Boolean getSafelySurrendedBabiesIndicatorVar() {
    return safelySurrendedBabiesIndicatorVar;
  }

  public ChildClientResourceBuilder setSafelySurrendedBabiesIndicatorVar(
      Boolean safelySurrendedBabiesIndicatorVar) {
    this.safelySurrendedBabiesIndicatorVar = safelySurrendedBabiesIndicatorVar;
    return this;
  }

  public Boolean getSaw1EligApplicationIndicatorVar() {
    return saw1EligApplicationIndicatorVar;
  }

  public ChildClientResourceBuilder setSaw1EligApplicationIndicatorVar(
      Boolean saw1EligApplicationIndicatorVar) {
    this.saw1EligApplicationIndicatorVar = saw1EligApplicationIndicatorVar;
    return this;
  }

  public Integer getSawsCaseSerialNumber() {
    return sawsCaseSerialNumber;
  }

  public ChildClientResourceBuilder setSawsCaseSerialNumber(Integer sawsCaseSerialNumber) {
    this.sawsCaseSerialNumber = sawsCaseSerialNumber;
    return this;
  }

  public String getSijsScheduledInterviewDate() {
    return sijsScheduledInterviewDate;
  }

  public ChildClientResourceBuilder setSijsScheduledInterviewDate(
      String sijsScheduledInterviewDate) {
    this.sijsScheduledInterviewDate = sijsScheduledInterviewDate;
    return this;
  }

  public String getSiiNextScreeningDueDate() {
    return siiNextScreeningDueDate;
  }

  public ChildClientResourceBuilder setSiiNextScreeningDueDate(String siiNextScreeningDueDate) {
    this.siiNextScreeningDueDate = siiNextScreeningDueDate;
    return this;
  }

  public Boolean getSsiSspApplicationIndicator() {
    return ssiSspApplicationIndicator;
  }

  public ChildClientResourceBuilder setSsiSspApplicationIndicator(
      Boolean ssiSspApplicationIndicator) {
    this.ssiSspApplicationIndicator = ssiSspApplicationIndicator;
    return this;
  }

  public Boolean getTribalAncestryNotifctnIndicatorVar() {
    return tribalAncestryNotifctnIndicatorVar;
  }

  public ChildClientResourceBuilder setTribalAncestryNotifctnIndicatorVar(
      Boolean tribalAncestryNotifctnIndicatorVar) {
    this.tribalAncestryNotifctnIndicatorVar = tribalAncestryNotifctnIndicatorVar;
    return this;
  }

  public String getTribalCustomaryAdoptionDate() {
    return tribalCustomaryAdoptionDate;
  }

  public ChildClientResourceBuilder setTribalCustomaryAdoptionDate(
      String tribalCustomaryAdoptionDate) {
    this.tribalCustomaryAdoptionDate = tribalCustomaryAdoptionDate;
    return this;
  }

  public Boolean getTribalCustomaryAdoptionIndicator() {
    return tribalCustomaryAdoptionIndicator;
  }

  public ChildClientResourceBuilder setTribalCustomaryAdoptionIndicator(
      Boolean tribalCustomaryAdoptionIndicator) {
    this.tribalCustomaryAdoptionIndicator = tribalCustomaryAdoptionIndicator;
    return this;
  }

}
