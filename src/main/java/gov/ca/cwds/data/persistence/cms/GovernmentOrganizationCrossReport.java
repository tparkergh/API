package gov.ca.cwds.data.persistence.cms;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * {@link CmsPersistentObject} Class representing an GovernmentOrganizationCrossReport.
 * 
 * @author CWDS API Team
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "GV_XRPT")
@JsonPropertyOrder(alphabetic = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class GovernmentOrganizationCrossReport extends CmsPersistentObject {

  @Id
  @Column(name = "THIRD_ID", length = CMS_ID_LEN)
  private String thirdId;

  @Column(name = "CNTY_SPFCD")
  private String countySpecificCode;

  @Column(name = "FKCRSS_RP0", length = CMS_ID_LEN)
  private String crossReportThirdId;

  @Column(name = "FKCRSS_RPT", length = CMS_ID_LEN)
  private String referralId;

  @Column(name = "FKGV_ORG_T", length = CMS_ID_LEN)
  private String governmentOrganizationId;

  @Column(name = "ORG_TY_IND")
  private String organizationTypeInd;

  /**
   * #147241489: referential integrity check.
   * <p>
   * Doesn't actually load the data. Just checks the existence of the parent referral record.
   * </p>
   */
  @ManyToOne(optional = false)
  @JoinColumn(name = "FKCRSS_RPT", nullable = false, updatable = false, insertable = false)
  private Referral referral;

  @ManyToOne(optional = false)
  @JoinColumn(name = "FKCRSS_RP0", nullable = false, updatable = false, insertable = false)
  private CrossReport crossreport;

  @ManyToOne(optional = true)
  @JoinColumn(name = "FKGV_ORG_T", nullable = true, updatable = false, insertable = false)
  private GovernmentOrganizationCrossReport governmentOrganizationCrossReportId;

  /**
   * Default constructor
   * 
   * Required for Hibernate
   */
  public GovernmentOrganizationCrossReport() {
    super();
  }

  /**
   * Constructor
   * 
   * @param thirdId primary key
   * @param countySpecificCode county Specific Code
   * @param crossReportThirdId cross Report ThirdId
   * @param referralId referralId
   * @param governmentOrganizationId government Organization Id
   * @param organizationTypeInd organization Type Ind
   */
  public GovernmentOrganizationCrossReport(String thirdId, String countySpecificCode,
      String crossReportThirdId, String referralId, String governmentOrganizationId,
      String organizationTypeInd) {
    super();
    this.thirdId = thirdId;
    this.countySpecificCode = countySpecificCode;
    this.crossReportThirdId = crossReportThirdId;
    this.referralId = referralId;
    this.governmentOrganizationId = governmentOrganizationId;
    this.organizationTypeInd = organizationTypeInd;
  }

  /**
   * Constructor using domain
   * 
   * @param thirdId the thirdId
   * @param domainGovernmentOrganizationCrossReport The domain object to construct this object from
   * @param lastUpdatedId The id of the last person to update this object
   * @param lastUpdatedTime The time when this object is last updated
   */
  public GovernmentOrganizationCrossReport(String thirdId,
      gov.ca.cwds.rest.api.domain.cms.GovernmentOrganizationCrossReport domainGovernmentOrganizationCrossReport,
      String lastUpdatedId, Date lastUpdatedTime) {
    super(lastUpdatedId, lastUpdatedTime);
    this.thirdId = domainGovernmentOrganizationCrossReport.getThirdId();
    this.countySpecificCode = domainGovernmentOrganizationCrossReport.getCountySpecificCode();
    this.crossReportThirdId = domainGovernmentOrganizationCrossReport.getCrossReportThirdId();
    this.referralId = domainGovernmentOrganizationCrossReport.getReferralId();
    this.governmentOrganizationId =
        StringUtils.isBlank(domainGovernmentOrganizationCrossReport.getGovernmentOrganizationId())
            ? null : domainGovernmentOrganizationCrossReport.getGovernmentOrganizationId();
    this.organizationTypeInd = domainGovernmentOrganizationCrossReport.getOrganizationTypeInd();
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.data.persistence.PersistentObject#getPrimaryKey()
   */
  @Override
  public String getPrimaryKey() {
    return getThirdId();
  }

  /**
   * @return the thirdId
   */
  public String getThirdId() {
    return thirdId;
  }

  /**
   * @return the countySpecificCode
   */
  public String getCountySpecificCode() {
    return countySpecificCode;
  }

  /**
   * @return the crossReportThirdId
   */
  public String getCrossReportThirdId() {
    return crossReportThirdId;
  }

  /**
   * @return the referralId
   */
  public String getReferralId() {
    return referralId;
  }

  /**
   * @return the governmentOrganizationId
   */
  public String getGovernmentOrganizationId() {
    return governmentOrganizationId;
  }

  /**
   * @return the organizationTypeInd
   */
  public String getOrganizationTypeInd() {
    return organizationTypeInd;
  }

}
