package gov.ca.cwds.rest.business.reminders;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.Dao;
import gov.ca.cwds.data.cms.ClientDao;
import gov.ca.cwds.data.cms.ReferralDao;
import gov.ca.cwds.data.persistence.cms.Client;
import gov.ca.cwds.data.persistence.cms.Referral;
import gov.ca.cwds.rest.api.domain.Participant;
import gov.ca.cwds.rest.api.domain.PostedScreeningToReferral;
import gov.ca.cwds.rest.api.domain.ScreeningToReferral;
import gov.ca.cwds.rest.services.cms.TickleService;

/**
 * @author CWDS API Team
 *
 */
public class R04631ReferralInvestigationContactDue {

  private static final Logger LOGGER =
      LoggerFactory.getLogger(R04631ReferralInvestigationContactDue.class);

  final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
  final DateFormat timeFormat = new SimpleDateFormat("HH:MM:SS");

  private static final String PERPETRATOR_ROLE = "Perpetrator";
  private static final String VICTIM_ROLE = "Victim";
  private static final String REFERRAL_REFERRALCLIENT = "RL";
  private static final short REFERRAL_INVESTIGATION_CONTACT_DUE = (short) 2058;

  private ClientDao clientDao;
  private ReferralDao referralDao;
  private TickleService tickleService;

  /**
   * @param clientDao - The {@link Dao} handling {@link gov.ca.cwds.data.persistence.cms.Client}
   *        objects
   * @param referralDao - The {@link Dao} handling {@link gov.ca.cwds.data.persistence.cms.Referral}
   *        objects
   * @param tickleService - tickleService
   */
  @Inject
  public R04631ReferralInvestigationContactDue(ClientDao clientDao, ReferralDao referralDao,
      TickleService tickleService) {
    super();
    this.clientDao = clientDao;
    this.referralDao = referralDao;
    this.tickleService = tickleService;
  }

  /**
   * <blockquote>
   * 
   * <pre>
   * BUSINESS RULE: "R - 04631" - TICKLE for Referral Investigation Contact Due reminder
   * 
   * If the Client age is less then 19 Years create a reminder for the investigation contact due and teh dueDate is updated according 
   * to the referralResponseType. Now the referralResponseType is define to a default value Zero. So updating the dueDate to 10 days. 
   * </blockquote>
   * </pre>
   * 
   * @param postedScreeningToReferral
   */
  public void referralInvestigationContactDue(PostedScreeningToReferral postedScreeningToReferral) {
    ScreeningToReferral screeningToReferral = postedScreeningToReferral;
    Set<Participant> participants = screeningToReferral.getParticipants();
    Referral referral = referralDao.find(postedScreeningToReferral.getReferralId());
    for (Participant participant : participants) {
      if ((participant.getRoles().contains(VICTIM_ROLE)
          || participant.getRoles().contains(PERPETRATOR_ROLE))
          && participant.getDateOfBirth() != null) {
        Client client = clientDao.find(participant.getLegacyId());
        String dateOfBirth = participant.getDateOfBirth();
        try {
          /*
           * check for the client age is below 19 or not by using the Java Calender
           */
          Date dob = dateFormat.parse(dateOfBirth);
          Calendar present = Calendar.getInstance();
          Calendar past = Calendar.getInstance();
          past.setTime(dob);
          int years = 0;
          while (past.before(present)) {
            past.add(Calendar.YEAR, 1);
            if (past.before(present)) {
              years++;
            }
          }

          if (years < 19 && referral.getReferralResponseType() == 0) {
            /*
             * duedate is updated with adding 10 Days to the referral receivedDate. As the
             * referralResponseType is defined to a default Zero.
             */
            Calendar dueDate = Calendar.getInstance();
            dueDate.setTime(referral.getReceivedDate());
            dueDate.add(Calendar.DATE, 10);
            gov.ca.cwds.rest.api.domain.cms.Tickle tickle =
                new gov.ca.cwds.rest.api.domain.cms.Tickle(referral.getId(),
                    REFERRAL_REFERRALCLIENT, client.getId(), null,
                    dateFormat.format(dueDate.getTime()), referral.getScreenerNoteText(),
                    REFERRAL_INVESTIGATION_CONTACT_DUE);
            tickleService.create(tickle);
            LOGGER.info("referralInvestigationContactDue reminder is created");
          }
        } catch (ParseException e) {
          LOGGER.error("Error While parsing the dateOfBirth");
        }
      }
    }
  }

}
