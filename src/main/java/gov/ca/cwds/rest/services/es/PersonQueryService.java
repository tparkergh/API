package gov.ca.cwds.rest.services.es;

import static com.google.common.base.Preconditions.checkArgument;
import gov.ca.cwds.data.es.ElasticsearchDao;
import gov.ca.cwds.data.persistence.cms.ApiSystemCodeCache;
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
    SimpleResourceService<String, PersonQueryRequest, PersonQueryResponse> {

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
   * @param index the name of index to search
   * @param query the elasticsearch query
   * @return complete domain object
   */
  protected String callDao(final String index, final String query) {

    return this.elasticsearchDao.searchIndexByQuery(index, query);
  }

  @Override
  protected PersonQueryResponse handleRequest(PersonQueryRequest req) {
    checkArgument(req != null, "query cannot be Null or empty");
    @SuppressWarnings("unchecked")
    String query = new JSONObject((Map<String, String>) req.getQuery()).toString();
    if (StringUtils.isBlank(query)) {
      throw new ServiceException("query cannot be null.");
    }
    return new PersonQueryResponse(callDao(req.getIndex(), query));
  }

  @Override
  protected PersonQueryResponse handleFind(String searchForThis) {
    try {
      return new PersonQueryResponse(callDao(
          gov.ca.cwds.data.es.ElasticsearchDao.DEFAULT_PERSON_IDX_NM, searchForThis.trim()));
    } catch (Exception e) {
      throw new ServiceException("Something went wrong ...", e);
    }
  }

}
