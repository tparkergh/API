package gov.ca.cwds.rest.api.domain.cms;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.DomainObject;
import gov.ca.cwds.rest.api.domain.ReportingDomain;
import gov.ca.cwds.rest.api.domain.SystemCodeCategoryId;
import gov.ca.cwds.rest.validation.ValidLogicalId;
import io.dropwizard.validation.OneOf;
import io.swagger.annotations.ApiModelProperty;

/**
 * {@link DomainObject} representing an GovernmentOrganizationCrossReport
 * 
 * @author CWDS API Team
 */
public class GovernmentOrganizationCrossReport extends ReportingDomain
    implements Request, Response, Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  @NotNull
  @Size(min = 1, max = 10)
  @ApiModelProperty(required = true, readOnly = false, value = "ABC1234560", example = "ABC1234560")
  private String thirdId;

  @NotNull
  @Size(min = 2, max = 2)
  @ValidLogicalId(required = true, category = SystemCodeCategoryId.COUNTY_CODE)
  @ApiModelProperty(required = true, readOnly = false, value = "", example = "q1p")
  private String countySpecificCode;

  @NotNull
  @Size(min = 1, max = 10)
  @ApiModelProperty(required = true, readOnly = false, value = "zBC1234569", example = "zBC1234569")
  private String crossReportThirdId;

  @NotNull
  @Size(min = 1, max = 10)
  @ApiModelProperty(required = true, readOnly = false, value = "lBC123456l", example = "lBC123456l")
  private String referralId;

  @Size(min = 1, max = 10)
  @ApiModelProperty(required = false, readOnly = false, value = "ABC12345pp",
      example = "ABC12345pp")
  private String governmentOrganizationId;

  @NotNull
  @Size(max = 1)
  @OneOf(value = {"A", "C", "D", "L", "P"}, ignoreCase = false, ignoreWhitespace = true)
  @ApiModelProperty(required = true, readOnly = false, value = "", example = "D")
  private String organizationTypeInd;

  /**
   * @param thirdId - thirdId
   * @param countySpecificCode - countySpecificCode
   * @param crossReportThirdId - crossReportThirdId
   * @param referralId - referralId
   * @param governmentOrganizationId - governmentOrganizationId
   * @param organizationTypeInd - organizationTypeInd
   */
  public GovernmentOrganizationCrossReport(@JsonProperty("thirdId") String thirdId,
      @JsonProperty("countySpecificCode") String countySpecificCode,
      @JsonProperty("crossReportThirdId") String crossReportThirdId,
      @JsonProperty("referralId") String referralId,
      @JsonProperty("governmentOrganizationId") String governmentOrganizationId,
      @JsonProperty("organizationTypeInd") String organizationTypeInd) {
    super();
    this.thirdId = thirdId;
    this.countySpecificCode = countySpecificCode;
    this.crossReportThirdId = crossReportThirdId;
    this.referralId = referralId;
    this.governmentOrganizationId = governmentOrganizationId;
    this.organizationTypeInd = organizationTypeInd;
  }

  /**
   * Construct using persistence
   * 
   * @param persistedGovernmentOrganizationCrossReport persistence level
   *        governmentOrganizationCrossReport
   */
  public GovernmentOrganizationCrossReport(
      gov.ca.cwds.data.persistence.cms.GovernmentOrganizationCrossReport persistedGovernmentOrganizationCrossReport) {
    super();
    this.thirdId = persistedGovernmentOrganizationCrossReport.getThirdId();
    this.countySpecificCode = persistedGovernmentOrganizationCrossReport.getCountySpecificCode();
    this.crossReportThirdId = persistedGovernmentOrganizationCrossReport.getCrossReportThirdId();
    this.referralId = persistedGovernmentOrganizationCrossReport.getReferralId();
    this.governmentOrganizationId =
        persistedGovernmentOrganizationCrossReport.getGovernmentOrganizationId();
    this.organizationTypeInd = persistedGovernmentOrganizationCrossReport.getOrganizationTypeInd();
  }

  /**
   * @return the thirdId
   */
  public String getThirdId() {
    return thirdId;
  }

  /**
   * @return the countySpecificCode
   */
  public String getCountySpecificCode() {
    return countySpecificCode;
  }

  /**
   * @return the crossReport0
   */
  public String getCrossReportThirdId() {
    return crossReportThirdId;
  }

  /**
   * @return the crossReportT
   */
  public String getReferralId() {
    return referralId;
  }

  /**
   * @return the governmentOrganizationId
   */
  public String getGovernmentOrganizationId() {
    return governmentOrganizationId;
  }

  /**
   * @return the organizationTypeInd
   */
  public String getOrganizationTypeInd() {
    return organizationTypeInd;
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
