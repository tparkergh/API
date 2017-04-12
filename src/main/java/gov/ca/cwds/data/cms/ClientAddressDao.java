package gov.ca.cwds.data.cms;

import org.hibernate.SessionFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.BaseDaoImpl;
import gov.ca.cwds.data.persistence.cms.ClientAddress;
import gov.ca.cwds.inject.CmsSessionFactory;

/**
 * Hibernate DAO for DB2
 * 
 * @author CWDS API Team
 * @see CmsSessionFactory
 * @see SessionFactory
 */
public class ClientAddressDao extends BaseDaoImpl<ClientAddress> {
  /**
   * Constructor
   * 
   * @param sessionFactory The sessionFactory
   */
  @Inject
  public ClientAddressDao(@CmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }
}
