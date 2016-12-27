package gov.ca.cwds.rest.jdbi.cms;

import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.hamcrest.junit.ExpectedException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import gov.ca.cwds.rest.api.persistence.cms.ReferralClient;
import gov.ca.cwds.rest.api.persistence.cms.ReferralClient.PrimaryKey;

public class ReferralClientDaoIT {
  private SessionFactory sessionFactory;
  private ReferralClientDao referralClientDao;

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Before
  public void setup() {
    sessionFactory = new Configuration().configure().buildSessionFactory();
    sessionFactory.getCurrentSession().beginTransaction();
    referralClientDao = new ReferralClientDao(sessionFactory);
  }

  @After
  public void teardown() {
    sessionFactory.close();
  }

  @Test
  public void testFind() {
    ReferralClient found = referralClientDao.find(new PrimaryKey("LNuzMKw06s", "AazXkWY06s"));
    assertThat(found, is(notNullValue()));
    assertThat(found.getReferralId(), is(equalTo("LNuzMKw06s")));
    assertThat(found.getClientId(), is(equalTo("AazXkWY06s")));
  }

  @Test
  public void testCreate() {
    ReferralClient referralClient = new ReferralClient("86XV1bG06k", "AazXkWY06k", "", (short) 122,
        (short) 681, "S", null, "N", "N", "", (short) 2, "", "", "Y", "N", "N");
    ReferralClient created = referralClientDao.create(referralClient);
    assertThat(created, is(referralClient));
  }

  @Test
  public void testCreateExistingEntityException() {
    thrown.expect(EntityExistsException.class);
    ReferralClient referralClient = new ReferralClient("LNuzMKw06s", "AazXkWY06s", "", (short) 122,
        (short) 681, "S", null, "N", "N", "", (short) 2, "", "", "Y", "N", "N");
    referralClientDao.create(referralClient);
  }

  @Test
  public void testDelete() {
    ReferralClient delete = referralClientDao.delete(new PrimaryKey("LNuzMKw06s", "AazXkWY06s"));
    assertThat(delete.getClientId(), is("AazXkWY06s"));
    assertThat(delete.getReferralId(), is("LNuzMKw06s"));
  }

  @Test
  public void testUpdate() {
    ReferralClient referralClient = new ReferralClient("LNuzMKw06s", "AazXkWY06s", "", (short) 122,
        (short) 681, "S", null, "N", "N", "", (short) 2, "", "", "Y", "N", "N");
    ReferralClient updated = referralClientDao.update(referralClient);
    assertThat(updated, is(referralClient));
  }

  @Test
  public void testUpdateEntityNotFoundException() {
    thrown.expect(EntityNotFoundException.class);
    ReferralClient referralClient = new ReferralClient("ZZuzMKw06s", "AazXkWY06s", "", (short) 122,
        (short) 681, "S", null, "N", "N", "", (short) 2, "", "", "Y", "N", "N");
    referralClientDao.update(referralClient);
  }

}
