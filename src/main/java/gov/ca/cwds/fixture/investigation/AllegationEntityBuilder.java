package gov.ca.cwds.fixture.investigation;

import java.util.HashSet;
import java.util.Set;

import gov.ca.cwds.rest.api.domain.investigation.Allegation;
import gov.ca.cwds.rest.api.domain.investigation.AllegationPerson;
import gov.ca.cwds.rest.api.domain.investigation.AllegationSubType;
import gov.ca.cwds.rest.api.domain.investigation.CmsRecordDescriptor;

@SuppressWarnings("javadoc")
public class AllegationEntityBuilder {

  private Short injuryHarmType1 = 1372;
  private Short injuryHarmType2 = 1372;
  private Short injuryHarmSubType1 = 6;
  private Short injuryHarmSubType2 = 7;

  private AllegationSubType allegationSubType1 =
      new AllegationSubType(injuryHarmType1, injuryHarmSubType1);
  private AllegationSubType allegationSubType2 =
      new AllegationSubType(injuryHarmType2, injuryHarmSubType2);
  private Set<AllegationSubType> allegationSubTypes = new HashSet<>();

  protected Short allegationType = 2179;
  protected Boolean createdByScreener = false;
  protected AllegationSubType allegationSubType;
  protected Short dispositionType = 46;
  protected String rational = "disposistion reason explained";
  private CmsRecordDescriptor legacyDescriptor = new CmsRecordDescriptorEntityBuilder().build();
  private AllegationPerson victim = new AllegationPersonEntityBuilder().build();
  private AllegationPerson perpetrator = new AllegationPersonEntityBuilder().setFirstName("Jack")
      .setLastName("Jones").setPrefixTitle("Mr").build();

  public Allegation build() {

    allegationSubTypes.add(allegationSubType1);
    allegationSubTypes.add(allegationSubType2);

    return new Allegation(allegationType, createdByScreener, allegationSubTypes, dispositionType,
        rational, legacyDescriptor, victim, perpetrator);
  }

  public AllegationEntityBuilder setAllegationType(Short allegationType) {
    this.allegationType = allegationType;
    return this;
  }

  public AllegationEntityBuilder setCreatedByScreener(Boolean createdByScreener) {
    this.createdByScreener = createdByScreener;
    return this;
  }

  public AllegationEntityBuilder setAllegationSubTypes(Set<AllegationSubType> allegationSubTypes) {
    this.allegationSubTypes = allegationSubTypes;
    return this;
  }

  public AllegationEntityBuilder setDispositionType(Short dispositionType) {
    this.dispositionType = dispositionType;
    return this;
  }

  public AllegationEntityBuilder setLegacyDescriptor(CmsRecordDescriptor legacyDescriptor) {
    this.legacyDescriptor = legacyDescriptor;
    return this;
  }

  public AllegationEntityBuilder setVictim(AllegationPerson victim) {
    this.victim = victim;
    return this;
  }

  public AllegationEntityBuilder setPerpetrator(AllegationPerson perpetrator) {
    this.perpetrator = perpetrator;
    return this;
  }


}
