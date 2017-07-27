package gov.ca.cwds.data.persistence.ns;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class PersonLanguageIdTest {

  PersonLanguageId personLanguageId;
  PersonLanguageId otherPersonLanguageId;

  @Before
  public void setup(){
    personLanguageId = new PersonLanguageId();
    otherPersonLanguageId = new PersonLanguageId();

  }

  @Test
  public void equalsShouldBeTrueWhenSameObject() throws Exception {
    assertTrue(personLanguageId.equals(personLanguageId));
  }

  @Test
  public void equalsShouldBeFalseWhenOtherObjectIsNull() throws Exception {
    assertFalse(personLanguageId.equals(null));
  }

  @Test
  public void equalsShouldBeFalseWhenOtherObjectIsADifferentClass() throws Exception {
    assertFalse(personLanguageId.equals("A Different Class"));
  }

  @Test
  public void equalsShouldBeFalseWhenThisObjectIsNotEqualsOtherObject(){
    otherPersonLanguageId.setPerson(new Person());
    personLanguageId.setPerson(new Person());
    assertFalse(personLanguageId.equals(otherPersonLanguageId));
  }

  @Test
  public void equalsShouldBeTrueWhenThisObjectEqualsOtherObject(){
    Person person = new Person();
    otherPersonLanguageId.setPerson(person);
    personLanguageId.setPerson(person);
    assertTrue(personLanguageId.equals(otherPersonLanguageId));
  }
}