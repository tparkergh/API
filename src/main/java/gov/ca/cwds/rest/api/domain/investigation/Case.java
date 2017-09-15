package gov.ca.cwds.rest.api.domain.investigation;

import java.util.Set;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.DomainObject;
import gov.ca.cwds.rest.api.domain.ReportingDomain;
import io.dropwizard.jackson.JsonSnakeCase;
import io.swagger.annotations.ApiModelProperty;

/**
 * {@link DomainObject} representing a Case.
 * 
 * @author CWDS API Team
 */
@JsonSnakeCase
@JsonPropertyOrder({"end_date", "county_name", "legacy_descriptor", "focus_child",
    "service_component", "assigned_social_worker", "access_limitation", "service_component_id",
    "start_date", "parents",})
public class Case extends ReportingDomain implements Response {

  private static final long serialVersionUID = 1L;

  @JsonProperty("end_date")
  @ApiModelProperty(required = false, readOnly = false, value = "", example = "2017-09-30")
  private String endDate;

  @JsonProperty("county_name")
  @ApiModelProperty(required = false, readOnly = false, value = "", example = "Kings")
  private String countyName;

  @JsonProperty("legacy_descriptor")
  private SimpleLegacyDescriptor legacyDescriptor;

  @JsonProperty("focus_child")
  private SimplePerson focusChild;

  @JsonProperty("service_component")
  @ApiModelProperty(required = false, readOnly = false, value = "", example = "Emergency Response")
  private String serviceComponent;

  @JsonProperty("assigned_social_worker")
  private SimplePerson assignedSocialWorker;

  @JsonProperty("access_limitation")
  private LimitedAccess accessLimitation;

  @JsonProperty("service_component_id")
  @ApiModelProperty(required = false, readOnly = false, value = "", example = "1694")
  private String serviceComponentId;

  @JsonProperty("start_date")
  @ApiModelProperty(required = false, readOnly = false, value = "", example = "2017-09-01")
  private String startDate;

  @JsonProperty("parents")
  private Set<SimplePersonWithRelationship> parents;

  /**
   * Constructor
   * 
   * @param endDate the end date
   * @param countyName county name
   * @param legacyDescriptor legacy descriptor
   * @param focusChild focus child
   * @param serviceComponent service component
   * @param assignedSocialWorker assigned social worker
   * @param accessLimitation limited access
   * @param serviceComponentId service component id
   * @param startDate start date
   * @param parents parents
   */
  public Case(@JsonProperty("end_date") String endDate,
      @JsonProperty("county_name") String countyName,
      @JsonProperty("legacy_descriptor") SimpleLegacyDescriptor legacyDescriptor,
      @JsonProperty("focus_child") SimplePerson focusChild,
      @JsonProperty("service_component") String serviceComponent,
      @JsonProperty("assigned_social_worker") SimplePerson assignedSocialWorker,
      @JsonProperty("access_limitation") LimitedAccess accessLimitation,
      @JsonProperty("service_component_id") String serviceComponentId,
      @JsonProperty("start_date") String startDate,
      @JsonProperty("parents") Set<SimplePersonWithRelationship> parents) {
    super();
    this.endDate = endDate;
    this.countyName = countyName;
    this.legacyDescriptor = legacyDescriptor;
    this.focusChild = focusChild;
    this.serviceComponent = serviceComponent;
    this.assignedSocialWorker = assignedSocialWorker;
    this.accessLimitation = accessLimitation;
    this.serviceComponentId = serviceComponentId;
    this.startDate = startDate;
    this.parents = parents;
  }

  /**
   * @return the endDate
   */
  public String getEndDate() {
    return endDate;
  }

  /**
   * @return the countyName
   */
  public String getCountyName() {
    return countyName;
  }

  /**
   * @return the legacyDescriptor
   */
  public SimpleLegacyDescriptor getLegacyDescriptor() {
    return legacyDescriptor;
  }

  /**
   * @return the focusChild
   */
  public SimplePerson getFocusChild() {
    return focusChild;
  }

  /**
   * @return the serviceComponent
   */
  public String getServiceComponent() {
    return serviceComponent;
  }

  /**
   * @return the assignedSocialWorker
   */
  public SimplePerson getAssignedSocialWorker() {
    return assignedSocialWorker;
  }

  /**
   * @return the accessLimitation
   */
  public LimitedAccess getAccessLimitation() {
    return accessLimitation;
  }

  /**
   * @return the serviceComponentId
   */
  public String getServiceComponentId() {
    return serviceComponentId;
  }

  /**
   * @return the startDate
   */
  public String getStartDate() {
    return startDate;
  }

  /**
   * @return the parents
   */
  public Set<SimplePersonWithRelationship> getParents() {
    return parents;
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
