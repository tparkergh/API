package gov.ca.cwds.rest.services.cms;

import java.io.Serializable;

import org.apache.commons.lang3.NotImplementedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.cms.CmsDocument;
import gov.ca.cwds.rest.jdbi.Dao;
import gov.ca.cwds.rest.jdbi.cms.CmsDocumentDao;
import gov.ca.cwds.rest.services.CrudsService;

/**
 * Business layer object to work on {@link CmsDocument}.
 * 
 * @author CWDS API Team
 */
public class CmsDocumentService implements CrudsService {
  private static final Logger LOGGER = LoggerFactory.getLogger(CmsDocumentService.class);

  private CmsDocumentDao dao;

  /**
   * Constructor.
   * 
   * @param dao The {@link Dao} handling {@link CmsDocumentDao} objects.
   */
  @Inject
  public CmsDocumentService(CmsDocumentDao dao) {
    this.dao = dao;
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#find(java.io.Serializable)
   */
  @Override
  public CmsDocument find(Serializable primaryKey) {
    assert (primaryKey instanceof String);
    LOGGER.info("primaryKey=" + primaryKey);

    CmsDocument retval = null;
    String base64Doc = "";

    gov.ca.cwds.rest.api.persistence.cms.CmsDocument doc = dao.find(primaryKey);
    if (doc != null) {
      // Trim strings.
      doc.setCompressionMethod(
          doc.getCompressionMethod() != null ? doc.getCompressionMethod().trim() : "");
      doc.setDocAuth(doc.getDocAuth() != null ? doc.getDocAuth().trim() : "");
      doc.setDocName(doc.getDocName() != null ? doc.getDocName().trim() : "");
      doc.setDocServ(doc.getDocServ() != null ? doc.getDocServ().trim() : "");

      base64Doc = dao.decompressDoc(doc);
      retval = new CmsDocument(doc);
      retval.setBase64Blob(base64Doc);
    } else {
      LOGGER.warn("EMPTY document!");
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
    throw new NotImplementedException("DELETE NOT IMPLEMENTED!");
  }

  /**
   * <p>
   * <strong>NOT YET IMPLEMENTED!</strong>
   * </p>
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#create(gov.ca.cwds.rest.api.Request)
   */
  @Override
  public CmsDocument create(Request request) {
    // assert request instanceof CmsDocument;
    throw new NotImplementedException("CREATE NOT IMPLEMENTED!");
  }

  /**
   * <p>
   * <strong>NOT YET IMPLEMENTED!</strong>
   * </p>
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#update(java.io.Serializable,
   *      gov.ca.cwds.rest.api.Request)
   */
  @Override
  public CmsDocument update(Serializable primaryKey, Request request) {
    assert primaryKey instanceof String;
    throw new NotImplementedException("UPDATE NOT IMPLEMENTED!");
  }

}
