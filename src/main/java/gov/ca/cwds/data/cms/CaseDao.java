package gov.ca.cwds.data.cms;

import org.hibernate.SessionFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.CrudsDaoImpl;
import gov.ca.cwds.data.persistence.cms.Case;
import gov.ca.cwds.inject.CmsSessionFactory;

/**
 * DAO for {@link Case}.
 * 
 * @author CWDS API Team
 */
public class CaseDao extends CrudsDaoImpl<Case> {

  /**
   * Constructor
   * 
   * @param sessionFactory The sessionFactory
   */
  @Inject
  public CaseDao(@CmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

}
