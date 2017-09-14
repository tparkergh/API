package gov.ca.cwds.rest.services.investigation;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import gov.ca.cwds.data.dao.contact.DeliveredServiceDao;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.investigation.ScreeningSummary;
import gov.ca.cwds.rest.filters.TestingRequestExecutionContext;
import io.dropwizard.jackson.Jackson;

import org.apache.commons.lang3.NotImplementedException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.fasterxml.jackson.databind.ObjectMapper;

/***
 * 
 * @author CWDS API Team
 *
 */
@SuppressWarnings("javadoc")
public class ScreeningSummaryServiceTest {
  private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

  private ScreeningSummaryService screeningSummaryService;
  private DeliveredServiceDao deliveredServiceDao;

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Before
  public void setup() throws Exception {
    new TestingRequestExecutionContext("0X5");
    screeningSummaryService = new ScreeningSummaryService(deliveredServiceDao);
  }

  // find test
  @Test
  public void findReturnsExpectedContact() throws Exception {
    ScreeningSummary serialized = new ScreeningSummary();
    serialized =
        MAPPER.readValue(fixture("fixtures/domain/investigation/screening/valid/valid.json"),
            ScreeningSummary.class);
    Response returned = screeningSummaryService.find("string");
    assertThat(returned, is(serialized));
  }

  // delete test
  @Test
  public void deleteThrowsNotImplementedException() throws Exception {
    thrown.expect(NotImplementedException.class);
    screeningSummaryService.delete("string");
  }

  // update test
  @Test
  public void updateThrowsNotImplementedException() throws Exception {
    thrown.expect(NotImplementedException.class);
    screeningSummaryService.delete("string");
  }


  // create test
  @Test
  public void createThrowsNotImplementedException() throws Exception {
    thrown.expect(NotImplementedException.class);
    screeningSummaryService.delete("string");
  }

}
