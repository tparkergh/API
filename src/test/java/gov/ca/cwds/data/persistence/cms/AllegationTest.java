package gov.ca.cwds.data.persistence.cms;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.junit.Test;

/**
 * @author CWDS API Team
 *
 */
public class AllegationTest {

  private final static DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
  private String id = "a";
  private String abuseEndDate = "1973-11-22";
  private String abuseStartDate = "2006-09-12";
  private Short abuseFrequency = 1;
  private String abuseFrequencyPeriodCode = "b";
  private String abuseLocationDescription = "c";
  private Short allegationDispositionType = 2;
  private Short allegationType = 3;
  private String dispositionDescription = "d";
  private String dispositionDate = "1963-11-22";
  private Boolean injuryHarmDetailIndicator = Boolean.TRUE;
  private String nonProtectingParentCode = "e";
  private Boolean staffPersonAddedIndicator = Boolean.FALSE;
  private String victimClientId = "f";
  private String perpetratorClientId = "g";
  private String referralId = "h";
  private String countySpecificCode = "i";
  private Boolean zippyCreatedIndicator = Boolean.TRUE;
  private Short placementFacilityType = 4;

  private String lastUpdatedId = "z";


  /*
   * Constructor test
   */
  @SuppressWarnings("javadoc")
  @Test
  public void emtpyConstructorIsNotNull() throws Exception {
    assertThat(Allegation.class.newInstance(), is(notNullValue()));
  }

  @SuppressWarnings("javadoc")
  @Test
  public void persistentAllegationConstructorTest() throws Exception {
    gov.ca.cwds.rest.api.domain.cms.Allegation domain =
        new gov.ca.cwds.rest.api.domain.cms.Allegation(abuseEndDate, abuseFrequency,
            abuseFrequencyPeriodCode, abuseLocationDescription, abuseStartDate,
            allegationDispositionType, allegationType, dispositionDescription, dispositionDate,
            injuryHarmDetailIndicator, nonProtectingParentCode, staffPersonAddedIndicator,
            victimClientId, perpetratorClientId, referralId, countySpecificCode,
            zippyCreatedIndicator, placementFacilityType);

    gov.ca.cwds.data.persistence.cms.Allegation persistent =
        new gov.ca.cwds.data.persistence.cms.Allegation(id, domain, "z");
    assertThat(persistent.getId(), is(equalTo(id)));
    assertThat(persistent.getAbuseEndDate(), is(equalTo(df.parse(abuseEndDate))));
    assertThat(persistent.getAbuseStartDate(), is(equalTo(df.parse(abuseStartDate))));
    assertThat(persistent.getAbuseFrequency(), is(equalTo(abuseFrequency)));
    assertThat(persistent.getAbuseFrequencyPeriodCode(), is(equalTo(abuseFrequencyPeriodCode)));
    assertThat(persistent.getAbuseLocationDescription(), is(equalTo(abuseLocationDescription)));
    assertThat(persistent.getAllegationDispositionType(), is(equalTo(allegationDispositionType)));
    assertThat(persistent.getAllegationType(), is(equalTo(allegationType)));
    assertThat(persistent.getDispositionDescription(), is(equalTo(dispositionDescription)));
    assertThat(persistent.getDispositionDate(), is(equalTo(df.parse(dispositionDate))));
    assertThat(persistent.getInjuryHarmDetailIndicator(), is(equalTo("Y")));
    assertThat(persistent.getNonProtectingParentCode(), is(equalTo(nonProtectingParentCode)));
    assertThat(persistent.getStaffPersonAddedIndicator(), is(equalTo("N")));
    assertThat(persistent.getPerpetratorClientId(), is(equalTo(perpetratorClientId)));
    assertThat(persistent.getVictimClientId(), is(equalTo(victimClientId)));
    assertThat(persistent.getReferralId(), is(equalTo(referralId)));
    assertThat(persistent.getCountySpecificCode(), is(equalTo(countySpecificCode)));
    assertThat(persistent.getZippyCreatedIndicator(), is(equalTo("Y")));
    assertThat(persistent.getPlacementFacilityType(), is(equalTo(placementFacilityType)));
    assertThat(persistent.getLastUpdatedId(), is(equalTo(lastUpdatedId)));
  }
}
