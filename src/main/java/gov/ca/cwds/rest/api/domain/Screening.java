package gov.ca.cwds.rest.api.domain;

import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.validation.Date;
import io.dropwizard.jackson.JsonSnakeCase;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * {@link DomainObject} representing a screening
 * 
 * @author CWDS API Team
 */
@JsonSnakeCase
@ApiModel("Screening")
public class Screening extends ReportingDomain implements Request, Response {
  /**
   * Serialization version
   */
  private static final long serialVersionUID = 1L;

  @JsonProperty("id")
  @Size(min = 1, max = 50)
  @ApiModelProperty(required = true, readOnly = false, value = "Screener id",
      example = "ABC1234568")
  private String id;

  @JsonProperty("name")
  @Size(min = 1, max = 50)
  @ApiModelProperty(required = true, readOnly = false, value = "screener Name",
      example = "Some Screening name")
  private String name;

  @JsonProperty("reference")
  @Size(min = 1, max = 50)
  @ApiModelProperty(required = true, readOnly = false, value = "screener Reference",
      example = "Screening Reference")
  private String reference;

  @JsonProperty("screening_decision")
  @Size(min = 1, max = 50)
  @ApiModelProperty(required = true, readOnly = false, value = "screener decision",
      example = "Screening decision")
  private String screeningDecision;

  @JsonProperty("screening_decision_detail")
  @Size(min = 1, max = 50)
  @ApiModelProperty(required = true, readOnly = false, value = "screener decision detail",
      example = "Screening decision Detail")
  private String screeningDecisionDetail;

  @JsonProperty("assignee")
  @Size(min = 1, max = 50)
  @ApiModelProperty(required = true, readOnly = false, value = "screener assignee",
      example = "assignee")
  private String assignee;

  @JsonProperty("started_at")
  @Type(type = "date")
  @ApiModelProperty(required = true, readOnly = false, value = "startdate of the Screening",
      example = "1992-06-18")
  @Date(format = "yyyy-MM-dd", required = true)
  private String startedAt;

  /**
   * default constructor
   */
  public Screening() {
    super();
  }

  /**
   * @param id - id
   * @param name - name
   * @param reference - reference
   * @param screeningDecision - screeningDecision
   * @param screeningDecisionDetail - screeningDecisionDetail
   * @param assignee - assignee
   * @param startedAt - startedAt
   */
  @JsonCreator
  public Screening(@JsonProperty("id") String id, @JsonProperty("name") String name,
      @JsonProperty("reference") String reference,
      @JsonProperty("screening_decision") String screeningDecision,
      @JsonProperty("screening_decision_detail") String screeningDecisionDetail,
      @JsonProperty("assignee") String assignee, @JsonProperty("started_at") String startedAt) {
    super();
    this.id = id;
    this.name = name;
    this.reference = reference;
    this.screeningDecision = screeningDecision;
    this.screeningDecisionDetail = screeningDecisionDetail;
    this.assignee = assignee;
    this.startedAt = startedAt;
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
   * @return the reference
   */
  public String getReference() {
    return reference;
  }

  /**
   * @return the screeningDecision
   */
  public String getScreeningDecision() {
    return screeningDecision;
  }

  /**
   * @return the screeningDecisionDetail
   */
  public String getScreeningDecisionDetail() {
    return screeningDecisionDetail;
  }

  /**
   * @return the assignee
   */
  public String getAssignee() {
    return assignee;
  }

  /**
   * @return the startedAt
   */
  public String getStartedAt() {
    return startedAt;
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
