package gov.ca.cwds.rest.resources.cms;

import static gov.ca.cwds.rest.core.Api.RESOURCE_CROSS_REPORT;

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

import gov.ca.cwds.inject.CrossReportServiceBackedResource;
import gov.ca.cwds.rest.api.domain.cms.CrossReport;
import gov.ca.cwds.rest.resources.ResourceDelegate;
import io.dropwizard.hibernate.UnitOfWork;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * A resource providing a RESTful interface for {@link CrossReport}. It delegates functions to
 * {@link ResourceDelegate}. It decorates the {@link ResourceDelegate} not in functionality but
 * with @see <a href= "https://github.com/swagger-api/swagger-core/wiki/Annotations-1.5.X">Swagger
 * Annotations</a> and
 * <a href="https://jersey.java.net/documentation/latest/user-guide.html#jaxrs-resources">Jersey
 * Annotations</a>
 * 
 * @author CWDS API Team
 */
@Api(value = RESOURCE_CROSS_REPORT, tags = RESOURCE_CROSS_REPORT)
@Path(value = RESOURCE_CROSS_REPORT)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CrossReportResource {

  private ResourceDelegate resourceDelegate;

  /**
   * Constructor
   * 
   * @param resourceDelegate The resourceDelegate to delegate to.
   */
  @Inject
  public CrossReportResource(@CrossReportServiceBackedResource ResourceDelegate resourceDelegate) {
    this.resourceDelegate = resourceDelegate;
  }

  /**
   * Finds a Cross Report by referral id and third id.
   * 
   * @param thirdId the thirdId is unique key for CrossReport
   * 
   * @return the response
   */
  @UnitOfWork(value = "cms")
  @GET
  @Path("/thirdId={thirdId}")
  @ApiResponses(value = {@ApiResponse(code = 401, message = "Not Authorized"),
      @ApiResponse(code = 404, message = "Not found"),
      @ApiResponse(code = 406, message = "Accept Header not supported")})
  @ApiOperation(value = "Find crossreport by referral id and third id",
      response = CrossReport.class, code = 200)
  public Response get(@PathParam("thirdId") @ApiParam(required = true, value = "The third id",
      example = "td89slaz98") String thirdId) {
    return resourceDelegate.get(thirdId);
  }

  /**
   * Delete a Cross Report by referral id and third id.
   * 
   * @param thirdId the thirdId is unique key for CrossReport
   * 
   * @return {@link Response}
   */
  @UnitOfWork(value = "cms")
  @DELETE
  @Path("/thirdId={thirdId}")
  @ApiResponses(value = {@ApiResponse(code = 401, message = "Not Authorized")})
  @ApiOperation(value = "Delete CrossReport", code = HttpStatus.SC_OK, response = Object.class)
  public Response delete(@PathParam("thirdId") @ApiParam(required = true, value = "The third id",
      example = "td89slaz98") String thirdId) {
    return resourceDelegate.delete(thirdId);
  }

  /**
   * Create an {@link CrossReport}
   * 
   * @param crossReport The {@link CrossReport}
   * 
   * @return The {@link Response}
   */
  @UnitOfWork(value = "cms")
  @POST
  @ApiResponses(value = {@ApiResponse(code = 400, message = "Unable to process JSON"),
      @ApiResponse(code = 401, message = "Not Authorized"),
      @ApiResponse(code = 406, message = "Accept Header not supported"),
      @ApiResponse(code = 409, message = "Conflict - already exists"),
      @ApiResponse(code = 422, message = "Unable to validate CrossReport")})
  @Consumes(value = MediaType.APPLICATION_JSON)
  @ApiOperation(value = "Create CrossReport", code = HttpStatus.SC_CREATED,
      response = CrossReport.class)
  public Response create(
      @Valid @ApiParam(hidden = false, required = true) CrossReport crossReport) {
    return resourceDelegate.create(crossReport);
  }

  /**
   * Update an {@link CrossReport}
   * 
   * @param crossReport {@link CrossReport}
   *
   * @return The {@link Response}
   */
  @UnitOfWork(value = "cms")
  @PUT
  @ApiResponses(value = {@ApiResponse(code = 400, message = "Unable to process JSON"),
      @ApiResponse(code = 401, message = "Not Authorized"),
      @ApiResponse(code = 404, message = "not found"),
      @ApiResponse(code = 406, message = "Accept Header not supported"),
      @ApiResponse(code = 422, message = "Unable to validate CrossReport")})
  @Consumes(value = MediaType.APPLICATION_JSON)
  @ApiOperation(value = "Update CrossReport", code = HttpStatus.SC_NO_CONTENT,
      response = Object.class)
  public Response update(
      @Valid @ApiParam(hidden = false, required = true) CrossReport crossReport) {
    return resourceDelegate.update(null, crossReport);
  }
}
