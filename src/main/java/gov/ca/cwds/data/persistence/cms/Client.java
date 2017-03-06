package gov.ca.cwds.data.persistence.cms;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.PersistenceException;
import javax.persistence.Table;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import gov.ca.cwds.data.persistence.PersistentObject;
import gov.ca.cwds.data.std.ApiAddressAware;
import gov.ca.cwds.data.std.ApiMultipleAddressesAware;
import gov.ca.cwds.rest.api.ApiException;
import gov.ca.cwds.rest.api.domain.DomainChef;

/**
 * {@link PersistentObject} representing a Client.
 * 
 * @author CWDS API Team
 */
@NamedQueries({@NamedQuery(name = "gov.ca.cwds.data.persistence.cms.Client.findAll",
    query = "FROM Client WHERE sensitivityIndicator = 'N' AND soc158SealedClientIndicator = 'N'")})
@Entity
@Table(name = "CLIENT_T")
@JsonPropertyOrder(alphabetic = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public final class Client extends BaseClient implements ApiMultipleAddressesAware {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  @OneToMany(fetch = FetchType.EAGER)
  @JoinColumns({@JoinColumn(name = "FKCLIENT_T", referencedColumnName = "IDENTIFIER")})
  @OrderBy("EFF_STRTDT")
  protected Set<ClientAddress> clientAddresses = new LinkedHashSet<>();

  /**
   * Default constructor
   * 
   * Required for Hibernate
   */
  public Client() {
    super();
  }

  /**
   * Construct from all fields.
   * 
   * @param adjudicatedDelinquentIndicator The adjudicatedDelinquentIndicator
   * @param adoptionStatusCode The adoptionStatusCode
   * @param alienRegistrationNumber The alienRegistrationNumber
   * @param birthCity The birthCity
   * @param birthCountryCodeType The birthCountryCodeType
   * @param birthDate The birthDate
   * @param birthFacilityName The birthFacilityName
   * @param birthStateCodeType The birthStateCodeType
   * @param birthplaceVerifiedIndicator The birthplaceVerifiedIndicator
   * @param childClientIndicatorVar The childClientIndicatorVar
   * @param clientIndexNumber The clientIndexNumber
   * @param commentDescription The commentDescription
   * @param commonFirstName The commonFirstName
   * @param commonLastName The commonLastName
   * @param commonMiddleName The commonMiddleName
   * @param confidentialityActionDate The confidentialityActionDate
   * @param confidentialityInEffectIndicator The confidentialityInEffectIndicator
   * @param creationDate The creationDate
   * @param currCaChildrenServIndicator The currCaChildrenServIndicator
   * @param currentlyOtherDescription The currentlyOtherDescription
   * @param currentlyRegionalCenterIndicator The currentlyRegionalCenterIndicator
   * @param deathDate The deathDate
   * @param deathDateVerifiedIndicator The deathDateVerifiedIndicator
   * @param deathPlace The deathPlace
   * @param deathReasonText The deathReasonText
   * @param driverLicenseNumber The driverLicenseNumber
   * @param driverLicenseStateCodeType The driverLicenseStateCodeType
   * @param emailAddress The emailAddress
   * @param estimatedDobCode The estimatedDobCode
   * @param ethUnableToDetReasonCode The ethUnableToDetReasonCode
   * @param fatherParentalRightTermDate The fatherParentalRightTermDate
   * @param genderCode The genderCode
   * @param healthSummaryText The healthSummaryText
   * @param hispUnableToDetReasonCode The hispUnableToDetReasonCode
   * @param hispanicOriginCode The hispanicOriginCode
   * @param id The id
   * @param immigrationCountryCodeType The immigrationCountryCodeType
   * @param immigrationStatusType The immigrationStatusType
   * @param incapacitatedParentCode The incapacitatedParentCode
   * @param individualHealthCarePlanIndicator The individualHealthCarePlanIndicator
   * @param limitationOnScpHealthIndicator The limitationOnScpHealthIndicator
   * @param literateCode The literateCode
   * @param maritalCohabitatnHstryIndicatorVar The maritalCohabitatnHstryIndicatorVar
   * @param maritalStatusType The maritalStatusType
   * @param militaryStatusCode The militaryStatusCode
   * @param motherParentalRightTermDate The motherParentalRightTermDate
   * @param namePrefixDescription The namePrefixDescription
   * @param nameType The nameType
   * @param outstandingWarrantIndicator The outstandingWarrantIndicator
   * @param prevCaChildrenServIndicator The prevCaChildrenServIndicator
   * @param prevOtherDescription The prevOtherDescription
   * @param prevRegionalCenterIndicator The prevRegionalCenterIndicator
   * @param primaryEthnicityType The primaryEthnicityType
   * @param primaryLanguageType The primaryLanguageType
   * @param religionType The religionType
   * @param secondaryLanguageType The secondaryLanguageType
   * @param sensitiveHlthInfoOnFileIndicator The sensitiveHlthInfoOnFileIndicator
   * @param sensitivityIndicator The sensitivityIndicator
   * @param soc158PlacementCode The soc158PlacementCode
   * @param soc158SealedClientIndicator The soc158SealedClientIndicator
   * @param socialSecurityNumChangedCode The socialSecurityNumChangedCode
   * @param socialSecurityNumber The socialSecurityNumber
   * @param suffixTitleDescription The suffixTitleDescription
   * @param tribalAncestryClientIndicatorVar The tribalAncestryClientIndicatorVar
   * @param tribalMembrshpVerifctnIndicatorVar The tribalMembrshpVerifctnIndicatorVar
   * @param unemployedParentCode The unemployedParentCode
   * @param zippyCreatedIndicator The zippyCreatedIndicator
   */
  public Client(String adjudicatedDelinquentIndicator, String adoptionStatusCode,
      String alienRegistrationNumber, String birthCity, Short birthCountryCodeType, Date birthDate,
      String birthFacilityName, Short birthStateCodeType, String birthplaceVerifiedIndicator,
      String childClientIndicatorVar, String clientIndexNumber, String commentDescription,
      String commonFirstName, String commonLastName, String commonMiddleName,
      Date confidentialityActionDate, String confidentialityInEffectIndicator, Date creationDate,
      String currCaChildrenServIndicator, String currentlyOtherDescription,
      String currentlyRegionalCenterIndicator, Date deathDate, String deathDateVerifiedIndicator,
      String deathPlace, String deathReasonText, String driverLicenseNumber,
      Short driverLicenseStateCodeType, String emailAddress, String estimatedDobCode,
      String ethUnableToDetReasonCode, Date fatherParentalRightTermDate, String genderCode,
      String healthSummaryText, String hispUnableToDetReasonCode, String hispanicOriginCode,
      String id, Short immigrationCountryCodeType, Short immigrationStatusType,
      String incapacitatedParentCode, String individualHealthCarePlanIndicator,
      String limitationOnScpHealthIndicator, String literateCode,
      String maritalCohabitatnHstryIndicatorVar, Short maritalStatusType, String militaryStatusCode,
      Date motherParentalRightTermDate, String namePrefixDescription, Short nameType,
      String outstandingWarrantIndicator, String prevCaChildrenServIndicator,
      String prevOtherDescription, String prevRegionalCenterIndicator, Short primaryEthnicityType,
      Short primaryLanguageType, Short religionType, Short secondaryLanguageType,
      String sensitiveHlthInfoOnFileIndicator, String sensitivityIndicator,
      String soc158PlacementCode, String soc158SealedClientIndicator,
      String socialSecurityNumChangedCode, String socialSecurityNumber,
      String suffixTitleDescription, String tribalAncestryClientIndicatorVar,
      String tribalMembrshpVerifctnIndicatorVar, String unemployedParentCode,
      String zippyCreatedIndicator) {
    super();
    this.adjudicatedDelinquentIndicator = adjudicatedDelinquentIndicator;
    this.adoptionStatusCode = adoptionStatusCode;
    this.alienRegistrationNumber = alienRegistrationNumber;
    this.birthCity = birthCity;
    this.birthCountryCodeType = birthCountryCodeType;
    this.birthDate = birthDate;
    this.birthFacilityName = birthFacilityName;
    this.birthStateCodeType = birthStateCodeType;
    this.birthplaceVerifiedIndicator = birthplaceVerifiedIndicator;
    this.childClientIndicatorVar = childClientIndicatorVar;
    this.clientIndexNumber = clientIndexNumber;
    this.commentDescription = commentDescription;
    this.commonFirstName = commonFirstName;
    this.commonLastName = commonLastName;
    this.commonMiddleName = commonMiddleName;
    this.confidentialityActionDate = confidentialityActionDate;
    this.confidentialityInEffectIndicator = confidentialityInEffectIndicator;
    this.creationDate = creationDate;
    this.currCaChildrenServIndicator = currCaChildrenServIndicator;
    this.currentlyOtherDescription = currentlyOtherDescription;
    this.currentlyRegionalCenterIndicator = currentlyRegionalCenterIndicator;
    this.deathDate = deathDate;
    this.deathDateVerifiedIndicator = deathDateVerifiedIndicator;
    this.deathPlace = deathPlace;
    this.deathReasonText = deathReasonText;
    this.driverLicenseNumber = driverLicenseNumber;
    this.driverLicenseStateCodeType = driverLicenseStateCodeType;
    this.emailAddress = emailAddress;
    this.estimatedDobCode = estimatedDobCode;
    this.ethUnableToDetReasonCode = ethUnableToDetReasonCode;
    this.fatherParentalRightTermDate = fatherParentalRightTermDate;
    this.genderCode = genderCode;
    this.healthSummaryText = healthSummaryText;
    this.hispUnableToDetReasonCode = hispUnableToDetReasonCode;
    this.hispanicOriginCode = hispanicOriginCode;
    this.id = id;
    this.immigrationCountryCodeType = immigrationCountryCodeType;
    this.immigrationStatusType = immigrationStatusType;
    this.incapacitatedParentCode = incapacitatedParentCode;
    this.individualHealthCarePlanIndicator = individualHealthCarePlanIndicator;
    this.limitationOnScpHealthIndicator = limitationOnScpHealthIndicator;
    this.literateCode = literateCode;
    this.maritalCohabitatnHstryIndicatorVar = maritalCohabitatnHstryIndicatorVar;
    this.maritalStatusType = maritalStatusType;
    this.militaryStatusCode = militaryStatusCode;
    this.motherParentalRightTermDate = motherParentalRightTermDate;
    this.namePrefixDescription = namePrefixDescription;
    this.nameType = nameType;
    this.outstandingWarrantIndicator = outstandingWarrantIndicator;
    this.prevCaChildrenServIndicator = prevCaChildrenServIndicator;
    this.prevOtherDescription = prevOtherDescription;
    this.prevRegionalCenterIndicator = prevRegionalCenterIndicator;
    this.primaryEthnicityType = primaryEthnicityType;
    this.primaryLanguageType = primaryLanguageType;
    this.religionType = religionType;
    this.secondaryLanguageType = secondaryLanguageType;
    this.sensitiveHlthInfoOnFileIndicator = sensitiveHlthInfoOnFileIndicator;
    this.sensitivityIndicator = sensitivityIndicator;
    this.soc158PlacementCode = soc158PlacementCode;
    this.soc158SealedClientIndicator = soc158SealedClientIndicator;
    this.socialSecurityNumChangedCode = socialSecurityNumChangedCode;
    this.socialSecurityNumber = socialSecurityNumber;
    this.suffixTitleDescription = suffixTitleDescription;
    this.tribalAncestryClientIndicatorVar = tribalAncestryClientIndicatorVar;
    this.tribalMembrshpVerifctnIndicatorVar = tribalMembrshpVerifctnIndicatorVar;
    this.unemployedParentCode = unemployedParentCode;
    this.zippyCreatedIndicator = zippyCreatedIndicator;
  }

  /**
   * Constructor. Construct from counterpart domain class.
   * 
   * @param client The domain object to construct this object from
   * @param lastUpdatedId the id of the last person to update this object
   */
  public Client(gov.ca.cwds.rest.api.domain.cms.Client client, String lastUpdatedId) {
    super(lastUpdatedId);
    try {
      this.adjudicatedDelinquentIndicator =
          DomainChef.cookBoolean(client.getAdjudicatedDelinquentIndicator());
      this.adoptionStatusCode = client.getAdoptionStatusCode();
      this.alienRegistrationNumber = client.getAlienRegistrationNumber();
      this.birthCity = client.getBirthCity();
      this.birthCountryCodeType = client.getBirthCountryCodeType();
      this.birthDate = DomainChef.uncookDateString(client.getBirthDate());
      this.birthFacilityName = client.getBirthFacilityName();
      this.birthStateCodeType = client.getBirthStateCodeType();
      this.birthplaceVerifiedIndicator =
          DomainChef.cookBoolean(client.getBirthplaceVerifiedIndicator());
      this.childClientIndicatorVar = DomainChef.cookBoolean(client.getChildClientIndicatorVar());
      this.clientIndexNumber = client.getClientIndexNumber();
      this.commentDescription = client.getCommentDescription();
      this.commonFirstName = client.getCommonFirstName();
      this.commonLastName = client.getCommonLastName();
      this.commonMiddleName = client.getCommonMiddleName();
      this.confidentialityActionDate =
          DomainChef.uncookDateString(client.getConfidentialityActionDate());
      this.confidentialityInEffectIndicator =
          DomainChef.cookBoolean(client.getConfidentialityInEffectIndicator());
      this.creationDate = DomainChef.uncookDateString(client.getCreationDate());
      this.currCaChildrenServIndicator =
          DomainChef.cookBoolean(client.getCurrCaChildrenServIndicator());
      this.currentlyOtherDescription = client.getCurrentlyOtherDescription();
      this.currentlyRegionalCenterIndicator =
          DomainChef.cookBoolean(client.getCurrentlyRegionalCenterIndicator());
      this.deathDate = DomainChef.uncookDateString(client.getDeathDate());
      this.deathDateVerifiedIndicator =
          DomainChef.cookBoolean(client.getDeathDateVerifiedIndicator());
      this.deathPlace = client.getDeathPlace();
      this.deathReasonText = client.getDeathReasonText();
      this.driverLicenseNumber = client.getDriverLicenseNumber();
      this.driverLicenseStateCodeType = client.getDriverLicenseStateCodeType();
      this.emailAddress = client.getEmailAddress();
      this.estimatedDobCode = client.getEstimatedDobCode();
      this.ethUnableToDetReasonCode = client.getEthUnableToDetReasonCode();
      this.fatherParentalRightTermDate =
          DomainChef.uncookDateString(client.getFatherParentalRightTermDate());
      this.genderCode = client.getGenderCode();
      this.healthSummaryText = client.getHealthSummaryText();
      this.hispUnableToDetReasonCode = client.getHispUnableToDetReasonCode();
      this.hispanicOriginCode = client.getHispanicOriginCode();
      this.id = client.getId();
      this.immigrationCountryCodeType = client.getImmigrationCountryCodeType();
      this.immigrationStatusType = client.getImmigrationStatusType();
      this.incapacitatedParentCode = client.getIncapacitatedParentCode();
      this.individualHealthCarePlanIndicator =
          DomainChef.cookBoolean(client.getIndividualHealthCarePlanIndicator());
      this.limitationOnScpHealthIndicator =
          DomainChef.cookBoolean(client.getLimitationOnScpHealthIndicator());
      this.literateCode = client.getLiterateCode();
      this.maritalCohabitatnHstryIndicatorVar =
          DomainChef.cookBoolean(client.getMaritalCohabitatnHstryIndicatorVar());
      this.maritalStatusType = client.getMaritalStatusType();
      this.militaryStatusCode = client.getMilitaryStatusCode();
      this.motherParentalRightTermDate =
          DomainChef.uncookDateString(client.getMotherParentalRightTermDate());
      this.namePrefixDescription = client.getNamePrefixDescription();
      this.nameType = client.getNameType();
      this.outstandingWarrantIndicator =
          DomainChef.cookBoolean(client.getOutstandingWarrantIndicator());
      this.prevCaChildrenServIndicator =
          DomainChef.cookBoolean(client.getPrevCaChildrenServIndicator());
      this.prevOtherDescription = client.getPrevOtherDescription();
      this.prevRegionalCenterIndicator =
          DomainChef.cookBoolean(client.getPrevRegionalCenterIndicator());
      this.primaryEthnicityType = client.getPrimaryEthnicityType();
      this.primaryLanguageType = client.getPrimaryLanguageType();
      this.religionType = client.getReligionType();
      this.secondaryLanguageType = client.getSecondaryLanguageType();
      this.sensitiveHlthInfoOnFileIndicator =
          DomainChef.cookBoolean(client.getSensitiveHlthInfoOnFileIndicator());
      this.sensitivityIndicator = client.getSensitivityIndicator();
      this.soc158PlacementCode = client.getSoc158PlacementCode();
      this.soc158SealedClientIndicator =
          DomainChef.cookBoolean(client.getSoc158SealedClientIndicator());
      this.socialSecurityNumChangedCode = client.getSocialSecurityNumChangedCode();
      this.socialSecurityNumber = client.getSocialSecurityNumber();
      this.suffixTitleDescription = client.getSuffixTitleDescription();
      this.tribalAncestryClientIndicatorVar =
          DomainChef.cookBoolean(client.getTribalAncestryClientIndicatorVar());
      this.tribalMembrshpVerifctnIndicatorVar =
          DomainChef.cookBoolean(client.getTribalMembrshpVerifctnIndicatorVar());
      this.unemployedParentCode = client.getUnemployedParentCode();
      this.zippyCreatedIndicator = DomainChef.cookBoolean(client.getZippyCreatedIndicator());

    } catch (ApiException e) {
      throw new PersistenceException(e);
    }
  }

  @Override
  public String toString() {
    return "Client [adjudicatedDelinquentIndicator=" + adjudicatedDelinquentIndicator
        + ", adoptionStatusCode=" + adoptionStatusCode + ", alienRegistrationNumber="
        + alienRegistrationNumber + ", birthCity=" + birthCity + ", birthCountryCodeType="
        + birthCountryCodeType + ", birthDate=" + birthDate + ", birthFacilityName="
        + birthFacilityName + ", birthStateCodeType=" + birthStateCodeType
        + ", birthplaceVerifiedIndicator=" + birthplaceVerifiedIndicator
        + ", childClientIndicatorVar=" + childClientIndicatorVar + ", clientIndexNumber="
        + clientIndexNumber + ", commentDescription=" + commentDescription + ", commonFirstName="
        + commonFirstName + ", commonLastName=" + commonLastName + ", commonMiddleName="
        + commonMiddleName + ", confidentialityActionDate=" + confidentialityActionDate
        + ", confidentialityInEffectIndicator=" + confidentialityInEffectIndicator
        + ", creationDate=" + creationDate + ", currCaChildrenServIndicator="
        + currCaChildrenServIndicator + ", currentlyOtherDescription=" + currentlyOtherDescription
        + ", currentlyRegionalCenterIndicator=" + currentlyRegionalCenterIndicator + ", deathDate="
        + deathDate + ", deathDateVerifiedIndicator=" + deathDateVerifiedIndicator + ", deathPlace="
        + deathPlace + ", deathReasonText=" + deathReasonText + ", driverLicenseNumber="
        + driverLicenseNumber + ", driverLicenseStateCodeType=" + driverLicenseStateCodeType
        + ", emailAddress=" + emailAddress + ", estimatedDobCode=" + estimatedDobCode
        + ", ethUnableToDetReasonCode=" + ethUnableToDetReasonCode
        + ", fatherParentalRightTermDate=" + fatherParentalRightTermDate + ", genderCode="
        + genderCode + ", healthSummaryText=" + healthSummaryText + ", hispUnableToDetReasonCode="
        + hispUnableToDetReasonCode + ", hispanicOriginCode=" + hispanicOriginCode + ", id=" + id
        + ", immigrationCountryCodeType=" + immigrationCountryCodeType + ", immigrationStatusType="
        + immigrationStatusType + ", incapacitatedParentCode=" + incapacitatedParentCode
        + ", individualHealthCarePlanIndicator=" + individualHealthCarePlanIndicator
        + ", limitationOnScpHealthIndicator=" + limitationOnScpHealthIndicator + ", literateCode="
        + literateCode + ", maritalCohabitatnHstryIndicatorVar="
        + maritalCohabitatnHstryIndicatorVar + ", maritalStatusType=" + maritalStatusType
        + ", militaryStatusCode=" + militaryStatusCode + ", motherParentalRightTermDate="
        + motherParentalRightTermDate + ", namePrefixDescription=" + namePrefixDescription
        + ", nameType=" + nameType + ", outstandingWarrantIndicator=" + outstandingWarrantIndicator
        + ", prevCaChildrenServIndicator=" + prevCaChildrenServIndicator + ", prevOtherDescription="
        + prevOtherDescription + ", prevRegionalCenterIndicator=" + prevRegionalCenterIndicator
        + ", primaryEthnicityType=" + primaryEthnicityType + ", primaryLanguageType="
        + primaryLanguageType + ", religionType=" + religionType + ", secondaryLanguageType="
        + secondaryLanguageType + ", sensitiveHlthInfoOnFileIndicator="
        + sensitiveHlthInfoOnFileIndicator + ", sensitivityIndicator=" + sensitivityIndicator
        + ", soc158PlacementCode=" + soc158PlacementCode + ", soc158SealedClientIndicator="
        + soc158SealedClientIndicator + ", socialSecurityNumChangedCode="
        + socialSecurityNumChangedCode + ", socialSecurityNumber=" + socialSecurityNumber
        + ", suffixTitleDescription=" + suffixTitleDescription
        + ", tribalAncestryClientIndicatorVar=" + tribalAncestryClientIndicatorVar
        + ", tribalMembrshpVerifctnIndicatorVar=" + tribalMembrshpVerifctnIndicatorVar
        + ", unemployedParentCode=" + unemployedParentCode + ", zippyCreatedIndicator="
        + zippyCreatedIndicator + "]";
  }

  /**
   * Get client address linkages.
   * 
   * @return client addresses
   */
  public Set<ClientAddress> getClientAddresses() {
    return clientAddresses;
  }

  /**
   * Set the client address linkages.
   * 
   * @param clientAddresses Set of client address linkages
   */
  public void setClientAddresses(Set<ClientAddress> clientAddresses) {
    this.clientAddresses = clientAddresses;
  }

  /**
   * Add a client address linkage.
   * 
   * @param clientAddress client address
   */
  public void addClientAddress(ClientAddress clientAddress) {
    this.clientAddresses.add(clientAddress);
  }

  // =============================
  // ApiMultipleAddressesAware:
  // =============================

  @Override
  public ApiAddressAware[] getAddresses() {
    List<ApiAddressAware> ret = new ArrayList<>();

    if (this.clientAddresses != null && !this.clientAddresses.isEmpty()) {
      for (final ClientAddress ca : this.clientAddresses) {
        // if (ca.getAddress() != null) {
        // ret.add(ca.getAddress());
        // }
      }
    }

    return ret.toArray(new ApiAddressAware[0]);
  }

}
