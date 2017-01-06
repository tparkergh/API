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

import com.google.inject.Inject;

import gov.ca.cwds.inject.PersonServiceBackedResource;
import gov.ca.cwds.rest.api.ApiException;
import gov.ca.cwds.rest.api.domain.PostedPerson;
import gov.ca.cwds.rest.api.domain.es.ESPerson;
import gov.ca.cwds.rest.api.domain.es.ESPersonSearchRequest;
import gov.ca.cwds.rest.services.PersonService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * A resource providing a RESTful interface for {@link PostedPerson}. It delegates functions to
 * {@link ServiceBackedResourceDelegate}. It decorates the {@link ServiceBackedResourceDelegate} not
 * in functionality but with @see
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

  private ResourceDelegate resourceDelegate;

  /**
   * Constructor
   * 
   * @param resourceDelegate The resourceDelegate to delegate to.
   */
  @Inject
  public AutoCompletePersonResource(
      @PersonServiceBackedResource ResourceDelegate resourceDelegate) {
    this.resourceDelegate = resourceDelegate;
  }

  /**
   * Return all Persons in ElasticSearch.
   * 
   * @return the response
   */
  @POST
  @Path("/all")
  @ApiResponses(value = {@ApiResponse(code = 404, message = "Not found"),
      @ApiResponse(code = 406, message = "Accept Header not supported")})
  @ApiOperation(value = "Fetch ALL Persons from ElasticSearch", response = ESPerson[].class)
  public Response showAllPersons() {

    ESPerson[] hits = null;
    try {
      // TODO: remove cast abuse. Story #136701343.
      hits = ((PersonService) ((ServiceBackedResourceDelegate) resourceDelegate).getService())
          .fetchAllPersons();
    } catch (Exception e) {
      throw new ApiException("ERROR calling ES \"fetch all persons\"", e);
    }

    if (hits != null) {
      return Response.ok(hits).build();
    } else {
      return Response.status(Response.Status.NOT_FOUND).entity(null).build();
    }
  }

  /**
   * Query Persons in ElasticSearch by searching on one of the following: first name, last name or
   * birth date.
   * 
   * @param req JSON {@link ESPersonSearchRequest}
   * @return the response
   */
  @POST
  @Path("/query_or")
  @ApiResponses(value = {@ApiResponse(code = 404, message = "Not found"),
      @ApiResponse(code = 400, message = "Unable to process JSON"),
      @ApiResponse(code = 406, message = "Accept Header not supported")})
  @ApiOperation(
      value = "Query Persons from ElasticSearch by first non-blank field. Wildcards allowed (*,?)",
      code = HttpStatus.SC_OK, response = ESPerson[].class)
  @Consumes(value = MediaType.APPLICATION_JSON)
  public Response queryPersonOrTerm(
      @Valid @ApiParam(hidden = false, required = true) ESPersonSearchRequest req) {
    ESPerson[] hits = null;
    try {
      // TODO: remove cast abuse. Story #136701343.
      hits = ((PersonService) ((ServiceBackedResourceDelegate) resourceDelegate).getService())
          .queryPersonOr(req.getFirstName(), req.getLastName(), req.getBirthDate());
    } catch (Exception e) {
      throw new ApiException("ERROR calling ES \"query person OR\"", e);
    }

    if (hits != null) {
      return Response.ok(hits).build();
    } else {
      return Response.status(Response.Status.NOT_FOUND).entity(null).build();
    }
  }

}
