package gov.ca.cwds.fixture;

import gov.ca.cwds.rest.api.domain.cms.ClientAddress;

/**
 * @author CWDS API Team
 *
 */
@SuppressWarnings("javadoc")
public class ClientAddressResourceBuilder {

  Short addressType = 2;
  String bkInmtId = "";
  String effEndDt = "2016-01-05";
  String effStartDt = "2017-01-05";
  String fkAddress = "FKADDRESSX";
  String fkClient = "FKCLIENTXX";
  String homelessInd = "";
  String fkReferral = "FKREFERRAL";

  /**
   * @return the ClientAddress
   */
  public ClientAddress buildClientAddress() {
    return new ClientAddress(addressType, bkInmtId, effEndDt, effStartDt, fkAddress, fkClient,
        homelessInd, fkReferral);
  }

  public Short getAddressType() {
    return addressType;
  }

  public ClientAddressResourceBuilder setAddressType(Short addressType) {
    this.addressType = addressType;
    return this;
  }

  public String getBkInmtId() {
    return bkInmtId;
  }

  public ClientAddressResourceBuilder setBkInmtId(String bkInmtId) {
    this.bkInmtId = bkInmtId;
    return this;
  }

  public String getEffEndDt() {
    return effEndDt;
  }

  public ClientAddressResourceBuilder setEffEndDt(String effEndDt) {
    this.effEndDt = effEndDt;
    return this;
  }

  public String getEffStartDt() {
    return effStartDt;
  }

  public ClientAddressResourceBuilder setEffStartDt(String effStartDt) {
    this.effStartDt = effStartDt;
    return this;
  }

  public String getFkAddress() {
    return fkAddress;
  }

  public ClientAddressResourceBuilder setFkAddress(String fkAddress) {
    this.fkAddress = fkAddress;
    return this;
  }

  public String getFkClient() {
    return fkClient;
  }

  public ClientAddressResourceBuilder setFkClient(String fkClient) {
    this.fkClient = fkClient;
    return this;
  }

  public String getHomelessInd() {
    return homelessInd;
  }

  public ClientAddressResourceBuilder setHomelessInd(String homelessInd) {
    this.homelessInd = homelessInd;
    return this;
  }

  public String getFkReferral() {
    return fkReferral;
  }

  public ClientAddressResourceBuilder setFkReferral(String fkReferral) {
    this.fkReferral = fkReferral;
    return this;
  }

}
