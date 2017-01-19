package gov.ca.cwds.rest.resources.cms;

import static gov.ca.cwds.rest.core.Api.RESOURCE_CMSREFERRAL;

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

import gov.ca.cwds.inject.CmsReferralServiceBackedResource;
import gov.ca.cwds.rest.api.domain.cms.CmsReferral;
import gov.ca.cwds.rest.resources.ResourceDelegate;
import io.dropwizard.hibernate.UnitOfWork;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;


/**
 * Implementation of {@link CmsReferralResource}.
 * 
 * @author CWDS API Team
 */
@Api(value = RESOURCE_CMSREFERRAL)
@Path(value = RESOURCE_CMSREFERRAL)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CmsReferralResource {
  private ResourceDelegate resourceDelegate;

  /**
   * Constructor
   * 
   * @param resourceDelegate The resourceDelegate to delegate to.
   */
  @Inject
  public CmsReferralResource(@CmsReferralServiceBackedResource ResourceDelegate resourceDelegate) {
    this.resourceDelegate = resourceDelegate;
  }

  /**
   * Finds an CmsReferral by id.
   * 
   * @param id the id
   * 
   * @return the response
   */
  @UnitOfWork(value = "cms")
  @GET
  @Path("/{id}")
  @ApiResponses(value = {@ApiResponse(code = 404, message = "Not found"),
      @ApiResponse(code = 406, message = "Accept Header not supported")})
  @ApiOperation(hidden = true, value = "Find CmsReferral by id", response = CmsReferral.class,
      code = 200)
  public Response get(@PathParam("id") @ApiParam(required = true, name = "id",
      value = "The id of the CmsReferral to find") String id) {
    return resourceDelegate.get(id);
  }

  /**
   * Delete an CmsReferral by id.
   * 
   * @param id The id of the {@link CmsReferral}
   * 
   * @return {@link Response}
   */
  @UnitOfWork(value = "cms")
  @DELETE
  @Path("/{id}")
  @ApiOperation(hidden = true, value = "Delete CmsReferral", code = HttpStatus.SC_OK,
      response = Object.class)
  public Response delete(@PathParam("id") @ApiParam(required = true,
      value = "id of CmsReferral to delete") String id) {
    // return resourceDelegate.delete(id);
    return Response.status(HttpStatus.SC_NOT_IMPLEMENTED).build();
  }

  /**
   * Create an {@link CmsReferral}
   * 
   * @param cmsReferral The {@link CmsReferral}
   * 
   * @return The {@link Response}
   */
  @UnitOfWork(value = "cms")
  @POST
  @ApiResponses(value = {@ApiResponse(code = 400, message = "Unable to process JSON"),
      @ApiResponse(code = 406, message = "Accept Header not supported"),
      @ApiResponse(code = 409, message = "Conflict - already exists"),
      @ApiResponse(code = 422, message = "Unable to validate CmsReferral")})
  @Consumes(value = MediaType.APPLICATION_JSON)
  @ApiOperation(value = "Create CmsReferral", code = HttpStatus.SC_CREATED,
      response = CmsReferral.class)
  public Response create(
      @Valid @ApiParam(hidden = false, required = true) CmsReferral cmsReferral) {
    return resourceDelegate.create(cmsReferral);
  }

  /**
   * Update an {@link CmsReferral}
   * 
   * @param id the id
   * @param cmsReferral {@link CmsReferral}
   *
   * @return The {@link Response}
   */
  @UnitOfWork(value = "cms")
  @PUT
  @Path("/{id}")
  @ApiResponses(value = {@ApiResponse(code = 400, message = "Unable to process JSON"),
      @ApiResponse(code = 404, message = "not found"),
      @ApiResponse(code = 406, message = "Accept Header not supported"),
      @ApiResponse(code = 422, message = "Unable to validate Allegation")})
  @Consumes(value = MediaType.APPLICATION_JSON)
  @ApiOperation(hidden = true, value = "Update CmsReferral", code = HttpStatus.SC_NO_CONTENT,
      response = Object.class)
  public Response update(
      @PathParam("id") @ApiParam(required = true, name = "id",
          value = "The id of the CmsReferral to update") String id,
      @Valid @ApiParam(hidden = false) CmsReferral cmsReferral) {
    // return resourceDelegate.update(id, cmsReferral);
    return Response.status(HttpStatus.SC_NOT_IMPLEMENTED).build();
  }
}

