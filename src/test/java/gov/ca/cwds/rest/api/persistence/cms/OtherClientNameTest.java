package gov.ca.cwds.rest.api.persistence.cms;

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

import io.dropwizard.jackson.Jackson;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;


public class OtherClientNameTest {

  private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

  @Test
  public void equalsHashCodeWork() {
    EqualsVerifier.forClass(OtherClientName.class).suppress(Warning.NONFINAL_FIELDS).verify();
  }

  /*
   * Constructor test
   */
  @Test
  public void emtpyConstructorIsNotNull() throws Exception {
    assertThat(OtherClientName.class.newInstance(), is(notNullValue()));
  }

  @Test
  public void persistentConstructorTest() throws Exception {
    OtherClientName vocn = validOtherClientName();
    OtherClientName persistent = new OtherClientName(vocn.getClientId(), vocn.getFirstName(),
        vocn.getLastName(), vocn.getMiddleName(), vocn.getNamePrefixDescription(),
        vocn.getNameType(), vocn.getSuffixTitleDescription(), vocn.getThirdId());

    assertThat(persistent.getClientId(), is(equalTo(vocn.getClientId())));
    assertThat(persistent.getFirstName(), is(equalTo(vocn.getFirstName())));
    assertThat(persistent.getLastName(), is(equalTo(vocn.getLastName())));
    assertThat(persistent.getMiddleName(), is(equalTo(vocn.getMiddleName())));
    assertThat(persistent.getNamePrefixDescription(), is(equalTo(vocn.getNamePrefixDescription())));
    assertThat(persistent.getNameType(), is(equalTo(vocn.getNameType())));
    assertThat(persistent.getSuffixTitleDescription(),
        is(equalTo(vocn.getSuffixTitleDescription())));
    assertThat(persistent.getThirdId(), is(equalTo(vocn.getThirdId())));
  }

  private OtherClientName validOtherClientName()
      throws JsonParseException, JsonMappingException, IOException {

    OtherClientName validOtherClientName = MAPPER.readValue(
        fixture("fixtures/domain/cms/OtherClientName/valid/valid.json"), OtherClientName.class);
    return validOtherClientName;
  }
}
