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

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
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
        resultClass = ReplicatedClient.class)})
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

  // ==============
  // SUPPORT:
  // ==============

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
  }

  @Override
  public final int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this, false);
  }

  @Override
  public final boolean equals(Object obj) {
    return EqualsBuilder.reflectionEquals(this, obj, false);
  }

}

