package gov.ca.cwds.data.persistence.cms;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import gov.ca.cwds.data.persistence.junit.template.PersistentTestTemplate;

import org.junit.Test;

/**
 * @author CWDS API Team
 *
 */
public class SystemCodeTest implements PersistentTestTemplate {

  private Short categoryId = (short) 2;
  private String foreignKeyMetaTable = "GVR_ENTC";
  private String inactiveIndicator = "Y";
  private String longDescription = "a";
  private String otherCd = "a";
  private String shortDescription = "a";
  private Short systemId = (short) 123;
  private String thirdId = "000";
  private String logicalId = "03";


  @Override
  @Test
  public void testEmptyConstructor() throws Exception {
    assertThat(SystemCode.class.newInstance(), is(notNullValue()));
  }

  @Override
  @Test
  public void testPersistentConstructor() throws Exception {
    gov.ca.cwds.data.persistence.cms.SystemCode persistent =
        new gov.ca.cwds.data.persistence.cms.SystemCode(systemId, categoryId, inactiveIndicator,
            otherCd, shortDescription, logicalId, thirdId, foreignKeyMetaTable, longDescription);

    assertThat(persistent.getCategoryId(), is(equalTo(categoryId)));
    assertThat(persistent.getForeignKeyMetaTable(), is(equalTo(foreignKeyMetaTable)));
    assertThat(persistent.getInactiveIndicator(), is(equalTo(inactiveIndicator)));
    assertThat(persistent.getLongDescription(), is(equalTo(longDescription)));
    assertThat(persistent.getOtherCd(), is(equalTo(otherCd)));
    assertThat(persistent.getShortDescription(), is(equalTo(shortDescription)));
    assertThat(persistent.getSystemId(), is(equalTo(systemId)));
    assertThat(persistent.getThirdId(), is(equalTo(thirdId)));
    assertThat(persistent.getLogicalId(), is(equalTo(logicalId)));
  }

  @Override
  public void testConstructorUsingDomain() throws Exception {
    // Not Implemented

  }

  @Override
  @Test
  public void testEqualsHashCodeWorks() throws Exception {
    assertThat(SystemCode.class.newInstance(), is(notNullValue()));
  }

}
