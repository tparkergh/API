package gov.ca.cwds.data.persistence.cms;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.NamedNativeQueries;
import org.hibernate.annotations.NamedNativeQuery;
import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import gov.ca.cwds.data.persistence.PersistentObject;

/**
 * {@link PersistentObject} representing a Attorney
 * 
 * @author CWDS API Team
 */
@NamedQueries({
    @NamedQuery(name = "gov.ca.cwds.data.persistence.cms.Attorney.findAll",
        query = "FROM Attorney"),
    @NamedQuery(name = "gov.ca.cwds.data.persistence.cms.Attorney.findAllUpdatedAfter",
        query = "FROM Attorney WHERE lastUpdatedTime > :after")})
@NamedNativeQueries({@NamedNativeQuery(
    name = "gov.ca.cwds.data.persistence.cms.Attorney.findAllByBucket",
    query = "select z.IDENTIFIER, z.CITY_NM, z.CWATRY_IND, z.FAX_NO, z.FIRST_NM, "
        + "z.GVR_ENTC, z.LANG_TPC, z.LAST_NM, z.MSG_EXT_NO, z.MSG_TEL_NO, "
        + "z.MID_INI_NM, z.NMPRFX_DSC, z.POSTIL_DSC, z.PRM_EXT_NO, z.PRM_TEL_NO, "
        + "z.STATE_C, z.STREET_NM, z.STREET_NO, z.SUFX_TLDSC, z.ZIP_NO, z.LST_UPD_ID, "
        + "z.LST_UPD_TS, z.BUSNSS_NM, z.ZIP_SFX_NO, z.END_DT, z.ARCASS_IND, z.EMAIL_ADDR "
        + "from ( select mod(y.rn, CAST(:total_buckets AS INTEGER)) + 1 as bucket, y.* "
        + "from ( select row_number() over (order by 1) as rn, x.* "
        + "from ( select c.* from {h-schema}ATTRNY_T c "
        + ") x ) y ) z where z.bucket = :bucket_num for read only",
    resultClass = Attorney.class)})
@Entity
@Table(name = "ATTRNY_T")
@JsonPropertyOrder(alphabetic = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Attorney extends BaseAttorney {

  private static final Logger LOGGER = LoggerFactory.getLogger(Attorney.class);

  /**
   * Default constructor
   * 
   * Required for Hibernate
   */
  public Attorney() {
    super();
  }

  /**
   * Construct from all fields.
   * 
   * @param archiveAssociationIndicator The archiveAssociationIndicator
   * @param businessName The businessName
   * @param cityName The cityName
   * @param cwsAttorneyIndicator The cwsAttorneyIndicator
   * @param emailAddress The cwsAttorneyIndicator
   * @param endDate The endDate
   * @param faxNumber The faxNumber
   * @param firstName The firstName
   * @param governmentEntityType The governmentEntityType
   * @param id The id
   * @param languageType The languageType
   * @param lastName The lastName
   * @param messagePhoneExtensionNumber The messagePhoneExtensionNumber
   * @param messagePhoneNumber The messagePhoneNumber
   * @param middleInitialName The middleInitialName
   * @param namePrefixDescription The namePrefixDescription
   * @param positionTitleDescription The positionTitleDescription
   * @param primaryPhoneExtensionNumber The primaryPhoneExtensionNumber
   * @param primaryPhoneNumber The primaryPhoneNumber
   * @param stateCodeType The stateCodeType
   * @param streetName The streetName
   * @param streetNumber The streetNumber
   * @param suffixTitleDescription The suffixTitleDescription
   * @param zipNumber The zipNumber
   * @param zipSuffixNumber The zipSuffixNumber
   */
  public Attorney(String archiveAssociationIndicator, String businessName, String cityName,
      String cwsAttorneyIndicator, String emailAddress, Date endDate, BigDecimal faxNumber,
      String firstName, Short governmentEntityType, String id, Short languageType, String lastName,
      Integer messagePhoneExtensionNumber, BigDecimal messagePhoneNumber, String middleInitialName,
      String namePrefixDescription, String positionTitleDescription,
      Integer primaryPhoneExtensionNumber, BigDecimal primaryPhoneNumber, Short stateCodeType,
      String streetName, String streetNumber, String suffixTitleDescription, Integer zipNumber,
      Short zipSuffixNumber) {
    super();
    this.archiveAssociationIndicator = archiveAssociationIndicator;
    this.businessName = businessName;
    this.cityName = cityName;
    this.cwsAttorneyIndicator = cwsAttorneyIndicator;
    this.emailAddress = emailAddress;
    this.endDate = endDate;
    this.faxNumber = faxNumber;
    this.firstName = firstName;
    this.governmentEntityType = governmentEntityType;
    this.id = id;
    this.languageType = languageType;
    this.lastName = lastName;
    this.messagePhoneExtensionNumber = messagePhoneExtensionNumber;
    this.messagePhoneNumber = messagePhoneNumber;
    this.middleInitialName = middleInitialName;
    this.namePrefixDescription = namePrefixDescription;
    this.positionTitleDescription = positionTitleDescription;
    this.primaryPhoneExtensionNumber = primaryPhoneExtensionNumber;
    this.primaryPhoneNumber = primaryPhoneNumber;
    this.stateCodeType = stateCodeType;
    this.streetName = streetName;
    this.streetNumber = streetNumber;
    this.suffixTitleDescription = suffixTitleDescription;
    this.zipNumber = zipNumber;
    this.zipSuffixNumber = zipSuffixNumber;
  }

}
