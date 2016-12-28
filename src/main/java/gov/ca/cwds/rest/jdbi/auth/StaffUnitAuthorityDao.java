package gov.ca.cwds.rest.jdbi.auth;

import org.hibernate.Query;
import org.hibernate.SessionFactory;

import com.google.inject.Inject;

import gov.ca.cwds.inject.CmsSessionFactory;
import gov.ca.cwds.rest.api.persistence.auth.StaffUnitAuthority;
import gov.ca.cwds.rest.jdbi.CrudsDaoImpl;

/**
 * DAO for {@link StaffUnitAuthority}.
 * 
 * @author CWDS API Team
 */
public class StaffUnitAuthorityDao extends CrudsDaoImpl<StaffUnitAuthority> {

  /**
   * Constructor
   * 
   * @param sessionFactory The session factory
   */
  @Inject
  public StaffUnitAuthorityDao(@CmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  public StaffUnitAuthority[] findByStaff(String staffId) {
    Query query = this.getSessionFactory().getCurrentSession()
        .getNamedQuery("gov.ca.cwds.rest.api.persistence.auth.StaffUnitAuthority.findByStaff")
        .setString("staffId", staffId);
    return (StaffUnitAuthority[]) query.list().toArray(new StaffUnitAuthority[0]);
  }

}
