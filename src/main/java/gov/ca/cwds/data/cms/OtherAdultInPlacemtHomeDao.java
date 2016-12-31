package gov.ca.cwds.data.cms;

import org.hibernate.SessionFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.BaseDaoImpl;
import gov.ca.cwds.data.persistence.cms.OtherAdultInPlacemtHome;
import gov.ca.cwds.inject.CmsSessionFactory;

/**
 * DAO for {@link OtherAdultInPlacemtHome}.
 * 
 * @author CWDS API Team
 */
public class OtherAdultInPlacemtHomeDao extends BaseDaoImpl<OtherAdultInPlacemtHome> {

  /**
   * Constructor
   * 
   * @param sessionFactory The sessionFactory
   */
  @Inject
  public OtherAdultInPlacemtHomeDao(@CmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }
}
