package gov.ca.cwds.data.persistence.ns;

import static org.junit.Assert.*;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.Before;
import org.junit.Test;

public class PersonEthnicityTest {

  PersonEthnicity personEthnicity;

  @Before
  public void setup(){
    personEthnicity = new PersonEthnicity();

  }

  @Test
  public void equalsShouldBeTrueWhenSameObject() throws Exception {
    assertTrue(personEthnicity.equals(personEthnicity));
  }

  @Test
  public void equalsShouldBeFalseWhenOtherObjectIsNull() throws Exception {
    assertFalse(personEthnicity.equals(null));
  }

  @Test
  public void equalsShouldBeFalseWhenOtherObjectIsADifferentClass() throws Exception {
    assertFalse(personEthnicity.equals("A Different Class"));
  }
  @Test
  public void equalsShouldBeFalseWhenPersonEthnicityDoesNotEqualOtherPersonEthnicity() throws Exception {
    PersonEthnicity otherPersonEthnicity = new PersonEthnicity(new Person(), new Ethnicity());
    assertFalse(personEthnicity.equals(otherPersonEthnicity));
  }

  @Test
  public void equalsShouldBeTrueWhenPersonEthnicityEqualsOtherPersonEthnicity() throws Exception {
    Person person = new Person();
    Ethnicity ethnicity = new Ethnicity();
    PersonEthnicity otherPersonEthnicity = new PersonEthnicity(person, ethnicity);
    personEthnicity = new PersonEthnicity(person, ethnicity);
    assertTrue(personEthnicity.equals(otherPersonEthnicity));
  }
}