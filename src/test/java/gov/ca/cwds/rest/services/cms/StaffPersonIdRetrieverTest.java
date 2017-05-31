package gov.ca.cwds.rest.services.cms;

import static org.junit.Assert.assertEquals;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * @author CWDS API Team
 *
 */
public class StaffPersonIdRetrieverTest {

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @SuppressWarnings("javadoc")
  @Test
  public void getStaffPersonIdReturnsHardCodedValue() {
    String actual = new StaffPersonIdRetriever().getStaffPersonId();
    String expected = "0X5";
    assertEquals(actual, expected);
  }
}
