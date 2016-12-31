package gov.ca.cwds.data.cms;

import org.hibernate.SessionFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.CrudsDaoImpl;
import gov.ca.cwds.data.persistence.cms.Referral;
import gov.ca.cwds.inject.CmsSessionFactory;

/**
 * DAO for {@link Referral}.
 * 
 * @author CWDS API Team
 */
public class ReferralDao extends CrudsDaoImpl<Referral> {

  /**
   * Constructor
   * 
   * @param sessionFactory The session factory
   */
  @Inject
  public ReferralDao(@CmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

}
