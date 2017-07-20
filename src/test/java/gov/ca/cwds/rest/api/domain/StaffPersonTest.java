package gov.ca.cwds.rest.api.domain;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import gov.ca.cwds.rest.core.Api;
import gov.ca.cwds.rest.resources.StaffPersonResource;
import gov.ca.cwds.rest.resources.cms.JerseyGuiceRule;
import io.dropwizard.jackson.Jackson;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

import org.junit.After;
import org.junit.ClassRule;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.squarespace.jersey2.guice.JerseyGuiceUtils;

@SuppressWarnings("javadoc")
public class StaffPersonTest {

  private static final String ROOT_RESOURCE = "/" + Api.RESOURCE_STAFF_PERSONS + "/";

  private static final StaffPersonResource mockedStaffPersonResource =
      mock(StaffPersonResource.class);


  @After
  public void ensureServiceLocatorPopulated() {
    JerseyGuiceUtils.reset();
  }

  @ClassRule
  public static JerseyGuiceRule rule = new JerseyGuiceRule();


  private static final ObjectMapper MAPPER = Jackson.newObjectMapper();
  private StaffPerson validStaffPerson = validStaffPerson();

  private final static DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
  private String id = "a";
  private String endDate = "2016-10-31";
  private String firstName = "b";
  private String jobTitle = "c";
  private String lastName = "d";
  private String middleInitial = "e";
  private String namePrefix = "f";
  private BigDecimal phoneNumber = new BigDecimal(1);
  private Integer phoneExt = 2;
  private String startDate = "2016-10-31";
  private String nameSuffix = "g";
  private Boolean telecommuterIndicator = Boolean.TRUE;
  private String cwsOffice = "h";
  private String availabilityAndLocationDescription = "i";
  private String ssrsLicensingWorkerId = "j";
  private String countyCode = "k";
  private Boolean dutyWorkerIndicator = Boolean.FALSE;
  private String cwsOfficeAddress = "l";
  private String emailAddress = "m";

  /*
   * Constructor Tests
   */
  @Test
  public void persistentObjectConstructorTest() throws Exception {
    gov.ca.cwds.data.persistence.cms.StaffPerson persistent =
        new gov.ca.cwds.data.persistence.cms.StaffPerson(id, DomainChef.uncookDateString(endDate),
            firstName, jobTitle, lastName, middleInitial, namePrefix, phoneNumber, phoneExt,
            DomainChef.uncookDateString(startDate), nameSuffix,
            DomainChef.cookBoolean(telecommuterIndicator), cwsOffice,
            availabilityAndLocationDescription, ssrsLicensingWorkerId, countyCode,
            DomainChef.cookBoolean(dutyWorkerIndicator), cwsOfficeAddress, emailAddress);
    StaffPerson domain = new StaffPerson(persistent);
    assertThat(domain.getEndDate(), is(equalTo(df.format(persistent.getEndDate()))));
    assertThat(domain.getFirstName(), is(equalTo(persistent.getFirstName())));
    assertThat(domain.getJobTitle(), is(equalTo(persistent.getJobTitle())));
    assertThat(domain.getLastName(), is(equalTo(persistent.getLastName())));
    assertThat(domain.getMiddleInitial(), is(equalTo(persistent.getMiddleInitial())));
    assertThat(domain.getNamePrefix(), is(equalTo(persistent.getNamePrefix())));
    assertThat(domain.getPhoneNumber(), is(equalTo(persistent.getPhoneNumber())));
    assertThat(domain.getPhoneExt(), is(equalTo(persistent.getPhoneExt())));
    assertThat(domain.getStartDate(), is(equalTo(df.format(persistent.getStartDate()))));
    assertThat(domain.getNameSuffix(), is(equalTo(persistent.getNameSuffix())));
    assertThat(domain.getTelecommuterIndicator(),
        is(equalTo(DomainChef.uncookBooleanString(persistent.getTelecommuterIndicator()))));
    assertThat(domain.getCwsOffice(), is(equalTo(persistent.getCwsOffice())));
    assertThat(domain.getAvailabilityAndLocationDescription(),
        is(equalTo(persistent.getAvailabilityAndLocationDescription())));
    assertThat(domain.getSsrsLicensingWorkerId(),
        is(equalTo(persistent.getSsrsLicensingWorkerId())));
    assertThat(domain.getCountyCode(), is(equalTo(persistent.getCountyCode())));
    assertThat(domain.getDutyWorkerIndicator(),
        is(equalTo(DomainChef.uncookBooleanString(persistent.getDutyWorkerIndicator()))));
    assertThat(domain.getCwsOfficeAddress(), is(equalTo(persistent.getCwsOfficeAddress())));
    assertThat(domain.getEmailAddress(), is(equalTo(persistent.getEmailAddress())));
  }

  @Test
  public void jsonCreatorConstructorTest() throws Exception {
    StaffPerson domain =
        new StaffPerson(endDate, firstName, jobTitle, lastName, middleInitial, namePrefix,
            phoneNumber, phoneExt, startDate, nameSuffix, telecommuterIndicator, cwsOffice,
            availabilityAndLocationDescription, ssrsLicensingWorkerId, countyCode,
            dutyWorkerIndicator, cwsOfficeAddress, emailAddress);

    assertThat(domain.getEndDate(), is(equalTo(endDate)));
    assertThat(domain.getFirstName(), is(equalTo(firstName)));
    assertThat(domain.getJobTitle(), is(equalTo(jobTitle)));
    assertThat(domain.getLastName(), is(equalTo(lastName)));
    assertThat(domain.getMiddleInitial(), is(equalTo(middleInitial)));
    assertThat(domain.getNamePrefix(), is(equalTo(namePrefix)));
    assertThat(domain.getPhoneNumber(), is(equalTo(phoneNumber)));
    assertThat(domain.getPhoneExt(), is(equalTo(phoneExt)));
    assertThat(domain.getStartDate(), is(equalTo(startDate)));
    assertThat(domain.getNameSuffix(), is(equalTo(nameSuffix)));
    assertThat(domain.getTelecommuterIndicator(), is(equalTo(telecommuterIndicator)));
    assertThat(domain.getCwsOffice(), is(equalTo(cwsOffice)));
    assertThat(domain.getAvailabilityAndLocationDescription(),
        is(equalTo(availabilityAndLocationDescription)));
    assertThat(domain.getSsrsLicensingWorkerId(), is(equalTo(ssrsLicensingWorkerId)));
    assertThat(domain.getCountyCode(), is(equalTo(countyCode)));
    assertThat(domain.getDutyWorkerIndicator(), is(equalTo(dutyWorkerIndicator)));
    assertThat(domain.getCwsOfficeAddress(), is(equalTo(cwsOfficeAddress)));
    assertThat(domain.getEmailAddress(), is(equalTo(emailAddress)));
  }

  @Test
  public void serializesToJSON() throws Exception {
    final String expected =
        MAPPER.writeValueAsString(MAPPER.readValue(
            fixture("fixtures/domain/StaffPerson/valid/valid.json"), StaffPerson.class));

    assertThat(MAPPER.writeValueAsString(validStaffPerson()), is(equalTo(expected)));
  }

  @Test
  public void deserializesFromJSON() throws Exception {
    assertThat(MAPPER.readValue(fixture("fixtures/domain/StaffPerson/valid/valid.json"),
        StaffPerson.class), is(equalTo(validStaffPerson())));
  }

  @Test
  public void equalsHashCodeWork() {
    EqualsVerifier.forClass(StaffPerson.class).suppress(Warning.NONFINAL_FIELDS).verify();
  }

  /*
   * Utils
   */
  private StaffPerson validStaffPerson() {
    return new StaffPerson("2016-10-31", "John", "CEO", "Doe", "C", "Mr", new BigDecimal(
        9165551212L), 22, "2016-10-31", "III", true, "MIZN02k11B", "abc", "def", "99", false,
        "3XPCP92b24", "john.doe@anyco.com");
  }

}
