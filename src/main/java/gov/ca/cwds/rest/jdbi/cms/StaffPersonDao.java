package gov.ca.cwds.rest.jdbi.cms;

import org.hibernate.SessionFactory;

import com.google.inject.Inject;

import gov.ca.cwds.inject.CmsSessionFactory;
import gov.ca.cwds.rest.api.persistence.cms.StaffPerson;
import gov.ca.cwds.rest.jdbi.CrudsDaoImpl;

/**
 * Hibernate DAO for DB2 {@link StaffPerson}.
 * 
 * @author CWDS API Team
 */
public class StaffPersonDao extends CrudsDaoImpl<StaffPerson> {

  /**
   * Constructor
   * 
   * @param sessionFactory The session factory
   */
  @Inject
  public StaffPersonDao(@CmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

}
