package gov.ca.cwds.rest.services.referentialintegrity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.ApiHibernateInterceptor;
import gov.ca.cwds.data.ApiReferentialCheck;
import gov.ca.cwds.data.cms.ClientDao;
import gov.ca.cwds.data.persistence.cms.ChildClient;
import gov.ca.cwds.rest.validation.ReferentialIntegrityException;

/**
 * Verifies that a child client record refers to a valid client. Returns true if all parent foreign
 * keys exist when the transaction commits, otherwise false.
 * 
 * <p>
 * Validate any other constraints or business rules here before committing a transaction to the
 * database.
 * </p>
 * 
 * <p>
 * Enforce foreign key constraints using "normal" Hibernate mechanisms, such as this typical FK:
 * </p>
 * <blockquote>
 * 
 * <pre>
 * &#064;ManyToOne(optional = false)
 * &#064;JoinColumn(name = &quot;FKCLIENT_T&quot;, nullable = false, updatable = false, insertable = false)
 * private Client client;
 * </pre>
 * 
 * </blockquote>
 * 
 * @author CWDS API Team
 * @see ApiHibernateInterceptor
 */
public class RIChildClient implements ApiReferentialCheck<ChildClient> {

  /**
   * Default.
   */
  private static final long serialVersionUID = 1L;

  private static final Logger LOGGER = LoggerFactory.getLogger(ChildClient.class);

  private transient ClientDao clientDao;

  /**
   * Constructor.
   * 
   * @param clientDao client DAO
   */
  @Inject
  public RIChildClient(final ClientDao clientDao) {
    this.clientDao = clientDao;
    ApiHibernateInterceptor.addHandler(ChildClient.class,
        childClient -> apply((ChildClient) childClient));
  }

  /**
   * Verifies that a child client record refers to a valid client. Returns true if all parent
   * foreign keys exist when the transaction commits, otherwise false.
   * 
   * @return true if all parent foreign keys exist
   */
  @Override
  public Boolean apply(ChildClient t) {
    LOGGER.debug("RI: ChildClient");
    if (clientDao.find(t.getVictimClientId()) == null) {
      throw new ReferentialIntegrityException(
          "ChildClient => Victim Client with given Identifier is not present in database");
    }
    return true;
  }

}
