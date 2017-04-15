package gov.ca.cwds.data.cms;

import org.hibernate.SessionFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.CrudsDaoImpl;
import gov.ca.cwds.data.persistence.cms.AllegationPerpetratorHistory;
import gov.ca.cwds.inject.CmsSessionFactory;

/**
 * DAO for {@link AllegationPerpetratorHistory}.
 * 
 * @author CWDS API Team
 */
public class AllegationPerpetratorHistoryDao extends CrudsDaoImpl<AllegationPerpetratorHistory> {

  /**
   * Constructor
   * 
   * @param sessionFactory The sessionFactory
   */
  @Inject
  public AllegationPerpetratorHistoryDao(@CmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

}
