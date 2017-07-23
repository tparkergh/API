package gov.ca.cwds.data.persistence.cms;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import java.io.IOException;
import java.util.Date;

import org.junit.Ignore;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import gov.ca.cwds.data.persistence.junit.template.PersistentTestTemplate;
import gov.ca.cwds.fixture.AllegationPerpetratorHistoryResourceBuilder;
import gov.ca.cwds.rest.api.domain.DomainChef;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

/**
 * @author CWDS API Team
 */
@SuppressWarnings("javadoc")
public class AllegationPerpetratorHistoryTest implements PersistentTestTemplate {

  private String id = "1234567ABC";
  private String staffId = "0X5";
  private Date lastUpdateDateTime = new Date();

  private static final ObjectMapper MAPPER = SystemCodeTestHarness.MAPPER;

  /*
   * Constructor test
   */
  @Override
  @Test
  public void testEmptyConstructor() throws Exception {
    assertThat(AllegationPerpetratorHistory.class.newInstance(), is(notNullValue()));
  }

  @Override
  @Test
  public void testConstructorUsingDomain() throws Exception {
    gov.ca.cwds.rest.api.domain.cms.AllegationPerpetratorHistory domain =
        validDomainAllegationPerpetratorHistory();
    AllegationPerpetratorHistory constructed =
        new AllegationPerpetratorHistory(id, domain, staffId);

    assertThat(constructed.getPrimaryKey(), is(equalTo(id)));
    assertThat(constructed.getId(), is(equalTo(id)));
    assertThat(constructed.getAllegationId(), is(equalTo(domain.getAllegationId())));
    assertThat(constructed.getCountySpecificCode(), is(equalTo(domain.getCountySpecificCode())));
    assertThat(constructed.getLastUpdatedId(), is(equalTo(staffId)));
    assertThat(constructed.getPerpetratorClientId(), is(equalTo(domain.getPerpetratorClientId())));
    assertThat(constructed.getPerpetratorUpdateDate(),
        is(equalTo(DomainChef.uncookDateString(domain.getPerpetratorUpdateDate()))));

  }

  @Test
  public void testConstructorUsingLastUpdatedTime() throws Exception {
    gov.ca.cwds.rest.api.domain.cms.AllegationPerpetratorHistory domain =
        validDomainAllegationPerpetratorHistory();
    AllegationPerpetratorHistory constructed =
        new AllegationPerpetratorHistory(id, domain, staffId, lastUpdateDateTime);

    assertThat(constructed.getPrimaryKey(), is(equalTo(id)));
    assertThat(constructed.getId(), is(equalTo(id)));
    assertThat(constructed.getAllegationId(), is(equalTo(domain.getAllegationId())));
    assertThat(constructed.getCountySpecificCode(), is(equalTo(domain.getCountySpecificCode())));
    assertThat(constructed.getLastUpdatedId(), is(equalTo(staffId)));
    assertThat(constructed.getPerpetratorClientId(), is(equalTo(domain.getPerpetratorClientId())));
    assertThat(constructed.getPerpetratorUpdateDate(),
        is(equalTo(DomainChef.uncookDateString(domain.getPerpetratorUpdateDate()))));
    assertThat(constructed.getLastUpdatedTime(), is(equalTo(lastUpdateDateTime)));

  }

  @Override
  @Test
  public void testPersistentConstructor() throws Exception {

    AllegationPerpetratorHistory vp = validAllegationPerpetratorHistory();

    gov.ca.cwds.data.persistence.cms.AllegationPerpetratorHistory persistent =
        new gov.ca.cwds.data.persistence.cms.AllegationPerpetratorHistory(id,
            vp.getCountySpecificCode(), vp.getPerpetratorClientId(), vp.getAllegationId(),
            vp.getPerpetratorUpdateDate());

    assertThat(persistent.getId(), is(equalTo(id)));
    assertThat(persistent.getCountySpecificCode(), is(equalTo(vp.getCountySpecificCode())));
    assertThat(persistent.getPerpetratorClientId(), is(equalTo(vp.getPerpetratorClientId())));
    assertThat(persistent.getAllegationId(), is(equalTo(vp.getAllegationId())));
    assertThat(persistent.getPerpetratorUpdateDate(), is(equalTo(vp.getPerpetratorUpdateDate())));
  }

  @Override
  @Test
  @Ignore
  public void testEqualsHashCodeWorks() {
    EqualsVerifier.forClass(AllegationPerpetratorHistory.class).suppress(Warning.NONFINAL_FIELDS)
        .verify();
  }

  private AllegationPerpetratorHistory validAllegationPerpetratorHistory()
      throws JsonParseException, JsonMappingException, IOException {

    AllegationPerpetratorHistory validAllegationPerpetratorHistory = MAPPER.readValue(
        fixture("fixtures/persistent/AllegationPerpetratorHistory/valid/valid.json"),
        AllegationPerpetratorHistory.class);
    return validAllegationPerpetratorHistory;
  }

  private gov.ca.cwds.rest.api.domain.cms.AllegationPerpetratorHistory validDomainAllegationPerpetratorHistory() {
    gov.ca.cwds.rest.api.domain.cms.AllegationPerpetratorHistory toTest =
        new AllegationPerpetratorHistoryResourceBuilder().createAllegationPerpetratorHistory();
    return toTest;

  }
}
