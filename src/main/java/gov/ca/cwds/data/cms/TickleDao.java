package gov.ca.cwds.data.cms;

import org.hibernate.SessionFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.CrudsDaoImpl;
import gov.ca.cwds.data.persistence.cms.Tickle;
import gov.ca.cwds.inject.CmsSessionFactory;

/**
 * DAO for {@link Tickle}.
 * 
 * @author CWDS API Team
 */
public class TickleDao extends CrudsDaoImpl<Tickle> {

  /**
   * Constructor
   * 
   * @param sessionFactory the sessionFactory
   */
  @Inject
  public TickleDao(@CmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

}
