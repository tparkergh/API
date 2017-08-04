package gov.ca.cwds.data.persistence.contact;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import org.junit.Test;

import gov.ca.cwds.fixture.contacts.DeliveredServiceEntityBuilder;
import gov.ca.cwds.fixture.contacts.DeliveredServiceResourceBuilder;
import gov.ca.cwds.rest.api.contact.DeliveredServiceDomain;
import gov.ca.cwds.rest.api.domain.DomainChef;


/**
 * @author CWDS API Team
 *
 */
public class DeliveredServiceEntityTest {

  private String id = "1234567ABC";
  private String lastUpdatedId = "0X5";

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
        deliverdServiceEntity.getEndTime(), deliverdServiceEntity.getPrimaryDeliveredServiceId(),
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
    assertThat(persistent.getPrimaryDeliveredServiceId(),
        is(equalTo(deliverdServiceEntity.getPrimaryDeliveredServiceId())));
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

  /**
   * @throws Exception - exception
   */
  @Test
  public void testConstructorUsingDomain() throws Exception {

    DeliveredServiceDomain deliveredServiceDomain =
        new DeliveredServiceResourceBuilder().buildDeliveredServiceResource();

    DeliveredServiceEntity persistent =
        new DeliveredServiceEntity(id, deliveredServiceDomain, lastUpdatedId);

    assertThat(persistent.getId(), is(equalTo(id)));
    assertThat(persistent.getCftLeadAgencyType(),
        is(equalTo(deliveredServiceDomain.getCftLeadAgencyType())));
    assertThat(persistent.getCoreServiceIndicator(),
        is(equalTo(DomainChef.cookBoolean(deliveredServiceDomain.getCoreServiceIndicator()))));
    assertThat(persistent.getCommunicationMethodType(),
        is(equalTo(deliveredServiceDomain.getCommunicationMethodType().shortValue())));
    assertThat(persistent.getContactLocationType(),
        is(equalTo(deliveredServiceDomain.getContactLocationType().shortValue())));
    assertThat(persistent.getContactVisitCode(),
        is(equalTo(deliveredServiceDomain.getContactVisitCode())));
    assertThat(persistent.getCountySpecificCode(),
        is(equalTo(deliveredServiceDomain.getCountySpecificCode())));
    assertThat(persistent.getDetailText(), is(equalTo(deliveredServiceDomain.getDetailText())));
    assertThat(persistent.getHardCopyDocumentOnFileCode(),
        is(equalTo(deliveredServiceDomain.getHardCopyDocumentOnFileCode())));
    assertThat(persistent.getDetailTextContinuation(),
        is(equalTo(deliveredServiceDomain.getDetailTextContinuation())));
    assertThat(persistent.getEndDate(),
        is(equalTo(DomainChef.uncookDateString(deliveredServiceDomain.getEndDate()))));
    assertThat(persistent.getEndTime(),
        is(equalTo(DomainChef.uncookTimeString(deliveredServiceDomain.getEndTime()))));
    assertThat(persistent.getPrimaryDeliveredServiceId(),
        is(equalTo(deliveredServiceDomain.getPrimaryDeliveredServiceId())));

    assertThat(persistent.getOtherParticipantsDesc(),
        is(equalTo(deliveredServiceDomain.getOtherParticipantsDesc())));
    assertThat(persistent.getProvidedByCode(),
        is(equalTo(deliveredServiceDomain.getProvidedByCode())));
    assertThat(persistent.getProvidedById(), is(equalTo(deliveredServiceDomain.getProvidedById())));
    assertThat(persistent.getStartDate(),
        is(equalTo(DomainChef.uncookDateString(deliveredServiceDomain.getStartDate()))));
    assertThat(persistent.getStartTime(),
        is(equalTo(DomainChef.uncookTimeString(deliveredServiceDomain.getStartTime()))));
    assertThat(persistent.getStatusCode(), is(equalTo(deliveredServiceDomain.getStatusCode())));
    assertThat(persistent.getSupervisionCode(),
        is(equalTo(deliveredServiceDomain.getSupervisionCode())));
    assertThat(persistent.getServiceContactType(),
        is(equalTo(deliveredServiceDomain.getServiceContactType().shortValue())));
    assertThat(persistent.getWraparoundServiceIndicator(), is(
        equalTo(DomainChef.cookBoolean(deliveredServiceDomain.getWraparoundServiceIndicator()))));

  }

}
