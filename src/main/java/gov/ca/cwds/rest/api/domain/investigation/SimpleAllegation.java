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
 * {@link DomainObject} representing a Simple Allegation
 * 
 * @author CWDS API Team
 */
@JsonSnakeCase
@JsonPropertyOrder({"victim_id", "perpetrator_id", "allegation_types"})
public class SimpleAllegation extends ReportingDomain implements Response {

  private static final long serialVersionUID = 1L;
  @JsonProperty("victim_id")
  @ApiModelProperty(required = false, readOnly = false, value = "", example = "22")
  private String victimId;

  @JsonProperty("perpetrator_id")
  @ApiModelProperty(required = false, readOnly = false, value = "", example = "11")
  private String perpetratorId;

  @JsonProperty("allegation_types")
  @ApiModelProperty(required = false, readOnly = false, value = "",
      example = "[General neglect, Physical abuse]")
  private Set<String> allegationTypes;

  /**
   * Constructor
   * 
   * @param victimId the victim id
   * @param perpetratorId the perpetrator id
   * @param allegationTypes the allegation types
   */
  public SimpleAllegation(@JsonProperty("victim_id") String victimId,
      @JsonProperty("perpetrator_id") String perpetratorId,
      @JsonProperty("allegation_types") Set<String> allegationTypes) {
    super();
    this.victimId = victimId;
    this.perpetratorId = perpetratorId;
    this.allegationTypes = allegationTypes;
  }


  /**
   * @return the victimId
   */
  public String getVictimId() {
    return victimId;
  }


  /**
   * @return the perpetratorId
   */
  public String getPerpetratorId() {
    return perpetratorId;
  }


  /**
   * @return the allegationTypes
   */
  public Set<String> getAllegationTypes() {
    return allegationTypes;
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
