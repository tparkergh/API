package gov.ca.cwds.rest.api.domain.cms;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

import org.junit.Test;

public class PostedClientCollateralTest {
  private String thirdId = "CLNTCOLL";
  private Short collateralClientReporterRelationshipType = 511;
  private String activeIndicator = "Y";
  private String commentDescription = "comment";
  private String clientId = "CLIENTID";
  private String collateralIndividualId = "COLLCLIENT";

  @Test
  public void persistentObjectConstructorTest() throws Exception {

    gov.ca.cwds.data.persistence.cms.ClientCollateral persistent =
        new gov.ca.cwds.data.persistence.cms.ClientCollateral(activeIndicator,
            collateralClientReporterRelationshipType, commentDescription, clientId,
            collateralIndividualId, thirdId);
    PostedClientCollateral domain = new PostedClientCollateral(persistent);
    assertThat(domain.getActiveIndicator(), is(equalTo(persistent.getActiveIndicator())));
    assertThat(domain.getCollateralClientReporterRelationshipType(),
        is(equalTo(persistent.getCollateralClientReporterRelationshipType())));
    assertThat(domain.getCommentDescription(), is(equalTo(persistent.getCommentDescription())));
    assertThat(domain.getClientId(), is(equalTo(persistent.getClientId())));
    assertThat(domain.getCollateralIndividualId(),
        is(equalTo(persistent.getCollateralIndividualId())));
  }

  @Test
  public void jsonCreatorConstructorTest() throws Exception {

    ClientCollateral clientRelationship =
        new ClientCollateral(activeIndicator, collateralClientReporterRelationshipType,
            commentDescription, clientId, collateralIndividualId);
    PostedClientCollateral domain = new PostedClientCollateral(clientRelationship, thirdId);
    assertThat(domain.getActiveIndicator(), is(equalTo(activeIndicator)));
    assertThat(domain.getCollateralClientReporterRelationshipType(),
        is(equalTo(collateralClientReporterRelationshipType)));
    assertThat(domain.getCommentDescription(), is(commentDescription));
    assertThat(domain.getClientId(), is(equalTo(clientId)));
    assertThat(domain.getCollateralIndividualId(), is(equalTo(collateralIndividualId)));
    assertThat(domain.getThirdId(), is(equalTo(thirdId)));
  }

  @Test
  public void equalsHashCodeWork() {
    EqualsVerifier.forClass(PostedClientCollateral.class).suppress(Warning.NONFINAL_FIELDS)
        .suppress(Warning.STRICT_INHERITANCE).withRedefinedSuperclass().verify();
  }
}
