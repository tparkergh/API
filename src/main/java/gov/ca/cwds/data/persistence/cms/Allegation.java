package gov.ca.cwds.data.persistence.cms;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import gov.ca.cwds.data.SystemCodeSerializer;
import gov.ca.cwds.rest.api.domain.DomainChef;


/**
 * Class representing an Allegation.
 * 
 * @author CWDS API Team
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "ALLGTN_T")
public class Allegation extends CmsPersistentObject {
  protected static final String DATE_FORMAT = "yyyy-MM-dd";

  @Id
  @Column(name = "IDENTIFIER", length = CMS_ID_LEN)
  private String id;

  @Type(type = "date")
  @Column(name = "ABUSE_ENDT")
  private Date abuseEndDate;

  @Type(type = "short")
  @Column(name = "ABUSE_FREQ")
  private Short abuseFrequency;

  @Column(name = "ABUSE_PDCD")
  private String abuseFrequencyPeriodCode;

  @Column(name = "LOC_DSC")
  private String abuseLocationDescription;

  @Type(type = "date")
  @Column(name = "ABUSE_STDT")
  private Date abuseStartDate;

  @Type(type = "short")
  @Column(name = "ALG_DSPC")
  private Short allegationDispositionType;

  @SystemCodeSerializer
  @Type(type = "short")
  @Column(name = "ALG_TPC")
  private Short allegationType;

  @Column(name = "DISPSN_DSC")
  private String dispositionDescription;

  @Type(type = "date")
  @Column(name = "DISPSN_DT")
  private Date dispositionDate;

  @Column(name = "IJHM_DET_B")
  private String injuryHarmDetailIndicator;

  @Column(name = "NON_PRT_CD")
  private String nonProtectingParentCode;

  @Column(name = "STFADD_IND")
  private String staffPersonAddedIndicator;

  @Column(name = "FKCLIENT_T", length = CMS_ID_LEN)
  private String victimClientId;

  @Column(name = "FKCLIENT_0", length = CMS_ID_LEN)
  private String perpetratorClientId;

  @Column(name = "FKREFERL_T", length = CMS_ID_LEN)
  private String referralId;

  @Column(name = "CNTY_SPFCD")
  private String countySpecificCode;

  @Column(name = "ZIPPY_IND")
  private String zippyCreatedIndicator;

  @Type(type = "short")
  @Column(name = "PLC_FCLC")
  private Short placementFacilityType;

  /**
   * Default constructor
   * 
   * Required for Hibernate
   */
  public Allegation() {
    super();
  }

  /**
   * Constructor
   * 
   * @param id primary key
   * @param abuseEndDate abuse end date
   * @param abuseStartDate abuse start date
   * @param abuseFrequency abuse frequency
   * @param abuseFrequencyPeriodCode abuse frequency period code
   * @param abuseLocationDescription abuse location
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
  public Allegation(String id, Date abuseEndDate, Date abuseStartDate, Short abuseFrequency,
      String abuseFrequencyPeriodCode, String abuseLocationDescription,
      Short allegationDispositionType, Short allegationType, String dispositionDescription,
      Date dispositionDate, String injuryHarmDetailIndicator, String nonProtectingParentCode,
      String staffPersonAddedIndicator, String victimClientId, String perpetratorClientId,
      String referralId, String countySpecificCode, String zippyCreatedIndicator,
      Short placementFacilityType) {
    super();

    this.id = id;
    this.abuseEndDate = abuseEndDate;
    this.abuseStartDate = abuseStartDate;
    this.abuseFrequency = abuseFrequency;
    this.abuseFrequencyPeriodCode = abuseFrequencyPeriodCode;
    this.abuseLocationDescription = abuseLocationDescription;
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
   * Constructor using domain
   * 
   * @param id The id
   * @param persistedAllegation The domain object to construct this object from
   * @param lastUpdatedId the id of the last person to update this object
   */
  public Allegation(String id, gov.ca.cwds.rest.api.domain.cms.Allegation persistedAllegation,
      String lastUpdatedId) {
    super(lastUpdatedId);

    // TODO: constructor will not work with null date fields
    this.id = id;
    this.abuseEndDate = DomainChef.uncookDateString(persistedAllegation.getAbuseEndDate());
    this.abuseStartDate = DomainChef.uncookDateString(persistedAllegation.getAbuseStartDate());
    this.abuseFrequency = persistedAllegation.getAbuseFrequency();
    this.abuseFrequencyPeriodCode = persistedAllegation.getAbuseFrequencyPeriodCode();
    this.abuseLocationDescription = persistedAllegation.getAbuseLocationDescription();
    this.allegationDispositionType = persistedAllegation.getAllegationDispositionType();
    this.allegationType = persistedAllegation.getAllegationType();
    this.dispositionDescription = persistedAllegation.getDispositionDescription();
    this.dispositionDate = DomainChef.uncookDateString(persistedAllegation.getDispositionDate());
    this.injuryHarmDetailIndicator =
        DomainChef.cookBoolean(persistedAllegation.getInjuryHarmDetailIndicator());
    this.nonProtectingParentCode = persistedAllegation.getNonProtectingParentCode();
    this.staffPersonAddedIndicator =
        DomainChef.cookBoolean(persistedAllegation.getStaffPersonAddedIndicator());
    this.victimClientId = persistedAllegation.getVictimClientId();
    this.perpetratorClientId = persistedAllegation.getPerpetratorClientId();
    this.referralId = persistedAllegation.getReferralId();
    this.countySpecificCode = persistedAllegation.getCountySpecificCode();
    this.zippyCreatedIndicator =
        DomainChef.cookBoolean(persistedAllegation.getZippyCreatedIndicator());
    this.placementFacilityType = persistedAllegation.getPlacementFacilityType();
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.data.persistence.PersistentObject#getPrimaryKey()
   */
  @Override
  public String getPrimaryKey() {
    return getId();
  }

  /**
   * @return the id
   */
  public String getId() {
    return id;
  }

  /**
   * @return the abuseEndDate
   */
  public Date getAbuseEndDate() {
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
  public Date getAbuseStartDate() {
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
  public Date getDispositionDate() {
    return dispositionDate;
  }

  /**
   * @return the injuryHarmDetailIndicator
   */
  public String getInjuryHarmDetailIndicator() {
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
  public String getStaffPersonAddedIndicator() {
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
  public String getZippyCreatedIndicator() {
    return zippyCreatedIndicator;
  }

  /**
   * @return the placementFacilityType
   */
  public Short getPlacementFacilityType() {
    return placementFacilityType;
  }

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
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    result = prime * result
        + ((injuryHarmDetailIndicator == null) ? 0 : injuryHarmDetailIndicator.hashCode());
    result = prime * result
        + ((nonProtectingParentCode == null) ? 0 : nonProtectingParentCode.hashCode());
    result = prime * result + ((perpetratorClientId == null) ? 0 : perpetratorClientId.hashCode());
    result =
        prime * result + ((placementFacilityType == null) ? 0 : placementFacilityType.hashCode());
    result = prime * result + ((referralId == null) ? 0 : referralId.hashCode());
    result = prime * result
        + ((staffPersonAddedIndicator == null) ? 0 : staffPersonAddedIndicator.hashCode());
    result = prime * result + ((victimClientId == null) ? 0 : victimClientId.hashCode());
    result =
        prime * result + ((zippyCreatedIndicator == null) ? 0 : zippyCreatedIndicator.hashCode());
    result = prime * result
        + ((super.getLastUpdatedId() == null) ? 0 : super.getLastUpdatedId().hashCode());
    result = prime * result
        + ((super.getLastUpdatedTime() == null) ? 0 : super.getLastUpdatedTime().hashCode());
    return result;
  }

  @Override
  public final boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (!(obj instanceof Allegation)) {
      return false;
    }
    Allegation other = (Allegation) obj;
    if (abuseEndDate == null) {
      if (other.abuseEndDate != null)
        return false;
    } else if (!abuseEndDate.equals(other.abuseEndDate))
      return false;
    if (abuseFrequency == null) {
      if (other.abuseFrequency != null)
        return false;
    } else if (!abuseFrequency.equals(other.abuseFrequency))
      return false;
    if (abuseFrequencyPeriodCode == null) {
      if (other.abuseFrequencyPeriodCode != null)
        return false;
    } else if (!abuseFrequencyPeriodCode.equals(other.abuseFrequencyPeriodCode))
      return false;
    if (abuseLocationDescription == null) {
      if (other.abuseLocationDescription != null)
        return false;
    } else if (!abuseLocationDescription.equals(other.abuseLocationDescription))
      return false;
    if (abuseStartDate == null) {
      if (other.abuseStartDate != null)
        return false;
    } else if (!abuseStartDate.equals(other.abuseStartDate))
      return false;
    if (allegationDispositionType == null) {
      if (other.allegationDispositionType != null)
        return false;
    } else if (!allegationDispositionType.equals(other.allegationDispositionType))
      return false;
    if (allegationType == null) {
      if (other.allegationType != null)
        return false;
    } else if (!allegationType.equals(other.allegationType))
      return false;
    if (countySpecificCode == null) {
      if (other.countySpecificCode != null)
        return false;
    } else if (!countySpecificCode.equals(other.countySpecificCode))
      return false;
    if (dispositionDate == null) {
      if (other.dispositionDate != null)
        return false;
    } else if (!dispositionDate.equals(other.dispositionDate))
      return false;
    if (dispositionDescription == null) {
      if (other.dispositionDescription != null)
        return false;
    } else if (!dispositionDescription.equals(other.dispositionDescription))
      return false;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    if (injuryHarmDetailIndicator == null) {
      if (other.injuryHarmDetailIndicator != null)
        return false;
    } else if (!injuryHarmDetailIndicator.equals(other.injuryHarmDetailIndicator))
      return false;
    if (nonProtectingParentCode == null) {
      if (other.nonProtectingParentCode != null)
        return false;
    } else if (!nonProtectingParentCode.equals(other.nonProtectingParentCode))
      return false;
    if (perpetratorClientId == null) {
      if (other.perpetratorClientId != null)
        return false;
    } else if (!perpetratorClientId.equals(other.perpetratorClientId))
      return false;
    if (placementFacilityType == null) {
      if (other.placementFacilityType != null)
        return false;
    } else if (!placementFacilityType.equals(other.placementFacilityType))
      return false;
    if (referralId == null) {
      if (other.referralId != null)
        return false;
    } else if (!referralId.equals(other.referralId))
      return false;
    if (staffPersonAddedIndicator == null) {
      if (other.staffPersonAddedIndicator != null)
        return false;
    } else if (!staffPersonAddedIndicator.equals(other.staffPersonAddedIndicator))
      return false;
    if (victimClientId == null) {
      if (other.victimClientId != null)
        return false;
    } else if (!victimClientId.equals(other.victimClientId))
      return false;
    if (zippyCreatedIndicator == null) {
      if (other.zippyCreatedIndicator != null)
        return false;
    } else if (!zippyCreatedIndicator.equals(other.zippyCreatedIndicator))
      return false;
    if (super.getLastUpdatedId() == null) {
      if (other.getLastUpdatedId() != null) {
        return false;
      }
    } else if (!super.getLastUpdatedId().equals(other.getLastUpdatedId())) {
      return false;
    }
    if (super.getLastUpdatedTime() == null) {
      if (other.getLastUpdatedTime() != null) {
        return false;
      }
    } else if (!super.getLastUpdatedTime().equals(other.getLastUpdatedTime())) {
      return false;
    }

    return true;
  }

}
