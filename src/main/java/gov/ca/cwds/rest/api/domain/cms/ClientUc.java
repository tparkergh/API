package gov.ca.cwds.rest.api.domain.cms;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.DomainObject;
import io.dropwizard.validation.OneOf;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * {@link DomainObject} representing a Client Uppercase
 * 
 * @author CWDS API Team
 */
@ApiModel
public class ClientUc extends DomainObject implements Request, Response {
  /**
   * Serialization version
   */
  private static final long serialVersionUID = 1L;


  @NotEmpty
  @NotBlank
  @Size(min = 10, max = 10)
  @ApiModelProperty(required = true, readOnly = false, value = "identifies a client",
      example = "ABC1234567")
  private String pktableId;

  @NotEmpty
  @Size(min = 1, max = 1, message = "size must be 1")
  @OneOf(value = {"C", "N"}, ignoreCase = true, ignoreWhitespace = true)
  @ApiModelProperty(required = true, readOnly = false,
      value = "C=Client Table - N=Other Client Name Table", example = "C")
  private String sourceTableCode;

  @NotBlank
  @Size(min = 1, max = 20)
  @ApiModelProperty(required = true, readOnly = false, value = "first name of Client",
      example = "first name")
  private String commonFirstName;

  @NotBlank
  @Size(min = 1, max = 25)
  @ApiModelProperty(required = true, readOnly = false, value = "", example = "last name")
  private String commonLastName;

  @NotNull
  @Size(max = 20)
  @ApiModelProperty(required = false, readOnly = false, value = "", example = "middle name")
  private String commonMiddleName;



  /**
   * @param pktableId The primary key
   * @param sourceTableCode The source table code
   * @param commonFirstName The common first name
   * @param commonLastName The common last name
   * @param commonMiddleName The common middle name
   */
  public ClientUc(String pktableId,
      @OneOf(value = {"C", "N"}, ignoreCase = true, ignoreWhitespace = true) String sourceTableCode,
      String commonFirstName, String commonLastName, String commonMiddleName) {
    super();
    this.pktableId = pktableId;
    this.sourceTableCode = sourceTableCode;
    this.commonFirstName = commonFirstName;
    this.commonLastName = commonLastName;
    this.commonMiddleName = commonMiddleName;
  }


  /**
   * @param persistedClientUc - persistedClientUc object
   * 
   */
  public ClientUc(gov.ca.cwds.data.persistence.cms.ClientUc persistedClientUc) {
    this.pktableId = persistedClientUc.getPktableId();
    this.sourceTableCode = persistedClientUc.getSourceTableCode();
    this.commonFirstName = persistedClientUc.getCommonFirstName();
    this.commonLastName = persistedClientUc.getCommonLastName();
    this.commonMiddleName = persistedClientUc.getCommonMiddleName();
  }


  /**
   * @return the pktableId
   */
  public String getPktableId() {
    return pktableId;
  }

  /**
   * @return the sourceTableCode
   */
  public String getSourceTableCode() {
    return sourceTableCode;
  }

  /**
   * @return the commonFirstName
   */
  public String getCommonFirstName() {
    return commonFirstName;
  }

  /**
   * @return the commonLastName
   */
  public String getCommonLastName() {
    return commonLastName;
  }

  /**
   * @return the commonMiddleName
   */
  public String getCommonMiddleName() {
    return commonMiddleName;
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
    result = prime * result + ((commonFirstName == null) ? 0 : commonFirstName.hashCode());
    result = prime * result + ((commonLastName == null) ? 0 : commonLastName.hashCode());
    result = prime * result + ((commonMiddleName == null) ? 0 : commonMiddleName.hashCode());
    result = prime * result + ((pktableId == null) ? 0 : pktableId.hashCode());
    result = prime * result + ((sourceTableCode == null) ? 0 : sourceTableCode.hashCode());
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
    if (!(obj instanceof ClientUc)) {
      return false;
    }
    ClientUc other = (ClientUc) obj;
    if (commonFirstName == null) {
      if (other.commonFirstName != null) {
        return false;
      }
    } else if (!commonFirstName.equals(other.commonFirstName)) {
      return false;
    }
    if (commonLastName == null) {
      if (other.commonLastName != null) {
        return false;
      }
    } else if (!commonLastName.equals(other.commonLastName)) {
      return false;
    }
    if (commonMiddleName == null) {
      if (other.commonMiddleName != null) {
        return false;
      }
    } else if (!commonMiddleName.equals(other.commonMiddleName)) {
      return false;
    }
    if (pktableId == null) {
      if (other.pktableId != null) {
        return false;
      }
    } else if (!pktableId.equals(other.pktableId)) {
      return false;
    }
    if (sourceTableCode == null) {
      if (other.sourceTableCode != null) {
        return false;
      }
    } else if (!sourceTableCode.equals(other.sourceTableCode)) {
      return false;
    }
    return true;
  }

}
