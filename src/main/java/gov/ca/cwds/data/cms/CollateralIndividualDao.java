package gov.ca.cwds.data.cms;

import org.hibernate.SessionFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.BaseDaoImpl;
import gov.ca.cwds.data.persistence.cms.CollateralIndividual;
import gov.ca.cwds.inject.CmsSessionFactory;

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
  @Inject
  public CollateralIndividualDao(@CmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }
}
