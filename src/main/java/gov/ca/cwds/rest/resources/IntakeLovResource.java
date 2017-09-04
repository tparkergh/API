package gov.ca.cwds.rest.resources;

import static gov.ca.cwds.rest.core.Api.RESOURCE_INTAKE_LOV;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import gov.ca.cwds.inject.IntakeLovServiceResource;
import gov.ca.cwds.rest.api.ApiException;
import gov.ca.cwds.rest.api.domain.IntakeLov;
import gov.ca.cwds.rest.api.domain.IntakeLovResponse;
import gov.ca.cwds.rest.api.domain.es.ESPersons;
import gov.ca.cwds.rest.services.IntakeLovService;
import io.dropwizard.hibernate.UnitOfWork;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * A resource providing a RESTful interface for {@link ESPersons}. It delegates functions to
 * {@link SimpleResourceDelegate}. It decorates the {@link SimpleResourceService} not in
 * functionality but with @see
 * <a href= "https://github.com/swagger-api/swagger-core/wiki/Annotations-1.5.X">Swagger
 * Annotations</a> and
 * <a href="https://jersey.java.net/documentation/latest/user-guide.html#jaxrs-resources">Jersey
 * Annotations</a>
 * 
 * @author CWDS API Team
 */
@Api(value = RESOURCE_INTAKE_LOV, tags = {RESOURCE_INTAKE_LOV})
@Path(value = RESOURCE_INTAKE_LOV)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.TEXT_PLAIN)
public class IntakeLovResource {

  /**
   * Class logger.
   */
  private static final Logger LOGGER = LoggerFactory.getLogger(IntakeLovResource.class);

  /**
   * Java lacks short-hand notation for typed interfaces and classes, such as C++ "typedef" or
   * "using alias", resulting in verbose type declarations.
   * 
   * <h4>Resource Delegate Type Parameters</h4>
   * 
   * <table>
   * <tr>
   * <th>Param</th>
   * <th>Purpose</th>
   * <th>Class</th>
   * </tr>
   * <tr>
   * <td>K</td>
   * <td>Key</td>
   * <td>String</td>
   * </tr>
   * <tr>
   * <td>Q</td>
   * <td>API Request</td>
   * <td>AutoCompletePersonRequest</td>
   * </tr>
   * <tr>
   * <td>P</td>
   * <td>API Response</td>
   * <td>AutoCompletePersonResponse</td>
   * </tr>
   * <tr>
   * <td>S</td>
   * <td>Service</td>
   * <td>IntakeLovService</td>
   * </tr>
   * </table>
   */
  private SimpleResourceDelegate<String, IntakeLov, IntakeLovResponse, IntakeLovService> resourceDelegate;

  /**
   * Constructor
   * 
   * @param resourceDelegate The resourceDelegate to delegate to.
   */
  @Inject
  public IntakeLovResource(
      @IntakeLovServiceResource SimpleResourceDelegate<String, IntakeLov, IntakeLovResponse, IntakeLovService> resourceDelegate) {
    this.resourceDelegate = resourceDelegate;
  }

  /**
   * Endpoint for Intake LOV.
   * 
   * @return web service response
   */
  @UnitOfWork(value = "ns")
  @GET
  @Path("/all")
  @ApiResponses(value = {@ApiResponse(code = 401, message = "Not Authorized"),
      @ApiResponse(code = 404, message = "Not found"),
      @ApiResponse(code = 400, message = "Unable to parse parameters")})
  @ApiOperation(value = "Query ElasticSearch Persons on given search terms",
      code = HttpStatus.SC_OK, response = IntakeLov[].class)
  @Consumes(value = MediaType.TEXT_PLAIN)
  public Response getAll() {
    Response ret;
    try {
      ret = resourceDelegate.handle(new IntakeLov());
    } catch (Exception e) {
      LOGGER.error("Intake LOV ERROR: {}", e.getMessage(), e);
      throw new ApiException("Intake LOV ERROR. " + e.getMessage(), e);
    }

    return ret;
  }

}
