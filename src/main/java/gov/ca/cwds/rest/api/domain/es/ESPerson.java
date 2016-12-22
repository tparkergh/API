package gov.ca.cwds.rest.api.domain.es;

import java.io.Serializable;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.search.SearchHit;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

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

  private static final ObjectMapper MAPPER;

  static {
    ObjectMapper mapper = new ObjectMapper();
    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    MAPPER = mapper;
  }

  /**
   * ElasticSearch field names for document type people.person.
   * 
   * @author CWDS API Team
   */
  enum ESColumn {
    ID("id", String.class, ""), FIRST_NAME("first_name", String.class, ""), LAST_NAME("last_name",
        String.class, ""), GENDER("gender", String.class, "U"), BIRTH_DATE("date_of_birth",
            String.class, null), SSN("ssn", String.class,
                null), TYPE("type", String.class, null), SOURCE("source", String.class, null);

    /**
     * ElasticSearch column name.
     */
    final String col;

    /**
     * Value's data type as a Java Class.
     */
    final Class<? extends Serializable> klazz;

    /**
     * Default value, if no value is provided.
     */
    final Object defaultVal;

    /**
     * Enum constructor populates final members.
     * 
     * @param col ES column name
     * @param klazz Java Class of value
     * @param defaultVal default value. Must be assignable from {@link #klazz}.
     */
    ESColumn(String col, Class<? extends Serializable> klazz, Object defaultVal) {
      this.col = col;
      this.klazz = klazz;
      this.defaultVal = defaultVal;
    }

    public String getCol() {
      return col;
    }

    public Class<? extends Serializable> getKlazz() {
      return klazz;
    }

    public Object getDefaultVal() {
      return defaultVal;
    }

  }

  /**
   * Extract field's value from an ElasticSearch result document {@link Map} (key: field name),
   * using the field's ES column name and data type.
   * 
   * @param <T> expected data type
   * @param m ES result map
   * @param f field to extract
   * @return field value as specified type T
   */
  @SuppressWarnings("unchecked")
  protected static <T extends Serializable> T pullData(final Map<String, Object> m, ESColumn f) {
    return (T) f.klazz.cast(m.getOrDefault(f.col, f.defaultVal));
  }

  /**
   * Produce an ESPerson domain from native ElasticSearch {@link SearchHit}. Parse JSON results and
   * populate associated fields.
   * 
   * @param hit search result
   * @return populated domain-level ES object
   * @see #pullData(Map, ESColumn)
   */
  public static ESPerson makeESPerson(SearchHit hit) {
    final Map<String, Object> m = hit.getSource();
    ESPerson ret = new ESPerson(ESPerson.<String>pullData(m, ESColumn.ID),
        ESPerson.<String>pullData(m, ESColumn.FIRST_NAME),
        ESPerson.<String>pullData(m, ESColumn.LAST_NAME),
        ESPerson.<String>pullData(m, ESColumn.GENDER),
        ESPerson.<String>pullData(m, ESColumn.BIRTH_DATE),
        ESPerson.<String>pullData(m, ESColumn.SSN), ESPerson.<String>pullData(m, ESColumn.TYPE),
        ESPerson.<String>pullData(m, ESColumn.SOURCE), null);

    if (!StringUtils.isBlank(ret.getSourceType()) && !StringUtils.isBlank(ret.getSourceJson())) {
      try {
        // When running in an application server, the root classloader may not know of our
        // domain/persistence class, and so we look it up using this thread's classloader.
        final Object obj = MAPPER.readValue(ret.getSourceJson(), Class.forName(ret.getSourceType(),
            false, Thread.currentThread().getContextClassLoader()));

        ret.sourceObj = obj;
      } catch (Exception e) {
        throw new RuntimeException("Failed to instantiate class " + ret.getSourceType(), e);
      }
    }

    return ret;
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
   * Java lacks "union", polymorphic return types, and true template.
   * </p>
   */
  @JsonProperty("id")
  private String id;

  /**
   * Original, fully-qualified, persistence-level source class, such
   * "gov.ca.cwds.rest.api.persistence.cms.OtherClientName".
   */
  @JsonProperty("type")
  private String sourceType;

  /**
   * Raw, nested, child document in JSON from object {@link #sourceType} and stored in ES document.
   */
  @JsonProperty("source")
  @JsonIgnore
  private String sourceJson;

  /**
   * Nested document, instantiated through reflection from Class type {@link #sourceType} and JSON
   * {@link #sourceJson}.
   */
  private Object sourceObj;

  /**
   * Overload constructor.
   * 
   * @param id unique identifier
   * @param firstName The first name
   * @param lastName The last name
   * @param gender The gender
   * @param birthDate The date of birth
   * @param ssn The ssn
   * @param address The address
   */
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
  @JsonIgnore
  public String getSourceJson() {
    return sourceJson;
  }

  public Object getSourceObj() {
    return sourceObj;
  }

}
