package gov.ca.cwds.rest.api.domain.investigation;

import java.util.Set;

import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonCreator;
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
public class InvestigationPerson implements Request, Response {

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
  @ApiModelProperty(example = "Gerry")
  @Size(max = 50)
  private String firstName;

  @JsonProperty("middle_name")
  @ApiModelProperty(example = "M")
  @Size(max = 50)
  private String middleName;

  @JsonProperty("last_name")
  @ApiModelProperty(example = "Mitchell")
  @Size(max = 50)
  private String lastName;

  @JsonProperty("suffix_title")
  @ApiModelProperty(example = "jr")
  @Size(max = 50)
  private String suffixTitle;

  @JsonProperty("prefix_title")
  @ApiModelProperty(example = "phd")
  @Size(max = 6)
  private String prefixTitle;

  @JsonProperty("gender")
  @ApiModelProperty(example = "M")
  @Size(max = 1)
  // @Pattern(message = "must be one of [M, F, O]", regexp = "[M|F|O]")
  private String gender;

  @Date
  // @PastDate()
  @JsonProperty("birth_date")
  @ApiModelProperty(example = "2012-04-01")
  private String birthDate;

  @JsonProperty("ssn")
  @ApiModelProperty(example = "999551111")
  @Size(min = 9, max = 9) // SSN is fixed width.
  private String ssn;

  @JsonProperty("primary_language")
  @ApiModelProperty(example = "1253", value = "Primary language code")
  private Short primaryLanguage;

  @JsonProperty("secondary_language")
  @ApiModelProperty(example = "1255", value = "Secondary language code")
  private Short secondaryLanguage;

  private RaceAndEthnicity raceAndEthnicity;

  @JsonProperty("sensitivity_indicator")
  @OneOf(value = {"N", "S", "R"}, ignoreCase = false, ignoreWhitespace = true)
  @ApiModelProperty(example = "N", value = "person contains sealed or sensitive information")
  @Size(max = 1)
  private String sensitivityIndicator;

  @JsonProperty("roles")
  @ApiModelProperty(required = false, readOnly = false, value = "Roles of person",
      dataType = "java.util.List", example = "['Non-mandated Reporter']")
  private Set<String> roles;

  @JsonProperty("addresses")
  private Set<InvestigationAddress> addresses;

  /**
   * @param legacyDescriptor - legacy descriptor
   * @param lastUpdatedBy - CWS Staff person ID
   * @param lastUpdatedAt - last updated date/time
   * @param firstName - first name
   * @param middleName - middle name
   * @param lastName - last name
   * @param suffixTitle - suffix
   * @param prefixTitle - prefix
   * @param gender - gender
   * @param birthDate - birth date
   * @param ssn - ssn
   * @param primaryLanguage - primary language code
   * @param secondaryLanguage - secondary language code
   * @param raceAndEthnicity - list of race/ethinicty code
   * @param addresses - address of investigation
   */
  @JsonCreator
  public InvestigationPerson(@JsonProperty("legacy_descriptor") LegacyDescriptor legacyDescriptor,
      @JsonProperty("lastUpdated_by") String lastUpdatedBy,
      @JsonProperty("lastUpdated_at") String lastUpdatedAt,
      @JsonProperty("first_name") String firstName, @JsonProperty("middle_name") String middleName,
      @JsonProperty("last_name") String lastName, @JsonProperty("suffix_title") String suffixTitle,
      @JsonProperty("prefix_title") String prefixTitle, @JsonProperty("gender") String gender,
      @JsonProperty("birthDate") String birthDate, @JsonProperty("ssn") String ssn,
      @JsonProperty("primary_language") Short primaryLanguage,
      @JsonProperty("secondary_language") Short secondaryLanguage,
      @JsonProperty("race_coce") RaceAndEthnicity raceAndEthnicity,
      @JsonProperty("addresses") Set<InvestigationAddress> addresses) {

    super();
    this.legacyDescriptor = legacyDescriptor;
    this.lastUpdatedBy = lastUpdatedBy;
    this.lastUpdatedAt = lastUpdatedAt;
    this.firstName = firstName;
    this.middleName = middleName;
    this.lastName = lastName;
    this.suffixTitle = suffixTitle;
    this.prefixTitle = prefixTitle;
    this.gender = gender;
    this.birthDate = birthDate;
    this.ssn = ssn;
    this.primaryLanguage = primaryLanguage;
    this.secondaryLanguage = secondaryLanguage;
    this.raceAndEthnicity = raceAndEthnicity;
    this.sensitivityIndicator = sensitivityIndicator;
    this.roles = roles;
    this.addresses = addresses;

  }



}
