package gov.ca.cwds.rest.services.cms;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.LinkedHashSet;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import gov.ca.cwds.data.cms.ClientScpEthnicityDao;
import gov.ca.cwds.fixture.ClientScpEthnicityResourceBuilder;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.RaceAndEthnicity;
import gov.ca.cwds.rest.api.domain.cms.ClientScpEthnicity;
import gov.ca.cwds.rest.filters.TestingRequestExecutionContext;
import gov.ca.cwds.rest.messages.MessageBuilder;
import gov.ca.cwds.rest.services.ServiceException;

/**
 * @author CWDS API Team
 *
 */
@SuppressWarnings("javadoc")
public class ClientScpEthnicityServiceTest {

  private ClientScpEthnicityService clientScpEthnicityService;
  private ClientScpEthnicityDao clientScpEthnicityDao;
  private MessageBuilder messageBuilder;

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Before
  public void setup() throws Exception {
    new TestingRequestExecutionContext("0X5");
    this.clientScpEthnicityDao = mock(ClientScpEthnicityDao.class);
    this.messageBuilder = mock(MessageBuilder.class);
    clientScpEthnicityService = new ClientScpEthnicityService(clientScpEthnicityDao);
  }

  // find test
  @Test
  public void clientScpEthnicityServiceFindReturnsCorrectEntity() throws Exception {
    String id = "ABC1234560";
    ClientScpEthnicity expected = new ClientScpEthnicityResourceBuilder().build();
    gov.ca.cwds.data.persistence.cms.ClientScpEthnicity clientScpEthnicity =
        new gov.ca.cwds.data.persistence.cms.ClientScpEthnicity(id, expected, "0XA", new Date());

    when(clientScpEthnicityDao.find(id)).thenReturn(clientScpEthnicity);
    ClientScpEthnicity found = clientScpEthnicityService.find(id);
    assertThat(found, is(expected));
  }

  @Test
  public void clientScpEthnicityServiceFindReturnsNullWhenNotFound() throws Exception {
    Response found = clientScpEthnicityService.find("ABC1234567");
    assertThat(found, is(nullValue()));
  }

  // delete test
  @Test
  public void clientScpEthnicityServiceDeleteDelegatesToCrudsService() {
    clientScpEthnicityService.delete("ABC2345678");
    verify(clientScpEthnicityDao, times(1)).delete("ABC2345678");
  }

  @Test
  public void clientScpEthnicityServiceDeleteReturnsNullWhenNotFound() throws Exception {
    Response found = clientScpEthnicityService.delete("ABC1234567");
    assertThat(found, is(nullValue()));
  }

  @Test
  public void clientScpEthnicityServiceDeleteReturnsNotNull() throws Exception {
    String id = "ABC1234560";
    ClientScpEthnicity expected = new ClientScpEthnicityResourceBuilder().build();

    gov.ca.cwds.data.persistence.cms.ClientScpEthnicity clientScpEthnicity =
        new gov.ca.cwds.data.persistence.cms.ClientScpEthnicity(id, expected, "0XA", new Date());

    when(clientScpEthnicityDao.delete(id)).thenReturn(clientScpEthnicity);
    ClientScpEthnicity found = clientScpEthnicityService.delete(id);
    assertThat(found, is(expected));
  }

  // update test
  @Test
  public void clientScpEthnicityServiceUpdateReturnsCorrectEntity() throws Exception {
    String id = "ABC1234560";
    ClientScpEthnicity expected = new ClientScpEthnicityResourceBuilder().build();

    gov.ca.cwds.data.persistence.cms.ClientScpEthnicity clientScpEthnicity =
        new gov.ca.cwds.data.persistence.cms.ClientScpEthnicity(id, expected, "ABC", new Date());

    when(clientScpEthnicityDao.find("ABC1234567")).thenReturn(clientScpEthnicity);
    when(clientScpEthnicityDao.update(any())).thenReturn(clientScpEthnicity);

    Object retval = clientScpEthnicityService.update("ABC1234567", expected);
    assertThat(retval.getClass(), is(ClientScpEthnicity.class));
  }

  @Test
  public void clientScpEthnicityServiceUpdateThrowsExceptionWhenNotFound() throws Exception {
    try {
      ClientScpEthnicity clientScpEthnicityRequest =
          new ClientScpEthnicityResourceBuilder().build();

      when(clientScpEthnicityDao.update(any())).thenThrow(EntityNotFoundException.class);

      clientScpEthnicityService.update("ZZZZZZZZZZ", clientScpEthnicityRequest);
    } catch (Exception e) {
      assertEquals(e.getClass(), ServiceException.class);
    }
  }

  // create test
  @Test
  public void clientScpEthnicityServiceCreateReturnsPostedClass() throws Exception {
    String id = "AabekZX00F";
    ClientScpEthnicity clientScpEthnicityDomain = new ClientScpEthnicityResourceBuilder().build();

    gov.ca.cwds.data.persistence.cms.ClientScpEthnicity toCreate =
        new gov.ca.cwds.data.persistence.cms.ClientScpEthnicity(id, clientScpEthnicityDomain, "ABC",
            new Date());

    ClientScpEthnicity request = new ClientScpEthnicity(toCreate);
    when(clientScpEthnicityDao
        .create(any(gov.ca.cwds.data.persistence.cms.ClientScpEthnicity.class)))
            .thenReturn(toCreate);

    Response response = clientScpEthnicityService.create(request);
    assertThat(response.getClass(), is(ClientScpEthnicity.class));
  }

  @Test
  public void clientScpEthnicityServiceCreateReturnsNonNull() throws Exception {
    String id = "AabekZX00F";
    ClientScpEthnicity clientScpEthnicityDomain = new ClientScpEthnicityResourceBuilder().build();

    gov.ca.cwds.data.persistence.cms.ClientScpEthnicity toCreate =
        new gov.ca.cwds.data.persistence.cms.ClientScpEthnicity(id, clientScpEthnicityDomain, "ABC",
            new Date());

    ClientScpEthnicity request = new ClientScpEthnicity(toCreate);
    when(clientScpEthnicityDao
        .create(any(gov.ca.cwds.data.persistence.cms.ClientScpEthnicity.class)))
            .thenReturn(toCreate);

    ClientScpEthnicity PostedClientScpEthnicity = clientScpEthnicityService.create(request);
    assertThat(PostedClientScpEthnicity, is(notNullValue()));
  }

  @Test
  public void clientScpEthnicityServiceCreateReturnsCorrectEntity() throws Exception {
    String id = "AabekZX00F";
    ClientScpEthnicity clientScpEthnicityDomain = new ClientScpEthnicityResourceBuilder().build();

    gov.ca.cwds.data.persistence.cms.ClientScpEthnicity toCreate =
        new gov.ca.cwds.data.persistence.cms.ClientScpEthnicity(id, clientScpEthnicityDomain, "ABC",
            new Date());

    ClientScpEthnicity request = new ClientScpEthnicity(toCreate);
    when(clientScpEthnicityDao
        .create(any(gov.ca.cwds.data.persistence.cms.ClientScpEthnicity.class)))
            .thenReturn(toCreate);

    ClientScpEthnicity expected = new ClientScpEthnicity(toCreate);
    ClientScpEthnicity returned = clientScpEthnicityService.create(request);
    assertThat(returned, is(expected));
  }

  @Test
  public void clientScpEthnicityServiceCreateThrowsEntityExistsException() throws Exception {
    try {
      ClientScpEthnicity clientScpEthnicityDomain = new ClientScpEthnicityResourceBuilder().build();
      when(clientScpEthnicityDao.create(any())).thenThrow(EntityExistsException.class);

      clientScpEthnicityService.create(clientScpEthnicityDomain);
    } catch (Exception e) {
      assertEquals(e.getClass(), ServiceException.class);
    }
  }

  @Test
  public void clientScpEthnicityServiceCreateNullIDError() throws Exception {
    try {
      ClientScpEthnicity clientScpEthnicityDomain = new ClientScpEthnicityResourceBuilder().build();

      gov.ca.cwds.data.persistence.cms.ClientScpEthnicity toCreate =
          new gov.ca.cwds.data.persistence.cms.ClientScpEthnicity(null, clientScpEthnicityDomain,
              "ABC", new Date());

      when(clientScpEthnicityDao
          .create(any(gov.ca.cwds.data.persistence.cms.ClientScpEthnicity.class)))
              .thenReturn(toCreate);

      ClientScpEthnicity expected = new ClientScpEthnicity(toCreate);
    } catch (ServiceException e) {
      assertEquals("ClientScpEthnicity ID cannot be blank", e.getMessage());
    }

  }

  @Test
  public void clientScpEthnicityServiceCreateBlankIDError() throws Exception {
    try {
      ClientScpEthnicity clientScpEthnicityDomain = new ClientScpEthnicityResourceBuilder().build();

      gov.ca.cwds.data.persistence.cms.ClientScpEthnicity toCreate =
          new gov.ca.cwds.data.persistence.cms.ClientScpEthnicity("  ", clientScpEthnicityDomain,
              "ABC", new Date());

      when(clientScpEthnicityDao
          .create(any(gov.ca.cwds.data.persistence.cms.ClientScpEthnicity.class)))
              .thenReturn(toCreate);

      ClientScpEthnicity expected = new ClientScpEthnicity(toCreate);
    } catch (ServiceException e) {
      assertEquals("ClientScpEthnicity ID cannot be blank", e.getMessage());
    }

  }

  @Test
  public void screeningToReferralServiceTestRaceCode() throws Exception {
    LinkedHashSet<Short> raceCode = new LinkedHashSet<>();
    raceCode.add((short) 841);
    LinkedHashSet<Short> hispanicCode = new LinkedHashSet<>();
    hispanicCode.add((short) 3162);
    RaceAndEthnicity raceAndEthnicity = new RaceAndEthnicity(raceCode, "A", hispanicCode, "X", "A");
    Short primaryEthnicity =
        clientScpEthnicityService.getRaceCode(raceAndEthnicity, messageBuilder);
    assertThat(primaryEthnicity, is(equalTo((short) 841)));

  }

  @Test
  public void screeningToReferralTestHispanicRaceCode() throws Exception {
    LinkedHashSet<Short> raceCode = new LinkedHashSet<>();
    LinkedHashSet<Short> hispanicCode = new LinkedHashSet<>();
    hispanicCode.add((short) 3162);
    RaceAndEthnicity raceAndEthnicity = new RaceAndEthnicity(raceCode, "A", hispanicCode, "X", "A");
    Short primaryEthnicity =
        clientScpEthnicityService.getRaceCode(raceAndEthnicity, messageBuilder);
    assertThat(primaryEthnicity, is(equalTo((short) 3162)));

  }

  @Test
  public void screeningToReferralServiceTestEmptyRaceHispanicCodes() throws Exception {
    LinkedHashSet<Short> raceCode = new LinkedHashSet<>();
    LinkedHashSet<Short> hispanicCode = new LinkedHashSet<>();
    RaceAndEthnicity raceAndEthnicity = new RaceAndEthnicity(raceCode, "A", hispanicCode, "X", "A");
    Short primaryEthnicity =
        clientScpEthnicityService.getRaceCode(raceAndEthnicity, messageBuilder);
    assertThat(primaryEthnicity, is(equalTo((short) 0)));

  }

  @Test
  public void clientScpEthnicityServiceTestCreateSingleOtherEthnicity() throws Exception {
    LinkedHashSet<Short> raceCode = new LinkedHashSet<>();
    raceCode.add((short) 841);
    LinkedHashSet<Short> hispanicCode = new LinkedHashSet<>();
    hispanicCode.add((short) 3162);
    RaceAndEthnicity raceAndEthnicity = new RaceAndEthnicity(raceCode, "A", hispanicCode, "X", "A");
    Short primaryEthnicity =
        clientScpEthnicityService.getRaceCode(raceAndEthnicity, messageBuilder);
    assertThat(primaryEthnicity, is(equalTo((short) 841)));
    clientScpEthnicityService.createOtherEthnicity("ABC1234567", raceAndEthnicity);
    verify(clientScpEthnicityDao, times(1)).create(any());
  }

  @Test
  public void clientScpEthnicityServiceTestCreateMultipleOtherEthnicity() throws Exception {
    LinkedHashSet<Short> raceCode = new LinkedHashSet<>();
    raceCode.add((short) 841);
    LinkedHashSet<Short> hispanicCode = new LinkedHashSet<>();
    hispanicCode.add((short) 3162);
    hispanicCode.add((short) 3163);
    RaceAndEthnicity raceAndEthnicity = new RaceAndEthnicity(raceCode, "A", hispanicCode, "X", "A");
    Short primaryEthnicity =
        clientScpEthnicityService.getRaceCode(raceAndEthnicity, messageBuilder);
    assertThat(primaryEthnicity, is(equalTo((short) 841)));
    clientScpEthnicityService.createOtherEthnicity("ABC1234567", raceAndEthnicity);
    verify(clientScpEthnicityDao, times(2)).create(any());
  }

  @Test
  public void clientScpServiceTestFailsWhenUnableToDetermineCodeEmpty() throws Exception {
    LinkedHashSet<Short> raceCode = new LinkedHashSet<>();
    raceCode.add((short) 6351);
    LinkedHashSet<Short> hispanicCode = new LinkedHashSet<>();
    hispanicCode.add((short) 3162);
    hispanicCode.add((short) 3163);
    RaceAndEthnicity raceAndEthnicity = new RaceAndEthnicity(raceCode, "", hispanicCode, "X", "A");
    Short primaryEthnicity =
        clientScpEthnicityService.getRaceCode(raceAndEthnicity, messageBuilder);
    assertThat(primaryEthnicity, is(equalTo((short) 6351)));
    clientScpEthnicityService.createOtherEthnicity("ABC1234567", raceAndEthnicity);
    verify(messageBuilder, times(1)).addMessageAndLog(any(), any());
  }

  @Test
  public void clientScpServiceTestFailsWhenUnableToDetermineCodeNull() throws Exception {
    LinkedHashSet<Short> raceCode = new LinkedHashSet<>();
    raceCode.add((short) 6351);
    LinkedHashSet<Short> hispanicCode = new LinkedHashSet<>();
    hispanicCode.add((short) 3162);
    RaceAndEthnicity raceAndEthnicity =
        new RaceAndEthnicity(raceCode, null, hispanicCode, "X", "A");
    Short primaryEthnicity =
        clientScpEthnicityService.getRaceCode(raceAndEthnicity, messageBuilder);
    assertThat(primaryEthnicity, is(equalTo((short) 6351)));
    clientScpEthnicityService.createOtherEthnicity("ABC1234567", raceAndEthnicity);
    verify(messageBuilder, times(1)).addMessageAndLog(any(), any());
  }

  @Test
  public void clientScpServiceTestFailsWhenHispanicUnableToDetermineCodeEmpty() throws Exception {
    LinkedHashSet<Short> raceCode = new LinkedHashSet<>();
    raceCode.add((short) 841);
    LinkedHashSet<Short> hispanicCode = new LinkedHashSet<>();
    hispanicCode.add((short) 6351);
    RaceAndEthnicity raceAndEthnicity = new RaceAndEthnicity(raceCode, "A", hispanicCode, "X", "");
    Short primaryEthnicity =
        clientScpEthnicityService.getRaceCode(raceAndEthnicity, messageBuilder);
    assertThat(primaryEthnicity, is(equalTo((short) 841)));
    clientScpEthnicityService.createOtherEthnicity("ABC1234567", raceAndEthnicity);
    verify(messageBuilder, times(1)).addMessageAndLog(any(), any());
  }

  @Test
  public void clientScpServiceTestFailsWhenHispanicUnableToDetermineCodeNull() throws Exception {
    LinkedHashSet<Short> raceCode = new LinkedHashSet<>();
    raceCode.add((short) 841);
    LinkedHashSet<Short> hispanicCode = new LinkedHashSet<>();
    hispanicCode.add((short) 6351);
    RaceAndEthnicity raceAndEthnicity =
        new RaceAndEthnicity(raceCode, "A", hispanicCode, "X", null);
    Short primaryEthnicity =
        clientScpEthnicityService.getRaceCode(raceAndEthnicity, messageBuilder);
    assertThat(primaryEthnicity, is(equalTo((short) 841)));
    clientScpEthnicityService.createOtherEthnicity("ABC1234567", raceAndEthnicity);
    verify(messageBuilder, times(1)).addMessageAndLog(any(), any());
  }

  @Test
  public void TestFailsWhenRaceAndHispanicUnableToDetermineIsEmpty() throws Exception {
    LinkedHashSet<Short> raceCode = new LinkedHashSet<>();
    raceCode.add((short) 6351);
    LinkedHashSet<Short> hispanicCode = new LinkedHashSet<>();
    hispanicCode.add((short) 6351);
    RaceAndEthnicity raceAndEthnicity = new RaceAndEthnicity(raceCode, "", hispanicCode, "X", "");
    Short primaryEthnicity =
        clientScpEthnicityService.getRaceCode(raceAndEthnicity, messageBuilder);
    assertThat(primaryEthnicity, is(equalTo((short) 6351)));
    clientScpEthnicityService.createOtherEthnicity("ABC1234567", raceAndEthnicity);
    verify(messageBuilder, times(2)).addMessageAndLog(any(), any());
  }

}
