package gov.ca.cwds.rest.jdbi.cms;

import org.hibernate.SessionFactory;

import com.google.inject.Inject;

import gov.ca.cwds.inject.CmsSessionFactory;
import gov.ca.cwds.rest.api.persistence.cms.CrossReport;
import gov.ca.cwds.rest.jdbi.CrudsDaoImpl;

/**
 * DAO for {@link CrossReport}.
 * 
 * @author CWDS API Team
 */
public class CrossReportDao extends CrudsDaoImpl<CrossReport> {

  /**
   * Constructor
   * 
   * @param sessionFactory The sessionFactory
   */
  @Inject
  public CrossReportDao(@CmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

}

