package gov.ca.cwds.rest.api.domain.cms;

import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.DomainObject;
import gov.ca.cwds.rest.api.domain.ReportingDomain;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * {@link DomainObject} representing a System Meta list response
 * 
 * @author CWDS API Team
 */
@JsonInclude(Include.ALWAYS)
public class SystemMetaListResponse extends ReportingDomain implements Response {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  @JsonProperty("system_metas")
  private Set<SystemMeta> systemMetas;

  /**
   * 
   */
  public SystemMetaListResponse() {
    // default
  }


  public SystemMetaListResponse(Set<SystemMeta> systemMetas) {
    this.systemMetas = systemMetas;
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
    SystemMetaListResponse other = (SystemMetaListResponse) obj;
    if (systemMetas == null) {
      if (other.systemMetas != null) {
        return false;
      }
    } else if (!systemMetas.equals(other.systemMetas)) {
      return false;
    }
    return true;
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
    result = prime * result + ((systemMetas == null) ? 0 : systemMetas.hashCode());
    return result;
  }

}
