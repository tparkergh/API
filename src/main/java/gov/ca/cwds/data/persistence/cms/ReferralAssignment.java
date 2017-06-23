package gov.ca.cwds.data.persistence.cms;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;

/**
 * {@link CmsPersistentObject} representing a Referral Assignment.
 * 
 * <p>
 * This entity bean wraps the Assignment table's "folded key" FK (virtual FK) on columns ESTBLISH_CD
 * and ESTBLSH_ID to either to Referral or Case, as dictated by ESTBLISH_CD.
 * </p>
 * 
 * @author CWDS API Team
 */
@Entity(name = "ReferralAssignment")
@DiscriminatorValue(value = "REFERRAL")
@PrimaryKeyJoinColumn(name = "IDENTIFIER")
@SuppressWarnings("serial")
public class ReferralAssignment extends BaseAssignment {

  private static final String FOLDED_KEY_CODE = "R";

  @Column(name = "ESTBLSH_ID", length = CMS_ID_LEN, insertable = false, updatable = false)
  protected String establishedForId;

  @OneToOne
  @JoinColumn(name = "IDENTIFIER", referencedColumnName = "ESTBLSH_ID")
  private Referral referral;

  /**
   * Default constructor.
   */
  public ReferralAssignment() {
    super();
  }

  /**
   * Construct from all fields.
   * 
   * @param countySpecificCode - county code of case load with this assignment
   * @param endDate - end date of assignment
   * @param endTime - end time of assignment
   * @param establishedForCode - referral or case
   * @param establishedForId - referral or case Id
   * @param fkCaseLoad - foreign key to the case load
   * @param fkOutOfStateContactParty - foreign ky to the out of state contact party
   * @param responsibilityDescription - description
   * @param secondaryAssignmentRoleType - primary or secondary
   * @param startDate - start date of assignment
   * @param startTime - end date of assignment
   * @param typeOfAssignmentCode - primary, secondary, or read only
   * @param weightingNumber - weighting within case load
   */
  public ReferralAssignment(String countySpecificCode, Date endDate, Date endTime,
      String establishedForCode, String establishedForId, String fkCaseLoad,
      String fkOutOfStateContactParty, String responsibilityDescription,
      Short secondaryAssignmentRoleType, Date startDate, Date startTime,
      String typeOfAssignmentCode, BigDecimal weightingNumber) {
    super(countySpecificCode, endDate, endTime, FOLDED_KEY_CODE, establishedForId, fkCaseLoad,
        fkOutOfStateContactParty, responsibilityDescription, secondaryAssignmentRoleType, startDate,
        startTime, typeOfAssignmentCode, weightingNumber);
  }

  public Referral getReferral() {
    return referral;
  }

  public void setReferral(Referral referral) {
    this.referral = referral;
  }

  @Override
  public String getEstablishedForId() {
    return establishedForId;
  }

  @Override
  public void setEstablishedForId(String establishedForId) {
    this.establishedForId = establishedForId;
  }

}
