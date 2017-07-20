package gov.ca.cwds.rest.services;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.NotImplementedException;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.jadira.usertype.spi.utils.lang.StringUtils;

import com.google.common.collect.ImmutableSet;
import com.google.inject.Inject;

import gov.ca.cwds.data.Dao;
import gov.ca.cwds.data.ns.ScreeningDao;
import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.Screening;
import gov.ca.cwds.rest.api.domain.ScreeningListResponse;
import gov.ca.cwds.rest.api.domain.ScreeningResponse;
import gov.ca.cwds.rest.util.ServiceUtils;

/**
 * Business layer object to work on {@link Screening}
 * 
 * @author CWDS API Team
 */
public class ScreeningService implements CrudsService {

  private ScreeningDao screeningDao;
  private PersonService personService;

  /**
   * 
   * @param screeningDao The {@link Dao} handling {@link gov.ca.cwds.data.persistence.ns.Screening}
   *        objects.
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
  public Response find(Serializable primaryKey) {
    if (primaryKey instanceof Long) {
      gov.ca.cwds.data.persistence.ns.Screening screening = screeningDao.find(primaryKey);
      if (screening != null) {
        // return new ScreeningResponse(screening, screening.getParticipants());
      }
      return null;
    } else {
      List<gov.ca.cwds.data.persistence.ns.Screening> screenings = findByCriteria(primaryKey);
      ImmutableSet.Builder<ScreeningResponse> builder = ImmutableSet.builder();
      for (gov.ca.cwds.data.persistence.ns.Screening screening : screenings) {
        if (screening != null) {
          // builder.add(new ScreeningResponse(screening, screening.getParticipants()));
        }

      }
      return new ScreeningListResponse(builder.build());
    }
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

  @SuppressWarnings("unchecked")
  public List<gov.ca.cwds.data.persistence.ns.Screening> findByCriteria(Serializable keys) {
    Map<String, String> nameValuePairs = ServiceUtils.extractKeyValuePairs(keys);
    String responseTimes = nameValuePairs.get("responseTimes");
    String screeningDecisions = nameValuePairs.get("screeningDecisions");
    Session session = screeningDao.getSessionFactory().getCurrentSession();

    Criteria criteria = session.createCriteria(gov.ca.cwds.data.persistence.ns.Screening.class);
    String anObject = "null";
    if (StringUtils.isNotEmpty(responseTimes) && !anObject.equals(responseTimes)) {
      criteria.add(Restrictions.like("responseTime", responseTimes));
    }
    if (StringUtils.isNotEmpty(screeningDecisions) && !anObject.equals(screeningDecisions)) {
      criteria.add(Restrictions.like("screeningDecision", screeningDecisions));
    }

    return criteria.list();
  }
}
