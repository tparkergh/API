package gov.ca.cwds.rest.services.cms;

import static org.mockito.Mockito.mock;
import gov.ca.cwds.data.cms.SystemCodeDao;
import gov.ca.cwds.data.cms.SystemMetaDao;

import org.apache.commons.lang3.NotImplementedException;
import org.hamcrest.junit.ExpectedException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;


/**
 * @author CWDS API Team
 *
 */
public class SystemCodeServiceTest {

  private SystemCodeDao systemCodeDao;
  private SystemMetaDao systemMetaDao;
  private SystemCodeService systemCodeService;

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @SuppressWarnings("javadoc")
  @Before
  public void setup() throws Exception {
    systemCodeDao = mock(SystemCodeDao.class);
    systemMetaDao = mock(SystemMetaDao.class);
    systemCodeService = new SystemCodeService(systemCodeDao, systemMetaDao);

  }

  /*
   * delete tests
   */
  @Test
  public void deleteThrowsNotImplementedException() throws Exception {
    thrown.expect(NotImplementedException.class);
    systemCodeService.delete(new Long(1));
  }

  /*
   * update tests
   */
  @Test
  public void updateThrowsNotImplementedException() throws Exception {
    thrown.expect(NotImplementedException.class);
    systemCodeService.update(new Long(1), new gov.ca.cwds.rest.api.domain.cms.SystemCode(
        (short) 123, (short) 123, "abc", "abc", null, null, null, null, null));
  }

  /*
   * create tests
   */
  @Test
  public void createThrowsNotImplementedException() throws Exception {
    thrown.expect(NotImplementedException.class);
    systemCodeService.create(new gov.ca.cwds.rest.api.domain.cms.SystemCode((short) 123,
        (short) 123, "abc", "abc", null, null, null, null, null));
  }

}
