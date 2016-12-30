package gov.ca.cwds.rest.api.domain;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import gov.ca.cwds.rest.api.Response;
import io.dropwizard.jackson.JsonSnakeCase;

/**
 * {@link Response} adding an id to the {@link ScreeningResponse}
 * 
 * @author CWDS API Team
 */
@JsonSnakeCase
@JsonInclude(Include.ALWAYS)
public class PostedScreening extends ScreeningResponse {

  @JsonProperty("id")
  private long id;

  /**
   * Default no-op constructor
   */
  public PostedScreening() {
    super();
  }

  /**
   * Constructor
   * 
   * @param id The id
   * @param reference The reference
   * @param endedAt The ended at
   * @param incidentCounty The incident county
   * @param incidentDate The incident date
   * @param locationType The location type
   * @param communicationMethod The communication method
   * @param name The name
   * @param responseTime The response time
   * @param screeningDecision The screening decision
   * @param startedAt The started at
   * @param narrative The narrative
   * @param address The {@link Address}
   * @param participants The {@link Set}
   */
  public PostedScreening(long id, String reference, String endedAt, String incidentCounty,
      String incidentDate, String locationType, String communicationMethod, String name,
      String responseTime, String screeningDecision, String startedAt, String narrative,
      Address address, Set<Person> participants) {
    super(reference, endedAt, incidentCounty, incidentDate, locationType, communicationMethod, name,
        responseTime, screeningDecision, startedAt, narrative, address, participants);
    this.id = id;
  }

  /**
   * Constructor
   * 
   * @param id The id
   * @param reference The reference
   */
  public PostedScreening(long id, String reference) {
    super(reference);
    this.id = id;
  }

  /**
   * @return the id
   */
  public long getId() {
    return id;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + (int) (id ^ (id >>> 32));
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (!super.equals(obj))
      return false;

    if (!(obj instanceof PostedScreening)) {
      return false;
    }

    PostedScreening other = (PostedScreening) obj;
    if (id != other.id)
      return false;
    return true;
  }

}
