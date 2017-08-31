package gov.ca.cwds.data.cms;

import org.hibernate.SessionFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.CrudsDaoImpl;
import gov.ca.cwds.data.persistence.cms.CaseLoad;
import gov.ca.cwds.inject.CmsSessionFactory;

/**
 * DAO for {@link CaseLoad}.
 * 
 * @author CWDS API Team
 */
public class CaseLoadDao extends CrudsDaoImpl<CaseLoad> {

  /**
   * Constructor
   * 
   * @param sessionFactory The sessionFactory
   */
  @Inject
  public CaseLoadDao(@CmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

}
