package gov.ca.cwds.data.persistence.cms;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import org.junit.Test;

import gov.ca.cwds.fixture.DeliveredServiceEntityBuilder;

/**
 * @author CWDS API Team
 *
 */
public class DeliveredServiceTest {

  /**
   * Constructor test
   * 
   * @throws Exception - exception
   */
  @Test
  public void testEmptyConstructor() throws Exception {
    assertThat(DeliveredServiceEntity.class.newInstance(), is(notNullValue()));
  }

  /**
   * @throws Exception
   */
  @Test
  public void testPersistentConstructor() throws Exception {
    DeliveredServiceEntity deliverdServiceEntity =
        new DeliveredServiceEntityBuilder().buildDeliveredServiceEntity();

    DeliveredServiceEntity persistent = new DeliveredServiceEntity(
        deliverdServiceEntity.getCftLeadAgencyType(),
        deliverdServiceEntity.getCoreServiceIndicator(),
        deliverdServiceEntity.getCommunicationMethodType(),
        deliverdServiceEntity.getContactLocationType(), deliverdServiceEntity.getContactVisitCode(),
        deliverdServiceEntity.getCountySpecificCode(), deliverdServiceEntity.getDetailText(),
        deliverdServiceEntity.getHardCopyDocumentOnFileCode(),
        deliverdServiceEntity.getDetailTextContinuation(), deliverdServiceEntity.getEndDate(),
        deliverdServiceEntity.getEndTime(), deliverdServiceEntity.getPrimaryDeliveryServiceId(),
        deliverdServiceEntity.getId(), deliverdServiceEntity.getOtherParticipantsDesc(),
        deliverdServiceEntity.getProvidedByCode(), deliverdServiceEntity.getProvidedById(),
        deliverdServiceEntity.getStartDate(), deliverdServiceEntity.getStartTime(),
        deliverdServiceEntity.getStatusCode(), deliverdServiceEntity.getSupervisionCode(),
        deliverdServiceEntity.getServiceContactType(),
        deliverdServiceEntity.getWraparoundServiceIndicator());

    assertThat(persistent.getCftLeadAgencyType(),
        is(equalTo(deliverdServiceEntity.getCftLeadAgencyType())));
    assertThat(persistent.getCoreServiceIndicator(),
        is(equalTo(deliverdServiceEntity.getCoreServiceIndicator())));
    assertThat(persistent.getCommunicationMethodType(),
        is(equalTo(deliverdServiceEntity.getCommunicationMethodType())));
    assertThat(persistent.getContactLocationType(),
        is(equalTo(deliverdServiceEntity.getContactLocationType())));
    assertThat(persistent.getContactVisitCode(),
        is(equalTo(deliverdServiceEntity.getContactVisitCode())));
    assertThat(persistent.getCountySpecificCode(),
        is(equalTo(deliverdServiceEntity.getCountySpecificCode())));
    assertThat(persistent.getDetailText(), is(equalTo(deliverdServiceEntity.getDetailText())));
    assertThat(persistent.getHardCopyDocumentOnFileCode(),
        is(equalTo(deliverdServiceEntity.getHardCopyDocumentOnFileCode())));
    assertThat(persistent.getDetailTextContinuation(),
        is(equalTo(deliverdServiceEntity.getDetailTextContinuation())));
    assertThat(persistent.getEndDate(), is(equalTo(deliverdServiceEntity.getEndDate())));
    assertThat(persistent.getEndTime(), is(equalTo(deliverdServiceEntity.getEndTime())));
    assertThat(persistent.getPrimaryDeliveryServiceId(),
        is(equalTo(deliverdServiceEntity.getPrimaryDeliveryServiceId())));
    assertThat(persistent.getId(), is(equalTo(deliverdServiceEntity.getId())));
    assertThat(persistent.getOtherParticipantsDesc(),
        is(equalTo(deliverdServiceEntity.getOtherParticipantsDesc())));
    assertThat(persistent.getProvidedByCode(),
        is(equalTo(deliverdServiceEntity.getProvidedByCode())));
    assertThat(persistent.getProvidedById(), is(equalTo(deliverdServiceEntity.getProvidedById())));
    assertThat(persistent.getStartDate(), is(equalTo(deliverdServiceEntity.getStartDate())));
    assertThat(persistent.getStartTime(), is(equalTo(deliverdServiceEntity.getStartTime())));
    assertThat(persistent.getStatusCode(), is(equalTo(deliverdServiceEntity.getStatusCode())));
    assertThat(persistent.getSupervisionCode(),
        is(equalTo(deliverdServiceEntity.getSupervisionCode())));
    assertThat(persistent.getServiceContactType(),
        is(equalTo(deliverdServiceEntity.getServiceContactType())));
    assertThat(persistent.getWraparoundServiceIndicator(),
        is(equalTo(deliverdServiceEntity.getWraparoundServiceIndicator())));

  }

}
