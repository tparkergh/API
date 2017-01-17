package gov.ca.cwds.rest.resources;

import static gov.ca.cwds.rest.core.Api.RESOURCE_PARTICIPANT;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.http.HttpStatus;

import com.google.inject.Inject;

import gov.ca.cwds.inject.ParticipantServiceBackedResource;
import gov.ca.cwds.rest.api.domain.Participant;
import io.dropwizard.hibernate.UnitOfWork;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * A resource providing a RESTful interface for {@link Participant}. It delegates functions to
 * {@link ResourceDelegate}. It decorates the {@link ResourceDelegate} not in functionality but
 * with @see <a href= "https://github.com/swagger-api/swagger-core/wiki/Annotations-1.5.X">Swagger
 * Annotations</a> and
 * <a href="https://jersey.java.net/documentation/latest/user-guide.html#jaxrs-resources">Jersey
 * Annotations</a>
 * 
 * @author CWDS API Team
 */
@Api(value = RESOURCE_PARTICIPANT, tags = RESOURCE_PARTICIPANT)
@Path(value = RESOURCE_PARTICIPANT)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)

public class ParticipantResource {

  private ResourceDelegate resourceDelegate;

  /**
   * Constructor
   * 
   * @param resourceDelegate The resourceDelegate to delegate to.
   */
  @Inject
  public ParticipantResource(@ParticipantServiceBackedResource ResourceDelegate resourceDelegate) {
    this.resourceDelegate = resourceDelegate;
  }

  /**
   * Finds an participant by id.
   * 
   * @param id the id
   * 
   * @return the response
   */
  @UnitOfWork(value = "ns")
  @GET
  @Path("/{id}")
  @ApiResponses(value = {@ApiResponse(code = 404, message = "Not found"),
      @ApiResponse(code = 406, message = "Accept Header not supported")})
  @ApiOperation(value = "Find participant by id", response = Participant.class, code = 200)
  public Response get(@PathParam("id") @ApiParam(required = true, name = "id",
      value = "The id of the Participant to find") long id) {
    return Response.status(Response.Status.NOT_IMPLEMENTED).entity(null).build();
  }

  /**
   * Delete an participant
   * 
   * @param id The id of the {@link Participant}
   * 
   * @return {@link Response}
   */
  @DELETE
  @Path("/{id}")
  @ApiOperation(hidden = true, value = "Delete Participant - not currently implemented",
      code = HttpStatus.SC_OK, response = Object.class)
  public Response delete(
      @PathParam("id") @ApiParam(required = true, value = "id of participant to delete") long id) {
    return Response.status(Response.Status.NOT_IMPLEMENTED).entity(null).build();
  }

  /**
   * Create an {@link Participant}
   * 
   * @param participant The {@link Participant}
   * 
   * @return The {@link Response}
   */
  @UnitOfWork(value = "ns")
  @POST
  @ApiResponses(value = {@ApiResponse(code = 400, message = "Unable to process JSON"),
      @ApiResponse(code = 406, message = "Accept Header not supported"),
      @ApiResponse(code = 409, message = "Conflict - already exists"),
      @ApiResponse(code = 422, message = "Unable to validate Participant")})
  @Consumes(value = MediaType.APPLICATION_JSON)
  @ApiOperation(value = "Create Participant", code = HttpStatus.SC_CREATED,
      response = Participant.class)
  public Response create(
      @Valid @ApiParam(hidden = false, required = true) Participant participant) {
    return resourceDelegate.create(participant);
  }

  /**
   * Update an {@link Participant}
   * 
   * @param id the id
   * @param participant {@link Participant}
   * @param acceptHeader The accept header.
   *
   * @return The {@link Response}
   */
  @UnitOfWork(value = "ns")
  @PUT
  @ApiResponses(value = {@ApiResponse(code = 400, message = "Unable to process JSON"),
      @ApiResponse(code = 404, message = "not found"),
      @ApiResponse(code = 406, message = "Accept Header not supported"),
      @ApiResponse(code = 422, message = "Unable to validate Participant")})
  @Consumes(value = MediaType.APPLICATION_JSON)
  @ApiOperation(hidden = true, value = "Update Participant", code = HttpStatus.SC_NO_CONTENT,
      response = Object.class)
  public Response update(
      @PathParam("id") @ApiParam(required = true, name = "id",
          value = "The id of the Participant to update") long id,
      @ApiParam(hidden = true) Participant participant,
      @HeaderParam("Accept") @ApiParam(hidden = true) String acceptHeader) {
    return Response.status(Response.Status.NOT_IMPLEMENTED).entity(null).build();
  }

}
