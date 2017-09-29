package gov.ca.cwds.rest.api.domain.investigation;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

import gov.ca.cwds.fixture.investigation.RelationshipEntityBuilder;
import gov.ca.cwds.fixture.investigation.RelationshipToEntityBuilder;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

@SuppressWarnings("javadoc")
public class RelationshipTest {

  private String tableName = "CLIENT_T";
  private String id = "1234567ABC";
  private DateTime now = new DateTime();

  private CmsRecordDescriptor cmsRecordDescriptor =
      new CmsRecordDescriptor(id, "111-222-333-4444", now, tableName, "Client");
  private String firstName = "Jackson";
  private String middleName = "R";
  private String lastName = "Greene";
  private String dateOfBirth = "2000-10-01";
  private String suffixTitle = "";
  private Boolean sensitive = Boolean.FALSE;
  private Boolean sealed = Boolean.FALSE;
  private Set<RelationshipTo> relationshipsTo = new HashSet();
  private RelationshipTo relationshipTo = new RelationshipToEntityBuilder().build();

  @Before
  public void setup() {

  }

  @Test
  public void shouldCreateObjectWithDefaultConstructor() {
    Relationship relationship = new Relationship();
    assertNotNull(relationship);
  }

  @Test
  public void testDomainConstructorSuccess() throws Exception {
    Relationship relationship = new Relationship(id, dateOfBirth, firstName, middleName, lastName,
        suffixTitle, sensitive, sealed, cmsRecordDescriptor, relationshipsTo);

    assertThat(id, is(equalTo(relationship.getId())));
    assertThat(firstName, is(equalTo(relationship.getFirstName())));
    assertThat(middleName, is(equalTo(relationship.getMiddleName())));
    assertThat(lastName, is(equalTo(relationship.getLastName())));
    assertThat(suffixTitle, is(equalTo(relationship.getSuffixName())));
    assertThat(sensitive, is(equalTo(relationship.getSensitive())));
    assertThat(sealed, is(equalTo(relationship.getSealed())));
    assertThat(cmsRecordDescriptor, is(equalTo(relationship.getCmsRecordDescriptor())));
    assertThat(relationshipsTo, is(equalTo(relationship.getRelatedTo())));
  }

  @Test
  public void shouldCompareEqualsToObjectWithSameValues() {
    Relationship relationship = new RelationshipEntityBuilder().build();
    Relationship otherRelationship = new RelationshipEntityBuilder().build();
    assertEquals(relationship, otherRelationship);
  }

  @Test
  public void shouldCompareNotEqualsToObjectWithDifferentValues() {
    Relationship relationship = new RelationshipEntityBuilder().build();
    Relationship otherRelationship =
        new RelationshipEntityBuilder().setDateOfBirth("2001-10-30").build();
    assertThat(relationship, is(not(equals(otherRelationship))));
  }

  @Test
  public void shouldFindMultipleItemInHashSetWhenItemsHaveWithDifferentValue() {
    Relationship relationship = new RelationshipEntityBuilder().build();
    Relationship otherRelationship =
        new RelationshipEntityBuilder().setSensitive(Boolean.TRUE).build();
    Set<Relationship> items = new HashSet<>();
    items.add(relationship);
    items.add(otherRelationship);

    assertTrue(items.contains(relationship));
    assertTrue(items.contains(otherRelationship));
    assertEquals(2, items.size());

  }

  @Test
  public void shouldFindSingleItemInHashSetWhenMultipleItemsAddedWithSameValue() {
    Relationship relationship = new RelationshipEntityBuilder().build();
    Relationship otherRelationship = new RelationshipEntityBuilder().build();
    Set<Relationship> items = new HashSet<>();
    items.add(relationship);
    items.add(otherRelationship);

    assertTrue(items.contains(relationship));
    assertTrue(items.contains(otherRelationship));
    assertEquals(1, items.size());

  }

  @Test
  public void equalsHashCodeWork() {
    EqualsVerifier.forClass(Relationship.class).suppress(Warning.NONFINAL_FIELDS).verify();
  }

}
