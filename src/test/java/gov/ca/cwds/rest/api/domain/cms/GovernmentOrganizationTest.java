package gov.ca.cwds.rest.api.domain.cms;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;

/**
 * @author CWDS API Team
 *
 */
public class GovernmentOrganizationTest {

  /**
   * @throws Exception - Exception
   */
  @Test
  public void type() throws Exception {
    assertThat(GovernmentOrganization.class, notNullValue());
  }

  /**
   * @throws Exception - Exception
   */
  @Test
  public void instantiation() throws Exception {
    GovernmentOrganization target = new GovernmentOrganization();
    assertThat(target, notNullValue());
  }

  /**
   * @throws Exception - Exception
   */
  @Test
  public void getId_Args__() throws Exception {
    GovernmentOrganization target = new GovernmentOrganization();
    String actual = target.getId();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  /**
   * @throws Exception - Exception
   */
  @Test
  public void getAgencyName_Args__() throws Exception {
    GovernmentOrganization target = new GovernmentOrganization();
    String actual = target.getAgencyName();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  /**
   * @throws Exception - Exception
   */
  @Test
  public void getAgencyType_Args__() throws Exception {
    GovernmentOrganization target = new GovernmentOrganization();
    String actual = target.getAgencyType();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  /**
   * @throws Exception - Exception
   */
  @Test
  public void getGovernmentEntityType_Args__() throws Exception {
    GovernmentOrganization target = new GovernmentOrganization();
    Short actual = target.getCountyId();
    Short expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

}
