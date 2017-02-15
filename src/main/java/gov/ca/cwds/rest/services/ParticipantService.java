package gov.ca.cwds.rest.services;

import java.io.Serializable;

import com.google.inject.Inject;

import gov.ca.cwds.data.Dao;
import gov.ca.cwds.data.ns.ParticipantDao;
import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.Address;
import gov.ca.cwds.rest.api.domain.Participant;

/**
 * Business layer object to work on {@link Address}
 * 
 * @author CWDS API Team
 */
public class ParticipantService implements CrudsService {

  private ParticipantDao participantDao;

  /**
   * Constructor
   * 
   * @param participantDao The {@link Dao} handling
   *        {@link gov.ca.cwds.data.persistence.ns.Participant}
   */
  @Inject
  public ParticipantService(ParticipantDao participantDao) {
    this.participantDao = participantDao;
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#create(gov.ca.cwds.rest.api.Request)
   */
  @Override
  public Response create(Request request) {
    assert request instanceof Participant;
    Participant participant = (Participant) request;
    gov.ca.cwds.data.persistence.ns.Participant managed =
        new gov.ca.cwds.data.persistence.ns.Participant(participant, null, null);
    managed = participantDao.create(managed);
    return new Participant(managed);
  }

  @Override
  public Response delete(Serializable arg0) {
    return null;
  }

  @Override
  public Response find(Serializable arg0) {
    return null;
  }

  @Override
  public Response update(Serializable arg0, Request arg1) {
    return null;
  }

}
