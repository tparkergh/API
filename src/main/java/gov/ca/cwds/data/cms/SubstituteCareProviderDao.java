package gov.ca.cwds.data.cms;

import org.hibernate.SessionFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.BaseDaoImpl;
import gov.ca.cwds.data.persistence.cms.SubstituteCareProvider;
import gov.ca.cwds.inject.CmsSessionFactory;

/**
 * DAO for {@link SubstituteCareProvider}.
 * 
 * @author CWDS API Team
 */
public class SubstituteCareProviderDao extends BaseDaoImpl<SubstituteCareProvider> {

  /**
   * Constructor
   * 
   * @param sessionFactory The sessionFactory
   */
  @Inject
  public SubstituteCareProviderDao(@CmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }
}
