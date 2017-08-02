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
import gov.ca.cwds.data.persistence.cms.DeliveredService;

/**
 * @author CWDS API Team
 *
 */
public class DeliveredServiceDaoIT implements DaoTestTemplate {

  private static SessionFactory sessionFactory;
  private static DeliveredServiceDao deliveredServiceDao;
  private Session session;
  private final static DateFormat tf = new SimpleDateFormat("yyyy-MM-dd");

  /*
   * pktableId matches src/main/resources/db.cms/ci-seeds.sql
   */
  private String id = "Aabg4cV0AB";

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
    deliveredServiceDao = new DeliveredServiceDao(sessionFactory);
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
    DeliveredService found = deliveredServiceDao.find(id);
    assertThat(found.getId(), is(equalTo(id)));
  }


  @Override
  @Test
  public void testFindEntityNotFoundException() throws Exception {
    DeliveredService found = deliveredServiceDao.find("Aabg4cV0AD");
    assertThat(found, is(nullValue()));
  }

  @Override
  @Test
  public void testCreate() throws Exception {
    DeliveredService deliveredService = new DeliveredService(null, "N", (short) 0, (short) 0, "N",
        "37", null, "N", null, new Date(), null, "CHSkjUu02T", "Aabg4cV0Av", " ", "V", "CHSkjUu02T",
        new Date(), null, "C", " ", "3239", "N");
    DeliveredService created = deliveredServiceDao.create(deliveredService);
    assertThat(created, is(deliveredService));
  }

  @Override
  @Test
  public void testCreateExistingEntityException() throws Exception {
    thrown.expect(EntityExistsException.class);
    DeliveredService deliveredService = new DeliveredService(null, "N", (short) 0, (short) 0, "N",
        "37", null, "N", null, new Date(), null, "CHSkjUu02T", "Aabg4cV0AB", " ", "V", "CHSkjUu02T",
        new Date(), null, "C", " ", "3239", "N");
    deliveredServiceDao.create(deliveredService);
  }

  @Override
  @Test
  public void testDelete() throws Exception {
    DeliveredService deleted = deliveredServiceDao.delete("AajvGGx0Et");
    assertThat(deleted.getId(), is(equalTo("AajvGGx0Et")));
  }

  @Override
  @Test
  public void testDeleteEntityNotFoundException() throws Exception {
    DeliveredService deleted = deliveredServiceDao.delete("AajvGGx0Ey");
    assertThat(deleted, is(nullValue()));

  }

  @Override
  @Test
  public void testUpdate() throws Exception {
    DeliveredService deliveredService = new DeliveredService(null, "N", (short) 0, (short) 0, "N",
        "37", null, "N", null, new Date(), null, "CHSkjUu02T", "Aabg4cV0AB", " ", "V", "CHSkjUu02T",
        new Date(), null, "C", " ", "3238", "N");
    DeliveredService updated = deliveredServiceDao.update(deliveredService);
    assertThat(updated, is(deliveredService));
  }

  @Override
  @Test
  public void testUpdateEntityNotFoundException() throws Exception {
    thrown.expect(EntityNotFoundException.class);
    String sedate = "2000-08-02";
    DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    Date date = formatter.parse(sedate);
    DeliveredService deliveredService = new DeliveredService(null, "N", (short) 0, (short) 0, "N",
        "37", null, "N", null, date, null, "CHSkjUu02T", "Aabg4cV0Am", " ", "V", "CHSkjUu02T", date,
        null, "C", " ", "3239", "N");
    deliveredServiceDao.update(deliveredService);
  }

  @Override
  public void testFindAllNamedQueryExist() throws Exception {

  }

  @Override
  public void testFindAllReturnsCorrectList() throws Exception {

  }


}
