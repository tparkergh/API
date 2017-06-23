package gov.ca.cwds.data.persistence.cms;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * {@link CmsPersistentObject} representing a Referral Assignment.
 * 
 * @author CWDS API Team
 */
@Entity(name = "ReferralAssignment")
@DiscriminatorValue(value = "REFERRAL")
@SuppressWarnings("serial")
public class ReferralAssignment extends Assignment {

  private static final String FOLDED_KEY_CODE = "R";

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

}
