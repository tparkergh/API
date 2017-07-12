package gov.ca.cwds.data.persistence.cms;

import gov.ca.cwds.data.CmsSystemCodeDeserializer;
import gov.ca.cwds.data.SystemCodeSerializer;
import gov.ca.cwds.data.persistence.PersistentObject;
import gov.ca.cwds.rest.api.domain.DomainChef;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * {@link PersistentObject} representing a Client Relationship
 * 
 * @author CWDS API Team
 */
@Entity
@Table(name = "CLN_RELT")
@JsonPropertyOrder(alphabetic = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ClientRelationship extends CmsPersistentObject {

  private static final long serialVersionUID = 1L;

  @Column(name = "ABSENT_CD")
  private String absentParentCode;

  @SystemCodeSerializer(logical = true, description = true)
  @JsonDeserialize(using = CmsSystemCodeDeserializer.class)
  @Type(type = "short")
  @Column(name = "CLNTRELC")
  private Short clientRelationshipType;

  @Type(type = "date")
  @Column(name = "END_DT")
  private Date endDate;

  @Column(name = "FKCLIENT_0")
  private String secondaryClientId;

  @Column(name = "FKCLIENT_T")
  private String primaryClientId;

  @Id
  @Column(name = "IDENTIFIER", length = CMS_ID_LEN)
  private String id;

  @Column(name = "SAME_HM_CD")
  private String sameHomeCode;

  @Type(type = "date")
  @Column(name = "START_DT")
  private Date startDate;


  /**
   * Default constructor
   * 
   * Required for Hibernate
   */
  public ClientRelationship() {
    super();
  }


  /**
   * Constructor
   * 
   * @param absentParentCode indicates if the parent CLIENT is absent for the child with whom the
   *        relationship is being defined (N)
   * @param clientRelationshipType Client Relationship Type from System Code table
   * @param endDate date the relationship ended
   * @param secondaryClientId Mandatory Foreign key that includes a secondary individual as a CLIENT
   * @param primaryClientId Mandatory Foreign key that includes a primary individual as a CLIENT
   * @param id unique key
   * @param sameHomeCode indicates whether the two CLIENTs live in the same home (Y)
   * @param startDate date the relationship began
   */
  public ClientRelationship(String absentParentCode, Short clientRelationshipType, Date endDate,
      String secondaryClientId, String primaryClientId, String id, String sameHomeCode,
      Date startDate) {
    super();
    this.absentParentCode = absentParentCode;
    this.clientRelationshipType = clientRelationshipType;
    this.endDate = endDate;
    this.secondaryClientId = secondaryClientId;
    this.primaryClientId = primaryClientId;
    this.id = id;
    this.sameHomeCode = sameHomeCode;
    this.startDate = startDate;
  }


  /**
   * 
   * @param id unique key
   * @param clientRelationship the domain object to construct this object from
   * @param lastUpdatedId the id of the last person to update this object
   */
  public ClientRelationship(String id,
      gov.ca.cwds.rest.api.domain.cms.ClientRelationship clientRelationship, String lastUpdatedId) {
    super(lastUpdatedId);
    this.absentParentCode = clientRelationship.getAbsentParentCode();
    this.clientRelationshipType = clientRelationship.getClientRelationshipType();
    this.endDate = DomainChef.uncookDateString(clientRelationship.getEndDate());
    this.secondaryClientId = clientRelationship.getSecondaryClientId();
    this.primaryClientId = clientRelationship.getPrimaryClientId();
    this.id = id;
    this.sameHomeCode = clientRelationship.getSameHomeCode();
    this.startDate = DomainChef.uncookDateString(clientRelationship.getStartDate());
  }

  /*
   * (non-Javadoc)
   * 
   * @see gov.ca.cwds.rest.api.persistence.PersistentObject#getPrimaryKey()
   */
  @Override
  public String getPrimaryKey() {
    return getId();
  }

  /**
   * @return the absentParentCode
   */
  public String getAbsentParentCode() {
    return absentParentCode;
  }

  /**
   * @return the clientRelationshipType
   */
  public Short getClientRelationshipType() {
    return clientRelationshipType;
  }

  /**
   * @return the endDate
   */
  public Date getEndDate() {
    return endDate;
  }

  /**
   * @return the secondaryClientId
   */
  public String getSecondaryClientId() {
    return secondaryClientId;
  }

  /**
   * @return the primaryClientId
   */
  public String getPrimaryClientId() {
    return primaryClientId;
  }

  /**
   * @return the id
   */
  public String getId() {
    return id;
  }

  /**
   * @return the sameHomeCode
   */
  public String getSameHomeCode() {
    return StringUtils.trimToEmpty(sameHomeCode);
  }

  /**
   * @return the startDate
   */
  public Date getStartDate() {
    return startDate;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((absentParentCode == null) ? 0 : absentParentCode.hashCode());
    result =
        prime * result + ((clientRelationshipType == null) ? 0 : clientRelationshipType.hashCode());
    result = prime * result + ((endDate == null) ? 0 : endDate.hashCode());
    result = prime * result + ((secondaryClientId == null) ? 0 : secondaryClientId.hashCode());
    result = prime * result + ((primaryClientId == null) ? 0 : primaryClientId.hashCode());
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    result = prime * result + ((sameHomeCode == null) ? 0 : sameHomeCode.hashCode());
    result = prime * result + ((startDate == null) ? 0 : startDate.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    ClientRelationship other = (ClientRelationship) obj;
    if (absentParentCode == null) {
      if (other.absentParentCode != null) {
        return false;
      }
    } else if (!absentParentCode.equals(other.absentParentCode)) {
      return false;
    }
    if (clientRelationshipType == null) {
      if (other.clientRelationshipType != null) {
        return false;
      }
    } else if (!clientRelationshipType.equals(other.clientRelationshipType)) {
      return false;
    }
    if (endDate == null) {
      if (other.endDate != null) {
        return false;
      }
    } else if (!endDate.equals(other.endDate)) {
      return false;
    }
    if (secondaryClientId == null) {
      if (other.secondaryClientId != null) {
        return false;
      }
    } else if (!secondaryClientId.equals(other.secondaryClientId)) {
      return false;
    }
    if (primaryClientId == null) {
      if (other.primaryClientId != null) {
        return false;
      }
    } else if (!primaryClientId.equals(other.primaryClientId)) {
      return false;
    }
    if (id == null) {
      if (other.id != null) {
        return false;
      }
    } else if (!id.equals(other.id)) {
      return false;
    }
    if (sameHomeCode == null) {
      if (other.sameHomeCode != null) {
        return false;
      }
    } else if (!sameHomeCode.equals(other.sameHomeCode)) {
      return false;
    }
    if (startDate == null) {
      if (other.startDate != null) {
        return false;
      }
    } else if (!startDate.equals(other.startDate)) {
      return false;
    }
    return true;
  }


}
