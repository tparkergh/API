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
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

/**
 * @author CWDS API Team
 *
 */
public class LongTextTest implements PersistentTestTemplate {

  private String id = "ABC1234567";
  private String lastUpdatedId = "0X5";

  private static final ObjectMapper MAPPER = SystemCodeTestHarness.MAPPER;

  /*
   * Constructor test
   */
  @Override
  @Test
  public void testEmptyConstructor() throws Exception {
    assertThat(LongText.class.newInstance(), is(notNullValue()));
  }

  @Override
  @Test
  public void testPersistentConstructor() throws Exception {

    LongText vl = validLongTest();

    gov.ca.cwds.data.persistence.cms.LongText persistent =
        new gov.ca.cwds.data.persistence.cms.LongText(id, vl.getCountySpecificCode(),
            vl.getTextDescrption());

    assertThat(persistent.getId(), is(equalTo(id)));
    assertThat(persistent.getCountySpecificCode(), is(equalTo(vl.getCountySpecificCode())));
    assertThat(persistent.getTextDescrption(), is(equalTo(vl.getTextDescrption())));

  }

  @Override
  @Test
  public void testConstructorUsingDomain() throws Exception {

    gov.ca.cwds.rest.api.domain.cms.LongText domain = validDomainLongText();

    LongText persistent = new LongText(id, domain, lastUpdatedId);

    assertThat(persistent.getId(), is(equalTo(id)));
    assertThat(persistent.getCountySpecificCode(), is(equalTo(domain.getCountySpecificCode())));
    assertThat(persistent.getTextDescrption(), is(equalTo(domain.getTextDescription())));

  }

  @Override
  @Test
  public void testEqualsHashCodeWorks() {
    EqualsVerifier.forClass(LongText.class).suppress(Warning.NONFINAL_FIELDS).verify();

  }

  private LongText validLongTest() throws JsonParseException, JsonMappingException, IOException {

    LongText validLongText =
        MAPPER.readValue(fixture("fixtures/persistent/LongText/valid/valid.json"), LongText.class);
    return validLongText;
  }

  private gov.ca.cwds.rest.api.domain.cms.LongText validDomainLongText()
      throws JsonParseException, JsonMappingException, IOException {

    gov.ca.cwds.rest.api.domain.cms.LongText validDomainLongText =
        MAPPER.readValue(fixture("fixtures/domain/legacy/LongText/valid/valid.json"),
            gov.ca.cwds.rest.api.domain.cms.LongText.class);
    return validDomainLongText;
  }

}
