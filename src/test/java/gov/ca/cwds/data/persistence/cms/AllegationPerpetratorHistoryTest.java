package gov.ca.cwds.data.persistence.cms;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import java.io.IOException;

import org.junit.Ignore;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import gov.ca.cwds.data.persistence.junit.template.PersistentTestTemplate;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

/**
 * @author CWDS API Team
 */
public class AllegationPerpetratorHistoryTest implements PersistentTestTemplate {

  private String id = "1234567ABC";

  private static final ObjectMapper MAPPER = SystemCodeTestHarness.MAPPER;

  /*
   * Constructor test
   */
  @Override
  @Test
  public void testEmptyConstructor() throws Exception {
    assertThat(AllegationPerpetratorHistory.class.newInstance(), is(notNullValue()));
  }

  @Override
  public void testConstructorUsingDomain() throws Exception {

  }

  @Override
  @Test
  public void testPersistentConstructor() throws Exception {

    AllegationPerpetratorHistory vp = validAllegationPerpetratorHistory();

    gov.ca.cwds.data.persistence.cms.AllegationPerpetratorHistory persistent =
        new gov.ca.cwds.data.persistence.cms.AllegationPerpetratorHistory(id,
            vp.getCountySpecificCode(), vp.getPerpetratorClientId(), vp.getAllegationId(),
            vp.getPerpetratorUpdateDate());

    assertThat(persistent.getId(), is(equalTo(id)));
    assertThat(persistent.getCountySpecificCode(), is(equalTo(vp.getCountySpecificCode())));
    assertThat(persistent.getPerpetratorClientId(), is(equalTo(vp.getPerpetratorClientId())));
    assertThat(persistent.getAllegationId(), is(equalTo(vp.getAllegationId())));
    assertThat(persistent.getPerpetratorUpdateDate(), is(equalTo(vp.getPerpetratorUpdateDate())));
  }

  @SuppressWarnings("javadoc")
  @Test
  public void testConstructorUsingLastUpdatedTime() throws Exception {

  }

  @Override
  @Test
  @Ignore
  public void testEqualsHashCodeWorks() {
    EqualsVerifier.forClass(AllegationPerpetratorHistory.class).suppress(Warning.NONFINAL_FIELDS)
        .verify();
  }

  private AllegationPerpetratorHistory validAllegationPerpetratorHistory()
      throws JsonParseException, JsonMappingException, IOException {

    AllegationPerpetratorHistory validAllegationPerpetratorHistory = MAPPER.readValue(
        fixture("fixtures/persistent/AllegationPerpetratorHistory/valid/valid.json"),
        AllegationPerpetratorHistory.class);
    return validAllegationPerpetratorHistory;
  }
}
