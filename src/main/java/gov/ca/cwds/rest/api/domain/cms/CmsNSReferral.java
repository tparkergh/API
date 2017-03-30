package gov.ca.cwds.rest.api.domain.cms;

import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.DomainObject;
import gov.ca.cwds.rest.api.domain.Person;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Logical representation of a CmsNSReferral
 * 
 * @author CWDS API Team
 */
public class CmsNSReferral extends DomainObject implements Request, Response {

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
  private Person person;

  /**
   * Construct from JSON.
   * 
   * @param referral - Referral object
   * @param person - Person object
   *
   */
  @JsonCreator
  public CmsNSReferral(@JsonProperty("referral") Referral referral,
      @JsonProperty("person") Person person) {
    super();
    this.referral = referral;
    this.person = person;
  }


  /**
   * @return the referral
   */
  public Referral getReferral() {
    return referral;
  }



  /**
   * @return the person
   */
  public Person getPerson() {
    return person;
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
    result = prime * result + ((referral == null) ? 0 : referral.hashCode());
    result = prime * result + ((person == null) ? 0 : person.hashCode());
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
    CmsNSReferral other = (CmsNSReferral) obj;

    if (referral == null) {
      if (other.referral != null) {
        return false;
      }
    } else if (!referral.equals(other.referral)) {
      return false;
    }
    if (person == null) {
      if (other.person != null) {
        return false;
      }
    } else if (!person.equals(other.person)) {
      return false;
    }
    return true;
  }


}
