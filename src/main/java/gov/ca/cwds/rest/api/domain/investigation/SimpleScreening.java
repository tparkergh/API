package gov.ca.cwds.rest.api.domain.investigation;

import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.DomainObject;
import gov.ca.cwds.rest.api.domain.ReportingDomain;
import io.dropwizard.jackson.JsonSnakeCase;
import io.swagger.annotations.ApiModelProperty;

import java.util.Set;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * {@link DomainObject} representing a Simple Screening
 * 
 * @author CWDS API Team
 */
@JsonInclude(Include.ALWAYS)
@JsonSnakeCase
@JsonPropertyOrder({"id", "end_date", "reporter", "county_name", "decision", "service_name",
    "all_people", "assigned_social_worker", "start_date"})
public class SimpleScreening extends ReportingDomain implements Response {

  /**
   * Default
   */
  private static final long serialVersionUID = 1L;

  @JsonProperty("id")
  @ApiModelProperty(required = false, readOnly = false, value = "")
  private String id;

  @JsonProperty("end_date")
  @ApiModelProperty(required = false, readOnly = false, value = "", example = "2017-08-23")
  private String endDate;

  @JsonProperty("decision")
  @ApiModelProperty(required = false, readOnly = false, value = "", example = "promote to referral")
  private String decision;

  @JsonProperty("service_name")
  private String serviceName;

  @JsonProperty("reporter")
  private SimplePerson reporter;

  @JsonProperty("county_name")
  @ApiModelProperty(required = false, readOnly = false, value = "", example = "Fresno")
  private String countyName;

  @JsonProperty("all_people")
  private Set<SimplePersonWithRoles> allPeople;

  @JsonProperty("assigned_social_worker")
  private SimplePerson assignedSocialWorker;

  @JsonProperty("start_date")
  @ApiModelProperty(required = false, readOnly = false, value = "", example = "2017-08-22")
  private String startDate;

  /**
   * 
   */
  public SimpleScreening() {
    super();
  }

  /**
   * Constructor
   * 
   * @param id the id
   * @param endDate the end date
   * @param decision the decision
   * @param serviceName the service name
   * @param reporter the reporter
   * @param countyName the county name
   * @param allPeople the participants
   * @param assignedSocialWorker the assigned social worker
   * @param startDate the start date
   */
  public SimpleScreening(@JsonProperty("id") String id, @JsonProperty("end_date") String endDate,
      @JsonProperty("decision") String decision, @JsonProperty("service_name") String serviceName,
      @JsonProperty("reporter") SimplePerson reporter,
      @JsonProperty("county_name") String countyName,
      @JsonProperty("all_people") Set<SimplePersonWithRoles> allPeople,
      @JsonProperty("assigned_social_worker") SimplePerson assignedSocialWorker,
      @JsonProperty("start_date") String startDate) {
    super();
    this.id = id;
    this.endDate = endDate;
    this.decision = decision;
    this.serviceName = serviceName;
    this.reporter = reporter;
    this.countyName = countyName;
    this.allPeople = allPeople;
    this.assignedSocialWorker = assignedSocialWorker;
    this.startDate = startDate;
  }


  /**
   * @return the endDate
   */
  public String getEndDate() {
    return endDate;
  }

  /**
   * @return the staffName
   */
  public String getId() {
    return id;
  }

  /**
   * @return the decision
   */
  public String getDecision() {
    return decision;
  }

  /**
   * @return the serviceName
   */
  public String getServiceName() {
    return serviceName;
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
   * @return the allPeople
   */
  public Set<SimplePersonWithRoles> getAllPeople() {
    return allPeople;
  }

  /**
   * @return the assignedSocialWorker
   */
  public SimplePerson getAssignedSocialWorker() {
    return assignedSocialWorker;
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
