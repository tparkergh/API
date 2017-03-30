package gov.ca.cwds.rest.resources.cms;

import static gov.ca.cwds.rest.core.Api.RESOURCE_CMSNSREFERRAL;
import gov.ca.cwds.inject.CmsNSReferralServiceBackedResource;
import gov.ca.cwds.rest.api.domain.cms.CmsNSReferral;
import gov.ca.cwds.rest.resources.ResourceDelegate;
import io.dropwizard.hibernate.UnitOfWork;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

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


/**
 * Implementation of {@link CmsReferralResource}.
 * 
 * @author CWDS API Team
 */
@Api(value = RESOURCE_CMSNSREFERRAL)
@Path(value = RESOURCE_CMSNSREFERRAL)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CmsNSReferralResource {
  private ResourceDelegate resourceDelegate;

  /**
   * Constructor
   * 
   * @param resourceDelegate The resourceDelegate to delegate to.
   */
  @Inject
  public CmsNSReferralResource(@CmsNSReferralServiceBackedResource ResourceDelegate resourceDelegate) {
    this.resourceDelegate = resourceDelegate;
  }

  /**
   * Finds an CmsNSReferral by id.
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
  @ApiOperation(hidden = true, value = "Find CmsReferral by id", response = CmsNSReferral.class,
      code = 200)
  public Response get(@PathParam("id") @ApiParam(required = true, name = "id",
      value = "The id of the CmsReferral to find") String id) {
    return resourceDelegate.get(id);
  }

  /**
   * Delete an CmsNSReferral by id.
   * 
   * @param id The id of the {@link CmsNSReferral}
   * 
   * @return {@link Response}
   */
  @UnitOfWork(value = "cms")
  @DELETE
  @Path("/{id}")
  @ApiResponses(value = {@ApiResponse(code = 401, message = "Not Authorized")})
  @ApiOperation(hidden = true, value = "Delete CmsNSReferral", code = HttpStatus.SC_OK,
      response = Object.class)
  public Response delete(@PathParam("id") @ApiParam(required = true,
      value = "id of CmsNSReferral to delete") String id) {
    // return resourceDelegate.delete(id);
    return Response.status(HttpStatus.SC_NOT_IMPLEMENTED).build();
  }

  /**
   * Create an {@link CmsNSReferral}
   * 
   * @param cmsNsReferral The {@link CmsNSReferral}
   * 
   * @return The {@link Response}
   */
  // @UnitOfWork(value = "cms")
  @POST
  @ApiResponses(value = {@ApiResponse(code = 400, message = "Unable to process JSON"),
      @ApiResponse(code = 401, message = "Not Authorized"),
      @ApiResponse(code = 406, message = "Accept Header not supported"),
      @ApiResponse(code = 409, message = "Conflict - already exists"),
      @ApiResponse(code = 422, message = "Unable to validate CmsReferral")})
  @Consumes(value = MediaType.APPLICATION_JSON)
  @ApiOperation(value = "Create CmsNSReferral", code = HttpStatus.SC_CREATED,
      response = CmsNSReferral.class)
  public Response create(
      @Valid @ApiParam(hidden = false, required = true) CmsNSReferral cmsNsReferral) {
    return resourceDelegate.create(cmsNsReferral);
  }

  /**
   * Update an {@link CmsNSReferral}
   * 
   * @param id the id
   * @param cmsNsReferral {@link CmsNSReferral}
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
      @ApiResponse(code = 422, message = "Unable to validate Allegation")})
  @Consumes(value = MediaType.APPLICATION_JSON)
  @ApiOperation(hidden = true, value = "Update CmsNSReferral", code = HttpStatus.SC_NO_CONTENT,
      response = Object.class)
  public Response update(@PathParam("id") @ApiParam(required = true, name = "id",
      value = "The id of the CmsReferral to update") String id,
      @Valid @ApiParam(hidden = false) CmsNSReferral cmsNsReferral) {
    // return resourceDelegate.update(id, cmsNsReferral);
    return Response.status(HttpStatus.SC_NOT_IMPLEMENTED).build();
  }
}
