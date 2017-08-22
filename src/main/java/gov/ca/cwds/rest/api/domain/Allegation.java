package gov.ca.cwds.rest.api.domain;

import static gov.ca.cwds.data.persistence.cms.CmsPersistentObject.CMS_ID_LEN;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.validation.ValidSystemCodeId;
import io.dropwizard.jackson.JsonSnakeCase;
import io.dropwizard.validation.OneOf;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * {@link DomainObject} representing an allegation
 * 
 * @author CWDS API Team
 */
@JsonSnakeCase
@ApiModel("nsAllegation")
public class Allegation extends ReportingDomain implements Request, Response {

  /**
   * Serialization version
   */
  private static final long serialVersionUID = 1L;

  @JsonProperty("legacy_source_table")
  @ApiModelProperty(required = true, value = "Legacy Source Table", example = "ALLGTN_T")
  private String legacySourceTable;

  @JsonProperty("legacy_id")
  @ApiModelProperty(required = true, value = "Legacy Id", example = "ABC1234567")
  @Size(max = CMS_ID_LEN)
  private String legacyId;

  @JsonProperty("victim_person_id")
  @ApiModelProperty(required = true, value = "id of victim", example = "12345")
  private long victimPersonId;

  @JsonProperty("perpetrator_person_id")
  @ApiModelProperty(required = true, value = "id of perpatrator", example = "12345")
  private long perpetratorPersonId;

  @JsonProperty("non_protecting_parent")
  @ApiModelProperty(required = true, value = "Non protecting parent Code", example = "U",
      allowableValues = "U, P, Y, N")
  @OneOf(value = {"U", "P", "Y", "N"})
  @Size(max = 1)
  private String nonProtectingParent;

  @JsonProperty("type")
  @ApiModelProperty(required = true, value = "type of allegation code", example = "1373")
  @NotNull
  @ValidSystemCodeId(required = true, category = SystemCodeCategoryId.INJURY_HARM_TYPE)
  private Short type;

  @JsonProperty("county")
  @ApiModelProperty(example = "34")
  private String county;

  /**
   * @param legacySourceTable - legacy source table
   * @param legacyId - legacy Id
   * @param victimPersonId - Person Id of victim
   * @param perpetratorPersonId - Person Id of perpetrator
   * @param type - Injury Harm type
   * @param county = County
   */
  public Allegation(@JsonProperty("legacy_source_table") String legacySourceTable,
      @JsonProperty("legacy_id") String legacyId,
      @JsonProperty("victim_person_id") long victimPersonId,
      @JsonProperty("perpetrator_person_id") long perpetratorPersonId,
      @JsonProperty("type") Short type, @JsonProperty("county") String county) {
    super();
    this.legacySourceTable = legacySourceTable;
    this.legacyId = legacyId;
    this.victimPersonId = victimPersonId;
    this.perpetratorPersonId = perpetratorPersonId;
    this.type = type;
    this.county = county;
  }

  /**
   * @return legacy source table
   */
  public String getLegacySourceTable() {
    return legacySourceTable;
  }

  /**
   * @param legacySourceTable - the legacy source table name
   */
  public void setLegacySourceTable(String legacySourceTable) {
    this.legacySourceTable = legacySourceTable;
  }

  /**
   * @return legacy id - the CWS/CMS database table name
   */
  public String getLegacyId() {
    return legacyId;
  }

  /**
   * @param legacyId - the idetifier from CWS/CMS database
   */
  public void setLegacyId(String legacyId) {
    this.legacyId = legacyId;
  }

  /**
   * @return victimPersonId
   */
  public long getVictimPersonId() {
    return victimPersonId;
  }

  /**
   * @return perpetratorPersonId
   */
  public long getPerpetratorPersonId() {
    return perpetratorPersonId;
  }

  /**
   * @return type
   */
  public Short getType() {
    return type;
  }

  /**
   * @return county
   */
  public String getCounty() {
    return county;
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.lang.Object#hashCode()
   */
  @Override
  public final int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((county == null) ? 0 : county.hashCode());
    result = prime * result + (int) (perpetratorPersonId ^ (perpetratorPersonId >>> 32));
    result = prime * result + ((type == null) ? 0 : type.hashCode());
    result = prime * result + ((nonProtectingParent == null) ? 0 : nonProtectingParent.hashCode());
    result = prime * result + ((legacySourceTable == null) ? 0 : legacySourceTable.hashCode());
    result = prime * result + ((legacyId == null) ? 0 : legacyId.hashCode());
    result = prime * result + (int) (victimPersonId ^ (victimPersonId >>> 32));
    return result;
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public final boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (!(obj instanceof Allegation)) {
      return false;
    }
    Allegation other = (Allegation) obj;
    if (county == null) {
      if (other.county != null)
        return false;
    } else if (!county.equals(other.county))
      return false;
    if (perpetratorPersonId != other.perpetratorPersonId)
      return false;
    if (type == null) {
      if (other.type != null)
        return false;
    } else if (!type.equals(other.type))
      return false;
    if (nonProtectingParent == null) {
      if (other.nonProtectingParent != null)
        return false;
    } else if (!nonProtectingParent.equals(other.nonProtectingParent))
      return false;
    if (legacySourceTable == null) {
      if (other.legacySourceTable != null)
        return false;
    } else if (!legacySourceTable.equals(other.legacySourceTable))
      return false;
    if (legacyId == null) {
      if (other.legacyId != null)
        return false;
    } else if (!legacyId.equals(other.legacyId))
      return false;
    if (victimPersonId != other.victimPersonId)
      return false;
    return true;
  }

}
