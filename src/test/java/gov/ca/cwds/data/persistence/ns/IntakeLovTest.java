package gov.ca.cwds.data.persistence.ns;

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
  public void getPrimaryKey_Args__() throws Exception {
    IntakeLov target = new IntakeLov();
    Long actual = target.getPrimaryKey();
    Long expected = null;
    assertThat(actual, is(equalTo(expected)));
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
  public void getLegacyShortDescription_Args__() throws Exception {
    IntakeLov target = new IntakeLov();
    String actual = target.getLegacyShortDescription();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setLegacyShortDescription_Args__String() throws Exception {
    IntakeLov target = new IntakeLov();
    String legacyShortDescription = null;
    target.setLegacyShortDescription(legacyShortDescription);
  }

  @Test
  public void getLegacyLogicalId_Args__() throws Exception {
    IntakeLov target = new IntakeLov();
    String actual = target.getLegacyLogicalCode();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setLegacyLogicalId_Args__String() throws Exception {
    IntakeLov target = new IntakeLov();
    String legacyLogicalId = null;
    target.setLegacyLogicalCode(legacyLogicalId);
  }

  @Test
  public void getLegacyInactive_Args__() throws Exception {
    IntakeLov target = new IntakeLov();
    boolean actual = target.isUseLogical();
    boolean expected = false;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setLegacyInactive_Args__String() throws Exception {
    IntakeLov target = new IntakeLov();
    target.setUseLogical(false);
  }

  @Test
  public void getLegacyCategoryId_Args__() throws Exception {
    IntakeLov target = new IntakeLov();
    String actual = target.getLegacyCategoryId();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setLegacyCategoryId_Args__String() throws Exception {
    IntakeLov target = new IntakeLov();
    String legacyCategoryId = null;
    target.setLegacyCategoryId(legacyCategoryId);
  }

  @Test
  public void getLegacyOtherCode_Args__() throws Exception {
    IntakeLov target = new IntakeLov();
    String actual = target.getLegacyOtherCode();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setLegacyOtherCode_Args__String() throws Exception {
    IntakeLov target = new IntakeLov();
    String legacyOtherCode = null;
    target.setLegacyOtherCode(legacyOtherCode);
  }

  @Test
  public void getLegacyLongDescription_Args__() throws Exception {
    IntakeLov target = new IntakeLov();
    String actual = target.getLegacyLongDescription();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setLegacyLongDescription_Args__String() throws Exception {
    IntakeLov target = new IntakeLov();
    String legacyLongDescription = null;
    target.setLegacyLongDescription(legacyLongDescription);
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
  public void getIntakeDisplay_Args__() throws Exception {
    IntakeLov target = new IntakeLov();
    String actual = target.getIntakeDisplay();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setIntakeDisplay_Args__String() throws Exception {
    IntakeLov target = new IntakeLov();
    String intakeDisplay = null;
    target.setIntakeDisplay(intakeDisplay);
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

}
