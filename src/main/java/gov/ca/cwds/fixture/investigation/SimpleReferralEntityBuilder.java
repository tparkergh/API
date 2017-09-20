package gov.ca.cwds.fixture.investigation;

import java.util.LinkedHashSet;
import java.util.Set;

import gov.ca.cwds.rest.api.domain.investigation.Allegation;
import gov.ca.cwds.rest.api.domain.investigation.LimitedAccess;
import gov.ca.cwds.rest.api.domain.investigation.SimpleLegacyDescriptor;
import gov.ca.cwds.rest.api.domain.investigation.SimplePerson;
import gov.ca.cwds.rest.api.domain.investigation.SimpleReferral;

@SuppressWarnings("javadoc")
public class SimpleReferralEntityBuilder {
  private String endDate = "2017-10-30";
  private SimpleLegacyDescriptor legacyDescriptor =
      new SimpleLegacyDescriptor("0426-8977-4115-400591");
  private SimplePerson reporter = new SimplePersonEntityBuilder().build();
  private String countyName = "Sacramento";
  private String responseTimeCode = "1525";
  private Allegation allegation = new AllegationEntityBuilder().build();
  private SimplePerson assignedSocialWorker = new SimplePersonEntityBuilder().build();
  private LimitedAccess limitedAccess = new LimitedAccess("0", "N");
  private String responseTime = "3 day";
  private String startDate = "2017-09-01";
  private Set<Allegation> allegations = new LinkedHashSet();

  public SimpleReferral build() {
    allegations.add(allegation);
    return new SimpleReferral(endDate, legacyDescriptor, reporter, countyName, responseTimeCode,
        allegations, assignedSocialWorker, limitedAccess, responseTime, startDate);
  }

  public SimpleReferralEntityBuilder setEndDate(String endDate) {
    this.endDate = endDate;
    return this;
  }

  public SimpleReferralEntityBuilder setLegacyDescriptor(SimpleLegacyDescriptor legacyDescriptor) {
    this.legacyDescriptor = legacyDescriptor;
    return this;
  }

  public SimpleReferralEntityBuilder setReporter(SimplePerson reporter) {
    this.reporter = reporter;
    return this;
  }

  public SimpleReferralEntityBuilder setCountyName(String countyName) {
    this.countyName = countyName;
    return this;
  }

  public SimpleReferralEntityBuilder setResponseTimeCode(String responseTimeCode) {
    this.responseTimeCode = responseTimeCode;
    return this;
  }

  public SimpleReferralEntityBuilder setAllegations(Set<Allegation> allegations) {
    this.allegations = allegations;
    return this;
  }

  public SimpleReferralEntityBuilder setAssignedSocialWorker(SimplePerson assignedSocialWorker) {
    this.assignedSocialWorker = assignedSocialWorker;
    return this;
  }

  public SimpleReferralEntityBuilder setLimitedAccess(LimitedAccess limitedAccess) {
    this.limitedAccess = limitedAccess;
    return this;
  }

  public SimpleReferralEntityBuilder setReponseTime(String responseTime) {
    this.responseTime = responseTime;
    return this;
  }

  public SimpleReferralEntityBuilder setStartDate(String startDate) {
    this.startDate = startDate;
    return this;
  }

  public String getEndDate() {
    return endDate;
  }

  public SimpleLegacyDescriptor getLegacyDescriptor() {
    return legacyDescriptor;
  }

  public SimplePerson getReporter() {
    return reporter;
  }

  public String getCountyName() {
    return countyName;
  }

  public String getResponseTimeCode() {
    return responseTimeCode;
  }

  public Allegation getAllegation() {
    return allegation;
  }

  public SimplePerson getAssignedSocialWorker() {
    return assignedSocialWorker;
  }

  public LimitedAccess getLimitedAccess() {
    return limitedAccess;
  }

  public String getResponseTime() {
    return responseTime;
  }

  public String getStartDate() {
    return startDate;
  }

  public Set<Allegation> getAllegations() {
    return allegations;
  }


}
