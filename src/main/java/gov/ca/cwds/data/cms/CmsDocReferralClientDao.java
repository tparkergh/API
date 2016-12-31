package gov.ca.cwds.data.cms;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.CrudsDaoImpl;
import gov.ca.cwds.data.persistence.cms.CmsDocReferralClient;
import gov.ca.cwds.inject.CmsSessionFactory;

/**
 * Data Access Object (DAO) for legacy CMS documents and client referral records.
 * 
 * @author CWDS API Team
 */
public class CmsDocReferralClientDao extends CrudsDaoImpl<CmsDocReferralClient> {

  /**
   * Constructor
   * 
   * @param sessionFactory The sessionFactory
   */
  @Inject
  public CmsDocReferralClientDao(@CmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  /**
   * Result set sort order is determined by {@link CmsDocReferralClient}.
   * 
   * @param docHandle "document handle", the primary key of CMS legacy document
   * @return ordered list of referral/client document records
   */
  @SuppressWarnings("unchecked")
  public List<CmsDocReferralClient> listDocReferralClient(String docHandle) {
    Query query = this.getSessionFactory().getCurrentSession().getNamedQuery("DocReferalClient")
        .setString("docHandle", docHandle);
    return query.list();
  }

}
