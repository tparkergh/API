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
 * {@link DomainObject} representing a Screening Summary
 * 
 * @author CWDS API Team
 */
@JsonInclude(Include.ALWAYS)
@JsonSnakeCase
@JsonPropertyOrder({"id", "name", "decision", "decision_detail", "safety_alerts",
    "safety_information", "additional_information", "started_at", "allegations"})
public class ScreeningSummary extends ReportingDomain implements Response {

  /**
   * Default
   */
  private static final long serialVersionUID = 1L;

  @JsonProperty("id")
  @ApiModelProperty(required = false, readOnly = false, value = "", example = "1")
  private String id;

  @JsonProperty("name")
  @ApiModelProperty(required = false, readOnly = false, value = "", example = "Henderson Screening")
  private String name;

  @JsonProperty("decision")
  @ApiModelProperty(required = false, readOnly = false, value = "", example = "promote_to_referral")
  private String decision;

  @JsonProperty("decision_detail")
  @ApiModelProperty(required = false, readOnly = false, value = "", example = "immediate")
  private String decisionDetail;

  @JsonProperty("safety_alerts")
  @ApiModelProperty(required = false, readOnly = false, value = "",
      example = "[Dangerous Animal on Premises, Firearms in Home, Severe Mental Health Status]")
  private Set<String> safetyAlerts;

  @JsonProperty("safety_information")
  @ApiModelProperty(required = false, readOnly = false, value = "",
      example = "The animal at residence is a lion")
  private String safetyInformation;

  @JsonProperty("additional_information")
  @ApiModelProperty(required = false, readOnly = false, value = "",
      example = "There was excessive evidence of abuse")
  private String additionalInformation;

  @JsonProperty("started_at")
  @ApiModelProperty(required = false, readOnly = false, value = "",
      example = "2017-09-01T16:48:05.457Z")
  private String startedAt;

  @JsonProperty("allegations")
  @ApiModelProperty(required = false, readOnly = false, value = "", example = "")
  private Set<SimpleAllegation> allegations;

  /**
   * Default
   */
  public ScreeningSummary() {
    super();
  }

  /**
   * Constructor
   * 
   * @param id the id
   * @param name name
   * @param decision decision
   * @param decisionDetail the decision detail
   * @param safetyAlerts the safety alerts
   * @param safetyInformation the safety information
   * @param additionalInformation the additional information
   * @param startedAt started at
   * @param allegations the allegations
   */
  public ScreeningSummary(@JsonProperty("id") String id, @JsonProperty("name") String name,
      @JsonProperty("decision") String decision,
      @JsonProperty("decision_detail") String decisionDetail,
      @JsonProperty("safety_alerts") Set<String> safetyAlerts,
      @JsonProperty("safety_information") String safetyInformation,
      @JsonProperty("additional_information") String additionalInformation,
      @JsonProperty("started_at") String startedAt,
      @JsonProperty("allegations") Set<SimpleAllegation> allegations) {
    super();
    this.id = id;
    this.name = name;
    this.decision = decision;
    this.decisionDetail = decisionDetail;
    this.safetyAlerts = safetyAlerts;
    this.safetyInformation = safetyInformation;
    this.additionalInformation = additionalInformation;
    this.startedAt = startedAt;
    this.allegations = allegations;
  }

  /**
   * @return the id
   */
  public String getId() {
    return id;
  }


  /**
   * @return the name
   */
  public String getName() {
    return name;
  }


  /**
   * @return the decision
   */
  public String getDecision() {
    return decision;
  }


  /**
   * @return the decisionDetail
   */
  public String getDecisionDetail() {
    return decisionDetail;
  }


  /**
   * @return the safetyAlerts
   */
  public Set<String> getSafetyAlerts() {
    return safetyAlerts;
  }


  /**
   * @return the safetyInformation
   */
  public String getSafetyInformation() {
    return safetyInformation;
  }


  /**
   * @return the additionalInformation
   */
  public String getAdditionalInformation() {
    return additionalInformation;
  }


  /**
   * @return the startedAt
   */
  public String getStartedAt() {
    return startedAt;
  }


  /**
   * @return the allegations
   */
  public Set<SimpleAllegation> getAllegations() {
    return allegations;
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
