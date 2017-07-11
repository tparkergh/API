package gov.ca.cwds.rest.api.domain;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;
import org.junit.Test;

public class LegacyDescriptorTest {
  private static final String ID = "12345";
  private static final String UI_ID = "98765";
  private static final String LAST_UPDATED = "98765";
  private static final String TABLE_NAME = "table_name";
  private static final String DESCRIPTION = "description";
  @Test
  public void shouldCreateObjectWithDefaultConstructor(){
    LegacyDescriptor legacyDescriptor = new LegacyDescriptor();
    assertNotNull(legacyDescriptor);
  }

  @Test
  public void shouldCreateObjectWithSpecifiedValues(){
    LegacyDescriptor legacyDescriptor = new LegacyDescriptor(ID, UI_ID, LAST_UPDATED, TABLE_NAME, DESCRIPTION);
    assertEquals(legacyDescriptor.getId(), ID);
    assertEquals(legacyDescriptor.getUiId(), UI_ID);
    assertEquals(legacyDescriptor.getLastUpdated(), LAST_UPDATED);
    assertEquals(legacyDescriptor.getTableName(), TABLE_NAME);
    assertEquals(legacyDescriptor.getTableDescription(), DESCRIPTION);
  }

  @Test
  public void shouldCompareEqualsToObjectWithSameValues(){
    LegacyDescriptor legacyDescriptor = new LegacyDescriptor(ID, UI_ID, LAST_UPDATED, TABLE_NAME, DESCRIPTION);
    LegacyDescriptor otherLegacyDescriptor = new LegacyDescriptor(ID, UI_ID, LAST_UPDATED,
        TABLE_NAME, DESCRIPTION);

    assertEquals(legacyDescriptor, otherLegacyDescriptor);
  }

  @Test
  public void shouldFindSingleItemInHashSetWhenMultipleItemsAddedWithSameValue(){
    LegacyDescriptor legacyDescriptor = new LegacyDescriptor(ID, UI_ID, LAST_UPDATED, TABLE_NAME, DESCRIPTION);
    LegacyDescriptor otherLegacyDescriptor = new LegacyDescriptor(ID, UI_ID, LAST_UPDATED,
        TABLE_NAME, DESCRIPTION);

    Set items = new HashSet<>();
    items.add(legacyDescriptor);
    items.add(otherLegacyDescriptor);

    assertTrue(items.contains(legacyDescriptor));
    assertTrue(items.contains(otherLegacyDescriptor));
    assertEquals(1, items.size());
  }
}