package gov.ca.cwds.data.cms;

import org.hibernate.SessionFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.CrudsDaoImpl;
import gov.ca.cwds.data.persistence.cms.ReferralAssignment;
import gov.ca.cwds.inject.CmsSessionFactory;

/**
 * DAO for {@link ReferralAssignment}.
 * 
 * @author CWDS API Team
 */
public class ReferralAssignmentDao extends CrudsDaoImpl<ReferralAssignment> {

  /**
   * Constructor
   * 
   * @param sessionFactory The sessionFactory
   */
  @Inject
  public ReferralAssignmentDao(@CmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

}
