package gov.ca.cwds.data.cms;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;

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
import gov.ca.cwds.data.persistence.cms.Assignment;
import gov.ca.cwds.rest.api.domain.DomainChef;

/**
 * @author CWDS API Team
 *
 */
public class AssignmentDaoIT implements DaoTestTemplate {

  private static SessionFactory sessionFactory;
  private static AssignmentDao assignmentDao;
  private Session session;

  private String countySpecificCode = "20";
  private String endDate = "2018-06-01";
  private String endTime = "12:01:00";
  private String establishedForCode = "R";
  private String establishedForId = "12345678ABC";
  private String caseLoadId = "2345678ABC";
  private String outOfStatePartyContactId = "";
  private String responsiblityDescription = "Assignment responsibility description";
  private Short secondaryAssignmentRoleType = 0;
  private String startDate = "2017-06-20";
  private String startTime = "16:41:49";
  private String typeOfAssignmentCode = "P";
  private BigDecimal weightingNumber = new BigDecimal("0.0");
  private String staffId = "0X5";

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
    Assignment found = assignmentDao.find(id);
    assertThat(found.getId(), is(equalTo(id)));
  }

  @Override
  @Test
  public void testFindEntityNotFoundException() throws Exception {
    Assignment found = assignmentDao.find("9999999ZZZ");
    assertThat(found, is(nullValue()));
  }

  @Override
  @Test
  public void testCreate() throws Exception {
    gov.ca.cwds.rest.api.domain.cms.Assignment da = validAssignment();

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

  @Override
  @Test
  public void testCreateExistingEntityException() throws Exception {

    thrown.expect(EntityExistsException.class);
    gov.ca.cwds.rest.api.domain.cms.Assignment da = validAssignment();

    Assignment pa = new Assignment(id, da.getCountySpecificCode(),
        DomainChef.uncookDateString(da.getEndDate()), DomainChef.uncookTimeString(da.getEndTime()),
        da.getEstablishedForCode(), da.getEstablishedForId(), da.getCaseLoadId(),
        da.getOutOfStateContactId(), da.getResponsibilityDescription(),
        da.getSecondaryAssignmentRoleType(), DomainChef.uncookDateString(da.getStartDate()),
        DomainChef.uncookTimeString(da.getStartTime()), da.getTypeOfAssignmentCode(),
        da.getWeightingNumber());

    assignmentDao.create(pa);
  }

  @Override
  @Test
  public void testDelete() throws Exception {
    Assignment deleted = assignmentDao.delete(id);
    assertThat(deleted.getId(), is(id));
  }

  @Override
  @Test
  public void testDeleteEntityNotFoundException() throws Exception {
    Assignment deleted = assignmentDao.delete("9999999ZZZ");
    assertThat(deleted, is(nullValue()));
  }

  @Override
  @Test
  public void testUpdate() throws Exception {
    gov.ca.cwds.rest.api.domain.cms.Assignment da = validAssignment();

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

  @Override
  @Test
  public void testUpdateEntityNotFoundException() throws Exception {

    thrown.expect(EntityNotFoundException.class);
    gov.ca.cwds.rest.api.domain.cms.Assignment da = validAssignment();

    Assignment pa = new Assignment("hfH1234580", da.getCountySpecificCode(),
        DomainChef.uncookDateString(da.getEndDate()), DomainChef.uncookTimeString(da.getEndTime()),
        da.getEstablishedForCode(), da.getEstablishedForId(), da.getCaseLoadId(),
        da.getOutOfStateContactId(), da.getResponsibilityDescription(),
        da.getSecondaryAssignmentRoleType(), DomainChef.uncookDateString(da.getStartDate()),
        DomainChef.uncookTimeString(da.getStartTime()), da.getTypeOfAssignmentCode(),
        da.getWeightingNumber());

    assignmentDao.update(pa);
  }

  @Override
  public void testFindAllNamedQueryExist() throws Exception {

  }

  @Override
  public void testFindAllReturnsCorrectList() throws Exception {

  }

  private gov.ca.cwds.rest.api.domain.cms.Assignment validAssignment() {
    gov.ca.cwds.rest.api.domain.cms.Assignment validAssignment =
        new gov.ca.cwds.rest.api.domain.cms.Assignment(countySpecificCode, endDate, endTime,
            establishedForCode, establishedForId, caseLoadId, outOfStatePartyContactId,
            responsiblityDescription, secondaryAssignmentRoleType, startDate, startTime,
            typeOfAssignmentCode, weightingNumber);
    return validAssignment;
  }

}
