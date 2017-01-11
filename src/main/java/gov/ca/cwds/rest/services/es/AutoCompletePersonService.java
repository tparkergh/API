package gov.ca.cwds.rest.services.es;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.es.ElasticSearchPerson;
import gov.ca.cwds.data.es.ElasticsearchDao;
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

  /**
   * Constructor
   * 
   * @param elasticsearchDao the ElasticSearch DAO
   */
  @Inject
  public AutoCompletePersonService(ElasticsearchDao elasticsearchDao) {
    this.elasticsearchDao = elasticsearchDao;
  }

  @Override
  protected AutoCompletePersonResponse handleRequest(AutoCompletePersonRequest req) {

    String searchTerm = req.getSearchTerm();
    if (StringUtils.isBlank(searchTerm)) {
      throw new ServiceException("Search term cannot be null.");
    }

    searchTerm = searchTerm.trim().toLowerCase();
    if (!searchTerm.endsWith("*")) {
      searchTerm = searchTerm + "*";
    }

    final ElasticSearchPerson[] hits = this.elasticsearchDao.autoCompletePerson(searchTerm);
    final int len = hits != null ? hits.length : 0;
    List<AutoCompletePerson> list = new ArrayList<>(len);

    if (len > 0) {

      for (ElasticSearchPerson hit : hits) {
        LOGGER.info(hit.toString());
        list.add(new AutoCompletePerson(hit));
      }

    } else {
      LOGGER.info("No records found");
    }

    return new AutoCompletePersonResponse(list);
  }

  @Override
  protected AutoCompletePersonResponse handleFind(String arg0) {
    // No-op for now.
    return null;
  }

}
