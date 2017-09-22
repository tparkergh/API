package gov.ca.cwds.rest.api.domain.investigation.contact;

import static gov.ca.cwds.data.persistence.cms.CmsPersistentObject.CMS_ID_LEN;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.validator.constraints.NotEmpty;
import org.jadira.usertype.spi.utils.lang.StringUtils;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import gov.ca.cwds.data.persistence.contact.DeliveredServiceEntity;
import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.DomainChef;
import gov.ca.cwds.rest.api.domain.DomainObject;
import gov.ca.cwds.rest.api.domain.LastUpdatedBy;
import gov.ca.cwds.rest.api.domain.PostedIndividualDeliveredService;
import gov.ca.cwds.rest.api.domain.ReportingDomain;
import gov.ca.cwds.rest.api.domain.SystemCodeCategoryId;
import gov.ca.cwds.rest.validation.ValidSystemCodeId;
import io.dropwizard.jackson.JsonSnakeCase;
import io.dropwizard.validation.OneOf;
import io.swagger.annotations.ApiModelProperty;

/**
 * {@link DomainObject} representing a Contact
 * 
 * @author CWDS API Team
 */
@JsonSnakeCase
@JsonPropertyOrder({"id", "lastUpdatedBy", "staffName", "startedAt", "endedAt", "purpose",
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

  @JsonProperty("last_updated_by")
  @ApiModelProperty(required = false, readOnly = false)
  // , value = "primary contact staff person id")
  private LastUpdatedBy lastUpdatedBy;


  @NotEmpty
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
  // datetime
  @JsonProperty("started_at")
  @gov.ca.cwds.rest.validation.Date(format = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", required = true)
  // "2017-08-28T14:30:00.000Z"
  @ApiModelProperty(required = true, readOnly = false, value = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
      example = "2010-04-27T23:30:14.000Z")
  private String startedAt;

  @NotEmpty
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
  @JsonProperty("ended_at")
  @gov.ca.cwds.rest.validation.Date(format = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", required = true)
  @ApiModelProperty(required = true, readOnly = false, value = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
      example = "2010-04-28T23:30:14.000Z")
  private String endedAt;

  @JsonProperty("purpose")
  // "service_contact" delivered service serviceContactType
  @ValidSystemCodeId(required = false, category = SystemCodeCategoryId.CONTACT_TYPE)
  @ApiModelProperty(required = false, readOnly = false,
      value = "Delivered service contact type system code ID e.g)  -> 433 Conduct Client Evaluation ",
      example = "433")
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
   * @param lastUpdatedBy last updated by staff
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
  public Contact(@JsonProperty("id") String id,
      @JsonProperty("last_updated_by") LastUpdatedBy lastUpdatedBy,
      @JsonProperty("started_at") String startedAt, @JsonProperty("ended_at") String endedAt,
      @JsonProperty("purpose") Integer purpose,
      @JsonProperty("communication_method") Integer communicationMethod,
      @JsonProperty("status") String status, @JsonProperty("services") Set<Integer> services,
      @JsonProperty("location") Integer location, @JsonProperty("note") String note,
      @JsonProperty("people") Set<PostedIndividualDeliveredService> people) {
    super();
    this.id = id;
    this.lastUpdatedBy = lastUpdatedBy;
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
   * @param persistedDeliverdService - persistedDeliverdService
   * @param lastUpdatedBy - lastUpdatedBy
   * @param note - note
   * @param people - people
   */
  public Contact(DeliveredServiceEntity persistedDeliverdService, LastUpdatedBy lastUpdatedBy,
      String note, Set<PostedIndividualDeliveredService> people) {
    super();
    this.id = persistedDeliverdService.getId();
    this.lastUpdatedBy = lastUpdatedBy;
    String startDate = DomainChef.cookDate(persistedDeliverdService.getStartDate());
    if (StringUtils.isNotEmpty(startDate)) {
      this.startedAt = startDate + "T" + cookTime(persistedDeliverdService.getStartTime()) + "Z";
    }
    String endDate = DomainChef.cookDate(persistedDeliverdService.getEndDate());
    if (StringUtils.isNotEmpty(endDate)) {
      this.endedAt = endDate + "T" + cookTime(persistedDeliverdService.getEndTime()) + "Z";
    }
    this.purpose = Integer.valueOf(persistedDeliverdService.getServiceContactType());
    this.communicationMethod =
        Integer.valueOf(persistedDeliverdService.getCommunicationMethodType());
    this.status = persistedDeliverdService.getStatusCode();
    this.services = null;
    this.location = Integer.valueOf(persistedDeliverdService.getContactLocationType());
    this.note = note;
    this.people = people;
  }

  public Contact() {
    // default
  }

  /**
   * @param date date to cook
   * @return String in TIME_FORMAT
   */
  public static String cookTime(Date date) {
    if (date != null) {
      DateFormat df = new SimpleDateFormat("HH:mm:ss.SSS");
      return df.format(date);
    }
    return "00:00:00.000";
  }

  /**
   * @return the id
   */
  public String getId() {
    return id;
  }



  /**
   * @return the lastUpdatedBy
   */
  public LastUpdatedBy getLastUpdatedBy() {
    return lastUpdatedBy;
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
