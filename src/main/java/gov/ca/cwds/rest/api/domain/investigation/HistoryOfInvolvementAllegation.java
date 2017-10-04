package gov.ca.cwds.rest.api.domain.investigation;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import io.dropwizard.jackson.JsonSnakeCase;
import io.swagger.annotations.ApiModelProperty;

/**
 * 
 * @author CWDS API Team
 */
@JsonSnakeCase
@JsonPropertyOrder({"victim_last_name", "victim_first_name", "perpetrator_last_name",
    "perpetrator_first_name", "disposition_description", "allegation_description"})
public class HistoryOfInvolvementAllegation {

  @JsonProperty("victim_last_name")
  @ApiModelProperty(required = false, readOnly = false, value = "Victim's last name",
      example = "Smith")
  private String victimLastName;

  @JsonProperty("victim_first_name")
  @ApiModelProperty(required = false, readOnly = false, value = "Victim's first name",
      example = "Cody")
  private String victimFirstName;

  @JsonProperty("perpetrator_last_name")
  @ApiModelProperty(required = false, readOnly = false, value = "Perpetrator's last name",
      example = "Berk")
  private String perpetratorLastName;

  @JsonProperty("perpetrator_first_name")
  @ApiModelProperty(required = false, readOnly = false, value = "Perpetrator's first name",
      example = "Jeremy")
  private String pereptratorFistName;

  @JsonProperty("disposition_description")
  @ApiModelProperty(required = false, readOnly = false,
      value = "Description of allegation disposition", example = "this probaly happened")
  private String dispositionDescription;

  @JsonProperty("allegation_description")
  @ApiModelProperty(required = false, readOnly = false, value = "Description of allegation",
      example = "Physical Abuse")
  private String allegationDescription;

  /**
   * 
   */
  public HistoryOfInvolvementAllegation() {
    super();
  }

  /**
   * @param victimLastName - victim's last name
   * @param victimFirstName - victim's first name
   * @param perpetratorLastName - perpetrator's last name
   * @param pereptratorFistName - perpetrator's first name
   * @param dispositionDescription - description of the disposition
   * @param allegationDescription - description of the allegation
   */
  public HistoryOfInvolvementAllegation(String victimLastName, String victimFirstName,
      String perpetratorLastName, String pereptratorFistName, String dispositionDescription,
      String allegationDescription) {
    super();
    this.victimLastName = victimLastName;
    this.victimFirstName = victimFirstName;
    this.perpetratorLastName = perpetratorLastName;
    this.pereptratorFistName = pereptratorFistName;
    this.dispositionDescription = dispositionDescription;
    this.allegationDescription = allegationDescription;
  }

  public String getVictimLastName() {
    return victimLastName;
  }

  public void setVictimLastName(String victimLastName) {
    this.victimLastName = victimLastName;
  }

  public String getVictimFirstName() {
    return victimFirstName;
  }

  public void setVictimFirstName(String victimFirstName) {
    this.victimFirstName = victimFirstName;
  }

  public String getPerpetratorLastName() {
    return perpetratorLastName;
  }

  public void setPerpetratorLastName(String perpetratorLastName) {
    this.perpetratorLastName = perpetratorLastName;
  }

  public String getPereptratorFistName() {
    return pereptratorFistName;
  }

  public void setPereptratorFistName(String pereptratorFistName) {
    this.pereptratorFistName = pereptratorFistName;
  }

  public String getDispositionDescription() {
    return dispositionDescription;
  }

  public void setDispositionDescription(String dispositionDescription) {
    this.dispositionDescription = dispositionDescription;
  }

  public String getAllegationDescription() {
    return allegationDescription;
  }

  public void setAllegationDescription(String allegationDescription) {
    this.allegationDescription = allegationDescription;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result =
        prime * result + ((allegationDescription == null) ? 0 : allegationDescription.hashCode());
    result =
        prime * result + ((dispositionDescription == null) ? 0 : dispositionDescription.hashCode());
    result = prime * result + ((pereptratorFistName == null) ? 0 : pereptratorFistName.hashCode());
    result = prime * result + ((perpetratorLastName == null) ? 0 : perpetratorLastName.hashCode());
    result = prime * result + ((victimFirstName == null) ? 0 : victimFirstName.hashCode());
    result = prime * result + ((victimLastName == null) ? 0 : victimLastName.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    HistoryOfInvolvementAllegation other = (HistoryOfInvolvementAllegation) obj;
    if (allegationDescription == null) {
      if (other.allegationDescription != null)
        return false;
    } else if (!allegationDescription.equals(other.allegationDescription))
      return false;
    if (dispositionDescription == null) {
      if (other.dispositionDescription != null)
        return false;
    } else if (!dispositionDescription.equals(other.dispositionDescription))
      return false;
    if (pereptratorFistName == null) {
      if (other.pereptratorFistName != null)
        return false;
    } else if (!pereptratorFistName.equals(other.pereptratorFistName))
      return false;
    if (perpetratorLastName == null) {
      if (other.perpetratorLastName != null)
        return false;
    } else if (!perpetratorLastName.equals(other.perpetratorLastName))
      return false;
    if (victimFirstName == null) {
      if (other.victimFirstName != null)
        return false;
    } else if (!victimFirstName.equals(other.victimFirstName))
      return false;
    if (victimLastName == null) {
      if (other.victimLastName != null)
        return false;
    } else if (!victimLastName.equals(other.victimLastName))
      return false;
    return true;
  }

}
