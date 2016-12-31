package gov.ca.cwds.data.cms;

import org.hibernate.SessionFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.CrudsDaoImpl;
import gov.ca.cwds.data.persistence.cms.ReferralClient;
import gov.ca.cwds.inject.CmsSessionFactory;

/**
 * DAO for {@link ReferralClient}.
 * 
 * @author CWDS API Team
 */
public class ReferralClientDao extends CrudsDaoImpl<ReferralClient> {

  /**
   * Constructor
   * 
   * @param sessionFactory The session factory
   */
  @Inject
  public ReferralClientDao(@CmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

}
