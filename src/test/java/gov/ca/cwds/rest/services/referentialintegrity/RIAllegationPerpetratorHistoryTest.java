package gov.ca.cwds.rest.services.referentialintegrity;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import gov.ca.cwds.data.cms.AllegationDao;
import gov.ca.cwds.data.cms.ClientDao;
import gov.ca.cwds.fixture.AllegationPerpetratorHistoryResourceBuilder;
import gov.ca.cwds.rest.api.domain.cms.AllegationPerpetratorHistory;
import gov.ca.cwds.rest.validation.ReferentialIntegrityException;

/**
 * @author CWDS API Team
 *
 */
@SuppressWarnings("javadoc")
public class RIAllegationPerpetratorHistoryTest {

  private ClientDao clientDao;
  private AllegationDao allegationDao;

  @Before
  public void setup() {
    allegationDao = mock(AllegationDao.class);
    clientDao = mock(ClientDao.class);
  }


  @Test
  public void type() throws Exception {
    assertThat(RIAllegationPerpetratorHistory.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    RIAllegationPerpetratorHistory target =
        new RIAllegationPerpetratorHistory(clientDao, allegationDao);
    assertThat(target, notNullValue());
  }

  /*
   * 
   * Test for test the referential Integrity Exception
   */
  @Test(expected = ReferentialIntegrityException.class)
  public void testForReferentialIntegrityException() throws Exception {
    AllegationPerpetratorHistory allegationPerpetratorHistoryDomain =
        new AllegationPerpetratorHistoryResourceBuilder().createAllegationPerpetratorHistory();
    gov.ca.cwds.data.persistence.cms.AllegationPerpetratorHistory allegationPerpetratorHistory =
        new gov.ca.cwds.data.persistence.cms.AllegationPerpetratorHistory("ABC1234567",
            allegationPerpetratorHistoryDomain, "0X5");

    when(clientDao.find(any(String.class))).thenReturn(null);
    when(allegationDao.find(any(String.class))).thenReturn(null);
    RIAllegationPerpetratorHistory target =
        new RIAllegationPerpetratorHistory(clientDao, allegationDao);
    target.apply(allegationPerpetratorHistory);
  }

}
