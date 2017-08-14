package gov.ca.cwds.rest.services.referentialintegrity;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import gov.ca.cwds.data.cms.ClientDao;
import gov.ca.cwds.data.cms.CollateralIndividualDao;
import gov.ca.cwds.data.persistence.cms.ClientCollateral;
import gov.ca.cwds.rest.validation.ReferentialIntegrityException;

/**
 * @author Tabpcenc
 *
 */
public class RIClientCollateralTest {

  /**
   * 
   */
  @Rule
  public ExpectedException expectedException = ExpectedException.none();

  /**
   * @throws Exception
   */
  @Test
  public void type() throws Exception {
    assertThat(RIClientCollateral.class, notNullValue());
  }

  /**
   * @throws Exception
   */
  @Test
  public void instantiation() throws Exception {
    ClientDao clientDao = null;
    CollateralIndividualDao collateralIndividualDao = null;
    RIClientCollateral target = new RIClientCollateral(clientDao, collateralIndividualDao);
    assertThat(target, notNullValue());
  }


  /**
   * @throws Exception Message
   */
  @Test
  public void testExceptionMessage() throws Exception {
    ClientDao clientDao = mock(ClientDao.class);
    CollateralIndividualDao collateralIndividualDao = mock(CollateralIndividualDao.class);
    RIClientCollateral target = new RIClientCollateral(clientDao, collateralIndividualDao);
    ClientCollateral t = mock(ClientCollateral.class);
    expectedException.expect(ReferentialIntegrityException.class);
    when(target.apply(t)).thenThrow(new ReferentialIntegrityException(
        "ClientCollateral => Client with given Identifier is not present in database1"));
  }

}
