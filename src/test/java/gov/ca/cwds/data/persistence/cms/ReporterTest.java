package gov.ca.cwds.data.persistence.cms;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import java.io.IOException;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import gov.ca.cwds.data.persistence.junit.template.PersistentTestTemplate;
import gov.ca.cwds.rest.api.domain.DomainChef;

/**
 * @author CWDS API Team
 */
public class ReporterTest implements PersistentTestTemplate {
  private static final ObjectMapper MAPPER = SystemCodeTestHarness.MAPPER;
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

    gov.ca.cwds.rest.api.domain.cms.Reporter domain = validReporterDomain();

    Reporter pers = new Reporter(domain, lastUpdatedId);

    assertThat(pers.getReferralId(), is(equalTo(domain.getReferralId())));
    assertThat(pers.getBadgeNumber(), is(equalTo(domain.getBadgeNumber())));
    assertThat(pers.getCityName(), is(equalTo(domain.getCityName())));
    assertThat(pers.getColltrClientRptrReltnshpType(),
        is(equalTo(domain.getColltrClientRptrReltnshpType())));
    assertThat(pers.getCommunicationMethodType(), is(equalTo(domain.getCommunicationMethodType())));
    assertThat(pers.getConfidentialWaiverIndicator(),
        is(equalTo(DomainChef.cookBoolean(domain.getConfidentialWaiverIndicator()))));
    assertThat(pers.getDrmsMandatedRprtrFeedback(),
        is(equalTo(domain.getDrmsMandatedRprtrFeedback())));
    assertThat(pers.getEmployerName(), is(equalTo(domain.getEmployerName())));
    assertThat(pers.getFeedbackDate(),
        is(equalTo(DomainChef.uncookDateString(domain.getFeedbackDate()))));
    assertThat(pers.getFeedbackRequiredIndicator(),
        is(equalTo(DomainChef.cookBoolean(domain.getFeedbackRequiredIndicator()))));
    assertThat(pers.getFirstName(), is(equalTo(domain.getFirstName())));
    assertThat(pers.getLastName(), is(equalTo(domain.getLastName())));
    assertThat(pers.getMandatedReporterIndicator(),
        is(equalTo(DomainChef.cookBoolean(domain.getMandatedReporterIndicator()))));
    assertThat(pers.getMessagePhoneExtensionNumber(),
        is(equalTo(domain.getMessagePhoneExtensionNumber())));
    assertThat(pers.getMessagePhoneNumber(), is(equalTo(domain.getMessagePhoneNumber())));
    assertThat(pers.getMiddleInitialName(), is(equalTo(domain.getMiddleInitialName())));
    assertThat(pers.getNamePrefixDescription(), is(equalTo(domain.getNamePrefixDescription())));
    assertThat(pers.getPrimaryPhoneNumber(), is(equalTo(domain.getPrimaryPhoneNumber())));
    assertThat(pers.getPrimaryPhoneExtensionNumber(),
        is(equalTo(domain.getPrimaryPhoneExtensionNumber())));
    assertThat(pers.getStateCodeType(), is(equalTo(domain.getStateCodeType())));
    assertThat(pers.getStreetName(), is(equalTo(domain.getStreetName())));
    assertThat(pers.getStreetNumber(), is(equalTo(domain.getStreetNumber())));
    assertThat(pers.getSuffixTitleDescription(), is(equalTo(domain.getSuffixTitleDescription())));
    assertThat(pers.getZipNumber(),
        is(equalTo(DomainChef.uncookZipcodeString(domain.getZipcode()))));
    assertThat(pers.getLawEnforcementId(), is(equalTo(domain.getLawEnforcementId())));
    assertThat(pers.getZipSuffixNumber(), is(equalTo(domain.getZipSuffixNumber())));
    assertThat(pers.getCountySpecificCode(), is(equalTo(domain.getCountySpecificCode())));
  }

  @Override
  @Test
  public void testPersistentConstructor() throws Exception {
    Reporter vp = validReporter();

    Reporter persistent = new Reporter(vp.getReferralId(), vp.getBadgeNumber(), vp.getCity(),
        vp.getColltrClientRptrReltnshpType(), vp.getCommunicationMethodType(),
        vp.getConfidentialWaiverIndicator(), vp.getDrmsMandatedRprtrFeedback(),
        vp.getEmployerName(), vp.getFeedbackDate(), vp.getFeedbackRequiredIndicator(),
        vp.getFirstName(), vp.getLastName(), vp.getMandatedReporterIndicator(),
        vp.getMessagePhoneExtensionNumber(), vp.getMessagePhoneNumber(), vp.getMiddleInitialName(),
        vp.getNamePrefixDescription(), vp.getPrimaryPhoneNumber(),
        vp.getPrimaryPhoneExtensionNumber(), vp.getStateCodeType(), vp.getStreetName(),
        vp.getStreetNumber(), vp.getSuffixTitleDescription(), vp.getZipNumber(),
        vp.getLawEnforcementId(), vp.getZipSuffixNumber(), vp.getCountySpecificCode());

    assertThat(persistent.getReferralId(), is(equalTo(vp.getReferralId())));
    assertThat(persistent.getBadgeNumber(), is(equalTo(vp.getBadgeNumber())));
    assertThat(persistent.getCity(), is(equalTo(vp.getCityName())));
    assertThat(persistent.getColltrClientRptrReltnshpType(),
        is(equalTo(vp.getColltrClientRptrReltnshpType())));
    assertThat(persistent.getCommunicationMethodType(),
        is(equalTo(vp.getCommunicationMethodType())));
    assertThat(persistent.getConfidentialWaiverIndicator(),
        is(equalTo(vp.getConfidentialWaiverIndicator())));
    assertThat(persistent.getDrmsMandatedRprtrFeedback(),
        is(equalTo(vp.getDrmsMandatedRprtrFeedback())));
    assertThat(persistent.getEmployerName(), is(equalTo(vp.getEmployerName())));
    assertThat(persistent.getFeedbackDate(), is(equalTo(vp.getFeedbackDate())));
    assertThat(persistent.getFeedbackRequiredIndicator(),
        is(equalTo(vp.getFeedbackRequiredIndicator())));
    assertThat(persistent.getFirstName(), is(equalTo(vp.getFirstName())));
    assertThat(persistent.getLastName(), is(equalTo(vp.getLastName())));
    assertThat(persistent.getMandatedReporterIndicator(),
        is(equalTo(vp.getMandatedReporterIndicator())));
    assertThat(persistent.getMessagePhoneExtensionNumber(),
        is(equalTo(vp.getMessagePhoneExtensionNumber())));
    assertThat(persistent.getMessagePhoneNumber(), is(equalTo(vp.getMessagePhoneNumber())));
    assertThat(persistent.getMiddleInitialName(), is(equalTo(vp.getMiddleInitialName())));
    assertThat(persistent.getNamePrefixDescription(), is(equalTo(vp.getNamePrefixDescription())));
    assertThat(persistent.getPrimaryPhoneNumber(), is(equalTo(vp.getPrimaryPhoneNumber())));
    assertThat(persistent.getPrimaryPhoneExtensionNumber(),
        is(equalTo(vp.getPrimaryPhoneExtensionNumber())));
    assertThat(persistent.getStateCodeType(), is(equalTo(vp.getStateCodeType())));
    assertThat(persistent.getStreetName(), is(equalTo(vp.getStreetName())));
    assertThat(persistent.getStreetNumber(), is(equalTo(vp.getStreetNumber())));
    assertThat(persistent.getSuffixTitleDescription(), is(equalTo(vp.getSuffixTitleDescription())));
    assertThat(persistent.getZipNumber(), is(equalTo(vp.getZipNumber())));
    assertThat(persistent.getLawEnforcementId(), is(equalTo(vp.getLawEnforcementId())));
    assertThat(persistent.getZipSuffixNumber(), is(equalTo(vp.getZipSuffixNumber())));
    assertThat(persistent.getCountySpecificCode(), is(equalTo(vp.getCountySpecificCode())));
    // MAPPER.writerWithDefaultPrettyPrinter().writeValue(System.out, persistent);
  }

  @SuppressWarnings("javadoc")
  @Test
  public void testSerializeAndDeserialize()
      throws JsonParseException, JsonMappingException, JsonProcessingException, IOException {

    Reporter vp = validReporter();

    Reporter persistent = new Reporter(vp.getReferralId(), vp.getBadgeNumber(), vp.getCity(),
        vp.getColltrClientRptrReltnshpType(), vp.getCommunicationMethodType(),
        vp.getConfidentialWaiverIndicator(), vp.getDrmsMandatedRprtrFeedback(),
        vp.getEmployerName(), vp.getFeedbackDate(), vp.getFeedbackRequiredIndicator(),
        vp.getFirstName(), vp.getLastName(), vp.getMandatedReporterIndicator(),
        vp.getMessagePhoneExtensionNumber(), vp.getMessagePhoneNumber(), vp.getMiddleInitialName(),
        vp.getNamePrefixDescription(), vp.getPrimaryPhoneNumber(),
        vp.getPrimaryPhoneExtensionNumber(), vp.getStateCodeType(), vp.getStreetName(),
        vp.getStreetNumber(), vp.getSuffixTitleDescription(), vp.getZipNumber(),
        vp.getLawEnforcementId(), vp.getZipSuffixNumber(), vp.getCountySpecificCode());

    final String expected = MAPPER.writeValueAsString((MAPPER.readValue(
        fixture("fixtures/persistent/Reporter/valid/validWithSysCodes.json"), Reporter.class)));

    assertThat(MAPPER.writeValueAsString(persistent)).isEqualTo(expected);

  }

  @Override
  public void testEqualsHashCodeWorks() throws Exception {}

  private Reporter validReporter() throws JsonParseException, JsonMappingException, IOException {
    Reporter validReporter =
        MAPPER.readValue(fixture("fixtures/persistent/Reporter/valid/valid.json"), Reporter.class);
    return validReporter;
  }

  private gov.ca.cwds.rest.api.domain.cms.Reporter validReporterDomain()
      throws JsonParseException, JsonMappingException, IOException {
    gov.ca.cwds.rest.api.domain.cms.Reporter validReporterDomain =
        MAPPER.readValue(fixture("fixtures/domain/legacy/Reporter/valid/valid.json"),
            gov.ca.cwds.rest.api.domain.cms.Reporter.class);
    return validReporterDomain;
  }
}
