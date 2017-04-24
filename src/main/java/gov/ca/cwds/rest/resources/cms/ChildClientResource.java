package gov.ca.cwds.rest.resources.cms;

import static gov.ca.cwds.rest.core.Api.RESOURCE_CHILDCLIENT;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.http.HttpStatus;

import com.google.inject.Inject;

import gov.ca.cwds.inject.ChildClientServiceBackedResource;
import gov.ca.cwds.rest.api.domain.cms.ChildClient;
import gov.ca.cwds.rest.resources.ResourceDelegate;
import io.dropwizard.hibernate.UnitOfWork;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * A resource providing a RESTful interface for {@link ChildClient}. It delegates functions to
 * {@link ResourceDelegate}. It decorates the {@link ResourceDelegate} not in functionality but
 * with @see <a href= "https://github.com/swagger-api/swagger-core/wiki/Annotations-1.5.X">Swagger
 * Annotations</a> and
 * <a href="https://jersey.java.net/documentation/latest/user-guide.html#jaxrs-resources">Jersey
 * Annotations</a>
 * 
 * @author CWDS API Team
 */
@Api(value = RESOURCE_CHILDCLIENT, tags = RESOURCE_CHILDCLIENT)
@Path(value = RESOURCE_CHILDCLIENT)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ChildClientResource {

  private ResourceDelegate resourceDelegate;

  /**
   * Constructor
   * 
   * @param resourceDelegate The resourceDelegate to delegate to.
   */
  @Inject
  public ChildClientResource(@ChildClientServiceBackedResource ResourceDelegate resourceDelegate) {
    this.resourceDelegate = resourceDelegate;
  }

  /**
   * Finds a Child Client by client id.
   * 
   * @param victimId the victimId is unique key for ChildClient
   * 
   * @return the response
   */
  @UnitOfWork(value = "cms")
  @GET
  @Path("/victimId={victimId}")
  @ApiResponses(value = {@ApiResponse(code = 401, message = "Not Authorized"),
      @ApiResponse(code = 404, message = "Not found"),
      @ApiResponse(code = 406, message = "Accept Header not supported")})
  @ApiOperation(value = "Find childClient by victimId", response = ChildClient.class, code = 200)
  public Response get(@PathParam("victimId") @ApiParam(required = true, value = "The victim id",
      example = "td89slaz98") String victimId) {
    return resourceDelegate.get(victimId);
  }

  /**
   * Delete a Child Client by client id.
   * 
   * @param victimId the victimId is unique key for ChildClient
   * 
   * @return {@link Response}
   */
  @UnitOfWork(value = "cms")
  @DELETE
  @Path("/victimId={victimId}")
  @ApiResponses(value = {@ApiResponse(code = 401, message = "Not Authorized")})
  @ApiOperation(value = "Delete ChildClient", code = HttpStatus.SC_OK, response = Object.class)
  public Response delete(@PathParam("victimId") @ApiParam(required = true, value = "The victim id",
      example = "td89slaz98") String victimId) {
    return resourceDelegate.delete(victimId);
  }

  /**
   * Create an {@link ChildClient}
   * 
   * @param childClient The {@link ChildClient}
   * 
   * @return The {@link Response}
   */
  @UnitOfWork(value = "cms")
  @POST
  @ApiResponses(value = {@ApiResponse(code = 400, message = "Unable to process JSON"),
      @ApiResponse(code = 401, message = "Not Authorized"),
      @ApiResponse(code = 406, message = "Accept Header not supported"),
      @ApiResponse(code = 409, message = "Conflict - already exists"),
      @ApiResponse(code = 422, message = "Unable to validate ChildClient")})
  @Consumes(value = MediaType.APPLICATION_JSON)
  @ApiOperation(value = "Create ChildClient", code = HttpStatus.SC_CREATED,
      response = ChildClient.class)
  public Response create(
      @Valid @ApiParam(hidden = false, required = true) ChildClient childClient) {
    return resourceDelegate.create(childClient);
  }

  /**
   * Update an {@link ChildClient}
   * 
   * @param childClient {@link ChildClient}
   *
   * @return The {@link Response}
   */
  @UnitOfWork(value = "cms")
  @PUT
  @ApiResponses(value = {@ApiResponse(code = 400, message = "Unable to process JSON"),
      @ApiResponse(code = 401, message = "Not Authorized"),
      @ApiResponse(code = 404, message = "not found"),
      @ApiResponse(code = 406, message = "Accept Header not supported"),
      @ApiResponse(code = 422, message = "Unable to validate ChildClient")})
  @Consumes(value = MediaType.APPLICATION_JSON)
  @ApiOperation(value = "Update ChildClient", code = HttpStatus.SC_NO_CONTENT,
      response = Object.class)
  public Response update(
      @Valid @ApiParam(hidden = false, required = true) ChildClient childClient) {
    return resourceDelegate.update(null, childClient);
  }

}
