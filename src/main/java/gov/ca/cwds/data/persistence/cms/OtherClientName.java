package gov.ca.cwds.data.persistence.cms;

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
 * {@link PersistentObject} representing an OtherClientName.
 * 
 * @author CWDS API Team
 */
@SuppressWarnings("serial")
@NamedQueries({@NamedQuery(name = "gov.ca.cwds.data.persistence.cms.OtherClientName.findAll",
    query = "FROM OtherClientName WHERE clientId IN (SELECT id FROM Client WHERE sensitivityIndicator = 'N' AND soc158SealedClientIndicator = 'N')"),})
@NamedNativeQueries({
    @NamedNativeQuery(name = "gov.ca.cwds.data.persistence.cms.OtherClientName.findAllUpdatedAfter",
        query = "select x.THIRD_ID, x.FIRST_NM, x.LAST_NM, x.MIDDLE_NM, x.NMPRFX_DSC, "
            + "x.NAME_TPC, x.SUFX_TLDSC, x.LST_UPD_ID, x.LST_UPD_TS, x.FKCLIENT_T "
            + "from {h-schema}OCL_NM_T x WHERE x.IBMSNAP_LOGMARKER >= :after for read only",
        resultClass = OtherClientName.class),
    @NamedNativeQuery(
        name = "gov.ca.cwds.data.persistence.cms.OtherClientName.findPartitionedBuckets",
        query = "select z.THIRD_ID, z.FIRST_NM, z.LAST_NM, z.MIDDLE_NM, z.NMPRFX_DSC, "
            + "z.NAME_TPC, z.SUFX_TLDSC, z.LST_UPD_ID, z.LST_UPD_TS, z.FKCLIENT_T "
            + "from ( select mod(y.rn, CAST(:total_buckets AS INTEGER)) + 1 as bucket, y.* "
            + "from ( select row_number() over (order by 1) as rn, x.* "
            + "from ( select c.* from {h-schema}OCL_NM_T c "
            + "WHERE c.FKCLIENT_T >= :min_id AND c.FKCLIENT_T < :max_id AND THIRD_ID < '9999999999' "
            + ") x ) y ) z where z.bucket = :bucket_num for read only",
        resultClass = OtherClientName.class)})
@Entity
@Table(name = "OCL_NM_T")
@JsonPropertyOrder(alphabetic = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class OtherClientName extends BaseOtherClientName {

  /**
   * Default constructor
   * 
   * Required for Hibernate
   */
  public OtherClientName() {
    super();
  }

  /**
   * Construct from String inputs.
   * 
   * @param clientId the client id
   * @param firstName first name
   * @param lastName last name
   * @param middleName middle name
   * @param namePrefixDescription name prefix description, if any
   * @param nameType name type
   * @param suffixTitleDescription suffix title description, if any
   * @param thirdId "third" id, generated value (time stamp + staffId) for unique identification
   */
  public OtherClientName(String clientId, String firstName, String lastName, String middleName,
      String namePrefixDescription, Short nameType, String suffixTitleDescription, String thirdId) {
    super();

    this.firstName = firstName;
    this.clientId = clientId;
    this.lastName = lastName;
    this.middleName = middleName;
    this.namePrefixDescription = namePrefixDescription;
    this.nameType = nameType;
    this.suffixTitleDescription = suffixTitleDescription;
    this.thirdId = thirdId;
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
