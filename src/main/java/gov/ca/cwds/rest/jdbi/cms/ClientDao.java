package gov.ca.cwds.rest.jdbi.cms;

import org.hibernate.SessionFactory;

import com.google.inject.Inject;

import gov.ca.cwds.inject.CmsSessionFactory;
import gov.ca.cwds.rest.api.persistence.cms.Client;
import gov.ca.cwds.rest.jdbi.BaseDaoImpl;

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
