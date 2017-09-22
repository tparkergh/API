package gov.ca.cwds.data.persistence.ns;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author CWDS API Team
 * 
 *         This class is the refernce to {@link gov.ca.cwds.data.persistence.ns.PersonRace}
 *
 */
@Embeddable
public class PersonRaceId implements Serializable {

  /**
   * Base serialization value. Increment by version
   */
  private static final long serialVersionUID = 1L;

  @ManyToOne
  private Person person;

  @ManyToOne
  private Race race;

  /**
   * constructor
   */
  public PersonRaceId() {
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
   * @param person - The person
   */
  public void setPerson(Person person) {
    this.person = person;
  }

  /**
   * @return the race
   */
  public Race getRace() {
    return race;
  }

  /**
   * @param race - The race
   */
  public void setRace(Race race) {
    this.race = race;
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
