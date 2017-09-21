package gov.ca.cwds.rest.api.domain.investigation;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.joda.time.DateTime;
import org.junit.Test;

import gov.ca.cwds.fixture.investigation.RelationshipToEntityBuilder;
import gov.ca.cwds.rest.api.domain.LegacyDescriptor;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

@SuppressWarnings("javadoc")
public class RelationshipToTest {

  private String tableName = "CLIENT_T";
  private String id = "2345678ABC";
  private String relatedFirstName = "Steve";
  private String relatedLastName = "Briggs";
  private String relationship = "Brother";
  private String relationshipToPerson = "Sister";
  private String relationshipContext = "step";
  private DateTime now = new DateTime("2010-10-01T15:26:42.000-0700");

  private LegacyDescriptor legacyDescriptor =
      new LegacyDescriptor(id, "111-222-333-4444", now, tableName, "Client");


  @Test
  public void testDomainConstructorSuccess() throws Exception {
    RelationshipTo relationshipTo = new RelationshipTo(relatedFirstName, relatedLastName,
        relationship, relationshipContext, relationshipToPerson, legacyDescriptor);

    assertThat(relatedFirstName, is(equalTo(relationshipTo.getRelatedFirstName())));
    assertThat(relatedLastName, is(equalTo(relationshipTo.getRelatedLastName())));
    assertThat(relationshipContext, is(equalTo(relationshipTo.getRelationshipContext())));
    assertThat(relationship, is(equalTo(relationshipTo.getRelationshipToPerson())));
    assertThat(relationshipToPerson, is(equalTo(relationshipTo.getRelatedPersonRelationship())));
    assertThat(legacyDescriptor, is(equalTo(relationshipTo.getLegacyDescriptor())));
  }

  @Test
  public void shouldCompareEqualsToObjectWithSameValues() {
    RelationshipTo relationshipTo = new RelationshipToEntityBuilder().build();
    RelationshipTo otherRelationshipTo = new RelationshipToEntityBuilder().build();
    assertEquals(relationshipTo, otherRelationshipTo);
  }

  @Test
  public void shouldCompareNotEqualsToObjectWithDifferentValues() {
    RelationshipTo relationshipTo = new RelationshipToEntityBuilder().build();
    RelationshipTo otherRelationshipTo =
        new RelationshipToEntityBuilder().setRelationshipContext("paternal").build();
    assertThat(relationshipTo, is(not(equals(otherRelationshipTo))));
  }

  @Test
  public void shouldFindMultipleItemInHashSetWhenItemsHaveWithDifferentValue() {
    RelationshipTo relationshipTo = new RelationshipToEntityBuilder().build();
    RelationshipTo otherRelationshipTo =
        new RelationshipToEntityBuilder().setRelationshipContext("paternal").build();

    Set<RelationshipTo> items = new HashSet<>();
    items.add(relationshipTo);
    items.add(otherRelationshipTo);

    assertTrue(items.contains(relationshipTo));
    assertTrue(items.contains(otherRelationshipTo));
    assertEquals(2, items.size());
  }

  @Test
  public void equalsHashCodeWork() {
    EqualsVerifier.forClass(RelationshipTo.class).suppress(Warning.NONFINAL_FIELDS).verify();
  }

}
