package gov.ca.cwds.data.persistence.cms;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.NamedNativeQueries;
import org.hibernate.annotations.NamedNativeQuery;
import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import gov.ca.cwds.data.persistence.PersistentObject;

/**
 * {@link PersistentObject} representing a CollateralIndividual
 * 
 * @author CWDS API Team
 */
@NamedQueries({
    @NamedQuery(name = "gov.ca.cwds.data.persistence.cms.CollateralIndividual.findAll",
        query = "FROM CollateralIndividual WHERE IDENTIFIER IN (SELECT collateralIndividualId from ClientCollateral "
            + "WHERE activeIndicator = 'Y' AND clientId IN "
            + "(SELECT id FROM Client WHERE sensitivityIndicator = 'N' AND soc158SealedClientIndicator = 'N'))"),
    @NamedQuery(name = "gov.ca.cwds.data.persistence.cms.CollateralIndividual.findAllUpdatedAfter",
        query = "FROM CollateralIndividual WHERE lastUpdatedTime > :after AND IDENTIFIER IN ("
            + "SELECT collateralIndividualId from ClientCollateral "
            + "WHERE activeIndicator = 'Y' AND clientId IN "
            + "(SELECT id FROM Client WHERE sensitivityIndicator = 'N' AND soc158SealedClientIndicator = 'N'))")})
@NamedNativeQueries({@NamedNativeQuery(
    name = "gov.ca.cwds.data.persistence.cms.CollateralIndividual.findPartitionedBuckets",
    query = "select z.IDENTIFIER, z.BADGE_NO, z.CITY_NM, z.EMPLYR_NM, z.FAX_NO, "
        + "z.FIRST_NM, z.FRG_ADRT_B, z.LAST_NM, z.MID_INI_NM, z.NMPRFX_DSC, "
        + "z.PRM_TEL_NO, z.PRM_EXT_NO, z.STATE_C, z.STREET_NM, z.STREET_NO, "
        + "z.SUFX_TLDSC, z.ZIP_NO, z.LST_UPD_ID, z.LST_UPD_TS, z.ZIP_SFX_NO, "
        + "z.COMNT_DSC, z.GENDER_CD, z.BIRTH_DT, z.MRTL_STC, z.EMAIL_ADDR, "
        + "z.ESTBLSH_CD, z.ESTBLSH_ID, z.RESOST_IND "
        + "from ( select mod(y.rn, CAST(:total_buckets AS INTEGER)) + 1 as bucket, y.* "
        + "from ( select row_number() over (order by 1) as rn, x.* "
        + "from ( select c.* from {h-schema}COLTRL_T c "
        + "WHERE c.IDENTIFIER >= :min_id and c.IDENTIFIER < :max_id "
        + ") x ) y ) z where z.bucket = :bucket_num for read only",
    resultClass = CollateralIndividual.class)})
@Entity
@Table(name = "COLTRL_T")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "ESTBLSH_CD", discriminatorType = DiscriminatorType.STRING, length = 1)
@JsonPropertyOrder(alphabetic = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CollateralIndividual extends BaseCollateralIndividual {

  /**
   * Default serialization.
   */
  private static final long serialVersionUID = 1L;

  /**
   * Default constructor
   * 
   * Required for Hibernate
   */
  public CollateralIndividual() {
    super();
  }

  /**
   * Constructor
   * 
   * @param badgeNumber The unique badge number
   * @param birthDate Date of birth
   * @param cityName The name of the city
   * @param commentDescription A brief description of any unusual circumstances
   * @param emailAddress The e-mail address
   * @param employerName The employer name
   * @param establishedForCode Defines each type of recipient entity
   * @param faxNumber The FAX number
   * @param firstName The first name
   * @param foreignAddressIndicatorVariable Indicates occurrences of foreign addresses
   * @param genderCode Indicates the gender
   * @param id The unique identifier
   * @param lastName The last name
   * @param maritalStatus The Martial Status type
   * @param middleInitialName The middle initial
   * @param namePrefixDescription The salutation form
   * @param primaryExtensionNumber The primary phone extension number
   * @param primaryPhoneNo The primary phone number
   * @param residedOutOfStateIndicator Indicates if reside out of state
   * @param stateCode The State Code Type
   * @param streetName The street name
   * @param streetNumber The street number
   * @param suffixTitleDescription The suffix name
   * @param zipNumber The zip code
   * @param zipSuffixNumber The zip suffix number
   */
  public CollateralIndividual(String badgeNumber, Date birthDate, String cityName,
      String commentDescription, String emailAddress, String employerName,
      String establishedForCode, BigDecimal faxNumber, String firstName,
      String foreignAddressIndicatorVariable, String genderCode, String id, String lastName,
      Short maritalStatus, String middleInitialName, String namePrefixDescription,
      Integer primaryExtensionNumber, BigInteger primaryPhoneNo, String residedOutOfStateIndicator,
      Short stateCode, String streetName, String streetNumber, String suffixTitleDescription,
      Integer zipNumber, Short zipSuffixNumber) {
    super();
    this.badgeNumber = badgeNumber;
    this.birthDate = birthDate;
    this.cityName = cityName;
    this.commentDescription = commentDescription;
    this.emailAddress = emailAddress;
    this.employerName = employerName;
    this.establishedForCode = establishedForCode;
    this.faxNumber = faxNumber;
    this.firstName = firstName;
    this.foreignAddressIndicatorVariable = foreignAddressIndicatorVariable;
    this.genderCode = genderCode;
    this.id = id;
    this.lastName = lastName;
    this.maritalStatusType = maritalStatus;
    this.middleInitialName = middleInitialName;
    this.namePrefixDescription = namePrefixDescription;
    this.primaryExtensionNumber = primaryExtensionNumber;
    this.primaryPhoneNo = primaryPhoneNo;
    this.residedOutOfStateIndicator = residedOutOfStateIndicator;
    this.stateCode = stateCode;
    this.streetName = streetName;
    this.streetNumber = streetNumber;
    this.suffixTitleDescription = suffixTitleDescription;
    this.zipNumber = zipNumber;
    this.zipSuffixNumber = zipSuffixNumber;
  }

  @Override
  public final int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this, false);
  }

  @Override
  public final boolean equals(Object obj) {
    return EqualsBuilder.reflectionEquals(this, obj, false);
  }

}
