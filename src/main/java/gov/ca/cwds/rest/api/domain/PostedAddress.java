package gov.ca.cwds.rest.api.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import gov.ca.cwds.rest.api.Response;
import io.dropwizard.jackson.JsonSnakeCase;

/**
 * {@link Response} adding an id to the {@link Address}
 * 
 * @author CWDS API Team
 */
@JsonSnakeCase
public class PostedAddress extends Address {
  /**
   * Serialization version
   */
  private static final long serialVersionUID = 1L;
  @JsonProperty("id")
  private long id;

  /**
   * Constructor
   * 
   * @param id The id
   * @param legacySourceTable - legacy source table name
   * @param legacyId - legacy Id
   * @param streetAddress The street address
   * @param city The city
   * @param state The state
   * @param zip The zip
   * @param type - address type
   */
  public PostedAddress(long id, String legacySourceTable, String legacyId, String streetAddress,
      String city, Integer state, String zip, Integer type) {
    super(legacySourceTable, legacyId, streetAddress, city, state, zip, type);
    this.id = id;
  }

  /**
   * Constructor
   * 
   * @param address The persisted address
   */
  public PostedAddress(gov.ca.cwds.data.persistence.ns.Address address) {
    super(address);
    assert address.getId() != null;
    this.id = address.getId();
  }

  /**
   * @return the id
   */
  public long getId() {
    return id;
  }

}
