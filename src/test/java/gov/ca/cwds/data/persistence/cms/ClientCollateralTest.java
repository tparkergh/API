package gov.ca.cwds.data.persistence.cms;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.assertj.core.api.Assertions.assertThat;
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

/**
 * @author CWDS API Team
 *
 */
public class ClientCollateralTest implements PersistentTestTemplate {

  private static final ObjectMapper MAPPER = SystemCodeTestHarness.MAPPER;

  /*
   * Constructor test
   */
  @Override
  @Test
  public void testEmptyConstructor() throws Exception {
    assertThat(ClientCollateral.class.newInstance(), is(notNullValue()));
  }

  @Override
  @Test
  public void testPersistentConstructor() throws Exception {
    ClientCollateral ci = validClientCollateral();

    ClientCollateral persistent = new ClientCollateral(ci.getActiveIndicator(),
        ci.getCollateralClientReporterRelationshipType(), ci.getCommentDescription(),
        ci.getClientId(), ci.getCollateralIndividualId(), ci.getThirdId());

    assertThat(persistent.getActiveIndicator(), is(equalTo(ci.getActiveIndicator())));
    assertThat(persistent.getCollateralClientReporterRelationshipType(),
        is(equalTo(ci.getCollateralClientReporterRelationshipType())));
    assertThat(persistent.getCommentDescription(), is(equalTo(ci.getCommentDescription())));
    assertThat(persistent.getClientId(), is(equalTo(ci.getClientId())));
    assertThat(persistent.getCollateralIndividualId(), is(equalTo(ci.getCollateralIndividualId())));
    assertThat(persistent.getThirdId(), is(equalTo(ci.getThirdId())));
  }

  @Override
  public void testConstructorUsingDomain() throws Exception {
    // no domain class.
  }

  @SuppressWarnings("javadoc")
  @Test
  public void testSerializeAndDeserialize() throws Exception {
    ClientCollateral ci = validClientCollateral();

    ClientCollateral persistent = new ClientCollateral(ci.getActiveIndicator(),
        ci.getCollateralClientReporterRelationshipType(), ci.getCommentDescription(),
        ci.getClientId(), ci.getCollateralIndividualId(), ci.getThirdId());
    final String expected = MAPPER.writeValueAsString((MAPPER.readValue(
        fixture("fixtures/persistent/ClientCollateral/valid/validWithSysCodes.json"),
        ClientCollateral.class)));

    assertThat(MAPPER.writeValueAsString(persistent)).isEqualTo(expected);
  }

  private ClientCollateral validClientCollateral()
      throws JsonParseException, JsonMappingException, IOException {

    ClientCollateral validClientCollateral = MAPPER.readValue(
        fixture("fixtures/persistent/ClientCollateral/valid/valid.json"), ClientCollateral.class);

    return validClientCollateral;
  }

}
