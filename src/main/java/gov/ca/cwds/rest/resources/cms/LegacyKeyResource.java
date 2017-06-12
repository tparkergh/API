package gov.ca.cwds.rest.resources.cms;

import static gov.ca.cwds.rest.core.Api.RESOURCE_CMS_UI_IDENTIFIER;

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
import gov.ca.cwds.inject.LegacyKeyServiceResource;
import gov.ca.cwds.logging.ApiLogUtils;
import gov.ca.cwds.rest.api.ApiException;
import gov.ca.cwds.rest.api.domain.cms.LegacyKeyRequest;
import gov.ca.cwds.rest.api.domain.cms.LegacyKeyResponse;
import gov.ca.cwds.rest.resources.SimpleResourceDelegate;
import gov.ca.cwds.rest.resources.SimpleResourceService;
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
@Api(value = RESOURCE_CMS_UI_IDENTIFIER, tags = {RESOURCE_CMS_UI_IDENTIFIER})
@Path(value = RESOURCE_CMS_UI_IDENTIFIER)
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
      @LegacyKeyServiceResource SimpleResourceDelegate<String, LegacyKeyRequest, LegacyKeyResponse, LegacyKeyService> resourceDelegate) {
    this.resourceDelegate = resourceDelegate;
  }

  /**
   * Endpoint to convert legacy 10-char id to 19-digit UI identifier.
   * 
   * @param req JSON {@link LegacyKeyRequest}
   * @return web service response
   */
  @GET
  // @Path("/")
  @ApiResponses(value = {@ApiResponse(code = 401, message = "Not Authorized"),
      @ApiResponse(code = 404, message = "Not found"),
      @ApiResponse(code = 400, message = "Unable to parse parameters")})
  @ApiOperation(value = "Convert legacy 10-char key to 19-digit UI identifier",
      code = HttpStatus.SC_OK, response = LegacyKeyResponse.class)
  @Consumes(value = MediaType.TEXT_PLAIN)
  public Response legacyKeyToUIIdentifer(@Valid @NotNull @QueryParam("key") @ApiParam(
      hidden = false, required = true, example = "U2Gaygg0Ki") LegacyKeyRequest req) {
    Response ret = null;
    try {
      ret = resourceDelegate.handle(req);
    } catch (Exception e) {
      new ApiLogUtils<>(ApiException.class).raiseError(LOGGER, e, "ERROR handling legacy key: {}",
          e.getMessage());
    }

    return ret;
  }

}
