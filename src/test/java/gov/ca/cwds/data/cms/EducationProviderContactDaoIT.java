package gov.ca.cwds.data.cms;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

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

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import gov.ca.cwds.data.junit.template.DaoTestTemplate;
import gov.ca.cwds.data.persistence.cms.EducationProviderContact;
import io.dropwizard.jackson.Jackson;

public class EducationProviderContactDaoIT implements DaoTestTemplate {
  private static final DateFormat TIMESTAMP_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
  private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  private static EducationProviderContactDao educationProviderContactDao;
  private static SessionFactory sessionFactory;
  private Session session;

  @SuppressWarnings("javadoc")
  @BeforeClass
  public static void beforeClass() {
    sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
    educationProviderContactDao = new EducationProviderContactDao(sessionFactory);
  }

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
  public void teardown() throws Exception {
    session.getTransaction().rollback();
  }

  @Override
  @Test
  public void testFindAllNamedQueryExist() throws Exception {
    Query query =
        session.getNamedQuery("gov.ca.cwds.data.persistence.cms.EducationProviderContact.findAll");
    assertThat(query, is(notNullValue()));
  }

  @Override
  @Test
  public void testFindAllReturnsCorrectList() throws Exception {
    Query query =
        session.getNamedQuery("gov.ca.cwds.data.persistence.cms.EducationProviderContact.findAll");
    assertThat(query.list().size(), is(4));
  }

  // @SuppressWarnings("javadoc")
  // @Test
  // public void testFindAllUpdatedAfterNameQueryExist() throws Exception {
  // Query query = session.getNamedQuery(
  // "gov.ca.cwds.data.persistence.cms.EducationProviderContact.findAllUpdatedAfter");
  // assertThat(query, is(notNullValue()));
  // }
  //
  // @SuppressWarnings("javadoc")
  // @Test
  // public void testfindAllUpdatedAfterReturnsCorrectList() throws Exception {
  // Query query = session
  // .getNamedQuery(
  // "gov.ca.cwds.data.persistence.cms.EducationProviderContact.findAllUpdatedAfter")
  // .setDate("after", TIMESTAMP_FORMAT.parse("2000-11-02 00:00:00"));
  // assertThat(query.list().size(), is(4));
  // }

  @Override
  @Test
  public void testFind() {
    String id = "H2UmfUi0X5";
    EducationProviderContact found = educationProviderContactDao.find(id);
    assertThat(found.getId(), is(id));
  }

  @Override
  @Test
  public void testFindEntityNotFoundException() throws Exception {
    String id = "ZZZZZZZ999";
    EducationProviderContact found = educationProviderContactDao.find(id);
    assertThat(found, is(nullValue()));
  }

  @Override
  @Test
  public void testCreate() throws Exception {
    EducationProviderContact epc = validEducationProviderContact();

    EducationProviderContact educationProviderContact = new EducationProviderContact(
        epc.getdepartmentOfEducationIndicator(), epc.getEmailAddress(), epc.getFaxNumber(),
        epc.getFirstName(), epc.getfKeyEducationProvider(), epc.getId(), epc.getLastName(),
        epc.getMiddleName(), epc.getNamePrefixDescription(), epc.getPhoneExtensionNumber(),
        epc.getPhoneNumberAsDecimal(), epc.getPrimaryContactIndicator(),
        epc.getSuffixTitleDescription(), epc.getTitleDescription());

    EducationProviderContact created = educationProviderContactDao.create(educationProviderContact);
    assertThat(created, is(educationProviderContact));
  }

  @Override
  @Test
  public void testCreateExistingEntityException() throws Exception {
    thrown.expect(EntityExistsException.class);
    EducationProviderContact epc = validExistingEducationProviderContact();

    EducationProviderContact educationProviderContact = new EducationProviderContact(
        epc.getdepartmentOfEducationIndicator(), epc.getEmailAddress(), epc.getFaxNumber(),
        epc.getFirstName(), epc.getfKeyEducationProvider(), epc.getId(), epc.getLastName(),
        epc.getMiddleName(), epc.getNamePrefixDescription(), epc.getPhoneExtensionNumber(),
        epc.getPhoneNumberAsDecimal(), epc.getPrimaryContactIndicator(),
        epc.getSuffixTitleDescription(), epc.getTitleDescription());

    educationProviderContactDao.create(educationProviderContact);
  }

  @Override
  @Test
  public void testDelete() {
    String id = "H2UmfUi0X5";
    EducationProviderContact deleted = educationProviderContactDao.delete(id);
    assertThat(deleted.getId(), is(id));
  }


  @Override
  @Test
  public void testDeleteEntityNotFoundException() throws Exception {
    String id = "ZZZZZZZ999";
    EducationProviderContact deleted = educationProviderContactDao.delete(id);
    assertThat(deleted, is(nullValue()));
  }

  @Override
  @Test
  public void testUpdate() throws Exception {
    EducationProviderContact epc = validExistingEducationProviderContact();
    EducationProviderContact educationProviderContact = new EducationProviderContact(
        epc.getdepartmentOfEducationIndicator(), epc.getEmailAddress(), epc.getFaxNumber(),
        epc.getFirstName(), epc.getfKeyEducationProvider(), epc.getId(), epc.getLastName(),
        epc.getMiddleName(), epc.getNamePrefixDescription(), epc.getPhoneExtensionNumber(),
        epc.getPhoneNumberAsDecimal(), epc.getPrimaryContactIndicator(),
        epc.getSuffixTitleDescription(), epc.getTitleDescription());

    EducationProviderContact updated = educationProviderContactDao.update(educationProviderContact);
    assertThat(updated, is(educationProviderContact));
  }

  @Override
  @Test
  public void testUpdateEntityNotFoundException() throws Exception {
    thrown.expect(EntityNotFoundException.class);
    EducationProviderContact epc = validEducationProviderContact();

    EducationProviderContact educationProviderContact = new EducationProviderContact(
        epc.getdepartmentOfEducationIndicator(), epc.getEmailAddress(), epc.getFaxNumber(),
        epc.getFirstName(), epc.getfKeyEducationProvider(), epc.getId(), epc.getLastName(),
        epc.getMiddleName(), epc.getNamePrefixDescription(), epc.getPhoneExtensionNumber(),
        epc.getPhoneNumberAsDecimal(), epc.getPrimaryContactIndicator(),
        epc.getSuffixTitleDescription(), epc.getTitleDescription());

    educationProviderContactDao.update(educationProviderContact);
  }


  private EducationProviderContact validEducationProviderContact()
      throws JsonParseException, JsonMappingException, IOException {

    EducationProviderContact validEducationProviderContact = MAPPER.readValue(
        fixture("fixtures/domain/legacy/EducationProviderContact/valid/valid.json"),
        EducationProviderContact.class);

    return validEducationProviderContact;
  }

  private EducationProviderContact validExistingEducationProviderContact()
      throws JsonParseException, JsonMappingException, IOException {

    EducationProviderContact validEducationProviderContact = MAPPER.readValue(
        fixture("fixtures/domain/legacy/EducationProviderContact/valid/validForUpdate.json"),
        EducationProviderContact.class);

    return validEducationProviderContact;
  }

}
