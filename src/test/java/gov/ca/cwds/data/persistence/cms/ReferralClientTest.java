package gov.ca.cwds.data.persistence.cms;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import gov.ca.cwds.data.persistence.junit.template.PersistentTestTemplate;
import gov.ca.cwds.rest.api.domain.DomainChef;
import io.dropwizard.jackson.Jackson;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

/**
 * @author CWDS API Team
 *
 */
public class ReferralClientTest implements PersistentTestTemplate {

  private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

  private final static DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
  private String referralId = "1234567ABC";
  private String clientId = "ABC1234567";
  private String approvalNumber = "A123";
  private Short approvalStatusType = 123;
  private Short dispositionClosureReasonType = 123;
  private String dispositionCode = "A";
  private Date dispositionDate = DomainChef.uncookDateString("2000-01-01");
  private String selfReportedIndicator = "N";
  private String staffPersonAddedIndicator = "N";
  private String dispositionClosureDescription = "description abc";
  private Short ageNumber = 12;
  private String agePeriodCode = "M";
  private String countySpecificCode = "AB";
  private String mentalHealthIssuesIndicator = "N";
  private String alcoholIndicator = "N";
  private String drugIndicator = "N";
  private String lastUpdatedId = "0X5";

  @Override
  @Test
  public void testEqualsHashCodeWorks() throws Exception {
    EqualsVerifier.forClass(ReferralClient.PrimaryKey.class).suppress(Warning.NONFINAL_FIELDS)
        .verify();
  }

  /*
   * Constructor test
   */
  @Override
  @Test
  public void testEmptyConstructor() throws Exception {
    assertThat(ReferralClient.class.newInstance(), is(notNullValue()));
  }


  @Override
  @Test
  public void testPersistentConstructor() throws Exception {

    ReferralClient prc =
        new ReferralClient(referralId, clientId, approvalNumber, approvalStatusType,
            dispositionClosureReasonType, dispositionCode, dispositionDate, selfReportedIndicator,
            staffPersonAddedIndicator, dispositionClosureDescription, ageNumber, agePeriodCode,
            countySpecificCode, mentalHealthIssuesIndicator, alcoholIndicator, drugIndicator);

    assertThat(prc.getReferralId(), is(equalTo(referralId)));
    assertThat(prc.getClientId(), is(equalTo(clientId)));
    assertThat(prc.getApprovalNumber(), is(equalTo(approvalNumber)));
    assertThat(prc.getApprovalStatusType(), is(equalTo(approvalStatusType)));
    assertThat(prc.getDispositionClosureReasonType(), is(equalTo(dispositionClosureReasonType)));
    assertThat(prc.getDispositionCode(), is(equalTo(dispositionCode)));
    assertThat(prc.getDispositionDate(), is(equalTo(dispositionDate)));
    assertThat(prc.getSelfReportedIndicator(), is(equalTo(selfReportedIndicator)));
    assertThat(prc.getStaffPersonAddedIndicator(), is(equalTo(staffPersonAddedIndicator)));

  }

  @Override
  @Test
  public void testConstructorUsingDomain() throws Exception {

    gov.ca.cwds.rest.api.domain.cms.ReferralClient domain = validReferralClient();

    ReferralClient persistent = new ReferralClient(domain, lastUpdatedId);

    assertThat(persistent.getReferralId(), is(equalTo(domain.getReferralId())));
    assertThat(persistent.getClientId(), is(equalTo(domain.getClientId())));
    assertThat(persistent.getApprovalNumber(), is(equalTo(domain.getApprovalNumber())));
    assertThat(persistent.getApprovalStatusType(), is(equalTo(domain.getApprovalStatusType())));
    assertThat(persistent.getDispositionClosureReasonType(),
        is(equalTo(domain.getDispositionClosureReasonType())));
    assertThat(persistent.getDispositionCode(), is(equalTo(domain.getDispositionCode())));
    assertThat(persistent.getDispositionDate(), is(equalTo(df.parse(domain.getDispositionDate()))));
    assertThat(persistent.getSelfReportedIndicator(),
        is(equalTo(DomainChef.cookBoolean(domain.getSelfReportedIndicator()))));
    assertThat(persistent.getStaffPersonAddedIndicator(),
        is(equalTo(DomainChef.cookBoolean(domain.getStaffPersonAddedIndicator()))));
    assertThat(persistent.getDispositionClosureDescription(),
        is(equalTo(domain.getDispositionClosureDescription())));
    assertThat(persistent.getAgeNumber(), is(equalTo(domain.getAgeNumber())));
    assertThat(persistent.getAgePeriodCode(), is(equalTo(domain.getAgePeriodCode())));
    assertThat(persistent.getCountySpecificCode(), is(equalTo(domain.getCountySpecificCode())));
    assertThat(persistent.getMentalHealthIssuesIndicator(),
        is(equalTo(DomainChef.cookBoolean(domain.getMentalHealthIssuesIndicator()))));
    assertThat(StringUtils.trimToNull(persistent.getAlcoholIndicator()),
        is(equalTo(DomainChef.cookBoolean(domain.getAlcoholIndicator()))));
    assertThat(persistent.getDrugIndicator(),
        is(equalTo(DomainChef.cookBoolean(domain.getDrugIndicator()))));
    assertThat(persistent.getLastUpdatedId(), is(equalTo(lastUpdatedId)));
  }

  private gov.ca.cwds.rest.api.domain.cms.ReferralClient validReferralClient()
      throws JsonParseException, JsonMappingException, IOException {
    gov.ca.cwds.rest.api.domain.cms.ReferralClient validDomainReferralClient =
        MAPPER.readValue(fixture("fixtures/domain/legacy/ReferralClient/valid/valid.json"),
            gov.ca.cwds.rest.api.domain.cms.ReferralClient.class);
    return validDomainReferralClient;

  }
}
