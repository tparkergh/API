package gov.ca.cwds.rest.services.cms;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

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
import gov.ca.cwds.rest.api.domain.Participant;
import gov.ca.cwds.rest.business.rules.LACountyTrigger;
import gov.ca.cwds.rest.business.rules.NonLACountyTriggers;
import gov.ca.cwds.rest.services.CrudsService;
import gov.ca.cwds.rest.services.ServiceException;
import gov.ca.cwds.rest.services.referentialintegrity.RIClientAddress;
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
  private NonLACountyTriggers nonLaTriggers;
  private StaffPersonIdRetriever staffPersonIdRetriever;
  private RIClientAddress riClientAddress;

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
   * @param nonLaTriggers The {@link Dao} handling
   *        {@link gov.ca.cwds.rest.business.rules.NonLACountyTriggers} objects.
   */
  @Inject
  public ClientAddressService(ClientAddressDao clientAddressDao, StaffPersonDao staffpersonDao,
      TriggerTablesDao triggerTablesDao, LACountyTrigger laCountyTrigger,
      StaffPersonIdRetriever staffPersonIdRetriever, NonLACountyTriggers nonLaTriggers,
      RIClientAddress riClientAddress) {
    this.clientAddressDao = clientAddressDao;
    this.staffpersonDao = staffpersonDao;
    this.triggerTablesDao = triggerTablesDao;
    this.laCountyTrigger = laCountyTrigger;
    this.staffPersonIdRetriever = staffPersonIdRetriever;
    this.nonLaTriggers = nonLaTriggers;
    this.riClientAddress = riClientAddress;
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

  /**
   * @param address - address
   * @param clientParticipant - clientParticipant
   * @return the address Found
   */
  public List<Response> findByAddressAndClient(gov.ca.cwds.rest.api.domain.Address address,
      Participant clientParticipant) {
    List<gov.ca.cwds.data.persistence.cms.ClientAddress> persistedClientAddresses = clientAddressDao
        .findByAddressAndClient(address.getLegacyId(), clientParticipant.getLegacyId());
    if (persistedClientAddresses != null && !persistedClientAddresses.isEmpty()) {
      ArrayList<Response> foundClientAddresses = new ArrayList<>();
      for (ClientAddress clientAddress : persistedClientAddresses) {
        foundClientAddresses
            .add(new gov.ca.cwds.rest.api.domain.cms.ClientAddress(clientAddress, true));

      }
      return foundClientAddresses;
    }
    return Collections.emptyList();
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
    return create(clientAddress, null);

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
    assert request instanceof gov.ca.cwds.rest.api.domain.cms.ClientAddress;

    gov.ca.cwds.rest.api.domain.cms.ClientAddress clientAddress =
        (gov.ca.cwds.rest.api.domain.cms.ClientAddress) request;
    return create(clientAddress, timestamp);

  }

  /**
   * This private method is created to handle to single clientAddress and referrals with single
   * timestamp
   * 
   */
  private Response create(gov.ca.cwds.rest.api.domain.cms.ClientAddress clientAddress,
      Date timestamp) {
    try {
      String lastUpdatedId = staffPersonIdRetriever.getStaffPersonId();
      ClientAddress managedClientAddress;
      if (timestamp == null) {
        managedClientAddress =
            new ClientAddress(IdGenerator.randomString(10), clientAddress, lastUpdatedId);
      } else {
        managedClientAddress = new ClientAddress(IdGenerator.randomString(10), clientAddress,
            lastUpdatedId, timestamp);
      }
      // checking the staffPerson county code
      StaffPerson staffperson = staffpersonDao.find(managedClientAddress.getLastUpdatedId());
      if (staffperson != null
          && (triggerTablesDao.getLaCountySpecificCode().equals(staffperson.getCountyCode()))) {
        laCountyTrigger.createClientAddressCountyTrigger(managedClientAddress);
      } else {
        nonLaTriggers.createAndUpdateClientAddressCoutyOwnership(managedClientAddress);
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
