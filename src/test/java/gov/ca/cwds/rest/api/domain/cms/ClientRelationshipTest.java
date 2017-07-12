package gov.ca.cwds.rest.api.domain.cms;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import gov.ca.cwds.rest.resources.cms.JerseyGuiceRule;
import io.dropwizard.jackson.Jackson;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

import org.junit.After;
import org.junit.ClassRule;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.squarespace.jersey2.guice.JerseyGuiceUtils;

public class ClientRelationshipTest {

  @SuppressWarnings("javadoc")
  @After
  public void ensureServiceLocatorPopulated() {
    JerseyGuiceUtils.reset();
  }

  @SuppressWarnings("javadoc")
  @ClassRule
  public static JerseyGuiceRule rule = new JerseyGuiceRule();

  private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

  private final static DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
  private String id = "CLNTRELN";
  private Short clientRelationshipType = 172;
  private String absentParentCode = "N";
  private String endDate = "2017-01-07";
  private String startDate = "2017-01-07";
  private String secondaryClientId = "SECCLIENT";
  private String primaryClientId = "PRICLIENT";
  private String sameHomeCode = "Y";


  @Test
  public void persistentObjectConstructorTest() throws Exception {

    ClientRelationship domain =
        new ClientRelationship(absentParentCode, clientRelationshipType, endDate,
            secondaryClientId, primaryClientId, sameHomeCode, startDate);

    gov.ca.cwds.data.persistence.cms.ClientRelationship pt =
        new gov.ca.cwds.data.persistence.cms.ClientRelationship(id, domain, "lastUpdatedId");

    assertThat(domain.getAbsentParentCode(), is(equalTo(pt.getAbsentParentCode())));
    assertThat(domain.getClientRelationshipType(), is(equalTo(pt.getClientRelationshipType())));
    assertThat(domain.getSecondaryClientId(), is(equalTo(pt.getSecondaryClientId())));
    assertThat(domain.getPrimaryClientId(), is(equalTo(pt.getPrimaryClientId())));
    assertThat(domain.getEndDate(), is(equalTo(df.format(pt.getEndDate()))));
    assertThat(domain.getSameHomeCode(), is(equalTo(pt.getSameHomeCode())));
    assertThat(domain.getStartDate(), is(equalTo(df.format(pt.getStartDate()))));
  }

  @Test
  public void jsonCreatorConstructorTest() throws Exception {

    ClientRelationship domain =
        new ClientRelationship(absentParentCode, clientRelationshipType, endDate,
            secondaryClientId, primaryClientId, sameHomeCode, startDate);
    assertThat(domain.getAbsentParentCode(), is(equalTo(absentParentCode)));
    assertThat(domain.getClientRelationshipType(), is(equalTo(clientRelationshipType)));
    assertThat(domain.getSecondaryClientId(), is(equalTo(secondaryClientId)));
    assertThat(domain.getPrimaryClientId(), is(equalTo(primaryClientId)));
    assertThat(domain.getEndDate(), is(equalTo(endDate)));
    assertThat(domain.getSameHomeCode(), is(equalTo(sameHomeCode)));
    assertThat(domain.getStartDate(), is(equalTo(startDate)));
  }

  @Test
  public void equalsHashCodeWork() {
    EqualsVerifier.forClass(ClientRelationship.class).suppress(Warning.NONFINAL_FIELDS).verify();
  }


}
