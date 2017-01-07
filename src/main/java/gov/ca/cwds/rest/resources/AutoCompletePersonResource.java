package gov.ca.cwds.rest.resources;

import static gov.ca.cwds.rest.core.Api.RESOURCE_AUTOCOMPLETE_PERSON;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import gov.ca.cwds.inject.IntakePersonAutoCompleteServiceResource;
import gov.ca.cwds.rest.api.ApiException;
import gov.ca.cwds.rest.api.domain.es.ESPerson;
import gov.ca.cwds.rest.api.domain.es.ESPersonSearchRequest;
import gov.ca.cwds.rest.api.domain.es.ESPersons;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
@Api(value = RESOURCE_AUTOCOMPLETE_PERSON, tags = {RESOURCE_AUTOCOMPLETE_PERSON})
@Path(value = RESOURCE_AUTOCOMPLETE_PERSON)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AutoCompletePersonResource {

  /**
   * Logger for this class.
   */
  private static final Logger LOGGER = LoggerFactory.getLogger(AutoCompletePersonResource.class);

  /**
   * Java desperately needs short-hand notation for typed interfaces and classes, such as C++
   * "typedef" or "using alias".
   */
  private SimpleResourceDelegate<String, ESPersonSearchRequest, ESPersons, SimpleResourceService<String, ESPersonSearchRequest, ESPersons>> resourceDelegate;

  /**
   * Constructor
   * 
   * @param resourceDelegate The resourceDelegate to delegate to.
   */
  @Inject
  public AutoCompletePersonResource(
      @IntakePersonAutoCompleteServiceResource SimpleResourceDelegate<String, ESPersonSearchRequest, ESPersons, SimpleResourceService<String, ESPersonSearchRequest, ESPersons>> resourceDelegate) {
    this.resourceDelegate = resourceDelegate;
  }

  public final Object[] getTypeParams() {
    return resourceDelegate.getClass().getTypeParameters();
  }

  /**
   * Query Persons in ElasticSearch by searching on one of the following: first name, last name or
   * birth date.
   * 
   * @param req JSON {@link ESPersonSearchRequest}
   * @return the response
   */
  @POST
  @Path("/person_autocomplete")
  @ApiResponses(value = {@ApiResponse(code = 404, message = "Not found"),
      @ApiResponse(code = 400, message = "Unable to process JSON"),
      @ApiResponse(code = 406, message = "Accept Header not supported")})
  @ApiOperation(
      value = "Query Persons from ElasticSearch by first non-blank field. Wildcards allowed (*,?)",
      code = HttpStatus.SC_OK, response = ESPerson[].class)
  @Consumes(value = MediaType.APPLICATION_JSON)
  public Response queryPersonOrTerm(
      @Valid @ApiParam(hidden = false, required = true) ESPersonSearchRequest req) {

    LOGGER.info("parameterized types: {}", this.getTypeParams());

    Response ret;
    try {
      ret = resourceDelegate.handle(req);
    } catch (Exception e) {
      LOGGER.error("Intake Person AutoComplete ERROR: {}", e.getMessage());
      throw new ApiException("Intake Person AutoComplete ERROR.", e);
    }

    return ret;
  }

}
