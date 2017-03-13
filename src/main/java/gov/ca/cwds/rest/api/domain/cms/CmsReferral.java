package gov.ca.cwds.rest.api.domain.cms;

import java.util.Set;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.DomainObject;
import io.swagger.annotations.ApiModelProperty;

/**
 * Logical representation of a CmsReferral
 * 
 * @author CWDS API Team
 */
public class CmsReferral extends DomainObject implements Request, Response {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  @NotNull
  @ApiModelProperty(required = true, readOnly = false)
  @Valid
  private Referral referral;

  @NotNull
  @ApiModelProperty(required = true, readOnly = false)
  @Valid
  private Allegation allegation;

  @ApiModelProperty(required = true, readOnly = false)
  @Valid
  private CrossReport crossReport;

  @NotNull
  @ApiModelProperty(required = false, readOnly = false)
  @Valid
  private ReferralClient referralClient;

  @NotNull
  @ApiModelProperty(required = true, readOnly = false)
  @Valid
  private Reporter reporter;

  @NotNull
  @ApiModelProperty(required = true, readOnly = false)
  @Valid
  private Set<Client> client;

  /**
   * Construct from JSON.
   * 
   * @param referral - Referral object
   * @param allegation - Allegation object
   * @param crossReport - CrossReport object
   * @param referralClient - ReferralClient object
   * @param reporter - Reporter object
   * @param client - Client object
   */
  @JsonCreator
  public CmsReferral(@JsonProperty("referral") Referral referral,
      @JsonProperty("allegation") Allegation allegation,
      @JsonProperty("crossReport") CrossReport crossReport,
      @JsonProperty("referralClient") ReferralClient referralClient,
      @JsonProperty("reporter") Reporter reporter, @JsonProperty("client") Set<Client> client) {
    super();
    this.referral = referral;
    this.allegation = allegation;
    this.crossReport = crossReport;
    this.referralClient = referralClient;
    this.reporter = reporter;
    this.client = client;
  }

  /**
   * @return the referral
   */
  public Referral getReferral() {
    return referral;
  }

  /**
   * @return the allegation
   */
  public Allegation getAllegation() {
    return allegation;
  }

  /**
   * @return the crossReport
   */
  public CrossReport getCrossReport() {
    return crossReport;
  }

  /**
   * @return the referralClient
   */
  public ReferralClient getReferralClient() {
    return referralClient;
  }

  /**
   * @return the reporter
   */
  public Reporter getReporter() {
    return reporter;
  }

  /**
   * @return the client
   */
  public Set<Client> getClient() {
    return client;
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.lang.Object#hashCode()
   */
  @Override
  public final int hashCode() {
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

  /**
   * {@inheritDoc}
   * 
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public final boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (!(obj instanceof CmsReferral)) {
      return false;
    }
    CmsReferral other = (CmsReferral) obj;
    if (allegation == null) {
      if (other.allegation != null)
        return false;
    } else if (!allegation.equals(other.allegation))
      return false;
    if (crossReport == null) {
      if (other.crossReport != null)
        return false;
    } else if (!crossReport.equals(other.crossReport))
      return false;
    if (referral == null) {
      if (other.referral != null)
        return false;
    } else if (!referral.equals(other.referral))
      return false;
    if (referralClient == null) {
      if (other.referralClient != null)
        return false;
    } else if (!referralClient.equals(other.referralClient))
      return false;
    if (reporter == null) {
      if (other.reporter != null)
        return false;
    } else if (!reporter.equals(other.reporter))
      return false;
    if (client == null) {
      if (other.client != null)
        return false;
    } else if (!client.equals(other.client))
      return false;
    return true;
  }
}
