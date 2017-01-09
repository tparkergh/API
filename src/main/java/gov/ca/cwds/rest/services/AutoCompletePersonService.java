package gov.ca.cwds.rest.services;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.es.ElasticSearchPerson;
import gov.ca.cwds.data.es.ElasticsearchDao;
import gov.ca.cwds.rest.api.domain.es.AutoCompletePerson;
import gov.ca.cwds.rest.api.domain.es.AutoCompletePersonRequest;
import gov.ca.cwds.rest.api.domain.es.AutoCompletePersonResponse;
import gov.ca.cwds.rest.resources.SimpleResourceService;


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

    final ElasticSearchPerson[] hits =
        this.elasticsearchDao.autoCompletePerson(req.getSearchCriteria());

    List<AutoCompletePerson> list = new ArrayList<>(hits.length);
    if (hits.length > 0) {
      // Convert ElasticSearchPerson to AutoCompletePerson.
      for (ElasticSearchPerson hit : hits) {
        LOGGER.info(hit.toString());
        list.add(new AutoCompletePerson(hit));
      }

    } else {
      LOGGER.info("No records found");
    }

    return new AutoCompletePersonResponse(list.toArray(new AutoCompletePerson[0]));
  }

  @Override
  protected AutoCompletePersonResponse handleFind(String arg0) {
    // No-op for now.
    return null;
  }

}
