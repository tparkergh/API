package gov.ca.cwds.data.persistence.cms;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.junit.Test;

import gov.ca.cwds.data.persistence.cms.Reporter;
import gov.ca.cwds.rest.api.domain.DomainChef;

public class ReporterTest {
  private final static DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
  private String referralId = "a";
  private String badgeNumber = "b";
  private String cityName = "c";
  private Short colltrClientRptrReltnshpType = 1;
  private Short communicationMethodType = 2;
  private Boolean confidentialWaiverIndicator = Boolean.TRUE;
  private String drmsMandatedRprtrFeedback = "d";
  private String employerName = "e";
  private String feedbackDate = "1973-11-22";
  private Boolean feedbackRequiredIndicator = Boolean.FALSE;
  private String firstName = "f";
  private String lastName = "g";
  private Boolean mandatedReporterIndicator = Boolean.TRUE;
  private int messagePhoneExtensionNumber = 3;
  private BigDecimal messagePhoneNumber = new BigDecimal(4);
  private String middleInitialName = "h";
  private String namePrefixDescription = "i";
  private BigDecimal primaryPhoneNumber = new BigDecimal(5);;
  private int primaryPhoneExtensionNumber = 6;
  private Short stateCodeType = 7;
  private String streetName = "j";
  private String streetNumber = "k";
  private String suffixTitleDescription = "l";
  private String zipcode = "95862";
  private String lawEnforcementId = "m";
  private Short zipSuffixNumber = 9;
  private String countySpecificCode = "n";

  private String lastUpdatedId = "z";

  /*
   * Constructor test
   */
  @Test
  public void emtpyConstructorIsNotNull() throws Exception {
    assertThat(Reporter.class.newInstance(), is(notNullValue()));
  }

  @Test
  public void domainReporterLastUpdateConstructorTest() throws Exception {
    gov.ca.cwds.rest.api.domain.cms.Reporter domain = new gov.ca.cwds.rest.api.domain.cms.Reporter(
        badgeNumber, cityName, colltrClientRptrReltnshpType, communicationMethodType,
        confidentialWaiverIndicator, drmsMandatedRprtrFeedback, employerName, feedbackDate,
        feedbackRequiredIndicator, firstName, lastName, mandatedReporterIndicator,
        messagePhoneExtensionNumber, messagePhoneNumber, middleInitialName, namePrefixDescription,
        primaryPhoneNumber, primaryPhoneExtensionNumber, stateCodeType, streetName, streetNumber,
        suffixTitleDescription, zipcode, referralId, lawEnforcementId, zipSuffixNumber,
        countySpecificCode);

    Reporter persistent = new Reporter(domain, lastUpdatedId);
    assertThat(persistent.getReferralId(), is(equalTo(referralId)));
    assertThat(persistent.getBadgeNumber(), is(equalTo(badgeNumber)));
    assertThat(persistent.getCityName(), is(equalTo(cityName)));
    assertThat(persistent.getColltrClientRptrReltnshpType(),
        is(equalTo(colltrClientRptrReltnshpType)));
    assertThat(persistent.getCommunicationMethodType(), is(equalTo(communicationMethodType)));
    assertThat(persistent.getConfidentialWaiverIndicator(), is(equalTo("Y")));
    assertThat(persistent.getDrmsMandatedRprtrFeedback(), is(equalTo(drmsMandatedRprtrFeedback)));
    assertThat(persistent.getEmployerName(), is(equalTo(employerName)));
    assertThat(persistent.getFeedbackDate(), is(equalTo(df.parse(feedbackDate))));
    assertThat(persistent.getFeedbackRequiredIndicator(), is(equalTo("N")));
    assertThat(persistent.getFirstName(), is(equalTo(firstName)));
    assertThat(persistent.getLastName(), is(equalTo(lastName)));
    assertThat(persistent.getMandatedReporterIndicator(), is(equalTo("Y")));
    assertThat(persistent.getMessagePhoneExtensionNumber(),
        is(equalTo(messagePhoneExtensionNumber)));
    assertThat(persistent.getMessagePhoneNumber(), is(equalTo(messagePhoneNumber)));
    assertThat(persistent.getMiddleInitialName(), is(equalTo(middleInitialName)));
    assertThat(persistent.getNamePrefixDescription(), is(equalTo(namePrefixDescription)));
    assertThat(persistent.getPrimaryPhoneNumber(), is(equalTo(primaryPhoneNumber)));
    assertThat(persistent.getPrimaryPhoneExtensionNumber(),
        is(equalTo(primaryPhoneExtensionNumber)));
    assertThat(persistent.getStateCodeType(), is(equalTo(stateCodeType)));
    assertThat(persistent.getStreetName(), is(equalTo(streetName)));
    assertThat(persistent.getStreetNumber(), is(equalTo(streetNumber)));
    assertThat(persistent.getSuffixTitleDescription(), is(equalTo(suffixTitleDescription)));
    assertThat(persistent.getZipNumber(), is(equalTo(DomainChef.uncookZipcodeString(zipcode))));
    assertThat(persistent.getLawEnforcementId(), is(equalTo(lawEnforcementId)));
    assertThat(persistent.getZipSuffixNumber(), is(equalTo(zipSuffixNumber)));
    assertThat(persistent.getCountySpecificCode(), is(equalTo(countySpecificCode)));
    assertThat(persistent.getLastUpdatedId(), is(equalTo(lastUpdatedId)));
  }

}
