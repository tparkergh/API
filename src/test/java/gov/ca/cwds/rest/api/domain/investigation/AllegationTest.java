package gov.ca.cwds.rest.api.domain.investigation;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import gov.ca.cwds.fixture.investigation.AllegationEntityBuilder;
import gov.ca.cwds.fixture.investigation.AllegationPersonEntityBuilder;
import gov.ca.cwds.fixture.investigation.CmsRecordDescriptorEntityBuilder;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

@SuppressWarnings("javadoc")
public class AllegationTest {

  private Short injuryHarmType1 = 1372;
  private Short injuryHarmType2 = 1372;
  private Short injuryHarmSubType1 = 6;
  private Short injuryHarmSubType2 = 7;

  private Short otherAllegationType = 2177;

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

  @Before
  public void setup() {
    allegationSubTypes.add(allegationSubType1);
    allegationSubTypes.add(allegationSubType2);
  }

  @Test
  public void shouldCreateObjectWithDefaultConstructor() {
    Allegation allegation = new Allegation();
    assertNotNull(allegation);
  }

  @Test
  public void domainConstructorTest() {

    Allegation domain = new Allegation(allegationType, createdByScreener, allegationSubTypes,
        dispositionType, rational, legacyDescriptor, victim, perpetrator);
    assertThat(domain.getAllegationType(), is(equalTo(allegationType)));
    assertThat(domain.getCreatedByScreener(), is(equalTo(createdByScreener)));
    assertThat(domain.getAllegationSubType(), is(equalTo(allegationSubTypes)));
    assertThat(domain.getDispositionType(), is(equalTo(dispositionType)));
    assertThat(domain.getRationale(), is(equalTo(rational)));
    assertThat(domain.getLegacyDescriptor(), is(equalTo(legacyDescriptor)));
    assertThat(domain.getVictim(), is(equalTo(victim)));
    assertThat(domain.getPerpetrator(), is(equalTo(perpetrator)));

  }

  @Test
  public void shouldCompareEqualsToObjectWithSameValues() {
    Allegation allegation = new Allegation(allegationType, createdByScreener, allegationSubTypes,
        dispositionType, rational, legacyDescriptor, victim, perpetrator);
    Allegation otherAllegation = new Allegation(allegationType, createdByScreener,
        allegationSubTypes, dispositionType, rational, legacyDescriptor, victim, perpetrator);
    assertEquals(allegation, otherAllegation);

  }

  @Test
  public void shouldCompareNotEqualsToObjectWithDifferentValues() {
    Allegation allegation = new AllegationEntityBuilder().build();
    Allegation otherAllegation =
        new AllegationEntityBuilder().setAllegationType(otherAllegationType).build();
    assertThat(allegation, is(not(equals(otherAllegation))));
  }

  @Test
  public void shouldFindSingleItemInHashSetWhenMultipleItemsAddedWithSameValue() {
    Allegation allegation = new AllegationEntityBuilder().build();
    Allegation otherAllegation = new AllegationEntityBuilder().build();
    Set<Allegation> items = new HashSet<>();
    items.add(allegation);
    items.add(otherAllegation);

    assertTrue(items.contains(allegation));
    assertTrue(items.contains(otherAllegation));
    assertEquals(1, items.size());

  }

  @Test
  public void shouldFindMultipleItemInHashSetWhenItemsHaveWithDifferentValue() {
    Allegation allegation = new AllegationEntityBuilder().build();
    Allegation otherAllegation =
        new AllegationEntityBuilder().setAllegationType(otherAllegationType).build();
    Set<Allegation> items = new HashSet<>();
    items.add(allegation);
    items.add(otherAllegation);

    assertTrue(items.contains(allegation));
    assertTrue(items.contains(otherAllegation));
    assertEquals(2, items.size());
  }

  @Test
  public void equalsHashCodeWork() {
    EqualsVerifier.forClass(Allegation.class).suppress(Warning.NONFINAL_FIELDS).verify();
  }

}
