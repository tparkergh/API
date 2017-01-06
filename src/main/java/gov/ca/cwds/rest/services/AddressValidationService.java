package gov.ca.cwds.rest.services;

import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.Address;
import gov.ca.cwds.rest.api.domain.ValidatedAddress;

import java.io.Serializable;
import java.util.ArrayList;

import org.apache.commons.lang3.NotImplementedException;

/**
 * Business layer object to work on {@link ValidatedAddress}
 * 
 * @author CWDS API Team
 */
public class AddressValidationService implements CrudsService {

  /**
   * Returns all valid addresses, up the default number set in smartystreet
   * 
   * @param address The address to validate
   * @return array of {@link ValidatedAddress}
   */
  public ValidatedAddress[] fetchValidatedAddresses(Address address) {
    ArrayList<ValidatedAddress> validatedAddresses = new ArrayList<ValidatedAddress>();
    validatedAddresses.add(new ValidatedAddress(address, 1, 2, true));
    return validatedAddresses.toArray(new ValidatedAddress[validatedAddresses.size()]);
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
