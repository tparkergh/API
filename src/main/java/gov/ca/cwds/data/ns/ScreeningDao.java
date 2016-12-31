package gov.ca.cwds.data.ns;

import org.hibernate.SessionFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.CrudsDaoImpl;
import gov.ca.cwds.data.persistence.ns.Screening;
import gov.ca.cwds.inject.NsSessionFactory;

/**
 * Screening DAO
 * 
 * @author CWDS API Team
 */
public class ScreeningDao extends CrudsDaoImpl<Screening> {

  /**
   * Constructor
   * 
   * @param sessionFactory The session factory
   */
  @Inject
  public ScreeningDao(@NsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

}
