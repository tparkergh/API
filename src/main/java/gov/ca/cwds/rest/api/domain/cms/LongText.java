package gov.ca.cwds.rest.api.domain.cms;

import javax.validation.constraints.Size;

import gov.ca.cwds.rest.api.domain.ReportingDomain;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.DomainObject;
import io.swagger.annotations.ApiModelProperty;

/**
 * {@link DomainObject} representing an LongText
 * 
 * @author CWDS API Team
 */
public class LongText extends ReportingDomain implements Request, Response {

  /**
   * Serialization version
   */
  private static final long serialVersionUID = 1L;

  @NotEmpty
  @Size(min = 1, max = 2)
  @ApiModelProperty(required = true, readOnly = false, value = "County code", example = "57")
  private String countySpecificCode;

  @NotEmpty
  @Size(min = 1, max = 32700)
  @ApiModelProperty(required = true, readOnly = false, value = "Text Description",
      example = "Arrange for parents to have demonstrating home maker come to the home twice a week")
  private String textDescription;

  /**
   * @param countySpecificCode county specific code
   * @param textDescription text description
   */
  @JsonCreator
  public LongText(@JsonProperty("countySpecificCode") String countySpecificCode,
      @JsonProperty("textDescription") String textDescription) {
    super();
    this.countySpecificCode = countySpecificCode;
    this.textDescription = textDescription;
  }

  /**
   * Construct from persistence layer CMS longText.
   * 
   * @param persistedLongText persistence level longText
   */
  public LongText(gov.ca.cwds.data.persistence.cms.LongText persistedLongText) {
    this.countySpecificCode = persistedLongText.getCountySpecificCode();
    this.textDescription = persistedLongText.getTextDescription();
  }

  /**
   * @return the countySpecificCode
   */
  public String getCountySpecificCode() {
    return countySpecificCode;
  }

  /**
   * @return the textDescription
   */
  public String getTextDescription() {
    return textDescription;
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
