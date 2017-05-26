package gov.ca.cwds.rest.services.cms;

import java.io.Serializable;
import java.util.Map;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.Dao;
import gov.ca.cwds.data.cms.ReferralClientDao;
import gov.ca.cwds.data.cms.StaffPersonDao;
import gov.ca.cwds.data.persistence.cms.ReferralClient;
import gov.ca.cwds.data.persistence.cms.StaffPerson;
import gov.ca.cwds.data.rules.TriggerTablesDao;
import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.business.rules.LACountyTrigger;
import gov.ca.cwds.rest.business.rules.NonLACountyTriggers;
import gov.ca.cwds.rest.services.CrudsService;
import gov.ca.cwds.rest.services.ServiceException;
import gov.ca.cwds.rest.util.ServiceUtils;

/**
 * Business layer object to work on {@link ReferralClient}
 * 
 * @author CWDS API Team
 */
public class ReferralClientService implements CrudsService {

  private static final Logger LOGGER = LoggerFactory.getLogger(ReferralClientService.class);

  private static final String KEY_REFERRAL_ID = "referralId";
  private static final String KEY_CLIENT_ID = "clientId";

  private ReferralClientDao referralClientDao;
  private NonLACountyTriggers nonLaTriggers;
  private LACountyTrigger laCountyTrigger;
  private TriggerTablesDao triggerTablesDao;
  private StaffPersonDao staffpersonDao;

  /**
   * Constructor
   * 
   * @param referralClientDao The {@link Dao} handling
   *        {@link gov.ca.cwds.data.persistence.cms.ReferralClient} objects.
   * @param nonLaTriggers The {@link Dao} handling
   *        {@link gov.ca.cwds.rest.business.rules.NonLACountyTriggers} objects
   * @param laCountyTrigger The {@link Dao} handling
   *        {@link gov.ca.cwds.rest.business.rules.LACountyTrigger} objects
   * @param triggerTablesDao The {@link Dao} handling
   *        {@link gov.ca.cwds.data.rules.TriggerTablesDao} objects
   * @param staffpersonDao The {@link Dao} handling
   *        {@link gov.ca.cwds.data.persistence.cms.StaffPerson} objects
   */
  @Inject
  public ReferralClientService(ReferralClientDao referralClientDao,
      NonLACountyTriggers nonLaTriggers, LACountyTrigger laCountyTrigger,
      TriggerTablesDao triggerTablesDao, StaffPersonDao staffpersonDao) {
    this.referralClientDao = referralClientDao;
    this.nonLaTriggers = nonLaTriggers;
    this.laCountyTrigger = laCountyTrigger;
    this.triggerTablesDao = triggerTablesDao;
    this.staffpersonDao = staffpersonDao;
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#find(java.io.Serializable)
   */
  @Override
  public gov.ca.cwds.rest.api.domain.cms.ReferralClient find(Serializable primaryKey) {
    ReferralClient.PrimaryKey primaryKeyObject = extractPrimaryKey(primaryKey);
    gov.ca.cwds.data.persistence.cms.ReferralClient persistedReferralClient =
        referralClientDao.find(primaryKeyObject);
    if (persistedReferralClient != null) {
      return new gov.ca.cwds.rest.api.domain.cms.ReferralClient(persistedReferralClient);
    }
    return null;
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#delete(java.io.Serializable)
   */
  @Override
  public gov.ca.cwds.rest.api.domain.cms.ReferralClient delete(Serializable primaryKey) {
    ReferralClient.PrimaryKey primaryKeyObject = extractPrimaryKey(primaryKey);
    gov.ca.cwds.data.persistence.cms.ReferralClient persistedReferralClient =
        referralClientDao.delete(primaryKeyObject);

    if (persistedReferralClient != null) {
      return new gov.ca.cwds.rest.api.domain.cms.ReferralClient(persistedReferralClient);
    }

    return null;
  }

  @Override
  public gov.ca.cwds.rest.api.domain.cms.ReferralClient create(Request request) {
    assert request instanceof gov.ca.cwds.rest.api.domain.cms.ReferralClient;

    gov.ca.cwds.rest.api.domain.cms.ReferralClient referralClient =
        (gov.ca.cwds.rest.api.domain.cms.ReferralClient) request;

    try {
      // TODO : refactor to actually determine who is updating. 'q1p' for now - #136737071 - Tech
      // Debt: Legacy Service classes must use Staff ID for last update ID value

      ReferralClient managed = new ReferralClient(referralClient, "q1p");
      managed = referralClientDao.create(managed);
      // checking the staffPerson county code
      StaffPerson staffperson = staffpersonDao.find(managed.getLastUpdatedId());
      if (staffperson != null
          && (triggerTablesDao.getLaCountySpecificCode().equals(staffperson.getCountyCode()))) {
        laCountyTrigger.createCountyTrigger(managed);
      } else {
        nonLaTriggers.createAndUpdateReferralClientCoutyOwnership(managed);
      }
      return new gov.ca.cwds.rest.api.domain.cms.ReferralClient(managed);
    } catch (EntityExistsException e) {
      LOGGER.info("Referral Client already exists : {}", referralClient);
      throw new ServiceException(e);
    }
  }

  @Override
  public gov.ca.cwds.rest.api.domain.cms.ReferralClient update(Serializable primaryKeyObject,
      Request request) {
    assert request instanceof gov.ca.cwds.rest.api.domain.cms.ReferralClient;
    gov.ca.cwds.rest.api.domain.cms.ReferralClient referralClient =
        (gov.ca.cwds.rest.api.domain.cms.ReferralClient) request;

    try {
      ReferralClient managed = new ReferralClient(referralClient, "BTr");
      // checking the staffPerson county code
      StaffPerson staffperson = staffpersonDao.find(managed.getLastUpdatedId());
      if (staffperson != null
          && (triggerTablesDao.getLaCountySpecificCode().equals(staffperson.getCountyCode()))) {
        laCountyTrigger.createCountyTrigger(managed);
      } else {
        nonLaTriggers.createAndUpdateReferralClientCoutyOwnership(managed);
      }
      return new gov.ca.cwds.rest.api.domain.cms.ReferralClient(managed);
    } catch (EntityNotFoundException e) {
      LOGGER.info("Referral not found : {}", referralClient);
      LOGGER.error(e.getMessage(), e);
      throw new ServiceException(e);
    }
  }

  private static ReferralClient.PrimaryKey extractPrimaryKey(Serializable primaryKey) {
    Map<String, String> nameValuePairs = ServiceUtils.extractKeyValuePairs(primaryKey);
    String referralId = nameValuePairs.get(KEY_REFERRAL_ID);
    String clientId = nameValuePairs.get(KEY_CLIENT_ID);
    return new ReferralClient.PrimaryKey(referralId, clientId);
  }

}
