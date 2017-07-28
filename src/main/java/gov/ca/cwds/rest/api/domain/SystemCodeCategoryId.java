package gov.ca.cwds.rest.api.domain;

/**
 * System code category IDs enumeration (this is also referred to as system metas)
 * 
 * @author CWDS API Team
 */
public final class SystemCodeCategoryId {

  //
  // This class could be an 'enum' but enums can not be used in annotations to provide property
  // values. Annotation property values must be known at compile time.
  //
  // We use these constants in annotations for validation, see for example ValidSystemCodeId
  //

  /**
   * Allegation Disposition, e.g) Substantiated, Inconclusive, Unfounded
   */
  public static final String ALLEGATION_DISPOSITION = "ALG_DSPC";

  /**
   * Cross Report Method, e.g) Electronic Report, Telephone Report, Child Abuse Form
   */
  public static final String CROSS_REPORT_METHOD = "XRPT_MTC";

  /**
   * Government Entity, e.g) Sacramento, Shasta, Marin
   */
  public static final String GOVERNMENT_ENTITY = "GVR_ENTC";

  /**
   * Referral Response, e.g) Immediate, 5 Day, 3 Day, Evaluate Out
   */
  public static final String REFERRAL_RESPONSE = "RFR_RSPC";

  /**
   * Service Component, e.g) Emergency Response, Permanent Placement, Family Reunification
   */
  public static final String SERVICE_COMPONENT = "SRV_CMPC";

  /**
   * Injury Harm Code ,e.g) General Neglect, Exploitation, Burns
   */
  public static final String INJURY_HARM_TYPE = "INJR_HMC";

  /**
   * Communication Method, e.g) Telephone, In-Person, Written
   */
  public static final String COMMUNICATION_METHOD = "CMM_MTHC";

  /**
   * Address Type e.g) Business, In-Person, Residence, Homeless
   */
  public static final String ADDRESS_TYPE = "ADDR_TPC";

  /**
   * Private constructor
   */
  private SystemCodeCategoryId() {}

}
