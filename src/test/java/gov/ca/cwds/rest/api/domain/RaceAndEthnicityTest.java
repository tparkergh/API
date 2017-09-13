package gov.ca.cwds.rest.api.domain;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import java.util.LinkedHashSet;

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
public class RaceAndEthnicityTest {

  /**
   * 
   */
  @After
  public void ensureServiceLocatorPopulated() {
    JerseyGuiceUtils.reset();
  }

  /**
   * 
   */
  @ClassRule
  public static JerseyGuiceRule rule = new JerseyGuiceRule();

  private LinkedHashSet<Short> raceCode = new LinkedHashSet<>();
  private String unableToDetermineCode = "A";
  private LinkedHashSet<Short> hispanicCode = new LinkedHashSet<>();
  private String hispanicOriginCode = "X";
  private String hispanicUnableToDetermineCode = "A";

  /**
   * testfor Empty Constructor
   * 
   * @throws Exception - exception
   */
  @Test
  public void testEmptyConstructor() throws Exception {
    RaceAndEthnicity empty = new RaceAndEthnicity();
    assertThat(empty.getClass(), is(RaceAndEthnicity.class));
  }

  /**
   * 
   */
  @Test
  public void equalsHashCodeWork() {
    EqualsVerifier.forClass(RaceAndEthnicity.class).suppress(Warning.NONFINAL_FIELDS).verify();
  }

  /**
   * @throws Exception test standard test standard
   */
  @Test
  public void jsonCreatorConstructorTest() throws Exception {

    RaceAndEthnicity domain = new RaceAndEthnicity(raceCode, unableToDetermineCode, hispanicCode,
        hispanicOriginCode, hispanicUnableToDetermineCode);

    assertThat(domain.getRaceCode(), is(equalTo(raceCode)));
    assertThat(domain.getUnableToDetermineCode(), is(equalTo(unableToDetermineCode)));
    assertThat(domain.getHispanicCode(), is(equalTo(hispanicCode)));
    assertThat(domain.getHispanicOriginCode(), is(equalTo(hispanicOriginCode)));
    assertThat(domain.getHispanicUnableToDetermineCode(),
        is(equalTo(hispanicUnableToDetermineCode)));
  }

}
