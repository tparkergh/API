package gov.ca.cwds.rest.api.domain.cms;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import java.util.Date;

import org.junit.After;
import org.junit.ClassRule;
import org.junit.Test;

import com.squarespace.jersey2.guice.JerseyGuiceUtils;

import gov.ca.cwds.rest.resources.cms.JerseyGuiceRule;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

/**
 * @author CWDS API Team
 *
 */
@SuppressWarnings("javadoc")
public class ClientScpEthnicityTest {

  @After
  public void ensureServiceLocatorPopulated() {
    JerseyGuiceUtils.reset();
  }

  @ClassRule
  public static JerseyGuiceRule rule = new JerseyGuiceRule();

  private String id = "ABC1234560";
  private String establishedForCode = "C";
  private String establishedId = "123450ABC";
  private Short ethnicity = (short) 834;

  /**
   * @throws Exception test standard test standard
   */
  @Test
  public void persistentObjectConstructorTest() throws Exception {

    ClientScpEthnicity domain =
        new ClientScpEthnicity(id, establishedForCode, establishedId, ethnicity);

    gov.ca.cwds.data.persistence.cms.ClientScpEthnicity persistent =
        new gov.ca.cwds.data.persistence.cms.ClientScpEthnicity(id, domain, "lastUpdatedId",
            new Date());

    assertThat(domain.getId(), is(equalTo(persistent.getId())));
    assertThat(domain.getEstablishedForCode(), is(equalTo(persistent.getEstablishedForCode())));
    assertThat(domain.getEstablishedId(), is(equalTo(persistent.getEstablishedId())));
    assertThat(domain.getEthnicity(), is(equalTo(persistent.getEthnicity())));

  }

  /**
   * @throws Exception test standard test standard
   */
  @Test
  public void jsonCreatorConstructorTest() throws Exception {

    ClientScpEthnicity domain =
        new ClientScpEthnicity(id, establishedForCode, establishedId, ethnicity);

    assertThat(domain.getId(), is(equalTo(id)));
    assertThat(domain.getEstablishedForCode(), is(equalTo(establishedForCode)));
    assertThat(domain.getEstablishedId(), is(equalTo(establishedId)));
    assertThat(domain.getEthnicity(), is(equalTo(ethnicity)));

  }

  /**
   * 
   */
  @Test
  public void equalsHashCodeWork() {
    EqualsVerifier.forClass(ClientScpEthnicity.class).suppress(Warning.NONFINAL_FIELDS).verify();
  }

}
