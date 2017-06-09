package gov.ca.cwds.rest.resources;

import static gov.ca.cwds.rest.core.Api.RESOURCE_PARTICIPANTS;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
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
@Api(value = RESOURCE_PARTICIPANTS, tags = RESOURCE_PARTICIPANTS)
@Path(value = RESOURCE_PARTICIPANTS)
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
   * @return the response /** <strong>NOT IMPLEMENTED! REQUIRED BY {@link ResourceDelegate}
   *         .</strong>
   */
  public Response get(long id) {
    return Response.status(Response.Status.NOT_IMPLEMENTED).entity(null).build();
  }

  /**
   * Delete an participant <strong>NOT IMPLEMENTED! REQUIRED BY {@link ResourceDelegate}.</strong>
   * 
   * @param id The id of the {@link Participant}
   * 
   * @return {@link Response}
   */
  public Response delete(long id) {
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
      @ApiResponse(code = 401, message = "Not Authorized"),
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
   * @return The {@link Response} /** <strong>NOT IMPLEMENTED! REQUIRED BY {@link ResourceDelegate}
   *         .</strong>
   */
  public Response update(long id, Participant participant) {
    return Response.status(Response.Status.NOT_IMPLEMENTED).entity(null).build();
  }

}
