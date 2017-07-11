package gov.ca.cwds.data.persistence.cms;

import gov.ca.cwds.data.CmsSystemCodeDeserializer;
import gov.ca.cwds.data.SystemCodeSerializer;
import gov.ca.cwds.data.persistence.PersistentObject;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * {@link PersistentObject} representing a Client Collateral.
 * 
 * @author CWDS API Team
 */
@Entity
@Table(name = "CLN_COLT")
@JsonPropertyOrder(alphabetic = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ClientCollateral extends CmsPersistentObject {

  private static final long serialVersionUID = 1L;

  @Column(name = "ACTIVE_IND")
  private String activeIndicator;

  @SystemCodeSerializer(logical = true, description = true)
  @JsonDeserialize(using = CmsSystemCodeDeserializer.class)
  @Type(type = "short")
  @Column(name = "COL_RELC")
  private Short collateralClientReporterRelationshipType;

  @Column(name = "COMNT_DSC")
  private String commentDescription;

  @Column(name = "FKCLIENT_T")
  private String clientId;

  @Column(name = "FKCOLTRL_T")
  private String collateralIndividualId;

  @Id
  @Column(name = "THIRD_ID", length = CMS_ID_LEN)
  private String thirdId;

  /**
   * Default constructor
   * 
   * Required for Hibernate
   */
  public ClientCollateral() {
    super();
  }

  /**
   * Constructor
   * 
   * @param activeIndicator = Indicates if relationship to CollateralIndividual is active (Y)
   * @param collateralClientReporterRelationshipType = Collateral Client Rptr Relationship Type in
   *        System Codes
   * @param commentDescription - description
   * @param clientId - foreign key Client table
   * @param collateralIndividualId - foreign key to CollateralIndividual table
   * @param thirdId - Unique key
   */
  public ClientCollateral(String activeIndicator, Short collateralClientReporterRelationshipType,
      String commentDescription, String clientId, String collateralIndividualId, String thirdId) {
    super();
    this.activeIndicator = activeIndicator;
    this.collateralClientReporterRelationshipType = collateralClientReporterRelationshipType;
    this.commentDescription = commentDescription;
    this.clientId = clientId;
    this.collateralIndividualId = collateralIndividualId;
    this.thirdId = thirdId;
  }

  /**
   * 
   * @param thirdId unique key
   * @param clientCollateral the domain object to construct this object from
   * @param lastUpdatedId the id of the last person to update this object
   */
  public ClientCollateral(String thirdId,
      gov.ca.cwds.rest.api.domain.cms.ClientCollateral clientCollateral, String lastUpdatedId) {
    super(lastUpdatedId);
    this.activeIndicator = clientCollateral.getActiveIndicator();
    this.collateralClientReporterRelationshipType =
        clientCollateral.getCollateralClientReporterRelationshipType();
    this.commentDescription = clientCollateral.getCommentDescription();
    this.clientId = clientCollateral.getClientId();
    this.collateralIndividualId = clientCollateral.getCollateralIndividualId();
    this.thirdId = thirdId;
  }


  /**
   * @return serialVersionUID
   */
  public static long getSerialversionuid() {
    return serialVersionUID;
  }

  @SuppressWarnings("javadoc")
  public String getActiveIndicator() {
    return activeIndicator;
  }

  @SuppressWarnings("javadoc")
  public Short getCollateralClientReporterRelationshipType() {
    return collateralClientReporterRelationshipType;
  }

  /**
   * @return commentDescription
   */
  public String getCommentDescription() {
    return commentDescription;
  }

  @SuppressWarnings("javadoc")
  public String getClientId() {
    return clientId;
  }

  @SuppressWarnings("javadoc")
  public String getCollateralIndividualId() {
    return collateralIndividualId;
  }

  @SuppressWarnings("javadoc")
  public String getThirdId() {
    return thirdId;
  }

  /*
   * (non-Javadoc)
   * 
   * @see gov.ca.cwds.rest.api.persistence.PersistentObject#getPrimaryKey()
   */
  @Override
  public String getPrimaryKey() {
    return getThirdId();
  }

  @Override
  public final int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((activeIndicator == null) ? 0 : activeIndicator.hashCode());
    result = prime * result + ((clientId == null) ? 0 : clientId.hashCode());
    result =
        prime
            * result
            + ((collateralClientReporterRelationshipType == null) ? 0
                : collateralClientReporterRelationshipType.hashCode());
    result =
        prime * result + ((collateralIndividualId == null) ? 0 : collateralIndividualId.hashCode());
    result = prime * result + ((commentDescription == null) ? 0 : commentDescription.hashCode());
    result = prime * result + ((thirdId == null) ? 0 : thirdId.hashCode());
    return result;
  }

  @Override
  public final boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    ClientCollateral other = (ClientCollateral) obj;
    if (activeIndicator == null) {
      if (other.activeIndicator != null)
        return false;
    } else if (!activeIndicator.equals(other.activeIndicator))
      return false;
    if (clientId == null) {
      if (other.clientId != null)
        return false;
    } else if (!clientId.equals(other.clientId))
      return false;
    if (collateralClientReporterRelationshipType == null) {
      if (other.collateralClientReporterRelationshipType != null)
        return false;
    } else if (!collateralClientReporterRelationshipType
        .equals(other.collateralClientReporterRelationshipType))
      return false;
    if (collateralIndividualId == null) {
      if (other.collateralIndividualId != null)
        return false;
    } else if (!collateralIndividualId.equals(other.collateralIndividualId))
      return false;
    if (commentDescription == null) {
      if (other.commentDescription != null)
        return false;
    } else if (!commentDescription.equals(other.commentDescription))
      return false;
    if (thirdId == null) {
      if (other.thirdId != null)
        return false;
    } else if (!thirdId.equals(other.thirdId))
      return false;
    return true;
  }

}
