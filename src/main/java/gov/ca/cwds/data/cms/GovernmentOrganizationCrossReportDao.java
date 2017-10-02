package gov.ca.cwds.data.cms;

import org.hibernate.SessionFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.CrudsDaoImpl;
import gov.ca.cwds.data.persistence.cms.GovernmentOrganizationCrossReport;
import gov.ca.cwds.inject.CmsSessionFactory;

/**
 * DAO for {@link GovernmentOrganizationCrossReport}.
 * 
 * @author CWDS API Team
 */
public class GovernmentOrganizationCrossReportDao
    extends CrudsDaoImpl<GovernmentOrganizationCrossReport> {

  /**
   * Constructor
   * 
   * @param sessionFactory the sessionFactory
   */
  @Inject
  public GovernmentOrganizationCrossReportDao(@CmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

}
