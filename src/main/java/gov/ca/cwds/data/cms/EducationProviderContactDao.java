package gov.ca.cwds.data.cms;

import org.hibernate.SessionFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.BaseDaoImpl;
import gov.ca.cwds.data.persistence.cms.EducationProviderContact;
import gov.ca.cwds.inject.CmsSessionFactory;

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
  @Inject
  public EducationProviderContactDao(@CmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }
}
