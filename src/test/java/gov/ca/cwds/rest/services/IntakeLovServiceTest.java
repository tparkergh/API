package gov.ca.cwds.rest.services;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.Test;

import gov.ca.cwds.data.ns.IntakeLovDao;
import gov.ca.cwds.rest.api.domain.IntakeLovEntry;
import gov.ca.cwds.rest.api.domain.IntakeLovResponse;

public class IntakeLovServiceTest {

  @Test
  public void type() throws Exception {
    assertThat(IntakeLovService.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    IntakeLovDao dao = null;
    IntakeLovService target = new IntakeLovService(dao);
    assertThat(target, notNullValue());
  }

  @Test
  public void handleRequest_Args__IntakeLov() throws Exception {
    IntakeLovDao dao = mock(IntakeLovDao.class);
    when(dao.findAll()).thenReturn(new ArrayList<gov.ca.cwds.data.persistence.ns.IntakeLov>());

    IntakeLovService target = new IntakeLovService(dao);
    IntakeLovEntry req = mock(IntakeLovEntry.class);

    IntakeLovResponse actual = target.handleRequest(req);
    IntakeLovResponse expected = null;
    assertThat(actual, notNullValue());
  }

  @Test
  public void handleFind_Args__String() throws Exception {
    IntakeLovDao dao = mock(IntakeLovDao.class);
    when(dao.findAll()).thenReturn(new ArrayList<gov.ca.cwds.data.persistence.ns.IntakeLov>());

    IntakeLovService target = new IntakeLovService(dao);
    String searchForThis = null;
    IntakeLovResponse actual = target.handleFind(searchForThis);
    IntakeLovResponse expected = new IntakeLovResponse(new ArrayList<IntakeLovEntry>());
    assertThat(actual, is(equalTo(expected)));
  }

  @Test(expected = ServiceException.class)
  public void handleFind_error() throws Exception {
    IntakeLovDao dao = mock(IntakeLovDao.class);
    when(dao.findAll()).thenThrow(ServiceException.class);

    IntakeLovService target = new IntakeLovService(dao);
    String searchForThis = null;
    IntakeLovResponse actual = target.handleFind(searchForThis);
  }

}
