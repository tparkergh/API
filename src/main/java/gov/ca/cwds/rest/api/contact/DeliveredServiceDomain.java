package gov.ca.cwds.rest.api.contact;

import static gov.ca.cwds.data.persistence.cms.CmsPersistentObject.CMS_ID_LEN;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.DomainChef;
import gov.ca.cwds.rest.api.domain.DomainObject;
import gov.ca.cwds.rest.api.domain.ReportingDomain;
import gov.ca.cwds.rest.api.domain.SystemCodeCategoryId;
import gov.ca.cwds.rest.validation.ValidSystemCodeId;
import io.dropwizard.validation.OneOf;
import io.swagger.annotations.ApiModelProperty;

/**
 * {@link DomainObject} representing an DeliveredSevice
 * 
 * @author CWDS API Team
 */
public class DeliveredServiceDomain extends ReportingDomain implements Request, Response {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  @Size(max = CMS_ID_LEN)
  @ApiModelProperty(required = true, readOnly = true, value = "", example = "ABC1234567")
  private String id;

  @JsonProperty("cft_lead_agency_type")
  @ApiModelProperty(required = false, readOnly = false, example = "4212")
  private Short cftLeadAgencyType;

  @JsonProperty("core_service")
  @ApiModelProperty(required = false, readOnly = false)
  private Boolean coreServiceIndicator;

  @ValidSystemCodeId(required = false, category = SystemCodeCategoryId.COMMUNICATION_METHOD)
  @ApiModelProperty(required = false, readOnly = false,
      value = "Delivered service communication method type system code ID e.g) 408 -> In-Person",
      example = "408")
  private Integer communicationMethodType;

  @ValidSystemCodeId(required = false, category = SystemCodeCategoryId.CONTACT_LOCATION)
  @ApiModelProperty(required = false, readOnly = false,
      value = "Delivered service contact location type system code ID e.g) 415 -> CWS Office",
      example = "415")
  private Integer contactLocationType;

  @NotEmpty
  @Size(max = 1)
  @OneOf(value = {"C", "N", "V"}, ignoreCase = false, ignoreWhitespace = true)
  @ApiModelProperty(required = true, readOnly = false, value = "", example = "C")
  private String contactVisitCode;

  @NotEmpty
  @Size(min = 2, max = 2)
  @ApiModelProperty(required = true, readOnly = false, value = "", example = "99")
  private String countySpecificCode;

  @Size(max = 32700)
  @ApiModelProperty(required = false, readOnly = false, value = "",
      example = "detail text of the contact")
  private String detailText;

  @Size(max = 32700)
  @ApiModelProperty(required = false, readOnly = false, value = "",
      example = "continued detailed text")
  private String detailTextContinuation;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_FORMAT)
  @JsonProperty(value = "end_Date")
  @gov.ca.cwds.rest.validation.Date(format = DATE_FORMAT, required = true)
  @ApiModelProperty(required = false, readOnly = false, value = "yyyy-MM-dd",
      example = "2000-01-01")
  private String endDate;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = TIME_FORMAT)
  @JsonProperty(value = "end_Time")
  @gov.ca.cwds.rest.validation.Date(format = TIME_FORMAT, required = false)
  @ApiModelProperty(required = false, readOnly = false, value = "Assignment end time",
      example = "16:41:49")
  private String endTime;

  @Size(min = CMS_ID_LEN, max = CMS_ID_LEN)
  @ApiModelProperty(required = false, readOnly = false, value = "", example = "ABC1234567")
  private String primaryDeliveredServiceId;

  @NotEmpty
  @Size(min = 1, max = 1)
  @OneOf(value = {"N", "U", "Y"}, ignoreCase = false, ignoreWhitespace = true)
  @ApiModelProperty(required = false, readOnly = false, value = "", example = "N")
  private String hardCopyDocumentOnFileCode;

  @Size(max = 100)
  @ApiModelProperty(required = false, readOnly = false, value = "",
      example = "Attorney, Doctor and etc")
  private String otherParticipantsDesc;

  @Size(min = 1, max = 1)
  @OneOf(value = {"S", "O", "V", "X"}, ignoreCase = false, ignoreWhitespace = true)
  @ApiModelProperty(required = false, readOnly = false, value = "", example = "S")
  private String providedByCode;

  @Size(min = CMS_ID_LEN, max = CMS_ID_LEN)
  @ApiModelProperty(required = false, readOnly = false, value = "", example = "ABC1234567")
  private String providedById;

  @ApiModelProperty(required = false, readOnly = false)
  private Integer serviceContactType;

  @NotEmpty
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_FORMAT)
  @JsonProperty(value = "start_Date")
  @gov.ca.cwds.rest.validation.Date(format = DATE_FORMAT, required = true)
  @ApiModelProperty(required = true, readOnly = false, value = "yyyy-MM-dd", example = "2000-01-01")
  private String startDate;

  @NotEmpty
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = TIME_FORMAT)
  @JsonProperty(value = "start_Time")
  @gov.ca.cwds.rest.validation.Date(format = TIME_FORMAT, required = false)
  @ApiModelProperty(required = true, readOnly = false, value = "Contact end time",
      example = "16:41:49")
  private String startTime;

  @NotEmpty
  @Size(min = 1, max = 1)
  @OneOf(value = {"C", "A", "S"}, ignoreCase = false, ignoreWhitespace = true)
  @ApiModelProperty(required = false, readOnly = false, value = "", example = "C")
  private String statusCode;

  @Size(min = 1, max = 1)
  @OneOf(value = {"C", "S", "O", "N"}, ignoreCase = false, ignoreWhitespace = true)
  @ApiModelProperty(required = false, readOnly = false, value = "", example = "C")
  private String supervisionCode;

  @NotNull
  @ApiModelProperty(required = true, readOnly = false)
  private Boolean wraparoundServiceIndicator;

  /**
   * @param id - id
   * @param cftLeadAgencyType - cftLeadAgencyType
   * @param coreServiceIndicator - coreServiceIndicator
   * @param communicationMethodType - communicationMethodType
   * @param contactLocationType - contactLocationType
   * @param contactVisitCode - contactVisitCode
   * @param countySpecificCode - countySpecificCode
   * @param detailText - detailText
   * @param detailTextContinuation - detailTextContinuation
   * @param endDate - endDate
   * @param endTime - endTime
   * @param primaryDeliveredServiceId - primaryDeliveredServiceId
   * @param hardCopyDocumentOnFileCode - hardCopyDocumentOnFileCode
   * @param otherParticipantsDesc - otherParticipantsDesc
   * @param providedByCode - providedByCode
   * @param providedById - providedById
   * @param serviceContactType - serviceContactType
   * @param startDate - startDate
   * @param startTime - startTime
   * @param statusCode - statusCode
   * @param supervisionCode - supervisionCode
   * @param wraparoundServiceIndicator - wraparoundServiceIndicator
   */
  @JsonCreator
  public DeliveredServiceDomain(@JsonProperty("id") String id,
      @JsonProperty("cft_lead_agency_type") Short cftLeadAgencyType,
      @JsonProperty("core_service") Boolean coreServiceIndicator,
      @JsonProperty("communication_type") Integer communicationMethodType,
      @JsonProperty("contact_location") Integer contactLocationType,
      @JsonProperty("contact_visit") String contactVisitCode,
      @JsonProperty("county") String countySpecificCode,
      @JsonProperty("detail_text") String detailText,
      @JsonProperty("detail_text_continuation") String detailTextContinuation,
      @JsonProperty(value = "end_Date") String endDate,
      @JsonProperty(value = "end_Time") String endTime,
      @JsonProperty(value = "primary_delivered_service_id") String primaryDeliveredServiceId,
      @JsonProperty(value = "document_on_file") String hardCopyDocumentOnFileCode,
      @JsonProperty(value = "other_participant") String otherParticipantsDesc,
      @JsonProperty(value = "provied_by") String providedByCode,
      @JsonProperty(value = "provided_by_id") String providedById,
      @JsonProperty(value = "service_contact") Integer serviceContactType,
      @JsonProperty(value = "start_Date") String startDate,
      @JsonProperty(value = "start_Time") String startTime,
      @JsonProperty(value = "status") String statusCode,
      @JsonProperty(value = "supervision") String supervisionCode,
      @JsonProperty(value = "wrap_around") Boolean wraparoundServiceIndicator) {
    super();
    this.id = id;
    this.cftLeadAgencyType = cftLeadAgencyType;
    this.coreServiceIndicator = coreServiceIndicator;
    this.communicationMethodType = communicationMethodType;
    this.contactLocationType = contactLocationType;
    this.contactVisitCode = contactVisitCode;
    this.countySpecificCode = countySpecificCode;
    this.detailText = detailText;
    this.detailTextContinuation = detailTextContinuation;
    this.endDate = endDate;
    this.endTime = endTime;
    this.primaryDeliveredServiceId = primaryDeliveredServiceId;
    this.hardCopyDocumentOnFileCode = hardCopyDocumentOnFileCode;
    this.otherParticipantsDesc = otherParticipantsDesc;
    this.providedByCode = providedByCode;
    this.providedById = providedById;
    this.serviceContactType = serviceContactType;
    this.startDate = startDate;
    this.startTime = startTime;
    this.statusCode = statusCode;
    this.supervisionCode = supervisionCode;
    this.wraparoundServiceIndicator = wraparoundServiceIndicator;
  }

  /**
   * Construct from persistence layer CMS tickle.
   * 
   * @param persistedDeliverdService persisted tickle object
   */
  public DeliveredServiceDomain(
      gov.ca.cwds.data.persistence.contact.DeliveredServiceEntity persistedDeliverdService) {
    this.id = persistedDeliverdService.getId();
    this.cftLeadAgencyType = persistedDeliverdService.getCftLeadAgencyType();
    this.coreServiceIndicator =
        DomainChef.uncookBooleanString(persistedDeliverdService.getCoreServiceIndicator());
    this.communicationMethodType =
        new Integer(persistedDeliverdService.getCommunicationMethodType());
    this.contactLocationType = new Integer(persistedDeliverdService.getContactLocationType());
    this.contactVisitCode = persistedDeliverdService.getContactVisitCode();
    this.countySpecificCode = persistedDeliverdService.getCountySpecificCode();
    this.detailText = persistedDeliverdService.getDetailText();
    this.detailTextContinuation = persistedDeliverdService.getDetailTextContinuation();
    this.endDate = DomainChef.cookDate(persistedDeliverdService.getEndDate());
    this.endTime = DomainChef.cookTime(persistedDeliverdService.getEndTime());
    this.primaryDeliveredServiceId = persistedDeliverdService.getPrimaryDeliveredServiceId();
    this.hardCopyDocumentOnFileCode = persistedDeliverdService.getHardCopyDocumentOnFileCode();
    this.otherParticipantsDesc = persistedDeliverdService.getOtherParticipantsDesc();
    this.providedByCode = persistedDeliverdService.getProvidedByCode();
    this.providedById = persistedDeliverdService.getProvidedById();
    this.serviceContactType = new Integer(persistedDeliverdService.getServiceContactType());
    this.startDate = DomainChef.cookDate(persistedDeliverdService.getStartDate());
    this.startTime = DomainChef.cookTime(persistedDeliverdService.getStartTime());
    this.statusCode = persistedDeliverdService.getStatusCode();
    this.supervisionCode = persistedDeliverdService.getSupervisionCode();
    this.wraparoundServiceIndicator =
        DomainChef.uncookBooleanString(persistedDeliverdService.getWraparoundServiceIndicator());
  }

  /**
   * @return the id
   */
  public String getId() {
    return id;
  }

  /**
   * @return the cftLeadAgencyType
   */
  public Short getCftLeadAgencyType() {
    return cftLeadAgencyType;
  }

  /**
   * @return the coreServiceIndicator
   */
  public Boolean getCoreServiceIndicator() {
    return coreServiceIndicator;
  }

  /**
   * @return the communicationMethodType
   */
  public Integer getCommunicationMethodType() {
    return communicationMethodType;
  }

  /**
   * @return the contactLocationType
   */
  public Integer getContactLocationType() {
    return contactLocationType;
  }

  /**
   * @return the contactVisitCode
   */
  public String getContactVisitCode() {
    return contactVisitCode;
  }

  /**
   * @return the countySpecificCode
   */
  public String getCountySpecificCode() {
    return countySpecificCode;
  }

  /**
   * @return the detailText
   */
  public String getDetailText() {
    return detailText;
  }

  /**
   * @return the detailTextContinuation
   */
  public String getDetailTextContinuation() {
    return detailTextContinuation;
  }

  /**
   * @return the endDate
   */
  public String getEndDate() {
    return endDate;
  }

  /**
   * @return the endTime
   */
  public String getEndTime() {
    return endTime;
  }

  /**
   * @return the primaryDeliveredServiceId
   */
  public String getPrimaryDeliveredServiceId() {
    return primaryDeliveredServiceId;
  }

  /**
   * @return the hardCopyDocumentOnFileCode
   */
  public String getHardCopyDocumentOnFileCode() {
    return hardCopyDocumentOnFileCode;
  }

  /**
   * @return the otherParticipantsDesc
   */
  public String getOtherParticipantsDesc() {
    return otherParticipantsDesc;
  }

  /**
   * @return the providedByCode
   */
  public String getProvidedByCode() {
    return providedByCode;
  }

  /**
   * @return the providedById
   */
  public String getProvidedById() {
    return providedById;
  }

  /**
   * @return the serviceContactType
   */
  public Integer getServiceContactType() {
    return serviceContactType;
  }

  /**
   * @return the startDate
   */
  public String getStartDate() {
    return startDate;
  }

  /**
   * @return the startTime
   */
  public String getStartTime() {
    return startTime;
  }

  /**
   * @return the statusCode
   */
  public String getStatusCode() {
    return statusCode;
  }

  /**
   * @return the supervisionCode
   */
  public String getSupervisionCode() {
    return supervisionCode;
  }

  /**
   * @return the wrapAroundIndicator
   */
  public Boolean getWraparoundServiceIndicator() {
    return wraparoundServiceIndicator;
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
