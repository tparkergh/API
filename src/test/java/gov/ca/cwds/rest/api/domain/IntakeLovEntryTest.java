package gov.ca.cwds.rest.api.domain;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class IntakeLovEntryTest {

  @Test
  public void type() throws Exception {
    assertThat(IntakeLovEntry.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    IntakeLovEntry target = new IntakeLovEntry();
    assertThat(target, notNullValue());
  }

  @Test
  public void getLegacySystemCodeId_Args__() throws Exception {
    IntakeLovEntry target = new IntakeLovEntry();
    Long actual = target.getLegacySystemCodeId();
    Long expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setLegacySystemCodeId_Args__Long() throws Exception {
    IntakeLovEntry target = new IntakeLovEntry();
    Long legacySystemCodeId = null;
    target.setLegacySystemCodeId(legacySystemCodeId);
  }

  @Test
  public void getLegacyMeta_Args__() throws Exception {
    IntakeLovEntry target = new IntakeLovEntry();
    String actual = target.getLegacyMeta();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setLegacyMeta_Args__String() throws Exception {
    IntakeLovEntry target = new IntakeLovEntry();
    String legacyMeta = null;
    target.setLegacyMeta(legacyMeta);
  }

  @Test
  public void getIntakeCode_Args__() throws Exception {
    IntakeLovEntry target = new IntakeLovEntry();
    String actual = target.getIntakeCode();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setIntakeCode_Args__String() throws Exception {
    IntakeLovEntry target = new IntakeLovEntry();
    String intakeCode = null;
    target.setIntakeCode(intakeCode);
  }

  @Test
  public void getIntakeValue_Args__() throws Exception {
    IntakeLovEntry target = new IntakeLovEntry();
    String actual = target.getIntakeValue();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setIntakeValue_Args__String() throws Exception {
    IntakeLovEntry target = new IntakeLovEntry();
    String intakeValue = null;
    target.setIntakeValue(intakeValue);
  }

  @Test
  public void getIntakeType_Args__() throws Exception {
    IntakeLovEntry target = new IntakeLovEntry();
    String actual = target.getSubCategory();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setIntakeType_Args__String() throws Exception {
    IntakeLovEntry target = new IntakeLovEntry();
    String intakeType = null;
    target.setSubCategory(intakeType);
  }

  @Test
  public void getSortOrder_Args__() throws Exception {
    IntakeLovEntry target = new IntakeLovEntry();
    int actual = target.getSortOrder();
    int expected = 0;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setSortOrder_Args__int() throws Exception {
    IntakeLovEntry target = new IntakeLovEntry();
    int sortOrder = 0;
    target.setSortOrder(sortOrder);
  }

  @Test
  public void getSerialversionuid_Args__() throws Exception {
    long actual = IntakeLovEntry.getSerialversionuid();
    long expected = 1L;
    assertThat(actual, is(equalTo(expected)));
  }

}
