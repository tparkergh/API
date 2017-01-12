package gov.ca.cwds.rest.validation;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smartystreets.api.us_street.Analysis;
import com.smartystreets.api.us_street.Candidate;
import com.smartystreets.api.us_street.Components;
import com.smartystreets.api.us_street.Metadata;

import gov.ca.cwds.data.validation.SmartyStreetsDao;
import gov.ca.cwds.rest.api.domain.ValidatedAddress;
import gov.ca.cwds.rest.core.Api;
import io.dropwizard.jackson.Jackson;

public class SmartyStreetTest {
  private static final String ROOT_RESOURCE = "/" + Api.RESOURCE_ADDRESS_VALIDATION + "/";

  private static final SmartyStreet spySmartyStreet = spy(new SmartyStreet());


  private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

  private SmartyStreetsDao smartyStreetsDao = mock(SmartyStreetsDao.class);


  @Before
  public void setup() {}


  @Test
  public void successfulWithEmptyCandidate() throws Exception {
    ArrayList<Candidate> empty = new ArrayList<Candidate>();
    String a = "a";
    String b = "b";
    String c = "c";
    Integer z = 0;
    Mockito.doReturn(empty).when(spySmartyStreet).getSmartyStreetsCandidates(a, b, c, z);
    ValidatedAddress[] actual = spySmartyStreet.usStreetSingleAddress(a, b, c, z);
    ValidatedAddress[] expected = new ValidatedAddress[1];
    expected[0] = new ValidatedAddress(null, null, null, null, null, null, false);
    assertThat(actual[0], is(equalTo(expected[0])));
  }

  /*
   * Successful Tests
   */
  @Test
  public void successfulWithDpvY() throws Exception {
    ArrayList<Candidate> dpvY = new ArrayList<Candidate>();
    Candidate mockcandidate1 = mock(Candidate.class);
    Analysis mockanalysis1 = mock(Analysis.class);
    Components mockancomponents1 = mock(Components.class);
    Metadata mockanmetadata1 = mock(Metadata.class);
    Mockito.when(mockcandidate1.getDeliveryLine1()).thenReturn("106 Big Valley Rd");
    Mockito.when(mockcandidate1.getComponents()).thenReturn(mockancomponents1);
    Mockito.when(mockancomponents1.getCityName()).thenReturn("Folsom");
    Mockito.when(mockancomponents1.getState()).thenReturn("CA");
    Mockito.when(mockancomponents1.getZipCode()).thenReturn("95630");
    Mockito.when(mockcandidate1.getMetadata()).thenReturn(mockanmetadata1);
    Mockito.when(mockanmetadata1.getLongitude()).thenReturn(-121.13233);
    Mockito.when(mockanmetadata1.getLatitude()).thenReturn(38.64028);
    Mockito.when(mockcandidate1.getAnalysis()).thenReturn(mockanalysis1);
    Mockito.when(mockanalysis1.getDpvMatchCode()).thenReturn("Y");
    dpvY.add(mockcandidate1);
    String a = "106 Big Valley Rd";
    String b = "folsom";
    String c = "ca";
    Integer z = 0;
    Mockito.doReturn(dpvY).when(spySmartyStreet).getSmartyStreetsCandidates(a, b, c, z);
    ValidatedAddress[] actual = spySmartyStreet.usStreetSingleAddress(a, b, c, z);
    ValidatedAddress[] expected = new ValidatedAddress[1];
    expected[0] = new ValidatedAddress("106 Big Valley Rd", "Folsom", "CA", 95630, -121.13233,
        38.64028, true);
    assertThat(actual[0], is(equalTo(expected[0])));
  }


}
