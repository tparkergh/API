package gov.ca.cwds.data.persistence.cms;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import java.io.IOException;

import org.junit.Ignore;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

/**
 * @author CWDS API Team
 *
 */
public class DeliveredServiceTest {


  private static final ObjectMapper MAPPER = SystemCodeTestHarness.MAPPER;

  /**
   * Constructor test
   */
  @Test
  public void testEmptyConstructor() throws Exception {
    assertThat(DeliveredService.class.newInstance(), is(notNullValue()));
  }

  /**
   * @throws Exception
   */
  @Test
  public void testPersistentConstructor() throws Exception {
    DeliveredService delsv = validDeliveredService();

    gov.ca.cwds.data.persistence.cms.DeliveredService persistent =
        new gov.ca.cwds.data.persistence.cms.DeliveredService(delsv.getCftLeadAgencyType(),
            delsv.getCoreServiceIndicator(), delsv.getCommunicationMethodType(),
            delsv.getContactLocationType(), delsv.getContactVisitCode(),
            delsv.getCountySpecificCode(), delsv.getDetailText(),
            delsv.getHardCopyDocumentOnFileCode(), delsv.getDetailTextContinuation(),
            delsv.getEndDate(), delsv.getEndTime(), delsv.getPrimaryDeliveryServiceId(),
            delsv.getId(), delsv.getOtherParticipantsDesc(), delsv.getProvidedByCode(),
            delsv.getProvidedById(), delsv.getStartDate(), delsv.getStartTime(),
            delsv.getStatusCode(), delsv.getSupervisionCode(), delsv.getServiceContactType(),
            delsv.getWraparoundServiceIndicator());

    assertThat(persistent.getCftLeadAgencyType(), is(equalTo(delsv.getCftLeadAgencyType())));
    assertThat(persistent.getCoreServiceIndicator(), is(equalTo(delsv.getCoreServiceIndicator())));
    assertThat(persistent.getCommunicationMethodType(),
        is(equalTo(delsv.getCommunicationMethodType())));
    assertThat(persistent.getContactLocationType(), is(equalTo(delsv.getContactLocationType())));
    assertThat(persistent.getContactVisitCode(), is(equalTo(delsv.getContactVisitCode())));
    assertThat(persistent.getCountySpecificCode(), is(equalTo(delsv.getCountySpecificCode())));
    assertThat(persistent.getDetailText(), is(equalTo(delsv.getDetailText())));
    assertThat(persistent.getHardCopyDocumentOnFileCode(),
        is(equalTo(delsv.getHardCopyDocumentOnFileCode())));
    assertThat(persistent.getDetailTextContinuation(),
        is(equalTo(delsv.getDetailTextContinuation())));
    assertThat(persistent.getEndDate(), is(equalTo(delsv.getEndDate())));
    assertThat(persistent.getEndTime(), is(equalTo(delsv.getEndTime())));
    assertThat(persistent.getPrimaryDeliveryServiceId(),
        is(equalTo(delsv.getPrimaryDeliveryServiceId())));
    assertThat(persistent.getId(), is(equalTo(delsv.getId())));
    assertThat(persistent.getOtherParticipantsDesc(),
        is(equalTo(delsv.getOtherParticipantsDesc())));
    assertThat(persistent.getProvidedByCode(), is(equalTo(delsv.getProvidedByCode())));
    assertThat(persistent.getProvidedById(), is(equalTo(delsv.getProvidedById())));
    assertThat(persistent.getStartDate(), is(equalTo(delsv.getStartDate())));
    assertThat(persistent.getStartTime(), is(equalTo(delsv.getStartTime())));
    assertThat(persistent.getStatusCode(), is(equalTo(delsv.getStatusCode())));
    assertThat(persistent.getSupervisionCode(), is(equalTo(delsv.getSupervisionCode())));
    assertThat(persistent.getServiceContactType(), is(equalTo(delsv.getServiceContactType())));
    assertThat(persistent.getWraparoundServiceIndicator(),
        is(equalTo(delsv.getWraparoundServiceIndicator())));
  }


  /**
   * @throws Exception
   */
  @Test
  @Ignore
  public void testEqualsHashCodeWorks() {
    EqualsVerifier.forClass(DeliveredService.class).suppress(Warning.NONFINAL_FIELDS).verify();
  }

  private DeliveredService validDeliveredService()
      throws JsonParseException, JsonMappingException, IOException {

    DeliveredService validDeliveredService = MAPPER.readValue(
        fixture("fixtures/persistent/DeliveredService/valid/valid.json"), DeliveredService.class);
    return validDeliveredService;
  }

}
