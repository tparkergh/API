package gov.ca.cwds.rest.api.domain.cms;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import io.dropwizard.jackson.Jackson;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * @author CWDS API Team
 */
@SuppressWarnings("javadoc")
public class SystemCodeTest {

  private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

  private Short categoryId = 0;
  private String foreignKeyMetaTable = "CNTRY_C ";
  private String inactiveIndicator = "N";
  private String longDescription = "     ";
  private String otherCd = "  ";
  private String shortDescription = "Albania";
  private Short systemId = 471;
  private String thirdId = "0000000000";
  private String logicalId = "AB  ";

  @Test
  public void persistentObjectConstructorTest() throws Exception {
    gov.ca.cwds.data.persistence.cms.SystemCode persistent =
        new gov.ca.cwds.data.persistence.cms.SystemCode(systemId, categoryId, inactiveIndicator,
            otherCd, shortDescription, logicalId, thirdId, foreignKeyMetaTable, longDescription);


    SystemCode totest = new SystemCode(persistent);
    assertThat(totest.getCategoryId(), is(equalTo(persistent.getCategoryId())));
    assertThat(totest.getForeignKeyMetaTable(), is(equalTo(persistent.getForeignKeyMetaTable())));
    assertThat(totest.getInactiveIndicator(), is(equalTo(persistent.getInactiveIndicator())));
    assertThat(totest.getLongDescription(), is(equalTo(persistent.getLongDescription())));
    assertThat(totest.getOtherCd(), is(equalTo(persistent.getOtherCd())));
    assertThat(totest.getShortDescription(), is(equalTo(persistent.getShortDescription())));
    assertThat(totest.getSystemId(), is(equalTo(persistent.getSystemId())));
    assertThat(totest.getThirdId(), is(equalTo(persistent.getThirdId())));
    assertThat(totest.getLogicalId(), is(equalTo(persistent.getLogicalId())));
  }

  @Test
  public void jsonCreatorConstructorTest() throws Exception {
    SystemCode domain =
        new SystemCode(systemId, categoryId, inactiveIndicator, otherCd, shortDescription,
            logicalId, thirdId, foreignKeyMetaTable, longDescription);


    assertThat(domain.getCategoryId(), is(equalTo(categoryId)));
    assertThat(domain.getForeignKeyMetaTable(), is(equalTo(foreignKeyMetaTable)));
    assertThat(domain.getInactiveIndicator(), is(equalTo(inactiveIndicator)));
    assertThat(domain.getLongDescription(), is(equalTo(longDescription)));
    assertThat(domain.getOtherCd(), is(equalTo(otherCd)));
    assertThat(domain.getShortDescription(), is(equalTo(shortDescription)));
    assertThat(domain.getSystemId(), is(equalTo(systemId)));
    assertThat(domain.getThirdId(), is(equalTo(thirdId)));
    assertThat(domain.getLogicalId(), is(equalTo(logicalId)));
  }

  @Test
  public void serializesToJSON() throws Exception {
    final String expected =
        MAPPER.writeValueAsString(MAPPER.readValue(
            fixture("fixtures/domain/legacy/SystemCode/valid/valid.json"), SystemCode.class));

    assertThat(MAPPER.writeValueAsString(validSystemCode()), is(equalTo(expected)));
  }

  @Test
  public void deserializesFromJSON() throws Exception {
    assertThat(MAPPER.readValue(fixture("fixtures/domain/legacy/SystemCode/valid/valid.json"),
        SystemCode.class), is(equalTo(validSystemCode())));
  }

  private SystemCode validSystemCode() {
    return new SystemCode(systemId, categoryId, inactiveIndicator, otherCd, shortDescription,
        logicalId, thirdId, foreignKeyMetaTable, longDescription);

  }
}
