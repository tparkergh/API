package gov.ca.cwds.rest.resources.cms;

import static gov.ca.cwds.rest.core.Api.RESOURCE_REFERRAL_CLIENT;

import java.text.MessageFormat;

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

import gov.ca.cwds.inject.ReferralClientServiceBackedResource;
import gov.ca.cwds.rest.api.domain.cms.ReferralClient;
import gov.ca.cwds.rest.resources.ResourceDelegate;
import io.dropwizard.hibernate.UnitOfWork;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ResponseHeader;

/**
 * Implementation of {@link ReferralClientResource} delegating work to
 * 
 * @author CWDS API Team
 */
@Api(value = RESOURCE_REFERRAL_CLIENT)
@Path(value = RESOURCE_REFERRAL_CLIENT)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)


public class ReferralClientResource {

  private ResourceDelegate resourceDelegate;

  /**
   * Constructor
   * 
   * @param resourceDelegate - the resourceDelegate to delegate to.
   */
  @Inject
  public ReferralClientResource(
      @ReferralClientServiceBackedResource ResourceDelegate resourceDelegate) {
    this.resourceDelegate = resourceDelegate;

  }

  /*
   * (non-Javadoc)
   * 
   * @see gov.ca.cwds.rest.resources.CrudsResource#get(java.lang.String, java.lang.String)
   */
  /**
   * @param referralId - referral ID
   * @param clientId - client ID
   * @return - the ReferralClient row
   */
  @UnitOfWork(value = "cms")
  @GET
  @Path("/referralId={referralId},clientId={clientId}")
  @ApiResponses(value = {@ApiResponse(code = 401, message = "Not Authorized"),
      @ApiResponse(code = 404, message = "Not found"),
      @ApiResponse(code = 406, message = "Accept Header not supported")})
  @ApiOperation(value = "Find Referral Client by composite id of referralId and clientId",
      response = ReferralClient.class, code = 200)
  public Response get(
      @PathParam("referralId") @ApiParam(required = true, value = "The referral id",
          example = "abcdefghif") String referralId,
      @PathParam("clientId") @ApiParam(required = true, value = "The client id",
          example = "td89slaz98") String clientId) {
    String pk = MessageFormat.format("referralId={0},clientId={1}", referralId, clientId);
    return resourceDelegate.get(pk);
  }

  /**
   * @param referralId FK to referral
   * @param clientId FK to client
   * @return - the deleted ReferralClient row
   */
  /*
   * (non-Javadoc)
   * 
   * @see gov.ca.cwds.rest.resources.CrudsResource#delete(java.lang.String, java.lang.String)
   */
  @UnitOfWork(value = "cms")
  @DELETE
  @Path("/referralId={referralId},clientId={clientId}")
  @ApiResponses(value = {@ApiResponse(code = 401, message = "Not Authorized")})
  @ApiOperation(value = "Delete Referral Client by composite id of referralId and clientId",
      code = HttpStatus.SC_OK, response = Object.class)
  public Response delete(
      @PathParam("referralId") @ApiParam(required = true, value = "The referral id",
          example = "abcdefghif") String referralId,
      @PathParam("clientId") @ApiParam(required = true, value = "The client id",
          example = "td89slaz98") String clientId) {
    String pk = MessageFormat.format("referralId={0},clientId={1}", referralId, clientId);
    return resourceDelegate.delete(pk);
  }

  /**
   * @param referralClient incoming referral client JSON
   * @return - updated ReferralClient row
   */
  /*
   * (non-Javadoc)
   * 
   * @see gov.ca.cwds.rest.resources.CrudsResource#update(gov.ca.cwds.rest.api.domain.DomainObject,
   * java.lang.String)
   */
  @UnitOfWork(value = "cms")
  @PUT
  @ApiResponses(value = {@ApiResponse(code = 400, message = "Unable to process JSON"),
      @ApiResponse(code = 401, message = "Not Authorized"),
      @ApiResponse(code = 404, message = "not found"),
      @ApiResponse(code = 406, message = "Accept Header not supported"),
      @ApiResponse(code = 422, message = "Unable to validate Referral Client")})
  @Consumes(value = MediaType.APPLICATION_JSON)
  @ApiOperation(value = "Update Referra lClient", code = HttpStatus.SC_NO_CONTENT,
      response = Object.class)
  public Response update(
      @Valid @ApiParam(hidden = false, required = true) ReferralClient referralClient) {
    return resourceDelegate.update(null, referralClient);
  }

  /**
   * @param referral incoming referral client JSON
   * @return - created ReferralClient row
   */
  /*
   * (non-Javadoc)
   * 
   * @see gov.ca.cwds.rest.resources.CrudsResource#create(gov.ca.cwds.rest.api.domain.DomainObject,
   * java.lang.String, javax.ws.rs.core.UriInfo)
   */
  @UnitOfWork(value = "cms")
  @POST
  @ApiResponses(value = {@ApiResponse(code = 400, message = "Unable to process JSON"),
      @ApiResponse(code = 401, message = "Not Authorized"),
      @ApiResponse(code = 406, message = "Accept Header not supported"),
      @ApiResponse(code = 409, message = "Conflict - already exists"),
      @ApiResponse(code = 422, message = "Unable to validate Referral")})
  @Consumes(value = MediaType.APPLICATION_JSON)
  @ApiOperation(value = "Create Referral Client", code = HttpStatus.SC_CREATED,
      responseHeaders = @ResponseHeader(name = "Location", description = "Link to Referral Client",
          response = Object.class))
  public Response create(
      @Valid @ApiParam(hidden = false, required = true) ReferralClient referral) {
    return resourceDelegate.create(referral);
  }
}
