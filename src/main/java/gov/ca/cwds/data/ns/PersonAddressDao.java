package gov.ca.cwds.data.ns;

import org.hibernate.SessionFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.BaseDaoImpl;
import gov.ca.cwds.data.persistence.ns.PersonAddress;
import gov.ca.cwds.inject.NsSessionFactory;

/**
 * PersonAddress DAO
 * 
 * @author CWDS API Team
 */
public class PersonAddressDao extends BaseDaoImpl<PersonAddress> {

  /**
   * Constructor
   * 
   * @param sessionFactory The session factory
   */
  @Inject
  public PersonAddressDao(@NsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

}
