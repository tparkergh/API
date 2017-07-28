package gov.ca.cwds.rest.api.domain;

import static gov.ca.cwds.data.persistence.cms.CmsPersistentObject.CMS_ID_LEN;
import gov.ca.cwds.rest.api.Request;
import io.dropwizard.jackson.JsonSnakeCase;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * {@link DomainObject} representing a staff person
 * 
 * @author CWDS API Team
 */
@JsonSnakeCase
public class StaffPerson extends ReportingDomain implements Request {
  /**
   * Serialization version
   */
  private static final long serialVersionUID = 1L;

  @JsonProperty("end_date")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_FORMAT)
  @gov.ca.cwds.rest.validation.Date(format = DATE_FORMAT, required = false)
  @ApiModelProperty(required = false, readOnly = false, value = "yyyy-MM-dd",
      example = "2016-10-31")
  private String endDate;

  @JsonProperty("first_name")
  @NotEmpty
  @Size(min = 1, max = 20)
  @ApiModelProperty(required = true, readOnly = false, value = "", example = "John")
  private String firstName;

  @JsonProperty("job_title")
  @Size(max = 30)
  @ApiModelProperty(required = false, readOnly = false, value = "Job Title",
      example = "Case Worker")
  private String jobTitle;

  @JsonProperty("last_name")
  @NotEmpty
  @Size(min = 1, max = 25)
  @ApiModelProperty(required = true, readOnly = false, value = "", example = "Smith")
  private String lastName;

  @JsonProperty("middle_initial")
  @Size(max = 1, message = "size must be 1")
  @ApiModelProperty(required = false, readOnly = false, value = "", example = "Q")
  private String middleInitial;

  @JsonProperty("name_prefix")
  @Size(max = 6)
  @ApiModelProperty(required = false, readOnly = false, value = "", example = "MR.")
  private String namePrefix;

  @JsonProperty("phone_number")
  @NotNull
  @ApiModelProperty(required = true, readOnly = false, example = "9165551212")
  private BigDecimal phoneNumber;

  @JsonProperty("phone_ext")
  @NotNull
  @ApiModelProperty(required = false, readOnly = false, example = "123")
  private Integer phoneExt;

  @JsonProperty("start_date")
  @NotNull
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_FORMAT)
  @gov.ca.cwds.rest.validation.Date(format = DATE_FORMAT, required = true)
  @ApiModelProperty(required = true, readOnly = false, value = "2016-10-31", example = "2016-10-31")
  private String startDate;

  @JsonProperty("name_suffix")
  @Size(max = 4)
  @ApiModelProperty(required = false, readOnly = false, value = "", example = "SR.")
  private String nameSuffix;

  @JsonProperty("telecommuter_indicator")
  @NotNull
  @ApiModelProperty(required = true, readOnly = false)
  private Boolean telecommuterIndicator;

  @JsonProperty("cws_office")
  @NotEmpty
  @Size(min = CMS_ID_LEN, max = CMS_ID_LEN)
  @ApiModelProperty(required = true, readOnly = false, value = "IDENTIFIER of CWS_OFFT",
      example = "1234567def")
  private String cwsOffice;

  @JsonProperty("availability_and_location_description")
  @NotNull
  @Size(max = 160)
  @ApiModelProperty(required = false, readOnly = false, value = "", example = "some free form text")
  private String availabilityAndLocationDescription;

  @JsonProperty("ssrs_licensing_worker_id")
  @NotNull
  @Size(max = 4)
  @ApiModelProperty(required = false, readOnly = false, value = "", example = "9021")
  private String ssrsLicensingWorkerId;

  @JsonProperty("county_code")
  @NotEmpty
  @Size(min = 1, max = 2)
  @ApiModelProperty(required = true, readOnly = false, value = "", example = "99")
  private String countyCode;

  @JsonProperty("duty_worker_indicator")
  @NotNull
  @ApiModelProperty(required = true, readOnly = false)
  private Boolean dutyWorkerIndicator;

  @JsonProperty("cws_office_address")
  @NotEmpty
  @Size(min = CMS_ID_LEN, max = CMS_ID_LEN)
  @ApiModelProperty(required = true, readOnly = false, value = "IDENTIFIER of CWSADDRT",
      example = "1234567ghi")
  private String cwsOfficeAddress;

  @JsonProperty("email_address")
  @Size(max = 50)
  @ApiModelProperty(required = false, readOnly = false, value = "",
      example = "john.q.smith@somedomain.com")
  private String emailAddress;

  /**
   * Constructor
   * 
   * @param endDate The endDate
   * @param firstName The firstName
   * @param jobTitle The jobTitle
   * @param lastName The lastName
   * @param middleInitial The middleInitial
   * @param namePrefix The namePrefix
   * @param phoneNumber The phoneNumber
   * @param phoneExt The phoneExt
   * @param startDate The startDate
   * @param nameSuffix The nameSuffix
   * @param telecommuterIndicator The telecommuterIndicator
   * @param cwsOffice The cws office
   * @param availabilityAndLocationDescription The availabilityAndLocationDescription
   * @param ssrsLicensingWorkerId The ssrsLicensingWorkerId
   * @param countyCode The countyCode
   * @param dutyWorkerIndicator The dutyWorkerIndicator
   * @param cwsOfficeAddress The cwsOfficeAddress
   * @param emailAddress The emailAddress
   */
  @JsonCreator
  public StaffPerson(
      @JsonProperty("end_date") String endDate,
      @JsonProperty("first_name") String firstName,
      @JsonProperty("job_title") String jobTitle,
      @JsonProperty("last_name") String lastName,
      @JsonProperty("middle_initial") String middleInitial,
      @JsonProperty("name_prefix") String namePrefix,
      @JsonProperty("phone_number") BigDecimal phoneNumber,
      @JsonProperty("phone_ext") Integer phoneExt,
      @JsonProperty("start_date") String startDate,
      @JsonProperty("name_suffix") String nameSuffix,
      @JsonProperty("telecommuter_indicator") Boolean telecommuterIndicator,
      @JsonProperty("cws_office") String cwsOffice,
      @JsonProperty("availability_and_location_description") String availabilityAndLocationDescription,
      @JsonProperty("ssrs_licensing_worker_id") String ssrsLicensingWorkerId,
      @JsonProperty("county_code") String countyCode,
      @JsonProperty("duty_worker_indicator") Boolean dutyWorkerIndicator,
      @JsonProperty("cws_office_address") String cwsOfficeAddress,
      @JsonProperty("email_address") String emailAddress) {
    super();
    this.endDate = endDate;
    this.firstName = firstName;
    this.jobTitle = jobTitle;
    this.lastName = lastName;
    this.middleInitial = middleInitial;
    this.namePrefix = namePrefix;
    this.phoneNumber = phoneNumber;
    this.phoneExt = phoneExt;
    this.startDate = startDate;
    this.nameSuffix = nameSuffix;
    this.telecommuterIndicator = telecommuterIndicator;
    this.cwsOffice = cwsOffice;
    this.availabilityAndLocationDescription = availabilityAndLocationDescription;
    this.ssrsLicensingWorkerId = ssrsLicensingWorkerId;
    this.countyCode = countyCode;
    this.dutyWorkerIndicator = dutyWorkerIndicator;
    this.cwsOfficeAddress = cwsOfficeAddress;
    this.emailAddress = emailAddress;
  }

  @SuppressWarnings("javadoc")
  public StaffPerson(gov.ca.cwds.data.persistence.cms.StaffPerson persistedStaffPerson) {
    this.endDate = DomainChef.cookDate(persistedStaffPerson.getEndDate());
    this.firstName = persistedStaffPerson.getFirstName();
    this.jobTitle = persistedStaffPerson.getJobTitle();
    this.lastName = persistedStaffPerson.getLastName();
    this.middleInitial = persistedStaffPerson.getMiddleInitial();
    this.namePrefix = persistedStaffPerson.getNamePrefix();
    this.phoneNumber = persistedStaffPerson.getPhoneNumber();
    this.phoneExt = persistedStaffPerson.getPhoneExt();
    this.startDate = DomainChef.cookDate(persistedStaffPerson.getStartDate());
    this.nameSuffix = persistedStaffPerson.getNameSuffix();
    this.telecommuterIndicator =
        DomainChef.uncookBooleanString(persistedStaffPerson.getTelecommuterIndicator());
    this.cwsOffice = persistedStaffPerson.getCwsOffice();
    this.availabilityAndLocationDescription =
        persistedStaffPerson.getAvailabilityAndLocationDescription();
    this.ssrsLicensingWorkerId = persistedStaffPerson.getSsrsLicensingWorkerId();
    this.countyCode = persistedStaffPerson.getCountyCode();
    this.dutyWorkerIndicator =
        DomainChef.uncookBooleanString(persistedStaffPerson.getDutyWorkerIndicator());
    this.cwsOfficeAddress = persistedStaffPerson.getCwsOfficeAddress();
    this.emailAddress = persistedStaffPerson.getEmailAddress();
  }

  /**
   * @return the endDate
   */
  public String getEndDate() {
    return endDate;
  }

  /**
   * @return the firstName
   */
  public String getFirstName() {
    return firstName;
  }

  /**
   * @return the jobTitle
   */
  public String getJobTitle() {
    return jobTitle;
  }

  /**
   * @return the lastName
   */
  public String getLastName() {
    return lastName;
  }

  /**
   * @return the middleInitial
   */
  public String getMiddleInitial() {
    return middleInitial;
  }

  /**
   * @return the namePrefix
   */
  public String getNamePrefix() {
    return namePrefix;
  }

  /**
   * @return the phoneNumber
   */
  public BigDecimal getPhoneNumber() {
    return phoneNumber;
  }

  /**
   * @return the phoneExt
   */
  public Integer getPhoneExt() {
    return phoneExt;
  }

  /**
   * @return the startDate
   */
  public String getStartDate() {
    return startDate;
  }

  /**
   * @return the nameSuffix
   */
  public String getNameSuffix() {
    return nameSuffix;
  }

  /**
   * @return the telecommuterIndicator
   */
  public Boolean getTelecommuterIndicator() {
    return telecommuterIndicator;
  }

  /**
   * @return the cwsOffice
   */
  public String getCwsOffice() {
    return cwsOffice;
  }

  /**
   * @return the availabilityAndLocationDescription
   */
  public String getAvailabilityAndLocationDescription() {
    return availabilityAndLocationDescription;
  }

  /**
   * @return the ssrsLicensingWorkerId
   */
  public String getSsrsLicensingWorkerId() {
    return ssrsLicensingWorkerId;
  }

  /**
   * @return the countyCode
   */
  public String getCountyCode() {
    return countyCode;
  }

  /**
   * @return the dutyWorkerIndicator
   */
  public Boolean getDutyWorkerIndicator() {
    return dutyWorkerIndicator;
  }

  /**
   * @return the cwsOfficeAddress
   */
  public String getCwsOfficeAddress() {
    return cwsOfficeAddress;
  }

  /**
   * @return the emailAddress
   */
  public String getEmailAddress() {
    return emailAddress;
  }

  /**
   * {@inheritDoc}
   *
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this, false);
  }

  /**
   * {@inheritDoc}
   *
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj) {
    return EqualsBuilder.reflectionEquals(this, obj, false);
  }

}
