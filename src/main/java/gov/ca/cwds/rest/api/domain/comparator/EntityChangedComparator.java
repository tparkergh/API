package gov.ca.cwds.rest.api.domain.comparator;

import gov.ca.cwds.rest.api.domain.Participant;
import gov.ca.cwds.rest.api.domain.cms.Client;
import org.joda.time.DateTime;

public class EntityChangedComparator {

  /**
   * Compares the last update time stamp of the participant and client. This comparator does NOT use
   * the standard interface since we are comparing to different objects, and thus is not
   * safe(compatible) for use in equals and hash methods. Both participant and client are different
   * representations of the same object at different parts of application flow.
   *
   * Joda time is used to handle
   *
   * @param participant The participant to compare last updated time with
   * @param client the Client to compare last updated time with
   * @return returns true if the date is equal to the second resolution
   */
  public boolean compare(Participant participant, Client client) {
    DateTime particpantDate = trimMilliseconds(participant.getLegacyDescriptor().getLastUpdated());
    DateTime clientDate = trimMilliseconds(client.getLastUpdatedTime());
    return particpantDate.getMillis() == clientDate.getMillis();
  }
  public static DateTime trimMilliseconds(DateTime dt) {
    return new DateTime(dt.getYear(), dt.getMonthOfYear(), dt.getDayOfMonth(), dt.getHourOfDay(), dt.getMinuteOfHour(), dt.getSecondOfMinute(), 0, dt.getZone());
  }
}
