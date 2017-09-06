package gov.ca.cwds.rest.resources.cms;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.http.HttpStatus;

import com.google.inject.Inject;

import gov.ca.cwds.inject.SystemCodeServiceBackedResource;
import gov.ca.cwds.rest.api.domain.cms.SystemCode;
import gov.ca.cwds.rest.api.domain.cms.SystemCodeListResponse;
import gov.ca.cwds.rest.api.domain.cms.SystemMeta;
import gov.ca.cwds.rest.api.domain.cms.SystemMetaListResponse;
import gov.ca.cwds.rest.resources.ResourceDelegate;
import gov.ca.cwds.rest.resources.ServiceBackedResourceDelegate;
import io.dropwizard.hibernate.UnitOfWork;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * A resource providing a RESTful interface for {@link SystemCode}. It delegates functions to
 * {@link ServiceBackedResourceDelegate}. It decorates the {@link ServiceBackedResourceDelegate} not
 * in functionality but with @see
 * <a href= "https://github.com/swagger-api/swagger-core/wiki/Annotations-1.5.X">Swagger
 * Annotations</a> and
 * <a href="https://jersey.java.net/documentation/latest/user-guide.html#jaxrs-resources">Jersey
 * Annotations</a>
 * 
 * @author CWDS API Team
 */
// @Api(value = RESOURCE_LOV, tags = {RESOURCE_LOV})
// @Path(value = RESOURCE_LOV)
// @Produces(MediaType.APPLICATION_JSON)
// @Consumes(MediaType.APPLICATION_JSON)
public class SystemCodeResource {

  private ResourceDelegate resourceDelegate;

  /**
   * Constructor
   * 
   * @param resourceDelegate The resourceDelegate to delegate to.
   */
  @Inject
  public SystemCodeResource(@SystemCodeServiceBackedResource ResourceDelegate resourceDelegate) {
    this.resourceDelegate = resourceDelegate;
  }

  /**
   * Finds a {@link SystemCode} by id.
   * 
   * @param id The id
   *
   * @return the response
   */
  @UnitOfWork(value = "cms")
  @GET
  @Path("/{id}")
  @ApiResponses(value = {@ApiResponse(code = 401, message = "Not Authorized"),
      @ApiResponse(code = 404, message = "Not found"),
      @ApiResponse(code = 406, message = "Accept Header not supported")})
  @ApiOperation(value = "Find System Codes by foreign_key_meta_table",
      response = SystemCodeListResponse.class)
  public Response get(@PathParam("id") @ApiParam(required = true, name = "id",
      value = "The foreign_key_meta_table of the System Codes to find, this corresponds to logical_table_dsd_name in System Meta Table") String id) {
    return resourceDelegate.get(id);
  }

  /**
   * Finds list of {@link SystemMeta}
   * 
   *
   * @return the response
   */
  @UnitOfWork(value = "cms")
  @GET
  @ApiResponses(value = {@ApiResponse(code = 401, message = "Not Authorized"),
      @ApiResponse(code = 404, message = "Not found"),
      @ApiResponse(code = 406, message = "Accept Header not supported")})
  @ApiOperation(
      value = "Find all the System Meta Table records, the value of logical_table_dsd_name may be used to retrieve System Codes for a matching foreign_key_meta_table",
      response = SystemMetaListResponse.class)
  public Response get() {
    return resourceDelegate.get("");
  }

  /**
   * Delete a {@link SystemCode}
   * 
   * @param id The id of the {@link SystemCode}
   * @param acceptHeader The accept header.
   * 
   * @return {@link Response}
   */
  @DELETE
  @Path("/{id}")
  @ApiResponses(value = {@ApiResponse(code = 401, message = "Not Authorized")})
  @ApiOperation(hidden = true, value = "Delete SystemCode - not currently implemented",
      code = HttpStatus.SC_OK, response = Object.class)
  public Response delete(
      @PathParam("id") @ApiParam(required = true, value = "id of SystemCode to delete") long id,
      @HeaderParam("Accept") @ApiParam(hidden = true) String acceptHeader) {
    return Response.status(Response.Status.NOT_IMPLEMENTED).entity(null).build();
  }

  /**
   * Create a {@link SystemCode}.
   * 
   * @param id The id of the {@link SystemCode}
   * @param acceptHeader The accept header
   * @return The {@link Response}
   */
  @POST
  @ApiResponses(value = {@ApiResponse(code = 401, message = "Not Authorized")})
  @ApiOperation(hidden = true, value = "Delete SystemCode - not currently implemented",
      code = HttpStatus.SC_OK, response = Object.class)
  public Response create(
      @PathParam("id") @ApiParam(required = true, value = "id of SystemCode to create") long id,
      @HeaderParam("Accept") @ApiParam(hidden = true) String acceptHeader) {
    return Response.status(Response.Status.NOT_IMPLEMENTED).entity(null).build();
  }

  /**
   * Update a {@link SystemCode}.
   *
   * @param id The id of the {@link SystemCode}
   * @param acceptHeader The accept header.
   *
   * @return The {@link Response}
   */
  @PUT
  @ApiResponses(value = {@ApiResponse(code = 401, message = "Not Authorized")})
  @Consumes(value = MediaType.APPLICATION_JSON)
  @ApiOperation(hidden = true, value = "Delete SystemCode - not currently implemented",
      code = HttpStatus.SC_OK, response = Object.class)
  public Response update(
      @PathParam("id") @ApiParam(required = true, name = "id",
          value = "The id of the SystemCode to update") long id,
      @ApiParam(hidden = true) String acceptHeader) {
    return Response.status(Response.Status.NOT_IMPLEMENTED).entity(null).build();
  }
}
