package gov.ca.cwds.rest.api.persistence.cms;

import java.io.Serializable;

public class PrimaryKey3 implements Serializable {
  private static final long serialVersionUID = 1L;
  private String keySegmentOne;
  private String keySegmentTwo;
  private String keySegmentThree;

  public PrimaryKey3() {
    super();
  }

  public PrimaryKey3(String keySegmentOne, String keySegmentTwo, String keySegmentThree) {
    this.keySegmentOne = keySegmentOne;
    this.keySegmentTwo = keySegmentTwo;
    this.keySegmentThree = keySegmentThree;
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return "keySegmentOne=" + keySegmentOne.trim() + ",keySegmentTwo=" + keySegmentTwo.trim()
        + ",keySegmentThree=" + keySegmentThree.trim();
  }

  public String getKeySegmentOne() {
    return keySegmentOne;
  }

  public String getKeySegmentTwo() {
    return keySegmentTwo;
  }

  public String getKeySegmentThree() {
    return keySegmentThree;
  }


  public static long getSerialversionuid() {
    return serialVersionUID;
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.lang.Object#hashCode()
   */
  @Override
  public final int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((keySegmentOne == null) ? 0 : keySegmentOne.hashCode());
    result = prime * result + ((keySegmentTwo == null) ? 0 : keySegmentTwo.hashCode());
    result = prime * result + ((keySegmentThree == null) ? 0 : keySegmentThree.hashCode());
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
    if (!(obj instanceof PrimaryKey3)) {
      return false;
    }
    if (!(obj instanceof PrimaryKey3)) {
      return false;
    }
    PrimaryKey3 other = (PrimaryKey3) obj;

    if (keySegmentOne == null) {
      if (other.keySegmentOne != null)
        return false;
    } else if (!keySegmentOne.equals(other.keySegmentOne))
      return false;

    if (keySegmentTwo == null) {
      if (other.keySegmentTwo != null)
        return false;
    } else if (!keySegmentTwo.equals(other.keySegmentTwo))
      return false;

    if (keySegmentThree == null) {
      if (other.keySegmentThree != null)
        return false;
    } else if (!keySegmentThree.equals(other.keySegmentThree))
      return false;

    return true;
  }

}
