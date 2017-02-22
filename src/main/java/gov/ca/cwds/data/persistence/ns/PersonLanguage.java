package gov.ca.cwds.data.persistence.ns;

import java.io.Serializable;

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

import gov.ca.cwds.data.ns.NsPersistentObject;

/**
 * @author CWS-NS2
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "person_language")
@AssociationOverrides({
    @AssociationOverride(name = "personLanguageId.person",
        joinColumns = @JoinColumn(name = "person_id")),
    @AssociationOverride(name = "personLanguageId.language",
        joinColumns = @JoinColumn(name = "language_id"))})
public class PersonLanguage extends NsPersistentObject implements Serializable {

  @EmbeddedId
  private PersonLanguageId personLanguageId = new PersonLanguageId();

  /**
   * Default constructor
   * 
   * Required for Hibernate
   */
  public PersonLanguage() {
    super();
  }

  /**
   * @param person parent person
   * @param language parent language
   */
  public PersonLanguage(Person person, Language language) {
    super(null, null);
    personLanguageId.setPerson(person);
    personLanguageId.setLanguage(language);
  }

  @Override
  public PersonLanguageId getPrimaryKey() {
    return personLanguageId;
  }

  @SuppressWarnings("unused")
  private void setPk(PersonLanguageId pk) {
    this.personLanguageId = pk;
  }

  /**
   * @param person - The person
   */
  public void setPerson(Person person) {
    getPrimaryKey().setPerson(person);
  }

  /**
   * @param language - The language
   */
  public void setLanguage(Language language) {
    getPrimaryKey().setLanguage(language);
  }

  /**
   * @return the person
   */
  public Person getPerson() {
    return personLanguageId.getPerson();
  }

  /**
   * @return the language
   */
  public Language getLanguage() {
    return personLanguageId.getLanguage();
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((personLanguageId == null) ? 0 : personLanguageId.hashCode());
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
    PersonLanguage other = (PersonLanguage) obj;
    if (personLanguageId == null) {
      if (other.personLanguageId != null)
        return false;
    } else if (!personLanguageId.equals(other.personLanguageId))
      return false;
    return true;
  }

}
