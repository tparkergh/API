package gov.ca.cwds.rest.api.domain.cms;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import gov.ca.cwds.rest.resources.cms.JerseyGuiceRule;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

import org.junit.After;
import org.junit.ClassRule;
import org.junit.Test;

import com.squarespace.jersey2.guice.JerseyGuiceUtils;

public class ClientCollateralTest {

  @SuppressWarnings("javadoc")
  @After
  public void ensureServiceLocatorPopulated() {
    JerseyGuiceUtils.reset();
  }

  @SuppressWarnings("javadoc")
  @ClassRule
  public static JerseyGuiceRule rule = new JerseyGuiceRule();

  private String thirdId = "CLNTCOLL";
  private Short collateralClientReporterRelationshipType = 511;
  private String activeIndicator = "Y";
  private String commentDescription = "comment";
  private String clientId = "CLIENTID";
  private String collateralIndividualId = "COLLCLIENT";


  @Test
  public void persistentObjectConstructorTest() throws Exception {

    ClientCollateral domain =
        new ClientCollateral(activeIndicator, collateralClientReporterRelationshipType,
            commentDescription, clientId, collateralIndividualId);

    gov.ca.cwds.data.persistence.cms.ClientCollateral pt =
        new gov.ca.cwds.data.persistence.cms.ClientCollateral(thirdId, domain, "lastUpdatedId");

    assertThat(domain.getActiveIndicator(), is(equalTo(pt.getActiveIndicator())));
    assertThat(domain.getCollateralClientReporterRelationshipType(),
        is(equalTo(pt.getCollateralClientReporterRelationshipType())));
    assertThat(domain.getCommentDescription(), is(equalTo(pt.getCommentDescription())));
    assertThat(domain.getClientId(), is(equalTo(pt.getClientId())));
    assertThat(domain.getCollateralIndividualId(), is(equalTo(pt.getCollateralIndividualId())));
  }

  @Test
  public void jsonCreatorConstructorTest() throws Exception {

    ClientCollateral domain =
        new ClientCollateral(activeIndicator, collateralClientReporterRelationshipType,
            commentDescription, clientId, collateralIndividualId);
    assertThat(domain.getActiveIndicator(), is(equalTo(activeIndicator)));
    assertThat(domain.getCollateralClientReporterRelationshipType(),
        is(equalTo(collateralClientReporterRelationshipType)));
    assertThat(domain.getCommentDescription(), is(commentDescription));
    assertThat(domain.getClientId(), is(equalTo(clientId)));
    assertThat(domain.getCollateralIndividualId(), is(equalTo(collateralIndividualId)));

  }

  @Test
  public void equalsHashCodeWork() {
    EqualsVerifier.forClass(ClientCollateral.class).suppress(Warning.NONFINAL_FIELDS).verify();
  }


}
