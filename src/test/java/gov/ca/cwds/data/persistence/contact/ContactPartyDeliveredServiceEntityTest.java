package gov.ca.cwds.data.persistence.contact;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import gov.ca.cwds.fixture.contacts.ContactPartyDeliverdServiceEntityBuilder;

import org.junit.Test;


/**
 * @author CWDS API Team
 *
 */
public class ContactPartyDeliveredServiceEntityTest {

  /**
   * Constructor test
   * 
   * @throws Exception - exception
   */
  @Test
  public void testEmptyConstructor() throws Exception {
    assertThat(ContactPartyDeliveredServiceEntity.class.newInstance(), is(notNullValue()));
  }

  /**
   * @throws Exception - exception
   */
  @Test
  public void testPersistentConstructor() throws Exception {

    ContactPartyDeliveredServiceEntity validContactPartyDeliveredServiceEntity =
        new ContactPartyDeliverdServiceEntityBuilder().buildContactPartyDeliveredService();

    gov.ca.cwds.data.persistence.contact.ContactPartyDeliveredServiceEntity persistent =
        new ContactPartyDeliveredServiceEntity(
            validContactPartyDeliveredServiceEntity.getThirdId(),
            validContactPartyDeliveredServiceEntity.getContactPartyType(),
            validContactPartyDeliveredServiceEntity.getCountySpecificCode(),
            validContactPartyDeliveredServiceEntity.getDeliveredServiceId(),
            validContactPartyDeliveredServiceEntity.getLastUpdatedId(),
            validContactPartyDeliveredServiceEntity.getLastUpdatedTime());

    assertThat(persistent.getThirdId(),
        is(equalTo(validContactPartyDeliveredServiceEntity.getThirdId())));
    assertThat(persistent.getContactPartyType(),
        is(equalTo(validContactPartyDeliveredServiceEntity.getContactPartyType())));
    assertThat(persistent.getCountySpecificCode(),
        is(equalTo(validContactPartyDeliveredServiceEntity.getCountySpecificCode())));
    assertThat(persistent.getDeliveredServiceId(),
        is(equalTo(validContactPartyDeliveredServiceEntity.getDeliveredServiceId())));
    assertThat(persistent.getLastUpdatedId(),
        is(equalTo(validContactPartyDeliveredServiceEntity.getLastUpdatedId())));
    assertThat(persistent.getLastUpdatedTime(),
        is(equalTo(validContactPartyDeliveredServiceEntity.getLastUpdatedTime())));
  }

}
