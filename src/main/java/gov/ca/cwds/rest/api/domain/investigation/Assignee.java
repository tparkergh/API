package gov.ca.cwds.rest.api.domain.investigation;

import java.io.Serializable;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

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
@JsonPropertyOrder({"county_code", "office", "staff_id"})
public class Assignee implements Serializable {

  @JsonProperty("name")
  @ApiModelProperty(required = true, readOnly = false,
      value = "name of staff person assigned to the investigation", example = "Madera Staff Person")
  private String name;

  @JsonProperty("county_code")
  @NotEmpty
  @ApiModelProperty(required = true, readOnly = false,
      value = "County with primary assignment responsibility for Investigation", example = "34")
  @ValidLogicalId(required = true, category = SystemCodeCategoryId.COUNTY_CODE)
  private String countyCode;

  @JsonProperty("office")
  @ApiModelProperty(required = true, readOnly = false,
      value = "name of the office assigned to investigate", example = "Madera CWS Office")
  private String office;

  @JsonProperty("staff_id")
  @ApiModelProperty(required = false, readOnly = false, value = "staff person id", example = "OX5")
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
  public Assignee(@JsonProperty("name") String name,
      @JsonProperty("county_code") @ValidLogicalId(required = true,
          category = "GVR_ENTC") String countyCode,
      @JsonProperty("office") String office, @JsonProperty("staff_id") String staffId) {
    super();
    this.name = name;
    this.countyCode = countyCode;
    this.office = office;
    this.staffId = staffId;
  }

  /**
   * @return social worker name
   */
  public String getName() {
    return name;
  }

  /**
   * @return county code
   */
  public String getCountyCode() {
    return countyCode;
  }

  /**
   * @return office name
   */
  public String getOffice() {
    return office;
  }

  /**
   * @return staff Id
   */
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
