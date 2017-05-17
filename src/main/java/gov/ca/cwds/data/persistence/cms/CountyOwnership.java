package gov.ca.cwds.data.persistence.cms;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import gov.ca.cwds.data.persistence.PersistentObject;

/**
 * Class representing an CountyOwnership.
 * 
 * @author CWDS API Team
 */
@Entity
@Table(name = "CNTYOWNT")
@JsonPropertyOrder(alphabetic = true)
@JsonIgnoreProperties(ignoreUnknown = true)
@SuppressWarnings("javadoc")
public class CountyOwnership implements PersistentObject, Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  protected static final int CMS_ID_LEN = 10;
  protected static final String DATE_FORMAT = "yyyy-MM-dd";

  @GenericGenerator(name = "generator", strategy = "foreign",
      parameters = @Parameter(name = "property", value = "client"))
  @Id
  @Column(name = "ENTITY_ID", length = CMS_ID_LEN)
  private String entityId;

  @NotEmpty
  @Size(min = 1, max = 2)
  @Column(name = "ENTITY_CD")
  private String entityCode;

  @NotEmpty
  @Size(min = 1, max = 1)
  @Column(name = "MULTI_FLG")
  private String multiFlag = "N";

  @NotEmpty
  @Size(min = 1, max = 1)
  @Column(name = "CTY_00_FLG")
  private String county00Flag = "N";

  @NotEmpty
  @Size(min = 1, max = 1)
  @Column(name = "CTY_01_FLG")
  private String county01Flag = "N";

  @NotEmpty
  @Size(min = 1, max = 1)
  @Column(name = "CTY_02_FLG")
  private String county02Flag = "N";

  @NotEmpty
  @Size(min = 1, max = 1)
  @Column(name = "CTY_03_FLG")
  private String county03Flag = "N";

  @NotEmpty
  @Size(min = 1, max = 1)
  @Column(name = "CTY_04_FLG")
  private String county04Flag = "N";

  @NotEmpty
  @Size(min = 1, max = 1)
  @Column(name = "CTY_05_FLG")
  private String county05Flag = "N";

  @NotEmpty
  @Size(min = 1, max = 1)
  @Column(name = "CTY_06_FLG")
  private String county06Flag = "N";

  @NotEmpty
  @Size(min = 1, max = 1)
  @Column(name = "CTY_07_FLG")
  private String county07Flag = "N";

  @NotEmpty
  @Size(min = 1, max = 1)
  @Column(name = "CTY_08_FLG")
  private String county08Flag = "N";

  @NotEmpty
  @Size(min = 1, max = 1)
  @Column(name = "CTY_09_FLG")
  private String county09Flag = "N";

  @NotEmpty
  @Size(min = 1, max = 1)
  @Column(name = "CTY_10_FLG")
  private String county10Flag = "N";

  @NotEmpty
  @Size(min = 1, max = 1)
  @Column(name = "CTY_11_FLG")
  private String county11Flag = "N";

  @NotEmpty
  @Size(min = 1, max = 1)
  @Column(name = "CTY_12_FLG")
  private String county12Flag = "N";

  @NotEmpty
  @Size(min = 1, max = 1)
  @Column(name = "CTY_13_FLG")
  private String county13Flag = "N";

  @NotEmpty
  @Size(min = 1, max = 1)
  @Column(name = "CTY_14_FLG")
  private String county14Flag = "N";

  @NotEmpty
  @Size(min = 1, max = 1)
  @Column(name = "CTY_15_FLG")
  private String county15Flag = "N";

  @NotEmpty
  @Size(min = 1, max = 1)
  @Column(name = "CTY_16_FLG")
  private String county16Flag = "N";

  @NotEmpty
  @Size(min = 1, max = 1)
  @Column(name = "CTY_17_FLG")
  private String county17Flag = "N";

  @NotEmpty
  @Size(min = 1, max = 1)
  @Column(name = "CTY_18_FLG")
  private String county18Flag = "N";

  @NotEmpty
  @Size(min = 1, max = 1)
  @Column(name = "CTY_19_FLG")
  private String county19Flag = "N";

  @NotEmpty
  @Size(min = 1, max = 1)
  @Column(name = "CTY_20_FLG")
  private String county20Flag = "N";

  @NotEmpty
  @Size(min = 1, max = 1)
  @Column(name = "CTY_21_FLG")
  private String county21Flag = "N";

  @NotEmpty
  @Size(min = 1, max = 1)
  @Column(name = "CTY_22_FLG")
  private String county22Flag = "N";

  @NotEmpty
  @Size(min = 1, max = 1)
  @Column(name = "CTY_23_FLG")
  private String county23Flag = "N";

  @NotEmpty
  @Size(min = 1, max = 1)
  @Column(name = "CTY_24_FLG")
  private String county24Flag = "N";

  @NotEmpty
  @Size(min = 1, max = 1)
  @Column(name = "CTY_25_FLG")
  private String county25Flag = "N";

  @NotEmpty
  @Size(min = 1, max = 1)
  @Column(name = "CTY_26_FLG")
  private String county26Flag = "N";

  @NotEmpty
  @Size(min = 1, max = 1)
  @Column(name = "CTY_27_FLG")
  private String county27Flag = "N";

  @NotEmpty
  @Size(min = 1, max = 1)
  @Column(name = "CTY_28_FLG")
  private String county28Flag = "N";

  @NotEmpty
  @Size(min = 1, max = 1)
  @Column(name = "CTY_29_FLG")
  private String county29Flag = "N";

  @NotEmpty
  @Size(min = 1, max = 1)
  @Column(name = "CTY_30_FLG")
  private String county30Flag = "N";

  @NotEmpty
  @Size(min = 1, max = 1)
  @Column(name = "CTY_31_FLG")
  private String county31Flag = "N";

  @NotEmpty
  @Size(min = 1, max = 1)
  @Column(name = "CTY_32_FLG")
  private String county32Flag = "N";

  @NotEmpty
  @Size(min = 1, max = 1)
  @Column(name = "CTY_33_FLG")
  private String county33Flag = "N";

  @NotEmpty
  @Size(min = 1, max = 1)
  @Column(name = "CTY_34_FLG")
  private String county34Flag = "N";

  @NotEmpty
  @Size(min = 1, max = 1)
  @Column(name = "CTY_35_FLG")
  private String county35Flag = "N";

  @NotEmpty
  @Size(min = 1, max = 1)
  @Column(name = "CTY_36_FLG")
  private String county36Flag = "N";

  @NotEmpty
  @Size(min = 1, max = 1)
  @Column(name = "CTY_37_FLG")
  private String county37Flag = "N";

  @NotEmpty
  @Size(min = 1, max = 1)
  @Column(name = "CTY_38_FLG")
  private String county38Flag = "N";

  @NotEmpty
  @Size(min = 1, max = 1)
  @Column(name = "CTY_39_FLG")
  private String county39Flag = "N";

  @NotEmpty
  @Size(min = 1, max = 1)
  @Column(name = "CTY_40_FLG")
  private String county40Flag = "N";

  @NotEmpty
  @Size(min = 1, max = 1)
  @Column(name = "CTY_41_FLG")
  private String county41Flag = "N";

  @NotEmpty
  @Size(min = 1, max = 1)
  @Column(name = "CTY_42_FLG")
  private String county42Flag = "N";

  @NotEmpty
  @Size(min = 1, max = 1)
  @Column(name = "CTY_43_FLG")
  private String county43Flag = "N";

  @NotEmpty
  @Size(min = 1, max = 1)
  @Column(name = "CTY_44_FLG")
  private String county44Flag = "N";

  @NotEmpty
  @Size(min = 1, max = 1)
  @Column(name = "CTY_45_FLG")
  private String county45Flag = "N";

  @NotEmpty
  @Size(min = 1, max = 1)
  @Column(name = "CTY_46_FLG")
  private String county46Flag = "N";

  @NotEmpty
  @Size(min = 1, max = 1)
  @Column(name = "CTY_47_FLG")
  private String county47Flag = "N";

  @NotEmpty
  @Size(min = 1, max = 1)
  @Column(name = "CTY_48_FLG")
  private String county48Flag = "N";

  @NotEmpty
  @Size(min = 1, max = 1)
  @Column(name = "CTY_49_FLG")
  private String county49Flag = "N";

  @NotEmpty
  @Size(min = 1, max = 1)
  @Column(name = "CTY_50_FLG")
  private String county50Flag = "N";

  @NotEmpty
  @Size(min = 1, max = 1)
  @Column(name = "CTY_51_FLG")
  private String county51Flag = "N";

  @NotEmpty
  @Size(min = 1, max = 1)
  @Column(name = "CTY_52_FLG")
  private String county52Flag = "N";

  @NotEmpty
  @Size(min = 1, max = 1)
  @Column(name = "CTY_53_FLG")
  private String county53Flag = "N";

  @NotEmpty
  @Size(min = 1, max = 1)
  @Column(name = "CTY_54_FLG")
  private String county54Flag = "N";

  @NotEmpty
  @Size(min = 1, max = 1)
  @Column(name = "CTY_55_FLG")
  private String county55Flag = "N";

  @NotEmpty
  @Size(min = 1, max = 1)
  @Column(name = "CTY_56_FLG")
  private String county56Flag = "N";

  @NotEmpty
  @Size(min = 1, max = 1)
  @Column(name = "CTY_57_FLG")
  private String county57Flag = "N";

  @NotEmpty
  @Size(min = 1, max = 1)
  @Column(name = "CTY_58_FLG")
  private String county58Flag = "N";

  @NotEmpty
  @Size(min = 1, max = 1)
  @Column(name = "CTY_59_FLG")
  private String county59Flag = "N";

  @NotEmpty
  @Size(min = 1, max = 1)
  @Column(name = "CTY_60_FLG")
  private String county60Flag = "N";

  @NotEmpty
  @Size(min = 1, max = 1)
  @Column(name = "CTY_61_FLG")
  private String county61Flag = "N";

  @NotEmpty
  @Size(min = 1, max = 1)
  @Column(name = "CTY_62_FLG")
  private String county62Flag = "N";

  @NotEmpty
  @Size(min = 1, max = 1)
  @Column(name = "CTY_63_FLG")
  private String county63Flag = "N";

  @Type(type = "date")
  @Column(name = "DELETE_DT")
  private Date deleteDate;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "ENTITY_ID")
  private Client client;

  /**
   * Default Constructor
   */
  public CountyOwnership() {
    super();

  }

  /**
   * @param entityId - uniqueId
   * @param entityCode - entityCode
   * @param multiFlag - multiFlag
   * @param county00Flag - county00Flag
   * @param county01Flag - county01Flag
   * @param county02Flag - county02Flag
   * @param county03Flag - county03Flag
   * @param county04Flag - county04Flag
   * @param county05Flag - county05Flag
   * @param county06Flag - county06Flag
   * @param county07Flag - county07Flag
   * @param county08Flag - county08Flag
   * @param county09Flag - county09Flag
   * @param county10Flag - county10Flag
   * @param county11Flag - county11Flag
   * @param county12Flag - county12Flag
   * @param county13Flag - county13Flag
   * @param county14Flag - county14Flag
   * @param county15Flag - county15Flag
   * @param county16Flag - county16Flag
   * @param county17Flag - county17Flag
   * @param county18Flag - county18Flag
   * @param county19Flag - county19Flag
   * @param county20Flag - county20Flag
   * @param county21Flag - county21Flag
   * @param county22Flag - county22Flag
   * @param county23Flag - county23Flag
   * @param county24Flag - county24Flag
   * @param county25Flag - county25Flag
   * @param county26Flag - county26Flag
   * @param county27Flag - county27Flag
   * @param county28Flag - county28Flag
   * @param county29Flag - county29Flag
   * @param county30Flag - county30Flag
   * @param county31Flag - county31Flag
   * @param county32Flag - county32Flag
   * @param county33Flag - county33Flag
   * @param county34Flag - county34Flag
   * @param county35Flag - county35Flag
   * @param county36Flag - county36Flag
   * @param county37Flag - county37Flag
   * @param county38Flag - county38Flag
   * @param county39Flag - county39Flag
   * @param county40Flag - county40Flag
   * @param county41Flag - county41Flag
   * @param county42Flag - county42Flag
   * @param county43Flag - county43Flag
   * @param county44Flag - county44Flag
   * @param county45Flag - county45Flag
   * @param county46Flag - county46Flag
   * @param county47Flag - county47Flag
   * @param county48Flag - county48Flag
   * @param county49Flag - county49Flag
   * @param county50Flag - county50Flag
   * @param county51Flag - county51Flag
   * @param county52Flag - county52Flag
   * @param county53Flag - county53Flag
   * @param county54Flag - county54Flag
   * @param county55Flag - county55Flag
   * @param county56Flag - county56Flag
   * @param county57Flag - county57Flag
   * @param county58Flag - county58Flag
   * @param county59Flag - county59Flag
   * @param county60Flag - county60Flag
   * @param county61Flag - county61Flag
   * @param county62Flag - county62Flag
   * @param county63Flag - county63Flag
   * @param deleteDate - deleteDate
   * @param client - client
   */
  public CountyOwnership(String entityId, String entityCode, String multiFlag, String county00Flag,
      String county01Flag, String county02Flag, String county03Flag, String county04Flag,
      String county05Flag, String county06Flag, String county07Flag, String county08Flag,
      String county09Flag, String county10Flag, String county11Flag, String county12Flag,
      String county13Flag, String county14Flag, String county15Flag, String county16Flag,
      String county17Flag, String county18Flag, String county19Flag, String county20Flag,
      String county21Flag, String county22Flag, String county23Flag, String county24Flag,
      String county25Flag, String county26Flag, String county27Flag, String county28Flag,
      String county29Flag, String county30Flag, String county31Flag, String county32Flag,
      String county33Flag, String county34Flag, String county35Flag, String county36Flag,
      String county37Flag, String county38Flag, String county39Flag, String county40Flag,
      String county41Flag, String county42Flag, String county43Flag, String county44Flag,
      String county45Flag, String county46Flag, String county47Flag, String county48Flag,
      String county49Flag, String county50Flag, String county51Flag, String county52Flag,
      String county53Flag, String county54Flag, String county55Flag, String county56Flag,
      String county57Flag, String county58Flag, String county59Flag, String county60Flag,
      String county61Flag, String county62Flag, String county63Flag, Date deleteDate,
      Client client) {
    super();
    this.entityId = entityId;
    this.entityCode = entityCode;
    this.multiFlag = multiFlag;
    this.county00Flag = county00Flag;
    this.county01Flag = county01Flag;
    this.county02Flag = county02Flag;
    this.county03Flag = county03Flag;
    this.county04Flag = county04Flag;
    this.county05Flag = county05Flag;
    this.county06Flag = county06Flag;
    this.county07Flag = county07Flag;
    this.county08Flag = county08Flag;
    this.county09Flag = county09Flag;
    this.county10Flag = county10Flag;
    this.county11Flag = county11Flag;
    this.county12Flag = county12Flag;
    this.county13Flag = county13Flag;
    this.county14Flag = county14Flag;
    this.county15Flag = county15Flag;
    this.county16Flag = county16Flag;
    this.county17Flag = county17Flag;
    this.county18Flag = county18Flag;
    this.county19Flag = county19Flag;
    this.county20Flag = county20Flag;
    this.county21Flag = county21Flag;
    this.county22Flag = county22Flag;
    this.county23Flag = county23Flag;
    this.county24Flag = county24Flag;
    this.county25Flag = county25Flag;
    this.county26Flag = county26Flag;
    this.county27Flag = county27Flag;
    this.county28Flag = county28Flag;
    this.county29Flag = county29Flag;
    this.county30Flag = county30Flag;
    this.county31Flag = county31Flag;
    this.county32Flag = county32Flag;
    this.county33Flag = county33Flag;
    this.county34Flag = county34Flag;
    this.county35Flag = county35Flag;
    this.county36Flag = county36Flag;
    this.county37Flag = county37Flag;
    this.county38Flag = county38Flag;
    this.county39Flag = county39Flag;
    this.county40Flag = county40Flag;
    this.county41Flag = county41Flag;
    this.county42Flag = county42Flag;
    this.county43Flag = county43Flag;
    this.county44Flag = county44Flag;
    this.county45Flag = county45Flag;
    this.county46Flag = county46Flag;
    this.county47Flag = county47Flag;
    this.county48Flag = county48Flag;
    this.county49Flag = county49Flag;
    this.county50Flag = county50Flag;
    this.county51Flag = county51Flag;
    this.county52Flag = county52Flag;
    this.county53Flag = county53Flag;
    this.county54Flag = county54Flag;
    this.county55Flag = county55Flag;
    this.county56Flag = county56Flag;
    this.county57Flag = county57Flag;
    this.county58Flag = county58Flag;
    this.county59Flag = county59Flag;
    this.county60Flag = county60Flag;
    this.county61Flag = county61Flag;
    this.county62Flag = county62Flag;
    this.county63Flag = county63Flag;
    this.deleteDate = deleteDate;
    this.client = client;
  }

  public CountyOwnership(Client client) {
    this.client = client;
    this.entityCode = "C";
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.data.persistence.PersistentObject#getPrimaryKey()
   */
  @Override
  public Serializable getPrimaryKey() {
    return entityId;
  }

  /**
   * @return the entityId
   */
  public String getEntityId() {
    return entityId;
  }

  /**
   * @return the entityCode
   */
  public String getEntityCode() {
    return entityCode;
  }

  /**
   * @return the multiFlag
   */
  public String getMultiFlag() {
    return multiFlag;
  }

  /**
   * @return the county00Flag
   */
  public String getCounty00Flag() {
    return county00Flag;
  }

  /**
   * @return the county01Flag
   */
  public String getCounty01Flag() {
    return county01Flag;
  }

  /**
   * @return the county02Flag
   */
  public String getCounty02Flag() {
    return county02Flag;
  }

  /**
   * @return the county03Flag
   */
  public String getCounty03Flag() {
    return county03Flag;
  }

  /**
   * @return the county04Flag
   */
  public String getCounty04Flag() {
    return county04Flag;
  }

  /**
   * @return the county05Flag
   */
  public String getCounty05Flag() {
    return county05Flag;
  }

  /**
   * @return the county06Flag
   */
  public String getCounty06Flag() {
    return county06Flag;
  }

  /**
   * @return the county07Flag
   */
  public String getCounty07Flag() {
    return county07Flag;
  }

  /**
   * @return the county08Flag
   */
  public String getCounty08Flag() {
    return county08Flag;
  }

  /**
   * @return the county09Flag
   */
  public String getCounty09Flag() {
    return county09Flag;
  }

  /**
   * @return the county10Flag
   */
  public String getCounty10Flag() {
    return county10Flag;
  }

  /**
   * @return the county11Flag
   */
  public String getCounty11Flag() {
    return county11Flag;
  }

  /**
   * @return the county12Flag
   */
  public String getCounty12Flag() {
    return county12Flag;
  }

  /**
   * @return the county13Flag
   */
  public String getCounty13Flag() {
    return county13Flag;
  }

  /**
   * @return the county14Flag
   */
  public String getCounty14Flag() {
    return county14Flag;
  }

  /**
   * @return the county15Flag
   */
  public String getCounty15Flag() {
    return county15Flag;
  }

  /**
   * @return the county16Flag
   */
  public String getCounty16Flag() {
    return county16Flag;
  }

  /**
   * @return the county17Flag
   */
  public String getCounty17Flag() {
    return county17Flag;
  }

  /**
   * @return the county18Flag
   */
  public String getCounty18Flag() {
    return county18Flag;
  }

  /**
   * @return the county19Flag
   */
  public String getCounty19Flag() {
    return county19Flag;
  }

  /**
   * @return the county20Flag
   */
  public String getCounty20Flag() {
    return county20Flag;
  }

  /**
   * @return the county21Flag
   */
  public String getCounty21Flag() {
    return county21Flag;
  }

  /**
   * @return the county22Flag
   */
  public String getCounty22Flag() {
    return county22Flag;
  }

  /**
   * @return the county23Flag
   */
  public String getCounty23Flag() {
    return county23Flag;
  }

  /**
   * @return the county24Flag
   */
  public String getCounty24Flag() {
    return county24Flag;
  }

  /**
   * @return the county25Flag
   */
  public String getCounty25Flag() {
    return county25Flag;
  }

  /**
   * @return the county26Flag
   */
  public String getCounty26Flag() {
    return county26Flag;
  }

  /**
   * @return the county27Flag
   */
  public String getCounty27Flag() {
    return county27Flag;
  }

  /**
   * @return the county28Flag
   */
  public String getCounty28Flag() {
    return county28Flag;
  }

  /**
   * @return the county29Flag
   */
  public String getCounty29Flag() {
    return county29Flag;
  }

  /**
   * @return the county30Flag
   */
  public String getCounty30Flag() {
    return county30Flag;
  }

  /**
   * @return the county31Flag
   */
  public String getCounty31Flag() {
    return county31Flag;
  }

  /**
   * @return the county32Flag
   */
  public String getCounty32Flag() {
    return county32Flag;
  }

  /**
   * @return the county33Flag
   */
  public String getCounty33Flag() {
    return county33Flag;
  }

  /**
   * @return the county34Flag
   */
  public String getCounty34Flag() {
    return county34Flag;
  }

  /**
   * @return the county35Flag
   */
  public String getCounty35Flag() {
    return county35Flag;
  }

  /**
   * @return the county36Flag
   */
  public String getCounty36Flag() {
    return county36Flag;
  }

  /**
   * @return the county37Flag
   */
  public String getCounty37Flag() {
    return county37Flag;
  }

  /**
   * @return the county38Flag
   */
  public String getCounty38Flag() {
    return county38Flag;
  }

  /**
   * @return the county39Flag
   */
  public String getCounty39Flag() {
    return county39Flag;
  }

  /**
   * @return the county40Flag
   */
  public String getCounty40Flag() {
    return county40Flag;
  }

  /**
   * @return the county41Flag
   */
  public String getCounty41Flag() {
    return county41Flag;
  }

  /**
   * @return the county42Flag
   */
  public String getCounty42Flag() {
    return county42Flag;
  }

  /**
   * @return the county43Flag
   */
  public String getCounty43Flag() {
    return county43Flag;
  }

  /**
   * @return the county44Flag
   */
  public String getCounty44Flag() {
    return county44Flag;
  }

  /**
   * @return the county45Flag
   */
  public String getCounty45Flag() {
    return county45Flag;
  }

  /**
   * @return the county45Flag
   */
  public String getCounty46Flag() {
    return county46Flag;
  }

  /**
   * @return the county47Flag
   */
  public String getCounty47Flag() {
    return county47Flag;
  }

  /**
   * @return the county48Flag
   */
  public String getCounty48Flag() {
    return county48Flag;
  }

  /**
   * @return the county49Flag
   */
  public String getCounty49Flag() {
    return county49Flag;
  }

  /**
   * @return the county50Flag
   */
  public String getCounty50Flag() {
    return county50Flag;
  }

  /**
   * @return the county51Flag
   */
  public String getCounty51Flag() {
    return county51Flag;
  }

  /**
   * @return the county52Flag
   */
  public String getCounty52Flag() {
    return county52Flag;
  }

  /**
   * @return the county53Flag
   */
  public String getCounty53Flag() {
    return county53Flag;
  }

  /**
   * @return the county54Flag
   */
  public String getCounty54Flag() {
    return county54Flag;
  }

  /**
   * @return the county55Flag
   */
  public String getCounty55Flag() {
    return county55Flag;
  }

  /**
   * @return the county56Flag
   */
  public String getCounty56Flag() {
    return county56Flag;
  }

  /**
   * @return the county57Flag
   */
  public String getCounty57Flag() {
    return county57Flag;
  }

  /**
   * @return the county58Flag
   */
  public String getCounty58Flag() {
    return county58Flag;
  }

  /**
   * @return the county59Flag
   */
  public String getCounty59Flag() {
    return county59Flag;
  }

  /**
   * @return the county60Flag
   */
  public String getCounty60Flag() {
    return county60Flag;
  }

  /**
   * @return the county61Flag
   */
  public String getCounty61Flag() {
    return county61Flag;
  }

  /**
   * @return the county62Flag
   */
  public String getCounty62Flag() {
    return county62Flag;
  }

  /**
   * @return the county63Flag
   */
  public String getCounty63Flag() {
    return county63Flag;
  }

  /**
   * @return the deleteDate
   */
  public Date getDeleteDate() {
    return deleteDate;
  }

  /**
   * @return the client
   */
  public Client getClient() {
    return client;
  }

  public void setClient(Client client) {
    this.client = client;
  }

  public void setEntityId(String entityId) {
    this.entityId = entityId;
  }

  public void setEntityCode(String entityCode) {
    this.entityCode = entityCode;
  }

  public void setMultiFlag(String multiFlag) {
    this.multiFlag = multiFlag;
  }

  public void setCounty00Flag(String county00Flag) {
    this.county00Flag = county00Flag;
  }

  public void setCounty01Flag(String county01Flag) {
    this.county01Flag = county01Flag;
  }

  public void setCounty02Flag(String county02Flag) {
    this.county02Flag = county02Flag;
  }

  public void setCounty03Flag(String county03Flag) {
    this.county03Flag = county03Flag;
  }

  public void setCounty04Flag(String county04Flag) {
    this.county04Flag = county04Flag;
  }

  public void setCounty05Flag(String county05Flag) {
    this.county05Flag = county05Flag;
  }

  public void setCounty06Flag(String county06Flag) {
    this.county06Flag = county06Flag;
  }

  public void setCounty07Flag(String county07Flag) {
    this.county07Flag = county07Flag;
  }

  public void setCounty08Flag(String county08Flag) {
    this.county08Flag = county08Flag;
  }

  public void setCounty09Flag(String county09Flag) {
    this.county09Flag = county09Flag;
  }

  public void setCounty10Flag(String county10Flag) {
    this.county10Flag = county10Flag;
  }

  public void setCounty11Flag(String county11Flag) {
    this.county11Flag = county11Flag;
  }

  public void setCounty12Flag(String county12Flag) {
    this.county12Flag = county12Flag;
  }

  public void setCounty13Flag(String county13Flag) {
    this.county13Flag = county13Flag;
  }

  public void setCounty14Flag(String county14Flag) {
    this.county14Flag = county14Flag;
  }

  public void setCounty15Flag(String county15Flag) {
    this.county15Flag = county15Flag;
  }

  public void setCounty16Flag(String county16Flag) {
    this.county16Flag = county16Flag;
  }

  public void setCounty17Flag(String county17Flag) {
    this.county17Flag = county17Flag;
  }

  public void setCounty18Flag(String county18Flag) {
    this.county18Flag = county18Flag;
  }

  public void setCounty19Flag(String county19Flag) {
    this.county19Flag = county19Flag;
  }

  public void setCounty20Flag(String county20Flag) {
    this.county20Flag = county20Flag;
  }

  public void setCounty21Flag(String county21Flag) {
    this.county21Flag = county21Flag;
  }

  public void setCounty22Flag(String county22Flag) {
    this.county22Flag = county22Flag;
  }

  public void setCounty23Flag(String county23Flag) {
    this.county23Flag = county23Flag;
  }

  public void setCounty24Flag(String county24Flag) {
    this.county24Flag = county24Flag;
  }

  public void setCounty25Flag(String county25Flag) {
    this.county25Flag = county25Flag;
  }

  public void setCounty26Flag(String county26Flag) {
    this.county26Flag = county26Flag;
  }

  public void setCounty27Flag(String county27Flag) {
    this.county27Flag = county27Flag;
  }

  public void setCounty28Flag(String county28Flag) {
    this.county28Flag = county28Flag;
  }

  public void setCounty29Flag(String county29Flag) {
    this.county29Flag = county29Flag;
  }

  public void setCounty30Flag(String county30Flag) {
    this.county30Flag = county30Flag;
  }

  public void setCounty31Flag(String county31Flag) {
    this.county31Flag = county31Flag;
  }

  public void setCounty32Flag(String county32Flag) {
    this.county32Flag = county32Flag;
  }

  public void setCounty33Flag(String county33Flag) {
    this.county33Flag = county33Flag;
  }

  public void setCounty34Flag(String county34Flag) {
    this.county34Flag = county34Flag;
  }

  public void setCounty35Flag(String county35Flag) {
    this.county35Flag = county35Flag;
  }

  public void setCounty36Flag(String county36Flag) {
    this.county36Flag = county36Flag;
  }

  public void setCounty37Flag(String county37Flag) {
    this.county37Flag = county37Flag;
  }

  public void setCounty38Flag(String county38Flag) {
    this.county38Flag = county38Flag;
  }

  public void setCounty39Flag(String county39Flag) {
    this.county39Flag = county39Flag;
  }

  public void setCounty40Flag(String county40Flag) {
    this.county40Flag = county40Flag;
  }

  public void setCounty41Flag(String county41Flag) {
    this.county41Flag = county41Flag;
  }

  public void setCounty42Flag(String county42Flag) {
    this.county42Flag = county42Flag;
  }

  public void setCounty43Flag(String county43Flag) {
    this.county43Flag = county43Flag;
  }

  public void setCounty44Flag(String county44Flag) {
    this.county44Flag = county44Flag;
  }

  public void setCounty45Flag(String county45Flag) {
    this.county45Flag = county45Flag;
  }

  public void setCounty46Flag(String county46Flag) {
    this.county46Flag = county46Flag;
  }

  public void setCounty47Flag(String county47Flag) {
    this.county47Flag = county47Flag;
  }

  public void setCounty48Flag(String county48Flag) {
    this.county48Flag = county48Flag;
  }

  public void setCounty49Flag(String county49Flag) {
    this.county49Flag = county49Flag;
  }

  public void setCounty50Flag(String county50Flag) {
    this.county50Flag = county50Flag;
  }

  public void setCounty51Flag(String county51Flag) {
    this.county51Flag = county51Flag;
  }

  public void setCounty52Flag(String county52Flag) {
    this.county52Flag = county52Flag;
  }

  public void setCounty53Flag(String county53Flag) {
    this.county53Flag = county53Flag;
  }

  public void setCounty54Flag(String county54Flag) {
    this.county54Flag = county54Flag;
  }

  public void setCounty55Flag(String county55Flag) {
    this.county55Flag = county55Flag;
  }

  public void setCounty56Flag(String county56Flag) {
    this.county56Flag = county56Flag;
  }

  public void setCounty57Flag(String county57Flag) {
    this.county57Flag = county57Flag;
  }

  public void setCounty58Flag(String county58Flag) {
    this.county58Flag = county58Flag;
  }

  public void setCounty59Flag(String county59Flag) {
    this.county59Flag = county59Flag;
  }

  public void setCounty60Flag(String county60Flag) {
    this.county60Flag = county60Flag;
  }

  public void setCounty61Flag(String county61Flag) {
    this.county61Flag = county61Flag;
  }

  public void setCounty62Flag(String county62Flag) {
    this.county62Flag = county62Flag;
  }

  public void setCounty63Flag(String county63Flag) {
    this.county63Flag = county63Flag;
  }

  public void setDeleteDate(Date deleteDate) {
    this.deleteDate = deleteDate;
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
