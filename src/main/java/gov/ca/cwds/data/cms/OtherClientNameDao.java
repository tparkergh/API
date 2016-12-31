package gov.ca.cwds.data.cms;

import org.hibernate.SessionFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.BaseDaoImpl;
import gov.ca.cwds.data.persistence.cms.OtherClientName;
import gov.ca.cwds.inject.CmsSessionFactory;

/**
 * DAO for {@link OtherClientName}.
 * 
 * @author CWDS API Team
 */
public class OtherClientNameDao extends BaseDaoImpl<OtherClientName> {

  /**
   * Constructor
   * 
   * @param sessionFactory The session factory
   */
  @Inject
  public OtherClientNameDao(@CmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

}
