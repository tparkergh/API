package gov.ca.cwds.fixture;

/**
 * 
 * @author CWDS API Team
 */
@SuppressWarnings("javadoc")
public class AddressResourceBuilder {

  String legacySourceTable = "";
  String legacyId = "";
  String streetAddress = "123 Main";
  String city = "Sacramento";
  String state = "CA";
  Integer zip = 95757;
  String type = "Home";

  public AddressResourceBuilder setLegacySourceTable(String legacySourceTable) {
    this.legacySourceTable = legacySourceTable;
    return this;
  }

  public AddressResourceBuilder setLegacyId(String legacyId) {
    this.legacyId = legacyId;
    return this;
  }

  public AddressResourceBuilder setStreetAddress(String streetAddress) {
    this.streetAddress = streetAddress;
    return this;
  }

  public AddressResourceBuilder setCity(String city) {
    this.city = city;
    return this;
  }

  public AddressResourceBuilder setState(String state) {
    this.state = state;
    return this;
  }

  public AddressResourceBuilder setZip(Integer zip) {
    this.zip = zip;
    return this;
  }

  public AddressResourceBuilder setType(String type) {
    this.type = type;
    return this;
  }

  public gov.ca.cwds.rest.api.domain.Address createAddress() {
    return new gov.ca.cwds.rest.api.domain.Address(legacySourceTable, legacyId, streetAddress, city,
        state, zip, type);
  }
}
