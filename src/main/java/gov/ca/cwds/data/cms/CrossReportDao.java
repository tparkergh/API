package gov.ca.cwds.data.cms;

import org.hibernate.SessionFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.CrudsDaoImpl;
import gov.ca.cwds.data.persistence.cms.CrossReport;
import gov.ca.cwds.inject.CmsSessionFactory;

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

