package gov.ca.cwds.rest.api.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

@SuppressWarnings("javadoc")
/**
 * @author CWDS API Team
 *
 */
public class LegacyDescriptorTest {
  private static final String ID = "12345";
  private static final String UI_ID = "98765";
  private static final DateTime LAST_UPDATED = new DateTime();
  private static final String TABLE_NAME = "table_name";
  private static final String DESCRIPTION = "description";

  private Validator validator;

  @Before
  public void setup() {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    validator = factory.getValidator();
  }

  /**
   * 
   */
  @Test
  public void shouldCreateObjectWithDefaultConstructor() {
    LegacyDescriptor legacyDescriptor = new LegacyDescriptor();
    assertNotNull(legacyDescriptor);
  }

  /**
   * 
   */
  @Test
  public void shouldCreateObjectWithSpecifiedValues() {
    LegacyDescriptor legacyDescriptor =
        new LegacyDescriptor(ID, UI_ID, LAST_UPDATED, TABLE_NAME, DESCRIPTION);
    assertEquals(legacyDescriptor.getId(), ID);
    assertEquals(legacyDescriptor.getUiId(), UI_ID);
    assertEquals(legacyDescriptor.getLastUpdated(), LAST_UPDATED);
    assertEquals(legacyDescriptor.getTableName(), TABLE_NAME);
    assertEquals(legacyDescriptor.getTableDescription(), DESCRIPTION);
  }

  /**
   * 
   */
  @Test
  public void shouldCompareEqualsToObjectWithSameValues() {
    LegacyDescriptor legacyDescriptor =
        new LegacyDescriptor(ID, UI_ID, LAST_UPDATED, TABLE_NAME, DESCRIPTION);
    LegacyDescriptor otherLegacyDescriptor =
        new LegacyDescriptor(ID, UI_ID, LAST_UPDATED, TABLE_NAME, DESCRIPTION);

    assertEquals(legacyDescriptor, otherLegacyDescriptor);
  }

  /**
   * 
   */
  @Test
  public void shouldFindSingleItemInHashSetWhenMultipleItemsAddedWithSameValue() {
    LegacyDescriptor legacyDescriptor =
        new LegacyDescriptor(ID, UI_ID, LAST_UPDATED, TABLE_NAME, DESCRIPTION);
    LegacyDescriptor otherLegacyDescriptor =
        new LegacyDescriptor(ID, UI_ID, LAST_UPDATED, TABLE_NAME, DESCRIPTION);

    Set<Object> items = new HashSet<>();
    items.add(legacyDescriptor);
    items.add(otherLegacyDescriptor);

    assertTrue(items.contains(legacyDescriptor));
    assertTrue(items.contains(otherLegacyDescriptor));
    assertEquals(1, items.size());
  }

  /**
   * 
   */
  @Test
  public void equalsHashCodeWork() {
    EqualsVerifier.forClass(LegacyDescriptor.class).suppress(Warning.NONFINAL_FIELDS).verify();
  }

}
