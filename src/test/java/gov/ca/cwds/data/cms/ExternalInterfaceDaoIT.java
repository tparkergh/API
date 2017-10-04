package gov.ca.cwds.data.cms;

import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
import gov.ca.cwds.data.persistence.cms.ExternalInterface;
import gov.ca.cwds.data.persistence.cms.ExternalInterface.PrimaryKey;

/**
 * @author CWDS API Team
 *
 */
public class ExternalInterfaceDaoIT implements DaoTestTemplate {

  private static SessionFactory sessionFactory;
  private static ExternalInterfaceDao externalInterfaceDao;
  private Session session;
  private final static DateFormat tf = new SimpleDateFormat("yyyy-MM-dd-HH.mm.ss.SSS");

  /*
   * pktableId matches src/main/resources/db.cms/ci-seeds.sql
   */
  private String id = "AbjqVmy04O";

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
    externalInterfaceDao = new ExternalInterfaceDao(sessionFactory);
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
    String timestamp = "2004-08-03 15:11:22.761";
    DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
    Date date = formatter.parse(timestamp);
    ExternalInterface found = externalInterfaceDao.find(new PrimaryKey(date, 2, "SMITHBO "));
    String dateString = formatter.format(found.getSubmitlTimestamp());
    Date datefound = formatter.parse(dateString);
    assertThat(datefound, is(equalTo(date)));
    assertThat(found.getSequenceNumber(), is(equalTo(2)));
    assertThat(found.getLogonUserId(), is(equalTo("SMITHBO ")));
  }


  @Override
  @Test
  public void testFindEntityNotFoundException() throws Exception {
    String timestamp = "2004-08-03 14:53:04.383";
    DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
    Date date = formatter.parse(timestamp);
    ExternalInterface found = externalInterfaceDao.find(new PrimaryKey(date, 1, "SMITHBO"));
    assertThat(found, is(nullValue()));
  }

  @Override
  @Test
  public void testCreate() throws Exception {
    String timestamp = "2004-08-03 14:53:04.385";
    DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
    Date date = formatter.parse(timestamp);
    ExternalInterface externalInterface = new ExternalInterface((short) 0, "1", (short) 0, " ",
        (short) 1124, "SMITHBO ", "         ", "N", "DyN0KOO0V2", "12", "KZQ79se0V2", "          ",
        "          ", "          ", "          ", "          ", "          ", "          ",
        "          ", "          ", "          ", 1, "1234568", "2004-08-03", date, "ST_ID_T ");
    ExternalInterface created = externalInterfaceDao.create(externalInterface);
    assertThat(created, is(externalInterface));
  }

  @Override
  @Test(expected = EntityExistsException.class)
  public void testCreateExistingEntityException() throws Exception {
    String timestamp = "2004-08-03 15:11:22.761";
    DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
    Date date = formatter.parse(timestamp);
    ExternalInterface externalInterface = new ExternalInterface((short) 0, "1", (short) 0, " ",
        (short) 1124, "SMITHBO ", "         ", "N", "DyN0KOO0V2", "12", "KZQ79se0V2", "          ",
        "          ", "          ", "          ", "          ", "          ", "          ",
        "          ", "          ", "          ", 2, "1234568", "2004-08-03", date, "FSTUA    ");
    externalInterfaceDao.create(externalInterface);
  }

  @Override
  @Test
  public void testDelete() throws Exception {
    String timestamp = "2004-08-03 15:11:22.761";
    DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
    Date date = formatter.parse(timestamp);
    ExternalInterface deleted = externalInterfaceDao.delete(new PrimaryKey(date, 2, "SMITHBO"));
    String dateString = formatter.format(deleted.getSubmitlTimestamp());
    Date datedeleted = formatter.parse(dateString);
    assertThat(datedeleted, is(equalTo(date)));
    assertThat(deleted.getSequenceNumber(), is(equalTo(2)));
    assertThat(deleted.getLogonUserId(), is(equalTo("SMITHBO ")));
  }

  @Override
  @Test
  public void testDeleteEntityNotFoundException() throws Exception {
    String timestamp = "2004-08-03 14:53:04.381";
    DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
    Date date = formatter.parse(timestamp);
    ExternalInterface deleted = externalInterfaceDao.delete(new PrimaryKey(date, 1, "SMITHBO"));
    assertThat(deleted, is(nullValue()));

  }

  @Override
  @Test
  public void testUpdate() throws Exception {
    String timestamp = "2004-08-03 15:11:22.761";
    DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
    Date date = formatter.parse(timestamp);
    ExternalInterface externalInterface = new ExternalInterface((short) 0, "1", (short) 0, " ",
        (short) 1124, "SMITHBO ", "         ", "N", "DyN0KOO0V2", "12", "KZQ79se0V2", "          ",
        "          ", "          ", "          ", "          ", "          ", "          ",
        "          ", "          ", "          ", 2, "1234568", "2004-08-03", date, "ST_ID_T ");
    ExternalInterface updated = externalInterfaceDao.update(externalInterface);
    assertThat(updated, is(externalInterface));
  }

  @Override
  @Test(expected = EntityNotFoundException.class)
  public void testUpdateEntityNotFoundException() throws Exception {
    String timestamp = "2004-08-03 14:53:04.385";
    DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
    Date date = formatter.parse(timestamp);
    ExternalInterface externalInterface = new ExternalInterface((short) 0, "1", (short) 0, " ",
        (short) 1124, "SMITHBO ", "         ", "N", "DyN0KOO0V2", "12", "KZQ79se0V2", "          ",
        "          ", "          ", "          ", "          ", "          ", "          ",
        "          ", "          ", "          ", 1, "1234568", "2004-08-03", date, "ST_ID_T ");
    externalInterfaceDao.update(externalInterface);
  }

  @Override
  public void testFindAllNamedQueryExist() throws Exception {

  }

  @Override
  public void testFindAllReturnsCorrectList() throws Exception {

  }


}
