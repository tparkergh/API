package gov.ca.cwds.data.cms;

import org.hibernate.SessionFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.BaseDaoImpl;
import gov.ca.cwds.data.persistence.cms.ClientCollateral;
import gov.ca.cwds.inject.CmsSessionFactory;

/**
 * DAO for {@link ClientCollateral}.
 * 
 * @author CWDS API Team
 */
public class ClientCollateralDao extends BaseDaoImpl<ClientCollateral> {

  /**
   * Constructor
   * 
   * @param sessionFactory The sessionFactory
   */
  @Inject
  public ClientCollateralDao(@CmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

}
