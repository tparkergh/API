package gov.ca.cwds.data.persistence.cms;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.junit.Test;

/**
 * @author CWDS API Team
 *
 */
public class StaffPersonTest {
  private final static DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
  private String id = "a";
  private String endDate = "1973-11-22";
  private String firstName = "b";
  private String jobTitle = "c";
  private String lastName = "d";
  private String middleInitial = "e";
  private String namePrefix = "f";
  private BigDecimal phoneNumber = new BigDecimal(1);
  private Integer phoneExt = 2;
  private String startDate = "2006-09-12";
  private String nameSuffix = "g";
  private Boolean telecommuterIndicator = Boolean.TRUE;
  private String cwsOffice = "h";
  private String availabilityAndLocationDescription = "i";
  private String ssrsLicensingWorkerId = "j";
  private String countyCode = "k";
  private Boolean dutyWorkerIndicator = Boolean.FALSE;
  private String cwsOfficeAddress = "l";
  private String emailAddress = "m";

  private String lastUpdatedId = "z";

  /*
   * Constructor test
   */
  @SuppressWarnings("javadoc")
  @Test
  public void emtpyConstructorIsNotNull() throws Exception {
    assertThat(StaffPerson.class.newInstance(), is(notNullValue()));
  }

  @SuppressWarnings("javadoc")
  @Test
  public void constructorUsingDomainTest() throws Exception {
    gov.ca.cwds.rest.api.domain.cms.StaffPerson domain =
        new gov.ca.cwds.rest.api.domain.cms.StaffPerson(endDate, firstName, jobTitle, lastName,
            middleInitial, namePrefix, phoneNumber, phoneExt, startDate, nameSuffix,
            telecommuterIndicator, cwsOffice, availabilityAndLocationDescription,
            ssrsLicensingWorkerId, countyCode, dutyWorkerIndicator, cwsOfficeAddress, emailAddress);

    StaffPerson persistent = new StaffPerson(id, domain, "z");
    assertThat(persistent.getId(), is(equalTo(id)));
    assertThat(persistent.getEndDate(), is(equalTo(df.parse(endDate))));
    assertThat(persistent.getFirstName(), is(equalTo(firstName)));
    assertThat(persistent.getJobTitle(), is(equalTo(jobTitle)));
    assertThat(persistent.getLastName(), is(equalTo(lastName)));
    assertThat(persistent.getMiddleInitial(), is(equalTo(middleInitial)));
    assertThat(persistent.getNamePrefix(), is(equalTo(namePrefix)));
    assertThat(persistent.getPhoneNumber(), is(equalTo(phoneNumber)));
    assertThat(persistent.getPhoneExt(), is(equalTo(phoneExt)));
    assertThat(persistent.getStartDate(), is(equalTo(df.parse(startDate))));
    assertThat(persistent.getNameSuffix(), is(equalTo(nameSuffix)));
    assertThat(persistent.getTelecommuterIndicator(), is(equalTo("Y")));
    assertThat(persistent.getCwsOffice(), is(equalTo(cwsOffice)));
    assertThat(persistent.getAvailabilityAndLocationDescription(),
        is(equalTo(availabilityAndLocationDescription)));
    assertThat(persistent.getSsrsLicensingWorkerId(), is(equalTo(ssrsLicensingWorkerId)));
    assertThat(persistent.getCountyCode(), is(equalTo(countyCode)));
    assertThat(persistent.getDutyWorkerIndicator(), is(equalTo("N")));
    assertThat(persistent.getCwsOfficeAddress(), is(equalTo(cwsOfficeAddress)));
    assertThat(persistent.getEmailAddress(), is(equalTo(emailAddress)));
    assertThat(persistent.getLastUpdatedId(), is(equalTo(lastUpdatedId)));
  }
}
