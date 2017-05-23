package gov.ca.cwds.rest.api.domain.cms;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.DomainChef;
import gov.ca.cwds.rest.api.domain.ReportingDomain;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
/**
 * {@link DomainObject} representing an address
 * 
 * @author CWDS API Team
 */
public class Address extends ReportingDomain implements Request, Response {

  /**
   * Base serialization value. Increment by version
   */
  private static final long serialVersionUID = 1L;

  @Size(max = 10)
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
  @Size(max = 10)
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
      this.zip = Integer.parseInt(persistedAddress.getZip());

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


  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#hashCode()
   */
  @Override
  public final int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((existingAddressId == null) ? 0 : existingAddressId.hashCode());
    result = prime * result + ((addressDescription == null) ? 0 : addressDescription.hashCode());
    result = prime * result + ((city == null) ? 0 : city.hashCode());
    result = prime * result + ((emergencyExtension == null) ? 0 : emergencyExtension.hashCode());
    result = prime * result + ((emergencyNumber == null) ? 0 : emergencyNumber.hashCode());
    result = prime * result + ((frgAdrtB == null) ? 0 : frgAdrtB.hashCode());
    result = prime * result + ((governmentEntityCd == null) ? 0 : governmentEntityCd.hashCode());
    result = prime * result + ((headerAddress == null) ? 0 : headerAddress.hashCode());
    result = prime * result + ((messageExtension == null) ? 0 : messageExtension.hashCode());
    result = prime * result + ((messageNumber == null) ? 0 : messageNumber.hashCode());
    result = prime * result + ((postDirCd == null) ? 0 : postDirCd.hashCode());
    result = prime * result + ((preDirCd == null) ? 0 : preDirCd.hashCode());
    result = prime * result + ((primaryExtension == null) ? 0 : primaryExtension.hashCode());
    result = prime * result + ((primaryNumber == null) ? 0 : primaryNumber.hashCode());
    result = prime * result + ((state == null) ? 0 : state.hashCode());
    result = prime * result + ((streetName == null) ? 0 : streetName.hashCode());
    result = prime * result + ((streetNumber == null) ? 0 : streetNumber.hashCode());
    result = prime * result + ((streetSuffixCd == null) ? 0 : streetSuffixCd.hashCode());
    result = prime * result + ((unitDesignationCd == null) ? 0 : unitDesignationCd.hashCode());
    result = prime * result + ((unitNumber == null) ? 0 : unitNumber.hashCode());
    result = prime * result + ((zip == null) ? 0 : zip.hashCode());
    result = prime * result + ((zip4 == null) ? 0 : zip4.hashCode());
    return result;
  }


  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public final boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (!(obj instanceof Address)) {
      return false;
    }
    Address other = (Address) obj;
    if (addressDescription == null) {
      if (other.addressDescription != null) {
        return false;
      }
    } else if (!addressDescription.equals(other.addressDescription)) {
      return false;
    }
    if (existingAddressId == null) {
      if (other.existingAddressId != null) {
        return false;
      }
    } else if (!existingAddressId.equals(other.existingAddressId)) {
      return false;
    }
    if (city == null) {
      if (other.city != null) {
        return false;
      }
    } else if (!city.equals(other.city)) {
      return false;
    }
    if (emergencyExtension == null) {
      if (other.emergencyExtension != null) {
        return false;
      }
    } else if (!emergencyExtension.equals(other.emergencyExtension)) {
      return false;
    }
    if (emergencyNumber == null) {
      if (other.emergencyNumber != null) {
        return false;
      }
    } else if (!emergencyNumber.equals(other.emergencyNumber)) {
      return false;
    }
    if (frgAdrtB == null) {
      if (other.frgAdrtB != null) {
        return false;
      }
    } else if (!frgAdrtB.equals(other.frgAdrtB)) {
      return false;
    }
    if (governmentEntityCd == null) {
      if (other.governmentEntityCd != null) {
        return false;
      }
    } else if (!governmentEntityCd.equals(other.governmentEntityCd)) {
      return false;
    }
    if (headerAddress == null) {
      if (other.headerAddress != null) {
        return false;
      }
    } else if (!headerAddress.equals(other.headerAddress)) {
      return false;
    }
    if (messageExtension == null) {
      if (other.messageExtension != null) {
        return false;
      }
    } else if (!messageExtension.equals(other.messageExtension)) {
      return false;
    }
    if (messageNumber == null) {
      if (other.messageNumber != null) {
        return false;
      }
    } else if (!messageNumber.equals(other.messageNumber)) {
      return false;
    }
    if (postDirCd == null) {
      if (other.postDirCd != null) {
        return false;
      }
    } else if (!postDirCd.equals(other.postDirCd)) {
      return false;
    }
    if (preDirCd == null) {
      if (other.preDirCd != null) {
        return false;
      }
    } else if (!preDirCd.equals(other.preDirCd)) {
      return false;
    }
    if (primaryExtension == null) {
      if (other.primaryExtension != null) {
        return false;
      }
    } else if (!primaryExtension.equals(other.primaryExtension)) {
      return false;
    }
    if (primaryNumber == null) {
      if (other.primaryNumber != null) {
        return false;
      }
    } else if (!primaryNumber.equals(other.primaryNumber)) {
      return false;
    }
    if (state == null) {
      if (other.state != null) {
        return false;
      }
    } else if (!state.equals(other.state)) {
      return false;
    }
    if (streetName == null) {
      if (other.streetName != null) {
        return false;
      }
    } else if (!streetName.equals(other.streetName)) {
      return false;
    }
    if (streetNumber == null) {
      if (other.streetNumber != null) {
        return false;
      }
    } else if (!streetNumber.equals(other.streetNumber)) {
      return false;
    }
    if (streetSuffixCd == null) {
      if (other.streetSuffixCd != null) {
        return false;
      }
    } else if (!streetSuffixCd.equals(other.streetSuffixCd)) {
      return false;
    }
    if (unitDesignationCd == null) {
      if (other.unitDesignationCd != null) {
        return false;
      }
    } else if (!unitDesignationCd.equals(other.unitDesignationCd)) {
      return false;
    }
    if (unitNumber == null) {
      if (other.unitNumber != null) {
        return false;
      }
    } else if (!unitNumber.equals(other.unitNumber)) {
      return false;
    }
    if (zip == null) {
      if (other.zip != null) {
        return false;
      }
    } else if (!zip.equals(other.zip)) {
      return false;
    }
    if (zip4 == null) {
      if (other.zip4 != null) {
        return false;
      }
    } else if (!zip4.equals(other.zip4)) {
      return false;
    }
    return true;
  }

}
