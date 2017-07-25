package gov.ca.cwds.rest.api.domain.cms;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import gov.ca.cwds.rest.api.domain.DomainChef;
import gov.ca.cwds.rest.services.ServiceException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class PostedClientRelationshipTest {
  private final static DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
  private String id = "CLNTRELN";
  private Short clientRelationshipType = 172;
  private String absentParentCode = "N";
  private String endDate = "2017-01-07";
  private String startDate = "2017-01-07";
  private String secondaryClientId = "SECCLIENT";
  private String primaryClientId = "PRICLIENT";
  private String sameHomeCode = "Y";

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Test
  public void persistentObjectConstructorTest() throws Exception {

    gov.ca.cwds.data.persistence.cms.ClientRelationship persistent =
        new gov.ca.cwds.data.persistence.cms.ClientRelationship(absentParentCode,
            clientRelationshipType, DomainChef.uncookDateString(endDate), secondaryClientId,
            primaryClientId, id, sameHomeCode, DomainChef.uncookDateString(startDate));
    PostedClientRelationship domain = new PostedClientRelationship(persistent);
    assertThat(domain.getAbsentParentCode(), is(equalTo(persistent.getAbsentParentCode())));
    assertThat(domain.getClientRelationshipType(),
        is(equalTo(persistent.getClientRelationshipType())));
    assertThat(domain.getSecondaryClientId(), is(equalTo(persistent.getSecondaryClientId())));
    assertThat(domain.getPrimaryClientId(), is(equalTo(persistent.getPrimaryClientId())));
    assertThat(domain.getEndDate(), is(equalTo(df.format(persistent.getEndDate()))));
    assertThat(domain.getSameHomeCode(), is(equalTo(persistent.getSameHomeCode())));
    assertThat(domain.getStartDate(), is(equalTo(df.format(persistent.getStartDate()))));
  }

  @Test
  public void jsonCreatorConstructorTest() throws Exception {

    ClientRelationship clientRelationship =
        new ClientRelationship(absentParentCode, clientRelationshipType, endDate,
            secondaryClientId, primaryClientId, sameHomeCode, startDate);
    PostedClientRelationship domain = new PostedClientRelationship(clientRelationship, id);
    assertThat(domain.getAbsentParentCode(), is(equalTo(absentParentCode)));
    assertThat(domain.getClientRelationshipType(), is(equalTo(clientRelationshipType)));
    assertThat(domain.getSecondaryClientId(), is(equalTo(secondaryClientId)));
    assertThat(domain.getPrimaryClientId(), is(equalTo(primaryClientId)));
    assertThat(domain.getEndDate(), is(equalTo(endDate)));
    assertThat(domain.getSameHomeCode(), is(equalTo(sameHomeCode)));
    assertThat(domain.getStartDate(), is(equalTo(startDate)));
    assertThat(domain.getId(), is(equalTo(id)));
  }

  @Test
  public void equalsHashCodeWork() {
    EqualsVerifier.forClass(PostedClientRelationship.class).suppress(Warning.NONFINAL_FIELDS)
        .suppress(Warning.STRICT_INHERITANCE).withRedefinedSuperclass().verify();
  }

  @Test
  public void serviceExceptionWhenIdIsNull() throws Exception {
    thrown.expect(ServiceException.class);
    ClientRelationship clientRelationship =
        new ClientRelationship(absentParentCode, clientRelationshipType, endDate,
            secondaryClientId, primaryClientId, sameHomeCode, startDate);
    new PostedClientRelationship(clientRelationship, null);
  }
}
