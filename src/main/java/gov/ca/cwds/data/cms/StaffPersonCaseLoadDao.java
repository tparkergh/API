package gov.ca.cwds.data.cms;

import org.hibernate.SessionFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.CrudsDaoImpl;
import gov.ca.cwds.data.persistence.cms.StaffPersonCaseLoad;
import gov.ca.cwds.inject.CmsSessionFactory;

/**
 * Hibernate DAO for DB2 {@link StaffPersonCaseLoad}.
 * 
 * @author CWDS API Team
 */
public class StaffPersonCaseLoadDao extends CrudsDaoImpl<StaffPersonCaseLoad> {
  /**
   * Constructor
   * 
   * @param sessionFactory The session factory
   */
  @Inject
  public StaffPersonCaseLoadDao(@CmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

}
