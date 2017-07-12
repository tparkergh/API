package gov.ca.cwds.data.cms;

import gov.ca.cwds.data.BaseDaoImpl;
import gov.ca.cwds.data.persistence.cms.ClientRelationship;
import gov.ca.cwds.inject.CmsSessionFactory;

import org.hibernate.SessionFactory;

import com.google.inject.Inject;

/**
 * DAO for {@link ClientRelationship}.
 * 
 * @author CWDS API Team
 */
public class ClientRelationshipDao extends BaseDaoImpl<ClientRelationship> {

  /**
   * Constructor
   * 
   * @param sessionFactory The sessionFactory
   */
  @Inject
  public ClientRelationshipDao(@CmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }
}
