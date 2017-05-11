package gov.ca.cwds.data.cms;

import org.hibernate.SessionFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.CrudsDaoImpl;
import gov.ca.cwds.data.persistence.cms.CountyOwnership;
import gov.ca.cwds.inject.CmsSessionFactory;

/**
 * DAO for {@link CountyOwnership}.
 * 
 * @author CWDS API Team
 */
public class CountyOwnershipDao extends CrudsDaoImpl<CountyOwnership> {

  /**
   * Constructor
   * 
   * @param sessionFactory The session factory
   */
  @Inject
  public CountyOwnershipDao(@CmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

}
