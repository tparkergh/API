package gov.ca.cwds.rest.services.cms;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang3.NotImplementedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.PostedScreening;
import gov.ca.cwds.rest.api.domain.ScreeningResponse;
import gov.ca.cwds.rest.api.domain.cms.CmsDocReferralClient;
import gov.ca.cwds.rest.api.persistence.cms.CmsDocument;
import gov.ca.cwds.rest.jdbi.cms.CmsDocReferralClientDao;
import gov.ca.cwds.rest.jdbi.cms.CmsDocumentDao;
import gov.ca.cwds.rest.services.CrudsService;

/**
 * Business layer object to work on {@link CmsDocReferralClient}.
 * 
 * @author CWDS API Team
 */
public class CmsDocReferralClientService implements CrudsService {

  private static final Logger LOGGER = LoggerFactory.getLogger(CmsDocReferralClientService.class);

  private CmsDocReferralClientDao dao;
  private CmsDocumentDao docDao;

  /**
   * Core constructor.
   * 
   * @param dao The referral client document DAO
   * @param docDao The document blob DAO
   */
  @Inject
  public CmsDocReferralClientService(CmsDocReferralClientDao dao, CmsDocumentDao docDao) {
    this.dao = dao;
    this.docDao = docDao;
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#find(java.io.Serializable)
   */
  @Override
  public CmsDocReferralClient find(Serializable primaryKey) {
    assert (primaryKey instanceof String);

    CmsDocReferralClient retval = null;

    final String key = (String) primaryKey;
    LOGGER.info("primaryKey=" + key);
    List<gov.ca.cwds.rest.api.persistence.cms.CmsDocReferralClient> docs =
        dao.listDocReferralClient(key);
    if (docs != null) {
      retval = new CmsDocReferralClient(docs);
      CmsDocument blobDoc = docDao.find(key);
      if (blobDoc != null) {
        retval.getCmsDocument().setContent(docDao.decompressDoc(blobDoc));
      }
    }
    return retval;
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#delete(java.io.Serializable)
   */
  @Override
  public Response delete(Serializable primaryKey) {
    assert primaryKey instanceof String;
    throw new NotImplementedException("Delete is not implemented");
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#create(gov.ca.cwds.rest.api.Request)
   */
  @Override
  public PostedScreening create(Request request) {
    // assert request instanceof ScreeningReference;
    throw new NotImplementedException("Create is not implemented");
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#update(java.io.Serializable,
   *      gov.ca.cwds.rest.api.Request)
   */
  @Override
  public ScreeningResponse update(Serializable primaryKey, Request request) {
    assert primaryKey instanceof Long;
    // assert request instanceof ScreeningRequest;
    throw new NotImplementedException("Update is not implemented");
  }

}
