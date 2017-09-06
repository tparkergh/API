package gov.ca.cwds.rest.api.domain;

import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.Response;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * {@link DomainObject} representing a last updated by person.
 * 
 * @author CWDS API Team
 */
public class LastUpdatedBy extends ReportingDomain implements Request, Response {

  /**
   * Default
   */
  private static final long serialVersionUID = 1L;

  @JsonProperty("id")
  @ApiModelProperty(example = "0X5")
  @Size(min = 3, max = 3)
  private String id;

  @JsonProperty("first_name")
  @Size(min = 1, max = 20)
  @ApiModelProperty(required = true, readOnly = false, value = "", example = "John")
  private String firstName;

  @JsonProperty("middle_initial")
  @ApiModelProperty(required = false, readOnly = false, value = "", example = "Q")
  @Size(max = 1, message = "size must be 1")
  private String middleInitial;

  @JsonProperty("last_name")
  @Size(min = 1, max = 25)
  @ApiModelProperty(required = true, readOnly = false, value = "", example = "Smith")
  private String lastName;

  @JsonProperty("suffix_title")
  @Size(max = 4)
  @ApiModelProperty(required = false, readOnly = false, value = "", example = "SR.")
  private String suffixTitle;

  @JsonProperty("prefix_title")
  @Size(max = 6)
  @ApiModelProperty(required = false, readOnly = false, value = "", example = "MR.")
  private String prefixTitle;


  /**
   * Constructor
   * 
   * @param id The id
   * @param firstName The first name
   * @param lastName The last name
   * @param middleInitial The middle name
   * @param suffixTitle The name suffix.
   * @param prefixTitle The prefix_title
   */
  @JsonCreator
  public LastUpdatedBy(@JsonProperty("id") String id, @JsonProperty("first_name") String firstName,
      @JsonProperty("middle_initial") String middleInitial,
      @JsonProperty("last_name") String lastName, @JsonProperty("suffix_title") String suffixTitle,
      @JsonProperty("prefix_title") String prefixTitle) {
    super();
    this.id = id;
    this.firstName = firstName;
    this.middleInitial = middleInitial;
    this.lastName = lastName;
    this.suffixTitle = suffixTitle;
    this.prefixTitle = prefixTitle;
  }


  /**
   * @return the id
   */
  public String getId() {
    return id;
  }


  /**
   * @return the firstName
   */
  public String getFirstName() {
    return firstName;
  }


  /**
   * @return the middleInitial
   */
  public String getMiddleInitial() {
    return middleInitial;
  }


  /**
   * @return the lastName
   */
  public String getLastName() {
    return lastName;
  }


  /**
   * @return the suffixTitle
   */
  public String getSuffixTitle() {
    return suffixTitle;
  }


  /**
   * @return the prefixTitle
   */
  public String getPrefixTitle() {
    return prefixTitle;
  }


  /**
   * {@inheritDoc}
   *
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this, false);
  }

  /**
   * {@inheritDoc}
   *
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj) {
    return EqualsBuilder.reflectionEquals(this, obj, false);
  }

}
