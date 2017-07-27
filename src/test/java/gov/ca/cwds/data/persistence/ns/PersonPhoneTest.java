package gov.ca.cwds.data.persistence.ns;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class PersonPhoneTest {
  PersonPhone personPhone;
  PersonPhone otherPersonPhone;

  @Before
  public void setup() {
    personPhone = new PersonPhone();
    otherPersonPhone = new PersonPhone();

  }

  @Test
  public void equalsShouldBeTrueWhenSameObject() throws Exception {
    assertTrue(personPhone.equals(personPhone));
  }

  @Test
  public void equalsShouldBeFalseWhenOtherObjectIsNull() throws Exception {
    assertFalse(personPhone.equals(null));
  }

  @Test
  public void equalsShouldBeFalseWhenOtherObjectIsADifferentClass() throws Exception {
    assertFalse(personPhone.equals("A Different Class"));
  }

  @Test
  public void equalsShouldBeFalseWhenThisObjectIsNotEqualsOtherObject() {
    otherPersonPhone.setPerson(new Person());
    personPhone.setPerson(new Person());
    assertFalse(personPhone.equals(otherPersonPhone));
  }

  @Test
  public void equalsShouldBeTrueWhenThisObjectEqualsOtherObject() {
    Person person = new Person();
    otherPersonPhone.setPerson(person);
    personPhone.setPerson(person);
    assertTrue(personPhone.equals(otherPersonPhone));
  }
}