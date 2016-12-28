package gov.ca.cwds.rest.jdbi.auth;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;

import com.google.inject.Inject;

import gov.ca.cwds.inject.CmsSessionFactory;
import gov.ca.cwds.rest.api.persistence.auth.UserId;
import gov.ca.cwds.rest.jdbi.CrudsDaoImpl;

/**
 * DAO for {@link UserId}.
 * 
 * @author CWDS API Team
 */
public class UserIdDao extends CrudsDaoImpl<UserId> {

  /**
   * Constructor
   * 
   * @param sessionFactory The session factory
   */
  @Inject
  public UserIdDao(@CmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  @SuppressWarnings("unchecked")
  public List<UserId> listUserFromLogonId(String logonId) {
    Query query = this.getSessionFactory().getCurrentSession()
        .getNamedQuery("gov.ca.cwds.rest.api.persistence.auth.UserId.findUserFromLogonId")
        .setString("logonId", logonId);
    return query.list();
  }

}
