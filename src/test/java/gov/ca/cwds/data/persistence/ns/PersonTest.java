package gov.ca.cwds.data.persistence.ns;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

import gov.ca.cwds.fixture.AddressResourceBuilder;
import gov.ca.cwds.fixture.PersonResourceBuilder;
import java.util.HashSet;
import java.util.Set;
import org.junit.Before;
import org.junit.Test;

public class PersonTest {
  gov.ca.cwds.rest.api.domain.Person domainPerson;
  String lastUpdatedId;
  String userId;
  gov.ca.cwds.rest.api.domain.Address address;

  @Before
  public void setup(){
    address = new AddressResourceBuilder().createAddress();
    Set addresses = new HashSet<>();
    addresses.add(address);
    domainPerson = new PersonResourceBuilder().setAddress(addresses).build();
    lastUpdatedId = "2010-12-02";
    userId = "AASSXX";
  }

  @Test
  public void shouldCreatePersonFromDomainPerson(){
    Person person = new Person(domainPerson, lastUpdatedId, userId);
    assertEquals(1, person.getPersonAddress().size());
    for(PersonAddress personAddress : person.getPersonAddress()){
      assertEquals(personAddress.getAddress().getCity(), address.getCity());
      assertEquals(personAddress.getAddress().getState(), address.getState().toString());
      assertEquals(personAddress.getAddress().getStreetAddress(), address.getStreetAddress());
      assertEquals(personAddress.getAddress().getZip(), address.getZip());
    }
  }
}