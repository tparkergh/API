package gov.ca.cwds.rest.validation;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.spy;

import org.junit.Test;

/**
 * 
 * @author CWDS API Team
 */
public class MaskStringTest {
  String actualSsn;
  String expectedSsn = "";

  private MaskString spyMaskString = spy(new MaskString());

  @Test
  public void returnEmptySsnWhenNull() throws Exception {
    String ssn = null;
    actualSsn = spyMaskString.maskSsn(ssn);
    assertThat(actualSsn, is(equalTo(expectedSsn)));
  }

  @Test
  public void returnEmptySsnWhenZero() throws Exception {
    String ssn = "000000000";
    actualSsn = spyMaskString.maskSsn(ssn);
    assertThat(actualSsn, is(equalTo(expectedSsn)));
  }

  @Test
  public void returnEmptySsnWhenPartlyZero() throws Exception {
    String ssn = "000006789";
    actualSsn = spyMaskString.maskSsn(ssn);
    assertThat(actualSsn, is(equalTo(expectedSsn)));
  }

  @Test
  public void returnEmptySsnWhenHyphenZero() throws Exception {
    String ssn = "000-00-0000";
    actualSsn = spyMaskString.maskSsn(ssn);
    assertThat(actualSsn, is(equalTo(expectedSsn)));
  }

  @Test
  public void returnEmptySsnWhenHyphenPartlyZero() throws Exception {
    String ssn = "123-00-6789";
    actualSsn = spyMaskString.maskSsn(ssn);
    assertThat(actualSsn, is(equalTo(expectedSsn)));
  }

  @Test
  public void returnEmptySsnWhenNotNineChar() throws Exception {
    String ssn = "1234567";
    actualSsn = spyMaskString.maskSsn(ssn);
    assertThat(actualSsn, is(equalTo(expectedSsn)));
  }

  @Test
  public void returnEmptySsnWhenNotZeroToNine() throws Exception {
    String ssn = "12345678A";
    actualSsn = spyMaskString.maskSsn(ssn);
    assertThat(actualSsn, is(equalTo(expectedSsn)));
  }

  /*
   * Successful Tests
   */
  @Test
  public void maskWithNineCharSsn() throws Exception {
    String ssn = "123456789";
    expectedSsn = "6789";
    actualSsn = spyMaskString.maskSsn(ssn);
    assertThat(actualSsn, is(equalTo(expectedSsn)));
  }

  @Test
  public void maskWithHyphenInSsn() throws Exception {
    String ssn = "012-03-0456";
    expectedSsn = "0456";
    actualSsn = spyMaskString.maskSsn(ssn);
    assertThat(actualSsn, is(equalTo(expectedSsn)));
  }

  @Test
  public void maskWithWhackSsn() throws Exception {
    String ssn = "561002314";
    expectedSsn = "";
    actualSsn = spyMaskString.maskSsn(ssn);
    assertThat(actualSsn, is(equalTo(expectedSsn)));
  }

}
