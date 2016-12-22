package gov.ca.cwds.rest.jdbi.auth;

import gov.ca.cwds.inject.CmsSessionFactory;
import gov.ca.cwds.rest.api.persistence.auth.UserAuthorization;
import gov.ca.cwds.rest.jdbi.CrudsDaoImpl;

import org.hibernate.SessionFactory;

import com.google.inject.Inject;

/**
 * 
 * 
 * @author CWDS API Team
 */
public class UserAuthorizationDao extends CrudsDaoImpl<UserAuthorization> {

  /**
   * Constructor
   * 
   * @param sessionFactory The session factory
   */
  @Inject
  public UserAuthorizationDao(@CmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

}
