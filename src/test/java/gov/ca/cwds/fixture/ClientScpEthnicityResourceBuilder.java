package gov.ca.cwds.fixture;

import gov.ca.cwds.rest.api.domain.cms.ClientScpEthnicity;

/**
 * @author CWDS API Team
 *
 */
@SuppressWarnings("javadoc")
public class ClientScpEthnicityResourceBuilder {

  String id = "ABC1234560";
  String establishedForCode = "C";
  String establishedId = "123450ABC";
  Short ethnicity = (short) 834;

  public ClientScpEthnicity build() {
    return new ClientScpEthnicity(id, establishedForCode, establishedId, ethnicity);
  }

  public String getId() {
    return id;
  }

  public ClientScpEthnicityResourceBuilder setId(String id) {
    this.id = id;
    return this;
  }

  public String getEstablishedForCode() {
    return establishedForCode;
  }

  public ClientScpEthnicityResourceBuilder setEstablishedForCode(String establishedForCode) {
    this.establishedForCode = establishedForCode;
    return this;
  }

  public String getEstablishedId() {
    return establishedId;
  }

  public ClientScpEthnicityResourceBuilder setEstablishedId(String establishedId) {
    this.establishedId = establishedId;
    return this;
  }

  public Short getEthnicity() {
    return ethnicity;
  }

  public ClientScpEthnicityResourceBuilder setEthnicity(Short ethnicity) {
    this.ethnicity = ethnicity;
    return this;
  }

}
