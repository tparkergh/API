package gov.ca.cwds.rest.resources.cms;

import static gov.ca.cwds.rest.core.Api.RESOURCE_LONG_TEXT;

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

import gov.ca.cwds.inject.LongTextServiceBackedResource;
import gov.ca.cwds.rest.api.domain.cms.LongText;
import gov.ca.cwds.rest.resources.TypedResourceDelegate;
import io.dropwizard.hibernate.UnitOfWork;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * A resource providing a RESTful interface for {@link LongText}. It delegates functions to
 * {@link TypedResourceDelegate}. It decorates the {@link TypedResourceDelegate} not in
 * functionality but with @see
 * <a href= "https://github.com/swagger-api/swagger-core/wiki/Annotations-1.5.X">Swagger
 * Annotations</a> and
 * <a href="https://jersey.java.net/documentation/latest/user-guide.html#jaxrs-resources">Jersey
 * Annotations</a>
 * 
 * @author CWDS API Team
 */
@Api(value = RESOURCE_LONG_TEXT)
@Path(value = RESOURCE_LONG_TEXT)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class LongTextResource {

  private TypedResourceDelegate<String, LongText> typedResourceDelegate;

  /**
   * Constructor
   * 
   * @param typedResourceDelegate The typedResourceDelegate to delegate to.
   */
  @Inject
  public LongTextResource(
      @LongTextServiceBackedResource TypedResourceDelegate<String, LongText> typedResourceDelegate) {
    this.typedResourceDelegate = typedResourceDelegate;
  }

  /**
   * Finds an longText by id.
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
  @ApiOperation(value = "Find LongText by id", response = LongText.class, code = 200)
  public Response get(@PathParam("id") @ApiParam(required = true, name = "id",
      value = "The id of the LongText to find") String id) {
    return typedResourceDelegate.get(id);
  }

  /**
   * Delete an LongText by id.
   * 
   * @param id The id of the {@link LongText}
   * 
   * @return {@link Response}
   */
  @UnitOfWork(value = "cms")
  @DELETE
  @Path("/{id}")
  @ApiResponses(value = {@ApiResponse(code = 401, message = "Not Authorized")})
  @ApiOperation(value = "Delete LongText", code = HttpStatus.SC_OK, response = Object.class)
  public Response delete(
      @PathParam("id") @ApiParam(required = true, value = "id of LongText to delete") String id) {
    return typedResourceDelegate.delete(id);
  }

  /**
   * Create an {@link LongText}.
   * 
   * @param longText The {@link LongText}
   * 
   * @return The {@link Response}
   */
  @UnitOfWork(value = "cms")
  @POST
  @ApiResponses(value = {@ApiResponse(code = 400, message = "Unable to process JSON"),
      @ApiResponse(code = 401, message = "Not Authorized"),
      @ApiResponse(code = 406, message = "Accept Header not supported"),
      @ApiResponse(code = 409, message = "Conflict - already exists"),
      @ApiResponse(code = 422, message = "Unable to validate LongText")})
  @Consumes(value = MediaType.APPLICATION_JSON)
  @ApiOperation(value = "Create LongText", code = HttpStatus.SC_CREATED, response = LongText.class)
  public Response create(@Valid @ApiParam(hidden = false, required = true) LongText longText) {
    return typedResourceDelegate.create(longText);
  }

  /**
   * Update an {@link LongText}.
   * 
   * @param id the id
   * @param longText {@link LongText}
   *
   * @return The {@link Response}
   */
  @UnitOfWork(value = "cms")
  @PUT
  @Path("/{id}")
  @ApiResponses(value = {@ApiResponse(code = 400, message = "Unable to process JSON"),
      @ApiResponse(code = 401, message = "Not Authorized"),
      @ApiResponse(code = 404, message = "not found"),
      @ApiResponse(code = 406, message = "Accept Header not supported"),
      @ApiResponse(code = 422, message = "Unable to validate LongText")})
  @Consumes(value = MediaType.APPLICATION_JSON)
  @ApiOperation(value = "Update LongText", code = HttpStatus.SC_NO_CONTENT, response = Object.class)
  public Response update(
      @PathParam("id") @ApiParam(required = true, name = "id",
          value = "The id of the LongText to update") String id,
      @Valid @ApiParam(hidden = false) LongText longText) {
    return typedResourceDelegate.update(id, longText);
  }

}
