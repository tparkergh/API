package gov.ca.cwds.rest.resources.cms;

import static gov.ca.cwds.rest.core.Api.RESOURCE_RELATIONSHIPS;
import gov.ca.cwds.inject.ClientRelationshipServiceBackedResource;
import gov.ca.cwds.rest.api.domain.cms.ClientRelationship;
import gov.ca.cwds.rest.api.domain.cms.PostedClientRelationship;
import gov.ca.cwds.rest.resources.ResourceDelegate;
import io.dropwizard.hibernate.UnitOfWork;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

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

/**
 * A resource providing a RESTful interface for {@link ClientRelationship}. It delegates functions
 * to {@link ResourceDelegate}. It decorates the {@link ResourceDelegate} not in functionality but
 * with @see <a href= "https://github.com/swagger-api/swagger-core/wiki/Annotations-1.5.X">Swagger
 * Annotations</a> and <a
 * href="https://jersey.java.net/documentation/latest/user-guide.html#jaxrs-resources">Jersey
 * Annotations</a>
 * 
 * @author CWDS API Team
 */
@Api(value = RESOURCE_RELATIONSHIPS)
@Path(value = RESOURCE_RELATIONSHIPS)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ClientRelationshipResource {

  private ResourceDelegate resourceDelegate;

  /**
   * Constructor
   * 
   * @param resourceDelegate The resourceDelegate to delegate to.
   */
  @Inject
  public ClientRelationshipResource(
      @ClientRelationshipServiceBackedResource ResourceDelegate resourceDelegate) {
    this.resourceDelegate = resourceDelegate;
  }

  /**
   * Finds Client Relationship by id.
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
  @ApiOperation(value = "Find Client Relationship by id",
      response = PostedClientRelationship.class, code = 200)
  public Response get(@PathParam("id") @ApiParam(required = true, name = "id",
      value = "The id of the Client Relationship to find") String id) {
    return resourceDelegate.get(id);
  }


  /**
   * Create an {@link ClientRelationship}.
   * 
   * @param clientRelationship - The {@link ClientRelationship} to create
   * 
   * @return The {@link Response}
   */
  @UnitOfWork(value = "cms")
  @POST
  @ApiResponses(value = {@ApiResponse(code = 400, message = "Unable to process JSON"),
      @ApiResponse(code = 401, message = "Not Authorized"),
      @ApiResponse(code = 406, message = "Accept Header not supported"),
      @ApiResponse(code = 409, message = "Conflict - already exists"),
      @ApiResponse(code = 422, message = "Unable to validate Client Relationship")})
  @Consumes(value = MediaType.APPLICATION_JSON)
  @ApiOperation(value = "Create Client Relationship", code = HttpStatus.SC_CREATED,
      response = PostedClientRelationship.class)
  public Response create(
      @Valid @ApiParam(hidden = false, required = true) ClientRelationship clientRelationship) {
    return resourceDelegate.create(clientRelationship);
  }


}
