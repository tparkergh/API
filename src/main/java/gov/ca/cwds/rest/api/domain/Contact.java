package gov.ca.cwds.rest.api.domain;

import static gov.ca.cwds.data.persistence.cms.CmsPersistentObject.CMS_ID_LEN;
import static gov.ca.cwds.rest.api.domain.DomainChef.TIMESTAMP_STRICT_FORMAT;
import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.validation.ValidSystemCodeId;
import io.dropwizard.jackson.JsonSnakeCase;
import io.dropwizard.validation.OneOf;
import io.swagger.annotations.ApiModelProperty;

import java.util.Set;

import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * {@link DomainObject} representing a Contact
 * 
 * @author CWDS API Team
 */
@JsonSnakeCase
@JsonPropertyOrder({"id", "staffId", "staffName", "startedAt", "endedAt", "purpose",
    "communicationMethod", "status", "services", "location", "note", "people"})
public class Contact extends ReportingDomain implements Request, Response {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  @Size(max = CMS_ID_LEN)
  // @NotNull
  @JsonProperty("id")
  @ApiModelProperty(required = true, readOnly = true, value = "", example = "ABC1234567")
  private String id;

  @NotEmpty
  @Size(min = 3, max = 3)
  @JsonProperty("staffId")
  @ApiModelProperty(required = true, readOnly = false, value = "primary contact staff person id",
      example = "A1A")
  private String staffId;


  @NotEmpty
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = TIMESTAMP_STRICT_FORMAT)
  // datetime
  @JsonProperty("started_at")
  @gov.ca.cwds.rest.validation.Date(format = TIMESTAMP_STRICT_FORMAT, required = true)
  @ApiModelProperty(required = true, readOnly = false, value = "yyyy-MM-dd'T'HH:mm:ss.SSSZZ",
      example = "2010-04-27T23:30:14.000-0400")
  private String startedAt;

  @NotEmpty
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = TIMESTAMP_STRICT_FORMAT)
  @JsonProperty("ended_at")
  @gov.ca.cwds.rest.validation.Date(format = TIMESTAMP_STRICT_FORMAT, required = true)
  @ApiModelProperty(required = true, readOnly = false, value = "yyyy-MM-dd'T'HH:mm:ss.SSSZZ",
      example = "2010-04-28T23:30:14.000-0400")
  private String endedAt;

  @JsonProperty("purpose")
  // "service_contact" delivered service serviceContactType
  @ValidSystemCodeId(required = false, category = SystemCodeCategoryId.CONTACT_TYPE)
  @ApiModelProperty(required = false, readOnly = false,
      value = "Delivered service contact type system code ID e.g)  -> ", example = "433")
  private Integer purpose;

  @JsonProperty("communication_method")
  @ValidSystemCodeId(required = false, category = SystemCodeCategoryId.COMMUNICATION_METHOD)
  @ApiModelProperty(required = false, readOnly = false,
      value = "Delivered service communication method type system code ID e.g) 408 -> In-Person",
      example = "408")
  private Integer communicationMethod;

  @NotEmpty
  @JsonProperty("status")
  @Size(min = 1, max = 1)
  @OneOf(value = {"C", "A", "S"}, ignoreCase = false, ignoreWhitespace = true)
  @ApiModelProperty(required = false, readOnly = false, value = "", example = "C")
  private String status;


  @JsonProperty("services")
  // need clarification
  @ApiModelProperty(required = false, readOnly = false)
  private Set<Integer> services;

  @JsonProperty("location")
  // ("contact_location")
  @ValidSystemCodeId(required = false, category = SystemCodeCategoryId.CONTACT_LOCATION)
  @ApiModelProperty(required = false, readOnly = false,
      value = "Delivered service contact location type system code ID e.g) 415 -> CWS Office",
      example = "415")
  private Integer location;

  @JsonProperty("note")
  // ("detail_text")
  // a row in LONG_TEXT with content of contact_narrative
  @Size(max = 8000)
  @ApiModelProperty(required = false, readOnly = false, value = "", example = "detail text")
  private String note;

  @ApiModelProperty(required = false, readOnly = false)
  @JsonProperty("people")
  // ("contact_participants") INDIVIDUAL_DELIVERED_SERVICE
  private Set<PostedIndividualDeliveredService> people;

  /**
   * @param id id
   * @param staffId staff id
   * @param startedAt started at
   * @param endedAt ended at
   * @param purpose purpose
   * @param communicationMethod communication method
   * @param status status
   * @param services services
   * @param location location
   * @param note note
   * @param people people
   */
  @JsonCreator
  public Contact(@JsonProperty("id") String id, @JsonProperty("staff_id") String staffId,
      @JsonProperty("started_at") String startedAt, @JsonProperty("ended_at") String endedAt,
      @JsonProperty("purpose") Integer purpose,
      @JsonProperty("communication_method") Integer communicationMethod,
      @JsonProperty("status") String status, @JsonProperty("services") Set<Integer> services,
      @JsonProperty("location") Integer location, @JsonProperty("note") String note,
      @JsonProperty("people") Set<PostedIndividualDeliveredService> people) {
    super();
    this.id = id;
    this.staffId = staffId;
    this.startedAt = startedAt;
    this.endedAt = endedAt;
    this.purpose = purpose;
    this.communicationMethod = communicationMethod;
    this.status = status;
    this.services = services;
    this.location = location;
    this.note = note;
    this.people = people;
  }



  /**
   * @return the id
   */
  public String getId() {
    return id;
  }



  /**
   * @return the staffId
   */
  public String getStaffId() {
    return staffId;
  }



  /**
   * @return the startedAt
   */
  public String getStartedAt() {
    return startedAt;
  }



  /**
   * @return the endedAt
   */
  public String getEndedAt() {
    return endedAt;
  }



  /**
   * @return the purpose
   */
  public Integer getPurpose() {
    return purpose;
  }



  /**
   * @return the communicationMethod
   */
  public Integer getCommunicationMethod() {
    return communicationMethod;
  }



  /**
   * @return the status
   */
  public String getStatus() {
    return status;
  }



  /**
   * @return the services
   */
  public Set<Integer> getServices() {
    return services;
  }



  /**
   * @return the location
   */
  public Integer getLocation() {
    return location;
  }



  /**
   * @return the note
   */
  public String getNote() {
    return note;
  }



  /**
   * @return the people
   */
  public Set<PostedIndividualDeliveredService> getPeople() {
    return people;
  }



  /**
   * {@inheritDoc}
   *
   * @see java.lang.Object#hashCode()
   */
  @Override
  public final int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this, false);
  }

  /**
   * {@inheritDoc}
   *
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public final boolean equals(Object obj) {
    return EqualsBuilder.reflectionEquals(this, obj, false);
  }

}
