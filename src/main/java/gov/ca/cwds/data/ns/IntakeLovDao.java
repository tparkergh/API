package gov.ca.cwds.data.ns;

import org.hibernate.SessionFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.CrudsDaoImpl;
import gov.ca.cwds.data.persistence.ns.IntakeLov;
import gov.ca.cwds.inject.NsSessionFactory;

/**
 * DAO for Intake LOV codes.
 * 
 * @author CWDS API Team
 */
public class IntakeLovDao extends CrudsDaoImpl<IntakeLov> {

  /**
   * Constructor.
   * 
   * @param sessionFactory The session factory
   */
  @Inject
  public IntakeLovDao(@NsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

}
