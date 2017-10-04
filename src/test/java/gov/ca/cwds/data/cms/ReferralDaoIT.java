package gov.ca.cwds.data.cms;

import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import java.util.Date;

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
import gov.ca.cwds.data.persistence.cms.Referral;

/**
 * @author CWDS API Team
 */
public class ReferralDaoIT implements DaoTestTemplate {

  private static SessionFactory sessionFactory;
  private static ReferralDao referralDao;
  private Session session;

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  /**
   * 
   */
  @BeforeClass
  public static void beforeClass() {
    sessionFactory = new Configuration().configure().buildSessionFactory();
    referralDao = new ReferralDao(sessionFactory);
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
    String id = "AbiQCgu0Hj";
    Referral found = referralDao.find(id);
    assertThat(found.getId(), is(id));
  }

  @Override
  @Test
  public void testFindEntityNotFoundException() throws Exception {
    String id = "ZZZZZZ999";
    Referral found = referralDao.find(id);
    assertThat(found, is(nullValue()));
  }

  @Override
  @Test
  public void testCreate() throws Exception {
    Referral referral = new Referral("AbiQCgu0Ht", " ", "N", "N", "D5YRVOm0Ht", (short) 122, " ",
        (Date) null, (short) 409, "", "", "L3H7sSC0Ht", "", "N", "N", (short) 1118, " ", "N", "N",
        (Date) null, "Verification (R3)", " ", (Date) null, (Date) null, (short) 1520, (short) 0,
        (Date) null, (Date) null, "", "", " ", " ", " ", "", "", "0Ht", "0Ht", "51", "N", "N", "N",
        "N", (Date) null, "C", (short) 0, (Date) null, "", (Date) null, null, null, null, null);
    Referral created = referralDao.create(referral);
    assertThat(created, is(referral));
  }

  @Override
  @Test(expected = EntityExistsException.class)
  public void testCreateExistingEntityException() throws Exception {
    Referral referral = new Referral("AbiQCgu0Hj", " ", "N", "N", "D5YRVOm0Ht", (short) 122, " ",
        (Date) null, (short) 409, "", "", "L3H7sSC0Ht", "", "N", "N", (short) 1118, " ", "N", "N",
        (Date) null, "Verification (R3)", " ", (Date) null, (Date) null, (short) 1520, (short) 0,
        (Date) null, (Date) null, "", "", " ", " ", " ", "", "", "0Ht", "0Ht", "51", "N", "N", "N",
        "N", (Date) null, "C", (short) 0, (Date) null, "", (Date) null, null, null, null, null);
    referralDao.create(referral);
  }

  @Override
  @Test
  public void testDelete() throws Exception {
    String id = "AbiQCgu0Hj";
    Referral deleted = referralDao.delete(id);
    assertThat(deleted.getId(), is(id));
  }

  @Override
  @Test
  public void testDeleteEntityNotFoundException() throws Exception {
    String id = "ZZZZZZZ999";
    Referral deleted = referralDao.delete(id);
    assertThat(deleted, is(nullValue()));
  }

  @Override
  @Test
  public void testUpdate() throws Exception {
    Referral referral = new Referral("AbiQCgu0Hj", " ", "N", "N", "D5YRVOm0Ht", (short) 122, " ",
        (Date) null, (short) 409, "", "", "L3H7sSC0Ht", "", "N", "N", (short) 1118, " ", "N", "N",
        (Date) null, "Verification (R3)", " ", (Date) null, (Date) null, (short) 1520, (short) 0,
        (Date) null, (Date) null, "", "", " ", " ", " ", "", "", "0Ht", "0Ht", "51", "N", "N", "N",
        "N", (Date) null, "C", (short) 0, (Date) null, "", (Date) null, null, null, null, null);
    Referral updated = referralDao.update(referral);
    assertThat(updated, is(referral));
  }

  @Override
  @Test(expected = EntityNotFoundException.class)
  public void testUpdateEntityNotFoundException() throws Exception {
    Referral referral = new Referral("ZZZZZZZZZ", " ", "N", "N", "D5YRVOm0Ht", (short) 122, " ",
        (Date) null, (short) 409, "", "", "L3H7sSC0Ht", "", "N", "N", (short) 1118, " ", "N", "N",
        (Date) null, "Verification (R3)", " ", (Date) null, (Date) null, (short) 1520, (short) 0,
        (Date) null, (Date) null, "", "", " ", " ", " ", "", "", "0Ht", "0Ht", "51", "N", "N", "N",
        "N", (Date) null, "C", (short) 0, (Date) null, "", (Date) null, null, null, null, null);
    referralDao.update(referral);
  }

  @Override
  public void testFindAllNamedQueryExist() throws Exception {

  }

  @Override
  public void testFindAllReturnsCorrectList() throws Exception {

  }

}
