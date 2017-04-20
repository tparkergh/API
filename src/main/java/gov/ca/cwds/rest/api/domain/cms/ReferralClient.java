package gov.ca.cwds.rest.api.domain.cms;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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
import io.dropwizard.validation.OneOf;
import io.swagger.annotations.ApiModelProperty;

/**
 * {@link DomainObject} representing a ReferralClient
 * 
 * @author CWDS API Team
 */
public class ReferralClient extends DomainObject implements Request, Response {
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
  @Size(min = 10, max = 10)
  @ApiModelProperty(required = true, readOnly = false, value = "named in referral",
      example = "ABC1234567")
  private String referralId;

  @NotEmpty
  @NotBlank
  @Size(min = 10, max = 10)
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

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#hashCode()
   */
  @Override
  public final int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((ageNumber == null) ? 0 : ageNumber.hashCode());
    result = prime * result + ((agePeriodCode == null) ? 0 : agePeriodCode.hashCode());
    result = prime * result + ((alcoholIndicator == null) ? 0 : alcoholIndicator.hashCode());
    result = prime * result + ((approvalNumber == null) ? 0 : approvalNumber.hashCode());
    result = prime * result + ((approvalStatusType == null) ? 0 : approvalStatusType.hashCode());
    result = prime * result + ((countySpecificCode == null) ? 0 : countySpecificCode.hashCode());
    result = prime * result
        + ((dispositionClosureDescription == null) ? 0 : dispositionClosureDescription.hashCode());
    result = prime * result
        + ((dispositionClosureReasonType == null) ? 0 : dispositionClosureReasonType.hashCode());
    result = prime * result + ((dispositionCode == null) ? 0 : dispositionCode.hashCode());
    result = prime * result + ((dispositionDate == null) ? 0 : dispositionDate.hashCode());
    result = prime * result + ((drugIndicator == null) ? 0 : drugIndicator.hashCode());
    result = prime * result + ((clientId == null) ? 0 : clientId.hashCode());
    result = prime * result + ((referralId == null) ? 0 : referralId.hashCode());
    result = prime * result
        + ((mentalHealthIssuesIndicator == null) ? 0 : mentalHealthIssuesIndicator.hashCode());
    result =
        prime * result + ((selfReportedIndicator == null) ? 0 : selfReportedIndicator.hashCode());
    result = prime * result
        + ((staffPersonAddedIndicator == null) ? 0 : staffPersonAddedIndicator.hashCode());
    return result;
  }

  /*
   * (non-Javadoc)
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
    if (!(obj instanceof ReferralClient)) {
      return false;
    }
    ReferralClient other = (ReferralClient) obj;
    if (ageNumber == null) {
      if (other.ageNumber != null)
        return false;
    } else if (!ageNumber.equals(other.ageNumber))
      return false;
    if (agePeriodCode == null) {
      if (other.agePeriodCode != null)
        return false;
    } else if (!agePeriodCode.equals(other.agePeriodCode))
      return false;
    if (alcoholIndicator == null) {
      if (other.alcoholIndicator != null)
        return false;
    } else if (!alcoholIndicator.equals(other.alcoholIndicator))
      return false;
    if (approvalNumber == null) {
      if (other.approvalNumber != null)
        return false;
    } else if (!approvalNumber.equals(other.approvalNumber))
      return false;
    if (approvalStatusType == null) {
      if (other.approvalStatusType != null)
        return false;
    } else if (!approvalStatusType.equals(other.approvalStatusType))
      return false;
    if (countySpecificCode == null) {
      if (other.countySpecificCode != null)
        return false;
    } else if (!countySpecificCode.equals(other.countySpecificCode))
      return false;
    if (dispositionClosureDescription == null) {
      if (other.dispositionClosureDescription != null)
        return false;
    } else if (!dispositionClosureDescription.equals(other.dispositionClosureDescription))
      return false;
    if (dispositionClosureReasonType == null) {
      if (other.dispositionClosureReasonType != null)
        return false;
    } else if (!dispositionClosureReasonType.equals(other.dispositionClosureReasonType))
      return false;
    if (dispositionCode == null) {
      if (other.dispositionCode != null)
        return false;
    } else if (!dispositionCode.equals(other.dispositionCode))
      return false;
    if (dispositionDate == null) {
      if (other.dispositionDate != null)
        return false;
    } else if (!dispositionDate.equals(other.dispositionDate))
      return false;
    if (drugIndicator == null) {
      if (other.drugIndicator != null)
        return false;
    } else if (!drugIndicator.equals(other.drugIndicator))
      return false;
    if (clientId == null) {
      if (other.clientId != null)
        return false;
    } else if (!clientId.equals(other.clientId))
      return false;
    if (referralId == null) {
      if (other.referralId != null)
        return false;
    } else if (!referralId.equals(other.referralId))
      return false;
    if (mentalHealthIssuesIndicator == null) {
      if (other.mentalHealthIssuesIndicator != null)
        return false;
    } else if (!mentalHealthIssuesIndicator.equals(other.mentalHealthIssuesIndicator))
      return false;
    if (selfReportedIndicator == null) {
      if (other.selfReportedIndicator != null)
        return false;
    } else if (!selfReportedIndicator.equals(other.selfReportedIndicator))
      return false;
    if (staffPersonAddedIndicator == null) {
      if (other.staffPersonAddedIndicator != null)
        return false;
    } else if (!staffPersonAddedIndicator.equals(other.staffPersonAddedIndicator))
      return false;
    return true;
  }
}
