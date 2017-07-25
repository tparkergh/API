package gov.ca.cwds.rest.api.domain.cms;

import static gov.ca.cwds.data.persistence.cms.CmsPersistentObject.CMS_ID_LEN;
import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.DomainObject;
import gov.ca.cwds.rest.api.domain.ReportingDomain;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * {@link DomainObject} representing a ClientRelationship
 * 
 * @author CWDS API Team
 */
public class ClientCollateral extends ReportingDomain implements Request, Response {

  /**
   * Serialization version
   */
  private static final long serialVersionUID = 1L;

  @NotEmpty
  @Size(min = 1, max = 1, message = "size must be 1")
  @ApiModelProperty(required = true, readOnly = false, value = "", example = "Y")
  private String activeIndicator;

  @NotNull
  @ApiModelProperty(required = true, readOnly = false, example = "573")
  private Short collateralClientReporterRelationshipType;

  @NotEmpty
  @Size(min = 1, max = CMS_ID_LEN)
  @ApiModelProperty(required = true, readOnly = false, value = "", example = "ABC123")
  private String clientId;

  @NotEmpty
  @Size(min = 1, max = CMS_ID_LEN)
  @ApiModelProperty(required = true, readOnly = false, value = "", example = "DEF123")
  private String collateralIndividualId;

  @Size(min = 0, max = 50)
  @ApiModelProperty(required = false, readOnly = false, value = "", example = "A")
  private String commentDescription;

  /**
   * Construct from all fields.
   * 
   * @param activeIndicator indicates if relationship to CollateralIndividual is active (Y)
   * @param collateralClientReporterRelationshipType Collateral Client Reporter Relationship Type in
   *        System Codes
   * @param commentDescription - description
   * @param clientId foreign key Client table
   * @param collateralIndividualId foreign key to CollateralIndividual table
   * 
   */
  @JsonCreator
  public ClientCollateral(
      @JsonProperty("activeIndicator") String activeIndicator,
      @JsonProperty("collateralClientReporterRelationshipType") Short collateralClientReporterRelationshipType,
      @JsonProperty("commentDescription") String commentDescription,
      @JsonProperty("clientId") String clientId,
      @JsonProperty("collateralIndividualId") String collateralIndividualId) {
    super();
    this.activeIndicator = activeIndicator;
    this.collateralClientReporterRelationshipType = collateralClientReporterRelationshipType;
    this.commentDescription = commentDescription;
    this.clientId = clientId;
    this.collateralIndividualId = collateralIndividualId;
  }

  /**
   *
   * @param persistedClientCollateral the persistent object to construct this object from
   */
  public ClientCollateral(
      gov.ca.cwds.data.persistence.cms.ClientCollateral persistedClientCollateral) {
    this.activeIndicator = persistedClientCollateral.getActiveIndicator();
    this.collateralClientReporterRelationshipType =
        persistedClientCollateral.getCollateralClientReporterRelationshipType();
    this.commentDescription = persistedClientCollateral.getCommentDescription();
    this.clientId = persistedClientCollateral.getClientId();
    this.collateralIndividualId = persistedClientCollateral.getCollateralIndividualId();
  }

  /**
   * @return the activeIndicator
   */
  public String getActiveIndicator() {
    return activeIndicator;
  }

  /**
   * @return the collateralClientReporterRelationshipType
   */
  public Short getCollateralClientReporterRelationshipType() {
    return collateralClientReporterRelationshipType;
  }

  /**
   * 
   * @return the commentDescription
   */
  public String getCommentDescription() {
    return commentDescription;
  }

  /**
   * @return the clientId
   */
  public String getClientId() {
    return clientId;
  }

  /**
   * @return the collateralIndividualId
   */
  public String getCollateralIndividualId() {
    return collateralIndividualId;
  }

  /**
   * {@inheritDoc}
   *
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this, false);
  }

  /**
   * {@inheritDoc}
   *
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj) {
    return EqualsBuilder.reflectionEquals(this, obj, false);
  }

}
