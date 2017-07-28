package gov.ca.cwds.rest.services.cms;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.validation.Validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.cms.AddressDao;
import gov.ca.cwds.data.cms.SsaName3Dao;
import gov.ca.cwds.data.persistence.cms.Address;
import gov.ca.cwds.data.persistence.cms.CmsKeyIdGenerator;
import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.ScreeningToReferral;
import gov.ca.cwds.rest.api.domain.cms.PostedAddress;
import gov.ca.cwds.rest.business.rules.UpperCaseTables;
import gov.ca.cwds.rest.messages.MessageBuilder;
import gov.ca.cwds.rest.services.CrudsService;
import gov.ca.cwds.rest.services.LegacyDefaultValues;
import gov.ca.cwds.rest.services.ServiceException;


/**
 * @author CWDS API Team
 */
public class AddressService implements CrudsService {
  private static final Logger LOGGER = LoggerFactory.getLogger(ClientService.class);


  private AddressDao addressDao;
  private StaffPersonIdRetriever staffPersonIdRetriever;
  private SsaName3Dao ssaname3Dao;
  private UpperCaseTables upperCaseTables;

  private Validator validator;

  LegacyDefaultValues legacyDefaultValues = new LegacyDefaultValues();

  /**
   * 
   * @param addressDao the address DAO
   * @param staffPersonIdRetriever the staffPersonIdRetriever
   * @param ssaname3Dao the stored procedure call
   * @param upperCaseTables the address upper case
   * @param validator the validator object used to validate validatable objects
   */
  @Inject
  public AddressService(AddressDao addressDao, StaffPersonIdRetriever staffPersonIdRetriever,
      SsaName3Dao ssaname3Dao, UpperCaseTables upperCaseTables, Validator validator) {
    this.addressDao = addressDao;
    this.staffPersonIdRetriever = staffPersonIdRetriever;
    this.ssaname3Dao = ssaname3Dao;
    this.upperCaseTables = upperCaseTables;
    this.validator = validator;
  }

  @Override
  public Response create(Request request) {
    assert request instanceof gov.ca.cwds.rest.api.domain.cms.Address;

    gov.ca.cwds.rest.api.domain.cms.Address address =
        (gov.ca.cwds.rest.api.domain.cms.Address) request;

    return create(address, null);
  }

  /**
   * This createWithSingleTimestamp is used for the referrals to maintian the same timestamp for the
   * whole transaction
   * 
   * @param request - request
   * @param timestamp - timestamp
   * @return the single timestamp
   */
  public Response createWithSingleTimestamp(Request request, Date timestamp) {
    assert request instanceof gov.ca.cwds.rest.api.domain.cms.Address;

    gov.ca.cwds.rest.api.domain.cms.Address address =
        (gov.ca.cwds.rest.api.domain.cms.Address) request;

    return create(address, timestamp);
  }


  /**
   * This private method is created to handle to single address and referral address with single
   * timestamp
   * 
   */
  private PostedAddress create(gov.ca.cwds.rest.api.domain.cms.Address address, Date timestamp) {
    try {
      String lastUpdatedId = staffPersonIdRetriever.getStaffPersonId();
      Address managed;
      if (timestamp == null) {
        managed = new Address(CmsKeyIdGenerator.generate(lastUpdatedId), address, lastUpdatedId);
      } else {
        managed = new Address(CmsKeyIdGenerator.generate(lastUpdatedId), address, lastUpdatedId,
            timestamp);
      }
      managed = addressDao.create(managed);
      if (managed.getId() == null) {
        throw new ServiceException("Address ID cannot be null");
      }
      // ssaname3Dao.addressSsaname3("I", managed);
      upperCaseTables.createAddressUc(managed);
      return new PostedAddress(managed, true);
    } catch (EntityExistsException e) {
      LOGGER.info("Address already exists : ()", address);
      throw new ServiceException(e);
    }
  }

  /**
   * @param scr - scr
   * @param timestamp - timestamp
   * @param messageBuilder - messageBuilder
   * @return the AddressFromScreening
   */
  public gov.ca.cwds.rest.api.domain.Address createAddressFromScreening(ScreeningToReferral scr,
      Date timestamp, MessageBuilder messageBuilder) {
    gov.ca.cwds.rest.api.domain.Address address = scr.getAddress();
    if (address == null || address.getZip() == null || address.getStreetAddress() == null
        || address.getStreetAddress().isEmpty() || address.getType() == null) {
      String message = "Screening address is null or empty";
      messageBuilder.addMessageAndLog(message, LOGGER);
      return address;
    }

    try {
      gov.ca.cwds.rest.api.domain.cms.Address domainAddress =
          gov.ca.cwds.rest.api.domain.cms.Address.createWithDefaults(address,
              LegacyDefaultValues.DEFAULT_STATE_CODE);

      messageBuilder.addDomainValidationError(validator.validate(domainAddress));

      PostedAddress postedAddress =
          (PostedAddress) this.createWithSingleTimestamp(domainAddress, timestamp);

      address.setLegacyId(postedAddress.getExistingAddressId());
      address.setLegacySourceTable("ADDRS_T");
    } catch (Exception e) {
      messageBuilder.addMessageAndLog(e.getMessage(), e, LOGGER);
    }

    return address;

  }

  @Override
  public Response delete(Serializable primaryKey) {
    assert primaryKey instanceof String;
    gov.ca.cwds.data.persistence.cms.Address persistedAddress = addressDao.delete(primaryKey);
    if (persistedAddress != null) {
      upperCaseTables.deleteAddressUc(primaryKey);
      return new gov.ca.cwds.rest.api.domain.cms.Address(persistedAddress, true);
    }
    return null;
  }

  @Override
  public Response find(Serializable primaryKey) {
    assert primaryKey instanceof String;

    gov.ca.cwds.data.persistence.cms.Address persistedAddress = addressDao.find(primaryKey);
    if (persistedAddress != null) {
      return new gov.ca.cwds.rest.api.domain.cms.Address(persistedAddress, true);
    }
    return null;
  }

  @Override
  public Response update(Serializable primaryKey, Request request) {
    assert primaryKey instanceof String;
    assert request instanceof gov.ca.cwds.rest.api.domain.cms.Address;
    gov.ca.cwds.rest.api.domain.cms.Address address =
        (gov.ca.cwds.rest.api.domain.cms.Address) request;

    try {
      String lastUpdatedId = staffPersonIdRetriever.getStaffPersonId();
      Address managed = new Address((String) primaryKey, address, lastUpdatedId);
      managed = addressDao.update(managed);
      upperCaseTables.updateAddressUc(managed);
      return new gov.ca.cwds.rest.api.domain.cms.Address(managed, true);
    } catch (EntityNotFoundException e) {
      LOGGER.info("Address not found : {}", address);
      throw new ServiceException(e);
    }
  }

}
