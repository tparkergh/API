package gov.ca.cwds.data.persistence.ns;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

/**
 * @author CWS-NS2
 *
 */
@Embeddable
public class PersonEthnicityId implements Serializable {
  /**
   * Base serialization value. Increment by version
   */
  private static final long serialVersionUID = 1L;

  /**
   * Default constructor
   */
  public PersonEthnicityId() {
    super();
  }

  @ManyToOne(cascade = CascadeType.ALL)
  private Person person; // NOSONAR

  @ManyToOne(cascade = CascadeType.ALL)
  private Ethnicity ethnicity; // NOSONAR

  /**
   * @return the person
   */
  public Person getPerson() {
    return person;
  }

  /**
   * @param person - the person
   */
  public void setPerson(Person person) {
    this.person = person;
  }

  /**
   * @return the ethnicity
   */
  public Ethnicity getEthnicity() {
    return ethnicity;
  }

  /**
   * @param ethnicity - the ethnicity
   */
  public void setEthnicity(Ethnicity ethnicity) {
    this.ethnicity = ethnicity;
  }

  @SuppressWarnings("javadoc")
  public Serializable getPrimaryKey() {
    return null;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((ethnicity == null) ? 0 : ethnicity.hashCode());
    result = prime * result + ((person == null) ? 0 : person.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    PersonEthnicityId other = (PersonEthnicityId) obj;
    if (ethnicity == null) {
      if (other.ethnicity != null)
        return false;
    } else if (!ethnicity.equals(other.ethnicity))
      return false;
    if (person == null) {
      if (other.person != null)
        return false;
    } else if (!person.equals(other.person))
      return false;
    return true;
  }

}
