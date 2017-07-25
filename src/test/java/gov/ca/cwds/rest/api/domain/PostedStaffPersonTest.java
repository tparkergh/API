package gov.ca.cwds.rest.api.domain;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import gov.ca.cwds.rest.services.ServiceException;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class PostedStaffPersonTest {
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
  private String countyCode = "99";
  private Boolean dutyWorkerIndicator = Boolean.FALSE;
  private String cwsOfficeAddress = "l";
  private String emailAddress = "m";


  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Test
  public void persistentObjectConstructorTest() throws Exception {

    gov.ca.cwds.data.persistence.cms.StaffPerson persistent =
        new gov.ca.cwds.data.persistence.cms.StaffPerson(id, DomainChef.uncookDateString(endDate),
            firstName, jobTitle, lastName, middleInitial, namePrefix, phoneNumber, phoneExt,
            DomainChef.uncookDateString(startDate), nameSuffix,
            DomainChef.cookBoolean(telecommuterIndicator), cwsOffice,
            availabilityAndLocationDescription, ssrsLicensingWorkerId, countyCode,
            DomainChef.cookBoolean(dutyWorkerIndicator), cwsOfficeAddress, emailAddress);
    PostedStaffPerson domain = new PostedStaffPerson(persistent);
    assertThat(domain.getId(), is(equalTo(persistent.getId())));
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
  public void domainObjectConstructorTest() throws Exception {

    StaffPerson domainStaffPerson =
        new StaffPerson(endDate, firstName, jobTitle, lastName, middleInitial, namePrefix,
            phoneNumber, phoneExt, startDate, nameSuffix, telecommuterIndicator, cwsOffice,
            availabilityAndLocationDescription, ssrsLicensingWorkerId, countyCode,
            dutyWorkerIndicator, cwsOfficeAddress, emailAddress);
    PostedStaffPerson domain = new PostedStaffPerson(domainStaffPerson, id);
    assertThat(domain.getId(), is(equalTo(id)));
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
    assertThat(domain.getCounty(), is(equalTo("State of California")));

  }

  @Test
  public void equalsHashCodeWork() {
    EqualsVerifier.forClass(PostedStaffPerson.class).suppress(Warning.NONFINAL_FIELDS)
        .suppress(Warning.STRICT_INHERITANCE).withRedefinedSuperclass().verify();
  }

  @Test
  public void serviceExceptionWhenIdIsNullPersistantObjectConstructor() throws Exception {
    thrown.expect(ServiceException.class);
    gov.ca.cwds.data.persistence.cms.StaffPerson persistent =
        new gov.ca.cwds.data.persistence.cms.StaffPerson(null,
            DomainChef.uncookDateString(endDate), firstName, jobTitle, lastName, middleInitial,
            namePrefix, phoneNumber, phoneExt, DomainChef.uncookDateString(startDate), nameSuffix,
            DomainChef.cookBoolean(telecommuterIndicator), cwsOffice,
            availabilityAndLocationDescription, ssrsLicensingWorkerId, countyCode,
            DomainChef.cookBoolean(dutyWorkerIndicator), cwsOfficeAddress, emailAddress);
    new PostedStaffPerson(persistent);
  }

  @Test
  public void serviceExceptionWhenIdIsNullDomainObjectConstructor() throws Exception {
    thrown.expect(ServiceException.class);
    StaffPerson domainStaffPerson =
        new StaffPerson(endDate, firstName, jobTitle, lastName, middleInitial, namePrefix,
            phoneNumber, phoneExt, startDate, nameSuffix, telecommuterIndicator, cwsOffice,
            availabilityAndLocationDescription, ssrsLicensingWorkerId, countyCode,
            dutyWorkerIndicator, cwsOfficeAddress, emailAddress);
    new PostedStaffPerson(domainStaffPerson, null);
  }
}
