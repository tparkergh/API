package gov.ca.cwds.fixture;

import java.util.Date;

import gov.ca.cwds.rest.api.domain.cms.DrmsDocument;

/**
 * @author CWDS API Team
 *
 */
@SuppressWarnings("javadoc")
public class DrmsDocumentResourceBuilder {

  Date creationTimeStamp = new Date();
  String drmsDocumentTemplateId = "DUMMY";
  String fingerprintStaffPerson = "q1p";
  String staffPersonId = "q1p";
  String handleName = "DUMMY";

  public DrmsDocument build() {
    return new DrmsDocument(creationTimeStamp, drmsDocumentTemplateId, fingerprintStaffPerson,
        staffPersonId, handleName);
  }

  public Date getCreationTimeStamp() {
    return creationTimeStamp;
  }

  public DrmsDocumentResourceBuilder setCreationTimeStamp(Date creationTimeStamp) {
    this.creationTimeStamp = creationTimeStamp;
    return this;
  }

  public String getDrmsDocumentTemplateId() {
    return drmsDocumentTemplateId;
  }

  public DrmsDocumentResourceBuilder setDrmsDocumentTemplateId(String drmsDocumentTemplateId) {
    this.drmsDocumentTemplateId = drmsDocumentTemplateId;
    return this;
  }

  public String getFingerprintStaffPerson() {
    return fingerprintStaffPerson;
  }

  public DrmsDocumentResourceBuilder setFingerprintStaffPerson(String fingerprintStaffPerson) {
    this.fingerprintStaffPerson = fingerprintStaffPerson;
    return this;
  }

  public String getStaffPersonId() {
    return staffPersonId;
  }

  public DrmsDocumentResourceBuilder setStaffPersonId(String staffPersonId) {
    this.staffPersonId = staffPersonId;
    return this;
  }

  public String getHandleName() {
    return handleName;
  }

  public DrmsDocumentResourceBuilder setHandleName(String handleName) {
    this.handleName = handleName;
    return this;
  }

}
