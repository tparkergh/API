package gov.ca.cwds.data.cms;

import org.hibernate.SessionFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.BaseDaoImpl;
import gov.ca.cwds.data.persistence.cms.GovernmentOrganizationEntity;
import gov.ca.cwds.inject.CmsSessionFactory;

/**
 * DAO for {@link GovernmentOrganizationEntity}.
 * 
 * @author CWDS API Team
 */
public class GovernmentOrganizationDao extends BaseDaoImpl<GovernmentOrganizationEntity> {

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
