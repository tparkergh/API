package gov.ca.cwds.rest.resources;

import static gov.ca.cwds.rest.core.Api.RESOURCE_REFERRALS;

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

import gov.ca.cwds.inject.ScreeningToReferralServiceBackedResource;
import gov.ca.cwds.rest.api.domain.Screening;
import gov.ca.cwds.rest.api.domain.ScreeningToReferral;
import gov.ca.cwds.rest.api.domain.cms.PostedCmsReferral;
import io.dropwizard.hibernate.UnitOfWork;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * A resource providing a RESTful interface for {@link ScreeningToReferral}. It delegates functions
 * to {@link ServiceBackedResourceDelegate}. It decorates the {@link ServiceBackedResourceDelegate}
 * not in functionality but with @see
 * <a href= "https://github.com/swagger-api/swagger-core/wiki/Annotations-1.5.X">Swagger
 * Annotations</a> and
 * <a href="https://jersey.java.net/documentation/latest/user-guide.html#jaxrs-resources">Jersey
 * Annotations</a>
 * 
 * @author CWDS API Team
 */
@Api(value = RESOURCE_REFERRALS, tags = {RESOURCE_REFERRALS})
@Path(value = RESOURCE_REFERRALS)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ScreeningToReferralResource {
  private ResourceDelegate resourceDelegate;

  /**
   * Constructor
   * 
   * @param resourceDelegate The resourceDelegate to delegate to.
   */
  @Inject
  public ScreeningToReferralResource(
      @ScreeningToReferralServiceBackedResource ResourceDelegate resourceDelegate) {
    this.resourceDelegate = resourceDelegate;
  }

  /**
   * Create an {@link ScreeningToReferral}
   * 
   * @param screeningToReferral The {@link ScreeningToReferral}
   * 
   * @return The {@link Response}
   */
  @UnitOfWork(value = "cms")
  @POST
  @ApiResponses(value = {@ApiResponse(code = 400, message = "Unable to process JSON"),
      @ApiResponse(code = 406, message = "Accept Header not supported"),
      @ApiResponse(code = 409, message = "Conflict - already exists"),
      @ApiResponse(code = 422, message = "Unable to validate ScreeningToReferral")})
  @Consumes(value = MediaType.APPLICATION_JSON)
  @ApiOperation(value = "Create Referral from Screening", code = HttpStatus.SC_CREATED,
      response = PostedCmsReferral.class)
  public Response create(
      @Valid @ApiParam(hidden = false, required = true) ScreeningToReferral screeningToReferral) {
    return resourceDelegate.create(screeningToReferral);
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
  @ApiResponses(value = {@ApiResponse(code = 401, message = "Not Authorized")})
  @ApiOperation(hidden = true, value = "Delete ScreeningToReferral - not currently implemented",
      code = HttpStatus.SC_OK, response = Object.class)
  public Response delete(
      @PathParam("id") @ApiParam(required = true, value = "id of referral to delete") long id,
      @HeaderParam("Accept") @ApiParam(hidden = true) String acceptHeader) {
    return Response.status(Response.Status.NOT_IMPLEMENTED).entity(null).build();
  }

  /*
   * (non-Javadoc)
   * 
   * @see gov.ca.cwds.rest.resources.CrudsResource#get(java.lang.String, java.lang.String)
   */
  @GET
  @Path("/fetch")
  @ApiResponses(value = {@ApiResponse(code = 401, message = "Not Authorized"),
      @ApiResponse(code = 404, message = "Not found"),
      @ApiResponse(code = 406, message = "Accept Header not supported")})
  @ApiOperation(value = "Find ScreeingToReferral - not currently implmented",
      response = Object.class)
  public Response get() {
    return Response.status(Response.Status.NOT_IMPLEMENTED).entity(null).build();
  }

  /**
   * Update a {@link Screening}.
   *
   * @return The {@link Response}
   */
  @PUT
  @Path("/{id}")
  @ApiResponses(value = {@ApiResponse(code = 400, message = "Unable to process JSON"),
      @ApiResponse(code = 401, message = "Not Authorized"),
      @ApiResponse(code = 404, message = "not found"),
      @ApiResponse(code = 406, message = "Accept Header not supported"),
      @ApiResponse(code = 422, message = "Unable to validate Screening")})
  @ApiOperation(value = "Update Screening to Referral - not currently implemented",
      code = HttpStatus.SC_OK, response = Object.class)
  public Response update() {
    return Response.status(Response.Status.NOT_IMPLEMENTED).entity(null).build();
  }

}
