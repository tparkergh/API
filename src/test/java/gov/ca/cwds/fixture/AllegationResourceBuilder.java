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
  Short type = 1373;
  String county = "Sacramento";

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
   * @param type - type
   * @return the type
   */
  public AllegationResourceBuilder setType(Short type) {
    this.type = type;
    return this;
  }

  /**
   * @param county - county
   * @return trhe county
   */
  public AllegationResourceBuilder setCounty(String county) {
    this.county = county;
    return this;
  }

  /**
   * @return the Allegation
   */
  public gov.ca.cwds.rest.api.domain.Allegation createAllegation() {
    return new gov.ca.cwds.rest.api.domain.Allegation(legacySourceTable, legacyId, victimPersonId,
        perpetratorPersonId, type, county);
  }
}
