package gov.ca.cwds.rest.api.domain.cms;

import static gov.ca.cwds.data.persistence.cms.CmsPersistentObject.CMS_ID_LEN;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.validator.constraints.NotBlank;
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
import io.dropwizard.validation.OneOf;
import io.swagger.annotations.ApiModelProperty;

/**
 * {@link DomainObject} representing a ReferralClient
 * 
 * @author CWDS API Team
 */
public class ReferralClient extends ReportingDomain implements Request, Response {
  private static final short DEFAULT_CODE = 0;
  private static final String DEFAULT_DISPOSITION_CODE = "A";

  /**
   * Serialization version
   */
  private static final long serialVersionUID = 1L;

  @Size(max = 10)
  @ApiModelProperty(required = false, readOnly = false, value = "", example = "ABC1234567")
  private String approvalNumber;

  @SystemCodeSerializer(logical = true, description = true)
  @NotNull
  @ApiModelProperty(required = true, readOnly = false, example = "123")
  private Short approvalStatusType;

  @SystemCodeSerializer(logical = true, description = true)
  @NotNull
  @ApiModelProperty(required = false, readOnly = false, example = "234")
  private Short dispositionClosureReasonType;

  @NotEmpty
  @Size(min = 1, max = 1, message = "size must be 1")
  @OneOf(value = {"A", "I", "S", "X"}, ignoreCase = true, ignoreWhitespace = true)
  @ApiModelProperty(required = false, readOnly = false,
      value = "A = Assesment, I = In Person Investigation, S = In Person Investigation and Services, X = Erroneously Added",
      example = "A")
  private String dispositionCode;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_FORMAT)
  @JsonProperty(value = "dispositionDate")
  @gov.ca.cwds.rest.validation.Date(format = DATE_FORMAT, required = false)
  @ApiModelProperty(required = false, readOnly = false, value = "yyyy-MM-dd",
      example = "2000-01-01")
  private String dispositionDate;

  @NotNull
  @ApiModelProperty(required = true, readOnly = false)
  private Boolean selfReportedIndicator;

  @NotNull
  @ApiModelProperty(required = true, readOnly = false)
  private Boolean staffPersonAddedIndicator;

  @NotEmpty
  @NotBlank
  @Size(min = CMS_ID_LEN, max = CMS_ID_LEN)
  @ApiModelProperty(required = true, readOnly = false, value = "named in referral",
      example = "ABC1234567")
  private String referralId;

  @NotEmpty
  @NotBlank
  @Size(min = CMS_ID_LEN, max = CMS_ID_LEN)
  @ApiModelProperty(required = true, readOnly = false, value = "identifies a client",
      example = "ABC1234567")
  private String clientId;

  @NotNull
  @Size(max = 254)
  @ApiModelProperty(required = false, readOnly = false, value = "", example = "Some Description")
  private String dispositionClosureDescription;

  @NotNull
  @ApiModelProperty(required = false, readOnly = false, example = "12")
  private Short ageNumber;

  @NotNull
  @Size(max = 1, message = "size must be 1")
  @ApiModelProperty(required = true, readOnly = false, value = "unit of measure for age",
      example = "D")
  private String agePeriodCode;

  @NotEmpty
  @Size(min = 1, max = 2)
  @ApiModelProperty(required = true, readOnly = false, value = "", example = "99")
  private String countySpecificCode;

  @ApiModelProperty(required = false, readOnly = false)
  private Boolean mentalHealthIssuesIndicator;

  @ApiModelProperty(required = false, readOnly = false)
  private Boolean alcoholIndicator;

  @ApiModelProperty(required = false, readOnly = false)
  private Boolean drugIndicator;


  /**
   * Construct from all fields.
   * 
   * @param approvalNumber - String approvalNumber
   * @param approvalStatusType - Short approvalStatusType
   * @param dispositionClosureReasonType - Short dispositionClosureReasonType
   * @param dispositionCode - String dispositionDate
   * @param dispositionDate - Boolean selfReportedIndicator
   * @param selfReportedIndicator - Boolean selfReportedIndicator
   * @param staffPersonAddedIndicator - Boolean staffPersonAddedIndicator
   * @param referralId - String referralId
   * @param clientId - String clientId
   * @param dispositionClosureDescription - String dispositionClosureDescription
   * @param ageNumber - Short ageNumber
   * @param agePeriodCode - String agePeriodCode
   * @param countySpecificCode - String countySpecificCode
   * @param mentalHealthIssuesIndicator - Boolean mentalHealthIssuesIndicator
   * @param alcoholIndicator - Boolean alcoholIndicator
   * @param drugIndicator - Boolean drugIndicator
   */
  @JsonCreator
  public ReferralClient(@JsonProperty("approvalNumber") String approvalNumber,
      @JsonProperty("approvalStatusType") Short approvalStatusType,
      @JsonProperty("dispositionClosureReasonType") Short dispositionClosureReasonType,
      @JsonProperty("dispositionCode") String dispositionCode,
      @JsonProperty("dispositionDate") String dispositionDate,
      @JsonProperty("selfReportedIndicator") Boolean selfReportedIndicator,
      @JsonProperty("staffPersonAddedIndicator") Boolean staffPersonAddedIndicator,
      @JsonProperty("referralId") String referralId, @JsonProperty("clientId") String clientId,
      @JsonProperty("dispositionClosureDescription") String dispositionClosureDescription,
      @JsonProperty("ageNumber") Short ageNumber,
      @JsonProperty("agePeriodCode") String agePeriodCode,
      @JsonProperty("countySpecificCode") String countySpecificCode,
      @JsonProperty("mentalHealthIssuesIndicator") Boolean mentalHealthIssuesIndicator,
      @JsonProperty("alcoholIndicator") Boolean alcoholIndicator,
      @JsonProperty("drugIndicator") Boolean drugIndicator) {
    super();
    this.approvalNumber = approvalNumber;
    this.approvalStatusType = approvalStatusType;
    this.dispositionClosureReasonType = dispositionClosureReasonType;
    this.dispositionCode = dispositionCode;
    this.dispositionDate = dispositionDate;
    this.selfReportedIndicator = selfReportedIndicator;
    this.staffPersonAddedIndicator = staffPersonAddedIndicator;
    this.referralId = referralId;
    this.clientId = clientId;
    this.dispositionClosureDescription = dispositionClosureDescription;
    this.ageNumber = ageNumber;
    this.agePeriodCode = agePeriodCode;
    this.countySpecificCode = countySpecificCode;
    this.mentalHealthIssuesIndicator = mentalHealthIssuesIndicator;
    this.alcoholIndicator = alcoholIndicator;
    this.drugIndicator = drugIndicator;
  }

  /**
   * @param persistedReferralClient - persisted ReferralClient object
   */
  public ReferralClient(gov.ca.cwds.data.persistence.cms.ReferralClient persistedReferralClient) {
    this.approvalNumber = persistedReferralClient.getApprovalNumber();
    this.approvalStatusType = persistedReferralClient.getApprovalStatusType();
    this.dispositionClosureReasonType = persistedReferralClient.getDispositionClosureReasonType();
    this.dispositionCode = persistedReferralClient.getDispositionCode();
    this.dispositionDate = DomainChef.cookDate(persistedReferralClient.getDispositionDate());
    this.selfReportedIndicator =
        DomainChef.uncookBooleanString(persistedReferralClient.getSelfReportedIndicator());
    this.staffPersonAddedIndicator =
        DomainChef.uncookBooleanString(persistedReferralClient.getStaffPersonAddedIndicator());
    this.referralId = persistedReferralClient.getReferralId().trim();
    this.clientId = persistedReferralClient.getClientId().trim();
    this.dispositionClosureDescription = persistedReferralClient.getDispositionClosureDescription();
    this.ageNumber = persistedReferralClient.getAgeNumber();
    this.agePeriodCode = persistedReferralClient.getAgePeriodCode();
    this.countySpecificCode = persistedReferralClient.getCountySpecificCode();
    this.mentalHealthIssuesIndicator =
        DomainChef.uncookBooleanString(persistedReferralClient.getMentalHealthIssuesIndicator());
    this.alcoholIndicator =
        DomainChef.uncookBooleanString(persistedReferralClient.getAlcoholIndicator());
    this.drugIndicator = DomainChef.uncookBooleanString(persistedReferralClient.getDrugIndicator());
  }

  /**
   * @param selfReported - selfReported
   * @param referralId - referralId
   * @param clientId - clientId
   * @param countyCode - countyCode
   * @param approvalCode - approvalCode
   * @return the referralClient
   */
  public static ReferralClient createWithDefault(Boolean selfReported, String referralId,
      String clientId, String countyCode, short approvalCode) {
    return new ReferralClient("", approvalCode, DEFAULT_CODE, DEFAULT_DISPOSITION_CODE, "",
        selfReported, false, referralId, clientId, "", DEFAULT_CODE, "", countyCode, false, false,
        false);
  }

  /**
   * @return the approvalNumber
   */
  public String getApprovalNumber() {
    return approvalNumber;
  }

  /**
   * @return the approvalStatusType
   */
  public Short getApprovalStatusType() {
    return approvalStatusType;
  }

  /**
   * @return the dispositionClosureReasonType
   */
  public Short getDispositionClosureReasonType() {
    return dispositionClosureReasonType;
  }

  /**
   * @return the dispositionCode
   */
  public String getDispositionCode() {
    return dispositionCode;
  }

  /**
   * @return the dispositionDate
   */
  public String getDispositionDate() {
    return dispositionDate;
  }

  /**
   * @return the selfReportedIndicator
   */
  public Boolean getSelfReportedIndicator() {
    return selfReportedIndicator;
  }

  /**
   * @return the staffPersonAddedIndicator
   */
  public Boolean getStaffPersonAddedIndicator() {
    return staffPersonAddedIndicator;
  }

  /**
   * @return the referralId
   */
  public String getReferralId() {
    return referralId;
  }

  /**
   * @return the clientId
   */
  public String getClientId() {
    return clientId;
  }

  /**
   * @return the dispositionClosureDescription
   */
  public String getDispositionClosureDescription() {
    return dispositionClosureDescription;
  }

  /**
   * @return the ageNumber
   */
  public Short getAgeNumber() {
    return ageNumber;
  }

  /**
   * @return the agePeriodCode
   */
  public String getAgePeriodCode() {
    return agePeriodCode;
  }

  /**
   * @return the countySpecificCode
   */
  public String getCountySpecificCode() {
    return countySpecificCode;
  }

  /**
   * @return the mentalHealthIssuesIndicator
   */
  public Boolean getMentalHealthIssuesIndicator() {
    return mentalHealthIssuesIndicator;
  }

  /**
   * @return the alcoholIndicator
   */
  public Boolean getAlcoholIndicator() {
    return alcoholIndicator;
  }

  /**
   * @return the drugIndicator
   */
  public Boolean getDrugIndicator() {
    return drugIndicator;
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
