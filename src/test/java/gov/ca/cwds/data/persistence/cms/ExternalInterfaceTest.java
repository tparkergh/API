package gov.ca.cwds.data.persistence.cms;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import java.io.IOException;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author CWDS API Team
 *
 */
public class ExternalInterfaceTest {

  private static final ObjectMapper MAPPER = SystemCodeTestHarness.MAPPER;

  /**
   * Constructor test
   * 
   * @throws Exception test general
   */
  @Test
  public void testEmptyConstructor() throws Exception {
    assertThat(ExternalInterface.class.newInstance(), is(notNullValue()));
  }

  /**
   * Persistent Constructor
   * 
   * @throws Exception - Exception
   */
  @Test
  public void testPersistentConstructor() throws Exception {
    ExternalInterface exin = validExternalInterface();

    gov.ca.cwds.data.persistence.cms.ExternalInterface persistent =
        new gov.ca.cwds.data.persistence.cms.ExternalInterface(exin.getAidTypeCode(),
            exin.getAssignmentUnitCode(), exin.getClearanceResponseType(), exin.getFbiIndicator(),
            exin.getGovernmentEntityCode(), exin.getLogonUserId(), exin.getLicenseNumber(),
            exin.getOperationType(), exin.getOtherData(), exin.getPersonNumber(),
            exin.getPrimaryKey1(), exin.getPrimaryKey2(), exin.getPrimaryKey3(),
            exin.getPrimaryKey4(), exin.getPrimaryKey5(), exin.getPrimaryKey6(),
            exin.getPrimaryKey7(), exin.getPrimaryKey8(), exin.getRapIdentifier(),
            exin.getResponseReceivedDate(), exin.getClearanceResponseDate(),
            exin.getSequenceNumber(), exin.getSerialNumber(), exin.getStartDate(),
            exin.getSubmitlTimestamp(), exin.getTableName());

    assertThat(persistent.getAidTypeCode(), is(equalTo(exin.getAidTypeCode())));
    assertThat(persistent.getAssignmentUnitCode(), is(equalTo(exin.getAssignmentUnitCode())));
    assertThat(persistent.getClearanceResponseType(), is(equalTo(exin.getClearanceResponseType())));
    assertThat(persistent.getFbiIndicator(), is(equalTo(exin.getFbiIndicator())));
    assertThat(persistent.getGovernmentEntityCode(), is(equalTo(exin.getGovernmentEntityCode())));
    assertThat(persistent.getLogonUserId(), is(equalTo(exin.getLogonUserId())));
    assertThat(persistent.getLicenseNumber(), is(equalTo(exin.getLicenseNumber())));
    assertThat(persistent.getOperationType(), is(equalTo(exin.getOperationType())));
    assertThat(persistent.getOtherData(), is(equalTo(exin.getOtherData())));
    assertThat(persistent.getPersonNumber(), is(equalTo(exin.getPersonNumber())));
    assertThat(persistent.getPrimaryKey1(), is(equalTo(exin.getPrimaryKey1())));
    assertThat(persistent.getPrimaryKey2(), is(equalTo(exin.getPrimaryKey2())));
    assertThat(persistent.getPrimaryKey3(), is(equalTo(exin.getPrimaryKey3())));
    assertThat(persistent.getPrimaryKey4(), is(equalTo(exin.getPrimaryKey4())));
    assertThat(persistent.getPrimaryKey5(), is(equalTo(exin.getPrimaryKey5())));
    assertThat(persistent.getPrimaryKey6(), is(equalTo(exin.getPrimaryKey6())));
    assertThat(persistent.getPrimaryKey7(), is(equalTo(exin.getPrimaryKey7())));
    assertThat(persistent.getPrimaryKey8(), is(equalTo(exin.getPrimaryKey8())));
    assertThat(persistent.getRapIdentifier(), is(equalTo(exin.getRapIdentifier())));
    assertThat(persistent.getResponseReceivedDate(), is(equalTo(exin.getResponseReceivedDate())));
    assertThat(persistent.getClearanceResponseDate(), is(equalTo(exin.getClearanceResponseDate())));
    assertThat(persistent.getSequenceNumber(), is(equalTo(exin.getSequenceNumber())));
    assertThat(persistent.getSerialNumber(), is(equalTo(exin.getSerialNumber())));
    assertThat(persistent.getStartDate(), is(equalTo(exin.getStartDate())));
    assertThat(persistent.getSubmitlTimestamp(), is(equalTo(exin.getSubmitlTimestamp())));
    assertThat(persistent.getTableName(), is(equalTo(exin.getTableName())));
  }

  private ExternalInterface validExternalInterface()
      throws JsonParseException, JsonMappingException, IOException {

    ExternalInterface validExternalInterface = MAPPER.readValue(
        fixture("fixtures/persistent/ExternalInterface/valid/valid.json"), ExternalInterface.class);
    return validExternalInterface;
  }

}
