package gov.ca.cwds.rest.services;

import java.io.Serializable;

import org.apache.commons.lang3.NotImplementedException;

import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.Screening;

/**
 * Business layer object to work on {@link Screening}
 * 
 * @author CWDS API Team
 */
public class ScreeningService implements CrudsService {

  // private ScreeningDao screeningDao;
  // private PersonService personService;

  /**
   * default constructor
   */
  public ScreeningService() {
    // no-opt
  }

  // /**
  // *
  // * @param screeningDao The {@link Dao} handling {@link
  // gov.ca.cwds.data.persistence.ns.Screening}
  // * objects.
  // * @param personService The person service
  // */
  // @Inject
  // public ScreeningService(ScreeningDao screeningDao, PersonService personService) {
  // this.screeningDao = screeningDao;
  // this.personService = personService;
  // }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#find(java.io.Serializable)
   */
  @Override
  public Response find(Serializable primaryKey) {
    return null;
    // if (primaryKey instanceof Long) {
    // gov.ca.cwds.data.persistence.ns.Screening screening = screeningDao.find(primaryKey);
    // if (screening != null) {
    // // return new ScreeningResponse(screening, screening.getParticipants());
    // }
    // return null;
    // } else {
    // List<gov.ca.cwds.data.persistence.ns.Screening> screenings = findByCriteria(primaryKey);
    // ImmutableSet.Builder<ScreeningResponse> builder = ImmutableSet.builder();
    // for (gov.ca.cwds.data.persistence.ns.Screening screening : screenings) {
    // if (screening != null) {
    // // builder.add(new ScreeningResponse(screening, screening.getParticipants()));
    // }
    //
    // }
    // return new ScreeningListResponse(builder.build());
    // }
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
   * @param request the request
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#create(gov.ca.cwds.rest.api.Request)
   */
  @Override
  public Screening create(Request request) {
    assert request instanceof Screening;
    return (Screening) request;
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#update(java.io.Serializable,
   *      gov.ca.cwds.rest.api.Request)
   */
  @Override
  public Screening update(Serializable primaryKey, Request request) {
    return (Screening) request;
    // assert primaryKey instanceof Long;
    // assert request instanceof ScreeningRequest;
    //
    // ScreeningRequest screeningRequest = (ScreeningRequest) request;
    //
    // Set<gov.ca.cwds.data.persistence.ns.Participant> participants = new HashSet<>();
    // Address address = new Address(screeningRequest.getAddress(), null, null);
    // // gov.ca.cwds.data.persistence.ns.Screening screening =
    // // new gov.ca.cwds.data.persistence.ns.Screening((Long) primaryKey, screeningRequest,
    // // address,
    // // participants, null, null);
    // // screening = screeningDao.update(screening);
    // if (screeningDao.getSessionFactory() != null) {
    // screeningDao.getSessionFactory().getCurrentSession().flush();
    // screeningDao.getSessionFactory().getCurrentSession().refresh(screening);
    // }
    // // return new ScreeningResponse(screening, screening.getParticipants());

  }

}
