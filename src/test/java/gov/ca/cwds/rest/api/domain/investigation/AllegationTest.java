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

import org.junit.Test;

import gov.ca.cwds.fixture.investigation.AllegationEntityBuilder;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

@SuppressWarnings("javadoc")
public class AllegationTest {

  private String victimFirstName = "sharon";
  private String perpetratorLastName = "w.";
  private String perpetratorFirstName = "ricky";
  private String dispositionDescription = "substantiated";
  private String victimLastName = "w.";
  private String allegationDescription = "emotional abuse";

  @Test
  public void shouldCreateObjectWithDefaultConstructor() {
    Allegation allegation = new Allegation();
    assertNotNull(allegation);
  }

  @Test
  public void jsonCreatorConstructorTest() throws Exception {
    Allegation domain = new Allegation(victimLastName, victimFirstName, perpetratorLastName,
        perpetratorFirstName, dispositionDescription, allegationDescription);
    assertThat(domain.getVictimLastName(), is(equalTo(victimLastName)));
    assertThat(domain.getVictimFirstName(), is(equalTo(victimFirstName)));
    assertThat(domain.getPerpetratorLastName(), is(equalTo(perpetratorLastName)));
    assertThat(domain.getPerpetratorFirstName(), is(equalTo(perpetratorFirstName)));
    assertThat(domain.getDispositionDescription(), is(equalTo(dispositionDescription)));
    assertThat(domain.getAllegationDescription(), is(equalTo(allegationDescription)));

  }

  @Test
  public void shouldCompareEqualsToObjectWithSameValues() {
    Allegation allegation = new Allegation(victimLastName, victimFirstName, perpetratorLastName,
        perpetratorFirstName, dispositionDescription, allegationDescription);
    Allegation otherAllegation = new Allegation(victimLastName, victimFirstName,
        perpetratorLastName, perpetratorFirstName, dispositionDescription, allegationDescription);
    assertEquals(allegation, otherAllegation);

  }

  @Test
  public void shouldCompareNotEqualsToObjectWithDifferentValues() {
    Allegation allegation = new AllegationEntityBuilder().build();
    Allegation otherAllegation = new AllegationEntityBuilder().setVictimLastName("jones").build();
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
    Allegation otherAllegation = new AllegationEntityBuilder().setVictimFirstName("jerry").build();
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
