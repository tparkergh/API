package gov.ca.cwds.data.persistence.cms;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * {@link CmsPersistentObject} representing a Referral Assignment.
 * 
 * @author CWDS API Team
 */
@Entity(name = "ReferralAssignment")
@DiscriminatorValue(value = "REFERRAL")
@SuppressWarnings("serial")
public class ReferralAssignment extends BaseAssignment {

  private static final String FOLDED_KEY_CODE = "R";

  @Column(name = "ESTBLSH_ID", length = CMS_ID_LEN, insertable = false, updatable = false)
  protected String establishedForId;

  @ManyToOne
  @JoinColumn(name = "ESTBLSH_ID", referencedColumnName = "IDENTIFIER")
  private Referral referral;

  /**
   * Default constructor.
   */
  public ReferralAssignment() {
    super();
  }

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
