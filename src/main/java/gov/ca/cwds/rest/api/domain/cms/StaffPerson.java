package gov.ca.cwds.rest.api.domain.cms;

import static gov.ca.cwds.data.persistence.cms.CmsPersistentObject.CMS_ID_LEN;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.DomainChef;
import gov.ca.cwds.rest.api.domain.DomainObject;
import gov.ca.cwds.rest.api.domain.ReportingDomain;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * {@link DomainObject} representing a StaffPerson
 * 
 * @author CWDS API Team
 */
@ApiModel
public class StaffPerson extends ReportingDomain implements Request, Response {
  /**
   * Serialization version
   */
  private static final long serialVersionUID = 1L;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_FORMAT)
  @JsonProperty(value = "endDate")
  @gov.ca.cwds.rest.validation.Date(format = DATE_FORMAT, required = false)
  @ApiModelProperty(required = false, readOnly = false, value = "yyyy-MM-dd",
      example = "2016-10-31")
  private String endDate;

  @NotEmpty
  @Size(min = 1, max = 20)
  @ApiModelProperty(required = true, readOnly = false, value = "", example = "John")
  private String firstName;

  @Size(max = 30)
  @ApiModelProperty(required = false, readOnly = false, value = "Job Title",
      example = "Case Worker")
  private String jobTitle;

  @NotEmpty
  @Size(min = 1, max = 25)
  @ApiModelProperty(required = true, readOnly = false, value = "", example = "Smith")
  private String lastName;

  @Size(max = 1, message = "size must be 1")
  @ApiModelProperty(required = false, readOnly = false, value = "", example = "Q")
  private String middleInitial;

  @Size(max = 6)
  @ApiModelProperty(required = false, readOnly = false, value = "", example = "MR.")
  private String namePrefix;

  @NotNull
  @ApiModelProperty(required = true, readOnly = false, example = "9165551212")
  private BigDecimal phoneNumber;

  @NotNull
  @ApiModelProperty(required = false, readOnly = false, example = "123")
  private Integer phoneExt;

  @NotNull
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_FORMAT)
  @JsonProperty(value = "startDate")
  @gov.ca.cwds.rest.validation.Date(format = DATE_FORMAT, required = true)
  @ApiModelProperty(required = true, readOnly = false, value = "2016-10-31", example = "2016-10-31")
  private String startDate;

  @Size(max = 4)
  @ApiModelProperty(required = false, readOnly = false, value = "", example = "SR.")
  private String nameSuffix;

  @NotNull
  @ApiModelProperty(required = true, readOnly = false)
  private Boolean telecommuterIndicator;

  @NotEmpty
  @Size(min = CMS_ID_LEN, max = CMS_ID_LEN)
  @ApiModelProperty(required = true, readOnly = false, value = "IDENTIFIER of CWS_OFFT",
      example = "1234567def")
  private String cwsOffice;

  @NotNull
  @Size(max = 160)
  @ApiModelProperty(required = false, readOnly = false, value = "", example = "some free form text")
  private String availabilityAndLocationDescription;

  @NotNull
  @Size(max = 4)
  @ApiModelProperty(required = false, readOnly = false, value = "", example = "9021")
  private String ssrsLicensingWorkerId;

  @NotEmpty
  @Size(min = 1, max = 2)
  @ApiModelProperty(required = true, readOnly = false, value = "", example = "99")
  private String countyCode;

  @NotNull
  @ApiModelProperty(required = true, readOnly = false)
  private Boolean dutyWorkerIndicator;

  @NotEmpty
  @Size(min = CMS_ID_LEN, max = CMS_ID_LEN)
  @ApiModelProperty(required = true, readOnly = false, value = "IDENTIFIER of CWSADDRT",
      example = "1234567ghi")
  private String cwsOfficeAddress;

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
  public StaffPerson(@JsonProperty("endDate") String endDate,
      @JsonProperty("firstName") String firstName, @JsonProperty("jobTitle") String jobTitle,
      @JsonProperty("lastName") String lastName,
      @JsonProperty("middleInitial") String middleInitial,
      @JsonProperty("namePrefix") String namePrefix,
      @JsonProperty("phoneNumber") BigDecimal phoneNumber,
      @JsonProperty("phoneExt") Integer phoneExt, @JsonProperty("startDate") String startDate,
      @JsonProperty("nameSuffix") String nameSuffix,
      @JsonProperty("telecommuterIndicator") Boolean telecommuterIndicator,
      @JsonProperty("cwsOffice") String cwsOffice,
      @JsonProperty("availabilityAndLocationDescription") String availabilityAndLocationDescription,
      @JsonProperty("ssrsLicensingWorkerId") String ssrsLicensingWorkerId,
      @JsonProperty("countyCode") String countyCode,
      @JsonProperty("dutyWorkerIndicator") Boolean dutyWorkerIndicator,
      @JsonProperty("cwsOfficeAddress") String cwsOfficeAddress,
      @JsonProperty("emailAddress") String emailAddress) {
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
