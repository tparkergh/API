package gov.ca.cwds.data.persistence.ns;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class PersonRaceTest {
  PersonRace personRace;
  PersonRace otherPersonRace;

  @Before
  public void setup() {
    personRace = new PersonRace();
    otherPersonRace = new PersonRace();

  }

  @Test
  public void equalsShouldBeTrueWhenSameObject() throws Exception {
    assertTrue(personRace.equals(personRace));
  }

  @Test
  public void equalsShouldBeFalseWhenOtherObjectIsNull() throws Exception {
    assertFalse(personRace.equals(null));
  }

  @Test
  public void equalsShouldBeFalseWhenOtherObjectIsADifferentClass() throws Exception {
    assertFalse(personRace.equals("A Different Class"));
  }

  @Test
  public void equalsShouldBeFalseWhenThisObjectIsNotEqualsOtherObject() {
    otherPersonRace.setPerson(new Person());
    personRace.setPerson(new Person());
    assertFalse(personRace.equals(otherPersonRace));
  }

  @Test
  public void equalsShouldBeTrueWhenThisObjectEqualsOtherObject() {
    Person person = new Person();
    otherPersonRace.setPerson(person);
    personRace.setPerson(person);
    assertTrue(personRace.equals(otherPersonRace));
  }
}