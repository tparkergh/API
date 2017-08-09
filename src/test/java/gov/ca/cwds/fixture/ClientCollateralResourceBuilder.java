package gov.ca.cwds.fixture;

import gov.ca.cwds.rest.api.domain.cms.ClientCollateral;

/**
 * 
 * @author CWDS API Team
 */
@SuppressWarnings("javadoc")
public class ClientCollateralResourceBuilder {

  private String thirdId = "CLNTCOLL";
  private Short collateralClientReporterRelationshipType = 511;
  private String activeIndicator = "Y";
  private String commentDescription = "comment";
  private String clientId = "CLIENTID";
  private String collateralIndividualId = "COLLCLIENT";

  public ClientCollateral buildClientCollateral() {
    return new ClientCollateral(activeIndicator, collateralClientReporterRelationshipType,
        commentDescription, clientId, collateralIndividualId);
  }

  public String getThirdId() {
    return thirdId;
  }

  public ClientCollateralResourceBuilder setThirdId(String thirdId) {
    this.thirdId = thirdId;
    return this;
  }

  public Short getCollateralClientReporterRelationshipType() {
    return collateralClientReporterRelationshipType;
  }

  public ClientCollateralResourceBuilder setCollateralClientReporterRelationshipType(
      Short collateralClientReporterRelationshipType) {
    this.collateralClientReporterRelationshipType = collateralClientReporterRelationshipType;
    return this;
  }

  public String getActiveIndicator() {
    return activeIndicator;
  }

  public ClientCollateralResourceBuilder setActiveIndicator(String activeIndicator) {
    this.activeIndicator = activeIndicator;
    return this;
  }

  public String getCommentDescription() {
    return commentDescription;
  }

  public ClientCollateralResourceBuilder setCommentDescription(String commentDescription) {
    this.commentDescription = commentDescription;
    return this;
  }

  public String getClientId() {
    return clientId;
  }

  public ClientCollateralResourceBuilder setClientId(String clientId) {
    this.clientId = clientId;
    return this;
  }

  public String getCollateralIndividualId() {
    return collateralIndividualId;
  }

  public ClientCollateralResourceBuilder setCollateralIndividualId(String collateralIndividualId) {
    this.collateralIndividualId = collateralIndividualId;
    return this;
  }

}
