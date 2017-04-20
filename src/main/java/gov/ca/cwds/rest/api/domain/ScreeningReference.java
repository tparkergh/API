package gov.ca.cwds.rest.api.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import gov.ca.cwds.rest.api.Request;
import io.dropwizard.jackson.JsonSnakeCase;

/**
 * A {@link Request} used to create an initial {@link Screening}
 * 
 * @author CWDS API Team
 */
@JsonSnakeCase
public class ScreeningReference extends DomainObject implements Request {
  /**
   * Serialization version
   */
  private static final long serialVersionUID = 1L;
  private String reference;

  /**
   * Constructor
   * 
   * @param reference The reference
   */
  @JsonCreator
  public ScreeningReference(@JsonProperty("reference") String reference) {
    super();
    this.reference = reference;
  }

  /**
   * @return the reference
   */
  public String getReference() {
    return reference;
  }



  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#hashCode()
   */
  @Override
  public final int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((reference == null) ? 0 : reference.hashCode());
    return result;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public final boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (!(getClass().isInstance(obj)))
      return false;
    ScreeningReference other = (ScreeningReference) obj;
    if (reference == null) {
      if (other.reference != null)
        return false;
    } else if (!reference.equals(other.reference))
      return false;
    return true;
  }


}
