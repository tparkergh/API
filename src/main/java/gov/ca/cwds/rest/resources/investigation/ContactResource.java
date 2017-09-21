package gov.ca.cwds.rest.resources.investigation;

import static gov.ca.cwds.rest.core.Api.RESOURCE_INVESTIGATIONS;
import gov.ca.cwds.inject.ContactServiceBackedResource;
import gov.ca.cwds.rest.api.domain.investigation.contact.Contact;
import gov.ca.cwds.rest.api.domain.investigation.contact.ContactList;
import gov.ca.cwds.rest.api.domain.investigation.contact.ContactRequest;
import gov.ca.cwds.rest.resources.TypedResourceDelegate;
import io.dropwizard.hibernate.UnitOfWork;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
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

/**
 * A resource providing a RESTful interface for {@link Contact}. It delegates functions to
 * {@link TypedResourceDelegate}. It decorates the {@link TypedResourceDelegate} not in
 * functionality but with @see <a href=
 * "https://github.com/swagger-api/swagger-core/wiki/Annotations-1.5.X">Swagger Annotations</a> and
 * <a href="https://jersey.java.net/documentation/latest/user-guide.html#jaxrs-resources">Jersey
 * Annotations</a>
 * 
 * @author CWDS API Team
 */
@Api(value = RESOURCE_INVESTIGATIONS)
@Path(value = RESOURCE_INVESTIGATIONS)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ContactResource {

  private TypedResourceDelegate<String, ContactRequest> typedResourceDelegate;

  /**
   * Constructor
   *
   * @param typedResourceDelegate The typedResourceDelegate to delegate to.
   */
  @Inject
  public ContactResource(
      @ContactServiceBackedResource TypedResourceDelegate<String, ContactRequest> typedResourceDelegate) {
    this.typedResourceDelegate = typedResourceDelegate;
  }

  /**
   * Create an {@link Contact}.
   *
   * @param contact The {@link ContactRequest}
   * @param id The id of the Referral the Contact is for
   *
   * @return The {@link Response}
   */
  @UnitOfWork(value = "cms")
  @POST
  @Path("/{id}/contacts")
  @ApiResponses(value = {@ApiResponse(code = 400, message = "Unable to process JSON"),
      @ApiResponse(code = 401, message = "Not Authorized"),
      @ApiResponse(code = 406, message = "Accept Header not supported"),
      @ApiResponse(code = 409, message = "Conflict - already exists"),
      @ApiResponse(code = 422, message = "Unable to validate deliveredServiceEntity")})
  @Consumes(value = MediaType.APPLICATION_JSON)
  @ApiOperation(value = "Create deliveredService", code = HttpStatus.SC_CREATED,
      response = Contact.class)
  public Response create(@PathParam("id") @ApiParam(required = true, name = "id",
      value = "The id of the Referral ") String id, @Valid @ApiParam(hidden = false,
      required = true) ContactRequest contact) {
    return typedResourceDelegate.create(contact);
  }

  /**
   * Find an {@link Contact}.
   *
   * @param id The id of the Referral the Contact is for
   * @param contactId The id of the Contact to retrieve
   *
   * @return The {@link Response}
   */
  @UnitOfWork(value = "cms")
  @GET
  @Path("/{id}/contacts/{contact_id}")
  @ApiResponses(value = {@ApiResponse(code = 400, message = "Unable to process JSON"),
      @ApiResponse(code = 401, message = "Not Authorized"),
      @ApiResponse(code = 404, message = "Not Found"),
      @ApiResponse(code = 406, message = "Accept Header not supported"),
      @ApiResponse(code = 409, message = "Conflict - already exists")})
  @Consumes(value = MediaType.APPLICATION_JSON)
  @ApiOperation(value = "Create deliveredService", code = HttpStatus.SC_OK,
      response = Contact.class)
  public Response find(@PathParam("id") @ApiParam(required = true, name = "id",
      value = "The id of the Referral ") String id, @PathParam("contact_id") @ApiParam(
      required = true, name = "contact_id", value = "The id of the Contact") String contactId) {
    String primaryKey = id + ":" + contactId;
    return typedResourceDelegate.get(primaryKey);
  }

  /**
   * Find a list of {@link Contact}.
   *
   * @param id The id of the Referral the Contacts are for
   *
   * @return The {@link Response}
   */
  @UnitOfWork(value = "cms")
  @GET
  @Path("/{id}/contacts")
  @ApiResponses(value = {@ApiResponse(code = 400, message = "Unable to process JSON"),
      @ApiResponse(code = 401, message = "Not Authorized"),
      @ApiResponse(code = 406, message = "Accept Header not supported"),
      @ApiResponse(code = 409, message = "Conflict - already exists"),
      @ApiResponse(code = 422, message = "Unable to validate Contacts")})
  @Consumes(value = MediaType.APPLICATION_JSON)
  @ApiOperation(value = "Create Contacts", code = HttpStatus.SC_OK, response = ContactList.class)
  public Response findAll(@PathParam("id") @ApiParam(required = true, name = "id",
      value = "The id of the Referral ") String id) {
    return typedResourceDelegate.get(id);
  }

  /**
   * Update an {@link Contact}.
   *
   * @param contactToUpdate The {@link Contact}
   * @param id The id of the Referral the Contact is for
   * @param contactId The id of the Contact to retrieve
   *
   * @return The {@link Response}
   */
  @UnitOfWork(value = "cms")
  @PUT
  @Path("/{id}/contacts/{contact_id}")
  @ApiResponses(value = {@ApiResponse(code = 400, message = "Unable to process JSON"),
      @ApiResponse(code = 401, message = "Not Authorized"),
      @ApiResponse(code = 406, message = "Accept Header not supported"),
      @ApiResponse(code = 409, message = "Conflict - already exists"),
      @ApiResponse(code = 422, message = "Unable to validate deliveredServiceEntity")})
  @Consumes(value = MediaType.APPLICATION_JSON)
  @ApiOperation(value = "Create deliveredService", code = HttpStatus.SC_OK,
      response = Contact.class)
  public Response update(@PathParam("id") @ApiParam(required = true, name = "id",
      value = "The id of the Referral ") String id, @PathParam("contact_id") @ApiParam(
      required = true, name = "contact_id", value = "The id of the Contact") String contactId,
      @Valid @ApiParam(hidden = false, required = true) ContactRequest contactToUpdate) {
    return typedResourceDelegate.update(id, contactToUpdate);
  }

}
