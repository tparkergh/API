package gov.ca.cwds.data.persistence.cms;

import static io.dropwizard.testing.FixtureHelpers.fixture;
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
import io.dropwizard.jackson.Jackson;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

/**
 * @author CWDS API Team
 *
 */
public class AllegationTest implements PersistentTestTemplate {

  private final static DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
  private String id = "1234567ABC";
  private String lastUpdatedId = "0X5";
  private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

  /*
   * Constructor test
   */
  @Override
  @Test
  public void testEmptyConstructor() throws Exception {
    assertThat(Allegation.class.newInstance(), is(notNullValue()));
  }

  @Override
  @Test
  public void testPersistentContructor() throws Exception {

    gov.ca.cwds.rest.api.domain.cms.Allegation domain = validDomainAllegation();

    gov.ca.cwds.data.persistence.cms.Allegation persistent =
        new gov.ca.cwds.data.persistence.cms.Allegation(id,
            DomainChef.uncookDateString(domain.getAbuseEndDate()),
            DomainChef.uncookDateString(domain.getAbuseStartDate()), domain.getAbuseFrequency(),
            domain.getAbuseFrequencyPeriodCode(), domain.getAbuseLocationDescription(),
            domain.getAllegationDispositionType(), domain.getAllegationType(),
            domain.getDispositionDescription(),
            DomainChef.uncookDateString(domain.getDispositionDate()),
            DomainChef.cookBoolean(domain.getInjuryHarmDetailIndicator()),
            domain.getNonProtectingParentCode(),
            DomainChef.cookBoolean(domain.getStaffPersonAddedIndicator()),
            domain.getVictimClientId(), domain.getPerpetratorClientId(), domain.getReferralId(),
            domain.getCountySpecificCode(),
            DomainChef.cookBoolean(domain.getZippyCreatedIndicator()),
            domain.getPlacementFacilityType());

    assertThat(persistent.getId(), is(equalTo(id)));
    assertThat(persistent.getAbuseEndDate(), is(equalTo(df.parse(domain.getAbuseEndDate()))));
    assertThat(persistent.getAbuseStartDate(), is(equalTo(df.parse(domain.getAbuseStartDate()))));
    assertThat(persistent.getAbuseFrequency(), is(equalTo(domain.getAbuseFrequency())));
    assertThat(persistent.getAbuseFrequencyPeriodCode(),
        is(equalTo(domain.getAbuseFrequencyPeriodCode())));
    assertThat(persistent.getAbuseLocationDescription(),
        is(equalTo(domain.getAbuseLocationDescription())));
    assertThat(persistent.getAllegationDispositionType(),
        is(equalTo(domain.getAllegationDispositionType())));
    assertThat(persistent.getAllegationType(), is(equalTo(domain.getAllegationType())));
    assertThat(persistent.getDispositionDescription(),
        is(equalTo(domain.getDispositionDescription())));
    assertThat(persistent.getDispositionDate(),
        is(equalTo(DomainChef.uncookDateString((domain.getDispositionDate())))));
    assertThat(persistent.getInjuryHarmDetailIndicator(),
        is(equalTo(DomainChef.cookBoolean(domain.getInjuryHarmDetailIndicator()))));
    assertThat(persistent.getNonProtectingParentCode(),
        is(equalTo(domain.getNonProtectingParentCode())));
    assertThat(persistent.getStaffPersonAddedIndicator(),
        is(equalTo(DomainChef.cookBoolean(domain.getStaffPersonAddedIndicator()))));
    assertThat(persistent.getPerpetratorClientId(), is(equalTo(domain.getPerpetratorClientId())));
    assertThat(persistent.getVictimClientId(), is(equalTo(domain.getVictimClientId())));
    assertThat(persistent.getReferralId(), is(equalTo(domain.getReferralId())));
    assertThat(persistent.getCountySpecificCode(), is(equalTo(domain.getCountySpecificCode())));
    assertThat(persistent.getZippyCreatedIndicator(),
        is(equalTo(DomainChef.cookBoolean(domain.getZippyCreatedIndicator()))));
    assertThat(persistent.getPlacementFacilityType(),
        is(equalTo(domain.getPlacementFacilityType())));
  }

  @Override
  @Test
  public void testConstructorUsingDomain() throws Exception {

    gov.ca.cwds.rest.api.domain.cms.Allegation domain = validDomainAllegation();

    gov.ca.cwds.data.persistence.cms.Allegation persistent =
        new gov.ca.cwds.data.persistence.cms.Allegation(id, domain, lastUpdatedId);

    assertThat(persistent.getId(), is(equalTo(id)));
    assertThat(persistent.getAbuseEndDate(), is(equalTo(df.parse(domain.getAbuseEndDate()))));
    assertThat(persistent.getAbuseStartDate(), is(equalTo(df.parse(domain.getAbuseStartDate()))));
    assertThat(persistent.getAbuseFrequency(), is(equalTo(domain.getAbuseFrequency())));
    assertThat(persistent.getAbuseFrequencyPeriodCode(),
        is(equalTo(domain.getAbuseFrequencyPeriodCode())));
    assertThat(persistent.getAbuseLocationDescription(),
        is(equalTo(domain.getAbuseLocationDescription())));
    assertThat(persistent.getAllegationDispositionType(),
        is(equalTo(domain.getAllegationDispositionType())));
    assertThat(persistent.getAllegationType(), is(equalTo(domain.getAllegationType())));
    assertThat(persistent.getDispositionDescription(),
        is(equalTo(domain.getDispositionDescription())));
    assertThat(persistent.getDispositionDate(),
        is(equalTo(DomainChef.uncookDateString((domain.getDispositionDate())))));
    assertThat(persistent.getInjuryHarmDetailIndicator(),
        is(equalTo(DomainChef.cookBoolean(domain.getInjuryHarmDetailIndicator()))));
    assertThat(persistent.getNonProtectingParentCode(),
        is(equalTo(domain.getNonProtectingParentCode())));
    assertThat(persistent.getStaffPersonAddedIndicator(),
        is(equalTo(DomainChef.cookBoolean(domain.getStaffPersonAddedIndicator()))));
    assertThat(persistent.getPerpetratorClientId(), is(equalTo(domain.getPerpetratorClientId())));
    assertThat(persistent.getVictimClientId(), is(equalTo(domain.getVictimClientId())));
    assertThat(persistent.getReferralId(), is(equalTo(domain.getReferralId())));
    assertThat(persistent.getCountySpecificCode(), is(equalTo(domain.getCountySpecificCode())));
    assertThat(persistent.getZippyCreatedIndicator(),
        is(equalTo(DomainChef.cookBoolean(domain.getZippyCreatedIndicator()))));
    assertThat(persistent.getPlacementFacilityType(),
        is(equalTo(domain.getPlacementFacilityType())));
    assertThat(persistent.getLastUpdatedId(), is(equalTo(lastUpdatedId)));

  }

  @Override
  @Test
  public void testEqualsHashCodeWorks() {
    EqualsVerifier.forClass(Allegation.class).suppress(Warning.NONFINAL_FIELDS).verify();

  }

  private gov.ca.cwds.rest.api.domain.cms.Allegation validDomainAllegation()
      throws JsonParseException, JsonMappingException, IOException {

    gov.ca.cwds.rest.api.domain.cms.Allegation validDomainAllegation =
        MAPPER.readValue(fixture("fixtures/domain/legacy/Allegation/valid/valid.json"),
            gov.ca.cwds.rest.api.domain.cms.Allegation.class);
    return validDomainAllegation;
  }

}
