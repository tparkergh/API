package gov.ca.cwds.data.persistence.ns;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class PersonLanguageTest {

  PersonLanguage personLanguage;
  PersonLanguage otherPersonLanguage;

    @Before
    public void setup(){
      personLanguage = new PersonLanguage();
      otherPersonLanguage = new PersonLanguage();

    }

    @Test
    public void equalsShouldBeTrueWhenSameObject() throws Exception {
      assertTrue(personLanguage.equals(personLanguage));
    }

    @Test
    public void equalsShouldBeFalseWhenOtherObjectIsNull() throws Exception {
      assertFalse(personLanguage.equals(null));
    }

    @Test
    public void equalsShouldBeFalseWhenOtherObjectIsADifferentClass() throws Exception {
      assertFalse(personLanguage.equals("A Different Class"));
    }

  @Test
  public void equalsShouldBeFalseWhenThisObjectIsNotEqualsOtherObject(){
    otherPersonLanguage.setPerson(new Person());
    personLanguage.setPerson(new Person());
    assertFalse(personLanguage.equals(otherPersonLanguage));
  }

    @Test
    public void equalsShouldBeTrueWhenThisObjectEqualsOtherObject(){
      Person person = new Person();
      otherPersonLanguage.setPerson(person);
      personLanguage.setPerson(person);
      assertTrue(personLanguage.equals(otherPersonLanguage));
    }
}