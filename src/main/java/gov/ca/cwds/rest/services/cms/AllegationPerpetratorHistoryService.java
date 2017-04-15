package gov.ca.cwds.rest.services.cms;

import java.io.Serializable;

import javax.persistence.EntityExistsException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.Dao;
import gov.ca.cwds.data.cms.AllegationPerpetratorHistoryDao;
import gov.ca.cwds.data.persistence.cms.AllegationPerpetratorHistory;
import gov.ca.cwds.data.persistence.cms.CmsKeyIdGenerator;
import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.cms.PoastedAllegationPerpetratorHistory;
import gov.ca.cwds.rest.services.CrudsService;
import gov.ca.cwds.rest.services.ServiceException;

/**
 * Business layer object to work on {@link AllegationPerpetratorHistory}
 * 
 * @author CWDS API Team
 */
public class AllegationPerpetratorHistoryService implements CrudsService {

  private static final Logger LOGGER =
      LoggerFactory.getLogger(AllegationPerpetratorHistoryService.class);

  private AllegationPerpetratorHistoryDao allegationPerpetratorHistoryDao;

  /**
   * Constructor
   * 
   * @param allegationPerpetratorHistoryDao The {@link Dao} handling
   *        {@link gov.ca.cwds.data.persistence.cms.AllegationPerpetratorHistory} objects.
   */
  @Inject
  public AllegationPerpetratorHistoryService(
      AllegationPerpetratorHistoryDao allegationPerpetratorHistoryDao) {
    this.allegationPerpetratorHistoryDao = allegationPerpetratorHistoryDao;
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#create(gov.ca.cwds.rest.api.Request)
   */
  @Override
  public PoastedAllegationPerpetratorHistory create(Request request) {
    assert request instanceof gov.ca.cwds.rest.api.domain.cms.AllegationPerpetratorHistory;

    gov.ca.cwds.rest.api.domain.cms.AllegationPerpetratorHistory allegationPerpetratorHistory =
        (gov.ca.cwds.rest.api.domain.cms.AllegationPerpetratorHistory) request;

    try {
      // TODO : refactor to actually determine who is updating. 'q1p' for now - #136737071 - Tech
      // Debt: Legacy Service classes must use Staff ID for last update ID value
      AllegationPerpetratorHistory managed = new AllegationPerpetratorHistory(
          CmsKeyIdGenerator.cmsIdGenertor(null), allegationPerpetratorHistory, "q1p");
      managed = allegationPerpetratorHistoryDao.create(managed);
      return new PoastedAllegationPerpetratorHistory(managed);
    } catch (EntityExistsException e) {
      LOGGER.info("AllegationPerpetratorHistory already exists : {}", allegationPerpetratorHistory);
      throw new ServiceException(e);
    }
  }

  @Override
  public Response delete(Serializable arg0) {
    return null;
  }

  @Override
  public Response find(Serializable arg0) {
    return null;
  }

  @Override
  public Response update(Serializable arg0, Request arg1) {
    return null;
  }

}
