package gov.ca.cwds.rest.services;

import static org.junit.Assert.*;

import gov.ca.cwds.fixture.ParticipantResourceBuilder;
import gov.ca.cwds.rest.api.domain.Participant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import org.eclipse.jetty.io.ssl.ALPNProcessor.Client;
import org.junit.Before;
import org.junit.Test;

public class ClientParticipantsTest {
  Set<Participant> participants;
  HashMap<Long, String> victimIds;
  HashMap<Long, String> perpetratorIds;

  @Before
  public void setup(){
     participants = new HashSet<>();
     victimIds = new HashMap<>();
     perpetratorIds = new HashMap<>();

  }

  @Test
  public void shouldConstructWithInitializedCollectionsWhenDefaultConstructorIsUsed(){
    ClientParticipants participants = new ClientParticipants();
    assertNotNull(participants.getParticipants());
    assertNotNull(participants.getVictimIds());
    assertNotNull(participants.getPerpetratorIds());
  }

  @Test
  public void shouldConstructWithPassedInValues(){

    ClientParticipants clientParticipants = new ClientParticipants(participants, victimIds,
        perpetratorIds);
    assertEquals(participants, clientParticipants.getParticipants());
    assertEquals(victimIds, clientParticipants.getVictimIds());
    assertEquals(perpetratorIds, clientParticipants.getPerpetratorIds());
  }

  @Test
  public void shouldCreateVictimIdCollectionWhenAddingToNullCollection(){
    ClientParticipants clientParticipants = new ClientParticipants( null, null, null);

    clientParticipants.addVictimIds(1L,"asdf");
    assertEquals("Expected one item in map", 1, clientParticipants.getVictimIds().size());
  }

  @Test
  public void shouldReplaceVictimCollectionWhenUsingSetter(){
    HashMap victimIds = new HashMap();
    ClientParticipants clientParticipants = new ClientParticipants( null, new HashMap<>(), null);

    clientParticipants.setVictimIds(victimIds);
    assertEquals("Expected victimIds to be the one passed to setter", victimIds, clientParticipants
        .getVictimIds());
  }

  @Test
  public void shouldCreatePertetratorIdCollectionWhenAddingToNullCollection(){
    ClientParticipants clientParticipants = new ClientParticipants( null, null, null);

    clientParticipants.addPerpetratorIds(1L,"asdf");
    assertEquals("Expected one item in map", 1, clientParticipants.getPerpetratorIds().size());
  }

  @Test
  public void shouldReplacePerpetratorsCollectionWhenUsingSetter(){
    HashMap perpetratorIds = new HashMap();
    ClientParticipants clientParticipants = new ClientParticipants( null, null, new HashMap<>() );

    clientParticipants.setPerpetratorIds(perpetratorIds);
    assertEquals("Expected perpetratorIds to be the one passed to setter", perpetratorIds, clientParticipants
        .getPerpetratorIds());
  }

  @Test
  public void shouldAddParticipantsToCollectionWhenItIsNull(){
    ClientParticipants clientParticipants = new ClientParticipants( null, null, null);
    clientParticipants.setParticipants(participants);
    assertEquals("Expected participants to be set to value passed to setter", participants,
        clientParticipants.getParticipants());
  }

  @Test
  public void shouldAddParticipantToParticipantAndVictimIdCollectionWhenAddingAVictim(){
    String legacyId = "ABCXYZ";
    Participant participant = new ParticipantResourceBuilder().setLegacyId(legacyId)
        .createVictimParticipant();
    ClientParticipants clientParticipants = new ClientParticipants( null, null, null);

    clientParticipants.addParticipant(participant);
    assertEquals("", 1, clientParticipants.getParticipants().size());

    Collection groupOfParticipants = clientParticipants.getParticipants();
    ArrayList participants = new ArrayList(groupOfParticipants);

    assertEquals("Expected the participant passed in to be retrieveable", participant, participants
        .get(0));
    assertEquals("Expected victimId to have been populated",participant.getLegacyId(), clientParticipants
        .getVictimIds().get(participant.getId()));
    assertNull("Expected Perpetrator to have not been populated", clientParticipants
        .getPerpetratorIds() );
  }

  @Test
  public void shouldAddParticipantToParticipantAndVictimIdCollectionWhenAddingAPerpetrator(){
    String legacyId = "ABCXYZ";
    Participant participant = new ParticipantResourceBuilder().setLegacyId(legacyId)
        .createPerpParticipant();
    ClientParticipants clientParticipants = new ClientParticipants( null, null, null);

    clientParticipants.addParticipant(participant);
    assertEquals("", 1, clientParticipants.getParticipants().size());

    Collection groupOfParticipants = clientParticipants.getParticipants();
    ArrayList participants = new ArrayList(groupOfParticipants);

    assertEquals("Expected the participant passed in to be retrieveable", participant, participants
        .get(0));
    assertEquals("Expected Perpetrator to have been populated",participant.getLegacyId(),
        clientParticipants .getPerpetratorIds().get(participant.getId()));
    assertNull("Expected VictimId to have not been populated", clientParticipants
        .getVictimIds() );

  }
}