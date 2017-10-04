package gov.ca.cwds.data.persistence.cms;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import java.util.Date;

import org.junit.Test;

import gov.ca.cwds.fixture.AllegationPerpetratorHistoryEntityBuilder;
import gov.ca.cwds.fixture.AllegationPerpetratorHistoryResourceBuilder;
import gov.ca.cwds.rest.api.domain.DomainChef;

/**
 * @author CWDS API Team
 */
public class AllegationPerpetratorHistoryTest {

  private String id = "1234567ABC";
  private String staffId = "0X5";
  private Date lastUpdateDateTime = new Date();

  /**
   * Empty Constructor test
   * 
   * @throws Exception - Exception
   */
  @Test
  public void testEmptyConstructor() throws Exception {
    assertThat(AllegationPerpetratorHistory.class.newInstance(), is(notNullValue()));
  }

  /**
   * Domain Constructor test
   * 
   * @throws Exception - Exception
   */
  @Test
  public void testConstructorUsingDomain() throws Exception {
    gov.ca.cwds.rest.api.domain.cms.AllegationPerpetratorHistory domainAllegationPerpetratorHistory =
        new AllegationPerpetratorHistoryResourceBuilder().createAllegationPerpetratorHistory();

    AllegationPerpetratorHistory constructed =
        new AllegationPerpetratorHistory(id, domainAllegationPerpetratorHistory, staffId);

    assertThat(constructed.getPrimaryKey(), is(equalTo(id)));
    assertThat(constructed.getId(), is(equalTo(id)));
    assertThat(constructed.getAllegationId(),
        is(equalTo(domainAllegationPerpetratorHistory.getAllegationId())));
    assertThat(constructed.getCountySpecificCode(),
        is(equalTo(domainAllegationPerpetratorHistory.getCountySpecificCode())));
    assertThat(constructed.getLastUpdatedId(), is(equalTo(staffId)));
    assertThat(constructed.getPerpetratorClientId(),
        is(equalTo(domainAllegationPerpetratorHistory.getPerpetratorClientId())));
    assertThat(constructed.getPerpetratorUpdateDate(), is(equalTo(DomainChef
        .uncookDateString(domainAllegationPerpetratorHistory.getPerpetratorUpdateDate()))));

  }

  /**
   * Last Updated Time Constructor test
   * 
   * @throws Exception - Exception
   */
  @Test
  public void testConstructorUsingLastUpdatedTime() throws Exception {
    gov.ca.cwds.rest.api.domain.cms.AllegationPerpetratorHistory domainAllegationPerpetratorHistory =
        new AllegationPerpetratorHistoryResourceBuilder().createAllegationPerpetratorHistory();

    AllegationPerpetratorHistory constructed = new AllegationPerpetratorHistory(id,
        domainAllegationPerpetratorHistory, staffId, lastUpdateDateTime);

    assertThat(constructed.getPrimaryKey(), is(equalTo(id)));
    assertThat(constructed.getId(), is(equalTo(id)));
    assertThat(constructed.getAllegationId(),
        is(equalTo(domainAllegationPerpetratorHistory.getAllegationId())));
    assertThat(constructed.getCountySpecificCode(),
        is(equalTo(domainAllegationPerpetratorHistory.getCountySpecificCode())));
    assertThat(constructed.getLastUpdatedId(), is(equalTo(staffId)));
    assertThat(constructed.getPerpetratorClientId(),
        is(equalTo(domainAllegationPerpetratorHistory.getPerpetratorClientId())));
    assertThat(constructed.getPerpetratorUpdateDate(), is(equalTo(DomainChef
        .uncookDateString(domainAllegationPerpetratorHistory.getPerpetratorUpdateDate()))));
    assertThat(constructed.getLastUpdatedTime(), is(equalTo(lastUpdateDateTime)));

  }

  /**
   * Persistent Constructor test
   * 
   * @throws Exception - Exception
   */
  @Test
  public void testPersistentConstructor() throws Exception {

    AllegationPerpetratorHistory validPersistent =
        new AllegationPerpetratorHistoryEntityBuilder().build();

    gov.ca.cwds.data.persistence.cms.AllegationPerpetratorHistory persistent =
        new gov.ca.cwds.data.persistence.cms.AllegationPerpetratorHistory(id,
            validPersistent.getCountySpecificCode(), validPersistent.getPerpetratorClientId(),
            validPersistent.getAllegationId(), validPersistent.getPerpetratorUpdateDate());

    assertThat(persistent.getId(), is(equalTo(id)));
    assertThat(persistent.getCountySpecificCode(),
        is(equalTo(validPersistent.getCountySpecificCode())));
    assertThat(persistent.getPerpetratorClientId(),
        is(equalTo(validPersistent.getPerpetratorClientId())));
    assertThat(persistent.getAllegationId(), is(equalTo(validPersistent.getAllegationId())));
    assertThat(persistent.getPerpetratorUpdateDate(),
        is(equalTo(validPersistent.getPerpetratorUpdateDate())));
  }

}
