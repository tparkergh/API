package gov.ca.cwds.rest.api.domain.comparator;

import static org.junit.Assert.*;

import gov.ca.cwds.fixture.ClientResourceBuilder;
import gov.ca.cwds.fixture.ParticipantResourceBuilder;
import gov.ca.cwds.rest.api.domain.LegacyDescriptor;
import gov.ca.cwds.rest.api.domain.Participant;
import gov.ca.cwds.rest.api.domain.cms.Client;
import org.junit.Before;
import org.junit.Test;

public class EntityChangedComparatorTest {

//  public static final String CLIENT_DATE = "2016-11-25-14.32.23.123";
//  public static final String PARTICIPANT_DATE = "2016-11-25T14:32:23.123-0700";
  EntityChangedComparator comparator;
  Participant participant;
  Client client;
  LegacyDescriptor legacyDescriptor;

  @Before
  public void setup(){
    comparator = new EntityChangedComparator();

  }

  @Test
  public void shouldCompareEqualDatesAsTrue(){
    participant = createParticipant("2016-11-25T14:32:23.123-0700");
    client = createClient( "2016-11-25-14.32.23.123");

    assertTrue(comparator.compare(participant, client));
  }

  @Test
  public void shouldIgnoreMilliseconds(){
    participant = createParticipant("2016-11-25T14:32:23.thisisignored");
    client = createClient( "2016-11-25-14.32.23.notreleventvalue");

    assertTrue(comparator.compare(participant, client));
  }

  @Test
  public void shouldNotEvaluateStringsWithDifferentSecondsAsEqual(){
    participant = createParticipant("2016-11-25T14:32:00.123-0700");
    client = createClient( "2016-11-25-14.32.59.123");

    assertFalse(comparator.compare(participant, client));
  }

  @Test
  public void shouldNotEvaluateStringsWithDifferentDayAsEqual(){
    participant = createParticipant("2016-11-02T14:32:00.123-0700");
    client = createClient( "2016-11-01-14.32.00.123");

    assertFalse(comparator.compare(participant, client));
  }

  @Test(expected=IllegalArgumentException.class)
  public void shouldThrowExceptionIfDateCantBeParsed(){
    participant = createParticipant("Bad.Date");
    client = createClient( "2016-11-01-14.32.00.123");

    assertFalse(comparator.compare(participant, client));
  }

  @Test(expected=StringIndexOutOfBoundsException.class)
  public void shouldThrowIndexOutOfBoundsExceptionIfNoMillisecondsArefound(){
    participant = createParticipant("BadDate");
    client = createClient( "2016-11-01-14.32.00.123");

    assertFalse(comparator.compare(participant, client));
  }

  private Client createClient(String clientDate){
    return new ClientResourceBuilder().setLastUpdateTime(clientDate).build();
  }

  private Participant createParticipant(String participantDate){
    legacyDescriptor = new LegacyDescriptor("DFGHJYTR", "", participantDate, "", "");
    return new ParticipantResourceBuilder().setLegacyDescriptor(legacyDescriptor).createParticipant();
  }
}