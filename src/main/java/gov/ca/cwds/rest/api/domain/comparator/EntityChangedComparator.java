package gov.ca.cwds.rest.api.domain.comparator;

import gov.ca.cwds.rest.api.domain.DomainChef;
import gov.ca.cwds.rest.api.domain.Participant;
import gov.ca.cwds.rest.api.domain.cms.Client;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class EntityChangedComparator{

  /**
   * Compares the last update time stamp of the participant and client. This comparator does NOT use
   * the standard interface since we are comparing to different objects, and thus is not safe(compatible)
   * for use in equals and hash methods. Both participant and client are differnent representations
   * of the same object at different parts of application flow.
   *
   * Joda time is used to handle
   *
   * @param participant The participant to compare last updated time with
   * @param client the Client to compare last updated time with
   * @return returns true if the date is equal to the second resolution
   */
  public boolean compare(Participant participant, Client client){
    DateTimeFormatter formatter = DateTimeFormat.forPattern(deleteAfterSeconds(DomainChef.TIMESTAMP_FORMAT));;
    String clientLastUpdated = client.getLastUpdatedTime();
    DateTime dbDate = formatter.parseDateTime(deleteAfterSeconds(clientLastUpdated));

    DateTimeFormatter formatter2 = DateTimeFormat.forPattern(deleteAfterSeconds(DomainChef.TIMESTAMP_STRICT_FORMAT));
    String participantLastUpdated = participant.getLegacyDescriptor().getLastUpdated();
    DateTime incommingDate = formatter2.parseDateTime(deleteAfterSeconds(participantLastUpdated));

    return dbDate.isEqual(incommingDate);

  }

  private String deleteAfterSeconds(String timestamp){
    int millisecondsLocation = timestamp.lastIndexOf(".");
    return timestamp.substring(0,millisecondsLocation);

  }

//  private String zeroOutStrictTimestampMilliseconds(String timestamp){
//    return timestamp;
//  }
}
