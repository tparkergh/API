package gov.ca.cwds.rest.api.domain;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class IntakeLovTest {

  @Test
  public void type() throws Exception {
    assertThat(IntakeLov.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    IntakeLov target = new IntakeLov();
    assertThat(target, notNullValue());
  }

  @Test
  public void getLegacySystemCodeId_Args__() throws Exception {
    IntakeLov target = new IntakeLov();
    Long actual = target.getLegacySystemCodeId();
    Long expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setLegacySystemCodeId_Args__Long() throws Exception {
    IntakeLov target = new IntakeLov();
    Long legacySystemCodeId = null;
    target.setLegacySystemCodeId(legacySystemCodeId);
  }

  @Test
  public void getLegacyMeta_Args__() throws Exception {
    IntakeLov target = new IntakeLov();
    String actual = target.getLegacyMeta();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setLegacyMeta_Args__String() throws Exception {
    IntakeLov target = new IntakeLov();
    String legacyMeta = null;
    target.setLegacyMeta(legacyMeta);
  }

  @Test
  public void getIntakeCode_Args__() throws Exception {
    IntakeLov target = new IntakeLov();
    String actual = target.getIntakeCode();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setIntakeCode_Args__String() throws Exception {
    IntakeLov target = new IntakeLov();
    String intakeCode = null;
    target.setIntakeCode(intakeCode);
  }

  @Test
  public void getIntakeValue_Args__() throws Exception {
    IntakeLov target = new IntakeLov();
    String actual = target.getIntakeValue();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setIntakeValue_Args__String() throws Exception {
    IntakeLov target = new IntakeLov();
    String intakeValue = null;
    target.setIntakeValue(intakeValue);
  }

  @Test
  public void getIntakeType_Args__() throws Exception {
    IntakeLov target = new IntakeLov();
    String actual = target.getIntakeType();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setIntakeType_Args__String() throws Exception {
    IntakeLov target = new IntakeLov();
    String intakeType = null;
    target.setIntakeType(intakeType);
  }

  @Test
  public void getSortOrder_Args__() throws Exception {
    IntakeLov target = new IntakeLov();
    int actual = target.getSortOrder();
    int expected = 0;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setSortOrder_Args__int() throws Exception {
    IntakeLov target = new IntakeLov();
    int sortOrder = 0;
    target.setSortOrder(sortOrder);
  }

  @Test
  public void getSerialversionuid_Args__() throws Exception {
    long actual = IntakeLov.getSerialversionuid();
    long expected = 1L;
    assertThat(actual, is(equalTo(expected)));
  }

}
