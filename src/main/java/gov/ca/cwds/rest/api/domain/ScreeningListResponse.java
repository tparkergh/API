package gov.ca.cwds.rest.api.domain;

import gov.ca.cwds.rest.api.Response;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * {@link DomainObject} representing a screening list response
 * 
 * @author CWDS API Team
 */
@JsonInclude(Include.ALWAYS)
public class ScreeningListResponse implements Response {

  @JsonProperty("screenings")
  private Set<ScreeningResponse> screeningResponses;

  /**
   * 
   */
  public ScreeningListResponse() {
    // default
  }


  public ScreeningListResponse(Set<ScreeningResponse> screeningResponses) {
    this.screeningResponses = screeningResponses;
  }

}
