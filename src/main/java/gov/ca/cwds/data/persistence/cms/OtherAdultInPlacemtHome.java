package gov.ca.cwds.data.persistence.cms;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import gov.ca.cwds.data.persistence.PersistentObject;

/**
 * {@link PersistentObject} representing a OtherAdultInPlacemtHome
 * 
 * @author CWDS API Team
 */
@NamedQueries({
    @NamedQuery(name = "gov.ca.cwds.data.persistence.cms.OtherAdultInPlacemtHome.findAll",
        query = "FROM OtherAdultInPlacemtHome"),
    @NamedQuery(
        name = "gov.ca.cwds.data.persistence.cms.OtherAdultInPlacemtHome.findAllUpdatedAfter",
        query = "FROM OtherAdultInPlacemtHome WHERE lastUpdatedTime > :after")})
@Entity
@Table(name = "OTH_ADLT")
@JsonPropertyOrder(alphabetic = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class OtherAdultInPlacemtHome extends BaseOtherAdultInPlacemtHome {

  /**
   * Default.
   */
  private static final long serialVersionUID = 1L;

  /**
   * Default constructor
   * 
   * Required for Hibernate
   */
  public OtherAdultInPlacemtHome() {
    super();
  }

  /**
   * @param birthDate The birthDate
   * @param commentDescription The commentDescription
   * @param endDate The endDate
   * @param fkplcHmT The fkplcHmT
   * @param genderCode The genderCode
   * @param id The id
   * @param identifiedDate The identifiedDate
   * @param name The name
   * @param otherAdultCode The otherAdultCode
   * @param passedBackgroundCheckCode The passedBackgroundCheckCode
   * @param residedOutOfStateIndicator The residedOutOfStateIndicator
   * @param startDate The startDate
   */
  public OtherAdultInPlacemtHome(Date birthDate, String commentDescription, Date endDate,
      String fkplcHmT, String genderCode, String id, Date identifiedDate, String name,
      String otherAdultCode, String passedBackgroundCheckCode, String residedOutOfStateIndicator,
      Date startDate) {
    super();
    this.birthDate = birthDate;
    this.commentDescription = commentDescription;
    this.endDate = endDate;
    this.fkplcHmT = fkplcHmT;
    this.genderCode = genderCode;
    this.id = id;
    this.identifiedDate = identifiedDate;
    this.name = name;
    this.otherAdultCode = otherAdultCode;
    this.passedBackgroundCheckCode = passedBackgroundCheckCode;
    this.residedOutOfStateIndicator = residedOutOfStateIndicator;
    this.startDate = startDate;
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
