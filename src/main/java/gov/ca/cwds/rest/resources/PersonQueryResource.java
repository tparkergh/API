package gov.ca.cwds.rest.resources;

import static gov.ca.cwds.rest.core.Api.RESOURCE_PERSON_QUERY;
import gov.ca.cwds.inject.IntakePersonQueryServiceResource;
import gov.ca.cwds.rest.api.ApiException;
import gov.ca.cwds.rest.api.domain.es.ESPersons;
import gov.ca.cwds.rest.api.domain.es.PersonQueryRequest;
import gov.ca.cwds.rest.services.es.PersonQueryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.http.HttpStatus;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

/**
 * A resource providing a RESTful interface for {@link ESPersons}. It delegates functions to
 * {@link SimpleResourceDelegate}. It decorates the {@link SimpleResourceService} not in
 * functionality but with @see <a href=
 * "https://github.com/swagger-api/swagger-core/wiki/Annotations-1.5.X">Swagger Annotations</a> and
 * <a href="https://jersey.java.net/documentation/latest/user-guide.html#jaxrs-resources">Jersey
 * Annotations</a>
 * 
 * @author CWDS API Team
 */
@Api(value = RESOURCE_PERSON_QUERY, tags = {RESOURCE_PERSON_QUERY})
@Path(value = RESOURCE_PERSON_QUERY)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PersonQueryResource {

  /**
   * Logger for this class.
   */
  private static final Logger LOGGER = LoggerFactory.getLogger(PersonQueryResource.class);


  private SimpleResourceDelegate<String, PersonQueryRequest, gov.ca.cwds.rest.api.Response, PersonQueryService> resourceDelegate;

  /**
   * Constructor
   * 
   * @param resourceDelegate The resourceDelegate to delegate to.
   */
  @Inject
  public PersonQueryResource(
      @IntakePersonQueryServiceResource SimpleResourceDelegate<String, PersonQueryRequest, gov.ca.cwds.rest.api.Response, PersonQueryService> resourceDelegate) {
    this.resourceDelegate = resourceDelegate;
  }

  /**
   * Endpoint for Intake Person Query Search.
   * 
   * @param req JSON {@link PersonQueryRequest}
   * @return web service response
   */
  @POST
  @ApiResponses(value = {@ApiResponse(code = 400, message = "Unable to process JSON"),
      @ApiResponse(code = 401, message = "Not Authorized"),
      @ApiResponse(code = 406, message = "Accept Header not supported")})
  @ApiOperation(value = "Query ElasticSearch Persons on given search terms",
      code = HttpStatus.SC_OK, response = JSONObject.class)
  @Consumes(value = MediaType.APPLICATION_JSON)
  public Response searchPerson(
      @Valid @ApiParam(hidden = false, required = true) PersonQueryRequest req) {
    Response ret;
    try {
      ret = resourceDelegate.handle(req);
    } catch (Exception e) {
      LOGGER.error("Intake Person Query ERROR: {}", e.getMessage(), e);
      throw new ApiException("Intake Person Query ERROR. " + e.getMessage(), e);
    }
    return ret;
  }

}
