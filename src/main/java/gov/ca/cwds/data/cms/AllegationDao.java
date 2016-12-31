package gov.ca.cwds.data.cms;

import org.hibernate.SessionFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.CrudsDaoImpl;
import gov.ca.cwds.data.persistence.cms.Allegation;
import gov.ca.cwds.inject.CmsSessionFactory;

/**
 * DAO for {@link Allegation}.
 * 
 * @author CWDS API Team
 */
public class AllegationDao extends CrudsDaoImpl<Allegation> {

  /**
   * Constructor
   * 
   * @param sessionFactory The sessionFactory
   */
  @Inject
  public AllegationDao(@CmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }
}
