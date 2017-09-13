package gov.ca.cwds.data.persistence.cms;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import java.util.Date;

import org.junit.Test;

import gov.ca.cwds.fixture.ClientScpEthnicityEntityBuilder;
import gov.ca.cwds.fixture.ClientScpEthnicityResourceBuilder;

/**
 * @author CWDS API Team
 *
 */
public class ClientScpEthnicityTest {

  private String id = "1234567ABC";
  private String lastUpdatedId = "0X5";
  private Date lastUpdatedTime = new Date();

  /**
   * Constructor test
   * 
   * @throws Exception - exception
   */
  @Test
  public void testEmptyConstructor() throws Exception {
    assertThat(ClientScpEthnicity.class.newInstance(), is(notNullValue()));
  }

  /**
   * persistent Constructor test
   * 
   * @throws Exception - exception
   */
  @Test
  public void testPersistentConstructor() throws Exception {

    ClientScpEthnicity validClientScpEthnicity = new ClientScpEthnicityEntityBuilder().build();

    ClientScpEthnicity persistent = new ClientScpEthnicity(id,
        validClientScpEthnicity.getEstablishedForCode(), validClientScpEthnicity.getEstablishedId(),
        validClientScpEthnicity.getEthnicity(), lastUpdatedId, lastUpdatedTime);

    assertThat(persistent.getId(), is(equalTo(id)));
    assertThat(persistent.getEstablishedForCode(),
        is(equalTo(validClientScpEthnicity.getEstablishedForCode())));
    assertThat(persistent.getEstablishedId(),
        is(equalTo(validClientScpEthnicity.getEstablishedId())));
    assertThat(persistent.getEthnicity(), is(equalTo(validClientScpEthnicity.getEthnicity())));
  }

  /**
   * domain Constructor test
   * 
   * @throws Exception - exception
   */
  @Test
  public void testConstructorUsingDomain() throws Exception {

    gov.ca.cwds.rest.api.domain.cms.ClientScpEthnicity domain =
        new ClientScpEthnicityResourceBuilder().build();

    ClientScpEthnicity persistent =
        new ClientScpEthnicity(id, domain, lastUpdatedId, lastUpdatedTime);
    assertThat(persistent.getId(), is(equalTo(id)));
    assertThat(persistent.getEstablishedForCode(), is(equalTo(domain.getEstablishedForCode())));
    assertThat(persistent.getEstablishedId(), is(equalTo(domain.getEstablishedId())));
    assertThat(persistent.getEthnicity(), is(equalTo(domain.getEthnicity())));
  }


}
