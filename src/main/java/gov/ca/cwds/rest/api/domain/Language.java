package gov.ca.cwds.rest.api.domain;

import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.Response;
import io.dropwizard.jackson.JsonSnakeCase;
import io.swagger.annotations.ApiModelProperty;

/**
 * {@link DomainObject} representing an language
 * 
 * @author CWDS API Team
 */
@JsonSnakeCase
public class Language extends DomainObject implements Request, Response {
  /**
   * Serialization version
   */
  private static final long serialVersionUID = 1L;
  @JsonProperty("language")
  @ApiModelProperty(example = "English")
  @Size(max = 50)
  String language;


  /**
   * Construct from persistence class
   * 
   * @param language persistence level language object
   */
  public Language(gov.ca.cwds.data.persistence.ns.Language language) {
    this.language = language.getLanguageCodeId();
  }

  /**
   * @param language - language
   */
  public Language(@JsonProperty("language") String language) {
    super();
    this.language = language;
  }

  /**
   * @return the language
   */
  public String getLanguage() {
    return language;
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
