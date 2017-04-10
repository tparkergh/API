package gov.ca.cwds.rest.api.domain.cms;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.DomainObject;
import io.dropwizard.jackson.JsonSnakeCase;
import io.swagger.annotations.ApiModelProperty;

/**
 * {@link DomainObject} representing an address
 * 
 * @author CWDS API Team
 */
@JsonSnakeCase
public class ClientAddress extends DomainObject implements Request, Response {

  /**
   * Base serialization value. Increment by version
   */
  private static final long serialVersionUID = 1L;


  @NotNull
  @ApiModelProperty(required = true, readOnly = false, example = "32")
  private Short addressType;

  @NotNull
  @ApiModelProperty(required = false, readOnly = false, value = "", example = "1234567890")
  @Size(max = 10)
  private String bkInmtId;

  @ApiModelProperty(required = false, readOnly = false, value = "yyyy-MM-dd",
      example = "2016-01-01")
  private String effEndDt;

  @ApiModelProperty(required = false, readOnly = false, value = "yyyy-MM-dd",
      example = "2016-01-01")
  private String effStartDt;

  @NotNull
  @ApiModelProperty(required = true, readOnly = false, example = "1234567890")
  @Size(max = 10)
  private String fkAddress;

  @NotNull
  @ApiModelProperty(required = true, readOnly = false, example = "1234567890")
  @Size(max = 10)
  private String fkClient;


  @NotNull
  @ApiModelProperty(required = false, readOnly = false, value = "", example = "N")
  @Size(max = 1)
  private String homelessInd;

  @NotNull
  @ApiModelProperty(required = false, readOnly = false, value = "", example = "1234567890")
  @Size(max = 10)
  private String fkReferral;

  /**
   * @param addressType The addressType
   * @param bkInmtId The bkInmtId
   * @param effEndDt The effEndDt
   * @param effStartDt The effStartDt
   * @param fkAddress The fkAddress
   * @param fkClient The fkClient
   * @param homelessInd The homelessInd
   * @param fkReferral The fkReferral
   */
  public ClientAddress(Short addressType, String bkInmtId, String effEndDt, String effStartDt,
      String fkAddress, String fkClient, String homelessInd, String fkReferral) {
    super();
    this.addressType = addressType;
    this.bkInmtId = bkInmtId;
    this.effEndDt = effEndDt;
    this.effStartDt = effStartDt;
    this.fkAddress = fkAddress;
    this.fkClient = fkClient;
    this.homelessInd = homelessInd;
    this.fkReferral = fkReferral;
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
  public String getBkInmtId() {
    return bkInmtId;
  }

  /**
   * @return the effEndDt
   */
  public String getEffEndDt() {
    return effEndDt;
  }

  /**
   * @return the effStartDt
   */
  public String getEffStartDt() {
    return effStartDt;
  }

  /**
   * @return the fkAddress
   */
  public String getFkAddress() {
    return fkAddress;
  }

  /**
   * @return the fkClient
   */
  public String getFkClient() {
    return fkClient;
  }

  /**
   * @return the homelessInd
   */
  public String getHomelessInd() {
    return homelessInd;
  }

  /**
   * @return the fkReferral
   */
  public String getFkReferral() {
    return fkReferral;
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
    result = prime * result + ((bkInmtId == null) ? 0 : bkInmtId.hashCode());
    result = prime * result + ((effEndDt == null) ? 0 : effEndDt.hashCode());
    result = prime * result + ((effStartDt == null) ? 0 : effStartDt.hashCode());
    result = prime * result + ((fkAddress == null) ? 0 : fkAddress.hashCode());
    result = prime * result + ((fkClient == null) ? 0 : fkClient.hashCode());
    result = prime * result + ((fkReferral == null) ? 0 : fkReferral.hashCode());
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
    if (bkInmtId == null) {
      if (other.bkInmtId != null) {
        return false;
      }
    } else if (!bkInmtId.equals(other.bkInmtId)) {
      return false;
    }
    if (effEndDt == null) {
      if (other.effEndDt != null) {
        return false;
      }
    } else if (!effEndDt.equals(other.effEndDt)) {
      return false;
    }
    if (effStartDt == null) {
      if (other.effStartDt != null) {
        return false;
      }
    } else if (!effStartDt.equals(other.effStartDt)) {
      return false;
    }
    if (fkAddress == null) {
      if (other.fkAddress != null) {
        return false;
      }
    } else if (!fkAddress.equals(other.fkAddress)) {
      return false;
    }
    if (fkClient == null) {
      if (other.fkClient != null) {
        return false;
      }
    } else if (!fkClient.equals(other.fkClient)) {
      return false;
    }
    if (fkReferral == null) {
      if (other.fkReferral != null) {
        return false;
      }
    } else if (!fkReferral.equals(other.fkReferral)) {
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
