package gov.ca.cwds.fixture;

import java.util.Date;

import gov.ca.cwds.data.persistence.cms.ClientScpEthnicity;

/**
 * @author CWDS API Team
 *
 */
@SuppressWarnings("javadoc")
public class ClientScpEthnicityEntityBuilder {

  String id = "ABC1234560";
  String establishedForCode = "C";
  String establishedId = "123450ABC";
  Short ethnicity = (short) 834;
  String lastUpdatedId = "0X5";
  Date lastUpdatedTime = new Date();

  public ClientScpEthnicity build() {
    return new ClientScpEthnicity(id, establishedForCode, establishedId, ethnicity, lastUpdatedId,
        lastUpdatedTime);
  }

  public String getId() {
    return id;
  }

  public ClientScpEthnicityEntityBuilder setId(String id) {
    this.id = id;
    return this;
  }

  public String getEstablishedForCode() {
    return establishedForCode;
  }

  public ClientScpEthnicityEntityBuilder setEstablishedForCode(String establishedForCode) {
    this.establishedForCode = establishedForCode;
    return this;
  }

  public String getEstablishedId() {
    return establishedId;
  }

  public ClientScpEthnicityEntityBuilder setEstablishedId(String establishedId) {
    this.establishedId = establishedId;
    return this;
  }

  public Short getEthnicity() {
    return ethnicity;
  }

  public ClientScpEthnicityEntityBuilder setEthnicity(Short ethnicity) {
    this.ethnicity = ethnicity;
    return this;
  }

  public String getLastUpdatedId() {
    return lastUpdatedId;
  }

  public ClientScpEthnicityEntityBuilder setLastUpdatedId(String lastUpdatedId) {
    this.lastUpdatedId = lastUpdatedId;
    return this;
  }

  public Date getLastUpdatedTime() {
    return lastUpdatedTime;
  }

  public ClientScpEthnicityEntityBuilder setLastUpdatedTime(Date lastUpdatedTime) {
    this.lastUpdatedTime = lastUpdatedTime;
    return this;
  }


}
