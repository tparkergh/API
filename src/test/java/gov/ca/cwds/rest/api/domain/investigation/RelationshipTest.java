package gov.ca.cwds.rest.api.domain.investigation;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import org.junit.Test;

import gov.ca.cwds.fixture.investigation.RelationshipEntityBuilder;
import gov.ca.cwds.fixture.investigation.RelationshipToEntityBuilder;

public class RelationshipTest {

  private RelationshipTo relationshipTo = new RelationshipToEntityBuilder().build();

  @Test
  public void testDomainConstructorSuccess() throws Exception {
    Relationship relationship = new RelationshipEntityBuilder().build();
    Relationship domain = new Relationship(relationship.getId(), relationship.getDateOfBirth(),
        relationship.getFirstName(), relationship.getMiddleName(), relationship.getLastName(),
        relationship.getSuffixName(), relationship.getSensitive(), relationship.getSealed(),
        relationship.getLegacyDescriptor(), relationship.getRelatedTo());

    assertThat(domain.getId(), is(equalTo(relationship.getId())));
    assertThat(domain.getDateOfBirth(), is(equalTo(relationship.getDateOfBirth())));
    assertThat(domain.getFirstName(), is(equalTo(relationship.getFirstName())));
    assertThat(domain.getMiddleName(), is(equalTo(relationship.getMiddleName())));
    assertThat(domain.getLastName(), is(equalTo(relationship.getLastName())));
    assertThat(domain.getSuffixName(), is(equalTo(relationship.getSuffixName())));
    assertThat(domain.getSensitive(), is(equalTo(relationship.getSensitive())));
    assertThat(domain.getSealed(), is(equalTo(relationship.getSealed())));
    assertThat(domain.getLegacyDescriptor(), is(equalTo(relationship.getLegacyDescriptor())));
    assertThat(domain.getRelatedTo(), is(equalTo(relationship.getRelatedTo())));

  }
}
