package gov.ca.cwds.rest.api.domain.cms;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.DomainChef;
import gov.ca.cwds.rest.api.domain.DomainObject;
import io.dropwizard.validation.OneOf;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * {@link DomainObject} representing an Allegation
 * 
 * @author CWDS API Team
 */
@ApiModel
public class Allegation extends DomainObject implements Request, Response {

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_FORMAT)
  @JsonProperty(value = "abuseEndDate")
  @gov.ca.cwds.rest.validation.Date(format = DATE_FORMAT, required = false)
  @ApiModelProperty(required = false, readOnly = false, value = "date when abuse allegedly ended",
      example = "2016-11-30")
  private String abuseEndDate;

  @NotNull
  @ApiModelProperty(required = false, readOnly = false, example = "1234")
  private Short abuseFrequency;

  @NotNull
  @Size(max = 1)
  @OneOf(value = {"D", "M", "W", "Y"}, ignoreCase = true, ignoreWhitespace = true)
  @ApiModelProperty(required = false, readOnly = false, value = "frequency of abuse", example = "D",
      allowableValues = "D, M, W, Y")
  private String abuseFrequencyPeriodCode;

  @NotEmpty
  @Size(max = 75)
  @ApiModelProperty(required = true, readOnly = false, value = "location description",
      example = "school yard")
  private String abuseLocationDescription;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_FORMAT)
  @JsonProperty(value = "abuseStartDate")
  @gov.ca.cwds.rest.validation.Date(format = DATE_FORMAT, required = false)
  @ApiModelProperty(required = false, readOnly = false, value = "start date of alleged abuse",
      example = "2016-11-30")
  private String abuseStartDate;

  @NotNull
  @ApiModelProperty(required = false, readOnly = false, example = "1234")
  private Short allegationDispositionType;

  @NotNull
  @ApiModelProperty(required = true, readOnly = false, example = "1234")
  private Short allegationType;

  @NotEmpty
  @Size(max = 254)
  @ApiModelProperty(required = false, readOnly = false,
      value = "description of allegation disposition", example = "investigate")
  private String dispositionDescription;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_FORMAT)
  @JsonProperty(value = "dispositionDate")
  @gov.ca.cwds.rest.validation.Date(format = DATE_FORMAT, required = false)
  @ApiModelProperty(required = false, readOnly = false, value = "date of allegation disposition",
      example = "2016-11-30")
  private String dispositionDate;

  @NotNull
  @ApiModelProperty(required = false, readOnly = false)
  private Boolean injuryHarmDetailIndicator;

  @NotEmpty
  @Size(min = 1, max = 1, message = "size must be 1")
  @OneOf(value = {"U", "P", "Y", "N"}, ignoreCase = true, ignoreWhitespace = true)
  @ApiModelProperty(required = true, readOnly = false, value = "", example = "U",
      allowableValues = "U, P, Y, N")
  private String nonProtectingParentCode;

  @NotNull
  @ApiModelProperty(required = true, readOnly = false)
  private Boolean staffPersonAddedIndicator;

  @NotEmpty
  @Size(min = 10, max = 10)
  @ApiModelProperty(required = true, readOnly = false, value = "CLIENT ID of victim",
      example = "ABC1234567")
  // TODO Add Foreign Key Validation after CLIENT table is added to source code
  private String victimClientId;

  @Size(max = 10)
  @ApiModelProperty(required = false, readOnly = false, value = "CLIENT ID of perpetrator",
      example = "ABC1234567")
  private String perpetratorClientId;

  @NotEmpty
  @Size(min = 10, max = 10)
  @ApiModelProperty(required = true, readOnly = false, value = "", example = "ABC1234567")
  private String referralId;

  @NotEmpty
  @Size(min = 1, max = 2)
  @ApiModelProperty(required = true, readOnly = false, value = "County code", example = "A1")
  private String countySpecificCode;

  @NotNull
  @ApiModelProperty(required = true, readOnly = false)
  private Boolean zippyCreatedIndicator;

  @ApiModelProperty(required = false, readOnly = false, example = "1234")
  private Short placementFacilityType;

  /**
   * Constructor. Build from JSON.
   * 
   * @param abuseEndDate abuse end date
   * @param abuseFrequency abuse frequency
   * @param abuseFrequencyPeriodCode abuse frequency period code
   * @param abuseLocationDescription abuse location
   * @param abuseStartDate abuse start date
   * @param allegationDispositionType abuse disposition
   * @param allegationType type of allegation
   * @param dispositionDescription description of disposition
   * @param dispositionDate disposition date
   * @param injuryHarmDetailIndicator flag injury or harm
   * @param nonProtectingParentCode non-protecting parent code
   * @param staffPersonAddedIndicator flag added by staff
   * @param victimClientId victim or client id
   * @param perpetratorClientId perpetrator's client id
   * @param referralId referral id
   * @param countySpecificCode county code
   * @param zippyCreatedIndicator created by Zippy
   * @param placementFacilityType type of placement facility
   */
  @JsonCreator
  public Allegation(@JsonProperty("abuseEndDate") String abuseEndDate,
      @JsonProperty("abuseFrequency") Short abuseFrequency,
      @JsonProperty("abuseFrequencyPeriodCode") String abuseFrequencyPeriodCode,
      @JsonProperty("abuseLocationDescription") String abuseLocationDescription,
      @JsonProperty("abuseStartDate") String abuseStartDate,
      @JsonProperty("allegationDispositionType") Short allegationDispositionType,
      @JsonProperty("allegationType") Short allegationType,
      @JsonProperty("dispositionDescription") String dispositionDescription,
      @JsonProperty("dispositionDate") String dispositionDate,
      @JsonProperty("injuryHarmDetailIndicator") Boolean injuryHarmDetailIndicator,
      @JsonProperty("nonProtectingParentCode") String nonProtectingParentCode,
      @JsonProperty("staffPersonAddedIndicator") Boolean staffPersonAddedIndicator,
      @JsonProperty("victimClientId") String victimClientId,
      @JsonProperty("perpetratorClientId") String perpetratorClientId,
      @JsonProperty("referralId") String referralId,
      @JsonProperty("countySpecificCode") String countySpecificCode,
      @JsonProperty("zippyCreatedIndicator") Boolean zippyCreatedIndicator,
      @JsonProperty("placementFacilityType") Short placementFacilityType) {
    super();
    this.abuseEndDate = abuseEndDate;
    this.abuseFrequency = abuseFrequency;
    this.abuseFrequencyPeriodCode = abuseFrequencyPeriodCode;
    this.abuseLocationDescription = abuseLocationDescription;
    this.abuseStartDate = abuseStartDate;
    this.allegationDispositionType = allegationDispositionType;
    this.allegationType = allegationType;
    this.dispositionDescription = dispositionDescription;
    this.dispositionDate = dispositionDate;
    this.injuryHarmDetailIndicator = injuryHarmDetailIndicator;
    this.nonProtectingParentCode = nonProtectingParentCode;
    this.staffPersonAddedIndicator = staffPersonAddedIndicator;
    this.victimClientId = victimClientId;
    this.perpetratorClientId = perpetratorClientId;
    this.referralId = referralId;
    this.countySpecificCode = countySpecificCode;
    this.zippyCreatedIndicator = zippyCreatedIndicator;
    this.placementFacilityType = placementFacilityType;
  }

  /**
   * Construct from persistence layer CMS allegation.
   * 
   * @param persistedAllegation persistence level allegation
   */
  public Allegation(gov.ca.cwds.data.persistence.cms.Allegation persistedAllegation) {
    this.abuseEndDate = DomainChef.cookDate(persistedAllegation.getAbuseEndDate());
    this.abuseFrequency = persistedAllegation.getAbuseFrequency();
    this.abuseFrequencyPeriodCode = persistedAllegation.getAbuseFrequencyPeriodCode();
    this.abuseLocationDescription = persistedAllegation.getAbuseLocationDescription();
    this.abuseStartDate = DomainChef.cookDate(persistedAllegation.getAbuseStartDate());
    this.allegationDispositionType = persistedAllegation.getAllegationDispositionType();
    this.allegationType = persistedAllegation.getAllegationType();
    this.dispositionDescription = persistedAllegation.getDispositionDescription();
    this.dispositionDate = DomainChef.cookDate(persistedAllegation.getDispositionDate());
    this.injuryHarmDetailIndicator =
        DomainChef.uncookBooleanString(persistedAllegation.getInjuryHarmDetailIndicator());
    this.nonProtectingParentCode = persistedAllegation.getNonProtectingParentCode();
    this.staffPersonAddedIndicator =
        DomainChef.uncookBooleanString(persistedAllegation.getStaffPersonAddedIndicator());
    this.victimClientId = persistedAllegation.getVictimClientId();
    this.perpetratorClientId = persistedAllegation.getPerpetratorClientId();
    this.referralId = persistedAllegation.getReferralId();
    this.countySpecificCode = persistedAllegation.getCountySpecificCode();
    this.zippyCreatedIndicator =
        DomainChef.uncookBooleanString(persistedAllegation.getZippyCreatedIndicator());
    this.placementFacilityType = persistedAllegation.getPlacementFacilityType();
  }

  /**
   * @return the abuseEndDate
   */
  public String getAbuseEndDate() {
    return abuseEndDate;
  }

  /**
   * @return the abuseFrequency
   */
  public Short getAbuseFrequency() {
    return abuseFrequency;
  }

  /**
   * @return the abuseFrequencyPeriodCode
   */
  public String getAbuseFrequencyPeriodCode() {
    return abuseFrequencyPeriodCode;
  }

  /**
   * @return the abuseLocationDescription
   */
  public String getAbuseLocationDescription() {
    return abuseLocationDescription;
  }

  /**
   * @return the abuseStartDate
   */
  public String getAbuseStartDate() {
    return abuseStartDate;
  }

  /**
   * @return the allegationDispositionType
   */
  public Short getAllegationDispositionType() {
    return allegationDispositionType;
  }

  /**
   * @return the allegationType
   */
  public Short getAllegationType() {
    return allegationType;
  }

  /**
   * @return the dispositionDescription
   */
  public String getDispositionDescription() {
    return dispositionDescription;
  }

  /**
   * @return the dispositionDate
   */
  public String getDispositionDate() {
    return dispositionDate;
  }

  /**
   * @return the injuryHarmDetailIndicator
   */
  public Boolean getInjuryHarmDetailIndicator() {
    return injuryHarmDetailIndicator;
  }

  /**
   * @return the nonProtectingParentCode
   */
  public String getNonProtectingParentCode() {
    return nonProtectingParentCode;
  }

  /**
   * @return the staffPersonAddedIndicator
   */
  public Boolean getStaffPersonAddedIndicator() {
    return staffPersonAddedIndicator;
  }

  /**
   * @return the victimClientId
   */
  public String getVictimClientId() {
    return victimClientId;
  }

  /**
   * @return the perpetratorClientId
   */
  public String getPerpetratorClientId() {
    return perpetratorClientId;
  }

  /**
   * @return the referralId
   */
  public String getReferralId() {
    return referralId;
  }

  /**
   * @return the countySpecificCode
   */
  public String getCountySpecificCode() {
    return countySpecificCode;
  }

  /**
   * @return the zippyCreatedIndicator
   */
  public Boolean getZippyCreatedIndicator() {
    return zippyCreatedIndicator;
  }

  /**
   * @return the placementFacilityType
   */
  public Short getPlacementFacilityType() {
    return placementFacilityType;
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
    result = prime * result + ((abuseEndDate == null) ? 0 : abuseEndDate.hashCode());
    result = prime * result + ((abuseFrequency == null) ? 0 : abuseFrequency.hashCode());
    result = prime * result
        + ((abuseFrequencyPeriodCode == null) ? 0 : abuseFrequencyPeriodCode.hashCode());
    result = prime * result
        + ((abuseLocationDescription == null) ? 0 : abuseLocationDescription.hashCode());
    result = prime * result + ((abuseStartDate == null) ? 0 : abuseStartDate.hashCode());
    result = prime * result
        + ((allegationDispositionType == null) ? 0 : allegationDispositionType.hashCode());
    result = prime * result + ((allegationType == null) ? 0 : allegationType.hashCode());
    result = prime * result + ((countySpecificCode == null) ? 0 : countySpecificCode.hashCode());
    result = prime * result + ((dispositionDate == null) ? 0 : dispositionDate.hashCode());
    result =
        prime * result + ((dispositionDescription == null) ? 0 : dispositionDescription.hashCode());
    result = prime * result + ((perpetratorClientId == null) ? 0 : perpetratorClientId.hashCode());
    result = prime * result + ((victimClientId == null) ? 0 : victimClientId.hashCode());
    result = prime * result + ((referralId == null) ? 0 : referralId.hashCode());
    result = prime * result
        + ((injuryHarmDetailIndicator == null) ? 0 : injuryHarmDetailIndicator.hashCode());
    result = prime * result
        + ((nonProtectingParentCode == null) ? 0 : nonProtectingParentCode.hashCode());
    result =
        prime * result + ((placementFacilityType == null) ? 0 : placementFacilityType.hashCode());
    result = prime * result
        + ((staffPersonAddedIndicator == null) ? 0 : staffPersonAddedIndicator.hashCode());
    result =
        prime * result + ((zippyCreatedIndicator == null) ? 0 : zippyCreatedIndicator.hashCode());
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
    if (!(obj instanceof Allegation)) {
      return false;
    }
    Allegation other = (Allegation) obj;
    if (abuseEndDate == null) {
      if (other.abuseEndDate != null) {
        return false;
      }
    } else if (!abuseEndDate.equals(other.abuseEndDate)) {
      return false;
    }
    if (abuseFrequency == null) {
      if (other.abuseFrequency != null) {
        return false;
      }
    } else if (!abuseFrequency.equals(other.abuseFrequency)) {
      return false;
    }
    if (abuseFrequencyPeriodCode == null) {
      if (other.abuseFrequencyPeriodCode != null) {
        return false;
      }
    } else if (!abuseFrequencyPeriodCode.equals(other.abuseFrequencyPeriodCode)) {
      return false;
    }
    if (abuseLocationDescription == null) {
      if (other.abuseLocationDescription != null) {
        return false;
      }
    } else if (!abuseLocationDescription.equals(other.abuseLocationDescription)) {
      return false;
    }
    if (abuseStartDate == null) {
      if (other.abuseStartDate != null) {
        return false;
      }
    } else if (!abuseStartDate.equals(other.abuseStartDate)) {
      return false;
    }
    if (allegationDispositionType == null) {
      if (other.allegationDispositionType != null) {
        return false;
      }
    } else if (!allegationDispositionType.equals(other.allegationDispositionType)) {
      return false;
    }
    if (allegationType == null) {
      if (other.allegationType != null) {
        return false;
      }
    } else if (!allegationType.equals(other.allegationType)) {
      return false;
    }
    if (countySpecificCode == null) {
      if (other.countySpecificCode != null) {
        return false;
      }
    } else if (!countySpecificCode.equals(other.countySpecificCode)) {
      return false;
    }
    if (dispositionDate == null) {
      if (other.dispositionDate != null) {
        return false;
      }
    } else if (!dispositionDate.equals(other.dispositionDate)) {
      return false;
    }
    if (dispositionDescription == null) {
      if (other.dispositionDescription != null) {
        return false;
      }
    } else if (!dispositionDescription.equals(other.dispositionDescription)) {
      return false;
    }
    if (perpetratorClientId == null) {
      if (other.perpetratorClientId != null) {
        return false;
      }
    } else if (!perpetratorClientId.equals(other.perpetratorClientId)) {
      return false;
    }
    if (victimClientId == null) {
      if (other.victimClientId != null) {
        return false;
      }
    } else if (!victimClientId.equals(other.victimClientId)) {
      return false;
    }
    if (referralId == null) {
      if (other.referralId != null) {
        return false;
      }
    } else if (!referralId.equals(other.referralId)) {
      return false;
    }
    if (injuryHarmDetailIndicator == null) {
      if (other.injuryHarmDetailIndicator != null) {
        return false;
      }
    } else if (!injuryHarmDetailIndicator.equals(other.injuryHarmDetailIndicator)) {
      return false;
    }
    if (nonProtectingParentCode == null) {
      if (other.nonProtectingParentCode != null) {
        return false;
      }
    } else if (!nonProtectingParentCode.equals(other.nonProtectingParentCode)) {
      return false;
    }
    if (placementFacilityType == null) {
      if (other.placementFacilityType != null) {
        return false;
      }
    } else if (!placementFacilityType.equals(other.placementFacilityType)) {
      return false;
    }
    if (staffPersonAddedIndicator == null) {
      if (other.staffPersonAddedIndicator != null) {
        return false;
      }
    } else if (!staffPersonAddedIndicator.equals(other.staffPersonAddedIndicator)) {
      return false;
    }
    if (zippyCreatedIndicator == null) {
      if (other.zippyCreatedIndicator != null) {
        return false;
      }
    } else if (!zippyCreatedIndicator.equals(other.zippyCreatedIndicator)) {
      return false;
    }
    return true;
  }

}
