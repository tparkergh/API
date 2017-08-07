package gov.ca.cwds.data.dao.contact;

import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
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

import gov.ca.cwds.data.persistence.contact.IndividualDeliveredServiceEmbeddable;
import gov.ca.cwds.data.persistence.contact.IndividualDeliveredServiceEntity;

/**
 * @author CWDS API Team
 *
 */
@SuppressWarnings("javadoc")
public class IndividualDeliveredServiceDaoIT {

  private static SessionFactory sessionFactory;
  private static IndividualDeliveredServiceDao individualDeliveredServiceDao;
  private Session session;

  /*
   * deliveredServiceId matches src/main/resources/db.cms/ci-seeds.sql
   */
  private String deliveredServiceId = "Aabg4cV0AB";
  private String deliveredToIndividualCode = "C";
  private String deliveredToIndividualId = "A0YcYQV0AB";

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
    individualDeliveredServiceDao = new IndividualDeliveredServiceDao(sessionFactory);
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
    IndividualDeliveredServiceEmbeddable individualDeliveredServiceEmbeddable =
        new IndividualDeliveredServiceEmbeddable(deliveredServiceId, deliveredToIndividualCode,
            deliveredToIndividualId);

    IndividualDeliveredServiceEntity found =
        individualDeliveredServiceDao.find(individualDeliveredServiceEmbeddable);
    assertThat(found.getIndividualDeliveredServiceEmbeddable().getDeliveredServiceId(),
        is(equalTo(deliveredServiceId)));
  }

  @Test
  public void testFindEntityNotFoundException() throws Exception {
    IndividualDeliveredServiceEmbeddable individualDeliveredServiceEmbeddable =
        new IndividualDeliveredServiceEmbeddable("jchc70ebdj", "B26hd8gdjd", "0mOx5tsxj6");

    IndividualDeliveredServiceEntity found =
        individualDeliveredServiceDao.find(individualDeliveredServiceEmbeddable);
    assertThat(found, is(nullValue()));
  }

  @Test
  public void testCreate() throws Exception {
    IndividualDeliveredServiceEntity individualDeliveredServiceEntity =
        new IndividualDeliveredServiceEntity("ABC1234567", deliveredToIndividualCode, "ABC123458",
            "99", new Date(), (short) 420, new Date());

    IndividualDeliveredServiceEntity created =
        individualDeliveredServiceDao.create(individualDeliveredServiceEntity);
    assertThat(created, is(individualDeliveredServiceEntity));
  }

  @Test
  public void testCreateExistingEntityException() throws Exception {
    thrown.expect(EntityExistsException.class);
    IndividualDeliveredServiceEntity individualDeliveredServiceEntity =
        new IndividualDeliveredServiceEntity(deliveredServiceId, deliveredToIndividualCode,
            deliveredToIndividualId, "99", new Date(), (short) 420, new Date());

    individualDeliveredServiceDao.create(individualDeliveredServiceEntity);
  }

  @Test
  public void testDelete() throws Exception {
    IndividualDeliveredServiceEmbeddable individualDeliveredServiceEmbeddable =
        new IndividualDeliveredServiceEmbeddable(deliveredServiceId, deliveredToIndividualCode,
            deliveredToIndividualId);

    IndividualDeliveredServiceEntity deleted =
        individualDeliveredServiceDao.delete(individualDeliveredServiceEmbeddable);
    assertThat(deleted.getIndividualDeliveredServiceEmbeddable().getDeliveredServiceId(),
        is(deliveredServiceId));
  }

  @Test
  public void testDeleteEntityNotFoundException() throws Exception {
    IndividualDeliveredServiceEmbeddable individualDeliveredServiceEmbeddable =
        new IndividualDeliveredServiceEmbeddable("jchc70ebdj", "B26hd8gdjd", "0mOx5tsxj6");

    IndividualDeliveredServiceEntity deleted =
        individualDeliveredServiceDao.delete(individualDeliveredServiceEmbeddable);
    assertThat(deleted, is(nullValue()));
  }

  @Test
  public void testUpdate() throws Exception {

    IndividualDeliveredServiceEntity individualDeliveredServiceEntity =
        new IndividualDeliveredServiceEntity(deliveredServiceId, deliveredToIndividualCode,
            deliveredToIndividualId, "00", new Date(), (short) 420, new Date());

    IndividualDeliveredServiceEntity updated =
        individualDeliveredServiceDao.update(individualDeliveredServiceEntity);
    assertThat(updated, is(individualDeliveredServiceEntity));
  }

  @Test
  public void testUpdateEntityNotFoundException() throws Exception {
    thrown.expect(EntityNotFoundException.class);
    IndividualDeliveredServiceEntity individualDeliveredServiceEntity =
        new IndividualDeliveredServiceEntity("CVJSVCjnj", deliveredToIndividualCode, "sbdvh7chhv",
            "00", new Date(), (short) 420, new Date());

    individualDeliveredServiceDao.update(individualDeliveredServiceEntity);

  }

}
