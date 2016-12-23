package gov.ca.cwds.rest.api.persistence.cms;

import java.io.Serializable;

public class VarargPrimaryKey implements Serializable {

  private static final long serialVersionUID = 1L;

  private String concatKey;

  public VarargPrimaryKey() {}

  public VarargPrimaryKey(String... values) {

    StringBuilder buf = new StringBuilder();
    for (String v : values) {
      if (v != null) {
        String v2 = v.trim();
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
   * {@inheritDoc}
   * 
   * @see java.lang.Object#hashCode()
   */
  @Override
  public final int hashCode() {
    final int prime = 43;
    int result = 1;
    result = prime * result + ((concatKey == null) ? 0 : concatKey.hashCode());
    return result;
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public final boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (!(obj instanceof VarargPrimaryKey)) {
      return false;
    }

    VarargPrimaryKey other = (VarargPrimaryKey) obj;

    if (concatKey == null) {
      if (other.concatKey != null)
        return false;
    } else if (!concatKey.equals(other.concatKey))
      return false;

    return true;
  }

}
