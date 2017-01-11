package gov.ca.cwds.data.persistence.cms;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

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
public class CrossReportTest implements PersistentTestTemplate {
  private final static DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
  private final static DateFormat tf = new SimpleDateFormat("HH:mm:ss");
  private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

  private String thirdId = "ABC1234567";
  private Short crossReportMethodType = 123;
  private String filedOutOfStateIndicator = "N";
  private String governmentOrgCrossRptIndicatorVar = "N";
  private String informTime = "16:41:49";
  private String recipientBadgeNumber = "AB123";
  private Integer recipientPhoneExtensionNumber = 234;
  private BigDecimal recipientPhoneNumber = new BigDecimal(1234567);
  private String informDate = "2016-01-31";
  private String recipientPositionTitleDesc = "ABC23";
  private String referenceNumber = "DE123";
  private String referralId = "1234567ABC";
  private String lawEnforcementId = "1234567ABC";
  private String staffPersonId = "q1p";
  private String description = "ABC DESC";
  private String recipientName = "JOHN";
  private String outstateLawEnforcementAddr = "ABC STREET";
  private String countySpecificCode = "AB";
  private String lawEnforcementIndicator = "N";
  private String outStateLawEnforcementIndicator = "N";
  private String satisfyCrossReportIndicator = "N";
  private String lastUpdatedId = "ABC";


  @Override
  @Test
  public void testEqualsHashCodeWorks() throws Exception {
    EqualsVerifier.forClass(Attorney.class).suppress(Warning.NONFINAL_FIELDS).verify();

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

    gov.ca.cwds.rest.api.domain.cms.CrossReport domain = validCrossReport();

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
    assertThat(persistent.getInformDate(), is(equalTo(df.parse(informDate))));
    assertThat(persistent.getRecipientPositionTitleDesc(),
        is(equalTo(domain.getRecipientPositionTitleDesc())));
    assertThat(persistent.getReferenceNumber(), is(equalTo(domain.getReferenceNumber())));
    assertThat(persistent.getReferralId(), is(equalTo(domain.getReferralId())));
    assertThat(persistent.getLawEnforcementId(), is(equalTo(domain.getLawEnforcementId())));
    assertThat(persistent.getStaffPersonId(), is(equalTo(domain.getStaffPersonId())));
    assertThat(persistent.getDescription(), is(equalTo(domain.getDescription())));
    assertThat(persistent.getRecipientName(), is(equalTo(domain.getRecipientName())));
    assertThat(persistent.getOutstateLawEnforcementAddr(),
        is(equalTo(domain.getOutstateLawEnforcementAddr())));
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

    CrossReport persistent = new CrossReport(referralId, thirdId, crossReportMethodType,
        filedOutOfStateIndicator, governmentOrgCrossRptIndicatorVar, tf.parse(informTime),
        recipientBadgeNumber, recipientPhoneExtensionNumber, recipientPhoneNumber,
        DomainChef.uncookDateString(informDate), recipientPositionTitleDesc, referenceNumber,
        lawEnforcementId, staffPersonId, description, recipientName, outstateLawEnforcementAddr,
        countySpecificCode, lawEnforcementIndicator, outStateLawEnforcementIndicator,
        satisfyCrossReportIndicator);

    assertThat(persistent.getThirdId(), is(equalTo(thirdId)));
    assertThat(persistent.getCrossReportMethodType(), is(equalTo(crossReportMethodType)));
    assertThat(persistent.getFiledOutOfStateIndicator(), is(equalTo(filedOutOfStateIndicator)));
    assertThat(persistent.getGovernmentOrgCrossRptIndicatorVar(),
        is(equalTo(governmentOrgCrossRptIndicatorVar)));
    assertThat(persistent.getInformTime(), is(equalTo(tf.parse(informTime))));
    assertThat(persistent.getRecipientBadgeNumber(), is(equalTo(recipientBadgeNumber)));
    assertThat(persistent.getRecipientPhoneExtensionNumber(),
        is(equalTo(recipientPhoneExtensionNumber)));
    assertThat(persistent.getRecipientPhoneNumber(), is(equalTo(recipientPhoneNumber)));
    assertThat(persistent.getInformDate(), is(equalTo(df.parse(informDate))));
    assertThat(persistent.getRecipientPositionTitleDesc(), is(equalTo(recipientPositionTitleDesc)));
    assertThat(persistent.getReferenceNumber(), is(equalTo(referenceNumber)));
    assertThat(persistent.getReferralId(), is(equalTo(referralId)));
    assertThat(persistent.getLawEnforcementId(), is(equalTo(lawEnforcementId)));
    assertThat(persistent.getStaffPersonId(), is(equalTo(staffPersonId)));
    assertThat(persistent.getDescription(), is(equalTo(description)));
    assertThat(persistent.getRecipientName(), is(equalTo(recipientName)));
    assertThat(persistent.getOutstateLawEnforcementAddr(), is(equalTo(outstateLawEnforcementAddr)));
    assertThat(persistent.getCountySpecificCode(), is(equalTo(countySpecificCode)));
    assertThat(persistent.getLawEnforcementIndicator(), is(equalTo(lawEnforcementIndicator)));
    assertThat(persistent.getOutStateLawEnforcementIndicator(),
        is(equalTo(outStateLawEnforcementIndicator)));
    assertThat(persistent.getSatisfyCrossReportIndicator(),
        is(equalTo(satisfyCrossReportIndicator)));
  }

  private gov.ca.cwds.rest.api.domain.cms.CrossReport validCrossReport()
      throws JsonParseException, JsonMappingException, IOException {
    gov.ca.cwds.rest.api.domain.cms.CrossReport validCrossReport =
        MAPPER.readValue(fixture("fixtures/domain/legacy/CrossReport/valid/valid.json"),
            gov.ca.cwds.rest.api.domain.cms.CrossReport.class);
    return validCrossReport;

  }

}
