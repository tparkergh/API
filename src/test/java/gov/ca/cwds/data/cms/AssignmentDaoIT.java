package gov.ca.cwds.data.cms;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.Is.is;
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

import gov.ca.cwds.data.persistence.cms.Assignment;
import gov.ca.cwds.fixture.AssignmentResourceBuilder;
import gov.ca.cwds.rest.api.domain.DomainChef;

/**
 * @author CWDS API Team
 *
 */
@SuppressWarnings("javadoc")
public class AssignmentDaoIT {

  private static SessionFactory sessionFactory;
  private static AssignmentDao assignmentDao;
  private Session session;

  /**
   * id matches src/main/resources/db.cms/ci-seeds.sql
   */
  private String id = "SlCAr46088";

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
    assignmentDao = new AssignmentDao(sessionFactory);
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
    Assignment found = assignmentDao.find(id);
    assertThat(found.getId(), is(equalTo(id)));
  }

  @Test
  public void testFindEntityNotFoundException() throws Exception {
    Assignment found = assignmentDao.find("9999999ZZZ");
    assertThat(found, is(nullValue()));
  }


  @Test
  public void testCreate() throws Exception {
    gov.ca.cwds.rest.api.domain.cms.Assignment da =
        new AssignmentResourceBuilder().buildAssignment();

    Assignment pa = new Assignment("ABC0987654", da.getCountySpecificCode(),
        DomainChef.uncookDateString(da.getEndDate()), DomainChef.uncookTimeString(da.getEndTime()),
        da.getEstablishedForCode(), da.getEstablishedForId(), da.getCaseLoadId(),
        da.getOutOfStateContactId(), da.getResponsibilityDescription(),
        da.getSecondaryAssignmentRoleType(), DomainChef.uncookDateString(da.getStartDate()),
        DomainChef.uncookTimeString(da.getStartTime()), da.getTypeOfAssignmentCode(),
        da.getWeightingNumber());

    Assignment create = assignmentDao.create(pa);
    assertThat(pa, is(create));
  }


  @Test
  public void testCreateExistingEntityException() throws Exception {

    thrown.expect(EntityExistsException.class);
    gov.ca.cwds.rest.api.domain.cms.Assignment da =
        new AssignmentResourceBuilder().buildAssignment();

    Assignment pa = new Assignment(id, da.getCountySpecificCode(),
        DomainChef.uncookDateString(da.getEndDate()), DomainChef.uncookTimeString(da.getEndTime()),
        da.getEstablishedForCode(), da.getEstablishedForId(), da.getCaseLoadId(),
        da.getOutOfStateContactId(), da.getResponsibilityDescription(),
        da.getSecondaryAssignmentRoleType(), DomainChef.uncookDateString(da.getStartDate()),
        DomainChef.uncookTimeString(da.getStartTime()), da.getTypeOfAssignmentCode(),
        da.getWeightingNumber());

    assignmentDao.create(pa);
  }


  @Test
  public void testDelete() throws Exception {
    Assignment deleted = assignmentDao.delete(id);
    assertThat(deleted.getId(), is(id));
  }


  @Test
  public void testDeleteEntityNotFoundException() throws Exception {
    Assignment deleted = assignmentDao.delete("9999999ZZZ");
    assertThat(deleted, is(nullValue()));
  }


  @Test
  public void testUpdate() throws Exception {
    gov.ca.cwds.rest.api.domain.cms.Assignment da =
        new AssignmentResourceBuilder().buildAssignment();

    Assignment pa = new Assignment(id, da.getCountySpecificCode(),
        DomainChef.uncookDateString(da.getEndDate()), DomainChef.uncookTimeString(da.getEndTime()),
        da.getEstablishedForCode(), da.getEstablishedForId(), da.getCaseLoadId(),
        da.getOutOfStateContactId(), da.getResponsibilityDescription(),
        da.getSecondaryAssignmentRoleType(), DomainChef.uncookDateString(da.getStartDate()),
        DomainChef.uncookTimeString(da.getStartTime()), da.getTypeOfAssignmentCode(),
        da.getWeightingNumber());

    Assignment updated = assignmentDao.update(pa);
    assertThat(pa, is(updated));
  }


  @Test(expected = EntityNotFoundException.class)
  public void testUpdateEntityNotFoundException() throws Exception {

    gov.ca.cwds.rest.api.domain.cms.Assignment da =
        new AssignmentResourceBuilder().buildAssignment();

    Assignment pa = new Assignment("hfH1234580", da.getCountySpecificCode(),
        DomainChef.uncookDateString(da.getEndDate()), DomainChef.uncookTimeString(da.getEndTime()),
        da.getEstablishedForCode(), da.getEstablishedForId(), da.getCaseLoadId(),
        da.getOutOfStateContactId(), da.getResponsibilityDescription(),
        da.getSecondaryAssignmentRoleType(), DomainChef.uncookDateString(da.getStartDate()),
        DomainChef.uncookTimeString(da.getStartTime()), da.getTypeOfAssignmentCode(),
        da.getWeightingNumber());

    assignmentDao.update(pa);
  }

  @Test
  public void testWhenCaseLoadIdFound() throws Exception {
    String caseLoadid = assignmentDao.findCaseId("0Al");
    assertThat(caseLoadid, is(equalTo("12z5Qos09B")));
  }

  @Test
  public void testWhenCaseLoadIdNotFound() throws Exception {
    String caseLoadid = assignmentDao.findCaseId("q1p");
    assertThat(caseLoadid, is(nullValue()));
  }

}
