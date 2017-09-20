package gov.ca.cwds.data.dao.investigation;

import org.hibernate.SessionFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.CrudsDaoImpl;
import gov.ca.cwds.data.persistence.cms.Referral;
import gov.ca.cwds.inject.CmsSessionFactory;
import gov.ca.cwds.rest.api.domain.investigation.Investigation;

/**
 * DAO for {@link Investigation}.
 * 
 * @author CWDS API Team
 */
public class InvestigationDao extends CrudsDaoImpl<Referral> {

  /**
   * Constructor
   * 
   * @param sessionFactory The session factory
   */
  @Inject
  public InvestigationDao(@CmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

}
