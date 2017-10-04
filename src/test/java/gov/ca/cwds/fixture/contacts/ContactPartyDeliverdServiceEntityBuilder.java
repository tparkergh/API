package gov.ca.cwds.fixture.contacts;

import gov.ca.cwds.data.persistence.contact.ContactPartyDeliveredServiceEntity;

import java.util.Date;

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
  private String lastUpdatedId = "0X5";
  private Date lastUpdatedTime = new Date();

  public ContactPartyDeliveredServiceEntity buildContactPartyDeliveredService() {
    return new ContactPartyDeliveredServiceEntity(thirdId, contactPartyType, countySpecificCode,
        deliveredServiceId, lastUpdatedId, lastUpdatedTime);
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

  public String getLastUpdatedId() {
    return lastUpdatedId;
  }

  public ContactPartyDeliverdServiceEntityBuilder setLastUpdatedId(String lastUpdatedId) {
    this.lastUpdatedId = lastUpdatedId;
    return this;
  }


  public Date getLastUpdatedTime() {
    return lastUpdatedTime;
  }

  public ContactPartyDeliverdServiceEntityBuilder setLastUpdatedTime(Date lastUpdatedTime) {
    this.lastUpdatedTime = lastUpdatedTime;
    return this;
  }



}
