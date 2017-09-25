package gov.ca.cwds.data.dao.contact;

import gov.ca.cwds.data.CrudsDaoImpl;
import gov.ca.cwds.data.persistence.contact.ReferralClientDeliveredServiceEntity;
import gov.ca.cwds.inject.CmsSessionFactory;

import org.hibernate.Query;
import org.hibernate.SessionFactory;

import com.google.inject.Inject;

/**
 * DAO for {@link ReferralClientDeliveredServiceEntity}.
 * 
 * @author CWDS API Team
 */
public class ReferralClientDeliveredServiceDao extends
    CrudsDaoImpl<ReferralClientDeliveredServiceEntity> {

  /**
   * Constructor
   * 
   * @param sessionFactory The session factory
   */
  @Inject
  public ReferralClientDeliveredServiceDao(@CmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  @SuppressWarnings("unchecked")
  public ReferralClientDeliveredServiceEntity[] findByReferralId(String referralId) {
    Query query =
        this.getSessionFactory()
            .getCurrentSession()
            .getNamedQuery(
                "gov.ca.cwds.data.persistence.contact.ReferralClientDeliveredServiceEntity.findAllForReferralId")
            .setString("referralId", referralId);
    return (ReferralClientDeliveredServiceEntity[]) query.list().toArray(
        new ReferralClientDeliveredServiceEntity[0]);

  }
}
