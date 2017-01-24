package gov.ca.cwds.data.persistence.cms;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.assertj.core.api.Assertions.assertThat;
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

  private static final ObjectMapper MAPPER = SystemCodeTestHarness.MAPPER;

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
  public void testPersistentConstructor() throws Exception {

    Allegation vp = validAllegation();

    gov.ca.cwds.data.persistence.cms.Allegation persistent =
        new gov.ca.cwds.data.persistence.cms.Allegation(id, vp.getAbuseEndDate(),
            vp.getAbuseStartDate(), vp.getAbuseFrequency(), vp.getAbuseFrequencyPeriodCode(),
            vp.getAbuseLocationDescription(), vp.getAllegationDispositionType(),
            vp.getAllegationType(), vp.getDispositionDescription(), vp.getDispositionDate(),
            vp.getInjuryHarmDetailIndicator(), vp.getNonProtectingParentCode(),
            vp.getStaffPersonAddedIndicator(), vp.getVictimClientId(), vp.getPerpetratorClientId(),
            vp.getReferralId(), vp.getCountySpecificCode(), vp.getZippyCreatedIndicator(),
            vp.getPlacementFacilityType());

    assertThat(persistent.getId(), is(equalTo(id)));
    assertThat(persistent.getAbuseEndDate(), is(equalTo(vp.getAbuseEndDate())));
    assertThat(persistent.getAbuseStartDate(), is(equalTo(vp.getAbuseStartDate())));
    assertThat(persistent.getAbuseFrequency(), is(equalTo(vp.getAbuseFrequency())));
    assertThat(persistent.getAbuseFrequencyPeriodCode(),
        is(equalTo(vp.getAbuseFrequencyPeriodCode())));
    assertThat(persistent.getAbuseLocationDescription(),
        is(equalTo(vp.getAbuseLocationDescription())));
    assertThat(persistent.getAllegationDispositionType(),
        is(equalTo(vp.getAllegationDispositionType())));
    assertThat(persistent.getAllegationType(), is(equalTo(vp.getAllegationType())));
    assertThat(persistent.getDispositionDescription(), is(equalTo(vp.getDispositionDescription())));
    assertThat(persistent.getDispositionDate(), is(equalTo((vp.getDispositionDate()))));
    assertThat(persistent.getInjuryHarmDetailIndicator(),
        is(equalTo(vp.getInjuryHarmDetailIndicator())));
    assertThat(persistent.getNonProtectingParentCode(),
        is(equalTo(vp.getNonProtectingParentCode())));
    assertThat(persistent.getStaffPersonAddedIndicator(),
        is(equalTo(vp.getStaffPersonAddedIndicator())));
    assertThat(persistent.getPerpetratorClientId(), is(equalTo(vp.getPerpetratorClientId())));
    assertThat(persistent.getVictimClientId(), is(equalTo(vp.getVictimClientId())));
    assertThat(persistent.getReferralId(), is(equalTo(vp.getReferralId())));
    assertThat(persistent.getCountySpecificCode(), is(equalTo(vp.getCountySpecificCode())));
    assertThat(persistent.getZippyCreatedIndicator(), is(equalTo(vp.getZippyCreatedIndicator())));
    assertThat(persistent.getPlacementFacilityType(), is(equalTo(vp.getPlacementFacilityType())));
  }

  @Override
  @Test
  public void testConstructorUsingDomain() throws Exception {

    gov.ca.cwds.rest.api.domain.cms.Allegation domain = validDomainAllegation();

    Allegation persistent = new Allegation(id, domain, lastUpdatedId);

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

  @SuppressWarnings("javadoc")
  @Test
  public void testSerializeAndDeserialize() throws Exception {
    Allegation vp = validAllegation();

    gov.ca.cwds.data.persistence.cms.Allegation persistent =
        new gov.ca.cwds.data.persistence.cms.Allegation(id, vp.getAbuseEndDate(),
            vp.getAbuseStartDate(), vp.getAbuseFrequency(), vp.getAbuseFrequencyPeriodCode(),
            vp.getAbuseLocationDescription(), vp.getAllegationDispositionType(),
            vp.getAllegationType(), vp.getDispositionDescription(), vp.getDispositionDate(),
            vp.getInjuryHarmDetailIndicator(), vp.getNonProtectingParentCode(),
            vp.getStaffPersonAddedIndicator(), vp.getVictimClientId(), vp.getPerpetratorClientId(),
            vp.getReferralId(), vp.getCountySpecificCode(), vp.getZippyCreatedIndicator(),
            vp.getPlacementFacilityType());


    final String expected = MAPPER.writeValueAsString((MAPPER.readValue(
        fixture("fixtures/persistent/Allegation/valid/validWithSysCodes.json"), Allegation.class)));
    assertThat(MAPPER.writeValueAsString(persistent)).isEqualTo(expected);
  }

  private Allegation validAllegation()
      throws JsonParseException, JsonMappingException, IOException {

    Allegation validAllegation = MAPPER
        .readValue(fixture("fixtures/persistent/Allegation/valid/valid.json"), Allegation.class);
    return validAllegation;

  }

  private gov.ca.cwds.rest.api.domain.cms.Allegation validDomainAllegation()
      throws JsonParseException, JsonMappingException, IOException {

    gov.ca.cwds.rest.api.domain.cms.Allegation validDomainAllegation =
        MAPPER.readValue(fixture("fixtures/domain/legacy/Allegation/valid/valid.json"),
            gov.ca.cwds.rest.api.domain.cms.Allegation.class);
    return validDomainAllegation;
  }

}
