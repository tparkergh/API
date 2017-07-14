package gov.ca.cwds.fixture;

import java.util.Date;

import gov.ca.cwds.data.persistence.cms.Address;
import gov.ca.cwds.data.persistence.cms.ClientAddress;

public class ClientAddressEntityBuilder {
  String id = "CLNTADDRID";
  Short addressType = 2;
  String bkInmtId = "";
  Date effEndDt = new Date();
  Date effStartDt = new Date();
  String fkAddress = "FKADDRESSX";
  String fkClient = "FKCLIENTXX";
  String homelessInd = "";
  String fkReferral = "FKREFERRAL";
  Address addresses = null;
  String lastUpdatedId = "0X5";

  public ClientAddress buildClientAddress() {
    return new ClientAddress(id, addressType, bkInmtId, effEndDt, effStartDt, fkAddress, fkClient,
        homelessInd, fkReferral, addresses, lastUpdatedId);
  }

  public String getId() {
    return id;
  }

  public ClientAddressEntityBuilder setId(String id) {
    this.id = id;
    return this;
  }

  public Short getAddressType() {
    return addressType;
  }

  public ClientAddressEntityBuilder setAddressType(Short addressType) {
    this.addressType = addressType;
    return this;
  }

  public String getBkInmtId() {
    return bkInmtId;
  }

  public ClientAddressEntityBuilder setBkInmtId(String bkInmtId) {
    this.bkInmtId = bkInmtId;
    return this;
  }

  public Date getEffEndDt() {
    return effEndDt;
  }

  public ClientAddressEntityBuilder setEffEndDt(Date effEndDt) {
    this.effEndDt = effEndDt;
    return this;
  }

  public Date getEffStartDt() {
    return effStartDt;
  }

  public ClientAddressEntityBuilder setEffStartDt(Date effStartDt) {
    this.effStartDt = effStartDt;
    return this;
  }

  public String getFkAddress() {
    return fkAddress;
  }

  public ClientAddressEntityBuilder setFkAddress(String fkAddress) {
    this.fkAddress = fkAddress;
    return this;
  }

  public String getFkClient() {
    return fkClient;
  }

  public ClientAddressEntityBuilder setFkClient(String fkClient) {
    this.fkClient = fkClient;
    return this;
  }

  public String getHomelessInd() {
    return homelessInd;
  }

  public ClientAddressEntityBuilder setHomelessInd(String homelessInd) {
    this.homelessInd = homelessInd;
    return this;
  }

  public String getFkReferral() {
    return fkReferral;
  }

  public ClientAddressEntityBuilder setFkReferral(String fkReferral) {
    this.fkReferral = fkReferral;
    return this;
  }

  public Address getAddresses() {
    return addresses;
  }

  public ClientAddressEntityBuilder setAddresses(Address addresses) {
    this.addresses = addresses;
    return this;
  }

  public String getLastUpdatedId() {
    return lastUpdatedId;
  }

  public ClientAddressEntityBuilder setLastUpdatedId(String lastUpdatedId) {
    this.lastUpdatedId = lastUpdatedId;
    return this;
  }
}
