package gov.ca.cwds.rest.resources;

import static gov.ca.cwds.rest.core.Api.RESOURCE_SCREENINGS;
import gov.ca.cwds.inject.ScreeningServiceBackedResource;
import gov.ca.cwds.rest.api.domain.PostedScreening;
import gov.ca.cwds.rest.api.domain.Screening;
import gov.ca.cwds.rest.api.domain.ScreeningReference;
import gov.ca.cwds.rest.api.domain.ScreeningRequest;
import gov.ca.cwds.rest.api.domain.ScreeningResponse;
import io.dropwizard.hibernate.UnitOfWork;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.text.MessageFormat;

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
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.http.HttpStatus;

import com.google.inject.Inject;

/**
 * A resource providing a RESTful interface for {@link Screening}. It delegates functions to
 * {@link ServiceBackedResourceDelegate}. It decorates the {@link ServiceBackedResourceDelegate} not
 * in functionality but with @see <a href=
 * "https://github.com/swagger-api/swagger-core/wiki/Annotations-1.5.X">Swagger Annotations</a> and
 * <a href="https://jersey.java.net/documentation/latest/user-guide.html#jaxrs-resources">Jersey
 * Annotations</a>
 * 
 * @author CWDS API Team
 */
@Api(value = RESOURCE_SCREENINGS, tags = {RESOURCE_SCREENINGS})
@Path(value = RESOURCE_SCREENINGS)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ScreeningResource {
  private ResourceDelegate resourceDelegate;

  /**
   * Constructor
   * 
   * @param resourceDelegate The resourceDelegate to delegate to.
   */
  @Inject
  public ScreeningResource(@ScreeningServiceBackedResource ResourceDelegate resourceDelegate) {
    this.resourceDelegate = resourceDelegate;
  }

  /**
   * Finds a {@link Screening} by id.
   * 
   * @param id The id
   *
   * @return the response
   */
  @UnitOfWork(value = "ns")
  @GET
  @Path("/fetch/{id}")
  @ApiResponses(value = {@ApiResponse(code = 404, message = "Not found"),
      @ApiResponse(code = 406, message = "Accept Header not supported")})
  @ApiOperation(value = "Find Screening by id", response = ScreeningResponse.class)
  public Response get(@PathParam("id") @ApiParam(required = true, name = "id",
      value = "The id of the Screening to find") long id) {
    return resourceDelegate.get(id);
  }

  /*
   * (non-Javadoc)
   * 
   * @see gov.ca.cwds.rest.resources.CrudsResource#get(java.lang.String, java.lang.String)
   */
  @UnitOfWork(value = "ns")
  @GET
  @Path("/fetch")
  @ApiResponses(value = {@ApiResponse(code = 404, message = "Not found"),
      @ApiResponse(code = 406, message = "Accept Header not supported")})
  @ApiOperation(value = "Find Screening", response = ScreeningResponse.class)
  @Consumes(value = MediaType.TEXT_PLAIN)
  public Response get(@QueryParam("response_times") @ApiParam(required = false,
      value = "The response times", example = "immediate") String responseTimes,
      @QueryParam("screening_decisions") @ApiParam(required = false,
          value = "The screening decisions", example = "Decision") String screeningDecisions) {
    String pk =
        MessageFormat.format("responseTimes={0},screeningDecisions={1}", responseTimes,
            screeningDecisions);
    return resourceDelegate.get(pk);
  }

  /**
   * Delete a {@link Screening}
   * 
   * @param id The id of the {@link Screening}
   * @param acceptHeader The accept header.
   * 
   * @return {@link Response}
   */
  @DELETE
  @Path("/{id}")
  @ApiOperation(hidden = true, value = "Delete Screening - not currently implemented",
      code = HttpStatus.SC_OK, response = Object.class)
  public Response delete(@PathParam("id") @ApiParam(required = true,
      value = "id of person to delete") long id,
      @HeaderParam("Accept") @ApiParam(hidden = true) String acceptHeader) {
    return Response.status(Response.Status.NOT_IMPLEMENTED).entity(null).build();
  }

  /**
   * Create a {@link Screening}.
   * 
   * @param screeningReference The {@link ScreeningReference}
   * 
   * @return The {@link Response}
   */
  @UnitOfWork(value = "ns")
  @POST
  @ApiResponses(value = {@ApiResponse(code = 400, message = "Unable to process JSON"),
      @ApiResponse(code = 406, message = "Accept Header not supported"),
      @ApiResponse(code = 409, message = "Conflict - already exists"),
      @ApiResponse(code = 422, message = "Unable to validate Screening")})
  @Consumes(value = MediaType.APPLICATION_JSON)
  @ApiOperation(value = "Creates a new screening", code = HttpStatus.SC_CREATED,
      response = PostedScreening.class)
  public Response create(
      @Valid @ApiParam(hidden = false, required = true) ScreeningReference screeningReference) {
    return resourceDelegate.create(screeningReference);
  }

  /**
   * Update a {@link Screening}.
   *
   * @param id the id
   * @param screeningRequest {@link Screening}
   *
   * @return The {@link Response}
   */
  @UnitOfWork(value = "ns")
  @PUT
  @Path("/{id}")
  @ApiResponses(value = {@ApiResponse(code = 400, message = "Unable to process JSON"),
      @ApiResponse(code = 404, message = "not found"),
      @ApiResponse(code = 406, message = "Accept Header not supported"),
      @ApiResponse(code = 422, message = "Unable to validate Screening")})
  @Consumes(value = MediaType.APPLICATION_JSON)
  @ApiOperation(value = "Update Screening", code = HttpStatus.SC_OK,
      response = ScreeningResponse.class)
  public Response update(@PathParam("id") @ApiParam(required = true, name = "id",
      value = "The id of the Screening to update") long id, @ApiParam(required = true,
      name = "screeningRequest", value = "The screening request") ScreeningRequest screeningRequest) {
    return resourceDelegate.update(id, screeningRequest);
  }
}
