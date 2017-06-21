package gov.ca.cwds.data.cms;

import org.hibernate.SessionFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.CrudsDaoImpl;
import gov.ca.cwds.data.persistence.cms.Assignment;
import gov.ca.cwds.inject.CmsSessionFactory;

/**
 * DAO for {@link Assignment}.
 * 
 * @author CWDS API Team
 */
public class AssignmentDao extends CrudsDaoImpl<Assignment> {

  /**
   * Constructor
   * 
   * @param sessionFactory The sessionFactory
   */
  @Inject
  public AssignmentDao(@CmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }
}
