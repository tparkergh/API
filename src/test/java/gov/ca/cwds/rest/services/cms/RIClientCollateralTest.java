package gov.ca.cwds.rest.services.cms;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

import org.junit.Test;

import gov.ca.cwds.data.cms.ClientDao;
import gov.ca.cwds.data.persistence.cms.ClientCollateral;

public class RIClientCollateralTest {

  @Test
  public void type() throws Exception {
    assertThat(RIClientCollateral.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    ClientDao clientDao = null;
    RIClientCollateral target = new RIClientCollateral(clientDao);
    assertThat(target, notNullValue());
  }

  @Test
  public void apply_Args__ClientCollateral() throws Exception {
    ClientDao clientDao = mock(ClientDao.class);
    RIClientCollateral target = new RIClientCollateral(clientDao);
    ClientCollateral t = mock(ClientCollateral.class);
    Boolean actual = target.apply(t);
    Boolean expected = false;
    assertThat(actual, is(equalTo(expected)));
  }

}
