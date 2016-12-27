package gov.ca.cwds.rest.resources;

import java.io.Serializable;
import java.lang.annotation.Annotation;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.ImmutableMap;
import com.google.inject.Inject;

import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.services.ServiceException;
import gov.ca.cwds.rest.services.TypedCrudsService;

/**
 * Implements the {@link ResourceDelegate} and passes work to the service layer. All
 * {@link Resource} should decorate this class. Resources will delegate to this class with the
 * decoration being swagger {@link Annotation} classes for documentation and Jersey
 * {@link Annotation} for RESTful resources.
 * 
 * @author CWDS API Team
 * @param <P> primary key type
 * @param <Q> request type
 * @param <S> response type
 */
public final class TypedServiceBackedResourceDelegate<P extends Serializable, Q extends Request, S extends gov.ca.cwds.rest.api.Response>
    implements TypedResourceDelegate<P, Q> {
  private static final Logger LOGGER =
      LoggerFactory.getLogger(TypedServiceBackedResourceDelegate.class);

  /**
   * The wrapped CRUD service.
   */
  private TypedCrudsService<P, Q, S> service;

  /**
   * Constructor
   * 
   * @param crudsService The crudsService for this resource.
   */
  @Inject
  public TypedServiceBackedResourceDelegate(TypedCrudsService<P, Q, S> crudsService) {
    this.service = crudsService;
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.resources.ResourceDelegate#get(java.io.Serializable)
   */
  @Override
  public Response get(P id) {
    S response = service.find(id);
    if (response != null) {
      return Response.ok(response).build();
    } else {
      return Response.status(Response.Status.NOT_FOUND).entity(null).build();
    }
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.resources.ResourceDelegate#delete(java.io.Serializable)
   */
  @Override
  public Response delete(P id) {
    gov.ca.cwds.rest.api.Response response = service.delete(id);
    if (response != null) {
      return Response.status(Response.Status.OK).build();
    } else {
      return Response.status(Response.Status.NOT_FOUND).entity(null).build();
    }
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.resources.ResourceDelegate#create(gov.ca.cwds.rest.api.Request)
   */
  @Override
  public Response create(Q request) {
    Response response = null;
    try {
      response = Response.status(Response.Status.CREATED).entity(service.create(request)).build();
    } catch (ServiceException e) {
      if (e.getCause() instanceof EntityExistsException) {
        response = Response.status(Response.Status.CONFLICT).entity(null).build();
      } else {
        LOGGER.error("Unable to handle request", e);
        response = Response.status(Response.Status.SERVICE_UNAVAILABLE).entity(null).build();
      }
    }
    return response;
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.resources.ResourceDelegate#update(java.io.Serializable,
   *      gov.ca.cwds.rest.api.Request)
   */
  @Override
  public Response update(P id, Q request) {
    Response response = null;
    try {
      response = Response.status(Response.Status.OK).entity(service.update(id, request)).build();
    } catch (ServiceException e) {
      Object entity = null;
      if (e.getCause() instanceof EntityNotFoundException) {
        if (StringUtils.isNotEmpty(e.getMessage())) {
          ImmutableMap<String, String> map =
              ImmutableMap.<String, String>builder().put("message", e.getMessage()).build();
          entity = map;
        }

        response = Response.status(Response.Status.NOT_FOUND).entity(entity).build();
      } else {
        LOGGER.error("Unable to handle request", e);
        response = Response.status(Response.Status.SERVICE_UNAVAILABLE).entity(null).build();
      }
    }
    return response;
  }

  /**
   * Exposes the wrapped {@link TypedCrudsService}.
   * 
   * @return the underlying, wrapped {@link TypedCrudsService}
   */
  public TypedCrudsService<P, Q, S> getService() {
    return service;
  }
}
