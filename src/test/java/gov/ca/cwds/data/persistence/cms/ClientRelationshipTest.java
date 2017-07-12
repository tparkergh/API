package gov.ca.cwds.data.persistence.cms;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import gov.ca.cwds.rest.api.domain.DomainChef;

import org.junit.Test;

/**
 * @author CWDS API Team
 *
 */
public class ClientRelationshipTest {
  private String id = "CLNTRELN";
  private Short clientRelationshipType = 172;
  private String absentParentCode = "N";
  private String endDate = "2017-01-07";
  private String startDate = "2017-01-07";
  private String secondaryClientId = "SECCLIENT";
  private String primaryClientId = "PRICLIENT";
  private String sameHomeCode = "Y";

  private String lastUpdatedId = "0XA";

  /*
   * Constructor test
   */
  @SuppressWarnings("javadoc")
  @Test
  public void emtpyConstructorIsNotNull() throws Exception {
    assertThat(StaffPerson.class.newInstance(), is(notNullValue()));
  }

  @SuppressWarnings("javadoc")
  @Test
  public void testConstructorUsingDomain() throws Exception {
    gov.ca.cwds.rest.api.domain.cms.ClientRelationship domain =
        new gov.ca.cwds.rest.api.domain.cms.ClientRelationship("N", (short) 172, "2017-01-07",
            "SECCLIENT", "PRICLIENT", "Y", "2017-01-07");

    ClientRelationship persistent = new ClientRelationship(id, domain, lastUpdatedId);
    assertThat(persistent.getId(), is(equalTo(id)));
    assertThat(persistent.getEndDate(), is(equalTo(DomainChef.uncookDateString(endDate))));
    assertThat(persistent.getAbsentParentCode(), is(equalTo(absentParentCode)));
    assertThat(persistent.getClientRelationshipType(), is(equalTo(clientRelationshipType)));
    assertThat(persistent.getPrimaryClientId(), is(equalTo(primaryClientId)));
    assertThat(persistent.getSecondaryClientId(), is(equalTo(secondaryClientId)));
    assertThat(persistent.getSameHomeCode(), is(equalTo(sameHomeCode)));
    assertThat(persistent.getStartDate(), is(equalTo(DomainChef.uncookDateString(startDate))));
    assertThat(persistent.getLastUpdatedId(), is(equalTo(lastUpdatedId)));
  }
}
