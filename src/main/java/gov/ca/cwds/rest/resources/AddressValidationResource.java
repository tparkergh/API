package gov.ca.cwds.rest.resources;

import static gov.ca.cwds.rest.core.Api.RESOURCE_ADDRESS_VALIDATION;
import gov.ca.cwds.inject.AddressValidationServiceBackedResource;
import gov.ca.cwds.rest.api.ApiException;
import gov.ca.cwds.rest.api.domain.Address;
import gov.ca.cwds.rest.api.domain.ValidatedAddress;
import gov.ca.cwds.rest.services.AddressValidationService;
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

import com.google.inject.Inject;

/**
 * A resource providing a RESTful interface for {@link ValidatedAddress}. It delegates functions to
 * {@link ResourceDelegate}. It decorates the {@link ResourceDelegate} not in functionality but with @see
 * <a href= "https://github.com/swagger-api/swagger-core/wiki/Annotations-1.5.X">Swagger
 * Annotations</a> and <a
 * href="https://jersey.java.net/documentation/latest/user-guide.html#jaxrs-resources">Jersey
 * Annotations</a>
 * 
 * @author CWDS API Team
 */
@Api(value = RESOURCE_ADDRESS_VALIDATION, tags = RESOURCE_ADDRESS_VALIDATION)
@Path(value = RESOURCE_ADDRESS_VALIDATION)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AddressValidationResource {
  private ResourceDelegate resourceDelegate;

  /**
   * Constructor
   * 
   * @param resourceDelegate The resourceDelegate to delegate to.
   */
  @Inject
  public AddressValidationResource(
      @AddressValidationServiceBackedResource ResourceDelegate resourceDelegate) {
    this.resourceDelegate = resourceDelegate;
  }

  /**
   * Returns a list of Validated Addresses
   * 
   * @param address The {@link Address}
   * 
   * @return The {@link Response}
   */
  @POST
  @ApiResponses(value = {@ApiResponse(code = 400, message = "Unable to process JSON"),
      @ApiResponse(code = 406, message = "Accept Header not supported"),
      @ApiResponse(code = 409, message = "Conflict - already exists"),
      @ApiResponse(code = 422, message = "Unable to validate Address")})
  @Consumes(value = MediaType.APPLICATION_JSON)
  @ApiOperation(value = "Create Address", code = HttpStatus.SC_CREATED,
      response = ValidatedAddress[].class)
  public Response create(@Valid @ApiParam(hidden = false, required = true) Address address) {
    ValidatedAddress[] addresses = null;
    try {
      addresses =
          ((AddressValidationService) ((ServiceBackedResourceDelegate) resourceDelegate)
              .getService()).fetchValidatedAddresses(address);
    } catch (Exception e) {
      throw new ApiException("ERROR calling SmartyStreet to fetch Validated Addresses", e);
    }
    if (addresses != null) {
      return Response.ok(addresses).build();
    } else {
      return Response.status(Response.Status.NOT_FOUND).entity(null).build();
    }
  }

}
