package gov.ca.cwds.fixture.investigation;

import java.util.LinkedHashSet;

import gov.ca.cwds.rest.api.domain.RaceAndEthnicity;

@SuppressWarnings("javadoc")
public class RaceAndEthnicityEntityBuilder {

  private LinkedHashSet<Short> raceCode = new LinkedHashSet<Short>(820, 822);
  private String unableToDetermineCode = "N";
  private LinkedHashSet<Short> hispanicCode = new LinkedHashSet<Short>();
  private String hispanicOriginCode = "N";
  private String hispanicUnableToDetermineCode = "";

  public RaceAndEthnicity build() {
    return new RaceAndEthnicity(raceCode, unableToDetermineCode, hispanicCode, hispanicOriginCode,
        hispanicUnableToDetermineCode);
  }

  public RaceAndEthnicityEntityBuilder setRaceCode(LinkedHashSet<Short> raceCode) {
    this.raceCode = raceCode;
    return this;
  }

  public RaceAndEthnicityEntityBuilder setUnableToDetermineCode(String unableToDetermineCode) {
    this.unableToDetermineCode = unableToDetermineCode;
    return this;
  }

  public RaceAndEthnicityEntityBuilder setHispanicCode(LinkedHashSet<Short> hispanciCode) {
    this.hispanicCode = hispanicCode;
    return this;
  }

  public RaceAndEthnicityEntityBuilder setHispanicOriginCode(String hispanicOriginCode) {
    this.hispanicOriginCode = hispanicOriginCode;
    return this;
  }

  public RaceAndEthnicityEntityBuilder setHispanicUnableToDetermineCode(
      String hispanicUnableToDetermineCode) {
    this.hispanicUnableToDetermineCode = hispanicUnableToDetermineCode;
    return this;
  }

  public LinkedHashSet<Short> getRaceCode() {
    return raceCode;
  }

  public String getUnableToDetermineCode() {
    return unableToDetermineCode;
  }

  public LinkedHashSet<Short> getHispanicCode() {
    return hispanicCode;
  }

  public String getHispanicOriginCode() {
    return hispanicOriginCode;
  }

  public String getHispanicUnableToDetermineCode() {
    return hispanicUnableToDetermineCode;
  }


}
