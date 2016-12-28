package gov.ca.cwds.rest.jdbi.cms;

import org.hibernate.SessionFactory;

import com.google.inject.Inject;

import gov.ca.cwds.inject.CmsSessionFactory;
import gov.ca.cwds.rest.api.persistence.cms.SubstituteCareProvider;
import gov.ca.cwds.rest.jdbi.BaseDaoImpl;

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
