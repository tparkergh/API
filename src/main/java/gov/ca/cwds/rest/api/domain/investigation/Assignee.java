package gov.ca.cwds.rest.api.domain.investigation;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
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
   * empty constructor
   */
  public Assignee() {
    super();
  }

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
