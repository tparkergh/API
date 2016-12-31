package gov.ca.cwds.data.persistence.cms;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.junit.Test;

import gov.ca.cwds.data.persistence.cms.CrossReport;
import gov.ca.cwds.rest.api.domain.DomainChef;

public class CrossReportTest {
  private final static DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
  private final static DateFormat tf = new SimpleDateFormat("HH:mm:ss");

  private String thirdId = "ABC1234567";
  private Short crossReportMethodType = 1;
  private Boolean filedOutOfStateIndicator = Boolean.TRUE;
  private Boolean governmentOrgCrossRptIndicatorVar = Boolean.FALSE;
  private String informTime = "16:41:49";
  private String recipientBadgeNumber = "bg12345";
  private Integer recipientPhoneExtensionNumber = 122;
  private BigDecimal recipientPhoneNumber = new BigDecimal(3456789);
  private String informDate = "2016-01-01";
  private String recipientPositionTitleDesc = "title";
  private String referenceNumber = "f";
  private String referralId = "ABC1234567";
  private String lawEnforcementId = "g";
  private String staffPersonId = "567";
  private String description = "description";
  private String recipientName = "j";
  private String outstateLawEnforcementAddr = "k";
  private String countySpecificCode = "99";
  private Boolean lawEnforcementIndicator = Boolean.TRUE;
  private Boolean outStateLawEnforcementIndicator = Boolean.FALSE;
  private Boolean satisfyCrossReportIndicator = Boolean.TRUE;
  private String lastUpdatedId = "ABC";

  /*
   * Constructor test
   */
  @Test
  public void emtpyConstructorIsNotNull() throws Exception {
    assertThat(CrossReport.class.newInstance(), is(notNullValue()));
  }

  @Test
  public void domainCrossReportLastUpdateConstructorTest() throws Exception {

    gov.ca.cwds.rest.api.domain.cms.CrossReport domain =
        new gov.ca.cwds.rest.api.domain.cms.CrossReport(thirdId, crossReportMethodType,
            filedOutOfStateIndicator, governmentOrgCrossRptIndicatorVar, informTime,
            recipientBadgeNumber, recipientPhoneExtensionNumber, recipientPhoneNumber, informDate,
            recipientPositionTitleDesc, referenceNumber, referralId, lawEnforcementId,
            staffPersonId, description, recipientName, outstateLawEnforcementAddr,
            countySpecificCode, lawEnforcementIndicator, outStateLawEnforcementIndicator,
            satisfyCrossReportIndicator);

    CrossReport persistent = new CrossReport(thirdId, domain, lastUpdatedId);

    assertThat(persistent.getThirdId(), is(equalTo(thirdId)));
    assertThat(persistent.getCrossReportMethodType(), is(equalTo(crossReportMethodType)));
    assertThat(persistent.getFiledOutOfStateIndicator(), is(equalTo("Y")));
    assertThat(persistent.getGovernmentOrgCrossRptIndicatorVar(), is(equalTo("N")));
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
    assertThat(persistent.getLawEnforcementIndicator(), is(equalTo("Y")));
    assertThat(persistent.getOutStateLawEnforcementIndicator(), is(equalTo("N")));
    assertThat(persistent.getSatisfyCrossReportIndicator(), is(equalTo("Y")));
    assertThat(persistent.getLastUpdatedId(), is(equalTo(lastUpdatedId)));
  }

  @Test
  public void persistentCrossReportContructorTest() throws Exception {

    CrossReport persistent = new CrossReport(referralId, thirdId, crossReportMethodType,
        DomainChef.cookBoolean(filedOutOfStateIndicator),
        DomainChef.cookBoolean(governmentOrgCrossRptIndicatorVar), tf.parse(informTime),
        recipientBadgeNumber, recipientPhoneExtensionNumber, recipientPhoneNumber,
        DomainChef.uncookDateString(informDate), recipientPositionTitleDesc, referenceNumber,
        lawEnforcementId, staffPersonId, description, recipientName, outstateLawEnforcementAddr,
        countySpecificCode, DomainChef.cookBoolean(lawEnforcementIndicator),
        DomainChef.cookBoolean(outStateLawEnforcementIndicator),
        DomainChef.cookBoolean(satisfyCrossReportIndicator));

    assertThat(persistent.getThirdId(), is(equalTo(thirdId)));
    assertThat(persistent.getCrossReportMethodType(), is(equalTo(crossReportMethodType)));
    assertThat(persistent.getFiledOutOfStateIndicator(), is(equalTo("Y")));
    assertThat(persistent.getGovernmentOrgCrossRptIndicatorVar(), is(equalTo("N")));
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
    assertThat(persistent.getLawEnforcementIndicator(), is(equalTo("Y")));
    assertThat(persistent.getOutStateLawEnforcementIndicator(), is(equalTo("N")));
    assertThat(persistent.getSatisfyCrossReportIndicator(), is(equalTo("Y")));
  }
}
