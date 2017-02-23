package gov.ca.cwds.data.ns;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

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
import org.junit.rules.ExpectedException;

import gov.ca.cwds.data.junit.template.DaoTestTemplate;
import gov.ca.cwds.data.persistence.ns.Language;

/**
 * @author CWDS API Team
 *
 */
public class LanguageDaoIT implements DaoTestTemplate {
  private static final DateFormat TIMESTAMP_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

  /**
   * 
   */
  @Rule
  public ExpectedException thrown = ExpectedException.none();

  private static LanguageDao languageDao;
  private static SessionFactory sessionFactory;
  private Session session;

  @SuppressWarnings("javadoc")
  @BeforeClass
  public static void beforeClass() {
    sessionFactory = new Configuration().configure("ns-hibernate.cfg.xml").buildSessionFactory();
    languageDao = new LanguageDao(sessionFactory);
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
    Query query = session.getNamedQuery("gov.ca.cwds.rest.api.persistence.ns.Language.findAll");
    assertThat(query, is(notNullValue()));
  }

  @Override
  @Test
  public void testFindAllReturnsCorrectList() {
    Query query = session.getNamedQuery("gov.ca.cwds.rest.api.persistence.ns.Language.findAll");
    assertThat(query.list().size(), is(2));
  }

  @SuppressWarnings("javadoc")
  @Test
  public void testfindAllUpdatedAfterNamedQueryExists() throws Exception {
    Query query =
        session.getNamedQuery("gov.ca.cwds.rest.api.persistence.ns.Language.findAllUpdatedAfter");
    assertThat(query, is(notNullValue()));
  }

  @SuppressWarnings("javadoc")
  @Test
  public void testfindAllUpdatedAfterReturnsCorrectList() throws Exception {
    Query query =
        session.getNamedQuery("gov.ca.cwds.rest.api.persistence.ns.Language.findAllUpdatedAfter")
            .setDate("after", TIMESTAMP_FORMAT.parse("2017-02-22 13:52:27.801"));
    assertThat(query.list().size(), is(2));
  }

  @Override
  @Test
  public void testFind() {
    long languageId = 1;
    Language found = languageDao.find(languageId);
    assertThat(found.getLanguageId(), is(languageId));
  }

  @Override
  @Test
  public void testFindEntityNotFoundException() throws Exception {
    long languageId = 99;
    Language found = languageDao.find(languageId);
    assertThat(found, is(nullValue()));

  }

  @Override
  @Test
  public void testCreate() {
    Language language = new Language(0L, "English");
    Language created = languageDao.create(language);
    assertThat(created, is(language));
  }

  @Override
  @Test
  public void testCreateExistingEntityException() {
    thrown.expect(EntityExistsException.class);
    Language language = new Language(1L, "Telugu");
    languageDao.create(language);
  }

  @Override
  @Test
  public void testDelete() throws Exception {
    long languageId = 1;
    Language deleted = languageDao.delete(languageId);
    assertThat(deleted.getLanguageId(), is(languageId));

  }

  @Override
  @Test
  public void testDeleteEntityNotFoundException() {
    Language updated = languageDao.delete((long) 99);
    assertThat(updated, is(nullValue()));

  }

  @Override
  @Test
  public void testUpdate() {
    Language language = new Language(1L, "Spanish");
    Language updated = languageDao.update(language);
    assertThat(updated, is(language));

  }

  @Override
  @Test
  public void testUpdateEntityNotFoundException() {
    thrown.expect(EntityNotFoundException.class);
    Language language = new Language(11L, "Hindi");
    languageDao.update(language);
  }
}
