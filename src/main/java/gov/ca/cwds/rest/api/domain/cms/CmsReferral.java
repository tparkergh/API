package gov.ca.cwds.rest.api.domain.cms;

import java.util.Set;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.ReportingDomain;
import io.swagger.annotations.ApiModelProperty;

/**
 * Logical representation of a CmsReferral
 * 
 * @author CWDS API Team
 */
public class CmsReferral extends ReportingDomain implements Request, Response {

  /**
   * Serialization version
   */
  private static final long serialVersionUID = 1L;

  @NotNull
  @ApiModelProperty(required = true, readOnly = false)
  @Valid
  private Referral referral;

  @NotNull
  @ApiModelProperty(required = true, readOnly = false)
  @Valid
  private Set<Client> client;

  @NotNull
  @ApiModelProperty(required = true, readOnly = false)
  @Valid
  private Set<Allegation> allegation;

  @ApiModelProperty(required = true, readOnly = false)
  @Valid
  private Set<CrossReport> crossReport;

  @NotNull
  @ApiModelProperty(required = false, readOnly = false)
  @Valid
  private Set<ReferralClient> referralClient;

  @ApiModelProperty(required = true, readOnly = false)
  @Valid
  private Reporter reporter;

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
      @JsonProperty("client") Set<Client> client,
      @JsonProperty("allegation") Set<Allegation> allegation,
      @JsonProperty("crossReport") Set<CrossReport> crossReport,
      @JsonProperty("referralClient") Set<ReferralClient> referralClient,
      @JsonProperty("reporter") Reporter reporter) {
    super();
    this.referral = referral;
    this.client = client;
    this.allegation = allegation;
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

  /**
   * {@inheritDoc}
   *
   * @see java.lang.Object#hashCode()
   */
  @Override
  public final int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this, false);
  }

  /**
   * {@inheritDoc}
   *
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public final boolean equals(Object obj) {
    return EqualsBuilder.reflectionEquals(this, obj, false);
  }

}
