package gov.ca.cwds.fixture.investigation;

import java.util.LinkedHashSet;

import gov.ca.cwds.rest.api.domain.RaceAndEthnicity;

@SuppressWarnings("javadoc")
public class RaceAndEthnicityEntityBuilder {

  private LinkedHashSet<Short> raceCode = new LinkedHashSet<>(820, 822);
  private String unableToDetermineCode = "N";
  private LinkedHashSet<Short> hispanicCode = new LinkedHashSet<>();
  private String hispanicOriginCode = "N";
  private String hispanicUnableToDetermineCode = "";

  public RaceAndEthnicity build() {
    return new RaceAndEthnicity(raceCode, unableToDetermineCode, hispanicCode, hispanicOriginCode,
        hispanicUnableToDetermineCode);
  }

  public LinkedHashSet<Short> getRaceCode() {
    return raceCode;
  }

  public RaceAndEthnicityEntityBuilder setRaceCode(LinkedHashSet<Short> raceCode) {
    this.raceCode = raceCode;
    return this;
  }

  public String getUnableToDetermineCode() {
    return unableToDetermineCode;
  }

  public RaceAndEthnicityEntityBuilder setUnableToDetermineCode(String unableToDetermineCode) {
    this.unableToDetermineCode = unableToDetermineCode;
    return this;
  }

  public LinkedHashSet<Short> getHispanicCode() {
    return hispanicCode;
  }

  public RaceAndEthnicityEntityBuilder setHispanicCode(LinkedHashSet<Short> hispanicCode) {
    this.hispanicCode = hispanicCode;
    return this;
  }

  public String getHispanicOriginCode() {
    return hispanicOriginCode;
  }

  public RaceAndEthnicityEntityBuilder setHispanicOriginCode(String hispanicOriginCode) {
    this.hispanicOriginCode = hispanicOriginCode;
    return this;
  }

  public String getHispanicUnableToDetermineCode() {
    return hispanicUnableToDetermineCode;
  }

  public RaceAndEthnicityEntityBuilder setHispanicUnableToDetermineCode(
      String hispanicUnableToDetermineCode) {
    this.hispanicUnableToDetermineCode = hispanicUnableToDetermineCode;
    return this;
  }

}
