package gov.ca.cwds.rest.business.rules;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.DaoException;
import gov.ca.cwds.data.cms.AddressUcDao;
import gov.ca.cwds.data.cms.ClientUcDao;
import gov.ca.cwds.data.persistence.cms.Address;
import gov.ca.cwds.data.persistence.cms.AddressUc;
import gov.ca.cwds.data.persistence.cms.Client;
import gov.ca.cwds.data.persistence.cms.ClientUc;
import gov.ca.cwds.rest.services.ServiceException;

/**
 * Business layer object to update upper case tables
 * 
 *
 * @author CWDS API Team
 */
public class UpperCaseTables {

  private static final String SOURCE_TBL_CD_CLIENT = "C";
  private static final String SOURCE_TBL_CD_ADDRESS = "A";

  private static final Logger LOGGER = LoggerFactory.getLogger(UpperCaseTables.class);

  private ClientUcDao clientUcDao;
  private AddressUcDao addressUcDao;

  /**
   * @param clientUcDao - client upper case
   * @param addressUcDao - address upper case
   */
  @Inject
  public UpperCaseTables(ClientUcDao clientUcDao, AddressUcDao addressUcDao) {
    this.clientUcDao = clientUcDao;
    this.addressUcDao = addressUcDao;
  }

  protected String convertToUpperCase(String s) {
    return s != null ? s.toUpperCase() : null;
  }

  /**
   * @param client Client creates the client upper case with the client Id
   */
  public void createClientUc(Client client) {
    ClientUc clientUc = createClientUpperCase(client);
    try {
      clientUcDao.create(clientUc);
      LOGGER.info("clientUc is created");
    } catch (ServiceException se) {
      throw new DaoException("Insert to client_uc failed - ", se);
    }
  }

  /**
   * @param client Client updates the client upper case with the client Id
   */
  public void updateClientUc(Client client) {
    ClientUc clientUc = createClientUpperCase(client);
    try {
      clientUcDao.update(clientUc);
      LOGGER.info("clientUc is updated");
    } catch (ServiceException se) {
      throw new DaoException("Update to client_uc failed - ", se);
    }
  }

  /**
   * @param pktableId primary key to delete the client upper case
   */
  public void deleteClientUc(Serializable pktableId) {
    if (pktableId != null) {
      try {
        clientUcDao.delete(pktableId);
        LOGGER.info("clientUc is deleted");
      } catch (ServiceException se) {
        throw new DaoException("Delete from client_uc failed - ", se);
      }
    }
  }

  /**
   * @param address Address creates the address upper case with the address Id
   */
  public void createAddressUc(Address address) {
    AddressUc addressUc = createAddressUpperCase(address);
    try {
      addressUcDao.create(addressUc);
      LOGGER.info("addressUc is created");
    } catch (ServiceException se) {
      throw new DaoException("Insert to addrs_uc failed - ", se);
    }
  }

  /**
   * @param address Address updates the address upper case with the address Id
   */
  public void updateAddressUc(Address address) {
    AddressUc addressUc = createAddressUpperCase(address);
    try {
      addressUcDao.update(addressUc);
      LOGGER.info("addressUc is updated");
    } catch (ServiceException se) {
      throw new DaoException("Update to addrs_uc failed - ", se);
    }
  }

  /**
   * @param pktableId primary key to deletes the address upper case
   */
  public void deleteAddressUc(Serializable pktableId) {
    if (pktableId != null) {
      try {
        addressUcDao.delete(pktableId);
        LOGGER.info("addressUc is deleted");
      } catch (ServiceException se) {
        throw new DaoException("Delete from addrs_uc failed - ", se);
      }
    }
  }

  /**
   * 
   * @param client - client
   * @return the created clientUpperCase
   */
  private ClientUc createClientUpperCase(Client client) {
    ClientUc clientUc = new ClientUc();
    clientUc.setPktableId(client.getPrimaryKey());
    clientUc.setSourceTableCode(SOURCE_TBL_CD_CLIENT);
    clientUc.setCommonFirstName(convertToUpperCase(client.getCommonFirstName()));
    clientUc.setCommonMiddleName(convertToUpperCase(client.getCommonMiddleName()));
    clientUc.setCommonLastName(convertToUpperCase(client.getCommonLastName()));
    clientUc.setLastUpdatedId(client.getLastUpdatedId());
    clientUc.setLastUpdatedTime(client.getLastUpdatedTime());
    return clientUc;
  }

  /**
   * 
   * @param address - address
   * @return the created addressUpperCase
   */
  private AddressUc createAddressUpperCase(Address address) {
    AddressUc addressUc = new AddressUc();
    addressUc.setPktableId(address.getPrimaryKey());
    addressUc.setSourceTableCode(SOURCE_TBL_CD_ADDRESS);
    addressUc.setCityName(convertToUpperCase(address.getCity()));
    addressUc.setStreetNumber(convertToUpperCase(address.getStreetNumber()));
    addressUc.setStreetName(convertToUpperCase(address.getStreetName()));
    addressUc.setLastUpdatedId(address.getLastUpdatedId());
    addressUc.setLastUpdatedTime(address.getLastUpdatedTime());
    return addressUc;
  }

}
