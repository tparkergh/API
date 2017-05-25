package gov.ca.cwds.rest.services.cms;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gov.ca.cwds.data.cms.SystemCodeDao;
import gov.ca.cwds.data.cms.SystemMetaDao;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.cms.SystemCode;
import gov.ca.cwds.rest.api.domain.cms.SystemCodeListResponse;
import gov.ca.cwds.rest.api.domain.cms.SystemMeta;
import gov.ca.cwds.rest.api.domain.cms.SystemMetaListResponse;
import io.dropwizard.jackson.Jackson;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.NotImplementedException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableSet;


/**
 * @author CWDS API Team
 *
 */
public class SystemCodeServiceTest {

  private static final ObjectMapper MAPPER = Jackson.newObjectMapper();
  private SystemCodeDao systemCodeDao;
  private SystemMetaDao systemMetaDao;
  private SystemCodeService systemCodeService;

  @SuppressWarnings("javadoc")
  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @SuppressWarnings("javadoc")
  @Before
  public void setup() throws Exception {
    systemCodeDao = mock(SystemCodeDao.class);
    systemMetaDao = mock(SystemMetaDao.class);
    systemCodeService = new SystemCodeService(systemCodeDao, systemMetaDao);
  }

  @Test
  public void findThrowsAssertionError() {
    thrown.expect(AssertionError.class);
    try {
      systemCodeService.find(1);
    } catch (AssertionError e) {
      System.out.println("error " + e.getMessage());
      assertEquals("Expected AssertionError", e.getMessage());
    }
  }

  @Test
  public void findReturnsCorrectEntitySystemMetas() throws Exception {
    String id = "";
    SystemMeta validSysMeta =
        MAPPER.readValue(fixture("fixtures/domain/legacy/SystemMeta/valid/valid.json"),
            SystemMeta.class);
    gov.ca.cwds.data.persistence.cms.SystemMeta systemMeta =
        new gov.ca.cwds.data.persistence.cms.SystemMeta("GVR_ENTC", "Government Entity Type",
            "Government Entity");
    gov.ca.cwds.data.persistence.cms.SystemMeta[] foundSysMetas =
        new gov.ca.cwds.data.persistence.cms.SystemMeta[1];
    foundSysMetas[0] = systemMeta;
    ImmutableSet.Builder<SystemMeta> builder = ImmutableSet.builder();
    builder.add(validSysMeta);
    Set<SystemMeta> expectedSysMetas = builder.build();
    SystemMetaListResponse expected = new SystemMetaListResponse(expectedSysMetas);

    when(systemMetaDao.findAll()).thenReturn(foundSysMetas);
    SystemMetaListResponse found = (SystemMetaListResponse) systemCodeService.find(id);
    assertThat(found, is(expected));
  }

  @Test
  public void findReturnsCorrectEntitySystemCodes() throws Exception {
    String id = "CNTRY_C";
    SystemCode validSysCode =
        MAPPER.readValue(fixture("fixtures/domain/legacy/SystemCode/valid/valid.json"),
            SystemCode.class);
    gov.ca.cwds.data.persistence.cms.SystemCode systemCode =
        new gov.ca.cwds.data.persistence.cms.SystemCode((short) 471, (short) 0, "N", "  ",
            "Albania", "AB  ", "0000000000", "CNTRY_C ", "     ");
    gov.ca.cwds.data.persistence.cms.SystemCode[] foundSysCodes =
        new gov.ca.cwds.data.persistence.cms.SystemCode[1];
    foundSysCodes[0] = systemCode;
    ImmutableSet.Builder<SystemCode> builder = ImmutableSet.builder();
    builder.add(validSysCode);
    Set<SystemCode> expectedSysCodes = builder.build();
    SystemCodeListResponse expected = new SystemCodeListResponse(expectedSysCodes);

    when(systemCodeService.findByCriteria(id)).thenReturn(foundSysCodes);
    SystemCodeListResponse found = (SystemCodeListResponse) systemCodeService.find(id);
    assertThat(found, is(expected));
  }

  @Test
  public void findReturnsEmptyWhenNotFound() throws Exception {
    gov.ca.cwds.data.persistence.cms.SystemCode[] foundSysCodes =
        new gov.ca.cwds.data.persistence.cms.SystemCode[0];
    when(systemCodeDao.findByForeignKeyMetaTable("ABC1234567")).thenReturn(foundSysCodes);
    Response found = systemCodeService.find("ABC1234567");
    SystemCodeListResponse expected = new SystemCodeListResponse(new HashSet<SystemCode>());
    assertEquals(found, expected);
  }

  @Test
  public void deleteThrowsNotImplementedException() throws Exception {
    thrown.expect(NotImplementedException.class);
    systemCodeService.delete(new Long(1));
  }

  @Test
  public void updateThrowsNotImplementedException() throws Exception {
    thrown.expect(NotImplementedException.class);
    systemCodeService.update(new Long(1), new gov.ca.cwds.rest.api.domain.cms.SystemCode(
        (short) 123, (short) 123, "abc", "abc", null, null, null, null, null));
  }

  @Test
  public void createThrowsNotImplementedException() throws Exception {
    thrown.expect(NotImplementedException.class);
    systemCodeService.create(new gov.ca.cwds.rest.api.domain.cms.SystemCode((short) 123,
        (short) 123, "abc", "abc", null, null, null, null, null));
  }

}
