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
public class CrossReportTest implements PersistentTestTemplate {
  private final static DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
  private final static DateFormat tf = new SimpleDateFormat("HH:mm:ss");
  private static final ObjectMapper MAPPER = SystemCodeTestHarness.MAPPER;

  private String lastUpdatedId = "ABC";


  @Override
  @Test
  public void testEqualsHashCodeWorks() throws Exception {
    EqualsVerifier.forClass(CrossReport.class).suppress(Warning.NONFINAL_FIELDS).verify();
  }

  /*
   * Constructor test
   */
  @Override
  @Test
  public void testEmptyConstructor() throws Exception {
    assertThat(CrossReport.class.newInstance(), is(notNullValue()));
  }

  @Override
  @Test
  public void testConstructorUsingDomain() throws Exception {

    gov.ca.cwds.rest.api.domain.cms.CrossReport domain = validDomainCrossReport();

    CrossReport persistent = new CrossReport(domain.getThirdId(), domain, lastUpdatedId);

    assertThat(persistent.getThirdId(), is(equalTo(domain.getThirdId())));
    assertThat(persistent.getCrossReportMethodType(),
        is(equalTo(domain.getCrossReportMethodType())));
    assertThat(persistent.getFiledOutOfStateIndicator(),
        is(equalTo(DomainChef.cookBoolean(domain.getFiledOutOfStateIndicator()))));
    assertThat(persistent.getGovernmentOrgCrossRptIndicatorVar(),
        is(equalTo(DomainChef.cookBoolean(domain.getGovernmentOrgCrossRptIndicatorVar()))));
    assertThat(persistent.getInformTime(), is(equalTo(tf.parse(domain.getInformTime()))));
    assertThat(persistent.getRecipientBadgeNumber(), is(equalTo(domain.getRecipientBadgeNumber())));
    assertThat(persistent.getRecipientPhoneExtensionNumber(),
        is(equalTo(domain.getRecipientPhoneExtensionNumber())));
    assertThat(persistent.getRecipientPhoneNumber(), is(equalTo(domain.getRecipientPhoneNumber())));
    assertThat(persistent.getInformDate(), is(equalTo(df.parse(domain.getInformDate()))));
    assertThat(persistent.getRecipientPositionTitleDesc(),
        is(equalTo(domain.getRecipientPositionTitleDesc())));
    assertThat(persistent.getReferenceNumber(), is(equalTo(domain.getReferenceNumber())));
    assertThat(persistent.getReferralId(), is(equalTo(domain.getReferralId())));
    assertThat(persistent.getLawEnforcementId(), is(equalTo(domain.getLawEnforcementId())));
    assertThat(persistent.getStaffPersonId(), is(equalTo(domain.getStaffPersonId())));
    assertThat(persistent.getDescription(), is(equalTo(domain.getDescription())));
    assertThat(persistent.getRecipientName(), is(equalTo(domain.getRecipientName())));
    assertThat(persistent.getOutStateLawEnforcementAddr(),
        is(equalTo(domain.getOutStateLawEnforcementAddr())));
    assertThat(persistent.getCountySpecificCode(), is(equalTo(domain.getCountySpecificCode())));
    assertThat(persistent.getLawEnforcementIndicator(),
        is(equalTo(DomainChef.cookBoolean(domain.getLawEnforcementIndicator()))));
    assertThat(persistent.getOutStateLawEnforcementIndicator(),
        is(equalTo(DomainChef.cookBoolean(domain.getOutStateLawEnforcementIndicator()))));
    assertThat(persistent.getSatisfyCrossReportIndicator(),
        is(equalTo(DomainChef.cookBoolean(domain.getSatisfyCrossReportIndicator()))));
    assertThat(persistent.getLastUpdatedId(), is(equalTo(lastUpdatedId)));
  }

  @Override
  @Test
  public void testPersistentConstructor() throws Exception {

    CrossReport vcp = validCrossReport();
    CrossReport persistent = new CrossReport(vcp.getReferralId(), vcp.getThirdId(),
        vcp.getCrossReportMethodType(), vcp.getFiledOutOfStateIndicator(),
        vcp.getGovernmentOrgCrossRptIndicatorVar(), vcp.getInformTime(),
        vcp.getRecipientBadgeNumber(), vcp.getRecipientPhoneExtensionNumber(),
        vcp.getRecipientPhoneNumber(), vcp.getInformDate(), vcp.getRecipientPositionTitleDesc(),
        vcp.getReferenceNumber(), vcp.getLawEnforcementId(), vcp.getStaffPersonId(),
        vcp.getDescription(), vcp.getRecipientName(), vcp.getOutStateLawEnforcementAddr(),
        vcp.getCountySpecificCode(), vcp.getLawEnforcementIndicator(),
        vcp.getOutStateLawEnforcementIndicator(), vcp.getSatisfyCrossReportIndicator());

    assertThat(persistent.getThirdId(), is(equalTo(vcp.getThirdId())));
    assertThat(persistent.getCrossReportMethodType(), is(equalTo(vcp.getCrossReportMethodType())));
    assertThat(persistent.getFiledOutOfStateIndicator(),
        is(equalTo(vcp.getFiledOutOfStateIndicator())));
    assertThat(persistent.getGovernmentOrgCrossRptIndicatorVar(),
        is(equalTo(vcp.getGovernmentOrgCrossRptIndicatorVar())));
    assertThat(persistent.getInformTime(), is(equalTo(vcp.getInformTime())));
    assertThat(persistent.getRecipientBadgeNumber(), is(equalTo(vcp.getRecipientBadgeNumber())));
    assertThat(persistent.getRecipientPhoneExtensionNumber(),
        is(equalTo(vcp.getRecipientPhoneExtensionNumber())));
    assertThat(persistent.getRecipientPhoneNumber(), is(equalTo(vcp.getRecipientPhoneNumber())));
    assertThat(persistent.getInformDate(), is(equalTo(vcp.getInformDate())));
    assertThat(persistent.getRecipientPositionTitleDesc(),
        is(equalTo(vcp.getRecipientPositionTitleDesc())));
    assertThat(persistent.getReferenceNumber(), is(equalTo(vcp.getReferenceNumber())));
    assertThat(persistent.getReferralId(), is(equalTo(vcp.getReferralId())));
    assertThat(persistent.getLawEnforcementId(), is(equalTo(vcp.getLawEnforcementId())));
    assertThat(persistent.getStaffPersonId(), is(equalTo(vcp.getStaffPersonId())));
    assertThat(persistent.getDescription(), is(equalTo(vcp.getDescription())));
    assertThat(persistent.getRecipientName(), is(equalTo(vcp.getRecipientName())));
    assertThat(persistent.getOutStateLawEnforcementAddr(),
        is(equalTo(vcp.getOutStateLawEnforcementAddr())));
    assertThat(persistent.getCountySpecificCode(), is(equalTo(vcp.getCountySpecificCode())));
    assertThat(persistent.getLawEnforcementIndicator(),
        is(equalTo(vcp.getLawEnforcementIndicator())));
    assertThat(persistent.getOutStateLawEnforcementIndicator(),
        is(equalTo(vcp.getOutStateLawEnforcementIndicator())));
    assertThat(persistent.getSatisfyCrossReportIndicator(),
        is(equalTo(vcp.getSatisfyCrossReportIndicator())));
  }

  @SuppressWarnings("javadoc")
  @Test
  public void testSerializeAndDeserialize() throws Exception {

    CrossReport vcp = validCrossReport();
    CrossReport persistent = new CrossReport(vcp.getReferralId(), vcp.getThirdId(),
        vcp.getCrossReportMethodType(), vcp.getFiledOutOfStateIndicator(),
        vcp.getGovernmentOrgCrossRptIndicatorVar(), vcp.getInformTime(),
        vcp.getRecipientBadgeNumber(), vcp.getRecipientPhoneExtensionNumber(),
        vcp.getRecipientPhoneNumber(), vcp.getInformDate(), vcp.getRecipientPositionTitleDesc(),
        vcp.getReferenceNumber(), vcp.getLawEnforcementId(), vcp.getStaffPersonId(),
        vcp.getDescription(), vcp.getRecipientName(), vcp.getOutStateLawEnforcementAddr(),
        vcp.getCountySpecificCode(), vcp.getLawEnforcementIndicator(),
        vcp.getOutStateLawEnforcementIndicator(), vcp.getSatisfyCrossReportIndicator());

    final String expected = MAPPER.writeValueAsString(
        (MAPPER.readValue(fixture("fixtures/persistent/CrossReport/valid/validWithSysCodes.json"),
            CrossReport.class)));

    assertThat(MAPPER.writeValueAsString(persistent)).isEqualTo(expected);
  }

  private CrossReport validCrossReport()
      throws JsonParseException, JsonMappingException, IOException {
    CrossReport vcp = MAPPER.readValue(fixture("fixtures/persistent/CrossReport/valid/valid.json"),
        CrossReport.class);
    return vcp;
  }

  private gov.ca.cwds.rest.api.domain.cms.CrossReport validDomainCrossReport()
      throws JsonParseException, JsonMappingException, IOException {
    gov.ca.cwds.rest.api.domain.cms.CrossReport validCrossReport =
        MAPPER.readValue(fixture("fixtures/domain/legacy/CrossReport/valid/valid.json"),
            gov.ca.cwds.rest.api.domain.cms.CrossReport.class);
    return validCrossReport;
  }

}
