package gov.ca.cwds.fixture.investigation;

import org.joda.time.DateTime;

import gov.ca.cwds.rest.api.domain.investigation.CmsRecordDescriptor;

@SuppressWarnings("javadoc")
public class CmsRecordDescriptorEntityBuilder {
  protected String id = "1234567ABC";
  protected String uiId = "111122223333444455";
  private DateTime dateTime = new DateTime("2017-10-01T15:26:42.000-0700");
  protected String tableName = "REFERL_T";
  protected String tableDescription = "REFERRAL";

  public CmsRecordDescriptor build() {
    return new CmsRecordDescriptor(id, uiId, dateTime, tableName, tableDescription);
  }

  public CmsRecordDescriptorEntityBuilder setId(String id) {
    this.id = id;
    return this;
  }

  public CmsRecordDescriptorEntityBuilder setUiId(String uiId) {
    this.uiId = uiId;
    return this;
  }

  public CmsRecordDescriptorEntityBuilder setDateTime(DateTime dateTime) {
    this.dateTime = dateTime;
    return this;
  }

  public CmsRecordDescriptorEntityBuilder setTableName(String tableName) {
    this.tableName = tableName;
    return this;
  }

  public CmsRecordDescriptorEntityBuilder setTableDescription(String tableDescription) {
    this.tableDescription = tableDescription;
    return this;
  }

}
