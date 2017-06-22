package gov.ca.cwds.rest.services.cms;

import gov.ca.cwds.rest.api.domain.Participant;
import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.Dao;
import gov.ca.cwds.data.cms.ClientAddressDao;
import gov.ca.cwds.data.cms.StaffPersonDao;
import gov.ca.cwds.data.persistence.cms.ClientAddress;
import gov.ca.cwds.data.persistence.cms.StaffPerson;
import gov.ca.cwds.data.rules.TriggerTablesDao;
import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.business.rules.LACountyTrigger;
import gov.ca.cwds.rest.services.CrudsService;
import gov.ca.cwds.rest.services.ServiceException;
import gov.ca.cwds.rest.util.IdGenerator;

/**
 * Business layer object to work on {@link ClientAddress}
 * 
 * @author CWDS API Team
 */
public class ClientAddressService implements CrudsService {
  private static final Logger LOGGER = LoggerFactory.getLogger(ClientService.class);

  private ClientAddressDao clientAddressDao;
  private StaffPersonDao staffpersonDao;
  private TriggerTablesDao triggerTablesDao;
  private LACountyTrigger laCountyTrigger;
  private StaffPersonIdRetriever staffPersonIdRetriever;

  /**
   * Constructor
   * 
   * @param clientAddressDao The {@link Dao} handling
   *        {@link gov.ca.cwds.data.persistence.cms.ClientAddress} objects.
   * @param laCountyTrigger The {@link Dao} handling
   *        {@link gov.ca.cwds.rest.business.rules.LACountyTrigger} objects
   * @param triggerTablesDao The {@link Dao} handling
   *        {@link gov.ca.cwds.data.rules.TriggerTablesDao} objects
   * @param staffpersonDao The {@link Dao} handling
   *        {@link gov.ca.cwds.data.persistence.cms.StaffPerson} objects
   * @param staffPersonIdRetriever the staffPersonIdRetriever
   */
  @Inject
  public ClientAddressService(ClientAddressDao clientAddressDao, StaffPersonDao staffpersonDao,
      TriggerTablesDao triggerTablesDao, LACountyTrigger laCountyTrigger,
      StaffPersonIdRetriever staffPersonIdRetriever) {
    this.clientAddressDao = clientAddressDao;
    this.staffpersonDao = staffpersonDao;
    this.triggerTablesDao = triggerTablesDao;
    this.laCountyTrigger = laCountyTrigger;
    this.staffPersonIdRetriever = staffPersonIdRetriever;
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

  public List<Response> findByAddressAndClient(gov.ca.cwds.rest.api.domain.Address address, Participant clientParticipant) {
    List<gov.ca.cwds.data.persistence.cms.ClientAddress> persistedClientAddresses =
        clientAddressDao.findByAddressAndClient(address.getLegacyId(), clientParticipant.getLegacyId());
    if (persistedClientAddresses != null && ! persistedClientAddresses.isEmpty()) {
      ArrayList<Response> foundClientAddresses = new ArrayList();
      for (ClientAddress clientAddress : persistedClientAddresses){
        foundClientAddresses.add(new gov.ca.cwds.rest.api.domain.cms.ClientAddress(clientAddress, true));

      }
      return foundClientAddresses;
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
      String lastUpdatedId = staffPersonIdRetriever.getStaffPersonId();
      ClientAddress managedClientAddress =
          new ClientAddress(IdGenerator.randomString(10), clientAddress, lastUpdatedId);
      // checking the staffPerson county code
      StaffPerson staffperson = staffpersonDao.find(managedClientAddress.getLastUpdatedId());
      if (staffperson != null
          && (triggerTablesDao.getLaCountySpecificCode().equals(staffperson.getCountyCode()))) {
        laCountyTrigger.createClientAddressCountyTrigger(managedClientAddress);
      }
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
      String lastUpdatedId = staffPersonIdRetriever.getStaffPersonId();
      ClientAddress managed = new ClientAddress((String) primaryKey, clientAddress, lastUpdatedId);
      // checking the staffPerson county code
      StaffPerson staffperson = staffpersonDao.find(managed.getLastUpdatedId());
      if (staffperson != null
          && (triggerTablesDao.getLaCountySpecificCode().equals(staffperson.getCountyCode()))) {
        laCountyTrigger.createClientAddressCountyTrigger(managed);
      }
      managed = clientAddressDao.update(managed);
      return new gov.ca.cwds.rest.api.domain.cms.ClientAddress(managed, true);
    } catch (EntityNotFoundException e) {
      LOGGER.info("ClientAddress not found : {}", clientAddress);
      throw new ServiceException(e);
    }
  }

}
