package gov.ca.cwds.fixture.investigation;

import org.joda.time.DateTime;

import gov.ca.cwds.rest.api.domain.LegacyDescriptor;
import gov.ca.cwds.rest.api.domain.investigation.InvestigationAddress;

@SuppressWarnings("javadoc")
public class InvestigationAddressEntityBuilder {
  protected LegacyDescriptor legacyDescriptor;
  protected String streetAddress = "741 Evergreen Ct";
  protected String city = "Springfield";
  protected Short state = 1828;
  protected String zip = "95757";
  protected Short type = 32;
  private DateTime now = new DateTime("2010-10-01T15:26:42.000-0700");

  public InvestigationAddress build() {
    legacyDescriptor =
        new LegacyDescriptor("1234567ABC", "001-2000-3399-415790", now, "CLIENT_T", "Client");
    return new InvestigationAddress(legacyDescriptor, streetAddress, city, state, zip, type);

  }

  public InvestigationAddressEntityBuilder setLegacyDescriptor(LegacyDescriptor legacyDescriptor) {

    this.legacyDescriptor = legacyDescriptor;
    return this;
  }

  public InvestigationAddressEntityBuilder setStreetAddress(String streetAddress) {
    this.streetAddress = streetAddress;
    return this;
  }

  public InvestigationAddressEntityBuilder setCity(String city) {
    this.city = city;
    return this;
  }

  public InvestigationAddressEntityBuilder setState(Short state) {
    this.state = state;
    return this;
  }

  public InvestigationAddressEntityBuilder setZip(String zip) {
    this.zip = zip;
    return this;
  }

  public InvestigationAddressEntityBuilder setType(Short type) {
    this.type = type;
    return this;
  }

  public LegacyDescriptor getLegacyDescriptor() {
    return legacyDescriptor;
  }

  public String getStreetAddress() {
    return streetAddress;
  }

  public String getCity() {
    return city;
  }

  public Short getState() {
    return state;
  }

  public String getZip() {
    return zip;
  }

  public Short getType() {
    return type;
  }



}
