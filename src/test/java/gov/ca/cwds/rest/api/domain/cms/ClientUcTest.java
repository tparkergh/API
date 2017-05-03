package gov.ca.cwds.rest.api.domain.cms;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import org.junit.After;
import org.junit.ClassRule;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.squarespace.jersey2.guice.JerseyGuiceUtils;

import gov.ca.cwds.rest.resources.cms.JerseyGuiceRule;
import io.dropwizard.jackson.Jackson;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

/**
 * @author CWDS API Team
 *
 */
public class ClientUcTest {

  @SuppressWarnings("javadoc")
  @After
  public void ensureServiceLocatorPopulated() {
    JerseyGuiceUtils.reset();
  }

  @SuppressWarnings("javadoc")
  @ClassRule
  public static JerseyGuiceRule rule = new JerseyGuiceRule();

  /**
   * 
   */
  private static final ObjectMapper MAPPER = Jackson.newObjectMapper();
  private ClientUc validClientUc = validClientUc();

  private String pktableId = "ABC1234567";
  private String sourceTableCode = "C";
  private String commonFirstName = "BORIS";
  private String commonLastName = "MACKAY";
  private String commonMiddleName = "HOWARD";


  /*
   * Constructor Tests
   */
  /**
   * @throws Exception test standard test standard
   */
  @Test
  public void persistentObjectConstructorTest() throws Exception {

    ClientUc domain =
        new ClientUc(pktableId, sourceTableCode, commonFirstName, commonLastName, commonMiddleName);

    gov.ca.cwds.data.persistence.cms.ClientUc persistent =
        new gov.ca.cwds.data.persistence.cms.ClientUc(domain, "lastUpdatedId");

    ClientUc totest = new ClientUc(persistent);
    assertThat(totest.getPktableId(), is(equalTo(persistent.getPktableId())));
    assertThat(totest.getSourceTableCode(), is(equalTo(persistent.getSourceTableCode())));
    assertThat(totest.getCommonFirstName(), is(equalTo(persistent.getCommonFirstName())));
    assertThat(totest.getCommonLastName(), is(equalTo(persistent.getCommonLastName())));
    assertThat(totest.getCommonMiddleName(), is(equalTo(persistent.getCommonMiddleName())));
  }

  /**
   * @throws Exception test standard test standard
   */
  @Test
  public void jsonCreatorConstructorTest() throws Exception {
    ClientUc domain =
        new ClientUc(pktableId, sourceTableCode, commonFirstName, commonLastName, commonMiddleName);

    assertThat(domain.getPktableId(), is(equalTo(pktableId)));
    assertThat(domain.getSourceTableCode(), is(equalTo(sourceTableCode)));
    assertThat(domain.getCommonFirstName(), is(equalTo(commonFirstName)));
    assertThat(domain.getCommonLastName(), is(equalTo(commonLastName)));
    assertThat(domain.getCommonMiddleName(), is(equalTo(commonMiddleName)));
  }

  /**
   * 
   */
  @Test
  public void equalsHashCodeWork() {
    EqualsVerifier.forClass(ClientUc.class)
            .suppress(Warning.NONFINAL_FIELDS)
            .withIgnoredFields("messages")
            .verify();
  }

  /**
   * @throws Exception test standard
   * 
   */
  @Test
  public void serializesToJSON() throws Exception {
    final String expected = MAPPER.writeValueAsString(MAPPER
        .readValue(fixture("fixtures/domain/legacy/ClientUc/valid/valid.json"), ClientUc.class));

    assertThat(MAPPER.writeValueAsString(validClientUc), is(equalTo(expected)));
  }

  /**
   * @throws Exception test standard
   * 
   */
  @Test
  public void deserializesFromJSON() throws Exception {
    assertThat(MAPPER.readValue(fixture("fixtures/domain/legacy/ClientUc/valid/valid.json"),
        ClientUc.class), is(equalTo(validClientUc)));
  }



  /*
   * Utilis
   */
  private ClientUc validClientUc() {
    return new ClientUc("ABC1234567", "C", "BORIS", "MACKAY", "HOWARD");
  }

}
