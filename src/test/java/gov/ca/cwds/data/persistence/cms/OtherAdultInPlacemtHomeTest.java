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
 *
 */
public class OtherAdultInPlacemtHomeTest implements PersistentTestTemplate {

  private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

  /*
   * Constructor test
   */
  @Override
  @Test
  public void testEmptyConstructor() throws Exception {
    assertThat(OtherAdultInPlacemtHome.class.newInstance(), is(notNullValue()));
  }

  @Override
  @Test
  public void testPersistentConstructor() throws Exception {
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


  @Override
  public void testConstructorUsingDomain() throws Exception {
    // no domain class

  }

  private OtherAdultInPlacemtHome validOtherAdultInPlacemtHome()
      throws JsonParseException, JsonMappingException, IOException {

    OtherAdultInPlacemtHome validOtherAdultInPlacemtHome =
        MAPPER.readValue(fixture("fixtures/domain/legacy/OtherAdultInPlacemtHome/valid/valid.json"),
            OtherAdultInPlacemtHome.class);

    return validOtherAdultInPlacemtHome;

  }

}
