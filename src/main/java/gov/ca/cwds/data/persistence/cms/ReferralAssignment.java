package gov.ca.cwds.data.persistence.cms;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("R")
public class ReferralAssignment extends Assignment {

  @Column(name = "ESTBLSH_ID", length = CMS_ID_LEN)
  private String establishedForId;

  public ReferralAssignment() {
    super();
  }

  public ReferralAssignment(String countySpecificCode, Date endDate, Date endTime,
      String establishedForCode, String establishedForId, String fkCaseLoad,
      String fkOutOfStateContactParty, String responsibilityDescription,
      Short secondaryAssignmentRoleType, Date startDate, Date startTime,
      String typeOfAssignmentCode, BigDecimal weightingNumber) {
    super(weightingNumber, endDate, endTime, startDate, startTime, secondaryAssignmentRoleType,
        countySpecificCode, establishedForCode, establishedForId, fkCaseLoad,
        fkOutOfStateContactParty, responsibilityDescription, typeOfAssignmentCode);
  }

}
