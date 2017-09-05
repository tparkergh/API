package gov.ca.cwds.fixture;

import gov.ca.cwds.data.persistence.cms.LongText;

public class LongTextEntityBuilder {
  private String id;
  private String countySpecificCode;
  private String textDescription;

  /**
   *
   * @param id key
   * @return the builder
   */
  public LongTextEntityBuilder setId(String id) {
    this.id = id;
    return this;
  }

  /**
   *
   * @param countySpecificCode county code
   * @return the builder
   */
  public LongTextEntityBuilder setCountySpecificCode(String countySpecificCode) {
    this.countySpecificCode = countySpecificCode;
    return this;
  }

  /**
   *
   * @param textDescription description
   * @return the builder
   */
  public LongTextEntityBuilder setTextDescription(String textDescription) {
    this.textDescription = textDescription;
    return this;
  }

  /**
   *
   * @return built LongText object
   */
  public LongText build() {
    return new LongText(id, countySpecificCode, textDescription);
  }
}
