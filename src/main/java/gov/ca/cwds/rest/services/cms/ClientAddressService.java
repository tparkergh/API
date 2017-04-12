package gov.ca.cwds.rest.services.cms;

import java.io.Serializable;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.Dao;
import gov.ca.cwds.data.cms.ClientAddressDao;
import gov.ca.cwds.data.persistence.cms.ClientAddress;
import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.services.CrudsService;
import gov.ca.cwds.rest.services.ServiceException;
import gov.ca.cwds.rest.util.IdGenerator;

public class ClientAddressService implements CrudsService {
  private static final Logger LOGGER = LoggerFactory.getLogger(ClientService.class);

  private ClientAddressDao clientAddressDao;

  /**
   * Constructor
   * 
   * @param clientAddressDao The {@link Dao} handling
   *        {@link gov.ca.cwds.data.persistence.cms.ClientAddress} objects.
   */
  @Inject
  public ClientAddressService(ClientAddressDao clientAddressDao) {
    this.clientAddressDao = clientAddressDao;
  }

  @Override
  public Response find(Serializable primaryKey) {
    assert primaryKey instanceof String;

    gov.ca.cwds.data.persistence.cms.ClientAddress persistedClientAddress =
        clientAddressDao.find(primaryKey);
    if (persistedClientAddress != null) {
      return new gov.ca.cwds.rest.api.domain.cms.ClientAddress(persistedClientAddress, true);
    }
    return null;
  }

  @Override
  public Response delete(Serializable primaryKey) {
    assert primaryKey instanceof String;
    gov.ca.cwds.data.persistence.cms.ClientAddress persistedClientAddress =
        clientAddressDao.delete(primaryKey);
    if (persistedClientAddress != null) {
      return new gov.ca.cwds.rest.api.domain.cms.ClientAddress(persistedClientAddress, true);
    }
    return null;
  }

  @Override
  public Response create(Request request) {
    assert request instanceof gov.ca.cwds.rest.api.domain.cms.ClientAddress;

    gov.ca.cwds.rest.api.domain.cms.ClientAddress clientAddress =
        (gov.ca.cwds.rest.api.domain.cms.ClientAddress) request;

    try {
      ClientAddress managedClientAddress =
          new ClientAddress(IdGenerator.randomString(10), clientAddress, "q1p");
      managedClientAddress = clientAddressDao.create(managedClientAddress);
      return new gov.ca.cwds.rest.api.domain.cms.ClientAddress(managedClientAddress, false);
    } catch (EntityExistsException e) {
      LOGGER.info("ClientAddress already exists : {}", clientAddress);
      throw new ServiceException(e);
    }
  }

  @Override
  public Response update(Serializable primaryKey, Request request) {
    assert primaryKey instanceof String;
    assert request instanceof gov.ca.cwds.rest.api.domain.cms.ClientAddress;
    gov.ca.cwds.rest.api.domain.cms.ClientAddress clientAddress =
        (gov.ca.cwds.rest.api.domain.cms.ClientAddress) request;

    try {
      ClientAddress managed = new ClientAddress((String) primaryKey, clientAddress, "q1p");
      managed = clientAddressDao.update(managed);
      return new gov.ca.cwds.rest.api.domain.cms.ClientAddress(managed, true);
    } catch (EntityNotFoundException e) {
      LOGGER.info("ClientAddress not found : {}", clientAddress);
      throw new ServiceException(e);
    }
  }


}
