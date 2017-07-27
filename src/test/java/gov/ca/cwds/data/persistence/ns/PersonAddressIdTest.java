package gov.ca.cwds.data.persistence.ns;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class PersonAddressIdTest {

  PersonAddressId personAddressId;

  @Before
  public void setup(){
    personAddressId = new PersonAddressId();

  }

  @Test
  public void equalsShouldBeTrueWhenSameObject() throws Exception {
    assertTrue(personAddressId.equals(personAddressId));
  }

  @Test
  public void equalsShouldBeFalseWhenOtherObjectIsNull() throws Exception {
    assertFalse(personAddressId.equals(null));
  }

  @Test
  public void equalsShouldBeFalseWhenOtherObjectIsADifferentClass() throws Exception {
    assertFalse(personAddressId.equals("A Different Class"));
  }
  @Test
  public void equalsShouldBeFalseWhenPersonIsNullInThisClassAndNotNullInOtherClass() throws Exception {
    PersonAddressId otherPersonAddressId = new PersonAddressId();
    otherPersonAddressId.setPerson(new Person());
    personAddressId.setPerson(null);
    assertFalse(personAddressId.equals(otherPersonAddressId));
  }

  @Test
  public void equalsShouldBeFalseWhenAddressIsNullInThisClassAndNotNullInOtherClass() throws Exception {
    PersonAddressId otherPersonAddressId = new PersonAddressId();
    otherPersonAddressId.setAddress(new Address());
    personAddressId.setPerson(null);
    assertFalse(personAddressId.equals(otherPersonAddressId));
  }

  @Test
  public void equalsShouldBeTrueWhenPersonAddressIdEqualsOtherPersonAddressId() throws Exception {
    Person person = new Person();
    PersonAddressId otherPersonAddressId = new PersonAddressId();
    otherPersonAddressId.setPerson(person);
    personAddressId.setPerson(person);
    assertTrue(personAddressId.equals(otherPersonAddressId));
  }
}