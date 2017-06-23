package gov.ca.cwds.data.cms;

import org.hibernate.SessionFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.CrudsDaoImpl;
import gov.ca.cwds.data.persistence.cms.CaseAssignment;
import gov.ca.cwds.inject.CmsSessionFactory;

/**
 * DAO for {@link CaseAssignment}.
 * 
 * @author CWDS API Team
 */
public class CaseAssignmentDao extends CrudsDaoImpl<CaseAssignment> {

  /**
   * Constructor
   * 
   * @param sessionFactory The sessionFactory
   */
  @Inject
  public CaseAssignmentDao(@CmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

}
