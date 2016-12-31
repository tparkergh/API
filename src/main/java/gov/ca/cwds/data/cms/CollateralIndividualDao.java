package gov.ca.cwds.data.cms;

import org.hibernate.SessionFactory;

import gov.ca.cwds.data.BaseDaoImpl;
import gov.ca.cwds.data.persistence.cms.CollateralIndividual;

/**
 * DAO for {@link CollateralIndividual}.
 * 
 * @author CWDS API Team
 */
public class CollateralIndividualDao extends BaseDaoImpl<CollateralIndividual> {

  /**
   * Constructor
   * 
   * @param sessionFactory The sessionFactory
   */
  public CollateralIndividualDao(SessionFactory sessionFactory) {
    super(sessionFactory);
  }
}
