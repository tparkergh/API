package gov.ca.cwds.fixture;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import gov.ca.cwds.rest.api.domain.DomainChef;
import gov.ca.cwds.rest.api.domain.Participant;
import gov.ca.cwds.rest.api.domain.ScreeningToReferral;

/**
 * 
 * @author CWDS API Team
 */
@SuppressWarnings("javadoc")
public class ScreeningToReferralResourceBuilder {

  private static final DateFormat dateTimeFormat =
      new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SS'Z'", Locale.US);

  private long id = 1L;
  private String referralId = "";
  private String legacySourceTable = "";
  private String endedAt = DomainChef.cookDate(new Date());
  private String incidentCounty = "34";
  private String incidentDate = DomainChef.cookDate(new Date());
  private String locationType = "Foster Home";
  private Short communicationMethod = 409;
  private String name = "The Rocky Horror Show";
  private String reportNarrative = "Narrative 123 test";
  private String reference = "123ABC";
  private Short responseTime = 1516;
  private String startedAt = dateTimeFormat.format(new Date());
  private String assignee = "Michael Bastow";
  private String additionalInformation = "additional information about the referral";
  private String screeningDecision = "Response time";
  private String screeningDecisionDetail = "Detail";
  private int approvalStatus = 118;
  private boolean familyAwareness = false;
  private boolean filedWithLawEnforcement = false;
  private String responsibleAgency = "C";
  private gov.ca.cwds.rest.api.domain.Address address;
  private Set<Participant> participants;
  private Set<gov.ca.cwds.rest.api.domain.CrossReport> crossReports;
  private Set<gov.ca.cwds.rest.api.domain.Allegation> allegations;


  public ScreeningToReferralResourceBuilder() {
    address = new AddressResourceBuilder().createAddress();
    Participant victim = new ParticipantResourceBuilder().createVictimParticipant();
    Participant perp = new ParticipantResourceBuilder().createPerpParticipant();
    Participant reporter = new ParticipantResourceBuilder().createReporterParticipant();
    this.participants = new HashSet<>(Arrays.asList(victim, perp, reporter));
    gov.ca.cwds.rest.api.domain.CrossReport crossReport =
        new CrossReportResourceBuilder().createCrossReport();
    this.crossReports = new HashSet<>(Arrays.asList(crossReport));
    gov.ca.cwds.rest.api.domain.Allegation allegation =
        new AllegationResourceBuilder().createAllegation();
    this.allegations = new HashSet<>(Arrays.asList(allegation));

  }

  public ScreeningToReferralResourceBuilder setId(long id) {
    this.id = id;
    return this;
  }

  public ScreeningToReferralResourceBuilder setReferralId(String referralId) {
    this.referralId = referralId;
    return this;
  }

  public ScreeningToReferralResourceBuilder setLegacySourceTable(String legacySourceTable) {
    this.legacySourceTable = legacySourceTable;
    return this;
  }

  public ScreeningToReferralResourceBuilder setEndedAt(String endedAt) {
    this.endedAt = endedAt;
    return this;
  }

  public ScreeningToReferralResourceBuilder setIncidentCounty(String incidentCounty) {
    this.incidentCounty = incidentCounty;
    return this;
  }

  public ScreeningToReferralResourceBuilder setIncidentDate(String incidentDate) {
    this.incidentDate = incidentDate;
    return this;
  }

  public ScreeningToReferralResourceBuilder setLocationType(String locationType) {
    this.locationType = locationType;
    return this;
  }

  public ScreeningToReferralResourceBuilder setCommunicationMethod(Short communicationMethod) {
    this.communicationMethod = communicationMethod;
    return this;
  }

  public ScreeningToReferralResourceBuilder setName(String name) {
    this.name = name;
    return this;
  }

  public ScreeningToReferralResourceBuilder setReportNarrative(String reportNarrative) {
    this.reportNarrative = reportNarrative;
    return this;
  }

  public ScreeningToReferralResourceBuilder setReference(String reference) {
    this.reference = reference;
    return this;
  }

  public ScreeningToReferralResourceBuilder setResponseTime(Short responseTime) {
    this.responseTime = responseTime;
    return this;
  }

  public ScreeningToReferralResourceBuilder setStartedAt(String startedAt) {
    this.startedAt = startedAt;
    return this;
  }

  public ScreeningToReferralResourceBuilder setAssignee(String assignee) {
    this.assignee = assignee;
    return this;
  }

  public ScreeningToReferralResourceBuilder setAdditionalInformation(String additionalInformation) {
    this.additionalInformation = additionalInformation;
    return this;
  }

  public ScreeningToReferralResourceBuilder setScreeningDecision(String screeningDecision) {
    this.screeningDecision = screeningDecision;
    return this;
  }

  public ScreeningToReferralResourceBuilder setScreeningDecisionDetail(
      String screeningDecisionDetail) {
    this.screeningDecisionDetail = screeningDecisionDetail;
    return this;
  }

  public ScreeningToReferralResourceBuilder setApprovalStatus(int approvalStatus) {
    this.approvalStatus = approvalStatus;
    return this;
  }

  public ScreeningToReferralResourceBuilder setFamilyAwareness(boolean familyAwareness) {
    this.familyAwareness = familyAwareness;
    return this;
  }

  public ScreeningToReferralResourceBuilder setFiledWithLawEnforcement(boolean filedWithLawEnforcement) {
    this.filedWithLawEnforcement = filedWithLawEnforcement;
    return this;
  }

  public ScreeningToReferralResourceBuilder setResponsibleAgency(String responsibleAgency) {
    this.responsibleAgency = responsibleAgency;
    return this;
  }

  public ScreeningToReferralResourceBuilder setAddress(
      gov.ca.cwds.rest.api.domain.Address address) {
    this.address = address;
    return this;
  }

  public ScreeningToReferralResourceBuilder setParticipants(Set<Participant> participants) {
    this.participants = participants;
    return this;
  }

  public ScreeningToReferralResourceBuilder setCrossReports(
      Set<gov.ca.cwds.rest.api.domain.CrossReport> crossReports) {
    this.crossReports = crossReports;
    return this;
  }

  public ScreeningToReferralResourceBuilder setAllegations(
      Set<gov.ca.cwds.rest.api.domain.Allegation> allegations) {
    this.allegations = allegations;
    return this;
  }

  public ScreeningToReferral createScreeningToReferral() {
    return new ScreeningToReferral(id, legacySourceTable, referralId, endedAt, incidentCounty,
        incidentDate, locationType, communicationMethod, name, reportNarrative, reference,
        responseTime, startedAt, assignee, additionalInformation, screeningDecision,
        screeningDecisionDetail, approvalStatus, familyAwareness, filedWithLawEnforcement,
        responsibleAgency, address, participants, crossReports, allegations);
  }
}
