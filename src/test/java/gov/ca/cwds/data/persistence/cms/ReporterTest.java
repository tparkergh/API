package gov.ca.cwds.data.persistence.cms;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.junit.Test;

import gov.ca.cwds.data.persistence.junit.template.PersistentTestTemplate;
import gov.ca.cwds.rest.api.domain.DomainChef;

/**
 * @author CWDS API Team
 *
 */
public class ReporterTest implements PersistentTestTemplate {
  private final static DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
  private String referralId = "1234567ABC";
  private String badgeNumber = "b1234";
  private String cityName = "Fresno";
  private Short colltrClientRptrReltnshpType = 1;
  private Short communicationMethodType = 2;
  private Boolean confidentialWaiverIndicator = Boolean.TRUE;
  private String drmsMandatedRprtrFeedback = "d";
  private String employerName = "School District";
  private String feedbackDate = "2016-11-30";
  private Boolean feedbackRequiredIndicator = Boolean.FALSE;
  private String firstName = "first";
  private String lastName = "last";
  private Boolean mandatedReporterIndicator = Boolean.TRUE;
  private Integer messagePhoneExtensionNumber = 3;
  private BigDecimal messagePhoneNumber = new BigDecimal(4);
  private String middleInitialName = "m";
  private String namePrefixDescription = "";
  private BigDecimal primaryPhoneNumber = new BigDecimal(5556666);;
  private Integer primaryPhoneExtensionNumber = 666;
  private Short stateCodeType = 19;
  private String streetName = "first st";
  private String streetNumber = "999";
  private String suffixTitleDescription = "";
  private String zipcode = "95862";
  private String lawEnforcementId = "";
  private Short zipSuffixNumber = 9;
  private String countySpecificCode = "19";

  private String lastUpdatedId = "0XA";

  /*
   * Constructor test
   */
  @Override
  @Test
  public void testEmptyConstructor() throws Exception {
    assertThat(Reporter.class.newInstance(), is(notNullValue()));
  }

  @Override
  @Test
  public void testConstructorUsingDomain() throws Exception {
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


  @Override
  @Test
  public void testPersistentConstructor() throws Exception {
    Reporter persistent =
        new Reporter(referralId, badgeNumber, cityName, colltrClientRptrReltnshpType,
            communicationMethodType, DomainChef.cookBoolean(confidentialWaiverIndicator),
            drmsMandatedRprtrFeedback, employerName, DomainChef.uncookDateString(feedbackDate),
            DomainChef.cookBoolean(feedbackRequiredIndicator), firstName, lastName,
            DomainChef.cookBoolean(mandatedReporterIndicator), messagePhoneExtensionNumber,
            messagePhoneNumber, middleInitialName, namePrefixDescription, primaryPhoneNumber,
            primaryPhoneExtensionNumber, stateCodeType, streetName, streetNumber,
            suffixTitleDescription, DomainChef.uncookZipcodeString(zipcode), lawEnforcementId,
            zipSuffixNumber, countySpecificCode);
    assertThat(persistent.getReferralId(), is(equalTo(referralId)));
    assertThat(persistent.getBadgeNumber(), is(equalTo(badgeNumber)));
    assertThat(persistent.getCity(), is(equalTo(cityName)));
    assertThat(persistent.getColltrClientRptrReltnshpType(),
        is(equalTo(colltrClientRptrReltnshpType)));
    assertThat(persistent.getCommunicationMethodType(), is(equalTo(communicationMethodType)));
    assertThat(persistent.getConfidentialWaiverIndicator(),
        is(equalTo(DomainChef.cookBoolean(confidentialWaiverIndicator))));
    assertThat(persistent.getDrmsMandatedRprtrFeedback(), is(equalTo(drmsMandatedRprtrFeedback)));
    assertThat(persistent.getEmployerName(), is(equalTo(employerName)));
    assertThat(persistent.getFeedbackDate(),
        is(equalTo(DomainChef.uncookDateString(feedbackDate))));
    assertThat(persistent.getFeedbackRequiredIndicator(),
        is(equalTo(DomainChef.cookBoolean(feedbackRequiredIndicator))));
    assertThat(persistent.getFirstName(), is(equalTo(firstName)));
    assertThat(persistent.getLastName(), is(equalTo(lastName)));
    assertThat(persistent.getMandatedReporterIndicator(),
        is(equalTo(DomainChef.cookBoolean(mandatedReporterIndicator))));
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
    assertThat(persistent.getZip(), is(equalTo(zipcode.toString())));
    assertThat(persistent.getLawEnforcementId(), is(equalTo(lawEnforcementId)));
    assertThat(persistent.getZipSuffixNumber(), is(equalTo(zipSuffixNumber)));
    assertThat(persistent.getCountySpecificCode(), is(equalTo(countySpecificCode)));
  }

  @Override
  public void testEqualsHashCodeWorks() throws Exception {
    // TODO Auto-generated method stub

  }

}
