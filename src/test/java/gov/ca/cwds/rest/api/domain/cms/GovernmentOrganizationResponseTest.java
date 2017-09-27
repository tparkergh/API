package gov.ca.cwds.rest.api.domain.cms;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import gov.ca.cwds.ObjectMapperUtils;

/**
 * @author CWDS API Team
 *
 */
public class GovernmentOrganizationResponseTest {

  /**
   * @throws Exception - Exception
   */
  @Test
  public void type() throws Exception {
    assertThat(GovernmentOrganizationResponse.class, notNullValue());
  }

  /**
   * @throws Exception - Exception
   */
  @Test
  public void instantiation() throws Exception {
    List<GovernmentOrganization> governmentOrganizations = null;
    GovernmentOrganizationResponse target =
        new GovernmentOrganizationResponse(governmentOrganizations);
    assertThat(target, notNullValue());
  }

  /**
   * @throws Exception - Exception
   */
  @Test
  public void getGovernmentOrganizations_Args__() throws Exception {
    List<GovernmentOrganization> governmentOrganizations = null;
    GovernmentOrganizationResponse target =
        new GovernmentOrganizationResponse(governmentOrganizations);
    List<GovernmentOrganization> actual = target.getGovernmentOrganizations();
    List<GovernmentOrganization> expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  /**
   * @throws Exception - Exception
   */
  @Test
  public void setLovEntries_Args__List() throws Exception {
    List<GovernmentOrganization> governmentOrganizations = new ArrayList<GovernmentOrganization>();
    GovernmentOrganizationResponse target =
        new GovernmentOrganizationResponse(governmentOrganizations);
    target.setGovernmentOrganizations(governmentOrganizations);
  }

  /**
   * @throws Exception - Exception
   */
  @Test
  public void hashCode_Args__() throws Exception {
    List<GovernmentOrganization> governmentOrganizations = null;
    GovernmentOrganizationResponse target =
        new GovernmentOrganizationResponse(governmentOrganizations);
    int actual = target.hashCode();
    int expected = 629;
    assertThat(actual, is(equalTo(expected)));
  }

  /**
   * @throws Exception - Exception
   */
  @Test
  public void equals_Args__Object() throws Exception {
    List<GovernmentOrganization> governmentOrganizations = null;
    GovernmentOrganizationResponse target =
        new GovernmentOrganizationResponse(governmentOrganizations);
    Object obj = null;
    boolean actual = target.equals(obj);
    boolean expected = false;
    assertThat(actual, is(equalTo(expected)));
  }

  /**
   * @throws Exception - Exception
   */
  @Test
  public void main_Args__StringArray() throws Exception {
    final List<GovernmentOrganization> governmentOrganizations = new ArrayList<>();
    governmentOrganizations.add(new GovernmentOrganization("ABC1234567", "Sheriff Department",
        "Lawenforcement", (short) 1094));
    governmentOrganizations.add(new GovernmentOrganization("ABC1234567", "Sheriff Department",
        "Lawenforcement", (short) 1094));

    GovernmentOrganizationResponse response =
        new GovernmentOrganizationResponse(governmentOrganizations);
    final ObjectMapper mapper = ObjectMapperUtils.createObjectMapper();

    String jsonString = null;
    try {
      jsonString = mapper.writeValueAsString(response);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
  }

}
