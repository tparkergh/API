package gov.ca.cwds.rest.api.domain.investigation;

import java.util.Set;

import javax.validation.Valid;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.DomainObject;
import gov.ca.cwds.rest.api.domain.ReportingDomain;
import gov.ca.cwds.rest.api.domain.SystemCodeCategoryId;
import gov.ca.cwds.rest.validation.ValidSystemCodeId;
import io.dropwizard.jackson.JsonSnakeCase;
import io.swagger.annotations.ApiModelProperty;

/**
 * {@link DomainObject} representing an Allegation
 * 
 * @author CWDS API Team
 */
@JsonSnakeCase
@JsonPropertyOrder({"allegation_type", "created_by_screener", "allegation_sub_types", "disposition",
    "rationale", "legacy_descriptor", "victim", "perpetrator"})
public class Allegation extends ReportingDomain implements Response {

  private static final long serialVersionUID = 1L;

  @JsonProperty("allegation_type")
  @ApiModelProperty(required = false, readOnly = false, value = "Allegation Type", example = "2179")
  @ValidSystemCodeId(required = true, category = SystemCodeCategoryId.ALLEGATION_TYPE)
  private Short allegationType;

  @JsonProperty("created_by_screener")
  @ApiModelProperty(required = true, readOnly = false,
      value = "allegation created during screening")
  private Boolean createdByScreener;

  @JsonProperty("allegation_sub_types")
  @ApiModelProperty(required = false, readOnly = false)
  @Valid
  private Set<AllegationSubType> allegationSubType;

  @JsonProperty("disposition")
  @ApiModelProperty(required = false, readOnly = false)
  private Short dispositionType;

  @JsonProperty("rationale")
  @ApiModelProperty(required = false, readOnly = false)
  private String rationale;

  @JsonProperty("legacy_descriptor")
  private CmsRecordDescriptor legacyDescriptor;

  @JsonProperty("victim")
  private AllegationPerson victim;

  @JsonProperty("perpetrator")
  private AllegationPerson perpetrator;


  /**
   * default constructor
   */
  public Allegation() {
    super();
  }


  /**
   * @param allegationType - LOV of allegation type
   * @param createdByScreener - is this created by a screener
   * @param allegationSubType - list of allegation sub-types
   * @param dispositionType - LOV of Allegation Disposition Type
   * @param rationale - rational of the disposition
   * @param legacyDescriptor - CMS record descriptor
   * @param victim - allegation victim (required)
   * @param perpetrator - allegation perpetrator
   */
  public Allegation(@ValidSystemCodeId(required = true, category = "INJR_HMC") Short allegationType,
      Boolean createdByScreener, Set<AllegationSubType> allegationSubType, Short dispositionType,
      String rationale, CmsRecordDescriptor legacyDescriptor, AllegationPerson victim,
      AllegationPerson perpetrator) {
    super();
    this.allegationType = allegationType;
    this.createdByScreener = createdByScreener;
    this.allegationSubType = allegationSubType;
    this.dispositionType = dispositionType;
    this.rationale = rationale;
    this.legacyDescriptor = legacyDescriptor;
    this.victim = victim;
    this.perpetrator = perpetrator;
  }


  /**
   * @return - the Allegation type
   */
  public Short getAllegationType() {
    return allegationType;
  }


  /**
   * @param allegationType - LOV of Injury Harm Type
   */
  public void setAllegationType(Short allegationType) {
    this.allegationType = allegationType;
  }


  /**
   * @return - created by a screening
   */
  public Boolean getCreatedByScreener() {
    return createdByScreener;
  }


  /**
   * @param createdByScreener - true or false
   */
  public void setCreatedByScreener(Boolean createdByScreener) {
    this.createdByScreener = createdByScreener;
  }


  public Set<AllegationSubType> getAllegationSubType() {
    return allegationSubType;
  }


  public void setAllegationSubType(Set<AllegationSubType> allegationSubType) {
    this.allegationSubType = allegationSubType;
  }


  public Short getDispositionType() {
    return dispositionType;
  }


  public void setDispositionType(Short dispositionType) {
    this.dispositionType = dispositionType;
  }


  public String getRationale() {
    return rationale;
  }


  public void setRationale(String rationale) {
    this.rationale = rationale;
  }


  public AllegationPerson getVictim() {
    return victim;
  }

  public void setLegacyDescriptor(CmsRecordDescriptor legacyDescriptor) {
    this.legacyDescriptor = legacyDescriptor;
  }

  public CmsRecordDescriptor getLegacyDescriptor() {
    return legacyDescriptor;
  }

  public void setVictim(AllegationPerson victim) {
    this.victim = victim;
  }


  public AllegationPerson getPerpetrator() {
    return perpetrator;
  }


  public void setPerpetrator(AllegationPerson perpetrator) {
    this.perpetrator = perpetrator;
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
