package gov.ca.cwds.rest.services;

import java.io.Serializable;

import org.apache.commons.lang3.NotImplementedException;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.rest.RestStatus;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import com.google.inject.name.Named;

import gov.ca.cwds.ObjectMapperUtils;
import gov.ca.cwds.data.es.ElasticsearchDao;
import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.Screening;

/**
 * Business layer object to work on {@link Screening}
 * 
 * @author CWDS API Team
 */
public class ScreeningService implements CrudsService {

  private static final ObjectMapper OBJECT_MAPPER = ObjectMapperUtils.createObjectMapper();

  private ElasticsearchDao esDao;

  /**
   * Construct the object
   * 
   * @param esDao Screenings ES DAO
   */
  @Inject
  public ScreeningService(@Named("screenings.index") ElasticsearchDao esDao) {
    this.esDao = esDao;
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#find(java.io.Serializable)
   */
  @Override
  public Response find(Serializable primaryKey) {
    throw new NotImplementedException("Find is not implemented");
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#delete(java.io.Serializable)
   */
  @Override
  public Response delete(Serializable primaryKey) {
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
    IndexResponse response = index(request);
    RestStatus status = response.status();

    if (201 != status.getStatus()) {
      throw new ServiceException("create -> Could not index screening. " + response);
    }

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
    assert primaryKey instanceof String;
    assert request instanceof Screening;
    Screening screening = (Screening) request;

    if (!primaryKey.equals(screening.getId())) {
      throw new ServiceException(
          "Primary key mismatch, [" + primaryKey + " != " + screening.getId() + "]");
    }

    IndexResponse response = index(request);
    RestStatus status = response.status();

    if (200 != status.getStatus()) {
      throw new ServiceException("update -> Could not index screening. " + response);
    }

    return (Screening) request;
  }

  /**
   * Convert given screening to JSON.
   * 
   * @param screening Screening to convert to JSON format
   * @return Screening as JSON format
   */
  private String toJson(Screening screening) {
    String screeningJson;
    try {
      screeningJson = OBJECT_MAPPER.writeValueAsString(screening);
    } catch (JsonProcessingException e) {
      throw new ServiceException(e);
    }
    return screeningJson;
  }

  /**
   * Index given screening request.
   * 
   * @param request The screening request
   * @return The IndexResponse
   */
  private IndexResponse index(Request request) {
    assert request instanceof Screening;
    Screening screening = (Screening) request;
    String screeningJson = toJson(screening);

    IndexRequestBuilder builder =
        esDao.getClient().prepareIndex(esDao.getConfig().getElasticsearchAlias(),
            esDao.getConfig().getElasticsearchDocType(), screening.getId());
    builder.setSource(screeningJson, XContentType.JSON);

    return builder.get();
  }

}
