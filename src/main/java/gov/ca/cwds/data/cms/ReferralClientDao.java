package gov.ca.cwds.data.cms;

import gov.ca.cwds.data.CrudsDaoImpl;
import gov.ca.cwds.data.persistence.cms.ReferralClient;
import gov.ca.cwds.inject.CmsSessionFactory;

import org.hibernate.Query;
import org.hibernate.SessionFactory;

import com.google.inject.Inject;

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

  @SuppressWarnings("unchecked")
  public ReferralClient[] findByReferralId(String referralId) {
    Query query =
        this.getSessionFactory().getCurrentSession()
            .getNamedQuery("gov.ca.cwds.data.persistence.cms.ReferralClient.findByReferral")
            .setString("referralId", referralId);
    return (ReferralClient[]) query.list().toArray(new ReferralClient[0]);
  }

  public ReferralClient[] findByClientId(String clientId) {
    Query query =
        this.getSessionFactory().getCurrentSession()
            .getNamedQuery("gov.ca.cwds.data.persistence.cms.ReferralClient.findByClient")
            .setString("clientId", clientId);
    return (ReferralClient[]) query.list().toArray(new ReferralClient[0]);
  }
}
