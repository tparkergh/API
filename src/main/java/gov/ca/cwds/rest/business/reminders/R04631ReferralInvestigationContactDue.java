package gov.ca.cwds.rest.business.reminders;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
import gov.ca.cwds.rest.validation.ParticipantValidator;

/**
 * 
 * <p>
 * BUSINESS RULE: "R - 04631" - TICKLE for Referral Investigation Contact Due reminder
 * 
 * If the Client age is less then 19 Years create a reminder for the investigation contact due and
 * the dueDate is updated according to the referralResponseType.
 * <p>
 * 
 * @author CWDS API Team
 *
 */
public class R04631ReferralInvestigationContactDue {

  private static final Logger LOGGER =
      LoggerFactory.getLogger(R04631ReferralInvestigationContactDue.class);

  final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
  final DateFormat timeFormat = new SimpleDateFormat("HH:MM:SS");

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
   * 
   * @param postedScreeningToReferral - postedScreeningToReferral
   */
  public void referralInvestigationContactDue(PostedScreeningToReferral postedScreeningToReferral) {
    ScreeningToReferral screeningToReferral = postedScreeningToReferral;
    Set<Participant> participants = screeningToReferral.getParticipants();
    Referral referral = referralDao.find(postedScreeningToReferral.getReferralId());
    for (Participant participant : participants) {
      if ((ParticipantValidator.hasVictimRole(participant)
          || ParticipantValidator.isPerpetrator(participant))
          && participant.getDateOfBirth() != null) {
        Client client = clientDao.find(participant.getLegacyId());
        String dateOfBirth = participant.getDateOfBirth();

        int years = ReminderHelper.checkForAgeDiffernce(dateOfBirth);

        if (years < 19
            && ReminderHelper.getMapTheDueDate().get(referral.getReferralResponseType()) != null) {
          /*
           * duedate is updated based on the referralResponseType.
           */
          Calendar dueDate = Calendar.getInstance();
          dueDate.setTime(referral.getReceivedDate());
          dueDate.add(Calendar.DATE,
              ReminderHelper.getMapTheDueDate().get(referral.getReferralResponseType()));

          createTickle(referral, client, dueDate);
        }
      }
    }
  }

  private void createTickle(Referral referral, Client client, Calendar dueDate) {
    gov.ca.cwds.rest.api.domain.cms.Tickle tickle =
        new gov.ca.cwds.rest.api.domain.cms.Tickle(referral.getId(), REFERRAL_REFERRALCLIENT,
            client.getId(), null, dateFormat.format(dueDate.getTime()),
            referral.getScreenerNoteText(), REFERRAL_INVESTIGATION_CONTACT_DUE);
    tickleService.create(tickle);
    LOGGER.info("referralInvestigationContactDue reminder is created");
  }

}
