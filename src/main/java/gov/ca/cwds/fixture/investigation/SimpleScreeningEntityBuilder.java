package gov.ca.cwds.fixture.investigation;

import java.util.Set;

import gov.ca.cwds.rest.api.domain.investigation.SimplePerson;
import gov.ca.cwds.rest.api.domain.investigation.SimplePersonWithRoles;
import gov.ca.cwds.rest.api.domain.investigation.SimpleScreening;

@SuppressWarnings("javadoc")
public class SimpleScreeningEntityBuilder {
  private String endDate = null;
  private String decision = "promote to referral";
  private String serviceName = null;
  private SimplePerson assignedSocialWorker = null;
  private SimplePerson reporter = null;
  private String id = "1234567ABC";
  private String countyName = "Fresno";
  private Set<SimplePersonWithRoles> allPeople = null;
  private String startDate = "2017-08-31";

  public SimpleScreening build() {
    return new SimpleScreening(id, endDate, decision, serviceName, reporter, countyName, allPeople,
        assignedSocialWorker, startDate);
  }

  public SimpleScreeningEntityBuilder setEndDate(String endDate) {
    this.endDate = endDate;
    return this;
  }


  public SimpleScreeningEntityBuilder setDecision(String decision) {
    this.decision = decision;
    return this;
  }

  public SimpleScreeningEntityBuilder setServiceName(String serviceName) {
    this.serviceName = serviceName;
    return this;
  }

  public SimpleScreeningEntityBuilder setAssignedSocialWorker(SimplePerson assignedSocialWorker) {
    this.assignedSocialWorker = assignedSocialWorker;
    return this;
  }

  public SimpleScreeningEntityBuilder setReporter(SimplePerson reporter) {
    this.reporter = reporter;
    return this;
  }

  public SimpleScreeningEntityBuilder setId(String id) {
    this.id = id;
    return this;
  }

  public SimpleScreeningEntityBuilder setCountyName(String countyName) {
    this.countyName = countyName;
    return this;
  }

  public SimpleScreeningEntityBuilder setAllPeople(Set<SimplePersonWithRoles> allPeople) {
    this.allPeople = allPeople;
    return this;
  }

  public SimpleScreeningEntityBuilder setStartDate(String startDate) {
    this.startDate = startDate;
    return this;
  }

  public String getEndDate() {
    return endDate;
  }

  public String getDecision() {
    return decision;
  }

  public String getServiceName() {
    return serviceName;
  }

  public SimplePerson getAssignedSocialWorker() {
    return assignedSocialWorker;
  }

  public SimplePerson getReporter() {
    return reporter;
  }

  public String getId() {
    return id;
  }

  public String getCountyName() {
    return countyName;
  }

  public Set<SimplePersonWithRoles> getAllPeople() {
    return allPeople;
  }

  public String getStartDate() {
    return startDate;
  }
}
