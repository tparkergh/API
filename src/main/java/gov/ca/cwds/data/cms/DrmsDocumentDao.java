package gov.ca.cwds.data.cms;

import org.hibernate.SessionFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.CrudsDaoImpl;
import gov.ca.cwds.data.persistence.cms.DrmsDocument;
import gov.ca.cwds.inject.CmsSessionFactory;

/**
 * DAO for {@link DrmsDocument}.
 * 
 * @author CWDS API Team
 */
public class DrmsDocumentDao extends CrudsDaoImpl<DrmsDocument> {

  /**
   * Constructor
   * 
   * @param sessionFactory the sessionFactory
   */
  @Inject
  public DrmsDocumentDao(@CmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }
}
