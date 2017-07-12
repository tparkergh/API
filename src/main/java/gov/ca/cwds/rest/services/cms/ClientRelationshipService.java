package gov.ca.cwds.rest.services.cms;

import gov.ca.cwds.data.Dao;
import gov.ca.cwds.data.cms.ClientRelationshipDao;
import gov.ca.cwds.data.persistence.cms.CmsKeyIdGenerator;
import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.domain.cms.ClientRelationship;
import gov.ca.cwds.rest.api.domain.cms.PostedClientRelationship;
import gov.ca.cwds.rest.services.CrudsService;
import gov.ca.cwds.rest.services.ServiceException;

import java.io.Serializable;

import javax.persistence.EntityExistsException;

import org.apache.commons.lang3.NotImplementedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

/**
 * Business layer object to work on {@link ClientRelationship}
 * 
 * @author CWDS API Team
 */
public class ClientRelationshipService implements CrudsService {

  private static final Logger LOGGER = LoggerFactory.getLogger(ClientRelationshipService.class);

  private ClientRelationshipDao clientRelationshipDao;
  private StaffPersonIdRetriever staffPersonIdRetriever;

  /**
   * Constructor
   * 
   * @param clientRelationshipDao The {@link Dao} handling
   *        {@link gov.ca.cwds.data.persistence.cms.ClientRelationship} objects.
   * @param staffPersonIdRetriever the staffPersonIdRetriever
   */
  @Inject
  public ClientRelationshipService(ClientRelationshipDao clientRelationshipDao,
      StaffPersonIdRetriever staffPersonIdRetriever) {
    this.clientRelationshipDao = clientRelationshipDao;
    this.staffPersonIdRetriever = staffPersonIdRetriever;
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#find(java.io.Serializable)
   */
  @Override
  public gov.ca.cwds.rest.api.domain.cms.PostedClientRelationship find(Serializable primaryKey) {
    assert primaryKey instanceof String;
    gov.ca.cwds.data.persistence.cms.ClientRelationship persistedClientRelationship =
        clientRelationshipDao.find(primaryKey);
    if (persistedClientRelationship != null) {
      return new gov.ca.cwds.rest.api.domain.cms.PostedClientRelationship(
          persistedClientRelationship);
    }
    return null;
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#delete(java.io.Serializable)
   */
  @Override
  public gov.ca.cwds.rest.api.domain.cms.ClientRelationship delete(Serializable primaryKey) {
    assert primaryKey instanceof String;
    throw new NotImplementedException("Delete is not implemented");
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#create(gov.ca.cwds.rest.api.Request)
   */
  @Override
  public PostedClientRelationship create(Request request) {
    assert request instanceof gov.ca.cwds.rest.api.domain.cms.ClientRelationship;
    ClientRelationship clientRelationship = (ClientRelationship) request;

    try {
      String lastUpdatedId = staffPersonIdRetriever.getStaffPersonId();
      gov.ca.cwds.data.persistence.cms.ClientRelationship managed =
          new gov.ca.cwds.data.persistence.cms.ClientRelationship(
              CmsKeyIdGenerator.generate(lastUpdatedId), clientRelationship, lastUpdatedId);
      managed = clientRelationshipDao.create(managed);
      return new PostedClientRelationship(managed);
    } catch (EntityExistsException e) {
      LOGGER.info("ClientRelationship already exists : {}", clientRelationship);
      throw new ServiceException(e);
    }
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#update(java.io.Serializable,
   *      gov.ca.cwds.rest.api.Request)
   */
  @Override
  public ClientRelationship update(Serializable primaryKey, Request request) {
    assert primaryKey instanceof String;
    throw new NotImplementedException("Update is not implemented");
  }

}
