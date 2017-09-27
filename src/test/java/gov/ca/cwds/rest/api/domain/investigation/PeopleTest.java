package gov.ca.cwds.rest.api.domain.investigation;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import gov.ca.cwds.fixture.investigation.PeopleEntityBuilder;
import gov.ca.cwds.fixture.investigation.PersonEntityBuilder;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

@SuppressWarnings("javadoc")
public class PeopleTest {

  private Person person1 = new PersonEntityBuilder().build();
  private Person person2 =
      new PersonEntityBuilder().setFirstName("johnny").setLastName("pedd").build();
  private Set<Person> people = new HashSet<Person>();

  @Before
  public void setup() {
    people.add(person1);
    people.add(person2);
  }

  @Test
  public void testEmptyConstructorSuccess() {
    People persons = new People();
    assertNotNull(persons);
  }

  @Test
  public void testDomainConstructorSuccess() {

    People persons = new People(people);
    assertThat(people, is(equalTo(persons.getPersons())));
  }

  @Test
  public void shouldCompareEqualsToObjectWithSameValues() {
    People persons1 = new PeopleEntityBuilder().build();
    People persons2 = new PeopleEntityBuilder().build();
    assertEquals(persons1, persons2);

  }

  @Test
  public void shouldCompareNotEqualsToObjectWithDifferentValues() {
    People persons1 = new PeopleEntityBuilder().build();
    People persons2 = new PeopleEntityBuilder().setPeople(people).build();

    assertThat(persons1, is(not(equals(persons2))));
  }

  @Test
  public void equalsHashCodeWork() {
    EqualsVerifier.forClass(People.class).suppress(Warning.NONFINAL_FIELDS).verify();
  }

}
