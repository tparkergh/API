package gov.ca.cwds.rest.api.domain.cms;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.DomainObject;
import gov.ca.cwds.rest.api.domain.ReportingDomain;
import io.dropwizard.jackson.JsonSnakeCase;
import io.swagger.annotations.ApiModelProperty;

/**
 * {@link DomainObject} representing a GovernmentOrganization
 * 
 * @author CWDS API Team
 */
@JsonSnakeCase
@JsonPropertyOrder({"id", "name", "type", "countyId"})
public class GovernmentOrganization extends ReportingDomain implements Request, Response {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  @Valid
  @JsonProperty("id")
  @ApiModelProperty(required = true, readOnly = false, value = "identifier", example = "ABC1234567")
  private String id;

  @Valid
  @JsonProperty("name")
  @ApiModelProperty(required = true, readOnly = false, value = "", example = "County Sheriff Dept")
  private String agencyName;

  @Valid
  @JsonProperty("type")
  @ApiModelProperty(required = true, readOnly = false, value = "",
      example = "DEPARTMENT_OF_JUSTICE")
  private String agencyType;

  @Valid
  @JsonProperty("countyId")
  @ApiModelProperty(required = true, readOnly = false, value = "", example = "1094")
  private Short governmentEntityType;

  /**
   * empty constructor
   */
  public GovernmentOrganization() {
    super();
  }

  /**
   * @param id the identifier
   * @param agencyName the agency Name
   * @param agencyType the agency Type
   * @param governmentEntityType the government Entity Type
   */
  @JsonCreator
  public GovernmentOrganization(@JsonProperty("id") String id,
      @JsonProperty("name") String agencyName, @JsonProperty("type") String agencyType,
      @JsonProperty("countyId") Short governmentEntityType) {
    super();
    this.id = id;
    this.agencyName = agencyName;
    this.agencyType = agencyType;
    this.governmentEntityType = governmentEntityType;
  }

  /**
   * @param persistent - governmentOrganization
   */
  public GovernmentOrganization(
      gov.ca.cwds.data.persistence.cms.GovernmentOrganization persistent) {
    this.id = persistent.getId();
    this.agencyName = persistent.getGovernmentOrganizationName();
    this.agencyType = persistent.getGovernmentOrganizationType().toString();
    this.governmentEntityType = persistent.getGovernmentEntityType();
  }

  /**
   * @param persistestedLawEnforcement - persistestedLawEnforcement
   */
  public GovernmentOrganization(
      gov.ca.cwds.data.persistence.cms.LawEnforcement persistestedLawEnforcement) {
    this.id = persistestedLawEnforcement.getId();
    this.agencyName = persistestedLawEnforcement.getLawEnforcementName();
    this.agencyType = "LAWENFORCEMENT";
    this.governmentEntityType = persistestedLawEnforcement.getGovernmentEntityType();
  }

  /**
   * @return the id
   */
  public String getId() {
    return id;
  }

  /**
   * @return the agencyName
   */
  public String getAgencyName() {
    return StringUtils.trim(agencyName);
  }

  /**
   * @return the agencyType
   */
  public String getAgencyType() {
    return agencyType;
  }

  /**
   * @return the governmentEntityType
   */
  public Short getGovernmentEntityType() {
    return governmentEntityType;
  }

  /**
   * {@inheritDoc}
   *
   * @see java.lang.Object#hashCode()
   */
  @Override
  public final int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this, false);
  }

  /**
   * {@inheritDoc}
   *
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public final boolean equals(Object obj) {
    return EqualsBuilder.reflectionEquals(this, obj, false);
  }

}
