package gov.ca.cwds.data.cms;

import org.hibernate.SessionFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.BaseDaoImpl;
import gov.ca.cwds.data.persistence.cms.Attorney;
import gov.ca.cwds.inject.CmsSessionFactory;

/**
 * DAO for {@link Attorney}.
 * 
 * @author CWDS API Team
 */
public class AttorneyDao extends BaseDaoImpl<Attorney> {

  /**
   * Constructor
   * 
   * @param sessionFactory The sessionFactory
   */
  @Inject
  public AttorneyDao(@CmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }
}
