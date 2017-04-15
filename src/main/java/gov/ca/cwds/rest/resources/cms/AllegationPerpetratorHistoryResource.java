package gov.ca.cwds.rest.resources.cms;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.http.HttpStatus;

import com.google.inject.Inject;

import gov.ca.cwds.inject.AllegationPerpetratorHistoryServiceBackedResource;
import gov.ca.cwds.rest.api.domain.cms.AllegationPerpetratorHistory;
import gov.ca.cwds.rest.resources.ResourceDelegate;
import io.dropwizard.hibernate.UnitOfWork;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * A resource providing a RESTful interface for {@link AllegationPerpetratorHistory}. It delegates
 * functions to {@link ResourceDelegate}. It decorates the {@link ResourceDelegate} not in
 * functionality but with @see
 * <a href= "https://github.com/swagger-api/swagger-core/wiki/Annotations-1.5.X">Swagger
 * Annotations</a> and
 * <a href="https://jersey.java.net/documentation/latest/user-guide.html#jaxrs-resources">Jersey
 * Annotations</a>
 * 
 * @author CWDS API Team
 */
@Api(value = "_allegationPerpetratorHistory")
@Path(value = "_allegationPerpetratorHistory")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AllegationPerpetratorHistoryResource {

  private ResourceDelegate resourceDelegate;

  /**
   * Constructor
   * 
   * @param resourceDelegate The resourceDelegate to delegate to.
   */
  @Inject
  public AllegationPerpetratorHistoryResource(
      @AllegationPerpetratorHistoryServiceBackedResource ResourceDelegate resourceDelegate) {
    this.resourceDelegate = resourceDelegate;
  }

  /**
   * Create an {@link AllegationPerpetratorHistory}.
   * 
   * @param allegationPerpetratorHistory The {@link AllegationPerpetratorHistory}
   * 
   * @return The {@link Response}
   */
  @UnitOfWork(value = "cms")
  @POST
  @ApiResponses(value = {@ApiResponse(code = 400, message = "Unable to process JSON"),
      @ApiResponse(code = 401, message = "Not Authorized"),
      @ApiResponse(code = 406, message = "Accept Header not supported"),
      @ApiResponse(code = 409, message = "Conflict - already exists"),
      @ApiResponse(code = 422, message = "Unable to validate Allegation")})
  @Consumes(value = MediaType.APPLICATION_JSON)
  @ApiOperation(value = "Create AllegationPerpetratorHistory", code = HttpStatus.SC_CREATED,
      response = AllegationPerpetratorHistory.class)
  public Response create(@Valid @ApiParam(hidden = false,
      required = true) AllegationPerpetratorHistory allegationPerpetratorHistory) {
    return resourceDelegate.create(allegationPerpetratorHistory);
  }

}
