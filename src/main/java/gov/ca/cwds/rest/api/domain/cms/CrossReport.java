package gov.ca.cwds.rest.api.domain.cms;

import static gov.ca.cwds.data.persistence.cms.CmsPersistentObject.CMS_ID_LEN;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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
  private static short CROSS_REPORT_METHOD_CODE = 0;

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
  @ValidSystemCodeId(required = true, category = "XRPT_MTC")
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

  // TODO: remove the unique key from the domain class
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
   * @param countyCode - countyCode
   * @param lawEnforcementIndicator - lawEnforcementIndicator
   * @return the crossReport
   */
  public static CrossReport createWithDefaults(String id,
      gov.ca.cwds.rest.api.domain.CrossReport crossReport, String referralId, String staffId,
      String countyCode, Boolean lawEnforcementIndicator) {
    return new CrossReport(id, crossReport.getMethod().shortValue(), false, false, "", "",
        DEFAULT_INT, DEFAULT_DECIMAL, crossReport.getInformDate(), "", "", referralId, "", staffId,
        crossReport.getAgencyName(), "", "", countyCode, lawEnforcementIndicator, false, false);
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
    final int prime = 31;
    int result = 1;
    result = prime * result + ((countySpecificCode == null) ? 0 : countySpecificCode.hashCode());
    result =
        prime * result + ((crossReportMethodType == null) ? 0 : crossReportMethodType.hashCode());
    result = prime * result + ((description == null) ? 0 : description.hashCode());
    result = prime * result
        + ((filedOutOfStateIndicator == null) ? 0 : filedOutOfStateIndicator.hashCode());
    result = prime * result + ((governmentOrgCrossRptIndicatorVar == null) ? 0
        : governmentOrgCrossRptIndicatorVar.hashCode());
    result = prime * result + ((informDate == null) ? 0 : informDate.hashCode());
    result = prime * result + ((informTime == null) ? 0 : informTime.hashCode());
    result = prime * result + ((lawEnforcementId == null) ? 0 : lawEnforcementId.hashCode());
    result = prime * result
        + ((lawEnforcementIndicator == null) ? 0 : lawEnforcementIndicator.hashCode());
    result = prime * result + ((outStateLawEnforcementIndicator == null) ? 0
        : outStateLawEnforcementIndicator.hashCode());
    result = prime * result
        + ((outStateLawEnforcementAddr == null) ? 0 : outStateLawEnforcementAddr.hashCode());
    result =
        prime * result + ((recipientBadgeNumber == null) ? 0 : recipientBadgeNumber.hashCode());
    result = prime * result + ((recipientName == null) ? 0 : recipientName.hashCode());
    result = prime * result
        + ((recipientPhoneExtensionNumber == null) ? 0 : recipientPhoneExtensionNumber.hashCode());
    result =
        prime * result + ((recipientPhoneNumber == null) ? 0 : recipientPhoneNumber.hashCode());
    result = prime * result
        + ((recipientPositionTitleDesc == null) ? 0 : recipientPositionTitleDesc.hashCode());
    result = prime * result + ((referenceNumber == null) ? 0 : referenceNumber.hashCode());
    result = prime * result + ((referralId == null) ? 0 : referralId.hashCode());
    result = prime * result
        + ((satisfyCrossReportIndicator == null) ? 0 : satisfyCrossReportIndicator.hashCode());
    result = prime * result + ((staffPersonId == null) ? 0 : staffPersonId.hashCode());
    result = prime * result + ((thirdId == null) ? 0 : thirdId.hashCode());
    return result;
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public final boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (!(obj instanceof CrossReport)) {
      return false;
    }
    CrossReport other = (CrossReport) obj;
    if (countySpecificCode == null) {
      if (other.countySpecificCode != null)
        return false;
    } else if (!countySpecificCode.equals(other.countySpecificCode))
      return false;
    if (crossReportMethodType == null) {
      if (other.crossReportMethodType != null)
        return false;
    } else if (!crossReportMethodType.equals(other.crossReportMethodType))
      return false;
    if (description == null) {
      if (other.description != null)
        return false;
    } else if (!description.equals(other.description))
      return false;
    if (filedOutOfStateIndicator == null) {
      if (other.filedOutOfStateIndicator != null)
        return false;
    } else if (!filedOutOfStateIndicator.equals(other.filedOutOfStateIndicator))
      return false;
    if (governmentOrgCrossRptIndicatorVar == null) {
      if (other.governmentOrgCrossRptIndicatorVar != null)
        return false;
    } else if (!governmentOrgCrossRptIndicatorVar.equals(other.governmentOrgCrossRptIndicatorVar))
      return false;
    if (informDate == null) {
      if (other.informDate != null)
        return false;
    } else if (!informDate.equals(other.informDate))
      return false;
    if (informTime == null) {
      if (other.informTime != null)
        return false;
    } else if (!informTime.equals(other.informTime))
      return false;
    if (lawEnforcementId == null) {
      if (other.lawEnforcementId != null)
        return false;
    } else if (!lawEnforcementId.equals(other.lawEnforcementId))
      return false;
    if (lawEnforcementIndicator == null) {
      if (other.lawEnforcementIndicator != null)
        return false;
    } else if (!lawEnforcementIndicator.equals(other.lawEnforcementIndicator))
      return false;
    if (outStateLawEnforcementIndicator == null) {
      if (other.outStateLawEnforcementIndicator != null)
        return false;
    } else if (!outStateLawEnforcementIndicator.equals(other.outStateLawEnforcementIndicator))
      return false;
    if (outStateLawEnforcementAddr == null) {
      if (other.outStateLawEnforcementAddr != null)
        return false;
    } else if (!outStateLawEnforcementAddr.equals(other.outStateLawEnforcementAddr))
      return false;
    if (recipientBadgeNumber == null) {
      if (other.recipientBadgeNumber != null)
        return false;
    } else if (!recipientBadgeNumber.equals(other.recipientBadgeNumber))
      return false;
    if (recipientName == null) {
      if (other.recipientName != null)
        return false;
    } else if (!recipientName.equals(other.recipientName))
      return false;
    if (recipientPhoneExtensionNumber == null) {
      if (other.recipientPhoneExtensionNumber != null)
        return false;
    } else if (!recipientPhoneExtensionNumber.equals(other.recipientPhoneExtensionNumber))
      return false;
    if (recipientPhoneNumber == null) {
      if (other.recipientPhoneNumber != null)
        return false;
    } else if (!recipientPhoneNumber.equals(other.recipientPhoneNumber))
      return false;
    if (recipientPositionTitleDesc == null) {
      if (other.recipientPositionTitleDesc != null)
        return false;
    } else if (!recipientPositionTitleDesc.equals(other.recipientPositionTitleDesc))
      return false;
    if (referenceNumber == null) {
      if (other.referenceNumber != null)
        return false;
    } else if (!referenceNumber.equals(other.referenceNumber))
      return false;
    if (referralId == null) {
      if (other.referralId != null)
        return false;
    } else if (!referralId.equals(other.referralId))
      return false;
    if (satisfyCrossReportIndicator == null) {
      if (other.satisfyCrossReportIndicator != null)
        return false;
    } else if (!satisfyCrossReportIndicator.equals(other.satisfyCrossReportIndicator))
      return false;
    if (staffPersonId == null) {
      if (other.staffPersonId != null)
        return false;
    } else if (!staffPersonId.equals(other.staffPersonId))
      return false;
    if (thirdId == null) {
      if (other.thirdId != null)
        return false;
    } else if (!thirdId.equals(other.thirdId))
      return false;
    return true;
  }

}
