package gov.ca.cwds.data.dao.contact;

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

import gov.ca.cwds.data.persistence.contact.ContactPartyDeliveredServiceEntity;
import gov.ca.cwds.fixture.contacts.ContactPartyDeliverdServiceEntityBuilder;

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
   * thirdId matches src/main/resources/db.cms/ci-seeds.sql
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
    ContactPartyDeliveredServiceEntity found = contactPartyDeliveredServiceDao.find(thirdId);
    assertThat(found.getThirdId(), is(equalTo(thirdId)));
  }

  @Test
  public void testCreate() throws Exception {
    ContactPartyDeliveredServiceEntity contactPartyDeliveredServiceEntity =
        new ContactPartyDeliverdServiceEntityBuilder().setThirdId("ABC12347ho")
            .buildContactPartyDeliveredService();

    ContactPartyDeliveredServiceEntity created =
        contactPartyDeliveredServiceDao.create(contactPartyDeliveredServiceEntity);
    assertThat(created, is(contactPartyDeliveredServiceEntity));
  }

  @Test
  public void testCreateExistingEntityException() throws Exception {
    thrown.expect(EntityExistsException.class);
    ContactPartyDeliveredServiceEntity contactPartyDeliveredServiceEntity =
        new ContactPartyDeliverdServiceEntityBuilder().setThirdId(thirdId)
            .buildContactPartyDeliveredService();

    contactPartyDeliveredServiceDao.create(contactPartyDeliveredServiceEntity);
  }

  @Test
  public void testDelete() throws Exception {
    ContactPartyDeliveredServiceEntity deleted = contactPartyDeliveredServiceDao.delete(thirdId);
    assertThat(deleted.getThirdId(), is(thirdId));
  }

  @Test
  public void testDeleteEntityNotFoundException() throws Exception {
    String thirdId = "ABC1234568";
    ContactPartyDeliveredServiceEntity deleted = contactPartyDeliveredServiceDao.delete(thirdId);
    assertThat(deleted, is(nullValue()));
  }

  @Test
  public void testUpdate() throws Exception {
    ContactPartyDeliveredServiceEntity contactPartyDeliveredServiceEntity =
        new ContactPartyDeliverdServiceEntityBuilder().setThirdId(thirdId)
            .setCountySpecificCode("00").buildContactPartyDeliveredService();

    ContactPartyDeliveredServiceEntity updated =
        contactPartyDeliveredServiceDao.update(contactPartyDeliveredServiceEntity);
    assertThat(updated, is(contactPartyDeliveredServiceEntity));
  }

  @Test
  public void testUpdateEntityNotFoundException() throws Exception {
    thrown.expect(EntityNotFoundException.class);
    ContactPartyDeliveredServiceEntity contactPartyDeliveredServiceEntity =
        new ContactPartyDeliverdServiceEntityBuilder().buildContactPartyDeliveredService();

    contactPartyDeliveredServiceDao.update(contactPartyDeliveredServiceEntity);
  }

}
