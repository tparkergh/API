package gov.ca.cwds.rest.api.domain.es;

import com.fasterxml.jackson.annotation.JsonProperty;

import gov.ca.cwds.rest.api.domain.Address;
import gov.ca.cwds.rest.api.domain.Person;

/**
 * Generic person class for Elasticsearch results, which decorates {@link Person} with a String id.
 * 
 * @author CWDS API Team
 */
public class ESPerson extends Person {

  @JsonProperty("id")
  private String id;

  /**
   * Original persistence source class.
   */
  private String sourceType;

  /**
   * Raw JSON from {@link #sourceType} and stored in ES document.
   */
  private String sourceJson;

  public ESPerson(String id, String first_name, String last_name, String gender,
      String date_of_birth, String ssn, Address address) {
    super(trim(first_name), trim(last_name), trim(gender), trim(date_of_birth), trim(ssn), address);
    this.id = id;
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
   * The identifier is String in legacy (CMS, mainframe DB2) but Long in new style (NS, PostGreSQL).
   * Therefore, the generic id here is String to accomodate all possibilities without resorting to
   * generics, untyped Object, or collections with heterogenous types. For now,
   * 
   * <p>
   * This case begs Java to add a "union" or equivalent storage technique.
   * </p>
   * 
   * @return the id
   */
  public String getId() {
    return id;
  }

  protected static String trim(String s) {
    return s != null ? s.trim() : null;
  }

  public String getSourceType() {
    return sourceType;
  }

  public void setSourceType(String sourceType) {
    this.sourceType = sourceType;
  }

  public String getSourceJson() {
    return sourceJson;
  }

  public void setSourceJson(String sourceJson) {
    this.sourceJson = sourceJson;
  }

}
