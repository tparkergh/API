package gov.ca.cwds.rest.api.domain.investigation.contact;

import org.apache.commons.lang3.StringUtils;

/**
 * Representing a Deliverd To Individual Code
 * 
 * This defines each type of participant to whom a service was delivered. Only Clients (C) may
 * participate in any non-contact type of services. Service Providers (P), Collateral Individuals
 * (O), Reporters (R), Attorneys (A), or Substitute Care Providers (S) can only participate in
 * Contacts.
 * 
 * @author CWDS API Team
 *
 */
public enum DeliveredToIndividualCode {
  CLIENT("C", "CLIENT_T", "Client"), SERVICE_PROVIDER("P", "SVC_PVRT", "Service Provider"), COLLATERAL_INDIVIDUAL(
      "O", "COLTRL_T", "Collateral Individual"), REPORTER("R", "REPTR_T", "Reporter"), ATTORNEY(
      "A", "ATTRNY_T", "Attorney"), SUBSTITUTE_CARE_PROVIDER("S", "SB_PVDRT",
      "Substitute Care Provider");

  private final String code;
  private final String value;
  private final String description;

  private DeliveredToIndividualCode(String code, String value, String description) {
    this.code = code;
    this.value = value;
    this.description = description;
  }

  public String getCode() {
    return code;
  }

  public String getValue() {
    return value;
  }

  public String getDescription() {
    return description;
  }

  /**
   * Lookup by Code
   * 
   * @param code The Delivered to Individual code
   * @return DeliveredToIndividualCode for given code if found, null otherwise.
   */
  public static DeliveredToIndividualCode lookupByCode(String code) {
    if (StringUtils.isBlank(code)) {
      return null;
    }

    DeliveredToIndividualCode deliveredToIndividualCode = null;
    for (DeliveredToIndividualCode deliveredToIndividual : DeliveredToIndividualCode.values()) {
      if (deliveredToIndividual.getCode().equals(code.trim())) {
        deliveredToIndividualCode = deliveredToIndividual;
        break;
      }
    }
    return deliveredToIndividualCode;
  }
}
