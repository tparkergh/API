package gov.ca.cwds.data.cms;

import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import com.google.inject.Inject;

import gov.ca.cwds.data.CrudsDaoImpl;
import gov.ca.cwds.data.persistence.cms.Assignment;
import gov.ca.cwds.inject.CmsSessionFactory;

/**
 * DAO for {@link Assignment}.
 * 
 * @author CWDS API Team
 */
public class AssignmentDao extends CrudsDaoImpl<Assignment> {

  /**
   * Constructor
   * 
   * @param sessionFactory The sessionFactory
   */
  @Inject
  public AssignmentDao(@CmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  /**
   * @param staffPersonId - staffPerson Id
   * @return the caseLoad Id
   */
  public String findCaseId(String staffPersonId) {

    Query query = this.getSessionFactory().getCurrentSession()
        .getNamedQuery("gov.ca.cwds.data.persistence.cms.Assignment.findCaseId")
        .setParameter("fkStaffPerson", staffPersonId);
    List<String> items = query.list();
    String caseLoadId;
    if (items != null && items.size() > 0) {
      caseLoadId = items.get(0);
    } else {
      return null;
    }

    return caseLoadId;
  }

}
