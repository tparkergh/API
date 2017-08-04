// package gov.ca.cwds.data.persistence.cms;
//
// import static org.hamcrest.MatcherAssert.assertThat;
// import static org.hamcrest.Matchers.equalTo;
// import static org.hamcrest.Matchers.is;
// import static org.hamcrest.Matchers.notNullValue;
//
// import org.junit.Test;
//
// import gov.ca.cwds.fixture.ContactPartyDeliverdServiceEntityBuilder;
//
/// **
// * @author CWDS API Team
// *
// */
// public class ContactPartyDeliveredServiceTest {
//
// /**
// * Constructor test
// *
// * @throws Exception - exception
// */
// @Test
// public void testEmptyConstructor() throws Exception {
// assertThat(ContactPartyDeliveredServiceEntity.class.newInstance(), is(notNullValue()));
// }
//
// /**
// * @throws Exception - exception
// */
// @Test
// public void testPersistentConstructor() throws Exception {
//
// ContactPartyDeliveredServiceEntity validContactPartyDeliveredService =
// new ContactPartyDeliverdServiceEntityBuilder().buildContactPartyDeliveredService();
//
// gov.ca.cwds.data.persistence.cms.ContactPartyDeliveredServiceEntity persistent =
// new ContactPartyDeliveredServiceEntity(validContactPartyDeliveredService.getThirdId(),
// validContactPartyDeliveredService.getContactPartyType(),
// validContactPartyDeliveredService.getCountySpecificCode(),
// validContactPartyDeliveredService.getDeliveredServiceId());
//
// assertThat(persistent.getThirdId(),
// is(equalTo(validContactPartyDeliveredService.getThirdId())));
// assertThat(persistent.getContactPartyType(),
// is(equalTo(validContactPartyDeliveredService.getContactPartyType())));
// assertThat(persistent.getCountySpecificCode(),
// is(equalTo(validContactPartyDeliveredService.getCountySpecificCode())));
// assertThat(persistent.getDeliveredServiceId(),
// is(equalTo(validContactPartyDeliveredService.getDeliveredServiceId())));
// }
//
// }
