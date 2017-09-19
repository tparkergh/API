package gov.ca.cwds.rest.services.referentialintegrity;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import gov.ca.cwds.data.cms.AllegationDao;
import gov.ca.cwds.data.cms.ClientDao;
import gov.ca.cwds.fixture.AllegationPerpetratorHistoryResourceBuilder;
import gov.ca.cwds.fixture.ClientResourceBuilder;
import gov.ca.cwds.fixture.CmsAllegationResourceBuilder;
import gov.ca.cwds.rest.api.domain.cms.Allegation;
import gov.ca.cwds.rest.api.domain.cms.AllegationPerpetratorHistory;
import gov.ca.cwds.rest.api.domain.cms.Client;
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
  public void riCheckForReferentialIntegrityException() throws Exception {
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

  @Test
  public void riCheckPassesWhenPerpetratorNull() throws Exception {
    AllegationPerpetratorHistory allegationPerpetratorHistoryDomain =
        new AllegationPerpetratorHistoryResourceBuilder().setPerpertratorClientId(null)
            .createAllegationPerpetratorHistory();
    gov.ca.cwds.data.persistence.cms.AllegationPerpetratorHistory allegationPerpetratorHistory =
        new gov.ca.cwds.data.persistence.cms.AllegationPerpetratorHistory("ABC1234567",
            allegationPerpetratorHistoryDomain, "0X5");

    Allegation allegationDomain = new CmsAllegationResourceBuilder().buildCmsAllegation();
    gov.ca.cwds.data.persistence.cms.Allegation allegation =
        new gov.ca.cwds.data.persistence.cms.Allegation("ABC1234plo", allegationDomain, "0X5");

    when(clientDao.find(any(String.class))).thenReturn(null);
    when(allegationDao.find(any(String.class))).thenReturn(allegation);
    RIAllegationPerpetratorHistory target =
        new RIAllegationPerpetratorHistory(clientDao, allegationDao);
    Boolean result = target.apply(allegationPerpetratorHistory);
    assertThat(result, is(equalTo(true)));
  }

  @Test
  public void riCheckPass() throws Exception {
    AllegationPerpetratorHistory allegationPerpetratorHistoryDomain =
        new AllegationPerpetratorHistoryResourceBuilder().setPerpertratorClientId(null)
            .createAllegationPerpetratorHistory();
    gov.ca.cwds.data.persistence.cms.AllegationPerpetratorHistory allegationPerpetratorHistory =
        new gov.ca.cwds.data.persistence.cms.AllegationPerpetratorHistory("ABC1234567",
            allegationPerpetratorHistoryDomain, "0X5");

    Client clientDomain = new ClientResourceBuilder().build();
    gov.ca.cwds.data.persistence.cms.Client client =
        new gov.ca.cwds.data.persistence.cms.Client("ABC123456k", clientDomain, "0X5");

    Allegation allegationDomain = new CmsAllegationResourceBuilder().buildCmsAllegation();
    gov.ca.cwds.data.persistence.cms.Allegation allegation =
        new gov.ca.cwds.data.persistence.cms.Allegation("ABC1234plo", allegationDomain, "0X5");

    when(clientDao.find(any(String.class))).thenReturn(client);
    when(allegationDao.find(any(String.class))).thenReturn(allegation);
    RIAllegationPerpetratorHistory target =
        new RIAllegationPerpetratorHistory(clientDao, allegationDao);
    Boolean result = target.apply(allegationPerpetratorHistory);
    assertThat(result, is(equalTo(true)));
  }

  @Test(expected = ReferentialIntegrityException.class)
  public void riCheckFailureWhenAllegationIsNull() throws Exception {
    AllegationPerpetratorHistory allegationPerpetratorHistoryDomain =
        new AllegationPerpetratorHistoryResourceBuilder().setAllegationId(null)
            .createAllegationPerpetratorHistory();
    gov.ca.cwds.data.persistence.cms.AllegationPerpetratorHistory allegationPerpetratorHistory =
        new gov.ca.cwds.data.persistence.cms.AllegationPerpetratorHistory("ABC1234567",
            allegationPerpetratorHistoryDomain, "0X5");

    Client clientDomain = new ClientResourceBuilder().build();
    gov.ca.cwds.data.persistence.cms.Client client =
        new gov.ca.cwds.data.persistence.cms.Client("ABC123456k", clientDomain, "0X5");

    when(clientDao.find(any(String.class))).thenReturn(client);
    when(allegationDao.find(any(String.class))).thenReturn(null);
    RIAllegationPerpetratorHistory target =
        new RIAllegationPerpetratorHistory(clientDao, allegationDao);
    target.apply(allegationPerpetratorHistory);

  }

}
