package gov.ca.cwds.rest.api.domain.cms;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import gov.ca.cwds.data.persistence.cms.CmsPersistentObject;
import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.DomainChef;
import gov.ca.cwds.rest.api.domain.DomainObject;
import gov.ca.cwds.rest.api.domain.ReportingDomain;
import gov.ca.cwds.rest.validation.IfThen;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * {@link DomainObject} representing an address
 * 
 * @author CWDS API Team
 */
@ApiModel
/**
 * <blockquote>
 * 
 * <pre>
 * BUSINESS RULE: "R - 05360" - StreetName is set then City is required
 * 
 * IF  streetNumber is set
 * THEN streetName is required
 * 
 * IF streetName is set
 * THEN city is required
 * </blockquote>
 * </pre>
 */
@IfThen.List({@IfThen(ifProperty = "streetName", thenProperty = "streetNumber", required = false),
    @IfThen(ifProperty = "streetNumber", thenProperty = "streetName", required = false),
    @IfThen(ifProperty = "streetName", thenProperty = "city", required = false)})
public class Address extends ReportingDomain implements Request, Response {
  private static final BigDecimal DEFAULT_DECIMAL = new BigDecimal(0);
  private static final int DEFAULT_INT = 0;
  private static final short DEFAULT_CODE = 0;

  /**
   * Base serialization value. Increment by version
   */
  private static final long serialVersionUID = 1L;

  @Size(max = CmsPersistentObject.CMS_ID_LEN)
  @ApiModelProperty(required = false, readOnly = false, value = "", example = "ABC1234567")
  private String existingAddressId;

  @NotNull
  @ApiModelProperty(required = false, readOnly = false, value = "", example = "Springfield")
  @Size(max = 20)
  private String city;

  @NotNull
  @ApiModelProperty(required = false, readOnly = false, example = "1112223333")
  private BigDecimal emergencyNumber;

  @NotNull
  @ApiModelProperty(example = "1112")
  private Integer emergencyExtension;

  @NotNull
  @ApiModelProperty(required = true, readOnly = false)
  private Boolean frgAdrtB;

  @ApiModelProperty(example = "1104")
  private Short governmentEntityCd;

  @ApiModelProperty(example = "1112223333")
  private BigDecimal messageNumber;

  @ApiModelProperty(example = "1112")
  private Integer messageExtension;

  @ApiModelProperty(example = "Senior Residence")
  @Size(max = 35)
  private String headerAddress;

  @ApiModelProperty(example = "1112223333")
  private BigDecimal primaryNumber;

  @ApiModelProperty(example = "1112")
  private Integer primaryExtension;

  @ApiModelProperty(example = "1828")
  private Short state;

  @ApiModelProperty(example = "Evergreen Terrace")
  @Size(max = 40)
  private String streetName;

  @ApiModelProperty(example = "450A")
  @Size(max = CmsPersistentObject.CMS_ID_LEN)
  private String streetNumber;

  @ApiModelProperty(example = "65258")
  private Integer zip;

  @ApiModelProperty(example = "Third Street on the left")
  @Size(max = 120)
  private String addressDescription;

  @ApiModelProperty(example = "6525")
  private Short zip4;

  @ApiModelProperty(example = "NE")
  @Size(max = 2)
  private String postDirCd;

  @ApiModelProperty(example = "NE")
  @Size(max = 2)
  private String preDirCd;

  @ApiModelProperty(example = "1882")
  private Short streetSuffixCd;

  @ApiModelProperty(example = "2065")
  private Short unitDesignationCd;

  @ApiModelProperty(example = "180")
  @Size(max = 8)
  private String unitNumber;

  /**
   * default constructor
   */
  public Address() {
    // no opt
  }

  /**
   * @param existingAddressId = Address Id
   * @param city The city
   * @param emergencyNumber city emergencyNumber
   * @param emergencyExtension city emergencyExtension
   * @param frgAdrtB city frgAdrtB
   * @param governmentEntityCd city governmentEntityCd
   * @param messageNumber city messageNumber
   * @param messageExtension city messageExtension
   * @param headerAddress city headerAddress
   * @param primaryNumber city primaryNumber
   * @param primaryExtension city primaryExtension
   * @param state city state
   * @param streetName city streetName
   * @param streetNumber city streetNumber
   * @param zip city zip
   * @param addressDescription city addressDescription
   * @param zip4 city zip4
   * @param postDirCd city postDirCd
   * @param preDirCd city preDirCd
   * @param streetSuffixCd city streetSuffixCd
   * @param unitDesignationCd city unitDesignationCd
   * @param unitNumber city unitNumber
   */
  public Address(String existingAddressId, String city, BigDecimal emergencyNumber,
      Integer emergencyExtension, Boolean frgAdrtB, Short governmentEntityCd,
      BigDecimal messageNumber, Integer messageExtension, String headerAddress,
      BigDecimal primaryNumber, Integer primaryExtension, Short state, String streetName,
      String streetNumber, Integer zip, String addressDescription, Short zip4, String postDirCd,
      String preDirCd, Short streetSuffixCd, Short unitDesignationCd, String unitNumber) {
    super();
    this.existingAddressId = existingAddressId;
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
   * @param persistedAddress - persisted Address object
   * @param isExist - does it exist
   */
  /*
   * constuctor from persistent Address
   */
  public Address(gov.ca.cwds.data.persistence.cms.Address persistedAddress, boolean isExist) {
    this.existingAddressId = isExist ? persistedAddress.getId() : "";
    this.city = persistedAddress.getCity();
    this.emergencyNumber = persistedAddress.getEmergencyNumber();
    this.emergencyExtension = persistedAddress.getEmergencyExtension();
    this.frgAdrtB = DomainChef.uncookBooleanString(persistedAddress.getFrgAdrtB());
    this.governmentEntityCd = persistedAddress.getGovernmentEntityCd();
    this.messageNumber = persistedAddress.getMessageNumber();
    this.messageExtension = persistedAddress.getMessageExtension();
    this.headerAddress = persistedAddress.getHeaderAddress();
    this.primaryNumber = persistedAddress.getPrimaryNumber();
    this.primaryExtension = persistedAddress.getPrimaryExtension();
    this.state = persistedAddress.getStateCd();
    this.streetName = persistedAddress.getStreetName();
    this.streetNumber = persistedAddress.getStreetNumber();
    try {
      this.zip = Integer.valueOf(persistedAddress.getZip());

    } catch (NumberFormatException e) {
      throw e;
    }
    this.addressDescription = persistedAddress.getAddressDescription();
    this.zip4 = persistedAddress.getZip4();
    this.postDirCd = persistedAddress.getPostDirCd();
    this.preDirCd = persistedAddress.getPreDirCd();
    this.streetSuffixCd = persistedAddress.getStreetSuffixCd();
    this.unitDesignationCd = persistedAddress.getUnitDesignationCd();
    this.unitNumber = persistedAddress.getUnitNumber();
  }

  /**
   * @param address - address
   * @return - postedAddress
   */
  public static Address createWithDefaults(gov.ca.cwds.rest.api.domain.Address address) {
    // TODO: 41511573 address parsing - Smarty Streets Free Form display requires standardizing
    // parsing to fields in CMS
    int zipCode = Integer.parseInt(address.getZip());
    short zipSuffix = 0;
    if (address.getZip().length() > 5) {
      zipSuffix = Short.parseShort(address.getZip().substring(5));
    }

    /**
     * Split the StreetAddress into separate streetNumber and StreetName objects, updates the
     * respective columns. If the streetAddress is entered only words, it will throw a validation
     * exception to enter the streetNumber.
     */
    String streetNumber = null;
    String streetName = null;
    int index;
    if ((index = address.getStreetAddress().indexOf(' ')) > 0) {
      streetNumber = address.getStreetAddress().substring(0, index);
      if (!streetNumber.chars().allMatch(Character::isDigit)) {
        streetNumber = null;
        streetName = address.getStreetAddress();
      } else {
        streetName =
            address.getStreetAddress().substring(index + 1, address.getStreetAddress().length());
      }
    } else {
      if (address.getStreetAddress().chars().allMatch(Character::isDigit)) {
        streetNumber = address.getStreetAddress();
      } else {
        streetName = address.getStreetAddress();
      }
    }

    return new Address(" ", address.getCity(), DEFAULT_DECIMAL, DEFAULT_INT, false, DEFAULT_CODE,
        DEFAULT_DECIMAL, DEFAULT_INT, " ", DEFAULT_DECIMAL, DEFAULT_INT,
        address.getState().shortValue(), streetName, streetNumber, zipCode, " ", zipSuffix, " ",
        " ", DEFAULT_CODE, DEFAULT_CODE, " ");
  }

  /**
   * @return the existingAddressId
   */
  public String getExistingAddressId() {
    return existingAddressId;
  }

  /**
   * @return the city
   */
  public String getCity() {
    return city;
  }

  /**
   * @return the emergencyNumber
   */
  public BigDecimal getEmergencyNumber() {
    return emergencyNumber;
  }

  /**
   * @return the emergencyExtension
   */
  public Integer getEmergencyExtension() {
    return emergencyExtension;
  }

  /**
   * @return the frgAdrtB
   */
  public Boolean getFrgAdrtB() {
    return frgAdrtB;
  }

  /**
   * @return the governmentEntityCd
   */
  public Short getGovernmentEntityCd() {
    return governmentEntityCd;
  }

  /**
   * @return the messageNumber
   */
  public BigDecimal getMessageNumber() {
    return messageNumber;
  }

  /**
   * @return the messageExtension
   */
  public Integer getMessageExtension() {
    return messageExtension;
  }

  /**
   * @return the headerAddress
   */
  public String getHeaderAddress() {
    return headerAddress;
  }

  /**
   * @return the primaryNumber
   */
  public BigDecimal getPrimaryNumber() {
    return primaryNumber;
  }

  /**
   * @return the primaryExtension
   */
  public Integer getPrimaryExtension() {
    return primaryExtension;
  }

  /**
   * @return the state
   */
  public Short getState() {
    return state;
  }

  /**
   * @return the streetName
   */
  public String getStreetName() {
    return streetName;
  }

  /**
   * @return the streetNumber
   */
  public String getStreetNumber() {
    return streetNumber;
  }

  /**
   * @return the zip
   */
  public Integer getZip() {
    return zip;
  }

  /**
   * @return the addressDescription
   */
  public String getAddressDescription() {
    return addressDescription;
  }

  /**
   * @return the zip4
   */
  public Short getZip4() {
    return zip4;
  }

  /**
   * @return the postDirCd
   */
  public String getPostDirCd() {
    return postDirCd;
  }

  /**
   * @return the preDirCd
   */
  public String getPreDirCd() {
    return preDirCd;
  }

  /**
   * @return the streetSuffixCd
   */
  public Short getStreetSuffixCd() {
    return streetSuffixCd;
  }

  /**
   * @return the unitDesignationCd
   */
  public Short getUnitDesignationCd() {
    return unitDesignationCd;
  }

  /**
   * @return the unitNumber
   */
  public String getUnitNumber() {
    return unitNumber;
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
