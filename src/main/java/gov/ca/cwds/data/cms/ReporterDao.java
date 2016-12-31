package gov.ca.cwds.data.cms;

import org.hibernate.SessionFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.BaseDaoImpl;
import gov.ca.cwds.data.persistence.cms.Reporter;
import gov.ca.cwds.inject.CmsSessionFactory;

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
