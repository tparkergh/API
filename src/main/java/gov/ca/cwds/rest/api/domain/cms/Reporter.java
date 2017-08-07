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
import gov.ca.cwds.rest.api.domain.Participant;
import gov.ca.cwds.rest.api.domain.ReportingDomain;
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
public class Reporter extends ReportingDomain implements Request, Response {
  private static final short DEFAULT_CODE = 0;
  private static final BigDecimal DEFAULT_DECIMAL = new BigDecimal(0);


  /**
   * Serialization version.
   */
  private static final long serialVersionUID = 1L;

  @NotEmpty
  @Size(min = CMS_ID_LEN, max = CMS_ID_LEN)
  @ApiModelProperty(required = true, readOnly = false, value = "Referral ID",
      example = "ABC1234567")
  private String referralId;

  @Size(max = 6, message = "size must be less than or equal to 6")
  @ApiModelProperty(required = false, readOnly = false,
      value = "can only be set if lawEnforcementId also provided", example = "ABC123")
  private String badgeNumber;

  @NotNull
  @Size(max = 20, message = "size must be less than or equal to 20")
  @ApiModelProperty(required = false, readOnly = false, value = "City name", example = "San Jose")
  private String cityName;

  @SystemCodeSerializer(logical = true, description = true)
  @NotNull
  @ApiModelProperty(required = true, readOnly = false, example = "1234")
  private Short colltrClientRptrReltnshpType;

  @SystemCodeSerializer(logical = true, description = true)
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
      value = "cannot be set if lawEnforcementId provided", example = "employer name")
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
  @ApiModelProperty(required = false, readOnly = false, value = "First name", example = "George")
  private String firstName;

  @NotEmpty
  @Size(min = 1, max = 25)
  @ApiModelProperty(required = false, readOnly = false, value = "Last name", example = "Jones")
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

  @NotNull
  @Size(min = 1, max = 1,
      message = "middleInitialName size should be 1 or assign the the default value of Space")
  @ApiModelProperty(required = true, readOnly = false, value = "Middle name", example = "A")
  private String middleInitialName;

  @NotNull
  @Size(max = 6)
  @ApiModelProperty(required = false, readOnly = false, value = "Name prefix", example = "ABC123")
  private String namePrefixDescription;

  @NotNull
  @ApiModelProperty(required = false, readOnly = false, example = "1234567")
  private BigDecimal primaryPhoneNumber;

  @NotNull
  @ApiModelProperty(required = false, readOnly = false, example = "123")
  private Integer primaryPhoneExtensionNumber;

  @SystemCodeSerializer(logical = true, description = true)
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
  @ApiModelProperty(required = false, readOnly = false, value = "Street number", example = "123")
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

  @Size(max = CMS_ID_LEN, message = "size must be 10")
  @ApiModelProperty(required = false, readOnly = false,
      value = "cannot be set if employerName provided", example = "ABC1236789")
  private String lawEnforcementId;

  @NotNull
  @ApiModelProperty(required = false, readOnly = false, example = "1234")
  private Short zipSuffixNumber;

  @NotEmpty
  @Size(min = 1, max = 2)
  @ApiModelProperty(required = true, readOnly = false, value = "County specific code",
      example = "99")
  private String countySpecificCode;

  /**
   * Default Constructor
   */
  public Reporter() {
    // Default, no-op.
  }

  /**
   * Construct from all fields.
   * 
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
    this.firstName = firstName == null ? "" : firstName;
    this.lastName = lastName == null ? "" : lastName;
    this.mandatedReporterIndicator = mandatedReporterIndicator;
    this.messagePhoneExtensionNumber = messagePhoneExtensionNumber;
    this.messagePhoneNumber = messagePhoneNumber;
    this.middleInitialName = middleInitialName == null ? "" : middleInitialName;
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
  public Reporter(gov.ca.cwds.data.persistence.cms.Reporter persistedReporter) {
    this.referralId = persistedReporter.getReferralId().trim();
    this.badgeNumber = persistedReporter.getBadgeNumber();
    this.cityName = persistedReporter.getCityName();
    this.colltrClientRptrReltnshpType = persistedReporter.getColltrClientRptrReltnshpType();
    this.communicationMethodType = persistedReporter.getCommunicationMethodType();
    this.confidentialWaiverIndicator =
        DomainChef.uncookBooleanString(persistedReporter.getConfidentialWaiverIndicator());
    this.drmsMandatedRprtrFeedback = persistedReporter.getDrmsMandatedRprtrFeedback();
    this.employerName = persistedReporter.getEmployerName();
    this.feedbackDate = DomainChef.cookDate(persistedReporter.getFeedbackDate());
    this.feedbackRequiredIndicator =
        DomainChef.uncookBooleanString(persistedReporter.getFeedbackRequiredIndicator());
    this.firstName = persistedReporter.getFirstName();
    this.lastName = persistedReporter.getLastName();
    this.mandatedReporterIndicator =
        DomainChef.uncookBooleanString(persistedReporter.getMandatedReporterIndicator());
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
    this.zipcode = DomainChef.cookZipcodeNumber(persistedReporter.getZipNumber());
    this.lawEnforcementId = persistedReporter.getLawEnforcementId();
    this.zipSuffixNumber = persistedReporter.getZipSuffixNumber();
    this.countySpecificCode = persistedReporter.getCountySpecificCode();
  }

  /**
   * @param referralId - referralId
   * @param isMandatedReporter - iaMandatedReporter
   * @param address - address
   * @param participant - participant
   * @param countyCode - countyCode
   * @param stateCode - stateCode
   * @return the address
   */
  public static Reporter createWithDefaults(String referralId, boolean isMandatedReporter,
      gov.ca.cwds.rest.api.domain.Address address, Participant participant, String countyCode) {
    // TODO: #141511573 address parsing - Smarty Streets Free Form display requires
    // standardizing
    // parsing to fields in CMS
    String zipCodeString = address.getZip().toString();

    /**
     * Split the StreetAddress into separate streetNumber and StreetName objects, updates the
     * respective columns. If the streetAddress is entered only words, it will throw a validation
     * exception to enter the streetNumber.
     */
    String streetNumber = null;
    String streetName = null;
    int index;
    if ((index = address.getStreetAddress().indexOf(" ")) > 0) {
      streetNumber = address.getStreetAddress().substring(0, index);
      if (!streetNumber.chars().allMatch(Character::isDigit)) {
        streetNumber = null;
        streetName = address.getStreetAddress();
      } else {
        streetName =
            address.getStreetAddress().substring(index + 1, address.getStreetAddress().length());
      }
    } else {
      if (address.getStreetAddress().chars().allMatch(Character::isDigit)) {
        streetNumber = address.getStreetAddress();
      } else {
        streetName = address.getStreetAddress();
      }
    }
    String city = address.getCity();

    return new Reporter("", city, DEFAULT_CODE, DEFAULT_CODE, false, "", "", "", false,
        participant.getFirstName(), participant.getLastName(), isMandatedReporter, 0,
        DEFAULT_DECIMAL, participant.getMiddleName(), "", DEFAULT_DECIMAL, 0,
        address.getState().shortValue(), streetName, streetNumber, "", zipCodeString, referralId,
        "", DEFAULT_CODE, countyCode);
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
