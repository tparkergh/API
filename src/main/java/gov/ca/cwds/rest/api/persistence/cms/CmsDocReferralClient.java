package gov.ca.cwds.rest.api.persistence.cms;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedNativeQuery;

import org.hibernate.annotations.Type;

import gov.ca.cwds.rest.api.persistence.PersistentObject;

/**
 * {@link PersistentObject} represents a custom query to find Referral and Client references.
 * Documents.
 * 
 * @author CWDS API Team
 */
@Entity
@NamedNativeQuery(name = "DocReferalClient", query = " SELECT REF.IDENTIFIER AS REFERL_ID "
    + "        , CLT.IDENTIFIER AS CLIENT_ID " + "        , RTRIM(CLT.COM_FST_NM) as COM_FST_NM "
    + "        , RTRIM(CLT.COM_MID_NM) as COM_MID_NM "
    + "        , RTRIM(CLT.COM_LST_NM) as COM_LST_NM "
    + "        , ( RTRIM(NMT.FIRST_NM) || ' ' || RTRIM(NMT.MIDDLE_NM) || ' ' || RTRIM(NMT.LAST_NM) ) AS OTHERNAME "
    + "        , RTRIM(CDTN.SHORT_DSC) AS NAME_TYPE " + "        , CLT.BIRTH_DT "
    + "        , ( RTRIM(ADR.STREET_NM) || ' ' || RTRIM(ADR.STREET_NO) || ' ' || RTRIM(ADR.CITY_NM) || ' ' || ADR.ZIP_NO ) AS ADDRESS "
    + "        , RTRIM(CDTA.SHORT_DSC) AS ADDRESS_TYPE " + "        , CNT.DOC_HANDLE "
    + "        , CNT.DOC_NAME " + "        , CNT.DOC_DATE AS DOC_ADDED_UPD " + " , ref.LST_UPD_ID "
    + " , ref.LST_UPD_TS " + " FROM (((((((((CWSINT.REFR_CLT AS RCL "
    + "     JOIN CWSINT.REFERL_T AS REF " + "    ON REF.IDENTIFIER = RCL.FKREFERL_T " + "   ) "
    + "     JOIN CWSINT.DRMSDOCT AS DRM " + "    ON ( DRM.IDENTIFIER = REF.ALGDSC_DOC "
    + "       OR DRM.IDENTIFIER = REF.ER_REF_DOC " + "       OR DRM.IDENTIFIER = REF.INVSTG_DOC "
    + "       ) " + "   ) " + "     JOIN CWSINT.TSCNTRLT AS CNT "
    + "    ON CNT.DOC_HANDLE = DRM.DOCHNDL_NM " + "    AND CNT.CMPRS_PRG <> 'DELETED' "
    + "    AND CNT.DOC_HANDLE <> 'DUMMY' " + "   ) " + "     JOIN CWSINT.CLIENT_T AS CLT "
    + "    ON CLT.IDENTIFIER = RCL.FKCLIENT_T " + "   ) "
    + "    LEFT OUTER JOIN CWSINT.OCL_NM_T AS NMT " + "    ON NMT.FKCLIENT_T = RCL.FKCLIENT_T "
    + "   ) " + "    LEFT OUTER JOIN CWSINT.SYS_CD_C AS CDTN "
    + "    ON CDTN.SYS_ID = NMT.NAME_TPC " + "    AND CDTN.FKS_META_T = 'NAME_TPC' " + "   ) "
    + "    LEFT OUTER JOIN CWSINT.CL_ADDRT AS CAD " + "    ON CAD.FKCLIENT_T = CLT.IDENTIFIER "
    + "    AND CAD.EFF_END_DT IS NULL " + "   ) " + "    LEFT OUTER JOIN CWSINT.ADDRS_T AS ADR "
    + "    ON ADR.IDENTIFIER = CAD.FKADDRS_T " + "   ) "
    + "    LEFT OUTER JOIN CWSINT.SYS_CD_C AS CDTA " + "    ON CDTA.SYS_ID = CAD.ADDR_TPC "
    + "    AND CDTA.FKS_META_T = 'ADDR_TPC' " + "   ) " + " WHERE CNT.DOC_HANDLE = :docHandle "
    + " UNION ALL " + " SELECT REF.IDENTIFIER AS REFERL_ID "
    + "        , CLT.IDENTIFIER AS CLIENT_ID " + "        , RTRIM(CLT.COM_FST_NM) as COM_FST_NM "
    + "        , RTRIM(CLT.COM_MID_NM) as COM_MID_NM "
    + "        , RTRIM(CLT.COM_LST_NM) as COM_LST_NM "
    + "        , ( RTRIM(NMT.FIRST_NM) || ' ' || RTRIM(NMT.MIDDLE_NM) || ' ' || RTRIM(NMT.LAST_NM) ) AS OTHERNAME "
    + "        , RTRIM(CDTN.SHORT_DSC) AS NAME_TYPE " + "        , CLT.BIRTH_DT "
    + "        , ( RTRIM(ADR.STREET_NM) || ' ' || RTRIM(ADR.STREET_NO) || ' ' || RTRIM(ADR.CITY_NM) || ' ' || ADR.ZIP_NO ) AS ADDRESS "
    + "        , RTRIM(CDTA.SHORT_DSC) AS ADDRESS_TYPE " + "        , CNT.DOC_HANDLE "
    + "        , CNT.DOC_NAME " + "        , CNT.DOC_DATE AS DOC_ADDED_UPD " + " , ref.LST_UPD_ID "
    + " , ref.LST_UPD_TS " + " FROM ((((((((((CWSINT.REFR_CLT AS RCL "
    + "     JOIN CWSINT.OTH_DOCT AS OTD " + "    ON OTD.FKREFERL_T = RCL.FKREFERL_T " + "   ) "
    + "     JOIN CWSINT.REFERL_T AS REF " + "    ON REF.IDENTIFIER = OTD.FKREFERL_T " + "   ) "
    + "     JOIN CWSINT.DRMSDOCT AS DRM " + "    ON DRM.IDENTIFIER = OTD.FKDRMSDOCT " + "   ) "
    + "     JOIN CWSINT.TSCNTRLT AS CNT " + "    ON CNT.DOC_HANDLE = DRM.DOCHNDL_NM "
    + "    AND CNT.CMPRS_PRG <> 'DELETED' " + "    AND CNT.DOC_HANDLE <> 'DUMMY' " + "   ) "
    + "     JOIN CWSINT.CLIENT_T AS CLT " + "    ON CLT.IDENTIFIER = RCL.FKCLIENT_T " + "   ) "
    + "    LEFT OUTER JOIN CWSINT.OCL_NM_T AS NMT " + "    ON NMT.FKCLIENT_T = RCL.FKCLIENT_T "
    + "   ) " + "    LEFT OUTER JOIN CWSINT.SYS_CD_C AS CDTN "
    + "    ON CDTN.SYS_ID = NMT.NAME_TPC " + "    AND CDTN.FKS_META_T = 'NAME_TPC' " + "   ) "
    + "    LEFT OUTER JOIN CWSINT.CL_ADDRT AS CAD " + "    ON CAD.FKCLIENT_T = CLT.IDENTIFIER "
    + "    AND CAD.EFF_END_DT IS NULL " + "   ) " + "    LEFT OUTER JOIN CWSINT.ADDRS_T AS ADR "
    + "    ON ADR.IDENTIFIER = CAD.FKADDRS_T " + "   ) "
    + "    LEFT OUTER JOIN CWSINT.SYS_CD_C AS CDTA " + "    ON CDTA.SYS_ID = CAD.ADDR_TPC "
    + "    AND CDTA.FKS_META_T = 'ADDR_TPC' " + "   ) " + " WHERE CNT.DOC_HANDLE = :docHandle "
    + " ORDER BY 1,2 " + " FOR READ ONLY ", resultClass = CmsDocReferralClient.class)
public class CmsDocReferralClient extends CmsPersistentObject {

  private static final long serialVersionUID = 1L;

  @Id
  @Column(name = "REFERL_ID", length = 10)
  private String referlId;

  @Id
  @Column(name = "CLIENT_ID", length = 10)
  private String clientId;

  @Id
  @Column(name = "DOC_HANDLE", length = 30)
  private String docHandle;

  @Column(name = "DOC_NAME")
  private String docName;

  @Type(type = "date")
  @Column(name = "DOC_ADDED_UPD")
  private Date docAddedDate;

  @Column(name = "COM_FST_NM", length = 20)
  private String commonFirstName;

  @Column(name = "COM_MID_NM", length = 20)
  private String commonMiddleName;

  @Column(name = "COM_LST_NM", length = 25)
  private String commonLastName;

  @Type(type = "date")
  @Column(name = "BIRTH_DT")
  private Date birthDate;

  @Column(name = "OTHERNAME")
  private String otherName;

  @Column(name = "NAME_TYPE")
  private String nameType;

  @Column(name = "ADDRESS")
  private String address;

  @Column(name = "ADDRESS_TYPE")
  private String addressType;

  /**
   * Default constructor
   * 
   * Required for Hibernate.
   */
  public CmsDocReferralClient() {
    super();
  }

  public CmsDocReferralClient(String docHandle, String referlId, String clientId,
      String commonFirstName, String commonMiddleName, String commonLastName, Date birthDate,
      String otherName, String nameType, String address, String addressType) {
    super();
    this.docHandle = docHandle;
    this.referlId = referlId;
    this.clientId = clientId;
    this.commonFirstName = commonFirstName;
    this.commonMiddleName = commonMiddleName;
    this.commonLastName = commonLastName;
    this.birthDate = birthDate;
    this.otherName = otherName;
    this.nameType = nameType;
    this.address = address;
    this.addressType = addressType;
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.api.persistence.PersistentObject#getPrimaryKey()
   */
  @Override
  public Serializable getPrimaryKey() {
    return new VarargPrimaryKey(getDocHandle(), getReferlId(), getClientId(), getCommonLastName(),
        getCommonFirstName(), String.valueOf(getBirthDate().getTime()), getOtherName(),
        getAddressType(), getAddress());
  }

  /**
   * @return the id
   */
  public String getId() {
    return this.docHandle;
  }

  public String getReferlId() {
    return referlId;
  }

  public void setReferlId(String referlId) {
    this.referlId = referlId;
  }

  public String getClientId() {
    return clientId;
  }

  public void setClientId(String clientId) {
    this.clientId = clientId;
  }

  public String getDocHandle() {
    return docHandle;
  }

  public void setDocHandle(String docHandle) {
    this.docHandle = docHandle;
  }

  public String getDocName() {
    return docName;
  }

  public void setDocName(String docName) {
    this.docName = docName;
  }

  public Date getDocAddedDate() {
    return docAddedDate;
  }

  public void setDocAddedDate(Date docAddedDate) {
    this.docAddedDate = docAddedDate;
  }

  public String getCommonFirstName() {
    return commonFirstName;
  }

  public void setCommonFirstName(String commonFirstName) {
    this.commonFirstName = commonFirstName;
  }

  public String getCommonMiddleName() {
    return commonMiddleName;
  }

  public void setCommonMiddleName(String commonMiddleName) {
    this.commonMiddleName = commonMiddleName;
  }

  public String getCommonLastName() {
    return commonLastName;
  }

  public void setCommonLastName(String commonLastName) {
    this.commonLastName = commonLastName;
  }

  public Date getBirthDate() {
    return birthDate;
  }

  public void setBirthDate(Date birthDate) {
    this.birthDate = birthDate;
  }

  /**
   * @return the otherName
   */
  public String getOtherName() {
    return otherName;
  }

  /**
   * @param otherName the otherName to set
   */
  public void setOtherName(String otherName) {
    this.otherName = otherName;
  }

  /**
   * @return the nameType
   */
  public String getNameType() {
    return nameType;
  }

  /**
   * @param nameType the nameType to set
   */
  public void setNameType(String nameType) {
    this.nameType = nameType;
  }

  /**
   * @return the address
   */
  public String getAddress() {
    return address;
  }

  /**
   * @param address the address to set
   */
  public void setAddress(String address) {
    this.address = address;
  }

  /**
   * @return the addressType
   */
  public String getAddressType() {
    return addressType;
  }

  /**
   * @param addressType the addressType to set
   */
  public void setAddressType(String addressType) {
    this.addressType = addressType;
  }

}
