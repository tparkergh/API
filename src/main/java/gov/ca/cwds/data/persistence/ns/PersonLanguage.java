package gov.ca.cwds.data.persistence.ns;

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

import gov.ca.cwds.data.persistence.PersistentObject;

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
public class PersonLanguage implements PersistentObject {

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
    super();
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
  @Transient
  public Person getPerson() {
    return personLanguageId.getPerson();
  }

  /**
   * @return the language
   */
  @Transient
  public Language getLanguage() {
    return personLanguageId.getLanguage();
  }

}
