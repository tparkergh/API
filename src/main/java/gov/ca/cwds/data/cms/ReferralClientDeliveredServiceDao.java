package gov.ca.cwds.data.cms;

import org.hibernate.SessionFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.CrudsDaoImpl;
import gov.ca.cwds.data.persistence.cms.ReferralClientDeliveredService;
import gov.ca.cwds.inject.CmsSessionFactory;

/**
 * DAO for {@link ReferralClientDeliveredService}.
 * 
 * @author CWDS API Team
 */
public class ReferralClientDeliveredServiceDao
    extends CrudsDaoImpl<ReferralClientDeliveredService> {

  /**
   * Constructor
   * 
   * @param sessionFactory The session factory
   */
  @Inject
  public ReferralClientDeliveredServiceDao(@CmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

}
