package gov.ca.cwds.rest.resources.cms;

import static gov.ca.cwds.rest.core.Api.RESOURCE_ASSIGNMENT;

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

import gov.ca.cwds.inject.AssignmentServiceBackedResource;
import gov.ca.cwds.rest.api.domain.cms.Assignment;
import gov.ca.cwds.rest.resources.ResourceDelegate;
import io.dropwizard.hibernate.UnitOfWork;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * A resource providing a RESTful interface for {@link Assignment}. It delegates functions to
 * {@link ResourceDelegate}. It decorates the {@link ResourceDelegate} not in functionality but
 * with @see <a href= "https://github.com/swagger-api/swagger-core/wiki/Annotations-1.5.X">Swagger
 * Annotations</a> and
 * <a href="https://jersey.java.net/documentation/latest/user-guide.html#jaxrs-resources">Jersey
 * Annotations</a>
 * 
 * @author CWDS API Team
 */
@Api(value = RESOURCE_ASSIGNMENT)
@Path(value = RESOURCE_ASSIGNMENT)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AssignmentResource {

  private ResourceDelegate resourceDelegate;

  /**
   * Constructor
   * 
   * @param resourceDelegate The resourceDelegate to delegate to.
   */
  @Inject
  public AssignmentResource(@AssignmentServiceBackedResource ResourceDelegate resourceDelegate) {
    this.resourceDelegate = resourceDelegate;
  }

  /**
   * Finds an assignment by id.
   * 
   * @param id the id
   * 
   * @return the response
   */
  @UnitOfWork(value = "cms")
  @GET
  @Path("/{id}")
  @ApiResponses(value = {@ApiResponse(code = 401, message = "Not Authorized"),
      @ApiResponse(code = 404, message = "Not found"),
      @ApiResponse(code = 406, message = "Accept Header not supported")})
  @ApiOperation(value = "Find assignment by id", response = Assignment.class, code = 200)
  public Response get(@PathParam("id") @ApiParam(required = true, name = "id",
      value = "The id of the Assignment to find") String id) {
    return resourceDelegate.get(id);
  }

  /**
   * Delete an assignment by id.
   * 
   * @param id The id of the {@link Assignment}
   * 
   * @return {@link Response}
   */
  @UnitOfWork(value = "cms")
  @DELETE
  @Path("/{id}")
  @ApiResponses(value = {@ApiResponse(code = 401, message = "Not Authorized")})
  @ApiOperation(value = "Delete Assignment", code = HttpStatus.SC_OK, response = Object.class)
  public Response delete(
      @PathParam("id") @ApiParam(required = true, value = "id of Assignment to delete") String id) {
    return resourceDelegate.delete(id);
  }

  /**
   * Create an {@link Assignment}.
   * 
   * @param assignment The {@link Assignment}
   * 
   * @return The {@link Response}
   */
  @UnitOfWork(value = "cms")
  @POST
  @ApiResponses(value = {@ApiResponse(code = 400, message = "Unable to process JSON"),
      @ApiResponse(code = 401, message = "Not Authorized"),
      @ApiResponse(code = 406, message = "Accept Header not supported"),
      @ApiResponse(code = 409, message = "Conflict - already exists"),
      @ApiResponse(code = 422, message = "Unable to validate Assignment")})
  @Consumes(value = MediaType.APPLICATION_JSON)
  @ApiOperation(value = "Create Assignment", code = HttpStatus.SC_CREATED,
      response = Assignment.class)
  public Response create(@Valid @ApiParam(hidden = false, required = true) Assignment assignment) {
    return resourceDelegate.create(assignment);
  }

  /**
   * Update an {@link Assignment}.
   * 
   * @param id the id
   * @param assignment {@link Assignment}
   *
   * @return The {@link Response}
   */
  @UnitOfWork(value = "cms")
  @PUT
  @Path("/{id}")
  @ApiResponses(value = {@ApiResponse(code = 400, message = "Unable to process JSON"),
      @ApiResponse(code = 401, message = "Not Authorized"),
      @ApiResponse(code = 404, message = "not found"),
      @ApiResponse(code = 406, message = "Accept Header not supported"),
      @ApiResponse(code = 422, message = "Unable to validate Assignment")})
  @Consumes(value = MediaType.APPLICATION_JSON)
  @ApiOperation(value = "Update Assignment", code = HttpStatus.SC_NO_CONTENT,
      response = Object.class)
  public Response update(
      @PathParam("id") @ApiParam(required = true, name = "id",
          value = "The id of the Assignment to update") String id,
      @Valid @ApiParam(hidden = false) Assignment assignment) {
    return resourceDelegate.update(id, assignment);
  }

}
