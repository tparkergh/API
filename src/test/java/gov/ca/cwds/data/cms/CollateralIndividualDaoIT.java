package gov.ca.cwds.data.cms;

import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;
import java.math.BigInteger;
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

import com.fasterxml.jackson.databind.ObjectMapper;

import gov.ca.cwds.data.persistence.cms.CollateralIndividual;
import io.dropwizard.jackson.Jackson;

/**
 * 
 * @author CWDS API Team
 */
@SuppressWarnings("javadoc")
public class CollateralIndividualDaoIT {

  static final ObjectMapper MAPPER = Jackson.newObjectMapper();

  private static SessionFactory sessionFactory;
  private static CollateralIndividualDao collateralIndividualDao;
  private Session session;

  String badgeNumber = "12345670";
  Date birthDate = new Date();
  String cityName = "Sacramento";
  String commentDescription = "commentDescription";
  String emailAddress = "abc@123.com";
  String employerName = "EmployerName";
  String establishedForCode = "564";
  BigDecimal faxNumber = BigDecimal.ONE;
  String firstName = "firstName";
  String foreignAddressIndicatorVariable = "variableName";
  String genderCode = "M";
  String lastName = "lastName";
  Short maritalStatus = (short) 12;
  String middleInitialName = "middleName";
  String namePrefixDescription = "PrefixDescription";
  Integer primaryExtensionNumber = 123;
  BigInteger primaryPhoneNo = BigInteger.ONE;
  String residedOutOfStateIndicator = "Y";
  Short stateCode = (short) 890;
  String streetName = "West River";
  String streetNumber = "2751";
  String suffixTitleDescription = "Junior";
  Integer zipNumber = 945833;
  Short zipSuffixNumber = (short) 78;

  /**
   * id matches src/main/resources/db.cms/ci-seeds.sql
   */
  private String id = "AarHGUP0Ki";

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
    collateralIndividualDao = new CollateralIndividualDao(sessionFactory);
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

  /**
   * Find JUnit test
   * 
   * @throws Exception test general
   */
  @Test
  public void testFind() throws Exception {
    CollateralIndividual found = collateralIndividualDao.find(id);
    assertThat(found.getId(), is(equalTo(id)));
  }

  @Test
  public void testFindEntityNotFoundException() throws Exception {
    CollateralIndividual found = collateralIndividualDao.find("9999999ZZZ");
    assertThat(found, is(nullValue()));
  }

  /**
   * Create JUnit test
   * 
   * @throws Exception test general
   */
  @Test
  public void testCreate() throws Exception {

    CollateralIndividual collateralIndividual = new CollateralIndividual(badgeNumber, birthDate,
        cityName, commentDescription, emailAddress, employerName, establishedForCode, faxNumber,
        firstName, foreignAddressIndicatorVariable, genderCode, "ABC1234567", lastName,
        maritalStatus, middleInitialName, namePrefixDescription, primaryExtensionNumber,
        primaryPhoneNo, residedOutOfStateIndicator, stateCode, streetName, streetNumber,
        suffixTitleDescription, zipNumber, zipSuffixNumber);

    CollateralIndividual create = collateralIndividualDao.create(collateralIndividual);
    assertThat(collateralIndividual, is(create));
  }

  @Test(expected = EntityExistsException.class)
  public void testCreateExistingEntityException() throws Exception {

    CollateralIndividual collateralIndividual =
        new CollateralIndividual(badgeNumber, birthDate, cityName, commentDescription, emailAddress,
            employerName, establishedForCode, faxNumber, firstName, foreignAddressIndicatorVariable,
            genderCode, id, lastName, maritalStatus, middleInitialName, namePrefixDescription,
            primaryExtensionNumber, primaryPhoneNo, residedOutOfStateIndicator, stateCode,
            streetName, streetNumber, suffixTitleDescription, zipNumber, zipSuffixNumber);

    collateralIndividualDao.create(collateralIndividual);

  }

  /**
   * Delete JUnit test
   * 
   * @throws Exception test general
   */
  @Test
  public void testDelete() throws Exception {
    CollateralIndividual deleted = collateralIndividualDao.delete(id);
    assertThat(deleted.getId(), is(id));
  }

  @Test
  public void testDeleteEntityNotFoundException() throws Exception {
    CollateralIndividual deleted = collateralIndividualDao.delete("9999999ZZZ");
    assertThat(deleted, is(nullValue()));
  }

  /**
   * Update JUnit test
   * 
   * @throws Exception test general
   */
  @Test
  public void testUpdate() throws Exception {

    CollateralIndividual collateralIndividual =
        new CollateralIndividual(badgeNumber, birthDate, cityName, commentDescription, emailAddress,
            employerName, establishedForCode, faxNumber, firstName, foreignAddressIndicatorVariable,
            genderCode, id, lastName, maritalStatus, middleInitialName, namePrefixDescription,
            primaryExtensionNumber, primaryPhoneNo, residedOutOfStateIndicator, stateCode,
            streetName, streetNumber, suffixTitleDescription, zipNumber, zipSuffixNumber);

    CollateralIndividual updated = collateralIndividualDao.update(collateralIndividual);
    assertThat(collateralIndividual, is(updated));

  }

  @Test(expected = EntityNotFoundException.class)
  public void testUpdateEntityNotFoundException() throws Exception {

    CollateralIndividual collateralIndividual = new CollateralIndividual(badgeNumber, birthDate,
        cityName, commentDescription, emailAddress, employerName, establishedForCode, faxNumber,
        firstName, foreignAddressIndicatorVariable, genderCode, "0pAiuNkf5r", lastName,
        maritalStatus, middleInitialName, namePrefixDescription, primaryExtensionNumber,
        primaryPhoneNo, residedOutOfStateIndicator, stateCode, streetName, streetNumber,
        suffixTitleDescription, zipNumber, zipSuffixNumber);

    collateralIndividualDao.update(collateralIndividual);


  }

}
