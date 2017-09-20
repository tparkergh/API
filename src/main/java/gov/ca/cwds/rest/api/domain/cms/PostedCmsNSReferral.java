package gov.ca.cwds.rest.api.domain.cms;

import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.DomainObject;
import gov.ca.cwds.rest.api.domain.Person;
import gov.ca.cwds.rest.api.domain.PostedPerson;

/**
 * Logical representation of a Referral
 * 
 * @author CWDS API Team
 */
public class PostedCmsNSReferral extends DomainObject implements Response {
  /**
   * Serialization version
   */
  private static final long serialVersionUID = 1L;
  private Referral referral;
  private Person person;


  /**
   * @param referral - PostedReferral
   * @param person - PostedPerson
   */
  public PostedCmsNSReferral(PostedReferral referral, PostedPerson person) {

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



  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#hashCode()
   */

  @Override
  public int hashCode() {
    final int PRIME = 31;
    int result = 1;
    result = PRIME * result + ((referral == null) ? 0 : referral.hashCode());
    result = PRIME * result + ((person == null) ? 0 : person.hashCode());
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
    PostedCmsNSReferral other = (PostedCmsNSReferral) obj;
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
