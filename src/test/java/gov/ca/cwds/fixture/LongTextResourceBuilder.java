package gov.ca.cwds.fixture;

import gov.ca.cwds.rest.api.domain.cms.LongText;

public class LongTextResourceBuilder {

  private String countySpecificCode = "57";
  private String textDescription = "Arrange for parents to have demonstrating home maker come to the home twice a week";


  public LongTextResourceBuilder setCountySpecificCode(String countySpecificCode) {
    this.countySpecificCode = countySpecificCode;
    return this;
  }

  public LongTextResourceBuilder setTextDescription(String textDescription) {
    this.textDescription = textDescription;
    return this;
  }

  public LongText build(){
    return new LongText(countySpecificCode, textDescription);
  }
}
