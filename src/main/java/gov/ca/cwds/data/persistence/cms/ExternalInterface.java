package gov.ca.cwds.data.persistence.cms;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import gov.ca.cwds.data.persistence.PersistentObject;

/**
 * {@link CmsPersistentObject} class representing a External Interface Table
 * 
 * @author CWDS API Team
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "EXTINF_T")
@IdClass(ExternalInterface.PrimaryKey.class)
@JsonPropertyOrder(alphabetic = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ExternalInterface implements PersistentObject, Serializable {
  /**
   * Hibernate annotation {@link IdClass} requires that members match the id columns of the parent
   * class. From the Javadoc of said annotation,
   * 
   * <blockquote> "The names of the fields or properties in the primary key class and the primary
   * key fields or properties of the entity must correspond and their types must be the same."
   * </blockquote>
   * 
   * @see VarargPrimaryKey
   */
  public static final class PrimaryKey implements Serializable {
    private static final long serialVersionUID = 1L;
    private Date submitlTimestamp;
    private Integer sequenceNumber;
    private String logonUserId;

    /**
     * Default constructor.
     */
    public PrimaryKey() {
      // Default values.
    }

    /**
     * @param submitlTimestamp the interface timestamp
     * @param sequenceNumber the sequence number
     * @param logonUserId the logon user id
     */
    public PrimaryKey(Date submitlTimestamp, Integer sequenceNumber, String logonUserId) {
      super();
      this.submitlTimestamp = submitlTimestamp;
      this.sequenceNumber = sequenceNumber;
      this.logonUserId = logonUserId;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
      final int PRIME = 31;
      int result = 1;
      result = PRIME * result + ((submitlTimestamp == null) ? 0 : submitlTimestamp.hashCode());
      result = PRIME * result + ((logonUserId == null) ? 0 : logonUserId.hashCode());
      result = PRIME * result + ((sequenceNumber == null) ? 0 : sequenceNumber.hashCode());
      return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      }
      if (obj == null) {
        return false;
      }
      if (getClass() != obj.getClass()) {
        return false;
      }
      PrimaryKey other = (PrimaryKey) obj;
      if (submitlTimestamp == null) {
        if (other.submitlTimestamp != null) {
          return false;
        }
      } else if (!submitlTimestamp.equals(other.submitlTimestamp)) {
        return false;
      }
      if (logonUserId == null) {
        if (other.logonUserId != null) {
          return false;
        }
      } else if (!logonUserId.equals(other.logonUserId)) {
        return false;
      }
      if (sequenceNumber == null) {
        if (other.sequenceNumber != null) {
          return false;
        }
      } else if (!sequenceNumber.equals(other.sequenceNumber)) {
        return false;
      }
      return true;
    }

  }

  @Type(type = "short")
  @Column(name = "AID_TPC")
  private Short aidTypeCode = 0;

  @Column(name = "AST_UNT_CD")
  private String assignmentUnitCode = "";

  @Type(type = "short")
  @Column(name = "CRSP_TPC")
  private Short clearanceResponseType = 0;

  @Column(name = "FBI_IND") // attribute no longer in use
  private String fbiIndicator = "";

  @Type(type = "short")
  @Column(name = "GVR_ENTC")
  private Short governmentEntityCode = 0;

  @Id
  @Column(name = "L_USERID")
  private String logonUserId = "";

  @Column(name = "LICENSE_NO")
  private String licenseNumber = "";

  @NotEmpty
  @Size(min = 1, max = 1)
  @Column(name = "OPER_TYPE")
  private String operationType;

  @Column(name = "OTHER_DATA")
  private String otherData = "";

  @Column(name = "PERSON_NO")
  private String personNumber = "";

  @Column(name = "PRM_KEY_1")
  private String primaryKey1 = "";

  @Column(name = "PRM_KEY_2")
  private String primaryKey2 = "";

  @Column(name = "PRM_KEY_3")
  private String primaryKey3 = "";

  @Column(name = "PRM_KEY_4")
  private String primaryKey4 = "";

  @Column(name = "PRM_KEY_5")
  private String primaryKey5 = "";

  @Column(name = "PRM_KEY_6")
  private String primaryKey6 = "";

  @Column(name = "PRM_KEY_7")
  private String primaryKey7 = "";

  @Column(name = "PRM_KEY_8")
  private String primaryKey8 = "";

  @Column(name = "RAP_ID") // attribute no longer in use
  private String rapIdentifier = "";

  @Column(name = "RECEIVE_DT")
  private String responseReceivedDate = "";

  @Column(name = "RESPONS_DT") // attribute no longer in use
  private String clearanceResponseDate = "";

  @Id
  @Type(type = "integer")
  @Column(name = "SEQ_NO")
  private Integer sequenceNumber;

  @Column(name = "SERIAL_NO")
  private String serialNumber = "";

  @Column(name = "START_DT")
  private String startDate = "";

  @Id
  @Type(type = "timestamp")
  @Column(name = "SUBMTL_TS")
  private Date submitlTimestamp;

  @NotEmpty
  @Column(name = "TABLE_NAME")
  private String tableName;

  /**
   * Default constructor
   * 
   * Required for Hibernate
   */
  public ExternalInterface() {
    super();
  }

  /**
   * @param aidTypeCode the aid type code
   * @param assignmentUnitCode the assignment unit code
   * @param clearanceResponseType the clearance response type
   * @param fbiIndicator the fbi indicator
   * @param governmentEntityCode the government entity code
   * @param logonUserId the logon user Id
   * @param licenseNumber the license number
   * @param operationType the crud type
   * @param otherData the other data
   * @param personNumber the person number
   * @param primaryKey1 the primary key
   * @param primaryKey2 the primary key
   * @param primaryKey3 the primary key
   * @param primaryKey4 the primary key
   * @param primaryKey5 the primary key
   * @param primaryKey6 the primary key
   * @param primaryKey7 the primary key
   * @param primaryKey8 the primary key
   * @param rapIdentifier the rpi identifier
   * @param responseReceivedDate the response received date
   * @param clearanceResponseDate the clearance response date
   * @param sequenceNumber the sequence number
   * @param serialNumber the serial number
   * @param startDate the start date
   * @param submitlTimestamp the submital time stamp
   * @param tableName the table name
   */
  public ExternalInterface(Short aidTypeCode, String assignmentUnitCode,
      Short clearanceResponseType, String fbiIndicator, Short governmentEntityCode,
      String logonUserId, String licenseNumber, String operationType, String otherData,
      String personNumber, String primaryKey1, String primaryKey2, String primaryKey3,
      String primaryKey4, String primaryKey5, String primaryKey6, String primaryKey7,
      String primaryKey8, String rapIdentifier, String responseReceivedDate,
      String clearanceResponseDate, Integer sequenceNumber, String serialNumber, String startDate,
      Date submitlTimestamp, String tableName) {
    super();
    this.aidTypeCode = aidTypeCode;
    this.assignmentUnitCode = assignmentUnitCode;
    this.clearanceResponseType = clearanceResponseType;
    this.fbiIndicator = fbiIndicator;
    this.governmentEntityCode = governmentEntityCode;
    this.logonUserId = logonUserId;
    this.licenseNumber = licenseNumber;
    this.operationType = operationType;
    this.otherData = otherData;
    this.personNumber = personNumber;
    this.primaryKey1 = primaryKey1;
    this.primaryKey2 = primaryKey2;
    this.primaryKey3 = primaryKey3;
    this.primaryKey4 = primaryKey4;
    this.primaryKey5 = primaryKey5;
    this.primaryKey6 = primaryKey6;
    this.primaryKey7 = primaryKey7;
    this.primaryKey8 = primaryKey8;
    this.rapIdentifier = rapIdentifier;
    this.responseReceivedDate = responseReceivedDate;
    this.clearanceResponseDate = clearanceResponseDate;
    this.sequenceNumber = sequenceNumber;
    this.serialNumber = serialNumber;
    this.startDate = startDate;
    this.submitlTimestamp = submitlTimestamp;
    this.tableName = tableName;
  }

  /**
   * @return the aidTypeCode
   */
  public Short getAidTypeCode() {
    return aidTypeCode;
  }

  /**
   * @param aidTypeCode the aidTypeCode to set
   */
  public void setAidTypeCode(Short aidTypeCode) {
    this.aidTypeCode = aidTypeCode;
  }

  /**
   * @return the assignmentUnitCode
   */
  public String getAssignmentUnitCode() {
    return assignmentUnitCode;
  }

  /**
   * @param assignmentUnitCode the assignmentUnitCode to set
   */
  public void setAssignmentUnitCode(String assignmentUnitCode) {
    this.assignmentUnitCode = assignmentUnitCode;
  }

  /**
   * @return the clearanceResponseType
   */
  public Short getClearanceResponseType() {
    return clearanceResponseType;
  }

  /**
   * @param clearanceResponseType the clearanceResponseType to set
   */
  public void setClearanceResponseType(Short clearanceResponseType) {
    this.clearanceResponseType = clearanceResponseType;
  }

  /**
   * @return the fbiIndicator
   */
  public String getFbiIndicator() {
    return fbiIndicator;
  }

  /**
   * @param fbiIndicator the fbiIndicator to set
   */
  public void setFbiIndicator(String fbiIndicator) {
    this.fbiIndicator = fbiIndicator;
  }

  /**
   * @return the governmentEntityCode
   */
  public Short getGovernmentEntityCode() {
    return governmentEntityCode;
  }

  /**
   * @param governmentEntityCode the governmentEntityCode to set
   */
  public void setGovernmentEntityCode(Short governmentEntityCode) {
    this.governmentEntityCode = governmentEntityCode;
  }

  /**
   * @return the logonUserId
   */
  public String getLogonUserId() {
    return logonUserId;
  }

  /**
   * @param logonUserId the logonUserId to set
   */
  public void setLogonUserId(String logonUserId) {
    this.logonUserId = logonUserId;
  }

  /**
   * @return the licenseNumber
   */
  public String getLicenseNumber() {
    return licenseNumber;
  }

  /**
   * @param licenseNumber the licenseNumber to set
   */
  public void setLicenseNumber(String licenseNumber) {
    this.licenseNumber = licenseNumber;
  }

  /**
   * @return the operationType
   */
  public String getOperationType() {
    return operationType;
  }

  /**
   * @param operationType the operationType to set
   */
  public void setOperationType(String operationType) {
    this.operationType = operationType;
  }

  /**
   * @return the otherData
   */
  public String getOtherData() {
    return otherData;
  }

  /**
   * @param otherData the otherData to set
   */
  public void setOtherData(String otherData) {
    this.otherData = otherData;
  }

  /**
   * @return the personNumber
   */
  public String getPersonNumber() {
    return personNumber;
  }

  /**
   * @param personNumber the personNumber to set
   */
  public void setPersonNumber(String personNumber) {
    this.personNumber = personNumber;
  }

  /**
   * @return the primaryKey1
   */
  public String getPrimaryKey1() {
    return primaryKey1;
  }

  /**
   * @param primaryKey1 the primaryKey1 to set
   */
  public void setPrimaryKey1(String primaryKey1) {
    this.primaryKey1 = primaryKey1;
  }

  /**
   * @return the primaryKey2
   */
  public String getPrimaryKey2() {
    return primaryKey2;
  }

  /**
   * @param primaryKey2 the primaryKey2 to set
   */
  public void setPrimaryKey2(String primaryKey2) {
    this.primaryKey2 = primaryKey2;
  }

  /**
   * @return the primaryKey3
   */
  public String getPrimaryKey3() {
    return primaryKey3;
  }

  /**
   * @param primaryKey3 the primaryKey3 to set
   */
  public void setPrimaryKey3(String primaryKey3) {
    this.primaryKey3 = primaryKey3;
  }

  /**
   * @return the primaryKey4
   */
  public String getPrimaryKey4() {
    return primaryKey4;
  }

  /**
   * @param primaryKey4 the primaryKey4 to set
   */
  public void setPrimaryKey4(String primaryKey4) {
    this.primaryKey4 = primaryKey4;
  }

  /**
   * @return the primaryKey5
   */
  public String getPrimaryKey5() {
    return primaryKey5;
  }

  /**
   * @param primaryKey5 the primaryKey5 to set
   */
  public void setPrimaryKey5(String primaryKey5) {
    this.primaryKey5 = primaryKey5;
  }

  /**
   * @return the primaryKey6
   */
  public String getPrimaryKey6() {
    return primaryKey6;
  }

  /**
   * @param primaryKey6 the primaryKey6 to set
   */
  public void setPrimaryKey6(String primaryKey6) {
    this.primaryKey6 = primaryKey6;
  }

  /**
   * @return the primaryKey7
   */
  public String getPrimaryKey7() {
    return primaryKey7;
  }

  /**
   * @param primaryKey7 the primaryKey7 to set
   */
  public void setPrimaryKey7(String primaryKey7) {
    this.primaryKey7 = primaryKey7;
  }

  /**
   * @return the primaryKey8
   */
  public String getPrimaryKey8() {
    return primaryKey8;
  }

  /**
   * @param primaryKey8 the primaryKey8 to set
   */
  public void setPrimaryKey8(String primaryKey8) {
    this.primaryKey8 = primaryKey8;
  }

  /**
   * @return the rapIdentifier
   */
  public String getRapIdentifier() {
    return rapIdentifier;
  }

  /**
   * @param rapIdentifier the rapIdentifier to set
   */
  public void setRapIdentifier(String rapIdentifier) {
    this.rapIdentifier = rapIdentifier;
  }

  /**
   * @return the responseReceivedDate
   */
  public String getResponseReceivedDate() {
    return responseReceivedDate;
  }

  /**
   * @param responseReceivedDate the responseReceivedDate to set
   */
  public void setResponseReceivedDate(String responseReceivedDate) {
    this.responseReceivedDate = responseReceivedDate;
  }

  /**
   * @return the clearanceResponseDate
   */
  public String getClearanceResponseDate() {
    return clearanceResponseDate;
  }

  /**
   * @param clearanceResponseDate the clearanceResponseDate to set
   */
  public void setClearanceResponseDate(String clearanceResponseDate) {
    this.clearanceResponseDate = clearanceResponseDate;
  }

  /**
   * @return the sequenceNumber
   */
  public Integer getSequenceNumber() {
    return sequenceNumber;
  }

  /**
   * @param sequenceNumber the sequenceNumber to set
   */
  public void setSequenceNumber(Integer sequenceNumber) {
    this.sequenceNumber = sequenceNumber;
  }

  /**
   * @return the serialNumber
   */
  public String getSerialNumber() {
    return serialNumber;
  }

  /**
   * @param serialNumber the serialNumber to set
   */
  public void setSerialNumber(String serialNumber) {
    this.serialNumber = serialNumber;
  }

  /**
   * @return the startDate
   */
  public String getStartDate() {
    return startDate;
  }

  /**
   * @param startDate the startDate to set
   */
  public void setStartDate(String startDate) {
    this.startDate = startDate;
  }

  /**
   * @return the submitlTimestamp
   */
  public Date getSubmitlTimestamp() {
    return submitlTimestamp;
  }

  /**
   * @param submitlTimestamp the submitlTimestamp to set
   */
  public void setSubmitlTimestamp(Date submitlTimestamp) {
    this.submitlTimestamp = submitlTimestamp;
  }

  /**
   * @return the tableName
   */
  public String getTableName() {
    return tableName;
  }

  /**
   * @param tableName the tableName to set
   */
  public void setTableName(String tableName) {
    this.tableName = tableName;
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

  @Override
  public Serializable getPrimaryKey() {
    return new PrimaryKey(this.getSubmitlTimestamp(), this.getSequenceNumber(), getLogonUserId());
  }

}
