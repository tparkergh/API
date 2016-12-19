package gov.ca.cwds.rest.jdbi.cms;

import org.hibernate.SessionFactory;

import com.google.inject.Inject;

import gov.ca.cwds.inject.CmsSessionFactory;
import gov.ca.cwds.rest.api.persistence.cms.OtherAdultInPlacemtHome;
import gov.ca.cwds.rest.jdbi.BaseDaoImpl;

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
