package gov.ca.cwds.data.ns;

import org.hibernate.SessionFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.CrudsDaoImpl;
import gov.ca.cwds.data.persistence.ns.Language;
import gov.ca.cwds.inject.NsSessionFactory;

/**
 * Language DAO
 * 
 * @author CWDS API Team
 */
public class LanguageDao extends CrudsDaoImpl<Language> {

  /**
   * Constructor
   * 
   * @param sessionFactory The session factory
   */
  @Inject
  public LanguageDao(@NsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

}
