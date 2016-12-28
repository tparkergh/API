package gov.ca.cwds.rest.jdbi.cms;

import org.hibernate.SessionFactory;

import gov.ca.cwds.rest.api.persistence.cms.EducationProviderContact;
import gov.ca.cwds.rest.jdbi.BaseDaoImpl;

/**
 * DAO for {@link EducationProviderContact}.
 * 
 * @author CWDS API Team
 */
public class EducationProviderContactDao extends BaseDaoImpl<EducationProviderContact> {

  /**
   * Constructor
   * 
   * @param sessionFactory The sessionFactory
   */
  public EducationProviderContactDao(SessionFactory sessionFactory) {
    super(sessionFactory);
  }
}
