package gov.ca.cwds.rest.jdbi.cms;

import org.hibernate.SessionFactory;

import gov.ca.cwds.rest.api.persistence.cms.CollateralIndividual;
import gov.ca.cwds.rest.jdbi.BaseDaoImpl;

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
