package gov.ca.cwds.fixture;

/**
 * 
 * @author CWDS API Team
 */
@SuppressWarnings("javadoc")
public class AllegationResourceBuilder {

  String legacySourceTable = "";
  String legacyId = "";
  long victimPersonId = 5432;
  long perpetratorPersonId = 0;
  String type = "physical abuse";
  String county = "Sacramento";

  public AllegationResourceBuilder setLegacySourceTable(String legacySourceTable) {
    this.legacySourceTable = legacySourceTable;
    return this;
  }

  public AllegationResourceBuilder setLegacyId(String legacyId) {
    this.legacyId = legacyId;
    return this;
  }

  public AllegationResourceBuilder setVictimPersonId(long victimPersonId) {
    this.victimPersonId = victimPersonId;
    return this;
  }

  public AllegationResourceBuilder setPerpetratorPersonId(long perpetratorPersonId) {
    this.perpetratorPersonId = perpetratorPersonId;
    return this;
  }

  public AllegationResourceBuilder setType(String type) {
    this.type = type;
    return this;
  }

  public AllegationResourceBuilder setCounty(String county) {
    this.county = county;
    return this;
  }

  public gov.ca.cwds.rest.api.domain.Allegation createAllegation() {
    return new gov.ca.cwds.rest.api.domain.Allegation(legacySourceTable, legacyId, victimPersonId,
        perpetratorPersonId, type, county);
  }
}
