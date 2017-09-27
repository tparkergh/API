package gov.ca.cwds.data.cms;

import org.hibernate.SessionFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.BaseDaoImpl;
import gov.ca.cwds.data.persistence.cms.GovernmentOrganization;
import gov.ca.cwds.inject.CmsSessionFactory;

/**
 * DAO for {@link GovernmentOrganization}.
 * 
 * @author CWDS API Team
 */
public class GovernmentOrganizationDao extends BaseDaoImpl<GovernmentOrganization> {

  /**
   * Constructor
   * 
   * @param sessionFactory The sessionFactory
   */
  @Inject
  public GovernmentOrganizationDao(@CmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

}
