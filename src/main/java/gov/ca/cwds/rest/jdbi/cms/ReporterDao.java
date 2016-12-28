package gov.ca.cwds.rest.jdbi.cms;

import org.hibernate.SessionFactory;

import com.google.inject.Inject;

import gov.ca.cwds.inject.CmsSessionFactory;
import gov.ca.cwds.rest.api.persistence.cms.Reporter;
import gov.ca.cwds.rest.jdbi.BaseDaoImpl;

/**
 * DAO for {@link Reporter}.
 * 
 * @author CWDS API Team
 */
public class ReporterDao extends BaseDaoImpl<Reporter> {

  /**
   * Constructor
   * 
   * @param sessionFactory The session factory
   */
  @Inject
  public ReporterDao(@CmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

}
