package gov.ca.cwds.rest.api.domain.investigation;

import com.fasterxml.jackson.annotation.JsonProperty;

import gov.ca.cwds.rest.api.domain.DomainObject;
import io.dropwizard.jackson.JsonSnakeCase;
import io.swagger.annotations.ApiModelProperty;

/**
 * {@link DomainObject} representing an AllegationPerson
 * 
 * @author CWDS API Team
 */
@JsonSnakeCase
public class AllegationPerson {
  private static final long serialVersionUID = 1L;

  @JsonProperty("first_name")
  @ApiModelProperty(required = false, readOnly = false, value = "First Name")
  private String firstName;

  @JsonProperty("last_name")
  @ApiModelProperty(required = false, readOnly = false, value = "Last Name")
  private String lastName;

  @JsonProperty("middle_name")
  @ApiModelProperty(required = false, readOnly = false, value = "Middle Name")
  private String middleName;

  @JsonProperty("suffix_title")
  @ApiModelProperty(required = false, readOnly = false, value = "Suffix")
  private String suffixTitle;

  @JsonProperty("prefix_title")
  @ApiModelProperty(required = false, readOnly = false, value = "Prefix")
  private String prefixTitle;

  @JsonProperty("legacy_descriptor")
  private CmsRecordDescriptor legacyDescriptor;


  /**
   * empty constructor
   */
  public AllegationPerson() {
    super();
  }

  /**
   * @param firstName - first name
   * @param lastName - last name
   * @param middleName - middle name
   * @param suffixTitle - suffix
   * @param prefixTitle - prefix
   * @param legacyDescriptor - description of CMS record
   */
  public AllegationPerson(String firstName, String lastName, String middleName, String suffixTitle,
      String prefixTitle, CmsRecordDescriptor legacyDescriptor) {
    super();
    this.firstName = firstName;
    this.lastName = lastName;
    this.middleName = middleName;
    this.suffixTitle = suffixTitle;
    this.prefixTitle = prefixTitle;
    this.legacyDescriptor = legacyDescriptor;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getMiddleName() {
    return middleName;
  }

  public void setMiddleName(String middleName) {
    this.middleName = middleName;
  }

  public String getSuffixTitle() {
    return suffixTitle;
  }

  public void setSuffixTitle(String suffixTitle) {
    this.suffixTitle = suffixTitle;
  }

  public String getPrefixTitle() {
    return prefixTitle;
  }

  public void setPrefixTitle(String prefixTitle) {
    this.prefixTitle = prefixTitle;
  }

  public CmsRecordDescriptor getLegacyDescriptor() {
    return legacyDescriptor;
  }

  public void setLegacyDescriptor(CmsRecordDescriptor legacyDescriptor) {
    this.legacyDescriptor = legacyDescriptor;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
    result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
    result = prime * result + ((legacyDescriptor == null) ? 0 : legacyDescriptor.hashCode());
    result = prime * result + ((middleName == null) ? 0 : middleName.hashCode());
    result = prime * result + ((prefixTitle == null) ? 0 : prefixTitle.hashCode());
    result = prime * result + ((suffixTitle == null) ? 0 : suffixTitle.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    AllegationPerson other = (AllegationPerson) obj;
    if (firstName == null) {
      if (other.firstName != null)
        return false;
    } else if (!firstName.equals(other.firstName))
      return false;
    if (lastName == null) {
      if (other.lastName != null)
        return false;
    } else if (!lastName.equals(other.lastName))
      return false;
    if (legacyDescriptor == null) {
      if (other.legacyDescriptor != null)
        return false;
    } else if (!legacyDescriptor.equals(other.legacyDescriptor))
      return false;
    if (middleName == null) {
      if (other.middleName != null)
        return false;
    } else if (!middleName.equals(other.middleName))
      return false;
    if (prefixTitle == null) {
      if (other.prefixTitle != null)
        return false;
    } else if (!prefixTitle.equals(other.prefixTitle))
      return false;
    if (suffixTitle == null) {
      if (other.suffixTitle != null)
        return false;
    } else if (!suffixTitle.equals(other.suffixTitle))
      return false;
    return true;
  }



}
