package gov.ca.cwds.fixture;

import gov.ca.cwds.rest.api.domain.cms.ClientRelationship;

/**
 * @author CWDS API Team
 *
 */
@SuppressWarnings("javadoc")
public class ClientRelationshipResourceBuilder {

  String absentParentCode = "N";
  Short clientRelationshipType = (short) 172;
  String endDate = "2017-01-07";
  String secondaryClientId = "7rEZRFK25H";
  String primaryClientId = "5R5fgpCIE6";
  String sameHomeCode = "Y";
  String startDate = "2017-01-07";

  public ClientRelationship build() {
    return new ClientRelationship(absentParentCode, clientRelationshipType, endDate,
        secondaryClientId, primaryClientId, sameHomeCode, startDate);
  }

  public String getAbsentParentCode() {
    return absentParentCode;
  }

  public ClientRelationshipResourceBuilder setAbsentParentCode(String absentParentCode) {
    this.absentParentCode = absentParentCode;
    return this;
  }

  public Short getClientRelationshipType() {
    return clientRelationshipType;
  }

  public ClientRelationshipResourceBuilder setClientRelationshipType(Short clientRelationshipType) {
    this.clientRelationshipType = clientRelationshipType;
    return this;
  }

  public String getEndDate() {
    return endDate;
  }

  public ClientRelationshipResourceBuilder setEndDate(String endDate) {
    this.endDate = endDate;
    return this;
  }

  public String getSecondaryClientId() {
    return secondaryClientId;
  }

  public ClientRelationshipResourceBuilder setSecondaryClientId(String secondaryClientId) {
    this.secondaryClientId = secondaryClientId;
    return this;
  }

  public String getPrimaryClientId() {
    return primaryClientId;
  }

  public ClientRelationshipResourceBuilder setPrimaryClientId(String primaryClientId) {
    this.primaryClientId = primaryClientId;
    return this;
  }

  public String getSameHomeCode() {
    return sameHomeCode;
  }

  public ClientRelationshipResourceBuilder setSameHomeCode(String sameHomeCode) {
    this.sameHomeCode = sameHomeCode;
    return this;
  }

  public String getStartDate() {
    return startDate;
  }

  public ClientRelationshipResourceBuilder setStartDate(String startDate) {
    this.startDate = startDate;
    return this;
  }

}

