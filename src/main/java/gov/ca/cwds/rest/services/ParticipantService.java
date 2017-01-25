package gov.ca.cwds.rest.services;

import java.io.Serializable;

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

  @Override
  public Response create(Request request) {

    assert request instanceof Participant;

    Participant participant = (Participant) request;

    return participant;
  }

  @Override
  public Response delete(Serializable arg0) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Response find(Serializable arg0) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Response update(Serializable arg0, Request arg1) {
    // TODO Auto-generated method stub
    return null;
  }

}
