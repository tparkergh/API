package gov.ca.cwds.data.cms;

import org.hibernate.SessionFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.CrudsDaoImpl;
import gov.ca.cwds.data.persistence.cms.ClientScpEthnicity;
import gov.ca.cwds.inject.CmsSessionFactory;

/**
 * DAO for {@link ClientScpEthnicity}.
 * 
 * @author CWDS API Team
 */
public class ClientScpEthnicityDao extends CrudsDaoImpl<ClientScpEthnicity> {

  /**
   * Constructor
   * 
   * @param sessionFactory the sessionFactory
   */
  @Inject
  public ClientScpEthnicityDao(@CmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

}
