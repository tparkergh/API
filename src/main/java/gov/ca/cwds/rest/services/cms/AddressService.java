package gov.ca.cwds.rest.services.cms;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.cms.AddressDao;
import gov.ca.cwds.data.cms.SsaName3Dao;
import gov.ca.cwds.data.persistence.cms.Address;
import gov.ca.cwds.data.persistence.cms.CmsKeyIdGenerator;
import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.cms.PostedAddress;
import gov.ca.cwds.rest.services.CrudsService;
import gov.ca.cwds.rest.services.ServiceException;


/**
 * @author CWDS API Team
 */
public class AddressService implements CrudsService {
  private static final Logger LOGGER = LoggerFactory.getLogger(ClientService.class);

  private AddressDao addressDao;
  private StaffPersonIdRetriever staffPersonIdRetriever;
  private SsaName3Dao ssaname3Dao;

  /**
   * 
   * @param addressDao the address DAO
   * @param staffPersonIdRetriever the staffPersonIdRetriever
   */
  @Inject
  public AddressService(AddressDao addressDao, StaffPersonIdRetriever staffPersonIdRetriever,
      SsaName3Dao ssaname3Dao) {
    this.addressDao = addressDao;
    this.staffPersonIdRetriever = staffPersonIdRetriever;
    this.ssaname3Dao = ssaname3Dao;
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

  public Response createWithSssaName3(Request request, Date timestamp) {
    assert request instanceof gov.ca.cwds.rest.api.domain.cms.Address;

    gov.ca.cwds.rest.api.domain.cms.Address address =
        (gov.ca.cwds.rest.api.domain.cms.Address) request;

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
      ssaname3Dao.addressSsaname3("I", managed);
      return new PostedAddress(managed, true);
    } catch (EntityExistsException e) {
      LOGGER.info("Address already exists : ()", address);
      throw new ServiceException(e);
    }
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
      return new PostedAddress(managed, true);
    } catch (EntityExistsException e) {
      LOGGER.info("Address already exists : ()", address);
      throw new ServiceException(e);
    }
  }


  @Override
  public Response delete(Serializable primaryKey) {
    assert primaryKey instanceof String;
    gov.ca.cwds.data.persistence.cms.Address persistedAddress = addressDao.delete(primaryKey);
    if (persistedAddress != null) {
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
      return new gov.ca.cwds.rest.api.domain.cms.Address(managed, true);
    } catch (EntityNotFoundException e) {
      LOGGER.info("Address not found : {}", address);
      throw new ServiceException(e);
    }
  }

}
