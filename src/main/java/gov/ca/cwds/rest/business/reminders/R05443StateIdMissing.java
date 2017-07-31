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
import gov.ca.cwds.rest.services.ServiceException;
import gov.ca.cwds.rest.services.cms.TickleService;

/**
 * 
 * <p>
 * BUSINESS RULE: "R - 05443" - TICKLE for State ID Missing
 * 
 * If the Client age is less then 26 Years, doesn't have state_Id and the client is in referral then
 * this reminder is created.
 * </p>
 * 
 * @author CWDS API Team
 *
 */
public class R05443StateIdMissing {

  private static final Logger LOGGER = LoggerFactory.getLogger(R05443StateIdMissing.class);

  final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
  final DateFormat timeFormat = new SimpleDateFormat("HH:MM:SS");

  private static final String PERPETRATOR_ROLE = "Perpetrator";
  private static final String VICTIM_ROLE = "Victim";
  private static final String REFERRAL_REFERRALCLIENT = "RL";
  private static final String DEFAULT_TRUE_INDICATOR = "Y";
  private static final short STATE_ID_MISSING = (short) 2062;

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
  public R05443StateIdMissing(ClientDao clientDao, ReferralDao referralDao,
      TickleService tickleService) {
    super();
    this.clientDao = clientDao;
    this.referralDao = referralDao;
    this.tickleService = tickleService;
  }

  /**
   * @param postedScreeningToReferral - postedScreeningToReferral
   * @throws ServiceException - ServiceException
   */
  public void stateIdMissing(PostedScreeningToReferral postedScreeningToReferral) {
    ScreeningToReferral screeningToReferral = postedScreeningToReferral;
    Set<Participant> participants = screeningToReferral.getParticipants();
    Referral referral = referralDao.find(postedScreeningToReferral.getReferralId());
    for (Participant participant : participants) {
      if ((participant.getRoles().contains(VICTIM_ROLE)
          || participant.getRoles().contains(PERPETRATOR_ROLE))
          && participant.getDateOfBirth() != null) {
        Client client = clientDao.find(participant.getLegacyId());
        String dateOfBirth = participant.getDateOfBirth();

        int years = ReminderHelper.checkForAgeDiffernce(dateOfBirth);

        boolean clientCheck = client.getEstimatedDobCode() != null
            && client.getEstimatedDobCode().equals(DEFAULT_TRUE_INDICATOR);

        if (years < 26 || clientCheck && referral.getClosureDate() == null) {
          /*
           * duedate is updated with adding 30 days to the client creationDate
           */
          Calendar dueDate = Calendar.getInstance();
          dueDate.setTime(client.getCreationDate());
          dueDate.add(Calendar.DATE, 30);

          gov.ca.cwds.rest.api.domain.cms.Tickle tickle =
              new gov.ca.cwds.rest.api.domain.cms.Tickle(referral.getId(), REFERRAL_REFERRALCLIENT,
                  client.getId(), null, dateFormat.format(dueDate.getTime()),
                  referral.getScreenerNoteText(), STATE_ID_MISSING);
          tickleService.create(tickle);
          LOGGER.info("stateIdMissing reminder is created");

        }
      }
    }
  }
}


