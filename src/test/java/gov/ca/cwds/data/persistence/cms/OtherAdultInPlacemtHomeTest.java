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

import gov.ca.cwds.data.persistence.cms.OtherAdultInPlacemtHome;
import io.dropwizard.jackson.Jackson;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;


public class OtherAdultInPlacemtHomeTest {

  private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

  @Test
  public void equalsHashCodeWork() {
    EqualsVerifier.forClass(OtherAdultInPlacemtHome.class).suppress(Warning.NONFINAL_FIELDS)
        .verify();
  }

  /*
   * Constructor test
   */
  @Test
  public void emtpyConstructorIsNotNull() throws Exception {
    assertThat(OtherAdultInPlacemtHome.class.newInstance(), is(notNullValue()));
  }

  @Test
  public void persistentConstructorTest() throws Exception {
    OtherAdultInPlacemtHome voaph = validOtherAdultInPlacemtHome();

    OtherAdultInPlacemtHome persistent = new OtherAdultInPlacemtHome(voaph.getBirthDate(),
        voaph.getCommentDescription(), voaph.getEndDate(), voaph.getFkplcHmT(),
        voaph.getGenderCode(), voaph.getId(), voaph.getIdentifiedDate(), voaph.getName(),
        voaph.getOtherAdultCode(), voaph.getPassedBackgroundCheckCode(),
        voaph.getResidedOutOfStateIndicator(), voaph.getStartDate());

    assertThat(persistent.getBirthDate(), is(equalTo(voaph.getBirthDate())));
    assertThat(persistent.getCommentDescription(), is(equalTo(voaph.getCommentDescription())));
    assertThat(persistent.getEndDate(), is(equalTo(voaph.getEndDate())));
    assertThat(persistent.getFkplcHmT(), is(equalTo(voaph.getFkplcHmT())));
    assertThat(persistent.getGenderCode(), is(equalTo(voaph.getGenderCode())));
    assertThat(persistent.getId(), is(equalTo(voaph.getId())));
    assertThat(persistent.getIdentifiedDate(), is(equalTo(voaph.getIdentifiedDate())));
    assertThat(persistent.getName(), is(equalTo(voaph.getName())));
    assertThat(persistent.getOtherAdultCode(), is(equalTo(voaph.getOtherAdultCode())));
    assertThat(persistent.getPassedBackgroundCheckCode(),
        is(equalTo(voaph.getPassedBackgroundCheckCode())));
    assertThat(persistent.getResidedOutOfStateIndicator(),
        is(equalTo(voaph.getResidedOutOfStateIndicator())));
    assertThat(persistent.getStartDate(), is(equalTo(voaph.getStartDate())));
  }

  private OtherAdultInPlacemtHome validOtherAdultInPlacemtHome()
      throws JsonParseException, JsonMappingException, IOException {

    OtherAdultInPlacemtHome validOtherAdultInPlacemtHome =
        MAPPER.readValue(fixture("fixtures/domain/legacy/OtherAdultInPlacemtHome/valid/valid.json"),
            OtherAdultInPlacemtHome.class);

    return validOtherAdultInPlacemtHome;

  }
}
