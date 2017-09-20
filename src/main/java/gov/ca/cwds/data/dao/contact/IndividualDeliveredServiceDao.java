package gov.ca.cwds.data.dao.contact;

import gov.ca.cwds.data.CrudsDaoImpl;
import gov.ca.cwds.data.persistence.contact.IndividualDeliveredServiceEntity;
import gov.ca.cwds.inject.CmsSessionFactory;

import org.hibernate.Query;
import org.hibernate.SessionFactory;

import com.google.inject.Inject;

/**
 * DAO for {@link IndividualDeliveredServiceEntity}.
 * 
 * @author CWDS API Team
 */
public class IndividualDeliveredServiceDao extends CrudsDaoImpl<IndividualDeliveredServiceEntity> {

  /**
   * Constructor
   * 
   * @param sessionFactory The session factory
   */
  @Inject
  public IndividualDeliveredServiceDao(@CmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  @SuppressWarnings("unchecked")
  public IndividualDeliveredServiceEntity[] findByDeliveredServiceId(String deliveredServiceId) {
    Query query =
        this.getSessionFactory()
            .getCurrentSession()
            .getNamedQuery(
                "gov.ca.cwds.data.persistence.contact.IndividualDeliveredServiceEntity.findAllForDeliveredService")
            .setString("deliveredServiceId", deliveredServiceId);
    return (IndividualDeliveredServiceEntity[]) query.list().toArray(
        new IndividualDeliveredServiceEntity[0]);

  }
}
