package gov.ca.cwds.data.ns;

import org.hibernate.SessionFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.CrudsDaoImpl;
import gov.ca.cwds.data.persistence.ns.PersonLanguage;
import gov.ca.cwds.inject.NsSessionFactory;

/**
 * Person Language DAO
 * 
 * @author CWDS API Team
 */
public class PersonLanguageDao extends CrudsDaoImpl<PersonLanguage> {

  /**
   * Constructor
   * 
   * @param sessionFactory The session factory
   */
  @Inject
  public PersonLanguageDao(@NsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

}
