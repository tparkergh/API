package gov.ca.cwds.fixture.investigation;

import gov.ca.cwds.rest.api.domain.investigation.AllegationPerson;
import gov.ca.cwds.rest.api.domain.investigation.CmsRecordDescriptor;

@SuppressWarnings("javadoc")
public class AllegationPersonEntityBuilder {

  protected String firstName = "Joanna";
  protected String lastName = "Kenneson";
  protected String middleName = "";
  protected String suffixTitle = "phd";
  protected String prefixTitle = "Ms";
  private CmsRecordDescriptor legacyDescriptor = new CmsRecordDescriptorEntityBuilder().build();

  public AllegationPerson build() {
    return new AllegationPerson(firstName, lastName, middleName, suffixTitle, prefixTitle,
        legacyDescriptor);
  }

  public AllegationPersonEntityBuilder setFirstName(String firstName) {
    this.firstName = firstName;
    return this;
  }

  public AllegationPersonEntityBuilder setLastName(String lastName) {
    this.lastName = lastName;
    return this;
  }

  public AllegationPersonEntityBuilder setMiddleName(String middleName) {
    this.middleName = middleName;
    return this;
  }

  public AllegationPersonEntityBuilder setSuffixTitle(String suffixTitle) {
    this.suffixTitle = suffixTitle;
    return this;
  }

  public AllegationPersonEntityBuilder setPrefixTitle(String prefixTitle) {
    this.prefixTitle = prefixTitle;
    return this;
  }

  public AllegationPersonEntityBuilder setLegacyDescriptor(CmsRecordDescriptor legacyDescriptor) {
    this.legacyDescriptor = legacyDescriptor;
    return this;
  }

}
