package gov.ca.cwds.rest.services.es;

import gov.ca.cwds.data.es.ElasticsearchDao;
import gov.ca.cwds.data.persistence.cms.ApiSystemCodeCache;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.es.PersonQueryRequest;
import gov.ca.cwds.rest.api.domain.es.PersonQueryResponse;
import gov.ca.cwds.rest.resources.SimpleResourceService;
import gov.ca.cwds.rest.services.ServiceException;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;


/**
 * Business service for Intake Person Query.
 * 
 * @author CWDS API Team
 */
public class PersonQueryService extends
    SimpleResourceService<String, PersonQueryRequest, gov.ca.cwds.rest.api.Response> {

  private static final Logger LOGGER = LoggerFactory.getLogger(PersonQueryService.class);

  private ElasticsearchDao elasticsearchDao;
  @SuppressWarnings("unused")
  private ApiSystemCodeCache sysCodeCache;

  /**
   * Constructor
   * 
   * @param elasticsearchDao the ElasticSearch DAO
   * @param sysCodeCache system code cache
   */
  @Inject
  public PersonQueryService(ElasticsearchDao elasticsearchDao, ApiSystemCodeCache sysCodeCache) {
    this.elasticsearchDao = elasticsearchDao;
    this.sysCodeCache = sysCodeCache;
  }

  /**
   * Consolidate calls to Elasticsearch DAO in one place.
   * 
   * @param query search term(s)
   * @return complete domain object
   */
  protected String callDao(final String query) {

    return this.elasticsearchDao.searchPersonQuery(query);
  }

  @Override
  protected Response handleRequest(PersonQueryRequest req) {
    String query = new JSONObject((Map<String, String>) req.getQuery()).toString();
    if (StringUtils.isBlank(query)) {
      throw new ServiceException("query cannot be null.");
    }
    return new PersonQueryResponse(callDao(query));
  }

  @Override
  protected Response handleFind(String searchForThis) {
    try {
      return new PersonQueryResponse(callDao(searchForThis.trim()));
    } catch (Exception e) {
      throw new ServiceException("Something went wrong ...", e);
    }
  }

}
