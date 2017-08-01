package gov.ca.cwds.data.cms;

import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.hamcrest.junit.ExpectedException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;

import gov.ca.cwds.data.persistence.cms.ContactPartyDeliveredService;
import gov.ca.cwds.fixture.ContactPartyDeliverdServiceEntityBuilder;

/**
 * @author CWDS API Team
 *
 */
@SuppressWarnings("javadoc")
public class ContactPartyDeliveredServiceDaoIT {

  private static SessionFactory sessionFactory;
  private static ContactPartyDeliveredServiceDao contactPartyDeliveredServiceDao;
  private Session session;

  /*
   * pktableId matches src/main/resources/db.cms/ci-seeds.sql
   */
  private String thirdId = "EvpOcb50Eg";

  /**
   * 
   */
  @Rule
  public ExpectedException thrown = ExpectedException.none();

  /**
   * 
   */
  @BeforeClass
  public static void beforeClass() {
    sessionFactory = new Configuration().configure().buildSessionFactory();
    contactPartyDeliveredServiceDao = new ContactPartyDeliveredServiceDao(sessionFactory);
  }

  /**
   * 
   */
  @AfterClass
  public static void afterClass() {
    sessionFactory.close();
  }

  @Before
  public void setup() {
    session = sessionFactory.getCurrentSession();
    session.beginTransaction();
  }

  @After
  public void teardown() {
    session.getTransaction().rollback();
  }

  @Test
  public void testFind() throws Exception {
    ContactPartyDeliveredService found = contactPartyDeliveredServiceDao.find(thirdId);
    assertThat(found.getThirdId(), is(equalTo(thirdId)));
  }

  @Test
  public void testCreate() throws Exception {
    ContactPartyDeliveredService contactPartyDeliveredService =
        new ContactPartyDeliverdServiceEntityBuilder().setThirdId("ABC12347ho")
            .buildContactPartyDeliveredService();
    ContactPartyDeliveredService created =
        contactPartyDeliveredServiceDao.create(contactPartyDeliveredService);
    assertThat(created, is(contactPartyDeliveredService));
  }

  @Test
  public void testCreateExistingEntityException() throws Exception {
    thrown.expect(EntityExistsException.class);
    ContactPartyDeliveredService contactPartyDeliveredService =
        new ContactPartyDeliverdServiceEntityBuilder().setThirdId(thirdId)
            .buildContactPartyDeliveredService();
    contactPartyDeliveredServiceDao.create(contactPartyDeliveredService);
  }

  @Test
  public void testDelete() throws Exception {
    ContactPartyDeliveredService deleted = contactPartyDeliveredServiceDao.delete(thirdId);
    assertThat(deleted.getThirdId(), is(thirdId));
  }

  @Test
  public void testDeleteEntityNotFoundException() throws Exception {
    String thirdId = "ABC1234568";
    ContactPartyDeliveredService deleted = contactPartyDeliveredServiceDao.delete(thirdId);
    assertThat(deleted, is(nullValue()));
  }

  @Test
  public void testUpdate() throws Exception {
    ContactPartyDeliveredService contactPartyDeliveredService =
        new ContactPartyDeliverdServiceEntityBuilder().setThirdId(thirdId)
            .setCountySpecificCode("00").buildContactPartyDeliveredService();
    ContactPartyDeliveredService updated =
        contactPartyDeliveredServiceDao.update(contactPartyDeliveredService);
    assertThat(updated, is(contactPartyDeliveredService));
  }

  @Test
  public void testUpdateEntityNotFoundException() throws Exception {
    thrown.expect(EntityNotFoundException.class);
    ContactPartyDeliveredService contactPartyDeliveredService =
        new ContactPartyDeliverdServiceEntityBuilder().buildContactPartyDeliveredService();
    contactPartyDeliveredServiceDao.update(contactPartyDeliveredService);
  }

}
