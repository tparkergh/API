package gov.ca.cwds.fixture.investigation;

import gov.ca.cwds.rest.api.domain.investigation.HistoryOfInvolvementAllegation;

@SuppressWarnings("javadoc")
public class HistoryOfInvolvementAllegationEntityBuilder {

  private String victimLastName = "Smith";
  private String victimFirstName = "Cody";
  private String perpetratorLastName = "Berk";
  private String perpetratorFirstName = "Jeremy";
  private String dispositionDescription = "Substantiated";
  private String allegationDescription = "Physical abuse";

  public HistoryOfInvolvementAllegation build() {
    return new HistoryOfInvolvementAllegation(victimLastName, victimFirstName, perpetratorLastName,
        perpetratorFirstName, dispositionDescription, allegationDescription);

  }

  public HistoryOfInvolvementAllegationEntityBuilder setVictimLastName(String victimLastName) {
    this.victimLastName = victimLastName;
    return this;
  }

  public HistoryOfInvolvementAllegationEntityBuilder setVictimFirstName(String victimFirstName) {
    this.victimFirstName = victimFirstName;
    return this;
  }

  public HistoryOfInvolvementAllegationEntityBuilder setPerpetratorLastName(String perpetratorLastName) {
    this.perpetratorLastName = perpetratorLastName;
    return this;
  }

  public HistoryOfInvolvementAllegationEntityBuilder setPerpetratorFirstName(String perpetratorFirstName) {
    this.perpetratorFirstName = perpetratorFirstName;
    return this;
  }

  public HistoryOfInvolvementAllegationEntityBuilder setDispositionDescription(
      String dispositionDescription) {
    this.dispositionDescription = dispositionDescription;
    return this;
  }

  public HistoryOfInvolvementAllegationEntityBuilder setAllegationDescription(
      String allegationDescription) {
    this.allegationDescription = allegationDescription;
    return this;
  }

}
