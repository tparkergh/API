package gov.ca.cwds.data.persistence.cms;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.PersistenceException;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import gov.ca.cwds.data.persistence.PersistentObject;
import gov.ca.cwds.rest.api.ApiException;
import gov.ca.cwds.rest.api.domain.DomainChef;

/**
 * {@link PersistentObject} representing a Address
 * 
 * @author CWDS API Team
 */
@NamedQueries({
    @NamedQuery(name = "gov.ca.cwds.data.persistence.cms.Address.findAll", query = "FROM Address"),
    @NamedQuery(name = "gov.ca.cwds.data.persistence.cms.Address.findAllUpdatedAfter",
        query = "FROM Address WHERE lastUpdatedTime > :after")})
@Entity
@Table(name = "ADDRS_T")
@JsonPropertyOrder(alphabetic = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Address extends BaseAddress {

  /**
   * Default serialization version.
   */
  private static final long serialVersionUID = 1L;

  /**
   * Default constructor
   * 
   * Required for Hibernate
   */
  public Address() {
    super();
  }

  /**
   * @param id The identifier
   * @param city The city
   * @param emergencyNumber The emergencyNumber
   * @param emergencyExtension The emergencyExtension
   * @param frgAdrtB The frgAdrtB
   * @param governmentEntityCd The governmentEntityCd
   * @param messageNumber The messageNumber
   * @param messageExtension The messageExtension
   * @param headerAddress The headerAddress
   * @param primaryNumber The primaryNumber
   * @param primaryExtension The primaryExtension
   * @param state The state
   * @param streetName The streetName
   * @param streetNumber The streetNumber
   * @param zip The zip
   * @param addressDescription The addressDescription
   * @param zip4 The zip4
   * @param postDirCd The postDirCd
   * @param preDirCd The preDirCd
   * @param streetSuffixCd The streetSuffixCd
   * @param unitDesignationCd The unitDesignationCd
   * @param unitNumber The unitNumber
   */
  public Address(String id, String city, BigDecimal emergencyNumber, Integer emergencyExtension,
      String frgAdrtB, Short governmentEntityCd, BigDecimal messageNumber, Integer messageExtension,
      String headerAddress, BigDecimal primaryNumber, Integer primaryExtension, Short state,
      String streetName, String streetNumber, String zip, String addressDescription, Short zip4,
      String postDirCd, String preDirCd, Short streetSuffixCd, Short unitDesignationCd,
      String unitNumber) {
    super();
    this.id = id;
    this.city = city;
    this.emergencyNumber = emergencyNumber;
    this.emergencyExtension = emergencyExtension;
    this.frgAdrtB = frgAdrtB;
    this.governmentEntityCd = governmentEntityCd;
    this.messageNumber = messageNumber;
    this.messageExtension = messageExtension;
    this.headerAddress = headerAddress;
    this.primaryNumber = primaryNumber;
    this.primaryExtension = primaryExtension;
    this.state = state;
    this.streetName = streetName;
    this.streetNumber = streetNumber;
    this.zip = zip;
    this.addressDescription = addressDescription;
    this.zip4 = zip4;
    this.postDirCd = postDirCd;
    this.preDirCd = preDirCd;
    this.streetSuffixCd = streetSuffixCd;
    this.unitDesignationCd = unitDesignationCd;
    this.unitNumber = unitNumber;
  }

  /**
   * Constructor
   * 
   * @param id - Address identifier
   * @param address - domain Address object
   * @param lastUpdateId - staff person Id
   */
  public Address(String id, gov.ca.cwds.rest.api.domain.cms.Address address, String lastUpdateId) {
    super(lastUpdateId);
    init(id, address);
  }

  /**
   * Constructor
   * 
   * @param id -Address identifier
   * @param address - domain Address object
   * @param lastUpdateId - staff person Id
   * @param lastUpdatedTime - timestamp
   */
  public Address(String id, gov.ca.cwds.rest.api.domain.cms.Address address, String lastUpdateId,
      Date lastUpdatedTime) {
    super(lastUpdateId, lastUpdatedTime);
    init(id, address);
  }

  /**
   * 
   * @param id - id
   * @param address - address
   */
  private void init(String id, gov.ca.cwds.rest.api.domain.cms.Address address) {
    try {
      this.id = id;
      this.city = address.getCity();
      this.emergencyNumber = address.getEmergencyNumber();
      this.emergencyExtension = address.getEmergencyExtension();
      this.frgAdrtB = DomainChef.cookBoolean(address.getFrgAdrtB());
      this.governmentEntityCd = address.getGovernmentEntityCd();
      this.messageNumber = address.getMessageNumber();
      this.messageExtension = address.getMessageExtension();
      this.headerAddress = address.getHeaderAddress();
      this.primaryNumber = address.getPrimaryNumber();
      this.primaryExtension = address.getPrimaryExtension();
      this.state = address.getState();
      this.streetName = address.getStreetName();
      this.streetNumber = address.getStreetNumber();
      this.zip = address.getZip().toString();
      this.zip4 = address.getZip4();
      this.addressDescription = address.getAddressDescription();
      this.postDirCd = address.getPostDirCd();
      this.preDirCd = address.getPreDirCd();
      this.streetSuffixCd = address.getStreetSuffixCd();
      this.unitDesignationCd = address.getUnitDesignationCd();
      this.unitNumber = address.getUnitNumber();

    } catch (ApiException e) {
      throw new PersistenceException(e);
    }
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
