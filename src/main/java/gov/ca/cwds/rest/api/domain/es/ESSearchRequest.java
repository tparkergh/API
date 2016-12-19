package gov.ca.cwds.rest.api.domain.es;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.domain.DomainObject;
import io.dropwizard.jackson.JsonSnakeCase;

/**
 * Domain request class for ElasticSearch.
 * 
 * @author CWDS API Team
 */
@JsonSnakeCase
public class ESSearchRequest extends DomainObject implements Request {

  public static final Method getAnyMethod(Class<?> cls, String name, Class<?>[] argTypes)
      throws NoSuchMethodException {
    try {
      return cls.getDeclaredMethod(name, argTypes);
    } catch (NoSuchMethodException ex) {
      final Class<?>[] clses = cls.getInterfaces();
      for (int j = 0; j < clses.length; ++j)
        try {
          return getAnyMethod(clses[j], name, argTypes);
        } catch (NoSuchMethodException e2) {
          // Ignore.
        }

      cls = cls.getSuperclass();
      if (cls == null)
        throw ex;
      return getAnyMethod(cls, name, argTypes);
    }
  }

  // ================
  // ENUMS:
  // ================

  /**
   * ElasticSearch 2.x+ prefers MUST/MUST_NOT/SHOULD over AND/OR/NOT. In fact, the latter support
   * classes have been deprecated.
   * 
   * <p>
   * The original idea behind this class was to nest logical operations like normal database WHERE
   * calls, but ES no longer prefers that traditional approach.
   * </p>
   */
  public enum LogicalOperation {
    AND(1, "AND"), OR(2, "OR"), NOT(3, "OR"), MUST(4, "MUST"), MUST_NOT(5, "MUST_NOT"), SHOULD(6,
        "SHOULD");

    private final int value;
    private final String text;

    LogicalOperation(int value, String text) {
      this.value = value;
      this.text = text;
    }

    public int value() {
      return value;
    }

    public String text() {
      return text;
    }
  }

  public enum QueryType {
    MATCH(1, "MATCH"), TERM(2, "TERM"), FUZZY(3, "FUZZY"), RANGE(4, "RANGE"), SIMPLE(5,
        "SIMPLE"), REGEXP(6, "REGEXP"), WILDCARD(7, "WILDCARD"), ALL(8, "ALL");

    private final int value;
    private final String text;

    QueryType(int value, String text) {
      this.value = value;
      this.text = text;
    }

    public int value() {
      return value;
    }

    public String text() {
      return text;
    }
  }

  public enum ElementType {
    GROUP, FIELD_TERM
  }

  // ================
  // INNER CLASSES:
  // ================

  /**
   * Node in a tree of groupings and search terms.
   * 
   * @author CWDS API Team
   */
  @JsonSnakeCase
  public static interface ESSearchElement {
    ElementType getElementType();
  }

  /**
   * Logical grouping node, contains any number of search terms or groups.
   * 
   * @author CWDS API Team
   */
  @JsonSnakeCase
  public static class ESSearchGroup implements ESSearchElement {
    private LogicalOperation logic = LogicalOperation.OR;
    private List<ESSearchElement> elems = new ArrayList<ESSearchElement>();

    public ESSearchGroup() {}

    public ESSearchGroup(LogicalOperation logic, List<ESSearchElement> elems) {
      super();
      this.logic = logic;
      this.elems = elems;
    }

    public ESSearchGroup(LogicalOperation logic) {
      super();
      this.logic = logic;
    }

    public LogicalOperation getLogic() {
      return logic;
    }

    public void setLogic(LogicalOperation logic) {
      this.logic = logic;
    }

    public List<ESSearchElement> getElems() {
      return elems;
    }

    public void setElems(List<ESSearchElement> elems) {
      this.elems = elems;
    }

    public void addElem(ESSearchElement elem) {
      elems.add(elem);
    }

    @Override
    public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((elems == null) ? 0 : elems.hashCode());
      result = prime * result + ((logic == null) ? 0 : logic.hashCode());
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

      ESSearchGroup other = (ESSearchGroup) obj;
      if (elems == null) {
        if (other.elems != null)
          return false;
      } else if (!elems.equals(other.elems))
        return false;
      if (logic != other.logic)
        return false;
      return true;
    }

    @Override
    public ElementType getElementType() {
      return ElementType.GROUP;
    }

  }

  /**
   * Individual search term, comprised of field name, value to search, and query type.
   * 
   * @author CWDS API Team
   */
  @JsonSnakeCase
  public static class ESFieldSearchEntry implements ESSearchElement {

    /**
     * Document field to search.
     */
    private String field;

    /**
     * Value to search for.
     */
    private String value;

    @JsonProperty("query_type")
    private QueryType queryType = QueryType.ALL;

    public ESFieldSearchEntry() {}

    public ESFieldSearchEntry(String field, String value, QueryType queryType) {
      super();
      this.field = field;
      this.value = value;
      this.queryType = queryType;
    }

    @JsonCreator
    public ESFieldSearchEntry(@JsonProperty("field") String field,
        @JsonProperty("value") String value) {
      super();
      this.field = field;
      this.value = value;
    }

    public String getField() {
      return field;
    }

    public String getValue() {
      return value;
    }

    public QueryType getQueryType() {
      return queryType;
    }

    public void setField(String field) {
      this.field = field;
    }

    public void setValue(String value) {
      this.value = value;
    }

    public void setQueryType(QueryType queryType) {
      this.queryType = queryType;
    }

    @Override
    public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((field == null) ? 0 : field.hashCode());
      result = prime * result + ((queryType == null) ? 0 : queryType.hashCode());
      result = prime * result + ((value == null) ? 0 : value.hashCode());
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
      ESFieldSearchEntry other = (ESFieldSearchEntry) obj;
      if (field == null) {
        if (other.field != null)
          return false;
      } else if (!field.equals(other.field))
        return false;
      if (queryType != other.queryType)
        return false;
      if (value == null) {
        if (other.value != null)
          return false;
      } else if (!value.equals(other.value))
        return false;
      return true;
    }

    @Override
    public ElementType getElementType() {
      return ElementType.FIELD_TERM;
    }

  }

  // ================
  // MEMBERS:
  // ================

  @JsonProperty("cwds_search")
  private ESSearchGroup root = new ESSearchGroup();

  @JsonProperty("document_type")
  private String documentType = "person";

  // ================
  // CTORS:
  // ================

  public ESSearchRequest() {}

  // ================
  // ACCESSORS:
  // ================

  public ESSearchGroup getRoot() {
    return root;
  }

  public void setRoot(ESSearchGroup root) {
    this.root = root;
  }

  public String getDocumentType() {
    return documentType;
  }

  public void setDocumentType(String documentType) {
    this.documentType = documentType;
  }

  // ================
  // COMPARISON:
  // ================

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((root == null) ? 0 : root.hashCode());
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
    ESSearchRequest other = (ESSearchRequest) obj;
    if (root == null) {
      if (other.root != null)
        return false;
    } else if (!root.equals(other.root))
      return false;
    return true;
  }

}
