package gov.ca.cwds.rest.api.domain;

import static org.junit.Assert.assertEquals;

import java.util.HashSet;
import java.util.Set;
import org.junit.Test;

public class PostedScreeningToReferralTest {

  public static final String SACRAMENTO_COUNTY_CODE = "34";

  @Test
  public void testCreateWithDefaultCreatesWithValues(){
    String referralId = "referalId";
    Set<Participant> resultParticipants = new HashSet<>();
    Set<CrossReport> resultCrossReports = new HashSet<>();
    Set<Allegation> resultAllegations = new HashSet<>();
    ScreeningToReferral referral = createScreeningReferral();

    PostedScreeningToReferral postedReferral = PostedScreeningToReferral.createWithDefaults(referralId, referral,resultParticipants, resultCrossReports, resultAllegations);

    assertEquals("expected id field to have initialized value",referralId,postedReferral.getReferralId());
    assertEquals("expected resultParticipants field to have initialized value",resultParticipants,postedReferral.getParticipants());
    assertEquals("expected resultCrossReports field to have initialized value",resultCrossReports,postedReferral.getCrossReports());
    assertEquals("expected resultAllegations field to have initialized value",resultAllegations,postedReferral.getAllegations());
    assertEquals("expected referral field to have initialized value",referral.getId(),postedReferral.getId());
    assertEquals("expected referral field to have initialized value",referral.getEndedAt(),postedReferral.getEndedAt());
    assertEquals("expected referral field to have initialized value",referral.getIncidentCounty(),postedReferral.getIncidentCounty());
    assertEquals("expected referral field to have initialized value",referral.getIncidentDate(),postedReferral.getIncidentDate());
    assertEquals("expected referral field to have initialized value",referral.getLocationType(),postedReferral.getLocationType());
    assertEquals("expected referral field to have initialized value",referral.getCommunicationMethod(),postedReferral.getCommunicationMethod());
    assertEquals("expected referral field to have initialized value",referral.getName(),postedReferral.getName());
    assertEquals("expected referral field to have initialized value",referral.getReportNarrative(),postedReferral.getReportNarrative());
    assertEquals("expected referral field to have initialized value",referral.getReference(),postedReferral.getReference());
    assertEquals("expected referral field to have initialized value",referral.getResponseTime(),postedReferral.getResponseTime());
    assertEquals("expected referral field to have initialized value",referral.getStartedAt(),postedReferral.getStartedAt());
    assertEquals("expected referral field to have initialized value",referral.getAssignee(),postedReferral.getAssignee());
    assertEquals("expected referral field to have initialized value",referral.getAdditionalInformation(),postedReferral.getAdditionalInformation());
    assertEquals("expected referral field to have initialized value",referral.getScreeningDecision(),postedReferral.getScreeningDecision());
    assertEquals("expected referral field to have initialized value",referral.getScreeningDecisionDetail(),postedReferral.getScreeningDecisionDetail());
    assertEquals("expected referral field to have initialized value",referral.getAddress(),postedReferral.getAddress());

  }

  @Test
  public void testCreateWithDefaultCreatesWithDefaultValues(){

    String referralId = "referralId ";
    Set<Participant> resultParticipants = new HashSet<>();
    Set<CrossReport> resultCrossReports = new HashSet<>();
    Set<Allegation> resultAllegations = new HashSet<>();

    String referalTableName = "REFERL_T";

    ScreeningToReferral referral = createScreeningReferral();
    PostedScreeningToReferral postedReferral = PostedScreeningToReferral.createWithDefaults(referralId, referral,resultParticipants, resultCrossReports, resultAllegations);

    assertEquals("expected Referral Table Name default field to have initialized value",referalTableName, postedReferral.getLegacySourceTable());
  }

  private ScreeningToReferral createScreeningReferral(){
    Short responseTime = (short)1516;
    Short communicationMethod = 409;
    return  new ScreeningToReferral(1235, "sourcetable", "referralId",
        "endedAt", SACRAMENTO_COUNTY_CODE,"incidentDate", "locationType",
        communicationMethod, "name", "reportNarrative", "reference",
        responseTime, "startedAt", "assignee", "additionalInformation",
        "screeningDecision", "screeningDecisionDetail", new Address(new gov.ca.cwds.data.persistence.ns.Address()),
        new HashSet(), new HashSet(), new HashSet());

  }

}