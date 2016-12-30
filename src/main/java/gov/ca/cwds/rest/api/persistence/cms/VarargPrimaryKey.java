package gov.ca.cwds.rest.api.persistence.cms;

import java.io.Serializable;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Generic class to represent composite, multi-column primary keys and unique constraints.
 * 
 * @author CWDS API Team
 */
public class VarargPrimaryKey implements Serializable {

  private static final long serialVersionUID = 1L;

  private final String[] columns;

  /**
   * Hide the default constructor.
   */
  @SuppressWarnings("unused")
  private VarargPrimaryKey() {
    this.columns = new String[0];
  }

  /**
   * @param values any number of String keys
   */
  public VarargPrimaryKey(String... values) {
    this.columns = new String[values.length];
    int cntr = -1;
    for (String v : values) {
      this.columns[++cntr] = !StringUtils.isBlank(v) ? v.trim() : " ";
    }
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return "varKey_" + ArrayUtils.toString(this.columns, "<null>").replace(',', '_');
  }

  /**
   * 
   * @return String array of key columns
   */
  public String[] getColumns() {
    return columns;
  }

  @Override
  public final int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this, false);
  }

  @Override
  public final boolean equals(Object obj) {
    return EqualsBuilder.reflectionEquals(this, obj, false);
  }

  /**
   * Returns a column value by position.
   * 
   * @param pos zero-based column position
   * @return value in specified column
   */
  public final String getPosition(int pos) {
    return this.columns.length > pos ? this.columns[pos] : null;
  }

}
