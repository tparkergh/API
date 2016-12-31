package gov.ca.cwds.data.cms;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.hamcrest.junit.ExpectedException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Rule;

import gov.ca.cwds.data.BaseDaoImpl;
import gov.ca.cwds.data.persistence.PersistentObject;

public abstract class CmsBaseDaoTest<T extends BaseDaoImpl<? extends PersistentObject>> {

  protected static final DateFormat TIMESTAMP_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

  // Construct session factory once for all supported tests.
  protected static SessionFactory sessionFactory;

  static {
    getSessionFactory();
  }

  protected T dao;
  protected Session session;
  protected DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  public static SessionFactory getSessionFactory() {
    if (sessionFactory == null) {
      synchronized (CmsBaseDaoTest.class) {
        if (sessionFactory == null) {
          sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
        }
      }
    }
    return sessionFactory;
  }

  @AfterClass
  public static void afterClass() {
    sessionFactory.close();
  }

  @Before
  public void setup() {
    session = getSessionFactory().getCurrentSession();
    session.beginTransaction();
  }

  @After
  public void tearddown() {
    session.getTransaction().rollback();
  }

}
