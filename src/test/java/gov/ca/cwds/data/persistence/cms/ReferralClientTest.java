package gov.ca.cwds.data.persistence.cms;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import gov.ca.cwds.data.persistence.cms.ReferralClient;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

public class ReferralClientTest {

  private final static DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
  private String referralId = "a";
  private String clientId = "b";
  private String approvalNumber = "c";
  private Short approvalStatusType = 1;
  private Short dispositionClosureReasonType = 2;
  private String dispositionCode = "d";
  private String dispositionDate = "1973-11-22";
  private Boolean selfReportedIndicator = Boolean.TRUE;
  private Boolean staffPersonAddedIndicator = Boolean.FALSE;
  private String dispositionClosureDescription = "e";
  private Short ageNumber = 3;
  private String agePeriodCode = "f";
  private String countySpecificCode = "g";
  private Boolean mentalHealthIssuesIndicator = Boolean.TRUE;
  private Boolean alcoholIndicator = null;
  private Boolean drugIndicator = Boolean.FALSE;
  private String lastUpdatedId = "h";

  /*
   * Constructor test
   */
  @Test
  public void emtpyConstructorIsNotNull() throws Exception {
    assertThat(ReferralClient.class.newInstance(), is(notNullValue()));
  }

  @Test
  public void equalsHashCodeWork() {
    EqualsVerifier.forClass(ReferralClient.PrimaryKey.class).suppress(Warning.NONFINAL_FIELDS)
        .verify();
  }

  @Test
  public void domainReferralClientLastUpdateConstructorTest() throws Exception {
    gov.ca.cwds.rest.api.domain.cms.ReferralClient domain =
        new gov.ca.cwds.rest.api.domain.cms.ReferralClient(approvalNumber, approvalStatusType,
            dispositionClosureReasonType, dispositionCode, dispositionDate, selfReportedIndicator,
            staffPersonAddedIndicator, referralId, clientId, dispositionClosureDescription,
            ageNumber, agePeriodCode, countySpecificCode, mentalHealthIssuesIndicator,
            alcoholIndicator, drugIndicator);

    ReferralClient persistent = new ReferralClient(domain, lastUpdatedId);
    assertThat(persistent.getReferralId(), is(equalTo(referralId)));
    assertThat(persistent.getClientId(), is(equalTo(clientId)));
    assertThat(persistent.getApprovalNumber(), is(equalTo(approvalNumber)));
    assertThat(persistent.getApprovalStatusType(), is(equalTo(approvalStatusType)));
    assertThat(persistent.getDispositionClosureReasonType(),
        is(equalTo(dispositionClosureReasonType)));
    assertThat(persistent.getDispositionCode(), is(equalTo(dispositionCode)));
    assertThat(persistent.getDispositionDate(), is(equalTo(df.parse(dispositionDate))));
    assertThat(persistent.getSelfReportedIndicator(), is(equalTo("Y")));
    assertThat(persistent.getStaffPersonAddedIndicator(), is(equalTo("N")));
    assertThat(persistent.getDispositionClosureDescription(),
        is(equalTo(dispositionClosureDescription)));
    assertThat(persistent.getAgeNumber(), is(equalTo(ageNumber)));
    assertThat(persistent.getAgePeriodCode(), is(equalTo(agePeriodCode)));
    assertThat(persistent.getCountySpecificCode(), is(equalTo(countySpecificCode)));
    assertThat(persistent.getMentalHealthIssuesIndicator(), is(equalTo("Y")));
    assertThat(StringUtils.trimToNull(persistent.getAlcoholIndicator()), is(nullValue()));
    assertThat(persistent.getDrugIndicator(), is(equalTo("N")));
    assertThat(persistent.getLastUpdatedId(), is(equalTo(lastUpdatedId)));
  }

}
