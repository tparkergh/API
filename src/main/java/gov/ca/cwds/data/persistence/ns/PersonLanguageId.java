package gov.ca.cwds.data.persistence.ns;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

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

  /**
   * constructor
   */
  public PersonLanguageId() {
    super();
  }

  @ManyToOne
  private Person person; // NOSONAR

  @ManyToOne
  private Language language; // NOSONAR


  /**
   * @return the person
   */
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

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((language == null) ? 0 : language.hashCode());
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
    PersonLanguageId other = (PersonLanguageId) obj;
    if (language == null) {
      if (other.language != null)
        return false;
    } else if (!language.equals(other.language))
      return false;
    if (person == null) {
      if (other.person != null)
        return false;
    } else if (!person.equals(other.person))
      return false;
    return true;
  }

}
