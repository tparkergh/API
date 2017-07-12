package gov.ca.cwds.data.persistence.cms;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import gov.ca.cwds.data.ns.NsPersistentObject;

/**
 * {@link NsPersistentObject} representing a Address Uppercase
 * 
 * @author CWDS API Team
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "ADDRS_UC")
@JsonPropertyOrder(alphabetic = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class AddressUc extends CmsPersistentObject {

  @Id
  @Column(name = "PKTBL_ID")
  private String pktableId;

  @Column(name = "SRCTBL_CD")
  private String sourceTableCode;

  @Column(name = "CITY_NM")
  private String cityName;

  @Column(name = "STREET_NM")
  private String streetName;

  @Column(name = "STREET_NO")
  private String streetNumber;

  /**
   * Default constructor
   * 
   * Required for Hibernate
   */
  public AddressUc() {
    super();
  }

  /**
   * @param pktableId the primary key
   * @param sourceTableCode the source table code
   * @param cityName the city name
   * @param streetName the street name
   * @param streetNumber the street number
   */
  public AddressUc(String pktableId, String sourceTableCode, String cityName, String streetName,
      String streetNumber) {
    super();
    this.pktableId = pktableId;
    this.sourceTableCode = sourceTableCode;
    this.cityName = cityName;
    this.streetName = streetName;
    this.streetNumber = streetNumber;
  }


  @Override
  public Serializable getPrimaryKey() {
    return pktableId;
  }

  /**
   * @return the pktableId
   */
  public String getPktableId() {
    return pktableId;
  }

  /**
   * @param pktableId the pktableId to set
   */
  public void setPktableId(String pktableId) {
    this.pktableId = pktableId;
  }

  /**
   * @return the sourceTableCode
   */
  public String getSourceTableCode() {
    return sourceTableCode;
  }

  /**
   * @param sourceTableCode the sourceTableCode to set
   */
  public void setSourceTableCode(String sourceTableCode) {
    this.sourceTableCode = sourceTableCode;
  }

  /**
   * @return the cityName
   */
  public String getCityName() {
    return cityName;
  }

  /**
   * @param cityName the cityName to set
   */
  public void setCityName(String cityName) {
    this.cityName = cityName;
  }

  /**
   * @return the streetName
   */
  public String getStreetName() {
    return streetName;
  }

  /**
   * @param streetName the streetName to set
   */
  public void setStreetName(String streetName) {
    this.streetName = streetName;
  }

  /**
   * @return the streetNumber
   */
  public String getStreetNumber() {
    return streetNumber;
  }

  /**
   * @param streetNumber the streetNumber to set
   */
  public void setStreetNumber(String streetNumber) {
    this.streetNumber = streetNumber;
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
