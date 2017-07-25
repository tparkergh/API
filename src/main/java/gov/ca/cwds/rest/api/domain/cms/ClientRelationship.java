package gov.ca.cwds.rest.api.domain.cms;

import static gov.ca.cwds.data.persistence.cms.CmsPersistentObject.CMS_ID_LEN;
import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.DomainChef;
import gov.ca.cwds.rest.api.domain.DomainObject;
import gov.ca.cwds.rest.api.domain.ReportingDomain;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * {@link DomainObject} representing a ClientRelationship
 * 
 * @author CWDS API Team
 */
public class ClientRelationship extends ReportingDomain implements Request, Response {

  /**
   * Serialization version
   */
  private static final long serialVersionUID = 1L;

  @NotEmpty
  @Size(min = 1, max = 1, message = "size must be 1")
  @ApiModelProperty(required = true, readOnly = false, value = "", example = "N")
  private String absentParentCode;

  @NotNull
  @ApiModelProperty(required = true, readOnly = false, example = "179")
  private Short clientRelationshipType;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_FORMAT)
  @JsonProperty(value = "endDate")
  @gov.ca.cwds.rest.validation.Date(format = DATE_FORMAT, required = false)
  @ApiModelProperty(required = false, readOnly = false, value = "yyyy-MM-dd",
      example = "2000-01-01")
  private String endDate;

  @NotEmpty
  @Size(min = 1, max = CMS_ID_LEN)
  @ApiModelProperty(required = true, readOnly = false, value = "", example = "ABC123")
  private String secondaryClientId;

  @NotEmpty
  @Size(min = 1, max = CMS_ID_LEN)
  @ApiModelProperty(required = true, readOnly = false, value = "", example = "DEF123")
  private String primaryClientId;

  @NotEmpty
  @Size(min = 1, max = 1, message = "size must be 1")
  @ApiModelProperty(required = true, readOnly = false, value = "", example = "Y")
  private String sameHomeCode;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_FORMAT)
  @JsonProperty(value = "startDate")
  @gov.ca.cwds.rest.validation.Date(format = DATE_FORMAT, required = false)
  @ApiModelProperty(required = false, readOnly = false, value = "yyyy-MM-dd",
      example = "2000-01-01")
  private String startDate;

  /**
   * Construct from all fields.
   * 
   * @param absentParentCode indicates if the parent CLIENT is absent for the child with whom the
   *        relationship is being defined (N)
   * @param clientRelationshipType Client Relationship Type from System Code table
   * @param endDate date the relationship ended
   * @param secondaryClientId Mandatory Foreign key that includes a secondary individual as a CLIENT
   * @param primaryClientId Mandatory Foreign key that includes a primary individual as a CLIENT
   * @param sameHomeCode indicates whether the two CLIENTs live in the same home (Y)
   * @param startDate date the relationship began
   */
  @JsonCreator
  public ClientRelationship(@JsonProperty("absentParentCode") String absentParentCode,
      @JsonProperty("clientRelationshipType") Short clientRelationshipType,
      @JsonProperty("endDate") String endDate,
      @JsonProperty("secondaryClientId") String secondaryClientId,
      @JsonProperty("primaryClientId") String primaryClientId,
      @JsonProperty("sameHomeCode") String sameHomeCode, @JsonProperty("startDate") String startDate) {
    super();
    this.absentParentCode = absentParentCode;
    this.clientRelationshipType = clientRelationshipType;
    this.endDate = endDate;
    this.secondaryClientId = secondaryClientId;
    this.primaryClientId = primaryClientId;
    this.sameHomeCode = sameHomeCode;
    this.startDate = startDate;
  }

  /**
   *
   * @param persistedClientRelationship the persistent object to construct this object from
   */
  public ClientRelationship(
      gov.ca.cwds.data.persistence.cms.ClientRelationship persistedClientRelationship) {
    this.absentParentCode = persistedClientRelationship.getAbsentParentCode();
    this.clientRelationshipType = persistedClientRelationship.getClientRelationshipType();
    this.endDate = DomainChef.cookDate(persistedClientRelationship.getEndDate());
    this.secondaryClientId = persistedClientRelationship.getSecondaryClientId();
    this.primaryClientId = persistedClientRelationship.getPrimaryClientId();
    this.sameHomeCode = persistedClientRelationship.getSameHomeCode();
    this.startDate = DomainChef.cookDate(persistedClientRelationship.getStartDate());
  }

  /**
   * @return the absentParentCode
   */
  public String getAbsentParentCode() {
    return absentParentCode;
  }

  /**
   * @return the clientRelationshipType
   */
  public Short getClientRelationshipType() {
    return clientRelationshipType;
  }

  /**
   * @return the endDate
   */
  public String getEndDate() {
    return endDate;
  }

  /**
   * @return the secondaryClientId
   */
  public String getSecondaryClientId() {
    return secondaryClientId;
  }

  /**
   * @return the primaryClientId
   */
  public String getPrimaryClientId() {
    return primaryClientId;
  }

  /**
   * @return the sameHomeCode
   */
  public String getSameHomeCode() {
    return sameHomeCode;
  }

  /**
   * @return the startDate
   */
  public String getStartDate() {
    return startDate;
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
