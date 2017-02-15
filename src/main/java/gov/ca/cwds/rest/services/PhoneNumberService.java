package gov.ca.cwds.rest.services;

import java.io.Serializable;

import org.apache.commons.lang3.NotImplementedException;

import com.google.inject.Inject;

import gov.ca.cwds.data.Dao;
import gov.ca.cwds.data.ns.PhoneNumberDao;
import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.PhoneNumber;
import gov.ca.cwds.rest.api.domain.PostedPhoneNumber;

/**
 * Business layer object to work on {@link PhoneNumber}
 * 
 * @author CWDS API Team
 */
public class PhoneNumberService implements CrudsService {
  private PhoneNumberDao phoneNumberDao;

  /**
   * Constructor
   * 
   * @param phoneNumberDao The {@link Dao} handling
   *        {@link gov.ca.cwds.data.persistence.ns.PhoneNumber} objects.
   */
  @Inject
  public PhoneNumberService(PhoneNumberDao phoneNumberDao) {
    this.phoneNumberDao = phoneNumberDao;
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#find(java.io.Serializable)
   */
  @Override
  public PhoneNumber find(Serializable primaryKey) {
    assert primaryKey instanceof Long;

    gov.ca.cwds.data.persistence.ns.PhoneNumber persistedPhoneNumber =
        phoneNumberDao.find(primaryKey);
    if (persistedPhoneNumber != null) {
      return new PhoneNumber(persistedPhoneNumber);
    }
    return null;
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
   * @see gov.ca.cwds.rest.services.CrudsService#create(gov.ca.cwds.rest.api.Request)
   */
  @Override
  public PostedPhoneNumber create(Request request) {
    assert request instanceof PhoneNumber;

    PhoneNumber phoneNumber = (PhoneNumber) request;
    gov.ca.cwds.data.persistence.ns.PhoneNumber managed =
        new gov.ca.cwds.data.persistence.ns.PhoneNumber(phoneNumber, null, null);

    managed = phoneNumberDao.create(managed);
    return new PostedPhoneNumber(managed);
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
