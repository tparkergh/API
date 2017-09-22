package gov.ca.cwds.rest.services.cms;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.services.CrudsService;
import java.util.HashMap;
import java.util.Map;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Test;

public class CmsNSHelperTest {

  Map<CrudsService, Request> cmsRequests;
  Map<CrudsService, Request> nsRequests;

  SessionFactory cmsSessionFactory;
  SessionFactory nsSessionFactory;
  Session cmsSession;
  Session nsSession;

  CmsNSHelper helper;

  @Before
  public void setup(){
    cmsRequests = new HashMap();
    nsRequests = new HashMap();

    cmsSession = mock(Session.class);
    cmsSessionFactory = mock(SessionFactory.class);
    when(cmsSessionFactory.openSession()).thenReturn(cmsSession);

    nsSession = mock(Session.class);
    nsSessionFactory = mock(SessionFactory.class);
    when(nsSessionFactory.openSession()).thenReturn(nsSession);

    helper = new CmsNSHelper(cmsSessionFactory, nsSessionFactory);

  }

  @Test
  public void shouldCloseHibernateCmsSessionResource() {
    helper.handleResponse(cmsRequests, nsRequests);
    verify(cmsSession).close();
  }

  @Test
  public void shouldCloseHibernateNsSessionResource() {
    helper.handleResponse(cmsRequests, nsRequests);
    verify(nsSession).close();
  }
}
