package gov.ca.cwds.data.cms;

import org.hibernate.SessionFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.CrudsDaoImpl;
import gov.ca.cwds.data.persistence.cms.ContactPartyDeliveredService;
import gov.ca.cwds.inject.CmsSessionFactory;

/**
 * DAO for {@link ContactPartyDeliveredService}.
 *
 * @author CWDS API Team
 */
public class ContactPartyDeliveredServiceDao extends CrudsDaoImpl<ContactPartyDeliveredService> {

  /**
   * Constructor
   * 
   * @param sessionFactory the sessionFactory
   */
  @Inject
  public ContactPartyDeliveredServiceDao(@CmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);

  }

}
