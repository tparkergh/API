package gov.ca.cwds.rest.api.domain.investigation;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import org.junit.Test;

import gov.ca.cwds.fixture.investigation.RelationshipToEntityBuilder;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

public class RelationshipToTest {


  private RelationshipTo relationshipTo = new RelationshipToEntityBuilder().build();

  @Test
  public void testDomainConstructorSuccess() throws Exception {
    RelationshipTo domain = new RelationshipTo(relationshipTo.getRelatedFirstName(),
        relationshipTo.getRelatedLastName(), relationshipTo.getRelationshipToPerson(),
        relationshipTo.getRelationshipContext(), relationshipTo.getRelatedPersonRelationship(),
        relationshipTo.getLegacyDescriptor());

    assertThat(domain.getRelatedFirstName(), is(equalTo(relationshipTo.getRelatedFirstName())));
    assertThat(domain.getRelatedLastName(), is(equalTo(relationshipTo.getRelatedLastName())));
    assertThat(domain.getRelationshipContext(),
        is(equalTo(relationshipTo.getRelationshipContext())));
    assertThat(domain.getRelatedPersonRelationship(),
        is(equalTo(relationshipTo.getRelatedPersonRelationship())));
    assertThat(domain.getLegacyDescriptor(), is(equalTo(relationshipTo.getLegacyDescriptor())));
  }

  @Test
  public void equalsHashCodeWork() {
    EqualsVerifier.forClass(RelationshipTo.class).suppress(Warning.NONFINAL_FIELDS).verify();
  }

}
