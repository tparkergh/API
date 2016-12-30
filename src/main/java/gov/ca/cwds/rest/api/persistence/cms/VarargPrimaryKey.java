package gov.ca.cwds.rest.api.persistence.cms;

import java.io.Serializable;
import java.util.Arrays;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

public final class VarargPrimaryKey implements Serializable {

  private static final long serialVersionUID = 1L;

  private final String[] columns;

  /**
   * Hide the default constructor.
   */
  @SuppressWarnings("unused")
  private VarargPrimaryKey() {
    this.columns = new String[0];
  }

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
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + Arrays.hashCode(columns);
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

    VarargPrimaryKey other = (VarargPrimaryKey) obj;
    return Arrays.equals(columns, other.columns);
  }

  protected final String getPosition(int pos) {
    return this.columns.length > pos ? this.columns[pos] : null;
  }

}
