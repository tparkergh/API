package gov.ca.cwds.rest.api.domain.investigation;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import gov.ca.cwds.fixture.investigation.RelationshipEntityBuilder;

@SuppressWarnings("javadoc")
public class RelationshipListTest {

  private Relationship relationship1 = new RelationshipEntityBuilder().build();
  private Relationship relationship2 = new RelationshipEntityBuilder().setFirstName("joe").build();
  private Relationship relationship3 = new RelationshipEntityBuilder().setLastName("utah").build();
  private Set<Relationship> relationships = new HashSet<>();
  private Set<Relationship> relationships1 = new HashSet<>();

  @Before
  public void setup() {
    relationships.add(relationship1);
    relationships.add(relationship2);

    relationships1.add(relationship1);
    relationships1.add(relationship3);
  }

  @Test
  public void testEmptyConstructor() {
    RelationshipList relationshipList = new RelationshipList();
    assertNotNull(relationshipList);
  }

  @Test
  public void testDomainConstructorSuccess() {
    RelationshipList relationshipList = new RelationshipList(relationships);
    assertThat(relationshipList.getRelationship(), is(equalTo(relationships)));
  }

  @Test
  public void shouldCompareEqualsToObjectWithSameValues() {
    RelationshipList relationshipList = new RelationshipList(relationships);
    RelationshipList relationshipList1 = new RelationshipList(relationships);
    assertEquals(relationshipList, relationshipList1);

  }

  @Test
  public void shouldCompareNotEqualsToObjectWithDifferentValues() {
    RelationshipList relationshipList = new RelationshipList(relationships);
    RelationshipList relationshipList1 = new RelationshipList(relationships1);
    assertThat(relationshipList, is(not(equals(relationshipList1))));
  }

}
