package gov.ca.cwds.data.persistence.contact;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import java.io.IOException;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

/**
 * @author CWDS API Team
 *
 */
public class IndividualDeliveredServiceEmbeddableTest {

  private String deliveredServiceId = "ABC1234567";
  private String deliveredToIndividualCode = "C";
  private String deliveredToIndividualId = "AVC1098765";


  /**
   * Constructor test
   * 
   * @throws Exception - exception
   */
  @Test
  public void testEmptyConstructor() throws Exception {
    assertThat(IndividualDeliveredServiceEmbeddable.class.newInstance(), is(notNullValue()));
  }

  /**
   * @throws Exception - exception
   */
  @Test
  public void testPersistentConstructor() throws Exception {

    IndividualDeliveredServiceEmbeddable valid = validIndividualDeliveredServiceEmbeddable();

    IndividualDeliveredServiceEmbeddable individualDeliveredServiceEmbeddable =
        new IndividualDeliveredServiceEmbeddable(valid.getDeliveredServiceId(),
            valid.getDeliveredToIndividualCode(), valid.getDeliveredToIndividualId());

    assertThat(individualDeliveredServiceEmbeddable.getDeliveredServiceId(),
        is(equalTo(valid.getDeliveredServiceId())));
    assertThat(individualDeliveredServiceEmbeddable.getDeliveredToIndividualCode(),
        is(equalTo(valid.getDeliveredToIndividualCode())));
    assertThat(individualDeliveredServiceEmbeddable.getDeliveredToIndividualId(),
        is(equalTo(valid.getDeliveredToIndividualId())));
  }

  /**
   * 
   */
  @Test
  public void testEqualsHashCodeWorks() {
    EqualsVerifier.forClass(IndividualDeliveredServiceEmbeddable.class)
        .suppress(Warning.NONFINAL_FIELDS).verify();
  }

  private IndividualDeliveredServiceEmbeddable validIndividualDeliveredServiceEmbeddable()
      throws JsonParseException, JsonMappingException, IOException {

    IndividualDeliveredServiceEmbeddable validIndividualDeliveredServiceEmbeddable =
        new IndividualDeliveredServiceEmbeddable(deliveredServiceId, deliveredToIndividualCode,
            deliveredToIndividualId);
    return validIndividualDeliveredServiceEmbeddable;

  }

}
