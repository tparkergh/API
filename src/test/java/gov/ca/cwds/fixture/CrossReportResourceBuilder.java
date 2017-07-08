package gov.ca.cwds.fixture;

/**
 * 
 * @author CWDS API Team
 */
public class CrossReportResourceBuilder {

  String id = "";
  String legacySourceTable = "";
  String legacyId = "";
  String agencyType = "Law enforcement";
  String agencyName = "Sacramento County Sheriff Deparment";
  Integer method = 2095; // "electronic report"
  String informDate = "2017-03-15";

  /**
   * @param id - id
   * @return the id
   */
  public CrossReportResourceBuilder setId(String id) {
    this.id = id;
    return this;
  }

  /**
   * @param legacySourceTable - legacySourceTable
   * @return the legacySourceTable
   */
  public CrossReportResourceBuilder setLegacySourceTable(String legacySourceTable) {
    this.legacySourceTable = legacySourceTable;
    return this;
  }

  /**
   * @param legacyId - legacyId
   * @return the legacyId
   */
  public CrossReportResourceBuilder setLegacyId(String legacyId) {
    this.legacyId = legacyId;
    return this;
  }

  /**
   * @param agencyType - agencyType
   * @return the agencyType
   */
  public CrossReportResourceBuilder setAgencyType(String agencyType) {
    this.agencyType = agencyType;
    return this;
  }

  /**
   * @param agencyName - agencyName
   * @return the agencyName
   */
  public CrossReportResourceBuilder setAgencyName(String agencyName) {
    this.agencyName = agencyName;
    return this;
  }

  /**
   * @param method - method
   * @return the method
   */
  public CrossReportResourceBuilder setMethod(Integer method) {
    this.method = method;
    return this;
  }

  /**
   * @param informDate - informDate
   * @return the informDate
   */
  public CrossReportResourceBuilder setInformDate(String informDate) {
    this.informDate = informDate;
    return this;
  }

  /**
   * @return the CrossReport
   */
  public gov.ca.cwds.rest.api.domain.CrossReport createCrossReport() {
    return new gov.ca.cwds.rest.api.domain.CrossReport(id, legacySourceTable, legacyId, agencyType,
        agencyName, method, informDate);
  }
}
