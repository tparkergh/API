package gov.ca.cwds.rest.jdbi.cms;

import org.hibernate.SessionFactory;

import com.google.inject.Inject;

import gov.ca.cwds.inject.CmsSessionFactory;
import gov.ca.cwds.rest.api.persistence.cms.OtherClientName;
import gov.ca.cwds.rest.jdbi.BaseDaoImpl;

/**
 * DAO for {@link OtherClientName}.
 * 
 * @author CWDS API Team
 */
public class OtherClientNameDao extends BaseDaoImpl<OtherClientName> {

  /**
   * Constructor
   * 
   * @param sessionFactory The session factory
   */
  @Inject
  public OtherClientNameDao(@CmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

}
