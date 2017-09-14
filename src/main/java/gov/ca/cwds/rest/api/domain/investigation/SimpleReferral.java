package gov.ca.cwds.rest.api.domain.investigation;

import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.DomainObject;
import gov.ca.cwds.rest.api.domain.ReportingDomain;
import io.dropwizard.jackson.JsonSnakeCase;
import io.swagger.annotations.ApiModelProperty;

import java.util.Set;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * {@link DomainObject} representing a Simple Referral
 * 
 * @author CWDS API Team
 */
@JsonSnakeCase
@JsonPropertyOrder({"end_date", "legacy_descriptor", "reporter", "county_name", "response_time_id",
    "allegations", "assigned_social_worker", "access_limitation", "response_time", "start_date"})
public class SimpleReferral extends ReportingDomain implements Response {

  private static final long serialVersionUID = 1L;

  @JsonProperty("end_date")
  @ApiModelProperty(required = false, readOnly = false, value = "", example = "2017-08-23")
  private String endDate;

  @JsonProperty("legacy_descriptor")
  private SimpleLegacyDescriptor legacyDescriptor;

  @JsonProperty("reporter")
  private SimplePerson reporter;

  @JsonProperty("county_name")
  @ApiModelProperty(required = false, readOnly = false, value = "", example = "Fresno")
  private String countyName;

  @JsonProperty("response_time_id")
  @ApiModelProperty(required = false, readOnly = false, value = "", example = "1520")
  private String responseTimeId;

  @JsonProperty("allegations")
  private Set<Allegation> allegations;

  @JsonProperty("assigned_social_worker")
  private SimplePerson assignedSocialWorker;

  @JsonProperty("access_limitation")
  private LimitedAccess accessLimitation;

  @JsonProperty("response_time")
  @ApiModelProperty(required = false, readOnly = false, value = "", example = "Immediate")
  private String responseTime;

  @JsonProperty("start_date")
  @ApiModelProperty(required = false, readOnly = false, value = "", example = "2017-08-22")
  private String startDate;



  /**
   * @param endDate the end date
   * @param legacyDescriptor the legacy descriptor
   * @param reporter the reporter
   * @param countyName the county name
   * @param responseTimeId the response time id
   * @param allegations the allegations
   * @param assignedSocialWorker the assigned social worker
   * @param accessLimitation the limited access
   * @param responseTime the response time
   * @param startDate the start date
   */
  public SimpleReferral(@JsonProperty("end_date") String endDate,
      @JsonProperty("legacy_descriptor") SimpleLegacyDescriptor legacyDescriptor,
      @JsonProperty("reporter") SimplePerson reporter,
      @JsonProperty("county_name") String countyName,
      @JsonProperty("response_time_id") String responseTimeId,
      @JsonProperty("allegations") Set<Allegation> allegations,
      @JsonProperty("assigned_social_worker") SimplePerson assignedSocialWorker,
      @JsonProperty("access_limitation") LimitedAccess accessLimitation,
      @JsonProperty("response_time") String responseTime,
      @JsonProperty("start_date") String startDate) {
    super();
    this.endDate = endDate;
    this.legacyDescriptor = legacyDescriptor;
    this.reporter = reporter;
    this.countyName = countyName;
    this.responseTimeId = responseTimeId;
    this.allegations = allegations;
    this.assignedSocialWorker = assignedSocialWorker;
    this.accessLimitation = accessLimitation;
    this.responseTime = responseTime;
    this.startDate = startDate;
  }

  /**
   * @return the endDate
   */
  public String getEndDate() {
    return endDate;
  }

  /**
   * @return the legacyDescriptor
   */
  public SimpleLegacyDescriptor getLegacyDescriptor() {
    return legacyDescriptor;
  }

  /**
   * @return the reporter
   */
  public SimplePerson getReporter() {
    return reporter;
  }

  /**
   * @return the countyName
   */
  public String getCountyName() {
    return countyName;
  }

  /**
   * @return the responseTimeId
   */
  public String getResponseTimeId() {
    return responseTimeId;
  }

  /**
   * @return the allegations
   */
  public Set<Allegation> getAllegations() {
    return allegations;
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
   * @return the responseTime
   */
  public String getResponseTime() {
    return responseTime;
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
