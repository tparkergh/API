package gov.ca.cwds.data.cms;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

import java.math.BigDecimal;
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
import gov.ca.cwds.data.persistence.cms.SubstituteCareProvider;

/**
 * @author CWDS API Team
 *
 */
public class SubstituteCareProviderDaoIT implements DaoTestTemplate {
  private static final DateFormat TIMESTAMP_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
  private DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
  private String birthDateString = "1953-04-26";

  @SuppressWarnings("javadoc")
  @Rule
  public ExpectedException thrown = ExpectedException.none();

  private static SubstituteCareProviderDao substituteCareProviderDao;
  private static SessionFactory sessionFactory;
  private Session session;

  @SuppressWarnings("javadoc")
  @BeforeClass
  public static void beforeClass() {
    sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
    substituteCareProviderDao = new SubstituteCareProviderDao(sessionFactory);
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
    Query query =
        session.getNamedQuery("gov.ca.cwds.data.persistence.cms.SubstituteCareProvider.findAll");
    assertThat(query, is(notNullValue()));
  }

  @Override
  @Test
  public void testFindAllReturnsCorrectList() {
    Query query =
        session.getNamedQuery("gov.ca.cwds.data.persistence.cms.SubstituteCareProvider.findAll");
    assertThat(query.list().size(), is(1));
  }

  // TODO: restore after replication schema is added to Docker DB2.
  // @SuppressWarnings("javadoc")
  // @Test
  // public void testfindAllUpdatedAfterNamedQueryExists() throws Exception {
  // Query query = session.getNamedQuery(
  // "gov.ca.cwds.data.persistence.cms.rep.ReplicatedSubstituteCareProvider.findAllUpdatedAfter");
  // assertThat(query, is(notNullValue()));
  // }

  // TODO: restore after replication schema is added to Docker DB2.
  // @SuppressWarnings("javadoc")
  // @Test
  // public void testfindAllUpdatedAfterReturnsCorrectList() throws Exception {
  // Query query = session
  // .getNamedQuery(
  // "gov.ca.cwds.data.persistence.cms.rep.ReplicatedSubstituteCareProvider.findAllUpdatedAfter")
  // .setDate("after", TIMESTAMP_FORMAT.parse("1900-01-01 00:00:00"));
  // assertThat(query.list().size(), is(1));
  // }

  @Override
  @Test
  public void testFind() throws Exception {
    String id = "aQqUhBQF11";
    SubstituteCareProvider found = substituteCareProviderDao.find(id);
    assertThat(found.getId(), is(id));
  }

  @Override
  @Test
  public void testFindEntityNotFoundException() throws Exception {
    String id = "ZZZZZZZ999";
    SubstituteCareProvider found = substituteCareProviderDao.find(id);
    assertThat(found, is(nullValue()));

  }

  @Override
  @Test
  public void testCreate() throws Exception {
    Date birthDate = df.parse(birthDateString);
    SubstituteCareProvider substituteCareProvider =
        new SubstituteCareProvider("AnPcSg504F", BigDecimal.ZERO, 0, BigDecimal.ZERO, birthDate,
            "C0000005", "Chico", (short) 3149, "dora@email.com", "Employer", (short) 0,
            "ReasonCode", "catherine", "N", "F", "HisReasonCode", "OrganicCode", (short) 0, "Tuna",
            "N", "PersonId", (short) 1308, "Mr", "Erik", "Passed", (short) 0, "N", (short) 0,
            "889987752", (short) 1828, "1611", "5th Street", "SufixDescrption", 95814, (short) 0);
    SubstituteCareProvider created = substituteCareProviderDao.create(substituteCareProvider);
    assertThat(created, is(substituteCareProvider));
  }

  @Override
  @Test
  public void testCreateExistingEntityException() throws Exception {
    thrown.expect(EntityExistsException.class);
    Date birthDate = df.parse(birthDateString);
    SubstituteCareProvider substituteCareProvider =
        new SubstituteCareProvider("aQqUhBQF11", BigDecimal.ZERO, 0, BigDecimal.ZERO, birthDate,
            "C0000005", "Chico", (short) 3149, "dora@email.com", "Employer", (short) 0,
            "ReasonCode", "catherine", "N", "F", "HisReasonCode", "OrganicCode", (short) 0, "Tuna",
            "N", "PersonId", (short) 1308, "Mr", "Erik", "Passed", (short) 0, "N", (short) 0,
            "889987752", (short) 1828, "1611", "5th Street", "SufixDescrption", 95814, (short) 0);
    substituteCareProviderDao.create(substituteCareProvider);
  }

  @Override
  @Test
  public void testDelete() throws Exception {
    String id = "aQqUhBQF11";
    SubstituteCareProvider deleted = substituteCareProviderDao.delete(id);
    assertThat(deleted.getId(), is(id));
  }

  @Override
  @Test
  public void testDeleteEntityNotFoundException() throws Exception {
    String id = "ZZZZZZZ999";
    SubstituteCareProvider deleted = substituteCareProviderDao.delete(id);
    assertThat(deleted, is(nullValue()));
  }

  @Override
  @Test
  public void testUpdate() throws Exception {
    Date birthDate = df.parse(birthDateString);
    SubstituteCareProvider substituteCareProvider =
        new SubstituteCareProvider("aQqUhBQF12", BigDecimal.ZERO, 0, BigDecimal.ZERO, birthDate,
            "C0000005", "Chico", (short) 3149, "epic@email.com", "Employer", (short) 0,
            "ReasonCode", "Alex", "N", "F", "HisReasonCode", "OrganicCode", (short) 0, "Tuna", "N",
            "PersonId", (short) 1308, "Mr", "Erik", "Passed", (short) 0, "N", (short) 0,
            "889987752", (short) 1828, "1611", "5th Street", "SufixDescrption", 95814, (short) 0);
    SubstituteCareProvider updated = substituteCareProviderDao.create(substituteCareProvider);
    assertThat(updated, is(substituteCareProvider));
  }

  @Override
  @Test
  public void testUpdateEntityNotFoundException() throws Exception {
    thrown.expect(EntityNotFoundException.class);
    Date birthDate = df.parse(birthDateString);
    SubstituteCareProvider substituteCareProvider =
        new SubstituteCareProvider("aQqUhBQF13", BigDecimal.ZERO, 0, BigDecimal.ZERO, birthDate,
            "C0000005", "Chico", (short) 3149, "epic@email.com", "Employer", (short) 0,
            "ReasonCode", "Alex", "N", "F", "HisReasonCode", "OrganicCode", (short) 0, "Tuna", "N",
            "PersonId", (short) 1308, "Mr", "Erik", "Passed", (short) 0, "N", (short) 0,
            "889987752", (short) 1828, "1611", "5th Street", "SufixDescrption", 95814, (short) 0);
    substituteCareProviderDao.update(substituteCareProvider);
  }

}
