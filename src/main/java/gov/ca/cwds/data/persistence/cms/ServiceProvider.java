package gov.ca.cwds.data.persistence.cms;

import java.math.BigDecimal;

import javax.persistence.Entity;
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
 * {@link PersistentObject} representing a ServiceProvider
 * 
 * @author CWDS API Team
 */
@NamedQueries({
    @NamedQuery(name = "gov.ca.cwds.data.persistence.cms.ServiceProvider.findAll",
        query = "FROM ServiceProvider"),
    @NamedQuery(name = "gov.ca.cwds.data.persistence.cms.ServiceProvider.findAllUpdatedAfter",
        query = "FROM ServiceProvider WHERE lastUpdatedTime > :after")})
@NamedNativeQueries({@NamedNativeQuery(
    name = "gov.ca.cwds.data.persistence.cms.ServiceProvider.findPartitionedBuckets",
    query = "select z.IDENTIFIER, z.AGENCY_NM, z.CITY_NM, z.FAX_NO, z.FIRST_NM, z.LAST_NM, "
        + "trim(z.NMPRFX_DSC) as NMPRFX_DSC, z.PHONE_NO, z.TEL_EXT_NO, "
        + "trim(z.PSTITL_DSC) as PSTITL_DSC, z.SVCPVDRC, z.STATE_C, "
        + "z.STREET_NM, z.STREET_NO, z.SUFX_TLDSC, z.ZIP_NM, z.LST_UPD_ID, z.LST_UPD_TS, "
        + "z.ZIP_SFX_NO, z.ARCASS_IND, z.EMAIL_ADDR "
        + "from ( select mod(y.rn, CAST(:total_buckets AS INTEGER)) + 1 as bucket, y.* "
        + "from ( select row_number() over (order by 1) as rn, x.* "
        + "from ( select c.* from {h-schema}SVC_PVRT c "
        + "WHERE c.IDENTIFIER >= :min_id and c.IDENTIFIER < :max_id "
        + ") x ) y ) z where z.bucket = :bucket_num for read only",
    resultClass = ServiceProvider.class)})
@Entity
@Table(name = "SVC_PVRT")
@JsonPropertyOrder(alphabetic = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ServiceProvider extends BaseServiceProvider {

  /**
   * Default.
   */
  private static final long serialVersionUID = 1L;

  /**
   * Default constructor
   * 
   * Required for Hibernate
   */
  public ServiceProvider() {
    super();
  }

  /**
   * @param agencyName The agencyName
   * @param archiveAssociationIndicator The archiveAssociationIndicator
   * @param cityName The cityName
   * @param emailAddress The emailAddress
   * @param faxNumber The faxNumber
   * @param firstName The firstName
   * @param id The id
   * @param lastName The lastName
   * @param namePrefixDescription The namePrefixDescription
   * @param phoneExtensionNumber The phoneExtensionNumber
   * @param phoneNumber The phoneNumber
   * @param positionTitleDescription The positionTitleDescription
   * @param serviceProviderType The serviceProviderType
   * @param stateCodeType The stateCodeType
   * @param streetName The streetName
   * @param streetNumber The streetNumber
   * @param suffixTitleDescription The suffixTitleDescription
   * @param zipNumber The zipNumber
   * @param zipSuffixNumber The zipSuffixNumber
   */
  public ServiceProvider(String agencyName, String archiveAssociationIndicator, String cityName,
      String emailAddress, BigDecimal faxNumber, String firstName, String id, String lastName,
      String namePrefixDescription, Integer phoneExtensionNumber, BigDecimal phoneNumber,
      String positionTitleDescription, Short serviceProviderType, Short stateCodeType,
      String streetName, String streetNumber, String suffixTitleDescription, Integer zipNumber,
      Short zipSuffixNumber) {
    super();
    this.agencyName = agencyName;
    this.archiveAssociationIndicator = archiveAssociationIndicator;
    this.cityName = cityName;
    this.emailAddress = emailAddress;
    this.faxNumber = faxNumber;
    this.firstName = firstName;
    this.id = id;
    this.lastName = lastName;
    this.namePrefixDescription = namePrefixDescription;
    this.phoneExtensionNumber = phoneExtensionNumber;
    this.phoneNumber = phoneNumber;
    this.positionTitleDescription = positionTitleDescription;
    this.serviceProviderType = serviceProviderType;
    this.stateCodeType = stateCodeType;
    this.streetName = streetName;
    this.streetNumber = streetNumber;
    this.suffixTitleDescription = suffixTitleDescription;
    this.zipNumber = zipNumber;
    this.zipSuffixNumber = zipSuffixNumber;
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
