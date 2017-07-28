package gov.ca.cwds.data.persistence.ns;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.HashSet;
import org.junit.Test;

public class ScreeningTest {

  @Test
  public void shouldAddParticipantWhenNotEmpty(){
    HashSet participants = new HashSet();
    Participant participant = new Participant();
    participants.add(participant);

    Screening screening = new Screening("reference", new Date(), "Sonoma", new Date(), "Home", "Phone", "Fred", "Immdiate", "Descicion", new Date(), "some Narative", new Address(), participants);
    assertEquals(screening.getParticipants(), participants);
  }

  @Test
  public void shouldNotChangeParticipantsWhenAddingEmptyHashSet(){
    HashSet participants = new HashSet();

    Screening screening = new Screening("reference", new Date(), "Sonoma", new Date(), "Home", "Phone", "Fred", "Immdiate", "Descicion", new Date(), "some Narative", new Address(), participants);
    assertEquals(0, screening.getParticipants().size());
  }
}