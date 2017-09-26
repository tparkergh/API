package gov.ca.cwds.fixture.investigation;

import java.util.HashSet;
import java.util.Set;

import gov.ca.cwds.rest.api.domain.investigation.People;
import gov.ca.cwds.rest.api.domain.investigation.Person;

@SuppressWarnings("javadoc")
public class PeopleEntityBuilder {

  private Person person1 = new PersonEntityBuilder().build();
  private Person person2 =
      new PersonEntityBuilder().setFirstName("johnny").setLastName("pedd").build();
  private Set<Person> people = new HashSet<>();

  public People build() {
    people.add(person1);
    people.add(person2);

    return new People(people);
  }

  public PeopleEntityBuilder setPeople(Set<Person> people) {
    this.people = people;
    return this;
  }

  public Set<Person> getPeople() {
    return people;
  }
}
