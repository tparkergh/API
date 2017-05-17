package gov.ca.cwds.rest.api.domain.cms;

import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.DomainObject;
import gov.ca.cwds.rest.api.domain.ReportingDomain;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * {@link DomainObject} representing a system code list response
 * 
 * @author CWDS API Team
 */
@JsonInclude(Include.ALWAYS)
public class SystemCodeListResponse extends ReportingDomain implements Response {

  @JsonProperty("system_codes")
  private Set<SystemCode> systemCodes;

  /**
   * 
   */
  public SystemCodeListResponse() {
    // default
  }


  public SystemCodeListResponse(Set<SystemCode> systemCodes) {
    this.systemCodes = systemCodes;
  }


  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((systemCodes == null) ? 0 : systemCodes.hashCode());
    return result;
  }


  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    SystemCodeListResponse other = (SystemCodeListResponse) obj;
    if (systemCodes == null) {
      if (other.systemCodes != null) {
        return false;
      }
    } else if (!systemCodes.equals(other.systemCodes)) {
      return false;
    }
    return true;
  }



}
