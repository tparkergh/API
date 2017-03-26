package gov.ca.cwds.rest.resources;

import static gov.ca.cwds.rest.core.Api.RESOURCE_ADDRESSES;

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

import gov.ca.cwds.inject.AddressServiceBackedResource;
import gov.ca.cwds.rest.api.domain.Address;
import gov.ca.cwds.rest.api.domain.PostedAddress;
import io.dropwizard.hibernate.UnitOfWork;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * A resource providing a RESTful interface for {@link Address}. It delegates functions to
 * {@link ResourceDelegate}. It decorates the {@link ResourceDelegate} not in functionality but
 * with @see <a href= "https://github.com/swagger-api/swagger-core/wiki/Annotations-1.5.X">Swagger
 * Annotations</a> and
 * <a href="https://jersey.java.net/documentation/latest/user-guide.html#jaxrs-resources">Jersey
 * Annotations</a>
 * 
 * @author CWDS API Team
 */
@Api(value = RESOURCE_ADDRESSES, tags = RESOURCE_ADDRESSES)
@Path(value = RESOURCE_ADDRESSES)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AddressResource {
  private ResourceDelegate resourceDelegate;

  /**
   * Constructor
   * 
   * @param resourceDelegate The resourceDelegate to delegate to.
   */
  @Inject
  public AddressResource(@AddressServiceBackedResource ResourceDelegate resourceDelegate) {
    this.resourceDelegate = resourceDelegate;
  }

  /**
   * Finds an address by id.
   * 
   * @param id The id
   * 
   * @return The response
   */
  @UnitOfWork(value = "ns")
  @GET
  @Path("/{id}")
  @ApiResponses(value = {@ApiResponse(code = 401, message = "Not Authorized"),
      @ApiResponse(code = 404, message = "Not found"),
      @ApiResponse(code = 406, message = "Accept Header not supported")})
  @ApiOperation(value = "Find Address by id", response = Address.class)
  public Response get(@PathParam("id") @ApiParam(required = true, name = "id",
      value = "The id of the Address to find") long id) {
    return resourceDelegate.get(id);
  }

  /**
   * Delete an address
   * 
   * @param id The id of the {@link Address}
   * 
   * @return {@link Response}
   */
  @UnitOfWork(value = "ns")
  @DELETE
  @Path("/{id}")
  @ApiResponses(value = {@ApiResponse(code = 401, message = "Not Authorized")})
  @ApiOperation(hidden = true, value = "Delete Address - not currently implemented",
      code = HttpStatus.SC_OK, response = Object.class)
  public Response delete(
      @PathParam("id") @ApiParam(required = true, value = "id of person to delete") long id) {
    return Response.status(Response.Status.NOT_IMPLEMENTED).entity(null).build();
  }

  /**
   * Create an {@link Address}
   * 
   * @param address The {@link Address}
   * 
   * @return The {@link Response}
   */
  @UnitOfWork(value = "ns")
  @POST
  @ApiResponses(value = {@ApiResponse(code = 400, message = "Unable to process JSON"),
      @ApiResponse(code = 401, message = "Not Authorized"),
      @ApiResponse(code = 406, message = "Accept Header not supported"),
      @ApiResponse(code = 409, message = "Conflict - already exists"),
      @ApiResponse(code = 422, message = "Unable to validate Address")})
  @Consumes(value = MediaType.APPLICATION_JSON)
  @ApiOperation(value = "Create Address", code = HttpStatus.SC_CREATED,
      response = PostedAddress.class)
  public Response create(@Valid @ApiParam(hidden = false, required = true) Address address) {
    return resourceDelegate.create(address);
  }

  /**
   * Update an {@link Address}
   *
   * @param id the id
   * @param address {@link Address}
   *
   * @return The {@link Response}
   */
  @UnitOfWork(value = "ns")
  @PUT
  @ApiResponses(value = {@ApiResponse(code = 400, message = "Unable to process JSON"),
      @ApiResponse(code = 401, message = "Not Authorized"),
      @ApiResponse(code = 404, message = "not found"),
      @ApiResponse(code = 406, message = "Accept Header not supported"),
      @ApiResponse(code = 422, message = "Unable to validate Address")})
  @Consumes(value = MediaType.APPLICATION_JSON)
  @ApiOperation(hidden = true, value = "Update Address", code = HttpStatus.SC_OK,
      response = Object.class)
  public Response update(
      @PathParam("id") @ApiParam(required = true, name = "id",
          value = "The id of the Address to update") long id,
      @ApiParam(hidden = true) Address address) {
    return Response.status(Response.Status.NOT_IMPLEMENTED).entity(null).build();
  }

}
