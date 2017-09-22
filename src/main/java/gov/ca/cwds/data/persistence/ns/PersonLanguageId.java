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
 *         This class is the refernce to {@link gov.ca.cwds.data.persistence.ns.PersonLanguage}
 *
 */
@Embeddable
public class PersonLanguageId implements Serializable {

  /**
   * constructor
   */
  private static final long serialVersionUID = 1L;

  @ManyToOne
  private Person person;

  @ManyToOne
  private Language language;

  /**
   * constructor
   */
  public PersonLanguageId() {
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
   * @return the language
   */
  public Language getLanguage() {
    return language;
  }

  /**
   * @param language - The language
   */
  public void setLanguage(Language language) {
    this.language = language;
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
