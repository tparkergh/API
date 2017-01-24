package gov.ca.cwds.data.persistence.cms;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import gov.ca.cwds.data.persistence.junit.template.PersistentTestTemplate;
import gov.ca.cwds.rest.api.domain.DomainChef;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

/**
 * @author CWDS API Team
 *
 */
public class ReferralClientTest implements PersistentTestTemplate {

  private static final ObjectMapper MAPPER = SystemCodeTestHarness.MAPPER;

  private final static DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
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

    ReferralClient prc = validReferralClient();

    ReferralClient pers = new ReferralClient(prc.getReferralId(), prc.getClientId(),
        prc.getApprovalNumber(), prc.getApprovalStatusType(), prc.getDispositionClosureReasonType(),
        prc.getDispositionCode(), prc.getDispositionDate(), prc.getSelfReportedIndicator(),
        prc.getStaffPersonAddedIndicator(), prc.getDispositionClosureDescription(),
        prc.getAgeNumber(), prc.getAgePeriodCode(), prc.getCountySpecificCode(),
        prc.getMentalHealthIssuesIndicator(), prc.getAlcoholIndicator(), prc.getDrugIndicator());

    assertThat(prc.getReferralId(), is(equalTo(pers.getReferralId())));
    assertThat(prc.getClientId(), is(equalTo(pers.getClientId())));
    assertThat(prc.getApprovalNumber(), is(equalTo(pers.getApprovalNumber())));
    assertThat(prc.getApprovalStatusType(), is(equalTo(pers.getApprovalStatusType())));
    assertThat(prc.getDispositionClosureReasonType(),
        is(equalTo(pers.getDispositionClosureReasonType())));
    assertThat(prc.getDispositionClosureDescription(),
        is(equalTo(pers.getDispositionClosureDescription())));
    assertThat(prc.getDispositionCode(), is(equalTo(pers.getDispositionCode())));
    assertThat(prc.getDispositionDate(), is(equalTo(pers.getDispositionDate())));
    assertThat(prc.getSelfReportedIndicator(), is(equalTo(pers.getSelfReportedIndicator())));
    assertThat(prc.getStaffPersonAddedIndicator(),
        is(equalTo(pers.getStaffPersonAddedIndicator())));
    assertThat(prc.getDispositionClosureDescription(),
        is(equalTo(pers.getDispositionClosureDescription())));
    assertThat(prc.getAgeNumber(), is(equalTo(pers.getAgeNumber())));
    assertThat(prc.getAgePeriodCode(), is(equalTo(pers.getAgePeriodCode())));
    assertThat(prc.getCountySpecificCode(), is(equalTo(pers.getCountySpecificCode())));
    assertThat(prc.getMentalHealthIssuesIndicator(),
        is(equalTo(pers.getMentalHealthIssuesIndicator())));
    assertThat(prc.getAlcoholIndicator(), is(equalTo(pers.getAlcoholIndicator())));
    assertThat(prc.getDrugIndicator(), is(equalTo(pers.getDrugIndicator())));
  }

  @Override
  @Test
  public void testConstructorUsingDomain() throws Exception {

    gov.ca.cwds.rest.api.domain.cms.ReferralClient domain = validDomainReferralClient();

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

  @SuppressWarnings("javadoc")
  @Test
  public void testSerializeAndDeserialize() throws Exception {

    ReferralClient prc = validReferralClient();

    ReferralClient pers = new ReferralClient(prc.getReferralId(), prc.getClientId(),
        prc.getApprovalNumber(), prc.getApprovalStatusType(), prc.getDispositionClosureReasonType(),
        prc.getDispositionCode(), prc.getDispositionDate(), prc.getSelfReportedIndicator(),
        prc.getStaffPersonAddedIndicator(), prc.getDispositionClosureDescription(),
        prc.getAgeNumber(), prc.getAgePeriodCode(), prc.getCountySpecificCode(),
        prc.getMentalHealthIssuesIndicator(), prc.getAlcoholIndicator(), prc.getDrugIndicator());

    final String expected = MAPPER.writeValueAsString((MAPPER.readValue(
        fixture("fixtures/persistent/ReferralClient/valid/validWithSysCodes.json"),
        ReferralClient.class)));

    assertThat(MAPPER.writeValueAsString(pers)).isEqualTo(expected);

  }


  private ReferralClient validReferralClient()
      throws JsonParseException, JsonMappingException, IOException {
    ReferralClient rc = MAPPER.readValue(
        fixture("fixtures/persistent/ReferralClient/valid/valid.json"), ReferralClient.class);
    return rc;
  }

  private gov.ca.cwds.rest.api.domain.cms.ReferralClient validDomainReferralClient()
      throws JsonParseException, JsonMappingException, IOException {
    gov.ca.cwds.rest.api.domain.cms.ReferralClient validDomainReferralClient =
        MAPPER.readValue(fixture("fixtures/domain/legacy/ReferralClient/valid/valid.json"),
            gov.ca.cwds.rest.api.domain.cms.ReferralClient.class);
    return validDomainReferralClient;

  }
}
