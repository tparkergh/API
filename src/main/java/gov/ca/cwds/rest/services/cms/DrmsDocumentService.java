package gov.ca.cwds.rest.services.cms;

import javax.persistence.EntityExistsException;

import org.apache.commons.lang3.NotImplementedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.Dao;
import gov.ca.cwds.data.cms.DrmsDocumentDao;
import gov.ca.cwds.data.persistence.cms.CmsKeyIdGenerator;
import gov.ca.cwds.data.persistence.cms.DrmsDocument;
import gov.ca.cwds.rest.api.domain.cms.PostedDrmsDocument;
import gov.ca.cwds.rest.messages.MessageBuilder;
import gov.ca.cwds.rest.services.ServiceException;
import gov.ca.cwds.rest.services.TypedCrudsService;

/**
 * Business layer object to work on {@link DrmsDocument}
 * 
 * @author CWDS API Team
 */
public class DrmsDocumentService implements
    TypedCrudsService<String, gov.ca.cwds.rest.api.domain.cms.DrmsDocument, gov.ca.cwds.rest.api.domain.cms.DrmsDocument> {

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
  public PostedDrmsDocument create(gov.ca.cwds.rest.api.domain.cms.DrmsDocument request) {

    gov.ca.cwds.rest.api.domain.cms.DrmsDocument drmsDocument = request;

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
  public gov.ca.cwds.rest.api.domain.cms.DrmsDocument delete(String arg0) {
    throw new NotImplementedException("delete not implement");
  }

  @Override
  public gov.ca.cwds.rest.api.domain.cms.DrmsDocument find(String arg0) {
    throw new NotImplementedException("find not implement");
  }

  @Override
  public gov.ca.cwds.rest.api.domain.cms.DrmsDocument update(String arg0,
      gov.ca.cwds.rest.api.domain.cms.DrmsDocument arg1) {
    throw new NotImplementedException("update not implement");
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
   *
   * @param messageBuilder the messaging object to build error messages
   * @return The Id of the created DRMS Document
   * @throws ServiceException - serviceException
   */
  public String generateDrmsDocumentId(MessageBuilder messageBuilder) {
    PostedDrmsDocument postedDrmsDocument = null;
    try {
      String staffPersonId = staffPersonIdRetriever.getStaffPersonId();
      gov.ca.cwds.rest.api.domain.cms.DrmsDocument drmsDocument =
          gov.ca.cwds.rest.api.domain.cms.DrmsDocument.createDefaults(staffPersonId);
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
