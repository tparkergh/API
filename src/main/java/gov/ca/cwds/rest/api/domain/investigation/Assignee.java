package gov.ca.cwds.rest.api.domain.investigation;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;

import gov.ca.cwds.rest.api.domain.DomainObject;
import gov.ca.cwds.rest.api.domain.SystemCodeCategoryId;
import gov.ca.cwds.rest.validation.ValidLogicalId;
import io.dropwizard.jackson.JsonSnakeCase;
import io.swagger.annotations.ApiModelProperty;

/**
 * {@link DomainObject} representing a Investigation
 * 
 * @author CWDS API Team
 */

@JsonSnakeCase
public class Assignee {

  @JsonProperty("name")
  @ApiModelProperty(required = true, readOnly = false,
      value = "name of staff person assigned to the investigation")
  private String name;

  @JsonProperty("county_code")
  @NotEmpty
  @ApiModelProperty(required = true, readOnly = false,
      value = "County with primary assignment responsibility for Investigation", example = "34")
  @ValidLogicalId(required = true, category = SystemCodeCategoryId.COUNTY_CODE)
  private String countyCode;

  @JsonProperty("office")
  @ApiModelProperty(required = true, readOnly = false,
      value = "name of the office assigned to investigate")
  private String office;

  @JsonProperty("staff_id")
  @ApiModelProperty(required = false, readOnly = false, value = "staff person id")
  private String staffId;

  /**
   * @param name - name of staff person
   * @param countyCode - county code
   * @param office - office name
   * @param staffId - staff person ID
   */
  public Assignee(String name,
      @ValidLogicalId(required = true, category = "GVR_ENTC") String countyCode, String office,
      String staffId) {
    super();
    this.name = name;
    this.countyCode = countyCode;
    this.office = office;
    this.staffId = staffId;
  }

  public String getName() {
    return name;
  }

  public String getCountyCode() {
    return countyCode;
  }

  public String getOffice() {
    return office;
  }

  public String getStaffId() {
    return staffId;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((countyCode == null) ? 0 : countyCode.hashCode());
    result = prime * result + ((name == null) ? 0 : name.hashCode());
    result = prime * result + ((office == null) ? 0 : office.hashCode());
    result = prime * result + ((staffId == null) ? 0 : staffId.hashCode());
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
    Assignee other = (Assignee) obj;
    if (countyCode == null) {
      if (other.countyCode != null)
        return false;
    } else if (!countyCode.equals(other.countyCode))
      return false;
    if (name == null) {
      if (other.name != null)
        return false;
    } else if (!name.equals(other.name))
      return false;
    if (office == null) {
      if (other.office != null)
        return false;
    } else if (!office.equals(other.office))
      return false;
    if (staffId == null) {
      if (other.staffId != null)
        return false;
    } else if (!staffId.equals(other.staffId))
      return false;
    return true;
  }

}
