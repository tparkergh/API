package gov.ca.cwds.rest.api.domain;

import javax.validation.constraints.Size;

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
   * 
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

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#hashCode()
   */
  @Override
  public final int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((language == null) ? 0 : language.hashCode());
    return result;
  }

  /*
   * (non-Javadoc)
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
    if (!(getClass().isInstance(obj))) {
      return false;
    }
    Language other = (Language) obj;
    if (language == null) {
      if (other.language != null) {
        return false;
      }
    } else if (!language.equals(other.language)) {
      return false;
    }
    return true;
  }


}
