package gov.ca.cwds.rest.resources.cms;

import static gov.ca.cwds.rest.core.Api.RESOURCE_CLIENT_COLLATERALS;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.http.HttpStatus;

import com.google.inject.Inject;

import gov.ca.cwds.inject.ClientCollateralServiceBackedResource;
import gov.ca.cwds.rest.api.domain.cms.ClientCollateral;
import gov.ca.cwds.rest.api.domain.cms.PostedClientCollateral;
import gov.ca.cwds.rest.resources.TypedResourceDelegate;
import io.dropwizard.hibernate.UnitOfWork;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * A resource providing a RESTful interface for {@link ClientCollateral}. It delegates functions to
 * {@link TypedResourceDelegate}. It decorates the {@link TypedResourceDelegate} not in
 * functionality but with @see
 * <a href= "https://github.com/swagger-api/swagger-core/wiki/Annotations-1.5.X">Swagger
 * Annotations</a> and
 * <a href="https://jersey.java.net/documentation/latest/user-guide.html#jaxrs-resources">Jersey
 * Annotations</a>
 * 
 * @author CWDS API Team
 */
@Api(value = RESOURCE_CLIENT_COLLATERALS)
@Path(value = RESOURCE_CLIENT_COLLATERALS)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ClientCollateralResource {

  private TypedResourceDelegate<String, ClientCollateral> typedResourceDelegate;

  /**
   * Constructor
   * 
   * @param typedResourceDelegate The typedResourceDelegate to delegate to.
   */
  @Inject
  public ClientCollateralResource(
      @ClientCollateralServiceBackedResource TypedResourceDelegate<String, ClientCollateral> typedResourceDelegate) {
    this.typedResourceDelegate = typedResourceDelegate;
  }

  /**
   * Finds Client Collateral by id.
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
  @ApiOperation(value = "Find Client Collateral by id", response = PostedClientCollateral.class,
      code = 200)
  public Response get(@PathParam("id") @ApiParam(required = true, name = "id",
      value = "The id of the Client Collateral to find") String id) {
    return typedResourceDelegate.get(id);
  }

  /**
   * Create an {@link ClientCollateral}.
   * 
   * @param clientCollateral - The {@link ClientCollateral} to create
   * 
   * @return The {@link Response}
   */
  @UnitOfWork(value = "cms")
  @POST
  @ApiResponses(value = {@ApiResponse(code = 400, message = "Unable to process JSON"),
      @ApiResponse(code = 401, message = "Not Authorized"),
      @ApiResponse(code = 406, message = "Accept Header not supported"),
      @ApiResponse(code = 409, message = "Conflict - already exists"),
      @ApiResponse(code = 422, message = "Unable to validate Client Collateral")})
  @Consumes(value = MediaType.APPLICATION_JSON)
  @ApiOperation(value = "Create Client Collateral", code = HttpStatus.SC_CREATED,
      response = PostedClientCollateral.class)
  public Response create(
      @Valid @ApiParam(hidden = false, required = true) ClientCollateral clientCollateral) {
    return typedResourceDelegate.create(clientCollateral);
  }

}
