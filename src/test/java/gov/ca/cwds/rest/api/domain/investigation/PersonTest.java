package gov.ca.cwds.rest.api.domain.investigation;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

import gov.ca.cwds.fixture.investigation.InvestigationAddressEntityBuilder;
import gov.ca.cwds.fixture.investigation.PersonEntityBuilder;
import gov.ca.cwds.fixture.investigation.RaceAndEthnicityEntityBuilder;
import gov.ca.cwds.rest.api.domain.LegacyDescriptor;
import gov.ca.cwds.rest.api.domain.RaceAndEthnicity;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

@SuppressWarnings("javadoc")
public class PersonTest {
  private LegacyDescriptor legacyDescriptor;
  private String lastUpdatedBy = "0X5";
  private String lastUpdatedAt = "2016-04-27T23:30:14.000Z";
  private String firstName = "Art";
  private String middleName = "Mike";
  private String lastName = "Griswald";
  private String suffixTitle = "bs";
  private String gender = "M";
  private String birthDate = "1998-10-30";
  private String ssn = "999667777";
  private Set<Short> languages = new LinkedHashSet<Short>();

  private Short primaryLanguage = 1253;
  private Short secondaryLanguage = 1255;
  private RaceAndEthnicity raceAndEthnicity = new RaceAndEthnicityEntityBuilder().build();
  private Boolean sensitive = false;
  private Boolean sealed = false;
  private DateTime now = new DateTime("2010-10-01T15:26:42.000-0700");

  private BigDecimal phoneNumber = new BigDecimal(3219876);
  private LegacyDescriptor phoneLegacyDescriptor =
      new LegacyDescriptor("1234567ABC", "001-2000-3399-415790", now, "CLIENT_T", "Client");

  private PhoneNumber phone = new PhoneNumber(phoneNumber, 3322, "Home", phoneLegacyDescriptor);
  private Set<PhoneNumber> phoneNumbers = new HashSet<PhoneNumber>();

  private Set<String> roles = new HashSet<>();

  private InvestigationAddress address = new InvestigationAddressEntityBuilder().build();
  private Set<InvestigationAddress> addresses = new HashSet<>();

  @Before
  public void setup() {
    roles.add("Mandated reporter");
    addresses.add(address);
    languages.add(primaryLanguage);
    languages.add(secondaryLanguage);
    legacyDescriptor =
        new LegacyDescriptor("1234567ABC", "111-222-333-4444", now, "CLIENT_T", "Client");
    phoneNumbers.add(phone);

  }

  @Test
  public void testEmptyConstructorSuccess() {
    Person person = new Person();
    assertNotNull(person);
  }

  @Test
  public void testDomainConstructorSuccess() {
    Person person = new Person(legacyDescriptor, lastUpdatedBy, lastUpdatedAt, firstName,
        middleName, lastName, suffixTitle, gender, birthDate, ssn, languages, raceAndEthnicity,
        sensitive, sealed, phoneNumbers, roles, addresses);
    assertThat(legacyDescriptor, is(equalTo(person.getLegacyDescriptor())));
    assertThat(lastUpdatedBy, is(equalTo(person.getLastUpdatedBy())));
    assertThat(lastUpdatedAt, is(equalTo(person.getLastUpdatedAt())));
    assertThat(firstName, is(equalTo(person.getFirstName())));
    assertThat(middleName, is(equalTo(person.getMiddleName())));
    assertThat(lastName, is(equalTo(person.getLastName())));
    assertThat(suffixTitle, is(equalTo(person.getNameSuffix())));
    assertThat(gender, is(equalTo(person.getGender())));
    assertThat(birthDate, is(equalTo(person.getDateOfBirth())));
    assertThat(ssn, is(equalTo(person.getSsn())));
    assertThat(languages, is(equalTo(person.getLanguages())));
    assertThat(raceAndEthnicity, is(equalTo(person.getRaceAndEthnicity())));
    assertThat(sensitive, is(equalTo(person.getSensitive())));
    assertThat(sealed, is(equalTo(person.getSealed())));
    assertThat(phoneNumbers, is(equalTo(person.getPhone())));
    assertThat(roles, is(equalTo(person.getRoles())));
    assertThat(addresses, is(equalTo(person.getAddresses())));

  }

  @Test
  public void shouldCompareEqualsToObjectWithSameValues() {
    Person person = new PersonEntityBuilder().build();
    Person otherPerson = new PersonEntityBuilder().build();
    assertEquals(person, otherPerson);

  }

  @Test
  public void shouldCompareNotEqualsToObjectWithDifferentValues() {
    Person person = new PersonEntityBuilder().build();
    Person otherPerson = new PersonEntityBuilder().setBirthDate("2001-09-30").build();
    assertThat(person, is(not(equals(otherPerson))));
  }

  @Test
  public void shouldFindMultipleItemInHashSetWhenItemsHaveWithDifferentValue() {
    Person person = new PersonEntityBuilder().build();
    Person otherPerson = new PersonEntityBuilder().setBirthDate("2001-09-30").build();

    Set<Person> items = new HashSet<>();
    items.add(otherPerson);
    items.add(person);

    assertTrue(items.contains(otherPerson));
    assertTrue(items.contains(person));
    assertEquals(2, items.size());
  }

  @Test
  public void shouldFindSingleItemInHashSetWhenMultipleItemsAddedWithSameValue() {
    Person person = new PersonEntityBuilder().build();
    Person otherPerson = new PersonEntityBuilder().build();
    Set<Person> items = new HashSet<>();
    items.add(otherPerson);
    items.add(person);

    assertTrue(items.contains(person));
    assertTrue(items.contains(otherPerson));
    assertEquals(1, items.size());
  }

  @Test
  public void equalsHashCodeWork() {
    EqualsVerifier.forClass(Person.class).suppress(Warning.NONFINAL_FIELDS).verify();
  }

}
