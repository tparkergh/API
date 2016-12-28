package gov.ca.cwds.rest.jdbi.cms;

import org.hibernate.SessionFactory;

import com.google.inject.Inject;

import gov.ca.cwds.inject.CmsSessionFactory;
import gov.ca.cwds.rest.api.persistence.cms.Attorney;
import gov.ca.cwds.rest.jdbi.BaseDaoImpl;

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
