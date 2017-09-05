package gov.ca.cwds.rest.api.domain.cms;

import static gov.ca.cwds.data.persistence.cms.CmsPersistentObject.CMS_ID_LEN;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.DomainChef;
import gov.ca.cwds.rest.api.domain.DomainObject;
import gov.ca.cwds.rest.validation.Date;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * {@link DomainObject} representing a StaffPersonCaseLoad
 * 
 * @author CWDS API Team
 */
@ApiModel
public class StaffPersonCaseLoad extends DomainObject implements Request, Response {

  /**
   * Serialization version
   */
  private static final long serialVersionUID = 1L;

  @NotEmpty
  @Size(min = 1, max = 2)
  @ApiModelProperty(required = true, readOnly = false, value = "", example = "99")
  private String countyCode;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_FORMAT)
  @JsonProperty(value = "endDate")
  @gov.ca.cwds.rest.validation.Date(format = DATE_FORMAT, required = false)
  @ApiModelProperty(required = false, readOnly = false, value = "yyyy-MM-dd",
      example = "2016-10-31")
  private String endDate;

  @NotEmpty
  @Size(min = CMS_ID_LEN, max = CMS_ID_LEN)
  @ApiModelProperty(required = true, readOnly = false, value = "contains Case Load",
      example = "ABC1234567")
  private String fkCaseLoad;

  @NotEmpty
  @Size(min = 3, max = 3)
  @ApiModelProperty(required = true, readOnly = false, value = "is assigned to Case Load",
      example = "ABC")
  private String fkStaffPerson;

  @NotEmpty
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_FORMAT)
  @JsonProperty(value = "startDate")
  @gov.ca.cwds.rest.validation.Date(format = DATE_FORMAT, required = false)
  @ApiModelProperty(required = true, readOnly = false, value = "yyyy-MM-dd", example = "2016-10-31")
  private String startDate;

  @NotNull
  @Size(min = CMS_ID_LEN, max = CMS_ID_LEN)
  @ApiModelProperty(required = true, readOnly = true, value = "Unique key", example = "1234ABC123")
  private String thirdId;

  @SuppressWarnings("javadoc")
  public StaffPersonCaseLoad() {
    super();
  }

  /**
   * @param countyCode - county specific code
   * @param endDate - end date of Staff Person Case Load
   * @param fkCaseLoad - Id to Case Load
   * @param fkStaffPerson - Id to Staff
   * @param startDate - start date of Staff Person Case Load
   * @param thirdId - unique key to Staff Person Case Load
   */
  public StaffPersonCaseLoad(String countyCode,
      @Date(format = "yyyy-MM-dd", required = false) String endDate, String fkCaseLoad,
      String fkStaffPerson, @Date(format = "yyyy-MM-dd", required = false) String startDate,
      String thirdId) {
    super();
    this.countyCode = countyCode;
    this.endDate = endDate;
    this.fkCaseLoad = fkCaseLoad;
    this.fkStaffPerson = fkStaffPerson;
    this.startDate = startDate;
    this.thirdId = thirdId;
  }

  /**
   * @param persisted - StaffPersonCaseLoad
   */
  public StaffPersonCaseLoad(gov.ca.cwds.data.persistence.cms.StaffPersonCaseLoad persisted) {
    super();
    this.countyCode = persisted.getCountyCode();
    this.endDate = DomainChef.cookDate(persisted.getEndDate());
    this.fkCaseLoad = persisted.getFkCaseLoad();
    this.fkStaffPerson = persisted.getFkStaffPerson();
    this.startDate = DomainChef.cookDate(persisted.getStartDate());
    this.thirdId = persisted.getThirdId();
  }

  @SuppressWarnings("javadoc")
  public String getCountyCode() {
    return countyCode;
  }

  @SuppressWarnings("javadoc")
  public String getEndDate() {
    return endDate;
  }

  @SuppressWarnings("javadoc")
  public String getFkCaseLoad() {
    return fkCaseLoad;
  }

  @SuppressWarnings("javadoc")
  public String getFkStaffPerson() {
    return fkStaffPerson;
  }

  @SuppressWarnings("javadoc")
  public String getStartDate() {
    return startDate;
  }

  @SuppressWarnings("javadoc")
  public String getThirdId() {
    return thirdId;
  }

  @Override
  public boolean equals(Object obj) {
    return EqualsBuilder.reflectionEquals(this, obj, false);
  }

  @Override
  public int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this, false);
  }

}
