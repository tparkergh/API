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
import gov.ca.cwds.fixture.DrmsDocumentResourceBuilder;

/**
 * @author CWDS API Team
 *
 */
public class DrmsDocumentTest implements PersistentTestTemplate {

  private String id = "1234567ABC";
  private String lastUpdatedId = "0X5";

  private static final ObjectMapper MAPPER = SystemCodeTestHarness.MAPPER;

  /*
   * Constructor test
   */
  @Override
  @Test
  public void testEmptyConstructor() throws Exception {
    assertThat(DrmsDocument.class.newInstance(), is(notNullValue()));
  }

  @Override
  @Test
  public void testPersistentConstructor() throws Exception {

    DrmsDocument vp = validDrmsDocument();

    gov.ca.cwds.data.persistence.cms.DrmsDocument persistent =
        new DrmsDocument(id, vp.getCreationTimeStamp(), vp.getDrmsDocumentTemplateId(),
            vp.getFingerprintStaffPerson(), vp.getStaffPersonId(), vp.getHandleName());

    assertThat(persistent.getId(), is(equalTo(id)));
    assertThat(persistent.getCreationTimeStamp(), is(equalTo(vp.getCreationTimeStamp())));
    assertThat(persistent.getDrmsDocumentTemplateId(), is(equalTo(vp.getDrmsDocumentTemplateId())));
    assertThat(persistent.getFingerprintStaffPerson(), is(equalTo(vp.getFingerprintStaffPerson())));
    assertThat(persistent.getStaffPersonId(), is(equalTo(vp.getStaffPersonId())));
    assertThat(persistent.getHandleName(), is(equalTo(vp.getHandleName())));
  }

  @Override
  @Test
  public void testConstructorUsingDomain() throws Exception {

    gov.ca.cwds.rest.api.domain.cms.DrmsDocument domain = new DrmsDocumentResourceBuilder().build();

    DrmsDocument persistent = new DrmsDocument(id, domain, lastUpdatedId);

    assertThat(persistent.getId(), is(equalTo(id)));
    assertThat(persistent.getCreationTimeStamp(), is(equalTo(domain.getCreationTimeStamp())));
    assertThat(persistent.getDrmsDocumentTemplateId(),
        is(equalTo(domain.getDrmsDocumentTemplateId())));
    assertThat(persistent.getFingerprintStaffPerson(),
        is(equalTo(domain.getFingerprintStaffPerson())));
    assertThat(persistent.getStaffPersonId(), is(equalTo(domain.getStaffPersonId())));
    assertThat(persistent.getHandleName(), is(equalTo(domain.getHandleName())));
  }

  private DrmsDocument validDrmsDocument()
      throws JsonParseException, JsonMappingException, IOException {

    DrmsDocument validDrmsDocument = MAPPER.readValue(
        fixture("fixtures/persistent/DrmsDocument/valid/valid.json"), DrmsDocument.class);
    return validDrmsDocument;
  }

}
