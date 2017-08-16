package gov.ca.cwds.fixture;

import gov.ca.cwds.rest.api.domain.cms.Allegation;

/**
 * @author CWDS API Team
 *
 */
@SuppressWarnings("javadoc")
public class CmsAllegationResourceBuilder {

  String abuseEndDate = "1998-04-21";
  Short abuseFrequency = (short) 2;
  String abuseFrequencyPeriodCode = "M";
  String abuseLocationDescription = "abuse Description";
  String abuseStartDate = "1998-01-21";
  Short allegationDispositionType = (short) 45;
  Short allegationType = (short) 2180;
  String dispositionDescription = "Disposition Descrption";
  String dispositionDate = "2001-01-21";
  Boolean injuryHarmDetailIndicator = false;
  String nonProtectingParentCode = "N";
  Boolean staffPersonAddedIndicator = false;
  String victimClientId = "ljnSt7KxnV";
  String perpetratorClientId = "MKPFcB90F4";
  String referralId = "H5CMVTm00h";
  String countySpecificCode = "99";
  Boolean zippyCreatedIndicator = false;
  Short placementFacilityType = (short) 6574;

  public Allegation buildCmsAllegation() {
    return new Allegation(abuseEndDate, abuseFrequency, abuseFrequencyPeriodCode,
        abuseLocationDescription, abuseStartDate, allegationDispositionType, allegationType,
        dispositionDescription, dispositionDate, injuryHarmDetailIndicator, nonProtectingParentCode,
        staffPersonAddedIndicator, victimClientId, perpetratorClientId, referralId,
        countySpecificCode, zippyCreatedIndicator, placementFacilityType);
  }

  public String getAbuseEndDate() {
    return abuseEndDate;
  }

  public CmsAllegationResourceBuilder setAbuseEndDate(String abuseEndDate) {
    this.abuseEndDate = abuseEndDate;
    return this;
  }

  public Short getAbuseFrequency() {
    return abuseFrequency;
  }

  public CmsAllegationResourceBuilder setAbuseFrequency(Short abuseFrequency) {
    this.abuseFrequency = abuseFrequency;
    return this;
  }

  public String getAbuseFrequencyPeriodCode() {
    return abuseFrequencyPeriodCode;
  }

  public CmsAllegationResourceBuilder setAbuseFrequencyPeriodCode(String abuseFrequencyPeriodCode) {
    this.abuseFrequencyPeriodCode = abuseFrequencyPeriodCode;
    return this;
  }

  public String getAbuseLocationDescription() {
    return abuseLocationDescription;
  }

  public CmsAllegationResourceBuilder setAbuseLocationDescription(String abuseLocationDescription) {
    this.abuseLocationDescription = abuseLocationDescription;
    return this;
  }

  public String getAbuseStartDate() {
    return abuseStartDate;
  }

  public CmsAllegationResourceBuilder setAbuseStartDate(String abuseStartDate) {
    this.abuseStartDate = abuseStartDate;
    return this;
  }

  public Short getAllegationDispositionType() {
    return allegationDispositionType;
  }

  public CmsAllegationResourceBuilder setAllegationDispositionType(
      Short allegationDispositionType) {
    this.allegationDispositionType = allegationDispositionType;
    return this;
  }

  public Short getAllegationType() {
    return allegationType;
  }

  public CmsAllegationResourceBuilder setAllegationType(Short allegationType) {
    this.allegationType = allegationType;
    return this;
  }

  public String getDispositionDescription() {
    return dispositionDescription;
  }

  public CmsAllegationResourceBuilder setDispositionDescription(String dispositionDescription) {
    this.dispositionDescription = dispositionDescription;
    return this;
  }

  public String getDispositionDate() {
    return dispositionDate;
  }

  public CmsAllegationResourceBuilder setDispositionDate(String dispositionDate) {
    this.dispositionDate = dispositionDate;
    return this;
  }

  public Boolean getInjuryHarmDetailIndicator() {
    return injuryHarmDetailIndicator;
  }

  public CmsAllegationResourceBuilder setInjuryHarmDetailIndicator(
      Boolean injuryHarmDetailIndicator) {
    this.injuryHarmDetailIndicator = injuryHarmDetailIndicator;
    return this;
  }

  public String getNonProtectingParentCode() {
    return nonProtectingParentCode;
  }

  public CmsAllegationResourceBuilder setNonProtectingParentCode(String nonProtectingParentCode) {
    this.nonProtectingParentCode = nonProtectingParentCode;
    return this;
  }

  public Boolean getStaffPersonAddedIndicator() {
    return staffPersonAddedIndicator;
  }

  public CmsAllegationResourceBuilder setStaffPersonAddedIndicator(
      Boolean staffPersonAddedIndicator) {
    this.staffPersonAddedIndicator = staffPersonAddedIndicator;
    return this;
  }

  public String getVictimClientId() {
    return victimClientId;
  }

  public CmsAllegationResourceBuilder setVictimClientId(String victimClientId) {
    this.victimClientId = victimClientId;
    return this;
  }

  public String getPerpetratorClientId() {
    return perpetratorClientId;
  }

  public CmsAllegationResourceBuilder setPerpetratorClientId(String perpetratorClientId) {
    this.perpetratorClientId = perpetratorClientId;
    return this;
  }

  public String getReferralId() {
    return referralId;
  }

  public CmsAllegationResourceBuilder setReferralId(String referralId) {
    this.referralId = referralId;
    return this;
  }

  public String getCountySpecificCode() {
    return countySpecificCode;
  }

  public CmsAllegationResourceBuilder setCountySpecificCode(String countySpecificCode) {
    this.countySpecificCode = countySpecificCode;
    return this;
  }

  public Boolean getZippyCreatedIndicator() {
    return zippyCreatedIndicator;
  }

  public CmsAllegationResourceBuilder setZippyCreatedIndicator(Boolean zippyCreatedIndicator) {
    this.zippyCreatedIndicator = zippyCreatedIndicator;
    return this;
  }

  public Short getPlacementFacilityType() {
    return placementFacilityType;
  }

  public CmsAllegationResourceBuilder setPlacementFacilityType(Short placementFacilityType) {
    this.placementFacilityType = placementFacilityType;
    return this;
  }

}
