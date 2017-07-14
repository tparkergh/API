package gov.ca.cwds.data.persistence.cms;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import java.io.IOException;

import org.junit.Ignore;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import gov.ca.cwds.data.persistence.junit.template.PersistentTestTemplate;
import gov.ca.cwds.rest.api.domain.DomainChef;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

public class TickleTest implements PersistentTestTemplate {

  private String id = "1234567ABC";
  private String lastUpdatedId = "0X5";

  private static final ObjectMapper MAPPER = SystemCodeTestHarness.MAPPER;

  /*
   * Constructor test
   */
  @Override
  @Test
  public void testEmptyConstructor() throws Exception {
    assertThat(Tickle.class.newInstance(), is(notNullValue()));
  }

  @Override
  @Test
  public void testPersistentConstructor() throws Exception {

    Tickle vt = validTickle();

    gov.ca.cwds.data.persistence.cms.Tickle persistent =
        new gov.ca.cwds.data.persistence.cms.Tickle(id, vt.getAffectedByCaseOrReferralId(),
            vt.getAffectedByCode(), vt.getAffectedByOtherId(), vt.getAffectedByThirdId(),
            vt.getDueDate(), vt.getNoteText(), vt.getTickleMessageType());

    assertThat(persistent.getId(), is(equalTo(id)));
    assertThat(persistent.getAffectedByCaseOrReferralId(),
        is(equalTo(vt.getAffectedByCaseOrReferralId())));
    assertThat(persistent.getAffectedByCode(), is(equalTo(vt.getAffectedByCode())));
    assertThat(persistent.getAffectedByCode(), is(equalTo(vt.getAffectedByCode())));
    assertThat(persistent.getAffectedByOtherId(), is(equalTo(vt.getAffectedByOtherId())));
    assertThat(persistent.getAffectedByThirdId(), is(equalTo(vt.getAffectedByThirdId())));
    assertThat(persistent.getDueDate(), is(equalTo(vt.getDueDate())));
    assertThat(persistent.getNoteText(), is(equalTo(vt.getNoteText())));
    assertThat(persistent.getTickleMessageType(), is(equalTo(vt.getTickleMessageType())));
  }

  @Override
  @Test
  public void testConstructorUsingDomain() throws Exception {

    gov.ca.cwds.rest.api.domain.cms.Tickle domain = validDomainTickle();

    Tickle persistent = new Tickle(id, domain, lastUpdatedId);

    assertThat(persistent.getId(), is(equalTo(id)));
    assertThat(persistent.getAffectedByCaseOrReferralId(),
        is(equalTo(domain.getAffectedByCaseOrReferralId())));
    assertThat(persistent.getAffectedByCode(), is(equalTo(domain.getAffectedByCode())));
    assertThat(persistent.getAffectedByCode(), is(equalTo(domain.getAffectedByCode())));
    assertThat(persistent.getAffectedByOtherId(), is(equalTo(domain.getAffectedByOtherId())));
    assertThat(persistent.getAffectedByThirdId(), is(equalTo(domain.getAffectedByThirdId())));
    assertThat(persistent.getDueDate(),
        is(equalTo(DomainChef.uncookDateString(domain.getDueDate()))));
    assertThat(persistent.getNoteText(), is(equalTo(domain.getNoteText())));
    assertThat(persistent.getTickleMessageType(), is(equalTo(domain.getTickleMessageType())));
    assertThat(persistent.getLastUpdatedId(), is(equalTo(lastUpdatedId)));
  }

  @Override
  @Test
  @Ignore
  public void testEqualsHashCodeWorks() {
    EqualsVerifier.forClass(Tickle.class).suppress(Warning.NONFINAL_FIELDS).verify();
  }

  private Tickle validTickle() throws JsonParseException, JsonMappingException, IOException {

    Tickle validTickle =
        MAPPER.readValue(fixture("fixtures/persistent/Tickle/valid/valid.json"), Tickle.class);
    return validTickle;
  }

  private gov.ca.cwds.rest.api.domain.cms.Tickle validDomainTickle()
      throws JsonParseException, JsonMappingException, IOException {

    gov.ca.cwds.rest.api.domain.cms.Tickle validDomainTickle =
        MAPPER.readValue(fixture("fixtures/domain/legacy/Tickle/valid/valid.json"),
            gov.ca.cwds.rest.api.domain.cms.Tickle.class);
    return validDomainTickle;
  }

}
