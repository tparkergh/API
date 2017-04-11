package gov.ca.cwds.data.cms;

import org.hibernate.SessionFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.CrudsDaoImpl;
import gov.ca.cwds.data.persistence.cms.LongText;
import gov.ca.cwds.inject.CmsSessionFactory;

/**
 * DAO for {@link LongText}.
 * 
 * @author CWDS API Team
 */
public class LongTextDao extends CrudsDaoImpl<LongText> {

  /**
   * Constructor
   * 
   * @param sessionFactory the sessionFactory
   */
  @Inject
  public LongTextDao(@CmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }
}
