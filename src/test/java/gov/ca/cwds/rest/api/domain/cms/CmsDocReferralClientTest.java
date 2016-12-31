package gov.ca.cwds.rest.api.domain.cms;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.ws.rs.core.Response;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import gov.ca.cwds.data.CrudsDao;
import gov.ca.cwds.data.persistence.cms.Referral;
import gov.ca.cwds.rest.api.domain.DomainChef;
import gov.ca.cwds.rest.api.domain.cms.CmsDocReferralClient;
import gov.ca.cwds.rest.api.domain.cms.CmsDocReferralClient.CmsDocReferralClientDetail;
import gov.ca.cwds.rest.api.domain.cms.CmsDocReferralClient.CmsDocReferralClientDocument;
import gov.ca.cwds.rest.resources.cms.CmsDocReferralClientResource;
import io.dropwizard.jackson.Jackson;
import io.dropwizard.testing.junit.ResourceTestRule;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

public class CmsDocReferralClientTest {
  private static final CmsDocReferralClientResource mockedCmsDocReferralClientResource =
      mock(CmsDocReferralClientResource.class);

  @ClassRule
  public static final ResourceTestRule resources =
      ResourceTestRule.builder().addResource(mockedCmsDocReferralClientResource).build();
  private static final ObjectMapper MAPPER = Jackson.newObjectMapper();
  private CmsDocReferralClient validCmsDocReferralClient = validCmsDocReferralClient();

  public CmsDocReferralClientTest() throws ParseException {}

  @Before
  public void setup() {
    @SuppressWarnings("rawtypes")
    CrudsDao crudsDao = mock(CrudsDao.class);

    when(crudsDao.find(any())).thenReturn(mock(Referral.class));

    when(mockedCmsDocReferralClientResource.create(eq(validCmsDocReferralClient)))
        .thenReturn(Response.status(Response.Status.NO_CONTENT).entity(null).build());
  }

  @Test
  public void serializesToJSON() throws Exception {
    final String expected = MAPPER.writeValueAsString(
        MAPPER.readValue(fixture("fixtures/domain/cms/CmsDocReferralClient/valid/valid.json"),
            CmsDocReferralClient.class));

    assertThat(MAPPER.writeValueAsString(validCmsDocReferralClient()), is(equalTo(expected)));
  }

  @Test
  public void deserializesFromJSON() throws Exception {
    assertThat(
        MAPPER.readValue(fixture("fixtures/domain/cms/CmsDocReferralClient/valid/valid.json"),
            CmsDocReferralClient.class),
        is(equalTo(validCmsDocReferralClient())));
  }

  @Test
  public void equalsCmsDocReferralClientDetailsHashCodeWorks() throws Exception {
    EqualsVerifier.forClass(CmsDocReferralClientDetail.class).suppress(Warning.NONFINAL_FIELDS)
        .verify();
  }

  @Test
  public void equalsCmsDocReferralClientDocumentHashCodeWorks() throws Exception {
    EqualsVerifier.forClass(CmsDocReferralClientDocument.class).suppress(Warning.NONFINAL_FIELDS)
        .verify();
  }

  @Test
  public void equalsHashCodeWork() {
    EqualsVerifier.forClass(CmsDocReferralClient.class).suppress(Warning.NONFINAL_FIELDS).verify();
  }

  /*
   * Constructor Tests
   */
  @Test
  public void persistentObjectConstructorTest() throws Exception {
    Set<CmsDocReferralClientDetail> details = new LinkedHashSet<CmsDocReferralClientDetail>();
    CmsDocReferralClientDetail rc1 =
        new CmsDocReferralClientDetail("1234567ABC", "ABC1234567", "first", "middle", "last",
            "2015-01-01", "other name", "name type", "123 first st", "business");
    CmsDocReferralClient.CmsDocReferralClientDocument cd =
        new CmsDocReferralClient.CmsDocReferralClientDocument("document name",
            "base64-encoded binary document");

    CmsDocReferralClient domain =
        new CmsDocReferralClient("0131351421120020*JONESMF 00004", null, "2015-01-01", cd, details);

    gov.ca.cwds.data.persistence.cms.CmsDocReferralClient persistent =
        new gov.ca.cwds.data.persistence.cms.CmsDocReferralClient(domain.getId(), rc1.getReferlId(),
            rc1.getClientId(), rc1.getCommonFirstName(), rc1.getCommonMiddleName(),
            rc1.getCommonLastName(), DomainChef.uncookDateString(rc1.getBirthDate()),
            rc1.getOtherName(), rc1.getNameType(), rc1.getAddress(), rc1.getAddressType());

    assertThat(domain.getId(), is(equalTo(persistent.getDocHandle())));
    // assertThat(domain.getDocName(), is(equalTo(persistent.getDocName())));
    // assertThat(domain.getDocAddedDate(), is(equalTo(persistent.getDocAddedDate())));
  }

  private CmsDocReferralClient validCmsDocReferralClient() {

    Set<CmsDocReferralClientDetail> details = new LinkedHashSet<CmsDocReferralClientDetail>();

    CmsDocReferralClientDetail rc1 =
        new CmsDocReferralClientDetail("1234567ABC", "ABC1234567", "first", "middle", "last",
            "2015-01-01", "other name", "name type", "123 first st", "business");
    CmsDocReferralClientDetail rc2 =
        new CmsDocReferralClientDetail("2345678ABC", "ABC2345678", "fname", "mname", "lname",
            "2015-10-01", "other name", "name type", "234 second st", "home");
    details.add(rc1);
    details.add(rc2);
    CmsDocReferralClient.CmsDocReferralClientDocument cd =
        new CmsDocReferralClient.CmsDocReferralClientDocument("document name",
            "base64-encoded binary document");

    return new CmsDocReferralClient("0131351421120020*JONESMF 00004", "MJS000.DOC", "2015-01-01",
        cd, details);

  }

}
