package gov.ca.cwds.rest.services.cms;

import com.google.inject.spi.Message;
import gov.ca.cwds.rest.messages.MessageBuilder;
import java.io.Serializable;

import javax.persistence.EntityExistsException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.Dao;
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
   * @param drmsDocumentDao {@link Dao} handling
   *        {@link gov.ca.cwds.data.persistence.cms.DrmsDocument} objects
   *        {@link gov.ca.cwds.data.persistence.cms.DrmsDocument} objects
   * @param staffPersonIdRetriever the staffPersonIdRetriever
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



    /**
   * <blockquote>
   *
   * <pre>
   * BUSINESS RULE: "R - 07577" - Create Dummy Docs for Referral
   *
   * When Referral is Posted, it creates three dummy document values in the drmsDocument and
   * assigned the identifer in the referrals(drmsAllegationDescriptionDoc, drmsErReferralDoc,
   * drmsInvestigationDoc).
   *
   * </pre>
   *
   * </blockquote>
   */
  public String generateDrmsDocumentId(MessageBuilder messageBuilder) throws ServiceException {
    PostedDrmsDocument postedDrmsDocument = null;
    try {
      String staffPersonId = staffPersonIdRetriever.getStaffPersonId();
      gov.ca.cwds.rest.api.domain.cms.DrmsDocument drmsDocument = gov.ca.cwds.rest.api.domain.cms.DrmsDocument
          .createDefaults(staffPersonId);
      postedDrmsDocument = create(drmsDocument);
    } catch (ServiceException e) {
      String message = e.getMessage();
      messageBuilder.addMessageAndLog(message, e, LOGGER);
    }
    if (postedDrmsDocument == null) {
      throw new ServiceException("Unable to Create DRMS Documents");
    }
    return postedDrmsDocument.getId();
  }
}
