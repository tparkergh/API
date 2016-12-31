package gov.ca.cwds.data.cms;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import java.util.Date;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.hamcrest.junit.ExpectedException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import gov.ca.cwds.data.cms.ReferralDao;
import gov.ca.cwds.data.persistence.cms.Referral;

public class ReferralDaoIT {
  private SessionFactory sessionFactory;
  private ReferralDao referralDao;

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Before
  public void setup() {
    sessionFactory = new Configuration().configure().buildSessionFactory();
    sessionFactory.getCurrentSession().beginTransaction();
    referralDao = new ReferralDao(sessionFactory);
  }

  @After
  public void teardown() {
    sessionFactory.close();
  }

  @Test
  public void testFind() {
    String id = "AbiQCgu0Hj";
    Referral found = referralDao.find(id);
    assertThat(found.getId(), is(id));
  }

  @Test
  public void testCreate() {
    Referral referral = new Referral("AbiQCgu0Ht", " ", "N", "N", "D5YRVOm0Ht", (short) 122, " ",
        (Date) null, (short) 409, "", "", "L3H7sSC0Ht", "", "N", "N", (short) 1118, " ", "N", "N",
        (Date) null, "Verification (R3)", " ", (Date) null, (Date) null, (short) 1520, (short) 0,
        (Date) null, (Date) null, "", "", " ", " ", " ", "", "", "0Ht", "0Ht", "51", "N", "N", "N",
        "N", (Date) null, "C", (short) 0, (Date) null, "", (Date) null);
    Referral created = referralDao.create(referral);
    assertThat(created, is(referral));

  }

  @Test
  public void testCreateExistingEntityException() {
    thrown.expect(EntityExistsException.class);
    Referral referral = new Referral("AbiQCgu0Hj", " ", "N", "N", "D5YRVOm0Ht", (short) 122, " ",
        (Date) null, (short) 409, "", "", "L3H7sSC0Ht", "", "N", "N", (short) 1118, " ", "N", "N",
        (Date) null, "Verification (R3)", " ", (Date) null, (Date) null, (short) 1520, (short) 0,
        (Date) null, (Date) null, "", "", " ", " ", " ", "", "", "0Ht", "0Ht", "51", "N", "N", "N",
        "N", (Date) null, "C", (short) 0, (Date) null, "", (Date) null);
    referralDao.create(referral);
  }

  @Test
  public void testDelete() {
    String id = "AbiQCgu0Hj";
    Referral deleted = referralDao.delete(id);
    assertThat(deleted.getId(), is(id));
  }

  @Test
  public void testUpdate() {
    Referral referral = new Referral("AbiQCgu0Hj", " ", "N", "N", "D5YRVOm0Ht", (short) 122, " ",
        (Date) null, (short) 409, "", "", "L3H7sSC0Ht", "", "N", "N", (short) 1118, " ", "N", "N",
        (Date) null, "Verification (R3)", " ", (Date) null, (Date) null, (short) 1520, (short) 0,
        (Date) null, (Date) null, "", "", " ", " ", " ", "", "", "0Ht", "0Ht", "51", "N", "N", "N",
        "N", (Date) null, "C", (short) 0, (Date) null, "", (Date) null);
    Referral updated = referralDao.update(referral);
    assertThat(updated, is(referral));
  }

  @Test
  public void testUpdateEntityNotFoundException() {
    thrown.expect(EntityNotFoundException.class);
    Referral referral = new Referral("ZZZZZZZZZ", " ", "N", "N", "D5YRVOm0Ht", (short) 122, " ",
        (Date) null, (short) 409, "", "", "L3H7sSC0Ht", "", "N", "N", (short) 1118, " ", "N", "N",
        (Date) null, "Verification (R3)", " ", (Date) null, (Date) null, (short) 1520, (short) 0,
        (Date) null, (Date) null, "", "", " ", " ", " ", "", "", "0Ht", "0Ht", "51", "N", "N", "N",
        "N", (Date) null, "C", (short) 0, (Date) null, "", (Date) null);
    referralDao.update(referral);
  }
}
