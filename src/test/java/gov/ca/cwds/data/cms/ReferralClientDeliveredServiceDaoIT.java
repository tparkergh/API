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

import gov.ca.cwds.data.persistence.cms.ReferralClientDeliveredService;
import gov.ca.cwds.data.persistence.cms.ReferralClientDeliveredServiceEmbeddable;

/**
 * @author CWDS API Team
 *
 */
@SuppressWarnings("javadoc")
public class ReferralClientDeliveredServiceDaoIT {

  private static SessionFactory sessionFactory;
  private static ReferralClientDeliveredServiceDao referralClientDeliveredServiceDao;
  private Session session;

  /*
   * deliveredServiceId matches src/main/resources/db.cms/ci-seeds.sql
   */
  private String deliveredServiceId = "ABC1234567";
  private String referralId = "ABX1234560";
  private String clientId = "APc109852u";

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
    referralClientDeliveredServiceDao = new ReferralClientDeliveredServiceDao(sessionFactory);
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
    ReferralClientDeliveredServiceEmbeddable referralClientDeliveredServiceEmbeddable =
        new ReferralClientDeliveredServiceEmbeddable(deliveredServiceId, referralId, clientId);

    ReferralClientDeliveredService found =
        referralClientDeliveredServiceDao.find(referralClientDeliveredServiceEmbeddable);
    assertThat(found.getReferralClientDeliveredServiceEmbeddable().getDeliveredServiceId(),
        is(equalTo(deliveredServiceId)));
  }

  @Test
  public void testFindEntityNotFoundException() throws Exception {
    ReferralClientDeliveredServiceEmbeddable referralClientDeliveredServiceEmbeddable =
        new ReferralClientDeliveredServiceEmbeddable("ZZZZ127o0", "ppppxsxcx", "icbdc00ll");

    ReferralClientDeliveredService found =
        referralClientDeliveredServiceDao.find(referralClientDeliveredServiceEmbeddable);
    assertThat(found, is(nullValue()));
  }

  @Test
  public void testCreate() throws Exception {
    ReferralClientDeliveredService referralClientDeliveredService =
        new ReferralClientDeliveredService("ABC123456k", "ABC1209876", "NMo09754e1", "99");

    ReferralClientDeliveredService created =
        referralClientDeliveredServiceDao.create(referralClientDeliveredService);
    assertThat(created, is(referralClientDeliveredService));
  }

  @Test
  public void testCreateExistingEntityException() throws Exception {
    thrown.expect(EntityExistsException.class);
    ReferralClientDeliveredService referralClientDeliveredService =
        new ReferralClientDeliveredService(deliveredServiceId, referralId, clientId, "13");

    referralClientDeliveredServiceDao.create(referralClientDeliveredService);
  }

  @Test
  public void testDelete() throws Exception {
    ReferralClientDeliveredServiceEmbeddable referralClientDeliveredServiceEmbeddable =
        new ReferralClientDeliveredServiceEmbeddable(deliveredServiceId, referralId, clientId);
    ReferralClientDeliveredService deleted =
        referralClientDeliveredServiceDao.delete(referralClientDeliveredServiceEmbeddable);
    assertThat(deleted, is(nullValue()));
  }

  @Test
  public void testDeleteEntityNotFoundException() throws Exception {
    ReferralClientDeliveredServiceEmbeddable referralClientDeliveredServiceEmbeddable =
        new ReferralClientDeliveredServiceEmbeddable("12v07bfyg1", "09mg5TFb1G", "lPyb15Fn8");
    ReferralClientDeliveredService deleted =
        referralClientDeliveredServiceDao.delete(referralClientDeliveredServiceEmbeddable);
    assertThat(deleted, is(nullValue()));
  }

  @Test
  public void testUpdate() throws Exception {

    ReferralClientDeliveredService referralClientDeliveredService =
        new ReferralClientDeliveredService(deliveredServiceId, referralId, clientId, "13");
    ReferralClientDeliveredService updated =
        referralClientDeliveredServiceDao.update(referralClientDeliveredService);
    assertThat(updated, is(referralClientDeliveredService));
  }

  @Test
  public void testUpdateEntityNotFoundException() throws Exception {
    thrown.expect(EntityNotFoundException.class);
    ReferralClientDeliveredService referralClientDeliveredService =
        new ReferralClientDeliveredService(deliveredServiceId, referralId, clientId, "13");
    referralClientDeliveredServiceDao.update(referralClientDeliveredService);

  }

}
