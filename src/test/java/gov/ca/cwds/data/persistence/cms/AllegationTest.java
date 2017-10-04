package gov.ca.cwds.data.persistence.cms;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import java.io.IOException;
import java.util.Date;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import gov.ca.cwds.data.persistence.junit.template.PersistentTestTemplate;
import gov.ca.cwds.fixture.AllegationEntityBuilder;
import gov.ca.cwds.fixture.CmsAllegationResourceBuilder;
import gov.ca.cwds.rest.api.domain.DomainChef;

/**
 * @author CWDS API Team
 *
 */
public class AllegationTest implements PersistentTestTemplate {

  protected static final String TIMESTAMP_FORMAT = "yyyy-MM-dd-HH.mm.ss.SSS";
  private String id = "1234567ABC";
  private String lastUpdatedId = "0X5";
  private Date lastUpdatedTime = new Date();

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

    Allegation validPersistent = new AllegationEntityBuilder().build();

    gov.ca.cwds.data.persistence.cms.Allegation persistent =
        new gov.ca.cwds.data.persistence.cms.Allegation(id, validPersistent.getAbuseEndDate(),
            validPersistent.getAbuseStartDate(), validPersistent.getAbuseFrequency(),
            validPersistent.getAbuseFrequencyPeriodCode(),
            validPersistent.getAbuseLocationDescription(),
            validPersistent.getAllegationDispositionType(), validPersistent.getAllegationType(),
            validPersistent.getDispositionDescription(), validPersistent.getDispositionDate(),
            validPersistent.getInjuryHarmDetailIndicator(),
            validPersistent.getNonProtectingParentCode(),
            validPersistent.getStaffPersonAddedIndicator(), validPersistent.getVictimClientId(),
            validPersistent.getPerpetratorClientId(), validPersistent.getReferralId(),
            validPersistent.getCountySpecificCode(), validPersistent.getZippyCreatedIndicator(),
            validPersistent.getPlacementFacilityType(), null, null);

    assertThat(persistent.getId(), is(equalTo(id)));
    assertThat(persistent.getAbuseEndDate(), is(equalTo(validPersistent.getAbuseEndDate())));
    assertThat(persistent.getAbuseStartDate(), is(equalTo(validPersistent.getAbuseStartDate())));
    assertThat(persistent.getAbuseFrequency(), is(equalTo(validPersistent.getAbuseFrequency())));
    assertThat(persistent.getAbuseFrequencyPeriodCode(),
        is(equalTo(validPersistent.getAbuseFrequencyPeriodCode())));
    assertThat(persistent.getAbuseLocationDescription(),
        is(equalTo(validPersistent.getAbuseLocationDescription())));
    assertThat(persistent.getAllegationDispositionType(),
        is(equalTo(validPersistent.getAllegationDispositionType())));
    assertThat(persistent.getAllegationType(), is(equalTo(validPersistent.getAllegationType())));
    assertThat(persistent.getDispositionDescription(),
        is(equalTo(validPersistent.getDispositionDescription())));
    assertThat(persistent.getDispositionDate(),
        is(equalTo((validPersistent.getDispositionDate()))));
    assertThat(persistent.getInjuryHarmDetailIndicator(),
        is(equalTo(validPersistent.getInjuryHarmDetailIndicator())));
    assertThat(persistent.getNonProtectingParentCode(),
        is(equalTo(validPersistent.getNonProtectingParentCode())));
    assertThat(persistent.getStaffPersonAddedIndicator(),
        is(equalTo(validPersistent.getStaffPersonAddedIndicator())));
    assertThat(persistent.getPerpetratorClientId(),
        is(equalTo(validPersistent.getPerpetratorClientId())));
    assertThat(persistent.getVictimClientId(), is(equalTo(validPersistent.getVictimClientId())));
    assertThat(persistent.getReferralId(), is(equalTo(validPersistent.getReferralId())));
    assertThat(persistent.getCountySpecificCode(),
        is(equalTo(validPersistent.getCountySpecificCode())));
    assertThat(persistent.getZippyCreatedIndicator(),
        is(equalTo(validPersistent.getZippyCreatedIndicator())));
    assertThat(persistent.getPlacementFacilityType(),
        is(equalTo(validPersistent.getPlacementFacilityType())));
  }

  @Override
  @Test
  public void testConstructorUsingDomain() throws Exception {

    gov.ca.cwds.rest.api.domain.cms.Allegation domainAllegation =
        new CmsAllegationResourceBuilder().buildCmsAllegation();

    Allegation persistent = new Allegation(id, domainAllegation, lastUpdatedId);

    assertThat(persistent.getId(), is(equalTo(id)));
    assertThat(persistent.getAbuseEndDate(),
        is(equalTo(DomainChef.uncookDateString(domainAllegation.getAbuseEndDate()))));
    assertThat(persistent.getAbuseStartDate(),
        is(equalTo(DomainChef.uncookDateString(domainAllegation.getAbuseStartDate()))));
    assertThat(persistent.getAbuseFrequency(), is(equalTo(domainAllegation.getAbuseFrequency())));
    assertThat(persistent.getAbuseFrequencyPeriodCode(),
        is(equalTo(domainAllegation.getAbuseFrequencyPeriodCode())));
    assertThat(persistent.getAbuseLocationDescription(),
        is(equalTo(domainAllegation.getAbuseLocationDescription())));
    assertThat(persistent.getAllegationDispositionType(),
        is(equalTo(domainAllegation.getAllegationDispositionType())));
    assertThat(persistent.getAllegationType(), is(equalTo(domainAllegation.getAllegationType())));
    assertThat(persistent.getDispositionDescription(),
        is(equalTo(domainAllegation.getDispositionDescription())));
    assertThat(persistent.getDispositionDate(),
        is(equalTo(DomainChef.uncookDateString((domainAllegation.getDispositionDate())))));
    assertThat(persistent.getInjuryHarmDetailIndicator(),
        is(equalTo(DomainChef.cookBoolean(domainAllegation.getInjuryHarmDetailIndicator()))));
    assertThat(persistent.getNonProtectingParentCode(),
        is(equalTo(domainAllegation.getNonProtectingParentCode())));
    assertThat(persistent.getStaffPersonAddedIndicator(),
        is(equalTo(DomainChef.cookBoolean(domainAllegation.getStaffPersonAddedIndicator()))));
    assertThat(persistent.getPerpetratorClientId(),
        is(equalTo(domainAllegation.getPerpetratorClientId())));
    assertThat(persistent.getVictimClientId(), is(equalTo(domainAllegation.getVictimClientId())));
    assertThat(persistent.getReferralId(), is(equalTo(domainAllegation.getReferralId())));
    assertThat(persistent.getCountySpecificCode(),
        is(equalTo(domainAllegation.getCountySpecificCode())));
    assertThat(persistent.getZippyCreatedIndicator(),
        is(equalTo(DomainChef.cookBoolean(domainAllegation.getZippyCreatedIndicator()))));
    assertThat(persistent.getPlacementFacilityType(),
        is(equalTo(domainAllegation.getPlacementFacilityType())));
    assertThat(persistent.getLastUpdatedId(), is(equalTo(lastUpdatedId)));

  }

  @SuppressWarnings("javadoc")
  @Test
  public void testConstructorUsingLastUpdatedTime() throws Exception {

    gov.ca.cwds.rest.api.domain.cms.Allegation domainAllegation =
        new CmsAllegationResourceBuilder().buildCmsAllegation();

    Allegation persistent = new Allegation(id, domainAllegation, lastUpdatedId, lastUpdatedTime);

    assertThat(persistent.getId(), is(equalTo(id)));
    assertThat(persistent.getAbuseEndDate(),
        is(equalTo(DomainChef.uncookDateString(domainAllegation.getAbuseEndDate()))));
    assertThat(persistent.getAbuseStartDate(),
        is(equalTo(DomainChef.uncookDateString(domainAllegation.getAbuseStartDate()))));
    assertThat(persistent.getAbuseFrequency(), is(equalTo(domainAllegation.getAbuseFrequency())));
    assertThat(persistent.getAbuseFrequencyPeriodCode(),
        is(equalTo(domainAllegation.getAbuseFrequencyPeriodCode())));
    assertThat(persistent.getAbuseLocationDescription(),
        is(equalTo(domainAllegation.getAbuseLocationDescription())));
    assertThat(persistent.getAllegationDispositionType(),
        is(equalTo(domainAllegation.getAllegationDispositionType())));
    assertThat(persistent.getAllegationType(), is(equalTo(domainAllegation.getAllegationType())));
    assertThat(persistent.getDispositionDescription(),
        is(equalTo(domainAllegation.getDispositionDescription())));
    assertThat(persistent.getDispositionDate(),
        is(equalTo(DomainChef.uncookDateString((domainAllegation.getDispositionDate())))));
    assertThat(persistent.getInjuryHarmDetailIndicator(),
        is(equalTo(DomainChef.cookBoolean(domainAllegation.getInjuryHarmDetailIndicator()))));
    assertThat(persistent.getNonProtectingParentCode(),
        is(equalTo(domainAllegation.getNonProtectingParentCode())));
    assertThat(persistent.getStaffPersonAddedIndicator(),
        is(equalTo(DomainChef.cookBoolean(domainAllegation.getStaffPersonAddedIndicator()))));
    assertThat(persistent.getPerpetratorClientId(),
        is(equalTo(domainAllegation.getPerpetratorClientId())));
    assertThat(persistent.getVictimClientId(), is(equalTo(domainAllegation.getVictimClientId())));
    assertThat(persistent.getReferralId(), is(equalTo(domainAllegation.getReferralId())));
    assertThat(persistent.getCountySpecificCode(),
        is(equalTo(domainAllegation.getCountySpecificCode())));
    assertThat(persistent.getZippyCreatedIndicator(),
        is(equalTo(DomainChef.cookBoolean(domainAllegation.getZippyCreatedIndicator()))));
    assertThat(persistent.getPlacementFacilityType(),
        is(equalTo(domainAllegation.getPlacementFacilityType())));
    assertThat(persistent.getLastUpdatedId(), is(equalTo(lastUpdatedId)));
    assertThat(persistent.getLastUpdatedTime(), is(equalTo(lastUpdatedTime)));

  }

  @SuppressWarnings("javadoc")
  @Test
  public void testConstructorUsingDomainWithNullDate() throws Exception {

    gov.ca.cwds.rest.api.domain.cms.Allegation domainAllegation =
        new CmsAllegationResourceBuilder().buildCmsAllegation();

    Allegation persistent = new Allegation(id, domainAllegation, lastUpdatedId);

    assertThat(persistent.getId(), is(equalTo(id)));
    assertThat(persistent.getAbuseEndDate(),
        is(equalTo(DomainChef.uncookDateString(domainAllegation.getAbuseEndDate()))));
    assertThat(persistent.getAbuseStartDate(),
        is(equalTo(DomainChef.uncookDateString(domainAllegation.getAbuseStartDate()))));
    assertThat(persistent.getAbuseFrequency(), is(equalTo(domainAllegation.getAbuseFrequency())));
    assertThat(persistent.getAbuseFrequencyPeriodCode(),
        is(equalTo(domainAllegation.getAbuseFrequencyPeriodCode())));
    assertThat(persistent.getAbuseLocationDescription(),
        is(equalTo(domainAllegation.getAbuseLocationDescription())));
    assertThat(persistent.getAllegationDispositionType(),
        is(equalTo(domainAllegation.getAllegationDispositionType())));
    assertThat(persistent.getAllegationType(), is(equalTo(domainAllegation.getAllegationType())));
    assertThat(persistent.getDispositionDescription(),
        is(equalTo(domainAllegation.getDispositionDescription())));
    assertThat(persistent.getDispositionDate(),
        is(equalTo(DomainChef.uncookDateString((domainAllegation.getDispositionDate())))));
    assertThat(persistent.getInjuryHarmDetailIndicator(),
        is(equalTo(DomainChef.cookBoolean(domainAllegation.getInjuryHarmDetailIndicator()))));
    assertThat(persistent.getNonProtectingParentCode(),
        is(equalTo(domainAllegation.getNonProtectingParentCode())));
    assertThat(persistent.getStaffPersonAddedIndicator(),
        is(equalTo(DomainChef.cookBoolean(domainAllegation.getStaffPersonAddedIndicator()))));
    assertThat(persistent.getPerpetratorClientId(),
        is(equalTo(domainAllegation.getPerpetratorClientId())));
    assertThat(persistent.getVictimClientId(), is(equalTo(domainAllegation.getVictimClientId())));
    assertThat(persistent.getReferralId(), is(equalTo(domainAllegation.getReferralId())));
    assertThat(persistent.getCountySpecificCode(),
        is(equalTo(domainAllegation.getCountySpecificCode())));
    assertThat(persistent.getZippyCreatedIndicator(),
        is(equalTo(DomainChef.cookBoolean(domainAllegation.getZippyCreatedIndicator()))));
    assertThat(persistent.getPlacementFacilityType(),
        is(equalTo(domainAllegation.getPlacementFacilityType())));
    assertThat(persistent.getLastUpdatedId(), is(equalTo(lastUpdatedId)));
  }

  @SuppressWarnings("javadoc")
  @Test
  public void testSerializeAndDeserialize() throws Exception {
    Allegation validPersistent = validAllegation();

    gov.ca.cwds.data.persistence.cms.Allegation persistent =
        new gov.ca.cwds.data.persistence.cms.Allegation(id, validPersistent.getAbuseEndDate(),
            validPersistent.getAbuseStartDate(), validPersistent.getAbuseFrequency(),
            validPersistent.getAbuseFrequencyPeriodCode(),
            validPersistent.getAbuseLocationDescription(),
            validPersistent.getAllegationDispositionType(), validPersistent.getAllegationType(),
            validPersistent.getDispositionDescription(), validPersistent.getDispositionDate(),
            validPersistent.getInjuryHarmDetailIndicator(),
            validPersistent.getNonProtectingParentCode(),
            validPersistent.getStaffPersonAddedIndicator(), validPersistent.getVictimClientId(),
            validPersistent.getPerpetratorClientId(), validPersistent.getReferralId(),
            validPersistent.getCountySpecificCode(), validPersistent.getZippyCreatedIndicator(),
            validPersistent.getPlacementFacilityType(), null, null);


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

}
