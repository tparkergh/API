package gov.ca.cwds.rest.api.domain.cms;

import java.io.Serializable;

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
import io.dropwizard.validation.OneOf;
import io.swagger.annotations.ApiModelProperty;

/**
 * {@link DomainObject} representing an Tickle
 * 
 * @author CWDS API Team
 */
public class Tickle extends ReportingDomain implements Request, Response, Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  @NotEmpty
  @Size(min = 1, max = 10)
  @ApiModelProperty(required = true, readOnly = false, value = "ABC1234567", example = "ABC1234567")
  private String affectedByCaseOrReferralId;

  @NotNull
  @Size(min = 1, max = 2)
  @OneOf(value = {"C", "CC", "CE", "CH", "CL", "CO", "CP", "CT", "CG", "R", "RL", "RP", "RH", "RO",
      "RT", "RE", "UC", "UR", "UL"}, ignoreCase = false, ignoreWhitespace = true)
  @ApiModelProperty(required = true, readOnly = false, value = "client", example = "CC")
  private String affectedByCode;

  @Size(min = 1, max = 10)
  @ApiModelProperty(required = false, readOnly = false, value = "ABC1234567",
      example = "ABC1234567")
  private String affectedByOtherId;

  @Size(min = 1, max = 10)
  @ApiModelProperty(required = false, readOnly = false, value = "ABC1234567",
      example = "ABC1234567")
  private String affectedByThirdId;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_FORMAT)
  @JsonProperty(value = "dueDate")
  @gov.ca.cwds.rest.validation.Date(format = DATE_FORMAT, required = false)
  @ApiModelProperty(required = true, readOnly = false, value = "due date will alert the worker",
      example = "1992-06-18")
  private String dueDate;

  @Size(min = 1, max = 10)
  @ApiModelProperty(required = false, readOnly = false, value = "ABC1234567",
      example = "ABC1234567")
  private String noteText;

  @NotNull
  @ApiModelProperty(required = true, readOnly = false, value = "1234", example = "2055")
  private Short tickleMessageType;

  /**
   * @param affectedByCaseOrReferralId The affectedByCaseOrReferralId
   * @param affectedByCode The affectedByCode
   * @param affectedByOtherId The affectedByOtherId
   * @param affectedByThirdId The affectedByThirdId
   * @param dueDate The dueDate
   * @param noteText The noteText
   * @param tickleMessageType The tickleMessageType
   */
  @JsonCreator
  public Tickle(@JsonProperty("affectedByCaseOrReferralId") String affectedByCaseOrReferralId,
      @JsonProperty("affectedByCode") String affectedByCode,
      @JsonProperty("affectedByOtherId") String affectedByOtherId,
      @JsonProperty("affectedByThirdId") String affectedByThirdId,
      @JsonProperty("dueDate") String dueDate, @JsonProperty("noteText") String noteText,
      @JsonProperty("tickleMessageType") Short tickleMessageType) {
    super();
    this.affectedByCaseOrReferralId = affectedByCaseOrReferralId;
    this.affectedByCode = affectedByCode;
    this.affectedByOtherId = affectedByOtherId;
    this.affectedByThirdId = affectedByThirdId;
    this.dueDate = dueDate;
    this.noteText = noteText;
    this.tickleMessageType = tickleMessageType;
  }

  /**
   * Construct from persistence layer CMS tickle.
   * 
   * @param persistedTickle - persisted tickle object
   */
  public Tickle(gov.ca.cwds.data.persistence.cms.Tickle persistedTickle) {
    this.affectedByCaseOrReferralId = persistedTickle.getAffectedByCaseOrReferralId();
    this.affectedByCode = persistedTickle.getAffectedByCode();
    this.affectedByOtherId = persistedTickle.getAffectedByOtherId();
    this.affectedByThirdId = persistedTickle.getAffectedByThirdId();
    this.dueDate = DomainChef.cookDate(persistedTickle.getDueDate());
    this.noteText = persistedTickle.getNoteText();
    this.tickleMessageType = persistedTickle.getTickleMessageType();
  }

  /**
   * @return the affectedByCaseOrReferralId
   */
  public String getAffectedByCaseOrReferralId() {
    return affectedByCaseOrReferralId;
  }

  /**
   * @return the affectedByCode
   */
  public String getAffectedByCode() {
    return affectedByCode;
  }

  /**
   * @return the affectedByOtherId
   */
  public String getAffectedByOtherId() {
    return affectedByOtherId;
  }

  /**
   * @return the affectedByThirdId
   */
  public String getAffectedByThirdId() {
    return affectedByThirdId;
  }

  /**
   * @return the dueDate
   */
  public String getDueDate() {
    return dueDate;
  }

  /**
   * @return the noteText
   */
  public String getNoteText() {
    return noteText;
  }

  /**
   * @return the tickleMessageType
   */
  public Short getTickleMessageType() {
    return tickleMessageType;
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
