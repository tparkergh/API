package gov.ca.cwds.rest.services.cms;

import java.io.Serializable;

import javax.persistence.EntityExistsException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.Dao;
import gov.ca.cwds.data.cms.LongTextDao;
import gov.ca.cwds.data.persistence.cms.LongText;
import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.cms.PostedLongText;
import gov.ca.cwds.rest.services.CrudsService;
import gov.ca.cwds.rest.services.ServiceException;
import gov.ca.cwds.rest.util.IdGenerator;

/**
 * Business layer object to work on {@link LongText}
 * 
 * @author CWDS API Team
 */
public class LongTextService implements CrudsService {

  private static final Logger LOGGER = LoggerFactory.getLogger(LongTextService.class);

  private LongTextDao longTextDao;

  /**
   * Constructor
   * 
   * @param longTextDao {@link Dao} handling {@link gov.ca.cwds.data.persistence.cms.LongText}
   *        objects
   */
  @Inject
  public LongTextService(LongTextDao longTextDao) {
    super();
    this.longTextDao = longTextDao;
  }


  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#create(gov.ca.cwds.rest.api.Request)
   */
  @Override
  public PostedLongText create(Request request) {
    assert request instanceof gov.ca.cwds.rest.api.domain.cms.LongText;

    gov.ca.cwds.rest.api.domain.cms.LongText longText =
        (gov.ca.cwds.rest.api.domain.cms.LongText) request;

    try {
      // TODO : refactor to actually determine who is updating. 'q1p' for now - #136737071 - Tech
      // Debt: Legacy Service classes must use Staff ID for last update ID value

      LongText managed = new LongText(IdGenerator.randomString(10), longText, "q1p");
      managed = longTextDao.create(managed);
      return new PostedLongText(managed);
    } catch (EntityExistsException e) {
      LOGGER.info("LongText already exists : {}", longText);
      throw new ServiceException(e);
    }
  }


  @Override
  public Response find(Serializable arg0) {

    return null;
  }


  @Override
  public Response delete(Serializable arg0) {

    return null;
  }


  @Override
  public Response update(Serializable arg0, Request arg1) {

    return null;
  }


}
