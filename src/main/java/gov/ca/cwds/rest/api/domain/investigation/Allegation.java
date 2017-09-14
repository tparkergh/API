package gov.ca.cwds.rest.api.domain.investigation;

import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.DomainObject;
import gov.ca.cwds.rest.api.domain.ReportingDomain;
import io.dropwizard.jackson.JsonSnakeCase;
import io.swagger.annotations.ApiModelProperty;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * {@link DomainObject} representing an Allegation
 * 
 * @author CWDS API Team
 */
@JsonSnakeCase
@JsonPropertyOrder({"victim_last_name", "victim_first_name", "perpetrator_last_name",
    "perpetrator_first_name", "disposition_description", "allegation_description"})
public class Allegation extends ReportingDomain implements Response {

  private static final long serialVersionUID = 1L;

  @JsonProperty("victim_last_name")
  @ApiModelProperty(required = false, readOnly = false, value = "", example = "Smith")
  private String victimLastName;

  @JsonProperty("victim_first_name")
  @ApiModelProperty(required = false, readOnly = false, value = "", example = "Cody")
  private String victimFirstName;

  @JsonProperty("perpetrator_last_name")
  @ApiModelProperty(required = false, readOnly = false, value = "", example = "B")
  private String perpetratorLastName;

  @JsonProperty("perpetrator_first_name")
  @ApiModelProperty(required = false, readOnly = false, value = "", example = "Jeremy")
  private String perpetratorFirstName;

  @JsonProperty("disposition_description")
  @ApiModelProperty(required = false, readOnly = false, value = "", example = "Substantiated")
  private String dispositionDescription;

  @JsonProperty("allegation_description")
  @ApiModelProperty(required = false, readOnly = false, value = "", example = "Physical Abuse")
  private String allegationDescription;

  /**
   * Constructor
   * 
   * @param victimLastName victim last name
   * @param victimFirstName victim first name
   * @param perpetratorLastName perpetrator last name
   * @param perpetratorFirstName perpetrator first name
   * @param dispositionDescription disposition description
   * @param allegationDescription allegation description
   */
  public Allegation(@JsonProperty("victim_last_name") String victimLastName,
      @JsonProperty("victim_first_name") String victimFirstName,
      @JsonProperty("perpetrator_last_name") String perpetratorLastName,
      @JsonProperty("perpetrator_first_name") String perpetratorFirstName,
      @JsonProperty("disposition_description") String dispositionDescription,
      @JsonProperty("allegation_description") String allegationDescription) {
    super();
    this.victimLastName = victimLastName;
    this.victimFirstName = victimFirstName;
    this.perpetratorLastName = perpetratorLastName;
    this.perpetratorFirstName = perpetratorFirstName;
    this.dispositionDescription = dispositionDescription;
    this.allegationDescription = allegationDescription;
  }

  /**
   * @return the victimLastName
   */
  public String getVictimLastName() {
    return victimLastName;
  }



  /**
   * @return the victimFirstName
   */
  public String getVictimFirstName() {
    return victimFirstName;
  }



  /**
   * @return the perpetratorLastName
   */
  public String getPerpetratorLastName() {
    return perpetratorLastName;
  }



  /**
   * @return the perpetratorFirstName
   */
  public String getPerpetratorFirstName() {
    return perpetratorFirstName;
  }



  /**
   * @return the dispositionDescription
   */
  public String getDispositionDescription() {
    return dispositionDescription;
  }



  /**
   * @return the allegationDescription
   */
  public String getAllegationDescription() {
    return allegationDescription;
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
