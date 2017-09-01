package gov.ca.cwds.rest.api.domain;

import static gov.ca.cwds.data.persistence.cms.CmsPersistentObject.CMS_ID_LEN;
import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.Response;
import io.dropwizard.jackson.JsonSnakeCase;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * {@link DomainObject} representing an PostedIndividualDeliveredSevice
 * 
 * @author CWDS API Team
 */
@JsonSnakeCase
@JsonPropertyOrder({"table_name", "id", "first_name", "last_name", "relationship"})
public class PostedIndividualDeliveredService extends ReportingDomain implements Request, Response {

  private static final long serialVersionUID = 1L;

  @NotEmpty
  @JsonProperty("table_name")
  @Size(min = 1, max = 10)
  @ApiModelProperty(required = true, readOnly = false, value = "", example = "CLIENT_T")
  private String tableName;

  @Size(max = CMS_ID_LEN)
  @NotNull
  @ApiModelProperty(required = true, readOnly = true, value = "", example = "ABC1234567")
  private String id;

  @NotEmpty
  @JsonProperty("first_name")
  @Size(min = 1, max = 10)
  @ApiModelProperty(required = true, readOnly = false, value = "", example = "John")
  private String firstName;

  @NotEmpty
  @JsonProperty("last_name")
  @Size(min = 1, max = 10)
  @ApiModelProperty(required = true, readOnly = false, value = "", example = "Doe")
  private String lastName;

  @NotEmpty
  @JsonProperty("relationship")
  @Size(min = 1, max = 10)
  @ApiModelProperty(required = true, readOnly = false, value = "", example = "unknown")
  private String relationship;


  /**
   * @param tableName legacy table name
   * @param id id in legacy table
   * @param firstName first name
   * @param lastName last name
   * @param relationship relationship
   */
  public PostedIndividualDeliveredService(@JsonProperty("table_name") String tableName,
      @JsonProperty("id") String id, @JsonProperty("first_name") String firstName,
      @JsonProperty("last_name") String lastName, @JsonProperty("relationship") String relationship) {
    super();
    this.tableName = tableName;
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.relationship = relationship;
  }



  /**
   * @return the tableName
   */
  public String getTableName() {
    return tableName;
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
   * @return the lastName
   */
  public String getLastName() {
    return lastName;
  }



  /**
   * @return the relationship
   */
  public String getRelationship() {
    return relationship;
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
