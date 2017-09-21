package gov.ca.cwds.data.persistence.cms;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * {@link CmsPersistentObject} Class representing an LongText.
 * 
 * @author CWDS API Team
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "LONG_TXT")
@JsonPropertyOrder(alphabetic = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class LongText extends CmsPersistentObject {

  @Id
  @Column(name = "IDENTIFIER", length = CMS_ID_LEN)
  private String id;

  @Column(name = "CNTY_SPFCD")
  private String countySpecificCode;

  @Column(name = "TTEXT_DSC")
  private String textDescription;

  /**
   * Default constructor
   * 
   * Required for Hibernate
   */
  public LongText() {
    super();
  }

  /**
   * @param id primary key
   * @param countySpecificCode county specific code
   * @param textDescription long text description
   */
  public LongText(String id, String countySpecificCode, String textDescription) {
    super();
    this.id = id;
    this.countySpecificCode = countySpecificCode;
    this.textDescription = textDescription;
  }

  /**
   * Constructor using domain
   * 
   * @param id The id
   * @param persistedLongText The domain object to construct this object from
   * @param lastUpdatedId the id of the last person to update this object
   */
  public LongText(String id, gov.ca.cwds.rest.api.domain.cms.LongText persistedLongText,
      String lastUpdatedId) {
    super(lastUpdatedId);

    this.id = id;
    this.countySpecificCode = persistedLongText.getCountySpecificCode();
    this.textDescription = persistedLongText.getTextDescription();
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.data.persistence.PersistentObject#getPrimaryKey()
   */
  @Override
  public String getPrimaryKey() {
    return getId();
  }

  /**
   * @return the id
   */
  public String getId() {
    return id;
  }

  /**
   * @return the countSpecificCode
   */
  public String getCountySpecificCode() {
    return countySpecificCode;
  }

  /**
   * @return the note description
   */
  public String getTextDescription() {
    return textDescription;
  }

}
