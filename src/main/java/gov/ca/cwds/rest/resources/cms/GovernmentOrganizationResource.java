package gov.ca.cwds.rest.resources.cms;

import static gov.ca.cwds.rest.core.Api.RESOURCE_GOVERNMENT_ORG;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.StringUtils;

import com.google.inject.Inject;

import gov.ca.cwds.inject.GovernmentOrganizationServiceBackedResource;
import gov.ca.cwds.rest.api.domain.cms.GovernmentOrganization;
import gov.ca.cwds.rest.api.domain.cms.GovernmentOrganizationResponse;
import gov.ca.cwds.rest.resources.SimpleResourceDelegate;
import gov.ca.cwds.rest.services.cms.GovernmentOrganizationService;
import io.dropwizard.hibernate.UnitOfWork;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * A resource providing a RESTful interface for {@link GovernmentOrganization}. It delegates
 * functions to {@link SimpleResourceDelegate}. It decorates the {@link SimpleResourceDelegate} not
 * in functionality but with @see
 * <a href= "https://github.com/swagger-api/swagger-core/wiki/Annotations-1.5.X">Swagger
 * Annotations</a> and
 * <a href="https://jersey.java.net/documentation/latest/user-guide.html#jaxrs-resources">Jersey
 * Annotations</a>
 * 
 * @author CWDS API Team
 */
@Api(value = RESOURCE_GOVERNMENT_ORG, tags = {RESOURCE_GOVERNMENT_ORG})
@Path(value = RESOURCE_GOVERNMENT_ORG)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class GovernmentOrganizationResource {

  private SimpleResourceDelegate<String, GovernmentOrganization, GovernmentOrganizationResponse, GovernmentOrganizationService> simpleResourceDelegate;

  /**
   * @param simpleResourceDelegate the simpleResourceDelegate
   */
  @Inject
  public GovernmentOrganizationResource(
      @GovernmentOrganizationServiceBackedResource SimpleResourceDelegate<String, GovernmentOrganization, GovernmentOrganizationResponse, GovernmentOrganizationService> simpleResourceDelegate) {
    super();
    this.simpleResourceDelegate = simpleResourceDelegate;
  }

  /**
   * @param countyId - countyId
   * @return the all the cross report agencies
   */
  @UnitOfWork(value = "cms")
  @GET
  @ApiResponses(value = {@ApiResponse(code = 401, message = "Not Authorized"),
      @ApiResponse(code = 404, message = "Not found"),
      @ApiResponse(code = 406, message = "Accept Header not supported")})
  @ApiOperation(value = "Get all the agencyName and agencyType for the countyCode",
      response = GovernmentOrganization[].class, code = 200)
  public Response get(@QueryParam("countyId") @ApiParam(required = false,
      value = "The county id") String countyId) {
    if (StringUtils.isEmpty(countyId)) {
      countyId = "";
    }

    return simpleResourceDelegate.find(countyId);
  }

}
