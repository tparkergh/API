package gov.ca.cwds.fixture;

import gov.ca.cwds.data.persistence.cms.ContactPartyDeliveredService;

/**
 * @author CWDS API Team
 *
 */
@SuppressWarnings("javadoc")
public class ContactPartyDeliverdServiceEntityBuilder {

  String thirdId = "ABC1906757";
  Short contactPartyType = (short) 421;
  String countySpecificCode = "99";
  String deliveredServiceId = "ABC123456y";

  public ContactPartyDeliveredService buildContactPartyDeliveredService() {
    return new ContactPartyDeliveredService(thirdId, contactPartyType, countySpecificCode,
        deliveredServiceId);
  }

  public String getThirdId() {
    return thirdId;
  }

  public ContactPartyDeliverdServiceEntityBuilder setThirdId(String thirdId) {
    this.thirdId = thirdId;
    return this;
  }

  public Short getContactPartyType() {
    return contactPartyType;
  }

  public ContactPartyDeliverdServiceEntityBuilder setContactPartyType(Short contactPartyType) {
    this.contactPartyType = contactPartyType;
    return this;
  }

  public String getCountySpecificCode() {
    return countySpecificCode;
  }

  public ContactPartyDeliverdServiceEntityBuilder setCountySpecificCode(String countySpecificCode) {
    this.countySpecificCode = countySpecificCode;
    return this;
  }

  public String getDeliveredServiceId() {
    return deliveredServiceId;
  }

  public ContactPartyDeliverdServiceEntityBuilder setDeliveredServiceId(String deliveredServiceId) {
    this.deliveredServiceId = deliveredServiceId;
    return this;
  }

}
