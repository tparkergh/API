package gov.ca.cwds.rest.api.domain.cms;

import javax.validation.constraints.Size;

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
public class LongText extends DomainObject implements Request, Response {

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
    this.textDescription = persistedLongText.getTextDescrption();
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
    final int prime = 31;
    int result = 1;
    result = prime * result + ((countySpecificCode == null) ? 0 : countySpecificCode.hashCode());
    result = prime * result + ((textDescription == null) ? 0 : textDescription.hashCode());
    return result;
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public final boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (!(obj instanceof LongText)) {
      return false;
    }
    LongText other = (LongText) obj;
    if (countySpecificCode == null) {
      if (other.countySpecificCode != null) {
        return false;
      }
    } else if (!countySpecificCode.equals(other.countySpecificCode)) {
      return false;
    }
    if (textDescription == null) {
      if (other.textDescription != null) {
        return false;
      }
    } else if (!textDescription.equals(other.textDescription)) {
      return false;
    }
    return true;
  }

}
