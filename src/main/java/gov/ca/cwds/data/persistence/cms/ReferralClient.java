package gov.ca.cwds.data.persistence.cms;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.PersistenceException;
import javax.persistence.Table;

import org.hibernate.annotations.NamedQuery;
import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import gov.ca.cwds.data.CmsSystemCodeDeserializer;
import gov.ca.cwds.data.SystemCodeSerializer;
import gov.ca.cwds.rest.api.ApiException;
import gov.ca.cwds.rest.api.domain.DomainChef;

/**
 * {@link CmsPersistentObject} representing a ReferralClient in the replicated schema.
 * 
 * @author CWDS API Team
 */

@NamedQuery(name = "gov.ca.cwds.data.persistence.cms.ReferralClient.findByReferral",
    query = "FROM ReferralClient WHERE referralId = :referralId")
@NamedQuery(name = "gov.ca.cwds.data.persistence.cms.ReferralClient.findByClient",
    query = "FROM ReferralClient WHERE clientId = :clientId")
@SuppressWarnings("serial")
@Entity
@Table(name = "REFR_CLT")
@IdClass(ReferralClient.PrimaryKey.class)
@JsonPropertyOrder(alphabetic = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReferralClient extends CmsPersistentObject {

  /**
   * Hibernate annotation {@link IdClass} requires that members match the id columns of the parent
   * class. From the Javadoc of said annotation,
   * 
   * <blockquote> "The names of the fields or properties in the primary key class and the primary
   * key fields or properties of the entity must correspond and their types must be the same."
   * </blockquote>
   * 
   * @see VarargPrimaryKey
   */
  public static final class PrimaryKey implements Serializable {
    private static final long serialVersionUID = 1L;

    private String referralId;
    private String clientId;

    /**
     * Default constructor.
     */
    public PrimaryKey() {
      // Default values.
    }

    /**
     * Construct from foreign keys.
     * 
     * @param referralId referral Id
     * @param clientId client Id
     */
    public PrimaryKey(String referralId, String clientId) {
      this.referralId = referralId;
      this.clientId = clientId;
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
      return "referralId=" + referralId.trim() + ",clientId=" + clientId.trim();
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((clientId == null) ? 0 : clientId.hashCode());
      result = prime * result + ((referralId == null) ? 0 : referralId.hashCode());
      return result;
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
      if (this == obj)
        return true;
      if (obj == null)
        return false;
      if (getClass() != obj.getClass())
        return false;
      PrimaryKey other = (PrimaryKey) obj;
      if (clientId == null) {
        if (other.clientId != null)
          return false;
      } else if (!clientId.equals(other.clientId))
        return false;
      if (referralId == null) {
        if (other.referralId != null)
          return false;
      } else if (!referralId.equals(other.referralId))
        return false;
      return true;
    }
  }

  @Id
  @Column(name = "FKREFERL_T", length = CMS_ID_LEN)
  private String referralId;

  @Id
  @Column(name = "FKCLIENT_T", length = CMS_ID_LEN)
  private String clientId;

  @Column(name = "APRVL_NO", length = CMS_ID_LEN)
  private String approvalNumber;

  @SystemCodeSerializer(logical = true, description = true)
  @JsonDeserialize(using = CmsSystemCodeDeserializer.class)
  @Type(type = "short")
  @Column(name = "APV_STC")
  private Short approvalStatusType;

  @SystemCodeSerializer(logical = true, description = true)
  @JsonDeserialize(using = CmsSystemCodeDeserializer.class)
  @Type(type = "short")
  @Column(name = "DSP_RSNC")
  private Short dispositionClosureReasonType;

  @Column(name = "DISPSTN_CD")
  private String dispositionCode;

  @Type(type = "date")
  @Column(name = "RCL_DISPDT")
  private Date dispositionDate;

  @Column(name = "SLFRPT_IND")
  private String selfReportedIndicator;

  @Column(name = "STFADD_IND")
  private String staffPersonAddedIndicator;

  @Column(name = "DSP_CLSDSC")
  private String dispositionClosureDescription;

  @Type(type = "short")
  @Column(name = "RFCL_AGENO")
  private Short ageNumber;

  @Column(name = "AGE_PRD_CD")
  private String agePeriodCode;

  @Column(name = "CNTY_SPFCD")
  private String countySpecificCode;

  @Column(name = "MHLTH_IND")
  private String mentalHealthIssuesIndicator;

  @Column(name = "ALCHL_IND")
  private String alcoholIndicator;

  @Column(name = "DRUG_IND")
  private String drugIndicator;

  /**
   * Default constructor
   * 
   * Required for Hibernate
   */
  public ReferralClient() {
    super();
  }

  /**
   * @param referralId - ID of Referral
   * @param clientId = ID of Client
   * @param approvalNumber - approval number
   * @param approvalStatusType - approval status type
   * @param dispositionClosureReasonType - disposition closure reason type
   * @param dispositionCode - disposition code
   * @param dispositionDate - disposition date
   * @param selfReportedIndicator = self reporter indicator
   * @param staffPersonAddedIndicator - staff person added indicator
   * @param dispositionClosureDescription - disposition closure description
   * @param ageNumber - age number
   * @param agePeriodCode - age period code
   * @param countySpecificCode - county specified code
   * @param mentalHealthIssuesIndicator - mental health issues indicator
   * @param alcoholIndicator - alcohol indicator
   * @param drugIndicator - drug indicator
   */
  public ReferralClient(String referralId, String clientId, String approvalNumber,
      Short approvalStatusType, Short dispositionClosureReasonType, String dispositionCode,
      Date dispositionDate, String selfReportedIndicator, String staffPersonAddedIndicator,
      String dispositionClosureDescription, Short ageNumber, String agePeriodCode,
      String countySpecificCode, String mentalHealthIssuesIndicator, String alcoholIndicator,
      String drugIndicator) {
    super();
    this.referralId = referralId;
    this.clientId = clientId;
    this.approvalNumber = approvalNumber;
    this.approvalStatusType = approvalStatusType;
    this.dispositionClosureReasonType = dispositionClosureReasonType;
    this.dispositionCode = dispositionCode;
    this.dispositionDate = dispositionDate;
    this.selfReportedIndicator = selfReportedIndicator;
    this.staffPersonAddedIndicator = staffPersonAddedIndicator;
    this.dispositionClosureDescription = dispositionClosureDescription;
    this.ageNumber = ageNumber;
    this.agePeriodCode = agePeriodCode;
    this.countySpecificCode = countySpecificCode;
    this.mentalHealthIssuesIndicator = mentalHealthIssuesIndicator;
    this.alcoholIndicator = alcoholIndicator;
    this.drugIndicator = drugIndicator;
  }

  /**
   * @param referralClient - ID of Client
   * @param lastUpdateId - ID of Staff
   */
  public ReferralClient(gov.ca.cwds.rest.api.domain.cms.ReferralClient referralClient,
      String lastUpdateId) {
    super(lastUpdateId);
    init(referralClient);
  }

  /**
   * Constructor
   * 
   * @param referralClient The domain object to construct this object from
   * @param lastUpdatedId the id of the last person to update this object
   * @param lastUpdatedTime the time of last person to update this object
   */
  public ReferralClient(gov.ca.cwds.rest.api.domain.cms.ReferralClient referralClient,
      String lastUpdatedId, Date lastUpdatedTime) {
    super(lastUpdatedId, lastUpdatedTime);
    init(referralClient);
  }

  /**
   * @param referralClient - referralClient
   */
  private void init(gov.ca.cwds.rest.api.domain.cms.ReferralClient referralClient) {
    try {
      this.referralId = referralClient.getReferralId();
      this.clientId = referralClient.getClientId();
      this.approvalNumber = referralClient.getApprovalNumber();
      this.approvalStatusType = referralClient.getApprovalStatusType();
      this.dispositionClosureReasonType = referralClient.getDispositionClosureReasonType();
      this.dispositionCode = referralClient.getDispositionCode();
      this.dispositionDate = DomainChef.uncookDateString(referralClient.getDispositionDate());
      this.selfReportedIndicator =
          DomainChef.cookBoolean(referralClient.getSelfReportedIndicator());
      this.staffPersonAddedIndicator =
          DomainChef.cookBoolean(referralClient.getStaffPersonAddedIndicator());
      this.dispositionClosureDescription = referralClient.getDispositionClosureDescription();
      this.ageNumber = referralClient.getAgeNumber();
      this.agePeriodCode = referralClient.getAgePeriodCode();
      this.countySpecificCode = referralClient.getCountySpecificCode();
      this.mentalHealthIssuesIndicator =
          DomainChef.cookBoolean(referralClient.getMentalHealthIssuesIndicator());
      this.alcoholIndicator = DomainChef.cookBoolean(referralClient.getAlcoholIndicator());
      this.drugIndicator = DomainChef.cookBoolean(referralClient.getDrugIndicator());

    } catch (ApiException e) {
      throw new PersistenceException(e);
    }
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.data.persistence.PersistentObject#getPrimaryKey()
   */
  @Override
  @JsonIgnore
  public Serializable getPrimaryKey() {
    return new PrimaryKey(this.getReferralId(), this.getClientId());
  }

  /**
   * @return the referralId
   */
  public String getReferralId() {
    return referralId;
  }

  /**
   * @return the clientId
   */
  public String getClientId() {
    return clientId;
  }

  /**
   * @return the approvalNumber
   */
  public String getApprovalNumber() {
    return approvalNumber;
  }

  /**
   * @return the approvalStatusType
   */
  public Short getApprovalStatusType() {
    return approvalStatusType;
  }

  /**
   * @return the dispositionClosureReasonType
   */
  public Short getDispositionClosureReasonType() {
    return dispositionClosureReasonType;
  }

  /**
   * @return the dispositionCode
   */
  public String getDispositionCode() {
    return dispositionCode;
  }

  /**
   * @return the dispositionDate
   */
  public Date getDispositionDate() {
    return dispositionDate;
  }

  /**
   * @return the selfReportedIndicator
   */
  public String getSelfReportedIndicator() {
    return selfReportedIndicator;
  }

  /**
   * @return the staffPersonAddedIndicator
   */
  public String getStaffPersonAddedIndicator() {
    return staffPersonAddedIndicator;
  }

  /**
   * @return the dispositionClosureDescription
   */
  public String getDispositionClosureDescription() {
    return dispositionClosureDescription;
  }

  /**
   * @return the ageNumber
   */
  public Short getAgeNumber() {
    return ageNumber;
  }

  /**
   * @return the agePeriodCode
   */
  public String getAgePeriodCode() {
    return agePeriodCode;
  }

  /**
   * @return the countySpecificCode
   */
  public String getCountySpecificCode() {
    return countySpecificCode;
  }

  /**
   * @return the mentalHealthIssuesIndicator
   */
  public String getMentalHealthIssuesIndicator() {
    return mentalHealthIssuesIndicator;
  }

  /**
   * @return the alcoholIndicator
   */
  public String getAlcoholIndicator() {
    return alcoholIndicator;
  }

  /**
   * @return the drugIndicator
   */
  public String getDrugIndicator() {
    return drugIndicator;
  }

}
