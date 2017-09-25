package gov.ca.cwds.fixture.contacts;

import java.util.HashSet;
import java.util.Set;

import gov.ca.cwds.rest.api.domain.investigation.contact.Contact;
import gov.ca.cwds.rest.api.domain.investigation.contact.ContactList;

public class ContactListEntityBuilder {

  private Contact contact1 = new ContactEntityBuilder().build();
  private Contact contact2 = new ContactEntityBuilder().setLocation(423).build();

  private Set<Contact> contact = new HashSet<Contact>();

  public ContactList build() {
    contact.add(contact1);
    contact.add(contact2);

    return new ContactList(contact);

  }
}
