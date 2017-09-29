package gov.ca.cwds.data.persistence.cms;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import java.io.IOException;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author CWDS API Team
 *
 */
public class CountyOwnershipTest {

  private String entityId = "1234567ABC";

  private static final ObjectMapper MAPPER = SystemCodeTestHarness.MAPPER;

  /*
   * Constructor test
   */
  /**
   * Constructor test
   * 
   * @throws Exception - Exception
   */
  @Test
  public void testEmptyConstructor() throws Exception {
    assertThat(CountyOwnership.class.newInstance(), is(notNullValue()));
  }

  /**
   * persistent constructor
   * 
   * @throws Exception - Exception
   */
  @Test
  public void testPersistentConstructor() throws Exception {

    CountyOwnership vc = validCountyOwnership();

    gov.ca.cwds.data.persistence.cms.CountyOwnership persistent =
        new gov.ca.cwds.data.persistence.cms.CountyOwnership(entityId, vc.getEntityCode(),
            vc.getMultiFlag(), vc.getCounty00Flag(), vc.getCounty01Flag(), vc.getCounty02Flag(),
            vc.getCounty03Flag(), vc.getCounty04Flag(), vc.getCounty05Flag(), vc.getCounty06Flag(),
            vc.getCounty07Flag(), vc.getCounty08Flag(), vc.getCounty09Flag(), vc.getCounty10Flag(),
            vc.getCounty11Flag(), vc.getCounty12Flag(), vc.getCounty13Flag(), vc.getCounty14Flag(),
            vc.getCounty15Flag(), vc.getCounty16Flag(), vc.getCounty17Flag(), vc.getCounty18Flag(),
            vc.getCounty19Flag(), vc.getCounty20Flag(), vc.getCounty21Flag(), vc.getCounty22Flag(),
            vc.getCounty23Flag(), vc.getCounty24Flag(), vc.getCounty25Flag(), vc.getCounty26Flag(),
            vc.getCounty27Flag(), vc.getCounty28Flag(), vc.getCounty29Flag(), vc.getCounty30Flag(),
            vc.getCounty31Flag(), vc.getCounty32Flag(), vc.getCounty33Flag(), vc.getCounty34Flag(),
            vc.getCounty35Flag(), vc.getCounty36Flag(), vc.getCounty37Flag(), vc.getCounty38Flag(),
            vc.getCounty39Flag(), vc.getCounty40Flag(), vc.getCounty41Flag(), vc.getCounty42Flag(),
            vc.getCounty43Flag(), vc.getCounty44Flag(), vc.getCounty45Flag(), vc.getCounty46Flag(),
            vc.getCounty47Flag(), vc.getCounty48Flag(), vc.getCounty49Flag(), vc.getCounty50Flag(),
            vc.getCounty51Flag(), vc.getCounty52Flag(), vc.getCounty53Flag(), vc.getCounty54Flag(),
            vc.getCounty55Flag(), vc.getCounty56Flag(), vc.getCounty57Flag(), vc.getCounty58Flag(),
            vc.getCounty59Flag(), vc.getCounty60Flag(), vc.getCounty61Flag(), vc.getCounty62Flag(),
            vc.getCounty63Flag(), vc.getDeleteDate());

    assertThat(persistent.getEntityId(), is(equalTo(entityId)));
    assertThat(persistent.getEntityCode(), is(equalTo(vc.getEntityCode())));
    assertThat(persistent.getMultiFlag(), is(equalTo(vc.getMultiFlag())));
    assertThat(persistent.getCounty01Flag(), is(equalTo(vc.getCounty01Flag())));
    assertThat(persistent.getCounty02Flag(), is(equalTo(vc.getCounty02Flag())));
    assertThat(persistent.getCounty03Flag(), is(equalTo(vc.getCounty03Flag())));
    assertThat(persistent.getCounty04Flag(), is(equalTo(vc.getCounty04Flag())));
    assertThat(persistent.getCounty05Flag(), is(equalTo(vc.getCounty05Flag())));
    assertThat(persistent.getCounty06Flag(), is(equalTo(vc.getCounty06Flag())));
    assertThat(persistent.getCounty07Flag(), is(equalTo(vc.getCounty07Flag())));
    assertThat(persistent.getCounty08Flag(), is(equalTo(vc.getCounty08Flag())));
    assertThat(persistent.getCounty09Flag(), is(equalTo(vc.getCounty09Flag())));
    assertThat(persistent.getCounty10Flag(), is(equalTo(vc.getCounty10Flag())));
    assertThat(persistent.getCounty11Flag(), is(equalTo(vc.getCounty11Flag())));
    assertThat(persistent.getCounty12Flag(), is(equalTo(vc.getCounty12Flag())));
    assertThat(persistent.getCounty13Flag(), is(equalTo(vc.getCounty13Flag())));
    assertThat(persistent.getCounty14Flag(), is(equalTo(vc.getCounty14Flag())));
    assertThat(persistent.getCounty15Flag(), is(equalTo(vc.getCounty15Flag())));
    assertThat(persistent.getCounty16Flag(), is(equalTo(vc.getCounty16Flag())));
    assertThat(persistent.getCounty17Flag(), is(equalTo(vc.getCounty17Flag())));
    assertThat(persistent.getCounty18Flag(), is(equalTo(vc.getCounty18Flag())));
    assertThat(persistent.getCounty19Flag(), is(equalTo(vc.getCounty19Flag())));
    assertThat(persistent.getCounty20Flag(), is(equalTo(vc.getCounty20Flag())));
    assertThat(persistent.getCounty21Flag(), is(equalTo(vc.getCounty21Flag())));
    assertThat(persistent.getCounty22Flag(), is(equalTo(vc.getCounty22Flag())));
    assertThat(persistent.getCounty23Flag(), is(equalTo(vc.getCounty23Flag())));
    assertThat(persistent.getCounty24Flag(), is(equalTo(vc.getCounty24Flag())));
    assertThat(persistent.getCounty25Flag(), is(equalTo(vc.getCounty25Flag())));
    assertThat(persistent.getCounty26Flag(), is(equalTo(vc.getCounty26Flag())));
    assertThat(persistent.getCounty27Flag(), is(equalTo(vc.getCounty27Flag())));
    assertThat(persistent.getCounty28Flag(), is(equalTo(vc.getCounty28Flag())));
    assertThat(persistent.getCounty29Flag(), is(equalTo(vc.getCounty29Flag())));
    assertThat(persistent.getCounty30Flag(), is(equalTo(vc.getCounty30Flag())));
    assertThat(persistent.getCounty31Flag(), is(equalTo(vc.getCounty31Flag())));
    assertThat(persistent.getCounty32Flag(), is(equalTo(vc.getCounty32Flag())));
    assertThat(persistent.getCounty33Flag(), is(equalTo(vc.getCounty33Flag())));
    assertThat(persistent.getCounty34Flag(), is(equalTo(vc.getCounty34Flag())));
    assertThat(persistent.getCounty35Flag(), is(equalTo(vc.getCounty35Flag())));
    assertThat(persistent.getCounty36Flag(), is(equalTo(vc.getCounty36Flag())));
    assertThat(persistent.getCounty37Flag(), is(equalTo(vc.getCounty37Flag())));
    assertThat(persistent.getCounty38Flag(), is(equalTo(vc.getCounty38Flag())));
    assertThat(persistent.getCounty39Flag(), is(equalTo(vc.getCounty39Flag())));
    assertThat(persistent.getCounty40Flag(), is(equalTo(vc.getCounty40Flag())));
    assertThat(persistent.getCounty41Flag(), is(equalTo(vc.getCounty41Flag())));
    assertThat(persistent.getCounty42Flag(), is(equalTo(vc.getCounty42Flag())));
    assertThat(persistent.getCounty43Flag(), is(equalTo(vc.getCounty43Flag())));
    assertThat(persistent.getCounty44Flag(), is(equalTo(vc.getCounty44Flag())));
    assertThat(persistent.getCounty45Flag(), is(equalTo(vc.getCounty45Flag())));
    assertThat(persistent.getCounty46Flag(), is(equalTo(vc.getCounty46Flag())));
    assertThat(persistent.getCounty47Flag(), is(equalTo(vc.getCounty47Flag())));
    assertThat(persistent.getCounty48Flag(), is(equalTo(vc.getCounty48Flag())));
    assertThat(persistent.getCounty49Flag(), is(equalTo(vc.getCounty49Flag())));
    assertThat(persistent.getCounty50Flag(), is(equalTo(vc.getCounty50Flag())));
    assertThat(persistent.getCounty51Flag(), is(equalTo(vc.getCounty51Flag())));
    assertThat(persistent.getCounty52Flag(), is(equalTo(vc.getCounty52Flag())));
    assertThat(persistent.getCounty53Flag(), is(equalTo(vc.getCounty53Flag())));
    assertThat(persistent.getCounty54Flag(), is(equalTo(vc.getCounty54Flag())));
    assertThat(persistent.getCounty55Flag(), is(equalTo(vc.getCounty55Flag())));
    assertThat(persistent.getCounty56Flag(), is(equalTo(vc.getCounty56Flag())));
    assertThat(persistent.getCounty57Flag(), is(equalTo(vc.getCounty57Flag())));
    assertThat(persistent.getCounty58Flag(), is(equalTo(vc.getCounty58Flag())));
    assertThat(persistent.getCounty59Flag(), is(equalTo(vc.getCounty59Flag())));
    assertThat(persistent.getCounty60Flag(), is(equalTo(vc.getCounty60Flag())));
    assertThat(persistent.getCounty61Flag(), is(equalTo(vc.getCounty61Flag())));
    assertThat(persistent.getCounty62Flag(), is(equalTo(vc.getCounty62Flag())));
    assertThat(persistent.getCounty63Flag(), is(equalTo(vc.getCounty63Flag())));
    assertThat(persistent.getDeleteDate(), is(equalTo(vc.getDeleteDate())));
  }

  private CountyOwnership validCountyOwnership()
      throws JsonParseException, JsonMappingException, IOException {

    CountyOwnership validCountyOwnership = MAPPER.readValue(
        fixture("fixtures/persistent/CountyOwnership/valid/valid.json"), CountyOwnership.class);
    return validCountyOwnership;

  }

}
