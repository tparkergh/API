package gov.ca.cwds.data.cms;

import org.hibernate.SessionFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.BaseDaoImpl;
import gov.ca.cwds.data.persistence.cms.Client;
import gov.ca.cwds.inject.CmsSessionFactory;

/**
 * Hibernate DAO for DB2 {@link Client}.
 * 
 * @author CWDS API Team
 * @see CmsSessionFactory
 * @see SessionFactory
 */
public class ClientDao extends BaseDaoImpl<Client> {

  /**
   * Constructor
   * 
   * @param sessionFactory The sessionFactory
   */
  @Inject
  public ClientDao(@CmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

}
