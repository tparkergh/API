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

import gov.ca.cwds.data.Dao;
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

  private static final String PERPETRATOR_ROLE = "Perpetrator";
  private static final String VICTIM_ROLE = "Victim";
  private static final String MANDATED_REPORTER_ROLE = "Mandated Reporter";
  private static final String NON_MANDATED_REPORTER_ROLE = "Non-mandated Reporter";
  private static final String REFERRAL_REFERRALCLIENT = "RL";
  private static final String DEFAULT_TRUE_INDICATOR = "Y";
  private static final String DEFAULT_FALSE_INDICATOR = "N";
  private static final String REFERRAL = "R";
  private static final short STATE_ID_MISSING = (short) 2062;
  private static final short CROSSREPORT_LAWENFORCEMENT_DUE = (short) 2049;
  private static final short REFERRAL_INVESTIGATION_CONTACT_DUE = (short) 2058;
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
   * @param clientDao - The {@link Dao} handling {@link gov.ca.cwds.data.persistence.cms.Client}
   *        objects
   * @param referralDao - The {@link Dao} handling {@link gov.ca.cwds.data.persistence.cms.Referral}
   *        objects
   * @param allegationDao - The {@link Dao} handling
   *        {@link gov.ca.cwds.data.persistence.cms.Allegation} objects
   * @param reporterDao - The {@link Dao} handling {@link gov.ca.cwds.data.persistence.cms.Reporter}
   *        objects
   * @param crossReportDao - The {@link Dao} handling
   *        {@link gov.ca.cwds.data.persistence.cms.CrossReport} objects
   * @param tickleService - tickleService
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
   * @param postedScreeningToReferral - postedScreeningToReferral
   */
  public void createTickle(PostedScreeningToReferral postedScreeningToReferral) {
    stateIdMissing(postedScreeningToReferral);
    crossReportForLawEnforcmentDue(postedScreeningToReferral);
    referralInvestigationContactDue(postedScreeningToReferral);
  }

  /**
   * <blockquote>
   * 
   * <pre>
   * BUSINESS RULE: "R - 05443" - TICKLE for State ID Missing
   * 
   * If the Client age is less then 26 Years, doesn't have state_Id and the client is in referral then this reminder is created.
   * </blockquote>
   * </pre>
   */
  private void stateIdMissing(PostedScreeningToReferral postedScreeningToReferral) {
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
           * check for the client age is below 26 or not by using the Java Calender
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

          if (years < 26 || (client.getEstimatedDobCode() != null
              && client.getEstimatedDobCode().equals(DEFAULT_TRUE_INDICATOR))
              && client.getDriverLicenseNumber().isEmpty()) {
            if (referral.getClosureDate() == null) {
              /*
               * duedate is updated with adding 30 days to the client creationDate
               */
              Calendar dueDate = Calendar.getInstance();
              dueDate.setTime(client.getCreationDate());
              dueDate.add(Calendar.DATE, 30);

              gov.ca.cwds.rest.api.domain.cms.Tickle tickle =
                  new gov.ca.cwds.rest.api.domain.cms.Tickle(referral.getId(),
                      REFERRAL_REFERRALCLIENT, client.getId(), null,
                      dateFormat.format(dueDate.getTime()), referral.getScreenerNoteText(),
                      STATE_ID_MISSING);
              tickleService.create(tickle);
              LOGGER.info("stateIdMissing reminder is created");
            }
          }
        } catch (ParseException e) {
          throw new ServiceException();
        }
      }
    }
  }

  /**
   * <blockquote>
   * 
   * <pre>
   * BUSINESS RULE: "R - 04464" - TICKLE for Cross Report for Law Enforcement Due
   * 
   * If the Allegation typs is not a general Neglect and substantial risk, crossReport and reporter doesnt have a lawEnforcement Id. 
   * Then create this reminde.
   * </blockquote>
   * </pre>
   */
  private void crossReportForLawEnforcmentDue(PostedScreeningToReferral postedScreeningToReferral) {
    Set<Participant> reporter = postedScreeningToReferral.getParticipants();
    boolean reminderCreated = false;
    Reporter persistedReporter = null;
    for (Participant participant : reporter) {
      if (participant.getRoles().contains(MANDATED_REPORTER_ROLE)
          || participant.getRoles().contains(NON_MANDATED_REPORTER_ROLE)) {
        persistedReporter = reporterDao.find(participant.getLegacyId());
      }
    }
    Set<Allegation> allegations = postedScreeningToReferral.getAllegations();
    Set<gov.ca.cwds.rest.api.domain.CrossReport> crossReports =
        postedScreeningToReferral.getCrossReports();
    Referral referral = referralDao.find(postedScreeningToReferral.getReferralId());
    /*
     * duedate is updated with adding 36 HRS to the referral receivedDate
     */
    Calendar dueDate = Calendar.getInstance();
    dueDate.setTime(referral.getReceivedDate());
    dueDate.add(Calendar.HOUR, 36);
    if (referral.getClosureDate() == null) {

      for (Allegation allegation : allegations) {
        gov.ca.cwds.data.persistence.cms.Allegation savedAllegation =
            allegationDao.find(allegation.getLegacyId());
        short allegationType = savedAllegation.getAllegationType();
        if (!reminderCreated && allegationType != GENERAL_NEGLECT
            && allegationType != SUBSTANTIAL_RISK
            && savedAllegation.getAllegationDispositionType() != ENTERED_IN_ERROR) {

          if (persistedReporter.getMandatedReporterIndicator() != DEFAULT_TRUE_INDICATOR
              && persistedReporter.getLawEnforcementId() != null) {

            for (gov.ca.cwds.rest.api.domain.CrossReport crossReport : crossReports) {
              gov.ca.cwds.data.persistence.cms.CrossReport savedCrossReport =
                  crossReportDao.find(crossReport.getLegacyId());
              if (!reminderCreated && savedCrossReport.getLawEnforcementId() != null
                  && DEFAULT_FALSE_INDICATOR
                      .equals(savedCrossReport.getSatisfyCrossReportIndicator())) {

                gov.ca.cwds.rest.api.domain.cms.Tickle tickle =
                    new gov.ca.cwds.rest.api.domain.cms.Tickle(referral.getId(), REFERRAL, null,
                        null, dateFormat.format(dueDate.getTime()), referral.getScreenerNoteText(),
                        CROSSREPORT_LAWENFORCEMENT_DUE);
                tickleService.create(tickle);
                reminderCreated = true;
                LOGGER.info("crossReportForLawEnforcmentDue reminder is created");
              }
            }
          }
        }
      }
    }
  }

  /**
   * <blockquote>
   * 
   * <pre>
   * BUSINESS RULE: "R - 04631" - TICKLE for Referral Investigation Contact Due reminder
   * 
   * If the Allegation typs is not a general Neglect and substantial risk, crossReport and reporter doesnt have a lawEnforcement Id. 
   * Then create this reminde.
   * </blockquote>
   * </pre>
   */
  private void referralInvestigationContactDue(
      PostedScreeningToReferral postedScreeningToReferral) {
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

          if (years < 19) {
            gov.ca.cwds.rest.api.domain.cms.Tickle tickle =
                new gov.ca.cwds.rest.api.domain.cms.Tickle(referral.getId(),
                    REFERRAL_REFERRALCLIENT, client.getId(), null,
                    dateFormat.format(referral.getReceivedDate()), referral.getScreenerNoteText(),
                    REFERRAL_INVESTIGATION_CONTACT_DUE);
            tickleService.create(tickle);
          }
        } catch (ParseException e) {
          throw new ServiceException();
        }
      }
    }

  }

}
