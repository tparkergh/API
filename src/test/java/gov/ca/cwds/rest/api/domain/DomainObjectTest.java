package gov.ca.cwds.rest.api.domain;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.Matchers.startsWith;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.hamcrest.core.Is;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import gov.ca.cwds.rest.api.ApiException;

// @RunWith(PowerMockRunner.class)
public class DomainObjectTest {
  protected static final String DATE_FORMAT = "yyyy-MM-dd";
  protected static final String TIMESTAMP_FORMAT = "yyyy-MM-dd-HH.mm.ss.SSS";
  protected static final String TIME_FORMAT = "HH:mm:ss";

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  // cookBoolean tests
  @Test
  public void cookBooleanReturnsNullOnNullBoolean() throws Exception {
    assertThat(DomainChef.cookBoolean(null), is(nullValue()));
  }

  @Test
  public void cookBooleanReturnsYOnTrue() throws Exception {
    assertThat(DomainChef.cookBoolean(Boolean.TRUE), is(equalTo("Y")));
  }

  @Test
  public void cookBooleanReturnsNOnFalse() throws Exception {
    assertThat(DomainChef.cookBoolean(Boolean.FALSE), is(equalTo("N")));
  }

  // uncookBoolean tests
  @Test
  public void uncookBooleanStringReturnsFalseOnN() throws Exception {
    assertThat(DomainChef.uncookBooleanString("N"), is(equalTo(Boolean.FALSE)));
  }

  @Test
  public void uncookBooleanStringReturnsFalseOnSmallN() throws Exception {
    assertThat(DomainChef.uncookBooleanString("n"), is(equalTo(Boolean.FALSE)));
  }

  @Test
  public void uncookBooleanStringReturnsFalseOnY() throws Exception {
    assertThat(DomainChef.uncookBooleanString("Y"), is(equalTo(Boolean.TRUE)));
  }

  @Test
  public void uncookBooleanStringReturnsFalseOnSmallY() throws Exception {
    assertThat(DomainChef.uncookBooleanString("y"), is(equalTo(Boolean.TRUE)));
  }

  @Test
  public void uncookBooleanstringReturnsNullOnNull() throws Exception {
    assertThat(DomainChef.uncookBooleanString(null), is(nullValue()));
  }

  @Test
  public void uncookBooleanstringReturnsNullOnEmpty() throws Exception {
    assertThat(DomainChef.uncookBooleanString("  "), is(nullValue()));
  }

  @Test
  public void uncookBooleanStringThrowsDomainExceptionOnNonYOrN() throws Exception {
    thrown.expect(ApiException.class);
    thrown.expectCause(Is.isA(ParseException.class));
    DomainChef.uncookBooleanString("T");
  }

  // cookDate tests
  @Test
  public void cookDateReturnsNullOnNullDate() throws Exception {
    assertThat(DomainChef.cookDate(null), is(nullValue()));
  }

  @Test
  public void cookDateReturnsCorrectString() throws Exception {
    DateFormat df = new SimpleDateFormat(DATE_FORMAT);
    Date date = new Date();

    assertThat(DomainChef.cookDate(date), is(equalTo(df.format(date))));
  }

  // cookTimestamp tests
  @Test
  public void cookTimestampReturnsNullOnNullDate() throws Exception {
    assertThat(DomainChef.cookTimestamp(null), is(nullValue()));
  }

  @Test
  public void cookTimestampReturnsCorrectString() throws Exception {
    DateFormat df = new SimpleDateFormat(TIMESTAMP_FORMAT);
    Date date = new Date();

    assertThat(DomainChef.cookTimestamp(date), is(equalTo(df.format(date))));
  }

  // cookTime tests
  @Test
  public void cookTimeReturnsNullOnNullDate() throws Exception {
    assertThat(DomainChef.cookTime(null), is(nullValue()));
  }

  @Test
  public void cookTimeReturnsCorrectString() throws Exception {
    DateFormat df = new SimpleDateFormat(TIME_FORMAT);
    Date date = new Date();

    assertThat(DomainChef.cookTime(date), is(equalTo(df.format(date))));
  }

  // uncookDateString tests
  @Test
  public void uncookDateStringReturnsCorrectDate() throws Exception {
    DateFormat df = new SimpleDateFormat(DATE_FORMAT);
    Date dateWithTime = new Date();
    String dateString = df.format(dateWithTime);

    Date dateBasedOnFormat = df.parse(df.format(dateWithTime));

    assertThat(DomainChef.uncookDateString(dateString), is(equalTo(dateBasedOnFormat)));
  }

  @Test
  public void uncookDateStringReturnsNullOnNullString() throws Exception {
    assertThat(DomainChef.uncookDateString(null), is(nullValue()));
  }

  @Test
  public void uncookDateStringThrowsExceptionOnBadInput() throws Exception {
    thrown.expect(ApiException.class);
    thrown.expectCause(Is.isA(ParseException.class));
    DomainChef.uncookDateString("dlfjkdfjdkfjkd");
  }

  // uncookTimestampString tests
  @Test
  public void uncookTimestampStringReturnsCorrectDate() throws Exception {
    DateFormat df = new SimpleDateFormat(TIMESTAMP_FORMAT);
    Date date = new Date();
    String dateString = df.format(date);

    assertThat(DomainChef.uncookTimestampString(dateString), is(equalTo(date)));
  }

  @Test
  public void uncookTimestampStringReturnsNullOnNullString() throws Exception {
    assertThat(DomainChef.uncookTimestampString(null), is(nullValue()));
  }

  @Test
  public void uncookTimestampStringThrowsExceptionOnBadInput() throws Exception {
    thrown.expect(ApiException.class);
    thrown.expectCause(Is.isA(ParseException.class));
    DomainChef.uncookTimestampString("dlfjkdfjdkfjkd");
  }

  // uncookTimeString tests
  @Test
  public void uncookTimeStringReturnsCorrectDate() throws Exception {
    Date dt = new SimpleDateFormat("HH:mm:ss").parse("14:20:20");
    assertThat(DomainChef.uncookTimeString("14:20:20"), is(equalTo(dt)));
  }

  @Test
  public void uncookTimeStringReturnsNullOnNullString() throws Exception {
    assertThat(DomainChef.uncookTimeString(null), is(nullValue()));
  }

  @Test
  public void uncookTimeStringThrowsExceptionOnBadInput() throws Exception {
    thrown.expect(ApiException.class);
    thrown.expectCause(Is.isA(ParseException.class));
    DomainChef.uncookTimeString("dlfjkdfjdkfjkd");
  }

  // cookZipcodeNumber tests
  @Test
  public void cookZipcodeNumberReturnsEmptyStringWhenZipcodeNumberIsNull() throws Exception {
    assertThat(DomainChef.cookZipcodeNumber(null), is(equalTo("")));
  }

  @Test
  public void cookZipcodeNumberReturnsEmptyStringWhenZipcodeNumberEquals0() throws Exception {
    assertThat(DomainChef.cookZipcodeNumber(new Integer(0)), is(equalTo("")));
  }

  @Test
  public void cookZipcodeNumberReturnsCorrectValueWhenLeading0Needed() throws Exception {
    assertThat(DomainChef.cookZipcodeNumber(new Integer(5842)), is(equalTo("05842")));
  }

  @Test
  public void cookZipcodeNumberReturnsCorrectValueWhenNoLeading0Needed() throws Exception {
    assertThat(DomainChef.cookZipcodeNumber(new Integer(95842)), is(equalTo("95842")));
  }

  // uncookZipcodeString tests
  @Test
  public void uncookZipcodeStringReturnsCorrectIntegerWhenLeading0s() throws Exception {
    assertThat(DomainChef.uncookZipcodeString("05842"), is(equalTo(new Integer(5842))));
  }

  @Test
  public void uncookZipcodeStringReturnsCorrectIntegerWhenLeadingNo0s() throws Exception {
    assertThat(DomainChef.uncookZipcodeString("95842"), is(equalTo(new Integer(95842))));
  }

  @Test
  public void uncookZipcodeStringReturnsCorrect0WhenEmpty() throws Exception {
    assertThat(DomainChef.uncookZipcodeString(" "), is(equalTo(new Integer(0))));
  }

  @Test
  public void uncookZipcodeStringThrowsExceptionOnBadGroupMatching() throws Exception {
    thrown.expect(ApiException.class);
    thrown.expectCause(Is.isA(NumberFormatException.class));
    DomainChef.uncookZipcodeString("000000");
  }

  @Test
  public void uncookZipcodeStringThrowsExceptionOnNoMatch() throws Exception {
    thrown.expect(ApiException.class);
    thrown.expectMessage(startsWith("Unable to uncook zipcode string"));
    DomainChef.uncookZipcodeString("dlfjkdfjdkfjkd");
  }

}
