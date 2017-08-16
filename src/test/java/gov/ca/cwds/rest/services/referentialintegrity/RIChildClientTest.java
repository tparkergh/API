package gov.ca.cwds.rest.services.referentialintegrity;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import gov.ca.cwds.data.cms.ClientDao;
import gov.ca.cwds.data.persistence.cms.ChildClient;

import org.junit.Test;

public class RIChildClientTest {

  @Test
  public void type() throws Exception {
    assertThat(RIChildClient.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    ClientDao clientDao = null;
    RIChildClient target = new RIChildClient(clientDao);
    assertThat(target, notNullValue());
  }

  @Test
  public void apply_Args__ChildClient() throws Exception {
    ClientDao clientDao = mock(ClientDao.class);
    RIChildClient target = new RIChildClient(clientDao);
    ChildClient t = mock(ChildClient.class);
    Boolean actual = target.apply(t);
    Boolean expected = false;
    assertThat(actual, is(equalTo(expected)));
  }

}
