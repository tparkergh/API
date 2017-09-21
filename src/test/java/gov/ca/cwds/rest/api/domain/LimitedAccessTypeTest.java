package gov.ca.cwds.rest.api.domain;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

@SuppressWarnings("javadoc")
public class LimitedAccessTypeTest {
  @Test
  public void shouldHaveNoneValueEnum() {
    assertEquals("Expected LimitedAccessType of none to have a correct Value", "N",
        LimitedAccessType.NONE.getValue());
  }

  @Test
  public void shouldHaveNoneDescriptionEnum() {
    assertEquals("Expected LimitedAccessType of none to have a correct Value", "None",
        LimitedAccessType.NONE.getDescription());
  }

  @Test
  public void shouldHaveSealedValueEnum() {
    assertEquals("Expected LimitedAccessType of none to have a correct Value", "R",
        LimitedAccessType.SEALED.getValue());
  }

  @Test
  public void shouldHaveSealedNoneDescriptionEnum() {
    assertEquals("Expected LimitedAccessType of none to have a correct Value", "Sealed",
        LimitedAccessType.SEALED.getDescription());
  }

  @Test
  public void shouldHaveSensitiveValueEnum() {
    assertEquals("Expected LimitedAccessType of none to have a correct Value", "S",
        LimitedAccessType.SENSITIVE.getValue());
  }

  @Test
  public void shouldHaveSensitivedNoneDescriptionEnum() {
    assertEquals("Expected LimitedAccessType of none to have a correct Value", "Sensitive",
        LimitedAccessType.SENSITIVE.getDescription());
  }
}
