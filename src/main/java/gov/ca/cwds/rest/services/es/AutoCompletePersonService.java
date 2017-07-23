package gov.ca.cwds.rest.services.es;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.google.inject.name.Named;

import gov.ca.cwds.data.es.ElasticSearchPerson;
import gov.ca.cwds.data.es.ElasticsearchDao;
import gov.ca.cwds.rest.api.domain.cms.SystemCodeCache;
import gov.ca.cwds.rest.api.domain.es.AutoCompletePerson;
import gov.ca.cwds.rest.api.domain.es.AutoCompletePersonRequest;
import gov.ca.cwds.rest.api.domain.es.AutoCompletePersonResponse;
import gov.ca.cwds.rest.resources.SimpleResourceService;
import gov.ca.cwds.rest.services.ServiceException;

/**
 * Business service for Intake Person Auto-complete.
 * 
 * @author CWDS API Team
 */
public class AutoCompletePersonService
    extends SimpleResourceService<String, AutoCompletePersonRequest, AutoCompletePersonResponse> {

  private static final Logger LOGGER = LoggerFactory.getLogger(AutoCompletePersonService.class);

  private ElasticsearchDao elasticsearchDao;

  private SystemCodeCache sysCodeCache;

  /**
   * Constructor
   * 
   * @param elasticsearchDao the ElasticSearch DAO
   * @param sysCodeCache system code cache
   */
  @Inject
  public AutoCompletePersonService(@Named("people.index") ElasticsearchDao elasticsearchDao,
      SystemCodeCache sysCodeCache) {
    this.elasticsearchDao = elasticsearchDao;
    this.sysCodeCache = sysCodeCache;
  }

  /**
   * Consolidate calls to Elasticsearch DAO in one place.
   * 
   * @param searchTerm search term(s)
   * @return complete domain object
   */
  protected AutoCompletePersonResponse callDao(final String searchTerm) {
    final ElasticSearchPerson[] hits = this.elasticsearchDao.searchPerson(searchTerm);
    final int len = hits != null ? hits.length : 0;
    List<AutoCompletePerson> list = new ArrayList<>(len);

    if (len > 0) {
      for (ElasticSearchPerson hit : hits) {
        LOGGER.info(hit.toString());
        list.add(new AutoCompletePerson(hit));
        LOGGER.info("AutoCompletePerson: {}", hit.toString());
      }
    } else {
      LOGGER.debug("No records found");
    }

    return new AutoCompletePersonResponse(list);
  }

  @Override
  protected AutoCompletePersonResponse handleRequest(AutoCompletePersonRequest req) {
    String searchTerm = req.getSearchTerm();
    if (StringUtils.isBlank(searchTerm)) {
      throw new ServiceException("Search term cannot be null.");
    }

    // Wildcard or no wildcard, that is the question.
    // Tune Elasticsearch field analysis.
    // TODO: #137794503: exclude reserved characters from queries.
    searchTerm = searchTerm.trim().toLowerCase().replace('^', ' ');
    // if (!searchTerm.endsWith("*")) {
    // searchTerm = searchTerm + "*";
    // }

    return callDao(searchTerm);
  }

  @Override
  protected AutoCompletePersonResponse handleFind(String searchForThis) {
    try {
      return callDao(searchForThis.trim().toLowerCase());
    } catch (Exception e) {
      throw new ServiceException("Something went wrong ...", e);
    }
  }

}
