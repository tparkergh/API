package gov.ca.cwds.rest.api.domain.cms;

import static gov.ca.cwds.data.persistence.cms.CmsPersistentObject.CMS_ID_LEN;

import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.DomainObject;
import gov.ca.cwds.rest.api.domain.ReportingDomain;
import io.swagger.annotations.ApiModelProperty;

/**
 * {@link DomainObject} representing an drmsDocument
 * 
 * @author CWDS API Team
 */
public class DrmsDocument extends ReportingDomain implements Request, Response {

  /**
   * Serialization version
   */
  private static final long serialVersionUID = 1L;

  @ApiModelProperty(required = true, readOnly = false, value = "2016-08-03T01:00:00.000",
      example = "")
  private Date creationTimeStamp;

  @NotNull
  @Size(max = CMS_ID_LEN)
  @ApiModelProperty(required = true, readOnly = false, value = "ABC1234567", example = "DUMMY")
  private String drmsDocumentTemplateId;

  @Size(min = 1, max = 3)
  @ApiModelProperty(required = true, readOnly = false, value = "q1p", example = "q1p")
  private String fingerprintStaffPerson;

  @Size(min = 1, max = 3)
  @ApiModelProperty(required = true, readOnly = false, value = "q1p", example = "q1p")
  private String staffPersonId;

  @NotNull
  @Size(min = 1, max = 30)
  @ApiModelProperty(required = true, readOnly = false, value = "Some thing", example = "DUMMY")
  private String handleName;

  /**
   * @param creationTimeStamp
   * @param drmsDocumentTemplateId
   * @param fingerprintStaffPerson
   * @param staffPersonId
   * @param handleName
   */
  public DrmsDocument(@JsonProperty("creationTimeStamp") Date creationTimeStamp,
      @JsonProperty("drmsDocumentTemplateId") String drmsDocumentTemplateId,
      @JsonProperty("fingerprintStaffPerson") String fingerprintStaffPerson,
      @JsonProperty("staffPersonId") String staffPersonId,
      @JsonProperty("handleName") String handleName) {
    super();
    this.creationTimeStamp = creationTimeStamp;
    this.drmsDocumentTemplateId = drmsDocumentTemplateId;
    this.fingerprintStaffPerson = fingerprintStaffPerson;
    this.staffPersonId = staffPersonId;
    this.handleName = handleName;
  }

  /**
   * Construct from persistence layer CMS drmsDocument.
   * 
   * @param persistedDrmsDocument
   */
  public DrmsDocument(gov.ca.cwds.data.persistence.cms.DrmsDocument persistedDrmsDocument) {
    super();
    this.creationTimeStamp = persistedDrmsDocument.getCreationTimeStamp();
    this.drmsDocumentTemplateId = persistedDrmsDocument.getDrmsDocumentTemplateId();
    this.fingerprintStaffPerson = persistedDrmsDocument.getFingerprintStaffPerson();
    this.staffPersonId = persistedDrmsDocument.getStaffPersonId();
    this.handleName = persistedDrmsDocument.getHandleName();
  }

  /**
   * @return the creationTimeStamp
   */
  public Date getCreationTimeStamp() {
    return creationTimeStamp;
  }

  /**
   * @return the drmsDocumentTemplateId
   */
  public String getDrmsDocumentTemplateId() {
    return drmsDocumentTemplateId;
  }

  /**
   * @return the fingerprintStaffPerson
   */
  public String getFingerprintStaffPerson() {
    return fingerprintStaffPerson;
  }

  /**
   * @return the staffPersonId
   */
  public String getStaffPersonId() {
    return staffPersonId;
  }

  /**
   * @return the handleName
   */
  public String getHandleName() {
    return handleName;
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
