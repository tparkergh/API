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

import gov.ca.cwds.data.persistence.junit.template.PersistentTestTemplate;
import io.dropwizard.jackson.Jackson;

/**
 * @author CWDS API Team
 */
public class OtherChildInPlacemtHomeTest implements PersistentTestTemplate {

  private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

  /*
   * Constructor test
   */
  @Override
  @Test
  public void testEmptyConstructor() throws Exception {
    assertThat(OtherChildInPlacemtHome.class.newInstance(), is(notNullValue()));
  }

  @Override
  @Test
  public void testPersistentConstructor() throws Exception {
    OtherChildInPlacemtHome vocph = validOtherChildInPlacemtHome();

    OtherChildInPlacemtHome persistent =
        new OtherChildInPlacemtHome(vocph.getAnnualUnearnedIncomeAmount(), vocph.getBirthDate(),
            vocph.getFkplcHmT(), vocph.getGenderCode(), vocph.getId(), vocph.getName());

    assertThat(persistent.getAnnualUnearnedIncomeAmount(),
        is(equalTo(vocph.getAnnualUnearnedIncomeAmount())));
    assertThat(persistent.getBirthDate(), is(equalTo(vocph.getBirthDate())));
    assertThat(persistent.getFkplcHmT(), is(equalTo(vocph.getFkplcHmT())));
    assertThat(persistent.getGenderCode(), is(equalTo(vocph.getGenderCode())));
    assertThat(persistent.getId(), is(equalTo(vocph.getId())));
    assertThat(persistent.getName(), is(equalTo(vocph.getName())));
  }

  private OtherChildInPlacemtHome validOtherChildInPlacemtHome()
      throws JsonParseException, JsonMappingException, IOException {

    OtherChildInPlacemtHome validOtherChildInPlacemtHome =
        MAPPER.readValue(fixture("fixtures/domain/legacy/OtherChildInPlacemtHome/valid/valid.json"),
            OtherChildInPlacemtHome.class);

    return validOtherChildInPlacemtHome;
  }


  @Override
  public void testConstructorUsingDomain() throws Exception {

  }

}
