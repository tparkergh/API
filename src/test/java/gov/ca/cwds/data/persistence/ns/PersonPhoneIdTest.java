package gov.ca.cwds.data.persistence.ns;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class PersonPhoneIdTest {

  PersonPhoneId personPhoneId;
  PersonPhoneId otherPersonPhoneId;

  @Before
  public void setup() {
    personPhoneId = new PersonPhoneId();
    otherPersonPhoneId = new PersonPhoneId();

  }

  @Test
  public void equalsShouldBeTrueWhenSameObject() throws Exception {
    assertTrue(personPhoneId.equals(personPhoneId));
  }

  @Test
  public void equalsShouldBeFalseWhenOtherObjectIsNull() throws Exception {
    assertFalse(personPhoneId.equals(null));
  }

  @Test
  public void equalsShouldBeFalseWhenOtherObjectIsADifferentClass() throws Exception {
    assertFalse(personPhoneId.equals("A Different Class"));
  }

  @Test
  public void equalsShouldBeFalseWhenThisObjectPersonIsNullAndOtherObjectPersonIsNotNull() {
    personPhoneId.setPerson(null);
    otherPersonPhoneId.setPerson(new Person());
    assertFalse(personPhoneId.equals(otherPersonPhoneId));
  }

  @Test
  public void equalsShouldBeFalseWhenThisObjectIsNotEqualsOtherObject() {
    otherPersonPhoneId.setPerson(new Person());
    personPhoneId.setPerson(new Person());
    assertFalse(personPhoneId.equals(otherPersonPhoneId));
  }

  @Test
  public void equalsShouldBeTrueWhenThisObjectEqualsOtherObject() {
    Person person = new Person();
    otherPersonPhoneId.setPerson(person);
    personPhoneId.setPerson(person);
    assertTrue(personPhoneId.equals(otherPersonPhoneId));
  }

  @Test
  public void equalsShouldBeFalseWhenThisObjectPhoneNumberIsNullAndOtherObjectPhoneNumberIsNotNull() {
    personPhoneId.setPhoneNumber(null);
    otherPersonPhoneId.setPhoneNumber(new PhoneNumber());
    assertFalse(personPhoneId.equals(otherPersonPhoneId));
  }

  @Test
  public void equalsShouldBeFalseWhenPhoneNumberIsNotEqualsOtherPhoneNumber() {
    otherPersonPhoneId.setPhoneNumber(new PhoneNumber());
    personPhoneId.setPhoneNumber(new PhoneNumber());
    assertFalse(personPhoneId.equals(otherPersonPhoneId));
  }

  @Test
  public void equalsShouldBeTrueWhenPhoneNumberEqualsOtherPhoneNumber() {
    PhoneNumber phoneNumber = new PhoneNumber(1L, "1234445555", "Home");
    otherPersonPhoneId.setPhoneNumber(phoneNumber);
    personPhoneId.setPhoneNumber(phoneNumber);
    assertTrue(personPhoneId.equals(otherPersonPhoneId));
  }
}