package gov.ca.cwds.data.persistence.ns;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class PersonEthnicityIdTest {

  PersonEthnicityId personEthnicityId;

  @Before
  public void setup() {
    personEthnicityId = new PersonEthnicityId();

  }

  @Test
  public void equalsShouldBeTrueWhenSameObject() throws Exception {
    assertTrue(personEthnicityId.equals(personEthnicityId));
  }

  @Test
  public void equalsShouldBeFalseWhenOtherObjectIsNull() throws Exception {
    assertFalse(personEthnicityId.equals(null));
  }

  @Test
  public void equalsShouldBeFalseWhenOtherObjectIsADifferentClass() throws Exception {
    assertFalse(personEthnicityId.equals("A Different Class"));
  }

  @Test
  public void equalsShouldBeFalseWhenPersonIsNullInThisClassAndNotNullInOtherClass()
      throws Exception {
    PersonEthnicityId otherPersonEthnicityId = new PersonEthnicityId();
    otherPersonEthnicityId.setPerson(new Person());
    personEthnicityId.setPerson(null);
    assertFalse(personEthnicityId.equals(otherPersonEthnicityId));
  }

  @Test
  public void equalsShouldBeFalseWhenThisPersonIsNullAndOtherPersonIsNot() throws Exception {
    PersonEthnicityId otherPersonEthnicityId = new PersonEthnicityId();
    otherPersonEthnicityId.setPerson(new Person());
    personEthnicityId.setPerson(null);
    assertFalse(personEthnicityId.equals(otherPersonEthnicityId));
  }

  @Test
  public void equalsShouldBeFalseWhenThisPersonDoesntEqualOtherPerson() throws Exception {
    PersonEthnicityId otherPersonEthnicityId = new PersonEthnicityId();
    otherPersonEthnicityId.setPerson(new Person());
    assertFalse(personEthnicityId.equals(otherPersonEthnicityId));
  }

  @Test
  public void equalsShouldBeFalseWhenThisEthnicityDoesntEqualOtherEthnicity() throws Exception {
    PersonEthnicityId otherPersonEthnicityId = new PersonEthnicityId();
    otherPersonEthnicityId.setEthnicity(new Ethnicity());
    personEthnicityId.setEthnicity(null);
    assertFalse(personEthnicityId.equals(otherPersonEthnicityId));
  }

  @Test
  public void equalsShouldBeTrueWhenPersonAddressIdEqualsOtherPersonAddressId() throws Exception {
    Person person = new Person();
    PersonEthnicityId otherPersonEthnicityId = new PersonEthnicityId();
    otherPersonEthnicityId.setPerson(person);
    personEthnicityId.setPerson(person);
    assertTrue(personEthnicityId.equals(otherPersonEthnicityId));
  }

}