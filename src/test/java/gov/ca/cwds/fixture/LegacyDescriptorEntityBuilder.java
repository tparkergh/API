package gov.ca.cwds.fixture;

import org.joda.time.DateTime;

import gov.ca.cwds.rest.api.domain.LegacyDescriptor;

@SuppressWarnings({"javadoc"})
public class LegacyDescriptorEntityBuilder {

  protected String id = "1234567ABC";
  protected String uiId = "111-222-333-4444";
  private DateTime dateTime = new DateTime("2017-10-01T15:26:42.000-0700");
  protected String tableName = "REFERL_T";
  protected String tableDescription = "Referral";

  public LegacyDescriptor build() {
    return new LegacyDescriptor(id, uiId, dateTime, tableName, tableDescription);
  }

  public LegacyDescriptorEntityBuilder setId(String id) {
    this.id = id;
    return this;
  }

  public LegacyDescriptorEntityBuilder setUiId(String uiId) {
    this.uiId = uiId;
    return this;
  }

  public LegacyDescriptorEntityBuilder setDateTime(DateTime dateTime) {
    this.dateTime = dateTime;
    return this;
  }

  public LegacyDescriptorEntityBuilder setTableName(String tableName) {
    this.tableName = tableName;
    return this;
  }

  public LegacyDescriptorEntityBuilder setTableDescription(String tableDescription) {
    this.tableDescription = tableDescription;
    return this;
  }

}
