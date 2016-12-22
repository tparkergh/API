package gov.ca.cwds.rest.api.domain.es;

import java.io.Serializable;
import java.util.Map;

import org.elasticsearch.search.SearchHit;

import com.fasterxml.jackson.annotation.JsonProperty;

import gov.ca.cwds.rest.api.domain.Address;
import gov.ca.cwds.rest.api.domain.Person;
import gov.ca.cwds.rest.api.persistence.cms.OtherClientName;
import gov.ca.cwds.rest.api.persistence.cms.Reporter;

/**
 * Generic "person" class for ElasticSearch results, which decorates domain-level {@link Person}
 * with a String id and optional nested person specialty document, such as {@link OtherClientName}
 * or {@link Reporter}.
 * 
 * @author CWDS API Team
 */
public class ESPerson extends Person {

  /**
   * ElasticSearch field names for document type people.person.
   * 
   * @author CWDS API Team
   */
  protected enum ESColumn {
    ID("id"), FIRST_NAME("first_name"), LAST_NAME("last_name"), GENDER("gender"), BIRTH_DATE(
        "date_of_birth"), SSN("ssn"), TYPE("type"), SOURCE("source");

    /**
     * ElasticSearch column name.
     */
    private final String col;

    ESColumn(String col) {
      this.col = col;
    }

    public String text() {
      return col;
    }
  }

  @SuppressWarnings("unchecked")
  protected static <T extends Serializable> T pullField(final Map<String, Object> m,
      ESColumn f) {
    return (T) m.getOrDefault(f.text(), "");
  }

  /**
   * Produce an ESPerson domain from native ElasticSearch {@link SearchHit}. Parse JSON results and
   * populate associated fields.
   * 
   * @param hit search result
   * @return populated domain-level ES object
   */
  public static ESPerson makeESPerson(SearchHit hit) {
    final Map<String, Object> m = hit.getSource();
    return new ESPerson(
        // m.getOrDefault("id", "0").toString(),
        ESPerson.<String>pullField(m, ESColumn.ID),
        (String) m.getOrDefault(ESColumn.FIRST_NAME.text(), ""),
        (String) m.getOrDefault("last_name", ""), (String) m.getOrDefault("gender", null),
        (String) m.getOrDefault("date_of_birth", null), (String) m.getOrDefault("ssn", null),
        (String) m.getOrDefault("type", null), (String) m.getOrDefault("source", null), null);
  }

  /**
   * Trim excess whitespace from ElasticSearch results. Non-null input of all whitespace returns
   * empty String, null input returns null.
   * 
   * @param s String to trim
   * @return String trimmed of outer whitespace or null if input is null
   */
  protected static String trim(String s) {
    return s != null ? s.trim() : null;
  }

  /**
   * The identifier is String in legacy (CMS, mainframe DB2) but Long in new style (NS, PostGreSQL).
   * Therefore, the generic id here is String to accomodate all possibilities without resorting to
   * generics, untyped Object, or collections with heterogenous types. For now,
   * 
   * <p>
   * This case begs Java to add a "union" or equivalent storage technique.
   * </p>
   */
  @JsonProperty("id")
  private String id;

  /**
   * Original, fully-qualified, persistence-level source class, such
   * "gov.ca.cwds.rest.api.persistence.cms.OtherClientName".
   */
  private String sourceType;

  /**
   * Raw, nested, child document in JSON from object {@link #sourceType} and stored in ES document.
   */
  private String sourceJson;

  public ESPerson(String id, String firstName, String lastName, String gender, String birthDate,
      String ssn, Address address) {
    super(trim(firstName), trim(lastName), trim(gender), trim(birthDate), trim(ssn), address);
    this.id = id;
  }

  /**
   * Overload constructor, used to accomodate nested document members {@link #sourceType} and
   * {@link #sourceJson}.
   * 
   * @param id identifier
   * @param firstName first name
   * @param lastName last name
   * @param gender gender code
   * @param birthDate birth date
   * @param ssn SSN without dashes
   * @param sourceType fully-qualified, persistence-level source class
   * @param sourceJson raw, nested child document as JSON
   * @param address address, if any
   */
  public ESPerson(String id, String firstName, String lastName, String gender, String birthDate,
      String ssn, String sourceType, String sourceJson, Address address) {
    super(trim(firstName), trim(lastName), trim(gender), trim(birthDate), trim(ssn), address);
    this.id = id;
    this.sourceType = sourceType;
    this.sourceJson = sourceJson;
  }

  /**
   * Construct from a persistence-level, new style
   * {@link gov.ca.cwds.rest.api.persistence.ns.Person}.
   * 
   * @param person database NS person object
   */
  public ESPerson(gov.ca.cwds.rest.api.persistence.ns.Person person) {
    super(person);
    this.id = person.getId().toString();
  }

  /**
   * See comments on {@link #id}.
   * 
   * @return the id
   */
  public String getId() {
    return id;
  }

  /**
   * See comments on {@link #sourceType}.
   * 
   * @return the fully-qualified source persistence class
   */
  public String getSourceType() {
    return sourceType;
  }

  /**
   * See comments on {@link #sourceJson}.
   * 
   * @return the raw JSON of nested person document, if any
   * @see #sourceType
   */
  public String getSourceJson() {
    return sourceJson;
  }

}
