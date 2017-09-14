package gov.ca.cwds.rest.api.domain.investigation;

import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.DomainObject;
import gov.ca.cwds.rest.api.domain.ReportingDomain;
import io.dropwizard.jackson.JsonSnakeCase;

import java.util.Set;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * {@link DomainObject} representing a History Of Involvement
 * 
 * @author CWDS API Team
 */
@JsonInclude(Include.ALWAYS)
@JsonSnakeCase
@JsonPropertyOrder({"cases", "referrals", "screenings"})
public class HistoryOfInvolvement extends ReportingDomain implements Response {

  /**
   * Default
   */
  private static final long serialVersionUID = 1L;

  @JsonProperty("cases")
  private Set<Case> cases;

  @JsonProperty("referrals")
  private Set<SimpleReferral> referrals;

  @JsonProperty("screenings")
  private Set<SimpleScreening> screenings;

  /**
   * 
   */
  public HistoryOfInvolvement() {
    super();
  }



  /**
   * Constructor
   * 
   * @param cases the cases
   * @param referrals the referrals
   * @param screenings the screenings
   */
  public HistoryOfInvolvement(@JsonProperty("cases") Set<Case> cases,
      @JsonProperty("referrals") Set<SimpleReferral> referrals,
      @JsonProperty("screenings") Set<SimpleScreening> screenings) {
    super();
    this.cases = cases;
    this.referrals = referrals;
    this.screenings = screenings;
  }



  /**
   * @return the cases
   */
  public Set<Case> getCases() {
    return cases;
  }



  /**
   * @return the referrals
   */
  public Set<SimpleReferral> getReferrals() {
    return referrals;
  }



  /**
   * @return the screenings
   */
  public Set<SimpleScreening> getScreenings() {
    return screenings;
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
