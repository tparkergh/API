package gov.ca.cwds.rest.resources;

import static gov.ca.cwds.rest.core.Api.RESOURCE_STAFF_PERSONS;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.inject.Inject;

import gov.ca.cwds.inject.StaffPersonsServiceBackedResource;
import gov.ca.cwds.rest.api.domain.PostedStaffPerson;
import gov.ca.cwds.rest.api.domain.StaffPerson;
import io.dropwizard.hibernate.UnitOfWork;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * A resource providing a RESTful interface for {@link StaffPerson}. It delegates functions to
 * {@link TypedResourceDelegate}. It decorates the {@link TypedResourceDelegate} not in
 * functionality but with @see
 * <a href= "https://github.com/swagger-api/swagger-core/wiki/Annotations-1.5.X">Swagger
 * Annotations</a> and
 * <a href="https://jersey.java.net/documentation/latest/user-guide.html#jaxrs-resources">Jersey
 * Annotations</a>
 * 
 * @author CWDS API Team
 */
@Api(value = RESOURCE_STAFF_PERSONS)
@Path(value = RESOURCE_STAFF_PERSONS)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class StaffPersonResource {
  private TypedResourceDelegate<String, StaffPerson> typedResourceDelegate;

  /**
   * Constructor
   * 
   * @param typedResourceDelegate The typedResourceDelegate to delegate to.
   */
  @Inject
  public StaffPersonResource(
      @StaffPersonsServiceBackedResource TypedResourceDelegate<String, StaffPerson> typedResourceDelegate) {
    this.typedResourceDelegate = typedResourceDelegate;
  }


  /**
   * Finds an {@link StaffPerson} by id.
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
  @ApiOperation(value = "Find staffperson by id", response = PostedStaffPerson.class, code = 200)
  public Response get(@PathParam("id") @ApiParam(required = true, name = "id",
      value = "The id of the StaffPerson to find") String id) {
    return typedResourceDelegate.get(id);
  }

}
