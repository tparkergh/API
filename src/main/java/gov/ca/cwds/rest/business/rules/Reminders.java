package gov.ca.cwds.rest.business.rules;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.cms.ChildClientDao;
import gov.ca.cwds.data.cms.ClientDao;
import gov.ca.cwds.data.cms.ReferralDao;
import gov.ca.cwds.data.cms.TickleDao;
import gov.ca.cwds.data.persistence.cms.Client;
import gov.ca.cwds.data.persistence.cms.Referral;
import gov.ca.cwds.rest.api.domain.Participant;
import gov.ca.cwds.rest.api.domain.PostedScreeningToReferral;
import gov.ca.cwds.rest.api.domain.ScreeningToReferral;
import gov.ca.cwds.rest.services.ServiceException;
import gov.ca.cwds.rest.services.cms.TickleService;

/**
 * @Business layer object to work on referrals for creating the reminders
 * 
 * @author CWDS API Team
 */
public class Reminders {

  private static final Logger LOGGER = LoggerFactory.getLogger(Reminders.class);

  private static final String ESTIMATED_DOB_CODE = "Y";
  private static final String REFERRAL_REFERRALCLIENT = "RL";

  private TickleDao tickleDao;
  private ChildClientDao childClientDao;
  private ClientDao clientDao;
  private ReferralDao referralDao;
  private TickleService tickleService;

  /**
   * @param tickleDao
   * @param childClientDao
   * @param clientDao
   * @param referralDao
   * @param tickleService
   * @param screeningToReferralService
   */
  @Inject
  public Reminders(TickleDao tickleDao, ChildClientDao childClientDao, ClientDao clientDao,
      ReferralDao referralDao, TickleService tickleService) {
    super();
    this.tickleDao = tickleDao;
    this.childClientDao = childClientDao;
    this.clientDao = clientDao;
    this.referralDao = referralDao;
    this.tickleService = tickleService;
  }

  /**
   * @param postedScreeningToReferral
   */
  public void createTickle(PostedScreeningToReferral postedScreeningToReferral) {

    ScreeningToReferral screeningToReferral = postedScreeningToReferral;
    Set<Participant> participants = screeningToReferral.getParticipants();
    Referral referral = referralDao.find(postedScreeningToReferral.getReferralId());
    for (Participant participant : participants) {
      if ((participant.getRoles().contains("Victim")
          || participant.getRoles().contains("Perpetrator"))
          && participant.getDateOfBirth() != null) {
        Client client = clientDao.find(participant.getLegacyId());
        String dateOfBirth = participant.getDateOfBirth();
        try {
          Date dob = new SimpleDateFormat("yyyy-MM-dd").parse(dateOfBirth);
          Date currentDate = new Date();
          int diffInYears =
              (int) ((currentDate.getTime() - dob.getTime()) / (1000 * 60 * 60 * 24 * 365));
          if (diffInYears < 26 || (client.getEstimatedDobCode() != null
              && client.getEstimatedDobCode().equals(ESTIMATED_DOB_CODE))
              && client.getDriverLicenseNumber().isEmpty()) {
            if (referral.getClosureDate() == null) {

              gov.ca.cwds.rest.api.domain.cms.Tickle tickle =
                  new gov.ca.cwds.rest.api.domain.cms.Tickle(referral.getId(),
                      REFERRAL_REFERRALCLIENT, client.getId(), null,
                      new SimpleDateFormat("yyyy-MM-dd").format(new Date(
                          client.getCreationDate().getTime() + (1000 * 60 * 60 * 24 * 30))),
                      null, (short) 2062);
              tickleService.create(tickle);
            }

          }
        } catch (ParseException e) {
          throw new ServiceException();
        }

      }
    }

  }


}
