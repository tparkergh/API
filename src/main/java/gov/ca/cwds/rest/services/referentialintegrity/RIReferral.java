
package gov.ca.cwds.rest.services.referentialintegrity;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.ApiHibernateInterceptor;
import gov.ca.cwds.data.ApiReferentialCheck;
import gov.ca.cwds.data.cms.AddressDao;
import gov.ca.cwds.data.cms.DrmsDocumentDao;
import gov.ca.cwds.data.cms.LongTextDao;
import gov.ca.cwds.data.cms.ReferralDao;
import gov.ca.cwds.data.cms.StaffPersonDao;
import gov.ca.cwds.data.persistence.cms.Referral;
import gov.ca.cwds.rest.validation.ReferentialIntegrityException;

/**
 * Verifies that a referral record refers to a valid referral. Returns true if all parent foreign
 * keys exist when the transaction commits, otherwise false.
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
 * &#64;JoinColumn(name = "FKREFERL_T", nullable = false, updatable = false, insertable = false)
 * private Referral referral;
 * </pre>
 * 
 * </blockquote>
 * 
 * @author CWDS API Team
 * @see ApiHibernateInterceptor
 */
public class RIReferral implements ApiReferentialCheck<Referral> {

  /**
   * Default.
   */
  private static final long serialVersionUID = 1L;

  private static final Logger LOGGER = LoggerFactory.getLogger(RIReferral.class);

  private transient AddressDao addressDao;
  private transient StaffPersonDao staffPersonDao;
  private transient DrmsDocumentDao drmsDocumentDao;
  private transient LongTextDao longTextDao;
  private transient ReferralDao referralDao;

  /**
   * Constructor.
   * 
   * @param addressDao address DAO
   * @param staffPersonDao staffPerson DAO
   * @param drmsDocumentDao drmsDocument DAO
   * @param longTextDao longText DAO
   * @param referralDao referral DAO
   */
  @Inject
  public RIReferral(final AddressDao addressDao, StaffPersonDao staffPersonDao,
      DrmsDocumentDao drmsDocumentDao, LongTextDao longTextDao, ReferralDao referralDao) {
    this.addressDao = addressDao;
    this.staffPersonDao = staffPersonDao;
    this.drmsDocumentDao = drmsDocumentDao;
    this.longTextDao = longTextDao;
    this.referralDao = referralDao;
    ApiHibernateInterceptor.addHandler(Referral.class, c -> apply((Referral) c));
  }

  /**
   * Verifies that a referral record refers to a valid referral. Returns true if all parent foreign
   * keys exist when the transaction commits, otherwise false.
   * 
   * @return true if all parent foreign keys exist
   */
  @Override
  public Boolean apply(Referral t) {
    LOGGER.debug("RI: Referral");
    if (staffPersonDao.find(t.getPrimaryContactStaffPersonId()) == null) {
      throw new ReferentialIntegrityException(
          "Referral => Staff Person with given Identifier is not present in database");
    } else if (StringUtils.isNotBlank(t.getFirstResponseDeterminedByStaffPersonId())
        && staffPersonDao.find(t.getFirstResponseDeterminedByStaffPersonId()) == null) {
      throw new ReferentialIntegrityException(
          "Referral => First Response Determined Staff Person with given Identifier is not present in database");
    } else if (StringUtils.isNotBlank(t.getAllegesAbuseOccurredAtAddressId())
        && addressDao.find(t.getAllegesAbuseOccurredAtAddressId()) == null) {
      throw new ReferentialIntegrityException(
          "Referral => Address with given Identifier is not present in database");
    } else if (StringUtils.isNotBlank(t.getLinkToPrimaryReferralId())
        && referralDao.find(t.getLinkToPrimaryReferralId()) == null) {
      throw new ReferentialIntegrityException(
          "Referral => LinkToPrimaryReferralId with given Identifier is not present in database");
    } else if (StringUtils.isNotBlank(t.getDrmsAllegationDescriptionDoc())
        && drmsDocumentDao.find(t.getDrmsAllegationDescriptionDoc()) == null) {
      throw new ReferentialIntegrityException(
          "Referral => Drms Allegation DescriptionDoc with given Identifier is not present in database");
    } else if (StringUtils.isNotBlank(t.getDrmsErReferralDoc())
        && drmsDocumentDao.find(t.getDrmsErReferralDoc()) == null) {
      throw new ReferentialIntegrityException(
          "Referral => Drms Referral Doc with given Identifier is not present in database");
    } else if (StringUtils.isNotBlank(t.getDrmsInvestigationDoc())
        && drmsDocumentDao.find(t.getDrmsInvestigationDoc()) == null) {
      throw new ReferentialIntegrityException(
          "Referral => Drms Investigation Doc with given Identifier is not present in database");
    } else if (StringUtils.isNotBlank(t.getCurrentLocationOfChildren())
        && longTextDao.find(t.getCurrentLocationOfChildren()) == null) {
      throw new ReferentialIntegrityException(
          "Referral => Current Location Of Children with given Identifier is not present in database");
    } else if (StringUtils.isNotBlank(t.getResponseRationaleText())
        && longTextDao.find(t.getResponseRationaleText()) == null) {
      throw new ReferentialIntegrityException(
          "Referral => Response Rationale Text with given Identifier is not present in database");
    } else if (StringUtils.isNotBlank(t.getScreenerNoteText())
        && longTextDao.find(t.getScreenerNoteText()) == null) {
      throw new ReferentialIntegrityException(
          "Referral => Screener Note Text with given Identifier is not present in database");
    }
    return Boolean.TRUE;
  }

}
