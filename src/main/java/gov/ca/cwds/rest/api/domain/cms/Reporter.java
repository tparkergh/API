package gov.ca.cwds.rest.api.domain.cms;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.DomainObject;
import gov.ca.cwds.rest.validation.IfThen;
import gov.ca.cwds.rest.validation.MutuallyExclusive;
import gov.ca.cwds.rest.validation.OnlyIf;
// import gov.ca.cwds.rest.validation.Zipcode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * {@link DomainObject} representing a Reporter
 * 
 * @author CWDS API Team
 */
@ApiModel
@MutuallyExclusive(required = false, properties = {"employerName", "lawEnforcementId"})
@OnlyIf(property = "badgeNumber", ifProperty = "lawEnforcementId")
@IfThen.List({@IfThen(ifProperty = "streetNumber", thenProperty = "streetName", required = false),
    @IfThen(ifProperty = "streetName", thenProperty = "cityName", required = false)})
public class Reporter extends DomainObject implements Request, Response {

  @NotEmpty
  @Size(min = 10, max = 10)
  @ApiModelProperty(required = true, readOnly = false, value = "Referral ID",
      example = "ABC1234567")
  private String referralId;

  @Size(max = 6, message = "size must be less than or equal to 6")
  @ApiModelProperty(required = false, readOnly = false,
      value = "can only be set if lawEnforcementId also provided", example = "ABC123")
  private String badgeNumber;

  @NotNull
  @Size(max = 20, message = "size must be less than or equal to 20")
  @ApiModelProperty(required = false, readOnly = false, value = "required if streetName provided",
      example = "San Jose")
  private String cityName;

  @NotNull
  @ApiModelProperty(required = true, readOnly = false, example = "1234")
  private Short colltrClientRptrReltnshpType;

  @NotNull
  @ApiModelProperty(required = true, readOnly = false, example = "1234")
  private Short communicationMethodType;

  @NotNull
  @ApiModelProperty(required = true, readOnly = false)
  private Boolean confidentialWaiverIndicator;

  @Size(max = 10)
  @ApiModelProperty(required = false, readOnly = false, value = "", example = "ABC1234567")
  private String drmsMandatedRprtrFeedback;

  @NotNull
  @Size(max = 35, message = "size must be less than or equal to 35")
  @ApiModelProperty(required = true, readOnly = false,
      value = "cannot be set if lawEnforcementId provided", example = "")
  private String employerName;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_FORMAT)
  @JsonProperty(value = "feedbackDate")
  @gov.ca.cwds.rest.validation.Date(format = DATE_FORMAT, required = false)
  @ApiModelProperty(required = false, readOnly = false, value = "yyyy-MM-dd",
      example = "2000-01-01")
  private String feedbackDate;

  @NotNull
  @ApiModelProperty(required = false, readOnly = false)
  private Boolean feedbackRequiredIndicator;

  @NotEmpty
  @Size(min = 1, max = 20)
  @ApiModelProperty(required = false, readOnly = false, value = "", example = "George")
  private String firstName;

  @NotEmpty
  @Size(min = 1, max = 25)
  @ApiModelProperty(required = false, readOnly = false, value = "", example = "Jones")
  private String lastName;

  @NotNull
  @ApiModelProperty(required = true, readOnly = false)
  private Boolean mandatedReporterIndicator;

  @NotNull
  @ApiModelProperty(required = false, readOnly = false, example = "123")
  private Integer messagePhoneExtensionNumber;

  @NotNull
  @ApiModelProperty(required = false, readOnly = false, example = "1234678")
  private BigDecimal messagePhoneNumber;

  @NotEmpty
  @Size(min = 1, max = 1, message = "size must be 1")
  @ApiModelProperty(required = false, readOnly = false, value = "", example = "A")
  private String middleInitialName;

  @NotEmpty
  @Size(min = 1, max = 6)
  @ApiModelProperty(required = false, readOnly = false, value = "", example = "ABC123")
  private String namePrefixDescription;

  @NotNull
  @ApiModelProperty(required = false, readOnly = false, example = "1234567")
  private BigDecimal primaryPhoneNumber;

  @NotNull
  @ApiModelProperty(required = false, readOnly = false, example = "123")
  private Integer primaryPhoneExtensionNumber;

  @NotNull
  @ApiModelProperty(required = false, readOnly = false, example = "12")
  private Short stateCodeType;

  @NotNull
  @Size(max = 40, message = "size must be less than or equal to 40")
  @ApiModelProperty(required = false, readOnly = false, value = "required if streetNumber provided",
      example = "Main")
  private String streetName;

  @NotNull
  @Size(max = 10, message = "size must be less than or equal to 10")
  @ApiModelProperty(required = false, readOnly = false, value = "", example = "123")
  private String streetNumber;

  @NotNull
  @Size(max = 4)
  @ApiModelProperty(required = false, readOnly = false, value = "", example = "A1")
  private String suffixTitleDescription;

  @NotNull
  @Size(min = 5, max = 5)
  @ApiModelProperty(required = false, readOnly = false, example = "08654")
  // @Zipcode(required=false)
  private String zipcode;

  @Size(max = 10, message = "size must be 10")
  @ApiModelProperty(required = false, readOnly = false,
      value = "cannot be set if employerName provided", example = "ABC1236789")
  private String lawEnforcementId;

  @NotNull
  @ApiModelProperty(required = false, readOnly = false, example = "1234")
  private Short zipSuffixNumber;

  @NotEmpty
  @Size(min = 1, max = 2)
  @ApiModelProperty(required = true, readOnly = false, value = "", example = "99")
  private String countySpecificCode;

  /**
   * @param badgeNumber badge number
   * @param cityName city name
   * @param colltrClientRptrReltnshpType ??
   * @param communicationMethodType communication method
   * @param confidentialWaiverIndicator confidential waiver flag
   * @param drmsMandatedRprtrFeedback DRMS mandated reporter feedback
   * @param employerName employer name
   * @param feedbackDate feedback date
   * @param feedbackRequiredIndicator feedback req'd flag
   * @param firstName first name
   * @param lastName last name
   * @param mandatedReporterIndicator mandated reporter flag
   * @param messagePhoneExtensionNumber message phone ext
   * @param messagePhoneNumber message phone
   * @param middleInitialName middle initial/name
   * @param namePrefixDescription name prefix description
   * @param primaryPhoneNumber primary phone
   * @param primaryPhoneExtensionNumber primary phone ext
   * @param stateCodeType state code
   * @param streetName street name
   * @param streetNumber street num
   * @param suffixTitleDescription title suffix description
   * @param zipcode zip (zip 5)
   * @param referralId FK to referral
   * @param lawEnforcementId law enforcement id
   * @param zipSuffixNumber zip suffix (zip 4)
   * @param countySpecificCode county code
   */
  @JsonCreator
  public Reporter(@JsonProperty("badgeNumber") String badgeNumber,
      @JsonProperty("cityName") String cityName,
      @JsonProperty("colltrClientRptrReltnshpType") Short colltrClientRptrReltnshpType,
      @JsonProperty("communicationMethodType") Short communicationMethodType,
      @JsonProperty("confidentialWaiverIndicator") Boolean confidentialWaiverIndicator,
      @JsonProperty("drmsMandatedRprtrFeedback") String drmsMandatedRprtrFeedback,
      @JsonProperty("employerName") String employerName,
      @JsonProperty("feedbackDate") String feedbackDate,
      @JsonProperty("feedbackRequiredIndicator") Boolean feedbackRequiredIndicator,
      @JsonProperty("firstName") String firstName, @JsonProperty("lastName") String lastName,
      @JsonProperty("mandatedReporterIndicator") Boolean mandatedReporterIndicator,
      @JsonProperty("messagePhoneExtensionNumber") Integer messagePhoneExtensionNumber,
      @JsonProperty("messagePhoneNumber") BigDecimal messagePhoneNumber,
      @JsonProperty("middleInitialName") String middleInitialName,
      @JsonProperty("namePrefixDescription") String namePrefixDescription,
      @JsonProperty("primaryPhoneNumber") BigDecimal primaryPhoneNumber,
      @JsonProperty("primaryPhoneExtensionNumber") Integer primaryPhoneExtensionNumber,
      @JsonProperty("stateCodeType") Short stateCodeType,
      @JsonProperty("streetName") String streetName,
      @JsonProperty("streetNumber") String streetNumber,
      @JsonProperty("suffixTitleDescription") String suffixTitleDescription,
      @JsonProperty("zipcode") String zipcode, @JsonProperty("referralId") String referralId,
      @JsonProperty("lawEnforcementId") String lawEnforcementId,
      @JsonProperty("zipSuffixNumber") Short zipSuffixNumber,
      @JsonProperty("countySpecificCode") String countySpecificCode) {
    super();
    this.badgeNumber = badgeNumber;
    this.cityName = cityName;
    this.colltrClientRptrReltnshpType = colltrClientRptrReltnshpType;
    this.communicationMethodType = communicationMethodType;
    this.confidentialWaiverIndicator = confidentialWaiverIndicator;
    this.drmsMandatedRprtrFeedback = drmsMandatedRprtrFeedback;
    this.employerName = employerName;
    this.feedbackDate = feedbackDate;
    this.feedbackRequiredIndicator = feedbackRequiredIndicator;
    this.firstName = firstName;
    this.lastName = lastName;
    this.mandatedReporterIndicator = mandatedReporterIndicator;
    this.messagePhoneExtensionNumber = messagePhoneExtensionNumber;
    this.messagePhoneNumber = messagePhoneNumber;
    this.middleInitialName = middleInitialName;
    this.namePrefixDescription = namePrefixDescription;
    this.primaryPhoneNumber = primaryPhoneNumber;
    this.primaryPhoneExtensionNumber = primaryPhoneExtensionNumber;
    this.stateCodeType = stateCodeType;
    this.streetName = streetName;
    this.streetNumber = streetNumber;
    this.suffixTitleDescription = suffixTitleDescription;
    this.zipcode = zipcode;
    this.referralId = referralId;
    this.lawEnforcementId = lawEnforcementId;
    this.zipSuffixNumber = zipSuffixNumber;
    this.countySpecificCode = countySpecificCode;
  }

  /**
   * @param persistedReporter - persisted Report object
   */
  public Reporter(gov.ca.cwds.rest.api.persistence.cms.Reporter persistedReporter) {
    this.referralId = persistedReporter.getReferralId().trim();
    this.badgeNumber = persistedReporter.getBadgeNumber();
    this.cityName = persistedReporter.getCityName();
    this.colltrClientRptrReltnshpType = persistedReporter.getColltrClientRptrReltnshpType();
    this.communicationMethodType = persistedReporter.getCommunicationMethodType();
    this.confidentialWaiverIndicator =
        DomainObject.uncookBooleanString(persistedReporter.getConfidentialWaiverIndicator());
    this.drmsMandatedRprtrFeedback = persistedReporter.getDrmsMandatedRprtrFeedback();
    this.employerName = persistedReporter.getEmployerName();
    this.feedbackDate = DomainObject.cookDate(persistedReporter.getFeedbackDate());
    this.feedbackRequiredIndicator =
        DomainObject.uncookBooleanString(persistedReporter.getFeedbackRequiredIndicator());
    this.firstName = persistedReporter.getFirstName();
    this.lastName = persistedReporter.getLastName();
    this.mandatedReporterIndicator =
        DomainObject.uncookBooleanString(persistedReporter.getMandatedReporterIndicator());
    this.messagePhoneExtensionNumber = persistedReporter.getMessagePhoneExtensionNumber();
    this.messagePhoneNumber = persistedReporter.getMessagePhoneNumber();
    this.middleInitialName = persistedReporter.getMiddleInitialName();
    this.namePrefixDescription = persistedReporter.getNamePrefixDescription();
    this.primaryPhoneNumber = persistedReporter.getPrimaryPhoneNumber();
    this.primaryPhoneExtensionNumber = persistedReporter.getPrimaryPhoneExtensionNumber();
    this.stateCodeType = persistedReporter.getStateCodeType();
    this.streetName = persistedReporter.getStreetName();
    this.streetNumber = persistedReporter.getStreetNumber();
    this.suffixTitleDescription = persistedReporter.getSuffixTitleDescription();
    this.zipcode = DomainObject.cookZipcodeNumber(persistedReporter.getZipNumber());
    this.lawEnforcementId = persistedReporter.getLawEnforcementId();
    this.zipSuffixNumber = persistedReporter.getZipSuffixNumber();
    this.countySpecificCode = persistedReporter.getCountySpecificCode();
  }

  /**
   * @return the badgeNumber
   */
  public String getBadgeNumber() {
    return badgeNumber;
  }

  /**
   * @return the cityName
   */
  public String getCityName() {
    return cityName;
  }

  /**
   * @return the colltrClientRptrReltnshpType
   */
  public Short getColltrClientRptrReltnshpType() {
    return colltrClientRptrReltnshpType;
  }

  /**
   * @return the communicationMethodType
   */
  public Short getCommunicationMethodType() {
    return communicationMethodType;
  }

  /**
   * @return the confidentialWaiverIndicator
   */
  public Boolean getConfidentialWaiverIndicator() {
    return confidentialWaiverIndicator;
  }

  /**
   * @return the drmsMandatedRprtrFeedback
   */
  public String getDrmsMandatedRprtrFeedback() {
    return drmsMandatedRprtrFeedback;
  }

  /**
   * @return the employerName
   */
  public String getEmployerName() {
    return employerName;
  }

  /**
   * @return the feedbackDate
   */
  public String getFeedbackDate() {
    return feedbackDate;
  }

  /**
   * @return the feedbackRequiredIndicator
   */
  public Boolean getFeedbackRequiredIndicator() {
    return feedbackRequiredIndicator;
  }

  /**
   * @return the firstName
   */
  public String getFirstName() {
    return firstName;
  }

  /**
   * @return the lastName
   */
  public String getLastName() {
    return lastName;
  }

  /**
   * @return the mandatedReporterIndicator
   */
  public Boolean getMandatedReporterIndicator() {
    return mandatedReporterIndicator;
  }

  /**
   * @return the messagePhoneExtensionNumber
   */
  public Integer getMessagePhoneExtensionNumber() {
    return messagePhoneExtensionNumber;
  }

  /**
   * @return the messagePhoneNumber
   */
  public BigDecimal getMessagePhoneNumber() {
    return messagePhoneNumber;
  }

  /**
   * @return the middleInitialName
   */
  public String getMiddleInitialName() {
    return middleInitialName;
  }

  /**
   * @return the namePrefixDescription
   */
  public String getNamePrefixDescription() {
    return namePrefixDescription;
  }

  /**
   * @return the primaryPhoneNumber
   */
  public BigDecimal getPrimaryPhoneNumber() {
    return primaryPhoneNumber;
  }

  /**
   * @return the primaryPhoneExtensionNumber
   */
  public Integer getPrimaryPhoneExtensionNumber() {
    return primaryPhoneExtensionNumber;
  }

  /**
   * @return the stateCodeType
   */
  public Short getStateCodeType() {
    return stateCodeType;
  }

  /**
   * @return the streetName
   */
  public String getStreetName() {
    return streetName;
  }

  /**
   * @return the streetNumber
   */
  public String getStreetNumber() {
    return streetNumber;
  }

  /**
   * @return the suffixTitleDescription
   */
  public String getSuffixTitleDescription() {
    return suffixTitleDescription;
  }

  /**
   * @return the zipcode
   */
  public String getZipcode() {
    return zipcode;
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
   * @return the zipSuffixNumber
   */
  public Short getZipSuffixNumber() {
    return zipSuffixNumber;
  }

  /**
   * @return the countySpecificCode
   */
  public String getCountySpecificCode() {
    return countySpecificCode;
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
    result = prime * result + ((badgeNumber == null) ? 0 : badgeNumber.hashCode());
    result = prime * result + ((cityName == null) ? 0 : cityName.hashCode());
    result = prime * result
        + ((colltrClientRptrReltnshpType == null) ? 0 : colltrClientRptrReltnshpType.hashCode());
    result = prime * result
        + ((communicationMethodType == null) ? 0 : communicationMethodType.hashCode());
    result = prime * result
        + ((confidentialWaiverIndicator == null) ? 0 : confidentialWaiverIndicator.hashCode());
    result = prime * result + ((countySpecificCode == null) ? 0 : countySpecificCode.hashCode());
    result = prime * result
        + ((drmsMandatedRprtrFeedback == null) ? 0 : drmsMandatedRprtrFeedback.hashCode());
    result = prime * result + ((employerName == null) ? 0 : employerName.hashCode());
    result = prime * result + ((feedbackDate == null) ? 0 : feedbackDate.hashCode());
    result = prime * result
        + ((feedbackRequiredIndicator == null) ? 0 : feedbackRequiredIndicator.hashCode());
    result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
    result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
    result = prime * result + ((lawEnforcementId == null) ? 0 : lawEnforcementId.hashCode());
    result = prime * result
        + ((mandatedReporterIndicator == null) ? 0 : mandatedReporterIndicator.hashCode());
    result = prime * result
        + ((messagePhoneExtensionNumber == null) ? 0 : messagePhoneExtensionNumber.hashCode());
    result = prime * result + ((messagePhoneNumber == null) ? 0 : messagePhoneNumber.hashCode());
    result = prime * result + ((middleInitialName == null) ? 0 : middleInitialName.hashCode());
    result =
        prime * result + ((namePrefixDescription == null) ? 0 : namePrefixDescription.hashCode());
    result = prime * result
        + ((primaryPhoneExtensionNumber == null) ? 0 : primaryPhoneExtensionNumber.hashCode());
    result = prime * result + ((primaryPhoneNumber == null) ? 0 : primaryPhoneNumber.hashCode());
    result = prime * result + ((referralId == null) ? 0 : referralId.hashCode());
    result = prime * result + ((stateCodeType == null) ? 0 : stateCodeType.hashCode());
    result = prime * result + ((streetName == null) ? 0 : streetName.hashCode());
    result = prime * result + ((streetNumber == null) ? 0 : streetNumber.hashCode());
    result =
        prime * result + ((suffixTitleDescription == null) ? 0 : suffixTitleDescription.hashCode());
    result = prime * result + ((zipcode == null) ? 0 : zipcode.hashCode());
    result = prime * result + ((zipSuffixNumber == null) ? 0 : zipSuffixNumber.hashCode());
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
    if (!(obj instanceof Reporter)) {
      return false;
    }
    Reporter other = (Reporter) obj;
    if (badgeNumber == null) {
      if (other.badgeNumber != null)
        return false;
    } else if (!badgeNumber.equals(other.badgeNumber))
      return false;
    if (cityName == null) {
      if (other.cityName != null)
        return false;
    } else if (!cityName.equals(other.cityName))
      return false;
    if (colltrClientRptrReltnshpType == null) {
      if (other.colltrClientRptrReltnshpType != null)
        return false;
    } else if (!colltrClientRptrReltnshpType.equals(other.colltrClientRptrReltnshpType))
      return false;
    if (communicationMethodType == null) {
      if (other.communicationMethodType != null)
        return false;
    } else if (!communicationMethodType.equals(other.communicationMethodType))
      return false;
    if (confidentialWaiverIndicator == null) {
      if (other.confidentialWaiverIndicator != null)
        return false;
    } else if (!confidentialWaiverIndicator.equals(other.confidentialWaiverIndicator))
      return false;
    if (countySpecificCode == null) {
      if (other.countySpecificCode != null)
        return false;
    } else if (!countySpecificCode.equals(other.countySpecificCode))
      return false;
    if (drmsMandatedRprtrFeedback == null) {
      if (other.drmsMandatedRprtrFeedback != null)
        return false;
    } else if (!drmsMandatedRprtrFeedback.equals(other.drmsMandatedRprtrFeedback))
      return false;
    if (employerName == null) {
      if (other.employerName != null)
        return false;
    } else if (!employerName.equals(other.employerName))
      return false;
    if (feedbackDate == null) {
      if (other.feedbackDate != null)
        return false;
    } else if (!feedbackDate.equals(other.feedbackDate))
      return false;
    if (feedbackRequiredIndicator == null) {
      if (other.feedbackRequiredIndicator != null)
        return false;
    } else if (!feedbackRequiredIndicator.equals(other.feedbackRequiredIndicator))
      return false;
    if (firstName == null) {
      if (other.firstName != null)
        return false;
    } else if (!firstName.equals(other.firstName))
      return false;
    if (lastName == null) {
      if (other.lastName != null)
        return false;
    } else if (!lastName.equals(other.lastName))
      return false;
    if (lawEnforcementId == null) {
      if (other.lawEnforcementId != null)
        return false;
    } else if (!lawEnforcementId.equals(other.lawEnforcementId))
      return false;
    if (mandatedReporterIndicator == null) {
      if (other.mandatedReporterIndicator != null)
        return false;
    } else if (!mandatedReporterIndicator.equals(other.mandatedReporterIndicator))
      return false;
    if (messagePhoneExtensionNumber == null) {
      if (other.messagePhoneExtensionNumber != null)
        return false;
    } else if (!messagePhoneExtensionNumber.equals(other.messagePhoneExtensionNumber))
      return false;
    if (messagePhoneNumber == null) {
      if (other.messagePhoneNumber != null)
        return false;
    } else if (!messagePhoneNumber.equals(other.messagePhoneNumber))
      return false;
    if (middleInitialName == null) {
      if (other.middleInitialName != null)
        return false;
    } else if (!middleInitialName.equals(other.middleInitialName))
      return false;
    if (namePrefixDescription == null) {
      if (other.namePrefixDescription != null)
        return false;
    } else if (!namePrefixDescription.equals(other.namePrefixDescription))
      return false;
    if (primaryPhoneExtensionNumber == null) {
      if (other.primaryPhoneExtensionNumber != null)
        return false;
    } else if (!primaryPhoneExtensionNumber.equals(other.primaryPhoneExtensionNumber))
      return false;
    if (primaryPhoneNumber == null) {
      if (other.primaryPhoneNumber != null)
        return false;
    } else if (!primaryPhoneNumber.equals(other.primaryPhoneNumber))
      return false;
    if (referralId == null) {
      if (other.referralId != null)
        return false;
    } else if (!referralId.equals(other.referralId))
      return false;
    if (stateCodeType == null) {
      if (other.stateCodeType != null)
        return false;
    } else if (!stateCodeType.equals(other.stateCodeType))
      return false;
    if (streetName == null) {
      if (other.streetName != null)
        return false;
    } else if (!streetName.equals(other.streetName))
      return false;
    if (streetNumber == null) {
      if (other.streetNumber != null)
        return false;
    } else if (!streetNumber.equals(other.streetNumber))
      return false;
    if (suffixTitleDescription == null) {
      if (other.suffixTitleDescription != null)
        return false;
    } else if (!suffixTitleDescription.equals(other.suffixTitleDescription))
      return false;
    if (zipcode == null) {
      if (other.zipcode != null)
        return false;
    } else if (!zipcode.equals(other.zipcode))
      return false;
    if (zipSuffixNumber == null) {
      if (other.zipSuffixNumber != null)
        return false;
    } else if (!zipSuffixNumber.equals(other.zipSuffixNumber))
      return false;
    return true;
  }
}
