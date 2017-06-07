package gov.ca.cwds.data.cms;

import gov.ca.cwds.data.CrudsDaoImpl;
import gov.ca.cwds.data.persistence.cms.Allegation;
import gov.ca.cwds.inject.CmsSessionFactory;

import org.hibernate.Query;
import org.hibernate.SessionFactory;

import com.google.inject.Inject;

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

  @SuppressWarnings("unchecked")
  public Allegation[] findByReferralId(String referralId) {
    Query query =
        this.getSessionFactory().getCurrentSession()
            .getNamedQuery("gov.ca.cwds.data.persistence.cms.Allegation.findByReferral")
            .setString("referralId", referralId);
    return (Allegation[]) query.list().toArray(new Allegation[0]);

  }

}
