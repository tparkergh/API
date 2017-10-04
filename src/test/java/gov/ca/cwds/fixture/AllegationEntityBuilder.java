package gov.ca.cwds.fixture;

import java.util.Date;

import gov.ca.cwds.data.persistence.cms.Allegation;
import gov.ca.cwds.data.persistence.cms.Client;

/**
 * @author CWDS API Team
 *
 */
@SuppressWarnings("javadoc")
public class AllegationEntityBuilder {
  String id = "1234567890";
  Date abuseEndDate = new Date();
  java.util.Date abuseStartDate = new Date();
  Short abuseFrequency = 0;
  String abuseFrequencyPeriodCode = "";
  String abuseLocationDescription = "";
  Short allegationDispositionType = 0;
  Short allegationType = 0;
  String dispositionDescription = "";
  Date dispositionDate = new Date();
  String injuryHarmDetailIndicator = "";
  String nonProtectingParentCode = "";
  String staffPersonAddedIndicator = "";
  String victimClientId = "";
  String perpetratorClientId = "";
  String referralId = "";
  String countySpecificCode = "";
  String zippyCreatedIndicator = "";
  Short placementFacilityType = 0;
  Client victimClients = new Client();
  Client perpetratorClients = new Client();

  public AllegationEntityBuilder setId(String id) {
    this.id = id;
    return this;
  }

  public AllegationEntityBuilder setAbuseEndDate(Date abuseEndDate) {
    this.abuseEndDate = abuseEndDate;
    return this;
  }

  public AllegationEntityBuilder setAbuseStartDate(Date abuseStartDate) {
    this.abuseStartDate = abuseStartDate;
    return this;
  }

  public AllegationEntityBuilder setAbuseFrequency(Short abuseFrequency) {
    this.abuseFrequency = abuseFrequency;
    return this;
  }

  public AllegationEntityBuilder setAbuseFrequencyPeriodCode(String abuseFrequencyPeriodCode) {
    this.abuseFrequencyPeriodCode = abuseFrequencyPeriodCode;
    return this;
  }

  public AllegationEntityBuilder setAbuseLocationDescription(String abuseLocationDescription) {
    this.abuseLocationDescription = abuseLocationDescription;
    return this;
  }

  public AllegationEntityBuilder setAllegationDispositionType(Short allegationDispositionType) {
    this.allegationDispositionType = allegationDispositionType;
    return this;
  }

  public AllegationEntityBuilder setAllegationType(Short allegationType) {
    this.allegationType = allegationType;
    return this;
  }

  public AllegationEntityBuilder setDispositionDescription(String dispositionDescription) {
    this.dispositionDescription = dispositionDescription;
    return this;
  }

  public AllegationEntityBuilder setDispositionDate(Date dispositionDate) {
    this.dispositionDate = dispositionDate;
    return this;
  }

  public AllegationEntityBuilder setInjuryHarmDetailIndicator(String injuryHarmDetailIndicator) {
    this.injuryHarmDetailIndicator = injuryHarmDetailIndicator;
    return this;
  }

  public AllegationEntityBuilder setNonProtectingParentCode(String nonProtectingParentCode) {
    this.nonProtectingParentCode = nonProtectingParentCode;
    return this;
  }

  public AllegationEntityBuilder setStaffPersonAddedIndicator(String staffPersonAddedIndicator) {
    this.staffPersonAddedIndicator = staffPersonAddedIndicator;
    return this;
  }

  public AllegationEntityBuilder setVictimClientId(String victimClientId) {
    this.victimClientId = victimClientId;
    return this;
  }

  public AllegationEntityBuilder setPerpetratorClientId(String perpetratorClientId) {
    this.perpetratorClientId = perpetratorClientId;
    return this;
  }

  public AllegationEntityBuilder setReferralId(String referralId) {
    this.referralId = referralId;
    return this;
  }

  public AllegationEntityBuilder setCountySpecificCode(String countySpecificCode) {
    this.countySpecificCode = countySpecificCode;
    return this;
  }

  public AllegationEntityBuilder setZippyCreatedIndicator(String zippyCreatedIndicator) {
    this.zippyCreatedIndicator = zippyCreatedIndicator;
    return this;
  }

  public AllegationEntityBuilder setPlacementFacilityType(Short placementFacilityType) {
    this.placementFacilityType = placementFacilityType;
    return this;
  }

  public AllegationEntityBuilder setVictimClients(Client victimClients) {
    this.victimClients = victimClients;
    return this;
  }

  public AllegationEntityBuilder setPerpetratorClients(Client perpetratorClients) {
    this.perpetratorClients = perpetratorClients;
    return this;
  }

  public Allegation build() {
    return new Allegation(id, abuseEndDate, abuseStartDate, abuseFrequency,
        abuseFrequencyPeriodCode, abuseLocationDescription, allegationDispositionType,
        allegationType, dispositionDescription, dispositionDate, injuryHarmDetailIndicator,
        nonProtectingParentCode, staffPersonAddedIndicator, victimClientId, perpetratorClientId,
        referralId, countySpecificCode, zippyCreatedIndicator, placementFacilityType, victimClients,
        perpetratorClients);
  }
}
