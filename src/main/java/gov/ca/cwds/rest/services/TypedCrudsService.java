package gov.ca.cwds.rest.services;

import java.io.Serializable;

import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.DomainObject;

/**
 * Interface for business {@link Service} which perform CRUDS operations. This interface strongly
 * types a service's primary key, request, and response.
 * 
 * @author CWDS API Team
 * @param <P> primary key type
 * @param <Q> request (input) type
 * @param <S> response (output) type
 */
public interface TypedCrudsService<P extends Serializable, Q extends Request, S extends Response>
    extends Service {

  /**
   * Find object by primaryKey
   * 
   * @param primaryKey The primaryKey of the {@link DomainObject} to find.
   * 
   * @return The {@link Response} containing the found object, null if not found.
   */
  public S find(P primaryKey);

  /**
   * Delete object by id
   * 
   * @param primaryKey The primaryKey of the {@link DomainObject} to delete.
   * 
   * @return The {@link Response} containing the deleted object, null if not found.
   */
  public S delete(P primaryKey);

  /**
   * Create object
   * 
   * @param request {@link Request} with a {@link DomainObject} to create.
   * 
   * @return The {@link Response}
   */
  public S create(Q request);

  /**
   * Update object
   * 
   * @param primaryKey The primaryKey of the {@link DomainObject} to update.
   * @param request {@link Request} with a {@link DomainObject} to update.
   * 
   * @return The {@link Response}
   */
  public S update(P primaryKey, Q request);
}
