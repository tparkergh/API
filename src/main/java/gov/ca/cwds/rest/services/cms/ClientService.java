package gov.ca.cwds.rest.services.cms;

import java.io.Serializable;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.Dao;
import gov.ca.cwds.data.cms.ClientDao;
import gov.ca.cwds.data.cms.StaffPersonDao;
import gov.ca.cwds.data.persistence.cms.Client;
import gov.ca.cwds.data.persistence.cms.CmsKeyIdGenerator;
import gov.ca.cwds.data.persistence.cms.CountyOwnership;
import gov.ca.cwds.data.persistence.cms.StaffPerson;
import gov.ca.cwds.data.rules.TriggerTablesDao;
import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.domain.cms.PostedClient;
import gov.ca.cwds.rest.services.CrudsService;
import gov.ca.cwds.rest.services.ServiceException;

/**
 * Business layer object to work on {@link Client}
 * 
 * @author CWDS API Team
 */
public class ClientService implements CrudsService {
  private static final Logger LOGGER = LoggerFactory.getLogger(ClientService.class);

  private ClientDao clientDao;
  private StaffPersonDao staffpersonDao;
  private TriggerTablesDao triggerTablesDao;

  /**
   * Constructor
   * 
   * @param clientDao The {@link Dao} handling {@link gov.ca.cwds.data.persistence.cms.Client}
   *        objects.
   * @param staffpersonDao The {@link Dao} handling
   *        {@link gov.ca.cwds.data.persistence.cms.StaffPerson} objects
   * @param triggerTablesDao The {@link Dao} handling
   *        {@link gov.ca.cwds.data.rules.TriggerTablesDao} objects
   */
  @Inject
  public ClientService(ClientDao clientDao, StaffPersonDao staffpersonDao,
      TriggerTablesDao triggerTablesDao) {
    this.clientDao = clientDao;
    this.staffpersonDao = staffpersonDao;
    this.triggerTablesDao = triggerTablesDao;
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#find(java.io.Serializable)
   */
  @Override
  public gov.ca.cwds.rest.api.domain.cms.Client find(Serializable primaryKey) {
    assert primaryKey instanceof String;

    gov.ca.cwds.data.persistence.cms.Client persistedClient = clientDao.find(primaryKey);
    if (persistedClient != null) {
      return new gov.ca.cwds.rest.api.domain.cms.Client(persistedClient, true);
    }
    return null;
  }

  /*
   * This method is the representation of postedCmsReferral to find the existing client
   */
  @SuppressWarnings("javadoc")
  public PostedClient findInboundId(Serializable primaryKey) {
    assert primaryKey instanceof String;

    gov.ca.cwds.data.persistence.cms.Client persistedClient = clientDao.find(primaryKey);
    if (persistedClient != null) {
      return new PostedClient(persistedClient, true);
    }
    return null;
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#delete(java.io.Serializable)
   */
  @Override
  public gov.ca.cwds.rest.api.domain.cms.Client delete(Serializable primaryKey) {
    assert primaryKey instanceof String;
    gov.ca.cwds.data.persistence.cms.Client persistedClient = clientDao.delete(primaryKey);
    if (persistedClient != null) {
      return new gov.ca.cwds.rest.api.domain.cms.Client(persistedClient, true);
    }
    return null;
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#create(gov.ca.cwds.rest.api.Request)
   */
  @Override
  public PostedClient create(Request request) {
    assert request instanceof gov.ca.cwds.rest.api.domain.cms.Client;

    gov.ca.cwds.rest.api.domain.cms.Client client =
        (gov.ca.cwds.rest.api.domain.cms.Client) request;

    try {
      // TODO : refactor to actually determine who is updating. 'q1p' for now - see user story
      // #136737071 - Tech Debt: Legacy Service classes must use Staff ID for last update ID value
      CountyOwnership countyOwnership = new CountyOwnership();

      Client managed = new Client(CmsKeyIdGenerator.cmsIdGenertor(null), client, "BTr");
      managed = clientDao.create(managed);

      StaffPerson staffperson = staffpersonDao.find(managed.getLastUpdatedId());
      if (staffperson != null && !("19".equals(staffperson.getCountyCode()))) {
        managed.setCountyOwnership(countyOwnership);
        countyOwnership.setEntityId(managed.getPrimaryKey());
        countyOwnership.setEntityCode("C");
      }
      return new PostedClient(managed, false);
    } catch (EntityExistsException e) {
      LOGGER.info("Client already exists : {}", client);
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
  public gov.ca.cwds.rest.api.domain.cms.Client update(Serializable primaryKey, Request request) {
    assert primaryKey instanceof String;
    assert request instanceof gov.ca.cwds.rest.api.domain.cms.Client;
    gov.ca.cwds.rest.api.domain.cms.Client client =
        (gov.ca.cwds.rest.api.domain.cms.Client) request;

    try {
      Client managed = new Client((String) primaryKey, client, "q1p");
      managed = clientDao.update(managed);
      return new gov.ca.cwds.rest.api.domain.cms.Client(managed, true);
    } catch (EntityNotFoundException e) {
      LOGGER.info("Client not found : {}", client);
      throw new ServiceException(e);
    }
  }

}
