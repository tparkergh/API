package gov.ca.cwds.rest.api.domain.cms;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.DomainChef;
import gov.ca.cwds.rest.api.domain.DomainObject;
import io.swagger.annotations.ApiModelProperty;

/**
 * {@link DomainObject} representing an address
 * 
 * @author CWDS API Team
 */
public class ClientAddress extends DomainObject implements Request, Response {

  /**
   * Base serialization value. Increment by version
   */
  private static final long serialVersionUID = 1L;

  @Size(max = 10)
  @ApiModelProperty(required = false, readOnly = false, value = "", example = "ABC1234567")
  private String existingClientAddressId;

  @NotNull
  @ApiModelProperty(required = true, readOnly = false, example = "32")
  private Short addressType;

  @NotNull
  @ApiModelProperty(required = false, readOnly = false, value = "", example = "123456ABC")
  @Size(max = 10)
  private String bookingOrInmateId;

  @ApiModelProperty(required = false, readOnly = false, value = "yyyy-MM-dd",
      example = "2016-01-01")
  private String effectiveEndDate;

  @ApiModelProperty(required = false, readOnly = false, value = "yyyy-MM-dd",
      example = "2016-01-01")
  private String effectiveStartDate;

  @NotEmpty
  @Size(min = 10, max = 10)
  @ApiModelProperty(required = true, readOnly = false, example = "1234567ABC")
  private String addressId;

  @NotEmpty
  @Size(min = 10, max = 10)
  @ApiModelProperty(required = true, readOnly = false, example = "1234567ABC")
  private String clientId;

  @NotNull
  @ApiModelProperty(required = false, readOnly = false, value = "", example = "1234567ABC")
  @Size(max = 10)
  private String referralId;

  @NotNull
  @ApiModelProperty(required = false, readOnly = false, value = "", example = "1234567ABC")
  @Size(max = 10)
  private String homelessInd;


  /*
   * default constructor
   */
  public ClientAddress() {

  }

  /**
   * @param addressType The addressType
   * @param bkInmtId The bkInmtId
   * @param effEndDt The effEndDt
   * @param effStartDt The effStartDt
   * @param addressId The fkAddress
   * @param clientId The fkClient
   * @param homelessInd The homelessInd
   * @param referralId The fkReferral
   */
  public ClientAddress(Short addressType, String bkInmtId, String effEndDt, String effStartDt,
      String fkAddress, String fkClient, String homelessInd, String fkReferral) {
    super();
    this.addressType = addressType;
    this.bookingOrInmateId = bkInmtId;
    this.effectiveEndDate = effEndDt;
    this.effectiveStartDate = effStartDt;
    this.addressId = fkAddress;
    this.clientId = fkClient;
    this.homelessInd = homelessInd;
    this.referralId = fkReferral;
  }

  /**
   * @param persistedClientAddress - persisted ClientAddress object
   * @param isExist - does the ClientAddress id exist
   */
  public ClientAddress(gov.ca.cwds.data.persistence.cms.ClientAddress persistedClientAddress,
      boolean isExist) {
    this.existingClientAddressId = isExist ? persistedClientAddress.getId() : "";
    this.addressType = persistedClientAddress.getAddressType();
    this.bookingOrInmateId = persistedClientAddress.getBkInmtId();
    this.effectiveEndDate = DomainChef.cookDate(persistedClientAddress.getEffEndDt());
    this.effectiveStartDate = DomainChef.cookDate(persistedClientAddress.getEffStartDt());
    this.addressId = persistedClientAddress.getFkAddress();
    this.clientId = persistedClientAddress.getFkClient();
    this.homelessInd = persistedClientAddress.getHomelessInd();
    this.referralId = persistedClientAddress.getFkReferral();
  }

  /**
   * @return the clientAddressId
   */
  public String getClientAddressId() {
    return existingClientAddressId;
  }

  /**
   * @return the addressType
   */
  public Short getAddressType() {
    return addressType;
  }

  /**
   * @return the bkInmtId
   */
  public String getBookingOrInmateId() {
    return bookingOrInmateId;
  }

  /**
   * @return the effEndDt
   */
  public String getEffectiveEndDate() {
    return effectiveEndDate;
  }

  /**
   * @return the effStartDt
   */
  public String getEffectiveStartDate() {
    return effectiveStartDate;
  }

  /**
   * @return the addressId
   */
  public String getAddressId() {
    return addressId;
  }

  /**
   * @return the clientId
   */
  public String getClientId() {
    return clientId;
  }

  /**
   * @return the homelessInd
   */
  public String getHomelessInd() {
    return homelessInd;
  }

  /**
   * @return the referralId
   */
  public String getReferralId() {
    return referralId;
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
    result = prime * result + ((addressType == null) ? 0 : addressType.hashCode());
    result = prime * result + ((bookingOrInmateId == null) ? 0 : bookingOrInmateId.hashCode());
    result = prime * result + ((effectiveEndDate == null) ? 0 : effectiveEndDate.hashCode());
    result = prime * result + ((effectiveStartDate == null) ? 0 : effectiveStartDate.hashCode());
    result = prime * result + ((addressId == null) ? 0 : addressId.hashCode());
    result = prime * result + ((clientId == null) ? 0 : clientId.hashCode());
    result = prime * result + ((referralId == null) ? 0 : referralId.hashCode());
    result = prime * result + ((homelessInd == null) ? 0 : homelessInd.hashCode());
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
    if (!(obj instanceof ClientAddress)) {
      return false;
    }
    ClientAddress other = (ClientAddress) obj;
    if (addressType == null) {
      if (other.addressType != null) {
        return false;
      }
    } else if (!addressType.equals(other.addressType)) {
      return false;
    }
    if (bookingOrInmateId == null) {
      if (other.bookingOrInmateId != null) {
        return false;
      }
    } else if (!bookingOrInmateId.equals(other.bookingOrInmateId)) {
      return false;
    }
    if (effectiveEndDate == null) {
      if (other.effectiveEndDate != null) {
        return false;
      }
    } else if (!effectiveEndDate.equals(other.effectiveEndDate)) {
      return false;
    }
    if (effectiveStartDate == null) {
      if (other.effectiveStartDate != null) {
        return false;
      }
    } else if (!effectiveStartDate.equals(other.effectiveStartDate)) {
      return false;
    }
    if (addressId == null) {
      if (other.addressId != null) {
        return false;
      }
    } else if (!addressId.equals(other.addressId)) {
      return false;
    }
    if (clientId == null) {
      if (other.clientId != null) {
        return false;
      }
    } else if (!clientId.equals(other.clientId)) {
      return false;
    }
    if (referralId == null) {
      if (other.referralId != null) {
        return false;
      }
    } else if (!referralId.equals(other.referralId)) {
      return false;
    }
    if (homelessInd == null) {
      if (other.homelessInd != null) {
        return false;
      }
    } else if (!homelessInd.equals(other.homelessInd)) {
      return false;
    }
    return true;
  }
}
