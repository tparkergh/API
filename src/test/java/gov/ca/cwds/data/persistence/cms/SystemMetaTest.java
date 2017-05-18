package gov.ca.cwds.data.persistence.cms;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import gov.ca.cwds.data.persistence.junit.template.PersistentTestTemplate;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

import org.junit.Test;

/**
 * @author CWDS API Team
 *
 */
public class SystemMetaTest implements PersistentTestTemplate {

  private String logicalTableDsdName = "a";
  private String shortDescriptionName = "a";
  private String userTableName = "a";


  @Override
  @Test
  public void testEmptyConstructor() throws Exception {
    assertThat(SystemMeta.class.newInstance(), is(notNullValue()));
  }

  @Override
  @Test
  public void testPersistentConstructor() throws Exception {
    gov.ca.cwds.data.persistence.cms.SystemMeta persistent =
        new gov.ca.cwds.data.persistence.cms.SystemMeta(logicalTableDsdName, shortDescriptionName,
            userTableName);
    assertThat(persistent.getLogicalTableDsdName(), is(equalTo(logicalTableDsdName)));
    assertThat(persistent.getShortDescriptionName(), is(equalTo(shortDescriptionName)));
    assertThat(persistent.getUserTableName(), is(equalTo(userTableName)));
  }

  @Override
  public void testConstructorUsingDomain() throws Exception {
    // Not Implemented

  }

  @Override
  @Test
  public void testEqualsHashCodeWorks() {
    EqualsVerifier.forClass(LongText.class).suppress(Warning.NONFINAL_FIELDS).verify();

  }



}
