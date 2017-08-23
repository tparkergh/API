package gov.ca.cwds.rest.services.referentialintegrity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.ApiHibernateInterceptor;
import gov.ca.cwds.data.ApiReferentialCheck;
import gov.ca.cwds.data.cms.ClientDao;
import gov.ca.cwds.data.cms.ReferralDao;
import gov.ca.cwds.data.persistence.cms.ClientCollateral;
import gov.ca.cwds.data.persistence.cms.ReferralClient;
import gov.ca.cwds.rest.validation.ReferentialIntegrityException;

/**
 * Verifies that a Referral Client record refers to a valid referral and client. Returns true if all
 * parent foreign keys exist when the transaction commits, otherwise false.
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
 * &#064;ManyToOne(optional = false)
 * &#064;JoinColumn(name = &quot;FKCLIENT_T&quot;, nullable = false, updatable = false, insertable = false)
 * private Client client;
 * &#064;ManyToOne(optional = false)
 * &#064;JoinColumn(name = &quot;FKREFERL_T&quot;, nullable = false, updatable = false, insertable = false)
 * private Referral referral;
 * </pre>
 * 
 * </blockquote>
 * 
 * @author CWDS API Team
 * @see ApiHibernateInterceptor
 */
public class RIReferralClient implements ApiReferentialCheck<ReferralClient> {

  /**
   * Default.
   */
  private static final long serialVersionUID = 1L;

  private static final Logger LOGGER = LoggerFactory.getLogger(ClientCollateral.class);

  private transient ClientDao clientDao;
  private transient ReferralDao referralDao;

  /**
   * Constructor.
   * 
   * @param clientDao client DAO
   * @param referralDao referral DAO
   */
  @Inject
  public RIReferralClient(final ClientDao clientDao, ReferralDao referralDao) {
    this.clientDao = clientDao;
    this.referralDao = referralDao;
    ApiHibernateInterceptor.addHandler(ReferralClient.class, c -> apply((ReferralClient) c));

  }

  /**
   * Verifies that a referral client record refers to a valid client and referral. Returns true if
   * all parent foreign keys exist when the transaction commits, otherwise false.
   * 
   * @return true if all parent foreign keys exist
   */
  @Override
  public Boolean apply(ReferralClient t) {
    LOGGER.debug("RI: ReferralClient");
    if (clientDao.find(t.getClientId()) == null) {
      throw new ReferentialIntegrityException(
          "ReferralClient => Client with given Identifier is not present in database");

    } else if (referralDao.find(t.getReferralId()) == null) {
      throw new ReferentialIntegrityException(
          "ReferralClient => Referral with given Identifier is not present in database");

    }
    return true;
  }

}
