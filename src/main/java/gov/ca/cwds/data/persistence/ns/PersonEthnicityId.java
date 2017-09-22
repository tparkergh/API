package gov.ca.cwds.data.persistence.ns;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.fasterxml.jackson.annotation.JsonIgnore;

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

  @ManyToOne(cascade = CascadeType.ALL)
  private Person person;

  @ManyToOne(cascade = CascadeType.ALL)
  private Ethnicity ethnicity;

  /**
   * Default constructor
   */
  public PersonEthnicityId() {
    super();
  }

  /**
   * @return the person
   */
  @JsonIgnore
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
