package gov.ca.cwds.rest.services.es;

import static com.google.common.base.Preconditions.checkArgument;
import gov.ca.cwds.data.es.ElasticsearchDao;
import gov.ca.cwds.data.persistence.cms.ApiSystemCodeCache;
import gov.ca.cwds.rest.api.domain.es.IndexQueryRequest;
import gov.ca.cwds.rest.api.domain.es.IndexQueryResponse;
import gov.ca.cwds.rest.resources.SimpleResourceService;
import gov.ca.cwds.rest.services.ServiceException;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;


/**
 * Business service for Intake Index Query.
 * 
 * @author CWDS API Team
 */
public class IndexQueryService extends
    SimpleResourceService<String, IndexQueryRequest, IndexQueryResponse> {

  private static final Logger LOGGER = LoggerFactory.getLogger(IndexQueryService.class);

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
  public IndexQueryService(ElasticsearchDao elasticsearchDao, ApiSystemCodeCache sysCodeCache) {
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
  protected IndexQueryResponse handleRequest(IndexQueryRequest req) {
    checkArgument(req != null, "query cannot be Null or empty");
    @SuppressWarnings("unchecked")
    String query = new JSONObject((Map<String, String>) req.getQuery()).toString();
    if (StringUtils.isBlank(query)) {
      throw new ServiceException("query cannot be null.");
    }
    return new IndexQueryResponse(callDao(req.getIndex(), query));
  }

  @Override
  protected IndexQueryResponse handleFind(String searchForThis) {
    try {
      return new IndexQueryResponse(callDao(
          gov.ca.cwds.data.es.ElasticsearchDao.DEFAULT_PERSON_IDX_NM, searchForThis.trim()));
    } catch (Exception e) {
      throw new ServiceException("Something went wrong ...", e);
    }
  }

}
