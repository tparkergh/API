package gov.ca.cwds.rest.api.domain.cms;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.After;
import org.junit.ClassRule;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.squarespace.jersey2.guice.JerseyGuiceUtils;

import gov.ca.cwds.rest.resources.cms.JerseyGuiceRule;
import io.dropwizard.jackson.Jackson;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

/**
 * @author CWDS API Team
 *
 */
public class TickleTest {

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
  private String id = "1234567ABC";
  private String affectedByCaseOrReferralId = "AafOQ4p0GE";
  private String affectedByCode = "CC";
  private String affectedByOtherId = "K5Fk8Yd00F";
  private String affectedByThirdId = "4OxzAA60BT";
  private String dueDate = "1992-06-18";
  private String noteText = "Boss is Back, I am waiting";
  private Short tickleMessageType = 2055;

  /*
   * Constructor Tests
   */
  /**
   * @throws Exception test standard test standard
   */
  @Test
  public void persistentObjectConstructorTest() throws Exception {

    Tickle domain = new Tickle(affectedByCaseOrReferralId, affectedByCode, affectedByOtherId,
        affectedByThirdId, dueDate, noteText, tickleMessageType);

    gov.ca.cwds.data.persistence.cms.Tickle pt =
        new gov.ca.cwds.data.persistence.cms.Tickle(id, domain, "lastUpdatedId", new Date());

    assertThat(domain.getAffectedByCaseOrReferralId(),
        is(equalTo(pt.getAffectedByCaseOrReferralId())));
    assertThat(domain.getAffectedByCode(), is(equalTo(pt.getAffectedByCode())));
    assertThat(domain.getAffectedByOtherId(), is(equalTo(pt.getAffectedByOtherId())));
    assertThat(domain.getAffectedByThirdId(), is(equalTo(pt.getAffectedByThirdId())));
    assertThat(domain.getDueDate(), is(equalTo(df.format(pt.getDueDate()))));
    assertThat(domain.getNoteText(), is(equalTo(pt.getNoteText())));
    assertThat(domain.getTickleMessageType(), is(equalTo(pt.getTickleMessageType())));
  }

  /**
   * @throws Exception test standard test standard
   */
  @Test
  public void jsonCreatorConstructorTest() throws Exception {

    Tickle domain = new Tickle(affectedByCaseOrReferralId, affectedByCode, affectedByOtherId,
        affectedByThirdId, dueDate, noteText, tickleMessageType);

    assertThat(domain.getAffectedByCaseOrReferralId(), is(equalTo(affectedByCaseOrReferralId)));
    assertThat(domain.getAffectedByCode(), is(equalTo(affectedByCode)));
    assertThat(domain.getAffectedByOtherId(), is(equalTo(affectedByOtherId)));
    assertThat(domain.getAffectedByThirdId(), is(equalTo(affectedByThirdId)));
    assertThat(domain.getDueDate(), is(equalTo(dueDate)));
    assertThat(domain.getNoteText(), is(equalTo(noteText)));
    assertThat(domain.getTickleMessageType(), is(equalTo(tickleMessageType)));
  }

  /**
   * 
   */
  @Test
  public void equalsHashCodeWork() {
    EqualsVerifier.forClass(Tickle.class).suppress(Warning.NONFINAL_FIELDS).verify();
  }


  @SuppressWarnings("javadoc")
  @Test
  public void serializesToJSON() throws Exception {
    final String expected = MAPPER.writeValueAsString(
        MAPPER.readValue(fixture("fixtures/domain/legacy/Tickle/valid/valid.json"), Tickle.class));

    assertThat(MAPPER.writeValueAsString(validDomainTickle()), is(equalTo(expected)));
  }

  @SuppressWarnings("javadoc")
  @Test
  public void deserializesFromJSON() throws Exception {
    assertThat(
        MAPPER.readValue(fixture("fixtures/domain/legacy/Tickle/valid/valid.json"), Tickle.class),
        is(equalTo(validDomainTickle())));
  }

  private gov.ca.cwds.rest.api.domain.cms.Tickle validDomainTickle()
      throws JsonParseException, JsonMappingException, IOException {

    gov.ca.cwds.rest.api.domain.cms.Tickle validDomainTickle =
        MAPPER.readValue(fixture("fixtures/domain/legacy/Tickle/valid/valid.json"),
            gov.ca.cwds.rest.api.domain.cms.Tickle.class);
    return validDomainTickle;
  }


}
