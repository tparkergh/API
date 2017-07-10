package gov.ca.cwds.rest.api.domain;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.validation.Date;
import io.dropwizard.jackson.JsonSnakeCase;

/**
 * 
 * @author CWDS API Team
 */
@JsonSnakeCase
@JsonInclude(Include.ALWAYS)
public class PostedScreeningToReferral extends ScreeningToReferral implements Response {

  /**
   * Serialization version
   */
  private static final long serialVersionUID = 1L;

  private static final String REFERRAL_TABLE_NAME = "REFERL_T";

  /**
   * Default, no-op constructor.
   */
  public PostedScreeningToReferral() {
    super();
  }

  /**
   * @param id - screening ID
   * @param referralId - legacy referral Id
   * @param legacySourceTable - legacy source table name
   * @param endedAt - date/time ended
   * @param incidentCounty - county
   * @param incidentDate - date of incident
   * @param locationType - location type
   * @param communicationMethod - communication method
   * @param name - name
   * @param reportNarrative - report narrative
   * @param reference - referrence
   * @param responseTime - response time frame
   * @param startedAt - started at
   * @param assignee - assigned to county worker
   * @param additionalInformation - additional information
   * @param screeningDecision - screening decision
   * @param screeningDecisionDetail - detail
   * @param address - address of referral
   * @param participants - participants of the referral
   * @param crossReports - cross reports
   * @param allegations - allegations
   */
  public PostedScreeningToReferral(long id, String referralId, String legacySourceTable,
      @Date String endedAt, String incidentCounty, @Date String incidentDate, String locationType,
      String communicationMethod, String name, String reportNarrative, String reference,
      String responseTime, @Date String startedAt, String assignee, String additionalInformation,
      String screeningDecision, String screeningDecisionDetail, Address address,
      Set<Participant> participants, Set<CrossReport> crossReports, Set<Allegation> allegations) {
    super(id, legacySourceTable, referralId, endedAt, incidentCounty, incidentDate, locationType,
        communicationMethod, name, reportNarrative, reference, responseTime, startedAt, assignee,
        additionalInformation, screeningDecision, screeningDecisionDetail, address, participants,
        crossReports, allegations);
  }

  /**
   * @param id - id
   * @param referral - referral
   * @param resultParticipants - resultParticipants
   * @param resultCrossReports - resultCrossReports
   * @param resultAllegations - resultAllegations
   * @return the postedScreeningToReferral
   */
  public static PostedScreeningToReferral createWithDefaults(String id,
      ScreeningToReferral referral, Set<Participant> resultParticipants,
      Set<CrossReport> resultCrossReports, Set<Allegation> resultAllegations) {

    return new PostedScreeningToReferral(referral.getId(), id, REFERRAL_TABLE_NAME,
        referral.getEndedAt(), referral.getIncidentCounty(), referral.getIncidentDate(),
        referral.getLocationType(), referral.getCommunicationMethod(), referral.getName(),
        referral.getReportNarrative(), referral.getReference(), referral.getResponseTime(),
        referral.getStartedAt(), referral.getAssignee(), referral.getAdditionalInformation(),
        referral.getScreeningDecision(), referral.getScreeningDecisionDetail(),
        referral.getAddress(), resultParticipants, resultCrossReports, resultAllegations);

  }

}
