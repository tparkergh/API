package gov.ca.cwds.data.cms;

import org.hibernate.SessionFactory;

import gov.ca.cwds.data.BaseDaoImpl;
import gov.ca.cwds.data.persistence.cms.EducationProviderContact;

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
