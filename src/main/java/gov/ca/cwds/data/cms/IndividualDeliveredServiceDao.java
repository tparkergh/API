package gov.ca.cwds.data.cms;

import org.hibernate.SessionFactory;

import gov.ca.cwds.data.CrudsDaoImpl;
import gov.ca.cwds.data.persistence.cms.IndividualDeliveredServiceEntity;
import gov.ca.cwds.inject.CmsSessionFactory;

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
  public IndividualDeliveredServiceDao(@CmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

}
