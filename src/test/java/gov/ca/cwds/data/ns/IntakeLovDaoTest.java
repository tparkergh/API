package gov.ca.cwds.data.ns;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.Connection;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.spi.SessionFactoryOptions;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;
import org.junit.Before;
import org.junit.Test;

public class IntakeLovDaoTest {

  private SessionFactory sessionFactory;

  @Before
  public void setup() throws Exception {
    SessionFactory sessionFactory = mock(SessionFactory.class);
    Session session = mock(Session.class);

    Transaction transaction = mock(Transaction.class);
    when(sessionFactory.getCurrentSession()).thenReturn(session);
    when(session.beginTransaction()).thenReturn(transaction);

    sessionFactory = mock(SessionFactory.class);

    SessionFactoryOptions sfo = mock(SessionFactoryOptions.class);
    when(sessionFactory.getSessionFactoryOptions()).thenReturn(sfo);

    StandardServiceRegistry reg = mock(StandardServiceRegistry.class);
    when(sfo.getServiceRegistry()).thenReturn(reg);

    ConnectionProvider cp = mock(ConnectionProvider.class);
    when(reg.getService(ConnectionProvider.class)).thenReturn(cp);

    Connection con = mock(Connection.class);
    when(cp.getConnection()).thenReturn(con);
  }

  @Test
  public void type() throws Exception {
    assertThat(IntakeLovDao.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    IntakeLovDao target = new IntakeLovDao(sessionFactory);
    assertThat(target, notNullValue());
  }

}
