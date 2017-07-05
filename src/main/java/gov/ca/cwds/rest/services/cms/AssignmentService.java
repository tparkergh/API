package gov.ca.cwds.rest.services.cms;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.Dao;
import gov.ca.cwds.data.cms.AssignmentDao;
import gov.ca.cwds.data.persistence.cms.Assignment;
import gov.ca.cwds.data.persistence.cms.CmsKeyIdGenerator;
import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.domain.cms.PostedAssignment;
import gov.ca.cwds.rest.services.CrudsService;
import gov.ca.cwds.rest.services.ServiceException;

/**
 * Business layer object serves {@link Assignment}.
 * 
 * @author CWDS API Team
 */
public class AssignmentService implements CrudsService {

  private static final Logger LOGGER = LoggerFactory.getLogger(AssignmentService.class);

  private AssignmentDao assignmentDao;
  private StaffPersonIdRetriever staffPersonIdRetriever;

  /**
   * Constructor
   * 
   * @param assignmentDao The {@link Dao} handling
   *        {@link gov.ca.cwds.data.persistence.cms.Assignment} objects.
   * @param staffPersonIdRetriever the staffPersonIdRetriever
   */
  @Inject
  public AssignmentService(AssignmentDao assignmentDao,
      StaffPersonIdRetriever staffPersonIdRetriever) {
    this.assignmentDao = assignmentDao;
    this.staffPersonIdRetriever = staffPersonIdRetriever;
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#find(java.io.Serializable)
   */
  @Override
  public gov.ca.cwds.rest.api.domain.cms.Assignment find(Serializable primaryKey) {
    assert primaryKey instanceof String;

    gov.ca.cwds.data.persistence.cms.Assignment persistedAssignment =
        assignmentDao.find(primaryKey);
    if (persistedAssignment != null) {
      return new gov.ca.cwds.rest.api.domain.cms.Assignment(persistedAssignment);
    }
    return null;
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#delete(java.io.Serializable)
   */
  @Override
  public gov.ca.cwds.rest.api.domain.cms.Assignment delete(Serializable primaryKey) {
    assert primaryKey instanceof String;
    gov.ca.cwds.data.persistence.cms.Assignment persistedAssignment =
        assignmentDao.delete(primaryKey);
    if (persistedAssignment != null) {
      return new gov.ca.cwds.rest.api.domain.cms.Assignment(persistedAssignment);
    }
    return null;
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#create(gov.ca.cwds.rest.api.Request)
   */
  @Override
  public PostedAssignment create(Request request) {
    assert request instanceof gov.ca.cwds.rest.api.domain.cms.Assignment;
    gov.ca.cwds.rest.api.domain.cms.Assignment assignment =
        (gov.ca.cwds.rest.api.domain.cms.Assignment) request;
    return create(assignment, null);
  }

  /**
   * This createWithSingleTimestamp is used for the referrals to maintian the same timestamp for the
   * whole transaction
   * 
   * @param request - request
   * @param timestamp - timestamp
   * @return the PostedAssignment
   */
  public PostedAssignment createWithSingleTimestamp(Request request, Date timestamp) {
    assert request instanceof gov.ca.cwds.rest.api.domain.cms.Assignment;

    gov.ca.cwds.rest.api.domain.cms.Assignment assignment =
        (gov.ca.cwds.rest.api.domain.cms.Assignment) request;
    return create(assignment, timestamp);

  }

  /**
   * This private method is created to handle to single referral and referrals with single timestamp
   * 
   */
  private PostedAssignment create(gov.ca.cwds.rest.api.domain.cms.Assignment assignment,
      Date timestamp) {
    try {
      String lastUpdatedId = staffPersonIdRetriever.getStaffPersonId();
      Assignment managed;
      if (timestamp == null) {
        managed =
            new Assignment(CmsKeyIdGenerator.generate(lastUpdatedId), assignment, lastUpdatedId);
      } else {
        managed = new Assignment(CmsKeyIdGenerator.generate(lastUpdatedId), assignment,
            lastUpdatedId, timestamp);

      }
      managed = assignmentDao.create(managed);
      if (managed.getId() == null) {
        throw new ServiceException("Assignment ID cannot be null");
      }
      return new PostedAssignment(managed);
    } catch (EntityExistsException e) {
      LOGGER.info("Assignment already exists : {}", assignment);
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
  public gov.ca.cwds.rest.api.domain.cms.Assignment update(Serializable primaryKey,
      Request request) {
    assert primaryKey instanceof String;
    assert request instanceof gov.ca.cwds.rest.api.domain.cms.Assignment;
    gov.ca.cwds.rest.api.domain.cms.Assignment assignment =
        (gov.ca.cwds.rest.api.domain.cms.Assignment) request;

    try {
      String lastUpdatedId = staffPersonIdRetriever.getStaffPersonId();
      Assignment managed = new Assignment((String) primaryKey, assignment, lastUpdatedId);
      managed = assignmentDao.update(managed);
      return new gov.ca.cwds.rest.api.domain.cms.Assignment(managed);
    } catch (EntityNotFoundException e) {
      LOGGER.info("Assignment not found : {}", assignment);
      throw new ServiceException(e);
    }
  }

}
