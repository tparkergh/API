package gov.ca.cwds.fixture;

/**
 * 
 * @author CWDS API Team
 */
@SuppressWarnings("javadoc")
public class CrossReportResourceBuilder {

  String id = "";
  String legacySourceTable = "";
  String legacyId = "";
  String agencyType = "Law enforcement";
  String agencyName = "Sacramento County Sheriff Deparment";
  String method = "electronic report";
  String informDate = "2017-03-15";

  public CrossReportResourceBuilder setId(String id) {
    this.id = id;
    return this;
  }

  public CrossReportResourceBuilder setLegacySourceTable(String legacySourceTable) {
    this.legacySourceTable = legacySourceTable;
    return this;
  }

  public CrossReportResourceBuilder setLegacyId(String legacyId) {
    this.legacyId = legacyId;
    return this;
  }

  public CrossReportResourceBuilder setAgencyType(String agencyType) {
    this.agencyType = agencyType;
    return this;
  }

  public CrossReportResourceBuilder setAgencyName(String agencyName) {
    this.agencyName = agencyName;
    return this;
  }

  public CrossReportResourceBuilder setMethod(String method) {
    this.method = method;
    return this;
  }

  public CrossReportResourceBuilder setInformDate(String informDate) {
    this.informDate = informDate;
    return this;
  }

  public gov.ca.cwds.rest.api.domain.CrossReport createCrossReport() {
    return new gov.ca.cwds.rest.api.domain.CrossReport(id, legacySourceTable, legacyId, agencyType,
        agencyName, method, informDate);
  }
}
