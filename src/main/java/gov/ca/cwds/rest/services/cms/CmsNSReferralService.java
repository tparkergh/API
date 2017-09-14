package gov.ca.cwds.rest.services.cms;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.NotImplementedException;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import gov.ca.cwds.inject.CmsSessionFactory;
import gov.ca.cwds.inject.NsSessionFactory;
import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.PostedPerson;
import gov.ca.cwds.rest.api.domain.cms.CmsNSReferral;
import gov.ca.cwds.rest.api.domain.cms.CmsReferral;
import gov.ca.cwds.rest.api.domain.cms.PostedCmsNSReferral;
import gov.ca.cwds.rest.api.domain.cms.PostedReferral;
import gov.ca.cwds.rest.services.CrudsService;
import gov.ca.cwds.rest.services.PersonService;

/**
 * @author CWDS API Team
 *
 */
public class CmsNSReferralService implements CrudsService {

  @SuppressWarnings("unused")
  private static final Logger LOGGER = LoggerFactory.getLogger(CmsNSReferralService.class);

  private ReferralService referralService;
  private PersonService personService;

  @Inject
  @CmsSessionFactory
  private SessionFactory cmsSessionFactory;

  @Inject
  @NsSessionFactory
  private SessionFactory nsSessionFactory;

  /**
   * Constructor
   * 
   * @param referralService the referralService
   * @param personService the personService
   * @param sessionFactory the CmsSessionFactory
   * @param nsSessionFactory the NsSessionFactory
   */
  @Inject
  public CmsNSReferralService(@CmsSessionFactory SessionFactory sessionFactory,
      @NsSessionFactory SessionFactory nsSessionFactory, ReferralService referralService,
      PersonService personService) {
    super();
    this.referralService = referralService;
    this.personService = personService;
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#create(gov.ca.cwds.rest.api.Request)
   */
  @Override
  public Response create(Request request) {

    assert request instanceof CmsReferral;
    CmsNSReferral cmsReferral = (CmsNSReferral) request;

    CmsNSHelper helper = new CmsNSHelper(cmsSessionFactory, nsSessionFactory);

    Map<CrudsService, Request> cmsRequest = new HashMap<>();
    Map<CrudsService, Request> nsRequest = new HashMap<>();

    cmsRequest.put((CrudsService) referralService, cmsReferral.getReferral());
    nsRequest.put(personService, cmsReferral.getPerson());

    Map<String, Map<CrudsService, Response>> response =
        helper.handleResponse(cmsRequest, nsRequest);
    return new PostedCmsNSReferral((PostedReferral) response.get("cms").get(referralService),
        (PostedPerson) response.get("ns").get(personService));

  }


  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#find(java.io.Serializable)
   */
  @Override
  public Response find(Serializable primaryKey) {
    throw new NotImplementedException("find not implement");
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#delete(java.io.Serializable)
   */
  @Override
  public Response delete(Serializable primaryKey) {
    throw new NotImplementedException("delete not implement");
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#update(java.io.Serializable,
   *      gov.ca.cwds.rest.api.Request)
   */
  @Override
  public Response update(Serializable primaryKey, Request request) {
    throw new NotImplementedException("update not implement");
  }

}
