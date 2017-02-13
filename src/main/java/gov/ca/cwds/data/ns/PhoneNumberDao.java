package gov.ca.cwds.data.ns;

import org.hibernate.SessionFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.CrudsDaoImpl;
import gov.ca.cwds.data.persistence.ns.PhoneNumber;
import gov.ca.cwds.inject.NsSessionFactory;

/**
 * PhoneNumber DAO
 * 
 * @author CWDS API Team
 */
public class PhoneNumberDao extends CrudsDaoImpl<PhoneNumber> {

  /**
   * Constructor
   * 
   * @param sessionFactory The session factory
   */
  @Inject
  public PhoneNumberDao(@NsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

}
