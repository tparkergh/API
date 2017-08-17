package gov.ca.cwds.rest.services.referentialintegrity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.ApiHibernateInterceptor;
import gov.ca.cwds.data.ApiReferentialCheck;
import gov.ca.cwds.data.cms.DrmsDocumentDao;
import gov.ca.cwds.data.cms.LawEnforcementDao;
import gov.ca.cwds.data.persistence.cms.Reporter;
import gov.ca.cwds.rest.validation.ReferentialIntegrityException;

/**
 * Verifies that a record refers to a valid referral, lawEnforcement and mandatedReporterDocument.
 * Returns true if all parent foreign keys exist when the transaction commits, otherwise false.
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
 * &#64;JoinColumn(name = "FKREFERL_T", nullable = true, updatable = false, insertable = false)
 * private Referral referral;
 * 
 * &#64;ManyToOne(optional = true)
 * &#64;JoinColumn(name = "FKLAW_ENFT", nullable = true, updatable = false, insertable = false)
 * private LawEnforcement lawEnforcement;
 * 
 * &#64;ManyToOne(optional = true)
 * &#64;JoinColumn(name = "FDBACK_DOC", nullable = true, updatable = false, insertable = false)
 * private DrmsDocument drmsDocument;
 * </pre>
 * 
 * </blockquote>
 * 
 * @author CWDS API Team
 * @see ApiHibernateInterceptor
 */
public class RIReporter implements ApiReferentialCheck<Reporter> {

  /**
   * Default.
   */
  private static final long serialVersionUID = 1L;

  private static final Logger LOGGER = LoggerFactory.getLogger(Reporter.class);

  private transient LawEnforcementDao lawEnforcementDao;
  private transient DrmsDocumentDao drmsDocumentDao;

  /**
   * Constructor
   * 
   * @param referralDao - referralDao
   * @param lawEnforcementDao - lawEnforcementDao
   * @param drmsDocumentDao - drmsDocumentDao
   * 
   */
  @Inject
  public RIReporter(final LawEnforcementDao lawEnforcementDao, DrmsDocumentDao drmsDocumentDao) {
    this.lawEnforcementDao = lawEnforcementDao;
    this.drmsDocumentDao = drmsDocumentDao;
    ApiHibernateInterceptor.addHandler(Reporter.class, reporter -> apply((Reporter) reporter));
  }

  @Override
  public Boolean apply(Reporter t) {
    LOGGER.debug("RI: Reporter");
    if (t.getLawEnforcementId() != null && !t.getLawEnforcementId().isEmpty()
        && lawEnforcementDao.find(t.getLawEnforcementId()) == null) {
      throw new ReferentialIntegrityException(
          "Reporter => LawEnforcement with given Identifier is not present in database");

    } else if (t.getDrmsMandatedRprtrFeedback() != null
        && !t.getDrmsMandatedRprtrFeedback().isEmpty()
        && drmsDocumentDao.find(t.getDrmsMandatedRprtrFeedback()) == null) {
      throw new ReferentialIntegrityException(
          "Reporter => DrmsReporterDocument with given Identifier is not present in database");
    }
    return true;
  }

}
