package gov.ca.cwds.rest.api.persistence.cms;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import org.junit.Test;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

public class VarargPrimaryKeyTest {

  private String argument1 = "argument1";
  private String argument2 = "argument2";
  private String argument3 = "argument3";

  @Test
  public void constructorTest() throws Exception {
    VarargPrimaryKey vpk = new VarargPrimaryKey(argument1, argument2, argument3);
    assertThat(vpk, is(notNullValue()));
  }

  @Test
  public void equalsHashCodeWork() {
    EqualsVerifier.forClass(VarargPrimaryKey.class).suppress(Warning.NONFINAL_FIELDS).verify();
  }

  @Test
  public void toStringTest() throws Exception {
    VarargPrimaryKey vpk = new VarargPrimaryKey(argument1, argument2, argument3);
    assertThat(vpk.toString(), is(equalTo("concatKeyargument1argument2argument3")));
  }

  @Test
  public void type() throws Exception {
    assertThat(VarargPrimaryKey.class, notNullValue());
  }

  @Test
  public void testGetColumns() throws Exception {
    VarargPrimaryKey target = new VarargPrimaryKey("one", "two");
    final String[] actual = target.getColumns();
    final String[] expected = {"one", "two"};
    assertThat(actual, is(equalTo(expected)));
  }

}
