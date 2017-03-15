package gov.ca.cwds.rest.api.domain.cms;

import java.util.LinkedHashSet;
import java.util.Set;

import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.DomainObject;

/**
 * Logical representation of a Referral
 * 
 * @author CWDS API Team
 */
public class PostedCmsReferral extends DomainObject implements Response {
  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  private Referral referral;
  private Set<Client> client;
  private Set<Allegation> allegation;
  private Set<CrossReport> crossReport;
  private Set<ReferralClient> referralClient;
  private Reporter reporter;


  /**
   * @param referral - PostedReferral
   * @param allegation = PostedAllegation
   * @param crossReport - CrossReport
   * @param referralClient - ReferralClient
   * @param reporter - PostedReporter
   * @param client - PostedClient
   */
  public PostedCmsReferral(PostedReferral referral, Set<PostedClient> client,
      Set<PostedAllegation> allegation, Set<CrossReport> crossReport,
      Set<ReferralClient> referralClient, PostedReporter reporter) {

    super();
    this.referral = referral;
    this.client = new LinkedHashSet<>();
    for (PostedClient resultClient : client)
      this.client.add(resultClient);
    this.allegation = new LinkedHashSet<>();
    for (PostedAllegation resultAllegation : allegation)
      this.allegation.add(resultAllegation);
    this.crossReport = crossReport;
    this.referralClient = referralClient;
    this.reporter = reporter;
  }


  /**
   * @return the referral
   */
  public Referral getReferral() {
    return referral;
  }

  /**
   * @return the client
   */
  public Set<Client> getClient() {
    return client;
  }

  /**
   * @return the allegation
   */
  public Set<Allegation> getAllegation() {
    return allegation;
  }



  /**
   * @return the crossReport
   */
  public Set<CrossReport> getCrossReport() {
    return crossReport;
  }



  /**
   * @return the referralClient
   */
  public Set<ReferralClient> getReferralClient() {
    return referralClient;
  }



  /**
   * @return the reporter
   */
  public Reporter getReporter() {
    return reporter;
  }


  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#hashCode()
   */

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((allegation == null) ? 0 : allegation.hashCode());
    result = prime * result + ((crossReport == null) ? 0 : crossReport.hashCode());
    result = prime * result + ((referral == null) ? 0 : referral.hashCode());
    result = prime * result + ((referralClient == null) ? 0 : referralClient.hashCode());
    result = prime * result + ((reporter == null) ? 0 : reporter.hashCode());
    result = prime * result + ((client == null) ? 0 : client.hashCode());
    return result;
  }


  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    PostedCmsReferral other = (PostedCmsReferral) obj;
    if (allegation == null) {
      if (other.allegation != null) {
        return false;
      }
    } else if (!allegation.equals(other.allegation)) {
      return false;
    }
    if (crossReport == null) {
      if (other.crossReport != null) {
        return false;
      }
    } else if (!crossReport.equals(other.crossReport)) {
      return false;
    }
    if (referral == null) {
      if (other.referral != null) {
        return false;
      }
    } else if (!referral.equals(other.referral)) {
      return false;
    }
    if (referralClient == null) {
      if (other.referralClient != null) {
        return false;
      }
    } else if (!referralClient.equals(other.referralClient)) {
      return false;
    }
    if (reporter == null) {
      if (other.reporter != null) {
        return false;
      }
    } else if (!reporter.equals(other.reporter)) {
      return false;
    }
    if (client == null) {
      if (other.client != null)
        return false;
    } else if (!client.equals(other.client))
      return false;
    return true;
  }



}
