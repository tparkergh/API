package gov.ca.cwds.rest.api.domain.cms;

import static gov.ca.cwds.data.persistence.cms.CmsPersistentObject.CMS_ID_LEN;

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
import gov.ca.cwds.rest.api.domain.ReportingDomain;
import io.swagger.annotations.ApiModelProperty;

/**
 * This domain class created only for demo purpose and will be removed later
 * 
 * @author CWDS API Team
 */
public class AllegationPerpetratorHistory extends ReportingDomain implements Request, Response {

  /**
   * Default serialization.
   */
  private static final long serialVersionUID = 1L;

  @Size(min = 2, max = 2)
  @ApiModelProperty(required = true, readOnly = false, value = "County code", example = "99")
  private String countySpecificCode;

  @Size(max = CMS_ID_LEN)
  @ApiModelProperty(required = false, readOnly = false, value = "Client ID of perpetrator",
      example = "ABC1234568")
  private String perpetratorClientId;

  @NotEmpty
  @Size(min = CMS_ID_LEN, max = CMS_ID_LEN)
  @ApiModelProperty(required = true, readOnly = false, value = "Allegation ID",
      example = "ABC1234567")
  private String allegationId;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_FORMAT)
  @JsonProperty(value = "perpetratorUpdateDate")
  @gov.ca.cwds.rest.validation.Date(format = DATE_FORMAT, required = true)
  @ApiModelProperty(required = true, readOnly = false, value = "yyyy-MM-dd", example = "2016-11-30")
  private String perpetratorUpdateDate;

  /**
   * Constructor. Build from JSON.
   * 
   * @param countySpecificCode county Specific Code
   * @param perpetratorClientId perpetrator Client Id
   * @param allegationId allegation Id
   * @param perpetratorUpdateDate perpetrator Update Date
   */
  @JsonCreator
  public AllegationPerpetratorHistory(@JsonProperty("countySpecificCode") String countySpecificCode,
      @JsonProperty("perpetratorClientId") String perpetratorClientId,
      @JsonProperty("allegationId") String allegationId,
      @JsonProperty("perpetratorUpdateDate") String perpetratorUpdateDate) {
    super();
    this.countySpecificCode = countySpecificCode;
    this.perpetratorClientId = perpetratorClientId;
    this.allegationId = allegationId;
    this.perpetratorUpdateDate = perpetratorUpdateDate;
  }

  @SuppressWarnings("javadoc")
  public AllegationPerpetratorHistory(
      gov.ca.cwds.data.persistence.cms.AllegationPerpetratorHistory persistedAllegationPerpetratorHistory) {
    this.countySpecificCode = persistedAllegationPerpetratorHistory.getCountySpecificCode();
    this.perpetratorClientId = persistedAllegationPerpetratorHistory.getPerpetratorClientId();
    this.allegationId = persistedAllegationPerpetratorHistory.getAllegationId();
    this.perpetratorUpdateDate =
        DomainChef.cookDate(persistedAllegationPerpetratorHistory.getPerpetratorUpdateDate());
  }

  @SuppressWarnings("javadoc")
  public String getCountySpecificCode() {
    return countySpecificCode;
  }

  @SuppressWarnings("javadoc")
  public String getPerpetratorClientId() {
    return perpetratorClientId;
  }

  @SuppressWarnings("javadoc")
  public String getAllegationId() {
    return allegationId;
  }

  @SuppressWarnings("javadoc")
  public String getPerpetratorUpdateDate() {
    return perpetratorUpdateDate;
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
