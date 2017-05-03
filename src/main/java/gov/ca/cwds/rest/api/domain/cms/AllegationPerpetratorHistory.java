package gov.ca.cwds.rest.api.domain.cms;

import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.DomainChef;
import gov.ca.cwds.rest.api.domain.DomainObject;
import gov.ca.cwds.rest.api.domain.ReportingDomain;
import io.swagger.annotations.ApiModelProperty;

/**
 * This domain class created only for demo purpose and will be removed later
 * 
 * @author CWDS API Team
 */
public class AllegationPerpetratorHistory extends ReportingDomain implements Request, Response {
  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  @ApiModelProperty(required = true, readOnly = false, value = "County code", example = "99")
  private String countySpecificCode;

  @Size(min = 10, max = 10)
  @ApiModelProperty(required = false, readOnly = false, value = "CLIENT ID of victim",
      example = "ABC1234568")
  private String victimClientId;

  @ApiModelProperty(required = true, readOnly = false, value = "ALLEGATION ID",
      example = "ABC1234567")
  private String allegationId;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_FORMAT)
  @JsonProperty(value = "perpetratorUpdateDate")
  @gov.ca.cwds.rest.validation.Date(format = DATE_FORMAT, required = true)
  @ApiModelProperty(required = true, readOnly = false, value = "yyyy-MM-dd", example = "2016-11-30")
  private String perpetratorUpdateDate;

  /**
   * Constructor. Build from JSON.
   * 
   * @param countySpecificCode county Specific Code
   * @param victimClientId victim Client Id
   * @param allegationId allegation Id
   * @param perpetratorUpdateDate perpetrator Update Date
   */
  @JsonCreator
  public AllegationPerpetratorHistory(@JsonProperty("countySpecificCode") String countySpecificCode,
      @JsonProperty("victimClientId") String victimClientId,
      @JsonProperty("allegationId") String allegationId,
      @JsonProperty("perpetratorUpdateDate") String perpetratorUpdateDate) {
    super();
    this.countySpecificCode = countySpecificCode;
    this.victimClientId = victimClientId;
    this.allegationId = allegationId;
    this.perpetratorUpdateDate = perpetratorUpdateDate;
  }

  @SuppressWarnings("javadoc")
  public AllegationPerpetratorHistory(
      gov.ca.cwds.data.persistence.cms.AllegationPerpetratorHistory persistedAllegationPerpetratorHistory) {
    this.countySpecificCode = persistedAllegationPerpetratorHistory.getCountySpecificCode();
    this.victimClientId = persistedAllegationPerpetratorHistory.getVictimClientId();
    this.allegationId = persistedAllegationPerpetratorHistory.getAllegationId();
    this.perpetratorUpdateDate =
        DomainChef.cookDate(persistedAllegationPerpetratorHistory.getPerpetratorUpdateDate());
  }

  @SuppressWarnings("javadoc")
  public String getCountySpecificCode() {
    return countySpecificCode;
  }

  @SuppressWarnings("javadoc")
  public String getVictimClientId() {
    return victimClientId;
  }

  @SuppressWarnings("javadoc")
  public String getAllegationId() {
    return allegationId;
  }

  @SuppressWarnings("javadoc")
  public String getPerpetratorUpdateDate() {
    return perpetratorUpdateDate;
  }

  @Override
  public final int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((allegationId == null) ? 0 : allegationId.hashCode());
    result = prime * result + ((countySpecificCode == null) ? 0 : countySpecificCode.hashCode());
    result =
        prime * result + ((perpetratorUpdateDate == null) ? 0 : perpetratorUpdateDate.hashCode());
    result = prime * result + ((victimClientId == null) ? 0 : victimClientId.hashCode());
    return result;
  }

  @Override
  public final boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (!(obj instanceof AllegationPerpetratorHistory)) {
      return false;
    }
    AllegationPerpetratorHistory other = (AllegationPerpetratorHistory) obj;
    if (allegationId == null) {
      if (other.allegationId != null) {
        return false;
      }
    } else if (!allegationId.equals(other.allegationId)) {
      return false;
    }
    if (countySpecificCode == null) {
      if (other.countySpecificCode != null) {
        return false;
      }
    } else if (!countySpecificCode.equals(other.countySpecificCode)) {
      return false;
    }
    if (perpetratorUpdateDate == null) {
      if (other.perpetratorUpdateDate != null) {
        return false;
      }
    } else if (!perpetratorUpdateDate.equals(other.perpetratorUpdateDate)) {
      return false;
    }
    if (victimClientId == null) {
      if (other.victimClientId != null) {
        return false;
      }
    } else if (!victimClientId.equals(other.victimClientId)) {
      return false;
    }
    return true;
  }

}
