package gov.ca.cwds.data.persistence.cms;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import java.io.IOException;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import gov.ca.cwds.data.persistence.junit.template.PersistentTestTemplate;

/**
 * @author CWDS API Team
 *
 */
public class ClientUcTest implements PersistentTestTemplate {

  private String lastUpdatedId = "0X5";

  private static final ObjectMapper MAPPER = SystemCodeTestHarness.MAPPER;

  /**
   * Constructor test
   */
  @Override
  @Test
  public void testEmptyConstructor() throws Exception {
    assertThat(ClientUc.class.newInstance(), is(notNullValue()));
  }

  @Override
  @Test
  public void testPersistentConstructor() throws Exception {
    ClientUc vcuc = validClientUc();

    gov.ca.cwds.data.persistence.cms.ClientUc persistent =
        new gov.ca.cwds.data.persistence.cms.ClientUc(vcuc.getPktableId(),
            vcuc.getSourceTableCode(), vcuc.getCommonFirstName(), vcuc.getCommonLastName(),
            vcuc.getCommonMiddleName());

    assertThat(persistent.getPktableId(), is(equalTo(vcuc.getPktableId())));
    assertThat(persistent.getSourceTableCode(), is(equalTo(vcuc.getSourceTableCode())));
    assertThat(persistent.getCommonFirstName(), is(equalTo(vcuc.getCommonFirstName())));
    assertThat(persistent.getCommonLastName(), is(equalTo(vcuc.getCommonLastName())));
    assertThat(persistent.getCommonMiddleName(), is(equalTo(vcuc.getCommonMiddleName())));
  }

  @Override
  @Test
  public void testConstructorUsingDomain() throws Exception {
    gov.ca.cwds.rest.api.domain.cms.ClientUc domain = validDomainClientUc();

    ClientUc persistent = new ClientUc(domain, lastUpdatedId);

    assertThat(persistent.getPktableId(), is(equalTo(domain.getPktableId())));
    assertThat(persistent.getSourceTableCode(), is(equalTo(domain.getSourceTableCode())));
    assertThat(persistent.getCommonFirstName(), is(equalTo(domain.getCommonFirstName())));
    assertThat(persistent.getCommonLastName(), is(equalTo(domain.getCommonLastName())));
    assertThat(persistent.getCommonMiddleName(), is(equalTo(domain.getCommonMiddleName())));
  }

  private ClientUc validClientUc() throws JsonParseException, JsonMappingException, IOException {

    ClientUc validClientUc =
        MAPPER.readValue(fixture("fixtures/persistent/ClientUc/valid/valid.json"), ClientUc.class);
    return validClientUc;
  }

  private gov.ca.cwds.rest.api.domain.cms.ClientUc validDomainClientUc()
      throws JsonParseException, JsonMappingException, IOException {

    gov.ca.cwds.rest.api.domain.cms.ClientUc validDomainClientUc =
        MAPPER.readValue(fixture("fixtures/domain/legacy/ClientUc/valid/valid.json"),
            gov.ca.cwds.rest.api.domain.cms.ClientUc.class);
    return validDomainClientUc;
  }

}
