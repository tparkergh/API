package gov.ca.cwds.rest.services;

import gov.ca.cwds.rest.api.domain.Participant;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class ClientParticipants {
  Set<Participant> participants;
  HashMap<Long, String> victimIds;
  HashMap<Long, String> perpetratorIds;


  public ClientParticipants() {
    this.participants = new HashSet<>();
    this.victimIds = new HashMap<>();
    this.perpetratorIds = new HashMap<>();
  }

  public ClientParticipants(
      Set<Participant> participants,
      HashMap<Long, String> victimIds,
      HashMap<Long, String> perpetratorIds) {
    this.participants = participants;
    this.victimIds = victimIds;
    this.perpetratorIds = perpetratorIds;
  }

  public Set<Participant> getParticipants() {
    return participants;
  }

  public void setParticipants(
      Set<Participant> participants) {
    this.participants = participants;
  }
  public void addParticipant( Participant participant) {
    if (participants == null){
      participants = new HashSet<Participant>();
    }
    addToClientIds(participant);
    this.participants.add(participant);
  }

  public HashMap<Long, String> getVictimIds() {
    return victimIds;
  }

  public void setVictimIds(HashMap<Long, String> victimIds) {
    this.victimIds = victimIds;
  }

  public void addVictimIds(Long id, String legacyId) {
    if (victimIds == null){
      victimIds = new HashMap<Long, String>();
    }
    this.victimIds.put(id, legacyId);
  }

  public HashMap<Long, String> getPerpetratorIds() {
    return perpetratorIds;
  }

  public void setPerpetratorIds(
      HashMap<Long, String> perpetratorIds) {
    this.perpetratorIds = perpetratorIds;
  }

  public void addPerpetratorIds( Long id, String legacyId) {
    if (perpetratorIds == null){
      perpetratorIds = new HashMap<Long, String>();
    }
    this.perpetratorIds.put(id, legacyId);
  }

  public void addParticipants(Set<Participant> participants) {
    for (Participant participant : participants) {
      addToClientIds(participant);
      addParticipant(participant);
    }
  }

  private void addToClientIds(Participant participant) {
    if (participant.isVictim()) {
      addVictimIds(participant.getId(), participant.getLegacyId());
    } else if (participant.isPerpetrator()) {
      addPerpetratorIds(participant.getId(), participant.getLegacyId());
    }
  }

}
