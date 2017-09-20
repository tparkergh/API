package gov.ca.cwds.rest.api.domain.investigation;

import java.util.Set;

import javax.validation.Valid;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.LegacyDescriptor;
import gov.ca.cwds.rest.api.domain.RaceAndEthnicity;
import gov.ca.cwds.rest.validation.Date;
import io.dropwizard.validation.OneOf;
import io.swagger.annotations.ApiModelProperty;

/**
 *
 * 
 * @author CWDS API Team
 */
public class Person implements Request, Response {

  private static final long serialVersionUID = 1L;

  @JsonProperty("legacy_descriptor")
  private LegacyDescriptor legacyDescriptor;

  @JsonProperty("last_updated_by")
  @ApiModelProperty(required = false, readOnly = false, value = "staff person Id")
  private String lastUpdatedBy;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
  @JsonProperty("last_udated_at")
  @ApiModelProperty(required = false, readOnly = false, value = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
      example = "2010-04-27T23:30:14.000Z")
  private String lastUpdatedAt;

  @JsonProperty("first_name")
  @ApiModelProperty(required = true, readOnly = false, value = "first name", example = "Gerry")
  @Size(min = 1, max = 20)
  private String firstName;

  @JsonProperty("middle_name")
  @ApiModelProperty(required = false, readOnly = false, value = "middle name", example = "M")
  @Size(min = 0, max = 50)
  private String middleName;

  @JsonProperty("last_name")
  @ApiModelProperty(required = true, readOnly = false, value = "last name", example = "Mitchell")
  @Size(max = 50)
  private String lastName;

  @JsonProperty("name_suffix")
  @ApiModelProperty(required = false, readOnly = false, value = "Suffix title", example = "jr")
  @Size(max = 4)
  private String nameSuffix;

  @JsonProperty("gender")
  @ApiModelProperty(required = true, readOnly = false, value = "gender code", example = "M")
  @Size(min = 1, max = 1, message = "size must be 1")
  @OneOf(value = {"M", "F", "U"}, ignoreCase = true, ignoreWhitespace = true)
  private String gender;

  @JsonFormat(shape = JsonFormat.Shape.STRING,
      pattern = gov.ca.cwds.rest.api.domain.DomainObject.DATE_FORMAT)
  @JsonProperty("date_of_birth")
  @gov.ca.cwds.rest.validation.Date(format = gov.ca.cwds.rest.api.domain.DomainObject.DATE_FORMAT,
      required = false)
  @ApiModelProperty(required = false, readOnly = false, value = "yyyy-MM-dd",
      example = "2012-04-01")
  private String dateOfBirth;

  @JsonProperty("ssn")
  @ApiModelProperty(required = true, readOnly = false, value = "social security number",
      example = "999551111")
  @Size(min = 0, max = 9)
  private String ssn;

  @Valid
  @JsonProperty("languages")
  @ApiModelProperty(required = false, readOnly = false, value = "languages",
      dataType = "java.util.List", example = "['839', '840']")
  private Set<Short> languages;

  // @SystemCodeSerializer(logical = true, description = true)
  // @JsonProperty("primary_language")
  // @ApiModelProperty(required = false, readOnly = false, example = "1253",
  // value = "Primary language code")
  // private Short primaryLanguage;
  //
  // @SystemCodeSerializer(logical = true, description = true)
  // @JsonProperty("secondary_language")
  // @ApiModelProperty(required = false, readOnly = false, example = "1255",
  // value = "Secondary language code")
  // private Short secondaryLanguage;

  private RaceAndEthnicity raceAndEthnicity;

  @JsonProperty("sensitive")
  @ApiModelProperty(required = true, readOnly = false, example = "false",
      value = "person contains sensitive information")
  private Boolean sensitive;

  @JsonProperty("sealed")
  @ApiModelProperty(required = true, readOnly = false, example = "false",
      value = "person contains sealed information")
  private Boolean sealed;

  @JsonProperty("phone")
  @ApiModelProperty(required = false, readOnly = false, value = "phone",
      dataType = "java.util.List")
  private Set<PhoneNumber> phone;

  @JsonProperty("roles")
  @ApiModelProperty(required = false, readOnly = false, value = "Roles of person",
      dataType = "java.util.List", example = "['Non-mandated Reporter']")
  private Set<String> roles;

  @JsonProperty("addresses")
  private Set<InvestigationAddress> addresses;


  /**
   * @param legacyDescriptor - CMS record description
   * @param lastUpdatedBy - last updated by
   * @param lastUpdatedAt - last updated at
   * @param firstName - frist name
   * @param middleName - middle name
   * @param lastName - last name
   * @param nameSuffix - name suffix
   * @param gender - gender
   * @param dateOfBirth - date of birth
   * @param ssn - ssn
   * @param languages - list of languages
   * @param raceAndEthnicity - race/ethnicity
   * @param sensitive - sensitive data
   * @param sealed - sealted data
   * @param phone - phone information
   * @param roles - roles
   * @param addresses - address information
   */
  public Person(LegacyDescriptor legacyDescriptor, String lastUpdatedBy, String lastUpdatedAt,
      String firstName, String middleName, String lastName, String nameSuffix,
      @OneOf(value = {"M", "F", "U"}, ignoreCase = true, ignoreWhitespace = true) String gender,
      @Date(format = "yyyy-MM-dd", required = false) String dateOfBirth, String ssn,
      Set<Short> languages, RaceAndEthnicity raceAndEthnicity, Boolean sensitive, Boolean sealed,
      Set<PhoneNumber> phone, Set<String> roles, Set<InvestigationAddress> addresses) {
    super();
    this.legacyDescriptor = legacyDescriptor;
    this.lastUpdatedBy = lastUpdatedBy;
    this.lastUpdatedAt = lastUpdatedAt;
    this.firstName = firstName;
    this.middleName = middleName;
    this.lastName = lastName;
    this.nameSuffix = nameSuffix;
    this.gender = gender;
    this.dateOfBirth = dateOfBirth;
    this.ssn = ssn;
    this.languages = languages;
    this.raceAndEthnicity = raceAndEthnicity;
    this.sensitive = sensitive;
    this.sealed = sealed;
    this.phone = phone;
    this.roles = roles;
    this.addresses = addresses;
  }

  /**
   * @return CMS record description
   */
  public LegacyDescriptor getLegacyDescriptor() {
    return legacyDescriptor;
  }


  /**
   * @return last updated by staff Id
   */
  public String getLastUpdatedBy() {
    return lastUpdatedBy;
  }


  /**
   * @return last updated date/time
   */
  public String getLastUpdatedAt() {
    return lastUpdatedAt;
  }


  /**
   * @return first name
   */
  public String getFirstName() {
    return firstName;
  }


  public String getMiddleName() {
    return middleName;
  }


  public String getLastName() {
    return lastName;
  }


  public String getNameSuffix() {
    return nameSuffix;
  }


  public String getGender() {
    return gender;
  }


  public String getDateOfBirth() {
    return dateOfBirth;
  }


  public String getSsn() {
    return ssn;
  }


  public Set<Short> getLanguages() {
    return languages;
  }


  public RaceAndEthnicity getRaceAndEthnicity() {
    return raceAndEthnicity;
  }


  public Boolean getSensitive() {
    return sensitive;
  }


  public Boolean getSealed() {
    return sealed;
  }


  public Set<PhoneNumber> getPhone() {
    return phone;
  }


  public Set<String> getRoles() {
    return roles;
  }


  public Set<InvestigationAddress> getAddresses() {
    return addresses;
  }


  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((addresses == null) ? 0 : addresses.hashCode());
    result = prime * result + ((dateOfBirth == null) ? 0 : dateOfBirth.hashCode());
    result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
    result = prime * result + ((gender == null) ? 0 : gender.hashCode());
    result = prime * result + ((languages == null) ? 0 : languages.hashCode());
    result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
    result = prime * result + ((lastUpdatedAt == null) ? 0 : lastUpdatedAt.hashCode());
    result = prime * result + ((lastUpdatedBy == null) ? 0 : lastUpdatedBy.hashCode());
    result = prime * result + ((legacyDescriptor == null) ? 0 : legacyDescriptor.hashCode());
    result = prime * result + ((middleName == null) ? 0 : middleName.hashCode());
    result = prime * result + ((nameSuffix == null) ? 0 : nameSuffix.hashCode());
    result = prime * result + ((phone == null) ? 0 : phone.hashCode());
    result = prime * result + ((raceAndEthnicity == null) ? 0 : raceAndEthnicity.hashCode());
    result = prime * result + ((roles == null) ? 0 : roles.hashCode());
    result = prime * result + ((sealed == null) ? 0 : sealed.hashCode());
    result = prime * result + ((sensitive == null) ? 0 : sensitive.hashCode());
    result = prime * result + ((ssn == null) ? 0 : ssn.hashCode());
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
    Person other = (Person) obj;
    if (addresses == null) {
      if (other.addresses != null)
        return false;
    } else if (!addresses.equals(other.addresses))
      return false;
    if (dateOfBirth == null) {
      if (other.dateOfBirth != null)
        return false;
    } else if (!dateOfBirth.equals(other.dateOfBirth))
      return false;
    if (firstName == null) {
      if (other.firstName != null)
        return false;
    } else if (!firstName.equals(other.firstName))
      return false;
    if (gender == null) {
      if (other.gender != null)
        return false;
    } else if (!gender.equals(other.gender))
      return false;
    if (languages == null) {
      if (other.languages != null)
        return false;
    } else if (!languages.equals(other.languages))
      return false;
    if (lastName == null) {
      if (other.lastName != null)
        return false;
    } else if (!lastName.equals(other.lastName))
      return false;
    if (lastUpdatedAt == null) {
      if (other.lastUpdatedAt != null)
        return false;
    } else if (!lastUpdatedAt.equals(other.lastUpdatedAt))
      return false;
    if (lastUpdatedBy == null) {
      if (other.lastUpdatedBy != null)
        return false;
    } else if (!lastUpdatedBy.equals(other.lastUpdatedBy))
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
    if (nameSuffix == null) {
      if (other.nameSuffix != null)
        return false;
    } else if (!nameSuffix.equals(other.nameSuffix))
      return false;
    if (phone == null) {
      if (other.phone != null)
        return false;
    } else if (!phone.equals(other.phone))
      return false;
    if (raceAndEthnicity == null) {
      if (other.raceAndEthnicity != null)
        return false;
    } else if (!raceAndEthnicity.equals(other.raceAndEthnicity))
      return false;
    if (roles == null) {
      if (other.roles != null)
        return false;
    } else if (!roles.equals(other.roles))
      return false;
    if (sealed == null) {
      if (other.sealed != null)
        return false;
    } else if (!sealed.equals(other.sealed))
      return false;
    if (sensitive == null) {
      if (other.sensitive != null)
        return false;
    } else if (!sensitive.equals(other.sensitive))
      return false;
    if (ssn == null) {
      if (other.ssn != null)
        return false;
    } else if (!ssn.equals(other.ssn))
      return false;
    return true;
  }

}
