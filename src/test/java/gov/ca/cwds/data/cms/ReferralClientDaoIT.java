package gov.ca.cwds.data.cms;

import static org.hamcrest.Matchers.notNullValue;
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

import gov.ca.cwds.data.junit.template.DaoTestTemplate;
import gov.ca.cwds.data.persistence.cms.ReferralClient;
import gov.ca.cwds.data.persistence.cms.ReferralClient.PrimaryKey;

/**
 * 
 * @author CWDS API Team
 */
public class ReferralClientDaoIT implements DaoTestTemplate {

  private static SessionFactory sessionFactory;
  private static ReferralClientDao referralClientDao;
  private Session session;

  @SuppressWarnings("javadoc")
  @Rule
  public ExpectedException thrown = ExpectedException.none();

  /**
   * 
   */
  @BeforeClass
  public static void beforeClass() {
    sessionFactory = new Configuration().configure().buildSessionFactory();
    referralClientDao = new ReferralClientDao(sessionFactory);
  }

  /**
   * 
   */
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
  public void testFind() throws Exception {
    ReferralClient found = referralClientDao.find(new PrimaryKey("LNuzMKw06s", "AazXkWY06s"));
    assertThat(found, is(notNullValue()));
    assertThat(found.getReferralId(), is(equalTo("LNuzMKw06s")));
    assertThat(found.getClientId(), is(equalTo("AazXkWY06s")));
  }

  @Override
  @Test
  public void testFindEntityNotFoundException() throws Exception {
    ReferralClient found = referralClientDao.find(new PrimaryKey("ZZZZZZZ999", "XXXXXXX000"));
    assertThat(found, is(nullValue()));
  }

  @Override
  @Test
  public void testCreate() throws Exception {
    ReferralClient referralClient = new ReferralClient("86XV1bG06k", "AazXkWY06k", "", (short) 122,
        (short) 681, "S", null, "N", "N", "", (short) 2, "", "", "Y", "N", "N");
    ReferralClient created = referralClientDao.create(referralClient);
    assertThat(created, is(referralClient));
  }

  @Override
  @Test
  public void testCreateExistingEntityException() throws Exception {
    thrown.expect(EntityExistsException.class);
    ReferralClient referralClient = new ReferralClient("LNuzMKw06s", "AazXkWY06s", "", (short) 122,
        (short) 681, "S", null, "N", "N", "", (short) 2, "", "", "Y", "N", "N");
    referralClientDao.create(referralClient);
  }

  @Override
  @Test
  public void testDelete() throws Exception {
    ReferralClient delete = referralClientDao.delete(new PrimaryKey("LNuzMKw06s", "AazXkWY06s"));
    assertThat(delete.getClientId(), is("AazXkWY06s"));
    assertThat(delete.getReferralId(), is("LNuzMKw06s"));
  }

  @Override
  @Test
  public void testDeleteEntityNotFoundException() throws Exception {
    ReferralClient delete = referralClientDao.delete(new PrimaryKey("ZZZZZZZ999", "XXXXXXX000"));
    assertThat(delete, is(nullValue()));
  }

  @Override
  @Test
  public void testUpdate() throws Exception {
    ReferralClient referralClient = new ReferralClient("LNuzMKw06s", "AazXkWY06s", "", (short) 122,
        (short) 681, "S", null, "N", "N", "", (short) 2, "", "", "Y", "N", "N");
    ReferralClient updated = referralClientDao.update(referralClient);
    assertThat(updated, is(referralClient));
  }

  @Override
  @Test
  public void testUpdateEntityNotFoundException() throws Exception {
    thrown.expect(EntityNotFoundException.class);
    ReferralClient referralClient = new ReferralClient("ZZuzMKw06s", "AazXkWY06s", "", (short) 122,
        (short) 681, "S", null, "N", "N", "", (short) 2, "", "", "Y", "N", "N");
    referralClientDao.update(referralClient);
  }

  @Override
  public void testFindAllNamedQueryExist() throws Exception {

  }

  @Override
  public void testFindAllReturnsCorrectList() throws Exception {

  }

}
