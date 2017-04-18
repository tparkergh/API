package gov.ca.cwds.rest.services.cms;

import static org.mockito.Mockito.mock;

import java.io.Serializable;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.Dao;
import gov.ca.cwds.data.cms.ClientUcDao;
import gov.ca.cwds.data.persistence.cms.ClientUc;
import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.services.CrudsService;
import gov.ca.cwds.rest.services.ServiceException;

/**
 * 
 * @author CWDS API Team
 */
public class ClientUcService implements CrudsService {
  private static final Logger LOGGER = LoggerFactory.getLogger(ClientUcService.class);

  private ClientUcDao clientucDao;

  /**
   * Constructor
   * 
   * @param clientucDao The {@link Dao} handling {@link gov.ca.cwds.data.persistence.cms.ClientUc}
   *        objects.
   */
  @Inject
  public ClientUcService(ClientUcDao clientucDao) {
    this.clientucDao = clientucDao;
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#find(java.io.Serializable)
   */
  @Override
  public gov.ca.cwds.rest.api.domain.cms.ClientUc find(Serializable primaryKey) {
    assert primaryKey instanceof String;

    gov.ca.cwds.data.persistence.cms.ClientUc persistedClient = clientucDao.find(primaryKey);
    if (persistedClient != null) {
      return new gov.ca.cwds.rest.api.domain.cms.ClientUc(persistedClient);
    }
    return null;
  }


  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#delete(java.io.Serializable)
   */
  @Override
  public gov.ca.cwds.rest.api.domain.cms.ClientUc delete(Serializable primaryKey) {
    assert primaryKey instanceof String;
    gov.ca.cwds.data.persistence.cms.ClientUc persistedClient = clientucDao.delete(primaryKey);
    if (persistedClient != null) {
      return new gov.ca.cwds.rest.api.domain.cms.ClientUc(persistedClient);
    }
    return null;
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#create(gov.ca.cwds.rest.api.Request)
   */
  @Override
  public gov.ca.cwds.rest.api.domain.cms.ClientUc create(Request request) {
    assert request instanceof gov.ca.cwds.rest.api.domain.cms.ClientUc;

    gov.ca.cwds.rest.api.domain.cms.ClientUc mockDomain =
        mock(gov.ca.cwds.rest.api.domain.cms.ClientUc.class);

    gov.ca.cwds.rest.api.domain.cms.ClientUc clientUc =
        (gov.ca.cwds.rest.api.domain.cms.ClientUc) request;

    try {
      ClientUc managed = new ClientUc(clientUc, "q1p");
      managed = clientucDao.create(managed); // NOSONAR
      return mockDomain;
    } catch (EntityExistsException e) {
      LOGGER.info("Client already exists : {}", clientUc);
      throw new ServiceException(e);
    }
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#update(java.io.Serializable,
   *      gov.ca.cwds.rest.api.Request)
   */
  @Override
  public gov.ca.cwds.rest.api.domain.cms.ClientUc update(Serializable primaryKey, Request request) {
    assert request instanceof gov.ca.cwds.rest.api.domain.cms.ClientUc;
    gov.ca.cwds.rest.api.domain.cms.ClientUc clientUc =
        (gov.ca.cwds.rest.api.domain.cms.ClientUc) request;

    try {
      ClientUc managed = new ClientUc(clientUc, "q1p");
      managed = clientucDao.update(managed);
      return new gov.ca.cwds.rest.api.domain.cms.ClientUc(managed);
    } catch (EntityNotFoundException e) {
      LOGGER.info("Client not found : {}", clientUc);
      throw new ServiceException(e);
    }
  }

}
