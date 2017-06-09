package gov.ca.cwds.rest.resources;

import static gov.ca.cwds.rest.core.Api.RESOURCE_AUTOCOMPLETE;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.persistence.cms.CmsKeyIdGenerator;
import gov.ca.cwds.inject.IntakePersonAutoCompleteServiceResource;
import gov.ca.cwds.rest.api.ApiException;
import gov.ca.cwds.rest.api.domain.cms.LegacyKeyRequest;
import gov.ca.cwds.rest.api.domain.cms.LegacyKeyResponse;
import gov.ca.cwds.rest.api.domain.es.AutoCompletePersonRequest;
import gov.ca.cwds.rest.services.cms.LegacyKeyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * A resource providing a RESTful interface for {@link CmsKeyIdGenerator}. It delegates functions to
 * {@link SimpleResourceDelegate}. It decorates the {@link SimpleResourceService} not in
 * functionality but with @see
 * <a href= "https://github.com/swagger-api/swagger-core/wiki/Annotations-1.5.X">Swagger
 * Annotations</a> and
 * <a href="https://jersey.java.net/documentation/latest/user-guide.html#jaxrs-resources">Jersey
 * Annotations</a>
 * 
 * @author CWDS API Team
 */
@Api(value = RESOURCE_AUTOCOMPLETE, tags = {RESOURCE_AUTOCOMPLETE})
@Path(value = RESOURCE_AUTOCOMPLETE)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.TEXT_PLAIN)
public class LegacyKeyResource {

  /**
   * SLF4J logger for this resource class.
   */
  private static final Logger LOGGER = LoggerFactory.getLogger(LegacyKeyResource.class);

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
   * <td>LegacyKeyRequest</td>
   * </tr>
   * <tr>
   * <td>P</td>
   * <td>API Response</td>
   * <td>LegacyKeyResponse</td>
   * </tr>
   * <tr>
   * <td>S</td>
   * <td>Service</td>
   * <td>LegacyKeyService</td>
   * </tr>
   * </table>
   */
  private SimpleResourceDelegate<String, LegacyKeyRequest, LegacyKeyResponse, LegacyKeyService> resourceDelegate;

  /**
   * Constructor
   * 
   * @param resourceDelegate The resourceDelegate to delegate to.
   */
  @Inject
  public LegacyKeyResource(
      @IntakePersonAutoCompleteServiceResource SimpleResourceDelegate<String, LegacyKeyRequest, LegacyKeyResponse, LegacyKeyService> resourceDelegate) {
    this.resourceDelegate = resourceDelegate;
  }

  /**
   * Endpoint for Intake Auto-complete Person Search.
   * 
   * @param req JSON {@link AutoCompletePersonRequest}
   * @return web service response
   */
  @GET
  @Path("/")
  @ApiResponses(value = {@ApiResponse(code = 401, message = "Not Authorized"),
      @ApiResponse(code = 404, message = "Not found"),
      @ApiResponse(code = 400, message = "Unable to parse parameters")})
  @ApiOperation(value = "Query ElasticSearch Persons on given search terms",
      code = HttpStatus.SC_OK, response = LegacyKeyResponse[].class)
  @Consumes(value = MediaType.TEXT_PLAIN)
  public Response searchPerson(@Valid @NotNull @QueryParam("search_term") @ApiParam(hidden = false,
      required = true, example = "U2Gaygg0Ki") LegacyKeyRequest req) {
    Response ret;
    try {
      ret = resourceDelegate.handle(req);
    } catch (Exception e) {
      LOGGER.error("Intake Person AutoComplete ERROR: {}", e.getMessage(), e);
      throw new ApiException("Intake Person AutoComplete ERROR. " + e.getMessage(), e);
    }

    return ret;
  }

}
