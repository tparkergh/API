package gov.ca.cwds.fixture;

/**
 * 
 * @author CWDS API Team
 */
public class AllegationResourceBuilder {

  String legacySourceTable = "";
  String legacyId = "";
  long victimPersonId = 5432;
  long perpetratorPersonId = 0;
  Short injuryHarmType = 2178;
  String countySpecificCode = "34";

  /**
   * @param legacySourceTable - legacySourceTable
   * @return the legacySourceTable
   */
  public AllegationResourceBuilder setLegacySourceTable(String legacySourceTable) {
    this.legacySourceTable = legacySourceTable;
    return this;
  }

  /**
   * @param legacyId - legacyId
   * @return the legacyId
   */
  public AllegationResourceBuilder setLegacyId(String legacyId) {
    this.legacyId = legacyId;
    return this;
  }

  /**
   * @param victimPersonId - victimPersonId
   * @return the victimPersonId
   */
  public AllegationResourceBuilder setVictimPersonId(long victimPersonId) {
    this.victimPersonId = victimPersonId;
    return this;
  }

  /**
   * @param perpetratorPersonId - perpetratorPersonId
   * @return the perpetratorPersonId
   */
  public AllegationResourceBuilder setPerpetratorPersonId(long perpetratorPersonId) {
    this.perpetratorPersonId = perpetratorPersonId;
    return this;
  }

  /**
   * @param injuryHarmType - injuryHarmType
   * @return the injuryHarmType
   */
  public AllegationResourceBuilder setInjuryHarmType(Short injuryHarmType) {
    this.injuryHarmType = injuryHarmType;
    return this;
  }

  /**
   * @param countySpecificCode - countySpecificCode
   * @return trhe countySpecificCode
   */
  public AllegationResourceBuilder setCountySpecificCode(String countySpecificCode) {
    this.countySpecificCode = countySpecificCode;
    return this;
  }

  /**
   * @return the Allegation
   */
  public gov.ca.cwds.rest.api.domain.Allegation createAllegation() {
    return new gov.ca.cwds.rest.api.domain.Allegation(legacySourceTable, legacyId, victimPersonId,
        perpetratorPersonId, injuryHarmType, countySpecificCode);
  }
}
