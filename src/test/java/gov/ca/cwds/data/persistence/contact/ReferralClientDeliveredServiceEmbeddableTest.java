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
public class ReferralClientDeliveredServiceEmbeddableTest {

  /**
   * Constructor test
   * 
   * @throws Exception - exception
   */
  @Test
  public void testEmptyConstructor() throws Exception {
    assertThat(ReferralClientDeliveredServiceEmbeddable.class.newInstance(), is(notNullValue()));
  }

  /**
   * 
   */
  @Test
  public void testEqualsHashCodeWorks() {
    EqualsVerifier.forClass(ReferralClientDeliveredServiceEmbeddable.class)
        .suppress(Warning.NONFINAL_FIELDS).verify();
  }

  /**
   * @throws Exception - exception
   */
  @Test
  public void testPersistentConstructor() throws Exception {

    ReferralClientDeliveredServiceEmbeddable valid =
        validReferralClientDeliveredServiceEmbeddable();

    ReferralClientDeliveredServiceEmbeddable ReferralClientDeliveredServiceEmbeddable =
        new ReferralClientDeliveredServiceEmbeddable(valid.getDeliveredServiceId(),
            valid.getReferralId(), valid.getClientId());

    assertThat(ReferralClientDeliveredServiceEmbeddable.getDeliveredServiceId(),
        is(equalTo(valid.getDeliveredServiceId())));
    assertThat(ReferralClientDeliveredServiceEmbeddable.getReferralId(),
        is(equalTo(valid.getReferralId())));
    assertThat(ReferralClientDeliveredServiceEmbeddable.getClientId(),
        is(equalTo(valid.getClientId())));
  }

  private ReferralClientDeliveredServiceEmbeddable validReferralClientDeliveredServiceEmbeddable()
      throws JsonParseException, JsonMappingException, IOException {

    ReferralClientDeliveredServiceEmbeddable validReferralClientDeliveredServiceEmbeddable =
        new ReferralClientDeliveredServiceEmbeddable("ABC1234567", "ABX1234560", "APc109852u");
    return validReferralClientDeliveredServiceEmbeddable;

  }

}
