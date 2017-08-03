package gov.ca.cwds.rest.filters;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingDeque;

import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.junit.Before;
import org.junit.Test;

import gov.ca.cwds.rest.services.cms.AbstractShiroTest;

public class RequestExecutionContextImplTest extends AbstractShiroTest {

  protected LinkedBlockingDeque<Date> resultQueue = new LinkedBlockingDeque<>(10);

  @Before
  public void setup() {
    Subject mockSubject = mock(Subject.class);
    PrincipalCollection principalCollection = mock(PrincipalCollection.class);

    List list = new ArrayList();
    list.add("msg");

    when(principalCollection.asList()).thenReturn(list);
    when(mockSubject.getPrincipals()).thenReturn(principalCollection);
    setSubject(mockSubject);
  }

  @Test
  public void type() throws Exception {
    assertThat(RequestExecutionContextImpl.class, notNullValue());
  }

  @Test
  public void getRequestCommon_Args__() throws Exception {
    RequestExecutionContextImpl.startRequest();
    RequestExecutionContext actual = RequestExecutionContext.instance();
    assertThat(actual, is(notNullValue()));
  }

  @Test
  public void getRequestCommon_Thread() throws Exception {
    ExecutorService service = Executors.newFixedThreadPool(4);
    Future<Date> future1 = execCallable(service, 4500);
    Future<Date> future2 = execCallable(service, 3500);
    Future<Date> future3 = execCallable(service, 2900);
    Future<Date> future4 = execCallable(service, 2500);

    waitOnFuture(future1);
    waitOnFuture(future2);
    waitOnFuture(future3);
    waitOnFuture(future4);

    Set<Date> set = new HashSet<>();
    for (Date dt : resultQueue) {
      System.out.println("dt=" + new SimpleDateFormat("yyyy-MM-dd-HH.mm.ss.SSS").format(dt));
      set.add(dt);
    }

    assertThat(set.size(), is(4));
  }

  protected void waitOnFuture(final Future<Date> future) {
    try {
      Date result = future.get();
    } catch (InterruptedException e) {
      e.printStackTrace();
    } catch (ExecutionException e) {
      e.printStackTrace();
    }
  }

  protected Future<Date> execCallable(ExecutorService service, int sleepMillis) {
    return service.submit(() -> {
      final long tid = Thread.currentThread().getId();
      Thread.sleep(sleepMillis); // NOSONAR
      RequestExecutionContextImpl.startRequest();
      RequestExecutionContext common = RequestExecutionContext.instance();
      final Date date = common.getRequestStartTime();
      System.out.println("thread id: " + tid + ", racf: " + common.getUserId() + ", date = "
          + new SimpleDateFormat("yyyy-MM-dd-HH.mm.ss.SSS").format(date));
      resultQueue.add(date);
      return date;
    });
  }

}
