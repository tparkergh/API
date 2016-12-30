package gov.ca.cwds.rest.api.persistence.cms;

import java.io.Serializable;
import java.util.Arrays;

/**
 * @author CWDS API Team
 *
 */
public final class VarargPrimaryKey implements Serializable {

  private static final long serialVersionUID = 1L;

  private final String concatKey;

  private final String[] columns;

  private VarargPrimaryKey() {
    this.concatKey = "";
    this.columns = new String[0];
  }

  /**
   * @param values String of key values
   */
  public VarargPrimaryKey(String... values) {
    StringBuilder buf = new StringBuilder();
    this.columns = new String[values.length];
    int cntr = -1;
    for (String v : values) {
      if (v != null) {
        String v2 = v.trim();
        this.columns[++cntr] = v2;
        if (v2.length() > 0) {
          buf.append(v2);
        } else {
          buf.append(' ');
        }
      } else {
        buf.append(' ');
      }
    }

    this.concatKey = buf.toString();
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return "concatKey" + concatKey;
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
    result = prime * result + ((concatKey == null) ? 0 : concatKey.hashCode());
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
    if (!Arrays.equals(columns, other.columns))
      return false;
    if (concatKey == null) {
      if (other.concatKey != null)
        return false;
    } else if (!concatKey.equals(other.concatKey))
      return false;
    return true;
  }

}
