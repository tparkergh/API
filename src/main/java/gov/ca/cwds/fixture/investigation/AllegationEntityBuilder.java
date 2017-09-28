package gov.ca.cwds.fixture.investigation;

import gov.ca.cwds.rest.api.domain.investigation.Allegation;

@SuppressWarnings("javadoc")
public class AllegationEntityBuilder {

  protected String victimLastName = "Tatum";
  protected String victimFirstName = "Timithoy";
  protected String perpetratorLastName = "Micheals";
  protected String perpetratorFirstName = "Mitchell";
  protected String dispositionDescription = "Substantiated";
  protected String allegationDescription = "physical abuse";

  public Allegation build() {
    return new Allegation(victimFirstName, victimLastName, perpetratorLastName,
        perpetratorFirstName, dispositionDescription, allegationDescription);
  }

  public AllegationEntityBuilder setVictimLastName(String victimLastName) {
    this.victimLastName = victimLastName;
    return this;
  }

  public AllegationEntityBuilder setVictimFirstName(String victimFirstName) {
    this.victimFirstName = victimFirstName;
    return this;
  }

  public AllegationEntityBuilder setPerpetratorLastName(String perpetratorLastName) {
    this.perpetratorLastName = perpetratorLastName;
    return this;
  }

  public AllegationEntityBuilder setPerpetratorFirstName(String perpetratorFirstName) {
    this.perpetratorFirstName = perpetratorFirstName;
    return this;
  }

  public AllegationEntityBuilder setDispositionDescription(String dispositionDescription) {
    this.dispositionDescription = dispositionDescription;
    return this;
  }

  public AllegationEntityBuilder setAllegationDescription(String allegationDescription) {
    this.allegationDescription = allegationDescription;
    return this;
  }

  public String getVictimLastName() {
    return victimLastName;
  }

  public String getVictimFirstName() {
    return victimFirstName;
  }

  public String getPerpetratorLastName() {
    return perpetratorLastName;
  }

  public String getPerpetratorFirstName() {
    return perpetratorFirstName;
  }

  public String getDispositionDescription() {
    return dispositionDescription;
  }

  public String getAllegationDescription() {
    return allegationDescription;
  }


}
