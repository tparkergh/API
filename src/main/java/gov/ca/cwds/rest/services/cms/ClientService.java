package gov.ca.cwds.rest.services.cms;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.Dao;
import gov.ca.cwds.data.cms.ClientAddressDao;
import gov.ca.cwds.data.cms.ClientDao;
import gov.ca.cwds.data.cms.SsaName3Dao;
import gov.ca.cwds.data.cms.StaffPersonDao;
import gov.ca.cwds.data.persistence.cms.Client;
import gov.ca.cwds.data.persistence.cms.CmsKeyIdGenerator;
import gov.ca.cwds.data.persistence.cms.StaffPerson;
import gov.ca.cwds.data.rules.TriggerTablesDao;
import gov.ca.cwds.rest.api.domain.cms.PostedClient;
import gov.ca.cwds.rest.business.rules.ExternalInterfaceTables;
import gov.ca.cwds.rest.business.rules.NonLACountyTriggers;
import gov.ca.cwds.rest.business.rules.UpperCaseTables;
import gov.ca.cwds.rest.services.ServiceException;
import gov.ca.cwds.rest.services.TypedCrudsService;

/**
 * Business layer object to work on {@link Client}
 * 
 * @author CWDS API Team
 */
public class ClientService implements
    TypedCrudsService<String, gov.ca.cwds.rest.api.domain.cms.Client, gov.ca.cwds.rest.api.domain.cms.Client> {
  private static final Logger LOGGER = LoggerFactory.getLogger(ClientService.class);

  private ClientDao clientDao;
  private ClientAddressDao clientAddressDao;
  private StaffPersonDao staffpersonDao;
  private TriggerTablesDao triggerTablesDao;
  private NonLACountyTriggers nonLaCountyTriggers;
  private StaffPersonIdRetriever staffPersonIdRetriever;
  private SsaName3Dao ssaname3Dao;
  private UpperCaseTables upperCaseTables;
  private ExternalInterfaceTables externalInterfaceTables;

  /**
   * Constructor
   * 
   * @param clientDao The {@link Dao} handling {@link gov.ca.cwds.data.persistence.cms.Client}
   *        objects.
   * @param staffpersonDao The {@link Dao} handling
   *        {@link gov.ca.cwds.data.persistence.cms.StaffPerson} objects
   * @param triggerTablesDao The {@link Dao} handling
   *        {@link gov.ca.cwds.data.rules.TriggerTablesDao} objects
   * @param nonLaCountyTriggers The {@link Dao} handling
   *        {@link gov.ca.cwds.rest.business.rules.NonLACountyTriggers} objects
   * @param staffPersonIdRetriever the staffPersonIdRetriever
   * @param ssaname3Dao dao the stored procedure call
   * @param upperCaseTables the downstream tables
   * @param externalInterfaceTables the external interface table
   */
  @Inject
  public ClientService(ClientDao clientDao, StaffPersonDao staffpersonDao,
      TriggerTablesDao triggerTablesDao, NonLACountyTriggers nonLaCountyTriggers,
      StaffPersonIdRetriever staffPersonIdRetriever, SsaName3Dao ssaname3Dao,
      UpperCaseTables upperCaseTables, ExternalInterfaceTables externalInterfaceTables) {
    this.clientDao = clientDao;
    this.staffpersonDao = staffpersonDao;
    this.triggerTablesDao = triggerTablesDao;
    this.nonLaCountyTriggers = nonLaCountyTriggers;
    this.staffPersonIdRetriever = staffPersonIdRetriever;
    this.ssaname3Dao = ssaname3Dao;
    this.upperCaseTables = upperCaseTables;
    this.externalInterfaceTables = externalInterfaceTables;
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#find(java.io.Serializable)
   */
  @Override
  public gov.ca.cwds.rest.api.domain.cms.Client find(String primaryKey) {

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
  public gov.ca.cwds.rest.api.domain.cms.Client delete(String primaryKey) {
    gov.ca.cwds.data.persistence.cms.Client persistedClient = clientDao.delete(primaryKey);
    if (persistedClient != null) {
      upperCaseTables.deleteClientUc(primaryKey);
      externalInterfaceTables.createExtInterForDelete(primaryKey, "CLIENT_T");
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
  public PostedClient create(gov.ca.cwds.rest.api.domain.cms.Client request) {

    gov.ca.cwds.rest.api.domain.cms.Client client = request;
    return create(client, null);

  }

  /**
   * This createWithSingleTimestamp is used for the referrals to maintian the same timestamp for the
   * whole transaction
   * 
   * @param request - request
   * @param timestamp - timestamp
   * @return the single timestamp
   */
  public PostedClient createWithSingleTimestamp(gov.ca.cwds.rest.api.domain.cms.Client request,
      Date timestamp) {

    gov.ca.cwds.rest.api.domain.cms.Client client = request;
    return create(client, timestamp);
  }

  /**
   * This private method is created to handle to single client and referrals with single timestamp
   * 
   */
  private PostedClient create(gov.ca.cwds.rest.api.domain.cms.Client client, Date timestamp) {
    try {
      String lastUpdatedId = staffPersonIdRetriever.getStaffPersonId();
      Client managed;
      if (timestamp == null) {
        managed = new Client(CmsKeyIdGenerator.generate(lastUpdatedId), client, lastUpdatedId);
      } else {
        managed =
            new Client(CmsKeyIdGenerator.generate(lastUpdatedId), client, lastUpdatedId, timestamp);
      }
      managed = clientDao.create(managed);
      // checking the staffPerson county code
      StaffPerson staffperson = staffpersonDao.find(managed.getLastUpdatedId());
      if (staffperson != null
          && !(triggerTablesDao.getLaCountySpecificCode().equals(staffperson.getCountyCode()))) {
        nonLaCountyTriggers.createClientCountyTrigger(managed);
      }
      ssaname3Dao.clientSsaname3("I", managed);
      upperCaseTables.createClientUc(managed);
      externalInterfaceTables.createExtInterClient(managed, "N");

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
  public gov.ca.cwds.rest.api.domain.cms.Client update(String primaryKey,
      gov.ca.cwds.rest.api.domain.cms.Client request) {
    gov.ca.cwds.rest.api.domain.cms.Client client = request;

    gov.ca.cwds.rest.api.domain.cms.Client savedEntity;
    try {
      String lastUpdatedId = staffPersonIdRetriever.getStaffPersonId();
      Client existingClient = clientDao.find(primaryKey);
      Client managed = new Client(primaryKey, client, lastUpdatedId);

      managed.setClientAddress(existingClient.getClientAddress());
      managed = clientDao.update(managed);
      savedEntity = new gov.ca.cwds.rest.api.domain.cms.Client(managed, true);
      upperCaseTables.updateClientUc(managed);
      externalInterfaceTables.createExtInterClient(managed, "C");
    } catch (EntityNotFoundException e) {
      savedEntity = null;
      LOGGER.info("client not found : {}", client);
      throw new ServiceException(e);
    }
    return savedEntity;
  }

}
