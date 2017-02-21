package gov.ca.cwds.rest.validation;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

import java.util.ArrayList;

import org.junit.Test;
import org.mockito.Mockito;

import com.smartystreets.api.us_street.Analysis;
import com.smartystreets.api.us_street.Candidate;
import com.smartystreets.api.us_street.Components;
import com.smartystreets.api.us_street.Metadata;

import gov.ca.cwds.rest.api.domain.ValidatedAddress;

/**
 * @author CWDS API Team
 *
 */
@SuppressWarnings("javadoc")
public class SmartyStreetTest {

  private static final SmartyStreet spySmartyStreet = spy(new SmartyStreet());


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


  @Test
  public void successfulWithMultipleCandidates() throws Exception {
    ArrayList<Candidate> multiCandidates = new ArrayList<Candidate>();
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
    multiCandidates.add(mockcandidate1);

    Candidate mockcandidate2 = mock(Candidate.class);
    Analysis mockanalysis2 = mock(Analysis.class);
    Components mockancomponents2 = mock(Components.class);
    Metadata mockanmetadata2 = mock(Metadata.class);
    Mockito.when(mockcandidate2.getDeliveryLine1()).thenReturn("106 Big Valley Ct");
    Mockito.when(mockcandidate2.getComponents()).thenReturn(mockancomponents2);
    Mockito.when(mockancomponents2.getCityName()).thenReturn("Folsom");
    Mockito.when(mockancomponents2.getState()).thenReturn("CA");
    Mockito.when(mockancomponents2.getZipCode()).thenReturn("95630");
    Mockito.when(mockcandidate2.getMetadata()).thenReturn(mockanmetadata2);
    Mockito.when(mockanmetadata2.getLongitude()).thenReturn(-121.13232);
    Mockito.when(mockanmetadata2.getLatitude()).thenReturn(38.68207);
    Mockito.when(mockcandidate2.getAnalysis()).thenReturn(mockanalysis2);
    Mockito.when(mockanalysis2.getDpvMatchCode()).thenReturn("S");
    multiCandidates.add(mockcandidate2);

    String a = "106 Big Valley";
    String b = "folsom";
    String c = "ca";
    Integer z = 0;
    Mockito.doReturn(multiCandidates).when(spySmartyStreet).getSmartyStreetsCandidates(a, b, c, z);
    ValidatedAddress[] actual = spySmartyStreet.usStreetSingleAddress(a, b, c, z);
    ValidatedAddress[] expected = new ValidatedAddress[2];
    expected[0] = new ValidatedAddress("106 Big Valley Rd", "Folsom", "CA", 95630, -121.13233,
        38.64028, true);
    expected[1] = new ValidatedAddress("106 Big Valley Ct", "Folsom", "CA", 95630, -121.13232,
        38.68207, true);
    assertThat(actual[0], is(equalTo(expected[0])));
    assertThat(actual[1], is(equalTo(expected[1])));
  }


}
