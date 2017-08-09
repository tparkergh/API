package gov.ca.cwds.rest.services.referentialintegrity;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import gov.ca.cwds.data.cms.AllegationDao;
import gov.ca.cwds.data.cms.ClientDao;

/**
 * @author CWDS API Team
 *
 */
public class RIAllegationPerpetratorHistoryTest {

  @Test
  public void type() throws Exception {
    assertThat(RIAllegationPerpetratorHistory.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    AllegationDao allegationDao = null;
    ClientDao clientDao = null;
    RIAllegationPerpetratorHistory target =
        new RIAllegationPerpetratorHistory(clientDao, allegationDao);
    assertThat(target, notNullValue());
  }

}
