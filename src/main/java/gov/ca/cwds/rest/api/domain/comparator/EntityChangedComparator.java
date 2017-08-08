package gov.ca.cwds.rest.api.domain.comparator;

import gov.ca.cwds.rest.api.domain.Participant;
import gov.ca.cwds.rest.api.domain.cms.Client;
import org.apache.logging.log4j.core.layout.StringBuilderEncoder;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EntityChangedComparator {
  private static final Logger LOGGER = LoggerFactory.getLogger(EntityChangedComparator.class);

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
    boolean isSameDate = particpantDate.getMillis() == clientDate.getMillis();
    if (!isSameDate){
      logDateNotEqual(particpantDate, clientDate);
    }
    return isSameDate;
  }
  private DateTime trimMilliseconds(DateTime dt) {
    return new DateTime(dt.getYear(), dt.getMonthOfYear(), dt.getDayOfMonth(), dt.getHourOfDay(), dt.getMinuteOfHour(), dt.getSecondOfMinute(), 0, dt.getZone());
  }

  private void logDateNotEqual(DateTime particpantDate, DateTime clientDate) {
    StringBuilder builder = new StringBuilder();
    builder.append("Date comparison failed. ParticipantDate: ");
    builder.append(particpantDate);
    builder.append(" ");
    builder.append( particpantDate.getZone());
    builder.append(" ");
    builder.append(particpantDate.getMillis());
    builder.append(" clientDate: ");
    builder.append(clientDate);
    builder.append(" ");
    builder.append( clientDate.getZone());
    builder.append(" ");
    builder.append(clientDate.getMillis());
    LOGGER.warn(builder.toString());
  }
}
