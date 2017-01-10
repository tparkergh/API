package gov.ca.cwds.rest.services;

import java.io.Serializable;

import org.apache.commons.lang3.NotImplementedException;

import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.Address;
import gov.ca.cwds.rest.api.domain.ValidatedAddress;
import gov.ca.cwds.rest.validation.SmartyStreet;

/**
 * Business layer object to work on {@link ValidatedAddress}
 * 
 * @author CWDS API Team
 */
public class AddressValidationService implements CrudsService {

  /**
   * Returns all valid addresses, up to the default number set in smartystreet
   * 
   * @param address The address to validate
   * @return array of {@link ValidatedAddress}
   * @throws Exception due to SmartyStreet error, I/O error, etc.
   */
  public ValidatedAddress[] fetchValidatedAddresses(Address address) throws Exception {
    ValidatedAddress[] addresses = null;
    SmartyStreet smartyStreet = new SmartyStreet();
    addresses = smartyStreet.usStreetSingleAddress(address.getStreet_address(), address.getCity(),
        address.getState(), address.getZip());

    return addresses;
  }

  // Not Implemented

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#create(gov.ca.cwds.rest.api.Request)
   */
  @Override
  public Response create(Request request) {
    assert request instanceof Address;
    throw new NotImplementedException("Create is not implemented");
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#find(java.io.Serializable)
   */
  @Override
  public Address find(Serializable primaryKey) {
    assert primaryKey instanceof Long;
    throw new NotImplementedException("Delete is not implemented");
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#delete(java.io.Serializable)
   */
  @Override
  public Response delete(Serializable primaryKey) {
    assert primaryKey instanceof Long;
    throw new NotImplementedException("Delete is not implemented");
  }


  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#update(java.io.Serializable,
   *      gov.ca.cwds.rest.api.Request)
   */
  @Override
  public Response update(Serializable primaryKey, Request request) {
    assert primaryKey instanceof Long;
    throw new NotImplementedException("Update is not implemented");
  }

}
