package gov.ca.cwds.data.persistence.ns;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

import gov.ca.cwds.data.ns.NsPersistentObject;

/**
 * {@link NsPersistentObject} representing a Language
 * 
 * @author CWDS API Team
 */
@NamedQueries({
    @NamedQuery(name = "gov.ca.cwds.rest.api.persistence.ns.Language.findAll",
        query = "FROM Language"),
    @NamedQuery(name = "gov.ca.cwds.rest.api.persistence.ns.Language.findAllUpdatedAfter",
        query = "FROM Language WHERE lastUpdatedTime > :after")})
@SuppressWarnings("serial")
@Entity
@Table(name = "language")
public class Language extends NsPersistentObject {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_language")
  @SequenceGenerator(name = "seq_language", sequenceName = "seq_language", allocationSize = 50)
  @Column(name = "language_id")
  private Long languageId;

  @Column(name = "language_code_id")
  private String languageCodeId;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "personLanguageId.language")
  private Set<PersonLanguage> personLanguages = new HashSet<>();

  /**
   * Default constructor
   * 
   * Required for Hibernate
   */
  public Language() {
    super();
  }

  /**
   * Constructor
   * 
   * @param languageId The language id
   * @param languageCodeId The language
   * 
   */
  public Language(Long languageId, String languageCodeId) {
    super();
    this.languageId = languageId;
    this.languageCodeId = languageCodeId;
  }


  /**
   * @param language is the language
   * @param lastUpdatedId the id of the last person to update this object
   * @param createUserId the id of the person created the record
   */
  public Language(gov.ca.cwds.rest.api.domain.Language language, String lastUpdatedId,
      String createUserId) {
    super(lastUpdatedId, createUserId);
    this.languageCodeId = language.getLanguage();
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.data.persistence.PersistentObject#getPrimaryKey()
   */
  @Override
  public Long getPrimaryKey() {
    return getLanguageId();
  }

  /**
   * @return the id
   */
  public Long getLanguageId() {
    return languageId;
  }

  /**
   * @return the streetAddress
   */
  public String getLanguageCodeId() {
    return languageCodeId;
  }

  /**
   * @return the personLanguages
   */
  public Set<PersonLanguage> getPersonLanguages() {
    return personLanguages;
  }

}
