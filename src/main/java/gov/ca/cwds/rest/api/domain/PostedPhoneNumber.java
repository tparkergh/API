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
public class PostedPhoneNumber extends PhoneNumber {

  @JsonProperty("id")
  private long id;

  /**
   * Constructor
   * 
   * @param id The id
   * @param number - phone number
   * @param type - phone number type
   */
  public PostedPhoneNumber(long id, String number, String type) {
    super(number, type);
    this.id = id;
  }

  /**
   * Constructor
   * 
   * @param phoneNumber The persisted phoneNumber
   */
  public PostedPhoneNumber(gov.ca.cwds.data.persistence.ns.PhoneNumber phoneNumber) {
    super(phoneNumber);
    assert phoneNumber.getId() != null;
    this.id = phoneNumber.getId();
  }

  /**
   * @return the id
   */
  public long getId() {
    return id;
  }
}
