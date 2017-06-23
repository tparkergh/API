package gov.ca.cwds.data.cms;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;

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
import gov.ca.cwds.data.persistence.cms.CaseAssignment;
import gov.ca.cwds.data.persistence.cms.CmsKeyIdGenerator;
import gov.ca.cwds.rest.api.domain.DomainChef;

public class CaseAssignmentDaoIT implements DaoTestTemplate {

  private static SessionFactory sessionFactory;
  private static CaseAssignmentDao dao;
  private Session session;

  private String countySpecificCode = "20";
  private String endDate = "2018-06-01";
  private String endTime = "12:01:00";
  private String establishedForCode = "C";
  private String establishedForId = "0iiVVuE088";
  private String caseLoadId = "2345678ABC";
  private String outOfStatePartyContactId = "";
  private String responsiblityDescription = "CaseAssignment responsibility description";
  private Short secondaryReferralAssignmentRoleType = 0;
  private String startDate = "2017-06-20";
  private String startTime = "16:41:49";
  private String typeOfReferralAssignmentCode = "P";
  private BigDecimal weightingNumber = new BigDecimal("0.0");
  private String staffId = "0X5";

  /**
   * id matches src/main/resources/db.cms/ci-seeds.sql
   */
  private String id = "5rVkB8c088";

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
    dao = new CaseAssignmentDao(sessionFactory);
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
  // @Ignore
  public void testFind() throws Exception {
    CaseAssignment found = dao.find(id);
    assertThat(found.getId(), is(equalTo(id)));
  }

  @Override
  @Test
  // @Ignore
  public void testFindEntityNotFoundException() throws Exception {
    CaseAssignment found = dao.find("xxxxxyzuk3");
    assertThat(found, is(nullValue()));
  }

  @Override
  @Test
  // @Ignore
  public void testCreate() throws Exception {
    gov.ca.cwds.rest.api.domain.cms.Assignment da = validCaseAssignment();

    CaseAssignment pa = new CaseAssignment(countySpecificCode, DomainChef.uncookDateString(endDate),
        DomainChef.uncookTimeString(endTime), establishedForId, caseLoadId,
        outOfStatePartyContactId, responsiblityDescription, secondaryReferralAssignmentRoleType,
        DomainChef.uncookDateString(startDate), DomainChef.uncookTimeString(startTime),
        typeOfReferralAssignmentCode, weightingNumber);

    pa.setId(CmsKeyIdGenerator.generate(staffId));

    CaseAssignment create = dao.create(pa);
    assertThat(pa, is(create));
  }

  private gov.ca.cwds.rest.api.domain.cms.Assignment validCaseAssignment() {
    gov.ca.cwds.rest.api.domain.cms.Assignment validReferralAssignment =
        new gov.ca.cwds.rest.api.domain.cms.Assignment(countySpecificCode, endDate, endTime,
            establishedForCode, establishedForId, caseLoadId, outOfStatePartyContactId,
            responsiblityDescription, secondaryReferralAssignmentRoleType, startDate, startTime,
            typeOfReferralAssignmentCode, weightingNumber);
    return validReferralAssignment;
  }

  @Override
  public void testFindAllNamedQueryExist() throws Exception {}

  @Override
  public void testFindAllReturnsCorrectList() throws Exception {}

  @Override
  public void testCreateExistingEntityException() throws Exception {}

  @Override
  public void testDelete() throws Exception {}

  @Override
  public void testDeleteEntityNotFoundException() throws Exception {}

  @Override
  public void testUpdate() throws Exception {}

  @Override
  public void testUpdateEntityNotFoundException() throws Exception {}

}
