package gov.ca.cwds.data.persistence.contact;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import java.util.Date;

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
  private Date lastUpdatedTime = new Date();

  /**
   * Constructor test
   * 
   * @throws Exception - exception
   */
  @Test
  public void testEmptyConstructor() throws Exception {
    assertThat(DeliveredServiceEntity.class.newInstance(), is(notNullValue()));
  }

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

    DeliveredServiceEntity deliveredServiceEntity =
        new DeliveredServiceEntity(id, deliveredServiceDomain, lastUpdatedId, lastUpdatedTime);

    assertThat(deliveredServiceEntity.getId(), is(equalTo(id)));
    assertThat(deliveredServiceEntity.getCftLeadAgencyType(),
        is(equalTo(deliveredServiceDomain.getCftLeadAgencyType())));
    assertThat(deliveredServiceEntity.getCoreServiceIndicator(),
        is(equalTo(DomainChef.cookBoolean(deliveredServiceDomain.getCoreServiceIndicator()))));
    assertThat(deliveredServiceEntity.getCommunicationMethodType(),
        is(equalTo(deliveredServiceDomain.getCommunicationMethodType().shortValue())));
    assertThat(deliveredServiceEntity.getContactLocationType(),
        is(equalTo(deliveredServiceDomain.getContactLocationType().shortValue())));
    assertThat(deliveredServiceEntity.getContactVisitCode(),
        is(equalTo(deliveredServiceDomain.getContactVisitCode())));
    assertThat(deliveredServiceEntity.getCountySpecificCode(),
        is(equalTo(deliveredServiceDomain.getCountySpecificCode())));
    assertThat(deliveredServiceEntity.getDetailText(),
        is(equalTo(deliveredServiceDomain.getDetailText())));
    assertThat(deliveredServiceEntity.getHardCopyDocumentOnFileCode(),
        is(equalTo(deliveredServiceDomain.getHardCopyDocumentOnFileCode())));
    assertThat(deliveredServiceEntity.getDetailTextContinuation(),
        is(equalTo(deliveredServiceDomain.getDetailTextContinuation())));
    assertThat(deliveredServiceEntity.getEndDate(),
        is(equalTo(DomainChef.uncookDateString(deliveredServiceDomain.getEndDate()))));
    assertThat(deliveredServiceEntity.getEndTime(),
        is(equalTo(DomainChef.uncookTimeString(deliveredServiceDomain.getEndTime()))));
    assertThat(deliveredServiceEntity.getPrimaryDeliveredServiceId(),
        is(equalTo(deliveredServiceDomain.getPrimaryDeliveredServiceId())));
    assertThat(deliveredServiceEntity.getOtherParticipantsDesc(),
        is(equalTo(deliveredServiceDomain.getOtherParticipantsDesc())));
    assertThat(deliveredServiceEntity.getProvidedByCode(),
        is(equalTo(deliveredServiceDomain.getProvidedByCode())));
    assertThat(deliveredServiceEntity.getProvidedById(),
        is(equalTo(deliveredServiceDomain.getProvidedById())));
    assertThat(deliveredServiceEntity.getStartDate(),
        is(equalTo(DomainChef.uncookDateString(deliveredServiceDomain.getStartDate()))));
    assertThat(deliveredServiceEntity.getStartTime(),
        is(equalTo(DomainChef.uncookTimeString(deliveredServiceDomain.getStartTime()))));
    assertThat(deliveredServiceEntity.getStatusCode(),
        is(equalTo(deliveredServiceDomain.getStatusCode())));
    assertThat(deliveredServiceEntity.getSupervisionCode(),
        is(equalTo(deliveredServiceDomain.getSupervisionCode())));
    assertThat(deliveredServiceEntity.getServiceContactType(),
        is(equalTo(deliveredServiceDomain.getServiceContactType().shortValue())));
    assertThat(deliveredServiceEntity.getWraparoundServiceIndicator(), is(
        equalTo(DomainChef.cookBoolean(deliveredServiceDomain.getWraparoundServiceIndicator()))));

  }

}
