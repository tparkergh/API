package gov.ca.cwds.data.dao.investigation;

import org.hibernate.SessionFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.CrudsDaoImpl;
import gov.ca.cwds.data.persistence.cms.Client;
import gov.ca.cwds.inject.CmsSessionFactory;
import gov.ca.cwds.rest.api.domain.investigation.Person;

/**
 * DAO for {@link Person}.
 * 
 * @author CWDS API Team
 */
public class PeopleDao extends CrudsDaoImpl<Client> {
  /**
   * Constructor
   * 
   * @param sessionFactory The session factory
   */
  @Inject
  public PeopleDao(@CmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }
}
