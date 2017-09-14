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

import gov.ca.cwds.data.SystemCodeSerializer;
import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.DomainChef;
import gov.ca.cwds.rest.api.domain.DomainObject;
import gov.ca.cwds.rest.api.domain.ReportingDomain;
import gov.ca.cwds.rest.api.domain.SystemCodeCategoryId;
import gov.ca.cwds.rest.validation.ValidSystemCodeId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * {@link DomainObject} representing a CrossReport
 * 
 * @author CWDS API Team
 */
@ApiModel("CmsCrossReport")
public class CrossReport extends ReportingDomain implements Request, Response {
  private static final int DEFAULT_INT = 0;
  private static final BigDecimal DEFAULT_DECIMAL = new BigDecimal(0);

  /**
   * Serialization version
   */
  private static final long serialVersionUID = 1L;

  // TODO: remove the unique key from the domain class
  @NotNull
  @Size(max = CMS_ID_LEN)
  @ApiModelProperty(required = true, readOnly = true, value = "", example = "1234ABC123")
  private String thirdId;

  @SystemCodeSerializer(logical = true, description = true)
  @NotNull
  @ValidSystemCodeId(required = true, category = SystemCodeCategoryId.CROSS_REPORT_METHOD)
  @ApiModelProperty(required = true, readOnly = false,
      value = "Cross report method type system code ID e.g) 2097 -> Telephone Report",
      example = "2097")
  private Short crossReportMethodType;

  @NotNull
  @ApiModelProperty(required = true, readOnly = false)
  private Boolean filedOutOfStateIndicator;

  @NotNull
  @ApiModelProperty(required = true, readOnly = false)
  private Boolean governmentOrgCrossRptIndicatorVar;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = TIME_FORMAT)
  @JsonProperty(value = "informTime")
  @gov.ca.cwds.rest.validation.Date(format = TIME_FORMAT, required = false)
  @ApiModelProperty(required = false, readOnly = false, example = "16:41:49")
  private String informTime;

  @NotNull
  @Size(max = 6)
  @ApiModelProperty(required = false, readOnly = false, value = "Recipent badge number",
      example = "ABC123")
  private String recipientBadgeNumber;

  @NotNull
  @ApiModelProperty(required = false, readOnly = false, example = "123")
  private Integer recipientPhoneExtensionNumber;

  @NotNull
  @ApiModelProperty(required = false, readOnly = false, example = "1234567")
  private BigDecimal recipientPhoneNumber;

  @NotNull
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_FORMAT)
  @JsonProperty(value = "informDate")
  @gov.ca.cwds.rest.validation.Date(format = DATE_FORMAT, required = true)
  @ApiModelProperty(required = true, readOnly = false, value = "yyyy-MM-dd", example = "2000-01-01")
  private String informDate;

  @NotNull
  @Size(max = 30)
  @ApiModelProperty(required = true, readOnly = false, value = "Recipient position title",
      example = "title")
  private String recipientPositionTitleDesc;

  @NotNull
  @Size(max = 10)
  @ApiModelProperty(required = true, readOnly = false, value = "Referrence number",
      example = "ABC123")
  private String referenceNumber;

  @NotNull
  @Size(min = CMS_ID_LEN, max = CMS_ID_LEN)
  @ApiModelProperty(required = true, readOnly = true, value = "Referral IDENTIFIER",
      example = "ABC1234567")
  private String referralId;

  @Size(max = CMS_ID_LEN)
  @ApiModelProperty(required = false, readOnly = true, value = "Law enforcement ID",
      example = "ABC1234567")
  private String lawEnforcementId;

  @NotEmpty
  @Size(min = 3, max = 3)
  @ApiModelProperty(required = true, readOnly = false, value = "", example = "ABC")
  private String staffPersonId;

  @NotNull
  @Size(min = 1, max = 120)
  @ApiModelProperty(required = false, readOnly = false, value = "", example = "ABC123")
  private String description;

  @NotNull
  @Size(max = 40)
  @ApiModelProperty(required = false, readOnly = false, value = "", example = "recipient name")
  private String recipientName;

  @NotNull
  @Size(max = 254)
  @ApiModelProperty(required = true, readOnly = false,
      value = "out of state law enforcement address", example = "law address")
  private String outStateLawEnforcementAddr;

  @NotEmpty
  @Size(min = 1, max = 2)
  @ApiModelProperty(required = true, readOnly = false, value = "", example = "99")
  private String countySpecificCode;

  @NotNull
  @ApiModelProperty(required = true, readOnly = false)
  private Boolean lawEnforcementIndicator;

  @NotNull
  @ApiModelProperty(required = true, readOnly = false)
  private Boolean outStateLawEnforcementIndicator;

  @NotNull
  @ApiModelProperty(required = true, readOnly = false)
  private Boolean satisfyCrossReportIndicator;

  /**
   * Construct from all fields.
   * 
   * @param thirdId "third id" makes record unique
   * @param crossReportMethodType reporting type
   * @param filedOutOfStateIndicator if filed out of state
   * @param governmentOrgCrossRptIndicatorVar if a govt cross report
   * @param informTime inform time
   * @param recipientBadgeNumber recipient's badge number
   * @param recipientPhoneExtensionNumber recip's phone ext
   * @param recipientPhoneNumber recip's phone
   * @param informDate inform date
   * @param recipientPositionTitleDesc recip's title
   * @param referenceNumber reference num
   * @param referralId FK to referral
   * @param lawEnforcementId law enforcement id
   * @param staffPersonId FK to staff person
   * @param description description of this cross report
   * @param recipientName recip's name
   * @param outStateLawEnforcementAddr address of out-of-state law enforcement agency
   * @param countySpecificCode county code
   * @param lawEnforcementIndicator if law enforcement involved
   * @param outStateLawEnforcementIndicator if out-of-state law enforcement agency
   * @param satisfyCrossReportIndicator if cross reporting is satisfied
   */
  @JsonCreator
  public CrossReport(@JsonProperty("thirdId") String thirdId,
      @JsonProperty("crossReportMethodType") Short crossReportMethodType,
      @JsonProperty("filedOutOfStateIndicator") Boolean filedOutOfStateIndicator,
      @JsonProperty("governmentOrgCrossRptIndicatorVar") Boolean governmentOrgCrossRptIndicatorVar,
      @JsonProperty("informTime") String informTime,
      @JsonProperty("recipientBadgeNumber") String recipientBadgeNumber,
      @JsonProperty("recipientPhoneExtensionNumber") Integer recipientPhoneExtensionNumber,
      @JsonProperty("recipientPhoneNumber") BigDecimal recipientPhoneNumber,
      @JsonProperty("informDate") String informDate,
      @JsonProperty("recipientPositionTitleDesc") String recipientPositionTitleDesc,
      @JsonProperty("referenceNumber") String referenceNumber,
      @JsonProperty("referralId") String referralId,
      @JsonProperty("lawEnforcementId") String lawEnforcementId,
      @JsonProperty("staffPersonId") String staffPersonId,
      @JsonProperty("description") String description,
      @JsonProperty("recipientName") String recipientName,
      @JsonProperty("outStateLawEnforcementAddr") String outStateLawEnforcementAddr,
      @JsonProperty("countySpecificCode") String countySpecificCode,
      @JsonProperty("lawEnforcementIndicator") Boolean lawEnforcementIndicator,
      @JsonProperty("outStateLawEnforcementIndicator") Boolean outStateLawEnforcementIndicator,
      @JsonProperty("satisfyCrossReportIndicator") Boolean satisfyCrossReportIndicator) {
    super();
    this.thirdId = thirdId;
    this.crossReportMethodType = crossReportMethodType;
    this.filedOutOfStateIndicator = filedOutOfStateIndicator;
    this.governmentOrgCrossRptIndicatorVar = governmentOrgCrossRptIndicatorVar;
    this.informTime = informTime;
    this.recipientBadgeNumber = recipientBadgeNumber;
    this.recipientPhoneExtensionNumber = recipientPhoneExtensionNumber;
    this.recipientPhoneNumber = recipientPhoneNumber;
    this.informDate = informDate;
    this.recipientPositionTitleDesc = recipientPositionTitleDesc;
    this.referenceNumber = referenceNumber;
    this.referralId = referralId;
    this.lawEnforcementId = lawEnforcementId;
    this.staffPersonId = staffPersonId;
    this.description = description;
    this.recipientName = recipientName;
    this.outStateLawEnforcementAddr = outStateLawEnforcementAddr;
    this.countySpecificCode = countySpecificCode;
    this.lawEnforcementIndicator = lawEnforcementIndicator;
    this.outStateLawEnforcementIndicator = outStateLawEnforcementIndicator;
    this.satisfyCrossReportIndicator = satisfyCrossReportIndicator;
  }

  /**
   * Construct from sibling persistence class.
   * 
   * @param persistedCrossReport whether the cross report is persisted
   */
  public CrossReport(gov.ca.cwds.data.persistence.cms.CrossReport persistedCrossReport) {
    this.referralId = persistedCrossReport.getReferralId();
    this.thirdId = persistedCrossReport.getThirdId();
    this.crossReportMethodType = persistedCrossReport.getCrossReportMethodType();
    this.filedOutOfStateIndicator =
        DomainChef.uncookBooleanString(persistedCrossReport.getFiledOutOfStateIndicator());
    this.governmentOrgCrossRptIndicatorVar =
        DomainChef.uncookBooleanString(persistedCrossReport.getGovernmentOrgCrossRptIndicatorVar());
    this.informTime = DomainChef.cookTime(persistedCrossReport.getInformTime());
    this.recipientBadgeNumber = persistedCrossReport.getRecipientBadgeNumber();
    this.recipientPhoneExtensionNumber = persistedCrossReport.getRecipientPhoneExtensionNumber();
    this.recipientPhoneNumber = persistedCrossReport.getRecipientPhoneNumber();
    this.informDate = DomainChef.cookDate(persistedCrossReport.getInformDate());
    this.recipientPositionTitleDesc = persistedCrossReport.getRecipientPositionTitleDesc();
    this.referenceNumber = persistedCrossReport.getReferenceNumber();
    this.lawEnforcementId = persistedCrossReport.getLawEnforcementId();
    this.staffPersonId = persistedCrossReport.getStaffPersonId();
    this.description = persistedCrossReport.getDescription();
    this.recipientName = persistedCrossReport.getRecipientName();
    this.outStateLawEnforcementAddr = persistedCrossReport.getOutStateLawEnforcementAddr();
    this.countySpecificCode = persistedCrossReport.getCountySpecificCode();
    this.lawEnforcementIndicator =
        DomainChef.uncookBooleanString(persistedCrossReport.getLawEnforcementIndicator());
    this.outStateLawEnforcementIndicator =
        DomainChef.uncookBooleanString(persistedCrossReport.getOutStateLawEnforcementIndicator());
    this.satisfyCrossReportIndicator =
        DomainChef.uncookBooleanString(persistedCrossReport.getSatisfyCrossReportIndicator());
  }

  /**
   * @param id - id
   * @param crossReport - crossReport
   * @param referralId - referralId
   * @param staffId - staffId
   * @param outStateLawEnforcementAddr - outStateLawEnforcementAddr
   * @param countyCode - countyCode
   * @param lawEnforcementIndicator - lawEnforcementIndicator
   * @param outStateLawEnforcementIndicator - outStateLawEnforcementIndicator
   * @return the crossReport
   */
  public static CrossReport createWithDefaults(String id,
      gov.ca.cwds.rest.api.domain.CrossReport crossReport, String referralId, String staffId,
      String outStateLawEnforcementAddr, String countyCode, Boolean lawEnforcementIndicator,
      Boolean outStateLawEnforcementIndicator) {
    return new CrossReport(id, crossReport.getMethod().shortValue(),
        crossReport.isFiledOutOfState(), Boolean.FALSE, "", "", DEFAULT_INT, DEFAULT_DECIMAL,
        crossReport.getInformDate(), "", "", referralId, "", staffId, crossReport.getAgencyName(),
        "", outStateLawEnforcementAddr, countyCode, lawEnforcementIndicator,
        outStateLawEnforcementIndicator, Boolean.FALSE);
  }

  /**
   * @return the thirdId
   */
  public String getThirdId() {
    return thirdId;
  }

  /**
   * @return the crossReportMethodType
   */
  public Short getCrossReportMethodType() {
    return crossReportMethodType;
  }

  /**
   * @return the filedOutOfStateIndicator
   */
  public Boolean getFiledOutOfStateIndicator() {
    return filedOutOfStateIndicator;
  }

  /**
   * @return the governmentOrgCrossRptIndicatorVar
   */
  public Boolean getGovernmentOrgCrossRptIndicatorVar() {
    return governmentOrgCrossRptIndicatorVar;
  }

  /**
   * @return the informTime
   */
  public String getInformTime() {
    return informTime;
  }

  /**
   * @return the recipientBadgeNumber
   */
  public String getRecipientBadgeNumber() {
    return recipientBadgeNumber;
  }

  /**
   * @return the recipientPhoneExtensionNumber
   */
  public Integer getRecipientPhoneExtensionNumber() {
    return recipientPhoneExtensionNumber;
  }

  /**
   * @return the recipientPhoneNumber
   */
  public BigDecimal getRecipientPhoneNumber() {
    return recipientPhoneNumber;
  }

  /**
   * @return the informDate
   */
  public String getInformDate() {
    return informDate;
  }

  /**
   * @return the recipientPositionTitleDesc
   */
  public String getRecipientPositionTitleDesc() {
    return recipientPositionTitleDesc;
  }

  /**
   * @return the referenceNumber
   */
  public String getReferenceNumber() {
    return referenceNumber;
  }

  /**
   * @return the referralId
   */
  public String getReferralId() {
    return referralId;
  }

  /**
   * @return the lawEnforcementId
   */
  public String getLawEnforcementId() {
    return lawEnforcementId;
  }

  /**
   * @return the staffPersonId
   */
  public String getStaffPersonId() {
    return staffPersonId;
  }

  /**
   * @return the description
   */
  public String getDescription() {
    return description;
  }

  /**
   * @return the recipientName
   */
  public String getRecipientName() {
    return recipientName;
  }

  /**
   * @return the outStateLawEnforcementAddr
   */
  public String getOutStateLawEnforcementAddr() {
    return outStateLawEnforcementAddr;
  }

  /**
   * @return the countySpecificCode
   */
  public String getCountySpecificCode() {
    return countySpecificCode;
  }

  /**
   * @return the lawEnforcementIndicator
   */
  public Boolean getLawEnforcementIndicator() {
    return lawEnforcementIndicator;
  }

  /**
   * @return the outStateLawEnforcementIndicator
   */
  public Boolean getOutStateLawEnforcementIndicator() {
    return outStateLawEnforcementIndicator;
  }

  /**
   * @return the satisfyCrossReportIndicator
   */
  public Boolean getSatisfyCrossReportIndicator() {
    return satisfyCrossReportIndicator;
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
