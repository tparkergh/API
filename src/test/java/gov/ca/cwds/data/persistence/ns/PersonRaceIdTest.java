package gov.ca.cwds.data.persistence.ns;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class PersonRaceIdTest {
  PersonRaceId personRaceId;
  PersonRaceId otherPersonRaceId;

  @Before
  public void setup() {
    personRaceId = new PersonRaceId();
    otherPersonRaceId = new PersonRaceId();

  }

  @Test
  public void equalsShouldBeTrueWhenSameObject() throws Exception {
    assertTrue(personRaceId.equals(personRaceId));
  }

  @Test
  public void equalsShouldBeFalseWhenOtherObjectIsNull() throws Exception {
    assertFalse(personRaceId.equals(null));
  }

  @Test
  public void equalsShouldBeFalseWhenOtherObjectIsADifferentClass() throws Exception {
    assertFalse(personRaceId.equals("A Different Class"));
  }

  @Test
  public void equalsShouldBeFalseWhenThisObjectPersonIsNullAndOtherObjectPersonIsNotNull() {
    personRaceId.setPerson(null);
    otherPersonRaceId.setPerson(new Person());
    assertFalse(personRaceId.equals(otherPersonRaceId));
  }

  @Test
  public void equalsShouldBeFalseWhenThisObjectIsNotEqualsOtherObject() {
    otherPersonRaceId.setPerson(new Person());
    personRaceId.setPerson(new Person());
    assertFalse(personRaceId.equals(otherPersonRaceId));
  }

  @Test
  public void equalsShouldBeTrueWhenThisObjectEqualsOtherObject() {
    Person person = new Person();
    otherPersonRaceId.setPerson(person);
    personRaceId.setPerson(person);
    assertTrue(personRaceId.equals(otherPersonRaceId));
  }

  @Test
  public void equalsShouldBeFalseWhenThisObjectPhoneNumberIsNullAndOtherObjectPhoneNumberIsNotNull() {
    personRaceId.setRace(null);
    otherPersonRaceId.setRace(new Race());
    assertFalse(personRaceId.equals(otherPersonRaceId));
  }

  @Test
  public void equalsShouldBeFalseWhenPhoneNumberIsNotEqualsOtherPhoneNumber() {
    otherPersonRaceId.setRace(new Race());
    personRaceId.setRace(new Race());
    assertFalse(personRaceId.equals(otherPersonRaceId));
  }

  @Test
  public void equalsShouldBeTrueWhenPhoneNumberEqualsOtherPhoneNumber() {
    Race race = new Race(1L,"race","subrace");
    otherPersonRaceId.setRace(race);
    personRaceId.setRace(race);
    assertTrue(personRaceId.equals(otherPersonRaceId));
  }

}