package gov.ca.cwds.rest.api.domain.investigation;

import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.DomainObject;
import gov.ca.cwds.rest.api.domain.ReportingDomain;
import io.dropwizard.jackson.JsonSnakeCase;
import io.swagger.annotations.ApiModelProperty;

import java.util.Set;

import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * {@link DomainObject} representing a Simple Person With Roles
 * 
 * @author CWDS API Team
 */
@JsonSnakeCase
@JsonPropertyOrder({"roles", "last_name", "first_name", "sensitivity_indicator"})
public class SimplePersonWithRoles extends ReportingDomain implements Response {

  private static final long serialVersionUID = 1L;

  @NotEmpty
  @JsonProperty("roles")
  @ApiModelProperty(required = false, readOnly = false, value = "")
  // , example = "[Perpetrator]")
  private Set<String> roles;


  @NotEmpty
  @JsonProperty("last_name")
  @Size(min = 1, max = 25)
  @ApiModelProperty(required = true, readOnly = false, value = "", example = "Jones")
  private String lastName;

  @JsonProperty("first_name")
  @Size(min = 1, max = 20)
  @ApiModelProperty(required = true, readOnly = false, value = "", example = "John")
  private String firstName;

  @JsonProperty("sensitivity_indicator")
  @Size(max = 4)
  @ApiModelProperty(required = false, readOnly = false, value = "", example = "R")
  private String sensitivityIndicator;


  /**
   * Constructor
   * 
   * @param roles the roles
   * @param lastName last name
   * @param firstName first name
   * @param sensitivityIndicator The Sensitivity Indicator
   */
  public SimplePersonWithRoles(@JsonProperty("roles") Set<String> roles,
      @JsonProperty("last_name") String lastName, @JsonProperty("first_name") String firstName,
      @JsonProperty("sensitivity_indicator") String sensitivityIndicator) {
    this.roles = roles;
    this.lastName = lastName;
    this.firstName = firstName;
    this.sensitivityIndicator = sensitivityIndicator;
  }



  /**
   * @return the roles
   */
  public Set<String> getRoles() {
    return roles;
  }



  /**
   * @return the firstName
   */
  public String getFirstName() {
    return firstName;
  }



  /**
   * @return the lastName
   */
  public String getLastName() {
    return lastName;
  }



  /**
   * @return the sensitivityIndicator
   */
  public String getSensitivityIndicator() {
    return sensitivityIndicator;
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
