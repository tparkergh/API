package gov.ca.cwds.data.cms;

import org.hibernate.SessionFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.CrudsDaoImpl;
import gov.ca.cwds.data.persistence.cms.ChildClient;
import gov.ca.cwds.inject.CmsSessionFactory;

/**
 * DAO for {@link ChildClient}.
 * 
 * @author CWDS API Team
 */
public class ChildClientDao extends CrudsDaoImpl<ChildClient> {

  /**
   * Constructor
   * 
   * @param sessionFactory The sessionFactory
   */
  @Inject
  public ChildClientDao(@CmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

}
