package gov.ca.cwds.data.persistence.ns;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

import gov.ca.cwds.data.ns.NsPersistentObject;

/**
 * @author CWDS API Team
 * 
 *         This class is the refernce to {@link gov.ca.cwds.data.persistence.ns.PersonLanguage}
 *
 */
@Embeddable
public class PersonLanguageId extends NsPersistentObject implements Serializable {

  /**
   * constructor
   */
  private static final long serialVersionUID = 1L;

  /**
   * constructor
   */
  public PersonLanguageId() {
    super(null, null);
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

  @Override
  public Serializable getPrimaryKey() {
    return null;
  }

}
