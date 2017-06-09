package gov.ca.cwds.rest.services.cms;

import java.io.Serializable;

import javax.persistence.EntityExistsException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.cms.DrmsDocumentDao;
import gov.ca.cwds.data.persistence.cms.CmsKeyIdGenerator;
import gov.ca.cwds.data.persistence.cms.DrmsDocument;
import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.cms.PostedDrmsDocument;
import gov.ca.cwds.rest.services.CrudsService;
import gov.ca.cwds.rest.services.ServiceException;

/**
 * Business layer object to work on {@link DrmsDocument}
 * 
 * @author CWDS API Team
 */
public class DrmsDocumentService implements CrudsService {

  private static final Logger LOGGER = LoggerFactory.getLogger(DrmsDocumentService.class);

  private DrmsDocumentDao drmsDocumentDao;
  private StaffPersonIdRetriever staffPersonIdRetriever;

  /**
   * @param drmsDocumentDao
   * @param staffPersonIdRetriever
   */
  @Inject
  public DrmsDocumentService(DrmsDocumentDao drmsDocumentDao,
      StaffPersonIdRetriever staffPersonIdRetriever) {
    super();
    this.drmsDocumentDao = drmsDocumentDao;
    this.staffPersonIdRetriever = staffPersonIdRetriever;
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#create(gov.ca.cwds.rest.api.Request)
   */
  @Override
  public PostedDrmsDocument create(Request request) {
    assert request instanceof gov.ca.cwds.rest.api.domain.cms.DrmsDocument;

    gov.ca.cwds.rest.api.domain.cms.DrmsDocument drmsDocument =
        (gov.ca.cwds.rest.api.domain.cms.DrmsDocument) request;

    try {
      String lastUpdatedId = staffPersonIdRetriever.getStaffPersonId();
      DrmsDocument managed =
          new DrmsDocument(CmsKeyIdGenerator.generate(lastUpdatedId), drmsDocument, lastUpdatedId);
      managed = drmsDocumentDao.create(managed);
      return new PostedDrmsDocument(managed);
    } catch (EntityExistsException e) {
      LOGGER.info("drmsDocument already exists : {}", drmsDocument);
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
