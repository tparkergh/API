package gov.ca.cwds.data.persistence.cms.rep;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.NamedNativeQueries;
import org.hibernate.annotations.NamedNativeQuery;
import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import gov.ca.cwds.data.persistence.PersistentObject;
import gov.ca.cwds.data.persistence.cms.BaseClient;
import gov.ca.cwds.data.persistence.cms.ReplicatedClientAddress;
import gov.ca.cwds.data.std.ApiMultipleLanguagesAware;
import gov.ca.cwds.data.std.ApiPersonAware;

/**
 * {@link PersistentObject} representing a Client as a {@link CmsReplicatedEntity}.
 * 
 * @author CWDS API Team
 */
// @SqlResultSetMapping(name = "mud",
// entities = {
// @EntityResult(entityClass = ReplicatedClient.class,
// fields = {@FieldResult(name = "id", column = "IDENTIFIER"),
// @FieldResult(name = "adjudicatedDelinquentIndicator", column = "ADJDEL_IND"),
// @FieldResult(name = "adoptionStatusCode", column = "ADPTN_STCD"),
// @FieldResult(name = "alienRegistrationNumber", column = "ALN_REG_NO"),
// @FieldResult(name = "birthCity", column = "BIRTH_CITY"),
// @FieldResult(name = "birthDate", column = "BIRTH_DT"),
// @FieldResult(name = "birthCountryCodeType", column = "B_CNTRY_C"),
// @FieldResult(name = "birthFacilityName", column = "BR_FAC_NM"),
// @FieldResult(name = "birthplaceVerifiedIndicator", column = "BP_VER_IND"),
// @FieldResult(name = "birthStateCodeType", column = "B_STATE_C"),
// @FieldResult(name = "childClientIndicatorVar", column = "CHLD_CLT_B"),
// @FieldResult(name = "clientIndexNumber", column = "CL_INDX_NO"),
// @FieldResult(name = "commentDescription", column = "COMMNT_DSC"),
// @FieldResult(name = "commonFirstName", column = "COM_FST_NM"),
// @FieldResult(name = "commonLastName", column = "COM_LST_NM"),
// @FieldResult(name = "commonMiddleName", column = "COM_MID_NM"),
// @FieldResult(name = "confidentialityActionDate", column = "CONF_ACTDT"),
// @FieldResult(name = "confidentialityInEffectIndicator", column = "CONF_EFIND"),
// @FieldResult(name = "creationDate", column = "CREATN_DT"),
// @FieldResult(name = "currCaChildrenServIndicator", column = "CURRCA_IND"),
// @FieldResult(name = "currentlyOtherDescription", column = "COTH_DESC"),
// @FieldResult(name = "currentlyRegionalCenterIndicator", column = "CURREG_IND"),
// @FieldResult(name = "deathDate", column = "DEATH_DT"),
// @FieldResult(name = "deathDateVerifiedIndicator", column = "DTH_DT_IND"),
// @FieldResult(name = "deathPlace", column = "DEATH_PLC"),
// @FieldResult(name = "deathReasonText", column = "DTH_RN_TXT"),
// @FieldResult(name = "driverLicenseNumber", column = "DRV_LIC_NO"),
// @FieldResult(name = "driverLicenseStateCodeType", column = "D_STATE_C"),
// @FieldResult(name = "emailAddress", column = "EMAIL_ADDR"),
// @FieldResult(name = "estimatedDobCode", column = "EST_DOB_CD"),
// @FieldResult(name = "ethUnableToDetReasonCode", column = "ETH_UD_CD"),
// @FieldResult(name = "fatherParentalRightTermDate", column = "FTERM_DT"),
// @FieldResult(name = "genderCode", column = "GENDER_CD"),
// @FieldResult(name = "healthSummaryText", column = "HEALTH_TXT"),
// @FieldResult(name = "hispanicOriginCode", column = "HISP_CD"),
// @FieldResult(name = "hispUnableToDetReasonCode", column = "HISP_UD_CD"),
// @FieldResult(name = "immigrationCountryCodeType", column = "I_CNTRY_C"),
// @FieldResult(name = "immigrationStatusType", column = "IMGT_STC"),
// @FieldResult(name = "incapacitatedParentCode", column = "INCAPC_CD"),
// @FieldResult(name = "individualHealthCarePlanIndicator", column = "HCARE_IND"),
// @FieldResult(name = "lastUpdatedId", column = "LST_UPD_ID"),
// @FieldResult(name = "lastUpdatedTime", column = "LST_UPD_TS"),
// @FieldResult(name = "limitationOnScpHealthIndicator", column = "LIMIT_IND"),
// @FieldResult(name = "literateCode", column = "LITRATE_CD"),
// @FieldResult(name = "maritalCohabitatnHstryIndicatorVar", column = "MAR_HIST_B"),
// @FieldResult(name = "maritalStatusType", column = "MRTL_STC"),
// @FieldResult(name = "militaryStatusCode", column = "MILT_STACD"),
// @FieldResult(name = "motherParentalRightTermDate", column = "MTERM_DT"),
// @FieldResult(name = "namePrefixDescription", column = "NMPRFX_DSC"),
// @FieldResult(name = "nameType", column = "NAME_TPC"),
// @FieldResult(name = "outstandingWarrantIndicator", column = "OUTWRT_IND"),
// @FieldResult(name = "prevCaChildrenServIndicator", column = "PREVCA_IND"),
// @FieldResult(name = "prevOtherDescription", column = "POTH_DESC"),
// @FieldResult(name = "prevRegionalCenterIndicator", column = "PREREG_IND"),
// @FieldResult(name = "primaryEthnicityType", column = "P_ETHNCTYC"),
// @FieldResult(name = "primaryLanguageType", column = "P_LANG_TPC"),
// @FieldResult(name = "religionType", column = "RLGN_TPC"),
// @FieldResult(name = "secondaryLanguageType", column = "S_LANG_TC"),
// @FieldResult(name = "sensitiveHlthInfoOnFileIndicator", column = "SNTV_HLIND"),
// @FieldResult(name = "sensitivityIndicator", column = "SENSTV_IND"),
// @FieldResult(name = "soc158PlacementCode", column = "SOCPLC_CD"),
// @FieldResult(name = "soc158SealedClientIndicator", column = "SOC158_IND"),
// @FieldResult(name = "socialSecurityNumber", column = "SS_NO"),
// @FieldResult(name = "socialSecurityNumChangedCode", column = "SSN_CHG_CD"),
// @FieldResult(name = "suffixTitleDescription", column = "SUFX_TLDSC"),
// @FieldResult(name = "tribalAncestryClientIndicatorVar", column = "TRBA_CLT_B"),
// @FieldResult(name = "tribalMembrshpVerifctnIndicatorVar", column = "TR_MBVRT_B"),
// @FieldResult(name = "unemployedParentCode", column = "UNEMPLY_CD"),
// @FieldResult(name = "zippyCreatedIndicator", column = "ZIPPY_IND"),
// @FieldResult(name = "replicationOperation", column = "IBMSNAP_OPERATION"),
// @FieldResult(name = "replicationDate", column = "IBMSNAP_LOGMARKER")}),
// @EntityResult(entityClass = ClientAddress.class,
// fields = {@FieldResult(name = "fkClient", column = "ca_FKCLIENT_T"),
// @FieldResult(name = "id", column = "ca_IDENTIFIER"),
// @FieldResult(name = "lastUpdatedId", column = "ca_LST_UPD_ID"),
// @FieldResult(name = "lastUpdatedTime", column = "ca_LST_UPD_TS"),
// @FieldResult(name = "addressType", column = "ca_ADDR_TPC"),
// @FieldResult(name = "bkInmtId", column = "ca_BK_INMT_ID"),
// @FieldResult(name = "effEndDt", column = "ca_EFF_END_DT"),
// @FieldResult(name = "effStartDt", column = "ca_EFF_STRTDT"),
// @FieldResult(name = "fkAddress", column = "ca_FKADDRS_T"),
// @FieldResult(name = "fkReferral", column = "ca_FKREFERL_T"),
// @FieldResult(name = "homelessInd", column = "ca_HOMLES_IND")}),
// @EntityResult(entityClass = Address.class,
// fields = {@FieldResult(name = "id", column = "a_IDENTIFIER"),
// @FieldResult(name = "city", column = "a_CITY_NM"),
// @FieldResult(name = "emergencyNumber", column = "a_EMRG_TELNO"),
// @FieldResult(name = "emergencyExtension", column = "a_EMRG_EXTNO"),
// @FieldResult(name = "frgAdrtB", column = "a_FRG_ADRT_B"),
// @FieldResult(name = "governmentEntityCd", column = "a_GVR_ENTC"),
// @FieldResult(name = "messageNumber", column = "a_MSG_TEL_NO"),
// @FieldResult(name = "messageExtension", column = "a_MSG_EXT_NO"),
// @FieldResult(name = "headerAddress", column = "a_HEADER_ADR"),
// @FieldResult(name = "primaryNumber", column = "a_PRM_TEL_NO"),
// @FieldResult(name = "primaryExtension", column = "a_PRM_EXT_NO"),
// @FieldResult(name = "state", column = "a_STATE_C"),
// @FieldResult(name = "streetName", column = "a_STREET_NM"),
// @FieldResult(name = "streetNumber", column = "a_STREET_NO"),
// @FieldResult(name = "zip", column = "a_ZIP_NO"),
// @FieldResult(name = "lastUpdatedId", column = "a_LST_UPD_ID"),
// @FieldResult(name = "lastUpdatedTime", column = "a_LST_UPD_TS"),
// @FieldResult(name = "addressDescription", column = "a_ADDR_DSC"),
// @FieldResult(name = "zip5", column = "a_ZIP_SFX_NO"),
// @FieldResult(name = "postDirCd", column = "a_POSTDIR_CD"),
// @FieldResult(name = "preDirCd", column = "a_PREDIR_CD"),
// @FieldResult(name = "streetSuffixCd", column = "a_ST_SFX_C"),
// @FieldResult(name = "unitDesignationCd", column = "a_UNT_DSGC"),
// @FieldResult(name = "unitNumber", column = "a_UNIT_NO")})})
@NamedNativeQueries({
    @NamedNativeQuery(
        name = "gov.ca.cwds.data.persistence.cms.rep.ReplicatedClient.findAllUpdatedAfter",
        query = "select z.IDENTIFIER, z.ADPTN_STCD, z.ALN_REG_NO, z.BIRTH_DT, "
            + "trim(z.BR_FAC_NM) as BR_FAC_NM, z.B_STATE_C, z.B_CNTRY_C, z.CHLD_CLT_B, "
            + "trim(z.COM_FST_NM) as COM_FST_NM, trim(z.COM_LST_NM) as COM_LST_NM, "
            + "trim(z.COM_MID_NM) as COM_MID_NM, z.CONF_EFIND, z.CONF_ACTDT, z.CREATN_DT, "
            + "z.DEATH_DT, trim(z.DTH_RN_TXT) as DTH_RN_TXT, trim(z.DRV_LIC_NO) as DRV_LIC_NO, "
            + "z.D_STATE_C, z.GENDER_CD, z.I_CNTRY_C, z.IMGT_STC, z.INCAPC_CD, "
            + "z.LITRATE_CD, z.MAR_HIST_B, z.MRTL_STC, z.MILT_STACD, z.NMPRFX_DSC, "
            + "z.NAME_TPC, z.OUTWRT_IND, z.P_ETHNCTYC, z.P_LANG_TPC, z.RLGN_TPC, "
            + "z.S_LANG_TC, z.SENSTV_IND, z.SNTV_HLIND, z.SS_NO, z.SSN_CHG_CD, "
            + "trim(z.SUFX_TLDSC) as SUFX_TLDSC, z.UNEMPLY_CD, z.LST_UPD_ID, z.LST_UPD_TS, "
            + "trim(z.COMMNT_DSC) as COMMNT_DSC, z.EST_DOB_CD, z.BP_VER_IND, z.HISP_CD, "
            + "z.CURRCA_IND, z.CURREG_IND, z.COTH_DESC, z.PREVCA_IND, z.PREREG_IND, "
            + "trim(z.POTH_DESC) as POTH_DESC, z.HCARE_IND, z.LIMIT_IND, "
            + "trim(z.BIRTH_CITY) as BIRTH_CITY, trim(z.HEALTH_TXT) as HEALTH_TXT, "
            + "z.MTERM_DT, z.FTERM_DT, z.ZIPPY_IND, trim(z.DEATH_PLC) as DEATH_PLC, "
            + "z.TR_MBVRT_B, z.TRBA_CLT_B, z.SOC158_IND, z.DTH_DT_IND, "
            + "trim(z.EMAIL_ADDR) as EMAIL_ADDR, z.ADJDEL_IND, z.ETH_UD_CD, "
            + "z.HISP_UD_CD, z.SOCPLC_CD, z.CL_INDX_NO, z.IBMSNAP_OPERATION, z.IBMSNAP_LOGMARKER "
            + "from {h-schema}CLIENT_T z WHERE z.IBMSNAP_LOGMARKER >= :after FOR READ ONLY",
        resultClass = ReplicatedClient.class),
    @NamedNativeQuery(
        name = "gov.ca.cwds.data.persistence.cms.rep.ReplicatedClient.findPartitionedBuckets",
        query = "select {a.*}, {b.*}, {c.*}  "
            + "from ( select mod(y.rn, CAST(:total_buckets AS INTEGER)) + 1 as bucket, y.* "
            + "from ( select row_number() over (order by 1) as rn, x.* "
            + "from {h-schema}CLIENT_T x where x.SOC158_IND ='N' and x.SENSTV_IND = 'N' "
            + "and x.identifier = '3YLLYNZ0LL' "
            + "AND x.IDENTIFIER >= :min_id and x.IDENTIFIER < :max_id ) y ) a "
            + "LEFT OUTER JOIN {h-schema}CL_ADDRT b ON a.IDENTIFIER = b.FKCLIENT_T and b.EFF_END_DT is null "
            + "LEFT OUTER JOIN {h-schema}ADDRS_T c ON b.FKADDRS_T = c.IDENTIFIER "
            + "where a.bucket = :bucket_num for read only",
        resultClass = ReplicatedClient.class)
    // query = "select z.IDENTIFIER, z.ADPTN_STCD, z.ALN_REG_NO, z.BIRTH_DT, "
    // + "trim(z.BR_FAC_NM) as BR_FAC_NM, z.B_STATE_C, z.B_CNTRY_C, z.CHLD_CLT_B, "
    // + "trim(z.COM_FST_NM) as COM_FST_NM, trim(z.COM_LST_NM) as COM_LST_NM, "
    // + "trim(z.COM_MID_NM) as COM_MID_NM, z.CONF_EFIND, z.CONF_ACTDT, z.CREATN_DT, "
    // + "z.DEATH_DT, trim(z.DTH_RN_TXT) as DTH_RN_TXT, trim(z.DRV_LIC_NO) as DRV_LIC_NO, "
    // + "z.D_STATE_C, z.GENDER_CD, z.I_CNTRY_C, z.IMGT_STC, z.INCAPC_CD, "
    // + "z.LITRATE_CD, z.MAR_HIST_B, z.MRTL_STC, z.MILT_STACD, z.NMPRFX_DSC, "
    // + "z.NAME_TPC, z.OUTWRT_IND, z.P_ETHNCTYC, z.P_LANG_TPC, z.RLGN_TPC, "
    // + "z.S_LANG_TC, z.SENSTV_IND, z.SNTV_HLIND, z.SS_NO, z.SSN_CHG_CD, "
    // + "trim(z.SUFX_TLDSC) as SUFX_TLDSC, z.UNEMPLY_CD, z.LST_UPD_ID, z.LST_UPD_TS, "
    // + "trim(z.COMMNT_DSC) as COMMNT_DSC, z.EST_DOB_CD, z.BP_VER_IND, z.HISP_CD, "
    // + "z.CURRCA_IND, z.CURREG_IND, z.COTH_DESC, z.PREVCA_IND, z.PREREG_IND, "
    // + "trim(z.POTH_DESC) as POTH_DESC, z.HCARE_IND, z.LIMIT_IND, "
    // + "trim(z.BIRTH_CITY) as BIRTH_CITY, trim(z.HEALTH_TXT) as HEALTH_TXT, "
    // + "z.MTERM_DT, z.FTERM_DT, z.ZIPPY_IND, trim(z.DEATH_PLC) as DEATH_PLC, "
    // + "z.TR_MBVRT_B, z.TRBA_CLT_B, z.SOC158_IND, z.DTH_DT_IND, "
    // + "trim(z.EMAIL_ADDR) as EMAIL_ADDR, z.ADJDEL_IND, z.ETH_UD_CD, "
    // + "z.HISP_UD_CD, z.SOCPLC_CD, z.CL_INDX_NO "
    // + ", 'U' as IBMSNAP_OPERATION, z.LST_UPD_TS as IBMSNAP_LOGMARKER "
    // + "from ( select mod(y.rn, CAST(:total_buckets AS INTEGER)) + 1 as bucket, y.* "
    // + "from ( select row_number() over (order by 1) as rn, x.* "
    // + "from {h-schema}CLIENT_T x where x.SOC158_IND ='N' and x.SENSTV_IND = 'N' "
    // + "AND x.IDENTIFIER >= :min_id and x.IDENTIFIER < :max_id "
    // + ") y ) z where z.bucket = :bucket_num for read only",
    // resultClass = ReplicatedClient.class)
    // @NamedNativeQuery(
    // name = "gov.ca.cwds.data.persistence.cms.rep.ReplicatedClient.findPartitionedBuckets",
    // query = "SELECT c.IDENTIFIER, c.ADPTN_STCD, c.ALN_REG_NO, c.BIRTH_DT, "
    // + "trim(c.BR_FAC_NM) as BR_FAC_NM, c.B_STATE_C, c.B_CNTRY_C, c.CHLD_CLT_B, "
    // + "trim(c.COM_FST_NM) as COM_FST_NM, trim(c.COM_LST_NM) as COM_LST_NM, "
    // + "trim(c.COM_MID_NM) as COM_MID_NM, c.CONF_EFIND, c.CONF_ACTDT, c.CREATN_DT, "
    // + "c.DEATH_DT, trim(c.DTH_RN_TXT) as DTH_RN_TXT, trim(c.DRV_LIC_NO) as DRV_LIC_NO, "
    // + "c.D_STATE_C, c.GENDER_CD, c.I_CNTRY_C, c.IMGT_STC, c.INCAPC_CD, "
    // + "c.LITRATE_CD, c.MAR_HIST_B, c.MRTL_STC, c.MILT_STACD, c.NMPRFX_DSC, "
    // + "c.NAME_TPC, c.OUTWRT_IND, c.P_ETHNCTYC, c.P_LANG_TPC, c.RLGN_TPC, "
    // + "c.S_LANG_TC, c.SENSTV_IND, c.SNTV_HLIND, c.SS_NO, c.SSN_CHG_CD, "
    // + "trim(c.SUFX_TLDSC) as SUFX_TLDSC, c.UNEMPLY_CD, c.LST_UPD_ID, c.LST_UPD_TS, "
    // + "trim(c.COMMNT_DSC) as COMMNT_DSC, c.EST_DOB_CD, c.BP_VER_IND, c.HISP_CD, "
    // + "c.CURRCA_IND, c.CURREG_IND, c.COTH_DESC, c.PREVCA_IND, c.PREREG_IND, "
    // + "trim(c.POTH_DESC) as POTH_DESC, c.HCARE_IND, c.LIMIT_IND, "
    // + "trim(c.BIRTH_CITY) as BIRTH_CITY, trim(c.HEALTH_TXT) as HEALTH_TXT, "
    // + "c.MTERM_DT, c.FTERM_DT, c.ZIPPY_IND, trim(c.DEATH_PLC) as DEATH_PLC, "
    // + "c.TR_MBVRT_B, c.TRBA_CLT_B, c.SOC158_IND, c.DTH_DT_IND, "
    // + "trim(c.EMAIL_ADDR) as EMAIL_ADDR, c.ADJDEL_IND, c.ETH_UD_CD, "
    // + "c.HISP_UD_CD, c.SOCPLC_CD, c.CL_INDX_NO "
    // + ", 'U' as IBMSNAP_OPERATION, c.LST_UPD_TS as IBMSNAP_LOGMARKER "
    // + ", ca.FKCLIENT_T as ca_FKCLIENT_T," + "ca.IDENTIFIER as ca_IDENTIFIER,"
    // + "ca.LST_UPD_ID as ca_LST_UPD_ID," + "ca.LST_UPD_TS as ca_LST_UPD_TS,"
    // + "ca.ADDR_TPC as ca_ADDR_TPC," + "ca.BK_INMT_ID as ca_BK_INMT_ID,"
    // + "ca.EFF_END_DT as ca_EFF_END_DT," + "ca.EFF_STRTDT as ca_EFF_STRTDT,"
    // + "ca.FKADDRS_T as ca_FKADDRS_T," + "ca.FKREFERL_T as ca_FKREFERL_T,"
    // + "ca.HOMLES_IND as ca_HOMLES_IND," + "a.IDENTIFIER as a_IDENTIFIER,"
    // + "a.CITY_NM as a_CITY_NM," + "a.EMRG_TELNO as a_EMRG_TELNO,"
    // + "a.EMRG_EXTNO as a_EMRG_EXTNO," + "a.FRG_ADRT_B as a_FRG_ADRT_B,"
    // + "a.GVR_ENTC as a_GVR_ENTC," + "a.MSG_TEL_NO as a_MSG_TEL_NO,"
    // + "a.MSG_EXT_NO as a_MSG_EXT_NO," + "a.HEADER_ADR as a_HEADER_ADR,"
    // + "a.PRM_TEL_NO as a_PRM_TEL_NO," + "a.PRM_EXT_NO as a_PRM_EXT_NO,"
    // + "a.STATE_C as a_STATE_C," + "a.STREET_NM as a_STREET_NM,"
    // + "a.STREET_NO as a_STREET_NO," + "a.ZIP_NO as a_ZIP_NO,"
    // + "a.LST_UPD_ID as a_LST_UPD_ID," + "a.LST_UPD_TS as a_LST_UPD_TS,"
    // + "a.ADDR_DSC as a_ADDR_DSC," + "a.ZIP_SFX_NO as a_ZIP_SFX_NO,"
    // + "a.POSTDIR_CD as a_POSTDIR_CD," + "a.PREDIR_CD as a_PREDIR_CD,"
    // + "a.ST_SFX_C as a_ST_SFX_C," + "a.UNT_DSGC as a_UNT_DSGC," + "a.UNIT_NO as a_UNIT_NO "
    // + "FROM {h-schema}CLIENT_T c "
    // + "LEFT OUTER JOIN {h-schema}CL_ADDRT ca ON c.IDENTIFIER = ca.FKCLIENT_T and ca.EFF_END_DT is
    // null "
    // + "LEFT OUTER JOIN {h-schema}ADDRS_T a ON ca.FKADDRS_T = a.IDENTIFIER "
    // + "where c.IDENTIFIER in (" + " select z.IDENTIFIER "
    // + " from ( select mod(y.rn, CAST(:total_buckets AS INTEGER)) + 1 as bucket, y.* "
    // + " from ( select row_number() over (order by 1) as rn, x.* "
    // + " from {h-schema}CLIENT_T x where x.SOC158_IND ='N' and x.SENSTV_IND = 'N' "
    // + " AND x.IDENTIFIER >= :min_id and x.IDENTIFIER < :max_id "
    // + " ) y ) z where z.bucket = :bucket_num" + ") "
    // + "ORDER BY c.IDENTIFIER, ca.EFF_STRTDT " + "for read only ",
    // resultSetMapping = "mud")
})
@Entity
@Table(name = "CLIENT_T")
@JsonPropertyOrder(alphabetic = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReplicatedClient extends BaseClient
    implements ApiPersonAware, ApiMultipleLanguagesAware, CmsReplicatedEntity {

  /**
   * Base serialization version. Increment by class version.
   */
  private static final long serialVersionUID = 1L;

  @Enumerated(EnumType.STRING)
  @Column(name = "IBMSNAP_OPERATION", updatable = false)
  private CmsReplicationOperation replicationOperation;

  @Type(type = "timestamp")
  @Column(name = "IBMSNAP_LOGMARKER", updatable = false)
  private Date replicationDate;

  @OneToMany(fetch = FetchType.EAGER, mappedBy = "fkClient")
  protected Set<ReplicatedClientAddress> clientAddresses = new LinkedHashSet<>();

  /**
   * Get client address linkages.
   *
   * @return client addresses
   */
  public Set<ReplicatedClientAddress> getClientAddresses() {
    return clientAddresses;
  }

  /**
   * Set the client address linkages.
   *
   * @param clientAddresses Set of client address linkages
   */
  public void setClientAddresses(Set<ReplicatedClientAddress> clientAddresses) {
    this.clientAddresses = clientAddresses;
  }

  /**
   * Add a client address linkage.
   *
   * @param clientAddress client address
   */
  public void addClientAddress(ReplicatedClientAddress clientAddress) {
    this.clientAddresses.add(clientAddress);
  }

  @Override
  public CmsReplicationOperation getReplicationOperation() {
    return replicationOperation;
  }

  @Override
  public void setReplicationOperation(CmsReplicationOperation replicationOperation) {
    this.replicationOperation = replicationOperation;
  }

  @Override
  public Date getReplicationDate() {
    return replicationDate;
  }

  @Override
  public void setReplicationDate(Date replicationDate) {
    this.replicationDate = replicationDate;
  }

}

