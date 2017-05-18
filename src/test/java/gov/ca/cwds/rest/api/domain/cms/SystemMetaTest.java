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
public class SystemMetaTest {

  private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

  private String logicalTableDsdName = "GVR_ENTC";
  private String shortDescriptionName = "Government Entity Type";
  private String userTableName = "Government Entity";

  @Test
  public void persistentObjectConstructorTest() throws Exception {
    gov.ca.cwds.data.persistence.cms.SystemMeta persistent =
        new gov.ca.cwds.data.persistence.cms.SystemMeta(logicalTableDsdName, userTableName,
            shortDescriptionName);


    SystemMeta totest = new SystemMeta(persistent);
    assertThat(totest.getLogicalTableDsdName(), is(equalTo(persistent.getLogicalTableDsdName())));
    assertThat(totest.getShortDescriptionName(), is(equalTo(persistent.getShortDescriptionName())));
    assertThat(totest.getUserTableName(), is(equalTo(persistent.getUserTableName())));
  }

  @Test
  public void jsonCreatorConstructorTest() throws Exception {
    SystemMeta domain = new SystemMeta(logicalTableDsdName, userTableName, shortDescriptionName);

    assertThat(domain.getLogicalTableDsdName(), is(equalTo(logicalTableDsdName)));
    assertThat(domain.getShortDescriptionName(), is(equalTo(shortDescriptionName)));
    assertThat(domain.getUserTableName(), is(equalTo(userTableName)));
  }

  @Test
  public void serializesToJSON() throws Exception {
    final String expected =
        MAPPER.writeValueAsString(MAPPER.readValue(
            fixture("fixtures/domain/legacy/SystemMeta/valid/valid.json"), SystemMeta.class));

    assertThat(MAPPER.writeValueAsString(validSystemMeta()), is(equalTo(expected)));
  }

  @Test
  public void deserializesFromJSON() throws Exception {
    assertThat(MAPPER.readValue(fixture("fixtures/domain/legacy/SystemMeta/valid/valid.json"),
        SystemMeta.class), is(equalTo(validSystemMeta())));
  }

  private SystemMeta validSystemMeta() {
    return new SystemMeta(logicalTableDsdName, shortDescriptionName, userTableName);
  }
}
