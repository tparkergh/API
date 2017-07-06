package gov.ca.cwds.rest.business.rules;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.cms.AllegationDao;
import gov.ca.cwds.data.cms.ClientDao;
import gov.ca.cwds.data.cms.CrossReportDao;
import gov.ca.cwds.data.cms.ReferralDao;
import gov.ca.cwds.data.cms.ReporterDao;
import gov.ca.cwds.data.persistence.cms.Client;
import gov.ca.cwds.data.persistence.cms.Referral;
import gov.ca.cwds.data.persistence.cms.Reporter;
import gov.ca.cwds.rest.api.domain.Allegation;
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

  final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

  private static final String ESTIMATED_DOB_CODE = "Y";
  private static final String REFERRAL_REFERRALCLIENT = "RL";
  private static final String MANDATED_REPORTER_INDICATOR = "Y";
  private static final String SATISFY_CROSSREPORT_IND = "N";
  private static final String REFERRAL = "R";
  private static final short STATE_ID_MISSING = (short) 2062;
  private static final short CROSSREPORT_LAWENFORCEMENT_DUE = (short) 2049;
  private static final int GENERAL_NEGLECT = 2178;
  private static final int SUBSTANTIAL_RISK = 5624;
  private static final short ENTERED_IN_ERROR = (short) 5918;

  private ClientDao clientDao;
  private ReferralDao referralDao;
  private AllegationDao allegationDao;
  private ReporterDao reporterDao;
  private CrossReportDao crossReportDao;
  private TickleService tickleService;

  /**
   * @param clientDao
   * @param referralDao
   * @param allegationDao
   * @param reporterDao
   * @param crossReportDao
   * @param tickleService
   */
  @Inject
  public Reminders(ClientDao clientDao, ReferralDao referralDao, AllegationDao allegationDao,
      ReporterDao reporterDao, CrossReportDao crossReportDao, TickleService tickleService) {
    super();
    this.clientDao = clientDao;
    this.referralDao = referralDao;
    this.tickleService = tickleService;
    this.allegationDao = allegationDao;
    this.reporterDao = reporterDao;
    this.crossReportDao = crossReportDao;
  }

  /**
   * @param postedScreeningToReferral
   */
  public void createTickle(PostedScreeningToReferral postedScreeningToReferral) {
    stateIdMissing(postedScreeningToReferral);
    crossReportForLawEnforcmentDue(postedScreeningToReferral);
  }

  private void stateIdMissing(PostedScreeningToReferral postedScreeningToReferral) {

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

          if (years < 26 || (client.getEstimatedDobCode() != null
              && client.getEstimatedDobCode().equals(ESTIMATED_DOB_CODE))
              && client.getDriverLicenseNumber().isEmpty()) {
            if (referral.getClosureDate() == null) {
              Calendar newDate = Calendar.getInstance();
              newDate.setTime(client.getCreationDate());
              newDate.add(Calendar.DATE, 30);

              gov.ca.cwds.rest.api.domain.cms.Tickle tickle =
                  new gov.ca.cwds.rest.api.domain.cms.Tickle(referral.getId(),
                      REFERRAL_REFERRALCLIENT, client.getId(), null,
                      dateFormat.format(newDate.getTime()), referral.getScreenerNoteText(),
                      STATE_ID_MISSING);
              tickleService.create(tickle);
            }

          }
        } catch (ParseException e) {
          throw new ServiceException();
        }

      }

      LOGGER.info("stateIdMissing reminder is created");

    }

  }

  private void crossReportForLawEnforcmentDue(PostedScreeningToReferral postedScreeningToReferral) {

    Set<Participant> reporter = postedScreeningToReferral.getParticipants();
    boolean tickleCreated = false;
    Reporter reporter1 = null;
    for (Participant participant : reporter) {
      if (participant.getRoles().contains("Mandated Reporter")
          || participant.getRoles().contains("Non-mandated Reporter")) {
        reporter1 = reporterDao.find(participant.getLegacyId());
      }
    }
    Set<Allegation> allegations = postedScreeningToReferral.getAllegations();
    Set<gov.ca.cwds.rest.api.domain.CrossReport> crossReports =
        postedScreeningToReferral.getCrossReports();
    Referral referral = referralDao.find(postedScreeningToReferral.getReferralId());
    Calendar newDate = Calendar.getInstance();
    newDate.setTime(referral.getReceivedDate());
    newDate.add(Calendar.HOUR, 36);
    if (referral.getClosureDate() == null) {

      for (Allegation allegation : allegations) {
        gov.ca.cwds.data.persistence.cms.Allegation savedAllegation =
            allegationDao.find(allegation.getLegacyId());
        short allegationType = savedAllegation.getAllegationType();
        if (!tickleCreated && allegationType != 0 && allegationType != GENERAL_NEGLECT
            && allegationType != SUBSTANTIAL_RISK
            && savedAllegation.getAllegationDispositionType() != ENTERED_IN_ERROR) {
          if (reporter1.getMandatedReporterIndicator() != MANDATED_REPORTER_INDICATOR
              && reporter1.getLawEnforcementId() != null) {
            for (gov.ca.cwds.rest.api.domain.CrossReport crossReport : crossReports) {
              gov.ca.cwds.data.persistence.cms.CrossReport savedCrossReport =
                  crossReportDao.find(crossReport.getLegacyId());

              if (!tickleCreated && savedCrossReport.getLawEnforcementId() != null
                  && SATISFY_CROSSREPORT_IND
                      .equals(savedCrossReport.getSatisfyCrossReportIndicator())) {

                gov.ca.cwds.rest.api.domain.cms.Tickle tickle =
                    new gov.ca.cwds.rest.api.domain.cms.Tickle(referral.getId(), REFERRAL, null,
                        null, dateFormat.format(newDate.getTime()), referral.getScreenerNoteText(),
                        CROSSREPORT_LAWENFORCEMENT_DUE);
                tickleService.create(tickle);
                tickleCreated = true;

              }
            }
          }
        }
      }
    }
  }

}
