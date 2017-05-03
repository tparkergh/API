package gov.ca.cwds.rest.api.domain;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.validation.Date;
import io.dropwizard.jackson.JsonSnakeCase;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * {@link DomainObject} representing an cross_report
 * 
 * @author CWDS API Team
 */
@SuppressWarnings("serial")
@JsonSnakeCase
@ApiModel("NsCrossReport")
public class CrossReport extends ReportingDomain implements Request, Response {

  @JsonProperty("agency_type")
  @ApiModelProperty(required = true, value = "Cross Report to", example = "Law Enforcement")
  @NotEmpty
  private String agencyType;

  @JsonProperty("agency_name")
  @ApiModelProperty(required = true, value = "Agency Name", example = "Sheriff Department")
  @Size(max = 120)
  @NotEmpty
  private String agencyName;

  @JsonProperty("method")
  @ApiModelProperty(required = true, value = "communication method", example = "phone")
  @NotEmpty
  private String method;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_FORMAT)
  @Date
  @JsonProperty("inform_date")
  @ApiModelProperty(required = true, value = "yyyy-MM-dd", example = "2001-09-13")
  private String informDate;


  /**
   * @param agencyType - Agency Type
   * @param agencyName - Agency Name
   * @param method - reporting method
   * @param informDate - reported date
   */
  public CrossReport(@JsonProperty("agency_type") String agencyType,
      @JsonProperty("agency_name") String agencyName, @JsonProperty("method") String method,
      @JsonProperty("inform_date") String informDate) {
    super();
    this.agencyType = agencyType;
    this.agencyName = agencyName;
    this.method = method;
    this.informDate = informDate;
  }

  /**
   * @return agencyType
   */
  public String getAgencyType() {
    return agencyType;
  }

  /**
   * @return agencyName
   */
  public String getAgencyName() {
    return agencyName;
  }

  /**
   * @return method
   */
  public String getMethod() {
    return method;
  }

  /**
   * @return informDate
   */
  public String getInformDate() {
    return informDate;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#hashCode()
   */
  @Override
  public final int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((agencyName == null) ? 0 : agencyName.hashCode());
    result = prime * result + ((agencyType == null) ? 0 : agencyType.hashCode());
    result = prime * result + ((informDate == null) ? 0 : informDate.hashCode());
    result = prime * result + ((method == null) ? 0 : method.hashCode());
    return result;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public final boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (!(obj instanceof CrossReport)) {
      return false;
    }
    CrossReport other = (CrossReport) obj;
    if (agencyName == null) {
      if (other.agencyName != null)
        return false;
    } else if (!agencyName.equals(other.agencyName))
      return false;
    if (agencyType == null) {
      if (other.agencyType != null)
        return false;
    } else if (!agencyType.equals(other.agencyType))
      return false;
    if (informDate == null) {
      if (other.informDate != null)
        return false;
    } else if (!informDate.equals(other.informDate))
      return false;
    if (method == null) {
      if (other.method != null)
        return false;
    } else if (!method.equals(other.method))
      return false;
    return true;
  }

}
