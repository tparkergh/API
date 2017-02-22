package gov.ca.cwds.data.ns;

import org.hibernate.SessionFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.CrudsDaoImpl;
import gov.ca.cwds.data.persistence.ns.Ethnicity;
import gov.ca.cwds.inject.NsSessionFactory;

/**
 * Ethnicity DAO
 *
 * @author CWDS API Team
 */
public class EthnicityDao extends CrudsDaoImpl<Ethnicity> {

  /**
   * Constructor
   * 
   * @param sessionFactory The session factory
   */
  @Inject
  public EthnicityDao(@NsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }


}
