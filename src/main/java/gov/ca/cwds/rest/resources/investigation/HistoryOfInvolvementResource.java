package gov.ca.cwds.rest.resources.investigation;

import static gov.ca.cwds.rest.core.Api.RESOURCE_INVESTIGATIONS;
import gov.ca.cwds.inject.HistoryOfInvolvementServiceBackedResource;
import gov.ca.cwds.rest.api.domain.investigation.HistoryOfInvolvement;
import gov.ca.cwds.rest.resources.TypedResourceDelegate;
import io.dropwizard.hibernate.UnitOfWork;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.http.HttpStatus;

import com.google.inject.Inject;

/**
 * A resource providing a RESTful interface for {@link HistoryOfInvolvement}. It delegates functions
 * to {@link TypedResourceDelegate}. It decorates the {@link TypedResourceDelegate} not in
 * functionality but with @see <a href=
 * "https://github.com/swagger-api/swagger-core/wiki/Annotations-1.5.X">Swagger Annotations</a> and
 * <a href="https://jersey.java.net/documentation/latest/user-guide.html#jaxrs-resources">Jersey
 * Annotations</a>
 * 
 * @author CWDS API Team
 */
@Api(value = RESOURCE_INVESTIGATIONS)
@Path(value = RESOURCE_INVESTIGATIONS)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class HistoryOfInvolvementResource {

  private TypedResourceDelegate<String, HistoryOfInvolvement> typedResourceDelegate;

  /**
   * Constructor
   *
   * @param typedResourceDelegate The typedResourceDelegate to delegate to.
   */
  @Inject
  public HistoryOfInvolvementResource(
      @HistoryOfInvolvementServiceBackedResource TypedResourceDelegate<String, HistoryOfInvolvement> typedResourceDelegate) {
    this.typedResourceDelegate = typedResourceDelegate;
  }


  /**
   * Find an {@link HistoryOfInvolvement}.
   *
   * @param id The id of the Referral the HistoryOfInvolvement is for
   *
   * @return The {@link Response}
   */
  @UnitOfWork(value = "cms")
  @GET
  @Path("/{id}/history_of_involvements")
  @ApiResponses(value = {@ApiResponse(code = 400, message = "Unable to process JSON"),
      @ApiResponse(code = 401, message = "Not Authorized"),
      @ApiResponse(code = 406, message = "Accept Header not supported"),
      @ApiResponse(code = 409, message = "Conflict - already exists")})
  @Consumes(value = MediaType.APPLICATION_JSON)
  @ApiOperation(value = "Find History Of Involvements by Referral Id", code = HttpStatus.SC_OK,
      response = HistoryOfInvolvement.class)
  public Response find(@PathParam("id") @ApiParam(required = true, name = "id",
      value = "The id of the Referral ") String id) {
    return typedResourceDelegate.get(id);
  }


}
