package gov.ca.cwds.data.cms;

import org.hibernate.SessionFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.BaseDaoImpl;
import gov.ca.cwds.data.persistence.cms.OtherChildInPlacemtHome;
import gov.ca.cwds.inject.CmsSessionFactory;

/**
 * DAO for {@link OtherChildInPlacemtHome}.
 * 
 * @author CWDS API Team
 */
public class OtherChildInPlacemtHomeDao extends BaseDaoImpl<OtherChildInPlacemtHome> {

  /**
   * Constructor
   * 
   * @param sessionFactory The sessionFactory
   */
  @Inject
  public OtherChildInPlacemtHomeDao(@CmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }
}
