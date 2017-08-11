package gov.ca.cwds.rest.services.referentialintegrity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.ApiHibernateInterceptor;
import gov.ca.cwds.data.ApiReferentialCheck;
import gov.ca.cwds.data.cms.ReferralDao;
import gov.ca.cwds.data.persistence.cms.Assignment;
import gov.ca.cwds.rest.validation.ReferentialIntegrityException;

/**
 * Verifies that a record refers to a valid referral. Returns true if all parent foreign keys exist
 * when the transaction commits, otherwise false.
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
 * &#64;OneToOne(optional = false)
 * &#64;JoinColumn(name = "ESTBLSH_ID", nullable = false, updatable = false, insertable = false)
 * private Referral referral;
 * </pre>
 * 
 * </blockquote>
 * 
 * @author CWDS API Team
 * @see ApiHibernateInterceptor
 */
public class RIAssignment implements ApiReferentialCheck<Assignment> {

  private static final long serialVersionUID = 1L;

  private static final Logger LOGGER = LoggerFactory.getLogger(Assignment.class);

  private transient ReferralDao referralDao;

  /**
   * Constructor
   * 
   * @param referralDao - referralDao
   * 
   */
  @Inject
  public RIAssignment(final ReferralDao referralDao) {
    this.referralDao = referralDao;
    ApiHibernateInterceptor.addHandler(Assignment.class,
        assignment -> apply((Assignment) assignment));
  }

  /**
   * Verifies that a assignment record refers to a valid referral. Returns true if all parent
   * foreign keys exist when the transaction commits, otherwise false.
   * 
   * @return true if all parent foreign keys exist
   */
  @Override
  public Boolean apply(Assignment t) {
    LOGGER.debug("RI: Assignment");
    if ("R".equals(t.getEstablishedForCode())
        && referralDao.find(t.getEstablishedForId()) == null) {
      throw new ReferentialIntegrityException(
          "Assignment => Referral with given Identifier is not present in database");

    }
    return true;
  }
}
