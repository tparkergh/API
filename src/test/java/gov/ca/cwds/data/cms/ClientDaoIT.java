package gov.ca.cwds.data.cms;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.hamcrest.junit.ExpectedException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;

import gov.ca.cwds.data.junit.template.DaoTestTemplate;
import gov.ca.cwds.data.persistence.cms.Client;

public class ClientDaoIT implements DaoTestTemplate {
  private static final DateFormat TIMESTAMP_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
  private DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
  private String birthDateString = "1972-08-17";
  private String creationDateString = "2004-08-17";

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  private static ClientDao clientDao;
  private static SessionFactory sessionFactory;
  private Session session;

  @BeforeClass
  public static void beforeClass() {
    sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
    clientDao = new ClientDao(sessionFactory);
  }

  @AfterClass
  public static void afterClass() {
    sessionFactory.close();
  }

  @Override
  @Before
  public void setup() {
    session = sessionFactory.getCurrentSession();
    session.beginTransaction();
  }

  @Override
  @After
  public void teardown() throws Exception {
    session.getTransaction().rollback();
  }

  @Override
  @Test
  public void testFindAllNamedQueryExist() throws Exception {
    Query query = session.getNamedQuery("gov.ca.cwds.data.persistence.cms.Client.findAll");
    assertThat(query, is(notNullValue()));
  }

  @Override
  @Test
  public void testFindAllReturnsCorrectList() {
    Query query = session.getNamedQuery("gov.ca.cwds.data.persistence.cms.Client.findAll");
    assertThat(query.list().size(), is(3));
  }

  @SuppressWarnings("javadoc")
  @Test
  public void testfindAllUpdatedAfterNamedQueryExists() throws Exception {
    Query query =
        session.getNamedQuery("gov.ca.cwds.data.persistence.cms.Client.findAllUpdatedAfter");
    assertThat(query, is(notNullValue()));
  }

  @SuppressWarnings("javadoc")
  @Test
  public void testfindAllUpdatedAfterReturnsCorrectList() throws Exception {
    Query query =
        session.getNamedQuery("gov.ca.cwds.data.persistence.cms.Client.findAllUpdatedAfter")
            .setDate("after", TIMESTAMP_FORMAT.parse("2000-11-02 00:00:00"));
    assertThat(query.list().size(), is(1));
  }

  @Override
  @Test
  public void testFind() {
    String id = "AaiU7IW0Rt";
    Client found = clientDao.find(id);
    assertThat(found.getId(), is(id));
  }

  @Override
  @Test
  public void testFindEntityNotFoundException() throws Exception {
    String id = "9999999ZZZ";
    Client found = clientDao.find(id);
    assertThat(found, is(nullValue()));
  }

  @Override
  @Test
  public void testCreate() throws Exception {
    Date birthDate = df.parse(birthDateString);
    Date creationDate = df.parse(creationDateString);
    Client client = new Client(null, "N", " ", " ", (short) 0, birthDate, "", (short) 0, "N", "N",
        null, " ", "Tumbling", "Waters", " ", null, "N", creationDate, "N", " ", "N", null, "N",
        null, null, " ", (short) 0, null, "Y", null, null, "M", null, null, "U", "AbiOD9Y0Hj",
        (short) 0, (short) 0, "U", "N", "N", "U", "N", (short) 0, "U", null, " ", (short) 1313, "N",
        "N", " ", "N", (short) 0, (short) 0, (short) 0, (short) 0, "N", "N", "N", "N", "O", " ",
        " ", "N", "N", "U", "N");
    Client created = clientDao.create(client);
    assertThat(created, is(client));
  }

  @Override
  @Test
  public void testCreateExistingEntityException() throws Exception {
    thrown.expect(EntityExistsException.class);
    Date birthDate = df.parse(birthDateString);
    Date creationDate = df.parse(creationDateString);
    Client client = new Client(null, "N", " ", " ", (short) 0, birthDate, "", (short) 0, "N", "N",
        null, " ", "Tumbling", "Waters", " ", null, "N", creationDate, "N", " ", "N", null, "N",
        null, null, " ", (short) 0, null, "Y", null, null, "M", null, null, "U", "AaiU7IW0Rt",
        (short) 0, (short) 0, "U", "N", "N", "U", "N", (short) 0, "U", null, " ", (short) 1313, "N",
        "N", " ", "N", (short) 0, (short) 0, (short) 0, (short) 0, "N", "N", "N", "N", "O", " ",
        " ", "N", "N", "U", "N");
    clientDao.create(client);
  }

  @Override
  @Test
  public void testDelete() {
    String id = "AaiU7IW0Rt";
    Client deleted = clientDao.delete(id);
    assertThat(deleted.getId(), is(id));
  }

  @Override
  public void testDeleteEntityNotFoundException() throws Exception {
    String id = "9999999ZZZ";
    Client deleted = clientDao.delete(id);
    assertThat(deleted, is(nullValue()));
  }

  @Override
  @Test
  public void testUpdate() throws Exception {
    Date birthDate = df.parse(birthDateString);
    Date creationDate = df.parse(creationDateString);
    Client client = new Client(null, "N", " ", " ", (short) 0, birthDate, "", (short) 0, "N", "N",
        null, " ", "Tumbling", "Waters", " ", null, "N", creationDate, "N", " ", "N", null, "N",
        null, null, " ", (short) 0, null, "Y", null, null, "M", null, null, "U", "AaiU7IW0Rt",
        (short) 0, (short) 0, "U", "N", "N", "U", "N", (short) 0, "U", null, " ", (short) 1314, "N",
        "N", " ", "N", (short) 0, (short) 0, (short) 0, (short) 0, "N", "N", "N", "N", "O", " ",
        " ", "N", "N", "U", "N");
    Client updated = clientDao.update(client);
    assertThat(updated, is(client));
  }

  @Override
  @Test
  public void testUpdateEntityNotFoundException() throws Exception {
    thrown.expect(EntityNotFoundException.class);
    Date birthDate = df.parse(birthDateString);
    Date creationDate = df.parse(creationDateString);
    Client client = new Client(null, "N", " ", " ", (short) 0, birthDate, "", (short) 0, "N", "N",
        null, " ", "Tumbling", "Waters", " ", null, "N", creationDate, "N", " ", "N", null, "N",
        null, null, " ", (short) 0, null, "Y", null, null, "M", null, null, "U", "AasRx3r0Ha",
        (short) 0, (short) 0, "U", "N", "N", "U", "N", (short) 0, "U", null, " ", (short) 1313, "N",
        "N", " ", "N", (short) 0, (short) 0, (short) 0, (short) 0, "N", "N", "N", "N", "O", " ",
        " ", "N", "N", "U", "N");
    clientDao.update(client);
  }

}
