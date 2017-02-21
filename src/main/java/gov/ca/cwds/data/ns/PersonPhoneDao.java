package gov.ca.cwds.data.ns;

import org.hibernate.SessionFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.BaseDaoImpl;
import gov.ca.cwds.data.persistence.ns.PersonPhone;
import gov.ca.cwds.inject.NsSessionFactory;

/**
 * PersonPhone DAO
 * 
 * @author CWDS API Team
 */
public class PersonPhoneDao extends BaseDaoImpl<PersonPhone> {

  /**
   * Constructor
   * 
   * @param sessionFactory The session factory
   */
  @Inject
  public PersonPhoneDao(@NsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

}

