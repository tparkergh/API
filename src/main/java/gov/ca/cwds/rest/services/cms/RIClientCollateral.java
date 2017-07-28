package gov.ca.cwds.rest.services.cms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.ApiHibernateInterceptor;
import gov.ca.cwds.data.ApiReferentialCheck;
import gov.ca.cwds.data.cms.ClientDao;
import gov.ca.cwds.data.persistence.cms.ClientCollateral;
import gov.ca.cwds.rest.api.ApiException;

/**
 * Verifies that a client collateral record refers to a valid client. Returns true if all parent
 * foreign keys exist when the transaction commits, otherwise false.
 * 
 * <p>
 * Validate any other "last ditch" constraints or business rules here before committing a
 * transaction to the database.
 * </p>
 * 
 * <p>
 * Enforce foreign key constraints using "normal" Hibernate mechanisms, such as this typical FK:
 * </p>
 * <blockquote>
 * 
 * <pre>
 * &#64;ManyToOne(optional = false)
 * &#64;JoinColumn(name = "FKCLIENT_T", nullable = false, updatable = false, insertable = false)
 * private Client client;
 * </pre>
 * 
 * </blockquote>
 * 
 * @author CWDS API Team
 * @see ApiHibernateInterceptor
 */
public class RIClientCollateral implements ApiReferentialCheck<ClientCollateral> {

  /**
   * Default.
   */
  private static final long serialVersionUID = 1L;

  private static final Logger LOGGER = LoggerFactory.getLogger(ClientCollateral.class);

  private ClientDao clientDao;

  /**
   * Constructor.
   * 
   * @param clientDao client DAO
   */
  @Inject
  public RIClientCollateral(final ClientDao clientDao) {
    this.clientDao = clientDao;
    ApiHibernateInterceptor.addHandler(ClientCollateral.class, c -> {
      if (!apply((ClientCollateral) c)) {
        throw new ApiException("RI ERROR: ClientCollateral => Client");
      }
    });
  }

  /**
   * Verifies that a client collateral record refers to a valid client. Returns true if all parent
   * foreign keys exist when the transaction commits, otherwise false.
   * 
   * @return true if all parent foreign keys exist
   */
  @Override
  public Boolean apply(ClientCollateral t) {
    LOGGER.debug("RI: ClientCollateral");
    return clientDao.find(t.getClientId()) != null;
  }

}
