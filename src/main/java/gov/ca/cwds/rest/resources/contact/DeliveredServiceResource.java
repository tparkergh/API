package gov.ca.cwds.rest.resources.contact;

import static gov.ca.cwds.rest.core.Api.RESOURCE_DELIVERY_SERVICE;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.http.HttpStatus;

import com.google.inject.Inject;

import gov.ca.cwds.inject.DeliveredServiceBackedResource;
import gov.ca.cwds.rest.api.contact.DeliveredServiceDomain;
import gov.ca.cwds.rest.api.domain.cms.DrmsDocument;
import gov.ca.cwds.rest.resources.TypedResourceDelegate;
import gov.ca.cwds.rest.services.contact.DeliveredService;
import io.dropwizard.hibernate.UnitOfWork;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * A resource providing a RESTful interface for {@link DeliveredServiceDomain}. It delegates
 * functions to {@link TypedResourceDelegate}. It decorates the {@link TypedResourceDelegate} not in
 * functionality but with @see
 * <a href= "https://github.com/swagger-api/swagger-core/wiki/Annotations-1.5.X">Swagger
 * Annotations</a> and
 * <a href="https://jersey.java.net/documentation/latest/user-guide.html#jaxrs-resources">Jersey
 * Annotations</a>
 * 
 * @author CWDS API Team
 */
@Api(value = RESOURCE_DELIVERY_SERVICE)
@Path(value = RESOURCE_DELIVERY_SERVICE)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class DeliveredServiceResource {

  private TypedResourceDelegate<String, DeliveredServiceDomain> typedResourceDelegate;

  /**
   * Constructor
   *
   * @param typedResourceDelegate The typedResourceDelegate to delegate to.
   */
  @Inject
  public DeliveredServiceResource(
      @DeliveredServiceBackedResource TypedResourceDelegate<String, DeliveredServiceDomain> typedResourceDelegate) {
    this.typedResourceDelegate = typedResourceDelegate;
  }

  /**
   * Create an {@link DrmsDocument}.
   *
   * @param deliveredServiceDomain The {@link DeliveredService}
   *
   * @return The {@link Response}
   */
  @UnitOfWork(value = "cms")
  @POST
  @ApiResponses(value = {@ApiResponse(code = 400, message = "Unable to process JSON"),
      @ApiResponse(code = 401, message = "Not Authorized"),
      @ApiResponse(code = 406, message = "Accept Header not supported"),
      @ApiResponse(code = 409, message = "Conflict - already exists"),
      @ApiResponse(code = 422, message = "Unable to validate deliveredServiceEntity")})
  @Consumes(value = MediaType.APPLICATION_JSON)
  @ApiOperation(value = "Create deliveredService", code = HttpStatus.SC_CREATED,
      response = DeliveredServiceDomain.class)
  public Response create(@Valid @ApiParam(hidden = false,
      required = true) DeliveredServiceDomain deliveredServiceDomain) {
    return typedResourceDelegate.create(deliveredServiceDomain);
  }

}
