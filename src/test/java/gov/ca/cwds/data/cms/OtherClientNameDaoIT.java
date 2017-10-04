package gov.ca.cwds.data.cms;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

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
import gov.ca.cwds.data.persistence.cms.OtherClientName;

/**
 * @author CWDS API Team
 *
 */
public class OtherClientNameDaoIT implements DaoTestTemplate {
  private static final DateFormat TIMESTAMP_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

  @SuppressWarnings("javadoc")
  @Rule
  public ExpectedException thrown = ExpectedException.none();

  private static OtherClientNameDao otherClientNameDao;
  private static SessionFactory sessionFactory;
  private Session session;

  @SuppressWarnings("javadoc")
  @BeforeClass
  public static void beforeClass() {
    sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
    otherClientNameDao = new OtherClientNameDao(sessionFactory);
  }

  @SuppressWarnings("javadoc")
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
  public void teardown() {
    session.getTransaction().rollback();
  }

  @Override
  @Test
  public void testFindAllNamedQueryExist() throws Exception {
    Query query = session.getNamedQuery("gov.ca.cwds.data.persistence.cms.OtherClientName.findAll");
    assertThat(query, is(notNullValue()));
  }

  @SuppressWarnings("unchecked")
  @Override
  @Test
  public void testFindAllReturnsCorrectList() throws Exception {
    Query query = session.getNamedQuery("gov.ca.cwds.data.persistence.cms.OtherClientName.findAll");
    final List<OtherClientName> list = query.list();
    System.out.println("size of query list is: " + list.size());
    for (OtherClientName oc : list) {
      System.out.println("id " + oc.getThirdId() + " " + oc.getClientId() + " " + oc.getFirstName()
          + " " + oc.getLastName() + " " + oc.getLastUpdatedTime());
    }
    assertThat(query.list().size(), is(2));
  }

  @SuppressWarnings("javadoc")
  @Test
  public void testfindAllUpdatedAfterNamedQueryExists() throws Exception {
    Query query = session
        .getNamedQuery("gov.ca.cwds.data.persistence.cms.OtherClientName.findAllUpdatedAfter");
    assertThat(query, is(notNullValue()));
  }

  // TODO: #138438305: uncomment after RS schema is available.
  // @SuppressWarnings("javadoc")
  // @Test
  // public void testfindAllUpdatedAfterReturnsCorrectList() throws Exception {
  // Query query = session
  // .getNamedQuery("gov.ca.cwds.data.persistence.cms.OtherClientName.findAllUpdatedAfter")
  // .setTimestamp("after",
  // new Timestamp(TIMESTAMP_FORMAT.parse("2000-01-01 00:00:00").getTime()));
  // @SuppressWarnings("unchecked")
  // final List<OtherClientName> list = query.list();
  // System.out.println("size of query list is: " + list.size());
  // for (OtherClientName oc : list) {
  // System.out.println("id " + oc.getThirdId() + " " + oc.getClientId() + " " + oc.getFirstName()
  // + " " + oc.getLastName() + " " + oc.getLastUpdatedTime());
  // }
  // assertThat(query.list().size(), is(1));
  // }

  @Override
  @Test
  public void testFind() throws Exception {
    final String thirdId = "AakYNc30AA";
    OtherClientName found = otherClientNameDao.find(thirdId);
    assertThat(found.getThirdId(), is(thirdId));
  }

  @Override
  @Test
  public void testFindEntityNotFoundException() throws Exception {
    final String thirdId = "ZZZZZZZ999";
    OtherClientName found = otherClientNameDao.find(thirdId);
    assertThat(found, is(nullValue()));
  }

  @Override
  @Test
  public void testCreate() throws Exception {
    OtherClientName otherClientName = new OtherClientName("AapJGAU04Z", "Gregg", "Hill", "Brian",
        "1", (short) 1, "1", "BCD1234567");
    OtherClientName created = otherClientNameDao.create(otherClientName);
    assertThat(created, is(otherClientName));
  }

  @Override
  @Test(expected = EntityExistsException.class)
  public void testCreateExistingEntityException() throws Exception {
    OtherClientName otherClientName = new OtherClientName("Ab6Lwal0KL", "Gregg", "Hill", "Brian",
        "1", (short) 1, "1", "Ab6Lwal0Ki");
    otherClientNameDao.create(otherClientName);
  }

  @Override
  @Test
  public void testDelete() throws Exception {
    final String thirdId = "AakYNc30AA";
    OtherClientName deleted = otherClientNameDao.delete(thirdId);
    assertThat(deleted.getThirdId(), is(thirdId));
  }

  @Override
  @Test
  public void testDeleteEntityNotFoundException() throws Exception {
    final String thirdId = "ZZZZZZZ999";
    OtherClientName deleted = otherClientNameDao.delete(thirdId);
    assertThat(deleted, is(nullValue()));

  }

  @Override
  @Test
  public void testUpdate() throws Exception {
    OtherClientName otherClientName = new OtherClientName("AakYNc30AK", "Gregory", "Hill", "Brian",
        "1", (short) 1, "1", "AakYNc30AA");
    OtherClientName updated = otherClientNameDao.update(otherClientName);
    assertThat(updated, is(otherClientName));
  }

  @Override
  @Test(expected = EntityNotFoundException.class)
  public void testUpdateEntityNotFoundException() throws Exception {
    OtherClientName otherClientName = new OtherClientName("ZZZZZZZ999", "Gregory", "Hill", "Brian",
        "1", (short) 1, "1", "ZZZZZZZ999");
    otherClientNameDao.update(otherClientName);
  }

}
