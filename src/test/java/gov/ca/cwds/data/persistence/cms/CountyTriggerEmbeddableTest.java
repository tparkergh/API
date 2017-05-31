package gov.ca.cwds.data.persistence.cms;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import org.junit.Test;

import gov.ca.cwds.data.persistence.junit.template.PersistentTestTemplate;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

/**
 * @author CWDS API Team
 *
 */
public class CountyTriggerEmbeddableTest implements PersistentTestTemplate {

  /*
   * Constructor test
   */
  @Override
  @Test
  public void testEmptyConstructor() throws Exception {
    assertThat(CountyTriggerEmbeddable.class.newInstance(), is(notNullValue()));
  }

  @Override
  @Test
  public void testEqualsHashCodeWorks() {
    EqualsVerifier.forClass(CountyTriggerEmbeddable.class).suppress(Warning.NONFINAL_FIELDS)
        .verify();

  }

  @Override
  public void testPersistentConstructor() throws Exception {

  }

  @Override
  public void testConstructorUsingDomain() throws Exception {

  }

}
