package gov.ca.cwds.rest.services;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.EntityNotFoundException;

import org.apache.commons.lang3.NotImplementedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.Dao;
import gov.ca.cwds.data.ns.ScreeningDao;
import gov.ca.cwds.data.persistence.ns.Address;
import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.Person;
import gov.ca.cwds.rest.api.domain.PostedScreening;
import gov.ca.cwds.rest.api.domain.Screening;
import gov.ca.cwds.rest.api.domain.ScreeningReference;
import gov.ca.cwds.rest.api.domain.ScreeningRequest;
import gov.ca.cwds.rest.api.domain.ScreeningResponse;

/**
 * Business layer object to work on {@link Screening}
 * 
 * @author CWDS API Team
 */
public class ScreeningService implements CrudsService {

  private static final Logger LOGGER = LoggerFactory.getLogger(ScreeningService.class);
  private ScreeningDao screeningDao;
  private PersonService personService;

  /**
   * 
   * @param screeningDao The {@link Dao} handling
   *        {@link gov.ca.cwds.data.persistence.ns.Screening} objects.
   * @param personService The person service
   */
  @Inject
  public ScreeningService(ScreeningDao screeningDao, PersonService personService) {
    this.screeningDao = screeningDao;
    this.personService = personService;
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#find(java.io.Serializable)
   */
  @Override
  public ScreeningResponse find(Serializable primaryKey) {
    assert primaryKey instanceof Long;

    gov.ca.cwds.data.persistence.ns.Screening screening = screeningDao.find(primaryKey);
    if (screening != null) {
      return new ScreeningResponse(screening, screening.getParticipants());
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
    assert (primaryKey instanceof Long);
    throw new NotImplementedException("Delete is not implemented");
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#create(gov.ca.cwds.rest.api.Request)
   */
  @Override
  public PostedScreening create(Request request) {
    assert request instanceof ScreeningReference;

    ScreeningReference screeningReference = (ScreeningReference) request;
    gov.ca.cwds.data.persistence.ns.Screening managed =
        new gov.ca.cwds.data.persistence.ns.Screening(screeningReference.getReference());

    managed = screeningDao.create(managed);
    return new PostedScreening(managed.getId(), managed.getReference());
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#update(java.io.Serializable,
   *      gov.ca.cwds.rest.api.Request)
   */
  @Override
  public ScreeningResponse update(Serializable primaryKey, Request request) {
    assert primaryKey instanceof Long;
    assert request instanceof ScreeningRequest;

    ScreeningRequest screeningRequest = (ScreeningRequest) request;

    Set<gov.ca.cwds.data.persistence.ns.Person> participants = new HashSet<>();
    for (Long participantId : screeningRequest.getParticipant_ids()) {
      Person person = personService.find(participantId);
      if (person == null) {
        String msg = MessageFormat.format("Unable to find participant with id={0}", participantId);
        LOGGER.warn(msg);
        throw new ServiceException(new EntityNotFoundException(msg));
      }
      participants.add(new gov.ca.cwds.data.persistence.ns.Person(person, null));
    }

    Address address = new Address(screeningRequest.getAddress(), null);
    gov.ca.cwds.data.persistence.ns.Screening screening =
        new gov.ca.cwds.data.persistence.ns.Screening((Long) primaryKey, screeningRequest,
            address, participants, null);

    screening = screeningDao.update(screening);
    return new ScreeningResponse(screening, screening.getParticipants());
  }
}
