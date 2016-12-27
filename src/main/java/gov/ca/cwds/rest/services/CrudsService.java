package gov.ca.cwds.rest.services;

import java.io.Serializable;

import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.DomainObject;

/**
 * Interface for business {@link Service} which perform CRUDS operations
 * 
 * @author CWDS API Team
 */
public interface CrudsService extends Service {

  /**
   * Find object by primaryKey
   * 
   * @param primaryKey The primaryKey of the {@link DomainObject} to find.
   * 
   * @return The {@link Response} containing the found object, null if not found.
   */
  public Response find(Serializable primaryKey);

  /**
   * Delete object by id
   * 
   * @param primaryKey The primaryKey of the {@link DomainObject} to delete.
   * 
   * @return The {@link Response} containing the deleted object, null if not found.
   */
  public Response delete(Serializable primaryKey);

  /**
   * Create object
   * 
   * @param request {@link Request} with a {@link DomainObject} to create.
   * 
   * @return The {@link Response}
   */
  public Response create(Request request);

  /**
   * Update object
   * 
   * @param primaryKey The primaryKey of the {@link DomainObject} to update.
   * @param request {@link Request} with a {@link DomainObject} to update.
   * 
   * @return The {@link Response}
   */
  public Response update(Serializable primaryKey, Request request);
}
